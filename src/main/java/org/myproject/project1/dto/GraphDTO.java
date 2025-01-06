package org.myproject.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.core.Edge;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.Node;
import org.myproject.project1.shared.GraphType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nguyenle
 * @since 11:25 PM Tue 11/12/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraphDTO {

    private String id;

    private GraphType type;

    private List<NodeDTO> nodes = new ArrayList<>();

    private List<EdgeDTO> edges = new ArrayList<>();

    public GraphDTO(Graph graph) {
        id = graph.getId();
        type = graph.getType();
        Map<String, Node> nodesMap = graph.getNodes();
        Map<String, Edge> edgesMap = graph.getEdges();
        for (Node node : nodesMap.values()) {
            nodes.add(new NodeDTO(node));
        }
        for (Edge edge : edgesMap.values()) {
            edges.add(new EdgeDTO(edge));
        }
    }

}
