package io.github.chaosawakens.data.provider;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.GateBlock;
import io.github.chaosawakens.common.blocks.crystal.EnergizedKyaniteBlock;
import io.github.chaosawakens.common.blocks.ore.CAFallingOreBlock;
import io.github.chaosawakens.common.blocks.ore.CAOreBlock;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerBlock;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Objects;

@SuppressWarnings("all")
public class CABlockModelProvider extends BlockModelProvider {
	public CABlockModelProvider(DataGenerator generator,ExistingFileHelper existingFileHelper) {
		super(generator, ChaosAwakens.MODID, existingFileHelper);
	}

	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + ": Block Models";
	}

	private static ResourceLocation getResourceLocation(String path) {
		return ChaosAwakens.prefix(path);
	}

	private static ResourceLocation getBlockResourceLocation(String name) {
		return getResourceLocation("block/" + name);
	}

	private static ResourceLocation getBlockResourceLocation(String name, @Nonnull String removeSuffix, String addSuffix) {
		return getBlockResourceLocation(name.substring(0, name.length() - removeSuffix.length()) + addSuffix);
	}

	@Override
	protected void registerModels() {
		// TODO Automate the data generators
		for (Block block : ForgeRegistries.BLOCKS) {
			if (!ChaosAwakens.MODID.equals(Objects.requireNonNull(block.getRegistryName()).getNamespace())) continue;

			String name = block.getRegistryName().getPath();
			ChaosAwakens.LOGGER.debug(block.getRegistryName());

			if (block instanceof StandingSignBlock || block instanceof WallSignBlock) {
				if (block instanceof StandingSignBlock) getBuilder(name).texture("particle", getBlockResourceLocation(name, "_sign", "_planks"));
			} else if (block instanceof DoorBlock) {
				doorBottomLeft(name + "_bottom", chaosRL(name + "_bottom"), chaosRL(name + "_top"));
				doorBottomRight(name + "_bottom_hinge", chaosRL(name + "_bottom"), chaosRL(name + "_top"));
				doorTopLeft(name + "_top", chaosRL(name + "_bottom"), chaosRL(name + "_top"));
				doorTopRight(name + "_top_hinge", chaosRL(name + "_bottom"), chaosRL(name + "_top"));
			} else if (block instanceof TrapDoorBlock) {
				trapDoor(name);
			} else if (block instanceof GateBlock) {
				gateBlock(name, name.contains("robo_") ? chaosRL("robo_gate_block_top") : chaosRL("gate_block_top"));
			} else if (block instanceof CAOreBlock || block instanceof CAFallingOreBlock) {
				if(block instanceof EnergizedKyaniteBlock) {
					cross(name, chaosRL(name));
				} else if (name.contains("sandstone")) {
					cubeBottomTop(name, chaosRL(name), mcRL("sandstone_bottom"), mcRL("sandstone_top"));
				} else {
					if (!(name.contains("ant_infested") || name.contains("termite_infested"))) {
						cubeAll(name, chaosRL(name));
					}
				}
			} else if (block instanceof DefossilizerBlock) {
				String type = name.replace("_defossilizer", "");
				orientable(name, type.equals("iron") ? mcRL(type + "_block") : type.equals("crystal") ? chaosRL("pink_tourmaline_block") :  chaosRL(type + "_block"), chaosRL(type + "_defossilizer_front"), chaosRL(type + "_defossilizer_top"));
			}
		}

		grassBlock("dense_grass_block", chaosRL("dense_dirt"), chaosRL("dense_dirt"), chaosRL("dense_grass_block_top"), chaosRL("dense_grass_block_side"), chaosRL("dense_grass_block_side_overlay"));
		//cubeBottomTop("dense_grass_block", chaosRL("dense_grass_block_side"), chaosRL("dense_dirt"), chaosRL("dense_grass_block_top"));
		cubeAll("dense_dirt", chaosRL("dense_dirt"));
		farmland("dense_farmland", chaosRL("dense_dirt"), chaosRL("dense_farmland"));
		farmland("dense_farmland_moist", chaosRL("dense_dirt"), chaosRL("dense_farmland_moist"));
		grassBlock("dense_red_ant_nest", chaosRL("dense_dirt"), chaosRL("dense_dirt"), chaosRL("dense_red_ant_nest"), chaosRL("dense_grass_block_side"), chaosRL("dense_grass_block_side_overlay"));
		tintedCross("dense_grass", chaosRL("dense_grass"));
		doubleTintedCross("tall_dense_grass", "tall_dense_grass");
		doublePlant("thorny_sun", "thorny_sun");
		cubeAll("terra_preta", chaosRL("terra_preta"));
		farmland("terra_preta_farmland", chaosRL("terra_preta"), chaosRL("terra_preta_farmland"));
		farmland("terra_preta_farmland_moist", chaosRL("terra_preta"), chaosRL("terra_preta_farmland_moist"));
		cubeAll("tar", chaosRL("tar"));
		cubeAll("latosol", chaosRL("latosol"));
		doublePlant("alstroemeriat", "alstroemeriat");
		cross("dense_orchid", chaosRL("dense_orchid"));
		cross("small_bush", chaosRL("small_bush"));
		doublePlant("tall_bush", "tall_bush");
		cross("small_carnivorous_plant", chaosRL("small_carnivorous_plant"));
		cross("big_carnivorous_plant", chaosRL("big_carnivorous_plant"));
		cross("blue_bulb", chaosRL("blue_bulb"));
		cross("pink_bulb", chaosRL("pink_bulb"));
		cross("purple_bulb", chaosRL("purple_bulb"));
		cross("mesozoic_vines", chaosRL("mesozoic_vines"));
		cross("mesozoic_vines_plant", chaosRL("mesozoic_vines_plant"));

		cubeAll("aluminum_block", chaosRL("aluminum_block"));
//		cubeAll("aluminum_ore", chaosRL("aluminum_ore"));
		cubeAll("amethyst_block", chaosRL("amethyst_block"));
//		cubeAll("amethyst_ore", chaosRL("amethyst_ore"));
		cubeAll("bloodstone_block", chaosRL("bloodstone_block"));
//		cubeAll("bloodstone_ore", chaosRL("bloodstone_ore"));
		cubeAll("copper_block", chaosRL("copper_block"));
//		cubeAll("copper_ore", chaosRL("copper_ore"));
		cubeAll("platinum_block", chaosRL("platinum_block"));
//		cubeAll("platinum_ore", chaosRL("platinum_ore"));
		cubeAll("ruby_block", chaosRL("ruby_block"));
//		cubeAll("ruby_ore", chaosRL("ruby_ore"));
//		cubeAll("netherrack_ruby_ore", chaosRL("netherrack_ruby_ore"));
//		cubeAll("blackstone_ruby_ore", chaosRL("blackstone_ruby_ore"));
		cubeAll("salt_block", chaosRL("salt_block"));
//		cubeAll("salt_ore", chaosRL("salt_ore"));
		cubeAll("silver_block", chaosRL("silver_block"));
//		cubeAll("silver_ore", chaosRL("silver_ore"));
		cubeAll("sunstone_block", chaosRL("sunstone_block"));
//		cubeAll("sunstone_ore", chaosRL("sunstone_ore"));
		cubeAll("tigers_eye_block", chaosRL("tigers_eye_block"));
//		cubeAll("tigers_eye_ore", chaosRL("tigers_eye_ore"));
		cubeAll("tin_block", chaosRL("tin_block"));
//		cubeAll("tin_ore", chaosRL("tin_ore"));
		cubeAll("titanium_block", chaosRL("titanium_block"));
//		cubeAll("titanium_ore", chaosRL("titanium_ore"));
		cubeAll("uranium_block", chaosRL("uranium_block"));
//		cubeAll("uranium_ore", chaosRL("uranium_ore"));

		cubeAll("budding_cats_eye", chaosRL("budding_cats_eye"));
		cubeAll("cats_eye_block", chaosRL("cats_eye_block"));
		cubeAll("budding_pink_tourmaline", chaosRL("budding_pink_tourmaline"));
		cubeAll("pink_tourmaline_block", chaosRL("pink_tourmaline_block"));
		cubeAll("energized_kyanite", chaosRL("energized_kyanite"));

		cubeAll("marble_block", chaosRL("marble_block"));
		cubeAll("marble_bricks", chaosRL("marble_bricks"));
		cubeAll("chiseled_marble_bricks", chaosRL("chiseled_marble_bricks"));
		cubeAll("cracked_marble_bricks", chaosRL("cracked_marble_bricks"));
		cubeAll("mossy_marble_bricks", chaosRL("mossy_marble_bricks"));
		cubeAll("polished_marble_block", chaosRL("polished_marble_block"));
		cubeColumn("marble_pillar", chaosRL("marble_pillar"), chaosRL("marble_pillar_top"));
		cubeColumnHorizontal("marble_pillar", chaosRL("marble_pillar"), chaosRL("marble_pillar_top"));
		cubeColumn("marble_pillar_t", chaosRL("marble_pillar_t"), chaosRL("marble_pillar_top"));
		cubeColumnHorizontal("marble_pillar_t", chaosRL("marble_pillar_t"), chaosRL("marble_pillar_top"));
		cubeColumn("marble_pillar_s", chaosRL("marble_pillar_s"), chaosRL("marble_pillar_top"));
		cubeColumnHorizontal("marble_pillar_s", chaosRL("marble_pillar_s"), chaosRL("marble_pillar_top"));
		cubeColumn("marble_pillar_3", chaosRL("marble_pillar_3"), chaosRL("marble_pillar_top"));
		cubeColumnHorizontal("marble_pillar_3", chaosRL("marble_pillar_3"), chaosRL("marble_pillar_top"));
		cubeColumn("marble_pillar_z", chaosRL("marble_pillar_z"), chaosRL("marble_pillar_top"));
		cubeColumnHorizontal("marble_pillar_z", chaosRL("marble_pillar_z"), chaosRL("marble_pillar_top"));
		slab("marble_slab", chaosRL("marble_block"), chaosRL("marble_block"), chaosRL("marble_block"));
		slabTop("marble_slab", chaosRL("marble_block"), chaosRL("marble_block"), chaosRL("marble_block"));
		slab("marble_brick_slab", chaosRL("marble_bricks"), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		slabTop("marble_brick_slab", chaosRL("marble_bricks"), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		slab("chiseled_marble_brick_slab", chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		slabTop("chiseled_marble_brick_slab", chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		slab("cracked_marble_brick_slab", chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		slabTop("cracked_marble_brick_slab", chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		slab("mossy_marble_brick_slab", chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		slabTop("mossy_marble_brick_slab", chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		slab("polished_marble_slab", chaosRL("polished_marble_block"), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		slabTop("polished_marble_slab", chaosRL("polished_marble_block"), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		stairs("marble_stairs", chaosRL("marble_block"), chaosRL("marble_block"), chaosRL("marble_block"));
		stairsInner("marble_stairs", chaosRL("marble_block"), chaosRL("marble_block"), chaosRL("marble_block"));
		stairsOuter("marble_stairs", chaosRL("marble_block"), chaosRL("marble_block"), chaosRL("marble_block"));
		stairs("marble_brick_stairs", chaosRL("marble_bricks"), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		stairsInner("marble_brick_stairs", chaosRL("marble_bricks"), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		stairsOuter("marble_brick_stairs", chaosRL("marble_bricks"), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		stairs("chiseled_marble_brick_stairs", chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		stairsInner("chiseled_marble_brick_stairs", chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		stairsOuter("chiseled_marble_brick_stairs", chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		stairs("cracked_marble_brick_stairs", chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		stairsInner("cracked_marble_brick_stairs", chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		stairsOuter("cracked_marble_brick_stairs", chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		stairs("mossy_marble_brick_stairs", chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		stairsInner("mossy_marble_brick_stairs", chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		stairsOuter("mossy_marble_brick_stairs", chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		stairs("polished_marble_stairs", chaosRL("polished_marble_block"), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		stairsInner("polished_marble_stairs", chaosRL("polished_marble_block"), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		stairsOuter("polished_marble_stairs", chaosRL("polished_marble_block"), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		wallPost("marble_wall", chaosRL("marble_block"));
		wallInventory("marble_wall_inventory", chaosRL("marble_block"));
		wallSide("marble_wall", chaosRL("marble_block"));
		wallSideTall("marble_wall", chaosRL("marble_block"));
		wallPost("marble_brick_wall", chaosRL("marble_bricks"));
		wallInventory("marble_brick_wall_inventory", chaosRL("marble_bricks"));
		wallSide("marble_brick_wall", chaosRL("marble_bricks"));
		wallSideTall("marble_brick_wall", chaosRL("marble_bricks"));
		wallPost("chiseled_marble_brick_wall", chaosRL("chiseled_marble_bricks"));
		wallInventory("chiseled_marble_brick_wall_inventory", chaosRL("chiseled_marble_bricks"));
		wallSide("chiseled_marble_brick_wall", chaosRL("chiseled_marble_bricks"));
		wallSideTall("chiseled_marble_brick_wall", chaosRL("chiseled_marble_bricks"));
		wallPost("cracked_marble_brick_wall", chaosRL("cracked_marble_bricks"));
		wallInventory("cracked_marble_brick_wall_inventory", chaosRL("cracked_marble_bricks"));
		wallSide("cracked_marble_brick_wall", chaosRL("cracked_marble_bricks"));
		wallSideTall("cracked_marble_brick_wall", chaosRL("cracked_marble_bricks"));
		wallPost("mossy_marble_brick_wall", chaosRL("mossy_marble_bricks"));
		wallInventory("mossy_marble_brick_wall_inventory", chaosRL("mossy_marble_bricks"));
		wallSide("mossy_marble_brick_wall", chaosRL("mossy_marble_bricks"));
		wallSideTall("mossy_marble_brick_wall", chaosRL("mossy_marble_bricks"));
		wallPost("polished_marble_wall", chaosRL("polished_marble_block"));
		wallInventory("polished_marble_wall_inventory", chaosRL("polished_marble_block"));
		wallSide("polished_marble_wall", chaosRL("polished_marble_block"));
		wallSideTall("polished_marble_wall", chaosRL("polished_marble_block"));

		cubeAll("limestone_block", chaosRL("limestone_block"));
		cubeAll("limestone_bricks", chaosRL("limestone_bricks"));
		cubeAll("chiseled_limestone_bricks", chaosRL("chiseled_limestone_bricks"));
		cubeAll("cracked_limestone_bricks", chaosRL("cracked_limestone_bricks"));
		cubeAll("mossy_limestone_bricks", chaosRL("mossy_limestone_bricks"));
		cubeAll("polished_limestone_block", chaosRL("polished_limestone_block"));
		cubeColumn("limestone_pillar", chaosRL("limestone_pillar"), chaosRL("limestone_pillar_top"));
		cubeColumnHorizontal("limestone_pillar", chaosRL("limestone_pillar"), chaosRL("limestone_pillar_top"));
		slab("limestone_slab", chaosRL("limestone_block"), chaosRL("limestone_block"), chaosRL("limestone_block"));
		slabTop("limestone_slab", chaosRL("limestone_block"), chaosRL("limestone_block"), chaosRL("limestone_block"));
		slab("limestone_brick_slab", chaosRL("limestone_bricks"), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		slabTop("limestone_brick_slab", chaosRL("limestone_bricks"), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		slab("chiseled_limestone_brick_slab", chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		slabTop("chiseled_limestone_brick_slab", chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		slab("cracked_limestone_brick_slab", chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		slabTop("cracked_limestone_brick_slab", chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		slab("mossy_limestone_brick_slab", chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		slabTop("mossy_limestone_brick_slab", chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		slab("polished_limestone_slab", chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		slabTop("polished_limestone_slab", chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		stairs("limestone_stairs", chaosRL("limestone_block"), chaosRL("limestone_block"), chaosRL("limestone_block"));
		stairsInner("limestone_stairs", chaosRL("limestone_block"), chaosRL("limestone_block"), chaosRL("limestone_block"));
		stairsOuter("limestone_stairs", chaosRL("limestone_block"), chaosRL("limestone_block"), chaosRL("limestone_block"));
		stairs("limestone_brick_stairs", chaosRL("limestone_bricks"), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		stairsInner("limestone_brick_stairs", chaosRL("limestone_bricks"), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		stairsOuter("limestone_brick_stairs", chaosRL("limestone_bricks"), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		stairs("chiseled_limestone_brick_stairs", chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		stairsInner("chiseled_limestone_brick_stairs", chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		stairsOuter("chiseled_limestone_brick_stairs", chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		stairs("cracked_limestone_brick_stairs", chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		stairsInner("cracked_limestone_brick_stairs", chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		stairsOuter("cracked_limestone_brick_stairs", chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		stairs("mossy_limestone_brick_stairs", chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		stairsInner("mossy_limestone_brick_stairs", chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		stairsOuter("mossy_limestone_brick_stairs", chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		stairs("polished_limestone_stairs", chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		stairsInner("polished_limestone_stairs", chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		stairsOuter("polished_limestone_stairs", chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		wallPost("limestone_wall", chaosRL("limestone_block"));
		wallInventory("limestone_wall_inventory", chaosRL("limestone_block"));
		wallSide("limestone_wall", chaosRL("limestone_block"));
		wallSideTall("limestone_wall", chaosRL("limestone_block"));
		wallPost("limestone_brick_wall", chaosRL("limestone_bricks"));
		wallInventory("limestone_brick_wall_inventory", chaosRL("limestone_bricks"));
		wallSide("limestone_brick_wall", chaosRL("limestone_bricks"));
		wallSideTall("limestone_brick_wall", chaosRL("limestone_bricks"));
		wallPost("chiseled_limestone_brick_wall", chaosRL("chiseled_limestone_bricks"));
		wallInventory("chiseled_limestone_brick_wall_inventory", chaosRL("chiseled_limestone_bricks"));
		wallSide("chiseled_limestone_brick_wall", chaosRL("chiseled_limestone_bricks"));
		wallSideTall("chiseled_limestone_brick_wall", chaosRL("chiseled_limestone_bricks"));
		wallPost("cracked_limestone_brick_wall", chaosRL("cracked_limestone_bricks"));
		wallInventory("cracked_limestone_brick_wall_inventory", chaosRL("cracked_limestone_bricks"));
		wallSide("cracked_limestone_brick_wall", chaosRL("cracked_limestone_bricks"));
		wallSideTall("cracked_limestone_brick_wall", chaosRL("cracked_limestone_bricks"));
		wallPost("mossy_limestone_brick_wall", chaosRL("mossy_limestone_bricks"));
		wallInventory("mossy_limestone_brick_wall_inventory", chaosRL("mossy_limestone_bricks"));
		wallSide("mossy_limestone_brick_wall", chaosRL("mossy_limestone_bricks"));
		wallSideTall("mossy_limestone_brick_wall", chaosRL("mossy_limestone_bricks"));
		wallPost("polished_limestone_wall", chaosRL("polished_limestone_block"));
		wallInventory("polished_limestone_wall_inventory", chaosRL("polished_limestone_block"));
		wallSide("polished_limestone_wall", chaosRL("polished_limestone_block"));
		wallSideTall("polished_limestone_wall", chaosRL("polished_limestone_block"));

		cubeAll("rhinestone_block", chaosRL("rhinestone_block"));
		cubeAll("rhinestone_bricks", chaosRL("rhinestone_bricks"));
		cubeAll("chiseled_rhinestone_bricks", chaosRL("chiseled_rhinestone_bricks"));
		cubeAll("cracked_rhinestone_bricks", chaosRL("cracked_rhinestone_bricks"));
		cubeAll("mossy_rhinestone_bricks", chaosRL("mossy_rhinestone_bricks"));
		cubeAll("polished_rhinestone_block", chaosRL("polished_rhinestone_block"));
		cubeColumn("rhinestone_pillar", chaosRL("rhinestone_pillar"), chaosRL("rhinestone_pillar_top"));
		cubeColumnHorizontal("rhinestone_pillar", chaosRL("rhinestone_pillar"), chaosRL("rhinestone_pillar_top"));
		slab("rhinestone_slab", chaosRL("rhinestone_block"), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		slabTop("rhinestone_slab", chaosRL("rhinestone_block"), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		slab("rhinestone_brick_slab", chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		slabTop("rhinestone_brick_slab", chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		slab("chiseled_rhinestone_brick_slab", chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		slabTop("chiseled_rhinestone_brick_slab", chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		slab("cracked_rhinestone_brick_slab", chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		slabTop("cracked_rhinestone_brick_slab", chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		slab("mossy_rhinestone_brick_slab", chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		slabTop("mossy_rhinestone_brick_slab", chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		slab("polished_rhinestone_slab", chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		slabTop("polished_rhinestone_slab", chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		stairs("rhinestone_stairs", chaosRL("rhinestone_block"), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		stairsInner("rhinestone_stairs", chaosRL("rhinestone_block"), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		stairsOuter("rhinestone_stairs", chaosRL("rhinestone_block"), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		stairs("rhinestone_brick_stairs", chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		stairsInner("rhinestone_brick_stairs", chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		stairsOuter("rhinestone_brick_stairs", chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		stairs("chiseled_rhinestone_brick_stairs", chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		stairsInner("chiseled_rhinestone_brick_stairs", chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		stairsOuter("chiseled_rhinestone_brick_stairs", chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		stairs("cracked_rhinestone_brick_stairs", chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		stairsInner("cracked_rhinestone_brick_stairs", chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		stairsOuter("cracked_rhinestone_brick_stairs", chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		stairs("mossy_rhinestone_brick_stairs", chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		stairsInner("mossy_rhinestone_brick_stairs", chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		stairsOuter("mossy_rhinestone_brick_stairs", chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		stairs("polished_rhinestone_stairs", chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		stairsInner("polished_rhinestone_stairs", chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		stairsOuter("polished_rhinestone_stairs", chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		wallPost("rhinestone_wall", chaosRL("rhinestone_block"));
		wallInventory("rhinestone_wall_inventory", chaosRL("rhinestone_block"));
		wallSide("rhinestone_wall", chaosRL("rhinestone_block"));
		wallSideTall("rhinestone_wall", chaosRL("rhinestone_block"));
		wallPost("rhinestone_brick_wall", chaosRL("rhinestone_bricks"));
		wallInventory("rhinestone_brick_wall_inventory", chaosRL("rhinestone_bricks"));
		wallSide("rhinestone_brick_wall", chaosRL("rhinestone_bricks"));
		wallSideTall("rhinestone_brick_wall", chaosRL("rhinestone_bricks"));
		wallPost("chiseled_rhinestone_brick_wall", chaosRL("chiseled_rhinestone_bricks"));
		wallInventory("chiseled_rhinestone_brick_wall_inventory", chaosRL("chiseled_rhinestone_bricks"));
		wallSide("chiseled_rhinestone_brick_wall", chaosRL("chiseled_rhinestone_bricks"));
		wallSideTall("chiseled_rhinestone_brick_wall", chaosRL("chiseled_rhinestone_bricks"));
		wallPost("cracked_rhinestone_brick_wall", chaosRL("cracked_rhinestone_bricks"));
		wallInventory("cracked_rhinestone_brick_wall_inventory", chaosRL("cracked_rhinestone_bricks"));
		wallSide("cracked_rhinestone_brick_wall", chaosRL("cracked_rhinestone_bricks"));
		wallSideTall("cracked_rhinestone_brick_wall", chaosRL("cracked_rhinestone_bricks"));
		wallPost("mossy_rhinestone_brick_wall", chaosRL("mossy_rhinestone_bricks"));
		wallInventory("mossy_rhinestone_brick_wall_inventory", chaosRL("mossy_rhinestone_bricks"));
		wallSide("mossy_rhinestone_brick_wall", chaosRL("mossy_rhinestone_bricks"));
		wallSideTall("mossy_rhinestone_brick_wall", chaosRL("mossy_rhinestone_bricks"));
		wallPost("polished_rhinestone_wall", chaosRL("polished_rhinestone_block"));
		wallInventory("polished_rhinestone_wall_inventory", chaosRL("polished_rhinestone_block"));
		wallSide("polished_rhinestone_wall", chaosRL("polished_rhinestone_block"));
		wallSideTall("polished_rhinestone_wall", chaosRL("polished_rhinestone_block"));

		cubeAll("robo_block_l", chaosRL("robo_block_l"));
		cubeColumn("robo_block_v", chaosRL("robo_block_v"), chaosRL("robo_block_top"));
		cubeColumnHorizontal("robo_block_v", chaosRL("robo_block_v"), chaosRL("robo_block_top"));
		cubeColumn("robo_block_x", chaosRL("robo_block_v"), chaosRL("robo_block_top"));
		cubeColumnHorizontal("robo_block_x", chaosRL("robo_block_v"), chaosRL("robo_block_top"));
		slab("robo_slab_l", chaosRL("robo_block_l"), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		slabTop("robo_slab_l", chaosRL("robo_block_l"), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		slab("robo_slab_x", chaosRL("robo_block_x"), chaosRL("robo_block_top"), chaosRL("robo_block_top"));
		slabTop("robo_slab_x", chaosRL("robo_block_x"), chaosRL("robo_block_top"), chaosRL("robo_block_top"));
		stairs("robo_stairs_l", chaosRL("robo_block_l"), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		stairsInner("robo_stairs_l", chaosRL("robo_block_l"), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		stairsOuter("robo_stairs_l", chaosRL("robo_block_l"), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		stairs("robo_stairs_x", chaosRL("robo_block_x"), chaosRL("robo_block_top"), chaosRL("robo_block_top"));
		stairsInner("robo_stairs_x", chaosRL("robo_block_x"), chaosRL("robo_block_top"), chaosRL("robo_block_top"));
		stairsOuter("robo_stairs_x", chaosRL("robo_block_x"), chaosRL("robo_block_top"), chaosRL("robo_block_top"));
		wallPost("robo_wall_l", chaosRL("robo_block_l"));
		wallInventory("robo_wall_l_inventory", chaosRL("robo_block_l"));
		wallSide("robo_wall_l", chaosRL("robo_block_l"));
		wallSideTall("robo_wall_l", chaosRL("robo_block_l"));
		wallPost("robo_wall_x", chaosRL("robo_block_x"));
		wallInventory("robo_wall_x_inventory", chaosRL("robo_block_x"));
		wallSide("robo_wall_x", chaosRL("robo_block_x"));
		wallSideTall("robo_wall_x", chaosRL("robo_block_x"));
		cubeAll("robo_lamp", chaosRL("robo_lamp"));
		cubeAll("robo_glass", chaosRL("robo_glass"));
		standardPaneBlock("robo_glass_pane", chaosRL("robo_glass"), chaosRL("robo_glass_pane_top"));
		standardBarBlock("robo_bars", chaosRL("robo_bars"));
		cubeAll("robo_bricks", chaosRL("robo_bricks"));
		slab("robo_brick_slab", chaosRL("robo_bricks"), chaosRL("robo_bricks"), chaosRL("robo_bricks"));
		slabTop("robo_brick_slab", chaosRL("robo_bricks"), chaosRL("robo_bricks"), chaosRL("robo_bricks"));
		stairs("robo_brick_stairs", chaosRL("robo_bricks"), chaosRL("robo_bricks"), chaosRL("robo_bricks"));
		stairsInner("robo_brick_stairs", chaosRL("robo_bricks"), chaosRL("robo_bricks"), chaosRL("robo_bricks"));
		stairsOuter("robo_brick_stairs", chaosRL("robo_bricks"), chaosRL("robo_bricks"), chaosRL("robo_bricks"));
		wallPost("robo_brick_wall", chaosRL("robo_bricks"));
		wallInventory("robo_brick_wall_inventory", chaosRL("robo_bricks"));
		wallSide("robo_brick_wall", chaosRL("robo_bricks"));
		wallSideTall("robo_brick_wall", chaosRL("robo_bricks"));
		cubeColumn("compact_robo_block", chaosRL("compact_robo_block"), chaosRL("compact_robo_block_top"));
		cubeColumnHorizontal("compact_robo_block", chaosRL("compact_robo_block"), chaosRL("compact_robo_block_top"));
		cubeColumn("double_compact_robo_block", chaosRL("double_compact_robo_block"), chaosRL("compact_robo_block_top"));
		cubeColumnHorizontal("double_compact_robo_block", chaosRL("double_compact_robo_block"), chaosRL("compact_robo_block_top"));

		cubeAll("black_terracotta_bricks", chaosRL("black_terracotta_bricks"));
		cubeAll("blue_terracotta_bricks", chaosRL("blue_terracotta_bricks"));
		cubeAll("brown_terracotta_bricks", chaosRL("brown_terracotta_bricks"));
		cubeAll("cyan_terracotta_bricks", chaosRL("cyan_terracotta_bricks"));
		cubeAll("gray_terracotta_bricks", chaosRL("gray_terracotta_bricks"));
		cubeAll("green_terracotta_bricks", chaosRL("green_terracotta_bricks"));
		cubeAll("light_blue_terracotta_bricks", chaosRL("light_blue_terracotta_bricks"));
		cubeAll("light_gray_terracotta_bricks", chaosRL("light_gray_terracotta_bricks"));
		cubeAll("lime_terracotta_bricks", chaosRL("lime_terracotta_bricks"));
		cubeAll("magenta_terracotta_bricks", chaosRL("magenta_terracotta_bricks"));
		cubeAll("orange_terracotta_bricks", chaosRL("orange_terracotta_bricks"));
		cubeAll("pink_terracotta_bricks", chaosRL("pink_terracotta_bricks"));
		cubeAll("purple_terracotta_bricks", chaosRL("purple_terracotta_bricks"));
		cubeAll("red_terracotta_bricks", chaosRL("red_terracotta_bricks"));
		cubeAll("terracotta_bricks", chaosRL("terracotta_bricks"));
		cubeAll("white_terracotta_bricks", chaosRL("white_terracotta_bricks"));
		cubeAll("yellow_terracotta_bricks", chaosRL("yellow_terracotta_bricks"));
		slab("black_terracotta_brick_slab", chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		slabTop("black_terracotta_brick_slab", chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		slab("blue_terracotta_brick_slab", chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		slabTop("blue_terracotta_brick_slab", chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		slab("brown_terracotta_brick_slab", chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		slabTop("brown_terracotta_brick_slab", chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		slab("cyan_terracotta_brick_slab", chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		slabTop("cyan_terracotta_brick_slab", chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		slab("gray_terracotta_brick_slab", chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		slabTop("gray_terracotta_brick_slab", chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		slab("green_terracotta_brick_slab", chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		slabTop("green_terracotta_brick_slab", chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		slab("light_blue_terracotta_brick_slab", chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		slabTop("light_blue_terracotta_brick_slab", chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		slab("light_gray_terracotta_brick_slab", chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		slabTop("light_gray_terracotta_brick_slab", chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		slab("lime_terracotta_brick_slab", chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		slabTop("lime_terracotta_brick_slab", chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		slab("magenta_terracotta_brick_slab", chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		slabTop("magenta_terracotta_brick_slab", chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		slab("orange_terracotta_brick_slab", chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		slabTop("orange_terracotta_brick_slab", chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		slab("pink_terracotta_brick_slab", chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		slabTop("pink_terracotta_brick_slab", chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		slab("purple_terracotta_brick_slab", chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		slabTop("purple_terracotta_brick_slab", chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		slab("red_terracotta_brick_slab", chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		slabTop("red_terracotta_brick_slab", chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		slab("terracotta_brick_slab", chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		slabTop("terracotta_brick_slab", chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		slab("white_terracotta_brick_slab", chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		slabTop("white_terracotta_brick_slab", chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		slab("yellow_terracotta_brick_slab", chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		slabTop("yellow_terracotta_brick_slab", chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		stairs("black_terracotta_brick_stairs", chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		stairsInner("black_terracotta_brick_stairs", chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		stairsOuter("black_terracotta_brick_stairs", chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		stairs("blue_terracotta_brick_stairs", chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		stairsInner("blue_terracotta_brick_stairs", chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		stairsOuter("blue_terracotta_brick_stairs", chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		stairs("brown_terracotta_brick_stairs", chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		stairsInner("brown_terracotta_brick_stairs", chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		stairsOuter("brown_terracotta_brick_stairs", chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		stairs("cyan_terracotta_brick_stairs", chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		stairsInner("cyan_terracotta_brick_stairs", chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		stairsOuter("cyan_terracotta_brick_stairs", chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		stairs("gray_terracotta_brick_stairs", chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		stairsInner("gray_terracotta_brick_stairs", chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		stairsOuter("gray_terracotta_brick_stairs", chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		stairs("green_terracotta_brick_stairs", chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		stairsInner("green_terracotta_brick_stairs", chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		stairsOuter("green_terracotta_brick_stairs", chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		stairs("light_blue_terracotta_brick_stairs", chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		stairsInner("light_blue_terracotta_brick_stairs", chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		stairsOuter("light_blue_terracotta_brick_stairs", chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		stairs("light_gray_terracotta_brick_stairs", chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		stairsInner("light_gray_terracotta_brick_stairs", chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		stairsOuter("light_gray_terracotta_brick_stairs", chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		stairs("lime_terracotta_brick_stairs", chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		stairsInner("lime_terracotta_brick_stairs", chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		stairsOuter("lime_terracotta_brick_stairs", chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		stairs("magenta_terracotta_brick_stairs", chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		stairsInner("magenta_terracotta_brick_stairs", chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		stairsOuter("magenta_terracotta_brick_stairs", chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		stairs("orange_terracotta_brick_stairs", chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		stairsInner("orange_terracotta_brick_stairs", chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		stairsOuter("orange_terracotta_brick_stairs", chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		stairs("pink_terracotta_brick_stairs", chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		stairsInner("pink_terracotta_brick_stairs", chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		stairsOuter("pink_terracotta_brick_stairs", chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		stairs("purple_terracotta_brick_stairs", chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		stairsInner("purple_terracotta_brick_stairs", chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		stairsOuter("purple_terracotta_brick_stairs", chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		stairs("red_terracotta_brick_stairs", chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		stairsInner("red_terracotta_brick_stairs", chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		stairsOuter("red_terracotta_brick_stairs", chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		stairs("terracotta_brick_stairs", chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		stairsInner("terracotta_brick_stairs", chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		stairsOuter("terracotta_brick_stairs", chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		stairs("white_terracotta_brick_stairs", chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		stairsInner("white_terracotta_brick_stairs", chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		stairsOuter("white_terracotta_brick_stairs", chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		stairs("yellow_terracotta_brick_stairs", chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		stairsInner("yellow_terracotta_brick_stairs", chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		stairsOuter("yellow_terracotta_brick_stairs", chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		wallPost("black_terracotta_brick_wall", chaosRL("black_terracotta_bricks"));
		wallInventory("black_terracotta_brick_wall_inventory", chaosRL("black_terracotta_bricks"));
		wallSide("black_terracotta_brick_wall", chaosRL("black_terracotta_bricks"));
		wallSideTall("black_terracotta_brick_wall", chaosRL("black_terracotta_bricks"));
		wallPost("blue_terracotta_brick_wall", chaosRL("blue_terracotta_bricks"));
		wallInventory("blue_terracotta_brick_wall_inventory", chaosRL("blue_terracotta_bricks"));
		wallSide("blue_terracotta_brick_wall", chaosRL("blue_terracotta_bricks"));
		wallSideTall("blue_terracotta_brick_wall", chaosRL("blue_terracotta_bricks"));
		wallPost("brown_terracotta_brick_wall", chaosRL("brown_terracotta_bricks"));
		wallInventory("brown_terracotta_brick_wall_inventory", chaosRL("brown_terracotta_bricks"));
		wallSide("brown_terracotta_brick_wall", chaosRL("brown_terracotta_bricks"));
		wallSideTall("brown_terracotta_brick_wall", chaosRL("brown_terracotta_bricks"));
		wallPost("cyan_terracotta_brick_wall", chaosRL("cyan_terracotta_bricks"));
		wallInventory("cyan_terracotta_brick_wall_inventory", chaosRL("cyan_terracotta_bricks"));
		wallSide("cyan_terracotta_brick_wall", chaosRL("cyan_terracotta_bricks"));
		wallSideTall("cyan_terracotta_brick_wall", chaosRL("cyan_terracotta_bricks"));
		wallPost("gray_terracotta_brick_wall", chaosRL("gray_terracotta_bricks"));
		wallInventory("gray_terracotta_brick_wall_inventory", chaosRL("gray_terracotta_bricks"));
		wallSide("gray_terracotta_brick_wall", chaosRL("gray_terracotta_bricks"));
		wallSideTall("gray_terracotta_brick_wall", chaosRL("gray_terracotta_bricks"));
		wallPost("green_terracotta_brick_wall", chaosRL("green_terracotta_bricks"));
		wallInventory("green_terracotta_brick_wall_inventory", chaosRL("green_terracotta_bricks"));
		wallSide("green_terracotta_brick_wall", chaosRL("green_terracotta_bricks"));
		wallSideTall("green_terracotta_brick_wall", chaosRL("green_terracotta_bricks"));
		wallPost("light_blue_terracotta_brick_wall", chaosRL("light_blue_terracotta_bricks"));
		wallInventory("light_blue_terracotta_brick_wall_inventory", chaosRL("light_blue_terracotta_bricks"));
		wallSide("light_blue_terracotta_brick_wall", chaosRL("light_blue_terracotta_bricks"));
		wallSideTall("light_blue_terracotta_brick_wall", chaosRL("light_blue_terracotta_bricks"));
		wallPost("light_gray_terracotta_brick_wall", chaosRL("light_gray_terracotta_bricks"));
		wallInventory("light_gray_terracotta_brick_wall_inventory", chaosRL("light_gray_terracotta_bricks"));
		wallSide("light_gray_terracotta_brick_wall", chaosRL("light_gray_terracotta_bricks"));
		wallSideTall("light_gray_terracotta_brick_wall", chaosRL("light_gray_terracotta_bricks"));
		wallPost("lime_terracotta_brick_wall", chaosRL("lime_terracotta_bricks"));
		wallInventory("lime_terracotta_brick_wall_inventory", chaosRL("lime_terracotta_bricks"));
		wallSide("lime_terracotta_brick_wall", chaosRL("lime_terracotta_bricks"));
		wallSideTall("lime_terracotta_brick_wall", chaosRL("lime_terracotta_bricks"));
		wallPost("magenta_terracotta_brick_wall", chaosRL("magenta_terracotta_bricks"));
		wallInventory("magenta_terracotta_brick_wall_inventory", chaosRL("magenta_terracotta_bricks"));
		wallSide("magenta_terracotta_brick_wall", chaosRL("magenta_terracotta_bricks"));
		wallSideTall("magenta_terracotta_brick_wall", chaosRL("magenta_terracotta_bricks"));
		wallPost("orange_terracotta_brick_wall", chaosRL("orange_terracotta_bricks"));
		wallInventory("orange_terracotta_brick_wall_inventory", chaosRL("orange_terracotta_bricks"));
		wallSide("orange_terracotta_brick_wall", chaosRL("orange_terracotta_bricks"));
		wallSideTall("orange_terracotta_brick_wall", chaosRL("orange_terracotta_bricks"));
		wallPost("pink_terracotta_brick_wall", chaosRL("pink_terracotta_bricks"));
		wallInventory("pink_terracotta_brick_wall_inventory", chaosRL("pink_terracotta_bricks"));
		wallSide("pink_terracotta_brick_wall", chaosRL("pink_terracotta_bricks"));
		wallSideTall("pink_terracotta_brick_wall", chaosRL("pink_terracotta_bricks"));
		wallPost("purple_terracotta_brick_wall", chaosRL("purple_terracotta_bricks"));
		wallInventory("purple_terracotta_brick_wall_inventory", chaosRL("purple_terracotta_bricks"));
		wallSide("purple_terracotta_brick_wall", chaosRL("purple_terracotta_bricks"));
		wallSideTall("purple_terracotta_brick_wall", chaosRL("purple_terracotta_bricks"));
		wallPost("red_terracotta_brick_wall", chaosRL("red_terracotta_bricks"));
		wallInventory("red_terracotta_brick_wall_inventory", chaosRL("red_terracotta_bricks"));
		wallSide("red_terracotta_brick_wall", chaosRL("red_terracotta_bricks"));
		wallSideTall("red_terracotta_brick_wall", chaosRL("red_terracotta_bricks"));
		wallPost("terracotta_brick_wall", chaosRL("terracotta_bricks"));
		wallInventory("terracotta_brick_wall_inventory", chaosRL("terracotta_bricks"));
		wallSide("terracotta_brick_wall", chaosRL("terracotta_bricks"));
		wallSideTall("terracotta_brick_wall", chaosRL("terracotta_bricks"));
		wallPost("white_terracotta_brick_wall", chaosRL("white_terracotta_bricks"));
		wallInventory("white_terracotta_brick_wall_inventory", chaosRL("white_terracotta_bricks"));
		wallSide("white_terracotta_brick_wall", chaosRL("white_terracotta_bricks"));
		wallSideTall("white_terracotta_brick_wall", chaosRL("white_terracotta_bricks"));
		wallPost("yellow_terracotta_brick_wall", chaosRL("yellow_terracotta_bricks"));
		wallInventory("yellow_terracotta_brick_wall_inventory", chaosRL("yellow_terracotta_bricks"));
		wallSide("yellow_terracotta_brick_wall", chaosRL("yellow_terracotta_bricks"));
		wallSideTall("yellow_terracotta_brick_wall", chaosRL("yellow_terracotta_bricks"));

		cubeAll("cracked_black_terracotta_bricks", chaosRL("cracked_black_terracotta_bricks"));
		cubeAll("cracked_blue_terracotta_bricks", chaosRL("cracked_blue_terracotta_bricks"));
		cubeAll("cracked_brown_terracotta_bricks", chaosRL("cracked_brown_terracotta_bricks"));
		cubeAll("cracked_cyan_terracotta_bricks", chaosRL("cracked_cyan_terracotta_bricks"));
		cubeAll("cracked_gray_terracotta_bricks", chaosRL("cracked_gray_terracotta_bricks"));
		cubeAll("cracked_green_terracotta_bricks", chaosRL("cracked_green_terracotta_bricks"));
		cubeAll("cracked_light_blue_terracotta_bricks", chaosRL("cracked_light_blue_terracotta_bricks"));
		cubeAll("cracked_light_gray_terracotta_bricks", chaosRL("cracked_light_gray_terracotta_bricks"));
		cubeAll("cracked_lime_terracotta_bricks", chaosRL("cracked_lime_terracotta_bricks"));
		cubeAll("cracked_magenta_terracotta_bricks", chaosRL("cracked_magenta_terracotta_bricks"));
		cubeAll("cracked_orange_terracotta_bricks", chaosRL("cracked_orange_terracotta_bricks"));
		cubeAll("cracked_pink_terracotta_bricks", chaosRL("cracked_pink_terracotta_bricks"));
		cubeAll("cracked_purple_terracotta_bricks", chaosRL("cracked_purple_terracotta_bricks"));
		cubeAll("cracked_red_terracotta_bricks", chaosRL("cracked_red_terracotta_bricks"));
		cubeAll("cracked_terracotta_bricks", chaosRL("cracked_terracotta_bricks"));
		cubeAll("cracked_white_terracotta_bricks", chaosRL("cracked_white_terracotta_bricks"));
		cubeAll("cracked_yellow_terracotta_bricks", chaosRL("cracked_yellow_terracotta_bricks"));
		slab("cracked_black_terracotta_brick_slab", chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		slabTop("cracked_black_terracotta_brick_slab", chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		slab("cracked_blue_terracotta_brick_slab", chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		slabTop("cracked_blue_terracotta_brick_slab", chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		slab("cracked_brown_terracotta_brick_slab", chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		slabTop("cracked_brown_terracotta_brick_slab", chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		slab("cracked_cyan_terracotta_brick_slab", chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		slabTop("cracked_cyan_terracotta_brick_slab", chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		slab("cracked_gray_terracotta_brick_slab", chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		slabTop("cracked_gray_terracotta_brick_slab", chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		slab("cracked_green_terracotta_brick_slab", chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		slabTop("cracked_green_terracotta_brick_slab", chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		slab("cracked_light_blue_terracotta_brick_slab", chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		slabTop("cracked_light_blue_terracotta_brick_slab", chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		slab("cracked_light_gray_terracotta_brick_slab", chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		slabTop("cracked_light_gray_terracotta_brick_slab", chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		slab("cracked_lime_terracotta_brick_slab", chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		slabTop("cracked_lime_terracotta_brick_slab", chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		slab("cracked_magenta_terracotta_brick_slab", chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		slabTop("cracked_magenta_terracotta_brick_slab", chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		slab("cracked_orange_terracotta_brick_slab", chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		slabTop("cracked_orange_terracotta_brick_slab", chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		slab("cracked_pink_terracotta_brick_slab", chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		slabTop("cracked_pink_terracotta_brick_slab", chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		slab("cracked_purple_terracotta_brick_slab", chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		slabTop("cracked_purple_terracotta_brick_slab", chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		slab("cracked_red_terracotta_brick_slab", chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		slabTop("cracked_red_terracotta_brick_slab", chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		slab("cracked_terracotta_brick_slab", chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		slabTop("cracked_terracotta_brick_slab", chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		slab("cracked_white_terracotta_brick_slab", chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		slabTop("cracked_white_terracotta_brick_slab", chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		slab("cracked_yellow_terracotta_brick_slab", chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		slabTop("cracked_yellow_terracotta_brick_slab", chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		stairs("cracked_black_terracotta_brick_stairs", chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		stairsInner("cracked_black_terracotta_brick_stairs", chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		stairsOuter("cracked_black_terracotta_brick_stairs", chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		stairs("cracked_blue_terracotta_brick_stairs", chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		stairsInner("cracked_blue_terracotta_brick_stairs", chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		stairsOuter("cracked_blue_terracotta_brick_stairs", chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		stairs("cracked_brown_terracotta_brick_stairs", chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		stairsInner("cracked_brown_terracotta_brick_stairs", chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		stairsOuter("cracked_brown_terracotta_brick_stairs", chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		stairs("cracked_cyan_terracotta_brick_stairs", chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		stairsInner("cracked_cyan_terracotta_brick_stairs", chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		stairsOuter("cracked_cyan_terracotta_brick_stairs", chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		stairs("cracked_gray_terracotta_brick_stairs", chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		stairsInner("cracked_gray_terracotta_brick_stairs", chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		stairsOuter("cracked_gray_terracotta_brick_stairs", chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		stairs("cracked_green_terracotta_brick_stairs", chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		stairsInner("cracked_green_terracotta_brick_stairs", chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		stairsOuter("cracked_green_terracotta_brick_stairs", chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		stairs("cracked_light_blue_terracotta_brick_stairs", chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		stairsInner("cracked_light_blue_terracotta_brick_stairs", chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		stairsOuter("cracked_light_blue_terracotta_brick_stairs", chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		stairs("cracked_light_gray_terracotta_brick_stairs", chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		stairsInner("cracked_light_gray_terracotta_brick_stairs", chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		stairsOuter("cracked_light_gray_terracotta_brick_stairs", chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		stairs("cracked_lime_terracotta_brick_stairs", chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		stairsInner("cracked_lime_terracotta_brick_stairs", chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		stairsOuter("cracked_lime_terracotta_brick_stairs", chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		stairs("cracked_magenta_terracotta_brick_stairs", chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		stairsInner("cracked_magenta_terracotta_brick_stairs", chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		stairsOuter("cracked_magenta_terracotta_brick_stairs", chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		stairs("cracked_orange_terracotta_brick_stairs", chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		stairsInner("cracked_orange_terracotta_brick_stairs", chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		stairsOuter("cracked_orange_terracotta_brick_stairs", chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		stairs("cracked_pink_terracotta_brick_stairs", chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		stairsInner("cracked_pink_terracotta_brick_stairs", chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		stairsOuter("cracked_pink_terracotta_brick_stairs", chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		stairs("cracked_purple_terracotta_brick_stairs", chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		stairsInner("cracked_purple_terracotta_brick_stairs", chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		stairsOuter("cracked_purple_terracotta_brick_stairs", chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		stairs("cracked_red_terracotta_brick_stairs", chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		stairsInner("cracked_red_terracotta_brick_stairs", chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		stairsOuter("cracked_red_terracotta_brick_stairs", chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		stairs("cracked_terracotta_brick_stairs", chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		stairsInner("cracked_terracotta_brick_stairs", chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		stairsOuter("cracked_terracotta_brick_stairs", chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		stairs("cracked_white_terracotta_brick_stairs", chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		stairsInner("cracked_white_terracotta_brick_stairs", chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		stairsOuter("cracked_white_terracotta_brick_stairs", chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		stairs("cracked_yellow_terracotta_brick_stairs", chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		stairsInner("cracked_yellow_terracotta_brick_stairs", chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		stairsOuter("cracked_yellow_terracotta_brick_stairs", chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		wallPost("cracked_black_terracotta_brick_wall", chaosRL("cracked_black_terracotta_bricks"));
		wallInventory("cracked_black_terracotta_brick_wall_inventory", chaosRL("cracked_black_terracotta_bricks"));
		wallSide("cracked_black_terracotta_brick_wall", chaosRL("cracked_black_terracotta_bricks"));
		wallSideTall("cracked_black_terracotta_brick_wall", chaosRL("cracked_black_terracotta_bricks"));
		wallPost("cracked_blue_terracotta_brick_wall", chaosRL("cracked_blue_terracotta_bricks"));
		wallInventory("cracked_blue_terracotta_brick_wall_inventory", chaosRL("cracked_blue_terracotta_bricks"));
		wallSide("cracked_blue_terracotta_brick_wall", chaosRL("cracked_blue_terracotta_bricks"));
		wallSideTall("cracked_blue_terracotta_brick_wall", chaosRL("cracked_blue_terracotta_bricks"));
		wallPost("cracked_brown_terracotta_brick_wall", chaosRL("cracked_brown_terracotta_bricks"));
		wallInventory("cracked_brown_terracotta_brick_wall_inventory", chaosRL("cracked_brown_terracotta_bricks"));
		wallSide("cracked_brown_terracotta_brick_wall", chaosRL("cracked_brown_terracotta_bricks"));
		wallSideTall("cracked_brown_terracotta_brick_wall", chaosRL("cracked_brown_terracotta_bricks"));
		wallPost("cracked_cyan_terracotta_brick_wall", chaosRL("cracked_cyan_terracotta_bricks"));
		wallInventory("cracked_cyan_terracotta_brick_wall_inventory", chaosRL("cracked_cyan_terracotta_bricks"));
		wallSide("cracked_cyan_terracotta_brick_wall", chaosRL("cracked_cyan_terracotta_bricks"));
		wallSideTall("cracked_cyan_terracotta_brick_wall", chaosRL("cracked_cyan_terracotta_bricks"));
		wallPost("cracked_gray_terracotta_brick_wall", chaosRL("cracked_gray_terracotta_bricks"));
		wallInventory("cracked_gray_terracotta_brick_wall_inventory", chaosRL("cracked_gray_terracotta_bricks"));
		wallSide("cracked_gray_terracotta_brick_wall", chaosRL("cracked_gray_terracotta_bricks"));
		wallSideTall("cracked_gray_terracotta_brick_wall", chaosRL("cracked_gray_terracotta_bricks"));
		wallPost("cracked_green_terracotta_brick_wall", chaosRL("cracked_green_terracotta_bricks"));
		wallInventory("cracked_green_terracotta_brick_wall_inventory", chaosRL("cracked_green_terracotta_bricks"));
		wallSide("cracked_green_terracotta_brick_wall", chaosRL("cracked_green_terracotta_bricks"));
		wallSideTall("cracked_green_terracotta_brick_wall", chaosRL("cracked_green_terracotta_bricks"));
		wallPost("cracked_light_blue_terracotta_brick_wall", chaosRL("cracked_light_blue_terracotta_bricks"));
		wallInventory("cracked_light_blue_terracotta_brick_wall_inventory", chaosRL("cracked_light_blue_terracotta_bricks"));
		wallSide("cracked_light_blue_terracotta_brick_wall", chaosRL("cracked_light_blue_terracotta_bricks"));
		wallSideTall("cracked_light_blue_terracotta_brick_wall", chaosRL("cracked_light_blue_terracotta_bricks"));
		wallPost("cracked_light_gray_terracotta_brick_wall", chaosRL("cracked_light_gray_terracotta_bricks"));
		wallInventory("cracked_light_gray_terracotta_brick_wall_inventory", chaosRL("cracked_light_gray_terracotta_bricks"));
		wallSide("cracked_light_gray_terracotta_brick_wall", chaosRL("cracked_light_gray_terracotta_bricks"));
		wallSideTall("cracked_light_gray_terracotta_brick_wall", chaosRL("cracked_light_gray_terracotta_bricks"));
		wallPost("cracked_lime_terracotta_brick_wall", chaosRL("cracked_lime_terracotta_bricks"));
		wallInventory("cracked_lime_terracotta_brick_wall_inventory", chaosRL("cracked_lime_terracotta_bricks"));
		wallSide("cracked_lime_terracotta_brick_wall", chaosRL("cracked_lime_terracotta_bricks"));
		wallSideTall("cracked_lime_terracotta_brick_wall", chaosRL("cracked_lime_terracotta_bricks"));
		wallPost("cracked_magenta_terracotta_brick_wall", chaosRL("cracked_magenta_terracotta_bricks"));
		wallInventory("cracked_magenta_terracotta_brick_wall_inventory", chaosRL("cracked_magenta_terracotta_bricks"));
		wallSide("cracked_magenta_terracotta_brick_wall", chaosRL("cracked_magenta_terracotta_bricks"));
		wallSideTall("cracked_magenta_terracotta_brick_wall", chaosRL("cracked_magenta_terracotta_bricks"));
		wallPost("cracked_orange_terracotta_brick_wall", chaosRL("cracked_orange_terracotta_bricks"));
		wallInventory("cracked_orange_terracotta_brick_wall_inventory", chaosRL("cracked_orange_terracotta_bricks"));
		wallSide("cracked_orange_terracotta_brick_wall", chaosRL("cracked_orange_terracotta_bricks"));
		wallSideTall("cracked_orange_terracotta_brick_wall", chaosRL("cracked_orange_terracotta_bricks"));
		wallPost("cracked_pink_terracotta_brick_wall", chaosRL("cracked_pink_terracotta_bricks"));
		wallInventory("cracked_pink_terracotta_brick_wall_inventory", chaosRL("cracked_pink_terracotta_bricks"));
		wallSide("cracked_pink_terracotta_brick_wall", chaosRL("cracked_pink_terracotta_bricks"));
		wallSideTall("cracked_pink_terracotta_brick_wall", chaosRL("cracked_pink_terracotta_bricks"));
		wallPost("cracked_purple_terracotta_brick_wall", chaosRL("cracked_purple_terracotta_bricks"));
		wallInventory("cracked_purple_terracotta_brick_wall_inventory", chaosRL("cracked_purple_terracotta_bricks"));
		wallSide("cracked_purple_terracotta_brick_wall", chaosRL("cracked_purple_terracotta_bricks"));
		wallSideTall("cracked_purple_terracotta_brick_wall", chaosRL("cracked_purple_terracotta_bricks"));
		wallPost("cracked_red_terracotta_brick_wall", chaosRL("cracked_red_terracotta_bricks"));
		wallInventory("cracked_red_terracotta_brick_wall_inventory", chaosRL("cracked_red_terracotta_bricks"));
		wallSide("cracked_red_terracotta_brick_wall", chaosRL("cracked_red_terracotta_bricks"));
		wallSideTall("cracked_red_terracotta_brick_wall", chaosRL("cracked_red_terracotta_bricks"));
		wallPost("cracked_terracotta_brick_wall", chaosRL("cracked_terracotta_bricks"));
		wallInventory("cracked_terracotta_brick_wall_inventory", chaosRL("cracked_terracotta_bricks"));
		wallSide("cracked_terracotta_brick_wall", chaosRL("cracked_terracotta_bricks"));
		wallSideTall("cracked_terracotta_brick_wall", chaosRL("cracked_terracotta_bricks"));
		wallPost("cracked_white_terracotta_brick_wall", chaosRL("cracked_white_terracotta_bricks"));
		wallInventory("cracked_white_terracotta_brick_wall_inventory", chaosRL("cracked_white_terracotta_bricks"));
		wallSide("cracked_white_terracotta_brick_wall", chaosRL("cracked_white_terracotta_bricks"));
		wallSideTall("cracked_white_terracotta_brick_wall", chaosRL("cracked_white_terracotta_bricks"));
		wallPost("cracked_yellow_terracotta_brick_wall", chaosRL("cracked_yellow_terracotta_bricks"));
		wallInventory("cracked_yellow_terracotta_brick_wall_inventory", chaosRL("cracked_yellow_terracotta_bricks"));
		wallSide("cracked_yellow_terracotta_brick_wall", chaosRL("cracked_yellow_terracotta_bricks"));
		wallSideTall("cracked_yellow_terracotta_brick_wall", chaosRL("cracked_yellow_terracotta_bricks"));

		cubeColumn("flower_stem", chaosRL("flower_stem"), chaosRL("flower_stem_top"));
		cubeColumnHorizontal("flower_stem", chaosRL("flower_stem"), chaosRL("flower_stem_top"));
		cubeAll("navy_blue_petal_block", chaosRL("navy_blue_petal_block"));
		cubeAll("blood_red_petal_block", chaosRL("blood_red_petal_block"));
		cubeAll("bright_pink_petal_block", chaosRL("bright_pink_petal_block"));

		cubeAll("moth_scale_block", chaosRL("moth_scale_block"));
		cubeAll("water_dragon_scale_block", chaosRL("water_dragon_scale_block"));
		cubeAll("ender_dragon_scale_block", chaosRL("ender_dragon_scale_block"));
		cubeAll("nightmare_scale_block", chaosRL("nightmare_scale_block"));
		cubeAll("mobzilla_scale_block", chaosRL("mobzilla_scale_block"));
		cubeAll("royal_guardian_scale_block", chaosRL("royal_guardian_scale_block"));
		cubeAll("queen_scale_block", chaosRL("queen_scale_block"));
		cubeAll("basilisk_scale_block", chaosRL("basilisk_scale_block"));
		cubeAll("emperor_scorpion_scale_block", chaosRL("emperor_scorpion_scale_block"));

		cubeColumn("apple_log", chaosRL("apple_log"), chaosRL("apple_log_top"));
		cubeColumnHorizontal("apple_log", chaosRL("apple_log"), chaosRL("apple_log_top"));
		cubeColumn("apple_wood", chaosRL("apple_log"), chaosRL("apple_log"));
		cubeColumnHorizontal("apple_wood", chaosRL("apple_log"), chaosRL("apple_log"));
		cubeColumn("stripped_apple_log", chaosRL("stripped_apple_log"), chaosRL("stripped_apple_log_top"));
		cubeColumnHorizontal("stripped_apple_log", chaosRL("stripped_apple_log"), chaosRL("stripped_apple_log_top"));
		cubeColumn("stripped_apple_wood", chaosRL("stripped_apple_log"), chaosRL("stripped_apple_log"));
		cubeColumnHorizontal("stripped_apple_wood", chaosRL("stripped_apple_log"), chaosRL("stripped_apple_log"));
		cubeColumn("cherry_log", chaosRL("cherry_log"), chaosRL("cherry_log_top"));
		cubeColumnHorizontal("cherry_log", chaosRL("cherry_log"), chaosRL("cherry_log_top"));
		cubeColumn("cherry_wood", chaosRL("cherry_log"), chaosRL("cherry_log"));
		cubeColumnHorizontal("cherry_wood", chaosRL("cherry_log"), chaosRL("cherry_log"));
		cubeColumn("stripped_cherry_log", chaosRL("stripped_cherry_log"), chaosRL("stripped_cherry_log_top"));
		cubeColumnHorizontal("stripped_cherry_log", chaosRL("stripped_cherry_log"), chaosRL("stripped_cherry_log_top"));
		cubeColumn("stripped_cherry_wood", chaosRL("stripped_cherry_log"), chaosRL("stripped_cherry_log"));
		cubeColumnHorizontal("stripped_cherry_wood", chaosRL("stripped_cherry_log"), chaosRL("stripped_cherry_log"));
		cubeColumn("ginkgo_log", chaosRL("ginkgo_log"), chaosRL("ginkgo_log_top"));
		cubeColumnHorizontal("ginkgo_log", chaosRL("ginkgo_log"), chaosRL("ginkgo_log_top"));
		cubeColumn("ginkgo_wood", chaosRL("ginkgo_log"), chaosRL("ginkgo_log"));
		cubeColumnHorizontal("ginkgo_wood", chaosRL("ginkgo_log"), chaosRL("ginkgo_log"));
		cubeColumn("stripped_ginkgo_log", chaosRL("stripped_ginkgo_log"), chaosRL("stripped_ginkgo_log_top"));
		cubeColumnHorizontal("stripped_ginkgo_log", chaosRL("stripped_ginkgo_log"), chaosRL("stripped_ginkgo_log_top"));
		cubeColumn("stripped_ginkgo_wood", chaosRL("stripped_ginkgo_log"), chaosRL("stripped_ginkgo_log"));
		cubeColumnHorizontal("stripped_ginkgo_wood", chaosRL("stripped_ginkgo_log"), chaosRL("stripped_ginkgo_log"));
		cubeColumn("mesozoic_log", chaosRL("mesozoic_log"), chaosRL("mesozoic_log_top"));
		cubeColumnHorizontal("mesozoic_log", chaosRL("mesozoic_log"), chaosRL("mesozoic_log_top"));
		cubeColumn("mesozoic_wood", chaosRL("mesozoic_log"), chaosRL("mesozoic_log"));
		cubeColumnHorizontal("mesozoic_wood", chaosRL("mesozoic_log"), chaosRL("mesozoic_log"));
		cubeColumn("stripped_mesozoic_log", chaosRL("stripped_mesozoic_log"), chaosRL("mesozoic_log_top"));
		cubeColumnHorizontal("stripped_mesozoic_log", chaosRL("stripped_mesozoic_log"), chaosRL("mesozoic_log_top"));
		cubeColumn("stripped_mesozoic_wood", chaosRL("stripped_mesozoic_log"), chaosRL("stripped_mesozoic_log"));
		cubeColumnHorizontal("stripped_mesozoic_wood", chaosRL("stripped_mesozoic_log"), chaosRL("stripped_mesozoic_log"));
		cubeColumn("densewood_log", chaosRL("densewood_log"), chaosRL("densewood_log_top"));
		cubeColumnHorizontal("densewood_log", chaosRL("densewood_log"), chaosRL("densewood_log_top"));
		cubeColumn("densewood", chaosRL("densewood_log"), chaosRL("densewood_log"));
		cubeColumnHorizontal("densewood", chaosRL("densewood_log"), chaosRL("densewood_log"));
		cubeColumn("stripped_densewood_log", chaosRL("stripped_densewood_log"), chaosRL("densewood_log_top"));
		cubeColumnHorizontal("stripped_densewood_log", chaosRL("stripped_densewood_log"), chaosRL("densewood_log_top"));
		cubeColumn("stripped_densewood", chaosRL("stripped_densewood_log"), chaosRL("stripped_densewood_log"));
		cubeColumnHorizontal("stripped_densewood", chaosRL("stripped_densewood_log"), chaosRL("stripped_densewood_log"));
		cubeColumn("peach_log", chaosRL("peach_log"), chaosRL("peach_log_top"));
		cubeColumnHorizontal("peach_log", chaosRL("peach_log"), chaosRL("peach_log_top"));
		cubeColumn("peach_wood", chaosRL("peach_log"), chaosRL("peach_log"));
		cubeColumnHorizontal("peach_wood", chaosRL("peach_log"), chaosRL("peach_log"));
		cubeColumn("stripped_peach_log", chaosRL("stripped_peach_log"), chaosRL("stripped_peach_log_top"));
		cubeColumnHorizontal("stripped_peach_log", chaosRL("stripped_peach_log"), chaosRL("stripped_peach_log_top"));
		cubeColumn("stripped_peach_wood", chaosRL("stripped_peach_log"), chaosRL("stripped_peach_log"));
		cubeColumnHorizontal("stripped_peach_wood", chaosRL("stripped_peach_log"), chaosRL("stripped_peach_log"));
		cubeColumn("duplication_log", chaosRL("duplication_log"), chaosRL("duplication_log_top"));
		cubeColumnHorizontal("duplication_log", chaosRL("duplication_log"), chaosRL("duplication_log_top"));
		cubeColumn("duplication_wood", chaosRL("duplication_log"), chaosRL("duplication_log"));
		cubeColumnHorizontal("duplication_wood", chaosRL("duplication_log"), chaosRL("duplication_log"));
		cubeColumn("stripped_duplication_log", chaosRL("stripped_duplication_log"), chaosRL("stripped_duplication_log_top"));
		cubeColumnHorizontal("stripped_duplication_log", chaosRL("stripped_duplication_log"), chaosRL("stripped_duplication_log_top"));
		cubeColumn("stripped_duplication_wood", chaosRL("stripped_duplication_log"), chaosRL("stripped_duplication_log"));
		cubeColumnHorizontal("stripped_duplication_wood", chaosRL("stripped_duplication_log"), chaosRL("stripped_duplication_log"));
		cubeColumn("dead_duplication_log", chaosRL("dead_duplication_log"), chaosRL("dead_duplication_log_top"));
		cubeColumnHorizontal("dead_duplication_log", chaosRL("dead_duplication_log"), chaosRL("dead_duplication_log_top"));
		cubeColumn("dead_duplication_wood", chaosRL("dead_duplication_log"), chaosRL("dead_duplication_log"));
		cubeColumnHorizontal("dead_duplication_wood", chaosRL("dead_duplication_log"), chaosRL("dead_duplication_log"));
		cubeColumn("stripped_dead_duplication_log", chaosRL("stripped_dead_duplication_log"), chaosRL("stripped_dead_duplication_log_top"));
		cubeColumnHorizontal("stripped_dead_duplication_log", chaosRL("stripped_dead_duplication_log"), chaosRL("stripped_dead_duplication_log_top"));
		cubeColumn("stripped_dead_duplication_wood", chaosRL("stripped_dead_duplication_log"), chaosRL("stripped_dead_duplication_log"));
		cubeColumnHorizontal("stripped_dead_duplication_wood", chaosRL("stripped_dead_duplication_log"), chaosRL("stripped_dead_duplication_log"));
		cubeColumn("skywood_log", chaosRL("skywood_log"), chaosRL("skywood_log_top"));
		cubeColumnHorizontal("skywood_log", chaosRL("skywood_log"), chaosRL("skywood_log_top"));
		cubeColumn("skywood", chaosRL("skywood_log"), chaosRL("skywood_log"));
		cubeColumnHorizontal("skywood", chaosRL("skywood_log"), chaosRL("skywood_log"));
		cubeColumn("stripped_skywood_log", chaosRL("stripped_skywood_log"), chaosRL("stripped_skywood_log_top"));
		cubeColumnHorizontal("stripped_skywood_log", chaosRL("stripped_skywood_log"), chaosRL("stripped_skywood_log_top"));
		cubeColumn("stripped_skywood", chaosRL("stripped_skywood_log"), chaosRL("stripped_skywood_log"));
		cubeColumnHorizontal("stripped_skywood", chaosRL("stripped_skywood_log"), chaosRL("stripped_skywood_log"));
		cubeColumn("crystalwood_log", chaosRL("crystalwood_log"), chaosRL("crystalwood_log_top"));
		cubeColumnHorizontal("crystalwood_log", chaosRL("crystalwood_log"), chaosRL("crystalwood_log_top"));
		cubeColumn("crystalwood", chaosRL("crystalwood_log"), chaosRL("crystalwood_log"));
		cubeColumnHorizontal("crystalwood", chaosRL("crystalwood_log"), chaosRL("crystalwood_log"));
		cubeAll("apple_planks", chaosRL("apple_planks"));
		cubeAll("apple_leaves", chaosRL("apple_leaves"));
		leafCarpet("apple_leaf_carpet", chaosRL("apple_leaves"));
		leafCarpetInventory("apple_leaf_carpet_inventory", chaosRL("apple_leaves"));
		cubeAll("apple_leaves_ripe", chaosRL("apple_leaves_ripe"));
		cross("apple_sapling", chaosRL("apple_sapling"));
		cubeAll("cherry_planks", chaosRL("cherry_planks"));
		cubeAll("cherry_leaves", chaosRL("cherry_leaves"));
		leafCarpet("cherry_leaf_carpet", chaosRL("cherry_leaves"));
		leafCarpetInventory("cherry_leaf_carpet_inventory", chaosRL("cherry_leaves"));
		cubeAll("cherry_leaves_ripe", chaosRL("cherry_leaves_ripe"));
		cross("cherry_sapling", chaosRL("cherry_sapling"));
		cubeAll("duplication_planks", chaosRL("duplication_planks"));
		cubeAll("duplication_leaves", chaosRL("duplication_leaves"));
		leafCarpet("duplication_leaf_carpet", chaosRL("duplication_leaves"));
		leafCarpetInventory("duplication_leaf_carpet_inventory", chaosRL("duplication_leaves"));
		cross("ginkgo_sapling", chaosRL("ginkgo_sapling"));
		cubeAll("ginkgo_planks", chaosRL("ginkgo_planks"));
		cubeAll("ginkgo_leaves", chaosRL("ginkgo_leaves"));
		leafCarpet("ginkgo_leaf_carpet", chaosRL("ginkgo_leaves"));
		leafCarpetInventory("ginkgo_leaf_carpet_inventory", chaosRL("ginkgo_leaves"));
		cross("mesozoic_sapling", chaosRL("mesozoic_sapling"));
		cubeAll("mesozoic_planks", chaosRL("mesozoic_planks"));
		cubeAll("mesozoic_leaves", chaosRL("mesozoic_leaves"));
		leafCarpet("mesozoic_leaf_carpet", chaosRL("mesozoic_leaves"));
		leafCarpetInventory("mesozoic_leaf_carpet_inventory", chaosRL("mesozoic_leaves"));
		cross("densewood_sapling", chaosRL("densewood_sapling"));
		cubeAll("densewood_planks", chaosRL("densewood_planks"));
		cubeAll("densewood_leaves", chaosRL("densewood_leaves"));
		leafCarpet("densewood_leaf_carpet", chaosRL("densewood_leaves"));
		leafCarpetInventory("densewood_leaf_carpet_inventory", chaosRL("densewood_leaves"));
		cubeAll("peach_planks", chaosRL("peach_planks"));
		cubeAll("peach_leaves", chaosRL("peach_leaves"));
		leafCarpet("peach_leaf_carpet", chaosRL("peach_leaves"));
		leafCarpetInventory("peach_leaf_carpet_inventory", chaosRL("peach_leaves"));
		cubeAll("peach_leaves_ripe", chaosRL("peach_leaves_ripe"));
		cross("peach_sapling", chaosRL("peach_sapling"));
		cubeAll("skywood_planks", chaosRL("skywood_planks"));
		cubeAll("skywood_leaves", chaosRL("skywood_leaves"));
		leafCarpet("skywood_leaf_carpet", chaosRL("skywood_leaves"));
		leafCarpetInventory("skywood_leaf_carpet_inventory", chaosRL("skywood_leaves"));
		cubeAll("crystalwood_planks", chaosRL("crystalwood_planks"));
		cubeAll("red_crystal_leaves", chaosRL("red_crystal_leaves"));
		cubeAll("green_crystal_leaves", chaosRL("green_crystal_leaves"));
		cubeAll("yellow_crystal_leaves", chaosRL("yellow_crystal_leaves"));
		cubeAll("pink_crystal_leaves", chaosRL("pink_crystal_leaves"));
		cubeAll("blue_crystal_leaves", chaosRL("blue_crystal_leaves"));
		cubeAll("orange_crystal_leaves", chaosRL("orange_crystal_leaves"));
		leafCarpet("red_crystal_leaf_carpet", chaosRL("red_crystal_leaves"));
		leafCarpetInventory("red_crystal_leaf_carpet_inventory", chaosRL("red_crystal_leaves"));
		leafCarpet("green_crystal_leaf_carpet", chaosRL("green_crystal_leaves"));
		leafCarpetInventory("green_crystal_leaf_carpet_inventory", chaosRL("green_crystal_leaves"));
		leafCarpet("yellow_crystal_leaf_carpet", chaosRL("yellow_crystal_leaves"));
		leafCarpetInventory("yellow_crystal_leaf_carpet_inventory", chaosRL("yellow_crystal_leaves"));
		leafCarpet("pink_crystal_leaf_carpet", chaosRL("pink_crystal_leaves"));
		leafCarpetInventory("pink_crystal_leaf_carpet_inventory", chaosRL("pink_crystal_leaves"));
		leafCarpet("blue_crystal_leaf_carpet", chaosRL("blue_crystal_leaves"));
		leafCarpetInventory("blue_crystal_leaf_carpet_inventory", chaosRL("blue_crystal_leaves"));
		leafCarpet("orange_crystal_leaves", chaosRL("orange_crystal_leaves"));
		leafCarpetInventory("orange_crystal_leaf_carpet_inventory", chaosRL("orange_crystal_leaves"));
		cross("red_crystal_sapling", chaosRL("red_crystal_sapling"));
		cross("green_crystal_sapling", chaosRL("green_crystal_sapling"));
		cross("yellow_crystal_sapling", chaosRL("yellow_crystal_sapling"));
		cross("pink_crystal_sapling", chaosRL("pink_crystal_sapling"));
		cross("blue_crystal_sapling", chaosRL("blue_crystal_sapling"));
		cross("orange_crystal_sapling", chaosRL("orange_crystal_sapling"));

		cubeAll("sky_moss_block", chaosRL("sky_moss"));
		leafCarpet("sky_moss_carpet", chaosRL("sky_moss"));
		leafCarpetInventory("sky_moss_carpet_inventory", chaosRL("sky_moss"));

		leafCarpet("oak_leaf_carpet", mcRL("oak_leaves"));
		leafCarpetInventory("oak_leaf_carpet_inventory", mcRL("oak_leaves"));
		leafCarpet("spruce_leaf_carpet", mcRL("spruce_leaves"));
		leafCarpetInventory("spruce_leaf_carpet_inventory", mcRL("spruce_leaves"));
		leafCarpet("birch_leaf_carpet", mcRL("birch_leaves"));
		leafCarpetInventory("birch_leaf_carpet_inventory", mcRL("birch_leaves"));
		leafCarpet("jungle_leaf_carpet", mcRL("jungle_leaves"));
		leafCarpetInventory("jungle_leaf_carpet_inventory", mcRL("jungle_leaves"));
		leafCarpet("acacia_leaf_carpet", mcRL("acacia_leaves"));
		leafCarpetInventory("acacia_leaf_carpet_inventory", mcRL("acacia_leaves"));
		leafCarpet("dark_oak_leaf_carpet", mcRL("dark_oak_leaves"));
		leafCarpetInventory("dark_oak_leaf_carpet_inventory", mcRL("dark_oak_leaves"));

		stairs("apple_stairs", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		stairsInner("apple_stairs", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		stairsOuter("apple_stairs", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		stairs("cherry_stairs", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		stairsInner("cherry_stairs", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		stairsOuter("cherry_stairs", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		stairs("ginkgo_stairs", chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		stairsInner("ginkgo_stairs", chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		stairsOuter("ginkgo_stairs", chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		stairs("mesozoic_stairs", chaosRL("mesozoic_planks"), chaosRL("mesozoic_planks"), chaosRL("mesozoic_planks"));
		stairsInner("mesozoic_stairs", chaosRL("mesozoic_planks"), chaosRL("mesozoic_planks"), chaosRL("mesozoic_planks"));
		stairsOuter("mesozoic_stairs", chaosRL("mesozoic_planks"), chaosRL("mesozoic_planks"), chaosRL("mesozoic_planks"));
		stairs("densewood_stairs", chaosRL("densewood_planks"), chaosRL("densewood_planks"), chaosRL("densewood_planks"));
		stairsInner("densewood_stairs", chaosRL("densewood_planks"), chaosRL("densewood_planks"), chaosRL("densewood_planks"));
		stairsOuter("densewood_stairs", chaosRL("densewood_planks"), chaosRL("densewood_planks"), chaosRL("densewood_planks"));
		stairs("peach_stairs", chaosRL("peach_planks"), chaosRL("peach_planks"), chaosRL("peach_planks"));
		stairsInner("peach_stairs", chaosRL("peach_planks"), chaosRL("peach_planks"), chaosRL("peach_planks"));
		stairsOuter("peach_stairs", chaosRL("peach_planks"), chaosRL("peach_planks"), chaosRL("peach_planks"));
		stairs("duplication_stairs", chaosRL("duplication_planks"), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		stairsInner("duplication_stairs", chaosRL("duplication_planks"), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		stairsOuter("duplication_stairs", chaosRL("duplication_planks"), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		stairs("skywood_stairs", chaosRL("skywood_planks"), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		stairsInner("skywood_stairs", chaosRL("skywood_planks"), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		stairsOuter("skywood_stairs", chaosRL("skywood_planks"), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		stairs("crystalwood_stairs", chaosRL("crystalwood_planks"), chaosRL("crystalwood_planks"), chaosRL("crystalwood_planks"));
		stairsInner("crystalwood_stairs", chaosRL("crystalwood_planks"), chaosRL("crystalwood_planks"), chaosRL("crystalwood_planks"));
		stairsOuter("crystalwood_stairs", chaosRL("crystalwood_planks"), chaosRL("crystalwood_planks"), chaosRL("crystalwood_planks"));

		slab("apple_slab", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		slabTop("apple_slab", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		slab("cherry_slab", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		slabTop("cherry_slab", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		slab("ginkgo_slab", chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		slabTop("ginkgo_slab", chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		slab("mesozoic_slab", chaosRL("mesozoic_planks"), chaosRL("mesozoic_planks"), chaosRL("mesozoic_planks"));
		slabTop("mesozoic_slab", chaosRL("mesozoic_planks"), chaosRL("mesozoic_planks"), chaosRL("mesozoic_planks"));
		slab("densewood_slab", chaosRL("densewood_planks"), chaosRL("densewood_planks"), chaosRL("densewood_planks"));
		slabTop("densewood_slab", chaosRL("densewood_planks"), chaosRL("densewood_planks"), chaosRL("densewood_planks"));
		slab("peach_slab", chaosRL("peach_planks"), chaosRL("peach_planks"), chaosRL("peach_planks"));
		slabTop("peach_slab", chaosRL("peach_planks"), chaosRL("peach_planks"), chaosRL("peach_planks"));
		slab("duplication_slab", chaosRL("duplication_planks"), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		slabTop("duplication_slab", chaosRL("duplication_planks"), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
		slab("skywood_slab", chaosRL("skywood_planks"), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		slabTop("skywood_slab", chaosRL("skywood_planks"), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
		slab("crystalwood_slab", chaosRL("crystalwood_planks"), chaosRL("crystalwood_planks"), chaosRL("crystalwood_planks"));
		slabTop("crystalwood_slab", chaosRL("crystalwood_planks"), chaosRL("crystalwood_planks"), chaosRL("crystalwood_planks"));

		fenceGate("apple_fence_gate", chaosRL("apple_planks"));
		fenceGateOpen("apple_fence_gate", chaosRL("apple_planks"));
		fenceGateWall("apple_fence_gate", chaosRL("apple_planks"));
		fenceGateWallOpen("apple_fence_gate", chaosRL("apple_planks"));
		fencePost("apple_fence", chaosRL("apple_planks"));
		fenceInventory("apple_fence_inventory", chaosRL("apple_planks"));
		fenceSide("apple_fence", chaosRL("apple_planks"));
		fenceGate("cherry_fence_gate", chaosRL("cherry_planks"));
		fenceGateOpen("cherry_fence_gate", chaosRL("cherry_planks"));
		fenceGateWall("cherry_fence_gate", chaosRL("cherry_planks"));
		fenceGateWallOpen("cherry_fence_gate", chaosRL("cherry_planks"));
		fencePost("cherry_fence", chaosRL("cherry_planks"));
		fenceInventory("cherry_fence_inventory", chaosRL("cherry_planks"));
		fenceSide("cherry_fence", chaosRL("cherry_planks"));
		fenceGate("ginkgo_fence_gate", chaosRL("ginkgo_planks"));
		fenceGateOpen("ginkgo_fence_gate", chaosRL("ginkgo_planks"));
		fenceGateWall("ginkgo_fence_gate", chaosRL("ginkgo_planks"));
		fenceGateWallOpen("ginkgo_fence_gate", chaosRL("ginkgo_planks"));
		fencePost("ginkgo_fence", chaosRL("ginkgo_planks"));
		fenceInventory("ginkgo_fence_inventory", chaosRL("ginkgo_planks"));
		fenceSide("ginkgo_fence", chaosRL("ginkgo_planks"));
		fenceGate("mesozoic_fence_gate", chaosRL("mesozoic_planks"));
		fenceGateOpen("mesozoic_fence_gate", chaosRL("mesozoic_planks"));
		fenceGateWall("mesozoic_fence_gate", chaosRL("mesozoic_planks"));
		fenceGateWallOpen("mesozoic_fence_gate", chaosRL("mesozoic_planks"));
		fencePost("mesozoic_fence", chaosRL("mesozoic_planks"));
		fenceInventory("mesozoic_fence_inventory", chaosRL("mesozoic_planks"));
		fenceSide("mesozoic_fence", chaosRL("mesozoic_planks"));
		fenceGate("densewood_fence_gate", chaosRL("densewood_planks"));
		fenceGateOpen("densewood_fence_gate", chaosRL("densewood_planks"));
		fenceGateWall("densewood_fence_gate", chaosRL("densewood_planks"));
		fenceGateWallOpen("densewood_fence_gate", chaosRL("densewood_planks"));
		fencePost("densewood_fence", chaosRL("densewood_planks"));
		fenceInventory("densewood_fence_inventory", chaosRL("densewood_planks"));
		fenceSide("densewood_fence", chaosRL("densewood_planks"));
		fenceGate("peach_fence_gate", chaosRL("peach_planks"));
		fenceGateOpen("peach_fence_gate", chaosRL("peach_planks"));
		fenceGateWall("peach_fence_gate", chaosRL("peach_planks"));
		fenceGateWallOpen("peach_fence_gate", chaosRL("peach_planks"));
		fencePost("peach_fence", chaosRL("peach_planks"));
		fenceInventory("peach_fence_inventory", chaosRL("peach_planks"));
		fenceSide("peach_fence", chaosRL("peach_planks"));
		fenceGate("duplication_fence_gate", chaosRL("duplication_planks"));
		fenceGateOpen("duplication_fence_gate", chaosRL("duplication_planks"));
		fenceGateWall("duplication_fence_gate", chaosRL("duplication_planks"));
		fenceGateWallOpen("duplication_fence_gate", chaosRL("duplication_planks"));
		fencePost("duplication_fence", chaosRL("duplication_planks"));
		fenceInventory("duplication_fence_inventory", chaosRL("duplication_planks"));
		fenceSide("duplication_fence", chaosRL("duplication_planks"));
		fenceGate("skywood_fence_gate", chaosRL("skywood_planks"));
		fenceGateOpen("skywood_fence_gate", chaosRL("skywood_planks"));
		fenceGateWall("skywood_fence_gate", chaosRL("skywood_planks"));
		fenceGateWallOpen("skywood_fence_gate", chaosRL("skywood_planks"));
		fencePost("skywood_fence", chaosRL("skywood_planks"));
		fenceInventory("skywood_fence_inventory", chaosRL("skywood_planks"));
		fenceSide("skywood_fence", chaosRL("skywood_planks"));
		fenceGate("crystalwood_fence_gate", chaosRL("crystalwood_planks"));
		fenceGateOpen("crystalwood_fence_gate", chaosRL("crystalwood_planks"));
		fenceGateWall("crystalwood_fence_gate", chaosRL("crystalwood_planks"));
		fenceGateWallOpen("crystal_fence_gate", chaosRL("crystalwood_planks"));
		fencePost("crystalwood_fence", chaosRL("crystalwood_planks"));
		fenceInventory("crystalwood_fence_inventory", chaosRL("crystalwood_planks"));
		fenceSide("crystalwood_fence", chaosRL("crystalwood_planks"));

		pressurePlateUp("apple_pressure_plate", chaosRL("apple_planks"));
		pressurePlateDown("apple_pressure_plate", chaosRL("apple_planks"));
		pressurePlateUp("cherry_pressure_plate", chaosRL("cherry_planks"));
		pressurePlateDown("cherry_pressure_plate", chaosRL("cherry_planks"));
		pressurePlateUp("ginkgo_pressure_plate", chaosRL("ginkgo_planks"));
		pressurePlateDown("ginkgo_pressure_plate", chaosRL("ginkgo_planks"));
		pressurePlateUp("mesozoic_pressure_plate", chaosRL("mesozoic_planks"));
		pressurePlateDown("mesozoic_pressure_plate", chaosRL("mesozoic_planks"));
		pressurePlateUp("duplication_pressure_plate", chaosRL("duplication_planks"));
		pressurePlateDown("duplication_pressure_plate", chaosRL("duplication_planks"));
		pressurePlateUp("densewood_pressure_plate", chaosRL("densewood_planks"));
		pressurePlateDown("densewood_pressure_plate", chaosRL("densewood_planks"));
		pressurePlateUp("peach_pressure_plate", chaosRL("peach_planks"));
		pressurePlateDown("peach_pressure_plate", chaosRL("peach_planks"));
		pressurePlateUp("skywood_pressure_plate", chaosRL("skywood_planks"));
		pressurePlateDown("skywood_pressure_plate", chaosRL("skywood_planks"));
		pressurePlateUp("crystalwood_pressure_plate", chaosRL("crystalwood_planks"));
		pressurePlateDown("crystalwood_pressure_plate", chaosRL("crystalwood_planks"));

		buttonInventory("apple_button_inventory", chaosRL("apple_planks"));
		buttonInventory("cherry_button_inventory", chaosRL("cherry_planks"));
		buttonInventory("ginkgo_button_inventory", chaosRL("ginkgo_planks"));
		buttonInventory("mesozoic_button_inventory", chaosRL("mesozoic_planks"));
		buttonInventory("densewood_button_inventory", chaosRL("densewood_planks"));
		buttonInventory("duplication_button_inventory", chaosRL("duplication_planks"));
		buttonInventory("peach_button_inventory", chaosRL("peach_planks"));
		buttonInventory("skywood_button_inventory", chaosRL("skywood_planks"));
		buttonInventory("crystalwood_button_inventory", chaosRL("crystalwood_planks"));

		cubeAll("moldy_planks", chaosRL("moldy_planks"));
		slab("moldy_slab", chaosRL("moldy_planks"), chaosRL("moldy_planks"), chaosRL("moldy_planks"));
		slabTop("moldy_slab", chaosRL("moldy_planks"), chaosRL("moldy_planks"), chaosRL("moldy_planks"));
		fencePost("moldy_fence", chaosRL("moldy_planks"));
		fenceInventory("moldy_fence", chaosRL("moldy_planks"));
		fenceSide("moldy_fence", chaosRL("moldy_planks"));
		cubeAll("mining_lamp", chaosRL("mining_lamp"));

		cross("cyan_rose", chaosRL("cyan_rose"));
		cross("red_rose", chaosRL("red_rose"));
		cross("paeonia", chaosRL("paeonia"));
		cross("daisy", chaosRL("daisy"));
		cross("swamp_milkweed", chaosRL("swamp_milkweed"));
		cross("primrose", chaosRL("primrose"));
		cross("blue_crystal_growth", chaosRL("blue_crystal_growth"));
		cross("green_crystal_growth", chaosRL("green_crystal_growth"));
		cross("red_crystal_growth", chaosRL("red_crystal_growth"));
		cross("yellow_crystal_growth", chaosRL("yellow_crystal_growth"));
		cross("orange_crystal_growth", chaosRL("orange_crystal_growth"));
		cross("pink_crystal_growth", chaosRL("pink_crystal_growth"));
		cross("blue_crystal_flower", chaosRL("blue_crystal_flower"));
		cross("green_crystal_flower", chaosRL("green_crystal_flower"));
		cross("red_crystal_flower", chaosRL("red_crystal_flower"));
		cross("yellow_crystal_flower", chaosRL("yellow_crystal_flower"));
		cross("pink_crystal_flower", chaosRL("pink_crystal_flower"));
		cross("orange_crystal_flower", chaosRL("orange_crystal_flower"));
		cross("crystal_rose", chaosRL("crystal_rose"));

		pottedCross("potted_ginkgo_sapling", chaosRL("ginkgo_sapling"));
		pottedCross("potted_mesozoic_sapling", chaosRL("mesozoic_sapling"));
		pottedCross("potted_densewood_sapling", chaosRL("densewood_sapling"));
		pottedCross("potted_dense_orchid", chaosRL("dense_orchid"));
		pottedCross("potted_swamp_milkweed", chaosRL("swamp_milkweed"));
		pottedCross("potted_small_bush", chaosRL("small_bush"));
		pottedCross("potted_small_carnivorous_plant", chaosRL("small_carnivorous_plant"));
		pottedCross("potted_big_carnivorous_plant", chaosRL("big_carnivorous_plant"));
		pottedCross("potted_primrose", chaosRL("primrose"));
		pottedCross("potted_daisy", chaosRL("daisy"));
		pottedCross("potted_apple_sapling", chaosRL("apple_sapling"));
		pottedCross("potted_cherry_sapling", chaosRL("cherry_sapling"));
	}

	private ResourceLocation chaosRL(String texture) {
		return ChaosAwakens.prefix(BLOCK_FOLDER + "/" + texture);
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

	public void trapDoor(String name) {
		singleTexture(name + "_bottom", mcRL("template_orientable_trapdoor_bottom"), chaosRL(name));
		singleTexture(name + "_open", mcRL("template_orientable_trapdoor_open"), chaosRL(name));
		singleTexture(name + "_top", mcRL("template_orientable_trapdoor_top"), chaosRL(name));
	}

	public void leafCarpet(String name, ResourceLocation texture) {
		singleTexture(name, chaosRL("leaf_carpet"), "texture", texture);
	}

	public void leafCarpetInventory(String name, ResourceLocation texture) {
		singleTexture(name, chaosRL("leaf_carpet_inventory"), "texture", texture);
	}

	public BlockModelBuilder wired2SidedLeftBlock(String name, ResourceLocation targetTexture, ResourceLocation sideTexture) {
		return orientableWithBottom(name, sideTexture, targetTexture, chaosRL(targetTexture.getPath() + "_lb"), chaosRL(targetTexture.getPath() + "_lt"));
	}

	public BlockModelBuilder wired2SidedRightBlock(String name, ResourceLocation targetTexture, ResourceLocation sideTexture) {
		return orientableWithBottom(name, sideTexture, targetTexture, chaosRL(targetTexture.getPath() + "_rb"), chaosRL(targetTexture.getPath() + "_rt"));
	}

	public BlockModelBuilder wired3SidedLeftBlock(String name, ResourceLocation targetTexture, ResourceLocation sideTexture) {
		return orientableWithBottom(name, sideTexture, targetTexture, chaosRL(targetTexture.getPath() + "_bottom"), chaosRL(targetTexture.getPath() + "_top"))
				.texture("east", chaosRL(targetTexture.getPath() + "_right"))
				.texture("west", chaosRL(targetTexture.getPath() + "_left"));
	}

	public void gateBlock(String name, ResourceLocation top) {
		withExistingParent(name, BLOCK_FOLDER).texture("side", chaosRL(name)).texture("top", top).texture("bottom", top);
	}

	public void plant(String name, String texture) {
		cross(name, chaosRL(texture));
	}

	public void doublePlant(String name, String texture) {
		cross(name + "_top", chaosRL(texture + "_top"));
		cross(name + "_bottom", chaosRL(texture + "_bottom"));
	}

	public BlockModelBuilder farmland(String name, ResourceLocation dirt, ResourceLocation top) {
		return withExistingParent(name, BLOCK_FOLDER + "/template_farmland")
				.texture("dirt", dirt)
				.texture("top", top);
	}

	public BlockModelBuilder grassBlock(String name, ResourceLocation particle, ResourceLocation bottom, ResourceLocation top, ResourceLocation side, ResourceLocation overlay) {
		return withExistingParent(name, BLOCK_FOLDER + "/grass_block")
				.texture("particle", particle)
				.texture("bottom", bottom)
				.texture("top", top)
				.texture("side", side)
				.texture("overlay", overlay);
	}

	public void standardPaneBlock(String normalPaneName, ResourceLocation paneTexture, ResourceLocation edgeTexture) {
		panePost(normalPaneName + "_post", paneTexture, edgeTexture);
		paneNoSide(normalPaneName + "_noside", paneTexture);
		paneSide(normalPaneName + "_side", paneTexture, edgeTexture);
		paneNoSideAlt(normalPaneName + "_noside_alt", paneTexture);
		paneSideAlt(normalPaneName + "_side_alt", paneTexture, edgeTexture);
	}

	public void standardBarBlock(String normalBarName, ResourceLocation barTexture) {
		barCap(normalBarName + "_cap", barTexture);
		barCapAlt(normalBarName + "_cap_alt", barTexture);
		barPost(normalBarName + "_post", barTexture);
		barPostEnds(normalBarName + "_post_ends", barTexture);
		barSide(normalBarName + "_side", barTexture);
		barSideAlt(normalBarName + "_side_alt", barTexture);
	}

	public BlockModelBuilder barCap(String name, ResourceLocation barTexture) {
		return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_cap")
				.texture("particle", barTexture)
				.texture("bars", barTexture)
				.texture("edge", barTexture);
	}

	public BlockModelBuilder barCapAlt(String name, ResourceLocation barTexture) {
		return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_cap_alt")
				.texture("particle", barTexture)
				.texture("bars", barTexture)
				.texture("edge", barTexture);
	}

	public BlockModelBuilder barPost(String name, ResourceLocation barTexture) {
		return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_post")
				.texture("particle", barTexture)
				.texture("bars", barTexture);
	}

	public BlockModelBuilder barPostEnds(String name, ResourceLocation barTexture) {
		return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_post_ends")
				.texture("particle", barTexture)
				.texture("bars", barTexture)
				.texture("edge", barTexture);
	}

	public BlockModelBuilder barSide(String name, ResourceLocation barTexture) {
		return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_side")
				.texture("particle", barTexture)
				.texture("bars", barTexture)
				.texture("edge", barTexture);
	}

	public BlockModelBuilder barSideAlt(String name, ResourceLocation barTexture) {
		return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_side_alt")
				.texture("particle", barTexture)
				.texture("bars", barTexture)
				.texture("edge", barTexture);
	}

	public BlockModelBuilder tintedCross(String name, ResourceLocation cross) {
		return withExistingParent(name, BLOCK_FOLDER + "/tinted_cross")
				.texture("cross", cross);
	}

	public void doubleTintedCross(String name, String texture) {
		tintedCross(name + "_top", chaosRL(texture + "_top"));
		tintedCross(name + "_bottom", chaosRL(texture + "_bottom"));
	}

	public BlockModelBuilder buttonInventory(String name, ResourceLocation texture) {
		return withExistingParent(name, BLOCK_FOLDER + "/button_inventory")
				.texture("texture", texture);
	}

	public BlockModelBuilder pottedCross(String name, ResourceLocation texture) {
		return withExistingParent(name, BLOCK_FOLDER + "/flower_pot_cross")
				.texture("plant", texture);
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
