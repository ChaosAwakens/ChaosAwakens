package io.github.chaosawakens.mixins.entity;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.chaosawakens.content.item.equipment.armor.LavaEelArmorItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    protected boolean isAffectedByFluids() {
        return true;
    }

    @WrapMethod(method = "travel")
    public void chaosawakens$travel(Vec3 travelVector, Operation<Void> original) {
        LivingEntity entity = (LivingEntity) (Object) this;
        boolean isFullLavaEelSet = true;
        for (ItemStack stack : entity.getArmorSlots()) {
            isFullLavaEelSet = isFullLavaEelSet && stack.getItem() instanceof LavaEelArmorItem;
        }
        FluidState fluidstate = entity.level().getFluidState(entity.blockPosition());
        if (isFullLavaEelSet && entity.isInLava() && this.isAffectedByFluids() && !entity.canStandOnFluid(fluidstate)) {
            double d0 = 0.08D;
            boolean flag = entity.getDeltaMovement().y <= 0.0D;
            if (flag && entity.hasEffect(MobEffects.SLOW_FALLING)) {
                d0 = 0.01D;
            }

            double d9 = entity.getY();
            double f4 = entity.isSprinting() ? 0.9 : 0.5;
            float f5 = 0.02F;

            entity.moveRelative(f5, travelVector);
            entity.move(MoverType.SELF, entity.getDeltaMovement());
            Vec3 vec36 = entity.getDeltaMovement();
            if (entity.horizontalCollision && entity.onClimbable()) {
                vec36 = new Vec3(vec36.x, 0.2D, vec36.z);
            }

            entity.setDeltaMovement(vec36.multiply(f4, 0.8, f4));
            Vec3 vec32 = entity.getFluidFallingAdjustedMovement(d0, flag, entity.getDeltaMovement());
            entity.setDeltaMovement(vec32);
            if (entity.horizontalCollision && entity.isFree(vec32.x, vec32.y + (double)0.6F - entity.getY() + d9, vec32.z)) {
                entity.setDeltaMovement(vec32.x, (double)0.3F, vec32.z);
            }
        } else {
            original.call(travelVector);
        }
    }
}
