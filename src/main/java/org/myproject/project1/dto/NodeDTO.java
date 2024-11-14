package org.myproject.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.core.Node;

/**
 * @author nguyenle
 * @since 11:29 PM Tue 11/12/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NodeDTO {

    private String id;

    public NodeDTO(Node node) {
        this.id = node.getId();
    }

}
