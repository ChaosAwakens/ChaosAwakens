package io.github.chaosawakens.content.block.vegetation.mining_paradise;

import com.mememan.nexus.template.object.block.vegetation.DefaultableDoublePlantBlock;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.tags.BlockTags;

public class DenseDoublePlantBlock extends DefaultableDoublePlantBlock {

    public DenseDoublePlantBlock(Properties properties) {
        super(properties, ObjectOpenHashSet.of(() -> BlockTags.DIRT, CATags.CABlockTags.DENSE_SOIL));
    }
}
