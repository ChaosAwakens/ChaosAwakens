package io.github.chaosawakens.common.integration.top.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.IEntityStyle;
import mcjty.theoneprobe.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static mcjty.theoneprobe.apiimpl.client.ElementEntityRender.fixEntityId;
import static mcjty.theoneprobe.rendering.RenderHelper.rot;

public class AnimMetadataElementRenderer { //TODO I'm sorry
    private static final Object2ObjectLinkedOpenHashMap<UUID, Entity> CACHED_ENTITIES = new Object2ObjectLinkedOpenHashMap<>();

    public static void renderEntity(Entity entity, MatrixStack matrixStack, int xPos, int yPos, float scale) {
        matrixStack.pushPose();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableColorMaterial();
        matrixStack.translate((double)(xPos + 8), (double)(yPos + 24), 50.0);
        matrixStack.scale(-scale, scale, scale);
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(135.0F));
        net.minecraft.client.renderer.RenderHelper.turnBackOn();
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(-135.0F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(rot));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(0.0F));
        if (!(entity instanceof PlayerEntity)) {
            entity.xRot = 0.0F;
            entity.xRotO = 0.0F;
            entity.yRot = 0.0F;
            entity.yRotO = 0.0F;
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                livingEntity.yBodyRotO = 0.0F;
                livingEntity.yBodyRot = 0.0F;
                livingEntity.yHeadRot = 0.0F;
                livingEntity.yHeadRotO = 0.0F;
            }
        }

        matrixStack.translate(0.0, (double)((float)entity.getMyRidingOffset() + (entity instanceof HangingEntity ? 0.5F : 0.0F)), 0.0);
        EntityRendererManager dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();

        try {
            IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
            dispatcher.setRenderShadow(false);
            dispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, Minecraft.getInstance().getFrameTime(), matrixStack, buffer, 15728880);
            buffer.endBatch();
        } catch (Exception var7) {
            TheOneProbe.logger.error("Error rendering entity!", var7);
        }

        dispatcher.setRenderShadow(true);
        net.minecraft.client.renderer.RenderHelper.turnOff();
        RenderSystem.disableRescaleNormal();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableLighting();
        RenderSystem.enableDepthTest();
        RenderSystem.disableColorMaterial();
        Minecraft.getInstance().gameRenderer.lightTexture().turnOffLightLayer();
        matrixStack.popPose();
    }

    private static void renderEntity(IEntityStyle style, MatrixStack matrixStack, int x, int y, Entity entity) {
        float height = entity.getBbHeight();
        height = (float)((double)(height - 1.0F) * 0.7 + 1.0);
        float s = style.getScale() * ((float)style.getHeight() * 14.0F / 25.0F / height);
        renderEntity(entity, matrixStack, x, y, s);
    }

    public static void render(String entityName, CompoundNBT entityNBT, IEntityStyle style, MatrixStack matrixStack, int x, int y, List<String> mappedAnimsByRegex, List<String> stoppedAnimsByRegex, int clampedTickCount) {
        if (entityName != null && !entityName.isEmpty() && !CACHED_ENTITIES.containsKey(entityNBT.getUUID("UUID"))) {
            String fixed = fixEntityId(entityName);
            ResourceLocation id = new ResourceLocation(fixed);
            if (!Config.isBlacklistForRendering(id)) {
                Entity entity;
                EntityType value;
                if (entityNBT != null) {
                    value = ForgeRegistries.ENTITIES.getValue(id);
                    if (value != null) {
                        try {
                            World world = Minecraft.getInstance().level;
                            entity = value.create(world);
                            entity.moveTo(0.5, 0.0, 0.5, MathHelper.wrapDegrees(world.random.nextFloat() * 360.0F), 0.0F);
                            if (entity instanceof MobEntity) {
                                MobEntity mob = (MobEntity)entity;
                                mob.yHeadRot = mob.yRot;
                                mob.yBodyRot = mob.yRot;
                                mob.setLeftHanded(world.random.nextFloat() < 0.05F);
                                mob.load(entityNBT);
                                CACHED_ENTITIES.put(entityNBT.getUUID("UUID"), mob);
                            } else {
                                entity.load(entityNBT);
                                CACHED_ENTITIES.put(entityNBT.getUUID("UUID"), entity);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }

        Entity finalEntity = CACHED_ENTITIES.get(entityNBT.getUUID("UUID"));

        if (finalEntity instanceof IAnimatableEntity) {
            IAnimatableEntity animatable = (IAnimatableEntity) finalEntity;

            finalEntity.load(entityNBT);
            renderEntity(style, matrixStack, x, y, finalEntity);

            AtomicBoolean hasPlayed = new AtomicBoolean(false);

            animatable.getWrappedControllers().forEach(controller -> {
                mappedAnimsByRegex.forEach(mappedAnim -> {
                    String controllerName = mappedAnim.split("\\|")[0];

                    if (controller.getName().equals(controllerName)) {
                        String animationName = mappedAnim.split("\\|")[1];
                        double animSpeed = Double.parseDouble(mappedAnim.split("\\|")[2]);

                        if (!animationName.equalsIgnoreCase("None")) {
                            animatable.playAnimation(animatable.getCachedAnimationByName(animationName).setAnimSpeed(animSpeed * 0.201D), true);
                            hasPlayed.set(true);
                        } else hasPlayed.set(false);

                        if (!hasPlayed.get()) controller.stopAnimation(null);
                    }
                });
            });

            finalEntity.tickCount++;
        }
    }
}
