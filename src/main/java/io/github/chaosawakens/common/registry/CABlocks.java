package io.github.chaosawakens.common.registry;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.*;
import io.github.chaosawakens.common.items.EnchantedBlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.IPositionPredicate;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
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
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CABlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosAwakens.MODID);
	public static final DeferredRegister<Item> ITEM_BLOCKS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);
	
	private static IPositionPredicate isFalse = (state, reader, pos) -> false;
//	private static IPositionPredicate isTrue = (state, reader, pos) -> true; --Unused
	
	private static Function<Integer, ToIntFunction<BlockState>> lightValueFunction = (lightValue) -> (state) -> state.get(BlockStateProperties.LIT) ? lightValue : 0;
	
	// SHINY FOODS
	public static final RegistryObject<Block> GOLDEN_MELON = registerBlock("golden_melon", () -> new GoldenMelonBlock(Block.Properties.from(Blocks.MELON).harvestTool(ToolType.AXE)), CAItemGroups.foodItemGroup);
	public static final RegistryObject<Block> ATTACHED_GOLDEN_MELON_STEM = registerBlock("attached_golden_melon_stem", () -> new AttachedStemBlock((StemGrownBlock) GOLDEN_MELON.get(), AbstractBlock.Properties.from(Blocks.ATTACHED_MELON_STEM)), null, false);
	public static final RegistryObject<Block> GOLDEN_MELON_STEM = registerBlock("golden_melon_stem", () -> new StemBlock((StemGrownBlock) GOLDEN_MELON.get(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.STEM)), null, false);
	public static final RegistryObject<GoldenCakeBlock> GOLDEN_CAKE = registerBlock("golden_cake", () -> new GoldenCakeBlock(Block.Properties.from(Blocks.CAKE).noDrops()), CAItemGroups.foodItemGroup, 1);
	public static final RegistryObject<EnchantedGoldenCakeBlock> ENCHANTED_GOLDEN_CAKE = registerEnchantedBlock("enchanted_golden_cake", () -> new EnchantedGoldenCakeBlock(Block.Properties.from(Blocks.CAKE).noDrops()), CAItemGroups.foodItemGroup, 1);
	
	// PLANTS
	public static final RegistryObject<Block> CORN_PLANT = registerBlock("corn_plant", () -> new CornBlock(Block.Properties.from(Blocks.SUGAR_CANE).tickRandomly()), CAItemGroups.foodItemGroup, false);
	public static final RegistryObject<Block> TOMATO_PLANT = registerBlock("tomato_plant", () -> new TomatoBlock(Block.Properties.from(Blocks.SUGAR_CANE).tickRandomly()), CAItemGroups.foodItemGroup, false);
	public static final RegistryObject<Block> STRAWBERRY_PLANT = registerBlock("strawberry_plant", () -> new StrawberryBushBlock( CAItems.STRAWBERRY_SEEDS, CAItems.STRAWBERRY, Block.Properties.from(Blocks.SWEET_BERRY_BUSH).tickRandomly()), CAItemGroups.foodItemGroup, false);
	
	// DUNGEON BLOCKS
	public static final RegistryObject<GateBlock> GATE_BLOCK = registerBlock("gate_block", () -> new GateBlock(Block.Properties.from(Blocks.OAK_PLANKS).hardnessAndResistance(-1.0F, 3600000.0F)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CASpawnerBlock> SPAWNER_BLOCK = registerBlock("spawner_block", () -> new CASpawnerBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(-1.0F).notSolid().noDrops().sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(2)), null);
	public static final RegistryObject<RandomTeleportBlock> RANDOM_TELEPORT_BLOCK = registerBlock("random_teleport_block", () -> new RandomTeleportBlock(Block.Properties.from(Blocks.OBSIDIAN).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.STONE)), CAItemGroups.blocksItemGroup);
	
	// MINERAL ORES
	public static final RegistryObject<Block> AMETHYST_ORE = registerBlock("amethyst_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.EMERALD_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.STONE)).withExpDrop((rand) -> MathHelper.nextInt(rand, 3, 7)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RUBY_ORE = registerBlock("ruby_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.ANCIENT_DEBRIS).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.STONE)).withExpDrop((rand) -> MathHelper.nextInt(rand, 4, 9)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> NETHER_RUBY_ORE = registerBlock("nether_ruby_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.ANCIENT_DEBRIS).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.STONE)).withExpDrop((rand) -> MathHelper.nextInt(rand, 4, 9)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TIGERS_EYE_ORE = registerBlock("tigers_eye_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.EMERALD_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()).withExpDrop((rand) -> MathHelper.nextInt(rand, 4, 8)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.OBSIDIAN).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> URANIUM_ORE = registerBlock("uranium_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.OBSIDIAN).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> ALUMINUM_ORE = registerBlock("aluminum_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> SALT_ORE = registerBlock("salt_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.COAL_ORE).harvestLevel(0).harvestTool(ToolType.PICKAXE).setRequiresTool()).withFossilExp(), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> COPPER_ORE = registerBlock("copper_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.COAL_ORE).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TIN_ORE = registerBlock("tin_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> SILVER_ORE = registerBlock("silver_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.STONE)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> PLATINUM_ORE = registerBlock("platinum_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.DIAMOND_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.STONE)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> SUNSTONE_ORE = registerBlock("sunstone_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.EMERALD_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.STONE).setLightLevel((state) -> 8)).withExpDrop((rand) -> MathHelper.nextInt(rand, 3, 6)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> BLOODSTONE_ORE = registerBlock("bloodstone_ore", () -> new CAOreBlock(Block.Properties.from(Blocks.EMERALD_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.STONE)).withExpDrop((rand) -> MathHelper.nextInt(rand, 2, 5)), CAItemGroups.blocksItemGroup);
	
	// INFESTED ORES
	public static final RegistryObject<Block> RED_ANT_INFESTED_ORE = registerBlock("red_ant_infested_ore", () -> new RedAntInfestedOre(Block.Properties.from(Blocks.INFESTED_STONE).noDrops().harvestLevel(1)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TERMITE_INFESTED_ORE = registerBlock("termite_infested_ore", () -> new TermiteInfestedOre(Block.Properties.from(Blocks.INFESTED_STONE).noDrops().harvestLevel(1)), CAItemGroups.blocksItemGroup);
	
	// MOB ORES
	public static final RegistryObject<Block> FOSSILISED_ENT = registerBlock("fossilised_ent", () -> new CAOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1).setRequiresTool()).withFossilExp(), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> FOSSILISED_HERCULES_BEETLE = registerBlock("fossilised_hercules_beetle", () -> new CAOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1).setRequiresTool()).withFossilExp(), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> FOSSILISED_RUBY_BUG = registerBlock("fossilised_ruby_bug", () -> new CAOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1).setRequiresTool()).withFossilExp(), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> FOSSILISED_EMERALD_GATOR = registerBlock("fossilised_emerald_gator", () -> new CAOreBlock(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1).setRequiresTool()).withFossilExp(), CAItemGroups.blocksItemGroup);
	
	// MINERAL BLOCKS
	public static final RegistryObject<Block> AMETHYST_BLOCK = registerBlock("amethyst_block", () -> new Block(Block.Properties.from(Blocks.EMERALD_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.METAL)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block", () -> new Block(Block.Properties.from(Blocks.ANCIENT_DEBRIS).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.METAL)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TIGERS_EYE_BLOCK = registerBlock("tigers_eye_block", () -> new Block(Block.Properties.from(Blocks.EMERALD_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block", () -> new Block(Block.Properties.from(Blocks.NETHERITE_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.NETHERITE)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> URANIUM_BLOCK = registerBlock("uranium_block", () -> new Block(Block.Properties.from(Blocks.NETHERITE_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.NETHERITE)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> ALUMINUM_BLOCK = registerBlock("aluminum_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> COPPER_BLOCK = registerBlock("copper_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.METAL)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> PLATINUM_BLOCK = registerBlock("platinum_block", () -> new Block(Block.Properties.from(Blocks.DIAMOND_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.NETHERITE)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> SUNSTONE_BLOCK = registerBlock("sunstone_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.METAL).setLightLevel((state) -> 15)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> BLOODSTONE_BLOCK = registerBlock("bloodstone_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.METAL)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> PINK_TOURMALINE_BLOCK = registerBlock("pink_tourmaline_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool().setOpaque(isFalse).notSolid()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> CATS_EYE_BLOCK = registerBlock("cats_eye_block", () -> new Block(Block.Properties.from(Blocks.DIAMOND_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().setOpaque(isFalse).notSolid()), CAItemGroups.blocksItemGroup);
	
	// MOB DROP BLOCKS
	public static final RegistryObject<Block> ENDER_PEARL_BLOCK = registerBlock("ender_pearl_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.SHROOMLIGHT)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> ENDER_EYE_BLOCK = registerBlock("ender_eye_block", () -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.SHROOMLIGHT)), CAItemGroups.blocksItemGroup);
	
	// ANT NESTS
	public static final RegistryObject<Block> BROWN_ANT_NEST = registerBlock("brown_ant_nest", () -> new AntNestBlock(CAEntityTypes.BROWN_ANT, Block.Properties.from(Blocks.GRASS_BLOCK)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RAINBOW_ANT_NEST = registerBlock("rainbow_ant_nest", () -> new AntNestBlock(CAEntityTypes.RAINBOW_ANT, Block.Properties.from(Blocks.GRASS_BLOCK)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RED_ANT_NEST = registerBlock("red_ant_nest", () -> new AntNestBlock(CAEntityTypes.RED_ANT, Block.Properties.from(Blocks.GRASS_BLOCK)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> UNSTABLE_ANT_NEST = registerBlock("unstable_ant_nest", () -> new AntNestBlock(CAEntityTypes.UNSTABLE_ANT, Block.Properties.from(Blocks.GRASS_BLOCK)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> TERMITE_NEST = registerBlock("termite_nest", () -> new AntNestBlock(CAEntityTypes.TERMITE, Block.Properties.from(Blocks.GRASS_BLOCK)), CAItemGroups.blocksItemGroup);
	
	// LEGACY CRYSTAL DIMENSION
	public static final RegistryObject<Block> CRYSTAL_GRASS_BLOCK = registerBlock("crystal_grass_block", () -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK).setRequiresTool().setOpaque(isFalse)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> KYANITE = registerBlock("kyanite", () -> new Block(Block.Properties.from(Blocks.STONE).setRequiresTool().setOpaque(isFalse)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> CRYSTAL_LOG = registerBlock("crystal_log", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.OAK_LOG).setOpaque(isFalse).notSolid()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<RotatedPillarBlock> CRYSTAL_WOOD = registerBlock("crystal_wood", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.OAK_WOOD).setOpaque(isFalse).notSolid()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> CRYSTAL_WOOD_PLANKS = registerBlock("crystal_wood_planks", () -> new Block(Block.Properties.from(Blocks.OAK_PLANKS).setOpaque(isFalse).notSolid()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> RED_CRYSTAL_LEAVES = registerBlock("red_crystal_leaves", () -> new Block(Block.Properties.from(Blocks.OAK_LEAVES).setOpaque(isFalse).notSolid()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> GREEN_CRYSTAL_LEAVES = registerBlock("green_crystal_leaves", () -> new Block(Block.Properties.from(Blocks.OAK_LEAVES).setOpaque(isFalse).notSolid()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> YELLOW_CRYSTAL_LEAVES = registerBlock("yellow_crystal_leaves", () -> new Block(Block.Properties.from(Blocks.OAK_LEAVES).setOpaque(isFalse).notSolid()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CrystalClusterBlock> PINK_TOURMALINE_CLUSTER = registerBlock("pink_tourmaline_cluster", () -> new CrystalClusterBlock(Block.Properties.from(Blocks.IRON_ORE).notSolid().setSuffocates(isFalse).setBlocksVision(isFalse).setOpaque(isFalse).harvestLevel(1)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CrystalClusterBlock> CATS_EYE_CLUSTER = registerBlock("cats_eye_cluster", () -> new CrystalClusterBlock(Block.Properties.from(Blocks.IRON_ORE).notSolid().setSuffocates(isFalse).setBlocksVision(isFalse).setOpaque(isFalse).harvestLevel(2)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<Block> CRYSTAL_ENERGY = registerBlock("crystal_energy", () -> new CrystalEnergyBlock(Block.Properties.from(Blocks.GLASS).notSolid().setSuffocates(isFalse).setBlocksVision(isFalse).setOpaque(isFalse).harvestLevel(0).setLightLevel((state) -> 8)), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<BuddingBlock> BUDDING_PINK_TOURMALINE = registerBlock("budding_pink_tourmaline", () -> new BuddingBlock(Block.Properties.from(Blocks.STONE).tickRandomly().setOpaque(isFalse).notSolid(), PINK_TOURMALINE_CLUSTER.get()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<BuddingBlock> BUDDING_CATS_EYE = registerBlock("budding_cats_eye", () -> new BuddingBlock(Block.Properties.from(Blocks.STONE).tickRandomly().setOpaque(isFalse).notSolid(), CATS_EYE_CLUSTER.get()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CrystalCraftingTableBlock> CRYSTAL_CRAFTING_TABLE = registerBlock("crystal_crafting_table", () -> new CrystalCraftingTableBlock(Block.Properties.from(Blocks.CRAFTING_TABLE).setOpaque(isFalse).notSolid()), CAItemGroups.blocksItemGroup);
	public static final RegistryObject<CrystalFurnaceBlock> CRYSTAL_FURNACE = registerBlock("crystal_furnace", () -> new CrystalFurnaceBlock(Block.Properties.from(Blocks.FURNACE).setOpaque(isFalse).notSolid().setLightLevel(lightValueFunction.apply(13))), CAItemGroups.blocksItemGroup);

	// TORCHES
	public static final RegistryObject<Block> CRYSTAL_TORCH = registerBlock("crystal_torch", () -> new TorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((state) -> 14).sound(SoundType.WOOD), ParticleTypes.FLAME), null, false);
	public static final RegistryObject<Block> WALL_CRYSTAL_TORCH = registerBlock("wall_crystal_torch", () -> new WallTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((state) -> 14).sound(SoundType.WOOD).lootFrom(() -> CABlocks.CRYSTAL_TORCH.get()), ParticleTypes.FLAME), null, false);
	public static final RegistryObject<Block> SUNSTONE_TORCH = registerBlock("sunstone_torch", () -> new TorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((state) -> 12).sound(SoundType.WOOD), ParticleTypes.END_ROD), null, false);
	public static final RegistryObject<Block> WALL_SUNSTONE_TORCH = registerBlock("wall_sunstone_torch", () -> new WallTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((state) -> 12).sound(SoundType.WOOD).lootFrom(() -> CABlocks.SUNSTONE_TORCH.get()), ParticleTypes.END_ROD), null, false);
	public static final RegistryObject<Block> EXTREME_TORCH = registerBlock("extreme_torch", () -> new TorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((state) -> 15).sound(SoundType.WOOD), ParticleTypes.FLAME), null, false);
	public static final RegistryObject<Block> WALL_EXTREME_TORCH = registerBlock("wall_extreme_torch", () -> new WallTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((state) -> 15).sound(SoundType.WOOD).lootFrom(() -> CABlocks.EXTREME_TORCH.get()), ParticleTypes.FLAME), null, false);

	// MOB HEADS
//	public static final RegistryObject<CASkullBlock> HUSK_HEAD = BLOCKS.register("husk_head", () -> new CASkullBlock(CASkullBlock.Types.HUSK, AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F)));
//	public static final RegistryObject<CASkullWallBlock> HUSK_WALL_HEAD = BLOCKS.register("husk_wall_head", () -> new CASkullWallBlock(CASkullBlock.Types.HUSK, AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F).lootFrom(HUSK_HEAD)));

	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
		return registerBlock(name, supplier, itemGroup, true);
	}
	
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, boolean generateItem) {
		return registerBlock(name, supplier, itemGroup, 64, generateItem);
	}
	
	public static <B extends Block> RegistryObject<B> registerBlockAlt(String name, Supplier<? extends B> supplier, boolean generateItem) {
		return registerBlockAlt(name, supplier, generateItem);
	}
	
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, int stackSize) {
		return registerBlock(name, supplier, itemGroup, stackSize, true);
	}
	
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, int stackSize, boolean generateItem) {
		RegistryObject<B> block = CABlocks.BLOCKS.register(name, supplier);
		if (generateItem)ITEM_BLOCKS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(itemGroup).maxStackSize(stackSize)));
		return block;
	}
	
	public static <E extends Block> RegistryObject<E> registerEnchantedBlock(String name, Supplier<? extends E> supplier, ItemGroup itemGroup, int stackSize) {
		RegistryObject<E> block = CABlocks.BLOCKS.register(name, supplier);
		ITEM_BLOCKS.register(name, () -> new EnchantedBlockItem(block.get(), new Item.Properties().group(itemGroup).maxStackSize(stackSize)));
		return block;
	}
}
