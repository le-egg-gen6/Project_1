package org.myproject.project1.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.dto.GraphDTO;
import org.myproject.project1.service.GraphService;
import org.myproject.project1.shared.GraphType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nguyenle
 * @since 1:19 AM Sun 12/8/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/graph")
public class GraphController {

    private final GraphService graphService;

    @GetMapping("/generate-random")
    public ResponseEntity<?> generateRandomGraph(
            @RequestParam String type
    ) {
        GraphType graphType = GraphType.fromValue(type);
        GraphDTO graphDTO = new GraphDTO(graphService.initNewRandomGraph(graphType));
        return ResponseEntity.ok(graphDTO);
    }

}
