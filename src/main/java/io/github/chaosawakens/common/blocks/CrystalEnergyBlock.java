package io.github.chaosawakens.common.blocks;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

public class CrystalEnergyBlock extends CAOreBlock {

	private static final List<IParticleData> particles = ImmutableList.of(ParticleTypes.FLAME.getType(), ParticleTypes.SMOKE.getType(), new RedstoneParticleData(0.3F, 0.4F, 0.2F, 0.8F));

	public CrystalEnergyBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!player.isCreative() && !worldIn.isClientSide && worldIn.random.nextInt(3) == 0)
			worldIn.explode(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 1.5F,
					worldIn.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
					? Explosion.Mode.DESTROY
					: Explosion.Mode.NONE);
		super.playerWillDestroy(worldIn, pos, state, player);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.random.nextInt(2) == 0) {
			worldIn.addParticle(particles.get(worldIn.random.nextInt(particles.size())), pos.getX() + worldIn.random.nextDouble(), pos.getY() + worldIn.random.nextDouble(), pos.getZ() + worldIn.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
}
