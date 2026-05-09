package io.github.chaosawakens.content.block.vegetation.crystalworld;

import com.mememan.nexus.template.object.block.vegetation.DefaultableDoublePlantBlock;
import io.github.chaosawakens.content.registry.CASoundTypes;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class CrystalDoublePlantBlock extends DefaultableDoublePlantBlock {

    public CrystalDoublePlantBlock(Properties properties) {
        super(properties.sound(CASoundTypes.CRYSTAL_GRASS), ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL));
    }
}
