package io.github.chaosawakens.common.block.dungeon.general;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class DungeonGateBlock extends Block {
    protected static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public DungeonGateBlock(Properties properties) {
        super(properties);
    }


}
