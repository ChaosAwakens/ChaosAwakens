package io.github.chaosawakens.content.block.vegetation.mining_paradise;

import io.github.chaosawakens.content.registry.CADamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BigCarnivorousPlantBlock extends DenseFlowerBlock {

    public BigCarnivorousPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        entity.hurt(CADamageTypes.indirectSource(level, CADamageTypes.BIG_CARNIVOROUS_PLANT), 1.0F);
    }
}
