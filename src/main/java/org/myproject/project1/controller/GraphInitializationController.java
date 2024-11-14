package org.myproject.project1.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.service.EdgeProviderFactory;
import org.myproject.project1.service.InMemoryGraphStoreService;
import org.myproject.project1.service.NodeProviderFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nguyenle
 * @since 11:22 PM Tue 11/12/2024
 */
@RestController
@RequestMapping("/graph/init")
@RequiredArgsConstructor
public class GraphInitializationController {

    private final InMemoryGraphStoreService graphStoreService;

    private final EdgeProviderFactory edgeProviderFactory;

    private final NodeProviderFactory nodeProviderFactory;


}
