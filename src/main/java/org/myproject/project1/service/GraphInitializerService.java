package org.myproject.project1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.myproject.project1.core.Edge;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.shared.GraphType;
import org.myproject.project1.utils.UUIDUtils;
import org.springframework.stereotype.Service;

/**
 * @author nguyenle
 * @since 4:14 PM Tue 11/12/2024
 */
@Service
public class GraphInitializerService {

	private NodeProviderFactory nodeProviderFactory;

	private InMemoryGraphStoreService graphStoreService;

	public Graph initNewGraph(GraphType type) {
		Graph graph = new Graph(type);
		graphStoreService.addGraph(graph);
		return graph;
	}

	public void setEdgeWeight(String graphId, String edgeId, int weight) {
		Graph graph = graphStoreService.getGraph(graphId);
		if (graph == null) {
			return;
		}
		graph.setUniqueHash(UUIDUtils.generateUUID());
		Edge edge = graph.getEdge(edgeId);
		if (edge == null) {
			return;
		}
		edge.setWeight(weight);
	}

	public void removeEdge(String graphId, String edgeId) {
		Graph graph = graphStoreService.getGraph(graphId);
		if (graph == null) {
			return;
		}
		graph.setUniqueHash(UUIDUtils.generateUUID());
		switch (graph.getType()) {
			case DIRECTED:
				removeEdgeDirectedGraph(graph, edgeId);
				break;
			case UNDIRECTED:
				removeEdgeUndirectedGraph(graph, edgeId);
				break;
			default:
				break;
		}
	}

	private void removeEdgeDirectedGraph(Graph graph, String edgeId) {
		EdgeDirected edgeDirected = (EdgeDirected) graph.getEdge(edgeId);
		String sourceId = edgeDirected.getFromId();
		String targetId = edgeDirected.getToId();
		NodeDirected sourceNode = (NodeDirected) graph.getNode(sourceId);
		NodeDirected targetNode = (NodeDirected) graph.getNode(targetId);
		sourceNode.getFromEdges().remove(edgeId);
		targetNode.getToEdges().remove(edgeId);
		graph.getEdges().remove(edgeId);
	}

	private void removeEdgeUndirectedGraph(Graph graph, String edgeId) {
		EdgeUndirected edgeUndirected = (EdgeUndirected) graph.getEdge(edgeId);
		List<NodeUndirected> nodeAdjacent = edgeUndirected.getNodes().stream()
			.map(nodeId -> (NodeUndirected)graph.getNode(nodeId))
			.toList();
		for (NodeUndirected node : nodeAdjacent) {
			node.getEdges().remove(edgeId);
		}
		graph.getEdges().remove(edgeId);
	}

	public void removeNode(String graphId, String nodeId) {
		Graph graph = graphStoreService.getGraph(graphId);
		if (graph == null) {
			return;
		}
		graph.setUniqueHash(UUIDUtils.generateUUID());
		switch (graph.getType()) {
			case DIRECTED:
				removeNodeDirectedGraph(graph, nodeId);
				break;
			case UNDIRECTED:
				removeNodeUndirectedGraph(graph, nodeId);
				break;
			default:
				break;
		}
	}

	private void removeNodeDirectedGraph(Graph graph, String nodeId) {
		NodeDirected nodeDirected = (NodeDirected) graph.getNode(nodeId);
		Set<String> edgeFroms = nodeDirected.getFromEdges();
		Set<String> edgeTos = nodeDirected.getToEdges();
		for (String edgeId : edgeFroms) {
			EdgeDirected edgeDirected = (EdgeDirected) graph.getEdge(edgeId);
			String adjacentNodeId = edgeDirected.getToId();
			NodeDirected adjacentNode = (NodeDirected) graph.getNode(adjacentNodeId);
			adjacentNode.getToEdges().remove(edgeId);
		}
		for (String edgeId : edgeTos) {
			EdgeDirected edgeDirected = (EdgeDirected) graph.getEdge(edgeId);
			String adjacentNodeId = edgeDirected.getFromId();
			NodeDirected adjacentNode = (NodeDirected) graph.getNode(adjacentNodeId);
			adjacentNode.getFromEdges().remove(edgeId);
		}
		edgeFroms.forEach(edgeId -> graph.getEdges().remove(edgeId));
		edgeTos.forEach(edgeId -> graph.getEdges().remove(edgeId));
		graph.getNodes().remove(nodeId);
	}

	private void removeNodeUndirectedGraph(Graph graph, String nodeId) {
		NodeUndirected nodeUndirected = (NodeUndirected) graph.getNode(nodeId);
		Set<String> edges = nodeUndirected.getEdges();
		for (String edgeId : edges) {
			EdgeUndirected edgeUndirected = (EdgeUndirected) graph.getEdge(edgeId);
			List<String> adjacentNodeIds = edgeUndirected.getNodes();
			for (String adjacentNodeId : adjacentNodeIds) {
				NodeUndirected adjacentNode = (NodeUndirected) graph.getNode(adjacentNodeId);
				adjacentNode.getEdges().remove(edgeId);
			}
		}
		edges.forEach(edgeId -> graph.getEdges().remove(edgeId));
		graph.getNodes().remove(nodeId);
	}

	public void addNewNodeGraph(String graphId, String... nodeIds) {
		Graph graph = graphStoreService.getGraph(graphId);
		if (graph == null) {
			return;
		}
		graph.setUniqueHash(UUIDUtils.generateUUID());
		switch (graph.getType()) {
			case DIRECTED:
				addNewNodeDirectedGraph(graph, nodeIds);
				break;
			case UNDIRECTED:
				addNewNodeUndirectedGraph(graph, nodeIds);
				break;
			default:
				break;
		}
	}

	private void addNewNodeDirectedGraph(Graph graph, String... nodeIds) {
		List<NodeDirected> adjacentNodes = new ArrayList<>();
		for (String nodeId : nodeIds) {
			NodeDirected nodeDirected = (NodeDirected) graph.getNode(nodeId);
			adjacentNodes.add(nodeDirected);
		}
		nodeProviderFactory.createNewNode(graph, adjacentNodes.toArray(new NodeDirected[0]));
	}

	private void addNewNodeUndirectedGraph(Graph graph, String... nodeIds) {
		List<NodeUndirected> adjacentNodes = new ArrayList<>();
		for (String nodeId : nodeIds) {
			NodeUndirected nodeUndirected = (NodeUndirected) graph.getNode(nodeId);
			adjacentNodes.add(nodeUndirected);
		}
		nodeProviderFactory.createNewNode(graph, adjacentNodes.toArray(new NodeUndirected[0]));
	}

}
