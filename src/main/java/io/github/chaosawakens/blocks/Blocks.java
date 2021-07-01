package io.github.chaosawakens.blocks;

import io.github.chaosawakens.ChaosAwakens;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks implements ModInitializer {
    public static final Block GATE_BLOCK;
    public static final Block ENT_DUNGEON_WOOD;
    public static final Block GOLDEN_MELON;
    public static final Block AMETHYST_ORE;
    public static final Block RUBY_ORE;
    public static final Block TIGERS_EYE_ORE;
    public static final Block TITANIUM_ORE;
    public static final Block URANIUM_ORE;
    public static final Block ALUMINIUM_ORE;
    public static final Block SALT_ORE;
    public static final Block RED_ANT_INFESTED_ORE;
    public static final Block TERMITE_INFESTED_ORE;
    public static final Block FOSSILISED_EMERALD_GATOR;
    public static final Block FOSSILISED_RUBY_BUG;
    public static final Block FOSSILISED_HERCULES_BEETLE;
    public static final Block FOSSILISED_ENT;
    public static final Block AMETHYST_BLOCK;
    public static final Block RUBY_BLOCK;
    public static final Block TIGERS_EYE_BLOCK;
    public static final Block TITANIUM_BLOCK;
    public static final Block URANIUM_BLOCK;
    public static final Block ALUMINIUM_BLOCK;
    public static final Block RED_ANT_NEST;
    public static final Block BROWN_ANT_NEST;
    public static final Block RAINBOW_ANT_NEST;
    public static final Block UNSTABLE_ANT_NEST;
    public static final Block TERMITE_NEST;
    
    private static Block register(String identifier, Block block, ItemGroup itemGroup) {
        Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new BlockItem(block, new FabricItemSettings().group(itemGroup)));
        return Registry.register(Registry.BLOCK, new Identifier(ChaosAwakens.modID, identifier), block);
    }
    private static Block register(String identifier, AbstractBlock.Settings settings, ItemGroup itemGroup) {
        Block block = new Block(settings);
        Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new BlockItem(block, new FabricItemSettings().group(itemGroup)));
        return Registry.register(Registry.BLOCK, new Identifier(ChaosAwakens.modID, identifier), block);
    }
    
    static {
        GATE_BLOCK = register("gate_block",  new Block(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.OAK_WOOD).hardness(-1.0f).resistance(3600000.0f)), ItemGroup.BUILDING_BLOCKS);
        ENT_DUNGEON_WOOD = register("ent_dungeon_wood",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.OAK_WOOD).hardness(-1.0f).resistance(3600000.0f), ItemGroup.BUILDING_BLOCKS);
        GOLDEN_MELON = register("golden_melon", FabricBlockSettings.copyOf(net.minecraft.block.Blocks.MELON).breakByTool(FabricToolTags.AXES), ItemGroup.DECORATIONS);
        AMETHYST_ORE = register("amethyst_ore",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE).breakByTool(FabricToolTags.PICKAXES, 2), ItemGroup.BUILDING_BLOCKS);
        RUBY_ORE = register("ruby_ore",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE).breakByTool(FabricToolTags.PICKAXES, 3), ItemGroup.BUILDING_BLOCKS);
        TIGERS_EYE_ORE = register("tigers_eye_ore",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE).breakByTool(FabricToolTags.PICKAXES, 2), ItemGroup.BUILDING_BLOCKS);
        TITANIUM_ORE = register("titanium_ore",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE).breakByTool(FabricToolTags.PICKAXES, 3), ItemGroup.BUILDING_BLOCKS);
        URANIUM_ORE = register("uranium_ore",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE).breakByTool(FabricToolTags.PICKAXES, 3), ItemGroup.BUILDING_BLOCKS);
        ALUMINIUM_ORE = register("aluminum_ore",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE).breakByTool(FabricToolTags.PICKAXES, 2), ItemGroup.BUILDING_BLOCKS);
        SALT_ORE = register("salt_ore",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE).breakByTool(FabricToolTags.PICKAXES, 0), ItemGroup.BUILDING_BLOCKS);
        RED_ANT_INFESTED_ORE = register("red_ant_infested_ore",  new RedAntInfestedOre(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.INFESTED_STONE).dropsNothing().breakByTool(FabricToolTags.PICKAXES, 1)), ItemGroup.BUILDING_BLOCKS);
        TERMITE_INFESTED_ORE = register("termite_infested_ore",  new TermiteInfestedOre(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.INFESTED_STONE).dropsNothing().breakByTool(FabricToolTags.PICKAXES, 1)), ItemGroup.BUILDING_BLOCKS);
        FOSSILISED_EMERALD_GATOR = register("fossilised_emerald_gator",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE).breakByTool(FabricToolTags.PICKAXES, 1), ItemGroup.BUILDING_BLOCKS);
        FOSSILISED_RUBY_BUG = register("fossilised_ruby_bug",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE).breakByTool(FabricToolTags.PICKAXES, 1), ItemGroup.BUILDING_BLOCKS);
        FOSSILISED_HERCULES_BEETLE = register("fossilised_hercules_beetle",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE).breakByTool(FabricToolTags.PICKAXES, 1), ItemGroup.BUILDING_BLOCKS);
        FOSSILISED_ENT = register("fossilised_ent",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE).breakByTool(FabricToolTags.PICKAXES, 1), ItemGroup.BUILDING_BLOCKS);
        AMETHYST_BLOCK = register("amethyst_block",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_BLOCK).breakByTool(FabricToolTags.PICKAXES, 2), ItemGroup.BUILDING_BLOCKS);
        RUBY_BLOCK = register("ruby_block",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_BLOCK).breakByTool(FabricToolTags.PICKAXES, 2), ItemGroup.BUILDING_BLOCKS);
        TIGERS_EYE_BLOCK = register("tigers_eye_block",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_BLOCK).breakByTool(FabricToolTags.PICKAXES, 2), ItemGroup.BUILDING_BLOCKS);
        TITANIUM_BLOCK = register("titanium_block",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_BLOCK).breakByTool(FabricToolTags.PICKAXES, 2), ItemGroup.BUILDING_BLOCKS);
        URANIUM_BLOCK = register("uranium_block",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_BLOCK).breakByTool(FabricToolTags.PICKAXES, 2), ItemGroup.BUILDING_BLOCKS);
        ALUMINIUM_BLOCK = register("aluminium_block",  FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_BLOCK).breakByTool(FabricToolTags.PICKAXES, 2), ItemGroup.BUILDING_BLOCKS);
        RED_ANT_NEST = register("red_ant_nest",  new Block(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.GRASS_BLOCK)), ItemGroup.BUILDING_BLOCKS);
        BROWN_ANT_NEST = register("brown_ant_nest",  new Block(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.GRASS_BLOCK)), ItemGroup.BUILDING_BLOCKS);
        RAINBOW_ANT_NEST = register("rainbow_ant_nest",  new Block(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.GRASS_BLOCK)), ItemGroup.BUILDING_BLOCKS);
        UNSTABLE_ANT_NEST = register("unstable_ant_nest",  new Block(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.GRASS_BLOCK)), ItemGroup.BUILDING_BLOCKS);
        TERMITE_NEST = register("termite_nest",  new Block(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.GRASS_BLOCK)), ItemGroup.BUILDING_BLOCKS);
    }

    @Override
    public void onInitialize() { }
}
