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

import java.util.HashMap;
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

    private Map<String, NodeDTO> nodes = new HashMap<>();

    private Map<String, EdgeDTO> edges =  new HashMap<>();

    public GraphDTO(Graph graph) {
        id = graph.getId();
        type = graph.getType();
        switch (type) {
            case DIRECTED:
                initForDirectedGraph(graph);
                break;
            case UNDIRECTED:
                initForUndirectedGraph(graph);
                break;
            default:
                break;
        }
    }

    private void initForDirectedGraph(Graph graph) {
        Map<String, NodeDirected> directedNodes = graph.getDirectedNodes();
        Map<String, EdgeDirected> directedEdges = graph.getDirectedEdges();

        for (Map.Entry<String, NodeDirected> entry : directedNodes.entrySet()) {
            String nodeId = entry.getKey();
            NodeDirected node = entry.getValue();
            nodes.put(nodeId, new NodeDTO(node));
        }
        for (Map.Entry<String, EdgeDirected> entry : directedEdges.entrySet()) {
            String edgeId = entry.getKey();
            EdgeDirected edge = entry.getValue();
            EdgeDTO edgeDTO = new EdgeDTO(edge);
            edgeDTO.setDirectedEdge(edge.getFromId(), edge.getToId());
            edges.put(edgeId, edgeDTO);
        }
    }

    private void initForUndirectedGraph(Graph graph) {
        Map<String, NodeUndirected> undirectedNodes = graph.getUndirectedNodes();
        Map<String, EdgeUndirected> undirectedEdges = graph.getUndirectedEdges();

        for (Map.Entry<String, NodeUndirected> entry : undirectedNodes.entrySet()) {
            String nodeId = entry.getKey();
            NodeUndirected node = entry.getValue();
            nodes.put(nodeId, new NodeDTO(node));
        }
        for (Map.Entry<String, EdgeUndirected> entry : undirectedEdges.entrySet()) {
            String edgeId = entry.getKey();
            EdgeUndirected edge = entry.getValue();
            EdgeDTO edgeDTO = new EdgeDTO(edge);
            edgeDTO.setUndirectedEdge(edge.getNodes().toArray(new String[0]));
            edges.put(edgeId, edgeDTO);
        }
    }

}
