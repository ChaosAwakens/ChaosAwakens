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
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.EntityTeleportEvent;

public class RandomTeleportBlock extends Block {
	public RandomTeleportBlock(Properties props) {
		super(props);
	}

	@Override
	public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
		if (!worldIn.isClientSide) {
			double d0 = entityIn.getX();
			double d1 = entityIn.getY();
			double d2 = entityIn.getZ();

			for (int i = 0; i < 16; ++i) {
				if (entityIn instanceof LivingEntity) {
					LivingEntity livingEntityIn = (LivingEntity) entityIn;
					double d3 = entityIn.getX() + (livingEntityIn.getRandom().nextDouble() - 0.5D) * 16.0D;
					double d4 = MathHelper.clamp(entityIn.getY() + (double) (livingEntityIn.getRandom().nextInt(16) - 8), 0.0D, (worldIn.getHeight() - 1));
					double d5 = entityIn.getZ() + (livingEntityIn.getRandom().nextDouble() - 0.5D) * 16.0D;
					if (entityIn.isPassenger()) entityIn.stopRiding();

					EntityTeleportEvent.ChorusFruit event = ForgeEventFactory.onChorusFruitTeleport(livingEntityIn, d3, d4, d5);
					if (livingEntityIn.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
						SoundEvent soundevent = entityIn instanceof FoxEntity ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
						worldIn.playSound(null, d0, d1, d2, soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
						entityIn.playSound(soundevent, 1.0F, 1.0F);
						break;
					}
				}
			}
		}
		super.stepOn(worldIn, pos, entityIn);
	}
}
