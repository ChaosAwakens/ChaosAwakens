package io.github.chaosawakens.content.block.vegetation.mining_paradise;

import com.mememan.nexus.template.object.block.vegetation.DefaultableMultiLayerPlantBlock;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.tags.BlockTags;

public class DenseMultiLayerPlantBlock extends DefaultableMultiLayerPlantBlock {

    public DenseMultiLayerPlantBlock(Properties properties, int maxLevel, IntOpenHashSet modularLevels) {
        super(properties, maxLevel, modularLevels, ObjectOpenHashSet.of(() -> BlockTags.DIRT, CATags.CABlockTags.DENSE_SOIL));
    }

    public DenseMultiLayerPlantBlock(Properties properties, int maxLevel) {
        super(properties, maxLevel, ObjectOpenHashSet.of(() -> BlockTags.DIRT, CATags.CABlockTags.DENSE_SOIL));
    }

    public DenseMultiLayerPlantBlock(Properties properties) {
        super(properties, ObjectOpenHashSet.of(() -> BlockTags.DIRT, CATags.CABlockTags.DENSE_SOIL));
    }
}
