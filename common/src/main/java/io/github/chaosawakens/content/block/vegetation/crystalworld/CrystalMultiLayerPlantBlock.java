package io.github.chaosawakens.content.block.vegetation.crystalworld;

import com.mememan.nexus.template.object.block.vegetation.DefaultableMultiLayerPlantBlock;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class CrystalMultiLayerPlantBlock extends DefaultableMultiLayerPlantBlock {

    public CrystalMultiLayerPlantBlock(Properties properties, int maxLevel, IntOpenHashSet modularLevels) {
        super(properties, maxLevel, modularLevels, ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL));
    }

    public CrystalMultiLayerPlantBlock(Properties properties, int maxLevel) {
        super(properties, maxLevel, ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL));
    }

    public CrystalMultiLayerPlantBlock(Properties properties) {
        super(properties, ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL));
    }
}
