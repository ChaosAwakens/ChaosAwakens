package io.github.chaosawakens.util;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.render.*;
import io.github.chaosawakens.registry.ModBlocks;
import io.github.chaosawakens.registry.ModEntityTypes;
import io.github.chaosawakens.registry.ModItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.HERCULES_BEETLE.get(), HerculesBeetleEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.RUBY_BUG.get(), RubyBugEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ENT.get(), EntEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.RED_ANT.get(), RedAntEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BROWN_ANT.get(), BrownAntEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.RAINBOW_ANT.get(), RainbowAntEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.UNSTABLE_ANT.get(), UnstableAntEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.TERMITE.get(), TermiteEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ULTIMATE_ARROW.get(), UltimateArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.IRUKANDJI_ARROW.get(), IrukandjiArrowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.APPLE_COW.get(), AppleCowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GOLDEN_APPLE_COW.get(), GoldenAppleCowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get(), EnchantedGoldenAppleCowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BEAVER.get(), BeaverEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.EMERALD_GATOR.get(), EmeraldGatorEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ROBO_SNIPER.get(), RoboSniperEntityRender::new);
		
		RenderTypeLookup.setRenderLayer(ModBlocks.PINK_TOURMALINE_BLOCK.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CATS_EYE_BLOCK.get(), RenderType.getCutout());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.RED_ANT_NEST.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BROWN_ANT_NEST.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.RAINBOW_ANT_NEST.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.UNSTABLE_ANT_NEST.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TERMITE_NEST.get(), RenderType.getCutout());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.GOLDEN_MELON_STEM.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.ATTACHED_GOLDEN_MELON_STEM.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.CRYSTAL_GRASS_BLOCK.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.KYANITE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CRYSTAL_LOG.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CRYSTAL_WOOD.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CRYSTAL_WOOD_PLANKS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.RED_CRYSTAL_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.GREEN_CRYSTAL_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.YELLOW_CRYSTAL_LEAVES.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.PINK_TOURMALINE_CLUSTER.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CATS_EYE_CLUSTER.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BUDDING_PINK_TOURMALINE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BUDDING_CATS_EYE.get(), RenderType.getCutout());

		ItemModelsProperties.registerProperty(ModItems.SKATE_STRING_BOW.get(), new ResourceLocation("pull"), (stack, world, living) -> {
			if (living == null) {
				return 0.0F;
			} else {
				return living.getActiveItemStack() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getItemInUseCount()) / 20.0F;
			}
		});
		ItemModelsProperties.registerProperty(ModItems.SKATE_STRING_BOW.get(), new ResourceLocation("pulling"), (stack, world, living) -> {
			return living != null && living.isHandActive() && living.getActiveItemStack() == stack ? 1.0F : 0.0F;
		});
	}
	
	@SubscribeEvent
	public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {}
}