package io.github.chaosawakens.content.block.vegetation.mining_paradise;

import com.mememan.nexus.template.object.block.vegetation.DefaultableTallGrassBlock;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.DoublePlantBlock;

import java.util.function.Supplier;

public class DenseTallGrassBlock extends DefaultableTallGrassBlock {

    public DenseTallGrassBlock(Properties properties, Supplier<DoublePlantBlock> tallPlantBlock) {
        super(properties, ObjectOpenHashSet.of(() -> BlockTags.DIRT, CATags.CABlockTags.DENSE_SOIL), tallPlantBlock);
    }

    public DenseTallGrassBlock(Properties properties) {
        super(properties, ObjectOpenHashSet.of(() -> BlockTags.DIRT, CATags.CABlockTags.DENSE_SOIL));
    }
}
