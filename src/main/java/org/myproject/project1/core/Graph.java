package org.myproject.project1.core;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.shared.GraphType;
import org.myproject.project1.utils.LabelUtils;
import org.myproject.project1.utils.UUIDUtils;

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

	private String label;

    private String uniqueHash;

	private GraphType type;

	private int nodeCount = 1;

	private int edgeCount = 1;

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
		edge.setLabel(LabelUtils.getEdgeLabel(edgeCount));
		++edgeCount;
		edges.put(edge.getId(), edge);
	}

	public void addNewNode(Node node) {
		if (node == null || node.getId() == null || node.getId().isEmpty()) {
			return;
		}
		node.setLabel(LabelUtils.getNodeLabel(nodeCount));
		++nodeCount;
		nodes.put(node.getId(), node);
	}

}
