package io.github.chaosawakens.mixins;

import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    private ClientPlayerEntityMixin() {
        throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
    }

 /*   @Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
    private void chaosawakens$aiStep(CallbackInfo ci) {
        ClientPlayerEntity targetClientPlayer = (ClientPlayerEntity) (Object) this;
        boolean preSprintCheck = targetClientPlayer.getFoodData().getFoodLevel() > 6 || targetClientPlayer.abilities.mayfly;
        boolean isUnderLava = targetClientPlayer.isEyeInFluid(FluidTags.LAVA);

        if (EntityUtil.isFullArmorSet(targetClientPlayer, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())) {
            if (targetClientPlayer.isInLava() && targetClientPlayer.input.shiftKeyDown && targetClientPlayer.isAffectedByFluids()) targetClientPlayer.setDeltaMovement(targetClientPlayer.getDeltaMovement().add(0.0D, -0.04D * targetClientPlayer.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue(), 0.0D));
            if (!targetClientPlayer.isSprinting() && (!targetClientPlayer.isInLava() || isUnderLava) && (isUnderLava ? targetClientPlayer.input.hasForwardImpulse() : targetClientPlayer.input.forwardImpulse >= 0.8D) && preSprintCheck && !targetClientPlayer.isUsingItem() && !targetClientPlayer.hasEffect(Effects.BLINDNESS) && Minecraft.getInstance().options.keySprint.isDown()) targetClientPlayer.setSprinting(true);
        }
    }

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/player/ClientPlayerEntity;isInWater()Z", ordinal = 2), cancellable = true)
    private void chaosawakens$aiStep2(CallbackInfo ci) {
        ClientPlayerEntity targetClientPlayer = (ClientPlayerEntity) (Object) this;

        if (EntityUtil.isFullArmorSet(targetClientPlayer, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get()) && (!targetClientPlayer.isInLava() || targetClientPlayer.isEyeInFluid(FluidTags.LAVA)) && !targetClientPlayer.isSprinting()) {
            targetClientPlayer.setSprinting(true);
            ci.cancel();
        }
    }*/
}