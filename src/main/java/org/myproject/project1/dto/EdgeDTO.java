package org.myproject.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.core.Edge;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.NodeUndirected;

/**
 * @author nguyenle
 * @since 11:30 PM Tue 11/12/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EdgeDTO {

    private String id;

    private int weight;

    private String sourceId;

    private String targetId;

    private double curvature;

    private double rotation;

    public EdgeDTO(Edge edge) {
        this.id = edge.getId();
        this.weight = edge.getWeight();
    }

    public void setDirectedEdge(NodeDirected fromNode, NodeDirected toNode) {
        this.sourceId = fromNode.getId();
        this.targetId = toNode.getId();
    }

    public void setDirectedEdge(String fromNodeId, String toNodeId) {
        this.sourceId = fromNodeId;
        this.targetId = toNodeId;
    }

    public void setUndirectedEdge(NodeUndirected nodeA, NodeUndirected nodeB) {
        this.sourceId = nodeA.getId();
        this.targetId = nodeB.getId();
    }

    public void setUndirectedEdge(String... nodeIds) {
        for (String nodeId : nodeIds) {
            addNodeForUndirectedGraph(nodeId);
        }
    }

    public void addNodeForUndirectedGraph(String nodeId) {
        if (sourceId == null || sourceId.isEmpty()) {
            sourceId = nodeId;
            return;
        }
        if (targetId == null || targetId.isEmpty()) {
            targetId = nodeId;
            return;
        }
    }

    public boolean isRing() {
        return sourceId.equals(targetId);
    }
}
