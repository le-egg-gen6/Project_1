package org.myproject.project1.utils;

import lombok.experimental.UtilityClass;
import org.myproject.project1.core.*;
import org.myproject.project1.dto.EdgeDTO;
import org.myproject.project1.dto.GraphDTO;
import org.myproject.project1.dto.NodeDTO;
import org.myproject.project1.dto.PathTraversalDTO;
import org.myproject.project1.shared.GraphType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nguyenle
 * @since 12:57 AM Thu 11/14/2024
 */
@UtilityClass
public class GraphUtils {

    public static GraphDTO convertToGraphDTO(Graph graph) {
        switch (graph.getType()) {
            case DIRECTED:
                return convertToDirectedGraphDTO(graph);
            case UNDIRECTED:
                return convertToUndirectedGraphDTO(graph);
            default:
                return null;
        }
    }

    private static GraphDTO convertToDirectedGraphDTO(Graph graph) {
        Map<String, NodeDirected> nodes = graph.getDirectedNodes();
        Map<String, Edge> edges = graph.getEdges();
        Map<String, EdgeDTO> edgeDTOs = new HashMap<>();
        Map<String, NodeDTO> nodeDTOs = new HashMap<>();
        for (Map.Entry<String, Edge> entry : edges.entrySet()) {
            edgeDTOs.put(entry.getKey(), new EdgeDTO(entry.getValue()));
        }
        for (Map.Entry<String, NodeDirected> entry : nodes.entrySet()) {
            NodeDirected node = entry.getValue();
            nodeDTOs.put(entry.getKey(), new NodeDTO(node));
            for (String from : node.getFromEdges()) {
                edgeDTOs.get(from).setSourceId(node.getId());
            }
            for (String to : node.getToEdges()) {
                edgeDTOs.get(to).setTargetId(node.getId());
            }
        }
        return new GraphDTO(graph.getId(), graph.getType(), nodeDTOs, edgeDTOs);
    }

    private static GraphDTO convertToUndirectedGraphDTO(Graph graph) {
        Map<String, NodeUndirected> nodes = graph.getUndirectedNodes();
        Map<String, Edge> edges = graph.getEdges();
        Map<String, EdgeDTO> edgeDTOs = new HashMap<>();
        Map<String, NodeDTO> nodeDTOs = new HashMap<>();
        for (Map.Entry<String, Edge> entry : edges.entrySet()) {
            edgeDTOs.put(entry.getKey(), new EdgeDTO(entry.getValue()));
        }
        for (Map.Entry<String, NodeUndirected> entry : nodes.entrySet()) {
            NodeUndirected node = entry.getValue();
            nodeDTOs.put(entry.getKey(), new NodeDTO(node));
            for (String edge : node.getEdges()) {
                edgeDTOs.get(edge).addNodeForUndirectedGraph(entry.getKey());
            }
        }
        return new GraphDTO(graph.getId(), graph.getType(), nodeDTOs, edgeDTOs);
    }

    public static PathTraversalDTO constructPath(Graph graph, Node start, Node end, Map<String, String> previousNodes) {
        boolean isDirectedGraph = graph.getType() == GraphType.DIRECTED;
        int totalWeight = 0;
        List<EdgeDTO> pathEdges = new ArrayList<>();
        String currentNodeId = end.getId();
        while (previousNodes.containsKey(currentNodeId)) {
            String previousNodeId = previousNodes.get(currentNodeId);
            Edge edge = graph.getEdge(currentNodeId);
            if (edge != null) {

            }
            currentNodeId = previousNodeId;
        }
        Collections.reverse(pathEdges);  // Reverse to get the path from start to end
        return new PathTraversalDTO(
                true,
                totalWeight,
                graph.getType(),
                new NodeDTO(start.getId()),
                new NodeDTO(end.getId()),
                pathEdges
        );
    }

    public static boolean isRing(NodeUndirected node, EdgeUndirected edge) {
        return edge.getNodes().stream().filter(id -> id.equals(node.getId())).count() > 1;
    }

    public static boolean isRing(NodeDirected node, EdgeDirected edge) {
        return edge.getFromId().contains(node.getId())
                && edge.getToId().contains(node.getId());
    }
}
