package org.myproject.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.core.Edge;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;

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

    private String label;

    private int weight;

    private String source;

    private String target;

    public EdgeDTO(Edge edge) {
        this.id = edge.getId();
        this.weight = edge.getWeight();
        if (edge instanceof EdgeDirected edgeDirected) {
            setDirectedEdge(edgeDirected.getSource(), edgeDirected.getTarget());
        } else if (edge instanceof EdgeUndirected edgeUndirected) {
            setUndirectedEdge(edgeUndirected.getNodes().toArray(new String[0]));
        }
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
}
