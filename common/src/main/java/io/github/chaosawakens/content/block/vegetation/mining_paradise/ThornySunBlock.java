package io.github.chaosawakens.content.block.vegetation.mining_paradise;

import com.mememan.nexus.template.object.block.vegetation.DefaultableTallFlowerBlock;
import io.github.chaosawakens.content.registry.CADamageTypes;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ThornySunBlock extends DefaultableTallFlowerBlock {

    public ThornySunBlock(Properties properties) {
        super(properties, ObjectOpenHashSet.of(CATags.CABlockTags.DENSE_FLOWERS));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        entity.hurt(CADamageTypes.indirectSource(level, CADamageTypes.THORNY_SUN), 1.0F);
    }
}
