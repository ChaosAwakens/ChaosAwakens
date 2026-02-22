package io.github.chaosawakens.content.block.vegetation.mining_paradise;

import com.mememan.nexus.template.object.block.vegetation.DefaultableTallFlowerBlock;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.tags.BlockTags;

public class TallDenseFlowerBlock extends DefaultableTallFlowerBlock {

    public TallDenseFlowerBlock(Properties properties) {
        super(properties, ObjectOpenHashSet.of(() -> BlockTags.DIRT, CATags.CABlockTags.DENSE_FLOWERS));
    }
}
