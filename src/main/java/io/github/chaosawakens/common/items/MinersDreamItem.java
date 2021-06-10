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
	public int[] size;
	public int torchDistance;

	public MinersDreamItem(Properties builderIn, int[] sizeIn, int torchDistanceIn) {
		super(builderIn);
		this.size = sizeIn;
		this.torchDistance = torchDistanceIn;
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		PlayerEntity playerIn = context.getPlayer();
		Direction direction = context.getPlacementHorizontalFacing();

		BlockState torchBlock = world.getDimensionKey() == CADimensions.CRYSTAL_DIMENSION_LEGACY ? CABlocks.CRYSTAL_TORCH.get().getDefaultState() : CABlocks.EXTREME_TORCH.get().getDefaultState();
		BlockState supportBlock = world.getDimensionKey() == CADimensions.CRYSTAL_DIMENSION_LEGACY ? CABlocks.KYANITE.get().getDefaultState() : Blocks.COBBLESTONE.getDefaultState();

		int conX = pos.getX(), conY = (int) playerIn.getPosY(), conZ = pos.getZ();

		if (direction == Direction.UP || direction == Direction.DOWN) return ActionResultType.FAIL;
		int deltaX = direction.getDirectionVec().getX(), deltaZ = direction.getDirectionVec().getZ();

		if (!world.isRemote) {

			playerIn.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.5F);
			world.addParticle(ParticleTypes.EXPLOSION.getType(), conX, conY, conZ, 0.25F, 0.25F, 0.25F);

			for (int i = 0; i < size[1]; i++) {
				for (int j = 0; j < size[2]; j++) {
					for (int k = -size[0]; k <= size[0]; k++) {

						int x2 = conX + j * deltaX + k * deltaZ;
						int z2 = conZ + j * deltaZ + k * deltaX;
						BlockPos pos2 = new BlockPos(x2, conY + i, z2);
						BlockState bid = world.getBlockState(pos2);

						if (bid.getBlock().isIn(CATags.MINERS_DREAM_MINEABLE)) {
							world.setBlockState(pos2, Blocks.AIR.getDefaultState());
						}
						if (i == size[1] - 1) {
							pos2 = new BlockPos(x2, conY + i + 1, z2);
							bid = world.getBlockState(pos2);
							if (!bid.isIn(CATags.AIR_BLOCKS) && bid.isIn(CATags.MINERS_DREAM_UNSAFE)) world.setBlockState(pos2, supportBlock);
							else if (bid.isIn(CATags.AIR_BLOCKS)) world.setBlockState(pos2, Blocks.AIR.getDefaultState());
						}
					}
				}
			}
			for (int l = 0; l < size[2]; l += this.torchDistance) {
				BlockPos pos3 = new BlockPos(conX + l * deltaX, conY, conZ + l * deltaZ);
				BlockPos pos4 = new BlockPos(conX + l * deltaX, conY - 1, conZ + l * deltaZ);
				if (!world.getBlockState(pos4).getBlock().isIn(CATags.MINERS_DREAM_TORCH_SAFE) && world.getBlockState(pos3).getBlock().isIn(CATags.AIR_BLOCKS)) world.setBlockState(pos3, torchBlock);
			}
			context.getPlayer().addStat(Stats.ITEM_USED.get(this));
			context.getItem().shrink(1);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}
}