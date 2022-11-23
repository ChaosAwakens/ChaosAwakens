package io.github.chaosawakens.mixins;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.items.EnderScaleArmorItem;
import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

//TODO Re-code this entire file, the code I wrote here is just a temporary substitute while other stuff gets worked on
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	private static final UUID SLOW_FALLING_ID = UUID.fromString("A5B6CF2A-2F7C-31EF-9022-7C3E7D5E6ABA");
	private static final AttributeModifier SLOW_FALLING = new AttributeModifier(SLOW_FALLING_ID, "Slow falling acceleration reduction", -0.07, AttributeModifier.Operation.ADDITION); // Add -0.07 to 0.08 so we get the vanilla default of 0.01
	
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	 
	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setSharedFlag(IZ)V"), method = "updateFallFlying")
	public boolean chaosawakens$setFlag(boolean value) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (EnderScaleArmorItem.isElytraToggled((entity.getItemBySlot(EquipmentSlotType.CHEST)))) {
			ItemStack chestplate = entity.getItemBySlot(EquipmentSlotType.CHEST);
			value = IUtilityHelper.isFullArmorSet((PlayerEntity) entity, CAItems.ENDER_DRAGON_SCALE_HELMET.get(), CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get(), CAItems.ENDER_DRAGON_SCALE_LEGGINGS.get(), CAItems.ENDER_DRAGON_SCALE_BOOTS.get()) && EnderScaleArmorItem.isElytraToggled(chestplate);
			entity.setSharedFlag(7, entity.getSharedFlag(7) && value);
			return entity.getSharedFlag(7);
		}
		return entity.getSharedFlag(7);
	}
	
	@Inject(method = "Lnet/minecraft/entity/LivingEntity;aiStep()V", at = @At("INVOKE"), cancellable = true)
	public void chaosawakens$aiStep(CallbackInfo info) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (EnderScaleArmorItem.isElytraToggled((entity.getItemBySlot(EquipmentSlotType.CHEST)))) {
			entity.flyingSpeed = 0.012F;
		}
	}

	@Inject(method = "isImmobile", at = @At("HEAD"), cancellable = true)
	protected void chaosawakens$isImmobile(CallbackInfoReturnable<Boolean> cir) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity.hasEffect(CAEffects.PARALYSIS_EFFECT.get())) {
			entity.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
			entity.setNoActionTime(entity.getEffect(CAEffects.PARALYSIS_EFFECT.get()).getDuration());
			cir.setReturnValue(true);
		}
	}
	
	//TODO make this more efficient and safe, what I did here is what I saw fit, but overwriting the entire travel method may cause problems with other mods trying to
	//modify values in it as well. No conflicts found as of yet. --Meme Man
	@SuppressWarnings("deprecation")
	@Overwrite
	public void travel(Vector3d p_213352_1_) {
		LivingEntity entity = (LivingEntity) (Object) this;
	      if (entity.isEffectiveAi() || entity.isControlledByLocalInstance()) {
	          double d0 = 0.08D;
	          ModifiableAttributeInstance gravity = entity.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
	          boolean flag = entity.getDeltaMovement().y <= 0.0D;
	          if (flag && entity.hasEffect(Effects.SLOW_FALLING)) {
	             if (!gravity.hasModifier(SLOW_FALLING)) gravity.addTransientModifier(SLOW_FALLING);
	             entity.fallDistance = 0.0F;
	          } else if (gravity.hasModifier(SLOW_FALLING)) {
	             gravity.removeModifier(SLOW_FALLING);
	          }
	          d0 = gravity.getValue();

	          FluidState fluidstate = entity.level.getFluidState(entity.blockPosition());
	          boolean playerb = entity instanceof PlayerEntity && IUtilityHelper.isFullArmorSet((PlayerEntity) entity, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get()) && entity.isInLava() && entity.isAffectedByFluids() && !entity.canStandOnFluid(fluidstate.getType());
	          if (entity.isInWater() && entity.isAffectedByFluids() && !entity.canStandOnFluid(fluidstate.getType())) {
	             double d8 = entity.getY();
	             float f5 = entity.isSprinting() ? 0.9F : entity.getWaterSlowDown();
	             float f6 = 0.02F;
	             float f7 = (float)EnchantmentHelper.getDepthStrider(entity);
	             if (f7 > 3.0F) {
	                f7 = 3.0F;
	             }

	             if (!entity.onGround) {
	                f7 *= 0.5F;
	             }

	             if (f7 > 0.0F) {
	                f5 += (0.54600006F - f5) * f7 / 3.0F;
	                f6 += (entity.getSpeed() - f6) * f7 / 3.0F;
	             }

	             if (entity.hasEffect(Effects.DOLPHINS_GRACE)) {
	                f5 = 0.96F;
	             }

	             f6 *= (float)entity.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();
	             entity.moveRelative(f6, p_213352_1_);
	             entity.move(MoverType.SELF, entity.getDeltaMovement());
	             Vector3d vector3d6 = entity.getDeltaMovement();
	             if (entity.horizontalCollision && entity.onClimbable()) {
	                vector3d6 = new Vector3d(vector3d6.x, 0.2D, vector3d6.z);
	             }

	             entity.setDeltaMovement(vector3d6.multiply((double)f5, (double)0.8F, (double)f5));
	             Vector3d vector3d2 = entity.getFluidFallingAdjustedMovement(d0, flag, entity.getDeltaMovement());
	             entity.setDeltaMovement(vector3d2);
	             if (entity.horizontalCollision && entity.isFree(vector3d2.x, vector3d2.y + (double)0.6F - entity.getY() + d8, vector3d2.z)) {
	                entity.setDeltaMovement(vector3d2.x, (double)0.3F, vector3d2.z);
	             }
	          } else if (entity instanceof PlayerEntity && playerb) {
	        	  PlayerEntity player = (PlayerEntity) entity;
		             double d8 = player.getY();
		             float f5 = player.isSprinting() ? 1.0F : 0.9F;
		             float f6 = 0.02F;
    	             ModifiableAttributeInstance gravityp = player.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
    	             d0 = gravityp.getValue();
    	             f6 *= (float)player.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();
		             float f7 = (float)EnchantmentHelper.getDepthStrider(player);
		             if (f7 > 3.0F) {
		                f7 = 3.0F;
		             }

		             if (!player.onGround) {
		                f7 *= 0.5F;
		             }

		             if (f7 > 0.0F) {
		                f5 += (0.54600006F - f5) * f7 / 3.0F;
		                f6 += (player.getSpeed() - f6) * f7 / 3.0F;
		             }
		             
		             if (player.isSprinting() && playerb) {
		            	 player.setForcedPose(Pose.SWIMMING);
		             } else {
		            	 player.setForcedPose(null);
		             }
		             if (!player.isInLava() || player.isOnGround() && !player.isInLava()) player.setForcedPose(null);

		             f6 *= (float)player.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();
		             player.moveRelative(f6, p_213352_1_);
		             player.move(MoverType.SELF, player.getDeltaMovement());
		             Vector3d vector3d6 = player.getDeltaMovement();
		             if (player.horizontalCollision && player.onClimbable()) {
		                vector3d6 = new Vector3d(vector3d6.x, 0.2D, vector3d6.z);
		             }
		             
		             if (player.getFluidHeight(FluidTags.LAVA) <= player.getFluidJumpThreshold()) {
		            	 player.setDeltaMovement(player.getDeltaMovement().multiply((double)f5, (double)0.8F, (double)f5));
			             if (player.isShiftKeyDown()) {			           
			            	 player.setDeltaMovement(player.getDeltaMovement().add(0.0D, (double)-0.04F * player.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue(), 0.0D));
			             }
			                Vector3d vector3d3 = player.getFluidFallingAdjustedMovement(d0, flag, player.getDeltaMovement());
			                player.setDeltaMovement(vector3d3);			          
		             } else {
		            	 player.setDeltaMovement(player.getDeltaMovement().scale(f5));
		             }

		             player.setDeltaMovement(vector3d6.multiply((double)f5, (double)0.8F, (double)f5));
		             Vector3d vector3d2 = player.getFluidFallingAdjustedMovement(d0, flag, player.getDeltaMovement());
		             player.setDeltaMovement(vector3d2);
		             if (player.horizontalCollision && player.isFree(vector3d2.x, vector3d2.y + (double)0.6F - player.getY() + d8, vector3d2.z)) {
		                player.setDeltaMovement(vector3d2.x, (double)0.3F, vector3d2.z);
		             }
	          } else if (entity.isInLava() && entity.isAffectedByFluids() && !entity.canStandOnFluid(fluidstate.getType()) && !playerb) {
	             double d7 = entity.getY();
	             entity.moveRelative(0.02F, p_213352_1_);
	             entity.move(MoverType.SELF, entity.getDeltaMovement());
	             if (entity.getFluidHeight(FluidTags.LAVA) <= entity.getFluidJumpThreshold()) {
	                entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.5D, (double)0.8F, 0.5D));
	                Vector3d vector3d3 = entity.getFluidFallingAdjustedMovement(d0, flag, entity.getDeltaMovement());
	                entity.setDeltaMovement(vector3d3);
	             } else {
	                entity.setDeltaMovement(entity.getDeltaMovement().scale(0.5D));
	             }

	             if (!entity.isNoGravity()) {
	                entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, -d0 / 4.0D, 0.0D));
	             }

	             Vector3d vector3d4 = entity.getDeltaMovement();
	             if (entity.horizontalCollision && entity.isFree(vector3d4.x, vector3d4.y + (double)0.6F - entity.getY() + d7, vector3d4.z)) {
	                entity.setDeltaMovement(vector3d4.x, (double)0.3F, vector3d4.z);
	             }
	          } else if (entity.isFallFlying()) {
	             Vector3d vector3d = entity.getDeltaMovement();
	             if (vector3d.y > -0.5D) {
	                entity.fallDistance = 1.0F;
	             }

	             Vector3d vector3d1 = entity.getLookAngle();
	             float f = entity.xRot * ((float)Math.PI / 180F);
	             double d1 = Math.sqrt(vector3d1.x * vector3d1.x + vector3d1.z * vector3d1.z);
	             double d3 = Math.sqrt(getHorizontalDistanceSqr(vector3d));
	             double d4 = vector3d1.length();
	             float f1 = MathHelper.cos(f);
	             f1 = (float)((double)f1 * (double)f1 * Math.min(1.0D, d4 / 0.4D));
	             vector3d = entity.getDeltaMovement().add(0.0D, d0 * (-1.0D + (double)f1 * 0.75D), 0.0D);
	             if (vector3d.y < 0.0D && d1 > 0.0D) {
	                double d5 = vector3d.y * -0.1D * (double)f1;
	                vector3d = vector3d.add(vector3d1.x * d5 / d1, d5, vector3d1.z * d5 / d1);
	             }

	             if (f < 0.0F && d1 > 0.0D) {
	                double d9 = d3 * (double)(-MathHelper.sin(f)) * 0.04D;
	                vector3d = vector3d.add(-vector3d1.x * d9 / d1, d9 * 3.2D, -vector3d1.z * d9 / d1);
	             }

	             if (d1 > 0.0D) {
	                vector3d = vector3d.add((vector3d1.x / d1 * d3 - vector3d.x) * 0.1D, 0.0D, (vector3d1.z / d1 * d3 - vector3d.z) * 0.1D);
	             }

	             entity.setDeltaMovement(vector3d.multiply((double)0.99F, (double)0.98F, (double)0.99F));
	             entity.move(MoverType.SELF, entity.getDeltaMovement());
	             if (entity.horizontalCollision && !entity.level.isClientSide) {
	                double d10 = Math.sqrt(getHorizontalDistanceSqr(entity.getDeltaMovement()));
	                double d6 = d3 - d10;
	                float f2 = (float)(d6 * 10.0D - 3.0D);
	                if (f2 > 0.0F) {
	                   entity.playSound(entity.getFallDamageSound((int)f2), 1.0F, 1.0F);
	                   entity.hurt(DamageSource.FLY_INTO_WALL, f2);
	                }
	             }

	             if (entity.onGround && !entity.level.isClientSide) {
	                entity.setSharedFlag(7, false);
	             }
	          } else {
	             BlockPos blockpos = entity.getBlockPosBelowThatAffectsMyMovement();
	             float f3 = entity.level.getBlockState(entity.getBlockPosBelowThatAffectsMyMovement()).getSlipperiness(level, entity.getBlockPosBelowThatAffectsMyMovement(), entity);
	             float f4 = entity.onGround ? f3 * 0.91F : 0.91F;
	             Vector3d vector3d5 = entity.handleRelativeFrictionAndCalculateMovement(p_213352_1_, f3);
	             double d2 = vector3d5.y;
	             if (entity.hasEffect(Effects.LEVITATION)) {
	                d2 += (0.05D * (double)(entity.getEffect(Effects.LEVITATION).getAmplifier() + 1) - vector3d5.y) * 0.2D;
	                entity.fallDistance = 0.0F;
	             } else if (entity.level.isClientSide && !entity.level.hasChunkAt(blockpos)) {
	                if (entity.getY() > 0.0D) {
	                   d2 = -0.1D;
	                } else {
	                   d2 = 0.0D;
	                }
	             } else if (!entity.isNoGravity()) {
	                d2 -= d0;
	             }

	             entity.setDeltaMovement(vector3d5.x * (double)f4, d2 * (double)0.98F, vector3d5.z * (double)f4);
	          }
	       }

	       entity.calculateEntityAnimation(entity, entity instanceof IFlyingAnimal);
	}
	
/*	@SuppressWarnings("deprecation")
	@Overwrite
	public void travel(Vector3d p_213352_1_) {
		LivingEntity entity = (LivingEntity) (Object) this;
	      if (entity.isEffectiveAi() || entity.isControlledByLocalInstance()) {
	          double d0 = 0.08D;
	          ModifiableAttributeInstance gravity = entity.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
	          boolean flag = entity.getDeltaMovement().y <= 0.0D;
	          if (flag && entity.hasEffect(Effects.SLOW_FALLING)) {
	             if (!gravity.hasModifier(SLOW_FALLING)) gravity.addTransientModifier(SLOW_FALLING);
	             entity.fallDistance = 0.0F;
	          } else if (gravity.hasModifier(SLOW_FALLING)) {
	             gravity.removeModifier(SLOW_FALLING);
	          }
	          d0 = gravity.getValue();

	          FluidState fluidstate = entity.level.getFluidState(entity.blockPosition());
	          if (entity.isInWater() && entity.isAffectedByFluids() && !entity.canStandOnFluid(fluidstate.getType())) {
	             double d8 = entity.getY();
	             float f5 = entity.isSprinting() ? 0.9F : entity.getWaterSlowDown();
	             float f6 = 0.02F;
	             float f7 = (float)EnchantmentHelper.getDepthStrider(entity);
	             if (f7 > 3.0F) {
	                f7 = 3.0F;
	             }

	             if (!entity.onGround) {
	                f7 *= 0.5F;
	             }

	             if (f7 > 0.0F) {
	                f5 += (0.54600006F - f5) * f7 / 3.0F;
	                f6 += (entity.getSpeed() - f6) * f7 / 3.0F;
	             }

	             if (entity.hasEffect(Effects.DOLPHINS_GRACE)) {
	                f5 = 0.96F;
	             }

	             f6 *= (float)entity.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();
	             entity.moveRelative(f6, p_213352_1_);
	             entity.move(MoverType.SELF, entity.getDeltaMovement());
	             Vector3d vector3d6 = entity.getDeltaMovement();
	             if (entity.horizontalCollision && entity.onClimbable()) {
	                vector3d6 = new Vector3d(vector3d6.x, 0.2D, vector3d6.z);
	             }

	             entity.setDeltaMovement(vector3d6.multiply((double)f5, (double)0.8F, (double)f5));
	             Vector3d vector3d2 = entity.getFluidFallingAdjustedMovement(d0, flag, entity.getDeltaMovement());
	             entity.setDeltaMovement(vector3d2);
	             if (entity.horizontalCollision && entity.isFree(vector3d2.x, vector3d2.y + (double)0.6F - entity.getY() + d8, vector3d2.z)) {
	                entity.setDeltaMovement(vector3d2.x, (double)0.3F, vector3d2.z);
	             }
	          } else if (entity.isInLava() && entity.isAffectedByFluids() && !entity.canStandOnFluid(fluidstate.getType())) {
	             double d7 = entity.getY();
	            if (entity instanceof PlayerEntity) {
	            	 PlayerEntity player = (PlayerEntity) entity;
	            	 FluidState fluidstate2 = player.level.getFluidState(player.blockPosition());
	            	 if (player.isInLava() && player.isAffectedByFluids() && !player.canStandOnFluid(fluidstate2.getType())) {
		            	 if (IUtilityHelper.isFullArmorSet(player, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())) {
		    	             float f5 = player.isSprinting() ? 0.96F : player.getWaterSlowDown();
		    	             float f6 = 0.02F;
		    	             double y = player.getY();
		    	             ModifiableAttributeInstance gravityp = entity.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
		    	             d0 = gravityp.getValue();
		    	             f6 *= (float)player.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();
		            		 player.moveRelative(f6, p_213352_1_);
		    	             player.move(MoverType.SELF, player.getDeltaMovement());
		    	             if (player.isSprinting()) {
		    	            	 player.setForcedPose(Pose.SWIMMING);
		    	             } else {
		    	            	 player.setForcedPose(null);
		    	             }
		    	             if (player.getFluidHeight(FluidTags.LAVA) <= player.getFluidJumpThreshold()) {
		    	                player.setDeltaMovement(player.getDeltaMovement().multiply((double)f5, (double)0.8F, (double)f5));
		    	                Vector3d vector3d3 = player.getFluidFallingAdjustedMovement(d0, flag, player.getDeltaMovement());
		    	                player.setDeltaMovement(vector3d3);
		    	             } else {
		    	                player.setDeltaMovement(player.getDeltaMovement().scale(f5));
		    	             }
				     
				             Vector3d vector3d4 = player.getDeltaMovement();		        
				             if (player.horizontalCollision && player.isFree(vector3d4.x, vector3d4.y + (double)0.6F - player.getY() + y, vector3d4.z)) {		           
				            	 player.setDeltaMovement(vector3d4.x, (double)0.3F, vector3d4.z);	           
				             }
		            	 } else {
				             player.moveRelative(0.02F, p_213352_1_);
				             player.move(MoverType.SELF, player.getDeltaMovement());
				             if (player.getFluidHeight(FluidTags.LAVA) <= player.getFluidJumpThreshold()) {
				                player.setDeltaMovement(player.getDeltaMovement().multiply(0.5D, (double)0.8F, 0.5D));
				                Vector3d vector3d3 = player.getFluidFallingAdjustedMovement(d0, flag, player.getDeltaMovement());
				                player.setDeltaMovement(vector3d3);
				             } else {
				                player.setDeltaMovement(player.getDeltaMovement().scale(0.5D));
				             }
				             if (!player.isNoGravity()) {	            
				            	 player.setDeltaMovement(player.getDeltaMovement().add(0.0D, -d0 / 4.0D, 0.0D));		        
				             }
				     
				             Vector3d vector3d4 = player.getDeltaMovement();		        
				             if (player.horizontalCollision && player.isFree(vector3d4.x, vector3d4.y + (double)0.6F - player.getY() + d7, vector3d4.z)) {		           
				            	 player.setDeltaMovement(vector3d4.x, (double)0.3F, vector3d4.z);	           
				             }
		            	 }
	            	 }    
	             } else {
		             entity.moveRelative(0.02F, p_213352_1_);
		             entity.move(MoverType.SELF, entity.getDeltaMovement());
		             if (entity.getFluidHeight(FluidTags.LAVA) <= entity.getFluidJumpThreshold()) {
		                entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.5D, (double)0.8F, 0.5D));
		                Vector3d vector3d3 = entity.getFluidFallingAdjustedMovement(d0, flag, entity.getDeltaMovement());
		                entity.setDeltaMovement(vector3d3);
		             } else {
		                entity.setDeltaMovement(entity.getDeltaMovement().scale(0.5D));
		             }
		             if (!entity.isNoGravity()) {	            
		            	 entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, -d0 / 4.0D, 0.0D));		        
		             }
		     
		             Vector3d vector3d4 = entity.getDeltaMovement();		        
		             if (entity.horizontalCollision && entity.isFree(vector3d4.x, vector3d4.y + (double)0.6F - entity.getY() + d7, vector3d4.z)) {		           
		            	 entity.setDeltaMovement(vector3d4.x, (double)0.3F, vector3d4.z);	           
		             }
	             }
	          } else if (entity.isFallFlying()) {
	             Vector3d vector3d = entity.getDeltaMovement();
	             if (vector3d.y > -0.5D) {
	                entity.fallDistance = 1.0F;
	             }

	             Vector3d vector3d1 = entity.getLookAngle();
	             float f = entity.xRot * ((float)Math.PI / 180F);
	             double d1 = Math.sqrt(vector3d1.x * vector3d1.x + vector3d1.z * vector3d1.z);
	             double d3 = Math.sqrt(getHorizontalDistanceSqr(vector3d));
	             double d4 = vector3d1.length();
	             float f1 = MathHelper.cos(f);
	             f1 = (float)((double)f1 * (double)f1 * Math.min(1.0D, d4 / 0.4D));
	             vector3d = entity.getDeltaMovement().add(0.0D, d0 * (-1.0D + (double)f1 * 0.75D), 0.0D);
	             if (vector3d.y < 0.0D && d1 > 0.0D) {
	                double d5 = vector3d.y * -0.1D * (double)f1;
	                vector3d = vector3d.add(vector3d1.x * d5 / d1, d5, vector3d1.z * d5 / d1);
	             }

	             if (f < 0.0F && d1 > 0.0D) {
	                double d9 = d3 * (double)(-MathHelper.sin(f)) * 0.04D;
	                vector3d = vector3d.add(-vector3d1.x * d9 / d1, d9 * 3.2D, -vector3d1.z * d9 / d1);
	             }

	             if (d1 > 0.0D) {
	                vector3d = vector3d.add((vector3d1.x / d1 * d3 - vector3d.x) * 0.1D, 0.0D, (vector3d1.z / d1 * d3 - vector3d.z) * 0.1D);
	             }

	             entity.setDeltaMovement(vector3d.multiply((double)0.99F, (double)0.98F, (double)0.99F));
	             entity.move(MoverType.SELF, entity.getDeltaMovement());
	             if (entity.horizontalCollision && !entity.level.isClientSide) {
	                double d10 = Math.sqrt(getHorizontalDistanceSqr(entity.getDeltaMovement()));
	                double d6 = d3 - d10;
	                float f2 = (float)(d6 * 10.0D - 3.0D);
	                if (f2 > 0.0F) {
	                   entity.playSound(entity.getFallDamageSound((int)f2), 1.0F, 1.0F);
	                   entity.hurt(DamageSource.FLY_INTO_WALL, f2);
	                }
	             }

	             if (entity.onGround && !entity.level.isClientSide) {
	                entity.setSharedFlag(7, false);
	             }
	          } else {
	             BlockPos blockpos = entity.getBlockPosBelowThatAffectsMyMovement();
	             float f3 = entity.level.getBlockState(entity.getBlockPosBelowThatAffectsMyMovement()).getSlipperiness(level, entity.getBlockPosBelowThatAffectsMyMovement(), entity);
	             float f4 = entity.onGround ? f3 * 0.91F : 0.91F;
	             Vector3d vector3d5 = entity.handleRelativeFrictionAndCalculateMovement(p_213352_1_, f3);
	             double d2 = vector3d5.y;
	             if (entity.hasEffect(Effects.LEVITATION)) {
	                d2 += (0.05D * (double)(entity.getEffect(Effects.LEVITATION).getAmplifier() + 1) - vector3d5.y) * 0.2D;
	                entity.fallDistance = 0.0F;
	             } else if (entity.level.isClientSide && !entity.level.hasChunkAt(blockpos)) {
	                if (entity.getY() > 0.0D) {
	                   d2 = -0.1D;
	                } else {
	                   d2 = 0.0D;
	                }
	             } else if (!entity.isNoGravity()) {
	                d2 -= d0;
	             }

	             entity.setDeltaMovement(vector3d5.x * (double)f4, d2 * (double)0.98F, vector3d5.z * (double)f4);
	          }
	       }

	       entity.calculateEntityAnimation(entity, entity instanceof IFlyingAnimal);
	}*/
	
  /*  @ModifyConstant(method = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/vector/Vector3d;)V", constant = {@Constant(doubleValue = 0.5D, ordinal = 0), @Constant(doubleValue = 0.5D, ordinal = 1), @Constant(doubleValue = 0.5D, ordinal = 2)})
    private double modifyLavaSpeed(double original) {
    	LivingEntity entity = (LivingEntity) (Object) this;
    	FluidState fluidstate = entity.level.getFluidState(entity.blockPosition());
    	if (entity instanceof PlayerEntity) {
    		PlayerEntity player = (PlayerEntity) entity;
        	if (player.isInLava() && player.isAffectedByFluids() && !player.canStandOnFluid(fluidstate.getType())) {
            	if (IUtilityHelper.isFullArmorSet(player, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())) {
            		original = 0.5D + 0.025D;
            		if (player.isSprinting()) {
            			original = 0.5D + 0.025D;
            			player.setPose(Pose.SWIMMING);
            		}
            		return original;
            	}
        	}
    	}
       return 0.5D;
    }*/
	
/*	@SuppressWarnings("unused")
	@Inject(method = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/vector/Vector3d;)V", at = @At("INVOKE"), cancellable = true)
	public void chaosawakens$travel(Vector3d v, CallbackInfo info) {
		LivingEntity entity = (LivingEntity) (Object) this;
		FluidState fluidstate = this.level.getFluidState(entity.blockPosition());
		boolean isInAirOrWorld = this.getDeltaMovement().y <= 0.0D;
		double d0 = 0.08D;
		ModifiableAttributeInstance gravity = entity.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
		d0 = gravity.getValue();
		
		if (entity.isInLava() && entity.isAffectedByFluids() && !entity.canStandOnFluid(fluidstate.getType())) {
			if (entity instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) entity;
				if (IUtilityHelper.isFullArmorSet(player, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())) {		         				
		            double d7 = player.getY();
		            if (player.getFluidHeight(FluidTags.LAVA) <= player.getFluidJumpThreshold()) {
		            	player.setDeltaMovement(player.getDeltaMovement().multiply(0.025D, 0, 0.025D));
		               Vector3d vector3d3 = player.getFluidFallingAdjustedMovement(d0, isInAirOrWorld, player.getDeltaMovement());
		               player.setDeltaMovement(vector3d3);
		            } else {
		            	player.setDeltaMovement(player.getDeltaMovement().scale(0.025D));
			               if (player.isSprinting()) {
			            	   player.setPose(Pose.SWIMMING);
			               }
		            }
					
					/*			double d8 = player.getY();			         
					float f5 = player.isSprinting() ? 0.9F : 0.8F;
					float f6 = 0.02F;			          
					float f7 = (float)EnchantmentHelper.getDepthStrider(player);			          
					if (f7 > 3.0F) {			         
						f7 = 3.0F;			         
					}			        
					if (!player.onGround) {			         
						f7 *= 0.5F;			         
					}			          
					if (f7 > 0.0F) {			        			     			           
						f5 += (0.54600006F - f5) * f7 / 3.0F;			           
						f6 += (player.getSpeed() - f6) * f7 / 3.0F;			           
					}           
					f6 *= (float)player.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();			        					
					player.moveRelative(f6, v);			          
					player.move(MoverType.SELF, player.getDeltaMovement());	          
					Vector3d vector3d6 = player.getDeltaMovement();			         
					if (player.horizontalCollision && player.onClimbable()) {
						vector3d6 = new Vector3d(vector3d6.x, 0.2D, vector3d6.z);			          
					}			         
					player.setDeltaMovement(vector3d6.multiply((double)f5, (double)0.8F, (double)f5));			         
					Vector3d vector3d2 = player.getFluidFallingAdjustedMovement(d0, isInAirOrWorld, player.getDeltaMovement());			         
					player.setDeltaMovement(vector3d2);			          
					if (player.horizontalCollision && player.isFree(vector3d2.x, vector3d2.y + (double)0.6F - player.getY() + d8, vector3d2.z)) {			         
						player.setDeltaMovement(vector3d2.x, (double)0.3F, vector3d2.z);	         
					}						
					if (player.isSprinting()) {
						if (player.getPose() != Pose.SWIMMING) {
							player.setPose(Pose.SWIMMING);
						}
					}
				}
			}
		}
	}*/
}