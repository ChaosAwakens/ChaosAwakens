package io.github.chaosawakens.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterBlock {
    protected static void register(String identifier, Block block, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, new Identifier("chaosawakens", identifier), block);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", identifier), new BlockItem(block, new FabricItemSettings().group(itemGroup)));
    }
}
