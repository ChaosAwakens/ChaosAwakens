package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CABlockModelProvider extends BlockModelProvider {
	public CABlockModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		// TODO Automate the data generators
		this.orientableWithBottom("defossilizer", mcRL("iron_block"), chaosRL("defossilizer_front"), mcRL("iron_block"), chaosRL("defossilizer_top"));

		this.cubeAll("fossilised_acacia_ent", chaosRL("fossilised_acacia_ent"));
		this.cubeAll("fossilised_birch_ent", chaosRL("fossilised_birch_ent"));
		this.cubeAll("fossilised_dark_oak_ent", chaosRL("fossilised_dark_oak_ent"));
		this.cubeAll("fossilised_jungle_ent", chaosRL("fossilised_jungle_ent"));
		this.cubeAll("fossilised_oak_ent", chaosRL("fossilised_oak_ent"));
		this.cubeAll("fossilised_spruce_ent", chaosRL("fossilised_spruce_ent"));
		this.cubeAll("fossilised_hercules_beetle", chaosRL("fossilised_hercules_beetle"));
		this.cubeAll("fossilised_ruby_bug", chaosRL("fossilised_ruby_bug"));
		this.cubeAll("fossilised_emerald_gator", chaosRL("fossilised_emerald_gator"));
		this.cubeAll("fossilised_green_fish", chaosRL("fossilised_green_fish"));
		this.cubeAll("fossilised_rock_fish", chaosRL("fossilised_rock_fish"));
		this.cubeAll("fossilised_spark_fish", chaosRL("fossilised_spark_fish"));
		this.cubeAll("fossilised_wood_fish", chaosRL("fossilised_wood_fish"));
		this.cubeAll("fossilised_whale", chaosRL("fossilised_whale"));
		this.cubeAll("fossilised_wtf", chaosRL("fossilised_wtf"));
		this.cubeAll("fossilised_scorpion", chaosRL("fossilised_scorpion"));
		this.cubeAll("fossilised_wasp", chaosRL("fossilised_wasp"));
		this.cubeAll("fossilised_piraporu", chaosRL("fossilised_piraporu"));
		this.cubeAll("fossilised_apple_cow", chaosRL("fossilised_apple_cow"));
		this.cubeAll("fossilised_golden_apple_cow", chaosRL("fossilised_golden_apple_cow"));
		this.cubeAll("fossilised_carrot_pig", chaosRL("fossilised_carrot_pig"));
		this.cubeAll("fossilised_golden_carrot_pig", chaosRL("fossilised_golden_carrot_pig"));
		this.cubeAll("fossilised_bird", chaosRL("fossilised_bird"));
		this.cubeAll("fossilised_dimetrodon", chaosRL("fossilised_dimetrodon"));
		this.cubeAll("fossilised_frog", chaosRL("fossilised_frog"));

		this.cubeAll("fossilised_bat", chaosRL("fossilised_bat"));
		this.cubeAll("fossilised_bee", chaosRL("fossilised_bee"));
		this.cubeAll("fossilised_cave_spider", chaosRL("fossilised_cave_spider"));
		this.cubeAll("fossilised_chicken", chaosRL("fossilised_chicken"));
		this.cubeAll("fossilised_cod", chaosRL("fossilised_cod"));
		this.cubeAll("fossilised_cow", chaosRL("fossilised_cow"));
		this.cubeAll("fossilised_creeper", chaosRL("fossilised_creeper"));
		this.cubeAll("fossilised_dolphin", chaosRL("fossilised_dolphin"));
		this.cubeAll("fossilised_donkey", chaosRL("fossilised_donkey"));
		this.cubeAll("fossilised_drowned", chaosRL("fossilised_drowned"));
		this.cubeAll("fossilised_enderman", chaosRL("fossilised_enderman"));
		this.cubeAll("fossilised_evoker", chaosRL("fossilised_evoker"));
		this.cubeAll("fossilised_fox", chaosRL("fossilised_fox"));
		this.cubeAll("fossilised_giant", chaosRL("fossilised_giant"));
		this.cubeAll("fossilised_guardian", chaosRL("fossilised_guardian"));
		this.cubeAll("fossilised_horse", chaosRL("fossilised_horse"));
		this.cubeAll("fossilised_husk_stone", chaosRL("fossilised_husk_stone"));
		this.cubeAll("fossilised_illusioner", chaosRL("fossilised_illusioner"));
		this.cubeAll("fossilised_iron_golem", chaosRL("fossilised_iron_golem"));
		this.cubeAll("fossilised_llama", chaosRL("fossilised_llama"));
		this.cubeAll("fossilised_mooshroom", chaosRL("fossilised_mooshroom"));
		this.cubeAll("fossilised_ocelot", chaosRL("fossilised_ocelot"));
		this.cubeAll("fossilised_panda", chaosRL("fossilised_panda"));
		this.cubeAll("fossilised_pig", chaosRL("fossilised_pig"));
		this.cubeAll("fossilised_phantom", chaosRL("fossilised_phantom"));
		this.cubeAll("fossilised_pillager", chaosRL("fossilised_pillager"));
		this.cubeAll("fossilised_polar_bear", chaosRL("fossilised_polar_bear"));
		this.cubeAll("fossilised_pufferfish", chaosRL("fossilised_pufferfish"));
		this.cubeAll("fossilised_rabbit", chaosRL("fossilised_rabbit"));
		this.cubeAll("fossilised_ravager", chaosRL("fossilised_ravager"));
		this.cubeAll("fossilised_salmon", chaosRL("fossilised_salmon"));
		this.cubeAll("fossilised_sheep", chaosRL("fossilised_sheep"));
		this.cubeAll("fossilised_skeleton", chaosRL("fossilised_skeleton"));
		this.cubeAll("fossilised_skeleton_horse", chaosRL("fossilised_skeleton_horse"));
		this.cubeAll("fossilised_slime", chaosRL("fossilised_slime"));
		this.cubeAll("fossilised_snow_golem", chaosRL("fossilised_snow_golem"));
		this.cubeAll("fossilised_spider", chaosRL("fossilised_spider"));
		this.cubeAll("fossilised_squid", chaosRL("fossilised_squid"));
		this.cubeAll("fossilised_stray", chaosRL("fossilised_stray"));
		this.cubeAll("fossilised_tropical_fish", chaosRL("fossilised_tropical_fish"));
		this.cubeAll("fossilised_turtle", chaosRL("fossilised_turtle"));
		this.cubeAll("fossilised_villager", chaosRL("fossilised_villager"));
		this.cubeAll("fossilised_vindicator", chaosRL("fossilised_vindicator"));
		this.cubeAll("fossilised_wandering_trader", chaosRL("fossilised_wandering_trader"));
		this.cubeAll("fossilised_witch", chaosRL("fossilised_witch"));
		this.cubeAll("fossilised_wolf", chaosRL("fossilised_wolf"));
		this.cubeAll("fossilised_zombie", chaosRL("fossilised_zombie"));
		this.cubeAll("fossilised_zombie_horse", chaosRL("fossilised_zombie_horse"));

		this.cubeAll("fossilised_crimson_ent", chaosRL("fossilised_crimson_ent"));
		this.cubeAll("fossilised_warped_ent", chaosRL("fossilised_warped_ent"));
		this.cubeAll("fossilised_lava_eel", chaosRL("fossilised_lava_eel"));

		this.cubeAll("fossilised_blaze", chaosRL("fossilised_blaze"));
		this.cubeAll("fossilised_ghast", chaosRL("fossilised_ghast"));
		this.cubeAll("fossilised_hoglin", chaosRL("fossilised_hoglin"));
		this.cubeAll("fossilised_enderman_netherrack", chaosRL("fossilised_enderman_netherrack"));
		this.cubeAll("fossilised_magma_cube_netherrack", chaosRL("fossilised_magma_cube_netherrack"));
		this.cubeAll("fossilised_magma_cube_blackstone", chaosRL("fossilised_magma_cube_blackstone"));
		this.cubeAll("fossilised_piglin", chaosRL("fossilised_piglin"));
		this.cubeAll("fossilised_skeleton_soul_soil", chaosRL("fossilised_skeleton_soul_soil"));
		this.cubeAll("fossilised_strider", chaosRL("fossilised_strider"));
		this.cubeAll("fossilised_wither_skeleton", chaosRL("fossilised_wither_skeleton"));
		this.cubeAll("fossilised_zombified_piglin", chaosRL("fossilised_zombified_piglin"));

		this.cubeAll("fossilised_enderman_end_stone", chaosRL("fossilised_enderman_end_stone"));
		this.cubeAll("fossilised_endermite", chaosRL("fossilised_endermite"));
		this.cubeAll("fossilised_shulker", chaosRL("fossilised_shulker"));

		this.cubeAll("crystalised_crystal_apple_cow", chaosRL("crystalised_crystal_apple_cow"));

		this.cubeAll("ruby_ore", chaosRL("ruby_ore"));
		this.cubeAll("netherrack_ruby_ore", chaosRL("netherrack_ruby_ore"));
		this.cubeAll("blackstone_ruby_ore", chaosRL("blackstone_ruby_ore"));

		this.cubeColumn("apple_log", chaosRL("apple_log"), chaosRL("apple_log_top"));
		this.cubeColumnHorizontal("apple_log", chaosRL("apple_log"), chaosRL("apple_log_top"));
		this.cubeColumn("apple_wood", chaosRL("apple_log"), chaosRL("apple_log"));
		this.cubeColumnHorizontal("apple_wood", chaosRL("apple_log"), chaosRL("apple_log"));
		this.cubeColumn("stripped_apple_log", chaosRL("stripped_apple_log"), chaosRL("stripped_apple_log_top"));
		this.cubeColumnHorizontal("stripped_apple_log", chaosRL("stripped_apple_log"), chaosRL("stripped_apple_log_top"));
		this.cubeColumn("stripped_apple_wood", chaosRL("stripped_apple_log"), chaosRL("stripped_apple_log"));
		this.cubeColumnHorizontal("stripped_apple_wood", chaosRL("stripped_apple_log"), chaosRL("stripped_apple_log"));
		this.cubeColumn("cherry_log", chaosRL("cherry_log"), chaosRL("cherry_log_top"));
		this.cubeColumnHorizontal("cherry_log", chaosRL("cherry_log"), chaosRL("cherry_log_top"));
		this.cubeColumn("cherry_wood", chaosRL("cherry_log"), chaosRL("cherry_log"));
		this.cubeColumnHorizontal("cherry_wood", chaosRL("cherry_log"), chaosRL("cherry_log"));
		this.cubeColumn("stripped_cherry_log", chaosRL("stripped_cherry_log"), chaosRL("stripped_cherry_log_top"));
		this.cubeColumnHorizontal("stripped_cherry_log", chaosRL("stripped_cherry_log"), chaosRL("stripped_cherry_log_top"));
		this.cubeColumn("stripped_cherry_wood", chaosRL("stripped_cherry_log"), chaosRL("stripped_cherry_log"));
		this.cubeColumnHorizontal("stripped_cherry_wood", chaosRL("stripped_cherry_log"), chaosRL("stripped_cherry_log"));
		this.cubeColumn("peach_log", chaosRL("peach_log"), chaosRL("peach_log_top"));
		this.cubeColumnHorizontal("peach_log", chaosRL("peach_log"), chaosRL("peach_log_top"));
		this.cubeColumn("peach_wood", chaosRL("peach_log"), chaosRL("peach_log"));
		this.cubeColumnHorizontal("peach_wood", chaosRL("peach_log"), chaosRL("peach_log"));
		this.cubeColumn("stripped_peach_log", chaosRL("stripped_peach_log"), chaosRL("stripped_peach_log_top"));
		this.cubeColumnHorizontal("stripped_peach_log", chaosRL("stripped_peach_log"), chaosRL("stripped_peach_log_top"));
		this.cubeColumn("stripped_peach_wood", chaosRL("stripped_peach_log"), chaosRL("stripped_peach_log"));
		this.cubeColumnHorizontal("stripped_peach_wood", chaosRL("stripped_peach_log"), chaosRL("stripped_peach_log"));
		this.cubeColumn("duplication_log", chaosRL("duplication_log"), chaosRL("duplication_log_top"));
		this.cubeColumnHorizontal("duplication_log", chaosRL("duplication_log"), chaosRL("duplication_log_top"));
		this.cubeColumn("duplication_wood", chaosRL("duplication_log"), chaosRL("duplication_log"));
		this.cubeColumnHorizontal("duplication_wood", chaosRL("duplication_log"), chaosRL("duplication_log"));
		this.cubeColumn("stripped_duplication_log", chaosRL("stripped_duplication_log"), chaosRL("stripped_duplication_log_top"));
		this.cubeColumnHorizontal("stripped_duplication_log", chaosRL("stripped_duplication_log"), chaosRL("stripped_duplication_log_top"));
		this.cubeColumn("stripped_duplication_wood", chaosRL("stripped_duplication_log"), chaosRL("stripped_duplication_log"));
		this.cubeColumnHorizontal("stripped_duplication_wood", chaosRL("stripped_duplication_log"), chaosRL("stripped_duplication_log"));
		this.cubeColumn("dead_duplication_log", chaosRL("dead_duplication_log"), chaosRL("dead_duplication_log_top"));
		this.cubeColumnHorizontal("dead_duplication_log", chaosRL("dead_duplication_log"), chaosRL("dead_duplication_log_top"));
		this.cubeColumn("dead_duplication_wood", chaosRL("dead_duplication_log"), chaosRL("dead_duplication_log"));
		this.cubeColumnHorizontal("dead_duplication_wood", chaosRL("dead_duplication_log"), chaosRL("dead_duplication_log"));
		this.cubeColumn("skywood_log", chaosRL("skywood_log"), chaosRL("skywood_log_top"));
		this.cubeColumnHorizontal("skywood_log", chaosRL("skywood_log"), chaosRL("skywood_log_top"));
		this.cubeColumn("skywood_wood", chaosRL("skywood_log"), chaosRL("skywood_log"));
		this.cubeColumnHorizontal("skywood_wood", chaosRL("skywood_log"), chaosRL("skywood_log"));
		this.cubeColumn("stripped_skywood_log", chaosRL("stripped_skywood_log"), chaosRL("stripped_skywood_log_top"));
		this.cubeColumnHorizontal("stripped_skywood_log", chaosRL("stripped_skywood_log"), chaosRL("stripped_skywood_log_top"));
		this.cubeColumn("stripped_skywood_wood", chaosRL("stripped_skywood_log"), chaosRL("stripped_skywood_log"));
		this.cubeColumnHorizontal("stripped_skywood_wood", chaosRL("stripped_skywood_log"), chaosRL("stripped_skywood_log"));
		this.cubeColumn("crystal_log", chaosRL("crystal_log"), chaosRL("crystal_log_top"));
		this.cubeColumnHorizontal("crystal_log", chaosRL("crystal_log"), chaosRL("crystal_log_top"));
		this.cubeColumn("crystal_wood", chaosRL("crystal_log"), chaosRL("crystal_log"));
		this.cubeColumnHorizontal("crystal_wood", chaosRL("crystal_log"), chaosRL("crystal_log"));
		this.cubeAll("apple_planks", chaosRL("apple_planks"));
		this.cubeAll("apple_leaves", chaosRL("apple_leaves"));
		this.cubeAll("apple_leaves_ripe", chaosRL("apple_leaves_ripe"));
		this.cross("apple_sapling", chaosRL("apple_sapling"));
		this.cubeAll("cherry_planks", chaosRL("cherry_planks"));
		this.cubeAll("cherry_leaves", chaosRL("cherry_leaves"));
		this.cubeAll("cherry_leaves_ripe", chaosRL("cherry_leaves_ripe"));
		this.cross("cherry_sapling", chaosRL("cherry_sapling"));
		this.cubeAll("peach_planks", chaosRL("peach_planks"));
		this.cubeAll("peach_leaves", chaosRL("peach_leaves"));
		this.cubeAll("peach_leaves_ripe", chaosRL("peach_leaves_ripe"));
		this.cross("peach_sapling", chaosRL("peach_sapling"));
		this.cubeAll("duplication_planks", chaosRL("duplication_planks"));
		this.cubeAll("duplication_leaves", chaosRL("duplication_leaves"));
		this.cubeAll("skywood_planks", chaosRL("skywood_planks"));
		this.cubeAll("skywood_leaves", chaosRL("skywood_leaves"));
		this.cubeAll("crystal_planks", chaosRL("crystal_planks"));
		this.cross("red_crystal_sapling", chaosRL("red_crystal_sapling"));
		this.cross("green_crystal_sapling", chaosRL("green_crystal_sapling"));
		this.cross("yellow_crystal_sapling", chaosRL("yellow_crystal_sapling"));

		this.stairs("apple_stairs", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		this.stairsInner("apple_stairs", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		this.stairsOuter("apple_stairs", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		this.stairs("cherry_stairs", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		this.stairsInner("cherry_stairs", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		this.stairsOuter("cherry_stairs", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		this.stairs("peach_stairs", chaosRL("peach_planks"), chaosRL("peach_planks"), chaosRL("peach_planks"));
		this.stairsInner("peach_stairs", chaosRL("peach_planks"), chaosRL("peach_planks"), chaosRL("peach_planks"));
		this.stairsOuter("peach_stairs", chaosRL("peach_planks"), chaosRL("peach_planks"), chaosRL("peach_planks"));
		this.stairs("duplication_stairs", chaosRL("duplication_planks"), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		this.stairsInner("duplication_stairs", chaosRL("duplication_planks"), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		this.stairsOuter("duplication_stairs", chaosRL("duplication_planks"), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		this.stairs("skywood_stairs", chaosRL("skywood_planks"), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		this.stairsInner("skywood_stairs", chaosRL("skywood_planks"), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		this.stairsOuter("skywood_stairs", chaosRL("skywood_planks"), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		this.stairs("crystal_stairs", chaosRL("crystal_planks"), chaosRL("crystal_planks"), chaosRL("crystal_planks"));
		this.stairsInner("crystal_stairs", chaosRL("crystal_planks"), chaosRL("crystal_planks"), chaosRL("crystal_planks"));
		this.stairsOuter("crystal_stairs", chaosRL("crystal_planks"), chaosRL("crystal_planks"), chaosRL("crystal_planks"));

		this.slab("apple_slab", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		this.slabTop("apple_slab", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		this.slab("cherry_slab", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		this.slabTop("cherry_slab", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		this.slab("peach_slab", chaosRL("peach_planks"), chaosRL("peach_planks"), chaosRL("peach_planks"));
		this.slabTop("peach_slab", chaosRL("peach_planks"), chaosRL("peach_planks"), chaosRL("peach_planks"));
		this.slab("duplication_slab", chaosRL("duplication_planks"), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		this.slabTop("duplication_slab", chaosRL("duplication_planks"), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		this.slab("skywood_slab", chaosRL("skywood_planks"), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		this.slabTop("skywood_slab", chaosRL("skywood_planks"), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		this.slab("crystal_slab", chaosRL("crystal_planks"), chaosRL("crystal_planks"), chaosRL("crystal_planks"));
		this.slabTop("crystal_slab", chaosRL("crystal_planks"), chaosRL("crystal_planks"), chaosRL("crystal_planks"));

		this.fenceGate("apple_fence_gate", chaosRL("apple_planks"));
		this.fenceGateOpen("apple_fence_gate", chaosRL("apple_planks"));
		this.fenceGateWall("apple_fence_gate", chaosRL("apple_planks"));
		this.fenceGateWallOpen("apple_fence_gate", chaosRL("apple_planks"));
		this.fencePost("apple_fence", chaosRL("apple_planks"));
		this.fenceInventory("apple_fence", chaosRL("apple_planks"));
		this.fenceSide("apple_fence", chaosRL("apple_planks"));
		this.fenceGate("cherry_fence_gate", chaosRL("cherry_planks"));
		this.fenceGateOpen("cherry_fence_gate", chaosRL("cherry_planks"));
		this.fenceGateWall("cherry_fence_gate", chaosRL("cherry_planks"));
		this.fenceGateWallOpen("cherry_fence_gate", chaosRL("cherry_planks"));
		this.fencePost("cherry_fence", chaosRL("cherry_planks"));
		this.fenceInventory("cherry_fence", chaosRL("cherry_planks"));
		this.fenceSide("cherry_fence", chaosRL("cherry_planks"));
		this.fenceGate("peach_fence_gate", chaosRL("peach_planks"));
		this.fenceGateOpen("peach_fence_gate", chaosRL("peach_planks"));
		this.fenceGateWall("peach_fence_gate", chaosRL("peach_planks"));
		this.fenceGateWallOpen("peach_fence_gate", chaosRL("peach_planks"));
		this.fencePost("peach_fence", chaosRL("peach_planks"));
		this.fenceInventory("peach_fence", chaosRL("peach_planks"));
		this.fenceSide("peach_fence", chaosRL("peach_planks"));
		this.fenceGate("duplication_fence_gate", chaosRL("duplication_planks"));
		this.fenceGateOpen("duplication_fence_gate", chaosRL("duplication_planks"));
		this.fenceGateWall("duplication_fence_gate", chaosRL("duplication_planks"));
		this.fenceGateWallOpen("duplication_fence_gate", chaosRL("duplication_planks"));
		this.fencePost("duplication_fence", chaosRL("duplication_planks"));
		this.fenceInventory("duplication_fence", chaosRL("duplication_planks"));
		this.fenceSide("duplication_fence", chaosRL("duplication_planks"));
		this.fenceGate("skywood_fence_gate", chaosRL("skywood_planks"));
		this.fenceGateOpen("skywood_fence_gate", chaosRL("skywood_planks"));
		this.fenceGateWall("skywood_fence_gate", chaosRL("skywood_planks"));
		this.fenceGateWallOpen("skywood_fence_gate", chaosRL("skywood_planks"));
		this.fencePost("skywood_fence", chaosRL("skywood_planks"));
		this.fenceInventory("skywood_fence", chaosRL("skywood_planks"));
		this.fenceSide("skywood_fence", chaosRL("skywood_planks"));
		this.fenceGate("crystal_fence_gate", chaosRL("crystal_planks"));
		this.fenceGateOpen("crystal_fence_gate", chaosRL("crystal_planks"));
		this.fenceGateWall("crystal_fence_gate", chaosRL("crystal_planks"));
		this.fenceGateWallOpen("crystal_fence_gate", chaosRL("crystal_planks"));
		this.fencePost("crystal_fence", chaosRL("crystal_planks"));
		this.fenceInventory("crystal_fence", chaosRL("crystal_planks"));
		this.fenceSide("crystal_fence", chaosRL("crystal_planks"));

		this.pressurePlateUp("apple_pressure_plate", chaosRL("apple_planks"));
		this.pressurePlateDown("apple_pressure_plate", chaosRL("apple_planks"));
		this.pressurePlateUp("cherry_pressure_plate", chaosRL("cherry_planks"));
		this.pressurePlateDown("cherry_pressure_plate", chaosRL("cherry_planks"));
		this.pressurePlateUp("duplication_pressure_plate", chaosRL("duplication_planks"));
		this.pressurePlateDown("duplication_pressure_plate", chaosRL("duplication_planks"));
		this.pressurePlateUp("peach_pressure_plate", chaosRL("peach_planks"));
		this.pressurePlateDown("peach_pressure_plate", chaosRL("peach_planks"));
		this.pressurePlateUp("skywood_pressure_plate", chaosRL("skywood_planks"));
		this.pressurePlateDown("skywood_pressure_plate", chaosRL("skywood_planks"));
		this.pressurePlateUp("crystal_pressure_plate", chaosRL("crystal_planks"));
		this.pressurePlateDown("crystal_pressure_plate", chaosRL("crystal_planks"));

		this.cubeAll("mining_lamp", chaosRL("mining_lamp"));

		this.cross("cyan_rose", chaosRL("cyan_rose"));
		this.cross("red_rose", chaosRL("red_rose"));
		this.cross("paeonia", chaosRL("paeonia"));
		this.cross("blue_crystal_fan", chaosRL("blue_crystal_fan"));
		this.cross("green_crystal_fan", chaosRL("green_crystal_fan"));
		this.cross("red_crystal_fan", chaosRL("red_crystal_fan"));
		this.cross("yellow_crystal_fan", chaosRL("red_crystal_fan"));
		this.cross("blue_crystal_flower", chaosRL("blue_crystal_flower"));
		this.cross("green_crystal_flower", chaosRL("green_crystal_flower"));
		this.cross("red_crystal_flower", chaosRL("red_crystal_flower"));
		this.cross("yellow_crystal_flower", chaosRL("yellow_crystal_flower"));

		this.gateBlock("acacia_gate_block", chaosRL("gate_block_acacia"), chaosRL("gate_block_top"));
		this.gateBlock("birch_gate_block", chaosRL("gate_block_birch"), chaosRL("gate_block_top"));
		this.gateBlock("crimson_gate_block", chaosRL("gate_block_crimson"), chaosRL("gate_block_top"));
		this.gateBlock("dark_oak_gate_block", chaosRL("gate_block_dark_oak"), chaosRL("gate_block_top"));
		this.gateBlock("jungle_gate_block", chaosRL("gate_block_jungle"), chaosRL("gate_block_top"));
		this.gateBlock("oak_gate_block", chaosRL("gate_block_oak"), chaosRL("gate_block_top"));
		this.gateBlock("spruce_gate_block", chaosRL("gate_block_spruce"), chaosRL("gate_block_top"));
		this.gateBlock("warped_gate_block", chaosRL("gate_block_warped"), chaosRL("gate_block_top"));
	}

	private ResourceLocation chaosRL(String texture) {
		return new ResourceLocation(ChaosAwakens.MODID, BLOCK_FOLDER + "/" + texture);
	}

	private ResourceLocation mcRL(String texture) {
		return new ResourceLocation("minecraft", BLOCK_FOLDER + "/" + texture);
	}

	public void pressurePlateUp(String name, ResourceLocation all) {
		singleTexture(name, mcRL("pressure_plate_up"), all);
	}

	public void pressurePlateDown(String name, ResourceLocation all) {
		singleTexture(name, mcRL("pressure_plate_down"), all);
	}

	public void gateBlock(String name, ResourceLocation side, ResourceLocation top) {
		withExistingParent(name, BLOCK_FOLDER).texture("side", side).texture("top", top).texture("bottom", top);
	}

	@Override
	public BlockModelBuilder cubeColumn(String name, ResourceLocation side, ResourceLocation end) {
		return withExistingParent(name, BLOCK_FOLDER).texture("side", side).texture("end", end);
	}

	@Override
	public BlockModelBuilder cubeColumnHorizontal(String name, ResourceLocation side, ResourceLocation end) {
		return withExistingParent(name, BLOCK_FOLDER).texture("side", side).texture("end", end);
	}
}