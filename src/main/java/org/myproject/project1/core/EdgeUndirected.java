package org.myproject.project1.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

/**
 * @author nguyenle
 * @since 2:17 AM Thu 11/14/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EdgeUndirected extends Edge {

    private List<String> nodes = new ArrayList<>();

    public EdgeUndirected(String id, int weight) {
        super(id, weight);
    }

    public EdgeUndirected(String id, List<String> nodes) {
        super(id);
        this.nodes = nodes;
    }

    public EdgeUndirected(String id, int weight, List<String> nodes) {
        super(id, weight);
        this.nodes = nodes;
    }

    public EdgeUndirected(String id, String... nodes) {
        super(id);
        this.nodes = new ArrayList<>(Arrays.asList(nodes));
    }

    public EdgeUndirected(String id, int weight, String... nodes) {
        super(id, weight);
        this.nodes = new ArrayList<>(Arrays.asList(nodes));
    }

}
