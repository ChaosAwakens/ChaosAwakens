package io.github.chaosawakens.mixins;

import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    private EntityMixin() {
        throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
    }

    @Inject(method = "updateSwimming", at = @At("HEAD"), cancellable = true)
    private void chaosawakens$updateSwimming(CallbackInfo ci) {
        Entity target = (Entity) (Object) this;

        if (target instanceof PlayerEntity) {
            PlayerEntity playerTarget = (PlayerEntity) target;

            if (EntityUtil.isFullArmorSet(playerTarget, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get()) && (playerTarget.isInLava() || playerTarget.isEyeInFluid(FluidTags.LAVA))) {
            }
        }
    }
}
