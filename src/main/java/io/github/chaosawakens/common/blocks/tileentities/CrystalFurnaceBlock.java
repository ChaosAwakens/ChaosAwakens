package io.github.chaosawakens.common.blocks.tileentities;

import io.github.chaosawakens.common.registry.CAStats;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class CrystalFurnaceBlock extends AbstractFurnaceBlock {
	
	public CrystalFurnaceBlock(Properties properties) {
		super(properties);
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new CrystalFurnaceTileEntity();
	}

	@Override
	protected void openContainer(World curWorld, BlockPos targetPos, PlayerEntity interactingPlayer) {
		TileEntity targetTE = curWorld.getBlockEntity(targetPos);
		
		if (targetTE instanceof CrystalFurnaceTileEntity) {
			interactingPlayer.openMenu((INamedContainerProvider) targetTE);
			interactingPlayer.awardStat(CAStats.INTERACT_WITH_CRYSTAL_FURNACE);
		}
	}

	@Override
	public void animateTick(BlockState targetState, World curWorld, BlockPos targetPos, Random rand) {
		if (targetState.getValue(LIT)) {
			Direction curFacingDir = targetState.getValue(FACING);
			double xPos = (double) targetPos.getX() + 0.5D;
			double yPos = targetPos.getY();
			double zPos = (double) targetPos.getZ() + 0.5D;
	        double fallbackOffset = rand.nextDouble() * 0.6D - 0.3D;
	        double randXOffset = curFacingDir.getAxis() == Direction.Axis.X ? curFacingDir.getStepX() * 0.52D : fallbackOffset;
	        double randYOffset = rand.nextDouble() * 6.0D / 16.0D;
	        double randZOffset = curFacingDir.getAxis() == Direction.Axis.Z ? curFacingDir.getStepZ() * 0.52D : fallbackOffset;
			
	        if (rand.nextDouble() < 0.1D) curWorld.playLocalSound(xPos, yPos, zPos, SoundEvents.FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);

			curWorld.addParticle(ParticleTypes.FLAME, xPos + randXOffset, yPos + randYOffset, zPos + randZOffset, 0.0D, 0.0D, 0.0D);
		}
	}
}
