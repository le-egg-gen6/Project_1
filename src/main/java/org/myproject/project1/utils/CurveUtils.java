package org.myproject.project1.utils;

import lombok.experimental.UtilityClass;

/**
 * @author nguyenle
 * @since 5:37 PM Tue 12/3/2024
 */
@UtilityClass
public class CurveUtils {

	public static double generateCurvatureValue(boolean isRing) {
		double minVal = 0;
		double maxVal = 1;
		if (isRing) {
			minVal = 0.5f;
		}
		return RandomUtils.randomInRange(minVal, maxVal);
	}

	public static double generateRotationValue() {
		return RandomUtils.randomInRange(0, Math.PI);
	}

}
