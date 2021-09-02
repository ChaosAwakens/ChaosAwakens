package io.github.chaosawakens.common.registry;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

/**
 * For when the logs weren't enough :)
 *
 * @author Meme Man
 */
public class CAStrippedLogBlocks {

    public static void registerStrippedLogs() {
        /*registerStrippingLog(CABlocks.DUPLICATION_LOG.get(), CABlocks.STRIPPED_DUPLICATION_LOG.get());
        registerStrippingLog(CABlocks.PEACH_LOG.get(), CABlocks.STRIPPED_PEACH_LOG.get());
        registerStrippingLog(CABlocks.APPLE_LOG.get(), CABlocks.STRIPPED_APPLE_LOG.get());
        registerStrippingLog(CABlocks.CHERRY_LOG.get(), CABlocks.STRIPPED_CHERRY_LOG.get());*/
    }


    public static void registerStrippingLog(Block log, Block strippedLog) {
        AxeItem.STRIPABLES = Maps.newHashMap(AxeItem.STRIPABLES);
        AxeItem.STRIPABLES.put(log, strippedLog);
    }

}


