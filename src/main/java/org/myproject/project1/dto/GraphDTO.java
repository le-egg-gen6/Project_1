package org.myproject.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.shared.GraphType;
import org.myproject.project1.utils.ColorUtils;
import org.myproject.project1.utils.CurveUtils;
import org.myproject.project1.utils.RandomUtils;

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
        if (type == GraphType.DIRECTED) {
            initForDirectedGraph(graph);
        } else if (type == GraphType.UNDIRECTED) {
            initForUndirectedGraph(graph);
        }
        for (NodeDTO node : nodes) {
            node.setColor(ColorUtils.generateNodeColor());
            node.setRadius(RandomUtils.randomInRange(1, 10));
        }
        for (EdgeDTO edge : edges) {
            edge.setCurvature(CurveUtils.generateCurvatureValue(edge.isRing()));
            edge.setRotation(CurveUtils.generateRotationValue());
        }
    }

    private void initForDirectedGraph(Graph graph) {
        Map<String, NodeDirected> directedNodes = graph.getDirectedNodes();
        Map<String, EdgeDirected> directedEdges = graph.getDirectedEdges();

        for (NodeDirected node : directedNodes.values()) {
            nodes.add(new NodeDTO(node));
        }
        for (EdgeDirected edge : directedEdges.values()) {
            EdgeDTO edgeDTO = new EdgeDTO(edge);
            edgeDTO.setDirectedEdge(edge.getSource(), edge.getTarget());
            edges.add(edgeDTO);
        }
    }

    private void initForUndirectedGraph(Graph graph) {
        Map<String, NodeUndirected> undirectedNodes = graph.getUndirectedNodes();
        Map<String, EdgeUndirected> undirectedEdges = graph.getUndirectedEdges();

        for (NodeUndirected node : undirectedNodes.values()) {
            nodes.add(new NodeDTO(node));
        }
        for (EdgeUndirected edge : undirectedEdges.values()) {
            EdgeDTO edgeDTO = new EdgeDTO(edge);
            edgeDTO.setUndirectedEdge(edge.getNodes().toArray(new String[0]));
            edges.add(edgeDTO);
        }
    }

}
