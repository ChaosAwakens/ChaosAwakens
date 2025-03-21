package io.github.chaosawakens.common.entity.prototype.base;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Panickable {
    UUID PANIC_SPEED_MOD_UUID = UUID.fromString("c4c3a9f9-6d8f-11e7-abc4-cec278b6b50a");

    boolean isPanicking();

    void setPanicking(boolean panicking);

    @Nullable
    AttributeModifier getPanicSpeedModifier();
}
