package io.github.chaosawakens.api;

import io.github.chaosawakens.ChaosAwakens;

/**
 * @author invalid2
 */
public class CAReflectionHelper {
	public static void classLoad(String classNamePath) {
		try {
			Class.forName(classNamePath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			ChaosAwakens.LOGGER.error("[CLASSLOAD]: Failed to load a class (" + classNamePath + "). This error probably happened due to file corruption, so please try downloading the mod again.");
		}
	}
}
