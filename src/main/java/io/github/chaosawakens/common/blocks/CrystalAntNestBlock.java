package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.entity.AggressiveAntEntity;
import io.github.chaosawakens.common.entity.AntEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class CrystalAntNestBlock extends CrystalBlock {
	private final Supplier<? extends EntityType<? extends CreatureEntity>> ant;

	public CrystalAntNestBlock(Supplier<? extends EntityType<? extends CreatureEntity>> ant, Properties builder) {
		super(builder);
		this.ant = ant;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (worldIn.isClientSide || worldIn.isRainingAt(pos.above())) return;

		List<PlayerEntity> nearbyPlayerExists = worldIn.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(pos).inflate(8.0D, 8.0D, 8.0D));
		List<AntEntity> nearbyAntEntityList = worldIn.getEntitiesOfClass(AntEntity.class, new AxisAlignedBB(pos).inflate(12.0D, 8.0D, 12.0D));
		List<AggressiveAntEntity> nearbyAggressiveAntEntityList = worldIn.getEntitiesOfClass(AggressiveAntEntity.class, new AxisAlignedBB(pos).inflate(12.0D, 8.0D, 12.0D));

		if (nearbyPlayerExists.isEmpty()) return;
		if (nearbyAntEntityList.size() + nearbyAggressiveAntEntityList.size() > 10) return;

		final BlockPos abovePos = pos.above();
		final int amountToSpawn = MathHelper.nextInt(random, 0, 3);
		if (worldIn.getBlockState(abovePos).isAir(worldIn, abovePos)) {
			for (int i = 0; i < amountToSpawn; ++i) {
				CreatureEntity entity = ant.get().create(worldIn);
				assert entity != null;
				entity.setPos(pos.getX() + Math.random(), pos.getY() + 1, pos.getZ() + Math.random());
				worldIn.addFreshEntity(entity);
			}
		}
	}
}
