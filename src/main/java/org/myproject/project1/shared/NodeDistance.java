package org.myproject.project1.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myproject.project1.core.Node;

/**
 * @author nguyenle
 * @since 2:03 AM Thu 11/14/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NodeDistance {

    private Node node;

    private int distance;

}
