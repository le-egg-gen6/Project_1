package org.myproject.project1.service;

import java.util.List;
import java.util.stream.Collectors;
import org.myproject.project1.core.Edge;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.utils.UUIDUtils;
import org.springframework.stereotype.Service;

/**
 * @author nguyenle
 * @since 4:14 PM Tue 11/12/2024
 */
@Service
public class GraphInitializerService {

	private InMemoryGraphStoreService graphStoreService;

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

	}

	private void removeNodeUndirectedGraph(Graph graph, String nodeId) {
	}

}
