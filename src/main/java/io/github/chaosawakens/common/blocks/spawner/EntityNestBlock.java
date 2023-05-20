package io.github.chaosawakens.common.blocks.spawner;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EntityNestBlock extends Block {
	private final Supplier<? extends EntityType<? extends MobEntity>> entityType;
	private final int maxSpawnLimit;

	public EntityNestBlock(Supplier<? extends EntityType<? extends MobEntity>> entityType, int maxSpawnLimit, Properties builder) {
		super(builder);
		this.entityType = entityType;
		this.maxSpawnLimit = maxSpawnLimit;
	}
	
	public EntityNestBlock(Supplier<? extends EntityType<? extends MobEntity>> entityType, Properties builder) {
		super(builder);
		this.entityType = entityType;
		this.maxSpawnLimit = 10;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (!shouldSpawnEntity(worldIn)) return;

		List<PlayerEntity> nearbyPlayers = worldIn.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(pos).inflate(8.0D, 8.0D, 8.0D));
		List<MobEntity> nearbyTargetEntities = worldIn.getEntitiesOfClass(MobEntity.class, new AxisAlignedBB(pos).inflate(12.0D, 8.0D, 12.0D));

		if (nearbyPlayers.isEmpty()) return;
		if (nearbyTargetEntities.size() > maxSpawnLimit) return;

		BlockPos abovePos = pos.above().immutable();
		final int amountToSpawn = MathHelper.nextInt(random, 0, 3);
		if (worldIn.getBlockState(abovePos).isAir(worldIn, abovePos)) {
			for (int i = 0; i < amountToSpawn; ++i) {
				MobEntity entity = entityType.get().create(worldIn);
				assert entity != null;
				entity.setPos(pos.getX() + Math.random(), pos.getY() + 1, pos.getZ() + Math.random());
				worldIn.addFreshEntity(entity);
			}
		}
	}
	
	protected boolean shouldSpawnEntity(World world) {
		return !world.isClientSide;
	}
}
