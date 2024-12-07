package org.myproject.project1.utils;

import lombok.experimental.UtilityClass;
import org.myproject.project1.core.Node;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author nguyenle
 * @since 12:57 AM Thu 11/14/2024
 */
@UtilityClass
public class GraphUtils {

    public static List<NodeDirected> generateDirectedNode(int size) {
        List<NodeDirected> nodes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            NodeDirected node = new NodeDirected(UUIDUtils.generateUUID());
            nodes.add(node);
        }
        return nodes;
    }

    public static List<EdgeDirected> generateDirectedEdge(List<NodeDirected> nodes, int size) {
        List<String> nodeIds = nodes.stream().map(NodeDirected::getId).toList();
        Map<String, NodeDirected> mapIdToNode = nodes.stream().collect(Collectors.toMap(
                NodeDirected::getId,
                node -> node
        ));
        List<EdgeDirected> edges = new ArrayList<>();
        List<String> sourceIds = RandomUtils.getNRandomElement(nodeIds, size);
        List<String> targetIds = RandomUtils.getNRandomElement(nodeIds, size);
        for (int i = 0; i < size; i++) {
            int weight = RandomUtils.randomInRange(1, 10);
            EdgeDirected edge = new EdgeDirected(UUIDUtils.generateUUID(), weight, sourceIds.get(i), targetIds.get(i));
            edges.add(edge);
            NodeDirected sourceNode = mapIdToNode.get(sourceIds.get(i));
            NodeDirected targetNode = mapIdToNode.get(targetIds.get(i));
            sourceNode.addSourceEdge(edge);
            targetNode.addTargetEdge(edge);
        }
        return edges;
    }

    public static List<NodeUndirected> generateUndirectedNode(int size) {
        List<NodeUndirected> nodes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            NodeUndirected node = new NodeUndirected(UUIDUtils.generateUUID());
            nodes.add(node);
        }
        return nodes;
    }

    public static List<EdgeUndirected> generateUndirectedEdge(List<NodeUndirected> nodes, int size) {
        List<String> nodeIds = nodes.stream().map(NodeUndirected::getId).toList();
        Map<String, NodeUndirected> mapIdToNode = nodes.stream().collect(Collectors.toMap(
                NodeUndirected::getId,
                node -> node
        ));
        List<EdgeUndirected> edges = new ArrayList<>();
        List<String> edgeHead1 = RandomUtils.getNRandomElement(nodeIds, size);
        List<String> edgeHead2 = RandomUtils.getNRandomElement(nodeIds, size);
        for (int i  = 0; i < size; ++i) {
            int weight = RandomUtils.randomInRange(1, 10);
            EdgeUndirected edge = new EdgeUndirected(UUIDUtils.generateUUID(), weight, edgeHead1.get(i), edgeHead2.get(i));
            edges.add(edge);
            NodeUndirected head1Node = mapIdToNode.get(edgeHead1.get(i));
            NodeUndirected head2Node = mapIdToNode.get(edgeHead2.get(i));
            head1Node.addEdge(edge);
            head2Node.addEdge(edge);
        }
        return edges;
    }
}
