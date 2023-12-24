package io.github.chaosawakens.common.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

public class FartParticle extends SpriteTexturedParticle {
	@SuppressWarnings("unused")
	private final IAnimatedSprite sprite;
	
	public FartParticle(ClientWorld world, double x, double y, double z, double xd, double yd, double zd, IAnimatedSprite spriteFromAge) {
		super(world, x, y, z, xd, yd, zd);
		this.hasPhysics = true;
		this.lifetime = 50;
		
		this.sprite = spriteFromAge;
		
		this.setSpriteFromAge(spriteFromAge);
		
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
	}
	
	public static class FartParticleProvider implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteF;
		
		public FartParticleProvider(IAnimatedSprite spriteF) {
			this.spriteF = spriteF;
		}

		@Override
		public Particle createParticle(BasicParticleType particle, ClientWorld world, double x, double y, double z, double xd, double yd, double zd) {
			FartParticle particleA = new FartParticle(world, x, y, z, xd, yd, zd, spriteF);
			particleA.setSpriteFromAge(spriteF);
			return particleA;
		}	
	}
}
