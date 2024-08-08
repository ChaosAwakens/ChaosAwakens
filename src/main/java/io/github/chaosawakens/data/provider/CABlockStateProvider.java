package io.github.chaosawakens.data.provider;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.GateBlock;
import io.github.chaosawakens.common.blocks.crystal.RotatedPillarCrystalBlock;
import io.github.chaosawakens.common.blocks.dense.DoubleDensePlantBlock;
import io.github.chaosawakens.common.blocks.multiface.LeafCarpetBlock;
import io.github.chaosawakens.common.blocks.multiface.PipeBlock;
import io.github.chaosawakens.common.blocks.ore.CAFallingOreBlock;
import io.github.chaosawakens.common.blocks.ore.CAOreBlock;
import io.github.chaosawakens.common.blocks.robo.WiredRoboBlock;
import io.github.chaosawakens.common.blocks.vegetation.GenericFarmlandBlock;
import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@SuppressWarnings("all")
public class CABlockStateProvider extends BlockStateProvider {
	private final ExistingFileHelper helper;
	
	public CABlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, ChaosAwakens.MODID, exFileHelper);
		helper = exFileHelper;
	}

	@Override
	protected void registerStatesAndModels() { //TODO Automate
		grassBlock(CABlocks.DENSE_GRASS_BLOCK.get(), chaosRL("dense_dirt"), chaosRL("dense_dirt"), chaosRL("dense_grass_block_top"), chaosRL("dense_grass_block_side"), chaosRL("dense_grass_block_side_overlay"));
		simpleBlock(CABlocks.DENSE_DIRT.get());
		grassBlock(CABlocks.DENSE_RED_ANT_NEST.get(), chaosRL("dense_dirt"), chaosRL("dense_dirt"), chaosRL("dense_red_ant_nest"), chaosRL("dense_grass_block_side"), chaosRL("dense_grass_block_side_overlay"));
		cross(CABlocks.DENSE_GRASS.get());
		doubleCross(CABlocks.TALL_DENSE_GRASS.get());
		doubleCross(CABlocks.TALL_BUSH.get());
		doubleCross(CABlocks.THORNY_SUN.get());
		simpleBlock(CABlocks.TERRA_PRETA.get());
		farmland(CABlocks.TERRA_PRETA_FARMLAND.get());
		simpleBlock(CABlocks.TAR.get());
		simpleBlock(CABlocks.LATOSOL.get());
		doubleCross(CABlocks.ALSTROEMERIAT.get());
		cross(CABlocks.DENSE_ORCHID.get());
		cross(CABlocks.DAISY.get());
		cross(CABlocks.SWAMP_MILKWEED.get());
		cross(CABlocks.PRIMROSE.get());
		cross(CABlocks.SMALL_BUSH.get());
		cross(CABlocks.SMALL_CARNIVOROUS_PLANT.get());
		cross(CABlocks.BIG_CARNIVOROUS_PLANT.get());
		cross(CABlocks.MESOZOIC_VINES.get());
		cross(CABlocks.MESOZOIC_VINES_PLANT.get());
		
		simpleBlock(CABlocks.MARBLE.get());
		simpleBlock(CABlocks.MARBLE_BRICKS.get());
		simpleBlock(CABlocks.CHISELED_MARBLE_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_MARBLE_BRICKS.get());
		simpleBlock(CABlocks.MOSSY_MARBLE_BRICKS.get());
		simpleBlock(CABlocks.POLISHED_MARBLE.get());
		logBlock(CABlocks.MARBLE_PILLAR.get());
		axisBlock(CABlocks.MARBLE_PILLAR_3.get(), chaosRL("marble_pillar_3"), chaosRL("marble_pillar_top"));
		axisBlock(CABlocks.MARBLE_PILLAR_S.get(), chaosRL("marble_pillar_s"), chaosRL("marble_pillar_top"));
		axisBlock(CABlocks.MARBLE_PILLAR_T.get(), chaosRL("marble_pillar_t"), chaosRL("marble_pillar_top"));
		axisBlock(CABlocks.MARBLE_PILLAR_Z.get(), chaosRL("marble_pillar_z"), chaosRL("marble_pillar_top"));
		slabBlock(CABlocks.MARBLE_SLAB.get(), chaosRL("marble_block"), chaosRL("marble_block"));
		slabBlock(CABlocks.MARBLE_BRICK_SLAB.get(), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		slabBlock(CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		slabBlock(CABlocks.CRACKED_MARBLE_BRICK_SLAB.get(), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		slabBlock(CABlocks.MOSSY_MARBLE_BRICK_SLAB.get(), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		slabBlock(CABlocks.POLISHED_MARBLE_SLAB.get(), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		stairsBlock(CABlocks.MARBLE_STAIRS.get(), chaosRL("marble_block"));
		stairsBlock(CABlocks.MARBLE_BRICK_STAIRS.get(), chaosRL("marble_bricks"));
		stairsBlock(CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get(), chaosRL("chiseled_marble_bricks"));
		stairsBlock(CABlocks.CRACKED_MARBLE_BRICK_STAIRS.get(), chaosRL("cracked_marble_bricks"));
		stairsBlock(CABlocks.MOSSY_MARBLE_BRICK_STAIRS.get(), chaosRL("mossy_marble_bricks"));
		stairsBlock(CABlocks.POLISHED_MARBLE_STAIRS.get(), chaosRL("polished_marble_block"));
		wallBlock(CABlocks.MARBLE_WALL.get(), chaosRL("marble_block"));
		wallBlock(CABlocks.MARBLE_BRICK_WALL.get(), chaosRL("marble_bricks"));
		wallBlock(CABlocks.CHISELED_MARBLE_BRICK_WALL.get(), chaosRL("chiseled_marble_bricks"));
		wallBlock(CABlocks.CRACKED_MARBLE_BRICK_WALL.get(), chaosRL("cracked_marble_bricks"));
		wallBlock(CABlocks.MOSSY_MARBLE_BRICK_WALL.get(), chaosRL("mossy_marble_bricks"));
		wallBlock(CABlocks.POLISHED_MARBLE_WALL.get(), chaosRL("polished_marble_block"));

		simpleBlock(CABlocks.LIMESTONE.get());
		simpleBlock(CABlocks.LIMESTONE_BRICKS.get());
		simpleBlock(CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_LIMESTONE_BRICKS.get());
		simpleBlock(CABlocks.MOSSY_LIMESTONE_BRICKS.get());
		simpleBlock(CABlocks.POLISHED_LIMESTONE.get());
		logBlock(CABlocks.LIMESTONE_PILLAR.get());
		slabBlock(CABlocks.LIMESTONE_SLAB.get(), chaosRL("limestone_block"), chaosRL("limestone_block"));
		slabBlock(CABlocks.LIMESTONE_BRICK_SLAB.get(), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		slabBlock(CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		slabBlock(CABlocks.CRACKED_LIMESTONE_BRICK_SLAB.get(), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		slabBlock(CABlocks.MOSSY_LIMESTONE_BRICK_SLAB.get(), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		slabBlock(CABlocks.POLISHED_LIMESTONE_SLAB.get(), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		stairsBlock(CABlocks.LIMESTONE_STAIRS.get(), chaosRL("limestone_block"));
		stairsBlock(CABlocks.LIMESTONE_BRICK_STAIRS.get(), chaosRL("limestone_bricks"));
		stairsBlock(CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get(), chaosRL("chiseled_limestone_bricks"));
		stairsBlock(CABlocks.CRACKED_LIMESTONE_BRICK_STAIRS.get(), chaosRL("cracked_limestone_bricks"));
		stairsBlock(CABlocks.MOSSY_LIMESTONE_BRICK_STAIRS.get(), chaosRL("mossy_limestone_bricks"));
		stairsBlock(CABlocks.POLISHED_LIMESTONE_STAIRS.get(), chaosRL("polished_limestone_block"));
		wallBlock(CABlocks.LIMESTONE_WALL.get(), chaosRL("limestone_block"));
		wallBlock(CABlocks.LIMESTONE_BRICK_WALL.get(), chaosRL("limestone_bricks"));
		wallBlock(CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get(), chaosRL("chiseled_limestone_bricks"));
		wallBlock(CABlocks.CRACKED_LIMESTONE_BRICK_WALL.get(), chaosRL("cracked_limestone_bricks"));
		wallBlock(CABlocks.MOSSY_LIMESTONE_BRICK_WALL.get(), chaosRL("mossy_limestone_bricks"));
		wallBlock(CABlocks.POLISHED_LIMESTONE_WALL.get(), chaosRL("polished_limestone_block"));
		
		simpleBlock(CABlocks.RHINESTONE.get());
		simpleBlock(CABlocks.RHINESTONE_BRICKS.get());
		simpleBlock(CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_RHINESTONE_BRICKS.get());
		simpleBlock(CABlocks.MOSSY_RHINESTONE_BRICKS.get());
		simpleBlock(CABlocks.POLISHED_RHINESTONE.get());
		logBlock(CABlocks.RHINESTONE_PILLAR.get());
		slabBlock(CABlocks.RHINESTONE_SLAB.get(), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		slabBlock(CABlocks.RHINESTONE_BRICK_SLAB.get(), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		slabBlock(CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		slabBlock(CABlocks.CRACKED_RHINESTONE_BRICK_SLAB.get(), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		slabBlock(CABlocks.MOSSY_RHINESTONE_BRICK_SLAB.get(), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		slabBlock(CABlocks.POLISHED_RHINESTONE_SLAB.get(), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		stairsBlock(CABlocks.RHINESTONE_STAIRS.get(), chaosRL("rhinestone_block"));
		stairsBlock(CABlocks.RHINESTONE_BRICK_STAIRS.get(), chaosRL("rhinestone_bricks"));
		stairsBlock(CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get(), chaosRL("chiseled_rhinestone_bricks"));
		stairsBlock(CABlocks.CRACKED_RHINESTONE_BRICK_STAIRS.get(), chaosRL("cracked_rhinestone_bricks"));
		stairsBlock(CABlocks.MOSSY_RHINESTONE_BRICK_STAIRS.get(), chaosRL("mossy_rhinestone_bricks"));
		stairsBlock(CABlocks.POLISHED_RHINESTONE_STAIRS.get(), chaosRL("polished_rhinestone_block"));
		wallBlock(CABlocks.RHINESTONE_WALL.get(), chaosRL("rhinestone_block"));
		wallBlock(CABlocks.RHINESTONE_BRICK_WALL.get(), chaosRL("rhinestone_bricks"));
		wallBlock(CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get(), chaosRL("chiseled_rhinestone_bricks"));
		wallBlock(CABlocks.CRACKED_RHINESTONE_BRICK_WALL.get(), chaosRL("cracked_rhinestone_bricks"));
		wallBlock(CABlocks.MOSSY_RHINESTONE_BRICK_WALL.get(), chaosRL("mossy_rhinestone_bricks"));
		wallBlock(CABlocks.POLISHED_RHINESTONE_WALL.get(), chaosRL("polished_rhinestone_block"));

		simpleBlock(CABlocks.ROBO_BLOCK_I.get());
		logBlock(CABlocks.ROBO_BLOCK_V.get());
		logBlock(CABlocks.ROBO_BLOCK_X.get());
		slabBlock(CABlocks.ROBO_SLAB_I.get(), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		slabBlock(CABlocks.ROBO_SLAB_X.get(), chaosRL("robo_block_x"), chaosRL("robo_block_x"));
		stairsBlock(CABlocks.ROBO_STAIRS_I.get(), chaosRL("robo_block_l"));
		stairsBlock(CABlocks.ROBO_STAIRS_X.get(), chaosRL("robo_block_x"));
		wallBlock(CABlocks.ROBO_WALL_I.get(), chaosRL("robo_block_l"));
		wallBlock(CABlocks.ROBO_WALL_X.get(), chaosRL("robo_block_x"));
		simpleBlock(CABlocks.ROBO_LAMP.get());
		simpleBlock(CABlocks.ROBO_GLASS.get());
		paneBlock(CABlocks.ROBO_GLASS_PANE.get(), chaosRL("robo_glass"), chaosRL("robo_glass_pane_top"));
		barBlock(CABlocks.ROBO_BARS.get());
		simpleBlock(CABlocks.ROBO_BRICKS.get());
		slabBlock(CABlocks.ROBO_BRICK_SLAB.get(), chaosRL("robo_bricks"), chaosRL("robo_bricks"));
		stairsBlock(CABlocks.ROBO_BRICK_STAIRS.get(), chaosRL("robo_bricks"));
		wallBlock(CABlocks.ROBO_BRICK_WALL.get(), chaosRL("robo_bricks"));
		logBlock(CABlocks.COMPACT_ROBO_BLOCK.get());
		logBlock(CABlocks.DOUBLE_COMPACT_ROBO_BLOCK.get());
		cubeBottomTopBlock(CABlocks.ROBO_GATE_BLOCK.get(), chaosRL("robo_gate_block"), chaosRL("robo_gate_block_top"));
				
		simpleBlock(CABlocks.BLACK_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.BLUE_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.BROWN_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CYAN_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.GRAY_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.GREEN_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.LIME_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.MAGENTA_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.ORANGE_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.PINK_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.PURPLE_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.RED_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.WHITE_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.YELLOW_TERRACOTTA_BRICKS.get());
		slabBlock(CABlocks.BLACK_TERRACOTTA_BRICK_SLAB.get(), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		slabBlock(CABlocks.BLUE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		slabBlock(CABlocks.BROWN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		slabBlock(CABlocks.CYAN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		slabBlock(CABlocks.GRAY_TERRACOTTA_BRICK_SLAB.get(), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		slabBlock(CABlocks.GREEN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		slabBlock(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		slabBlock(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		slabBlock(CABlocks.LIME_TERRACOTTA_BRICK_SLAB.get(), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		slabBlock(CABlocks.MAGENTA_TERRACOTTA_BRICK_SLAB.get(), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		slabBlock(CABlocks.ORANGE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		slabBlock(CABlocks.PINK_TERRACOTTA_BRICK_SLAB.get(), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		slabBlock(CABlocks.PURPLE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		slabBlock(CABlocks.RED_TERRACOTTA_BRICK_SLAB.get(), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		slabBlock(CABlocks.TERRACOTTA_BRICK_SLAB.get(), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		slabBlock(CABlocks.WHITE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		slabBlock(CABlocks.YELLOW_TERRACOTTA_BRICK_SLAB.get(), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		stairsBlock(CABlocks.BLACK_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("black_terracotta_bricks"));
		stairsBlock(CABlocks.BLUE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("blue_terracotta_bricks"));
		stairsBlock(CABlocks.BROWN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("brown_terracotta_bricks"));
		stairsBlock(CABlocks.CYAN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cyan_terracotta_bricks"));
		stairsBlock(CABlocks.GRAY_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("gray_terracotta_bricks"));
		stairsBlock(CABlocks.GREEN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("green_terracotta_bricks"));
		stairsBlock(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("light_blue_terracotta_bricks"));
		stairsBlock(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("light_gray_terracotta_bricks"));
		stairsBlock(CABlocks.LIME_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("lime_terracotta_bricks"));
		stairsBlock(CABlocks.MAGENTA_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("magenta_terracotta_bricks"));
		stairsBlock(CABlocks.ORANGE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("orange_terracotta_bricks"));
		stairsBlock(CABlocks.PINK_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("pink_terracotta_bricks"));
		stairsBlock(CABlocks.PURPLE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("purple_terracotta_bricks"));
		stairsBlock(CABlocks.RED_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("red_terracotta_bricks"));
		stairsBlock(CABlocks.TERRACOTTA_BRICK_STAIRS.get(), chaosRL("terracotta_bricks"));
		stairsBlock(CABlocks.WHITE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("white_terracotta_bricks"));
		stairsBlock(CABlocks.YELLOW_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("yellow_terracotta_bricks"));
		wallBlock(CABlocks.BLACK_TERRACOTTA_BRICK_WALL.get(), chaosRL("black_terracotta_bricks"));
		wallBlock(CABlocks.BLUE_TERRACOTTA_BRICK_WALL.get(), chaosRL("blue_terracotta_bricks"));
		wallBlock(CABlocks.BROWN_TERRACOTTA_BRICK_WALL.get(), chaosRL("brown_terracotta_bricks"));
		wallBlock(CABlocks.CYAN_TERRACOTTA_BRICK_WALL.get(), chaosRL("cyan_terracotta_bricks"));
		wallBlock(CABlocks.GRAY_TERRACOTTA_BRICK_WALL.get(), chaosRL("gray_terracotta_bricks"));
		wallBlock(CABlocks.GREEN_TERRACOTTA_BRICK_WALL.get(), chaosRL("green_terracotta_bricks"));
		wallBlock(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get(), chaosRL("light_blue_terracotta_bricks"));
		wallBlock(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get(), chaosRL("light_gray_terracotta_bricks"));
		wallBlock(CABlocks.LIME_TERRACOTTA_BRICK_WALL.get(), chaosRL("lime_terracotta_bricks"));
		wallBlock(CABlocks.MAGENTA_TERRACOTTA_BRICK_WALL.get(), chaosRL("magenta_terracotta_bricks"));
		wallBlock(CABlocks.ORANGE_TERRACOTTA_BRICK_WALL.get(), chaosRL("orange_terracotta_bricks"));
		wallBlock(CABlocks.PINK_TERRACOTTA_BRICK_WALL.get(), chaosRL("pink_terracotta_bricks"));
		wallBlock(CABlocks.PURPLE_TERRACOTTA_BRICK_WALL.get(), chaosRL("purple_terracotta_bricks"));
		wallBlock(CABlocks.RED_TERRACOTTA_BRICK_WALL.get(), chaosRL("red_terracotta_bricks"));
		wallBlock(CABlocks.TERRACOTTA_BRICK_WALL.get(), chaosRL("terracotta_bricks"));
		wallBlock(CABlocks.WHITE_TERRACOTTA_BRICK_WALL.get(), chaosRL("white_terracotta_bricks"));
		wallBlock(CABlocks.YELLOW_TERRACOTTA_BRICK_WALL.get(), chaosRL("yellow_terracotta_bricks"));

		simpleBlock(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get());
		simpleBlock(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get());
		slabBlock(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_RED_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		slabBlock(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_black_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_blue_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_brown_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_cyan_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_gray_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_green_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_light_blue_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_light_gray_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_lime_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_magenta_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_orange_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_pink_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_purple_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_RED_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_red_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_white_terracotta_bricks"));
		stairsBlock(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_yellow_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_black_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_blue_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_brown_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_cyan_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_gray_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_green_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_light_blue_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_light_gray_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_lime_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_magenta_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_orange_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_pink_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_purple_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_RED_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_red_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_white_terracotta_bricks"));
		wallBlock(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_yellow_terracotta_bricks"));

		logBlock(CABlocks.FLOWER_STEM.get());
		simpleBlock(CABlocks.NAVY_BLUE_PETAL_BLOCK.get());
		simpleBlock(CABlocks.BLOOD_RED_PETAL_BLOCK.get());
		simpleBlock(CABlocks.BRIGHT_PINK_PETAL_BLOCK.get());

		for (Block block : ForgeRegistries.BLOCKS) {
			if (!ChaosAwakens.MODID.equals(Objects.requireNonNull(block.getRegistryName()).getNamespace())) continue;

			String name = block.getRegistryName().getPath();
			ChaosAwakens.LOGGER.debug(block.getRegistryName());

			if (block instanceof CAOreBlock || block instanceof CAFallingOreBlock) {
				if (name.contains("sandstone")) {
					cubeBottomTopBlock(block, chaosRL(name), mcRL("sandstone_bottom"), mcRL("sandstone_top"));
				} else if (name.contains("ant_infested") || name.contains("termite_infested")) {
					simpleBlock(block, name.contains("ant_infested") ? Blocks.DIAMOND_ORE : Blocks.EMERALD_ORE);
				} else {
					noVariant(block);
				}
			}
		}

		simpleBlock(CABlocks.MOTH_SCALE_BLOCK.get());
		simpleBlock(CABlocks.WATER_DRAGON_SCALE_BLOCK.get());
		simpleBlock(CABlocks.ENDER_DRAGON_SCALE_BLOCK.get());
		simpleBlock(CABlocks.NIGHTMARE_SCALE_BLOCK.get());
		simpleBlock(CABlocks.MOBZILLA_SCALE_BLOCK.get());
		simpleBlock(CABlocks.ROYAL_GUARDIAN_SCALE_BLOCK.get());
		simpleBlock(CABlocks.QUEEN_SCALE_BLOCK.get());
		simpleBlock(CABlocks.BASILISK_SCALE_BLOCK.get());
		simpleBlock(CABlocks.EMPEROR_SCORPION_SCALE_BLOCK.get());

		simpleBlock(CABlocks.SALT_BLOCK.get());

		logBlock(CABlocks.APPLE_LOG.get());
		woodBlock(CABlocks.APPLE_WOOD.get(), chaosRL("apple_log"));
		simpleBlock(CABlocks.APPLE_PLANKS.get());
		leafCarpetBlock(CABlocks.APPLE_LEAF_CARPET.get(), chaosRL("apple_leaves"));
		logBlock(CABlocks.STRIPPED_APPLE_LOG.get());
		woodBlock(CABlocks.STRIPPED_APPLE_WOOD.get(), chaosRL("stripped_apple_log"));
		logBlock(CABlocks.CHERRY_LOG.get());
		woodBlock(CABlocks.CHERRY_WOOD.get(), chaosRL("cherry_log"));
		simpleBlock(CABlocks.CHERRY_PLANKS.get());
		leafCarpetBlock(CABlocks.CHERRY_LEAF_CARPET.get(), chaosRL("cherry_leaves"));
		logBlock(CABlocks.STRIPPED_CHERRY_LOG.get());
		woodBlock(CABlocks.STRIPPED_CHERRY_WOOD.get(), chaosRL("stripped_cherry_log"));
		logBlock(CABlocks.DUPLICATION_LOG.get());
		woodBlock(CABlocks.DUPLICATION_WOOD.get(), chaosRL("duplication_log"));
		logBlock(CABlocks.DEAD_DUPLICATION_LOG.get());
		woodBlock(CABlocks.DEAD_DUPLICATION_WOOD.get(), chaosRL("dead_duplication_log"));
		logBlock(CABlocks.STRIPPED_DEAD_DUPLICATION_LOG.get());
		woodBlock(CABlocks.STRIPPED_DEAD_DUPLICATION_WOOD.get(), chaosRL("stripped_dead_duplication_log"));
		simpleBlock(CABlocks.DUPLICATION_PLANKS.get());
		simpleBlock(CABlocks.DUPLICATION_LEAVES.get());
		leafCarpetBlock(CABlocks.DUPLICATION_LEAF_CARPET.get(), chaosRL("duplication_leaves"));
		logBlock(CABlocks.STRIPPED_DUPLICATION_LOG.get());
		woodBlock(CABlocks.STRIPPED_DUPLICATION_WOOD.get(), chaosRL("stripped_duplication_log"));
		cross(CABlocks.GINKGO_SAPLING.get());
		logBlock(CABlocks.GINKGO_LOG.get());
		woodBlock(CABlocks.GINKGO_WOOD.get(), chaosRL("ginkgo_log"));
		simpleBlock(CABlocks.GINKGO_PLANKS.get());
		simpleBlock(CABlocks.GINKGO_LEAVES.get());
		leafCarpetBlock(CABlocks.GINKGO_LEAF_CARPET.get(), chaosRL("ginkgo_leaves"));
		logBlock(CABlocks.STRIPPED_GINKGO_LOG.get());
		woodBlock(CABlocks.STRIPPED_GINKGO_WOOD.get(), chaosRL("stripped_ginkgo_log"));
		cross(CABlocks.MESOZOIC_SAPLING.get());
		logBlock(CABlocks.MESOZOIC_LOG.get());
		woodBlock(CABlocks.MESOZOIC_WOOD.get(), chaosRL("mesozoic_log"));
		simpleBlock(CABlocks.MESOZOIC_PLANKS.get());
		simpleBlock(CABlocks.MESOZOIC_LEAVES.get());
		leafCarpetBlock(CABlocks.MESOZOIC_LEAF_CARPET.get(), chaosRL("mesozoic_leaves"));
		logBlock(CABlocks.STRIPPED_MESOZOIC_LOG.get());
		woodBlock(CABlocks.STRIPPED_MESOZOIC_WOOD.get(), chaosRL("stripped_mesozoic_log"));
		cross(CABlocks.DENSEWOOD_SAPLING.get());
		logBlock(CABlocks.DENSEWOOD_LOG.get());
		woodBlock(CABlocks.DENSEWOOD.get(), chaosRL("densewood_log"));
		simpleBlock(CABlocks.DENSEWOOD_PLANKS.get());
		simpleBlock(CABlocks.DENSEWOOD_LEAVES.get());
		leafCarpetBlock(CABlocks.DENSEWOOD_LEAF_CARPET.get(), chaosRL("densewood_leaves"));
		logBlock(CABlocks.STRIPPED_DENSEWOOD_LOG.get());
		woodBlock(CABlocks.STRIPPED_DENSEWOOD.get(), chaosRL("stripped_densewood_log"));
		logBlock(CABlocks.PEACH_LOG.get());
		woodBlock(CABlocks.PEACH_WOOD.get(), chaosRL("peach_log"));
		simpleBlock(CABlocks.PEACH_PLANKS.get());
		leafCarpetBlock(CABlocks.PEACH_LEAF_CARPET.get(), chaosRL("peach_leaves"));
		logBlock(CABlocks.STRIPPED_PEACH_LOG.get());
		woodBlock(CABlocks.STRIPPED_PEACH_WOOD.get(), chaosRL("stripped_peach_log"));
		logBlock(CABlocks.SKYWOOD_LOG.get());
		woodBlock(CABlocks.SKYWOOD.get(), chaosRL("skywood_log"));
		simpleBlock(CABlocks.SKYWOOD_PLANKS.get());
		simpleBlock(CABlocks.SKYWOOD_LEAVES.get());
		leafCarpetBlock(CABlocks.SKYWOOD_LEAF_CARPET.get(), chaosRL("skywood_leaves"));
		logBlock(CABlocks.STRIPPED_SKYWOOD_LOG.get());
		woodBlock(CABlocks.STRIPPED_SKYWOOD.get(), chaosRL("stripped_skywood_log"));
		crystalLogBlock(CABlocks.CRYSTALWOOD_LOG.get());
		crystalWoodBlock(CABlocks.CRYSTALWOOD.get(), chaosRL("crystalwood_log"));
		simpleBlock(CABlocks.CRYSTALWOOD_PLANKS.get());
		simpleBlock(CABlocks.RED_CRYSTAL_LEAVES.get());
		simpleBlock(CABlocks.GREEN_CRYSTAL_LEAVES.get());
		simpleBlock(CABlocks.YELLOW_CRYSTAL_LEAVES.get());
		simpleBlock(CABlocks.PINK_CRYSTAL_LEAVES.get());
		simpleBlock(CABlocks.BLUE_CRYSTAL_LEAVES.get());
		simpleBlock(CABlocks.ORANGE_CRYSTAL_LEAVES.get());
		leafCarpetBlock(CABlocks.RED_CRYSTAL_LEAF_CARPET.get(), chaosRL("red_crystal_leaves"));
		leafCarpetBlock(CABlocks.GREEN_CRYSTAL_LEAF_CARPET.get(), chaosRL("green_crystal_leaves"));
		leafCarpetBlock(CABlocks.YELLOW_CRYSTAL_LEAF_CARPET.get(), chaosRL("yellow_crystal_leaves"));
		leafCarpetBlock(CABlocks.PINK_CRYSTAL_LEAF_CARPET.get(), chaosRL("pink_crystal_leaves"));
		leafCarpetBlock(CABlocks.BLUE_CRYSTAL_LEAF_CARPET.get(), chaosRL("blue_crystal_leaves"));
		leafCarpetBlock(CABlocks.ORANGE_CRYSTAL_LEAF_CARPET.get(), chaosRL("orange_crystal_leaves"));
		stairsBlock(CABlocks.APPLE_STAIRS.get(), chaosRL("apple_planks"));
		stairsBlock(CABlocks.CHERRY_STAIRS.get(), chaosRL("cherry_planks"));
		stairsBlock(CABlocks.DUPLICATION_STAIRS.get(), chaosRL("duplication_planks"));
		stairsBlock(CABlocks.GINKGO_STAIRS.get(), chaosRL("ginkgo_planks"));
		stairsBlock(CABlocks.MESOZOIC_STAIRS.get(), chaosRL("mesozoic_planks"));
		stairsBlock(CABlocks.DENSEWOOD_STAIRS.get(), chaosRL("densewood_planks"));
		stairsBlock(CABlocks.PEACH_STAIRS.get(), chaosRL("peach_planks"));
		stairsBlock(CABlocks.SKYWOOD_STAIRS.get(), chaosRL("skywood_planks"));
		stairsBlock(CABlocks.CRYSTALWOOD_STAIRS.get(), chaosRL("crystalwood_planks"));
		slabBlock(CABlocks.APPLE_SLAB.get(), chaosRL("apple_planks"), chaosRL("apple_planks"));
		slabBlock(CABlocks.CHERRY_SLAB.get(), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		slabBlock(CABlocks.DUPLICATION_SLAB.get(), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		slabBlock(CABlocks.GINKGO_SLAB.get(), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		slabBlock(CABlocks.MESOZOIC_SLAB.get(), chaosRL("mesozoic_planks"), chaosRL("mesozoic_planks"));
		slabBlock(CABlocks.DENSEWOOD_SLAB.get(), chaosRL("densewood_planks"), chaosRL("densewood_planks"));
		slabBlock(CABlocks.PEACH_SLAB.get(), chaosRL("peach_planks"), chaosRL("peach_planks"));
		slabBlock(CABlocks.SKYWOOD_SLAB.get(), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		slabBlock(CABlocks.CRYSTALWOOD_SLAB.get(), chaosRL("crystalwood_planks"), chaosRL("crystalwood_planks"));
		fenceBlock(CABlocks.APPLE_FENCE.get(), chaosRL("apple_planks"));
		fenceBlock(CABlocks.CHERRY_FENCE.get(), chaosRL("cherry_planks"));
		fenceBlock(CABlocks.DUPLICATION_FENCE.get(), chaosRL("duplication_planks"));
		fenceBlock(CABlocks.GINKGO_FENCE.get(), chaosRL("ginkgo_planks"));
		fenceBlock(CABlocks.MESOZOIC_FENCE.get(), chaosRL("mesozoic_planks"));
		fenceBlock(CABlocks.DENSEWOOD_FENCE.get(), chaosRL("densewood_planks"));
		fenceBlock(CABlocks.PEACH_FENCE.get(), chaosRL("peach_planks"));
		fenceBlock(CABlocks.SKYWOOD_FENCE.get(), chaosRL("skywood_planks"));
		fenceBlock(CABlocks.CRYSTALWOOD_FENCE.get(), chaosRL("crystalwood_planks"));
		fenceGateBlock(CABlocks.APPLE_FENCE_GATE.get(), chaosRL("apple_planks"));
		fenceGateBlock(CABlocks.CHERRY_FENCE_GATE.get(), chaosRL("cherry_planks"));
		fenceGateBlock(CABlocks.DUPLICATION_FENCE_GATE.get(), chaosRL("duplication_planks"));
		fenceGateBlock(CABlocks.GINKGO_FENCE_GATE.get(), chaosRL("ginkgo_planks"));
		fenceGateBlock(CABlocks.MESOZOIC_FENCE_GATE.get(), chaosRL("mesozoic_planks"));
		fenceGateBlock(CABlocks.DENSEWOOD_FENCE_GATE.get(), chaosRL("densewood_planks"));
		fenceGateBlock(CABlocks.PEACH_FENCE_GATE.get(), chaosRL("peach_planks"));
		fenceGateBlock(CABlocks.SKYWOOD_FENCE_GATE.get(), chaosRL("skywood_planks"));
		fenceGateBlock(CABlocks.CRYSTALWOOD_FENCE_GATE.get(), chaosRL("crystalwood_planks"));
		pressurePlateBlock(CABlocks.APPLE_PRESSURE_PLATE.get(), chaosRL("apple_planks"));
		pressurePlateBlock(CABlocks.CHERRY_PRESSURE_PLATE.get(), chaosRL("cherry_planks"));
		pressurePlateBlock(CABlocks.DUPLICATION_PRESSURE_PLATE.get(), chaosRL("duplication_planks"));
		pressurePlateBlock(CABlocks.GINKGO_PRESSURE_PLATE.get(), chaosRL("ginkgo_planks"));
		pressurePlateBlock(CABlocks.MESOZOIC_PRESSURE_PLATE.get(), chaosRL("mesozoic_planks"));
		pressurePlateBlock(CABlocks.DENSEWOOD_PRESSURE_PLATE.get(), chaosRL("densewood_planks"));
		pressurePlateBlock(CABlocks.PEACH_PRESSURE_PLATE.get(), chaosRL("peach_planks"));
		pressurePlateBlock(CABlocks.SKYWOOD_PRESSURE_PLATE.get(), chaosRL("skywood_planks"));
		pressurePlateBlock(CABlocks.CRYSTAL_PRESSURE_PLATE.get(), chaosRL("crystalwood_planks"));
		buttonBlock(CABlocks.APPLE_BUTTON.get(), chaosRL("apple_planks"));
		buttonBlock(CABlocks.CHERRY_BUTTON.get(), chaosRL("cherry_planks"));
		buttonBlock(CABlocks.DUPLICATION_BUTTON.get(), chaosRL("duplication_planks"));
		buttonBlock(CABlocks.GINKGO_BUTTON.get(), chaosRL("ginkgo_planks"));
		buttonBlock(CABlocks.MESOZOIC_BUTTON.get(), chaosRL("mesozoic_planks"));
		buttonBlock(CABlocks.DENSEWOOD_BUTTON.get(), chaosRL("densewood_planks"));
		buttonBlock(CABlocks.PEACH_BUTTON.get(), chaosRL("peach_planks"));
		buttonBlock(CABlocks.SKYWOOD_BUTTON.get(), chaosRL("skywood_planks"));
		buttonBlock(CABlocks.CRYSTALWOOD_BUTTON.get(), chaosRL("crystalwood_planks"));
		trapdoorBlock(CABlocks.APPLE_TRAPDOOR.get());
		trapdoorBlock(CABlocks.CHERRY_TRAPDOOR.get());
		trapdoorBlock(CABlocks.DUPLICATION_TRAPDOOR.get());
		trapdoorBlock(CABlocks.GINKGO_TRAPDOOR.get());
		trapdoorBlock(CABlocks.MESOZOIC_TRAPDOOR.get());
		trapdoorBlock(CABlocks.DENSEWOOD_TRAPDOOR.get());
		trapdoorBlock(CABlocks.PEACH_TRAPDOOR.get());
		trapdoorBlock(CABlocks.SKYWOOD_TRAPDOOR.get());
		trapdoorBlock(CABlocks.CRYSTALWOOD_TRAPDOOR.get());
		doorBlock(CABlocks.APPLE_DOOR.get());
		doorBlock(CABlocks.CHERRY_DOOR.get());
		doorBlock(CABlocks.DUPLICATION_DOOR.get());
		doorBlock(CABlocks.GINKGO_DOOR.get());
		doorBlock(CABlocks.MESOZOIC_DOOR.get());
		doorBlock(CABlocks.DENSEWOOD_DOOR.get());
		doorBlock(CABlocks.PEACH_DOOR.get());
		doorBlock(CABlocks.SKYWOOD_DOOR.get());
		doorBlock(CABlocks.CRYSTALWOOD_DOOR.get());

		simpleBlock(CABlocks.SKY_MOSS_BLOCK.get(), models().cubeAll(CABlocks.SKY_MOSS_BLOCK.getId().getPath(), chaosRL("sky_moss")));
		leafCarpetBlock(CABlocks.SKY_MOSS_CARPET.get(), chaosRL("sky_moss"));

		simpleBlock(CABlocks.MOLDY_PLANKS.get());
		slabBlock(CABlocks.MOLDY_SLAB.get(), chaosRL("moldy_planks"), chaosRL("moldy_planks"));
		fenceBlock(CABlocks.MOLDY_FENCE.get(), chaosRL("moldy_planks"));

		simpleBlock(CABlocks.WASP_NEST_BLOCK.get());
		simpleBlock(CABlocks.PATTERN_WASP_NEST_BLOCK.get());

		cubeBottomTopBlock(CABlocks.APPLE_GATE_BLOCK.get(), chaosRL("apple_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.CHERRY_GATE_BLOCK.get(), chaosRL("cherry_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.DUPLICATION_GATE_BLOCK.get(), chaosRL("duplication_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.GINKGO_GATE_BLOCK.get(), chaosRL("ginkgo_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.PEACH_GATE_BLOCK.get(), chaosRL("peach_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.SKYWOOD_GATE_BLOCK.get(), chaosRL("skywood_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.MUSHROOM_GATE_BLOCK.get(), chaosRL("mushroom_gate_block"), chaosRL("gate_block_top"));

		cubeBottomTopBlock(CABlocks.ACACIA_GATE_BLOCK.get(), chaosRL("acacia_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.BIRCH_GATE_BLOCK.get(), chaosRL("birch_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.CRIMSON_GATE_BLOCK.get(), chaosRL("crimson_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.DARK_OAK_GATE_BLOCK.get(), chaosRL("dark_oak_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.JUNGLE_GATE_BLOCK.get(), chaosRL("jungle_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.OAK_GATE_BLOCK.get(), chaosRL("oak_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.SPRUCE_GATE_BLOCK.get(), chaosRL("spruce_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.WARPED_GATE_BLOCK.get(), chaosRL("warped_gate_block"), chaosRL("gate_block_top"));
		cubeBottomTopBlock(CABlocks.MUSHROOM_GATE_BLOCK.get(), chaosRL("mushroom_gate_block"), chaosRL("gate_block_top"));

		leafCarpetBlock(CABlocks.OAK_LEAF_CARPET.get(), mcRL("oak_leaves"));
		leafCarpetBlock(CABlocks.SPRUCE_LEAF_CARPET.get(), mcRL("spruce_leaves"));
		leafCarpetBlock(CABlocks.BIRCH_LEAF_CARPET.get(), mcRL("birch_leaves"));
		leafCarpetBlock(CABlocks.JUNGLE_LEAF_CARPET.get(), mcRL("jungle_leaves"));
		leafCarpetBlock(CABlocks.ACACIA_LEAF_CARPET.get(), mcRL("acacia_leaves"));
		leafCarpetBlock(CABlocks.DARK_OAK_LEAF_CARPET.get(), mcRL("dark_oak_leaves"));
		
		noVariant(CABlocks.POTTED_DENSE_ORCHID.get());
		noVariant(CABlocks.POTTED_SWAMP_MILKWEED.get());
		noVariant(CABlocks.POTTED_SMALL_BUSH.get());
		noVariant(CABlocks.POTTED_SMALL_CARNIVOROUS_PLANT.get());
		noVariant(CABlocks.POTTED_BIG_CARNIVOROUS_PLANT.get());
		noVariant(CABlocks.POTTED_PRIMROSE.get());
		noVariant(CABlocks.POTTED_DAISY.get());
		noVariant(CABlocks.POTTED_GINKGO_SAPLING.get());
		noVariant(CABlocks.POTTED_MESOZOIC_SAPLING.get());
		noVariant(CABlocks.POTTED_DENSEWOOD_SAPLING.get());
		noVariant(CABlocks.POTTED_APPLE_SAPLING.get());
		noVariant(CABlocks.POTTED_CHERRY_SAPLING.get());
		
		sign(CABlocks.MESOZOIC_SIGN.get(), CABlocks.MESOZOIC_WALL_SIGN.get());
		sign(CABlocks.DENSEWOOD_SIGN.get(), CABlocks.DENSEWOOD_WALL_SIGN.get());
	}

	private String name(Block block) {
		return Objects.requireNonNull(block.getRegistryName()).getPath();
	}

	private ResourceLocation extend(ResourceLocation rl, String suffix) {
		return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
	}

	private static ResourceLocation getResourceLocation(String path) {
		return ChaosAwakens.prefix(path);
	}

	private static ResourceLocation getBlockResourceLocation(String name) {
		return getResourceLocation("block/" + name);
	}

	public void simpleBlock(Block block, Block other) {
		simpleBlock(block, models().getExistingFile(other.getRegistryName()));
	}

	public void cross(Block block) {
		ModelFile cross = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(block.getRegistryName()).getPath()));
		getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(cross).build());
	}

	public void doubleCross(DoubleDensePlantBlock block) {
		ModelFile crossLower = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(block.getRegistryName()).getPath() + "_bottom"));
		ModelFile crossUpper = models().getExistingFile(getBlockResourceLocation(block.getRegistryName().getPath() + "_top"));

		getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
				.modelFile(state.getValue(DoubleDensePlantBlock.HALF) == DoubleBlockHalf.LOWER ? crossLower : crossUpper)
				.build());
	}

	public void grassBlock(Block block, ResourceLocation particle, ResourceLocation bottom, ResourceLocation top, ResourceLocation side, ResourceLocation overlay) {
		grassBlock(block, models().getExistingFile(getBlockResourceLocation(block.getRegistryName().getPath())));
	}
	
	public void grassBlock(Block block, ModelFile model) {
		grassBlock(block, new ConfiguredModel(model));
	}
	
	public void grassBlock(Block block, ConfiguredModel... model) {
		getVariantBuilder(block).partialState().addModels(model);
	}
	
	public void cubeBottomTopBlock(GateBlock block, ResourceLocation side, ResourceLocation top) {
		cubeBottomTopBlock(block, models().cubeBottomTop(name(block), side, top, top));
	}

	public void cubeBottomTopBlock(Block block, ResourceLocation side, ResourceLocation top, ResourceLocation bottom) {
		cubeBottomTopBlock(block, models().cubeBottomTop(name(block), side, top, bottom));
	}

	public void cubeBottomTopBlock(Block block, ModelFile model) {
		cubeBottomTopBlock(block, new ConfiguredModel(model));
	}

	public void cubeBottomTopBlock(Block block, ConfiguredModel... model) {
		getVariantBuilder(block).partialState().addModels(model);
	}

	public void crystalLogBlock(RotatedPillarCrystalBlock block) {
		crystalAxisBlock(block, blockTexture(block), extend(blockTexture(block), "_top"));
	}

	public void crystalAxisBlock(RotatedPillarCrystalBlock block, ResourceLocation side, ResourceLocation end) {
		crystalAxisBlock(block, models().cubeColumn(name(block), side, end), models().cubeColumnHorizontal(name(block) + "_horizontal", side, end));
	}

	public void crystalAxisBlock(RotatedPillarCrystalBlock block, ModelFile vertical, ModelFile horizontal) {
		getVariantBuilder(block)
				.partialState().with(RotatedPillarCrystalBlock.AXIS, Direction.Axis.Y).modelForState().modelFile(vertical).addModel()
				.partialState().with(RotatedPillarCrystalBlock.AXIS, Direction.Axis.Z).modelForState().modelFile(horizontal).rotationX(90).addModel()
				.partialState().with(RotatedPillarCrystalBlock.AXIS, Direction.Axis.X).modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();
	}

	public void crystalWoodBlock(RotatedPillarCrystalBlock block, ResourceLocation texture) {
		crystalAxisBlock(block, texture, texture);
	}

	public void woodBlock(RotatedPillarBlock block, ResourceLocation texture) {
		axisBlock(block, texture, texture);
	}

	public void farmland(GenericFarmlandBlock farm) {
		ModelFile normal = models().getExistingFile(chaosRL(farm.getRegistryName().getPath()));
		ModelFile moist = models().getExistingFile(chaosRL(farm.getRegistryName().getPath() + "_moist"));
		getVariantBuilder(farm).forAllStatesExcept(state -> ConfiguredModel.builder()
				.modelFile(state.getValue(GenericFarmlandBlock.MOISTURE) == 7 ? moist : normal).build());
	}

	public void pressurePlateBlock(PressurePlateBlock block, ResourceLocation all) {
		pressurePlateBlockInternal(block, name(block), all);
	}

	private void pressurePlateBlockInternal(PressurePlateBlock block, String baseName, ResourceLocation all) {
		ModelFile pressurePlateUp = models().withExistingParent(baseName, mcRL("pressure_plate_up")).texture("texture", all);
		ModelFile pressurePlateDown = models().withExistingParent(baseName + "_down", mcRL("pressure_plate_down")).texture("texture", all);
		pressurePlateBlock(block, pressurePlateUp, pressurePlateDown);
	}

	public void pressurePlateBlock(PressurePlateBlock block, ModelFile pressurePlateUp, ModelFile pressurePlateDown) {
		getVariantBuilder(block).forAllStates(state -> {
			Boolean powered = state.getValue(PressurePlateBlock.POWERED);
			return ConfiguredModel.builder().modelFile(powered ? pressurePlateDown : pressurePlateUp).build();
		});
	}

	public void trapdoorBlock(TrapDoorBlock block) {
		String name = Objects.requireNonNull(block.getRegistryName()).getPath();
		ModelFile bottom = models().getExistingFile(getBlockResourceLocation(name + "_bottom"));
		ModelFile top = models().getExistingFile(getBlockResourceLocation(name + "_top"));
		ModelFile open = models().getExistingFile(getBlockResourceLocation(name + "_open"));
		trapdoorBlock(block, bottom, top, open, true);
	}
	
	public void noVariant(Block block) {
		ModelFile model = models().getExistingFile(block.getRegistryName());
		getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
	}
	
	public void sign(Block standing, Block wall) {
		ModelFile standingModel = models().getExistingFile(standing.getRegistryName());
		getVariantBuilder(standing).partialState().addModels(new ConfiguredModel(standingModel));
		getVariantBuilder(wall).partialState().addModels(new ConfiguredModel(standingModel));
	}

	public void barBlock(Block targetBlock) {
		ModelFile posteEndsLoc = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_post_ends"));
		ModelFile postLoc = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_post"));
		ModelFile capLoc = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_cap"));
		ModelFile capAltLoc = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_cap_alt"));
		ModelFile sideLoc = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_side"));
		ModelFile sideAltLoc = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_side_alt"));

		getMultipartBuilder(targetBlock)
				.part().modelFile(posteEndsLoc).addModel()
				.end()

				.part().modelFile(postLoc).addModel()
				.condition(BlockStateProperties.WEST, false)
				.condition(BlockStateProperties.EAST, false)
				.condition(BlockStateProperties.SOUTH, false)
				.condition(BlockStateProperties.NORTH, false)
				.end()

				.part().modelFile(capLoc).addModel()
				.condition(BlockStateProperties.WEST, false)
				.condition(BlockStateProperties.EAST, false)
				.condition(BlockStateProperties.SOUTH, false)
				.condition(BlockStateProperties.NORTH, true)
				.end()

				.part().modelFile(capLoc).rotationY(90).addModel()
				.condition(BlockStateProperties.WEST, false)
				.condition(BlockStateProperties.EAST, true)
				.condition(BlockStateProperties.SOUTH, false)
				.condition(BlockStateProperties.NORTH, false)
				.end()

				.part().modelFile(capAltLoc).addModel()
				.condition(BlockStateProperties.WEST, false)
				.condition(BlockStateProperties.EAST, false)
				.condition(BlockStateProperties.SOUTH, true)
				.condition(BlockStateProperties.NORTH, false)
				.end()

				.part().modelFile(capAltLoc).rotationY(90).addModel()
				.condition(BlockStateProperties.WEST, true)
				.condition(BlockStateProperties.EAST, false)
				.condition(BlockStateProperties.SOUTH, false)
				.condition(BlockStateProperties.NORTH, false)
				.end()

				.part().modelFile(capAltLoc).addModel()
				.condition(BlockStateProperties.WEST, false)
				.condition(BlockStateProperties.EAST, false)
				.condition(BlockStateProperties.SOUTH, true)
				.condition(BlockStateProperties.NORTH, false)
				.end()

				.part().modelFile(sideLoc).addModel()
				.condition(BlockStateProperties.NORTH, true)
				.end()

				.part().modelFile(sideLoc).rotationY(90).addModel()
				.condition(BlockStateProperties.EAST, true)
				.end()

				.part().modelFile(sideAltLoc).addModel()
				.condition(BlockStateProperties.SOUTH, true)
				.end()

				.part().modelFile(sideAltLoc).rotationY(90).addModel()
				.condition(BlockStateProperties.WEST, true)
				.end();
	}
	
	public void doorBlock(DoorBlock block) {
		ModelFile bottom = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(block.getRegistryName()).getPath() + "_bottom"));
		ModelFile bottomHinge = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(block.getRegistryName()).getPath() + "_bottom_hinge"));
		ModelFile top = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(block.getRegistryName()).getPath() + "_top"));
		ModelFile topHinge = models().getExistingFile(getBlockResourceLocation(Objects.requireNonNull(block.getRegistryName()).getPath() + "_top_hinge"));
		getVariantBuilder(block).forAllStatesExcept(state -> {
			int yRot = ((int) state.getValue(DoorBlock.FACING).toYRot()) + 90;
			boolean rh = state.getValue(DoorBlock.HINGE) == DoorHingeSide.RIGHT;
			boolean open = state.getValue(DoorBlock.OPEN);
			boolean right = rh ^ open;
			if (open) {
				yRot += 90;
			}
			if (rh && open) {
				yRot += 180;
			}
			yRot %= 360;
			return ConfiguredModel.builder().modelFile(state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? (right ? bottomHinge : bottom) : (right ? topHinge : top))
					.rotationY(yRot)
					.build();
		}, DoorBlock.POWERED);
	}

	public void buttonBlock(AbstractButtonBlock block, ResourceLocation textureName) {
		ModelFile unpressed = models().withExistingParent(Objects.requireNonNull(block.getRegistryName()).getPath(), "button").texture("texture", textureName);
		ModelFile pressed = models().withExistingParent(block.getRegistryName().getPath() + "_pressed", "button_pressed").texture("texture", textureName);
		ModelFile inventory = models().withExistingParent(block.getRegistryName().getPath() + "_inventory", "button_inventory").texture("texture", textureName);

		getVariantBuilder(block).forAllStates(state -> {
			int rotX = 0;
			switch (state.getValue(HorizontalFaceBlock.FACE)) {
				case CEILING:
					rotX = 180;
					break;
				case FLOOR:
					break;
				case WALL:
					rotX = 90;
			}
			int rotY = 0;
			if (state.getValue(HorizontalFaceBlock.FACE) == AttachFace.CEILING) {
				switch (state.getValue(HorizontalBlock.FACING)) {
					case NORTH:
						rotY = 180;
						break;
					case SOUTH:
						break;
					case WEST:
						rotY = 90;
						break;
					case EAST:
						rotY = 270;
				}
			} else {
				switch (state.getValue(HorizontalBlock.FACING)) {
					case NORTH:
						break;
					case SOUTH:
						rotY = 180;
						break;
					case WEST:
						rotY = 270;
						break;
					case EAST:
						rotY = 90;
				}
			}
			inventory.assertExistence();
			ModelFile model0 = state.getValue(AbstractButtonBlock.POWERED) ? pressed : unpressed;
			boolean uvlock = state.getValue(HorizontalFaceBlock.FACE) == AttachFace.WALL;

			return ConfiguredModel.builder().uvLock(uvlock).rotationX(rotX).rotationY(rotY).modelFile(model0).build();
		});
	}

	public void leafCarpetBlock(LeafCarpetBlock block, ResourceLocation textureName) {
		ModelFile carpet = models().withExistingParent(Objects.requireNonNull(block.getRegistryName()).getPath(), ChaosAwakens.MODID + ":leaf_carpet").texture("texture", textureName);

		getMultipartBuilder(block)
				.part().modelFile(carpet).addModel()
					.condition(PipeBlock.NORTH, true).end()
				.part().modelFile(carpet).addModel()
					.condition(PipeBlock.DOWN, false)
					.condition(PipeBlock.EAST, false)
					.condition(PipeBlock.NORTH, false)
					.condition(PipeBlock.SOUTH, false)
					.condition(PipeBlock.UP, false)
					.condition(PipeBlock.WEST, false).end()
				.part().modelFile(carpet).uvLock(true).rotationY(90).addModel()
					.condition(PipeBlock.EAST, true).end()
				.part().modelFile(carpet).uvLock(true).rotationY(90).addModel()
					.condition(PipeBlock.DOWN, false)
					.condition(PipeBlock.EAST, false)
					.condition(PipeBlock.NORTH, false)
					.condition(PipeBlock.SOUTH, false)
					.condition(PipeBlock.UP, false)
					.condition(PipeBlock.WEST, false).end()
				.part().modelFile(carpet).uvLock(true).rotationY(180).addModel()
					.condition(PipeBlock.SOUTH, true).end()
				.part().modelFile(carpet).uvLock(true).rotationY(180).addModel()
					.condition(PipeBlock.DOWN, false)
					.condition(PipeBlock.EAST, false)
					.condition(PipeBlock.NORTH, false)
					.condition(PipeBlock.SOUTH, false)
					.condition(PipeBlock.UP, false)
					.condition(PipeBlock.WEST, false).end()
				.part().modelFile(carpet).uvLock(true).rotationY(270).addModel()
					.condition(PipeBlock.WEST, true).end()
				.part().modelFile(carpet).uvLock(true).rotationY(270).addModel()
					.condition(PipeBlock.DOWN, false)
					.condition(PipeBlock.EAST, false)
					.condition(PipeBlock.NORTH, false)
					.condition(PipeBlock.SOUTH, false)
					.condition(PipeBlock.UP, false)
					.condition(PipeBlock.WEST, false).end()
				.part().modelFile(carpet).uvLock(true).rotationX(270).addModel()
					.condition(PipeBlock.UP, true).end()
				.part().modelFile(carpet).uvLock(true).rotationX(270).addModel()
					.condition(PipeBlock.DOWN, false)
					.condition(PipeBlock.EAST, false)
					.condition(PipeBlock.NORTH, false)
					.condition(PipeBlock.SOUTH, false)
					.condition(PipeBlock.UP, false)
					.condition(PipeBlock.WEST, false).end()
				.part().modelFile(carpet).uvLock(true).rotationX(90).addModel()
					.condition(PipeBlock.DOWN, true).end()
				.part().modelFile(carpet).uvLock(true).rotationX(90).addModel()
					.condition(PipeBlock.DOWN, false)
					.condition(PipeBlock.EAST, false)
					.condition(PipeBlock.NORTH, false)
					.condition(PipeBlock.SOUTH, false)
					.condition(PipeBlock.UP, false)
					.condition(PipeBlock.WEST, false).end();
	}

	private void wireBlock(WiredRoboBlock targetBlock, ResourceLocation textureName) { //TODO Harcoded ahh
		ModelFile wiredRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath()));
		ModelFile wiredTopRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_top"));
		ModelFile wiredCenterRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_center"));
		ModelFile wiredBottomRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_bottom"));
		ModelFile wiredNorth2WayRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_2_north"));
		ModelFile wiredSouth2WayRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_2_south"));
		ModelFile wiredTop3WayRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_2_south"));
		ModelFile wiredBottom3WayRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_2_south"));
		ModelFile wiredNorth3WayRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_2_south"));
		ModelFile wiredSouth3WayRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_2_south"));
		ModelFile wiredEast3WayRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_2_south"));
		ModelFile wiredWest3WayRoboBlockModel = models().getExistingFile(chaosRL(Objects.requireNonNull(targetBlock.getRegistryName()).getPath() + "_2_south"));

		//N/S = Z, E/W = X, U/D = Y, N == W, S == E
		getMultipartBuilder(targetBlock).part()
				.modelFile(wiredRoboBlockModel).addModel()
				.condition(WiredRoboBlock.FORCED_DEFAULT, true)
				.end()

				.part()
				.modelFile(wiredBottomRoboBlockModel).rotationX(90).rotationY(90).uvLock(true).addModel()
				.condition(WiredRoboBlock.NORTH, false)
				.condition(WiredRoboBlock.SOUTH, false)
				.condition(WiredRoboBlock.EAST, false)
				.condition(WiredRoboBlock.WEST, false)
				.condition(WiredRoboBlock.ABOVE, false)
				.condition(WiredRoboBlock.BELOW, false)
				.condition(WiredRoboBlock.AXIS, Direction.Axis.X)
				.end()

				.part()
				.modelFile(wiredBottomRoboBlockModel).rotationX(90).rotationY(90).uvLock(true).addModel()
				.condition(WiredRoboBlock.NORTH, true)
				.condition(WiredRoboBlock.AXIS, Direction.Axis.X)
				.end()

				.part()
				.modelFile(wiredTopRoboBlockModel).rotationX(90).rotationY(90).uvLock(true).addModel()
				.condition(WiredRoboBlock.SOUTH, true)
				.condition(WiredRoboBlock.AXIS, Direction.Axis.X)
				.end()

				.part()
				.modelFile(wiredCenterRoboBlockModel).rotationX(90).rotationY(90).uvLock(true).addModel()
				.condition(WiredRoboBlock.NORTH, true)
				.condition(WiredRoboBlock.SOUTH, true)
				.condition(WiredRoboBlock.AXIS, Direction.Axis.X)
				.end()

				.part()
				.modelFile(wiredBottomRoboBlockModel).rotationX(0).rotationY(90).uvLock(true).addModel()
				.condition(WiredRoboBlock.EAST, true)
				.condition(WiredRoboBlock.AXIS, Direction.Axis.X)
				.end()

				.part()
				.modelFile(wiredTopRoboBlockModel).rotationX(0).rotationY(90).uvLock(true).addModel()
				.condition(WiredRoboBlock.WEST, true)
				.condition(WiredRoboBlock.AXIS, Direction.Axis.X)
				.end()

				.part()
				.modelFile(wiredCenterRoboBlockModel).rotationX(0).rotationY(90).uvLock(true).addModel()
				.condition(WiredRoboBlock.EAST, true)
				.condition(WiredRoboBlock.WEST, true)
				.condition(WiredRoboBlock.AXIS, Direction.Axis.X)
				.end()

				.part()
				.modelFile(wiredBottomRoboBlockModel).rotationX(90).rotationY(90).uvLock(true).addModel()
				.condition(WiredRoboBlock.ABOVE, true)
				.condition(WiredRoboBlock.AXIS, Direction.Axis.X)
				.end()

				.part()
				.modelFile(wiredTopRoboBlockModel).rotationX(90).rotationY(90).uvLock(true).addModel()
				.condition(WiredRoboBlock.BELOW, true)
				.condition(WiredRoboBlock.AXIS, Direction.Axis.X)
				.end()

				.part()
				.modelFile(wiredCenterRoboBlockModel).rotationX(90).rotationY(90).uvLock(true).addModel()
				.condition(WiredRoboBlock.ABOVE, true)
				.condition(WiredRoboBlock.BELOW, true)
				.condition(WiredRoboBlock.AXIS, Direction.Axis.X)
				.end()

				.part()
				.modelFile(wiredTop3WayRoboBlockModel).rotationX(90).rotationY(90).uvLock(true).addModel()
				.condition(WiredRoboBlock.WEST, true)
				.condition(WiredRoboBlock.NORTH, true)
				.condition(WiredRoboBlock.EAST, true)
				.condition(WiredRoboBlock.AXIS, Direction.Axis.X)
				.end();
	}

	private ResourceLocation chaosRL(String texturePath) {
		return ChaosAwakens.prefix("block/" + texturePath);
	}

	private ResourceLocation mcRL(String texture) {
		return new ResourceLocation("block/" + texture);
	}
}
