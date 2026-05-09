package io.github.chaosawakens.content.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public final class CASoundTypes {

    // Crystal Blocks
    public static final SoundType CRYSTALWOOD = new SoundType(1.0F, 1.0F, CASoundEvents.CRYSTALWOOD_BREAK.get(), CASoundEvents.CRYSTALWOOD_STEP.get(), SoundEvents.AMETHYST_BLOCK_PLACE, SoundEvents.AMETHYST_BLOCK_HIT, SoundEvents.AMETHYST_BLOCK_FALL);
    public static final SoundType KYANITE = new SoundType(1.0F, 1.0F, CASoundEvents.KYANITE_BREAK.get(), CASoundEvents.KYANITE_STEP.get(), SoundEvents.AMETHYST_BLOCK_PLACE, SoundEvents.AMETHYST_BLOCK_HIT, SoundEvents.AMETHYST_BLOCK_FALL);

    public static final SoundType CRYSTAL_GRASS = new SoundType(1.0F, 1.0F, CASoundEvents.CRYSTAL_GRASS_BREAK.get(), CASoundEvents.CRYSTAL_GRASS_STEP.get(), SoundEvents.AMETHYST_BLOCK_PLACE, SoundEvents.AMETHYST_BLOCK_HIT, SoundEvents.AMETHYST_BLOCK_FALL);
    public static final SoundType CRYSTAL_LEAVES = new SoundType(1.0F, 1.0F, CASoundEvents.CRYSTAL_LEAVES_BREAK.get(), CASoundEvents.CRYSTAL_LEAVES_STEP.get(), SoundEvents.AMETHYST_BLOCK_PLACE, SoundEvents.AMETHYST_BLOCK_HIT, SoundEvents.AMETHYST_BLOCK_FALL);

    public static final SoundType CRYSTAL_BUDDING = new SoundType(1.0F, 1.0F, CASoundEvents.CRYSTAL_BUDDING_BREAK.get(), CASoundEvents.KYANITE_STEP.get(), SoundEvents.AMETHYST_BLOCK_PLACE, SoundEvents.AMETHYST_BLOCK_HIT, SoundEvents.AMETHYST_BLOCK_FALL);
    public static final SoundType CRYSTAL_CLUSTER = new SoundType(1.0F, 1.0F, CASoundEvents.CRYSTAL_CLUSTER_BREAK.get(), CASoundEvents.KYANITE_STEP.get(), SoundEvents.AMETHYST_CLUSTER_PLACE, SoundEvents.AMETHYST_CLUSTER_HIT, SoundEvents.AMETHYST_CLUSTER_FALL);

    // Mining Paradise Blocks
    public static final SoundType TAR = new SoundType(1.0F, 1.0F, SoundEvents.HONEY_BLOCK_BREAK, CASoundEvents.TAR_STEP.get(), CASoundEvents.TAR_PLACE.get(), CASoundEvents.TAR_HIT.get(), CASoundEvents.TAR_FALL.get());
    public static final SoundType DENSE_GRASS = new SoundType(1.0F, 1.0F, CASoundEvents.DENSE_GRASS_BREAK.get(), CASoundEvents.DENSE_GRASS_STEP.get(), CASoundEvents.DENSE_GRASS_PLACE.get(), CASoundEvents.DENSE_GRASS_HIT.get(), CASoundEvents.DENSE_GRASS_FALL.get());
}
