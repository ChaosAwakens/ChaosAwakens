package io.github.chaosawakens.common.registry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.AntInfestedOre;
import io.github.chaosawakens.common.blocks.AntNestBlock;
import io.github.chaosawakens.common.blocks.BuddingBlock;
import io.github.chaosawakens.common.blocks.CACropsBlock;
import io.github.chaosawakens.common.blocks.CAFallingOreBlock;
import io.github.chaosawakens.common.blocks.CAOreBlock;
import io.github.chaosawakens.common.blocks.CAStandingSignBlock;
import io.github.chaosawakens.common.blocks.CAWallSignBlock;
import io.github.chaosawakens.common.blocks.CropBodyPlantBlock;
import io.github.chaosawakens.common.blocks.CropTopPlantBlock;
import io.github.chaosawakens.common.blocks.CrystalAntNestBlock;
import io.github.chaosawakens.common.blocks.CrystalBlock;
import io.github.chaosawakens.common.blocks.CrystalClusterBlock;
import io.github.chaosawakens.common.blocks.CrystalCraftingTableBlock;
import io.github.chaosawakens.common.blocks.CrystalEnergyBlock;
import io.github.chaosawakens.common.blocks.CrystalFlowerBlock;
import io.github.chaosawakens.common.blocks.CrystalFurnaceBlock;
import io.github.chaosawakens.common.blocks.CrystalGrassBlock;
import io.github.chaosawakens.common.blocks.CrystalSaplingBlock;
import io.github.chaosawakens.common.blocks.DenseFlowerBlock;
import io.github.chaosawakens.common.blocks.DenseGrassBlock;
import io.github.chaosawakens.common.blocks.DoubleCrystalPlantBlock;
import io.github.chaosawakens.common.blocks.DoubleDensePlantBlock;
import io.github.chaosawakens.common.blocks.FruitableLeavesBlock;
import io.github.chaosawakens.common.blocks.GateBlock;
import io.github.chaosawakens.common.blocks.GenericFarmlandBlock;
import io.github.chaosawakens.common.blocks.LeafCarpetBlock;
import io.github.chaosawakens.common.blocks.RandomTeleportBlock;
import io.github.chaosawakens.common.blocks.RotatedPillarCrystalBlock;
import io.github.chaosawakens.common.blocks.SaltBlock;
import io.github.chaosawakens.common.blocks.StrawberryBushBlock;
import io.github.chaosawakens.common.blocks.TallCrystalGrassBlock;
import io.github.chaosawakens.common.blocks.TallDenseGrassBlock;
import io.github.chaosawakens.common.blocks.TopTubeBlock;
import io.github.chaosawakens.common.blocks.TubeBlock;
import io.github.chaosawakens.common.blocks.UraniumOreBlock;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerBlock;
import io.github.chaosawakens.common.blocks.tileentities.RoboCrateBlock;
import io.github.chaosawakens.common.blocks.trees.CATree;
import io.github.chaosawakens.common.blocks.trees.FancyableTree;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.IPositionPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("all")
public class CABlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosAwakens.MODID);
	public static final DeferredRegister<Item> ITEM_BLOCKS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);

	public static final WoodType APPLE = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "apple").toString());
	public static final WoodType CHERRY = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "cherry").toString());
	public static final WoodType DUPLICATION = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "duplication").toString());
	public static final WoodType GINKGO = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "ginkgo").toString());
	public static final WoodType PEACH = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "peach").toString());
	public static final WoodType SKYWOOD = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "skywood").toString());

	private static final IPositionPredicate isFalse = (state, reader, pos) -> false;

	private static final Function<Integer, ToIntFunction<BlockState>> lightValueFunction = (lightValue) -> (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;

	// TREES
	public static final RegistryObject<Block> APPLE_PLANKS = registerBlock("apple_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<SaplingBlock> APPLE_SAPLING = registerBlock("apple_sapling", () -> new SaplingBlock(new FancyableTree(() -> CAConfiguredFeatures.FANCY_APPLE_TREE, () -> CAConfiguredFeatures.FANCY_APPLE_TREE_BEES_005, () -> CAConfiguredFeatures.APPLE_TREE, () -> CAConfiguredFeatures.APPLE_TREE_BEES_005), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> APPLE_LOG = registerBlock("apple_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_APPLE_LOG = registerBlock("stripped_apple_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_APPLE_WOOD = registerBlock("stripped_apple_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> APPLE_WOOD = registerBlock("apple_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<FruitableLeavesBlock> APPLE_LEAVES = registerBlock("apple_leaves", () -> new FruitableLeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES), () -> Items.APPLE, 1, 1), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> APPLE_LEAF_CARPET = registerBlock("apple_leaf_carpet", () -> new LeafCarpetBlock(AbstractBlock.Properties.copy(APPLE_LEAVES.get()).noCollission()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> APPLE_SLAB = registerBlock("apple_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.APPLE_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<PressurePlateBlock> APPLE_PRESSURE_PLATE = registerBlock("apple_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.copy(CABlocks.APPLE_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceBlock> APPLE_FENCE = registerBlock("apple_fence", () -> new FenceBlock(AbstractBlock.Properties.copy(CABlocks.APPLE_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceGateBlock> APPLE_FENCE_GATE = registerBlock("apple_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.copy(CABlocks.APPLE_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<WoodButtonBlock> APPLE_BUTTON = registerBlock("apple_button", () -> new WoodButtonBlock(AbstractBlock.Properties.copy(CABlocks.APPLE_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> APPLE_STAIRS = registerBlock("apple_stairs", () -> new StairsBlock(APPLE_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.APPLE_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<CAStandingSignBlock> APPLE_SIGN = registerBlock("apple_sign", () -> new CAStandingSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), APPLE), null, false);
	public static final RegistryObject<CAWallSignBlock> APPLE_WALL_SIGN = registerBlock("apple_wall_sign", () -> new CAWallSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(APPLE_SIGN.get()), APPLE), null, false);
	public static final RegistryObject<TrapDoorBlock> APPLE_TRAPDOOR = registerBlock("apple_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(CABlocks::never)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DoorBlock> APPLE_DOOR = registerBlock("apple_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()), CAItemGroups.BLOCKS);
	
	public static final RegistryObject<Block> CHERRY_PLANKS = registerBlock("cherry_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(2.0F, 3.0F).sound(SoundType.WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<SaplingBlock> CHERRY_SAPLING = registerBlock("cherry_sapling", () -> new SaplingBlock(new FancyableTree(() -> CAConfiguredFeatures.FANCY_CHERRY_TREE, () -> CAConfiguredFeatures.FANCY_CHERRY_TREE_BEES_005, () -> CAConfiguredFeatures.CHERRY_TREE, () -> CAConfiguredFeatures.CHERRY_TREE_BEES_005), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> CHERRY_LOG = registerBlock("cherry_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_CHERRY_LOG = registerBlock("stripped_cherry_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_CHERRY_WOOD = registerBlock("stripped_cherry_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> CHERRY_WOOD = registerBlock("cherry_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<FruitableLeavesBlock> CHERRY_LEAVES = registerBlock("cherry_leaves", () -> new FruitableLeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES), CAItems.CHERRIES, 2, 4), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> CHERRY_LEAF_CARPET = registerBlock("cherry_leaf_carpet", () -> new LeafCarpetBlock(AbstractBlock.Properties.copy(CHERRY_LEAVES.get()).noCollission()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CHERRY_SLAB = registerBlock("cherry_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.CHERRY_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<PressurePlateBlock> CHERRY_PRESSURE_PLATE = registerBlock("cherry_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.copy(CABlocks.CHERRY_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceBlock> CHERRY_FENCE = registerBlock("cherry_fence", () -> new FenceBlock(AbstractBlock.Properties.copy(CABlocks.CHERRY_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceGateBlock> CHERRY_FENCE_GATE = registerBlock("cherry_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.copy(CABlocks.CHERRY_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<WoodButtonBlock> CHERRY_BUTTON = registerBlock("cherry_button", () -> new WoodButtonBlock(AbstractBlock.Properties.copy(CABlocks.CHERRY_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CHERRY_STAIRS = registerBlock("cherry_stairs", () -> new StairsBlock(CHERRY_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.CHERRY_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<CAStandingSignBlock> CHERRY_SIGN = registerBlock("cherry_sign", () -> new CAStandingSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).noCollission().strength(1.0F).sound(SoundType.WOOD), CHERRY), null, false);
	public static final RegistryObject<CAWallSignBlock> CHERRY_WALL_SIGN = registerBlock("cherry_wall_sign", () -> new CAWallSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(CHERRY_SIGN.get()), CHERRY), null, false);
	public static final RegistryObject<TrapDoorBlock> CHERRY_TRAPDOOR = registerBlock("cherry_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(CABlocks::never)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DoorBlock> CHERRY_DOOR = registerBlock("cherry_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()), CAItemGroups.BLOCKS);
	
	public static final RegistryObject<Block> DUPLICATION_PLANKS = registerBlock("duplication_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(2.0F, 3.0F).sound(SoundType.WOOD)), CAItemGroups.BLOCKS);
	//	public static final RegistryObject<SaplingBlock> DUPLICATION_SAPLING = registerBlock("duplication_sapling", () -> new SaplingBlock(new FancyableTree(() -> ), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> DUPLICATION_LOG = registerBlock("duplication_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_DUPLICATION_LOG = registerBlock("stripped_duplication_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_DUPLICATION_WOOD = registerBlock("stripped_duplication_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> DUPLICATION_WOOD = registerBlock("duplication_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeavesBlock> DUPLICATION_LEAVES = registerBlock("duplication_leaves", () -> new LeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> DUPLICATION_LEAF_CARPET = registerBlock("duplication_leaf_carpet", () -> new LeafCarpetBlock(AbstractBlock.Properties.copy(DUPLICATION_LEAVES.get()).noCollission()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> DUPLICATION_SLAB = registerBlock("duplication_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.DUPLICATION_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<PressurePlateBlock> DUPLICATION_PRESSURE_PLATE = registerBlock("duplication_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.copy(CABlocks.DUPLICATION_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceBlock> DUPLICATION_FENCE = registerBlock("duplication_fence", () -> new FenceBlock(AbstractBlock.Properties.copy(CABlocks.DUPLICATION_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceGateBlock> DUPLICATION_FENCE_GATE = registerBlock("duplication_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.copy(CABlocks.DUPLICATION_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<WoodButtonBlock> DUPLICATION_BUTTON = registerBlock("duplication_button", () -> new WoodButtonBlock(AbstractBlock.Properties.copy(CABlocks.DUPLICATION_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> DUPLICATION_STAIRS = registerBlock("duplication_stairs", () -> new StairsBlock(DUPLICATION_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.DUPLICATION_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> DEAD_DUPLICATION_LOG = registerBlock("dead_duplication_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> DEAD_DUPLICATION_WOOD = registerBlock("dead_duplication_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_DEAD_DUPLICATION_LOG = registerBlock("stripped_dead_duplication_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_DEAD_DUPLICATION_WOOD = registerBlock("stripped_dead_duplication_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<CAStandingSignBlock> DUPLICATION_SIGN = registerBlock("duplication_sign", () -> new CAStandingSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).noCollission().strength(1.0F).sound(SoundType.WOOD), DUPLICATION), null, false);
	public static final RegistryObject<CAWallSignBlock> DUPLICATION_WALL_SIGN = registerBlock("duplication_wall_sign", () -> new CAWallSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(DUPLICATION_SIGN.get()), DUPLICATION), null, false);
	public static final RegistryObject<TrapDoorBlock> DUPLICATION_TRAPDOOR = registerBlock("duplication_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(CABlocks::never)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DoorBlock> DUPLICATION_DOOR = registerBlock("duplication_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(3.0F).sound(SoundType.WOOD).noOcclusion()), CAItemGroups.BLOCKS);
	
	public static final RegistryObject<Block> GINKGO_PLANKS = registerBlock("ginkgo_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)), CAItemGroups.BLOCKS);
	//	public static final RegistryObject<SaplingBlock> GINKGO_SAPLING = registerBlock("ginkgo_sapling", () -> new SaplingBlock(new FancyableTree(() -> ), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> GINKGO_LOG = registerBlock("ginkgo_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_GINKGO_LOG = registerBlock("stripped_ginkgo_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_GINKGO_WOOD = registerBlock("stripped_ginkgo_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> GINKGO_WOOD = registerBlock("ginkgo_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeavesBlock> GINKGO_LEAVES = registerBlock("ginkgo_leaves", () -> new LeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> GINKGO_LEAF_CARPET = registerBlock("ginkgo_leaf_carpet", () -> new LeafCarpetBlock(AbstractBlock.Properties.copy(GINKGO_LEAVES.get()).noCollission()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> GINKGO_SLAB = registerBlock("ginkgo_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.GINKGO_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<PressurePlateBlock> GINKGO_PRESSURE_PLATE = registerBlock("ginkgo_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.copy(CABlocks.GINKGO_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceBlock> GINKGO_FENCE = registerBlock("ginkgo_fence", () -> new FenceBlock(AbstractBlock.Properties.copy(CABlocks.GINKGO_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceGateBlock> GINKGO_FENCE_GATE = registerBlock("ginkgo_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.copy(CABlocks.GINKGO_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<WoodButtonBlock> GINKGO_BUTTON = registerBlock("ginkgo_button", () -> new WoodButtonBlock(AbstractBlock.Properties.copy(CABlocks.GINKGO_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> GINKGO_STAIRS = registerBlock("ginkgo_stairs", () -> new StairsBlock(GINKGO_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.GINKGO_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<CAStandingSignBlock> GINKGO_SIGN = registerBlock("ginkgo_sign", () -> new CAStandingSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), GINKGO), null, false);
	public static final RegistryObject<CAWallSignBlock> GINKGO_WALL_SIGN = registerBlock("ginkgo_wall_sign", () -> new CAWallSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(GINKGO_SIGN.get()), GINKGO), null, false);
	public static final RegistryObject<TrapDoorBlock> GINKGO_TRAPDOOR = registerBlock("ginkgo_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(CABlocks::never)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DoorBlock> GINKGO_DOOR = registerBlock("ginkgo_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(3.0F).sound(SoundType.WOOD).noOcclusion()), CAItemGroups.BLOCKS);
	
	public static final RegistryObject<Block> PEACH_PLANKS = registerBlock("peach_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).strength(2.0F, 3.0F).sound(SoundType.WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<SaplingBlock> PEACH_SAPLING = registerBlock("peach_sapling", () -> new SaplingBlock(new FancyableTree(() -> CAConfiguredFeatures.FANCY_PEACH_TREE, () -> CAConfiguredFeatures.FANCY_PEACH_TREE_BEES_005, () -> CAConfiguredFeatures.PEACH_TREE, () -> CAConfiguredFeatures.PEACH_TREE_BEES_005), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> PEACH_LOG = registerBlock("peach_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_PEACH_LOG = registerBlock("stripped_peach_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_PEACH_WOOD = registerBlock("stripped_peach_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> PEACH_WOOD = registerBlock("peach_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<FruitableLeavesBlock> PEACH_LEAVES = registerBlock("peach_leaves", () -> new FruitableLeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES), CAItems.PEACH, 1, 3), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> PEACH_LEAF_CARPET = registerBlock("peach_leaf_carpet", () -> new LeafCarpetBlock(AbstractBlock.Properties.copy(PEACH_LEAVES.get()).noCollission()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> PEACH_SLAB = registerBlock("peach_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.PEACH_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<PressurePlateBlock> PEACH_PRESSURE_PLATE = registerBlock("peach_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.copy(CABlocks.PEACH_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceBlock> PEACH_FENCE = registerBlock("peach_fence", () -> new FenceBlock(AbstractBlock.Properties.copy(CABlocks.PEACH_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceGateBlock> PEACH_FENCE_GATE = registerBlock("peach_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.copy(CABlocks.PEACH_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<WoodButtonBlock> PEACH_BUTTON = registerBlock("peach_button", () -> new WoodButtonBlock(AbstractBlock.Properties.copy(CABlocks.PEACH_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> PEACH_STAIRS = registerBlock("peach_stairs", () -> new StairsBlock(PEACH_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.PEACH_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<CAStandingSignBlock> PEACH_SIGN = registerBlock("peach_sign", () -> new CAStandingSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).noCollission().strength(1.0F).sound(SoundType.WOOD), PEACH), null, false);
	public static final RegistryObject<CAWallSignBlock> PEACH_WALL_SIGN = registerBlock("peach_wall_sign", () -> new CAWallSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(PEACH_SIGN.get()), PEACH), null, false);
	public static final RegistryObject<TrapDoorBlock> PEACH_TRAPDOOR = registerBlock("peach_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(CABlocks::never)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DoorBlock> PEACH_DOOR = registerBlock("peach_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(3.0F).sound(SoundType.WOOD).noOcclusion()), CAItemGroups.BLOCKS);

	public static final RegistryObject<Block> SKYWOOD_PLANKS = registerBlock("skywood_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_BLUE).strength(2.0F, 3.0F).sound(SoundType.WOOD)), CAItemGroups.BLOCKS);
	//	public static final RegistryObject<SaplingBlock> SKYWOOD_SAPLING = registerBlock("skywood_sapling", () -> new SaplingBlock(new FancyableTree(() -> ), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> SKYWOOD_LOG = registerBlock("skywood_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYWOOD_LOG = registerBlock("stripped_skywood_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_SKYWOOD_WOOD = registerBlock("stripped_skywood_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> SKYWOOD_WOOD = registerBlock("skywood_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeavesBlock> SKYWOOD_LEAVES = registerBlock("skywood_leaves", () -> new LeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> SKYWOOD_LEAF_CARPET = registerBlock("skywood_leaf_carpet", () -> new LeafCarpetBlock(AbstractBlock.Properties.copy(SKYWOOD_LEAVES.get()).noCollission()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> SKYWOOD_SLAB = registerBlock("skywood_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.SKYWOOD_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<PressurePlateBlock> SKYWOOD_PRESSURE_PLATE = registerBlock("skywood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.copy(CABlocks.SKYWOOD_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceBlock> SKYWOOD_FENCE = registerBlock("skywood_fence", () -> new FenceBlock(AbstractBlock.Properties.copy(CABlocks.SKYWOOD_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceGateBlock> SKYWOOD_FENCE_GATE = registerBlock("skywood_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.copy(CABlocks.SKYWOOD_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<WoodButtonBlock> SKYWOOD_BUTTON = registerBlock("skywood_button", () -> new WoodButtonBlock(AbstractBlock.Properties.copy(CABlocks.SKYWOOD_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> SKYWOOD_STAIRS = registerBlock("skywood_stairs", () -> new StairsBlock(SKYWOOD_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.SKYWOOD_PLANKS.get())), CAItemGroups.BLOCKS);
	public static final RegistryObject<CAStandingSignBlock> SKYWOOD_SIGN = registerBlock("skywood_sign", () -> new CAStandingSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_BLUE).noCollission().strength(1.0F).sound(SoundType.WOOD), SKYWOOD), null, false);
	public static final RegistryObject<CAWallSignBlock> SKYWOOD_WALL_SIGN = registerBlock("skywood_wall_sign", () -> new CAWallSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_BLUE).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(SKYWOOD_SIGN.get()), SKYWOOD), null, false);
	public static final RegistryObject<TrapDoorBlock> SKYWOOD_TRAPDOOR = registerBlock("skywood_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_LIGHT_BLUE).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(CABlocks::never)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DoorBlock> SKYWOOD_DOOR = registerBlock("skywood_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(3.0F).sound(SoundType.WOOD).noOcclusion()), CAItemGroups.BLOCKS);

	public static final RegistryObject<Block> SKY_MOSS_BLOCK = registerBlock("sky_moss_block", () -> new Block(Block.Properties.of(Material.PLANT, MaterialColor.COLOR_BLUE).strength(0.1F).sound(CASoundType.SKY_MOSS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> SKY_MOSS_CARPET = registerBlock("sky_moss_carpet", () -> new LeafCarpetBlock(Block.Properties.of(Material.PLANT, MaterialColor.COLOR_BLUE).strength(0.1F).sound(CASoundType.SKY_MOSS_CARPET).noOcclusion().noCollission().isValidSpawn(CABlocks::always).isSuffocating(CABlocks::never).isViewBlocking(CABlocks::never)), CAItemGroups.BLOCKS);

	public static final RegistryObject<LeafCarpetBlock> OAK_LEAF_CARPET = registerBlock("oak_leaf_carpet", () -> new LeafCarpetBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().noCollission().isValidSpawn(CABlocks::always).isSuffocating(CABlocks::never).isViewBlocking(CABlocks::never)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> SPRUCE_LEAF_CARPET = registerBlock("spruce_leaf_carpet", () -> new LeafCarpetBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().noCollission().isValidSpawn(CABlocks::always).isSuffocating(CABlocks::never).isViewBlocking(CABlocks::never)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> BIRCH_LEAF_CARPET = registerBlock("birch_leaf_carpet", () -> new LeafCarpetBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().noCollission().isValidSpawn(CABlocks::always).isSuffocating(CABlocks::never).isViewBlocking(CABlocks::never)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> JUNGLE_LEAF_CARPET = registerBlock("jungle_leaf_carpet", () -> new LeafCarpetBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().noCollission().isValidSpawn(CABlocks::always).isSuffocating(CABlocks::never).isViewBlocking(CABlocks::never)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> ACACIA_LEAF_CARPET = registerBlock("acacia_leaf_carpet", () -> new LeafCarpetBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().noCollission().isValidSpawn(CABlocks::always).isSuffocating(CABlocks::never).isViewBlocking(CABlocks::never)), CAItemGroups.BLOCKS);
	public static final RegistryObject<LeafCarpetBlock> DARK_OAK_LEAF_CARPET = registerBlock("dark_oak_leaf_carpet", () -> new LeafCarpetBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().noCollission().isValidSpawn(CABlocks::always).isSuffocating(CABlocks::never).isViewBlocking(CABlocks::never)), CAItemGroups.BLOCKS);

	// MARBLE
	public static final RegistryObject<Block> MARBLE = registerBlock("marble_block", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> MARBLE_SLAB = registerBlock("marble_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.MARBLE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> MARBLE_STAIRS = registerBlock("marble_stairs", () -> new StairsBlock(MARBLE.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.MARBLE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> MARBLE_WALL = registerBlock("marble_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.MARBLE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> MARBLE_BRICKS = registerBlock("marble_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> MARBLE_BRICK_SLAB = registerBlock("marble_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> MARBLE_BRICK_STAIRS = registerBlock("marble_brick_stairs", () -> new StairsBlock(MARBLE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> MARBLE_BRICK_WALL = registerBlock("marble_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CHISELED_MARBLE_BRICKS = registerBlock("chiseled_marble_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CHISELED_MARBLE_BRICK_SLAB = registerBlock("chiseled_marble_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.CHISELED_MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CHISELED_MARBLE_BRICK_STAIRS = registerBlock("chiseled_marble_brick_stairs", () -> new StairsBlock(CHISELED_MARBLE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.CHISELED_MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CHISELED_MARBLE_BRICK_WALL = registerBlock("chiseled_marble_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.CHISELED_MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_MARBLE_BRICKS = registerBlock("cracked_marble_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_MARBLE_BRICK_SLAB = registerBlock("cracked_marble_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.CRACKED_MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_MARBLE_BRICK_STAIRS = registerBlock("cracked_marble_brick_stairs", () -> new StairsBlock(CRACKED_MARBLE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.CRACKED_MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_MARBLE_BRICK_WALL = registerBlock("cracked_marble_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.CRACKED_MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> MOSSY_MARBLE_BRICKS = registerBlock("mossy_marble_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> MOSSY_MARBLE_BRICK_SLAB = registerBlock("mossy_marble_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.MOSSY_MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> MOSSY_MARBLE_BRICK_STAIRS = registerBlock("mossy_marble_brick_stairs", () -> new StairsBlock(MOSSY_MARBLE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.MOSSY_MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> MOSSY_MARBLE_BRICK_WALL = registerBlock("mossy_marble_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.MOSSY_MARBLE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> POLISHED_MARBLE = registerBlock("polished_marble_block", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> POLISHED_MARBLE_SLAB = registerBlock("polished_marble_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.POLISHED_MARBLE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> POLISHED_MARBLE_STAIRS = registerBlock("polished_marble_stairs", () -> new StairsBlock(POLISHED_MARBLE.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.POLISHED_MARBLE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> POLISHED_MARBLE_WALL = registerBlock("polished_marble_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.POLISHED_MARBLE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> MARBLE_PILLAR = registerBlock("marble_pillar", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.QUARTZ_PILLAR).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);

	// LIMESTONE
	public static final RegistryObject<Block> LIMESTONE = registerBlock("limestone_block", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> LIMESTONE_SLAB = registerBlock("limestone_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.LIMESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> LIMESTONE_STAIRS = registerBlock("limestone_stairs", () -> new StairsBlock(LIMESTONE.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.LIMESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> LIMESTONE_WALL = registerBlock("limestone_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.LIMESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> LIMESTONE_BRICKS = registerBlock("limestone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> LIMESTONE_BRICK_SLAB = registerBlock("limestone_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> LIMESTONE_BRICK_STAIRS = registerBlock("limestone_brick_stairs", () -> new StairsBlock(LIMESTONE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> LIMESTONE_BRICK_WALL = registerBlock("limestone_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CHISELED_LIMESTONE_BRICKS = registerBlock("chiseled_limestone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CHISELED_LIMESTONE_BRICK_SLAB = registerBlock("chiseled_limestone_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.CHISELED_LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CHISELED_LIMESTONE_BRICK_STAIRS = registerBlock("chiseled_limestone_brick_stairs", () -> new StairsBlock(CHISELED_LIMESTONE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.CHISELED_LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CHISELED_LIMESTONE_BRICK_WALL = registerBlock("chiseled_limestone_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.CHISELED_LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS = registerBlock("cracked_limestone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_LIMESTONE_BRICK_SLAB = registerBlock("cracked_limestone_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.CRACKED_LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_LIMESTONE_BRICK_STAIRS = registerBlock("cracked_limestone_brick_stairs", () -> new StairsBlock(CRACKED_LIMESTONE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.CRACKED_LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_LIMESTONE_BRICK_WALL = registerBlock("cracked_limestone_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.CRACKED_LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> MOSSY_LIMESTONE_BRICKS = registerBlock("mossy_limestone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> MOSSY_LIMESTONE_BRICK_SLAB = registerBlock("mossy_limestone_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.MOSSY_LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> MOSSY_LIMESTONE_BRICK_STAIRS = registerBlock("mossy_limestone_brick_stairs", () -> new StairsBlock(MOSSY_LIMESTONE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.MOSSY_LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> MOSSY_LIMESTONE_BRICK_WALL = registerBlock("mossy_limestone_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.MOSSY_LIMESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> POLISHED_LIMESTONE = registerBlock("polished_limestone_block", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> POLISHED_LIMESTONE_SLAB = registerBlock("polished_limestone_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.POLISHED_LIMESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> POLISHED_LIMESTONE_STAIRS = registerBlock("polished_limestone_stairs", () -> new StairsBlock(POLISHED_LIMESTONE.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.POLISHED_LIMESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> POLISHED_LIMESTONE_WALL = registerBlock("polished_limestone_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.POLISHED_LIMESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> LIMESTONE_PILLAR = registerBlock("limestone_pillar", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.QUARTZ_PILLAR).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	
	// RHINESTONE
	public static final RegistryObject<Block> RHINESTONE = registerBlock("rhinestone_block", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> RHINESTONE_SLAB = registerBlock("rhinestone_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.RHINESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> RHINESTONE_STAIRS = registerBlock("rhinestone_stairs", () -> new StairsBlock(RHINESTONE.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.RHINESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> RHINESTONE_WALL = registerBlock("rhinestone_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.RHINESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> RHINESTONE_BRICKS = registerBlock("rhinestone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> RHINESTONE_BRICK_SLAB = registerBlock("rhinestone_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> RHINESTONE_BRICK_STAIRS = registerBlock("rhinestone_brick_stairs", () -> new StairsBlock(RHINESTONE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> RHINESTONE_BRICK_WALL = registerBlock("rhinestone_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CHISELED_RHINESTONE_BRICKS = registerBlock("chiseled_rhinestone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CHISELED_RHINESTONE_BRICK_SLAB = registerBlock("chiseled_rhinestone_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.CHISELED_RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CHISELED_RHINESTONE_BRICK_STAIRS = registerBlock("chiseled_rhinestone_brick_stairs", () -> new StairsBlock(CHISELED_RHINESTONE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.CHISELED_RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CHISELED_RHINESTONE_BRICK_WALL = registerBlock("chiseled_rhinestone_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.CHISELED_RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_RHINESTONE_BRICKS = registerBlock("cracked_rhinestone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_RHINESTONE_BRICK_SLAB = registerBlock("cracked_rhinestone_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.CRACKED_RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_RHINESTONE_BRICK_STAIRS = registerBlock("cracked_rhinestone_brick_stairs", () -> new StairsBlock(CRACKED_RHINESTONE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.CRACKED_RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_RHINESTONE_BRICK_WALL = registerBlock("cracked_rhinestone_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.CRACKED_RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> MOSSY_RHINESTONE_BRICKS = registerBlock("mossy_rhinestone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> MOSSY_RHINESTONE_BRICK_SLAB = registerBlock("mossy_rhinestone_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.MOSSY_RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> MOSSY_RHINESTONE_BRICK_STAIRS = registerBlock("mossy_rhinestone_brick_stairs", () -> new StairsBlock(MOSSY_RHINESTONE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.MOSSY_RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> MOSSY_RHINESTONE_BRICK_WALL = registerBlock("mossy_rhinestone_brick_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.MOSSY_RHINESTONE_BRICKS.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> POLISHED_RHINESTONE = registerBlock("polished_rhinestone_block", () -> new Block(Block.Properties.copy(Blocks.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> POLISHED_RHINESTONE_SLAB = registerBlock("polished_rhinestone_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.POLISHED_RHINESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> POLISHED_RHINESTONE_STAIRS = registerBlock("polished_rhinestone_stairs", () -> new StairsBlock(POLISHED_RHINESTONE.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.POLISHED_RHINESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> POLISHED_RHINESTONE_WALL = registerBlock("polished_rhinestone_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CABlocks.POLISHED_RHINESTONE.get()).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> RHINESTONE_PILLAR = registerBlock("rhinestone_pillar", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.QUARTZ_PILLAR).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	
	// ROBO BLOCKS
	public static final RegistryObject<Block> ROBO_BLOCK_I = registerBlock("robo_block_l", () -> new Block(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> ROBO_BLOCK_V = registerBlock("robo_block_v", () -> new RotatedPillarBlock(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> ROBO_BLOCK_X = registerBlock("robo_block_x", () -> new RotatedPillarBlock(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> ROBO_SLAB_I = registerBlock("robo_slab_l", () -> new SlabBlock(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> ROBO_SLAB_X = registerBlock("robo_slab_x", () -> new SlabBlock(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> ROBO_STAIRS_I = registerBlock("robo_stairs_l", () -> new StairsBlock(Blocks.OBSIDIAN.defaultBlockState(), Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> ROBO_STAIRS_X = registerBlock("robo_stairs_x", () -> new StairsBlock(Blocks.OBSIDIAN.defaultBlockState(), Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> ROBO_WALL_I = registerBlock("robo_wall_l", () -> new WallBlock(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> ROBO_WALL_X = registerBlock("robo_wall_x", () -> new WallBlock(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> ROBO_LAMP = registerBlock("robo_lamp", () -> new Block(Block.Properties.of(Material.BUILDABLE_GLASS).lightLevel((state) -> 20).strength(30.0F, 100.0F).sound(SoundType.GLASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> ROBO_BRICKS = registerBlock("robo_bricks", () -> new Block(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> ROBO_BRICK_SLAB = registerBlock("robo_brick_slab", () -> new SlabBlock(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> ROBO_BRICK_STAIRS = registerBlock("robo_brick_stairs", () -> new StairsBlock(Blocks.OBSIDIAN.defaultBlockState(), Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> ROBO_BRICK_WALL = registerBlock("robo_brick_wall", () -> new WallBlock(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarBlock> COMPACT_ROBO_BLOCK = registerBlock("compact_robo_block", () -> new RotatedPillarBlock(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> ROBO_GATE_BLOCK = registerBlock("robo_gate_block", () -> new GateBlock(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<RoboCrateBlock> ROBO_CRATE = registerBlock("robo_crate", () -> new RoboCrateBlock(Block.Properties.of(Material.HEAVY_METAL).strength(55.0F, 1800.0F).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);

	// TERRACOTTA BRICKS
	public static final RegistryObject<Block> BLACK_TERRACOTTA_BRICKS = registerBlock("black_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.BLACK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> BLUE_TERRACOTTA_BRICKS = registerBlock("blue_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> BROWN_TERRACOTTA_BRICKS = registerBlock("brown_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.BROWN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CYAN_TERRACOTTA_BRICKS = registerBlock("cyan_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.CYAN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> GRAY_TERRACOTTA_BRICKS = registerBlock("gray_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> GREEN_TERRACOTTA_BRICKS = registerBlock("green_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.GREEN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_BRICKS = registerBlock("light_blue_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.LIGHT_BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_BRICKS = registerBlock("light_gray_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.LIGHT_GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> LIME_TERRACOTTA_BRICKS = registerBlock("lime_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.LIME_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> MAGENTA_TERRACOTTA_BRICKS = registerBlock("magenta_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.MAGENTA_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> ORANGE_TERRACOTTA_BRICKS = registerBlock("orange_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.ORANGE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> PINK_TERRACOTTA_BRICKS = registerBlock("pink_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.PINK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> PURPLE_TERRACOTTA_BRICKS = registerBlock("purple_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.PURPLE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> RED_TERRACOTTA_BRICKS = registerBlock("red_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.RED_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> TERRACOTTA_BRICKS = registerBlock("terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> WHITE_TERRACOTTA_BRICKS = registerBlock("white_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.WHITE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> YELLOW_TERRACOTTA_BRICKS = registerBlock("yellow_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.YELLOW_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> BLACK_TERRACOTTA_BRICK_SLAB = registerBlock("black_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.BLACK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> BLUE_TERRACOTTA_BRICK_SLAB = registerBlock("blue_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> BROWN_TERRACOTTA_BRICK_SLAB = registerBlock("brown_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.BROWN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CYAN_TERRACOTTA_BRICK_SLAB = registerBlock("cyan_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.CYAN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> GRAY_TERRACOTTA_BRICK_SLAB = registerBlock("gray_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> GREEN_TERRACOTTA_BRICK_SLAB = registerBlock("green_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.GREEN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> LIGHT_BLUE_TERRACOTTA_BRICK_SLAB = registerBlock("light_blue_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.LIGHT_BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> LIGHT_GRAY_TERRACOTTA_BRICK_SLAB = registerBlock("light_gray_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.LIGHT_GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> LIME_TERRACOTTA_BRICK_SLAB = registerBlock("lime_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.LIME_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> MAGENTA_TERRACOTTA_BRICK_SLAB = registerBlock("magenta_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.MAGENTA_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> ORANGE_TERRACOTTA_BRICK_SLAB = registerBlock("orange_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.ORANGE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> PINK_TERRACOTTA_BRICK_SLAB = registerBlock("pink_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.PINK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> PURPLE_TERRACOTTA_BRICK_SLAB = registerBlock("purple_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.PURPLE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> RED_TERRACOTTA_BRICK_SLAB = registerBlock("red_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.RED_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> TERRACOTTA_BRICK_SLAB = registerBlock("terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> WHITE_TERRACOTTA_BRICK_SLAB = registerBlock("white_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.WHITE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> YELLOW_TERRACOTTA_BRICK_SLAB = registerBlock("yellow_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.YELLOW_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> BLACK_TERRACOTTA_BRICK_STAIRS = registerBlock("black_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.BLACK_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.BLACK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> BLUE_TERRACOTTA_BRICK_STAIRS = registerBlock("blue_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.BLUE_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> BROWN_TERRACOTTA_BRICK_STAIRS = registerBlock("brown_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.BROWN_TERRACOTTA_BRICKS.get().defaultBlockState(),Block.Properties.copy(Blocks.BROWN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CYAN_TERRACOTTA_BRICK_STAIRS = registerBlock("cyan_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.CYAN_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.CYAN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> GRAY_TERRACOTTA_BRICK_STAIRS = registerBlock("gray_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.GRAY_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> GREEN_TERRACOTTA_BRICK_STAIRS = registerBlock("green_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.GREEN_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.GREEN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS = registerBlock("light_blue_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.LIGHT_BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS = registerBlock("light_gray_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.LIGHT_GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> LIME_TERRACOTTA_BRICK_STAIRS = registerBlock("lime_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.LIME_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.LIME_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> MAGENTA_TERRACOTTA_BRICK_STAIRS = registerBlock("magenta_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.MAGENTA_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.MAGENTA_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> ORANGE_TERRACOTTA_BRICK_STAIRS = registerBlock("orange_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.ORANGE_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.ORANGE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> PINK_TERRACOTTA_BRICK_STAIRS = registerBlock("pink_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.PINK_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.PINK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> PURPLE_TERRACOTTA_BRICK_STAIRS = registerBlock("purple_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.PURPLE_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.PURPLE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> RED_TERRACOTTA_BRICK_STAIRS = registerBlock("red_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.RED_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.RED_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> TERRACOTTA_BRICK_STAIRS = registerBlock("terracotta_brick_stairs", () -> new StairsBlock(CABlocks.TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> WHITE_TERRACOTTA_BRICK_STAIRS = registerBlock("white_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.WHITE_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.WHITE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> YELLOW_TERRACOTTA_BRICK_STAIRS = registerBlock("yellow_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.YELLOW_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.YELLOW_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> BLACK_TERRACOTTA_BRICK_WALL = registerBlock("black_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.BLACK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> BLUE_TERRACOTTA_BRICK_WALL = registerBlock("blue_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> BROWN_TERRACOTTA_BRICK_WALL = registerBlock("brown_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.BROWN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CYAN_TERRACOTTA_BRICK_WALL = registerBlock("cyan_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.CYAN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> GRAY_TERRACOTTA_BRICK_WALL = registerBlock("gray_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> GREEN_TERRACOTTA_BRICK_WALL = registerBlock("green_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.GREEN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> LIGHT_BLUE_TERRACOTTA_BRICK_WALL = registerBlock("light_blue_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.LIGHT_BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> LIGHT_GRAY_TERRACOTTA_BRICK_WALL = registerBlock("light_gray_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.LIGHT_GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> LIME_TERRACOTTA_BRICK_WALL = registerBlock("lime_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.LIME_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> MAGENTA_TERRACOTTA_BRICK_WALL = registerBlock("magenta_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.LIME_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> ORANGE_TERRACOTTA_BRICK_WALL = registerBlock("orange_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.ORANGE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> PINK_TERRACOTTA_BRICK_WALL = registerBlock("pink_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.PINK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> PURPLE_TERRACOTTA_BRICK_WALL = registerBlock("purple_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.PURPLE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> RED_TERRACOTTA_BRICK_WALL = registerBlock("red_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.RED_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> TERRACOTTA_BRICK_WALL = registerBlock("terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> WHITE_TERRACOTTA_BRICK_WALL = registerBlock("white_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.WHITE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> YELLOW_TERRACOTTA_BRICK_WALL = registerBlock("yellow_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.YELLOW_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);

	// CRACKED TERRACOTTA BRICKS
	public static final RegistryObject<Block> CRACKED_BLACK_TERRACOTTA_BRICKS = registerBlock("cracked_black_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.BLACK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_BLUE_TERRACOTTA_BRICKS = registerBlock("cracked_blue_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_BROWN_TERRACOTTA_BRICKS = registerBlock("cracked_brown_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.BROWN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_CYAN_TERRACOTTA_BRICKS = registerBlock("cracked_cyan_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.CYAN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_GRAY_TERRACOTTA_BRICKS = registerBlock("cracked_gray_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_GREEN_TERRACOTTA_BRICKS = registerBlock("cracked_green_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.GREEN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS = registerBlock("cracked_light_blue_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.LIGHT_BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS = registerBlock("cracked_light_gray_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.LIGHT_GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_LIME_TERRACOTTA_BRICKS = registerBlock("cracked_lime_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.LIME_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_MAGENTA_TERRACOTTA_BRICKS = registerBlock("cracked_magenta_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.MAGENTA_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_ORANGE_TERRACOTTA_BRICKS = registerBlock("cracked_orange_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.ORANGE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_PINK_TERRACOTTA_BRICKS = registerBlock("cracked_pink_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.PINK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_PURPLE_TERRACOTTA_BRICKS = registerBlock("cracked_purple_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.PURPLE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_RED_TERRACOTTA_BRICKS = registerBlock("cracked_red_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.RED_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_TERRACOTTA_BRICKS = registerBlock("cracked_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_WHITE_TERRACOTTA_BRICKS = registerBlock("cracked_white_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.WHITE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> CRACKED_YELLOW_TERRACOTTA_BRICKS = registerBlock("cracked_yellow_terracotta_bricks", () -> new Block(Block.Properties.copy(Blocks.YELLOW_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_BLACK_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_black_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.BLACK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_BLUE_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_blue_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_BROWN_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_brown_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.BROWN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_CYAN_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_cyan_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.CYAN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_GRAY_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_gray_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_GREEN_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_green_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.GREEN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_light_blue_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.LIGHT_BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_light_gray_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.LIGHT_GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_LIME_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_lime_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.LIME_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_MAGENTA_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_magenta_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.MAGENTA_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_ORANGE_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_orange_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.ORANGE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_PINK_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_pink_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.PINK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_PURPLE_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_purple_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.PURPLE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_RED_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_red_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.RED_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_WHITE_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_white_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.WHITE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRACKED_YELLOW_TERRACOTTA_BRICK_SLAB = registerBlock("cracked_yellow_terracotta_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.YELLOW_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_BLACK_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_black_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.BLACK_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.BLACK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_BLUE_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_blue_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.BLUE_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_BROWN_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_brown_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.BROWN_TERRACOTTA_BRICKS.get().defaultBlockState(),Block.Properties.copy(Blocks.BROWN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_CYAN_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_cyan_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.CYAN_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.CYAN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_GRAY_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_gray_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.GRAY_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_GREEN_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_green_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.GREEN_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.GREEN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_light_blue_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.LIGHT_BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_light_gray_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.LIGHT_GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_LIME_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_lime_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.LIME_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.LIME_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_MAGENTA_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_magenta_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.MAGENTA_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.MAGENTA_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_ORANGE_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_orange_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.ORANGE_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.ORANGE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_PINK_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_pink_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.PINK_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.PINK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_PURPLE_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_purple_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.PURPLE_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.PURPLE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_RED_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_red_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.RED_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.RED_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_WHITE_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_white_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.WHITE_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.WHITE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRACKED_YELLOW_TERRACOTTA_BRICK_STAIRS = registerBlock("cracked_yellow_terracotta_brick_stairs", () -> new StairsBlock(CABlocks.YELLOW_TERRACOTTA_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.YELLOW_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_BLACK_TERRACOTTA_BRICK_WALL = registerBlock("cracked_black_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.BLACK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_BLUE_TERRACOTTA_BRICK_WALL = registerBlock("cracked_blue_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_BROWN_TERRACOTTA_BRICK_WALL = registerBlock("cracked_brown_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.BROWN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_CYAN_TERRACOTTA_BRICK_WALL = registerBlock("cracked_cyan_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.CYAN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_GRAY_TERRACOTTA_BRICK_WALL = registerBlock("cracked_gray_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_GREEN_TERRACOTTA_BRICK_WALL = registerBlock("cracked_green_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.GREEN_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_WALL = registerBlock("cracked_light_blue_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.LIGHT_BLUE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_WALL = registerBlock("cracked_light_gray_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.LIGHT_GRAY_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_LIME_TERRACOTTA_BRICK_WALL = registerBlock("cracked_lime_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.LIME_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_MAGENTA_TERRACOTTA_BRICK_WALL = registerBlock("cracked_magenta_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.MAGENTA_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_ORANGE_TERRACOTTA_BRICK_WALL = registerBlock("cracked_orange_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.ORANGE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_PINK_TERRACOTTA_BRICK_WALL = registerBlock("cracked_pink_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.PINK_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_PURPLE_TERRACOTTA_BRICK_WALL = registerBlock("cracked_purple_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.PURPLE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_RED_TERRACOTTA_BRICK_WALL = registerBlock("cracked_red_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.RED_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_TERRACOTTA_BRICK_WALL = registerBlock("cracked_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_WHITE_TERRACOTTA_BRICK_WALL = registerBlock("cracked_white_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.WHITE_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WallBlock> CRACKED_YELLOW_TERRACOTTA_BRICK_WALL = registerBlock("cracked_yellow_terracotta_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.YELLOW_TERRACOTTA).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);

	//ROCK
	//public static final RegistryObject<RockBlock> ROCK = registerBlock("rock", () -> new RockBlock(Block.Properties.copy(Blocks.STONE).instabreak()), CAItemGroups.BLOCKS);

	// PLANTS
	public static final RegistryObject<TopTubeBlock> TUBE_WORM = registerBlock("tube_worm", () -> new TopTubeBlock(AbstractBlock.Properties.of(Material.PLANT, MaterialColor.TERRACOTTA_WHITE).sound(SoundType.BONE_BLOCK).noCollission().instabreak()), CAItemGroups.BLOCKS);
	public static final RegistryObject<TubeBlock> TUBE_WORM_PLANT = registerBlock("tube_worm_plant", () -> new TubeBlock(AbstractBlock.Properties.of(Material.PLANT, MaterialColor.TERRACOTTA_WHITE).sound(SoundType.BONE_BLOCK).noCollission().instabreak().lootFrom(CABlocks.TUBE_WORM)), null, false);
	public static final RegistryObject<CropTopPlantBlock> CORN_TOP_BLOCK = registerBlock("corn_top_block", () -> new CropTopPlantBlock(Block.Properties.copy(Blocks.SUGAR_CANE).randomTicks(), Direction.UP, Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D), 0.5, 3, CABlocks.CORN_BODY_BLOCK), CAItemGroups.FOOD, false);
	public static final RegistryObject<CropBodyPlantBlock> CORN_BODY_BLOCK = registerBlock("corn_body_block", () -> new CropBodyPlantBlock(Block.Properties.copy(Blocks.SUGAR_CANE).randomTicks(), Direction.UP, Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D), false, CABlocks.CORN_TOP_BLOCK), CAItemGroups.FOOD, false);
	public static final RegistryObject<CropTopPlantBlock> TOMATO_TOP_BLOCK = registerBlock("tomato_top_block", () -> new CropTopPlantBlock(Block.Properties.copy(Blocks.SUGAR_CANE).randomTicks(), Direction.UP, Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D), 0.5, 2, CABlocks.TOMATO_BODY_BLOCK), CAItemGroups.FOOD, false);
	public static final RegistryObject<CropBodyPlantBlock> TOMATO_BODY_BLOCK = registerBlock("tomato_body_block", () -> new CropBodyPlantBlock(Block.Properties.copy(Blocks.SUGAR_CANE).randomTicks(), Direction.UP, Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D), false, CABlocks.TOMATO_TOP_BLOCK), CAItemGroups.FOOD, false);
	public static final RegistryObject<StrawberryBushBlock> STRAWBERRY_BUSH = registerBlock("strawberry_bush", () -> new StrawberryBushBlock(CAItems.STRAWBERRY_SEEDS, CAItems.STRAWBERRY, Block.Properties.copy(Blocks.SWEET_BERRY_BUSH).randomTicks()), CAItemGroups.FOOD, false);
	public static final RegistryObject<CACropsBlock> LETTUCE = registerBlock("lettuce", () -> new CACropsBlock(CAItems.LETTUCE_SEEDS, AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)), CAItemGroups.FOOD, false);
	public static final RegistryObject<CACropsBlock> RADISH = registerBlock("radish", () -> new CACropsBlock(CAItems.RADISH_SEEDS, AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)), CAItemGroups.FOOD, false);

	// FLOWER BLOCKS
	public static final RegistryObject<RotatedPillarBlock> FLOWER_STEM = registerBlock("flower_stem", () -> new RotatedPillarBlock(Block.Properties.of(CAMaterial.FLOWER_BLOCK).harvestTool(ToolType.AXE).harvestTool(ToolType.HOE).strength(0.3F).sound(SoundType.GRASS).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> NAVY_BLUE_PETAL_BLOCK = registerBlock("navy_blue_petal_block", () -> new Block(Block.Properties.of(CAMaterial.FLOWER_BLOCK, MaterialColor.TERRACOTTA_BLUE).harvestTool(ToolType.AXE).harvestTool(ToolType.HOE).strength(0.2F).sound(SoundType.GRASS).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> BLOOD_RED_PETAL_BLOCK = registerBlock("blood_red_petal_block", () -> new Block(Block.Properties.of(CAMaterial.FLOWER_BLOCK, MaterialColor.TERRACOTTA_RED).harvestTool(ToolType.AXE).harvestTool(ToolType.HOE).strength(0.2F).sound(SoundType.GRASS).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> BRIGHT_PINK_PETAL_BLOCK = registerBlock("bright_pink_petal_block", () -> new Block(Block.Properties.of(CAMaterial.FLOWER_BLOCK, MaterialColor.TERRACOTTA_MAGENTA).harvestTool(ToolType.AXE).harvestTool(ToolType.HOE).strength(0.2F).sound(SoundType.GRASS).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);

	// FLOWERS
	public static final RegistryObject<FlowerBlock> CYAN_ROSE = registerBlock("cyan_rose", () -> new FlowerBlock(Effects.MOVEMENT_SPEED, 7, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<FlowerBlock> RED_ROSE = registerBlock("red_rose", () -> new FlowerBlock(Effects.MOVEMENT_SLOWDOWN, 14, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<FlowerBlock> PAEONIA = registerBlock("paeonia", () -> new FlowerBlock(Effects.SLOW_FALLING, 10, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);

	// DUNGEON BLOCKS
	public static final RegistryObject<Block> NEST_BLOCK = registerBlock("nest_block", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).harvestTool(ToolType.AXE).strength(0.3F).sound(SoundType.WOOD)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> APPLE_GATE_BLOCK = registerBlock("apple_gate_block", () -> new GateBlock(Block.Properties.copy(APPLE_PLANKS.get()).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> CHERRY_GATE_BLOCK = registerBlock("cherry_gate_block", () -> new GateBlock(Block.Properties.copy(CHERRY_PLANKS.get()).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> DUPLICATION_GATE_BLOCK = registerBlock("duplication_gate_block", () -> new GateBlock(Block.Properties.copy(DUPLICATION_PLANKS.get()).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> GINKGO_GATE_BLOCK = registerBlock("ginkgo_gate_block", () -> new GateBlock(Block.Properties.copy(GINKGO_PLANKS.get()).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> PEACH_GATE_BLOCK = registerBlock("peach_gate_block", () -> new GateBlock(Block.Properties.copy(PEACH_PLANKS.get()).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> SKYWOOD_GATE_BLOCK = registerBlock("skywood_gate_block", () -> new GateBlock(Block.Properties.copy(SKYWOOD_PLANKS.get()).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> ACACIA_GATE_BLOCK = registerBlock("acacia_gate_block", () -> new GateBlock(Block.Properties.copy(Blocks.ACACIA_PLANKS).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> BIRCH_GATE_BLOCK = registerBlock("birch_gate_block", () -> new GateBlock(Block.Properties.copy(Blocks.BIRCH_PLANKS).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> CRIMSON_GATE_BLOCK = registerBlock("crimson_gate_block", () -> new GateBlock(Block.Properties.copy(Blocks.CRIMSON_PLANKS).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> DARK_OAK_GATE_BLOCK = registerBlock("dark_oak_gate_block", () -> new GateBlock(Block.Properties.copy(Blocks.DARK_OAK_PLANKS).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> JUNGLE_GATE_BLOCK = registerBlock("jungle_gate_block", () -> new GateBlock(Block.Properties.copy(Blocks.JUNGLE_PLANKS).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> OAK_GATE_BLOCK = registerBlock("oak_gate_block", () -> new GateBlock(Block.Properties.copy(Blocks.OAK_PLANKS).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> SPRUCE_GATE_BLOCK = registerBlock("spruce_gate_block", () -> new GateBlock(Block.Properties.copy(Blocks.SPRUCE_PLANKS).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> WARPED_GATE_BLOCK = registerBlock("warped_gate_block", () -> new GateBlock(Block.Properties.copy(Blocks.WARPED_PLANKS).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GateBlock> MUSHROOM_GATE_BLOCK = registerBlock("mushroom_gate_block", () -> new GateBlock(Block.Properties.copy(Blocks.MUSHROOM_STEM).harvestTool(ToolType.AXE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<RandomTeleportBlock> RANDOM_TELEPORT_BLOCK = registerBlock("random_teleport_block", () -> new RandomTeleportBlock(Block.Properties.copy(Blocks.OBSIDIAN).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)), CAItemGroups.BLOCKS);

	// MINERAL ORES
	public static final RegistryObject<Block> AMETHYST_ORE = registerBlock("amethyst_ore", () -> new CAOreBlock(AbstractBlock.Properties.of(Material.STONE).strength(4.5F, 3.25F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)).withExpDrop(3, 7), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> RUBY_ORE = registerBlock("ruby_ore", () -> new CAOreBlock(AbstractBlock.Properties.of(Material.STONE).strength(6.5F, 3.25F).harvestLevel(4).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)).withExpDrop(4, 9), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> NETHERRACK_RUBY_ORE = registerBlock("netherrack_ruby_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE).strength(6.5F, 3F).harvestLevel(4).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.NETHER_ORE)).withExpDrop(4, 9), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> BLACKSTONE_RUBY_ORE = registerBlock("blackstone_ruby_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE).strength(6.5F, 3F).harvestLevel(4).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.NETHER_ORE)).withExpDrop(4, 9), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> TIGERS_EYE_ORE = registerBlock("tigers_eye_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE).strength(4F, 3.25F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)).withExpDrop(2, 8), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE).strength(9F, 3.5F).harvestLevel(5).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> URANIUM_ORE = registerBlock("uranium_ore", () -> new UraniumOreBlock(Block.Properties.of(Material.STONE).strength(7.5F, 3.5F).harvestLevel(5).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().randomTicks().lightLevel((state) -> state.getValue(CABlockStateProperties.URANIUM_GLOW_STRENGTH)).sound(SoundType.STONE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> ALUMINUM_ORE = registerBlock("aluminum_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE).strength(3F).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> SALT_ORE = registerBlock("salt_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE).strength(2.5F).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)).withExpDrop(0, 3), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> COPPER_ORE = registerBlock("copper_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.COAL_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> TIN_ORE = registerBlock("tin_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> SILVER_ORE = registerBlock("silver_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> PLATINUM_ORE = registerBlock("platinum_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.DIAMOND_ORE).harvestLevel(4).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> SUNSTONE_ORE = registerBlock("sunstone_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.EMERALD_ORE).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE).lightLevel((state) -> 8)).withExpDrop(3, 6), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> BLOODSTONE_ORE = registerBlock("bloodstone_ore", () -> new CAOreBlock(Block.Properties.copy(Blocks.EMERALD_ORE).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE).lightLevel((state) -> 4)).withExpDrop(2, 5), CAItemGroups.BLOCKS);
	// INFESTED ORES
	public static final RegistryObject<AntInfestedOre> RED_ANT_INFESTED_ORE = registerBlock("red_ant_infested_ore", () -> new AntInfestedOre(CAEntityTypes.RED_ANT, Block.Properties.copy(Blocks.INFESTED_STONE).noDrops().requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(2)), CAItemGroups.BLOCKS);
	public static final RegistryObject<AntInfestedOre> TERMITE_INFESTED_ORE = registerBlock("termite_infested_ore", () -> new AntInfestedOre(CAEntityTypes.TERMITE, Block.Properties.copy(Blocks.INFESTED_STONE).noDrops().requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(2)), CAItemGroups.BLOCKS);

	// FOSSILISED MOBS
	public static final Map<DefossilizerType, RegistryObject<DefossilizerBlock>> DEFOSSILIZER_BLOCKS = Arrays.stream(DefossilizerType.values()).map(type -> Pair.of(type, registerBlock(type.getSerializedName() + "_defossilizer", () -> new DefossilizerBlock(Block.Properties.of(Material.METAL).strength(4, 20).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(2)), CAItemGroups.FOSSILS))).collect(Collectors.toMap(Pair::getKey, Pair::getValue));

	// Overworld (CA)
	public static final RegistryObject<CAOreBlock> FOSSILISED_ACACIA_ENT = registerBlock("fossilised_acacia_ent", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_BIRCH_ENT = registerBlock("fossilised_birch_ent", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_DARK_OAK_ENT = registerBlock("fossilised_dark_oak_ent", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_JUNGLE_ENT = registerBlock("fossilised_jungle_ent", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_OAK_ENT = registerBlock("fossilised_oak_ent", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_SPRUCE_ENT = registerBlock("fossilised_spruce_ent", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_HERCULES_BEETLE = registerBlock("fossilised_hercules_beetle", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_BEAVER = registerBlock("fossilised_beaver", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_RUBY_BUG = registerBlock("fossilised_ruby_bug", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_EMERALD_GATOR = registerBlock("fossilised_emerald_gator", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_GREEN_FISH = registerBlock("fossilised_green_fish", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_ROCK_FISH = registerBlock("fossilised_rock_fish", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_SPARK_FISH = registerBlock("fossilised_spark_fish", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_WOOD_FISH = registerBlock("fossilised_wood_fish", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_WHALE = registerBlock("fossilised_whale", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_WTF = registerBlock("fossilised_wtf", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_SCORPION = registerBlock("fossilised_scorpion", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_WASP = registerBlock("fossilised_wasp", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_PIRAPORU = registerBlock("fossilised_piraporu", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_APPLE_COW = registerBlock("fossilised_apple_cow", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_GOLDEN_APPLE_COW = registerBlock("fossilised_golden_apple_cow", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_CARROT_PIG = registerBlock("fossilised_carrot_pig", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_GOLDEN_CARROT_PIG = registerBlock("fossilised_golden_carrot_pig", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_LETTUCE_CHICKEN = registerBlock("fossilised_lettuce_chicken", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_BIRD = registerBlock("fossilised_bird", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_TREE_FROG = registerBlock("fossilised_tree_frog", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);

	// Overworld (Vanilla)
	public static final RegistryObject<CAOreBlock> FOSSILISED_BAT = registerBlock("fossilised_bat", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_BEE = registerBlock("fossilised_bee", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_CAVE_SPIDER = registerBlock("fossilised_cave_spider", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_CHICKEN = registerBlock("fossilised_chicken", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_COD = registerBlock("fossilised_cod", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_COW = registerBlock("fossilised_cow", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_CREEPER = registerBlock("fossilised_creeper", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_DOLPHIN = registerBlock("fossilised_dolphin", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_DONKEY = registerBlock("fossilised_donkey", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_DROWNED = registerBlock("fossilised_drowned", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_ENDERMAN = registerBlock("fossilised_enderman", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_EVOKER = registerBlock("fossilised_evoker", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_FOX = registerBlock("fossilised_fox", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_GIANT = registerBlock("fossilised_giant", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_GUARDIAN = registerBlock("fossilised_guardian", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_HORSE = registerBlock("fossilised_horse", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_HUSK = registerBlock("fossilised_husk", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_ILLUSIONER = registerBlock("fossilised_illusioner", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_IRON_GOLEM = registerBlock("fossilised_iron_golem", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_LLAMA = registerBlock("fossilised_llama", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_MOOSHROOM = registerBlock("fossilised_mooshroom", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_OCELOT = registerBlock("fossilised_ocelot", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_PANDA = registerBlock("fossilised_panda", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_PIG = registerBlock("fossilised_pig", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_PHANTOM = registerBlock("fossilised_phantom", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_PILLAGER = registerBlock("fossilised_pillager", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_PUFFERFISH = registerBlock("fossilised_pufferfish", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_RABBIT = registerBlock("fossilised_rabbit", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_RAVAGER = registerBlock("fossilised_ravager", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_SALMON = registerBlock("fossilised_salmon", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_SHEEP = registerBlock("fossilised_sheep", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_SKELETON = registerBlock("fossilised_skeleton", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_SKELETON_HORSE = registerBlock("fossilised_skeleton_horse", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_SLIME = registerBlock("fossilised_slime", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_SPIDER = registerBlock("fossilised_spider", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_SQUID = registerBlock("fossilised_squid", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_TROPICAL_FISH = registerBlock("fossilised_tropical_fish", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAFallingOreBlock> FOSSILISED_TURTLE = registerBlock("fossilised_turtle", () -> new CAFallingOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.GRAVEL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_VILLAGER = registerBlock("fossilised_villager", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_VINDICATOR = registerBlock("fossilised_vindicator", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_WANDERING_TRADER = registerBlock("fossilised_wandering_trader", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_WITCH = registerBlock("fossilised_witch", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_WOLF = registerBlock("fossilised_wolf", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_ZOMBIE = registerBlock("fossilised_zombie", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_ZOMBIE_HORSE = registerBlock("fossilised_zombie_horse", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	
	// Mining Paradise (CA)
	public static final RegistryObject<CAOreBlock> FOSSILISED_DIMETRODON = registerBlock("fossilised_dimetrodon", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	
	// Overworld Sandstone (Vanilla)
	public static final RegistryObject<CAOreBlock> FOSSILISED_HUSK_SANDSTONE = registerBlock("fossilised_husk_sandstone", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	
	// Overworld Frozen (Vanilla)
	public static final RegistryObject<CAOreBlock> FROZEN_POLAR_BEAR = registerBlock("frozen_polar_bear", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FROZEN_SNOW_GOLEM = registerBlock("frozen_snow_golem", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FROZEN_STRAY = registerBlock("frozen_stray", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	
	// Nether (CA)
	public static final RegistryObject<CAOreBlock> FOSSILISED_CRIMSON_ENT = registerBlock("fossilised_crimson_ent", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_WARPED_ENT = registerBlock("fossilised_warped_ent", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_LAVA_EEL = registerBlock("fossilised_lava_eel", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);

	// Nether (Vanilla)
	public static final RegistryObject<CAOreBlock> FOSSILISED_BLAZE = registerBlock("fossilised_blaze", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.NETHER_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_GHAST = registerBlock("fossilised_ghast", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.NETHER_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_HOGLIN = registerBlock("fossilised_hoglin", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.NETHER_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_ENDERMAN_NETHERRACK = registerBlock("fossilised_enderman_netherrack", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.NETHER_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_MAGMA_CUBE_NETHERRACK = registerBlock("fossilised_magma_cube_netherrack", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.NETHER_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_MAGMA_CUBE_BLACKSTONE = registerBlock("fossilised_magma_cube_blackstone", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.NETHER_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_PIGLIN = registerBlock("fossilised_piglin", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.NETHER_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_SKELETON_SOUL_SOIL = registerBlock("fossilised_skeleton_soul_soil", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.SOUL_SOIL).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_STRIDER = registerBlock("fossilised_strider", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.NETHER_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_WITHER_SKELETON = registerBlock("fossilised_wither_skeleton", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.NETHER_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_ZOMBIFIED_PIGLIN = registerBlock("fossilised_zombified_piglin", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).sound(SoundType.NETHER_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);

	// End (CA)

	// End (Vanilla)
	public static final RegistryObject<CAOreBlock> FOSSILISED_ENDERMAN_END_STONE = registerBlock("fossilised_enderman_end_stone", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_ENDERMITE = registerBlock("fossilised_endermite", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> FOSSILISED_SHULKER = registerBlock("fossilised_shulker", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops()), CAItemGroups.FOSSILS);

	// Crystal World (CA)
	public static final RegistryObject<CAOreBlock> CRYSTALISED_CRYSTAL_APPLE_COW = registerBlock("crystalised_crystal_apple_cow", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops().isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.FOSSILS);
	public static final RegistryObject<CAOreBlock> CRYSTALISED_CRYSTAL_CARROT_PIG = registerBlock("crystalised_crystal_carrot_pig", () -> new CAOreBlock(Block.Properties.copy(Blocks.IRON_ORE).harvestLevel(1).requiresCorrectToolForDrops().isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.FOSSILS);

	// MINERAL BLOCKS
	public static final RegistryObject<Block> AMETHYST_BLOCK = registerBlock("amethyst_block", () -> new Block(Block.Properties.copy(Blocks.EMERALD_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block", () -> new Block(Block.Properties.copy(Blocks.ANCIENT_DEBRIS).harvestLevel(4).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> TIGERS_EYE_BLOCK = registerBlock("tigers_eye_block", () -> new Block(Block.Properties.copy(Blocks.EMERALD_BLOCK).harvestLevel(4).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block", () -> new Block(Block.Properties.copy(Blocks.NETHERITE_BLOCK).harvestLevel(5).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> URANIUM_BLOCK = registerBlock("uranium_block", () -> new Block(Block.Properties.copy(Blocks.NETHERITE_BLOCK).harvestLevel(5).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> ALUMINUM_BLOCK = registerBlock("aluminum_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SaltBlock> SALT_BLOCK = registerBlock("salt_block", () -> new SaltBlock(Block.Properties.copy(Blocks.SAND).harvestLevel(1).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops().sound(SoundType.SAND).speedFactor(0.8F)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> COPPER_BLOCK = registerBlock("copper_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> PLATINUM_BLOCK = registerBlock("platinum_block", () -> new Block(Block.Properties.copy(Blocks.DIAMOND_BLOCK).harvestLevel(4).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> SUNSTONE_BLOCK = registerBlock("sunstone_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL).lightLevel((state) -> 15)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> BLOODSTONE_BLOCK = registerBlock("bloodstone_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)), CAItemGroups.BLOCKS);

	// MOB DROP BLOCKS
	public static final RegistryObject<Block> ENDER_PEARL_BLOCK = registerBlock("ender_pearl_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> ENDER_EYE_BLOCK = registerBlock("ender_eye_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> MOTH_SCALE_BLOCK = registerRareBlock("moth_scale_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> WATER_DRAGON_SCALE_BLOCK = registerRareBlock("water_dragon_scale_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> ENDER_DRAGON_SCALE_BLOCK = registerRareBlock("ender_dragon_scale_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> NIGHTMARE_SCALE_BLOCK = registerRareBlock("nightmare_scale_block", () -> new Block(Block.Properties.copy(Blocks.DIAMOND_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> MOBZILLA_SCALE_BLOCK = registerEpicRareBlock("mobzilla_scale_block", () -> new Block(Block.Properties.copy(Blocks.DIAMOND_BLOCK).harvestLevel(4).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> ROYAL_GUARDIAN_SCALE_BLOCK = registerRoyaltyBlock("royal_guardian_scale_block", () -> new Block(Block.Properties.copy(Blocks.NETHERITE_BLOCK).harvestLevel(4).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> QUEEN_SCALE_BLOCK = registerRoyaltyBlock("queen_scale_block", () -> new Block(Block.Properties.copy(Blocks.NETHERITE_BLOCK).harvestLevel(4).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> BASILISK_SCALE_BLOCK = registerRareBlock("basilisk_scale_block", () -> new Block(Block.Properties.copy(Blocks.DIAMOND_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> EMPEROR_SCORPION_SCALE_BLOCK = registerRareBlock("emperor_scorpion_scale_block", () -> new Block(Block.Properties.copy(Blocks.DIAMOND_BLOCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.SHROOMLIGHT)), CAItemGroups.BLOCKS);
	
	// ANT NESTS
	public static final RegistryObject<AntNestBlock> BROWN_ANT_NEST = registerBlock("brown_ant_nest", () -> new AntNestBlock(CAEntityTypes.BROWN_ANT, Block.Properties.copy(Blocks.GRASS_BLOCK).harvestTool(ToolType.SHOVEL).randomTicks()), CAItemGroups.BLOCKS);
	public static final RegistryObject<AntNestBlock> RAINBOW_ANT_NEST = registerBlock("rainbow_ant_nest", () -> new AntNestBlock(CAEntityTypes.RAINBOW_ANT, Block.Properties.copy(Blocks.GRASS_BLOCK).harvestTool(ToolType.SHOVEL).randomTicks()), CAItemGroups.BLOCKS);
	public static final RegistryObject<AntNestBlock> RED_ANT_NEST = registerBlock("red_ant_nest", () -> new AntNestBlock(CAEntityTypes.RED_ANT, Block.Properties.copy(Blocks.GRASS_BLOCK).harvestTool(ToolType.SHOVEL).randomTicks()), CAItemGroups.BLOCKS);
	public static final RegistryObject<AntNestBlock> UNSTABLE_ANT_NEST = registerBlock("unstable_ant_nest", () -> new AntNestBlock(CAEntityTypes.UNSTABLE_ANT, Block.Properties.copy(Blocks.GRASS_BLOCK).harvestTool(ToolType.SHOVEL).randomTicks()), CAItemGroups.BLOCKS);
	public static final RegistryObject<AntNestBlock> TERMITE_NEST = registerBlock("termite_nest", () -> new AntNestBlock(CAEntityTypes.TERMITE, Block.Properties.copy(Blocks.GRASS_BLOCK).harvestTool(ToolType.SHOVEL).randomTicks()), CAItemGroups.BLOCKS);

	// TORCHES
	public static final RegistryObject<TorchBlock> CRYSTAL_TORCH = registerBlock("crystal_torch", () -> new TorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 14).sound(SoundType.WOOD), ParticleTypes.FLAME), null, false);
	public static final RegistryObject<WallTorchBlock> WALL_CRYSTAL_TORCH = registerBlock("wall_crystal_torch", () -> new WallTorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 14).sound(SoundType.WOOD).lootFrom(CABlocks.CRYSTAL_TORCH), ParticleTypes.FLAME), null, false);
	public static final RegistryObject<TorchBlock> SUNSTONE_TORCH = registerBlock("sunstone_torch", () -> new TorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 12).sound(SoundType.WOOD), ParticleTypes.END_ROD), null, false);
	public static final RegistryObject<WallTorchBlock> WALL_SUNSTONE_TORCH = registerBlock("wall_sunstone_torch", () -> new WallTorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 12).sound(SoundType.WOOD).lootFrom(CABlocks.SUNSTONE_TORCH), ParticleTypes.END_ROD), null, false);
	public static final RegistryObject<TorchBlock> EXTREME_TORCH = registerBlock("extreme_torch", () -> new TorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 20).sound(SoundType.WOOD), ParticleTypes.FLAME), null, false);
	public static final RegistryObject<WallTorchBlock> WALL_EXTREME_TORCH = registerBlock("wall_extreme_torch", () -> new WallTorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((state) -> 20).sound(SoundType.WOOD).lootFrom(CABlocks.EXTREME_TORCH), ParticleTypes.FLAME), null, false);

	// MINERS DREAM BLOCKS
	public static final RegistryObject<Block> MOLDY_PLANKS = registerBlock("moldy_planks", () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> MOLDY_SLAB = registerBlock("moldy_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_SLAB)), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceBlock> MOLDY_FENCE = registerBlock("moldy_fence", () -> new FenceBlock(Block.Properties.copy(Blocks.OAK_FENCE)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> MINING_LAMP = registerBlock("mining_lamp", () -> new Block(Block.Properties.of(Material.BUILDABLE_GLASS).lightLevel((state) -> 15).strength(0.3F).sound(SoundType.GLASS)), CAItemGroups.BLOCKS);
	
	// QUINOA
	
	
	// CRYSTAL WORLD DIMENSION
	public static final RegistryObject<CrystalGrassBlock> CRYSTAL_GRASS_BLOCK = registerBlock("crystal_grass_block", () -> new CrystalGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS).harvestTool(ToolType.SHOVEL).requiresCorrectToolForDrops().isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalAntNestBlock> CRYSTAL_TERMITE_NEST = registerBlock("crystal_termite_nest", () -> new CrystalAntNestBlock(CAEntityTypes.TERMITE, Block.Properties.copy(CABlocks.CRYSTAL_GRASS_BLOCK.get()).harvestTool(ToolType.SHOVEL).randomTicks().isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalBlock> KYANITE = registerBlock("kyanite", () -> new CrystalBlock(Block.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarCrystalBlock> CRYSTAL_LOG = registerBlock("crystal_log", () -> new RotatedPillarCrystalBlock(Block.Properties.copy(Blocks.OAK_LOG).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<RotatedPillarCrystalBlock> CRYSTAL_WOOD = registerBlock("crystal_wood", () -> new RotatedPillarCrystalBlock(Block.Properties.copy(Blocks.OAK_WOOD).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalBlock> CRYSTAL_PLANKS = registerBlock("crystal_planks", () -> new CrystalBlock(Block.Properties.copy(Blocks.OAK_PLANKS).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalSaplingBlock> RED_CRYSTAL_SAPLING = registerBlock("red_crystal_sapling", () -> new CrystalSaplingBlock(new CATree(() -> CAConfiguredFeatures.RED_CRYSTAL_TREE), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalSaplingBlock> GREEN_CRYSTAL_SAPLING = registerBlock("green_crystal_sapling", () -> new CrystalSaplingBlock(new CATree(() -> CAConfiguredFeatures.GREEN_CRYSTAL_TREE), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalSaplingBlock> YELLOW_CRYSTAL_SAPLING = registerBlock("yellow_crystal_sapling", () -> new CrystalSaplingBlock(new CATree(() -> CAConfiguredFeatures.YELLOW_CRYSTAL_TREE), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalSaplingBlock> PINK_CRYSTAL_SAPLING = registerBlock("pink_crystal_sapling", () -> new CrystalSaplingBlock(new CATree(() -> CAConfiguredFeatures.PINK_CRYSTAL_TREE), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalSaplingBlock> BLUE_CRYSTAL_SAPLING = registerBlock("blue_crystal_sapling", () -> new CrystalSaplingBlock(new CATree(() -> CAConfiguredFeatures.BLUE_CRYSTAL_TREE), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalSaplingBlock> ORANGE_CRYSTAL_SAPLING = registerBlock("orange_crystal_sapling", () -> new CrystalSaplingBlock(new CATree(() -> CAConfiguredFeatures.ORANGE_CRYSTAL_TREE), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> RED_CRYSTAL_LEAVES = registerBlock("red_crystal_leaves", () -> new Block(Block.Properties.copy(Blocks.OAK_LEAVES).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> GREEN_CRYSTAL_LEAVES = registerBlock("green_crystal_leaves", () -> new Block(Block.Properties.copy(Blocks.OAK_LEAVES).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> YELLOW_CRYSTAL_LEAVES = registerBlock("yellow_crystal_leaves", () -> new Block(Block.Properties.copy(Blocks.OAK_LEAVES).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> PINK_CRYSTAL_LEAVES = registerBlock("pink_crystal_leaves", () -> new Block(Block.Properties.copy(Blocks.OAK_LEAVES).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> BLUE_CRYSTAL_LEAVES = registerBlock("blue_crystal_leaves", () -> new Block(Block.Properties.copy(Blocks.OAK_LEAVES).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> ORANGE_CRYSTAL_LEAVES = registerBlock("orange_crystal_leaves", () -> new Block(Block.Properties.copy(Blocks.OAK_LEAVES).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<SlabBlock> CRYSTAL_SLAB = registerBlock("crystal_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CABlocks.CRYSTAL_PLANKS.get()).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<PressurePlateBlock> CRYSTAL_PRESSURE_PLATE = registerBlock("crystal_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.copy(CABlocks.CRYSTAL_PLANKS.get()).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceBlock> CRYSTAL_FENCE = registerBlock("crystal_fence", () -> new FenceBlock(AbstractBlock.Properties.copy(CABlocks.CRYSTAL_PLANKS.get()).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<FenceGateBlock> CRYSTAL_FENCE_GATE = registerBlock("crystal_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.copy(CABlocks.CRYSTAL_PLANKS.get()).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<WoodButtonBlock> CRYSTAL_BUTTON = registerBlock("crystal_button", () -> new WoodButtonBlock(AbstractBlock.Properties.copy(CABlocks.CRYSTAL_PLANKS.get()).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<StairsBlock> CRYSTAL_STAIRS = registerBlock("crystal_stairs", () -> new StairsBlock(CRYSTAL_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CABlocks.CRYSTAL_PLANKS.get()).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalClusterBlock> PINK_TOURMALINE_CLUSTER = registerBlock("pink_tourmaline_cluster", () -> new CrystalClusterBlock(Block.Properties.copy(Blocks.IRON_ORE).noOcclusion().isSuffocating(isFalse).isViewBlocking(isFalse).isRedstoneConductor(isFalse).harvestLevel(1)), CAItemGroups.BLOCKS);
	public static final RegistryObject<BuddingBlock> BUDDING_PINK_TOURMALINE = registerBlock("budding_pink_tourmaline", () -> new BuddingBlock(Block.Properties.copy(Blocks.STONE).randomTicks().isRedstoneConductor(isFalse).noOcclusion(), PINK_TOURMALINE_CLUSTER.get()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalClusterBlock> CATS_EYE_CLUSTER = registerBlock("cats_eye_cluster", () -> new CrystalClusterBlock(Block.Properties.copy(Blocks.IRON_ORE).isSuffocating(isFalse).isViewBlocking(isFalse).isRedstoneConductor(isFalse).noOcclusion().harvestLevel(2)), CAItemGroups.BLOCKS);
	public static final RegistryObject<BuddingBlock> BUDDING_CATS_EYE = registerBlock("budding_cats_eye", () -> new BuddingBlock(Block.Properties.copy(Blocks.STONE).randomTicks().isRedstoneConductor(isFalse).noOcclusion(), CATS_EYE_CLUSTER.get()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalEnergyBlock> CRYSTAL_ENERGY = registerBlock("crystal_energy", () -> new CrystalEnergyBlock(Block.Properties.copy(Blocks.GLASS).isSuffocating(isFalse).isViewBlocking(isFalse).isRedstoneConductor(isFalse).noOcclusion().harvestLevel(0).lightLevel((state) -> 8)), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalCraftingTableBlock> CRYSTAL_CRAFTING_TABLE = registerBlock("crystal_crafting_table", () -> new CrystalCraftingTableBlock(Block.Properties.copy(Blocks.CRAFTING_TABLE).isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFurnaceBlock> CRYSTAL_FURNACE = registerBlock("crystal_furnace", () -> new CrystalFurnaceBlock(Block.Properties.copy(Blocks.FURNACE).isRedstoneConductor(isFalse).noOcclusion().lightLevel(lightValueFunction.apply(13))), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalBlock> PINK_TOURMALINE_BLOCK = registerBlock("pink_tourmaline_block", () -> new CrystalBlock(Block.Properties.copy(Blocks.IRON_BLOCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalBlock> CATS_EYE_BLOCK = registerBlock("cats_eye_block", () -> new CrystalBlock(Block.Properties.copy(Blocks.DIAMOND_BLOCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().isRedstoneConductor(isFalse).noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<TallCrystalGrassBlock> CRYSTAL_GRASS = registerBlock("crystal_grass", () -> new TallCrystalGrassBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).requiresCorrectToolForDrops().noCollission().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DoubleCrystalPlantBlock> TALL_CRYSTAL_GRASS = registerBlock("tall_crystal_grass", () -> new DoubleCrystalPlantBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).requiresCorrectToolForDrops().noCollission().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> RED_CRYSTAL_FLOWER = registerBlock("red_crystal_flower", () -> new CrystalFlowerBlock(Effects.GLOWING, 9, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> BLUE_CRYSTAL_FLOWER = registerBlock("blue_crystal_flower", () -> new CrystalFlowerBlock(Effects.GLOWING, 15, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> GREEN_CRYSTAL_FLOWER = registerBlock("green_crystal_flower", () -> new CrystalFlowerBlock(Effects.GLOWING, 6, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> YELLOW_CRYSTAL_FLOWER = registerBlock("yellow_crystal_flower", () -> new CrystalFlowerBlock(Effects.GLOWING, 12, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> PINK_CRYSTAL_FLOWER = registerBlock("pink_crystal_flower", () -> new CrystalFlowerBlock(Effects.GLOWING, 10, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> ORANGE_CRYSTAL_FLOWER = registerBlock("orange_crystal_flower", () -> new CrystalFlowerBlock(Effects.GLOWING, 11, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> RED_CRYSTAL_GROWTH = registerBlock("red_crystal_growth", () -> new CrystalFlowerBlock(Effects.GLOWING, 9, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> BLUE_CRYSTAL_GROWTH = registerBlock("blue_crystal_growth", () -> new CrystalFlowerBlock(Effects.GLOWING, 15, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> GREEN_CRYSTAL_GROWTH = registerBlock("green_crystal_growth", () -> new CrystalFlowerBlock(Effects.GLOWING, 6, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> YELLOW_CRYSTAL_GROWTH = registerBlock("yellow_crystal_growth", () -> new CrystalFlowerBlock(Effects.GLOWING, 12, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> ORANGE_CRYSTAL_GROWTH = registerBlock("orange_crystal_growth", () -> new CrystalFlowerBlock(Effects.GLOWING, 12, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> PINK_CRYSTAL_GROWTH = registerBlock("pink_crystal_growth", () -> new CrystalFlowerBlock(Effects.GLOWING, 12, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);
	public static final RegistryObject<CrystalFlowerBlock> CRYSTAL_ROSE = registerBlock("crystal_rose", () -> new CrystalFlowerBlock(Effects.GLOWING, 16, AbstractBlock.Properties.copy(Blocks.RED_TULIP).noCollission().instabreak().noOcclusion()), CAItemGroups.BLOCKS);

	// MINING PARADISE DIMENSION
	public static final RegistryObject<DenseGrassBlock> DENSE_GRASS_BLOCK = registerBlock("dense_grass_block", () -> new DenseGrassBlock(AbstractBlock.Properties.of(Material.GRASS).randomTicks().harvestTool(ToolType.SHOVEL).strength(0.9F).sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<Block> DENSE_DIRT = registerBlock("dense_dirt", () -> new Block(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.DIRT).harvestTool(ToolType.SHOVEL).strength(0.75F).sound(SoundType.GRAVEL)), CAItemGroups.BLOCKS);
	public static final RegistryObject<GenericFarmlandBlock> DENSE_FARMLAND = registerBlock("dense_farmland", () -> new GenericFarmlandBlock(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.DIRT).randomTicks().harvestTool(ToolType.SHOVEL).strength(0.75F).sound(SoundType.GRAVEL), CABlocks.DENSE_DIRT), CAItemGroups.BLOCKS);
	public static final RegistryObject<AntNestBlock> DENSE_RED_ANT_NEST = registerBlock("dense_red_ant_nest", () -> new AntNestBlock(CAEntityTypes.RED_ANT, Block.Properties.copy(DENSE_GRASS_BLOCK.get()).harvestTool(ToolType.SHOVEL).randomTicks()), CAItemGroups.BLOCKS);
	public static final RegistryObject<TallDenseGrassBlock> DENSE_GRASS = registerBlock("dense_grass", () -> new TallDenseGrassBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DoubleDensePlantBlock> TALL_DENSE_GRASS = registerBlock("tall_dense_grass", () -> new DoubleDensePlantBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).lightLevel((state) -> 8)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DoubleDensePlantBlock> THORNY_SUN = registerBlock("thorny_sun", () -> new DoubleDensePlantBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).lightLevel((state) -> 15)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DenseFlowerBlock> BLUE_BULB = registerBlock("blue_bulb", () -> new DenseFlowerBlock(Effects.HEALTH_BOOST, 30, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).lightLevel((state) -> 8)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DenseFlowerBlock> PINK_BULB = registerBlock("pink_bulb", () -> new DenseFlowerBlock(Effects.HEALTH_BOOST, 30, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).lightLevel((state) -> 8)), CAItemGroups.BLOCKS);
	public static final RegistryObject<DenseFlowerBlock> PURPLE_BULB = registerBlock("purple_bulb", () -> new DenseFlowerBlock(Effects.HEALTH_BOOST, 30, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).lightLevel((state) -> 8)), CAItemGroups.BLOCKS);

	// POTS
	public static final RegistryObject<FlowerPotBlock> POTTED_CYAN_ROSE = registerBlock("potted_cyan_rose", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CYAN_ROSE, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_RED_ROSE = registerBlock("potted_red_rose", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, RED_ROSE, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_PAEONIA = registerBlock("potted_paeonia", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PAEONIA, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_BLUE_BULB = registerBlock("potted_blue_bulb", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLUE_BULB, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_PINK_BULB = registerBlock("potted_pink_bulb", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PINK_BULB, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_PURPLE_BULB = registerBlock("potted_purple_bulb", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PURPLE_BULB, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_APPLE_SAPLING = registerBlock("potted_apple_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, APPLE_SAPLING, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_CHERRY_SAPLING = registerBlock("potted_cherry_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CHERRY_SAPLING, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_PEACH_SAPLING = registerBlock("potted_peach_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PEACH_SAPLING, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_RED_CRYSTAL_SAPLING = registerBlock("potted_red_crystal_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, RED_CRYSTAL_SAPLING, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_GREEN_CRYSTAL_SAPLING = registerBlock("potted_green_crystal_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREEN_CRYSTAL_SAPLING, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_YELLOW_CRYSTAL_SAPLING = registerBlock("potted_yellow_crystal_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, YELLOW_CRYSTAL_SAPLING, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_PINK_CRYSTAL_SAPLING = registerBlock("potted_pink_crystal_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PINK_CRYSTAL_SAPLING, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_BLUE_CRYSTAL_SAPLING = registerBlock("potted_blue_crystal_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLUE_CRYSTAL_SAPLING, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_ORANGE_CRYSTAL_SAPLING = registerBlock("potted_orange_crystal_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ORANGE_CRYSTAL_SAPLING, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_RED_CRYSTAL_FLOWER = registerBlock("potted_red_crystal_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, RED_CRYSTAL_FLOWER, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_BLUE_CRYSTAL_FLOWER = registerBlock("potted_blue_crystal_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLUE_CRYSTAL_FLOWER, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_GREEN_CRYSTAL_FLOWER = registerBlock("potted_green_crystal_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREEN_CRYSTAL_FLOWER, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_YELLOW_CRYSTAL_FLOWER = registerBlock("potted_yellow_crystal_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, YELLOW_CRYSTAL_FLOWER, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_PINK_CRYSTAL_FLOWER = registerBlock("potted_pink_crystal_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PINK_CRYSTAL_FLOWER, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_ORANGE_CRYSTAL_FLOWER = registerBlock("potted_orange_crystal_flower", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ORANGE_CRYSTAL_FLOWER, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_RED_CRYSTAL_GROWTH = registerBlock("potted_red_crystal_growth", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, RED_CRYSTAL_GROWTH, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_BLUE_CRYSTAL_GROWTH = registerBlock("potted_blue_crystal_growth", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLUE_CRYSTAL_GROWTH, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_GREEN_CRYSTAL_GROWTH = registerBlock("potted_green_crystal_growth", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GREEN_CRYSTAL_GROWTH, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_YELLOW_CRYSTAL_GROWTH = registerBlock("potted_yellow_crystal_growth", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, YELLOW_CRYSTAL_GROWTH, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_ORANGE_CRYSTAL_GROWTH = registerBlock("potted_orange_crystal_growth", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ORANGE_CRYSTAL_GROWTH, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_PINK_CRYSTAL_GROWTH = registerBlock("potted_pink_crystal_growth", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PINK_CRYSTAL_GROWTH, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);
	public static final RegistryObject<FlowerPotBlock> POTTED_CRYSTAL_ROSE = registerBlock("potted_crystal_rose", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CRYSTAL_ROSE, AbstractBlock.Properties.copy(Blocks.FLOWER_POT)), null, false);

	/**
	 * No stack size and generateItem variant, defaults to 64 and true, respectively
	 *
	 * @param <B>       Block type
	 * @param name      Block registry name
	 * @param supplier  Block instance supplier
	 * @param itemGroup Block item group, {@code null} for no item group
	 * @return A RegistryObject<B>
	 */
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
		return registerBlock(name, supplier, itemGroup, true);
	}

	/**
	 * No stack size variant, defaults to 64
	 *
	 * @param <B>          Block type
	 * @param name         Block registry name
	 * @param supplier     Block instance supplier
	 * @param itemGroup    Block item group, {@code null} for no item group
	 * @param generateItem If a BlockItem should be generated for this block
	 * @return A RegistryObject<B>
	 */
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, boolean generateItem) {
		return registerBlock(name, supplier, itemGroup, 64, generateItem);
	}

	/**
	 * No generateItem variant, defaults to true
	 *
	 * @param <B>       Block type
	 * @param name      Block registry name
	 * @param supplier  Block instance supplier
	 * @param itemGroup Block item group, {@code null} for no item group
	 * @param stackSize Block stack size
	 * @return A RegistryObject<B>
	 */
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, int stackSize) {
		return registerBlock(name, supplier, itemGroup, stackSize, true);
	}

	/**
	 * Registers a block to the BLOCKS deferred register and BlockItem to the
	 * ITEM_BLOCKS deferred register
	 *
	 * @param <B>          Block type
	 * @param name         Block registry name
	 * @param supplier     Block instance supplier
	 * @param itemGroup    Block item group, {@code null} for no item group
	 * @param stackSize    Block stack size
	 * @param generateItem If a BlockItem should be generated for this block
	 * @return A RegistryObject<B>
	 */
	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, int stackSize, boolean generateItem) {
		RegistryObject<B> block = CABlocks.BLOCKS.register(name, supplier);
		if (generateItem) ITEM_BLOCKS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup).stacksTo(stackSize)));
		return block;
	}

	public static <B extends Block> RegistryObject<B> registerRareBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
		return registerRareBlock(name, supplier, itemGroup, true);
	}

	public static <B extends Block> RegistryObject<B> registerRareBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, boolean generateItem) {
		RegistryObject<B> block = CABlocks.BLOCKS.register(name, supplier);
		if (generateItem) ITEM_BLOCKS.register(name, () -> new BlockItem(block.get(), new Item.Properties().rarity(Rarity.RARE).tab(itemGroup)));
		return block;
	}

	public static <B extends Block> RegistryObject<B> registerEpicRareBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
		return registerEpicRareBlock(name, supplier, itemGroup, true);
	}

	public static <B extends Block> RegistryObject<B> registerEpicRareBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, boolean generateItem) {
		RegistryObject<B> block = CABlocks.BLOCKS.register(name, supplier);
		if (generateItem) ITEM_BLOCKS.register(name, () -> new BlockItem(block.get(), new Item.Properties().rarity(Rarity.EPIC).tab(itemGroup)));
		return block;
	}

	public static <B extends Block> RegistryObject<B> registerRoyaltyBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
		return registerRoyaltyBlock(name, supplier, itemGroup, true);
	}

	public static <B extends Block> RegistryObject<B> registerRoyaltyBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, boolean generateItem) {
		RegistryObject<B> block = CABlocks.BLOCKS.register(name, supplier);
		if (generateItem) ITEM_BLOCKS.register(name, () -> new BlockItem(block.get(), new Item.Properties().rarity(CAItems.RARITY_ROYALTY).tab(itemGroup)));
		return block;
	}

	public static void flowerPots() {
		FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;

		pot.addPlant(CYAN_ROSE.getId(), POTTED_CYAN_ROSE);
		pot.addPlant(RED_ROSE.getId(), POTTED_RED_ROSE);
		pot.addPlant(PAEONIA.getId(), POTTED_PAEONIA);
		pot.addPlant(BLUE_BULB.getId(), POTTED_BLUE_BULB);
		pot.addPlant(PINK_BULB.getId(), POTTED_PINK_BULB);
		pot.addPlant(PURPLE_BULB.getId(), POTTED_PURPLE_BULB);
		pot.addPlant(APPLE_SAPLING.getId(), POTTED_APPLE_SAPLING);
		pot.addPlant(CHERRY_SAPLING.getId(), POTTED_CHERRY_SAPLING);
		pot.addPlant(PEACH_SAPLING.getId(), POTTED_PEACH_SAPLING);
		pot.addPlant(RED_CRYSTAL_SAPLING.getId(), POTTED_RED_CRYSTAL_SAPLING);
		pot.addPlant(GREEN_CRYSTAL_SAPLING.getId(), POTTED_GREEN_CRYSTAL_SAPLING);
		pot.addPlant(YELLOW_CRYSTAL_SAPLING.getId(), POTTED_YELLOW_CRYSTAL_SAPLING);
		pot.addPlant(PINK_CRYSTAL_SAPLING.getId(), POTTED_PINK_CRYSTAL_SAPLING);
		pot.addPlant(RED_CRYSTAL_FLOWER.getId(), POTTED_RED_CRYSTAL_FLOWER);
		pot.addPlant(BLUE_CRYSTAL_FLOWER.getId(), POTTED_BLUE_CRYSTAL_FLOWER);
		pot.addPlant(GREEN_CRYSTAL_FLOWER.getId(), POTTED_GREEN_CRYSTAL_FLOWER);
		pot.addPlant(YELLOW_CRYSTAL_FLOWER.getId(), POTTED_YELLOW_CRYSTAL_FLOWER);
		pot.addPlant(PINK_CRYSTAL_FLOWER.getId(), POTTED_PINK_CRYSTAL_FLOWER);
		pot.addPlant(ORANGE_CRYSTAL_FLOWER.getId(), POTTED_ORANGE_CRYSTAL_FLOWER);
		pot.addPlant(RED_CRYSTAL_GROWTH.getId(), POTTED_RED_CRYSTAL_GROWTH);
		pot.addPlant(BLUE_CRYSTAL_GROWTH.getId(), POTTED_BLUE_CRYSTAL_GROWTH);
		pot.addPlant(GREEN_CRYSTAL_GROWTH.getId(), POTTED_GREEN_CRYSTAL_GROWTH);
		pot.addPlant(YELLOW_CRYSTAL_GROWTH.getId(), POTTED_YELLOW_CRYSTAL_GROWTH);
		pot.addPlant(ORANGE_CRYSTAL_GROWTH.getId(), POTTED_ORANGE_CRYSTAL_GROWTH);
		pot.addPlant(PINK_CRYSTAL_GROWTH.getId(), POTTED_PINK_CRYSTAL_GROWTH);
		pot.addPlant(CRYSTAL_ROSE.getId(), POTTED_CRYSTAL_ROSE);
	}

	public static boolean never(BlockState blockState, IBlockReader blockReader, BlockPos blockPos) {
		return false;
	}

	public static boolean never(BlockState blockState, IBlockReader blockReader, BlockPos blockPos, EntityType<?> entityType) {
		return false;
	}

	public static boolean always(BlockState blockState, IBlockReader blockReader, BlockPos blockPos, EntityType<?> entityType) {
		return true;
	}

	public enum DefossilizerType implements IStringSerializable {
		COPPER(0, "copper"),
		IRON(1, "iron");

		private static final DefossilizerType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(DefossilizerType::getId)).toArray(DefossilizerType[]::new);

		private final int id;
		private final String name;

		DefossilizerType(int idIn, String name) {
			this.id = idIn;
			this.name = name;
		}

		public int getId() {
			return this.id;
		}

		public static DefossilizerType byId(int id) {
			if(id < 0 || id >= VALUES.length) id = 0;
			return VALUES[id];
		}

		public static String getDefossilizerTypeByID(int id) {
			for(DefossilizerType defossilizerType : values()) {
				if(defossilizerType.getId() == id) return defossilizerType.getSerializedName();
			}
			return null;
		}

		public static boolean getBooleanDefossilizerTypeByID(int id) {
			for(DefossilizerType defossilizerType : values()) {
				if(defossilizerType.getId() == id) return true;
			}
			return false;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
