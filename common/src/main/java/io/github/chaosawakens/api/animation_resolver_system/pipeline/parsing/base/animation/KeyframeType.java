package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum KeyframeType implements StringRepresentable {
    POSITION,
    ROTATION,
    SCALE;

    public static KeyframeType byName(String name) {
        return valueOf(name.toUpperCase());
    }

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase();
    }
}
