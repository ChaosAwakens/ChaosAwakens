package io.github.chaosawakens.integrations.waila;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public enum RedAntOre implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public BlockState getOverride(IBlockAccessor accessor, IPluginConfig config) {
        return Blocks.DIAMOND_ORE.getDefaultState();
    }
}
