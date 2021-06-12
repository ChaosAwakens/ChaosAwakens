package io.github.chaosawakens.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class RandomTeleportBlock extends Block {
    
	public RandomTeleportBlock(Properties props) {
        super(props);
    }

    public Random getRNG() {
        return this.RANDOM;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote) {
            double d0 = entityIn.getPosX();
            double d1 = entityIn.getPosY();
            double d2 = entityIn.getPosZ();

            for(int i = 0; i < 16; ++i) {
                double d3 = entityIn.getPosX() + (getRNG().nextDouble() - 0.5D) * 16.0D;
                double d4 = MathHelper.clamp(entityIn.getPosY() + (double)(getRNG().nextInt(16) - 8), 0.0D, (worldIn.func_234938_ad_() - 1));
                double d5 = entityIn.getPosZ() + (getRNG().nextDouble() - 0.5D) * 16.0D;
                if (entityIn.isPassenger()) {
                    entityIn.stopRiding();
                }

                net.minecraftforge.event.entity.living.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport((LivingEntity) entityIn, d3, d4, d5);
                if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
                    SoundEvent soundevent = entityIn instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                    worldIn.playSound(null, d0, d1, d2, soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    entityIn.playSound(soundevent, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }
}