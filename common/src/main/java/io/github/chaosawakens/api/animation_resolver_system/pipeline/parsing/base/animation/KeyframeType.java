package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum KeyframeType implements StringRepresentable {
    POSITION,
    ROTATION,
    SCALE;

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase();
    }

    public static KeyframeType byName(String name) {
        return valueOf(name.toUpperCase());
    }
}
