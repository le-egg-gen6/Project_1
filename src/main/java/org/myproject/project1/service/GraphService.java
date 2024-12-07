package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.shared.GraphType;
import org.myproject.project1.utils.GraphUtils;
import org.myproject.project1.utils.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nguyenle
 * @since 10:39 PM Sat 12/7/2024
 */
@Service
@RequiredArgsConstructor
public class GraphService {

    private final InMemoryGraphStoreService graphStoreService;

    public Graph initNewGraph(GraphType type) {
        Graph graph = new Graph(type);
        graphStoreService.addGraph(graph);
        return graph;
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

}
