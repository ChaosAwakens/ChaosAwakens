package io.github.chaosawakens.registry;

import java.util.function.Supplier;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.blocks.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StemBlock;
import net.minecraft.block.StemGrownBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosAwakens.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);
	
	// SHINY FOODS
	public static final RegistryObject<Block> GOLDEN_MELON = registerBlock("golden_melon", () -> new GoldenMelonBlock(Block.Properties.from(Blocks.MELON).harvestTool(ToolType.AXE)), ModItemGroups.foodItemGroup);
	public static final RegistryObject<Block> ATTACHED_GOLDEN_MELON_STEM = registerBlock("attached_golden_melon_stem", () -> new AttachedStemBlock((StemGrownBlock)GOLDEN_MELON.get(), AbstractBlock.Properties.from(Blocks.ATTACHED_MELON_STEM)), null);
	public static final RegistryObject<Block> GOLDEN_MELON_STEM = registerBlock("golden_melon_stem", () -> new StemBlock((StemGrownBlock)GOLDEN_MELON.get(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.STEM)), null);
	//public static final RegistryObject<Block> CORN_CROP = registerBlock("corn_plant", () -> new CropsBlock(Block.Properties.from(Blocks.WHEAT)), null);
	
	// ENT DUNGEON BLOCKS
	public static final RegistryObject<GateBlock> GATE_BLOCK = registerBlock("gate_block", () -> new GateBlock(Block.Properties.from(Blocks.OAK_PLANKS).hardnessAndResistance(-1.0F, 3600000.0F)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> ENT_DUNGEON_WOOD = registerBlock("ent_dungeon_wood", () -> new Block(Block.Properties.from(Blocks.OAK_PLANKS).hardnessAndResistance(-1.0F, 3600000.0F)), ModItemGroups.blocksItemGroup);
	
	// MINERAL ORES
	public static final RegistryObject<ModOreBlock> AMETHYST_ORE = registerBlock("amethyst_ore", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> RUBY_ORE = registerBlock("ruby_ore", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> TIGERS_EYE_ORE = registerBlock("tigers_eye_ore", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> TITANIUM_ORE = registerBlock("titanium_ore", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> URANIUM_ORE = registerBlock("uranium_ore", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> ALUMINIUM_ORE = registerBlock("aluminium_ore", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> SALT_ORE = registerBlock("salt_ore", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(0)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> COPPER_ORE = registerBlock("copper_ore", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> TIN_ORE = registerBlock("tin_ore", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> SILVER_ORE = registerBlock("silver_ore", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> PLATINUM_ORE = registerBlock("platinum_ore", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<CrystalClusterBlock> PINK_TOURMALINE_CLUSTER = registerBlock("pink_tourmaline_cluster", () -> new CrystalClusterBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<CrystalClusterBlock> CATS_EYE_CLUSTER = registerBlock("cats_eye_cluster", () -> new CrystalClusterBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);

	// INFESTED ORES
	public static final RegistryObject<RedAntInfestedOre> RED_ANT_INFESTED_ORE = registerBlock("red_ant_infested_ore", () -> new RedAntInfestedOre(Block.Properties.from(Blocks.INFESTED_STONE).noDrops().harvestLevel(1)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<TermiteInfestedOre> TERMITE_INFESTED_ORE = registerBlock("termite_infested_ore", () -> new TermiteInfestedOre(Block.Properties.from(Blocks.INFESTED_STONE).noDrops().harvestLevel(1)), ModItemGroups.blocksItemGroup);
	
	// MOB ORES
	public static final RegistryObject<ModOreBlock> FOSSILISED_ENT = registerBlock("fossilised_ent", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1), true), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> FOSSILISED_HERCULES_BEETLE = registerBlock("fossilised_hercules_beetle", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1), true), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> FOSSILISED_RUBY_BUG = registerBlock("fossilised_ruby_bug", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1), true), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<ModOreBlock> FOSSILISED_EMERALD_GATOR = registerBlock("fossilised_emerald_gator", () -> new ModOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1), true), ModItemGroups.blocksItemGroup);
	
	// MINERAL BLOCKS
	public static final RegistryObject<Block> AMETHYST_BLOCK = registerBlock("amethyst_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TIGERS_EYE_BLOCK = registerBlock("tigers_eye_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> URANIUM_BLOCK = registerBlock("uranium_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> ALUMINIUM_BLOCK = registerBlock("aluminium_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> COPPER_BLOCK = registerBlock("copper_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(1)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(1)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> PLATINUM_BLOCK = registerBlock("platinum_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> PINK_TOURMALINE_BLOCK = registerBlock("pink_tourmaline_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).setOpaque(ModBlocks::_false).notSolid()), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> CATS_EYE_BLOCK = registerBlock("cats_eye_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).setOpaque(ModBlocks::_false).notSolid()), ModItemGroups.blocksItemGroup);
	
	// ANT NESTS
	public static final RegistryObject<Block> RED_ANT_NEST = registerBlock("red_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> BROWN_ANT_NEST = registerBlock("brown_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RAINBOW_ANT_NEST = registerBlock("rainbow_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> UNSTABLE_ANT_NEST = registerBlock("unstable_ant_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TERMITE_NEST = registerBlock("termite_nest", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
	
	// CRYSTAL DIMENSION
	public static final RegistryObject<Block> CRYSTAL_GRASS_BLOCK = registerBlock("crystal_grass_block", () -> new Block(Block.Properties.from(Blocks.STONE).setOpaque(ModBlocks::_false)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> KYANITE = registerBlock("kyanite", () -> new Block(Block.Properties.from(Blocks.STONE).setOpaque(ModBlocks::_false)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> CRYSTAL_LOG = registerBlock("crystal_log", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.OAK_LOG).setOpaque(ModBlocks::_false).notSolid()), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> CRYSTAL_WOOD = registerBlock("crystal_wood", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.OAK_WOOD).setOpaque(ModBlocks::_false).notSolid()), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> CRYSTAL_WOOD_PLANKS = registerBlock("crystal_wood_planks", () -> new Block(Block.Properties.from(Blocks.OAK_PLANKS).setOpaque(ModBlocks::_false).notSolid()), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RED_CRYSTAL_LEAVES = registerBlock("red_crystal_leaves", () -> new Block(Block.Properties.from(Blocks.OAK_LEAVES).setOpaque(ModBlocks::_false).notSolid()), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> GREEN_CRYSTAL_LEAVES = registerBlock("green_crystal_leaves", () -> new Block(Block.Properties.from(Blocks.OAK_LEAVES).setOpaque(ModBlocks::_false).notSolid()), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> YELLOW_CRYSTAL_LEAVES = registerBlock("yellow_crystal_leaves", () -> new Block(Block.Properties.from(Blocks.OAK_LEAVES).setOpaque(ModBlocks::_false).notSolid()), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> BUDDING_PINK_TOURMALINE = registerBlock("budding_pink_tourmaline", () -> new Block(Block.Properties.from(Blocks.STONE).setOpaque(ModBlocks::_false)), ModItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> BUDDING_CATS_EYE = registerBlock("budding_cats_eye", () -> new Block(Block.Properties.from(Blocks.STONE).setOpaque(ModBlocks::_false)), ModItemGroups.blocksItemGroup);


	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
		RegistryObject<B> block = ModBlocks.BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(itemGroup)));
		return block;
	}
	
	private static boolean _false(BlockState state, IBlockReader reader, BlockPos pos) {
		return false;
	}
	
	private static boolean _true(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}
	
	public static final DeferredRegister<Block> ENCHANTED_CAKE_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosAwakens.MODID);
	public static final DeferredRegister<Item> ENCHANTED_CAKE_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);
	
	public static final RegistryObject<EnchantedGoldenCakeBlock> ENCHANTED_GOLDEN_CAKE = registerEnchantedBlock("enchanted_golden_cake", () -> new EnchantedGoldenCakeBlock(Block.Properties.from(Blocks.CAKE).noDrops()), ModItemGroups.foodItemGroup, 1);
	
	public static <E extends Block> RegistryObject<E> registerEnchantedBlock(String name, Supplier<? extends E> supplier, ItemGroup itemGroup, int stackSize) {
		RegistryObject<E> enchantedBlock = ModBlocks.ENCHANTED_CAKE_BLOCKS.register(name, supplier);
		
		ENCHANTED_CAKE_ITEMS.register(name, () -> new EnchantedBlockItem(enchantedBlock.get(), new Item.Properties().group(itemGroup).maxStackSize(stackSize)));
		return enchantedBlock;
	}
}
