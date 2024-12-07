package org.myproject.project1.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.shared.GraphType;
import org.myproject.project1.utils.UUIDUtils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author nguyenle
 * @since 4:14 PM Tue 11/12/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Graph {

	private String id;

    private String uniqueHash;

	private GraphType type;

	private Map<String, Node> nodes = new HashMap<>();

	private Map<String, Edge> edges = new HashMap<>();

	public Graph(GraphType type) {
		id = UUIDUtils.generateUUID();
		uniqueHash = UUIDUtils.generateUUID();
		this.type = type;
	}

	public Edge getEdge(String edgeId) {
		return edges.get(edgeId);
	}

	public Node getNode(String nodeId) {
		return nodes.get(nodeId);
	}

	public int getVertexCount() {
		return nodes.size();
	}

	public int getEdgeCount() {
		return edges.size();
	}

	public void addNewEdge(Edge edge) {
		if (edge == null || edge.getId() == null || edge.getId().isEmpty()) {
			return;
		}
		edges.put(edge.getId(), edge);
	}

	public void addNewNode(Node node) {
		if (node == null || node.getId() == null || node.getId().isEmpty()) {
			return;
		}
		nodes.put(node.getId(), node);
	}

	public Map<String, NodeDirected> getDirectedNodes() {
		if (type == GraphType.DIRECTED) {
			return nodes.entrySet().stream()
				.map(entry ->
					new AbstractMap.SimpleEntry<>(entry.getKey(), (NodeDirected) entry.getValue())
				)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}
		return null;
	}

	public Map<String, NodeUndirected> getUndirectedNodes() {
		if (type == GraphType.UNDIRECTED) {
			return nodes.entrySet().stream()
				.map(entry ->
					new AbstractMap.SimpleEntry<>(entry.getKey(), (NodeUndirected) entry.getValue())
				)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}
		return null;
	}

	public Map<String, EdgeDirected> getDirectedEdges() {
		if (type == GraphType.DIRECTED) {
			return edges.entrySet().stream()
				.map(entry ->
					new AbstractMap.SimpleEntry<>(entry.getKey(), (EdgeDirected) entry.getValue())
				).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}
		return null;
	}

	public Map<String, EdgeUndirected> getUndirectedEdges() {
		if (type == GraphType.UNDIRECTED) {
			return edges.entrySet().stream()
				.map(entry ->
					new AbstractMap.SimpleEntry<>(entry.getKey(), (EdgeUndirected) entry.getValue()) {
					}
				)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}
		return null;
	}

}
