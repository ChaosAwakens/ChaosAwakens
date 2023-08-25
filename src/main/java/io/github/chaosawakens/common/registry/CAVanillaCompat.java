package io.github.chaosawakens.common.registry;

import com.google.common.collect.Maps;

import io.github.chaosawakens.common.entity.projectile.CALettuceChickenEggEntity;
import io.github.chaosawakens.common.entity.projectile.arrow.IrukandjiArrowEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;

public class CAVanillaCompat {
	
	public static void registerVanillaCompat() {
		setCompostableItems();
		setDispenserBehaviour();
		setFlammableBlocks();
		setFlowerPots();
		setStrippableLogs();
	}
	
	private static void setCompostableItems() {
		registerCompostable(0.3F, CABlocks.APPLE_SAPLING.get());
		registerCompostable(0.3F, CABlocks.APPLE_LEAVES.get());
		registerCompostable(0.2F, CABlocks.APPLE_LEAF_CARPET.get());
		registerCompostable(0.3F, CABlocks.CHERRY_SAPLING.get());
		registerCompostable(0.3F, CABlocks.CHERRY_LEAVES.get());
		registerCompostable(0.2F, CABlocks.CHERRY_LEAF_CARPET.get());
		registerCompostable(0.3F, CABlocks.DUPLICATION_LEAVES.get());
		registerCompostable(0.2F, CABlocks.DUPLICATION_LEAF_CARPET.get());
		registerCompostable(0.3F, CABlocks.PEACH_SAPLING.get());
		registerCompostable(0.3F, CABlocks.PEACH_LEAVES.get());
		registerCompostable(0.2F, CABlocks.PEACH_LEAF_CARPET.get());
//        registerCompostable(0.3F, CABlocks.SKYWOOD_SAPLING.get());
		registerCompostable(0.3F, CABlocks.SKYWOOD_LEAVES.get());
		registerCompostable(0.2F, CABlocks.SKYWOOD_LEAF_CARPET.get());
		registerCompostable(0.3F, CABlocks.SKY_MOSS_CARPET.get());
		registerCompostable(0.65F, CABlocks.SKY_MOSS_BLOCK.get());
		registerCompostable(0.3F, CABlocks.GINKGO_SAPLING.get());
		registerCompostable(0.3F, CABlocks.GINKGO_LEAVES.get());
		registerCompostable(0.2F, CABlocks.GINKGO_LEAF_CARPET.get());
		registerCompostable(0.3F, CABlocks.MESOZOIC_SAPLING.get());
		registerCompostable(0.3F, CABlocks.MESOZOIC_LEAVES.get());
		registerCompostable(0.2F, CABlocks.MESOZOIC_LEAF_CARPET.get());
		registerCompostable(0.3F, CABlocks.DENSEWOOD_SAPLING.get());
		registerCompostable(0.3F, CABlocks.DENSEWOOD_LEAVES.get());
		registerCompostable(0.2F, CABlocks.DENSEWOOD_LEAF_CARPET.get());
		registerCompostable(0.3F, CABlocks.RED_CRYSTAL_SAPLING.get());
		registerCompostable(0.3F, CABlocks.RED_CRYSTAL_LEAVES.get());
		registerCompostable(0.3F, CABlocks.GREEN_CRYSTAL_SAPLING.get());
		registerCompostable(0.3F, CABlocks.GREEN_CRYSTAL_LEAVES.get());
		registerCompostable(0.3F, CABlocks.YELLOW_CRYSTAL_SAPLING.get());
		registerCompostable(0.3F, CABlocks.YELLOW_CRYSTAL_LEAVES.get());
		registerCompostable(0.5F, CABlocks.TUBE_WORM.get());
		registerCompostable(0.65F, CABlocks.CYAN_ROSE.get());
		registerCompostable(0.65F, CABlocks.RED_ROSE.get());
		registerCompostable(0.65F, CABlocks.PAEONIA.get());
		registerCompostable(0.65F, CABlocks.SWAMP_MILKWEED.get());
		registerCompostable(0.65F, CABlocks.PRIMROSE.get());
		registerCompostable(0.65F, CABlocks.DAISY.get());
		registerCompostable(0.2F, CABlocks.OAK_LEAF_CARPET.get());
		registerCompostable(0.2F, CABlocks.SPRUCE_LEAF_CARPET.get());
		registerCompostable(0.2F, CABlocks.BIRCH_LEAF_CARPET.get());
		registerCompostable(0.2F, CABlocks.JUNGLE_LEAF_CARPET.get());
		registerCompostable(0.2F, CABlocks.ACACIA_LEAF_CARPET.get());
		registerCompostable(0.2F, CABlocks.DARK_OAK_LEAF_CARPET.get());
		registerCompostable(0.15F, CAItems.CORN_SEEDS.get());
		registerCompostable(0.15F, CAItems.TOMATO_SEEDS.get());
		registerCompostable(0.15F, CAItems.RADISH_SEEDS.get());
		registerCompostable(0.15F, CAItems.QUINOA_SEEDS.get());
		registerCompostable(0.15F, CAItems.LETTUCE_SEEDS.get());
		registerCompostable(0.15F, CAItems.STRAWBERRY_SEEDS.get());
		registerCompostable(0.45F, CABlocks.DENSE_GRASS.get());
		registerCompostable(0.75F, CABlocks.TALL_DENSE_GRASS.get());
		registerCompostable(0.75F, CABlocks.THORNY_SUN.get());
		registerCompostable(0.65F, CABlocks.BLUE_BULB.get());
		registerCompostable(0.65F, CABlocks.PINK_BULB.get());
		registerCompostable(0.65F, CABlocks.PURPLE_BULB.get());
		registerCompostable(0.75F, CABlocks.ALSTROEMERIAT.get());
		registerCompostable(0.6F, CABlocks.SMALL_BUSH.get());
		registerCompostable(0.55F, CABlocks.SMALL_CARNIVOROUS_PLANT.get());
		registerCompostable(0.55F, CABlocks.BIG_CARNIVOROUS_PLANT.get());
		registerCompostable(0.45F, CABlocks.MESOZOIC_VINES.get());
		registerCompostable(0.45F, CABlocks.MESOZOIC_VINES_PLANT.get());
		registerCompostable(0.3F, CABlocks.CRYSTAL_GRASS.get());
		registerCompostable(0.6F, CAItems.CORN.get());
		registerCompostable(0.5F, CAItems.TOMATO.get());
		registerCompostable(0.35F, CAItems.STRAWBERRY.get());
		registerCompostable(0.4F, CAItems.RADISH.get());
		registerCompostable(0.45F, CAItems.QUINOA.get());
		registerCompostable(0.5F, CAItems.LETTUCE.get());
		registerCompostable(0.5F, CAItems.CHEESE.get());
		registerCompostable(0.25F, CAItems.CHERRIES.get());
		registerCompostable(0.3F, CAItems.PEACH.get());
		registerCompostable(0.7F, CAItems.CRYSTAL_APPLE.get());
		registerCompostable(0.65F, CAItems.CRYSTAL_BEETROOT.get());
		registerCompostable(0.75F, CAItems.CRYSTAL_CARROT.get());
		registerCompostable(0.8F, CAItems.CRYSTAL_POTATO.get());
		registerCompostable(0.5F, CABlocks.TALL_CRYSTAL_GRASS.get());
		registerCompostable(0.65F, CABlocks.RED_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.BLUE_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.GREEN_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.YELLOW_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.PINK_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.ORANGE_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.RED_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.BLUE_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.GREEN_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.YELLOW_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.ORANGE_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.PINK_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.CRYSTAL_ROSE.get());
	}
	
	private static void setStrippableLogs() {
		registerStrippable(CABlocks.APPLE_LOG.get(), CABlocks.STRIPPED_APPLE_LOG.get());
		registerStrippable(CABlocks.APPLE_WOOD.get(), CABlocks.STRIPPED_APPLE_WOOD.get());
		registerStrippable(CABlocks.CHERRY_LOG.get(), CABlocks.STRIPPED_CHERRY_LOG.get());
		registerStrippable(CABlocks.CHERRY_WOOD.get(), CABlocks.STRIPPED_CHERRY_WOOD.get());
		registerStrippable(CABlocks.PEACH_LOG.get(), CABlocks.STRIPPED_PEACH_LOG.get());
		registerStrippable(CABlocks.PEACH_WOOD.get(), CABlocks.STRIPPED_PEACH_WOOD.get());
		registerStrippable(CABlocks.DUPLICATION_LOG.get(), CABlocks.STRIPPED_DUPLICATION_LOG.get());
		registerStrippable(CABlocks.DUPLICATION_WOOD.get(), CABlocks.STRIPPED_DUPLICATION_WOOD.get());
		registerStrippable(CABlocks.DEAD_DUPLICATION_LOG.get(), CABlocks.STRIPPED_DEAD_DUPLICATION_LOG.get());
		registerStrippable(CABlocks.DEAD_DUPLICATION_WOOD.get(), CABlocks.STRIPPED_DEAD_DUPLICATION_WOOD.get());
		registerStrippable(CABlocks.SKYWOOD_LOG.get(), CABlocks.STRIPPED_SKYWOOD_LOG.get());
		registerStrippable(CABlocks.SKYWOOD.get(), CABlocks.STRIPPED_SKYWOOD.get());
		registerStrippable(CABlocks.GINKGO_LOG.get(), CABlocks.STRIPPED_GINKGO_LOG.get());
		registerStrippable(CABlocks.GINKGO_WOOD.get(), CABlocks.STRIPPED_GINKGO_WOOD.get());
		registerStrippable(CABlocks.MESOZOIC_LOG.get(), CABlocks.STRIPPED_MESOZOIC_LOG.get());
		registerStrippable(CABlocks.MESOZOIC_WOOD.get(), CABlocks.STRIPPED_MESOZOIC_WOOD.get());
		registerStrippable(CABlocks.DENSEWOOD_LOG.get(), CABlocks.STRIPPED_DENSEWOOD_LOG.get());
		registerStrippable(CABlocks.DENSEWOOD.get(), CABlocks.STRIPPED_DENSEWOOD.get());
	}
	
	private static void setFlammableBlocks() {
		registerFlammable(CABlocks.APPLE_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.APPLE_LOG.get(), 5, 5);
		registerFlammable(CABlocks.APPLE_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_APPLE_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_APPLE_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.APPLE_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.APPLE_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.APPLE_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.APPLE_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.APPLE_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.CHERRY_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.CHERRY_LOG.get(), 5, 5);
		registerFlammable(CABlocks.CHERRY_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_CHERRY_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_CHERRY_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.CHERRY_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.CHERRY_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.CHERRY_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.CHERRY_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.CHERRY_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.DUPLICATION_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.DUPLICATION_LOG.get(), 5, 5);
		registerFlammable(CABlocks.DUPLICATION_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_DUPLICATION_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_DUPLICATION_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.DUPLICATION_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.DUPLICATION_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.DUPLICATION_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.DUPLICATION_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.DUPLICATION_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.PEACH_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.PEACH_LOG.get(), 5, 5);
		registerFlammable(CABlocks.PEACH_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_PEACH_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_PEACH_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.PEACH_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.PEACH_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.PEACH_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.PEACH_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.PEACH_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.SKYWOOD_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.SKYWOOD_LOG.get(), 5, 5);
		registerFlammable(CABlocks.SKYWOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_SKYWOOD_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_SKYWOOD.get(), 5, 5);
		registerFlammable(CABlocks.SKYWOOD_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.SKYWOOD_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.SKYWOOD_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.SKYWOOD_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.SKYWOOD_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.GINKGO_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.GINKGO_LOG.get(), 5, 5);
		registerFlammable(CABlocks.GINKGO_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_GINKGO_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_GINKGO_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.GINKGO_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.GINKGO_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.GINKGO_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.GINKGO_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.GINKGO_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.MESOZOIC_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.MESOZOIC_LOG.get(), 5, 5);
		registerFlammable(CABlocks.MESOZOIC_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_MESOZOIC_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_MESOZOIC_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.MESOZOIC_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.MESOZOIC_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.MESOZOIC_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.MESOZOIC_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.MESOZOIC_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.DENSEWOOD_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.DENSEWOOD_LOG.get(), 5, 5);
		registerFlammable(CABlocks.DENSEWOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_DENSEWOOD_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_DENSEWOOD.get(), 5, 5);
		registerFlammable(CABlocks.DENSEWOOD_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.DENSEWOOD_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.DENSEWOOD_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.DENSEWOOD_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.DENSEWOOD_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.RED_CRYSTAL_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.GREEN_CRYSTAL_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.YELLOW_CRYSTAL_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.CRYSTALWOOD_LOG.get(), 5, 5);
		registerFlammable(CABlocks.CRYSTALWOOD.get(), 5, 5);
		registerFlammable(CABlocks.CRYSTALWOOD_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.CRYSTALWOOD_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.CRYSTALWOOD_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.CRYSTALWOOD_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.CRYSTALWOOD_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.CYAN_ROSE.get(), 60, 100);
		registerFlammable(CABlocks.RED_ROSE.get(), 60, 100);
		registerFlammable(CABlocks.PAEONIA.get(), 60, 100);
		registerFlammable(CABlocks.SWAMP_MILKWEED.get(), 60, 100);
		registerFlammable(CABlocks.PRIMROSE.get(), 60, 100);
		registerFlammable(CABlocks.DAISY.get(), 60, 100);
		registerFlammable(CABlocks.STRAWBERRY_BUSH.get(), 60, 100);
		registerFlammable(CABlocks.CORN_TOP_BLOCK.get(), 60, 100);
		registerFlammable(CABlocks.CORN_BODY_BLOCK.get(), 60, 100);
		registerFlammable(CABlocks.TOMATO_TOP_BLOCK.get(), 60, 100);
		registerFlammable(CABlocks.TOMATO_BODY_BLOCK.get(), 60, 100);
		registerFlammable(CABlocks.DENSE_GRASS.get(), 50, 80);
		registerFlammable(CABlocks.TALL_DENSE_GRASS.get(), 50, 80);
		registerFlammable(CABlocks.THORNY_SUN.get(), 50, 80);
		registerFlammable(CABlocks.BLUE_BULB.get(), 60, 100);
		registerFlammable(CABlocks.PINK_BULB.get(), 60, 100);
		registerFlammable(CABlocks.PURPLE_BULB.get(), 60, 100);
		registerFlammable(CABlocks.DENSE_ORCHID.get(), 60, 100);
		registerFlammable(CABlocks.ALSTROEMERIAT.get(), 60, 100);
		registerFlammable(CABlocks.SMALL_BUSH.get(), 50, 80);
		registerFlammable(CABlocks.SMALL_CARNIVOROUS_PLANT.get(), 50, 80);
		registerFlammable(CABlocks.BIG_CARNIVOROUS_PLANT.get(), 50, 80);
		registerFlammable(CABlocks.MESOZOIC_VINES.get(), 50, 80);
		registerFlammable(CABlocks.MESOZOIC_VINES_PLANT.get(), 50, 80);
		registerFlammable(CABlocks.CRYSTAL_GRASS.get(), 50, 80);
		registerFlammable(CABlocks.TALL_CRYSTAL_GRASS.get(), 50, 80);
		registerFlammable(CABlocks.RED_CRYSTAL_FLOWER.get(), 60, 100);
		registerFlammable(CABlocks.BLUE_CRYSTAL_FLOWER.get(), 60, 100);
		registerFlammable(CABlocks.GREEN_CRYSTAL_FLOWER.get(), 60, 100);
		registerFlammable(CABlocks.YELLOW_CRYSTAL_FLOWER.get(), 60, 100);		
		registerFlammable(CABlocks.PINK_CRYSTAL_FLOWER.get(), 60, 100);
		registerFlammable(CABlocks.ORANGE_CRYSTAL_FLOWER.get(), 60, 100);
		registerFlammable(CABlocks.RED_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.BLUE_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.GREEN_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.YELLOW_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.ORANGE_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.PINK_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.CRYSTAL_ROSE.get(), 60, 100);
	}
	
	private static void setFlowerPots() {
		FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;

		pot.addPlant(CABlocks.CYAN_ROSE.getId(), CABlocks.POTTED_CYAN_ROSE);
		pot.addPlant(CABlocks.RED_ROSE.getId(), CABlocks.POTTED_RED_ROSE);
		pot.addPlant(CABlocks.PAEONIA.getId(), CABlocks.POTTED_PAEONIA);
		pot.addPlant(CABlocks.BLUE_BULB.getId(), CABlocks.POTTED_BLUE_BULB);
		pot.addPlant(CABlocks.PINK_BULB.getId(), CABlocks.POTTED_PINK_BULB);
		pot.addPlant(CABlocks.PURPLE_BULB.getId(), CABlocks.POTTED_PURPLE_BULB);
		pot.addPlant(CABlocks.DENSE_ORCHID.getId(), CABlocks.POTTED_DENSE_ORCHID);
		pot.addPlant(CABlocks.SWAMP_MILKWEED.getId(), CABlocks.POTTED_SWAMP_MILKWEED);
		pot.addPlant(CABlocks.SMALL_BUSH.getId(), CABlocks.POTTED_SMALL_BUSH);
		pot.addPlant(CABlocks.SMALL_CARNIVOROUS_PLANT.getId(), CABlocks.POTTED_SMALL_CARNIVOROUS_PLANT);
		pot.addPlant(CABlocks.BIG_CARNIVOROUS_PLANT.getId(), CABlocks.POTTED_BIG_CARNIVOROUS_PLANT);
		pot.addPlant(CABlocks.PRIMROSE.getId(), CABlocks.POTTED_PRIMROSE);
		pot.addPlant(CABlocks.DAISY.getId(), CABlocks.POTTED_DAISY);
		pot.addPlant(CABlocks.APPLE_SAPLING.getId(), CABlocks.POTTED_APPLE_SAPLING);
		pot.addPlant(CABlocks.CHERRY_SAPLING.getId(), CABlocks.POTTED_CHERRY_SAPLING);
		pot.addPlant(CABlocks.PEACH_SAPLING.getId(), CABlocks.POTTED_PEACH_SAPLING);
		pot.addPlant(CABlocks.GINKGO_SAPLING.getId(), CABlocks.POTTED_GINKGO_SAPLING);
		pot.addPlant(CABlocks.MESOZOIC_SAPLING.getId(), CABlocks.POTTED_MESOZOIC_SAPLING);
		pot.addPlant(CABlocks.DENSEWOOD_SAPLING.getId(), CABlocks.POTTED_DENSEWOOD_SAPLING);
		pot.addPlant(CABlocks.RED_CRYSTAL_SAPLING.getId(), CABlocks.POTTED_RED_CRYSTAL_SAPLING);
		pot.addPlant(CABlocks.GREEN_CRYSTAL_SAPLING.getId(),CABlocks. POTTED_GREEN_CRYSTAL_SAPLING);
		pot.addPlant(CABlocks.YELLOW_CRYSTAL_SAPLING.getId(), CABlocks.POTTED_YELLOW_CRYSTAL_SAPLING);
		pot.addPlant(CABlocks.PINK_CRYSTAL_SAPLING.getId(), CABlocks.POTTED_PINK_CRYSTAL_SAPLING);
		pot.addPlant(CABlocks.BLUE_CRYSTAL_SAPLING.getId(), CABlocks.POTTED_BLUE_CRYSTAL_SAPLING);
		pot.addPlant(CABlocks.ORANGE_CRYSTAL_SAPLING.getId(), CABlocks.POTTED_ORANGE_CRYSTAL_SAPLING);
		pot.addPlant(CABlocks.RED_CRYSTAL_FLOWER.getId(), CABlocks.POTTED_RED_CRYSTAL_FLOWER);
		pot.addPlant(CABlocks.BLUE_CRYSTAL_FLOWER.getId(), CABlocks.POTTED_BLUE_CRYSTAL_FLOWER);
		pot.addPlant(CABlocks.GREEN_CRYSTAL_FLOWER.getId(), CABlocks.POTTED_GREEN_CRYSTAL_FLOWER);
		pot.addPlant(CABlocks.YELLOW_CRYSTAL_FLOWER.getId(), CABlocks.POTTED_YELLOW_CRYSTAL_FLOWER);
		pot.addPlant(CABlocks.PINK_CRYSTAL_FLOWER.getId(), CABlocks.POTTED_PINK_CRYSTAL_FLOWER);
		pot.addPlant(CABlocks.ORANGE_CRYSTAL_FLOWER.getId(), CABlocks.POTTED_ORANGE_CRYSTAL_FLOWER);
		pot.addPlant(CABlocks.RED_CRYSTAL_GROWTH.getId(), CABlocks.POTTED_RED_CRYSTAL_GROWTH);
		pot.addPlant(CABlocks.BLUE_CRYSTAL_GROWTH.getId(), CABlocks.POTTED_BLUE_CRYSTAL_GROWTH);
		pot.addPlant(CABlocks.GREEN_CRYSTAL_GROWTH.getId(), CABlocks.POTTED_GREEN_CRYSTAL_GROWTH);
		pot.addPlant(CABlocks.YELLOW_CRYSTAL_GROWTH.getId(), CABlocks.POTTED_YELLOW_CRYSTAL_GROWTH);
		pot.addPlant(CABlocks.ORANGE_CRYSTAL_GROWTH.getId(), CABlocks.POTTED_ORANGE_CRYSTAL_GROWTH);
		pot.addPlant(CABlocks.PINK_CRYSTAL_GROWTH.getId(), CABlocks.POTTED_PINK_CRYSTAL_GROWTH);
		pot.addPlant(CABlocks.CRYSTAL_ROSE.getId(), CABlocks.POTTED_CRYSTAL_ROSE);
	}
	
	private static void setDispenserBehaviour() {
		DispenserBlock.registerBehavior(CAItems.IRUKANDJI_ARROW.get(), new ProjectileDispenseBehavior() {			
			@Override
			protected ProjectileEntity getProjectile(World world, IPosition pos, ItemStack stack) {
				return new IrukandjiArrowEntity(world, pos.x(), pos.y(), pos.z());
			}
		});
		DispenserBlock.registerBehavior(CAItems.LETTUCE_CHICKEN_EGG.get(), new ProjectileDispenseBehavior() {			
			@Override
			protected ProjectileEntity getProjectile(World world, IPosition pos, ItemStack stack) {
				return new CALettuceChickenEggEntity(world, pos.x(), pos.y(), pos.z());
			}
		});
	//	DispenserBlock.registerBehavior(null, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
	}
	
	private static void registerStrippable(Block log, Block stripped_log) {
		AxeItem.STRIPABLES = Maps.newHashMap(AxeItem.STRIPABLES);
		AxeItem.STRIPABLES.put(log, stripped_log);
	}

	private static void registerCompostable(float chance, IItemProvider itemIn) {
		ComposterBlock.COMPOSTABLES.put(itemIn.asItem(), chance);
	}

	private static void registerFlammable(Block blockIn, int encouragement, int flammability) {
		FireBlock fireBlock = (FireBlock) Blocks.FIRE;
		fireBlock.setFlammable(blockIn, encouragement, flammability);
	}
}
