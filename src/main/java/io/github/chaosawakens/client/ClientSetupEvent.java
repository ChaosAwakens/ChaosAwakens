package io.github.chaosawakens.client;

import io.github.chaosawakens.client.entity.render.*;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientSetupEvent {

	public static void register() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetupEvent::onFMLClientSetupEvent);
	}

	public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.HERCULES_BEETLE.get(), HerculesBeetleEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RUBY_BUG.get(), RubyBugEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ENT.get(), EntEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RED_ANT.get(), RedAntEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BROWN_ANT.get(), BrownAntEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RAINBOW_ANT.get(), RainbowAntEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.UNSTABLE_ANT.get(), UnstableAntEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.TERMITE.get(), TermiteEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ULTIMATE_ARROW.get(), UltimateArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.IRUKANDJI_ARROW.get(), IrukandjiArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.THUNDER_BALL.get(), ThunderStaffProjectileRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.EXPLOSIVE_BALL.get(), RayGunProjectileRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.APPLE_COW.get(), AppleCowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GOLDEN_APPLE_COW.get(), GoldenAppleCowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get(), EnchantedGoldenAppleCowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CRYSTAL_APPLE_COW.get(), CrystalAppleCowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BEAVER.get(), BeaverEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.EMERALD_GATOR.get(), EmeraldGatorEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_SNIPER.get(), RoboSniperEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_WARRIOR.get(), RoboWarriorEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_LASER.get(), RoboLaserRender::new);

		RenderTypeLookup.setRenderLayer(CABlocks.PINK_TOURMALINE_BLOCK.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(CABlocks.CATS_EYE_BLOCK.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(CABlocks.RED_ANT_NEST.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(CABlocks.BROWN_ANT_NEST.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(CABlocks.RAINBOW_ANT_NEST.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(CABlocks.UNSTABLE_ANT_NEST.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(CABlocks.TERMITE_NEST.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(CABlocks.GOLDEN_MELON_STEM.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.ATTACHED_GOLDEN_MELON_STEM.get(), RenderType.getCutoutMipped());

		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_GRASS_BLOCK.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(CABlocks.KYANITE.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_LOG.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_WOOD.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_WOOD_PLANKS.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.RED_CRYSTAL_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.GREEN_CRYSTAL_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.YELLOW_CRYSTAL_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.PINK_TOURMALINE_CLUSTER.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CATS_EYE_CLUSTER.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.BUDDING_PINK_TOURMALINE.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.BUDDING_CATS_EYE.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_ENERGY.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_TORCH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.WALL_CRYSTAL_TORCH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_CRAFTING_TABLE.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_FURNACE.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(CABlocks.SUNSTONE_TORCH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.WALL_SUNSTONE_TORCH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.EXTREME_TORCH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.WALL_EXTREME_TORCH.get(), RenderType.getCutoutMipped());
		
		ItemModelsProperties.registerProperty(CAItems.ULTIMATE_BOW.get(), new ResourceLocation("pull"),
				(stack, world, living) -> {
					if (living == null) {
						return 0.0F;
					} else {
						return living.getActiveItemStack() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getItemInUseCount()) / 20.0F;
					}
		});
		ItemModelsProperties.registerProperty(CAItems.ULTIMATE_BOW.get(), new ResourceLocation("pulling"),
				(stack, world, living) -> living != null && living.isHandActive() && living.getActiveItemStack() == stack ? 1.0F : 0.0F);
		ItemModelsProperties.registerProperty(CAItems.SKATE_STRING_BOW.get(), new ResourceLocation("pull"),
				(stack, world, living) -> {
					if (living == null) {
						return 0.0F;
					} else {
						return living.getActiveItemStack() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getItemInUseCount()) / 20.0F;
					}
				});
		ItemModelsProperties.registerProperty(CAItems.SKATE_STRING_BOW.get(), new ResourceLocation("pulling"),
				(stack, world, living) -> living != null && living.isHandActive() && living.getActiveItemStack() == stack ? 1.0F : 0.0F);
	}
}
