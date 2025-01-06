package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.Edge;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.Node;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.shared.GraphType;
import org.myproject.project1.utils.GraphUtils;
import org.myproject.project1.utils.RandomUtils;
import org.myproject.project1.utils.UUIDUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nguyenle
 * @since 10:39 PM Sat 12/7/2024
 */
@Service
@RequiredArgsConstructor
public class GraphService {

    private final InMemoryGraphStoreService graphStoreService;

    public Graph initNewGraph(GraphType type, List<List<Integer>> graphArray) {
        Graph graph = new Graph(type);
	    switch (type) {
		    case DIRECTED:
			    initDirectedGraphFromArray(graph, graphArray);
			    break;
		    case UNDIRECTED:
			    initUndirectedGraphFromArray(graph, graphArray);
			    break;
	    }
        graphStoreService.addGraph(graph);
        return graph;
    }

	private void initDirectedGraphFromArray(Graph graph, List<List<Integer>> graphArray) {
		List<Node> nodes = new ArrayList<>();
		for (int i = 0; i < graphArray.size(); i++) {
			nodes.add(addNewNode(graph));
		}
		for (int i = 0; i < graphArray.size(); i++) {
			Node souceNode = nodes.get(i);
			for (int j = i; j < graphArray.get(i).size(); j++) {
				Node targetNode = nodes.get(j);
				if (graphArray.get(i).get(j) != 0) {
					int weight = graphArray.get(i).get(j);
					addNewEdge(graph, souceNode, targetNode, weight);
				}
			}
		}
	}

	private void initUndirectedGraphFromArray(Graph graph, List<List<Integer>> graphArray) {
		List<Node> nodes = new ArrayList<>();
		for (int i = 0; i < graphArray.size(); i++) {
			nodes.add(addNewNode(graph));
		}
		for (int i = 0; i < graphArray.size(); i++) {
			Node souceNode = nodes.get(i);
			for (int j = 0; j < graphArray.get(i).size(); j++) {
				Node targetNode = nodes.get(j);
				if (graphArray.get(i).get(j) != 0) {
					int weight = graphArray.get(i).get(j);
					addNewEdge(graph, souceNode, targetNode, weight);
				}
			}
		}
	}

    public Graph initNewRandomGraph(GraphType type) {
        Graph graph = new Graph(type);
        switch (type) {
            case DIRECTED:
                addNewRandomFeatureForDirectedGraph(graph);
                break;
            case UNDIRECTED:
                addNewRandomFeatureForUndirectedGraph(graph);
                break;
            default:
                break;
        }
        graphStoreService.addGraph(graph);
        return graph;
    }

    private void addNewRandomFeatureForDirectedGraph(Graph graph) {
        int vertexCount = RandomUtils.randomInRange(10, 30);
        List<NodeDirected> nodes = GraphUtils.generateDirectedNode(vertexCount);
        int maximumEdge = (int) (vertexCount * RandomUtils.randomInRange(1.25f, 1.75f));
        int edgeCount = RandomUtils.randomInRange(vertexCount, maximumEdge);
        List<EdgeDirected> edges = GraphUtils.generateDirectedEdge(nodes, edgeCount);
        for (NodeDirected node : nodes) {
            graph.addNewNode(node);
        }
        for (EdgeDirected edge : edges) {
            graph.addNewEdge(edge);
        }
    }

    private void addNewRandomFeatureForUndirectedGraph(Graph graph) {
        int vertexCount = RandomUtils.randomInRange(10, 30);
        List<NodeUndirected> nodes = GraphUtils.generateUndirectedNode(vertexCount);
        int maximumEdge = (int) (vertexCount * RandomUtils.randomInRange(1.25f, 1.75f));
        int edgeCount = RandomUtils.randomInRange(vertexCount, maximumEdge);
        List<EdgeUndirected> edges = GraphUtils.generateUndirectedEdge(nodes, edgeCount);
        for (NodeUndirected node : nodes) {
            graph.addNewNode(node);
        }
        for (EdgeUndirected edge : edges) {
            graph.addNewEdge(edge);
        }
    }

    public List<Graph> getAllGraph(GraphType type) {
        List<String> graphIds = graphStoreService.getAllUserGraphId();
        List<Graph> graphs = new ArrayList<>(graphIds.size());
        for (String graphId : graphIds) {
            Graph graph = graphStoreService.getGraph(graphId);
            if (graph != null && graph.getType() == type) {
                graphs.add(graph);
            }
        }
        return graphs;
    }

    public Graph getGraph(String graphId) {
        return graphStoreService.getGraph(graphId);
    }

	public Node addNewNode(Graph graph) {
		graph.setUniqueHash(UUIDUtils.generateUUID());
		switch (graph.getType()) {
			case DIRECTED:
				return addNewDirectedNode(graph);
			case UNDIRECTED:
				return addNewUndirectedNode(graph);
		}
		return null;
	}

    private NodeDirected addNewDirectedNode(Graph graph) {
		NodeDirected node = new NodeDirected();
		node.setId(UUIDUtils.generateUUID());
		graph.addNewNode(node);
		return node;
    }

	private NodeUndirected addNewUndirectedNode(Graph graph) {
		NodeUndirected node = new NodeUndirected();
		node.setId(UUIDUtils.generateUUID());
		graph.addNewNode(node);
		return node;
	}

	public Edge addNewEdge(Graph graph, Node source, Node target, int weight) {
		if (source == null || target == null) {
			return null;
		}
		graph.setUniqueHash(UUIDUtils.generateUUID());
		switch (graph.getType()) {
			case DIRECTED:
				return addNewEdgeDirected(graph, (NodeDirected) source, (NodeDirected) target, weight);
			case UNDIRECTED:
				return addNewEdgeUndirected(graph, (NodeUndirected) source, (NodeUndirected) target, weight);
		}
		return null;
	}

	private EdgeDirected addNewEdgeDirected(Graph graph, NodeDirected source, NodeDirected target, int weight) {
		EdgeDirected edge = new EdgeDirected();
		edge.setId(UUIDUtils.generateUUID());
		edge.setWeight(weight);
		edge.setSource(source.getId());
		edge.setTarget(target.getId());
		source.addSourceEdge(edge);
		target.addTargetEdge(edge);
		graph.addNewEdge(edge);
		return edge;
	}

	private EdgeUndirected addNewEdgeUndirected(Graph graph, NodeUndirected source, NodeUndirected target, int weight) {
		EdgeUndirected edge = new EdgeUndirected();
		edge.setId(UUIDUtils.generateUUID());
		edge.setWeight(weight);
		edge.setNodes(source.getId(), target.getId());
		source.addEdge(edge);
		target.addEdge(edge);
		graph.addNewEdge(edge);
		return edge;
	}


}
