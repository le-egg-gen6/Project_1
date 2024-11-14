package org.myproject.project1.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.Node;
import org.myproject.project1.dto.PathTraversalDTO;
import org.myproject.project1.service.GraphAlgorithmService;
import org.myproject.project1.service.InMemoryGraphStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nguyenle
 * @since 11:48 PM Tue 11/12/2024
 */
@RestController
@RequestMapping("/algo")
@RequiredArgsConstructor
public class GraphAlgorithmProviderController {

    private final GraphAlgorithmService graphAlgorithmService;

    private final InMemoryGraphStoreService inMemoryGraphStoreService;

    @GetMapping("/shortest-path")
    public ResponseEntity<PathTraversalDTO> getShortestPath(
        @RequestParam("graphId") String graphId,
        @RequestParam("nodeStartId") String nodeStartId,
        @RequestParam("nodeEndId") String nodeEndId
    ) {
        Graph graph = inMemoryGraphStoreService.getGraph(graphId);
        Node nodeStart = graph.getNode(nodeStartId);
        Node nodeEnd = graph.getNode(nodeEndId);
        PathTraversalDTO result = graphAlgorithmService.getShortestTraversalPath(graph, nodeStart, nodeEnd);
        return ResponseEntity.ok(result);
    }

}
