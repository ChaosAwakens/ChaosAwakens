package io.github.chaosawakens.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBIColors {
	
	public static final IBlockColor GRASS_BLOCK_COLOR = (state, reader,  pos, color) -> {
		return reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : GrassColors.get(0.5, 1);
	};
	
	@SubscribeEvent
	public void registerBlockColors(ColorHandlerEvent.Block event) {
		
		event.getBlockColors().register(GRASS_BLOCK_COLOR,
				ModBlocks.RED_ANT_NEST.get(), ModBlocks.BROWN_ANT_NEST.get(), ModBlocks.RAINBOW_ANT_NEST.get(), ModBlocks.UNSTABLE_ANT_NEST.get(), ModBlocks.TERMITE_NEST.get());
	}
	
	@SubscribeEvent
	public void registerItemColors(ColorHandlerEvent.Item event) {
		
		event.getItemColors().register((stack, color) -> {
			return event.getBlockColors().getColor(((BlockItem) stack.getItem()).getBlock().getDefaultState(), (IBlockDisplayReader) null, (BlockPos) null, color);
		}, ModBlocks.RED_ANT_NEST.get(), ModBlocks.BROWN_ANT_NEST.get(), ModBlocks.RAINBOW_ANT_NEST.get(), ModBlocks.UNSTABLE_ANT_NEST.get(), ModBlocks.TERMITE_NEST.get());
	}
}
