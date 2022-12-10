package io.github.chaosawakens.client;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.BlockItem;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;

public class CABlockItemColors {
	public static final IBlockColor GRASS_BLOCK_COLOR = (state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColors.get(0.5D, 1.0D);
	public static final IBlockColor FOLIAGE_BLOCK_COLOR = (state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColors.getDefaultColor();
	public static final IBlockColor SPRUCE_FOLIAGE_BLOCK_COLOR = (state, reader, pos, color) -> FoliageColors.getEvergreenColor();
	public static final IBlockColor BIRCH_FOLIAGE_BLOCK_COLOR = (state, reader, pos, color) -> FoliageColors.getBirchColor();

	public static void registerBlockColors(ColorHandlerEvent.Block event) {
		event.getBlockColors().register(GRASS_BLOCK_COLOR, CABlocks.RED_ANT_NEST.get(), CABlocks.BROWN_ANT_NEST.get(),
				CABlocks.RAINBOW_ANT_NEST.get(), CABlocks.UNSTABLE_ANT_NEST.get(), CABlocks.TERMITE_NEST.get());
		event.getBlockColors().register(FOLIAGE_BLOCK_COLOR, CABlocks.OAK_LEAF_CARPET.get(),
				CABlocks.JUNGLE_LEAF_CARPET.get(), CABlocks.ACACIA_LEAF_CARPET.get(),
				CABlocks.DARK_OAK_LEAF_CARPET.get());
		event.getBlockColors().register(SPRUCE_FOLIAGE_BLOCK_COLOR, CABlocks.SPRUCE_LEAF_CARPET.get());
		event.getBlockColors().register(BIRCH_FOLIAGE_BLOCK_COLOR, CABlocks.BIRCH_LEAF_CARPET.get());
	}

	public static void registerItemColors(ColorHandlerEvent.Item event) {
		event.getItemColors().register(
				(stack, color) -> event.getBlockColors().getColor(((BlockItem) stack.getItem()).getBlock().defaultBlockState(), null, null, color),
				CABlocks.RED_ANT_NEST.get(), CABlocks.BROWN_ANT_NEST.get(), CABlocks.RAINBOW_ANT_NEST.get(),
				CABlocks.UNSTABLE_ANT_NEST.get(), CABlocks.TERMITE_NEST.get(), CABlocks.OAK_LEAF_CARPET.get(),
				CABlocks.SPRUCE_LEAF_CARPET.get(), CABlocks.BIRCH_LEAF_CARPET.get(), CABlocks.JUNGLE_LEAF_CARPET.get(),
				CABlocks.ACACIA_LEAF_CARPET.get(), CABlocks.DARK_OAK_LEAF_CARPET.get());
	}
}
