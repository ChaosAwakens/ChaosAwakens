package io.github.chaosawakens.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosAwakens.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);
	
	// SHINY FOODS
	public static final RegistryObject<GoldenCakeBlock> GOLDEN_CAKE = registerBlock("golden_cake", () -> new GoldenCakeBlock(Block.Properties.from(Blocks.CAKE).noDrops()), ModItemGroups.foodItemGroup, 1);

	// CROPS
	public static final RegistryObject<Block> GOLDEN_MELON = registerBlock("golden_melon", () -> new GoldenMelonBlock(Block.Properties.create(Material.GOURD, MaterialColor.GOLD).hardnessAndResistance(1.0F).sound(SoundType.WOOD).harvestTool(ToolType.AXE)), ModItemGroups.foodItemGroup, 64);
	public static final RegistryObject<Block> ATTACHED_GOLDEN_MELON_STEM = registerBlock("attached_golden_melon_stem", () -> new AttachedStemBlock((StemGrownBlock)GOLDEN_MELON.get(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD)), null, 0);
	public static final RegistryObject<Block> GOLDEN_MELON_STEM = registerBlock("golden_melon_stem", () -> new StemBlock((StemGrownBlock)GOLDEN_MELON.get(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.STEM)), null, 0);

	// ENT DUNGEON BLOCKS
	public static final RegistryObject<GateBlock> GATE_BLOCK = registerBlock("gate_block", () -> new GateBlock(Block.Properties.from(Blocks.OAK_PLANKS).hardnessAndResistance(-1.0F, 3600000.0F)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> ENT_DUNGEON_WOOD = registerBlock("ent_dungeon_wood", () -> new Block(Block.Properties.from(Blocks.OAK_PLANKS).hardnessAndResistance(-1.0F, 3600000.0F)), ModItemGroups.blocksItemGroup, 64);

	// GEMSTONE ORES
	public static final RegistryObject<Block> AMETHYST_ORE = registerBlock("amethyst_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> RUBY_ORE = registerBlock("ruby_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> TIGERS_EYE_ORE = registerBlock("tigers_eye_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> URANIUM_ORE = registerBlock("uranium_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> ALUMINIUM_ORE = registerBlock("aluminium_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> SALT_ORE = registerBlock("salt_ore", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(0)), ModItemGroups.blocksItemGroup, 64);
	
	// INFESTED ORES
	public static final RegistryObject<RedAntInfestedOre> RED_ANT_INFESTED_ORE = registerBlock("red_ant_infested_ore", () -> new RedAntInfestedOre(Block.Properties.from(Blocks.INFESTED_STONE).noDrops().harvestLevel(1)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<TermiteInfestedOre> TERMITE_INFESTED_ORE = registerBlock("termite_infested_ore", () -> new TermiteInfestedOre(Block.Properties.from(Blocks.INFESTED_STONE).noDrops().harvestLevel(1)), ModItemGroups.blocksItemGroup, 64);
	
	// MOB ORES
	public static final RegistryObject<Block> FOSSILISED_ENT = registerBlock("fossilised_ent", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> FOSSILISED_HERCULES_BEETLE = registerBlock("fossilised_hercules_beetle", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> FOSSILISED_RUBY_BUG = registerBlock("fossilised_ruby_bug", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> FOSSILISED_EMERALD_GATOR = registerBlock("fossilised_emerald_gator", () -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup, 64);
	
	// GEMSTONE BLOCKS
	public static final RegistryObject<Block> AMETHYST_BLOCK = registerBlock("amethyst_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> TIGERS_EYE_BLOCK = registerBlock("tigers_eye_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> URANIUM_BLOCK = registerBlock("uranium_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> ALUMINIUM_BLOCK = registerBlock("aluminium_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup, 64);
	
	public static final RegistryObject<Block> RED_ANT_NEST = registerBlock("red_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> BROWN_ANT_NEST = registerBlock("brown_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> RAINBOW_ANT_NEST = registerBlock("rainbow_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> UNSTABLE_ANT_NEST = registerBlock("unstable_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup, 64);
	public static final RegistryObject<Block> TERMITE_NEST = registerBlock("termite_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup, 64);
	
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, int stackSize) {
		RegistryObject<B> block = ModBlocks.BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(itemGroup).maxStackSize(stackSize)));
		return block;
	}

	public static final DeferredRegister<Block> ENCHANTEDCAKEBLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosAwakens.MODID);
	public static final DeferredRegister<Item> ENCHANTEDCAKEITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);

	public static final RegistryObject<EnchantedGoldenCakeBlock> ENCHANTED_GOLDEN_CAKE = registerEnchantedBlock("enchanted_golden_cake", () -> new EnchantedGoldenCakeBlock(Block.Properties.from(Blocks.CAKE).noDrops()), ModItemGroups.foodItemGroup, 1);

	public static <E extends Block> RegistryObject<E> registerEnchantedBlock(String name, Supplier<? extends E> supplier, ItemGroup itemGroup, int stackSize) {
		RegistryObject<E> enchantedblock = ModBlocks.ENCHANTEDCAKEBLOCKS.register(name, supplier);
		ENCHANTEDCAKEITEMS.register(name, () -> new EnchantedBlockItem(enchantedblock.get(), new Item.Properties().group(itemGroup).maxStackSize(stackSize)));
		return enchantedblock;
	}
}