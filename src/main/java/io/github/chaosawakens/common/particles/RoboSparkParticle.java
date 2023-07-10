package io.github.chaosawakens.common.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

public class RoboSparkParticle extends SpriteTexturedParticle {
	@SuppressWarnings("unused")
	private final IAnimatedSprite sprite;
	
	public RoboSparkParticle(ClientWorld world, double x, double y, double z, double xd, double yd, double zd, IAnimatedSprite spriteFromAge) {
		super(world, x, y, z, xd, yd, zd);
		this.hasPhysics = true;
		this.lifetime = 30;
		
		this.sprite = spriteFromAge;
		
		setSpriteFromAge(spriteFromAge);
		
		//Positions
		this.xo = x;
		this.yo = y;
		this.zo = z;
		
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;
		
		//RGB Colors
		this.rCol = 1F;
		this.gCol = 1F;
		this.bCol = 1F;
	}
	
	private void fade() {
		this.alpha = (-(1/(float)lifetime)) * age + 1;
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}
	
	@Override
	public void tick() {
		super.tick();
		fade();
		
        this.yd += 0.004D;
        
        move(this.xd, this.yd, this.zd);
        
        this.xd *= (double)0.9F;
        this.yd *= (double)0.9F;
        this.zd *= (double)0.9F;
        
        if (this.onGround) {
           this.xd *= (double)0.7F;
           this.zd *= (double)0.7F;
        }
	}
	
	public static class RoboSparkParticleProvider implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteF;
		
		public RoboSparkParticleProvider(IAnimatedSprite spriteF) {
			this.spriteF = spriteF;
		}

		@Override
		public Particle createParticle(BasicParticleType particle, ClientWorld world, double x, double y, double z, double xd, double yd, double zd) {
			RoboSparkParticle particleA = new RoboSparkParticle(world, x, y, z, xd, yd, zd, spriteF);
			particleA.setSpriteFromAge(spriteF);
			return particleA;
		}
		
	}
}
