package org.myproject.project1.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * @author nguyenle
 * @since 2:16 AM Thu 11/14/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EdgeDirected extends Edge {

    private String fromId;

    private String toId;

    public EdgeDirected(String id, int weight) {
        super(id, weight);
    }

    public EdgeDirected(String id, int weight, String fromId, String toId) {
        super(id, weight);
        this.fromId = fromId;
        this.toId = toId;
    }

    public EdgeDirected(String id) {
        super(id);
    }

    public EdgeDirected(String id, String fromId, String toId) {
        super(id);
        this.fromId = fromId;
        this.toId = toId;
    }

}
