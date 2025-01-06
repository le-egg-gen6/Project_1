package org.myproject.project1.utils;

import java.util.HashSet;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.myproject.project1.core.Edge;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.Node;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.dto.EdgeDTO;
import org.myproject.project1.dto.NodeDTO;
import org.myproject.project1.dto.PathTraversalDTO;

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
        for (int i = 0; i < size; ++i) {
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

    public static PathTraversalDTO constructPath(
            Graph graph,
            int distance,
            Map<String, String> previousNodes,
            Map<String, String> previousEdges
    ) {
        PathTraversalDTO result = PathTraversalDTO.foundPath(graph);

        Set<String> highlightedNodes = new HashSet<>();
        Set<String> highlightedEdges = new HashSet<>();
        for (Map.Entry<String, String> p : previousNodes.entrySet()) {
            highlightedNodes.add(p.getValue());
            highlightedNodes.add(p.getKey());
        }
        for (Map.Entry<String, String> p : previousEdges.entrySet()) {
            highlightedEdges.add(p.getValue());
            highlightedEdges.add(p.getKey());
        }

        List<NodeDTO> nodes = highlightedNodes.stream().map(nodeId -> new NodeDTO(graph.getNode(nodeId))).toList();
        List<EdgeDTO> edges = highlightedEdges.stream().map(edgeId -> new EdgeDTO(graph.getEdge(edgeId))).toList();

        result.setTotalWeight(distance);
        result.setNodes(nodes);
        result.setEdges(edges);

        return result;

    }

    public static PathTraversalDTO constructHamiltonPath(
            Graph graph,
            List<String> usedEdge
    ) {
        Set<String> highlightedNodes = new HashSet<>();
        Set<String> highlightedEdges = new HashSet<>(usedEdge);

        PathTraversalDTO result = PathTraversalDTO.foundPath(graph);
        List<NodeDTO> nodes = new ArrayList<>();
        List<EdgeDTO> edges = new ArrayList<>();

        int totalWeight = 0;
        for (String edgeId : highlightedEdges) {
            Edge edge = graph.getEdge(edgeId);
            EdgeDTO edgeDTO = new EdgeDTO(edge);
            edges.add(edgeDTO);
            totalWeight += edge.getWeight();
            if (edge instanceof EdgeUndirected edgeUndirected) {
                highlightedNodes.addAll(edgeUndirected.getNodes());
            }
        }


        for (String nodeId : highlightedNodes) {
            nodes.add(new NodeDTO(graph.getNode(nodeId)));
        }

        result.setNodes(nodes);
        result.setTotalWeight(totalWeight);
        result.setEdges(edges);

        return result;
    }
}
