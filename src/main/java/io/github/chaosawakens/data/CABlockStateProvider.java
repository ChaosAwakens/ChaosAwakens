package io.github.chaosawakens.data;

import java.util.Objects;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.DoubleDensePlantBlock;
import io.github.chaosawakens.common.blocks.GateBlock;
import io.github.chaosawakens.common.blocks.LeafCarpetBlock;
import io.github.chaosawakens.common.blocks.PipeBlock;
import io.github.chaosawakens.common.blocks.RotatedPillarCrystalBlock;
import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

@SuppressWarnings("all")
public class CABlockStateProvider extends BlockStateProvider {
	ExistingFileHelper helper;
	
	public CABlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
		super(gen, modid, exFileHelper);
		helper = exFileHelper;
	}

	@Override
	protected void registerStatesAndModels() {
		this.cubeBottomTopBlock(CABlocks.DENSE_GRASS_BLOCK.get(), chaosRL("dense_grass_block_side"), chaosRL("dense_dirt"), chaosRL("dense_grass_block_top"));
		this.simpleBlock(CABlocks.DENSE_DIRT.get());
		this.cubeBottomTopBlock(CABlocks.DENSE_RED_ANT_NEST.get(), chaosRL("dense_grass_block_side"), chaosRL("dense_dirt"), chaosRL("dense_red_ant_nest"));
		this.cross(CABlocks.DENSE_GRASS.get());
		this.doubleCross(CABlocks.TALL_DENSE_GRASS.get());
		this.doubleCross(CABlocks.THORNY_SUN.get());

		this.simpleBlock(CABlocks.MARBLE.get());
		this.simpleBlock(CABlocks.MARBLE_BRICKS.get());
		this.simpleBlock(CABlocks.CHISELED_MARBLE_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_MARBLE_BRICKS.get());
		this.simpleBlock(CABlocks.MOSSY_MARBLE_BRICKS.get());
		this.simpleBlock(CABlocks.POLISHED_MARBLE.get());
		this.logBlock(CABlocks.MARBLE_PILLAR.get());
		this.slabBlock(CABlocks.MARBLE_SLAB.get(), chaosRL("marble_block"), chaosRL("marble_block"));
		this.slabBlock(CABlocks.MARBLE_BRICK_SLAB.get(), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		this.slabBlock(CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		this.slabBlock(CABlocks.CRACKED_MARBLE_BRICK_SLAB.get(), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		this.slabBlock(CABlocks.MOSSY_MARBLE_BRICK_SLAB.get(), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		this.slabBlock(CABlocks.POLISHED_MARBLE_SLAB.get(), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		this.stairsBlock(CABlocks.MARBLE_STAIRS.get(), chaosRL("marble_block"));
		this.stairsBlock(CABlocks.MARBLE_BRICK_STAIRS.get(), chaosRL("marble_bricks"));
		this.stairsBlock(CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get(), chaosRL("chiseled_marble_bricks"));
		this.stairsBlock(CABlocks.CRACKED_MARBLE_BRICK_STAIRS.get(), chaosRL("cracked_marble_bricks"));
		this.stairsBlock(CABlocks.MOSSY_MARBLE_BRICK_STAIRS.get(), chaosRL("mossy_marble_bricks"));
		this.stairsBlock(CABlocks.POLISHED_MARBLE_STAIRS.get(), chaosRL("polished_marble_block"));
		this.wallBlock(CABlocks.MARBLE_WALL.get(), "marble", chaosRL("marble_block"));
		this.wallBlock(CABlocks.MARBLE_BRICK_WALL.get(), "marble_brick", chaosRL("marble_bricks"));
		this.wallBlock(CABlocks.CHISELED_MARBLE_BRICK_WALL.get(), "chiseled_marble", chaosRL("chiseled_marble_bricks"));
		this.wallBlock(CABlocks.CRACKED_MARBLE_BRICK_WALL.get(), "cracked_marble", chaosRL("cracked_marble_bricks"));
		this.wallBlock(CABlocks.MOSSY_MARBLE_BRICK_WALL.get(), "mossy_marble", chaosRL("mossy_marble_bricks"));
		this.wallBlock(CABlocks.POLISHED_MARBLE_WALL.get(), "polished_marble", chaosRL("polished_marble_block"));

		this.simpleBlock(CABlocks.LIMESTONE.get());
		this.simpleBlock(CABlocks.LIMESTONE_BRICKS.get());
		this.simpleBlock(CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_LIMESTONE_BRICKS.get());
		this.simpleBlock(CABlocks.MOSSY_LIMESTONE_BRICKS.get());
		this.simpleBlock(CABlocks.POLISHED_LIMESTONE.get());
		this.logBlock(CABlocks.LIMESTONE_PILLAR.get());
		this.slabBlock(CABlocks.LIMESTONE_SLAB.get(), chaosRL("limestone_block"), chaosRL("limestone_block"));
		this.slabBlock(CABlocks.LIMESTONE_BRICK_SLAB.get(), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		this.slabBlock(CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		this.slabBlock(CABlocks.CRACKED_LIMESTONE_BRICK_SLAB.get(), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		this.slabBlock(CABlocks.MOSSY_LIMESTONE_BRICK_SLAB.get(), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		this.slabBlock(CABlocks.POLISHED_LIMESTONE_SLAB.get(), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		this.stairsBlock(CABlocks.LIMESTONE_STAIRS.get(), chaosRL("limestone_block"));
		this.stairsBlock(CABlocks.LIMESTONE_BRICK_STAIRS.get(), chaosRL("limestone_bricks"));
		this.stairsBlock(CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get(), chaosRL("chiseled_limestone_bricks"));
		this.stairsBlock(CABlocks.CRACKED_LIMESTONE_BRICK_STAIRS.get(), chaosRL("cracked_limestone_bricks"));
		this.stairsBlock(CABlocks.MOSSY_LIMESTONE_BRICK_STAIRS.get(), chaosRL("mossy_limestone_bricks"));
		this.stairsBlock(CABlocks.POLISHED_LIMESTONE_STAIRS.get(), chaosRL("polished_limestone_block"));
		this.wallBlock(CABlocks.LIMESTONE_WALL.get(), "limestone", chaosRL("limestone_block"));
		this.wallBlock(CABlocks.LIMESTONE_BRICK_WALL.get(), "limestone_brick", chaosRL("limestone_bricks"));
		this.wallBlock(CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get(), "chiseled_limestone", chaosRL("chiseled_limestone_bricks"));
		this.wallBlock(CABlocks.CRACKED_LIMESTONE_BRICK_WALL.get(), "cracked_limestone", chaosRL("cracked_limestone_bricks"));
		this.wallBlock(CABlocks.MOSSY_LIMESTONE_BRICK_WALL.get(), "mossy_limestone", chaosRL("mossy_limestone_bricks"));
		this.wallBlock(CABlocks.POLISHED_LIMESTONE_WALL.get(), "polished_limestone", chaosRL("polished_limestone_block"));
		
		this.simpleBlock(CABlocks.RHINESTONE.get());
		this.simpleBlock(CABlocks.RHINESTONE_BRICKS.get());
		this.simpleBlock(CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_RHINESTONE_BRICKS.get());
		this.simpleBlock(CABlocks.MOSSY_RHINESTONE_BRICKS.get());
		this.simpleBlock(CABlocks.POLISHED_RHINESTONE.get());
		this.logBlock(CABlocks.RHINESTONE_PILLAR.get());
		this.slabBlock(CABlocks.RHINESTONE_SLAB.get(), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		this.slabBlock(CABlocks.RHINESTONE_BRICK_SLAB.get(), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		this.slabBlock(CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		this.slabBlock(CABlocks.CRACKED_RHINESTONE_BRICK_SLAB.get(), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		this.slabBlock(CABlocks.MOSSY_RHINESTONE_BRICK_SLAB.get(), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		this.slabBlock(CABlocks.POLISHED_RHINESTONE_SLAB.get(), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		this.stairsBlock(CABlocks.RHINESTONE_STAIRS.get(), chaosRL("rhinestone_block"));
		this.stairsBlock(CABlocks.RHINESTONE_BRICK_STAIRS.get(), chaosRL("rhinestone_bricks"));
		this.stairsBlock(CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get(), chaosRL("chiseled_rhinestone_bricks"));
		this.stairsBlock(CABlocks.CRACKED_RHINESTONE_BRICK_STAIRS.get(), chaosRL("cracked_rhinestone_bricks"));
		this.stairsBlock(CABlocks.MOSSY_RHINESTONE_BRICK_STAIRS.get(), chaosRL("mossy_rhinestone_bricks"));
		this.stairsBlock(CABlocks.POLISHED_RHINESTONE_STAIRS.get(), chaosRL("polished_rhinestone_block"));
		this.wallBlock(CABlocks.RHINESTONE_WALL.get(), "rhinestone", chaosRL("rhinestone_block"));
		this.wallBlock(CABlocks.RHINESTONE_BRICK_WALL.get(), "rhinestone_brick", chaosRL("rhinestone_bricks"));
		this.wallBlock(CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get(), "chiseled_rhinestone", chaosRL("chiseled_rhinestone_bricks"));
		this.wallBlock(CABlocks.CRACKED_RHINESTONE_BRICK_WALL.get(), "cracked_rhinestone", chaosRL("cracked_rhinestone_bricks"));
		this.wallBlock(CABlocks.MOSSY_RHINESTONE_BRICK_WALL.get(), "mossy_rhinestone", chaosRL("mossy_rhinestone_bricks"));
		this.wallBlock(CABlocks.POLISHED_RHINESTONE_WALL.get(), "polished_rhinestone", chaosRL("polished_rhinestone_block"));

		this.simpleBlock(CABlocks.ROBO_BLOCK_I.get());
		this.logBlock(CABlocks.ROBO_BLOCK_V.get());
		this.logBlock(CABlocks.ROBO_BLOCK_X.get());
		this.slabBlock(CABlocks.ROBO_SLAB_I.get(), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		this.slabBlock(CABlocks.ROBO_SLAB_X.get(), chaosRL("robo_block_x"), chaosRL("robo_block_x"));
		this.stairsBlock(CABlocks.ROBO_STAIRS_I.get(), chaosRL("robo_block_l"));
		this.stairsBlock(CABlocks.ROBO_STAIRS_X.get(), chaosRL("robo_block_x"));
		this.wallBlock(CABlocks.ROBO_WALL_I.get(), chaosRL("robo_block_l"));
		this.wallBlock(CABlocks.ROBO_WALL_X.get(), chaosRL("robo_block_x"));
		this.simpleBlock(CABlocks.ROBO_LAMP.get());
		
	//	this.horizontalBlock(CABlocks.ROCK.get(), new ModelFile.ExistingModelFile(chaosRL("rock"), helper));
		
		this.simpleBlock(CABlocks.BLACK_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.BLUE_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.BROWN_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CYAN_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.GRAY_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.GREEN_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.LIME_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.MAGENTA_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.ORANGE_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.PINK_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.PURPLE_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.RED_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.WHITE_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.YELLOW_TERRACOTTA_BRICKS.get());
		this.slabBlock(CABlocks.BLACK_TERRACOTTA_BRICK_SLAB.get(), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		this.slabBlock(CABlocks.BLUE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		this.slabBlock(CABlocks.BROWN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		this.slabBlock(CABlocks.CYAN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		this.slabBlock(CABlocks.GRAY_TERRACOTTA_BRICK_SLAB.get(), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		this.slabBlock(CABlocks.GREEN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		this.slabBlock(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		this.slabBlock(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		this.slabBlock(CABlocks.LIME_TERRACOTTA_BRICK_SLAB.get(), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		this.slabBlock(CABlocks.MAGENTA_TERRACOTTA_BRICK_SLAB.get(), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		this.slabBlock(CABlocks.ORANGE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		this.slabBlock(CABlocks.PINK_TERRACOTTA_BRICK_SLAB.get(), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		this.slabBlock(CABlocks.PURPLE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		this.slabBlock(CABlocks.RED_TERRACOTTA_BRICK_SLAB.get(), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		this.slabBlock(CABlocks.TERRACOTTA_BRICK_SLAB.get(), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		this.slabBlock(CABlocks.WHITE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		this.slabBlock(CABlocks.YELLOW_TERRACOTTA_BRICK_SLAB.get(), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		this.stairsBlock(CABlocks.BLACK_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("black_terracotta_bricks"));
		this.stairsBlock(CABlocks.BLUE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("blue_terracotta_bricks"));
		this.stairsBlock(CABlocks.BROWN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("brown_terracotta_bricks"));
		this.stairsBlock(CABlocks.CYAN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cyan_terracotta_bricks"));
		this.stairsBlock(CABlocks.GRAY_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("gray_terracotta_bricks"));
		this.stairsBlock(CABlocks.GREEN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("green_terracotta_bricks"));
		this.stairsBlock(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("light_blue_terracotta_bricks"));
		this.stairsBlock(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("light_gray_terracotta_bricks"));
		this.stairsBlock(CABlocks.LIME_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("lime_terracotta_bricks"));
		this.stairsBlock(CABlocks.MAGENTA_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("magenta_terracotta_bricks"));
		this.stairsBlock(CABlocks.ORANGE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("orange_terracotta_bricks"));
		this.stairsBlock(CABlocks.PINK_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("pink_terracotta_bricks"));
		this.stairsBlock(CABlocks.PURPLE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("purple_terracotta_bricks"));
		this.stairsBlock(CABlocks.RED_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("red_terracotta_bricks"));
		this.stairsBlock(CABlocks.TERRACOTTA_BRICK_STAIRS.get(), chaosRL("terracotta_bricks"));
		this.stairsBlock(CABlocks.WHITE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("white_terracotta_bricks"));
		this.stairsBlock(CABlocks.YELLOW_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("yellow_terracotta_bricks"));
		this.wallBlock(CABlocks.BLACK_TERRACOTTA_BRICK_WALL.get(), chaosRL("black_terracotta_bricks"));
		this.wallBlock(CABlocks.BLUE_TERRACOTTA_BRICK_WALL.get(), chaosRL("blue_terracotta_bricks"));
		this.wallBlock(CABlocks.BROWN_TERRACOTTA_BRICK_WALL.get(), chaosRL("brown_terracotta_bricks"));
		this.wallBlock(CABlocks.CYAN_TERRACOTTA_BRICK_WALL.get(), chaosRL("cyan_terracotta_bricks"));
		this.wallBlock(CABlocks.GRAY_TERRACOTTA_BRICK_WALL.get(), chaosRL("gray_terracotta_bricks"));
		this.wallBlock(CABlocks.GREEN_TERRACOTTA_BRICK_WALL.get(), chaosRL("green_terracotta_bricks"));
		this.wallBlock(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get(), chaosRL("light_blue_terracotta_bricks"));
		this.wallBlock(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get(), chaosRL("light_gray_terracotta_bricks"));
		this.wallBlock(CABlocks.LIME_TERRACOTTA_BRICK_WALL.get(), chaosRL("lime_terracotta_bricks"));
		this.wallBlock(CABlocks.MAGENTA_TERRACOTTA_BRICK_WALL.get(), chaosRL("magenta_terracotta_bricks"));
		this.wallBlock(CABlocks.ORANGE_TERRACOTTA_BRICK_WALL.get(), chaosRL("orange_terracotta_bricks"));
		this.wallBlock(CABlocks.PINK_TERRACOTTA_BRICK_WALL.get(), chaosRL("pink_terracotta_bricks"));
		this.wallBlock(CABlocks.PURPLE_TERRACOTTA_BRICK_WALL.get(), chaosRL("purple_terracotta_bricks"));
		this.wallBlock(CABlocks.RED_TERRACOTTA_BRICK_WALL.get(), chaosRL("red_terracotta_bricks"));
		this.wallBlock(CABlocks.TERRACOTTA_BRICK_WALL.get(), chaosRL("terracotta_bricks"));
		this.wallBlock(CABlocks.WHITE_TERRACOTTA_BRICK_WALL.get(), chaosRL("white_terracotta_bricks"));
		this.wallBlock(CABlocks.YELLOW_TERRACOTTA_BRICK_WALL.get(), chaosRL("yellow_terracotta_bricks"));

		this.simpleBlock(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get());
		this.simpleBlock(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get());
		this.slabBlock(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_RED_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		this.slabBlock(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_SLAB.get(), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_black_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_blue_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_brown_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_cyan_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_gray_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_green_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_light_blue_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_light_gray_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_lime_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_magenta_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_orange_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_pink_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_purple_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_RED_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_red_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_white_terracotta_bricks"));
		this.stairsBlock(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_STAIRS.get(), chaosRL("cracked_yellow_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_black_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_blue_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_brown_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_cyan_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_gray_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_green_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_light_blue_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_light_gray_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_lime_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_magenta_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_orange_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_pink_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_purple_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_RED_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_red_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_white_terracotta_bricks"));
		this.wallBlock(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_WALL.get(), chaosRL("cracked_yellow_terracotta_bricks"));

		this.simpleBlock(CABlocks.FOSSILISED_ACACIA_ENT.get());
		this.simpleBlock(CABlocks.FOSSILISED_BIRCH_ENT.get());
		this.simpleBlock(CABlocks.FOSSILISED_DARK_OAK_ENT.get());
		this.simpleBlock(CABlocks.FOSSILISED_JUNGLE_ENT.get());
		this.simpleBlock(CABlocks.FOSSILISED_OAK_ENT.get());
		this.simpleBlock(CABlocks.FOSSILISED_SPRUCE_ENT.get());
		this.simpleBlock(CABlocks.FOSSILISED_HERCULES_BEETLE.get());
		this.simpleBlock(CABlocks.FOSSILISED_BEAVER.get());
		this.simpleBlock(CABlocks.FOSSILISED_RUBY_BUG.get());
		this.simpleBlock(CABlocks.FOSSILISED_EMERALD_GATOR.get());
		this.simpleBlock(CABlocks.FOSSILISED_GREEN_FISH.get());
		this.simpleBlock(CABlocks.FOSSILISED_ROCK_FISH.get());
		this.simpleBlock(CABlocks.FOSSILISED_SPARK_FISH.get());
		this.simpleBlock(CABlocks.FOSSILISED_WOOD_FISH.get());
		this.simpleBlock(CABlocks.FOSSILISED_WHALE.get());
		this.simpleBlock(CABlocks.FOSSILISED_WTF.get());
		this.simpleBlock(CABlocks.FOSSILISED_SCORPION.get());
		this.simpleBlock(CABlocks.FOSSILISED_WASP.get());
		this.simpleBlock(CABlocks.FOSSILISED_PIRAPORU.get());
		this.simpleBlock(CABlocks.FOSSILISED_APPLE_COW.get());
		this.simpleBlock(CABlocks.FOSSILISED_GOLDEN_APPLE_COW.get());
		this.simpleBlock(CABlocks.FOSSILISED_CARROT_PIG.get());
		this.simpleBlock(CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.get());
		this.simpleBlock(CABlocks.FOSSILISED_LETTUCE_CHICKEN.get());
		this.simpleBlock(CABlocks.FOSSILISED_BIRD.get());
		this.simpleBlock(CABlocks.FOSSILISED_DIMETRODON.get());
		this.simpleBlock(CABlocks.FOSSILISED_TREE_FROG.get());

		this.simpleBlock(CABlocks.FOSSILISED_BAT.get());
		this.simpleBlock(CABlocks.FOSSILISED_BEE.get());
		this.simpleBlock(CABlocks.FOSSILISED_CAVE_SPIDER.get());
		this.simpleBlock(CABlocks.FOSSILISED_CHICKEN.get());
		this.simpleBlock(CABlocks.FOSSILISED_COD.get());
		this.simpleBlock(CABlocks.FOSSILISED_COW.get());
		this.simpleBlock(CABlocks.FOSSILISED_CREEPER.get());
		this.simpleBlock(CABlocks.FOSSILISED_DOLPHIN.get());
		this.simpleBlock(CABlocks.FOSSILISED_DONKEY.get());
		this.simpleBlock(CABlocks.FOSSILISED_DROWNED.get());
		this.simpleBlock(CABlocks.FOSSILISED_ENDERMAN.get());
		this.simpleBlock(CABlocks.FOSSILISED_EVOKER.get());
		this.simpleBlock(CABlocks.FOSSILISED_FOX.get());
		this.simpleBlock(CABlocks.FOSSILISED_GIANT.get());
		this.simpleBlock(CABlocks.FOSSILISED_GUARDIAN.get());
		this.simpleBlock(CABlocks.FOSSILISED_HORSE.get());
		this.simpleBlock(CABlocks.FOSSILISED_HUSK.get());
		this.cubeBottomTopBlock(CABlocks.FOSSILISED_HUSK_SANDSTONE.get(), chaosRL("fossilised_husk_sandstone"), mcRL("sandstone_bottom"), mcRL("sandstone_top"));
		this.simpleBlock(CABlocks.FOSSILISED_ILLUSIONER.get());
		this.simpleBlock(CABlocks.FOSSILISED_IRON_GOLEM.get());
		this.simpleBlock(CABlocks.FOSSILISED_LLAMA.get());
		this.simpleBlock(CABlocks.FOSSILISED_MOOSHROOM.get());
		this.simpleBlock(CABlocks.FOSSILISED_OCELOT.get());
		this.simpleBlock(CABlocks.FOSSILISED_PANDA.get());
		this.simpleBlock(CABlocks.FOSSILISED_PIG.get());
		this.simpleBlock(CABlocks.FOSSILISED_PHANTOM.get());
		this.simpleBlock(CABlocks.FOSSILISED_PILLAGER.get());
		this.simpleBlock(CABlocks.FROZEN_POLAR_BEAR.get());
		this.simpleBlock(CABlocks.FOSSILISED_PUFFERFISH.get());
		this.simpleBlock(CABlocks.FOSSILISED_RABBIT.get());
		this.simpleBlock(CABlocks.FOSSILISED_RAVAGER.get());
		this.simpleBlock(CABlocks.FOSSILISED_SALMON.get());
		this.simpleBlock(CABlocks.FOSSILISED_SHEEP.get());
		this.simpleBlock(CABlocks.FOSSILISED_SKELETON.get());
		this.simpleBlock(CABlocks.FOSSILISED_SKELETON_HORSE.get());
		this.simpleBlock(CABlocks.FOSSILISED_SLIME.get());
		this.simpleBlock(CABlocks.FROZEN_SNOW_GOLEM.get());
		this.simpleBlock(CABlocks.FOSSILISED_SPIDER.get());
		this.simpleBlock(CABlocks.FOSSILISED_SQUID.get());
		this.simpleBlock(CABlocks.FROZEN_STRAY.get());
		this.simpleBlock(CABlocks.FOSSILISED_TROPICAL_FISH.get());
		this.simpleBlock(CABlocks.FOSSILISED_TURTLE.get());
		this.simpleBlock(CABlocks.FOSSILISED_VILLAGER.get());
		this.simpleBlock(CABlocks.FOSSILISED_VINDICATOR.get());
		this.simpleBlock(CABlocks.FOSSILISED_WANDERING_TRADER.get());
		this.simpleBlock(CABlocks.FOSSILISED_WOLF.get());
		this.simpleBlock(CABlocks.FOSSILISED_WITCH.get());
		this.simpleBlock(CABlocks.FOSSILISED_ZOMBIE.get());
		this.simpleBlock(CABlocks.FOSSILISED_ZOMBIE_HORSE.get());

		this.simpleBlock(CABlocks.FOSSILISED_CRIMSON_ENT.get());
		this.simpleBlock(CABlocks.FOSSILISED_WARPED_ENT.get());
		this.simpleBlock(CABlocks.FOSSILISED_LAVA_EEL.get());

		this.simpleBlock(CABlocks.FOSSILISED_BLAZE.get());
		this.simpleBlock(CABlocks.FOSSILISED_GHAST.get());
		this.simpleBlock(CABlocks.FOSSILISED_HOGLIN.get());
		this.simpleBlock(CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get());
		this.simpleBlock(CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get());
		this.simpleBlock(CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get());
		this.simpleBlock(CABlocks.FOSSILISED_PIGLIN.get());
		this.simpleBlock(CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.get());
		this.simpleBlock(CABlocks.FOSSILISED_STRIDER.get());
		this.simpleBlock(CABlocks.FOSSILISED_WITHER_SKELETON.get());
		this.simpleBlock(CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.get());

		this.simpleBlock(CABlocks.FOSSILISED_ENDERMAN_END_STONE.get());
		this.simpleBlock(CABlocks.FOSSILISED_ENDERMITE.get());
		this.simpleBlock(CABlocks.FOSSILISED_SHULKER.get());

		this.simpleBlock(CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.get());
		this.simpleBlock(CABlocks.CRYSTALISED_CRYSTAL_CARROT_PIG.get());

		this.simpleBlock(CABlocks.MOTH_SCALE_BLOCK.get());
		this.simpleBlock(CABlocks.WATER_DRAGON_SCALE_BLOCK.get());
		this.simpleBlock(CABlocks.ENDER_DRAGON_SCALE_BLOCK.get());
		this.simpleBlock(CABlocks.NIGHTMARE_SCALE_BLOCK.get());
		this.simpleBlock(CABlocks.MOBZILLA_SCALE_BLOCK.get());
		this.simpleBlock(CABlocks.ROYAL_GUARDIAN_SCALE_BLOCK.get());
		this.simpleBlock(CABlocks.QUEEN_SCALE_BLOCK.get());
		this.simpleBlock(CABlocks.BASILISK_SCALE_BLOCK.get());
		this.simpleBlock(CABlocks.EMPEROR_SCORPION_SCALE_BLOCK.get());

		this.simpleBlock(CABlocks.RUBY_ORE.get());
		this.simpleBlock(CABlocks.NETHERRACK_RUBY_ORE.get());
		this.simpleBlock(CABlocks.BLACKSTONE_RUBY_ORE.get());
		this.simpleBlock(CABlocks.SALT_BLOCK.get());
		this.simpleBlock(CABlocks.SALT_ORE.get());

		this.logBlock(CABlocks.APPLE_LOG.get());
		this.woodBlock(CABlocks.APPLE_WOOD.get(), chaosRL("apple_log"));
		this.simpleBlock(CABlocks.APPLE_PLANKS.get());
		this.leafCarpetBlock(CABlocks.APPLE_LEAF_CARPET.get(), chaosRL("apple_leaves"));
		this.logBlock(CABlocks.STRIPPED_APPLE_LOG.get());
		this.woodBlock(CABlocks.STRIPPED_APPLE_WOOD.get(), chaosRL("stripped_apple_log"));
		this.logBlock(CABlocks.CHERRY_LOG.get());
		this.woodBlock(CABlocks.CHERRY_WOOD.get(), chaosRL("cherry_log"));
		this.simpleBlock(CABlocks.CHERRY_PLANKS.get());
		this.leafCarpetBlock(CABlocks.CHERRY_LEAF_CARPET.get(), chaosRL("cherry_leaves"));
		this.logBlock(CABlocks.STRIPPED_CHERRY_LOG.get());
		this.woodBlock(CABlocks.STRIPPED_CHERRY_WOOD.get(), chaosRL("stripped_cherry_log"));
		this.logBlock(CABlocks.DUPLICATION_LOG.get());
		this.woodBlock(CABlocks.DUPLICATION_WOOD.get(), chaosRL("duplication_log"));
		this.logBlock(CABlocks.DEAD_DUPLICATION_LOG.get());
		this.woodBlock(CABlocks.DEAD_DUPLICATION_WOOD.get(), chaosRL("dead_duplication_log"));
		this.simpleBlock(CABlocks.DUPLICATION_PLANKS.get());
		this.simpleBlock(CABlocks.DUPLICATION_LEAVES.get());
		this.leafCarpetBlock(CABlocks.DUPLICATION_LEAF_CARPET.get(), chaosRL("duplication_leaves"));
		this.logBlock(CABlocks.STRIPPED_DUPLICATION_LOG.get());
		this.woodBlock(CABlocks.STRIPPED_DUPLICATION_WOOD.get(), chaosRL("stripped_duplication_log"));
		this.logBlock(CABlocks.GINKGO_LOG.get());
		this.woodBlock(CABlocks.GINKGO_WOOD.get(), chaosRL("ginkgo_log"));
		this.simpleBlock(CABlocks.GINKGO_PLANKS.get());
		this.simpleBlock(CABlocks.GINKGO_LEAVES.get());
		this.leafCarpetBlock(CABlocks.GINKGO_LEAF_CARPET.get(), chaosRL("ginkgo_leaves"));
		this.logBlock(CABlocks.STRIPPED_GINKGO_LOG.get());
		this.woodBlock(CABlocks.STRIPPED_GINKGO_WOOD.get(), chaosRL("stripped_ginkgo_log"));
		this.logBlock(CABlocks.PEACH_LOG.get());
		this.woodBlock(CABlocks.PEACH_WOOD.get(), chaosRL("peach_log"));
		this.simpleBlock(CABlocks.PEACH_PLANKS.get());
		this.leafCarpetBlock(CABlocks.PEACH_LEAF_CARPET.get(), chaosRL("peach_leaves"));
		this.logBlock(CABlocks.STRIPPED_PEACH_LOG.get());
		this.woodBlock(CABlocks.STRIPPED_PEACH_WOOD.get(), chaosRL("stripped_peach_log"));
		this.logBlock(CABlocks.SKYWOOD_LOG.get());
		this.woodBlock(CABlocks.SKYWOOD_WOOD.get(), chaosRL("skywood_log"));
		this.simpleBlock(CABlocks.SKYWOOD_PLANKS.get());
		this.simpleBlock(CABlocks.SKYWOOD_LEAVES.get());
		this.leafCarpetBlock(CABlocks.SKYWOOD_LEAF_CARPET.get(), chaosRL("skywood_leaves"));
		this.logBlock(CABlocks.STRIPPED_SKYWOOD_LOG.get());
		this.woodBlock(CABlocks.STRIPPED_SKYWOOD_WOOD.get(), chaosRL("stripped_skywood_log"));
		this.crystalLogBlock(CABlocks.CRYSTAL_LOG.get());
		this.crystalWoodBlock(CABlocks.CRYSTAL_WOOD.get(), chaosRL("crystal_log"));
		this.simpleBlock(CABlocks.CRYSTAL_PLANKS.get());
		this.stairsBlock(CABlocks.APPLE_STAIRS.get(), chaosRL("apple_planks"));
		this.stairsBlock(CABlocks.CHERRY_STAIRS.get(), chaosRL("cherry_planks"));
		this.stairsBlock(CABlocks.DUPLICATION_STAIRS.get(), chaosRL("duplication_planks"));
		this.stairsBlock(CABlocks.GINKGO_STAIRS.get(), chaosRL("ginkgo_planks"));
		this.stairsBlock(CABlocks.PEACH_STAIRS.get(), chaosRL("peach_planks"));
		this.stairsBlock(CABlocks.SKYWOOD_STAIRS.get(), chaosRL("skywood_planks"));
		this.stairsBlock(CABlocks.CRYSTAL_STAIRS.get(), chaosRL("crystal_planks"));
		this.slabBlock(CABlocks.APPLE_SLAB.get(), chaosRL("apple_planks"), chaosRL("apple_planks"));
		this.slabBlock(CABlocks.CHERRY_SLAB.get(), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		this.slabBlock(CABlocks.DUPLICATION_SLAB.get(), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		this.slabBlock(CABlocks.GINKGO_SLAB.get(), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		this.slabBlock(CABlocks.PEACH_SLAB.get(), chaosRL("peach_planks"), chaosRL("peach_planks"));
		this.slabBlock(CABlocks.SKYWOOD_SLAB.get(), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		this.slabBlock(CABlocks.CRYSTAL_SLAB.get(), chaosRL("crystal_planks"), chaosRL("crystal_planks"));
		this.fenceBlock(CABlocks.APPLE_FENCE.get(), chaosRL("apple_planks"));
		this.fenceBlock(CABlocks.CHERRY_FENCE.get(), chaosRL("cherry_planks"));
		this.fenceBlock(CABlocks.DUPLICATION_FENCE.get(), chaosRL("duplication_planks"));
		this.fenceBlock(CABlocks.GINKGO_FENCE.get(), chaosRL("ginkgo_planks"));
		this.fenceBlock(CABlocks.PEACH_FENCE.get(), chaosRL("peach_planks"));
		this.fenceBlock(CABlocks.SKYWOOD_FENCE.get(), chaosRL("skywood_planks"));
		this.fenceBlock(CABlocks.CRYSTAL_FENCE.get(), chaosRL("crystal_planks"));
		this.fenceGateBlock(CABlocks.APPLE_FENCE_GATE.get(), chaosRL("apple_planks"));
		this.fenceGateBlock(CABlocks.CHERRY_FENCE_GATE.get(), chaosRL("cherry_planks"));
		this.fenceGateBlock(CABlocks.DUPLICATION_FENCE_GATE.get(), chaosRL("duplication_planks"));
		this.fenceGateBlock(CABlocks.GINKGO_FENCE_GATE.get(), chaosRL("ginkgo_planks"));
		this.fenceGateBlock(CABlocks.PEACH_FENCE_GATE.get(), chaosRL("peach_planks"));
		this.fenceGateBlock(CABlocks.SKYWOOD_FENCE_GATE.get(), chaosRL("skywood_planks"));
		this.fenceGateBlock(CABlocks.CRYSTAL_FENCE_GATE.get(), chaosRL("crystal_planks"));
		this.pressurePlateBlock(CABlocks.APPLE_PRESSURE_PLATE.get(), "apple_pressure_plate", chaosRL("apple_planks"));
		this.pressurePlateBlock(CABlocks.CHERRY_PRESSURE_PLATE.get(), "cherry_pressure_plate", chaosRL("cherry_planks"));
		this.pressurePlateBlock(CABlocks.DUPLICATION_PRESSURE_PLATE.get(), "duplication_pressure_plate", chaosRL("duplication_planks"));
		this.pressurePlateBlock(CABlocks.GINKGO_PRESSURE_PLATE.get(), "ginkgo_pressure_plate", chaosRL("ginkgo_planks"));
		this.pressurePlateBlock(CABlocks.PEACH_PRESSURE_PLATE.get(), "peach_pressure_plate", chaosRL("peach_planks"));
		this.pressurePlateBlock(CABlocks.SKYWOOD_PRESSURE_PLATE.get(), "skywood_pressure_plate", chaosRL("skywood_planks"));
		this.pressurePlateBlock(CABlocks.CRYSTAL_PRESSURE_PLATE.get(), "crystal_pressure_plate", chaosRL("crystal_planks"));
		this.buttonBlock(CABlocks.APPLE_BUTTON.get(), chaosRL("apple_planks"));
		this.buttonBlock(CABlocks.CHERRY_BUTTON.get(), chaosRL("cherry_planks"));
		this.buttonBlock(CABlocks.DUPLICATION_BUTTON.get(), chaosRL("duplication_planks"));
		this.buttonBlock(CABlocks.GINKGO_BUTTON.get(), chaosRL("ginkgo_planks"));
		this.buttonBlock(CABlocks.PEACH_BUTTON.get(), chaosRL("peach_planks"));
		this.buttonBlock(CABlocks.SKYWOOD_BUTTON.get(), chaosRL("skywood_planks"));
		this.buttonBlock(CABlocks.CRYSTAL_BUTTON.get(), chaosRL("crystal_planks"));
		this.trapdoorBlock(CABlocks.APPLE_TRAPDOOR.get());
		this.trapdoorBlock(CABlocks.CHERRY_TRAPDOOR.get());
		this.trapdoorBlock(CABlocks.DUPLICATION_TRAPDOOR.get());
		this.trapdoorBlock(CABlocks.GINKGO_TRAPDOOR.get());
		this.trapdoorBlock(CABlocks.PEACH_TRAPDOOR.get());
		this.trapdoorBlock(CABlocks.SKYWOOD_TRAPDOOR.get());
		this.doorBlock(CABlocks.APPLE_DOOR.get());
		this.doorBlock(CABlocks.CHERRY_DOOR.get());
		this.doorBlock(CABlocks.DUPLICATION_DOOR.get());
		this.doorBlock(CABlocks.GINKGO_DOOR.get());
		this.doorBlock(CABlocks.PEACH_DOOR.get());
		this.doorBlock(CABlocks.SKYWOOD_DOOR.get());

		this.simpleBlock(CABlocks.MOLDY_PLANKS.get());
		this.slabBlock(CABlocks.MOLDY_SLAB.get(), chaosRL("moldy_planks"), chaosRL("moldy_planks"));
		this.fenceBlock(CABlocks.MOLDY_FENCE.get(), chaosRL("moldy_planks"));

		this.cubeBottomTopBlock(CABlocks.APPLE_GATE_BLOCK.get(), chaosRL("apple_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.CHERRY_GATE_BLOCK.get(), chaosRL("cherry_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.DUPLICATION_GATE_BLOCK.get(), chaosRL("duplication_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.GINKGO_GATE_BLOCK.get(), chaosRL("ginkgo_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.PEACH_GATE_BLOCK.get(), chaosRL("peach_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.SKYWOOD_GATE_BLOCK.get(), chaosRL("skywood_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.MUSHROOM_GATE_BLOCK.get(), chaosRL("mushroom_gate_block"), chaosRL("gate_block_top"));

		this.cubeBottomTopBlock(CABlocks.ACACIA_GATE_BLOCK.get(), chaosRL("acacia_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.BIRCH_GATE_BLOCK.get(), chaosRL("birch_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.CRIMSON_GATE_BLOCK.get(), chaosRL("crimson_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.DARK_OAK_GATE_BLOCK.get(), chaosRL("dark_oak_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.JUNGLE_GATE_BLOCK.get(), chaosRL("jungle_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.OAK_GATE_BLOCK.get(), chaosRL("oak_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.SPRUCE_GATE_BLOCK.get(), chaosRL("spruce_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.WARPED_GATE_BLOCK.get(), chaosRL("warped_gate_block"), chaosRL("gate_block_top"));
		this.cubeBottomTopBlock(CABlocks.MUSHROOM_GATE_BLOCK.get(), chaosRL("mushroom_gate_block"), chaosRL("gate_block_top"));

		this.leafCarpetBlock(CABlocks.OAK_LEAF_CARPET.get(), mcRL("oak_leaves"));
		this.leafCarpetBlock(CABlocks.SPRUCE_LEAF_CARPET.get(), mcRL("spruce_leaves"));
		this.leafCarpetBlock(CABlocks.BIRCH_LEAF_CARPET.get(), mcRL("birch_leaves"));
		this.leafCarpetBlock(CABlocks.JUNGLE_LEAF_CARPET.get(), mcRL("jungle_leaves"));
		this.leafCarpetBlock(CABlocks.ACACIA_LEAF_CARPET.get(), mcRL("acacia_leaves"));
		this.leafCarpetBlock(CABlocks.DARK_OAK_LEAF_CARPET.get(), mcRL("dark_oak_leaves"));
	}

	private String name(Block block) {
		return Objects.requireNonNull(block.getRegistryName()).getPath();
	}

	private ResourceLocation extend(ResourceLocation rl, String suffix) {
		return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
	}

	private static ResourceLocation getResourceLocation(String path) {
		return new ResourceLocation(ChaosAwakens.MODID, path);
	}

	private static ResourceLocation getBlockResourceLocation(String name) {
		return getResourceLocation("block/" + name);
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

	public void pressurePlateBlock(PressurePlateBlock block, String name, ResourceLocation all) {
		pressurePlateBlockInternal(block, name, all);
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

	private ResourceLocation chaosRL(String texture) {
		return new ResourceLocation(ChaosAwakens.MODID, "block/" + texture);
	}

	private ResourceLocation mcRL(String texture) {
		return new ResourceLocation("minecraft", "block/" + texture);
	}
}
