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

    DIRECTED("directed"),

    UNDIRECTED("undirected")
    ;

    private final String name;

    private static Map<String, GraphType> mapId2GraphType = new HashMap<>();

    static {
        for (GraphType graphType : values()) {
            mapId2GraphType.put(graphType.getName(), graphType);
        }
    }

    GraphType(String name) {
        this.name = name;
    }

    public static GraphType fromValue(String value) {
        return mapId2GraphType.get(value);
    }
}
