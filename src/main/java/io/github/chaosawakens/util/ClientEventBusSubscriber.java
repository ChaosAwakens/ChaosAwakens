package io.github.chaosawakens.util;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.render.*;
import io.github.chaosawakens.registry.ModBlocks;
import io.github.chaosawakens.registry.ModEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ENT.get(), EntEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.RED_ANT.get(), RedAntEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BROWN_ANT.get(), BrownAntEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.RAINBOW_ANT.get(), RainbowAntEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.UNSTABLE_ANT.get(), UnstableAntEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.TERMITE.get(), TermiteEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ULTIMATE_ARROW.get(), UltimateArrowEntityRender::new);
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
    }

}