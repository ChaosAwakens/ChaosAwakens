package io.github.chaosawakens.common.registry;

import com.google.common.collect.Maps;


import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

/**
 * @author Meme Man
 *
 */
public class CAStrippedLogBlocks{
	
	//I went a bit crazy while making this because it took more than 15 minutes lmao
	public static void initveryfunni() {
		registerCustomStrippedShit(CABlocks.DUPLICATION_LOG.get(), CABlocks.STRIPPED_DUPLICATION_LOG.get());
		registerCustomStrippedShit(CABlocks.PEACH_LOG.get(), CABlocks.STRIPPED_PEACH_LOG.get());
		registerCustomStrippedShit(CABlocks.APPLE_LOG.get(), CABlocks.STRIPPED_APPLE_LOG.get());
		registerCustomStrippedShit(CABlocks.CHERRY_LOG.get(), CABlocks.STRIPPED_CHERRY_LOG.get());
	}
	
	
	public static void registerCustomStrippedShit(Block log, Block strippedLog) {
		AxeItem.STRIPABLES = Maps.newHashMap(AxeItem.STRIPABLES);
		AxeItem.STRIPABLES.put(log, strippedLog);
	}
	
}


