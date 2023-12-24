package io.github.chaosawakens.common.blocks.ore;

import io.github.chaosawakens.common.registry.CABlockStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class UraniumOreBlock extends CAOreBlock {
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final IntegerProperty GLOW_STRENGTH = CABlockStateProperties.URANIUM_GLOW_STRENGTH;
	private static final IParticleData DUST_PARTICLE = new RedstoneParticleData(0.6F, 0.8F, 0.3F, 1.0F);

	public UraniumOreBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
		this.registerDefaultState(this.defaultBlockState().setValue(GLOW_STRENGTH, 0));
	}

	private static void interact(BlockState state, World world, BlockPos position) {
		spawnParticles(world, position);
		if (!state.getValue(LIT) || state.getValue(GLOW_STRENGTH) < 5) world.setBlock(position, state.setValue(LIT, true).setValue(GLOW_STRENGTH, 5), 3);
	}

	private static void spawnParticles(World world, BlockPos position) {
		Random random = world.random;
		for (Direction direction : Direction.values()) {
			BlockPos targetPos = position.relative(direction);
			if (!world.getBlockState(targetPos).isSolidRender(world, targetPos)) {
				Direction.Axis curAxis = direction.getAxis();
				double xd = curAxis == Direction.Axis.X
						? 0.5D + 0.5625D * (double) direction.getStepX()
						: (double) random.nextFloat();
				double yd = curAxis == Direction.Axis.Y
						? 0.5D + 0.5625D * (double) direction.getStepY()
						: (double) random.nextFloat();
				double zd = curAxis == Direction.Axis.Z
						? 0.5D + 0.5625D * (double) direction.getStepZ()
						: (double) random.nextFloat();
				world.addParticle(DUST_PARTICLE, (double) position.getX() + xd, (double) position.getY() + yd, (double) position.getZ() + zd, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public void animateTick(BlockState state, World world, BlockPos blockPos, Random random) {
		if (state.getValue(LIT) && state.getValue(GLOW_STRENGTH) > 0) spawnParticles(world, blockPos);
		super.animateTick(state, world, blockPos, random);
	}

	@Override
	public void attack(BlockState state, World world, BlockPos pos, PlayerEntity player) {
		interact(state, world, pos);
	}

	@Override
	public void stepOn(World world, BlockPos position, Entity steppingEntity) {
		interact(world.getBlockState(position), world, position);
		super.stepOn(world, position, steppingEntity);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos targetPos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		if (world.isClientSide) {
			spawnParticles(world, targetPos);
		} else
			interact(state, world, targetPos);
		ItemStack handStack = player.getItemInHand(hand);
		return handStack.getItem() instanceof BlockItem && (new BlockItemUseContext(player, hand, handStack, result)).canPlace() ? ActionResultType.PASS : ActionResultType.SUCCESS;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LIT);
		builder.add(GLOW_STRENGTH);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerWorld server, BlockPos blockPos, Random random) {
		if (state.getValue(GLOW_STRENGTH) > 0 && !server.isClientSide) {
			server.setBlock(blockPos, state.setValue(GLOW_STRENGTH, state.getValue(GLOW_STRENGTH) - 1), 3);
			if (state.getValue(GLOW_STRENGTH) <= 0 && state.getValue(LIT)) server.setBlock(blockPos, state.setValue(LIT, false), 3);
		}
		super.randomTick(state, server, blockPos, random);
	}
}
