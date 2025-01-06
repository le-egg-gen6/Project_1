package org.myproject.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.core.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nguyenle
 * @since 4:32 PM Tue 11/12/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PathTraversalDTO {

    private String graphId;

    private boolean timeLimitExceeded;

    private boolean hasPath;

    private int totalWeight;

    private List<NodeDTO> nodes = new ArrayList<>();

    private List<EdgeDTO> edges = new ArrayList<>();

    public static PathTraversalDTO notFound(Graph graph) {
        PathTraversalDTO dto = new PathTraversalDTO();
        dto.graphId = graph.getId();
        dto.hasPath = false;
        dto.timeLimitExceeded = false;
        dto.totalWeight = -1;
        return dto;
    }

    public static PathTraversalDTO timeLimitExceeded(Graph graph) {
        PathTraversalDTO dto = new PathTraversalDTO();
        dto.graphId = graph.getId();
        dto.hasPath = false;
        dto.timeLimitExceeded = true;
        dto.totalWeight = -1;
        return dto;
    }

    public static PathTraversalDTO foundPath(Graph graph) {
        PathTraversalDTO dto = new PathTraversalDTO();
        dto.graphId = graph.getId();
        dto.hasPath = true;
        dto.timeLimitExceeded = false;
        return dto;
    }

}
