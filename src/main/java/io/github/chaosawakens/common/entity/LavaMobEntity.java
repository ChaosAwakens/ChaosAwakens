package io.github.chaosawakens.common.entity;

import java.util.Collection;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class LavaMobEntity extends WaterMobEntity{

	public LavaMobEntity(EntityType<? extends WaterMobEntity> p_i48565_1_, World p_i48565_2_) {
		super(p_i48565_1_, p_i48565_2_);
		this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
		this.setPathfindingMalus(PathNodeType.LAVA, 10.0F);
		this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 0.0F);
	}
	
	@Override
	protected void dropFromLootTable(DamageSource src, boolean hitRecently) {
		super.dropFromLootTable(src, hitRecently);
		
		if (this.isInLava()) {
			MinecraftServer srvr = this.level.getServer();		
			ResourceLocation loc = this.getLootTable();
			
			if(srvr != null) {
				LootTable loottable = srvr.getLootTables().get(loc);
				LootContext.Builder ltcntxt = this.createLootContext(hitRecently, src);
				loottable.getRandomItems(ltcntxt.create(LootParameterSets.ENTITY)).forEach(itemstack -> {
			          if (hitRecently) {
			              ItemHandlerHelper.giveItemToPlayer(this.lastHurtByPlayer, itemstack);
			          } else {
			           this.getdropItemEntity(itemstack, 2.0F);
			       }
	           });
			}
		} else {
			super.dropFromLootTable(src, hitRecently);
		}
	}
	
	@Override
	protected void handleAirSupply(int air) {
	    if (this.isAlive() && !this.isInLava()) {
	        this.setAirSupply(air - 1);

	        if (this.isInWater()) {
	          this.setAirSupply(0);
	          this.hurt(DamageSource.DROWN, Integer.MAX_VALUE);
	        } else if (this.getAirSupply() == -20 || this.isInWater()) {
	          this.setAirSupply(0);
	          this.hurt(DamageSource.DROWN, 1.0F);
	        }
	      } else {
	        this.setAirSupply(900);
	      }
	}
	
        @Nullable
		public ItemEntity getdropItemEntity(ItemStack stack, float offsetY) {

		    if (stack.isEmpty()) {
		      return null;
		    } else {
		      ItemEntity itemEntity = new ItemEntity(this.level, this.getX(),
		          this.getY() + (double) offsetY, this.getZ(), stack);
		      itemEntity.setDefaultPickUpDelay();
		      itemEntity.setInvulnerable(true);
		      Collection<ItemEntity> captureDrops = captureDrops();

		      if (captureDrops != null) {
		        captureDrops.add(itemEntity);
		      } else {
		        this.level.addFreshEntity(itemEntity);
		      }
		      return itemEntity;
		    }
		  }
}
