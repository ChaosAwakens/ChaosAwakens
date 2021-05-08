package io.github.chaosawakens.blocks;

import io.github.chaosawakens.blocks.custom.RedAntInfestedOre;
import io.github.chaosawakens.blocks.custom.TermiteInfestedOre;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;

public class ModBlocks implements ModInitializer {
    //Ent Dungeon Blocks
    public static final Block GATE_BLOCK = new Block(FabricBlockSettings.of(Material.WOOD));
    public static final Block END_DUNGEON_WOOD = new Block(FabricBlockSettings.of(Material.WOOD));

    //Gemstone Ores
    public static final Block AMETHYST_ORE = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block RUBY_ORE = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block TIGERS_EYE_ORE = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block TITANIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block URANIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block ALUMINIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block SALT_ORE = new Block(FabricBlockSettings.of(Material.STONE));

    //Infested Ores
    public static final Block RED_ANT_INFESTED_ORE = new RedAntInfestedOre(FabricBlockSettings.of(Material.STONE));
    public static final Block TERMITE_INFESTED_ORE = new TermiteInfestedOre(FabricBlockSettings.of(Material.STONE));

    //Mob Ores
    public static final Block FOSSILISED_EMERALD_GATOR = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block FOSSILISED_RUBY_BUG = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block FOSSILISED_HERCULES_BEETLE = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block FOSSILISED_ENT = new Block(FabricBlockSettings.of(Material.STONE));

    //Gemstone Blocks
    public static final Block AMETHYST_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));
    public static final Block RUBY_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));
    public static final Block TIGERS_EYE_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));
    public static final Block TITANIUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));
    public static final Block URANIUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));
    public static final Block ALUMINIUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL));

    //Nests
    public static final Block RED_ANT_NEST = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block BROWN_ANT_NEST = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block RAINBOW_ANT_NEST = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block UNSTABLE_ANT_NEST = new Block(FabricBlockSettings.of(Material.STONE));
    public static final Block TERMITE_NEST = new Block(FabricBlockSettings.of(Material.STONE));

    

    @Override
    public void onInitialize() {
        //Ent Dungeon Blocks
        RegisterBlock.register("gate_block",  GATE_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("ent_dungeon_wood",  END_DUNGEON_WOOD, ItemGroup.BUILDING_BLOCKS);

        //Gemstone Ores
        RegisterBlock.register("amethyst_ore",  AMETHYST_ORE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("ruby_ore",  RUBY_ORE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("tigers_eye_ore",  TIGERS_EYE_ORE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("titanium_ore",  TITANIUM_ORE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("uranium_ore",  URANIUM_ORE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("aluminium_ore",  ALUMINIUM_ORE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("salt_ore",  SALT_ORE, ItemGroup.BUILDING_BLOCKS);

        //Infested Ores
        RegisterBlock.register("red_ant_infested_ore",  RED_ANT_INFESTED_ORE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("termite_infested_ore",  TERMITE_INFESTED_ORE, ItemGroup.BUILDING_BLOCKS);

        //Mob Ores
        RegisterBlock.register("fossilised_emerald_gator",  FOSSILISED_EMERALD_GATOR, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("fossilised_ruby_bug",  FOSSILISED_RUBY_BUG, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("fossilised_hercules_beetle",  FOSSILISED_HERCULES_BEETLE, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("fossilised_ent",  FOSSILISED_ENT, ItemGroup.BUILDING_BLOCKS);

        //Gemstone Blocks
        RegisterBlock.register("amethyst_block",  AMETHYST_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("ruby_block",  RUBY_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("tigers_eye_block",  TIGERS_EYE_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("titanium_block",  TITANIUM_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("uranium_block",  URANIUM_BLOCK, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("aluminium_block",  ALUMINIUM_BLOCK, ItemGroup.BUILDING_BLOCKS);

        //Nests
        RegisterBlock.register("red_ant_nest",  RED_ANT_NEST, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("brown_ant_nest",  BROWN_ANT_NEST, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("rainbow_ant_nest",  RAINBOW_ANT_NEST, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("unstable_ant_nest",  UNSTABLE_ANT_NEST, ItemGroup.BUILDING_BLOCKS);
        RegisterBlock.register("termite_nest",  TERMITE_NEST, ItemGroup.BUILDING_BLOCKS);
    }
}
