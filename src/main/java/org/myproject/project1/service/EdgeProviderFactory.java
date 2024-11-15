package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.*;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.utils.UUIDUtils;
import org.springframework.stereotype.Service;

/**
 * @author nguyenle
 * @since 11:06 PM Tue 11/12/2024
 */
@Service
@RequiredArgsConstructor
public class EdgeProviderFactory {

    public Edge createEdge(Graph graph, Node source, Node target) {
        return createEdge(graph, source, target, 1);
    }

    public Edge createEdge(Graph graph, Node source, Node target, int weight) {
        switch (graph.getType()) {
            case DIRECTED:
                return createEdgeDirected(graph, (NodeDirected) source, (NodeDirected) target, weight);
            case UNDIRECTED:
                return createEdgeUndirected(graph, (NodeUndirected) source, (NodeUndirected) target, weight);
            default:
                return null;
        }
    }

    private EdgeDirected createEdgeDirected(Graph graph, NodeDirected source, NodeDirected target, int weight) {
        String edgeId = UUIDUtils.generateUUID();
        EdgeDirected edge = new EdgeDirected(edgeId, weight, source.getId(), target.getId());
        source.addFromEdge(edge);
        target.addToEdge(edge);
        graph.addNewEdge(edge);
        return edge;
    }

    private EdgeUndirected createEdgeUndirected(Graph graph, NodeUndirected source, NodeUndirected target, int weight) {
        String edgeId = UUIDUtils.generateUUID();
        EdgeUndirected edge = new EdgeUndirected(edgeId, weight, source.getId(), target.getId());
        source.addEdge(edge);
        target.addEdge(edge);
        graph.addNewEdge(edge);
        return edge;
    }

}
