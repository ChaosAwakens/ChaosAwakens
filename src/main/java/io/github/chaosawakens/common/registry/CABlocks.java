package io.github.chaosawakens.common.registry;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.AntInfestedOre;
import io.github.chaosawakens.common.blocks.AntNestBlock;
import io.github.chaosawakens.common.blocks.BossSpawnerBlock;
import io.github.chaosawakens.common.blocks.BuddingBlock;
import io.github.chaosawakens.common.blocks.CAOreBlock;
import io.github.chaosawakens.common.blocks.CropPlantBlock;
import io.github.chaosawakens.common.blocks.CrystalClusterBlock;
import io.github.chaosawakens.common.blocks.CrystalCraftingTableBlock;
import io.github.chaosawakens.common.blocks.CrystalEnergyBlock;
import io.github.chaosawakens.common.blocks.CrystalFurnaceBlock;
import io.github.chaosawakens.common.blocks.EnchantedGoldenCakeBlock;
import io.github.chaosawakens.common.blocks.GateBlock;
import io.github.chaosawakens.common.blocks.GoldenCakeBlock;
import io.github.chaosawakens.common.blocks.GoldenMelonBlock;
import io.github.chaosawakens.common.blocks.RandomTeleportBlock;
import io.github.chaosawakens.common.blocks.StrawberryBushBlock;
import io.github.chaosawakens.common.blocks.TopTubeBlock;
import io.github.chaosawakens.common.blocks.TubeBlock;
import io.github.chaosawakens.common.blocks.UraniumOreBlock;
import io.github.chaosawakens.common.items.EnchantedBlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.IPositionPredicate;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StemBlock;
import net.minecraft.block.StemGrownBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CABlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosAwakens.MODID);
	public static final DeferredRegister<Item> ITEM_BLOCKS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);

	private static final IPositionPredicate isFalse = (state, reader, pos) -> false;
//	private static IPositionPredicate isTrue = (state, reader, pos) -> true; --Unused

	private static final Function<Integer, ToIntFunction<BlockState>> lightValueFunction = (lightValue) -> (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;

	//DUPE TREE
	//public static final RegistryObject<Block> DUPLICATION_LOG = registerBlock("duplication_log", ()-> new Block(Block.Properties.copy(Blocks.SPRUCE_LOG).harvestLevel(3).harvestTool(ToolType.AXE).requiresCorrectToolForDrops().sound(SoundType.WOOD)), CAItemGroups.blocksItemGroup);


	// SHINY FOODS
	public static final RegistryObject<GoldenMelonBlock> GOLDEN_MELON = registerBlock("golden_melon", () -> new GoldenMelonBlock(Block.Properties.copy(Blocks.MELON).harvestTool(ToolType.AXE)), CAItemGroups.foodItemGroup);
	public static final RegistryObject<AttachedStemBlock> ATTACHED_GOLDEN_MELON_STEM = registerBlock("attached_golden_melon_stem", () -> new AttachedStemBlock((StemGrownBlock) GOLDEN_MELON.get(), AbstractBlock.Properties.copy(Blocks.ATTACHED_MELON_STEM)), null, false);
	public static final RegistryObject<StemBlock> GOLDEN_MELON_STEM = registerBlock("golden_melon_stem", () -> new StemBlock((StemGrownBlock) GOLDEN_MELON.get(), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.HARD_CROP)), null, false);
	public static final RegistryObject<GoldenCakeBlock> GOLDEN_CAKE = registerBlock("golden_cake", () -> new GoldenCakeBlock(Block.Properties.copy(Blocks.CAKE).noDrops()), CAItemGroups.foodItemGroup, 1);
	public static final RegistryObject<EnchantedGoldenCakeBlock> ENCHANTED_GOLDEN_CAKE = registerEnchantedBlock("enchanted_golden_cake", () -> new EnchantedGoldenCakeBlock(Block.Properties.copy(Blocks.CAKE).noDrops()), CAItemGroups.foodItemGroup, 1);

	// PLANTS
	public static final RegistryObject<TopTubeBlock> TUBE_WORM = registerBlock("tube_worm",() -> new TopTubeBlock(AbstractBlock.Properties.of(Material.PLANT, MaterialColor.TERRACOTTA_WHITE).sound(SoundType.BONE_BLOCK).noCollission().instabreak()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<TubeBlock> TUBE_WORM_PLANT = registerBlock("tube_worm_plant",() -> new TubeBlock(AbstractBlock.Properties.of(Material.PLANT, MaterialColor.TERRACOTTA_WHITE).sound(SoundType.BONE_BLOCK).noCollission().instabreak().lootFrom(CABlocks.TUBE_WORM)), null, false);
	public static final RegistryObject<CropPlantBlock> CORN_PLANT = registerBlock("corn_plant", () -> new CropPlantBlock(CAItems.CORN_SEEDS, Block.Properties.copy(Blocks.SUGAR_CANE).randomTicks()), CAItemGroups.foodItemGroup, false);
	public static final RegistryObject<CropPlantBlock> TOMATO_PLANT = registerBlock("tomato_plant", () -> new CropPlantBlock(CAItems.TOMATO_SEEDS, Block.Properties.copy(Blocks.SUGAR_CANE).randomTicks()), CAItemGroups.foodItemGroup, false);
	public static final RegistryObject<StrawberryBushBlock> STRAWBERRY_PLANT = registerBlock("strawberry_plant", () -> new StrawberryBushBlock(CAItems.STRAWBERRY_SEEDS, CAItems.STRAWBERRY, Block.Properties.copy(Blocks.SWEET_BERRY_BUSH).randomTicks()), CAItemGroups.foodItemGroup, false);
	
	// TREES
	public static final RegistryObject<RotatedPillarBlock> APPLE_LOG = registerBlock("apple_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> APPLE_PLANKS = registerBlock("apple_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> CHERRY_LOG = registerBlock("cherry_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<LeavesBlock> CHERRY_LEAVES = registerBlock("cherry_leaves", () -> new LeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> CHERRY_PLANKS = registerBlock("cherry_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> PEACH_LOG = registerBlock("peach_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<LeavesBlock> PEACH_LEAVES = registerBlock("peach_leaves", () -> new LeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> PEACH_PLANKS = registerBlock("peach_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_PEACH_LOG = registerBlock("stripped_peach_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> DUPLICATION_LOG = registerBlock("duplication_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<LeavesBlock> DUPLICATION_LEAVES = registerBlock("duplication_leaves", () -> new LeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_DUPLICATION_LOG = registerBlock("stripped_duplication_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_CHERRY_LOG = registerBlock("stripped_cherry_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_APPLE_LOG = registerBlock("stripped_apple_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> DEAD_DUPLICATION_LOG = registerBlock("dead_duplication_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> DUPLICATION_PLANKS = registerBlock("duplication_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)), CAItemGroups.blocksItemGroup);
	
	// DUNGEON BLOCKS
	public static final RegistryObject<Block> NEST_BLOCK = registerBlock("nest_block", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(0.3F).sound(SoundType.WOOD)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<GateBlock> GATE_BLOCK = registerBlock("gate_block", () -> new GateBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(-1.0F, 3600000.0F)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RandomTeleportBlock> RANDOM_TELEPORT_BLOCK = registerBlock("random_teleport_block", () -> new RandomTeleportBlock(Block.Properties.copy(Blocks.OBSIDIAN).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)), CAItemGroups.blocksItemGroup);

	// MINERAL ORES
	public static final RegistryObject<CAOreBlock> AMETHYST_ORE = registerBlock("amethyst_ore", () -> new CAOreBlock(AbstractBlock.Properties.of(Material.STONE)
		.strength(4.5F,3.25F).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()
		.sound(SoundType.STONE)).withExpDrop(3, 7), CAItemGroups.blocksItemGroup);

	public static final RegistryObject<CAOreBlock> RUBY_ORE = registerBlock("ruby_ore", () -> new CAOreBlock(AbstractBlock.Properties.of(Material.STONE)
		.strength(6.5F,3.25F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()
		.sound(SoundType.STONE)).withExpDrop(4, 9), CAItemGroups.blocksItemGroup);

	public static final RegistryObject<CAOreBlock> NETHER_RUBY_ORE = registerBlock("nether_ruby_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE)
		.strength(6.5F,3F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()
		.sound(SoundType.NETHER_ORE)).withExpDrop(4, 9), CAItemGroups.blocksItemGroup);

	public static final RegistryObject<CAOreBlock> TIGERS_EYE_ORE = registerBlock("tigers_eye_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE)
		.strength(4F,3.25F).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()
		.sound(SoundType.STONE)).withExpDrop(2, 8), CAItemGroups.blocksItemGroup);

	public static final RegistryObject<CAOreBlock> TITANIUM_ORE = registerBlock("titanium_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE)
		.strength(9F, 3.5F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()
		.sound(SoundType.STONE)), CAItemGroups.blocksItemGroup);

	public static final RegistryObject<CAOreBlock> URANIUM_ORE = registerBlock("uranium_ore", () -> new UraniumOreBlock(Block.Properties.of(Material.STONE)
		.strength(7.5F,3.5F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().randomTicks()
		.lightLevel((state) -> state.getValue(UraniumOreBlock.GLOW_STRENGTH))
		.sound(SoundType.STONE)), CAItemGroups.blocksItemGroup);

	public static final RegistryObject<CAOreBlock> ALUMINUM_ORE = registerBlock("aluminum_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE)
		.strength(3F).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()
		.sound(SoundType.STONE)), CAItemGroups.blocksItemGroup);

	public static final RegistryObject<CAOreBlock> SALT_ORE = registerBlock("salt_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE)
		.strength(2.5F).harvestLevel(0).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()
		.sound(SoundType.STONE)).withExpDrop(0,3), CAItemGroups.blocksItemGroup);

	public static final RegistryObject<CAOreBlock> COPPER_ORE = registerBlock("copper_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.COAL_ORE).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> TIN_ORE = registerBlock("tin_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> SILVER_ORE = registerBlock("silver_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> PLATINUM_ORE = registerBlock("platinum_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.DIAMOND_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> SUNSTONE_ORE = registerBlock("sunstone_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.EMERALD_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE).lightLevel((state) -> 8)).withExpDrop(3, 6), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> BLOODSTONE_ORE = registerBlock("bloodstone_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.EMERALD_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)).withExpDrop(2, 5), CAItemGroups.blocksItemGroup);

	// INFESTED ORES
	public static final RegistryObject<AntInfestedOre> RED_ANT_INFESTED_ORE = registerBlock("red_ant_infested_ore", () -> new AntInfestedOre(CAEntityTypes.RED_ANT, Block.Properties.copy(Blocks.INFESTED_STONE).noDrops().harvestLevel(1)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<AntInfestedOre> TERMITE_INFESTED_ORE = registerBlock("termite_infested_ore", () -> new AntInfestedOre(CAEntityTypes.TERMITE, Block.Properties.copy(Blocks.INFESTED_STONE).noDrops().harvestLevel(1)), CAItemGroups.blocksItemGroup);

	// MOB ORES
	public static final RegistryObject<CAOreBlock> FOSSILISED_ENT = registerBlock("fossilised_ent", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()).withFossilExp(), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> FOSSILISED_HERCULES_BEETLE = registerBlock("fossilised_hercules_beetle", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()).withFossilExp(), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> FOSSILISED_RUBY_BUG = registerBlock("fossilised_ruby_bug", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()).withFossilExp(), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> FOSSILISED_EMERALD_GATOR = registerBlock("fossilised_emerald_gator", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()).withFossilExp(), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> FOSSILISED_WTF = registerBlock("fossilised_wtf", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()).withFossilExp(), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> FOSSILISED_SCORPION = registerBlock("fossilised_scorpion", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()).withFossilExp(), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> FOSSILISED_WASP = registerBlock("fossilised_wasp", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()).withFossilExp(), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CAOreBlock> FOSSILISED_PIRAPORU = registerBlock("fossilised_piraporu", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()).withFossilExp(), CAItemGroups.blocksItemGroup);

	// MINERAL BLOCKS
	public static final RegistryObject<Block> AMETHYST_BLOCK = registerBlock("amethyst_block", () -> new Block(Block.Properties.copy(Blocks.EMERALD_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block", () -> new Block(Block.Properties.copy(Blocks.ANCIENT_DEBRIS).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TIGERS_EYE_BLOCK = registerBlock("tigers_eye_block", () -> new Block(Block.Properties.copy(Blocks.EMERALD_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block", () -> new Block(Block.Properties.copy(Blocks.NETHERITE_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> URANIUM_BLOCK = registerBlock("uranium_block", () -> new Block(Block.Properties.copy(Blocks.NETHERITE_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> ALUMINUM_BLOCK = registerBlock("aluminum_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> COPPER_BLOCK = registerBlock("copper_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> PLATINUM_BLOCK = registerBlock("platinum_block", () -> new Block(Block.Properties.copy(Blocks.DIAMOND_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> SUNSTONE_BLOCK = registerBlock("sunstone_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL).lightLevel((state) -> 15)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> BLOODSTONE_BLOCK = registerBlock("bloodstone_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> PINK_TOURMALINE_BLOCK = registerBlock("pink_tourmaline_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> CATS_EYE_BLOCK = registerBlock("cats_eye_block", () -> new Block(Block.Properties.copy(Blocks.DIAMOND_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.blocksItemGroup);

	// MOB DROP BLOCKS
	public static final RegistryObject<Block> ENDER_PEARL_BLOCK = registerBlock("ender_pearl_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> ENDER_EYE_BLOCK = registerBlock("ender_eye_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.blocksItemGroup);

	// LEGACY CRYSTAL DIMENSION
	public static final RegistryObject<Block> CRYSTAL_GRASS_BLOCK = registerBlock("crystal_grass_block", () -> new Block(Block.Properties.copy(Blocks.GRASS_BLOCK).requiresCorrectToolForDrops().isRedstoneConductor(isFalse)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> KYANITE = registerBlock("kyanite", () -> new Block(Block.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().isRedstoneConductor(isFalse)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> CRYSTAL_LOG = registerBlock("crystal_log", () -> new RotatedPillarBlock(Block.Properties.copy(Blocks.OAK_LOG).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> CRYSTAL_WOOD = registerBlock("crystal_wood", () -> new RotatedPillarBlock(Block.Properties.copy(Blocks.OAK_WOOD).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> CRYSTAL_WOOD_PLANKS = registerBlock("crystal_wood_planks", () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RED_CRYSTAL_LEAVES = registerBlock("red_crystal_leaves", () -> new Block(Block.Properties.copy(Blocks.OAK_LEAVES).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> GREEN_CRYSTAL_LEAVES = registerBlock("green_crystal_leaves", () -> new Block(Block.Properties.copy(Blocks.OAK_LEAVES).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> YELLOW_CRYSTAL_LEAVES = registerBlock("yellow_crystal_leaves", () -> new Block(Block.Properties.copy(Blocks.OAK_LEAVES).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CrystalClusterBlock> PINK_TOURMALINE_CLUSTER = registerBlock("pink_tourmaline_cluster", () -> new CrystalClusterBlock(Block.Properties.copy(Blocks.IRON_ORE).noOcclusion().isSuffocating(isFalse).isViewBlocking(isFalse).isRedstoneConductor(isFalse).harvestLevel(1)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CrystalClusterBlock> CATS_EYE_CLUSTER = registerBlock("cats_eye_cluster", () -> new CrystalClusterBlock(Block.Properties.copy(Blocks.IRON_ORE).noOcclusion().isSuffocating(isFalse).isViewBlocking(isFalse).isRedstoneConductor(isFalse).harvestLevel(2)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CrystalEnergyBlock> CRYSTAL_ENERGY = registerBlock("crystal_energy", () -> new CrystalEnergyBlock(Block.Properties.copy(Blocks.GLASS).noOcclusion().isSuffocating(isFalse).isViewBlocking(isFalse).isRedstoneConductor(isFalse).harvestLevel(0).lightLevel((state) -> 8)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<BuddingBlock> BUDDING_PINK_TOURMALINE = registerBlock("budding_pink_tourmaline", () -> new BuddingBlock(Block.Properties.copy(Blocks.STONE).randomTicks().isRedstoneConductor(isFalse).noOcclusion(), PINK_TOURMALINE_CLUSTER.get()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<BuddingBlock> BUDDING_CATS_EYE = registerBlock("budding_cats_eye", () -> new BuddingBlock(Block.Properties.copy(Blocks.STONE).randomTicks().isRedstoneConductor(isFalse).noOcclusion(), CATS_EYE_CLUSTER.get()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CrystalCraftingTableBlock> CRYSTAL_CRAFTING_TABLE = registerBlock("crystal_crafting_table", () -> new CrystalCraftingTableBlock(Block.Properties.copy(Blocks.CRAFTING_TABLE).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CrystalFurnaceBlock> CRYSTAL_FURNACE = registerBlock("crystal_furnace", () -> new CrystalFurnaceBlock(Block.Properties.copy(Blocks.FURNACE).isRedstoneConductor(isFalse).noOcclusion().lightLevel(lightValueFunction.apply(13))), CAItemGroups.blocksItemGroup);

	// ANT NESTS
	public static final RegistryObject<AntNestBlock> BROWN_ANT_NEST = registerBlock("brown_ant_nest", () -> new AntNestBlock(CAEntityTypes.BROWN_ANT, Block.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<AntNestBlock> RAINBOW_ANT_NEST = registerBlock("rainbow_ant_nest", () -> new AntNestBlock(CAEntityTypes.RAINBOW_ANT, Block.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<AntNestBlock> RED_ANT_NEST = registerBlock("red_ant_nest", () -> new AntNestBlock(CAEntityTypes.RED_ANT, Block.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<AntNestBlock> UNSTABLE_ANT_NEST = registerBlock("unstable_ant_nest", () -> new AntNestBlock(CAEntityTypes.UNSTABLE_ANT, Block.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<AntNestBlock> TERMITE_NEST = registerBlock("termite_nest", () -> new AntNestBlock(CAEntityTypes.TERMITE, Block.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<AntNestBlock> CRYSTAL_TERMITE_NEST = registerBlock("crystal_termite_nest", () -> new AntNestBlock(CAEntityTypes.TERMITE, Block.Properties.copy(CABlocks.CRYSTAL_GRASS_BLOCK.get()).randomTicks()), CAItemGroups.blocksItemGroup);

	// BOSS SPAWNERS
	public static final RegistryObject<BossSpawnerBlock> ENT_BOSS_SPAWNER = registerBlock("ent_boss_spawner", () -> new BossSpawnerBlock(CAEntityTypes.OAK_ENT, Block.Properties.copy(Blocks.SPAWNER).strength(-1.0F).noOcclusion().noDrops().sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(2).randomTicks()), CAItemGroups.blocksItemGroup);

	// TORCHES
	public static final RegistryObject<TorchBlock> CRYSTAL_TORCH = registerBlock("crystal_torch", () -> new TorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 14).sound(SoundType.WOOD), ParticleTypes.FLAME), null, false);
	public static final RegistryObject<WallTorchBlock> WALL_CRYSTAL_TORCH = registerBlock("wall_crystal_torch", () -> new WallTorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 14).sound(SoundType.WOOD).lootFrom(CABlocks.CRYSTAL_TORCH), ParticleTypes.FLAME), null, false);
	public static final RegistryObject<TorchBlock> SUNSTONE_TORCH = registerBlock("sunstone_torch", () -> new TorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 12).sound(SoundType.WOOD), ParticleTypes.END_ROD), null, false);
	public static final RegistryObject<WallTorchBlock> WALL_SUNSTONE_TORCH = registerBlock("wall_sunstone_torch", () -> new WallTorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 12).sound(SoundType.WOOD).lootFrom(CABlocks.SUNSTONE_TORCH), ParticleTypes.END_ROD), null, false);
	public static final RegistryObject<TorchBlock> EXTREME_TORCH = registerBlock("extreme_torch", () -> new TorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 15).sound(SoundType.WOOD), ParticleTypes.FLAME), null, false);
	public static final RegistryObject<WallTorchBlock> WALL_EXTREME_TORCH = registerBlock("wall_extreme_torch", () -> new WallTorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 15).sound(SoundType.WOOD).lootFrom(CABlocks.EXTREME_TORCH), ParticleTypes.FLAME), null, false);

	//MINERS DREAM BLOCKS
	public static final RegistryObject<Block> MOULDY_PLANKS = registerBlock("mouldy_planks", () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<SlabBlock> MOULDY_SLAB = registerBlock("mouldy_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_SLAB)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<FenceBlock> MOULDY_FENCE = registerBlock("mouldy_fence", () -> new FenceBlock(Block.Properties.copy(Blocks.OAK_FENCE)), CAItemGroups.blocksItemGroup);

	/**
	 * No stack size and generateItem variant, defaults to 64 and true, respectively
	 * @param <B> Block type
	 * @param name Block registry name
	 * @param supplier Block instance supplier
	 * @param itemGroup Block item group, {@code null} for no item group
	 * @return A RegistryObject<B>
	 */
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
		return registerBlock(name, supplier, itemGroup, true);
	}

	/**
	 * No stack size variant, defaults to 64
	 * @param <B> Block type
	 * @param name Block registry name
	 * @param supplier Block instance supplier
	 * @param itemGroup Block item group, {@code null} for no item group
	 * @param generateItem If a BlockItem should be generated for this block
	 * @return A RegistryObject<B>
	 */
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, boolean generateItem) {
		return registerBlock(name, supplier, itemGroup, 64, generateItem);
	}

	/**
	 * No generateItem variant, defaults to true
	 * @param <B> Block type
	 * @param name Block registry name
	 * @param supplier Block instance supplier
	 * @param itemGroup Block item group, {@code null} for no item group
	 * @param stackSize Block stack size
	 * @return A RegistryObject<B>
	 */
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, int stackSize) {
		return registerBlock(name, supplier, itemGroup, stackSize, true);
	}

	/**
	 * Registers a block to the BLOCKS deferred register and BlockItem to the ITEM_BLOCKS deferred register
	 * @param <B> Block type
	 * @param name Block registry name
	 * @param supplier Block instance supplier
	 * @param itemGroup Block item group, {@code null} for no item group
	 * @param stackSize Block stack size
	 * @param generateItem If a BlockItem should be generated for this block
	 * @return A RegistryObject<B>
	 */
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, int stackSize, boolean generateItem) {
		RegistryObject<B> block = CABlocks.BLOCKS.register(name, supplier);
		if (generateItem)ITEM_BLOCKS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup).stacksTo(stackSize)));
		return block;
	}

	public static <E extends Block> RegistryObject<E> registerEnchantedBlock(String name, Supplier<? extends E> supplier, ItemGroup itemGroup, int stackSize) {
		RegistryObject<E> block = CABlocks.BLOCKS.register(name, supplier);
		ITEM_BLOCKS.register(name, () -> new EnchantedBlockItem(block.get(), new Item.Properties().tab(itemGroup).stacksTo(stackSize)));
		return block;
	}
}
