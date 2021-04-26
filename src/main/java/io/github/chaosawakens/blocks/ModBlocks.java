package io.github.chaosawakens.blocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;

public class ModBlocks implements ModInitializer {

    public static final Block AMETHYST_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(0));
    public static final Block AMETHYST_ORE = new Block(FabricBlockSettings.of(Material.STONE).strength(0));

    public static final Block RUBY_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(0));
    public static final Block RUBY_ORE = new Block(FabricBlockSettings.of(Material.STONE).strength(0));

    @Override
    public void onInitialize() {
        RegisterBlock.register("amethyst_block", AMETHYST_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("amethyst_ore", AMETHYST_ORE, ItemGroup.BUILDING_BLOCKS);

        RegisterBlock.register("ruby_block", RUBY_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("ruby_ore", RUBY_ORE, ItemGroup.BUILDING_BLOCKS);
    }
}
