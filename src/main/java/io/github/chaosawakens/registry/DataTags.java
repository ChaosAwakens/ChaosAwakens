package io.github.chaosawakens.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public final class DataTags {
    public static final Tag<Block> BASE_STONE_CRYSTAL;
    public static final Tag<Block> CORNS;
    public static final Tag<Block> MINERS_DREAM_MINEABLE;
    public static final Tag<Block> MINERS_DREAM_UNSAFE;
    public static final Tag<Block> MINERS_DREAM_TORCH_SAFE;
    public static final Tag<Block> AIR_BLOCKS;

    private static Tag<Block> register(String id) {
        return TagRegistry.block(new Identifier(ChaosAwakens.modID, id));
    }

    static {
        BASE_STONE_CRYSTAL = register("base_stone_crystal");
        CORNS = register("corn");
        MINERS_DREAM_MINEABLE = register("miners_dream_mineable");
        MINERS_DREAM_UNSAFE = register("miners_dream_unsafe");
        MINERS_DREAM_TORCH_SAFE = register("miners_dream_torch_sage");
        AIR_BLOCKS = register("air");
    }
}
