package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CAEggEntity extends ProjectileItemEntity {
	private final EntityType<? extends AnimalEntity> ownerEntity;
	private ItemStack stack;
	
	public CAEggEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world);
		ownerEntity = CAEntityTypes.LEAFY_CHICKEN.get();
		stack = new ItemStack(CAItems.LEAFY_CHICKEN_EGG.get());
	}
		  
	public CAEggEntity(EntityType<? extends AnimalEntity> type, ItemStack ownerStack, World world, LivingEntity owner) {		
		super(CAEntityTypes.LEAFY_CHICKEN_EGG.get(), owner, world);	
		ownerEntity = type;
		stack = ownerStack;
	}
	
	public CAEggEntity(EntityType<? extends AnimalEntity> type, ItemStack ownerStack, World world, double xo, double yo, double zo) {		  
		super(CAEntityTypes.LEAFY_CHICKEN_EGG.get(), xo, yo, zo, world);		
		ownerEntity = type;
		stack = ownerStack;
	}
	
	@Override
	protected Item getDefaultItem() {
		return stack.getItem();
	}
	
	@Override
	public ItemStack getItem() {
		return stack;
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult result) {
		super.onHitEntity(result);
		result.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
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
					AnimalEntity m = ownerEntity.create(level);;					
					m.setAge(-24000);		            						
					m.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);		            					
					this.level.addFreshEntity(m);	           
				}	
			}
			this.level.broadcastEntityEvent(this, (byte)3);	       
			this.remove();	    
		}
	}

}
