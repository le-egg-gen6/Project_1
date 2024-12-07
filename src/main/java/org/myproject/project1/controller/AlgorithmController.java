package org.myproject.project1.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.service.AlgorithmService;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
