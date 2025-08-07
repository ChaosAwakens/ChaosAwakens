package io.github.chaosawakens.api.animation.geckolib.object.animation;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum KeyframeType implements StringRepresentable {
    POSITION,
    ROTATION,
    SCALE;

    public static final Codec<KeyframeType> CODEC = StringRepresentable.fromEnum(KeyframeType::values);

    KeyframeType() {

    }

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase();
    }
}
