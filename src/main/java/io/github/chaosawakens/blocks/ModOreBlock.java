package io.github.chaosawakens.blocks;

import io.github.chaosawakens.registry.CABlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import java.util.Random;

public class ModOreBlock extends Block {
    private boolean isFossilisedOre = false;

    public ModOreBlock(AbstractBlock.Properties properties, boolean fossilised) {
        super(properties);
        this.isFossilisedOre = fossilised;
    }

    public ModOreBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    protected int getExperience(Random rand) {
        if (CABlocks.AMETHYST_ORE.get().equals(this)) {
            return MathHelper.nextInt(rand, 3, 7);
        } else if (CABlocks.RUBY_ORE.get().equals(this)) {
            return MathHelper.nextInt(rand, 4, 9);
        } else if (CABlocks.TIGERS_EYE_ORE.get().equals(this)) {
            return MathHelper.nextInt(rand, 4, 8);
        } else if (CABlocks.SALT_ORE.get().equals(this)) {
            return MathHelper.nextInt(rand, 0, 2);
        }
        return this.isFossilisedOre ? MathHelper.nextInt(rand, 0, 2) : 0;
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 || isFossilisedOre ? this.getExperience(RANDOM) : 0;
    }
}
