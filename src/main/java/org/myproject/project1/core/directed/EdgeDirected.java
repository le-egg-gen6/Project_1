package org.myproject.project1.core.directed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.myproject.project1.core.Edge;

/**
 * @author nguyenle
 * @since 2:16 AM Thu 11/14/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EdgeDirected extends Edge {

    private String source;

    private String target;

    public EdgeDirected(String id, int weight) {
        super(id, weight);
    }

    public EdgeDirected(String id, int weight, String source, String target) {
        super(id, weight);
        this.source = source;
        this.target = target;
    }

    public EdgeDirected(String id) {
        super(id);
    }

    public EdgeDirected(String id, String source, String target) {
        super(id);
        this.source = source;
        this.target = target;
    }

}
