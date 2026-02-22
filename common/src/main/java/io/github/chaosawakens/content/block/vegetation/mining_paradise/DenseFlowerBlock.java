package io.github.chaosawakens.content.block.vegetation.mining_paradise;

import com.mememan.nexus.template.object.block.vegetation.DefaultableFlowerBlock;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;

public class DenseFlowerBlock extends DefaultableFlowerBlock {

    public DenseFlowerBlock(Supplier<MobEffect> suspiciousStewEffect, int effectDurationSeconds, Properties properties) {
        super(suspiciousStewEffect, effectDurationSeconds, properties, ObjectOpenHashSet.of(() -> BlockTags.DIRT, CATags.CABlockTags.DENSE_FLOWERS));
    }

    public DenseFlowerBlock(Properties properties) {
        super(properties, ObjectOpenHashSet.of(() -> BlockTags.DIRT, CATags.CABlockTags.DENSE_FLOWERS));
    }
}
