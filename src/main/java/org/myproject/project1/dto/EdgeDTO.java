package org.myproject.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.core.Edge;

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

    private String source;

    private String target;

    private double curvature;

    private double rotation;

    public EdgeDTO(Edge edge) {
        this.id = edge.getId();
        this.weight = edge.getWeight();
    }

    public void setDirectedEdge(String fromNodeId, String toNodeId) {
        this.source = fromNodeId;
        this.target = toNodeId;
    }

    public void setUndirectedEdge(String... nodeIds) {
        for (String nodeId : nodeIds) {
            addNodeForUndirectedGraph(nodeId);
        }
    }

    public void addNodeForUndirectedGraph(String nodeId) {
        if (source == null || source.isEmpty()) {
            source = nodeId;
            return;
        }
        if (target == null || target.isEmpty()) {
            target = nodeId;
            return;
        }
    }

    public boolean isRing() {
        return source.equals(target);
    }
}
