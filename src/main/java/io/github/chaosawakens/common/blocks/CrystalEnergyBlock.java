package io.github.chaosawakens.common.blocks;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
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

public class CrystalEnergyBlock extends CAOreBlock {
	
	private static final List<IParticleData> particles = ImmutableList.of(ParticleTypes.FLAME.getType(), ParticleTypes.SMOKE.getType(), new RedstoneParticleData(0.3F, 0.4F, 0.2F, 0.8F));
	
	public CrystalEnergyBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!player.isCreative() && !worldIn.isRemote && worldIn.rand.nextInt(3) == 0)
			worldIn.createExplosion((Entity) null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 1.5F, worldIn.getGameRules().getBoolean(GameRules.MOB_GRIEFING) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.rand.nextInt(2) == 0) {
			worldIn.addParticle(particles.get(worldIn.rand.nextInt(particles.size())), pos.getX() + worldIn.rand.nextDouble(), pos.getY() + worldIn.rand.nextDouble(), pos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
}
