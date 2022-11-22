package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ExplosiveFireworkEntity extends FireworkRocketEntity {

	public ExplosiveFireworkEntity(EntityType<? extends FireworkRocketEntity> type, World world) {
		super(CAEntityTypes.EXPLOSIVE_FIREWORKS.get(), world);
	}
		 
	public ExplosiveFireworkEntity(World world, ItemStack ownerStack, Entity owner, double x, double y, double z, boolean b1) {		
		super(world, ownerStack, x, y, z, b1);		  
		this.setOwner(owner);		  
	}
	
	@Override
	protected void onHit(RayTraceResult result) {
		if (result.getType() != RayTraceResult.Type.MISS) {
			this.level.explode(this, getX(), getY(), getZ(), 2.0F, Explosion.Mode.NONE);
		}
	}

}
