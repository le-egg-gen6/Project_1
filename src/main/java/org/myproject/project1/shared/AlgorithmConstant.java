package org.myproject.project1.shared;

import lombok.Getter;

/**
 * @author nguyenle
 * @since 10:39 AM Thu 11/14/2024
 */
@Getter
public enum AlgorithmConstant {

	SHORTEST_PATH(0, "shortest_path"),

	HAMILTON_CYCLE(1, "hamilton_cycle")
	;

	private final int value;
	private final String name;

	AlgorithmConstant(final int value, final String name) {
		this.value = value;
		this.name = name;
	}

}
