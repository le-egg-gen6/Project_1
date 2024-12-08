package org.myproject.project1.shared;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nguyenle
 * @since 10:39 AM Thu 11/14/2024
 */
@Getter
public enum AlgorithmConstant {

	IS_CONNECTIVITY(0, "is_connectivity"),

	SHORTEST_PATH(1, "shortest_path"),

	HAMILTON_CYCLE(2, "hamilton_cycle")
	;

	private final int value;
	private final String cacheKey;

	private static Map<Integer, AlgorithmConstant> mapId2AlgorithmConstant = new HashMap<>();

	static {
		for (AlgorithmConstant algorithmConstant : values()) {
			mapId2AlgorithmConstant.put(algorithmConstant.getValue(), algorithmConstant);
		}
	}

	AlgorithmConstant(int value, String cacheKey) {
		this.value = value;
		this.cacheKey = cacheKey;
	}

	public static AlgorithmConstant valueOf(int value) {
		return mapId2AlgorithmConstant.get(value);
	}

}
