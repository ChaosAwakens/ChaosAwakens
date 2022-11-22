package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.blocks.tileentities.CrystalFurnaceTileEntity;
import io.github.chaosawakens.common.registry.CAStats;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class CrystalFurnaceBlock extends AbstractFurnaceBlock {
	public CrystalFurnaceBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new CrystalFurnaceTileEntity();
	}

	@Override
	protected void openContainer(World worldIn, BlockPos pos, PlayerEntity player) {
		TileEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof CrystalFurnaceTileEntity) {
			player.openMenu((INamedContainerProvider) tileentity);
			player.awardStat(CAStats.INTERACT_WITH_CRYSTAL_FURNACE);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.getValue(LIT)) {
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = pos.getY();
			double d2 = (double) pos.getZ() + 0.5D;
			if (rand.nextDouble() < 0.1D) worldIn.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);

			worldIn.addParticle(ParticleTypes.FLAME, d0, d1 + 1.1D, d2, 0.0D, 0.0D, 0.0D);
		}
	}
}
