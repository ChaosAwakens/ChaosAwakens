package io.github.chaosawakens.content.block.vegetation.crystalworld;

import com.mememan.nexus.template.object.block.vegetation.DefaultableMutiLayerFlowerBlock;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class CrystalMultiLayerFlowerBlock extends DefaultableMutiLayerFlowerBlock {

    public CrystalMultiLayerFlowerBlock(Properties properties, int maxLevel) {
        super(properties, maxLevel, ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL));
    }

    public CrystalMultiLayerFlowerBlock(Properties properties) {
        super(properties, ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL));
    }
}
