package org.myproject.project1.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.service.AlgorithmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nguyenle
 * @since 1:20 AM Sun 12/8/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/algorithm")
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    @GetMapping("/shortest-path")
    public ResponseEntity<?> getShortestPath(
            @RequestParam String graphId,
            @RequestParam String startNodeId,
            @RequestParam String endNodeId
    ) {
        return ResponseEntity.ok(algorithmService.getShortestPath(graphId, startNodeId, endNodeId));
    }

    @GetMapping("/hamilton-cycle")
    public ResponseEntity<?> getHamiltonCycle(
            @RequestParam String graphId,
            @RequestParam String startNodeId
    ) {
        return ResponseEntity.ok(algorithmService.getHamiltonCycle(graphId, startNodeId));
    }

}
