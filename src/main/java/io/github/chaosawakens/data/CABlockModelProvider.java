package io.github.chaosawakens.data;

import java.util.Objects;

import javax.annotation.Nonnull;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.CAFallingOreBlock;
import io.github.chaosawakens.common.blocks.CAOreBlock;
import io.github.chaosawakens.common.blocks.GateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ModelTextures;
import net.minecraft.data.ModelsResourceUtil;
import net.minecraft.data.StockModelShapes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("all")
public class CABlockModelProvider extends BlockModelProvider {
	public CABlockModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}

	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + ": Block Models";
	}

	private static ResourceLocation getResourceLocation(String path) {
		return new ResourceLocation(ChaosAwakens.MODID, path);
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
				this.trapDoor(name);
			} else if (block instanceof GateBlock) {
				this.gateBlock(name, name.contains("robo_") ? chaosRL("robo_gate_block_top") : chaosRL("gate_block_top"));
			} else if (block instanceof CAOreBlock || block instanceof CAFallingOreBlock) {
				if (name.contains("crystal_energy")) {
					this.cross(name, chaosRL(name));
				} else if (name.contains("sandstone")) {
					this.cubeBottomTop(name, chaosRL(name), mcRL("sandstone_bottom"), mcRL("sandstone_top"));
				} else {
					this.cubeAll(name, chaosRL(name));
				}
			}
		}

		this.orientable("copper_defossilizer", chaosRL("copper_block"), chaosRL("copper_defossilizer_front"), chaosRL("copper_defossilizer_top"));
		this.orientable("iron_defossilizer", mcRL("iron_block"), chaosRL("iron_defossilizer_front"), chaosRL("iron_defossilizer_top"));
		
		this.grassBlock("dense_grass_block", chaosRL("dense_dirt"), chaosRL("dense_dirt"), chaosRL("dense_grass_block_top"), chaosRL("dense_grass_block_side"), chaosRL("dense_grass_block_side_overlay"));
		//this.cubeBottomTop("dense_grass_block", chaosRL("dense_grass_block_side"), chaosRL("dense_dirt"), chaosRL("dense_grass_block_top"));
		this.cubeAll("dense_dirt", chaosRL("dense_dirt"));
		this.farmland("dense_farmland", chaosRL("dense_dirt"), chaosRL("dense_farmland"));
		this.farmland("dense_farmland_moist", chaosRL("dense_dirt"), chaosRL("dense_farmland_moist"));
		this.grassBlock("dense_red_ant_nest", chaosRL("dense_dirt"), chaosRL("dense_dirt"), chaosRL("dense_red_ant_nest"), chaosRL("dense_grass_block_side"), chaosRL("dense_grass_block_side_overlay"));
		this.tintedCross("dense_grass", chaosRL("dense_grass"));
		this.doubleTintedCross("tall_dense_grass", "tall_dense_grass");
		this.doublePlant("thorny_sun", "thorny_sun");
		this.cubeAll("terra_preta", chaosRL("terra_preta"));
		this.farmland("terra_preta_farmland", chaosRL("terra_preta"), chaosRL("terra_preta_farmland"));
		this.farmland("terra_preta_farmland_moist", chaosRL("terra_preta"), chaosRL("terra_preta_farmland_moist"));
		this.cubeAll("tar", chaosRL("tar"));
		this.cubeAll("latosol", chaosRL("latosol"));
		this.doublePlant("alstroemeriat", "alstroemeriat");
		this.cross("dense_orchid", chaosRL("dense_orchid"));
		this.cross("small_bush", chaosRL("small_bush"));
		this.cross("small_carnivorous_plant", chaosRL("small_carnivorous_plant"));
		this.cross("big_carnivorous_plant", chaosRL("big_carnivorous_plant"));
		this.cross("blue_bulb", chaosRL("blue_bulb"));
		this.cross("pink_bulb", chaosRL("pink_bulb"));
		this.cross("purple_bulb", chaosRL("purple_bulb"));
		this.cross("mesozoic_vines", chaosRL("mesozoic_vines"));
		this.cross("mesozoic_vines_plant", chaosRL("mesozoic_vines_plant"));

		this.cubeAll("aluminum_block", chaosRL("aluminum_block"));
//		this.cubeAll("aluminum_ore", chaosRL("aluminum_ore"));
		this.cubeAll("amethyst_block", chaosRL("amethyst_block"));
//		this.cubeAll("amethyst_ore", chaosRL("amethyst_ore"));
		this.cubeAll("bloodstone_block", chaosRL("bloodstone_block"));
//		this.cubeAll("bloodstone_ore", chaosRL("bloodstone_ore"));
		this.cubeAll("copper_block", chaosRL("copper_block"));
//		this.cubeAll("copper_ore", chaosRL("copper_ore"));
		this.cubeAll("platinum_block", chaosRL("platinum_block"));
//		this.cubeAll("platinum_ore", chaosRL("platinum_ore"));
		this.cubeAll("ruby_block", chaosRL("ruby_block"));
//		this.cubeAll("ruby_ore", chaosRL("ruby_ore"));
//		this.cubeAll("netherrack_ruby_ore", chaosRL("netherrack_ruby_ore"));
//		this.cubeAll("blackstone_ruby_ore", chaosRL("blackstone_ruby_ore"));
		this.cubeAll("salt_block", chaosRL("salt_block"));
//		this.cubeAll("salt_ore", chaosRL("salt_ore"));
		this.cubeAll("silver_block", chaosRL("silver_block"));
//		this.cubeAll("silver_ore", chaosRL("silver_ore"));
		this.cubeAll("sunstone_block", chaosRL("sunstone_block"));
//		this.cubeAll("sunstone_ore", chaosRL("sunstone_ore"));
		this.cubeAll("tigers_eye_block", chaosRL("tigers_eye_block"));
//		this.cubeAll("tigers_eye_ore", chaosRL("tigers_eye_ore"));
		this.cubeAll("tin_block", chaosRL("tin_block"));
//		this.cubeAll("tin_ore", chaosRL("tin_ore"));
		this.cubeAll("titanium_block", chaosRL("titanium_block"));
//		this.cubeAll("titanium_ore", chaosRL("titanium_ore"));
		this.cubeAll("uranium_block", chaosRL("uranium_block"));
//		this.cubeAll("uranium_ore", chaosRL("uranium_ore"));

		this.cubeAll("budding_cats_eye", chaosRL("budding_cats_eye"));
		this.cubeAll("cats_eye_block", chaosRL("cats_eye_block"));
		this.cubeAll("budding_pink_tourmaline", chaosRL("budding_pink_tourmaline"));
		this.cubeAll("pink_tourmaline_block", chaosRL("pink_tourmaline_block"));

		this.cubeAll("marble_block", chaosRL("marble_block"));
		this.cubeAll("marble_bricks", chaosRL("marble_bricks"));
		this.cubeAll("chiseled_marble_bricks", chaosRL("chiseled_marble_bricks"));
		this.cubeAll("cracked_marble_bricks", chaosRL("cracked_marble_bricks"));
		this.cubeAll("mossy_marble_bricks", chaosRL("mossy_marble_bricks"));
		this.cubeAll("polished_marble_block", chaosRL("polished_marble_block"));
		this.cubeColumn("marble_pillar", chaosRL("marble_pillar"), chaosRL("marble_pillar_top"));
		this.cubeColumnHorizontal("marble_pillar", chaosRL("marble_pillar"), chaosRL("marble_pillar_top"));
		this.slab("marble_slab", chaosRL("marble_block"), chaosRL("marble_block"), chaosRL("marble_block"));
		this.slabTop("marble_slab", chaosRL("marble_block"), chaosRL("marble_block"), chaosRL("marble_block"));
		this.slab("marble_brick_slab", chaosRL("marble_bricks"), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		this.slabTop("marble_brick_slab", chaosRL("marble_bricks"), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		this.slab("chiseled_marble_brick_slab", chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		this.slabTop("chiseled_marble_brick_slab", chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		this.slab("cracked_marble_brick_slab", chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		this.slabTop("cracked_marble_brick_slab", chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		this.slab("mossy_marble_brick_slab", chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		this.slabTop("mossy_marble_brick_slab", chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		this.slab("polished_marble_slab", chaosRL("polished_marble_block"), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		this.slabTop("polished_marble_slab", chaosRL("polished_marble_block"), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		this.stairs("marble_stairs", chaosRL("marble_block"), chaosRL("marble_block"), chaosRL("marble_block"));
		this.stairsInner("marble_stairs", chaosRL("marble_block"), chaosRL("marble_block"), chaosRL("marble_block"));
		this.stairsOuter("marble_stairs", chaosRL("marble_block"), chaosRL("marble_block"), chaosRL("marble_block"));
		this.stairs("marble_brick_stairs", chaosRL("marble_bricks"), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		this.stairsInner("marble_brick_stairs", chaosRL("marble_bricks"), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		this.stairsOuter("marble_brick_stairs", chaosRL("marble_bricks"), chaosRL("marble_bricks"), chaosRL("marble_bricks"));
		this.stairs("chiseled_marble_brick_stairs", chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		this.stairsInner("chiseled_marble_brick_stairs", chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		this.stairsOuter("chiseled_marble_brick_stairs", chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"), chaosRL("chiseled_marble_bricks"));
		this.stairs("cracked_marble_brick_stairs", chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		this.stairsInner("cracked_marble_brick_stairs", chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		this.stairsOuter("cracked_marble_brick_stairs", chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"), chaosRL("cracked_marble_bricks"));
		this.stairs("mossy_marble_brick_stairs", chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		this.stairsInner("mossy_marble_brick_stairs", chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		this.stairsOuter("mossy_marble_brick_stairs", chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"), chaosRL("mossy_marble_bricks"));
		this.stairs("polished_marble_stairs", chaosRL("polished_marble_block"), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		this.stairsInner("polished_marble_stairs", chaosRL("polished_marble_block"), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		this.stairsOuter("polished_marble_stairs", chaosRL("polished_marble_block"), chaosRL("polished_marble_block"), chaosRL("polished_marble_block"));
		this.wallPost("marble_wall", chaosRL("marble_block"));
		this.wallInventory("marble_wall_inventory", chaosRL("marble_block"));
		this.wallSide("marble_wall", chaosRL("marble_block"));
		this.wallSideTall("marble_wall", chaosRL("marble_block"));
		this.wallPost("marble_brick_wall", chaosRL("marble_bricks"));
		this.wallInventory("marble_brick_wall_inventory", chaosRL("marble_bricks"));
		this.wallSide("marble_brick_wall", chaosRL("marble_bricks"));
		this.wallSideTall("marble_brick_wall", chaosRL("marble_bricks"));
		this.wallPost("chiseled_marble_brick_wall", chaosRL("chiseled_marble_bricks"));
		this.wallInventory("chiseled_marble_brick_wall_inventory", chaosRL("chiseled_marble_bricks"));
		this.wallSide("chiseled_marble_brick_wall", chaosRL("chiseled_marble_bricks"));
		this.wallSideTall("chiseled_marble_brick_wall", chaosRL("chiseled_marble_bricks"));
		this.wallPost("cracked_marble_brick_wall", chaosRL("cracked_marble_bricks"));
		this.wallInventory("cracked_marble_brick_wall_inventory", chaosRL("cracked_marble_bricks"));
		this.wallSide("cracked_marble_brick_wall", chaosRL("cracked_marble_bricks"));
		this.wallSideTall("cracked_marble_brick_wall", chaosRL("cracked_marble_bricks"));
		this.wallPost("mossy_marble_brick_wall", chaosRL("mossy_marble_bricks"));
		this.wallInventory("mossy_marble_brick_wall_inventory", chaosRL("mossy_marble_bricks"));
		this.wallSide("mossy_marble_brick_wall", chaosRL("mossy_marble_bricks"));
		this.wallSideTall("mossy_marble_brick_wall", chaosRL("mossy_marble_bricks"));
		this.wallPost("polished_marble_wall", chaosRL("polished_marble_block"));
		this.wallInventory("polished_marble_wall_inventory", chaosRL("polished_marble_block"));
		this.wallSide("polished_marble_wall", chaosRL("polished_marble_block"));
		this.wallSideTall("polished_marble_wall", chaosRL("polished_marble_block"));

		this.cubeAll("limestone_block", chaosRL("limestone_block"));
		this.cubeAll("limestone_bricks", chaosRL("limestone_bricks"));
		this.cubeAll("chiseled_limestone_bricks", chaosRL("chiseled_limestone_bricks"));
		this.cubeAll("cracked_limestone_bricks", chaosRL("cracked_limestone_bricks"));
		this.cubeAll("mossy_limestone_bricks", chaosRL("mossy_limestone_bricks"));
		this.cubeAll("polished_limestone_block", chaosRL("polished_limestone_block"));
		this.cubeColumn("limestone_pillar", chaosRL("limestone_pillar"), chaosRL("limestone_pillar_top"));
		this.cubeColumnHorizontal("limestone_pillar", chaosRL("limestone_pillar"), chaosRL("limestone_pillar_top"));
		this.slab("limestone_slab", chaosRL("limestone_block"), chaosRL("limestone_block"), chaosRL("limestone_block"));
		this.slabTop("limestone_slab", chaosRL("limestone_block"), chaosRL("limestone_block"), chaosRL("limestone_block"));
		this.slab("limestone_brick_slab", chaosRL("limestone_bricks"), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		this.slabTop("limestone_brick_slab", chaosRL("limestone_bricks"), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		this.slab("chiseled_limestone_brick_slab", chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		this.slabTop("chiseled_limestone_brick_slab", chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		this.slab("cracked_limestone_brick_slab", chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		this.slabTop("cracked_limestone_brick_slab", chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		this.slab("mossy_limestone_brick_slab", chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		this.slabTop("mossy_limestone_brick_slab", chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		this.slab("polished_limestone_slab", chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		this.slabTop("polished_limestone_slab", chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		this.stairs("limestone_stairs", chaosRL("limestone_block"), chaosRL("limestone_block"), chaosRL("limestone_block"));
		this.stairsInner("limestone_stairs", chaosRL("limestone_block"), chaosRL("limestone_block"), chaosRL("limestone_block"));
		this.stairsOuter("limestone_stairs", chaosRL("limestone_block"), chaosRL("limestone_block"), chaosRL("limestone_block"));
		this.stairs("limestone_brick_stairs", chaosRL("limestone_bricks"), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		this.stairsInner("limestone_brick_stairs", chaosRL("limestone_bricks"), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		this.stairsOuter("limestone_brick_stairs", chaosRL("limestone_bricks"), chaosRL("limestone_bricks"), chaosRL("limestone_bricks"));
		this.stairs("chiseled_limestone_brick_stairs", chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		this.stairsInner("chiseled_limestone_brick_stairs", chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		this.stairsOuter("chiseled_limestone_brick_stairs", chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"), chaosRL("chiseled_limestone_bricks"));
		this.stairs("cracked_limestone_brick_stairs", chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		this.stairsInner("cracked_limestone_brick_stairs", chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		this.stairsOuter("cracked_limestone_brick_stairs", chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"), chaosRL("cracked_limestone_bricks"));
		this.stairs("mossy_limestone_brick_stairs", chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		this.stairsInner("mossy_limestone_brick_stairs", chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		this.stairsOuter("mossy_limestone_brick_stairs", chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"), chaosRL("mossy_limestone_bricks"));
		this.stairs("polished_limestone_stairs", chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		this.stairsInner("polished_limestone_stairs", chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		this.stairsOuter("polished_limestone_stairs", chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"), chaosRL("polished_limestone_block"));
		this.wallPost("limestone_wall", chaosRL("limestone_block"));
		this.wallInventory("limestone_wall_inventory", chaosRL("limestone_block"));
		this.wallSide("limestone_wall", chaosRL("limestone_block"));
		this.wallSideTall("limestone_wall", chaosRL("limestone_block"));
		this.wallPost("limestone_brick_wall", chaosRL("limestone_bricks"));
		this.wallInventory("limestone_brick_wall_inventory", chaosRL("limestone_bricks"));
		this.wallSide("limestone_brick_wall", chaosRL("limestone_bricks"));
		this.wallSideTall("limestone_brick_wall", chaosRL("limestone_bricks"));
		this.wallPost("chiseled_limestone_brick_wall", chaosRL("chiseled_limestone_bricks"));
		this.wallInventory("chiseled_limestone_brick_wall_inventory", chaosRL("chiseled_limestone_bricks"));
		this.wallSide("chiseled_limestone_brick_wall", chaosRL("chiseled_limestone_bricks"));
		this.wallSideTall("chiseled_limestone_brick_wall", chaosRL("chiseled_limestone_bricks"));
		this.wallPost("cracked_limestone_brick_wall", chaosRL("cracked_limestone_bricks"));
		this.wallInventory("cracked_limestone_brick_wall_inventory", chaosRL("cracked_limestone_bricks"));
		this.wallSide("cracked_limestone_brick_wall", chaosRL("cracked_limestone_bricks"));
		this.wallSideTall("cracked_limestone_brick_wall", chaosRL("cracked_limestone_bricks"));
		this.wallPost("mossy_limestone_brick_wall", chaosRL("mossy_limestone_bricks"));
		this.wallInventory("mossy_limestone_brick_wall_inventory", chaosRL("mossy_limestone_bricks"));
		this.wallSide("mossy_limestone_brick_wall", chaosRL("mossy_limestone_bricks"));
		this.wallSideTall("mossy_limestone_brick_wall", chaosRL("mossy_limestone_bricks"));
		this.wallPost("polished_limestone_wall", chaosRL("polished_limestone_block"));
		this.wallInventory("polished_limestone_wall_inventory", chaosRL("polished_limestone_block"));
		this.wallSide("polished_limestone_wall", chaosRL("polished_limestone_block"));
		this.wallSideTall("polished_limestone_wall", chaosRL("polished_limestone_block"));
		
		this.cubeAll("rhinestone_block", chaosRL("rhinestone_block"));
		this.cubeAll("rhinestone_bricks", chaosRL("rhinestone_bricks"));
		this.cubeAll("chiseled_rhinestone_bricks", chaosRL("chiseled_rhinestone_bricks"));
		this.cubeAll("cracked_rhinestone_bricks", chaosRL("cracked_rhinestone_bricks"));
		this.cubeAll("mossy_rhinestone_bricks", chaosRL("mossy_rhinestone_bricks"));
		this.cubeAll("polished_rhinestone_block", chaosRL("polished_rhinestone_block"));
		this.cubeColumn("rhinestone_pillar", chaosRL("rhinestone_pillar"), chaosRL("rhinestone_pillar_top"));
		this.cubeColumnHorizontal("rhinestone_pillar", chaosRL("rhinestone_pillar"), chaosRL("rhinestone_pillar_top"));
		this.slab("rhinestone_slab", chaosRL("rhinestone_block"), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		this.slabTop("rhinestone_slab", chaosRL("rhinestone_block"), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		this.slab("rhinestone_brick_slab", chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		this.slabTop("rhinestone_brick_slab", chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		this.slab("chiseled_rhinestone_brick_slab", chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		this.slabTop("chiseled_rhinestone_brick_slab", chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		this.slab("cracked_rhinestone_brick_slab", chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		this.slabTop("cracked_rhinestone_brick_slab", chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		this.slab("mossy_rhinestone_brick_slab", chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		this.slabTop("mossy_rhinestone_brick_slab", chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		this.slab("polished_rhinestone_slab", chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		this.slabTop("polished_rhinestone_slab", chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		this.stairs("rhinestone_stairs", chaosRL("rhinestone_block"), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		this.stairsInner("rhinestone_stairs", chaosRL("rhinestone_block"), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		this.stairsOuter("rhinestone_stairs", chaosRL("rhinestone_block"), chaosRL("rhinestone_block"), chaosRL("rhinestone_block"));
		this.stairs("rhinestone_brick_stairs", chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		this.stairsInner("rhinestone_brick_stairs", chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		this.stairsOuter("rhinestone_brick_stairs", chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"), chaosRL("rhinestone_bricks"));
		this.stairs("chiseled_rhinestone_brick_stairs", chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		this.stairsInner("chiseled_rhinestone_brick_stairs", chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		this.stairsOuter("chiseled_rhinestone_brick_stairs", chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"), chaosRL("chiseled_rhinestone_bricks"));
		this.stairs("cracked_rhinestone_brick_stairs", chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		this.stairsInner("cracked_rhinestone_brick_stairs", chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		this.stairsOuter("cracked_rhinestone_brick_stairs", chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"), chaosRL("cracked_rhinestone_bricks"));
		this.stairs("mossy_rhinestone_brick_stairs", chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		this.stairsInner("mossy_rhinestone_brick_stairs", chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		this.stairsOuter("mossy_rhinestone_brick_stairs", chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"), chaosRL("mossy_rhinestone_bricks"));
		this.stairs("polished_rhinestone_stairs", chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		this.stairsInner("polished_rhinestone_stairs", chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		this.stairsOuter("polished_rhinestone_stairs", chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"), chaosRL("polished_rhinestone_block"));
		this.wallPost("rhinestone_wall", chaosRL("rhinestone_block"));
		this.wallInventory("rhinestone_wall_inventory", chaosRL("rhinestone_block"));
		this.wallSide("rhinestone_wall", chaosRL("rhinestone_block"));
		this.wallSideTall("rhinestone_wall", chaosRL("rhinestone_block"));
		this.wallPost("rhinestone_brick_wall", chaosRL("rhinestone_bricks"));
		this.wallInventory("rhinestone_brick_wall_inventory", chaosRL("rhinestone_bricks"));
		this.wallSide("rhinestone_brick_wall", chaosRL("rhinestone_bricks"));
		this.wallSideTall("rhinestone_brick_wall", chaosRL("rhinestone_bricks"));
		this.wallPost("chiseled_rhinestone_brick_wall", chaosRL("chiseled_rhinestone_bricks"));
		this.wallInventory("chiseled_rhinestone_brick_wall_inventory", chaosRL("chiseled_rhinestone_bricks"));
		this.wallSide("chiseled_rhinestone_brick_wall", chaosRL("chiseled_rhinestone_bricks"));
		this.wallSideTall("chiseled_rhinestone_brick_wall", chaosRL("chiseled_rhinestone_bricks"));
		this.wallPost("cracked_rhinestone_brick_wall", chaosRL("cracked_rhinestone_bricks"));
		this.wallInventory("cracked_rhinestone_brick_wall_inventory", chaosRL("cracked_rhinestone_bricks"));
		this.wallSide("cracked_rhinestone_brick_wall", chaosRL("cracked_rhinestone_bricks"));
		this.wallSideTall("cracked_rhinestone_brick_wall", chaosRL("cracked_rhinestone_bricks"));
		this.wallPost("mossy_rhinestone_brick_wall", chaosRL("mossy_rhinestone_bricks"));
		this.wallInventory("mossy_rhinestone_brick_wall_inventory", chaosRL("mossy_rhinestone_bricks"));
		this.wallSide("mossy_rhinestone_brick_wall", chaosRL("mossy_rhinestone_bricks"));
		this.wallSideTall("mossy_rhinestone_brick_wall", chaosRL("mossy_rhinestone_bricks"));
		this.wallPost("polished_rhinestone_wall", chaosRL("polished_rhinestone_block"));
		this.wallInventory("polished_rhinestone_wall_inventory", chaosRL("polished_rhinestone_block"));
		this.wallSide("polished_rhinestone_wall", chaosRL("polished_rhinestone_block"));
		this.wallSideTall("polished_rhinestone_wall", chaosRL("polished_rhinestone_block"));

		this.cubeAll("robo_block_l", chaosRL("robo_block_l"));
		this.cubeColumn("robo_block_v", chaosRL("robo_block_v"), chaosRL("robo_block_top"));
		this.cubeColumnHorizontal("robo_block_v", chaosRL("robo_block_v"), chaosRL("robo_block_top"));
		this.cubeColumn("robo_block_x", chaosRL("robo_block_v"), chaosRL("robo_block_top"));
		this.cubeColumnHorizontal("robo_block_x", chaosRL("robo_block_v"), chaosRL("robo_block_top"));
		this.slab("robo_slab_l", chaosRL("robo_block_l"), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		this.slabTop("robo_slab_l", chaosRL("robo_block_l"), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		this.slab("robo_slab_x", chaosRL("robo_block_x"), chaosRL("robo_block_top"), chaosRL("robo_block_top"));
		this.slabTop("robo_slab_x", chaosRL("robo_block_x"), chaosRL("robo_block_top"), chaosRL("robo_block_top"));
		this.stairs("robo_stairs_l", chaosRL("robo_block_l"), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		this.stairsInner("robo_stairs_l", chaosRL("robo_block_l"), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		this.stairsOuter("robo_stairs_l", chaosRL("robo_block_l"), chaosRL("robo_block_l"), chaosRL("robo_block_l"));
		this.stairs("robo_stairs_x", chaosRL("robo_block_x"), chaosRL("robo_block_top"), chaosRL("robo_block_top"));
		this.stairsInner("robo_stairs_x", chaosRL("robo_block_x"), chaosRL("robo_block_top"), chaosRL("robo_block_top"));
		this.stairsOuter("robo_stairs_x", chaosRL("robo_block_x"), chaosRL("robo_block_top"), chaosRL("robo_block_top"));
		this.wallPost("robo_wall_l", chaosRL("robo_block_l"));
		this.wallInventory("robo_wall_l_inventory", chaosRL("robo_block_l"));
		this.wallSide("robo_wall_l", chaosRL("robo_block_l"));
		this.wallSideTall("robo_wall_l", chaosRL("robo_block_l"));
		this.wallPost("robo_wall_x", chaosRL("robo_block_x"));
		this.wallInventory("robo_wall_x_inventory", chaosRL("robo_block_x"));
		this.wallSide("robo_wall_x", chaosRL("robo_block_x"));
		this.wallSideTall("robo_wall_x", chaosRL("robo_block_x"));
		this.cubeAll("robo_lamp", chaosRL("robo_lamp"));
		this.cubeAll("robo_bricks", chaosRL("robo_bricks"));
		this.slab("robo_brick_slab", chaosRL("robo_bricks"), chaosRL("robo_bricks"), chaosRL("robo_bricks"));
		this.slabTop("robo_brick_slab", chaosRL("robo_bricks"), chaosRL("robo_bricks"), chaosRL("robo_bricks"));
		this.stairs("robo_brick_stairs", chaosRL("robo_bricks"), chaosRL("robo_bricks"), chaosRL("robo_bricks"));
		this.stairsInner("robo_brick_stairs", chaosRL("robo_bricks"), chaosRL("robo_bricks"), chaosRL("robo_bricks"));
		this.stairsOuter("robo_brick_stairs", chaosRL("robo_bricks"), chaosRL("robo_bricks"), chaosRL("robo_bricks"));
		this.wallPost("robo_brick_wall", chaosRL("robo_bricks"));
		this.wallInventory("robo_brick_wall_inventory", chaosRL("robo_bricks"));
		this.wallSide("robo_brick_wall", chaosRL("robo_bricks"));
		this.wallSideTall("robo_brick_wall", chaosRL("robo_bricks"));
		this.cubeColumn("compact_robo_block", chaosRL("compact_robo_block"), chaosRL("compact_robo_block_top"));
		this.cubeColumnHorizontal("compact_robo_block", chaosRL("compact_robo_block"), chaosRL("compact_robo_block_top"));

		this.cubeAll("black_terracotta_bricks", chaosRL("black_terracotta_bricks"));
		this.cubeAll("blue_terracotta_bricks", chaosRL("blue_terracotta_bricks"));
		this.cubeAll("brown_terracotta_bricks", chaosRL("brown_terracotta_bricks"));
		this.cubeAll("cyan_terracotta_bricks", chaosRL("cyan_terracotta_bricks"));
		this.cubeAll("gray_terracotta_bricks", chaosRL("gray_terracotta_bricks"));
		this.cubeAll("green_terracotta_bricks", chaosRL("green_terracotta_bricks"));
		this.cubeAll("light_blue_terracotta_bricks", chaosRL("light_blue_terracotta_bricks"));
		this.cubeAll("light_gray_terracotta_bricks", chaosRL("light_gray_terracotta_bricks"));
		this.cubeAll("lime_terracotta_bricks", chaosRL("lime_terracotta_bricks"));
		this.cubeAll("magenta_terracotta_bricks", chaosRL("magenta_terracotta_bricks"));
		this.cubeAll("orange_terracotta_bricks", chaosRL("orange_terracotta_bricks"));
		this.cubeAll("pink_terracotta_bricks", chaosRL("pink_terracotta_bricks"));
		this.cubeAll("purple_terracotta_bricks", chaosRL("purple_terracotta_bricks"));
		this.cubeAll("red_terracotta_bricks", chaosRL("red_terracotta_bricks"));
		this.cubeAll("terracotta_bricks", chaosRL("terracotta_bricks"));
		this.cubeAll("white_terracotta_bricks", chaosRL("white_terracotta_bricks"));
		this.cubeAll("yellow_terracotta_bricks", chaosRL("yellow_terracotta_bricks"));
		this.slab("black_terracotta_brick_slab", chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		this.slabTop("black_terracotta_brick_slab", chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		this.slab("blue_terracotta_brick_slab", chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		this.slabTop("blue_terracotta_brick_slab", chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		this.slab("brown_terracotta_brick_slab", chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		this.slabTop("brown_terracotta_brick_slab", chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		this.slab("cyan_terracotta_brick_slab", chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		this.slabTop("cyan_terracotta_brick_slab", chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		this.slab("gray_terracotta_brick_slab", chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		this.slabTop("gray_terracotta_brick_slab", chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		this.slab("green_terracotta_brick_slab", chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		this.slabTop("green_terracotta_brick_slab", chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		this.slab("light_blue_terracotta_brick_slab", chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		this.slabTop("light_blue_terracotta_brick_slab", chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		this.slab("light_gray_terracotta_brick_slab", chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		this.slabTop("light_gray_terracotta_brick_slab", chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		this.slab("lime_terracotta_brick_slab", chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		this.slabTop("lime_terracotta_brick_slab", chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		this.slab("magenta_terracotta_brick_slab", chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		this.slabTop("magenta_terracotta_brick_slab", chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		this.slab("orange_terracotta_brick_slab", chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		this.slabTop("orange_terracotta_brick_slab", chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		this.slab("pink_terracotta_brick_slab", chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		this.slabTop("pink_terracotta_brick_slab", chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		this.slab("purple_terracotta_brick_slab", chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		this.slabTop("purple_terracotta_brick_slab", chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		this.slab("red_terracotta_brick_slab", chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		this.slabTop("red_terracotta_brick_slab", chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		this.slab("terracotta_brick_slab", chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		this.slabTop("terracotta_brick_slab", chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		this.slab("white_terracotta_brick_slab", chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		this.slabTop("white_terracotta_brick_slab", chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		this.slab("yellow_terracotta_brick_slab", chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		this.slabTop("yellow_terracotta_brick_slab", chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		this.stairs("black_terracotta_brick_stairs", chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		this.stairsInner("black_terracotta_brick_stairs", chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		this.stairsOuter("black_terracotta_brick_stairs", chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"), chaosRL("black_terracotta_bricks"));
		this.stairs("blue_terracotta_brick_stairs", chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		this.stairsInner("blue_terracotta_brick_stairs", chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		this.stairsOuter("blue_terracotta_brick_stairs", chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"), chaosRL("blue_terracotta_bricks"));
		this.stairs("brown_terracotta_brick_stairs", chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		this.stairsInner("brown_terracotta_brick_stairs", chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		this.stairsOuter("brown_terracotta_brick_stairs", chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"), chaosRL("brown_terracotta_bricks"));
		this.stairs("cyan_terracotta_brick_stairs", chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		this.stairsInner("cyan_terracotta_brick_stairs", chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		this.stairsOuter("cyan_terracotta_brick_stairs", chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"), chaosRL("cyan_terracotta_bricks"));
		this.stairs("gray_terracotta_brick_stairs", chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		this.stairsInner("gray_terracotta_brick_stairs", chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		this.stairsOuter("gray_terracotta_brick_stairs", chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"), chaosRL("gray_terracotta_bricks"));
		this.stairs("green_terracotta_brick_stairs", chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		this.stairsInner("green_terracotta_brick_stairs", chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		this.stairsOuter("green_terracotta_brick_stairs", chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"), chaosRL("green_terracotta_bricks"));
		this.stairs("light_blue_terracotta_brick_stairs", chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		this.stairsInner("light_blue_terracotta_brick_stairs", chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		this.stairsOuter("light_blue_terracotta_brick_stairs", chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"), chaosRL("light_blue_terracotta_bricks"));
		this.stairs("light_gray_terracotta_brick_stairs", chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		this.stairsInner("light_gray_terracotta_brick_stairs", chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		this.stairsOuter("light_gray_terracotta_brick_stairs", chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"), chaosRL("light_gray_terracotta_bricks"));
		this.stairs("lime_terracotta_brick_stairs", chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		this.stairsInner("lime_terracotta_brick_stairs", chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		this.stairsOuter("lime_terracotta_brick_stairs", chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"), chaosRL("lime_terracotta_bricks"));
		this.stairs("magenta_terracotta_brick_stairs", chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		this.stairsInner("magenta_terracotta_brick_stairs", chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		this.stairsOuter("magenta_terracotta_brick_stairs", chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"), chaosRL("magenta_terracotta_bricks"));
		this.stairs("orange_terracotta_brick_stairs", chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		this.stairsInner("orange_terracotta_brick_stairs", chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		this.stairsOuter("orange_terracotta_brick_stairs", chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"), chaosRL("orange_terracotta_bricks"));
		this.stairs("pink_terracotta_brick_stairs", chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		this.stairsInner("pink_terracotta_brick_stairs", chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		this.stairsOuter("pink_terracotta_brick_stairs", chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"), chaosRL("pink_terracotta_bricks"));
		this.stairs("purple_terracotta_brick_stairs", chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		this.stairsInner("purple_terracotta_brick_stairs", chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		this.stairsOuter("purple_terracotta_brick_stairs", chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"), chaosRL("purple_terracotta_bricks"));
		this.stairs("red_terracotta_brick_stairs", chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		this.stairsInner("red_terracotta_brick_stairs", chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		this.stairsOuter("red_terracotta_brick_stairs", chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"), chaosRL("red_terracotta_bricks"));
		this.stairs("terracotta_brick_stairs", chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		this.stairsInner("terracotta_brick_stairs", chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		this.stairsOuter("terracotta_brick_stairs", chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"), chaosRL("terracotta_bricks"));
		this.stairs("white_terracotta_brick_stairs", chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		this.stairsInner("white_terracotta_brick_stairs", chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		this.stairsOuter("white_terracotta_brick_stairs", chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"), chaosRL("white_terracotta_bricks"));
		this.stairs("yellow_terracotta_brick_stairs", chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		this.stairsInner("yellow_terracotta_brick_stairs", chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		this.stairsOuter("yellow_terracotta_brick_stairs", chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"), chaosRL("yellow_terracotta_bricks"));
		this.wallPost("black_terracotta_brick_wall", chaosRL("black_terracotta_bricks"));
		this.wallInventory("black_terracotta_brick_wall_inventory", chaosRL("black_terracotta_bricks"));
		this.wallSide("black_terracotta_brick_wall", chaosRL("black_terracotta_bricks"));
		this.wallSideTall("black_terracotta_brick_wall", chaosRL("black_terracotta_bricks"));
		this.wallPost("blue_terracotta_brick_wall", chaosRL("blue_terracotta_bricks"));
		this.wallInventory("blue_terracotta_brick_wall_inventory", chaosRL("blue_terracotta_bricks"));
		this.wallSide("blue_terracotta_brick_wall", chaosRL("blue_terracotta_bricks"));
		this.wallSideTall("blue_terracotta_brick_wall", chaosRL("blue_terracotta_bricks"));
		this.wallPost("brown_terracotta_brick_wall", chaosRL("brown_terracotta_bricks"));
		this.wallInventory("brown_terracotta_brick_wall_inventory", chaosRL("brown_terracotta_bricks"));
		this.wallSide("brown_terracotta_brick_wall", chaosRL("brown_terracotta_bricks"));
		this.wallSideTall("brown_terracotta_brick_wall", chaosRL("brown_terracotta_bricks"));
		this.wallPost("cyan_terracotta_brick_wall", chaosRL("cyan_terracotta_bricks"));
		this.wallInventory("cyan_terracotta_brick_wall_inventory", chaosRL("cyan_terracotta_bricks"));
		this.wallSide("cyan_terracotta_brick_wall", chaosRL("cyan_terracotta_bricks"));
		this.wallSideTall("cyan_terracotta_brick_wall", chaosRL("cyan_terracotta_bricks"));
		this.wallPost("gray_terracotta_brick_wall", chaosRL("gray_terracotta_bricks"));
		this.wallInventory("gray_terracotta_brick_wall_inventory", chaosRL("gray_terracotta_bricks"));
		this.wallSide("gray_terracotta_brick_wall", chaosRL("gray_terracotta_bricks"));
		this.wallSideTall("gray_terracotta_brick_wall", chaosRL("gray_terracotta_bricks"));
		this.wallPost("green_terracotta_brick_wall", chaosRL("green_terracotta_bricks"));
		this.wallInventory("green_terracotta_brick_wall_inventory", chaosRL("green_terracotta_bricks"));
		this.wallSide("green_terracotta_brick_wall", chaosRL("green_terracotta_bricks"));
		this.wallSideTall("green_terracotta_brick_wall", chaosRL("green_terracotta_bricks"));
		this.wallPost("light_blue_terracotta_brick_wall", chaosRL("light_blue_terracotta_bricks"));
		this.wallInventory("light_blue_terracotta_brick_wall_inventory", chaosRL("light_blue_terracotta_bricks"));
		this.wallSide("light_blue_terracotta_brick_wall", chaosRL("light_blue_terracotta_bricks"));
		this.wallSideTall("light_blue_terracotta_brick_wall", chaosRL("light_blue_terracotta_bricks"));
		this.wallPost("light_gray_terracotta_brick_wall", chaosRL("light_gray_terracotta_bricks"));
		this.wallInventory("light_gray_terracotta_brick_wall_inventory", chaosRL("light_gray_terracotta_bricks"));
		this.wallSide("light_gray_terracotta_brick_wall", chaosRL("light_gray_terracotta_bricks"));
		this.wallSideTall("light_gray_terracotta_brick_wall", chaosRL("light_gray_terracotta_bricks"));
		this.wallPost("lime_terracotta_brick_wall", chaosRL("lime_terracotta_bricks"));
		this.wallInventory("lime_terracotta_brick_wall_inventory", chaosRL("lime_terracotta_bricks"));
		this.wallSide("lime_terracotta_brick_wall", chaosRL("lime_terracotta_bricks"));
		this.wallSideTall("lime_terracotta_brick_wall", chaosRL("lime_terracotta_bricks"));
		this.wallPost("magenta_terracotta_brick_wall", chaosRL("magenta_terracotta_bricks"));
		this.wallInventory("magenta_terracotta_brick_wall_inventory", chaosRL("magenta_terracotta_bricks"));
		this.wallSide("magenta_terracotta_brick_wall", chaosRL("magenta_terracotta_bricks"));
		this.wallSideTall("magenta_terracotta_brick_wall", chaosRL("magenta_terracotta_bricks"));
		this.wallPost("orange_terracotta_brick_wall", chaosRL("orange_terracotta_bricks"));
		this.wallInventory("orange_terracotta_brick_wall_inventory", chaosRL("orange_terracotta_bricks"));
		this.wallSide("orange_terracotta_brick_wall", chaosRL("orange_terracotta_bricks"));
		this.wallSideTall("orange_terracotta_brick_wall", chaosRL("orange_terracotta_bricks"));
		this.wallPost("pink_terracotta_brick_wall", chaosRL("pink_terracotta_bricks"));
		this.wallInventory("pink_terracotta_brick_wall_inventory", chaosRL("pink_terracotta_bricks"));
		this.wallSide("pink_terracotta_brick_wall", chaosRL("pink_terracotta_bricks"));
		this.wallSideTall("pink_terracotta_brick_wall", chaosRL("pink_terracotta_bricks"));
		this.wallPost("purple_terracotta_brick_wall", chaosRL("purple_terracotta_bricks"));
		this.wallInventory("purple_terracotta_brick_wall_inventory", chaosRL("purple_terracotta_bricks"));
		this.wallSide("purple_terracotta_brick_wall", chaosRL("purple_terracotta_bricks"));
		this.wallSideTall("purple_terracotta_brick_wall", chaosRL("purple_terracotta_bricks"));
		this.wallPost("red_terracotta_brick_wall", chaosRL("red_terracotta_bricks"));
		this.wallInventory("red_terracotta_brick_wall_inventory", chaosRL("red_terracotta_bricks"));
		this.wallSide("red_terracotta_brick_wall", chaosRL("red_terracotta_bricks"));
		this.wallSideTall("red_terracotta_brick_wall", chaosRL("red_terracotta_bricks"));
		this.wallPost("terracotta_brick_wall", chaosRL("terracotta_bricks"));
		this.wallInventory("terracotta_brick_wall_inventory", chaosRL("terracotta_bricks"));
		this.wallSide("terracotta_brick_wall", chaosRL("terracotta_bricks"));
		this.wallSideTall("terracotta_brick_wall", chaosRL("terracotta_bricks"));
		this.wallPost("white_terracotta_brick_wall", chaosRL("white_terracotta_bricks"));
		this.wallInventory("white_terracotta_brick_wall_inventory", chaosRL("white_terracotta_bricks"));
		this.wallSide("white_terracotta_brick_wall", chaosRL("white_terracotta_bricks"));
		this.wallSideTall("white_terracotta_brick_wall", chaosRL("white_terracotta_bricks"));
		this.wallPost("yellow_terracotta_brick_wall", chaosRL("yellow_terracotta_bricks"));
		this.wallInventory("yellow_terracotta_brick_wall_inventory", chaosRL("yellow_terracotta_bricks"));
		this.wallSide("yellow_terracotta_brick_wall", chaosRL("yellow_terracotta_bricks"));
		this.wallSideTall("yellow_terracotta_brick_wall", chaosRL("yellow_terracotta_bricks"));

		this.cubeAll("cracked_black_terracotta_bricks", chaosRL("cracked_black_terracotta_bricks"));
		this.cubeAll("cracked_blue_terracotta_bricks", chaosRL("cracked_blue_terracotta_bricks"));
		this.cubeAll("cracked_brown_terracotta_bricks", chaosRL("cracked_brown_terracotta_bricks"));
		this.cubeAll("cracked_cyan_terracotta_bricks", chaosRL("cracked_cyan_terracotta_bricks"));
		this.cubeAll("cracked_gray_terracotta_bricks", chaosRL("cracked_gray_terracotta_bricks"));
		this.cubeAll("cracked_green_terracotta_bricks", chaosRL("cracked_green_terracotta_bricks"));
		this.cubeAll("cracked_light_blue_terracotta_bricks", chaosRL("cracked_light_blue_terracotta_bricks"));
		this.cubeAll("cracked_light_gray_terracotta_bricks", chaosRL("cracked_light_gray_terracotta_bricks"));
		this.cubeAll("cracked_lime_terracotta_bricks", chaosRL("cracked_lime_terracotta_bricks"));
		this.cubeAll("cracked_magenta_terracotta_bricks", chaosRL("cracked_magenta_terracotta_bricks"));
		this.cubeAll("cracked_orange_terracotta_bricks", chaosRL("cracked_orange_terracotta_bricks"));
		this.cubeAll("cracked_pink_terracotta_bricks", chaosRL("cracked_pink_terracotta_bricks"));
		this.cubeAll("cracked_purple_terracotta_bricks", chaosRL("cracked_purple_terracotta_bricks"));
		this.cubeAll("cracked_red_terracotta_bricks", chaosRL("cracked_red_terracotta_bricks"));
		this.cubeAll("cracked_terracotta_bricks", chaosRL("cracked_terracotta_bricks"));
		this.cubeAll("cracked_white_terracotta_bricks", chaosRL("cracked_white_terracotta_bricks"));
		this.cubeAll("cracked_yellow_terracotta_bricks", chaosRL("cracked_yellow_terracotta_bricks"));
		this.slab("cracked_black_terracotta_brick_slab", chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		this.slabTop("cracked_black_terracotta_brick_slab", chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		this.slab("cracked_blue_terracotta_brick_slab", chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		this.slabTop("cracked_blue_terracotta_brick_slab", chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		this.slab("cracked_brown_terracotta_brick_slab", chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		this.slabTop("cracked_brown_terracotta_brick_slab", chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		this.slab("cracked_cyan_terracotta_brick_slab", chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		this.slabTop("cracked_cyan_terracotta_brick_slab", chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		this.slab("cracked_gray_terracotta_brick_slab", chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		this.slabTop("cracked_gray_terracotta_brick_slab", chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		this.slab("cracked_green_terracotta_brick_slab", chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		this.slabTop("cracked_green_terracotta_brick_slab", chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		this.slab("cracked_light_blue_terracotta_brick_slab", chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		this.slabTop("cracked_light_blue_terracotta_brick_slab", chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		this.slab("cracked_light_gray_terracotta_brick_slab", chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		this.slabTop("cracked_light_gray_terracotta_brick_slab", chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		this.slab("cracked_lime_terracotta_brick_slab", chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		this.slabTop("cracked_lime_terracotta_brick_slab", chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		this.slab("cracked_magenta_terracotta_brick_slab", chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		this.slabTop("cracked_magenta_terracotta_brick_slab", chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		this.slab("cracked_orange_terracotta_brick_slab", chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		this.slabTop("cracked_orange_terracotta_brick_slab", chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		this.slab("cracked_pink_terracotta_brick_slab", chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		this.slabTop("cracked_pink_terracotta_brick_slab", chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		this.slab("cracked_purple_terracotta_brick_slab", chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		this.slabTop("cracked_purple_terracotta_brick_slab", chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		this.slab("cracked_red_terracotta_brick_slab", chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		this.slabTop("cracked_red_terracotta_brick_slab", chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		this.slab("cracked_terracotta_brick_slab", chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		this.slabTop("cracked_terracotta_brick_slab", chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		this.slab("cracked_white_terracotta_brick_slab", chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		this.slabTop("cracked_white_terracotta_brick_slab", chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		this.slab("cracked_yellow_terracotta_brick_slab", chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		this.slabTop("cracked_yellow_terracotta_brick_slab", chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		this.stairs("cracked_black_terracotta_brick_stairs", chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		this.stairsInner("cracked_black_terracotta_brick_stairs", chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		this.stairsOuter("cracked_black_terracotta_brick_stairs", chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"), chaosRL("cracked_black_terracotta_bricks"));
		this.stairs("cracked_blue_terracotta_brick_stairs", chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		this.stairsInner("cracked_blue_terracotta_brick_stairs", chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		this.stairsOuter("cracked_blue_terracotta_brick_stairs", chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"), chaosRL("cracked_blue_terracotta_bricks"));
		this.stairs("cracked_brown_terracotta_brick_stairs", chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		this.stairsInner("cracked_brown_terracotta_brick_stairs", chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		this.stairsOuter("cracked_brown_terracotta_brick_stairs", chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"), chaosRL("cracked_brown_terracotta_bricks"));
		this.stairs("cracked_cyan_terracotta_brick_stairs", chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		this.stairsInner("cracked_cyan_terracotta_brick_stairs", chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		this.stairsOuter("cracked_cyan_terracotta_brick_stairs", chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"), chaosRL("cracked_cyan_terracotta_bricks"));
		this.stairs("cracked_gray_terracotta_brick_stairs", chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		this.stairsInner("cracked_gray_terracotta_brick_stairs", chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		this.stairsOuter("cracked_gray_terracotta_brick_stairs", chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"), chaosRL("cracked_gray_terracotta_bricks"));
		this.stairs("cracked_green_terracotta_brick_stairs", chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		this.stairsInner("cracked_green_terracotta_brick_stairs", chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		this.stairsOuter("cracked_green_terracotta_brick_stairs", chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"), chaosRL("cracked_green_terracotta_bricks"));
		this.stairs("cracked_light_blue_terracotta_brick_stairs", chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		this.stairsInner("cracked_light_blue_terracotta_brick_stairs", chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		this.stairsOuter("cracked_light_blue_terracotta_brick_stairs", chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"), chaosRL("cracked_light_blue_terracotta_bricks"));
		this.stairs("cracked_light_gray_terracotta_brick_stairs", chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		this.stairsInner("cracked_light_gray_terracotta_brick_stairs", chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		this.stairsOuter("cracked_light_gray_terracotta_brick_stairs", chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"), chaosRL("cracked_light_gray_terracotta_bricks"));
		this.stairs("cracked_lime_terracotta_brick_stairs", chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		this.stairsInner("cracked_lime_terracotta_brick_stairs", chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		this.stairsOuter("cracked_lime_terracotta_brick_stairs", chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"), chaosRL("cracked_lime_terracotta_bricks"));
		this.stairs("cracked_magenta_terracotta_brick_stairs", chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		this.stairsInner("cracked_magenta_terracotta_brick_stairs", chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		this.stairsOuter("cracked_magenta_terracotta_brick_stairs", chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"), chaosRL("cracked_magenta_terracotta_bricks"));
		this.stairs("cracked_orange_terracotta_brick_stairs", chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		this.stairsInner("cracked_orange_terracotta_brick_stairs", chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		this.stairsOuter("cracked_orange_terracotta_brick_stairs", chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"), chaosRL("cracked_orange_terracotta_bricks"));
		this.stairs("cracked_pink_terracotta_brick_stairs", chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		this.stairsInner("cracked_pink_terracotta_brick_stairs", chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		this.stairsOuter("cracked_pink_terracotta_brick_stairs", chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"), chaosRL("cracked_pink_terracotta_bricks"));
		this.stairs("cracked_purple_terracotta_brick_stairs", chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		this.stairsInner("cracked_purple_terracotta_brick_stairs", chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		this.stairsOuter("cracked_purple_terracotta_brick_stairs", chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"), chaosRL("cracked_purple_terracotta_bricks"));
		this.stairs("cracked_red_terracotta_brick_stairs", chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		this.stairsInner("cracked_red_terracotta_brick_stairs", chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		this.stairsOuter("cracked_red_terracotta_brick_stairs", chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"), chaosRL("cracked_red_terracotta_bricks"));
		this.stairs("cracked_terracotta_brick_stairs", chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		this.stairsInner("cracked_terracotta_brick_stairs", chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		this.stairsOuter("cracked_terracotta_brick_stairs", chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"), chaosRL("cracked_terracotta_bricks"));
		this.stairs("cracked_white_terracotta_brick_stairs", chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		this.stairsInner("cracked_white_terracotta_brick_stairs", chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		this.stairsOuter("cracked_white_terracotta_brick_stairs", chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"), chaosRL("cracked_white_terracotta_bricks"));
		this.stairs("cracked_yellow_terracotta_brick_stairs", chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		this.stairsInner("cracked_yellow_terracotta_brick_stairs", chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		this.stairsOuter("cracked_yellow_terracotta_brick_stairs", chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"), chaosRL("cracked_yellow_terracotta_bricks"));
		this.wallPost("cracked_black_terracotta_brick_wall", chaosRL("cracked_black_terracotta_bricks"));
		this.wallInventory("cracked_black_terracotta_brick_wall_inventory", chaosRL("cracked_black_terracotta_bricks"));
		this.wallSide("cracked_black_terracotta_brick_wall", chaosRL("cracked_black_terracotta_bricks"));
		this.wallSideTall("cracked_black_terracotta_brick_wall", chaosRL("cracked_black_terracotta_bricks"));
		this.wallPost("cracked_blue_terracotta_brick_wall", chaosRL("cracked_blue_terracotta_bricks"));
		this.wallInventory("cracked_blue_terracotta_brick_wall_inventory", chaosRL("cracked_blue_terracotta_bricks"));
		this.wallSide("cracked_blue_terracotta_brick_wall", chaosRL("cracked_blue_terracotta_bricks"));
		this.wallSideTall("cracked_blue_terracotta_brick_wall", chaosRL("cracked_blue_terracotta_bricks"));
		this.wallPost("cracked_brown_terracotta_brick_wall", chaosRL("cracked_brown_terracotta_bricks"));
		this.wallInventory("cracked_brown_terracotta_brick_wall_inventory", chaosRL("cracked_brown_terracotta_bricks"));
		this.wallSide("cracked_brown_terracotta_brick_wall", chaosRL("cracked_brown_terracotta_bricks"));
		this.wallSideTall("cracked_brown_terracotta_brick_wall", chaosRL("cracked_brown_terracotta_bricks"));
		this.wallPost("cracked_cyan_terracotta_brick_wall", chaosRL("cracked_cyan_terracotta_bricks"));
		this.wallInventory("cracked_cyan_terracotta_brick_wall_inventory", chaosRL("cracked_cyan_terracotta_bricks"));
		this.wallSide("cracked_cyan_terracotta_brick_wall", chaosRL("cracked_cyan_terracotta_bricks"));
		this.wallSideTall("cracked_cyan_terracotta_brick_wall", chaosRL("cracked_cyan_terracotta_bricks"));
		this.wallPost("cracked_gray_terracotta_brick_wall", chaosRL("cracked_gray_terracotta_bricks"));
		this.wallInventory("cracked_gray_terracotta_brick_wall_inventory", chaosRL("cracked_gray_terracotta_bricks"));
		this.wallSide("cracked_gray_terracotta_brick_wall", chaosRL("cracked_gray_terracotta_bricks"));
		this.wallSideTall("cracked_gray_terracotta_brick_wall", chaosRL("cracked_gray_terracotta_bricks"));
		this.wallPost("cracked_green_terracotta_brick_wall", chaosRL("cracked_green_terracotta_bricks"));
		this.wallInventory("cracked_green_terracotta_brick_wall_inventory", chaosRL("cracked_green_terracotta_bricks"));
		this.wallSide("cracked_green_terracotta_brick_wall", chaosRL("cracked_green_terracotta_bricks"));
		this.wallSideTall("cracked_green_terracotta_brick_wall", chaosRL("cracked_green_terracotta_bricks"));
		this.wallPost("cracked_light_blue_terracotta_brick_wall", chaosRL("cracked_light_blue_terracotta_bricks"));
		this.wallInventory("cracked_light_blue_terracotta_brick_wall_inventory", chaosRL("cracked_light_blue_terracotta_bricks"));
		this.wallSide("cracked_light_blue_terracotta_brick_wall", chaosRL("cracked_light_blue_terracotta_bricks"));
		this.wallSideTall("cracked_light_blue_terracotta_brick_wall", chaosRL("cracked_light_blue_terracotta_bricks"));
		this.wallPost("cracked_light_gray_terracotta_brick_wall", chaosRL("cracked_light_gray_terracotta_bricks"));
		this.wallInventory("cracked_light_gray_terracotta_brick_wall_inventory", chaosRL("cracked_light_gray_terracotta_bricks"));
		this.wallSide("cracked_light_gray_terracotta_brick_wall", chaosRL("cracked_light_gray_terracotta_bricks"));
		this.wallSideTall("cracked_light_gray_terracotta_brick_wall", chaosRL("cracked_light_gray_terracotta_bricks"));
		this.wallPost("cracked_lime_terracotta_brick_wall", chaosRL("cracked_lime_terracotta_bricks"));
		this.wallInventory("cracked_lime_terracotta_brick_wall_inventory", chaosRL("cracked_lime_terracotta_bricks"));
		this.wallSide("cracked_lime_terracotta_brick_wall", chaosRL("cracked_lime_terracotta_bricks"));
		this.wallSideTall("cracked_lime_terracotta_brick_wall", chaosRL("cracked_lime_terracotta_bricks"));
		this.wallPost("cracked_magenta_terracotta_brick_wall", chaosRL("cracked_magenta_terracotta_bricks"));
		this.wallInventory("cracked_magenta_terracotta_brick_wall_inventory", chaosRL("cracked_magenta_terracotta_bricks"));
		this.wallSide("cracked_magenta_terracotta_brick_wall", chaosRL("cracked_magenta_terracotta_bricks"));
		this.wallSideTall("cracked_magenta_terracotta_brick_wall", chaosRL("cracked_magenta_terracotta_bricks"));
		this.wallPost("cracked_orange_terracotta_brick_wall", chaosRL("cracked_orange_terracotta_bricks"));
		this.wallInventory("cracked_orange_terracotta_brick_wall_inventory", chaosRL("cracked_orange_terracotta_bricks"));
		this.wallSide("cracked_orange_terracotta_brick_wall", chaosRL("cracked_orange_terracotta_bricks"));
		this.wallSideTall("cracked_orange_terracotta_brick_wall", chaosRL("cracked_orange_terracotta_bricks"));
		this.wallPost("cracked_pink_terracotta_brick_wall", chaosRL("cracked_pink_terracotta_bricks"));
		this.wallInventory("cracked_pink_terracotta_brick_wall_inventory", chaosRL("cracked_pink_terracotta_bricks"));
		this.wallSide("cracked_pink_terracotta_brick_wall", chaosRL("cracked_pink_terracotta_bricks"));
		this.wallSideTall("cracked_pink_terracotta_brick_wall", chaosRL("cracked_pink_terracotta_bricks"));
		this.wallPost("cracked_purple_terracotta_brick_wall", chaosRL("cracked_purple_terracotta_bricks"));
		this.wallInventory("cracked_purple_terracotta_brick_wall_inventory", chaosRL("cracked_purple_terracotta_bricks"));
		this.wallSide("cracked_purple_terracotta_brick_wall", chaosRL("cracked_purple_terracotta_bricks"));
		this.wallSideTall("cracked_purple_terracotta_brick_wall", chaosRL("cracked_purple_terracotta_bricks"));
		this.wallPost("cracked_red_terracotta_brick_wall", chaosRL("cracked_red_terracotta_bricks"));
		this.wallInventory("cracked_red_terracotta_brick_wall_inventory", chaosRL("cracked_red_terracotta_bricks"));
		this.wallSide("cracked_red_terracotta_brick_wall", chaosRL("cracked_red_terracotta_bricks"));
		this.wallSideTall("cracked_red_terracotta_brick_wall", chaosRL("cracked_red_terracotta_bricks"));
		this.wallPost("cracked_terracotta_brick_wall", chaosRL("cracked_terracotta_bricks"));
		this.wallInventory("cracked_terracotta_brick_wall_inventory", chaosRL("cracked_terracotta_bricks"));
		this.wallSide("cracked_terracotta_brick_wall", chaosRL("cracked_terracotta_bricks"));
		this.wallSideTall("cracked_terracotta_brick_wall", chaosRL("cracked_terracotta_bricks"));
		this.wallPost("cracked_white_terracotta_brick_wall", chaosRL("cracked_white_terracotta_bricks"));
		this.wallInventory("cracked_white_terracotta_brick_wall_inventory", chaosRL("cracked_white_terracotta_bricks"));
		this.wallSide("cracked_white_terracotta_brick_wall", chaosRL("cracked_white_terracotta_bricks"));
		this.wallSideTall("cracked_white_terracotta_brick_wall", chaosRL("cracked_white_terracotta_bricks"));
		this.wallPost("cracked_yellow_terracotta_brick_wall", chaosRL("cracked_yellow_terracotta_bricks"));
		this.wallInventory("cracked_yellow_terracotta_brick_wall_inventory", chaosRL("cracked_yellow_terracotta_bricks"));
		this.wallSide("cracked_yellow_terracotta_brick_wall", chaosRL("cracked_yellow_terracotta_bricks"));
		this.wallSideTall("cracked_yellow_terracotta_brick_wall", chaosRL("cracked_yellow_terracotta_bricks"));

		this.cubeColumn("flower_stem", chaosRL("flower_stem"), chaosRL("flower_stem_top"));
		this.cubeColumnHorizontal("flower_stem", chaosRL("flower_stem"), chaosRL("flower_stem_top"));
		this.cubeAll("navy_blue_petal_block", chaosRL("navy_blue_petal_block"));
		this.cubeAll("blood_red_petal_block", chaosRL("blood_red_petal_block"));
		this.cubeAll("bright_pink_petal_block", chaosRL("bright_pink_petal_block"));

		this.cubeAll("moth_scale_block", chaosRL("moth_scale_block"));
		this.cubeAll("water_dragon_scale_block", chaosRL("water_dragon_scale_block"));
		this.cubeAll("ender_dragon_scale_block", chaosRL("ender_dragon_scale_block"));
		this.cubeAll("nightmare_scale_block", chaosRL("nightmare_scale_block"));
		this.cubeAll("mobzilla_scale_block", chaosRL("mobzilla_scale_block"));
		this.cubeAll("royal_guardian_scale_block", chaosRL("royal_guardian_scale_block"));
		this.cubeAll("queen_scale_block", chaosRL("queen_scale_block"));
		this.cubeAll("basilisk_scale_block", chaosRL("basilisk_scale_block"));
		this.cubeAll("emperor_scorpion_scale_block", chaosRL("emperor_scorpion_scale_block"));

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
		this.cubeColumn("ginkgo_log", chaosRL("ginkgo_log"), chaosRL("ginkgo_log_top"));
		this.cubeColumnHorizontal("ginkgo_log", chaosRL("ginkgo_log"), chaosRL("ginkgo_log_top"));
		this.cubeColumn("ginkgo_wood", chaosRL("ginkgo_log"), chaosRL("ginkgo_log"));
		this.cubeColumnHorizontal("ginkgo_wood", chaosRL("ginkgo_log"), chaosRL("ginkgo_log"));
		this.cubeColumn("stripped_ginkgo_log", chaosRL("stripped_ginkgo_log"), chaosRL("stripped_ginkgo_log_top"));
		this.cubeColumnHorizontal("stripped_ginkgo_log", chaosRL("stripped_ginkgo_log"), chaosRL("stripped_ginkgo_log_top"));
		this.cubeColumn("stripped_ginkgo_wood", chaosRL("stripped_ginkgo_log"), chaosRL("stripped_ginkgo_log"));
		this.cubeColumnHorizontal("stripped_ginkgo_wood", chaosRL("stripped_ginkgo_log"), chaosRL("stripped_ginkgo_log"));
		this.cubeColumn("hirmeriella_log", chaosRL("hirmeriella_log"), chaosRL("hirmeriella_log_top"));
		this.cubeColumnHorizontal("hirmeriella_log", chaosRL("hirmeriella_log"), chaosRL("hirmeriella_log_top"));
		this.cubeColumn("densewood_log", chaosRL("densewood_log"), chaosRL("densewood_log_top"));
		this.cubeColumnHorizontal("densewood_log", chaosRL("densewood_log"), chaosRL("densewood_log_top"));
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
		this.cubeColumn("stripped_dead_duplication_log", chaosRL("stripped_dead_duplication_log"), chaosRL("stripped_dead_duplication_log_top"));
		this.cubeColumnHorizontal("stripped_dead_duplication_log", chaosRL("stripped_dead_duplication_log"), chaosRL("stripped_dead_duplication_log_top"));
		this.cubeColumn("stripped_dead_duplication_wood", chaosRL("stripped_dead_duplication_log"), chaosRL("stripped_dead_duplication_log"));
		this.cubeColumnHorizontal("stripped_dead_duplication_wood", chaosRL("stripped_dead_duplication_log"), chaosRL("stripped_dead_duplication_log"));
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
		this.leafCarpet("apple_leaf_carpet", chaosRL("apple_leaves"));
		this.leafCarpetInventory("apple_leaf_carpet_inventory", chaosRL("apple_leaves"));
		this.cubeAll("apple_leaves_ripe", chaosRL("apple_leaves_ripe"));
		this.cross("apple_sapling", chaosRL("apple_sapling"));
		this.cubeAll("cherry_planks", chaosRL("cherry_planks"));
		this.cubeAll("cherry_leaves", chaosRL("cherry_leaves"));
		this.leafCarpet("cherry_leaf_carpet", chaosRL("cherry_leaves"));
		this.leafCarpetInventory("cherry_leaf_carpet_inventory", chaosRL("cherry_leaves"));
		this.cubeAll("cherry_leaves_ripe", chaosRL("cherry_leaves_ripe"));
		this.cross("cherry_sapling", chaosRL("cherry_sapling"));
		this.cubeAll("duplication_planks", chaosRL("duplication_planks"));
		this.cubeAll("duplication_leaves", chaosRL("duplication_leaves"));
		this.leafCarpet("duplication_leaf_carpet", chaosRL("duplication_leaves"));
		this.leafCarpetInventory("duplication_leaf_carpet_inventory", chaosRL("duplication_leaves"));
		this.cross("ginkgo_sapling", chaosRL("ginkgo_sapling"));
		this.cubeAll("ginkgo_planks", chaosRL("ginkgo_planks"));
		this.cubeAll("ginkgo_leaves", chaosRL("ginkgo_leaves"));
		this.leafCarpet("ginkgo_leaf_carpet", chaosRL("ginkgo_leaves"));
		this.leafCarpetInventory("ginkgo_leaf_carpet_inventory", chaosRL("ginkgo_leaves"));
		this.cross("hirmeriella_sapling", chaosRL("hirmeriella_sapling"));
		this.cubeAll("hirmeriella_planks", chaosRL("hirmeriella_planks"));
		this.cubeAll("hirmeriella_leaves", chaosRL("hirmeriella_leaves"));
		this.leafCarpet("hirmeriella_leaf_carpet", chaosRL("hirmeriella_leaves"));
		this.leafCarpetInventory("hirmeriella_leaf_carpet_inventory", chaosRL("hirmeriella_leaves"));
		this.cross("densewood_sapling", chaosRL("densewood_sapling"));
		this.cubeAll("densewood_leaves", chaosRL("densewood_leaves"));
		this.leafCarpet("densewood_leaf_carpet", chaosRL("densewood_leaves"));
		this.leafCarpetInventory("densewood_leaf_carpet_inventory", chaosRL("densewood_leaves"));
		this.cubeAll("peach_planks", chaosRL("peach_planks"));
		this.cubeAll("peach_leaves", chaosRL("peach_leaves"));
		this.leafCarpet("peach_leaf_carpet", chaosRL("peach_leaves"));
		this.leafCarpetInventory("peach_leaf_carpet_inventory", chaosRL("peach_leaves"));
		this.cubeAll("peach_leaves_ripe", chaosRL("peach_leaves_ripe"));
		this.cross("peach_sapling", chaosRL("peach_sapling"));
		this.cubeAll("skywood_planks", chaosRL("skywood_planks"));
		this.cubeAll("skywood_leaves", chaosRL("skywood_leaves"));
		this.leafCarpet("skywood_leaf_carpet", chaosRL("skywood_leaves"));
		this.leafCarpetInventory("skywood_leaf_carpet_inventory", chaosRL("skywood_leaves"));
		this.cubeAll("crystal_planks", chaosRL("crystal_planks"));
		this.cross("red_crystal_sapling", chaosRL("red_crystal_sapling"));
		this.cross("green_crystal_sapling", chaosRL("green_crystal_sapling"));
		this.cross("yellow_crystal_sapling", chaosRL("yellow_crystal_sapling"));
		this.cross("pink_crystal_sapling", chaosRL("pink_crystal_sapling"));
		this.cross("blue_crystal_sapling", chaosRL("blue_crystal_sapling"));
		this.cross("orange_crystal_sapling", chaosRL("orange_crystal_sapling"));

		this.cubeAll("sky_moss_block", chaosRL("sky_moss"));
		this.leafCarpet("sky_moss_carpet", chaosRL("sky_moss"));
		this.leafCarpetInventory("sky_moss_carpet_inventory", chaosRL("sky_moss"));

		this.leafCarpet("oak_leaf_carpet", mcRL("oak_leaves"));
		this.leafCarpetInventory("oak_leaf_carpet_inventory", mcRL("oak_leaves"));
		this.leafCarpet("spruce_leaf_carpet", mcRL("spruce_leaves"));
		this.leafCarpetInventory("spruce_leaf_carpet_inventory", mcRL("spruce_leaves"));
		this.leafCarpet("birch_leaf_carpet", mcRL("birch_leaves"));
		this.leafCarpetInventory("birch_leaf_carpet_inventory", mcRL("birch_leaves"));
		this.leafCarpet("jungle_leaf_carpet", mcRL("jungle_leaves"));
		this.leafCarpetInventory("jungle_leaf_carpet_inventory", mcRL("jungle_leaves"));
		this.leafCarpet("acacia_leaf_carpet", mcRL("acacia_leaves"));
		this.leafCarpetInventory("acacia_leaf_carpet_inventory", mcRL("acacia_leaves"));
		this.leafCarpet("dark_oak_leaf_carpet", mcRL("dark_oak_leaves"));
		this.leafCarpetInventory("dark_oak_leaf_carpet_inventory", mcRL("dark_oak_leaves"));

		this.stairs("apple_stairs", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		this.stairsInner("apple_stairs", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		this.stairsOuter("apple_stairs", chaosRL("apple_planks"), chaosRL("apple_planks"), chaosRL("apple_planks"));
		this.stairs("cherry_stairs", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		this.stairsInner("cherry_stairs", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		this.stairsOuter("cherry_stairs", chaosRL("cherry_planks"), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
		this.stairs("ginkgo_stairs", chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		this.stairsInner("ginkgo_stairs", chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		this.stairsOuter("ginkgo_stairs", chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		this.stairs("hirmeriella_stairs", chaosRL("hirmeriella_planks"), chaosRL("hirmeriella_planks"), chaosRL("hirmeriella_planks"));
		this.stairsInner("hirmeriella_stairs", chaosRL("hirmeriella_planks"), chaosRL("hirmeriella_planks"), chaosRL("hirmeriella_planks"));
		this.stairsOuter("hirmeriella_stairs", chaosRL("hirmeriella_planks"), chaosRL("hirmeriella_planks"), chaosRL("hirmeriella_planks"));
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
		this.slab("ginkgo_slab", chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		this.slabTop("ginkgo_slab", chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"), chaosRL("ginkgo_planks"));
		this.slab("hirmeriella_slab", chaosRL("hirmeriella_planks"), chaosRL("hirmeriella_planks"), chaosRL("hirmeriella_planks"));
		this.slabTop("hirmeriella_slab", chaosRL("hirmeriella_planks"), chaosRL("hirmeriella_planks"), chaosRL("hirmeriella_planks"));
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
		this.fenceInventory("apple_fence_inventory", chaosRL("apple_planks"));
		this.fenceSide("apple_fence", chaosRL("apple_planks"));
		this.fenceGate("cherry_fence_gate", chaosRL("cherry_planks"));
		this.fenceGateOpen("cherry_fence_gate", chaosRL("cherry_planks"));
		this.fenceGateWall("cherry_fence_gate", chaosRL("cherry_planks"));
		this.fenceGateWallOpen("cherry_fence_gate", chaosRL("cherry_planks"));
		this.fencePost("cherry_fence", chaosRL("cherry_planks"));
		this.fenceInventory("cherry_fence_inventory", chaosRL("cherry_planks"));
		this.fenceSide("cherry_fence", chaosRL("cherry_planks"));
		this.fenceGate("ginkgo_fence_gate", chaosRL("ginkgo_planks"));
		this.fenceGateOpen("ginkgo_fence_gate", chaosRL("ginkgo_planks"));
		this.fenceGateWall("ginkgo_fence_gate", chaosRL("ginkgo_planks"));
		this.fenceGateWallOpen("ginkgo_fence_gate", chaosRL("ginkgo_planks"));
		this.fencePost("ginkgo_fence", chaosRL("ginkgo_planks"));
		this.fenceInventory("ginkgo_fence_inventory", chaosRL("ginkgo_planks"));
		this.fenceSide("ginkgo_fence", chaosRL("ginkgo_planks"));
		this.fenceGate("hirmeriella_fence_gate", chaosRL("hirmeriella_planks"));
		this.fenceGateOpen("hirmeriella_fence_gate", chaosRL("hirmeriella_planks"));
		this.fenceGateWall("hirmeriella_fence_gate", chaosRL("hirmeriella_planks"));
		this.fenceGateWallOpen("hirmeriella_fence_gate", chaosRL("hirmeriella_planks"));
		this.fencePost("hirmeriella_fence", chaosRL("hirmeriella_planks"));
		this.fenceInventory("hirmeriella_fence_inventory", chaosRL("hirmeriella_planks"));
		this.fenceSide("hirmeriella_fence", chaosRL("hirmeriella_planks"));
		this.fenceGate("peach_fence_gate", chaosRL("peach_planks"));
		this.fenceGateOpen("peach_fence_gate", chaosRL("peach_planks"));
		this.fenceGateWall("peach_fence_gate", chaosRL("peach_planks"));
		this.fenceGateWallOpen("peach_fence_gate", chaosRL("peach_planks"));
		this.fencePost("peach_fence", chaosRL("peach_planks"));
		this.fenceInventory("peach_fence_inventory", chaosRL("peach_planks"));
		this.fenceSide("peach_fence", chaosRL("peach_planks"));
		this.fenceGate("duplication_fence_gate", chaosRL("duplication_planks"));
		this.fenceGateOpen("duplication_fence_gate", chaosRL("duplication_planks"));
		this.fenceGateWall("duplication_fence_gate", chaosRL("duplication_planks"));
		this.fenceGateWallOpen("duplication_fence_gate", chaosRL("duplication_planks"));
		this.fencePost("duplication_fence", chaosRL("duplication_planks"));
		this.fenceInventory("duplication_fence_inventory", chaosRL("duplication_planks"));
		this.fenceSide("duplication_fence", chaosRL("duplication_planks"));
		this.fenceGate("skywood_fence_gate", chaosRL("skywood_planks"));
		this.fenceGateOpen("skywood_fence_gate", chaosRL("skywood_planks"));
		this.fenceGateWall("skywood_fence_gate", chaosRL("skywood_planks"));
		this.fenceGateWallOpen("skywood_fence_gate", chaosRL("skywood_planks"));
		this.fencePost("skywood_fence", chaosRL("skywood_planks"));
		this.fenceInventory("skywood_fence_inventory", chaosRL("skywood_planks"));
		this.fenceSide("skywood_fence", chaosRL("skywood_planks"));
		this.fenceGate("crystal_fence_gate", chaosRL("crystal_planks"));
		this.fenceGateOpen("crystal_fence_gate", chaosRL("crystal_planks"));
		this.fenceGateWall("crystal_fence_gate", chaosRL("crystal_planks"));
		this.fenceGateWallOpen("crystal_fence_gate", chaosRL("crystal_planks"));
		this.fencePost("crystal_fence", chaosRL("crystal_planks"));
		this.fenceInventory("crystal_fence_inventory", chaosRL("crystal_planks"));
		this.fenceSide("crystal_fence", chaosRL("crystal_planks"));

		this.pressurePlateUp("apple_pressure_plate", chaosRL("apple_planks"));
		this.pressurePlateDown("apple_pressure_plate", chaosRL("apple_planks"));
		this.pressurePlateUp("cherry_pressure_plate", chaosRL("cherry_planks"));
		this.pressurePlateDown("cherry_pressure_plate", chaosRL("cherry_planks"));
		this.pressurePlateUp("ginkgo_pressure_plate", chaosRL("ginkgo_planks"));
		this.pressurePlateDown("ginkgo_pressure_plate", chaosRL("ginkgo_planks"));
		this.pressurePlateUp("hirmeriella_pressure_plate", chaosRL("hirmeriella_planks"));
		this.pressurePlateDown("hirmeriella_pressure_plate", chaosRL("hirmeriella_planks"));
		this.pressurePlateUp("duplication_pressure_plate", chaosRL("duplication_planks"));
		this.pressurePlateDown("duplication_pressure_plate", chaosRL("duplication_planks"));
		this.pressurePlateUp("peach_pressure_plate", chaosRL("peach_planks"));
		this.pressurePlateDown("peach_pressure_plate", chaosRL("peach_planks"));
		this.pressurePlateUp("skywood_pressure_plate", chaosRL("skywood_planks"));
		this.pressurePlateDown("skywood_pressure_plate", chaosRL("skywood_planks"));
		this.pressurePlateUp("crystal_pressure_plate", chaosRL("crystal_planks"));
		this.pressurePlateDown("crystal_pressure_plate", chaosRL("crystal_planks"));
		
		this.buttonInventory("apple_button_inventory", chaosRL("apple_planks"));
		this.buttonInventory("cherry_button_inventory", chaosRL("cherry_planks"));
		this.buttonInventory("ginkgo_button_inventory", chaosRL("ginkgo_planks"));
		this.buttonInventory("hirmeriella_button_inventory", chaosRL("hirmeriella_planks"));
		this.buttonInventory("duplication_button_inventory", chaosRL("duplication_planks"));
		this.buttonInventory("peach_button_inventory", chaosRL("peach_planks"));
		this.buttonInventory("skywood_button_inventory", chaosRL("skywood_planks"));
		this.buttonInventory("crystal_button_inventory", chaosRL("crystal_planks"));
		
		this.cubeAll("moldy_planks", chaosRL("moldy_planks"));
		this.slab("moldy_slab", chaosRL("moldy_planks"), chaosRL("moldy_planks"), chaosRL("moldy_planks"));
		this.slabTop("moldy_slab", chaosRL("moldy_planks"), chaosRL("moldy_planks"), chaosRL("moldy_planks"));
		this.fencePost("moldy_fence", chaosRL("moldy_planks"));
		this.fenceInventory("moldy_fence", chaosRL("moldy_planks"));
		this.fenceSide("moldy_fence", chaosRL("moldy_planks"));
		this.cubeAll("mining_lamp", chaosRL("mining_lamp"));

		this.cross("cyan_rose", chaosRL("cyan_rose"));
		this.cross("red_rose", chaosRL("red_rose"));
		this.cross("paeonia", chaosRL("paeonia"));
		this.cross("daisy", chaosRL("daisy"));
		this.cross("swamp_milkweed", chaosRL("swamp_milkweed"));
		this.cross("primrose", chaosRL("primrose"));
		this.cross("blue_crystal_growth", chaosRL("blue_crystal_growth"));
		this.cross("green_crystal_growth", chaosRL("green_crystal_growth"));
		this.cross("red_crystal_growth", chaosRL("red_crystal_growth"));
		this.cross("yellow_crystal_growth", chaosRL("yellow_crystal_growth"));
		this.cross("orange_crystal_growth", chaosRL("orange_crystal_growth"));
		this.cross("pink_crystal_growth", chaosRL("pink_crystal_growth"));
		this.cross("blue_crystal_flower", chaosRL("blue_crystal_flower"));
		this.cross("green_crystal_flower", chaosRL("green_crystal_flower"));
		this.cross("red_crystal_flower", chaosRL("red_crystal_flower"));
		this.cross("yellow_crystal_flower", chaosRL("yellow_crystal_flower"));
		this.cross("pink_crystal_flower", chaosRL("pink_crystal_flower"));
		this.cross("orange_crystal_flower", chaosRL("orange_crystal_flower"));
		this.cross("crystal_rose", chaosRL("crystal_rose"));
		
		this.pottedCross("potted_ginkgo_sapling", chaosRL("ginkgo_sapling"));
		this.pottedCross("potted_hirmeriella_sapling", chaosRL("hirmeriella_sapling"));
		this.pottedCross("potted_densewood_sapling", chaosRL("densewood_sapling"));
		this.pottedCross("potted_dense_orchid", chaosRL("dense_orchid"));
		this.pottedCross("potted_swamp_milkweed", chaosRL("swamp_milkweed"));
		this.pottedCross("potted_small_bush", chaosRL("small_bush"));
		this.pottedCross("potted_small_carnivorous_plant", chaosRL("small_carnivorous_plant"));
		this.pottedCross("potted_big_carnivorous_plant", chaosRL("big_carnivorous_plant"));
		this.pottedCross("potted_primrose", chaosRL("primrose"));
		this.pottedCross("potted_daisy", chaosRL("daisy"));
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
