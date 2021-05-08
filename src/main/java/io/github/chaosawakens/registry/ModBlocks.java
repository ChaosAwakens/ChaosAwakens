package io.github.chaosawakens.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.blocks.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.FluidContainerColorer;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosAwakens.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);
	
	// SHINY FOODS
	public static final RegistryObject<Block> GOLDEN_MELON = registerBlock("golden_melon", () -> new Block(Block.Properties.from(Blocks.MELON).harvestTool(ToolType.AXE)), ModItemGroups.foodItemGroup);
	
	// ENT DUNGEON BLOCKS
	public static final RegistryObject<GateBlock> GATE_BLOCK = registerBlock("gate_block", () -> new GateBlock(Block.Properties.from(Blocks.OAK_PLANKS).hardnessAndResistance(-1.0F, 3600000.0F)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> ENT_DUNGEON_WOOD = registerBlock("ent_dungeon_wood", () -> new Block(Block.Properties.from(Blocks.OAK_PLANKS).hardnessAndResistance(-1.0F, 3600000.0F)), ModItemGroups.blocksItemGroup);
	
	// GEMSTONE ORES
	public static final RegistryObject<Block> AMETHYST_ORE = registerBlock("amethyst_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RUBY_ORE = registerBlock("ruby_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TIGERS_EYE_ORE = registerBlock("tigers_eye_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> URANIUM_ORE = registerBlock("uranium_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> ALUMINIUM_ORE = registerBlock("aluminium_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> SALT_ORE = registerBlock("salt_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(0)), ModItemGroups.blocksItemGroup);
	
	// INFESTED ORES
	public static final RegistryObject<RedAntInfestedOre> RED_ANT_INFESTED_ORE = registerBlock("red_ant_infested_ore", () -> new RedAntInfestedOre(Block.Properties.from(Blocks.INFESTED_STONE).noDrops().harvestLevel(1)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<TermiteInfestedOre> TERMITE_INFESTED_ORE = registerBlock("termite_infested_ore", () -> new TermiteInfestedOre(Block.Properties.from(Blocks.INFESTED_STONE).noDrops().harvestLevel(1)), ModItemGroups.blocksItemGroup);
	
	// MOB ORES
	public static final RegistryObject<Block> FOSSILISED_ENT = registerBlock("fossilised_ent", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> FOSSILISED_HERCULES_BEETLE = registerBlock("fossilised_hercules_beetle", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> FOSSILISED_RUBY_BUG = registerBlock("fossilised_ruby_bug", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> FOSSILISED_EMERALD_GATOR = registerBlock("fossilised_emerald_gator", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup);
	
	// GEMSTONE BLOCKS
	public static final RegistryObject<Block> AMETHYST_BLOCK = registerBlock("amethyst_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TIGERS_EYE_BLOCK = registerBlock("tigers_eye_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> URANIUM_BLOCK = registerBlock("uranium_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> ALUMINIUM_BLOCK = registerBlock("aluminium_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	
	public static final RegistryObject<Block> RED_ANT_NEST = registerBlock("red_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> BROWN_ANT_NEST = registerBlock("brown_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RAINBOW_ANT_NEST = registerBlock("rainbow_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> UNSTABLE_ANT_NEST = registerBlock("unstable_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TERMITE_NEST = registerBlock("termite_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
	
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
		RegistryObject<B> block = ModBlocks.BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(itemGroup)));
		return block;
	}
	
	private static boolean nonSolid(BlockState state, IBlockReader reader, BlockPos pos) {
		return false;
	}
}