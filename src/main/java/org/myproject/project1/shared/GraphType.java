package org.myproject.project1.shared;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

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

    private static Map<Integer, GraphType> mapId2GraphType = new HashMap<>();

    static {
        for (GraphType graphType : values()) {
            mapId2GraphType.put(graphType.value, graphType);
        }
    }

    GraphType(final int value) {
        this.value = value;
    }

    public static GraphType valueOf(int value) {
        return mapId2GraphType.get(value);
    }
}
