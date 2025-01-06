package org.myproject.project1.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author nguyenle
 * @since 4:13 PM Tue 11/12/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Edge {

    private String id;

    private String label;

    private int weight = 1;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge edge) {
            return id.equals(edge.id);
        }
        return false;
    }

    public Edge(String id) {
        this.id = id;
    }
}
