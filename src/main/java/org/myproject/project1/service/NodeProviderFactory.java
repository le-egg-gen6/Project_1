package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.*;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.utils.UUIDUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author nguyenle
 * @since 4:46 PM Tue 11/12/2024
 */
@Service
@RequiredArgsConstructor
public class NodeProviderFactory {

    private final EdgeProviderFactory edgeProviderFactory;

    public Node createNewNode(Graph graph, Node... nodes) {
        switch (graph.getType()) {
            case DIRECTED:
                return createNodeDirected(graph, (NodeDirected[]) nodes);
            case UNDIRECTED:
                return createNodeUndirected(graph, (NodeUndirected[]) nodes);
            default:
                return null;
        }
    }

    private NodeDirected createNodeDirected(Graph graph, NodeDirected... nodes) {
        String nodeId = UUIDUtils.generateUUID();
        NodeDirected node = new NodeDirected(nodeId);
        Arrays.stream(nodes).toList()
                .forEach(n -> {
                    edgeProviderFactory.createEdge(graph, n, node, 1);
                });
        graph.addNewNode(node);
        return node;
    }

    private NodeUndirected createNodeUndirected(Graph graph, NodeUndirected... nodes) {
        String nodeId = UUIDUtils.generateUUID();
        NodeUndirected node = new NodeUndirected(nodeId);
        Arrays.stream(nodes).toList()
                .forEach(n -> {
                    edgeProviderFactory.createEdge(graph, n, node, 1);
                });
        graph.addNewNode(node);
        return node;
    }

}
