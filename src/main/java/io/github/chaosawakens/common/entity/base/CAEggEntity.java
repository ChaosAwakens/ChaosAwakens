package io.github.chaosawakens.common.entity.base;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

//TODO
public class CAEggEntity extends ProjectileItemEntity {
	private final EntityType<? extends AnimalEntity> ownerEntity;
	private ItemStack stack;
	
	public CAEggEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world);
		this.ownerEntity = EntityType.PIG;
		this.stack = new ItemStack(Items.AIR);
	}
		  
	public CAEggEntity(EntityType<? extends AnimalEntity> type, ItemStack ownerStack, World world, LivingEntity owner) {		
		super(CAEntityTypes.LETTUCE_CHICKEN_EGG.get(), owner, world);	
		this.ownerEntity = type;
		this.stack = ownerStack;
	}
	
	public CAEggEntity(EntityType<? extends AnimalEntity> type, ItemStack ownerStack, World world, double xo, double yo, double zo) {		  
		super(CAEntityTypes.LETTUCE_CHICKEN_EGG.get(), xo, yo, zo, world);		
		this.ownerEntity = type;
		this.stack = ownerStack;
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
	public void handleEntityEvent(byte state) {	    
		if (state == 3) {	     
			for(int i = 0; i < 8; ++i) this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
		}   
	}
	
	@Override
	protected void onHit(RayTraceResult result) {		
		super.onHit(result);	
		
		if (!this.level.isClientSide) {		     
			if (this.random.nextInt(8) == 0) {		      
				int sumChance = 1;		        
				if (this.random.nextInt(32) == 0) sumChance = 4;
				
				for(int i = 0; i < sumChance; ++i) {									
					AnimalEntity targetAnimal = ownerEntity.create(level);;					
					targetAnimal.setAge(-24000);		            						
					targetAnimal.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
					this.level.addFreshEntity(targetAnimal);	           
				}	
			}
			this.level.broadcastEntityEvent(this, (byte)3);	       
			this.remove();	    
		}
	}
}
