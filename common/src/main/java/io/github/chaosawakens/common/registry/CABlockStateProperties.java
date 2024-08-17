package io.github.chaosawakens.common.registry;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

public final class CABlockStateProperties {
    // Boolean
    public static final BooleanProperty RIPE = BooleanProperty.create("ripe");

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty VANISHED = BooleanProperty.create("vanished");
}
