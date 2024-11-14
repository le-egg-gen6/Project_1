package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.*;
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
                return createEdgeDirected(graph, source, target, weight);
            case UNDIRECTED:
                return createEdgeUndirected(graph, source, target, weight);
            default:
                return null;
        }
    }

    private EdgeDirected createEdgeDirected(Graph graph, Node source, Node target, int weight) {
        String edgeId = UUIDUtils.generateUUID();
        EdgeDirected edge = new EdgeDirected(edgeId, weight, source.getId(), target.getId());
        NodeDirected sourceDirected = (NodeDirected)source;
        sourceDirected.addFromEdge(edge);
        NodeDirected targetDirected = (NodeDirected)target;
        targetDirected.addToEdge(edge);
        graph.addNewEdge(edge);
        return edge;
    }

    private EdgeUndirected createEdgeUndirected(Graph graph, Node source, Node target, int weight) {
        String edgeId = UUIDUtils.generateUUID();
        EdgeUndirected edge = new EdgeUndirected(edgeId, weight, source.getId(), target.getId());
        NodeUndirected sourceDirected = (NodeUndirected) source;
        sourceDirected.addEdge(edge);
        NodeUndirected targetDirected = (NodeUndirected)target;
        targetDirected.addEdge(edge);
        graph.addNewEdge(edge);
        return edge;
    }

}
