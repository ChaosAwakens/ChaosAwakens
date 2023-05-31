package io.github.chaosawakens.client.events;

import java.util.Objects;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.ClientLanguageMap;
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
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CAClientMiscEvents {

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onCameraSetupEvent(EntityViewRenderEvent.CameraSetup event) {
		PlayerEntity clientPlayer = Minecraft.getInstance().player;

		if (CAConfigManager.MAIN_CLIENT.enableCameraShake.get()) {
			float amp = 0F;

			for (CAScreenShakeEntity shaker : IUtilityHelper.getEntitiesAroundNoPredicate(clientPlayer, CAScreenShakeEntity.class, 20, 20, 20, 20)) {
				if (shaker.distanceTo(clientPlayer) < shaker.getRadius()) amp += shaker.getAmp(clientPlayer, Minecraft.getInstance().getFrameTime());
			}

			//Cap
			if (amp > 1.0F) amp = 1.0F;

			if (!Minecraft.getInstance().isPaused()) {
				//Note to self: Don't use .random, and don't EVER leave this inside a loop. Seriously. -- Meme Man
				event.setPitch((float) (event.getPitch() + amp * Math.cos(Minecraft.getInstance().getFrameTime() * 3 + 2) * 25));
				event.setYaw((float) (event.getYaw() + amp * Math.cos(Minecraft.getInstance().getFrameTime() * 5 + 1) * 25));
				event.setRoll((float) (event.getRoll() + amp * Math.cos(Minecraft.getInstance().getFrameTime() * 4) * 25));
			}
		}
	}

	@SubscribeEvent
	public static void onToolTipEvent(ItemTooltipEvent event) {
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

		if (cameraEntity == null)
			return;

		if (CAConfigManager.MAIN_CLIENT.enableCrystalDimensionFog.get()) {
			if (cameraEntity.level.dimension() == CADimensions.CRYSTAL_WORLD) {
				event.setDensity(Float.valueOf(CAConfigManager.MAIN_CLIENT.crystalDimensionFogDensity.get().toString()));
				event.setCanceled(true);
			}
		}

		if (CAConfigManager.MAIN_COMMON.enableLavaEelArmorSetBonus.get()) {
			if (cameraEntity instanceof PlayerEntity) {
				if (IUtilityHelper.isFullArmorSet((PlayerEntity) cameraEntity, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())) {
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
			if (IUtilityHelper.isFullArmorSet(player, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())) {
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
}
