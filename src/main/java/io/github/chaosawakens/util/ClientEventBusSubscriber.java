package io.github.chaosawakens.util;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.render.*;
import io.github.chaosawakens.registry.ModEntityTypes;
import net.minecraft.entity.EntityType;
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
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ULTIMATE_ARROW.get(), UltimateArrowEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.APPLE_COW.get(), AppleCowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GOLDEN_APPLE_COW.get(), GoldenAppleCowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BEAVER.get(), BeaverEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.EMERALD_GATOR.get(), EmeraldGatorEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ROBO_SNIPER.get(), RoboSniperEntityRender::new);
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
    }
}