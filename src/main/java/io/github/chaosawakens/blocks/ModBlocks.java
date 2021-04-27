package io.github.chaosawakens.blocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;

public class ModBlocks implements ModInitializer {

    public static final Block ALUMINUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));
    public static final Block ALUMINUM_ORE = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block DRUID_KEYHOLE = new Block(FabricBlockSettings.of(Material.WOOD));
    public static final Block OIL_ORE = new Block(FabricBlockSettings.of(Material.STONE));

    public static final Block AMETHYST_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));
    public static final Block AMETHYST_ORE = new Block(FabricBlockSettings.of(Material.STONE));

    public static final Block RUBY_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));
    public static final Block RUBY_ORE = new Block(FabricBlockSettings.of(Material.STONE));

    public static final Block URANIUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));
    public static final Block URANIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block TITANIUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));
    public static final Block TITANIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE));

    public static final Block TIGERSEYE_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));
    public static final Block TIGERSEYE_ORE = new Block(FabricBlockSettings.of(Material.STONE));

    public static final Block DRIED_EMERALD_ALLIGATOR = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block DRIED_RUBY_BEETLE = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block DRIED_SWARM = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block DRIED_TREE_MONSTER = new Block(FabricBlockSettings.of(Material.STONE));

    @Override
    public void onInitialize() {
        RegisterBlock.register("aluminum_block", ALUMINUM_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("aluminum_ore", ALUMINUM_ORE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("druid_keyhole", DRUID_KEYHOLE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("oil_ore", OIL_ORE, ItemGroup.BUILDING_BLOCKS);

        RegisterBlock.register("amethyst_block", AMETHYST_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("amethyst_ore", AMETHYST_ORE, ItemGroup.BUILDING_BLOCKS);

        RegisterBlock.register("ruby_block", RUBY_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("ruby_ore", RUBY_ORE, ItemGroup.BUILDING_BLOCKS);

        RegisterBlock.register("uranium_block", URANIUM_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("uranium_ore", URANIUM_ORE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("titanium_block", TITANIUM_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("titanium_ore", TITANIUM_ORE, ItemGroup.BUILDING_BLOCKS);

        RegisterBlock.register("tigerseye_block", TIGERSEYE_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("tigerseye_ore", TIGERSEYE_ORE, ItemGroup.BUILDING_BLOCKS);

        RegisterBlock.register("dried_emerald_alligator", DRIED_EMERALD_ALLIGATOR, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("dried_ruby_beetle", DRIED_RUBY_BEETLE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("dried_swarm", DRIED_SWARM, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("dried_tree_monster", DRIED_TREE_MONSTER, ItemGroup.BUILDING_BLOCKS);
    }
}
