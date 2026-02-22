package io.github.chaosawakens.content.block.vegetation.crystalworld;

import com.mememan.nexus.template.object.block.vegetation.DefaultableTallFlowerBlock;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class TallCrystalFlowerBlock extends DefaultableTallFlowerBlock {

    public TallCrystalFlowerBlock(Properties properties) {
        super(properties, ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL));
    }
}
