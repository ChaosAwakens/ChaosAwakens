package io.github.chaosawakens.content.block.vegetation.mining_paradise;

import com.mememan.nexus.template.object.block.vegetation.DefaultableMutiLayerFlowerBlock;
import io.github.chaosawakens.content.registry.CASoundTypes;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.tags.BlockTags;

public class DenseMultiLayerFlowerBlock extends DefaultableMutiLayerFlowerBlock {

    public DenseMultiLayerFlowerBlock(Properties properties, int maxLevel) {
        super(properties.sound(CASoundTypes.DENSE_GRASS), maxLevel, ObjectOpenHashSet.of(() -> BlockTags.DIRT, CATags.CABlockTags.DENSE_SOIL));
    }

    public DenseMultiLayerFlowerBlock(Properties properties) {
        super(properties.sound(CASoundTypes.DENSE_GRASS), ObjectOpenHashSet.of(() -> BlockTags.DIRT, CATags.CABlockTags.DENSE_SOIL));
    }
}
