package org.myproject.project1.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author nguyenle
 * @since 4:11 PM Tue 11/12/2024
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Node {

    private String id;

    private String label;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node node) {
            return id.equals(node.id);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append("*");
        return sb.toString();
    }
}
