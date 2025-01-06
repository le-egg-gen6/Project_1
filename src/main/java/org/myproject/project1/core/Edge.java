package org.myproject.project1.core;

import lombok.*;

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

    public Edge(String id, int weight) {
        this.id = id;
        this.weight = weight;
    }

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
