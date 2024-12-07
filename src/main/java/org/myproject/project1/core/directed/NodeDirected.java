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

    private Set<String> sourceEdges = new HashSet<>();

    private Set<String> targetEdges = new HashSet<>();

    public NodeDirected(String id) {
        super(id);
    }

    public void addSourceEdge(String edgeId) {
        sourceEdges.add(edgeId);
    }

    public void addSourceEdge(Edge edge) {
        if (edge == null || edge.getId() == null || edge.getId().isEmpty()) {
            return;
        }
        sourceEdges.add(edge.getId());
    }

    public void addTargetEdge(String edgeId) {
        targetEdges.add(edgeId);
    }

    public void addTargetEdge(Edge edge) {
        if (edge == null || edge.getId() == null || edge.getId().isEmpty()) {
            return;
        }
        targetEdges.add(edge.getId());
    }

}
