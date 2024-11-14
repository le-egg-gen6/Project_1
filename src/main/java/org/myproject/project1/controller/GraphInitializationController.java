package org.myproject.project1.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.Graph;
import org.myproject.project1.dto.GraphDTO;
import org.myproject.project1.service.GraphInitializerService;
import org.myproject.project1.service.InMemoryGraphStoreService;
import org.myproject.project1.shared.GraphType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nguyenle
 * @since 11:22 PM Tue 11/12/2024
 */
@RestController
@RequestMapping("/graph")
@RequiredArgsConstructor
public class GraphInitializationController {

    private final GraphInitializerService graphInitializerService;

    @GetMapping("/init")
    public ResponseEntity<GraphDTO> initGraph(
            @RequestParam(name = "type") int type
    ) {
        GraphType graphType = GraphType.valueOf(type);
        Graph newGraph = graphInitializerService.initNewGraph(graphType);
        return ResponseEntity.ok(new GraphDTO(newGraph));
    }

}
