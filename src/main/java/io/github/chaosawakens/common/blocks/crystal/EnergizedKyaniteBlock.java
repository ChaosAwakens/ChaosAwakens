package io.github.chaosawakens.common.blocks.crystal;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.common.blocks.ore.CAOreBlock;
import net.minecraft.block.BlockState;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class EnergizedKyaniteBlock extends CAOreBlock {
	private static final List<IParticleData> EMIT_PARTICLES = ImmutableList.of(ParticleTypes.FLAME.getType(), ParticleTypes.SMOKE.getType(), new RedstoneParticleData(0.3F, 0.4F, 0.2F, 0.8F));

	public EnergizedKyaniteBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.random.nextInt(2) == 0) worldIn.addParticle(EMIT_PARTICLES.get(worldIn.random.nextInt(EMIT_PARTICLES.size())), pos.getX() + worldIn.random.nextDouble(), pos.getY() + worldIn.random.nextDouble(), pos.getZ() + worldIn.random.nextDouble(), 0.0D, 0.0D, 0.0D);
	}
}