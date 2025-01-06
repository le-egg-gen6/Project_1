package org.myproject.project1.utils;

import lombok.experimental.UtilityClass;

/**
 * @author nguyenle
 * @since 3:29 PM Mon 1/6/2025
 */
@UtilityClass
public class LabelUtils {

	public static String getEdgeLabel(int currentCnt) {
		return "Edge " + currentCnt;
	}

	public static String getNodeLabel(int currentCnt) {
		return "Node " + currentCnt;
	}

}
