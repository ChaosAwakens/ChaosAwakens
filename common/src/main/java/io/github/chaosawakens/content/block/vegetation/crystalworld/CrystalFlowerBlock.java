package io.github.chaosawakens.content.block.vegetation.crystalworld;

import com.mememan.nexus.template.object.block.vegetation.DefaultableFlowerBlock;
import io.github.chaosawakens.content.registry.CASoundTypes;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class CrystalFlowerBlock extends DefaultableFlowerBlock {

    public CrystalFlowerBlock(Supplier<MobEffect> suspiciousStewEffect, int effectDurationSeconds, Properties properties) {
        super(suspiciousStewEffect, effectDurationSeconds, properties.sound(CASoundTypes.CRYSTAL_GRASS), ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL));
    }

    public CrystalFlowerBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(CASoundTypes.CRYSTAL_GRASS), ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL));
    }

    @Override
    public float getMaxVerticalOffset() {
        return 0.0F;
    }
}