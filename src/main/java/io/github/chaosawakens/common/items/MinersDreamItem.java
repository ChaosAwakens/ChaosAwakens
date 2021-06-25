package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MinersDreamItem extends Item {
	private int[] size;
	private static final int HOLE_LENGTH = 36;
	private static final int HOLE_WIDTH = 9;
	private static final int HOLE_HEIGHT = 8;
	private int torchDistance;

	public MinersDreamItem(Properties builderIn, int[] sizeIn, int torchDistanceIn) {
		super(builderIn);
		this.size = sizeIn;
		this.torchDistance = torchDistanceIn;
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		Direction direction = context.getPlacementHorizontalFacing();
		if (direction == Direction.UP || direction == Direction.DOWN) return ActionResultType.FAIL;
		
		World worldIn = context.getWorld();
		BlockPos breakPos = context.getPos();
		PlayerEntity playerIn = context.getPlayer();

		BlockState torchBlock = worldIn.getDimensionKey() == CADimensions.CRYSTAL_DIMENSION_LEGACY ? CABlocks.CRYSTAL_TORCH.get().getDefaultState() : CABlocks.EXTREME_TORCH.get().getDefaultState();
		BlockState supportBlock = worldIn.getDimensionKey() == CADimensions.CRYSTAL_DIMENSION_LEGACY ? CABlocks.KYANITE.get().getDefaultState() : Blocks.COBBLESTONE.getDefaultState();
		
		int deltaX = direction.getDirectionVec().getX(), deltaZ = direction.getDirectionVec().getZ();

		if (!worldIn.isRemote) {
			playerIn.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.5F);
			worldIn.addParticle(ParticleTypes.EXPLOSION.getType(), breakPos.getX(), breakPos.getY(), breakPos.getZ(), 0.25F, 0.25F, 0.25F);

			for (int i = 0; i < HOLE_HEIGHT; i++) {
				for (int j = 0; j < HOLE_LENGTH; j++) {
					for (int k = -HOLE_WIDTH/2; k <= HOLE_WIDTH/2; k++) {

						int x2 = breakPos.getX() + j * deltaX + k * deltaZ;
						int z2 = breakPos.getZ() + j * deltaZ + k * deltaX;
						BlockPos pos2 = new BlockPos(x2, breakPos.getY() + i, z2);
						BlockState bid = worldIn.getBlockState(pos2);

						if (bid.getBlock().isIn(CATags.MINERS_DREAM_MINEABLE)) {
							worldIn.removeBlock(pos2, false);
						}
						if (i == size[1] - 1) {
							pos2 = new BlockPos(x2, breakPos.getY() + i + 1, z2);
							bid = worldIn.getBlockState(pos2);
							if (!bid.isIn(CATags.AIR_BLOCKS) && bid.isIn(CATags.MINERS_DREAM_UNSAFE)) worldIn.setBlockState(pos2, supportBlock);
							else if (bid.isIn(CATags.AIR_BLOCKS)) worldIn.setBlockState(pos2, Blocks.AIR.getDefaultState());
						}
					}
				}
			}
			for (int l = 0; l < size[2]; l += this.torchDistance) {
				BlockPos pos3 = new BlockPos(breakPos.getX() + l * deltaX, breakPos.getY(), breakPos.getZ() + l * deltaZ);
				BlockPos pos4 = new BlockPos(breakPos.getX() + l * deltaX, breakPos.getY() - 1, breakPos.getZ() + l * deltaZ);
				if (worldIn.getBlockState(pos4).getBlock().isIn(CATags.MINERS_DREAM_TORCH_SAFE) && worldIn.getBlockState(pos3).getBlock().isIn(CATags.AIR_BLOCKS)) worldIn.setBlockState(pos3, torchBlock);
			}
			context.getPlayer().addStat(Stats.ITEM_USED.get(this));
			context.getItem().shrink(1);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}
}