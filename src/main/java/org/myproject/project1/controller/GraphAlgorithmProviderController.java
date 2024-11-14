package org.myproject.project1.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.service.GraphAlgorithmService;
import org.myproject.project1.service.InMemoryGraphStoreService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nguyenle
 * @since 11:48 PM Tue 11/12/2024
 */
@RestController
@RequestMapping("/graph/algo")
@RequiredArgsConstructor
public class GraphAlgorithmProviderController {

    private final InMemoryGraphStoreService graphStoreService;

    private final GraphAlgorithmService graphAlgorithmService;


}
