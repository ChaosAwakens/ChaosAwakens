package io.github.chaosawakens.mixins;

import io.github.chaosawakens.common.registry.CAEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow public abstract boolean hasEffect(Effect effect);

	@Inject(method = "isImmobile", at = @At("HEAD"), cancellable = true)
	protected void chaosawakens$isImmobile(CallbackInfoReturnable<Boolean> cir) {
		if (this.hasEffect(CAEffects.PARALYSIS_EFFECT.get())) {
			cir.setReturnValue(true);
		}
	}
}
