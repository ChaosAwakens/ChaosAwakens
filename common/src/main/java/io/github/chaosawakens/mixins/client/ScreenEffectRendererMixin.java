package io.github.chaosawakens.mixins.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.chaosawakens.content.item.equipment.armor.LavaEelArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {

    @WrapMethod(method = "renderScreenEffect")
    private static void chaosawakens$renderScreenEffect(Minecraft minecraft, PoseStack poseStack, Operation<Void> original) {
        boolean isFullLavaEelSet = true;
        for (ItemStack stack : minecraft.player.getArmorSlots()) {
            isFullLavaEelSet = isFullLavaEelSet && stack.getItem() instanceof LavaEelArmorItem;
        }
        if (!minecraft.player.isOnFire() || !isFullLavaEelSet) {
            original.call(minecraft, poseStack);
        }
    }
}
