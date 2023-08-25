package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.common.entity.creature.land.LettuceChickenEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CALettuceChickenEggEntity extends ProjectileItemEntity {

	public CALettuceChickenEggEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world);
	}
	
	public CALettuceChickenEggEntity(World world, LivingEntity owner) {
		super(CAEntityTypes.LETTUCE_CHICKEN_EGG.get(), owner, world);	
	}
	
	public CALettuceChickenEggEntity(World world, double xo, double yo, double zo) {
		super(CAEntityTypes.LETTUCE_CHICKEN_EGG.get(), xo, yo, zo, world);		
	}
	
	@Override
	protected Item getDefaultItem() {
		return CAItems.LETTUCE_CHICKEN_EGG.get();
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult result) {
		super.onHitEntity(result);
		result.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
	}
	
	@Override
	public void handleEntityEvent(byte state) {	    
		if (state == 3) {	     
			for(int i = 0; i < 8; ++i) {	   
				this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);	        
			}	      
		}   
	}
	
	@Override
	protected void onHit(RayTraceResult result) {		
		super.onHit(result);		 
		if (!this.level.isClientSide) {		     
			if (this.random.nextInt(8) == 0) {		      
				int i = 1;		        
				if (this.random.nextInt(32) == 0) {		       
					i = 4;		         
				}
				for(int j = 0; j < i; ++j) {									
					LettuceChickenEntity lettuceChicken = CAEntityTypes.LETTUCE_CHICKEN.get().create(level);;
					lettuceChicken.setAge(-24000);
					lettuceChicken.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
					this.level.addFreshEntity(lettuceChicken);
				}	
			}
			this.level.broadcastEntityEvent(this, (byte)3);	       
			this.remove();	    
		}
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
