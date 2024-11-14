package org.myproject.project1.shared;

import lombok.Getter;

/**
 * @author nguyenle
 * @since 4:37 PM Tue 11/12/2024
 */
@Getter
public enum GraphType {

    DIRECTED(0),

    UNDIRECTED(1)
    ;

    private final int value;

    GraphType(final int value) {
        this.value = value;
    }
}
