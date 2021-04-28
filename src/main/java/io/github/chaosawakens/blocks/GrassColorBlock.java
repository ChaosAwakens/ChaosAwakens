package io.github.chaosawakens.blocks;

import net.minecraft.block.Block;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class GrassColorBlock extends Block {

    public static final Block block = null;

    public GrassColorBlock (Properties properties) {
        super(properties);
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().register(new BlockColorRegisterHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new ItemColorRegisterHandler());
    }

    private static class BlockColorRegisterHandler {
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public void blockColorLoad(ColorHandlerEvent.Block event) {
            event.getBlockColors().register((bs, world, pos, index) -> {
                return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.get(0.5D, 1.0D);
            }, block);
        }
    }

    private static class ItemColorRegisterHandler {
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public void itemColorLoad(ColorHandlerEvent.Item event) {
            event.getItemColors().register((stack, index) -> {
                return GrassColors.get(0.5D, 1.0D);
            }, block);
        }
    }


}
