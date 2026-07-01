package io.github.chaosawakens.mixins.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.chaosawakens.content.item.equipment.armor.LavaEelArmorItem;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

// TODO Make the access widener work for FogData and MobEffectFogFunction

//    @WrapMethod(method = "setupFog")
//    public static void chaosawakens$setupFog(Camera camera, FogRenderer.FogMode fogMode, float farPlaneDistance, boolean p_234176_, float p_234177_, Operation<Void> original) {
//        if (camera.getEntity() instanceof Player) {
//            Player player = (Player) camera.getEntity();
//            boolean isFullLavaEelSet = true;
//            for (ItemStack stack : player.getArmorSlots()) {
//                isFullLavaEelSet = isFullLavaEelSet && stack.getItem() instanceof LavaEelArmorItem;
//            }
//            if (isFullLavaEelSet) {
//                FogType fogtype = camera.getFluidInCamera();
//                Entity entity = camera.getEntity();
//                FogRenderer.FogData fogrenderer$fogdata = new FogRenderer.FogData(fogMode);
//                FogRenderer.MobEffectFogFunction fogrenderer$mobeffectfogfunction = getPriorityFogFunction(entity, p_234177_);
//                if (fogtype == FogType.LAVA) {
//                    if (entity.isSpectator()) {
//                        fogrenderer$fogdata.start = -8.0F;
//                        fogrenderer$fogdata.end = farPlaneDistance * 0.5F;
//                    } else if (entity instanceof LivingEntity && ((LivingEntity)entity).hasEffect(MobEffects.FIRE_RESISTANCE)) {
//                        fogrenderer$fogdata.start = 0.0F;
//                        fogrenderer$fogdata.end = 3.0F;
//                    } else {
//                        fogrenderer$fogdata.start = 0.25F;
//                        fogrenderer$fogdata.end = 1.0F;
//                    }
//                } else if (fogtype == FogType.POWDER_SNOW) {
//                    if (entity.isSpectator()) {
//                        fogrenderer$fogdata.start = -8.0F;
//                        fogrenderer$fogdata.end = farPlaneDistance * 0.5F;
//                    } else {
//                        fogrenderer$fogdata.start = 0.0F;
//                        fogrenderer$fogdata.end = 2.0F;
//                    }
//                } else if (fogrenderer$mobeffectfogfunction != null) {
//                    LivingEntity livingentity = (LivingEntity)entity;
//                    MobEffectInstance mobeffectinstance = livingentity.getEffect(fogrenderer$mobeffectfogfunction.getMobEffect());
//                    if (mobeffectinstance != null) {
//                        fogrenderer$mobeffectfogfunction.setupFog(fogrenderer$fogdata, livingentity, mobeffectinstance, farPlaneDistance, p_234177_);
//                    }
//                } else if (fogtype == FogType.WATER) {
//                    fogrenderer$fogdata.start = -8.0F;
//                    fogrenderer$fogdata.end = 96.0F;
//                    if (entity instanceof LocalPlayer) {
//                        LocalPlayer localplayer = (LocalPlayer)entity;
//                        fogrenderer$fogdata.end *= Math.max(0.25F, localplayer.getWaterVision());
//                        Holder<Biome> holder = localplayer.level().getBiome(localplayer.blockPosition());
//                        if (holder.is(BiomeTags.HAS_CLOSER_WATER_FOG)) {
//                            fogrenderer$fogdata.end *= 0.85F;
//                        }
//                    }
//
//                    if (fogrenderer$fogdata.end > farPlaneDistance) {
//                        fogrenderer$fogdata.end = farPlaneDistance;
//                        fogrenderer$fogdata.shape = FogShape.CYLINDER;
//                    }
//                } else if (p_234176_) {
//                    fogrenderer$fogdata.start = farPlaneDistance * 0.05F;
//                    fogrenderer$fogdata.end = Math.min(farPlaneDistance, 192.0F) * 0.5F;
//                } else if (fogMode == FogRenderer.FogMode.FOG_SKY) {
//                    fogrenderer$fogdata.start = 0.0F;
//                    fogrenderer$fogdata.end = farPlaneDistance;
//                    fogrenderer$fogdata.shape = FogShape.CYLINDER;
//                } else {
//                    float f = Mth.clamp(farPlaneDistance / 10.0F, 4.0F, 64.0F);
//                    fogrenderer$fogdata.start = farPlaneDistance - f;
//                    fogrenderer$fogdata.end = farPlaneDistance;
//                    fogrenderer$fogdata.shape = FogShape.CYLINDER;
//                }
//
//                RenderSystem.setShaderFogStart(fogrenderer$fogdata.start);
//                RenderSystem.setShaderFogEnd(fogrenderer$fogdata.end);
//                RenderSystem.setShaderFogShape(fogrenderer$fogdata.shape);
//            } else {
//                original.call(camera, fogMode, farPlaneDistance, p_234176_, p_234177_);
//            }
//        } else {
//            original.call(camera, fogMode, farPlaneDistance, p_234176_, p_234177_);
//        }
//    }
}
