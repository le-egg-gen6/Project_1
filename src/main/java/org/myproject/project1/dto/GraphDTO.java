package org.myproject.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.shared.GraphType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nguyenle
 * @since 11:25 PM Tue 11/12/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraphDTO {

    private String id;

    private GraphType type;

    private Map<String, NodeDTO> nodes = new HashMap<>();

    private Map<String, EdgeDTO> edges =  new HashMap<>();

}
