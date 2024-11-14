package org.myproject.project1.core.directed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import org.myproject.project1.core.Edge;
import org.myproject.project1.core.Node;

/**
 * @author nguyenle
 * @since 10:34 PM Tue 11/12/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NodeDirected extends Node {

    private Set<String> fromEdges = new HashSet<>();

    private Set<String> toEdges = new HashSet<>();

    public NodeDirected(String id) {
        super(id);
    }

    public void addFromEdge(String edgeId) {
        fromEdges.add(edgeId);
    }

    public void addFromEdge(Edge edge) {
        if (edge == null || edge.getId() == null || edge.getId().isEmpty()) {
            return;
        }
        fromEdges.add(edge.getId());
    }

    public void addToEdge(String edgeId) {
        toEdges.add(edgeId);
    }

    public void addToEdge(Edge edge) {
        if (edge == null || edge.getId() == null || edge.getId().isEmpty()) {
            return;
        }
        toEdges.add(edge.getId());
    }

}
