package io.github.chaosawakens.common.blocks;

import static io.github.chaosawakens.common.blocks.TopTubeBlock.SHAPE;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.AbstractBodyPlantBlock;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class TubeBlock extends AbstractBodyPlantBlock implements ILiquidContainer {

	public TubeBlock(Properties properties) {
		super(properties, Direction.UP, SHAPE, false);
	}

	@Override
	protected AbstractTopPlantBlock getHeadBlock() {
		return CABlocks.TUBE_WORM.get();
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState targetFluidState = context.getLevel().getFluidState(context.getClickedPos());
		return targetFluidState.is(FluidTags.WATER) && targetFluidState.getAmount() == 8 ? super.getStateForPlacement(context) : null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		BlockState targetState = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if (!targetState.isAir()) worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		return targetState;
	}

	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof LivingEntity && !entityIn.isShiftKeyDown() && entityIn.getBbHeight() > 1) {
			((LivingEntity) entityIn).addEffect(new EffectInstance(Effects.POISON, 100, 1));
			entityIn.makeStuckInBlock(state, new Vector3d(0.95F, 1D, 0.95F));
			if (worldIn.getRandom().nextInt(20) == 0) worldIn.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BONE_BLOCK_STEP, SoundCategory.AMBIENT, 1, 1, true);
		}
	}

	@Override
	public boolean canPlaceLiquid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		return false;
	}

	@Override
	public boolean placeLiquid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		return false;
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return Fluids.WATER.getSource(false);
	}
}
