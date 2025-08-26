package io.github.chaosawakens.api.animation.geckolib.molang;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public class MolangVariableAndQueryMapper { //TODO Handle remapping + add all var support + query support
    public static final MolangVariableAndQueryMapper INSTANCE = new MolangVariableAndQueryMapper();
    private static final Map<String, String> VARIABLE_MAP = new Object2ObjectOpenHashMap<>();
    private static final Map<String, MolangQueryHolder> QUERY_MAP = new Object2ObjectOpenHashMap<>();

    private MolangVariableAndQueryMapper() {

    }

    protected void handleVariableRemaps() {

    }

    static {

    }
}
