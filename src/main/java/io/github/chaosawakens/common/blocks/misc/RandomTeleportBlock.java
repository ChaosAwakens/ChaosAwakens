package io.github.chaosawakens.common.blocks.misc;

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
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.EntityTeleportEvent;

public class RandomTeleportBlock extends Block {
	
	public RandomTeleportBlock(Properties props) {
		super(props);
	}

	@Override
	public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
		if (!worldIn.isClientSide) {
			double x = entityIn.getX();
			double y = entityIn.getY();
			double z = entityIn.getZ();

			for (int i = 0; i < 16; ++i) {
				if (entityIn instanceof LivingEntity) {
					LivingEntity targetEntity = (LivingEntity) entityIn;
					double targetX = entityIn.getX() + (targetEntity.getRandom().nextDouble() - 0.5D) * 16.0D;
					double targetY = MathHelper.clamp(entityIn.getY() + (double) (targetEntity.getRandom().nextInt(16) - 8), 0.0D, (worldIn.getHeight() - 1));
					double targetZ = entityIn.getZ() + (targetEntity.getRandom().nextDouble() - 0.5D) * 16.0D;
					if (entityIn.isPassenger()) entityIn.stopRiding();

					EntityTeleportEvent.ChorusFruit event = ForgeEventFactory.onChorusFruitTeleport(targetEntity, targetX, targetY, targetZ);
					if (targetEntity.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
						SoundEvent tpSound = entityIn instanceof FoxEntity ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
						worldIn.playSound(null, x, y, z, tpSound, SoundCategory.PLAYERS, 1.0F, 1.0F);
						entityIn.playSound(tpSound, 1.0F, 1.0F);
						break;
					}
				}
			}
		}
		super.stepOn(worldIn, pos, entityIn);
	}
}
