package org.myproject.project1.controller;

import java.util.ArrayList;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.Edge;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.Node;
import org.myproject.project1.dto.EdgeDTO;
import org.myproject.project1.dto.GraphDTO;
import org.myproject.project1.dto.NodeDTO;
import org.myproject.project1.exception.custom.BadParameterException;
import org.myproject.project1.service.GraphService;
import org.myproject.project1.shared.GraphType;
import org.myproject.project1.utils.JsonUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author nguyenle
 * @since 1:19 AM Sun 12/8/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/graph")
public class GraphController {

    private final GraphService graphService;

    @GetMapping("/generate")
    public ResponseEntity<?> generateGraph(
            @RequestParam String type,
            @RequestParam String jsonGraphArray
    ) {
        GraphType graphType = GraphType.fromValue(type);
        if (graphType == null) {
            throw new BadParameterException("Invalid graph type");
        }
        List graphArray = JsonUtils.fromJson(jsonGraphArray, List.class);
        List<List<Integer>> graphArrayInteger = new ArrayList<>();
        for (int i = 0; i < graphArray.size(); i++) {
            List<Integer> graphArrayIntegerIndexI = new ArrayList<>();
            if (!(graphArray.get(i) instanceof List<?>)) {
                throw new BadParameterException("Invalid graph array");
            }
            List graphArrayIndexI = (List) graphArray.get(i);
	        for (Object o : graphArrayIndexI) {
		        if (!(o instanceof Integer)) {
			        throw new BadParameterException("Invalid graph array");
		        }
		        graphArrayIntegerIndexI.add((Integer) o);
	        }
            graphArrayInteger.add(graphArrayIntegerIndexI);
        }
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
                }
            }
        }
        GraphDTO graphDTO = new GraphDTO(graphService.initNewGraph(graphType, graphArrayInteger));
        return ResponseEntity.ok(graphDTO);
    }

    @GetMapping("/generate-random")
    public ResponseEntity<?> generateRandomGraph(
            @RequestParam String type
    ) {
        GraphType graphType = GraphType.fromValue(type);
        if (graphType == null) {
            throw new BadParameterException("Invalid graph type");
        }
        GraphDTO graphDTO = new GraphDTO(graphService.initNewRandomGraph(graphType));
        return ResponseEntity.ok(graphDTO);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllGraphs(
            @RequestParam String type
    ) {
        GraphType graphType = GraphType.fromValue(type);
        if (graphType == null) {
            throw new BadParameterException("Invalid graph type");
        }
        List<GraphDTO> listGraphs = graphService.getAllGraph(graphType).stream().map(GraphDTO::new).toList();
        return ResponseEntity.ok(listGraphs);
    }

    @PostMapping("/add-node")
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

    @PostMapping("/add-edge")
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
