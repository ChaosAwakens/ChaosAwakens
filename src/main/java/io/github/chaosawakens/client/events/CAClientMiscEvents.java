package io.github.chaosawakens.client.events;

import com.google.common.collect.Lists;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.manager.CAConfigManager;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.ClientLanguageMap;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Objects;

public class CAClientMiscEvents {

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onCameraSetupEvent(EntityViewRenderEvent.CameraSetup event) { // Credits to BobMowzie for screen shake camera math
		PlayerEntity clientPlayer = Minecraft.getInstance().player;
		float defaultCamYaw = clientPlayer.getYHeadRot();

		if (CAConfigManager.MAIN_CLIENT.enableVFXEffects.get()) {
			if (CAConfigManager.MAIN_CLIENT.enableCameraShake.get()) {
				float amp = 0F;

				for (CAScreenShakeEntity shaker : EntityUtil.getEntitiesAroundNoPredicate(clientPlayer, CAScreenShakeEntity.class, 20, 20, 20, 20)) {
					if (shaker.distanceTo(clientPlayer) < shaker.getRadius()) amp += shaker.getAmp(clientPlayer, Minecraft.getInstance().getFrameTime());
				}

				if (amp > 1.0F) amp = 1.0F;

				if (!Minecraft.getInstance().isPaused()) {
					// Note to self: Don't use .random, and don't EVER leave this inside a loop. Seriously. -- Meme Man
					event.setPitch((float) (event.getPitch() + amp * Math.cos(Minecraft.getInstance().getFrameTime() * 3 + 2) * 25));
					event.setYaw((float) (event.getYaw() + amp * Math.cos(Minecraft.getInstance().getFrameTime() * 5 + 1) * 25));
					event.setRoll((float) (event.getRoll() + amp * Math.cos(Minecraft.getInstance().getFrameTime() * 4) * 25));
				}

				/*AnimatableMonsterEntity selectedEntity = null;

				for (AnimatableMonsterEntity potentialSelectedEntity : EntityUtil.getEntitiesAroundNoPredicate(clientPlayer, AnimatableMonsterEntity.class, 10, 10, 10, 10)) {
					if (potentialSelectedEntity == null || !potentialSelectedEntity.isAlive()) continue;

					if (selectedEntity != null) {
						if (MathUtil.getDistanceBetween(clientPlayer, potentialSelectedEntity) < MathUtil.getDistanceBetween(clientPlayer, selectedEntity)) selectedEntity = potentialSelectedEntity;
					} else selectedEntity = potentialSelectedEntity;
				}

				if (selectedEntity != null) {
					final AnimatableMonsterEntity finalizedSelectedEntity = selectedEntity;

					if (finalizedSelectedEntity instanceof RoboPounderEntity) {
						RoboPounderEntity selectedPounder = (RoboPounderEntity) finalizedSelectedEntity;
						double leftPunchAnimProgress = selectedPounder.getCachedAnimationByName("Left Heavy Attack").getWrappedAnimProgress();
						double rightPunchAnimProgress = selectedPounder.getCachedAnimationByName("Right Heavy Attack").getWrappedAnimProgress();
						double leftSweepAnimProgress = selectedPounder.getCachedAnimationByName("Left Swing Attack").getWrappedAnimProgress();
						double rightSweepAnimProgress = selectedPounder.getCachedAnimationByName("Right Swing Attack").getWrappedAnimProgress();

						if (selectedPounder.getAttackID() == RoboPounderEntity.PUNCH_ATTACK_ID) {
							if (MathUtil.isBetween(rightPunchAnimProgress, 1.0D, 14.6D)) event.setRoll(16);
							if (MathUtil.isBetween(rightPunchAnimProgress, 15.0D, 22.6D)) event.setRoll(-37);
							if (MathUtil.isBetween(leftPunchAnimProgress, 1.0D, 14.6D)) event.setRoll(-16);
							if (MathUtil.isBetween(leftPunchAnimProgress, 15.0D, 22.6D)) event.setRoll(37);
						}

						if (selectedPounder.getAttackID() == RoboPounderEntity.SWING_ATTACK_ID) {
							if (MathUtil.isBetween(rightSweepAnimProgress, 1.0D, 15.6D)) {
								event.setYaw(event.getYaw() + 17.5F);
								event.setRoll(6);
							}
							if (MathUtil.isBetween(rightSweepAnimProgress, 16.0D, 25.6D)) {
								event.setYaw(event.getYaw() - 30F);
								event.setRoll(-15);
							}
							if (MathUtil.isBetween(leftSweepAnimProgress, 1.0D, 15.6D)) {
								event.setYaw(event.getYaw() - 17.5F);
								event.setRoll(-6);
							}
							if (MathUtil.isBetween(leftSweepAnimProgress, 16.0D, 25.6D)) {
								event.setYaw(event.getYaw() + 30F);
								event.setRoll(15);
							}
						}
					}
				}*/
			}
		}
	}

	@SubscribeEvent
	public static void onFOVUpdateEvent(FOVUpdateEvent event) { //TODO Un-hardcode
		PlayerEntity clientPlayer = Minecraft.getInstance().player;

		if (CAConfigManager.MAIN_CLIENT.enableVFXEffects.get()) {
			if (CAConfigManager.MAIN_CLIENT.enableCameraZoom.get()) {
				float curFOV = event.getFov();
				/*AnimatableMonsterEntity selectedEntity = null;

				for (AnimatableMonsterEntity potentialSelectedEntity : EntityUtil.getEntitiesAroundNoPredicate(clientPlayer, AnimatableMonsterEntity.class, 15, 15, 15, 15)) {
					if (potentialSelectedEntity == null || !potentialSelectedEntity.isAlive()) continue;

					if (selectedEntity != null) {
						if (MathUtil.getDistanceBetween(clientPlayer, potentialSelectedEntity) < MathUtil.getDistanceBetween(clientPlayer, selectedEntity)) selectedEntity = potentialSelectedEntity;
					} else selectedEntity = potentialSelectedEntity;
				}

				if (selectedEntity != null) {
					final AnimatableMonsterEntity finalizedSelectedEntity = selectedEntity;

					if (finalizedSelectedEntity instanceof RoboPounderEntity) {
						RoboPounderEntity selectedPounder = (RoboPounderEntity) finalizedSelectedEntity;
						double leftPunchAnimProgress = selectedPounder.getCachedAnimationByName("Left Heavy Attack").getWrappedAnimProgress();
						double rightPunchAnimProgress = selectedPounder.getCachedAnimationByName("Right Heavy Attack").getWrappedAnimProgress();
						double leftSweepAnimProgress = selectedPounder.getCachedAnimationByName("Left Swing Attack").getWrappedAnimProgress();
						double rightSweepAnimProgress = selectedPounder.getCachedAnimationByName("Right Swing Attack").getWrappedAnimProgress();

						if (selectedPounder.getAttackID() == RoboPounderEntity.PUNCH_ATTACK_ID) {
							if (MathUtil.isBetween(leftPunchAnimProgress, 1.0D, 14.6D) || MathUtil.isBetween(rightPunchAnimProgress, 1.0D, 14.6D)) event.setNewfov(curFOV * 0.35F);
							else if (leftPunchAnimProgress > 0 || rightPunchAnimProgress > 0) event.setNewfov(curFOV * 3.2526103764F); // Guess the reference +1 :trollhappy:
						}

						if (selectedPounder.getAttackID() == RoboPounderEntity.SWING_ATTACK_ID) {
							if (MathUtil.isBetween(leftSweepAnimProgress, 1.0D, 15.6D) || MathUtil.isBetween(rightSweepAnimProgress, 1.0D, 12.6D)) event.setNewfov(curFOV * 0.75F);
							else if (leftSweepAnimProgress > 0 || rightSweepAnimProgress > 0) event.setNewfov(curFOV * 1.75F);
						}
					}
				}*/
			}
		}
	}

	@SubscribeEvent
	public static void onItemToolTipEvent(ItemTooltipEvent event) {
		if (event.getPlayer() == null) return;
		if (CAConfigManager.MAIN_CLIENT.enableTooltips.get()) {
			final ItemStack targetStack = event.getItemStack();
			final Item targetEntity = targetStack.getItem();

			if (CAConfigManager.MAIN_CLIENT.enableDamageTooltips.get()) {
				if (targetEntity.getDefaultInstance().getMaxDamage() > 0) {
					event.getToolTip().add(new StringTextComponent("Durability: ").withStyle(TextFormatting.DARK_GREEN).append(new StringTextComponent((targetStack.getMaxDamage() - targetStack.getDamageValue()) + "/" + targetStack.getMaxDamage()).withStyle(TextFormatting.YELLOW)));
				}
			}

			if (Objects.requireNonNull(targetEntity.getRegistryName()).getNamespace().equals(ChaosAwakens.MODID)) {
				if (ClientLanguageMap.getInstance().has("tooltip." + ChaosAwakens.MODID + "." + targetEntity.getRegistryName().getPath())) {
					if (Screen.hasShiftDown() || Screen.hasControlDown()) {
						event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens." + targetEntity.getRegistryName().getPath()).withStyle(CAConfigManager.MAIN_CLIENT.toolTipColor.get()));
					} else {
						event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens.default").withStyle(CAConfigManager.MAIN_CLIENT.toolTipColor.get()));
					}
				}

				for (int n = 2; n <= 15; n++) {
					if (ClientLanguageMap.getInstance().has("tooltip." + ChaosAwakens.MODID + "." + targetEntity.getRegistryName().getPath() + "." + n)) {
						if (Screen.hasShiftDown() || Screen.hasControlDown()) {
							event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens." + targetEntity.getRegistryName().getPath() + "." + n).withStyle(CAConfigManager.MAIN_CLIENT.toolTipColor.get()));
						}
					}
				}
			} else {
				if (ClientLanguageMap.getInstance().has("tooltip." + ChaosAwakens.MODID + "." + targetEntity.getRegistryName().getNamespace() + "." + targetEntity.getRegistryName().getPath())) {
					if (Screen.hasShiftDown() || Screen.hasControlDown()) {
						event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens." + targetEntity.getRegistryName().getNamespace() + "." + targetEntity.getRegistryName().getPath()).withStyle(CAConfigManager.MAIN_CLIENT.toolTipColor.get()));
					} else {
						event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens.default").withStyle(CAConfigManager.MAIN_CLIENT.toolTipColor.get()));
					}
				}

				for (int n = 2; n <= 15; n++) {
					if (ClientLanguageMap.getInstance().has("tooltip." + ChaosAwakens.MODID + "." + targetEntity.getRegistryName().getNamespace() + "." + targetEntity.getRegistryName().getPath() + "." + n)) {
						if (Screen.hasShiftDown() || Screen.hasControlDown()) {
							event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens." + targetEntity.getRegistryName().getNamespace() + "." + targetEntity.getRegistryName().getPath() + "." + n).withStyle(CAConfigManager.MAIN_CLIENT.toolTipColor.get()));
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onRenderFogColorEvent(EntityViewRenderEvent.FogColors event) {
		Entity cameraEntity = event.getRenderer().getMainCamera().getEntity();

		if (cameraEntity == null) return;

		if (cameraEntity.level.dimension() == CADimensions.CRYSTAL_WORLD) {
			event.setRed(1);
			event.setGreen(1);
			event.setBlue(1);
		}
	}

	@SubscribeEvent
	public static void onRenderFogDensityEvent(EntityViewRenderEvent.FogDensity event) {
		Entity cameraEntity = event.getRenderer().getMainCamera().getEntity();

		if (cameraEntity == null) return;

		if (CAConfigManager.MAIN_CLIENT.enableCrystalDimensionFog.get()) {
			if (cameraEntity.level.dimension() == CADimensions.CRYSTAL_WORLD) {
				event.setDensity(Float.valueOf(CAConfigManager.MAIN_CLIENT.crystalDimensionFogDensity.get().toString()));
				event.setCanceled(true);
			}
		}

		if (CAConfigManager.MAIN_COMMON.enableLavaEelArmorSetBonus.get()) {
			if (cameraEntity instanceof PlayerEntity) {
				if (EntityUtil.isFullArmorSet((PlayerEntity) cameraEntity, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())) {
					if (cameraEntity.isEyeInFluid(FluidTags.LAVA)) {
						event.setDensity(Float.valueOf(CAConfigManager.MAIN_CLIENT.lavaEelSetLavaFogDensity.get().toString()));
						event.setCanceled(true);
					}
				}
			}
		}
	}

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onClientWorldTickEvent(TickEvent.WorldTickEvent event) {
		ClientPlayerEntity player = Minecraft.getInstance().player;

		if (player == null) return;

		if (CAConfigManager.MAIN_CLIENT.enableCrystalDimensionParticles.get()) {
			if (player.level.dimension() == CADimensions.CRYSTAL_WORLD) {
				IUtilityHelper.addParticles(player.level, ParticleTypes.WHITE_ASH, player.getX(), player.getEyeY(), player.getZ(), 50, 0.03, 50, CAConfigManager.MAIN_CLIENT.crystalDimensionParticleDensity.get());
			}
		}
	}

	@SubscribeEvent
	public static void onBlockOverlayEvent(RenderBlockOverlayEvent event) {
		if (CAConfigManager.MAIN_COMMON.enableLavaEelArmorSetBonus.get()) {
			PlayerEntity player = event.getPlayer();
			if (EntityUtil.isFullArmorSet(player, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())) {
				if (player.isEyeInFluid(FluidTags.LAVA) && player.hasEffect(Effects.FIRE_RESISTANCE) || player.isInLava() || player.isOnFire()) {
					event.getMatrixStack().translate(0, CAConfigManager.MAIN_CLIENT.lavaEelSetFireStackTranslation.get(), 0);
				}
			}
		}
	}

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onPreRenderHUDEvent(RenderGameOverlayEvent.Pre event) {
		ClientPlayerEntity targetPlayer = Minecraft.getInstance().player;

		if (targetPlayer != null) {
			if (targetPlayer.isPassenger() && targetPlayer.getVehicle() != null) {
				if (targetPlayer.getVehicle() instanceof AnimatableMonsterEntity && !((AnimatableMonsterEntity) targetPlayer.getVehicle()).shouldAllowDismount()) {
					if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT) event.setCanceled(true);
					if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) Minecraft.getInstance().gui.setOverlayMessage(new TranslationTextComponent(""), false);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onKeyInputEvent(InputEvent.KeyInputEvent event) {
		boolean validCanelableInput = Minecraft.getInstance().options.keyAttack.matches(event.getKey(), event.getScanCode()) || Minecraft.getInstance().options.keyJump.matches(event.getKey(), event.getScanCode())
				|| Minecraft.getInstance().options.keyShift.matches(event.getKey(), event.getScanCode()) || Minecraft.getInstance().options.keyUse.matches(event.getKey(), event.getScanCode())
				|| Minecraft.getInstance().options.keyPickItem.matches(event.getKey(), event.getScanCode()) || Minecraft.getInstance().options.keySprint.matches(event.getKey(), event.getScanCode())
				|| Minecraft.getInstance().options.keyUp.matches(event.getKey(), event.getScanCode()) || Minecraft.getInstance().options.keyDown.matches(event.getKey(), event.getScanCode())
				|| Minecraft.getInstance().options.keyLeft.matches(event.getKey(), event.getScanCode()) || Minecraft.getInstance().options.keyRight.matches(event.getKey(), event.getScanCode())
				|| Minecraft.getInstance().options.keyDrop.matches(event.getKey(), event.getScanCode()); //TODO Redo this but better (:tm:)
		ObjectArrayList<KeyBinding> validCancelableInputList = new ObjectArrayList<>();

		validCancelableInputList.addAll(Lists.newArrayList(Minecraft.getInstance().options.keyAttack, Minecraft.getInstance().options.keyJump, Minecraft.getInstance().options.keyShift, Minecraft.getInstance().options.keyUse, Minecraft.getInstance().options.keyPickItem, Minecraft.getInstance().options.keySprint, Minecraft.getInstance().options.keyUp, Minecraft.getInstance().options.keyDown, Minecraft.getInstance().options.keyLeft, Minecraft.getInstance().options.keyRight, Minecraft.getInstance().options.keyDrop));
		@Nullable
		PlayerEntity player = Minecraft.getInstance().player;
		boolean cancel = player != null && !player.isDeadOrDying() && player.hasEffect(CAEffects.PARALYSIS_EFFECT.get()) && validCanelableInput;

		if (cancel) validCancelableInputList.forEach(key -> key.setDown(false));
	}

	@SubscribeEvent
	public static void onClickInputEvent(InputEvent.ClickInputEvent event) {
		@Nullable
		PlayerEntity player = Minecraft.getInstance().player;

		if (event.isCancelable() && player != null && !player.isDeadOrDying() && player.hasEffect(CAEffects.PARALYSIS_EFFECT.get())) {
			event.setSwingHand(false);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onMouseScrollEvent(InputEvent.MouseScrollEvent event) {
		@Nullable
		PlayerEntity player = Minecraft.getInstance().player;

		if (event.isCancelable() && player != null && !player.isDeadOrDying() && player.hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
	}
}
