package io.github.chaosawakens.client.entity.render.util;

import java.util.Arrays;
import java.util.Comparator;

public enum EntityTextureEnum {
    DEFAULT(0), HALLOWEEN(1), CHRISTMAS(2);

    private static final EntityTextureEnum[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(EntityTextureEnum::getId)).toArray(EntityTextureEnum[]::new);

    private final int id;

    EntityTextureEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EntityTextureEnum byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            id = 0;
        }
        return VALUES[id];
    }
}