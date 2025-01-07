package org.myproject.project1.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.Edge;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.Node;
import org.myproject.project1.dto.CreateGraph;
import org.myproject.project1.dto.EdgeDTO;
import org.myproject.project1.dto.GraphDTO;
import org.myproject.project1.dto.NodeDTO;
import org.myproject.project1.exception.custom.BadParameterException;
import org.myproject.project1.service.GraphService;
import org.myproject.project1.shared.GraphType;
import org.myproject.project1.utils.JsonUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author nguyenle
 * @since 1:19 AM Sun 12/8/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/graph")
public class GraphController {

    private final GraphService graphService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateGraph(
            @RequestParam String name,
            @RequestParam String type,
            @RequestBody CreateGraph createGraph
    ) throws Exception {
        GraphType graphType = GraphType.fromValue(type);
        if (graphType == null) {
            throw new BadParameterException("Invalid graph type");
        }
        List<List<Integer>> graphArrayInteger = JsonUtils.convertJsonToList(createGraph.getJsonGraphArray());
        for (int i = 0; i < graphArrayInteger.size(); i++) {
            if (graphArrayInteger.size() != graphArrayInteger.get(i).size()) {
                throw new BadParameterException("Invalid graph array");
            }
        }
        if (graphType == GraphType.UNDIRECTED) {
            for (int i = 0; i < graphArrayInteger.size(); i++) {
                for (int j = 0; j < graphArrayInteger.get(i).size(); j++) {
                    if (!Objects.equals(graphArrayInteger.get(i).get(j), graphArrayInteger.get(j).get(i))) {
                        throw new BadParameterException("Invalid graph array");
                    }
                    if (i == j && graphArrayInteger.get(i).get(j) != 0) {
                        throw new BadParameterException("Invalid graph array");
                    }
                }
            }
        }
        Graph graph = graphService.initNewGraph(graphType, graphArrayInteger);
        graph.setLabel(name);
        GraphDTO graphDTO = new GraphDTO(graph);
        return ResponseEntity.ok(graphDTO);
    }

    @GetMapping("/generate-random")
    public ResponseEntity<?> generateRandomGraph(
            @RequestParam String name,
            @RequestParam String type
    ) {
        GraphType graphType = GraphType.fromValue(type);
        if (graphType == null) {
            throw new BadParameterException("Invalid graph type");
        }
        Graph graph = graphService.initNewRandomGraph(graphType);
        graph.setLabel(name);
        GraphDTO graphDTO = new GraphDTO(graph);
        return ResponseEntity.ok(graphDTO);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllGraphs() {
        List<GraphDTO> listGraphs = graphService.getAllGraph().stream().map(GraphDTO::new).toList();
        return ResponseEntity.ok(listGraphs);
    }

    @GetMapping("/node-info")
    public ResponseEntity<?> getNodeInfo(
            @RequestParam String graphId,
            @RequestParam String nodeId
    ) {
        Graph graph = graphService.getGraph(graphId);
        if (graph == null) {
            throw new BadParameterException("Invalid graph id");
        }
        return ResponseEntity.ok(new NodeDTO(graph.getNode(nodeId)));
    }

    @GetMapping("/edge-info")
    public ResponseEntity<?> getEdgeInfo(
            @RequestParam String graphId,
            @RequestParam String edgeId
    ) {
        Graph graph = graphService.getGraph(graphId);
        if (graph == null) {
            throw new BadParameterException("Invalid graph id");
        }
        return ResponseEntity.ok(new EdgeDTO(graph.getEdge(edgeId)));
    }

    @GetMapping("/add-node")
    public ResponseEntity<?> addNewNode(
            @RequestParam String graphId
    ) {
        Graph graph = graphService.getGraph(graphId);
        if (graph == null) {
            throw new BadParameterException("Graph not found");
        }
        Node node = graphService.addNewNode(graph);
        if (node == null) {
            throw new BadParameterException("Graph type invalid");
        }
        return ResponseEntity.ok(new NodeDTO(node));
    }

    @GetMapping("/add-edge")
    public ResponseEntity<?> addNewEdge(
            @RequestParam String graphId,
            @RequestParam String sourceId,
            @RequestParam String targetId,
            @RequestParam Integer weight
    ) {
        Graph graph = graphService.getGraph(graphId);
        if (graph == null) {
            throw new BadParameterException("Graph not found");
        }
        Node source = graph.getNode(sourceId);
        Node target = graph.getNode(targetId);
        Edge edge = graphService.addNewEdge(graph, source, target, weight);
        if (edge == null) {
            throw new BadParameterException("Error occurred, please reload page and try again");
        }
        return ResponseEntity.ok(new EdgeDTO(edge));
    }


//
//    @PostMapping("/delete-node")
//    public ResponseEntity<?> deleteNode() {
//
//    }
//
//    @PostMapping("/add-edge")
//    public ResponseEntity<?> addNewEdge() {
//
//    }
//
//    @PostMapping("/delete-edge")
//    public ResponseEntity<?> deleteEdge() {
//
//    }
//
//    @PostMapping("/update-edge")
//    public ResponseEntity<?> updateEdge() {
//
//    }

}
