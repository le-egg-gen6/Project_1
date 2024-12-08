package org.myproject.project1.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.dto.GraphDTO;
import org.myproject.project1.exception.custom.BadParameterException;
import org.myproject.project1.service.GraphService;
import org.myproject.project1.shared.GraphType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
            @RequestParam String type
    ) {
        GraphType graphType = GraphType.fromValue(type);
        if (graphType == null) {
            throw new BadParameterException("Invalid graph type");
        }
        GraphDTO graphDTO = new GraphDTO(graphService.initNewGraph(graphType));
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

//    @PostMapping("/add-node")
//    public ResponseEntity<?> addNewNode() {
//
//    }
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
