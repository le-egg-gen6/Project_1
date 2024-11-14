package org.myproject.project1.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author nguyenle
 * @since 10:35 PM Tue 11/12/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NodeUndirected extends Node {

    private Set<String> edges = new HashSet<>();

    public NodeUndirected(String id) {
        super(id);
    }

    public void addEdge(String edgeId) {
        edges.add(edgeId);
    }

    public void addEdge(Edge edge) {
        if (edge == null || edge.getId() == null || edge.getId().isEmpty()) {
            return;
        }
        edges.add(edge.getId());
    }

}
