package org.myproject.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.core.Graph;
import org.myproject.project1.shared.GraphType;

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

    private boolean hasPath;

    private int totalWeight;

    private GraphType type;

    private NodeDTO start;

    private NodeDTO end;

    private List<EdgeDTO> paths = new ArrayList<>();

    public static PathTraversalDTO notFound(Graph graph) {
        PathTraversalDTO dto = new PathTraversalDTO();
        dto.hasPath = false;
        dto.totalWeight = -1;
        dto.type = graph.getType();
        return dto;
    }

}
