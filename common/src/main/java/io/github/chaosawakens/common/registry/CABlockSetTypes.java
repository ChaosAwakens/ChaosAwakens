package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public final class CABlockSetTypes {
    private static final ObjectArrayList<BlockSetType> BLOCK_SET_TYPES = new ObjectArrayList<>();

    public static final BlockSetType APPLE = registerBlockSetType(new BlockSetType("apple"));
    public static final BlockSetType CRYSTALWOOD = registerBlockSetType(new BlockSetType("crystalwood"));
    public static final BlockSetType DENSEWOOD = registerBlockSetType(new BlockSetType("densewood"));
    public static final BlockSetType DUPLICATOR = registerBlockSetType(new BlockSetType("duplicator"));
    public static final BlockSetType GINKGO = registerBlockSetType(new BlockSetType("ginkgo"));
    public static final BlockSetType MESOZOIC = registerBlockSetType(new BlockSetType("mesozoic"));
    public static final BlockSetType SKYWOOD = registerBlockSetType(new BlockSetType("skywood"));

    private static BlockSetType registerBlockSetType(BlockSetType blockSetType) {
        BLOCK_SET_TYPES.add(blockSetType);
        return blockSetType;
    }

    public static ImmutableList<BlockSetType> getBlockSetTypes() {
        return ImmutableList.copyOf(BLOCK_SET_TYPES);
    }
}
