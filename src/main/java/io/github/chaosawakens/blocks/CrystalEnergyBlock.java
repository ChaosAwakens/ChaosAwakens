package io.github.chaosawakens.blocks;

import com.ibm.icu.text.MessagePattern;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class CrystalEnergyBlock extends CAOreBlock{
    public CrystalEnergyBlock(Properties properties) {
        super(properties);
    }

    private static ParticleType[] particles = new ParticleType[] {ParticleTypes.FLAME, ParticleTypes.SMOKE, ParticleTypes.DUST};

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote && worldIn.rand.nextInt(3) == 1) worldIn.createExplosion((Entity) null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 1.5F, worldIn.getGameRules().getBoolean(GameRules.MOB_GRIEFING) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (worldIn.rand.nextInt(5) == 0) {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + 0.5D;
            double d2 = (double)pos.getZ() + 0.5D;

            worldIn.addParticle((IParticleData) particles[worldIn.rand.nextInt(3)], d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
