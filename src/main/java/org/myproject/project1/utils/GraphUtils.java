package org.myproject.project1.utils;

import lombok.experimental.UtilityClass;
import org.myproject.project1.core.*;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.dto.EdgeDTO;
import org.myproject.project1.dto.GraphDTO;
import org.myproject.project1.dto.NodeDTO;
import org.myproject.project1.dto.PathTraversalDTO;

import java.util.*;

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
		Map<String, EdgeDirected> edges = graph.getDirectedEdges();
		Map<String, EdgeDTO> edgeDTOs = new HashMap<>();
		Map<String, NodeDTO> nodeDTOs = new HashMap<>();
		for (Map.Entry<String, EdgeDirected> entry : edges.entrySet()) {
            String edgeId = entry.getKey();
            EdgeDirected edge = entry.getValue();
            EdgeDTO edgeDTO = new EdgeDTO(entry.getValue());
            edgeDTO.setDirectedEdge(edge.getFromId(), edge.getToId());
			edgeDTOs.put(edgeId, edgeDTO);
		}
		for (Map.Entry<String, NodeDirected> entry : nodes.entrySet()) {
			NodeDirected node = entry.getValue();
			nodeDTOs.put(entry.getKey(), new NodeDTO(node));
		}
		return new GraphDTO(graph.getId(), graph.getType(), nodeDTOs, edgeDTOs);
	}

	private static GraphDTO convertToUndirectedGraphDTO(Graph graph) {
		Map<String, NodeUndirected> nodes = graph.getUndirectedNodes();
		Map<String, EdgeUndirected> edges = graph.getUndirectedEdges();
		Map<String, EdgeDTO> edgeDTOs = new HashMap<>();
		Map<String, NodeDTO> nodeDTOs = new HashMap<>();
		for (Map.Entry<String, EdgeUndirected> entry : edges.entrySet()) {
            String edgeId = entry.getKey();
            EdgeUndirected edge = entry.getValue();
            EdgeDTO edgeDTO = new EdgeDTO(entry.getValue());
            edgeDTO.setUndirectedEdge(edge.getNodes().toArray(new String[0]));
            edgeDTOs.put(edgeId, edgeDTO);
		}
		for (Map.Entry<String, NodeUndirected> entry : nodes.entrySet()) {
			NodeUndirected node = entry.getValue();
			nodeDTOs.put(entry.getKey(), new NodeDTO(node));
		}
		return new GraphDTO(graph.getId(), graph.getType(), nodeDTOs, edgeDTOs);
	}

	public static PathTraversalDTO constructPath(Graph graph, Node start, Node end, Map<String, String> previousNodes) {
		switch (graph.getType()) {
			case DIRECTED:
				return constructPathForDirectedGraph(graph, (NodeDirected) start, (NodeDirected) end, previousNodes);
			case UNDIRECTED:
				return constructPathForUndirectedGraph(graph, (NodeUndirected) start, (NodeUndirected) end, previousNodes);
			default:
				return null;

		}
	}

	private static PathTraversalDTO constructPathForDirectedGraph(Graph graph, NodeDirected start, NodeDirected end, Map<String, String> previousNodes) {
		Map<String, NodeDirected> directedNodes = graph.getDirectedNodes();
        Map<String, EdgeDirected> directedEdges = graph.getDirectedEdges();
	}

	private static PathTraversalDTO constructPathForUndirectedGraph(Graph graph, NodeUndirected start, NodeUndirected end, Map<String, String> previousNodes) {
        Map<String, NodeUndirected> undirectedNodes = graph.getUndirectedNodes();
        Map<String, EdgeUndirected> undirectedEdges = graph.getUndirectedEdges();

    }

	public static boolean isRing(NodeUndirected node, EdgeUndirected edge) {
		return edge.getNodes().stream().filter(id -> id.equals(node.getId())).count() > 1;
	}

	public static boolean isRing(NodeDirected node, EdgeDirected edge) {
		return edge.getFromId().contains(node.getId())
			&& edge.getToId().contains(node.getId());
	}
}
