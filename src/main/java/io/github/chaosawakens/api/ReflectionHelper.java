package io.github.chaosawakens.api;

import io.github.chaosawakens.ChaosAwakens;

public class ReflectionHelper {
    public static void classLoad(String classNamePath) {
        try {
            Class.forName(classNamePath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            ChaosAwakens.error("CLASSLOAD", "Failed to load a class. This error probably happened due to file corruption, so please try downloading the mod again.");
        }
    }
}
