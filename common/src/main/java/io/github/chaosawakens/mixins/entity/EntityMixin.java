package io.github.chaosawakens.mixins.entity;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.item.equipment.armor.LavaEelArmorItem;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.PriorityQueue;
import java.util.Queue;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    private BlockPos blockPosition;

    @Shadow
    protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;

    @WrapMethod(method = "updateSwimming")
    public void chaosawakens$updateSwimming(Operation<Void> original) {
        Entity entity = (Entity) (Object) this;

        if (entity instanceof LivingEntity) {
            boolean isFullLavaEelSet = true;
            for (ItemStack stack : ((LivingEntity) entity).getArmorSlots()) {
                isFullLavaEelSet = isFullLavaEelSet && stack.getItem() instanceof LavaEelArmorItem;
            }
            if (isFullLavaEelSet && entity.isInLava()) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 310, 1));
                if (entity.isSwimming()) {
                    entity.setSwimming(entity.isSprinting() && !entity.isPassenger());
                } else {
                    entity.setSwimming(entity.isSprinting() && entity.isEyeInFluid(FluidTags.LAVA) && !entity.isPassenger() && entity.level().getFluidState(this.blockPosition).is(FluidTags.LAVA));
                }
            } else {
                original.call();
            }
        } else {
            original.call();
        }
    }
}
