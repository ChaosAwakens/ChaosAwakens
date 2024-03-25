package io.github.chaosawakens.mixins;

import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.util.EntityUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	private LivingEntityMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}

	@Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isInLava()Z"), cancellable = true)
	private void chaosawakens$travel(Vector3d travelVec, CallbackInfo ci) {
		LivingEntity target = (LivingEntity) (Object) this;
		FluidState curFluidState = target.level.getFluidState(target.blockPosition());

		if (target.isEffectiveAi() || target.isControlledByLocalInstance()) {
			if (target.isInLava() && target.isAffectedByFluids() && !target.canStandOnFluid(curFluidState.getType()) && target instanceof PlayerEntity && EntityUtil.isFullArmorSet((PlayerEntity) target, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())) {
				double defaultGravity = target.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).getValue();
				boolean isFalling = target.getDeltaMovement().y < 0.0D;
				double curY = target.getY();
				float swimSpeedMod = target.isSprinting() ? 0.9F : 0.8F; //TODO I love decompiler artifacts (actually adjust variable types (L a z y))
				float baseSwimSpeed = 0.02F;
				float modifiedDepthStriderLevel = EnchantmentHelper.getDepthStrider(target);

				if (modifiedDepthStriderLevel > 3.0F) modifiedDepthStriderLevel = 3.0F;
				if (!target.isOnGround()) modifiedDepthStriderLevel *= 0.5F;

				if (modifiedDepthStriderLevel > 0.0F) {
					swimSpeedMod += (0.54600006F - baseSwimSpeed) * modifiedDepthStriderLevel / 3.0F;
					baseSwimSpeed *= (target.getSpeed() - baseSwimSpeed) * modifiedDepthStriderLevel / 3.0F;
				}

				baseSwimSpeed *= (float) target.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue();

				target.moveRelative(baseSwimSpeed, travelVec);
				target.move(MoverType.SELF, target.getDeltaMovement());

				Vector3d modifiedDeltaMovement = target.getDeltaMovement();

				if (target.horizontalCollision && target.onClimbable()) modifiedDeltaMovement = new Vector3d(modifiedDeltaMovement.x, 0.2D, modifiedDeltaMovement.z);

				target.setDeltaMovement(modifiedDeltaMovement.multiply(swimSpeedMod, 0.8D, swimSpeedMod));

				Vector3d postAdjustedMovement = target.getFluidFallingAdjustedMovement(defaultGravity, isFalling, target.getDeltaMovement());

				target.setDeltaMovement(postAdjustedMovement);

				if (target.horizontalCollision && target.isFree(postAdjustedMovement.x, postAdjustedMovement.y + 0.6D - curY, postAdjustedMovement.z)) target.setDeltaMovement(postAdjustedMovement.x, 0.3D, postAdjustedMovement.z);

				ci.cancel();
			}
		}
	}
}