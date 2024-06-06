package io.github.chaosawakens.data.provider;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerBlock.DefossilizerType;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CARecipeTypes;
import io.github.chaosawakens.common.registry.CATags;
import io.github.chaosawakens.data.builder.FossilRecipeBuilder;
import moze_intel.projecte.gameObjs.registries.PEItems;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import java.util.function.Consumer;

@SuppressWarnings("all")
public class CARecipeProvider extends RecipeProvider {
	
	public CARecipeProvider(DataGenerator gen) {
		super(gen);
	}
	
	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + ": Recipes";
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> recipeConsumer) {
		woodenPlanks(recipeConsumer, CABlocks.APPLE_PLANKS.get(), CATags.Items.APPLE_LOGS);
		woodenSlab(recipeConsumer, CABlocks.APPLE_SLAB.get(), CABlocks.APPLE_PLANKS.get());
		woodenStairs(recipeConsumer, CABlocks.APPLE_STAIRS.get(), CABlocks.APPLE_PLANKS.get());
		woodenTrapdoor(recipeConsumer, CABlocks.APPLE_TRAPDOOR.get(), CABlocks.APPLE_PLANKS.get());
		woodenDoor(recipeConsumer, CABlocks.APPLE_DOOR.get(), CABlocks.APPLE_PLANKS.get());
		woodenFence(recipeConsumer, CABlocks.APPLE_FENCE.get(), CABlocks.APPLE_PLANKS.get());
		woodenFenceGate(recipeConsumer, CABlocks.APPLE_FENCE_GATE.get(), CABlocks.APPLE_PLANKS.get());
		woodenPressurePlate(recipeConsumer, CABlocks.APPLE_PRESSURE_PLATE.get(), CABlocks.APPLE_PLANKS.get());
		woodenButton(recipeConsumer, CABlocks.APPLE_BUTTON.get(), CABlocks.APPLE_PLANKS.get());
		woodenWood(recipeConsumer, CABlocks.APPLE_WOOD.get(), CABlocks.APPLE_LOG.get());
		woodenSign(recipeConsumer, CAItems.APPLE_SIGN.get(), CABlocks.APPLE_PLANKS.get(), Items.STICK);
		woodenPlanks(recipeConsumer, CABlocks.CHERRY_PLANKS.get(), CATags.Items.CHERRY_LOGS);
		woodenSlab(recipeConsumer, CABlocks.CHERRY_SLAB.get(), CABlocks.CHERRY_PLANKS.get());
		woodenStairs(recipeConsumer, CABlocks.CHERRY_STAIRS.get(), CABlocks.CHERRY_PLANKS.get());
		woodenTrapdoor(recipeConsumer, CABlocks.CHERRY_TRAPDOOR.get(), CABlocks.CHERRY_PLANKS.get());
		woodenDoor(recipeConsumer, CABlocks.CHERRY_DOOR.get(), CABlocks.CHERRY_PLANKS.get());
		woodenFence(recipeConsumer, CABlocks.CHERRY_FENCE.get(), CABlocks.CHERRY_PLANKS.get());
		woodenFenceGate(recipeConsumer, CABlocks.CHERRY_FENCE_GATE.get(), CABlocks.CHERRY_PLANKS.get());
		woodenPressurePlate(recipeConsumer, CABlocks.CHERRY_PRESSURE_PLATE.get(), CABlocks.CHERRY_PLANKS.get());
		woodenButton(recipeConsumer, CABlocks.CHERRY_BUTTON.get(), CABlocks.CHERRY_PLANKS.get());
		woodenWood(recipeConsumer, CABlocks.CHERRY_WOOD.get(), CABlocks.CHERRY_LOG.get());
		woodenSign(recipeConsumer, CAItems.CHERRY_SIGN.get(), CABlocks.CHERRY_PLANKS.get(), Items.STICK);
		woodenPlanks(recipeConsumer, CABlocks.DUPLICATION_PLANKS.get(), CATags.Items.DUPLICATION_LOGS);
		woodenSlab(recipeConsumer, CABlocks.DUPLICATION_SLAB.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenStairs(recipeConsumer, CABlocks.DUPLICATION_STAIRS.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenTrapdoor(recipeConsumer, CABlocks.DUPLICATION_TRAPDOOR.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenDoor(recipeConsumer, CABlocks.DUPLICATION_DOOR.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenFence(recipeConsumer, CABlocks.DUPLICATION_FENCE.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenFenceGate(recipeConsumer, CABlocks.DUPLICATION_FENCE_GATE.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenPressurePlate(recipeConsumer, CABlocks.DUPLICATION_PRESSURE_PLATE.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenButton(recipeConsumer, CABlocks.DUPLICATION_BUTTON.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenWood(recipeConsumer, CABlocks.DUPLICATION_WOOD.get(), CABlocks.DUPLICATION_LOG.get());
		woodenSign(recipeConsumer, CAItems.DUPLICATION_SIGN.get(), CABlocks.DUPLICATION_PLANKS.get(), Items.STICK);
		woodenPlanks(recipeConsumer, CABlocks.GINKGO_PLANKS.get(), CATags.Items.GINKGO_LOGS);
		woodenSlab(recipeConsumer, CABlocks.GINKGO_SLAB.get(), CABlocks.GINKGO_PLANKS.get());
		woodenStairs(recipeConsumer, CABlocks.GINKGO_STAIRS.get(), CABlocks.GINKGO_PLANKS.get());
		woodenTrapdoor(recipeConsumer, CABlocks.GINKGO_TRAPDOOR.get(), CABlocks.GINKGO_PLANKS.get());
		woodenDoor(recipeConsumer, CABlocks.GINKGO_DOOR.get(), CABlocks.GINKGO_PLANKS.get());
		woodenFence(recipeConsumer, CABlocks.GINKGO_FENCE.get(), CABlocks.GINKGO_PLANKS.get());
		woodenFenceGate(recipeConsumer, CABlocks.GINKGO_FENCE_GATE.get(), CABlocks.GINKGO_PLANKS.get());
		woodenPressurePlate(recipeConsumer, CABlocks.GINKGO_PRESSURE_PLATE.get(), CABlocks.GINKGO_PLANKS.get());
		woodenButton(recipeConsumer, CABlocks.GINKGO_BUTTON.get(), CABlocks.GINKGO_PLANKS.get());
		woodenWood(recipeConsumer, CABlocks.GINKGO_WOOD.get(), CABlocks.GINKGO_LOG.get());
		woodenSign(recipeConsumer, CAItems.GINKGO_SIGN.get(), CABlocks.GINKGO_PLANKS.get(), Items.STICK);
		woodenPlanks(recipeConsumer, CABlocks.MESOZOIC_PLANKS.get(), CATags.Items.MESOZOIC_LOGS);
		woodenSlab(recipeConsumer, CABlocks.MESOZOIC_SLAB.get(), CABlocks.MESOZOIC_PLANKS.get());
		woodenStairs(recipeConsumer, CABlocks.MESOZOIC_STAIRS.get(), CABlocks.MESOZOIC_PLANKS.get());
		woodenTrapdoor(recipeConsumer, CABlocks.MESOZOIC_TRAPDOOR.get(), CABlocks.MESOZOIC_PLANKS.get());
		woodenDoor(recipeConsumer, CABlocks.MESOZOIC_DOOR.get(), CABlocks.MESOZOIC_PLANKS.get());
		woodenFence(recipeConsumer, CABlocks.MESOZOIC_FENCE.get(), CABlocks.MESOZOIC_PLANKS.get());
		woodenFenceGate(recipeConsumer, CABlocks.MESOZOIC_FENCE_GATE.get(), CABlocks.MESOZOIC_PLANKS.get());
		woodenPressurePlate(recipeConsumer, CABlocks.MESOZOIC_PRESSURE_PLATE.get(), CABlocks.MESOZOIC_PLANKS.get());
		woodenButton(recipeConsumer, CABlocks.MESOZOIC_BUTTON.get(), CABlocks.MESOZOIC_PLANKS.get());
		woodenWood(recipeConsumer, CABlocks.MESOZOIC_WOOD.get(), CABlocks.MESOZOIC_LOG.get());
		woodenPlanks(recipeConsumer, CABlocks.DENSEWOOD_PLANKS.get(), CATags.Items.DENSEWOOD_LOGS);
		woodenSlab(recipeConsumer, CABlocks.DENSEWOOD_SLAB.get(), CABlocks.DENSEWOOD_PLANKS.get());
		woodenStairs(recipeConsumer, CABlocks.DENSEWOOD_STAIRS.get(), CABlocks.DENSEWOOD_PLANKS.get());
		woodenTrapdoor(recipeConsumer, CABlocks.DENSEWOOD_TRAPDOOR.get(), CABlocks.DENSEWOOD_PLANKS.get());
		woodenDoor(recipeConsumer, CABlocks.DENSEWOOD_DOOR.get(), CABlocks.DENSEWOOD_PLANKS.get());
		woodenFence(recipeConsumer, CABlocks.DENSEWOOD_FENCE.get(), CABlocks.DENSEWOOD_PLANKS.get());
		woodenFenceGate(recipeConsumer, CABlocks.DENSEWOOD_FENCE_GATE.get(), CABlocks.DENSEWOOD_PLANKS.get());
		woodenPressurePlate(recipeConsumer, CABlocks.DENSEWOOD_PRESSURE_PLATE.get(), CABlocks.DENSEWOOD_PLANKS.get());
		woodenButton(recipeConsumer, CABlocks.DENSEWOOD_BUTTON.get(), CABlocks.DENSEWOOD_PLANKS.get());
		woodenWood(recipeConsumer, CABlocks.DENSEWOOD.get(), CABlocks.DENSEWOOD_LOG.get());
		woodenSign(recipeConsumer, CAItems.DENSEWOOD_SIGN.get(), CABlocks.DENSEWOOD_PLANKS.get(), Items.STICK);
		woodenPlanks(recipeConsumer, CABlocks.PEACH_PLANKS.get(), CATags.Items.PEACH_LOGS);
		woodenSlab(recipeConsumer, CABlocks.PEACH_SLAB.get(), CABlocks.PEACH_PLANKS.get());
		woodenStairs(recipeConsumer, CABlocks.PEACH_STAIRS.get(), CABlocks.PEACH_PLANKS.get());
		woodenTrapdoor(recipeConsumer, CABlocks.PEACH_TRAPDOOR.get(), CABlocks.PEACH_PLANKS.get());
		woodenDoor(recipeConsumer, CABlocks.PEACH_DOOR.get(), CABlocks.PEACH_PLANKS.get());
		woodenFence(recipeConsumer, CABlocks.PEACH_FENCE.get(), CABlocks.PEACH_PLANKS.get());
		woodenFenceGate(recipeConsumer, CABlocks.PEACH_FENCE_GATE.get(), CABlocks.PEACH_PLANKS.get());
		woodenPressurePlate(recipeConsumer, CABlocks.PEACH_PRESSURE_PLATE.get(), CABlocks.PEACH_PLANKS.get());
		woodenButton(recipeConsumer, CABlocks.PEACH_BUTTON.get(), CABlocks.PEACH_PLANKS.get());
		woodenWood(recipeConsumer, CABlocks.PEACH_WOOD.get(), CABlocks.PEACH_LOG.get());
		woodenPlanks(recipeConsumer, CABlocks.SKYWOOD_PLANKS.get(), CATags.Items.SKYWOOD_LOGS);
		woodenSlab(recipeConsumer, CABlocks.SKYWOOD_SLAB.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenStairs(recipeConsumer, CABlocks.SKYWOOD_STAIRS.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenTrapdoor(recipeConsumer, CABlocks.SKYWOOD_TRAPDOOR.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenDoor(recipeConsumer, CABlocks.SKYWOOD_DOOR.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenFence(recipeConsumer, CABlocks.SKYWOOD_FENCE.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenFenceGate(recipeConsumer, CABlocks.SKYWOOD_FENCE_GATE.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenPressurePlate(recipeConsumer, CABlocks.SKYWOOD_PRESSURE_PLATE.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenButton(recipeConsumer, CABlocks.SKYWOOD_BUTTON.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenWood(recipeConsumer, CABlocks.SKYWOOD.get(), CABlocks.SKYWOOD_LOG.get());
		woodenSign(recipeConsumer, CAItems.PEACH_SIGN.get(), CABlocks.PEACH_PLANKS.get(), Items.STICK);
		woodenPlanks(recipeConsumer, CABlocks.CRYSTALWOOD_PLANKS.get(), CABlocks.CRYSTALWOOD_LOG.get());
		woodenSlab(recipeConsumer, CABlocks.CRYSTALWOOD_SLAB.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		woodenStairs(recipeConsumer, CABlocks.CRYSTALWOOD_STAIRS.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		woodenTrapdoor(recipeConsumer, CABlocks.CRYSTALWOOD_TRAPDOOR.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		woodenDoor(recipeConsumer, CABlocks.CRYSTALWOOD_DOOR.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		woodenFence(recipeConsumer, CABlocks.CRYSTALWOOD_FENCE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		woodenFenceGate(recipeConsumer, CABlocks.CRYSTALWOOD_FENCE_GATE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		woodenPressurePlate(recipeConsumer, CABlocks.CRYSTAL_PRESSURE_PLATE.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		woodenButton(recipeConsumer, CABlocks.CRYSTALWOOD_BUTTON.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		woodenWood(recipeConsumer, CABlocks.CRYSTALWOOD.get(), CABlocks.CRYSTALWOOD_LOG.get());
		woodenSign(recipeConsumer, CAItems.SKYWOOD_SIGN.get(), CABlocks.SKYWOOD_PLANKS.get(), Items.STICK);

		leafCarpet(recipeConsumer, CABlocks.APPLE_LEAF_CARPET.get(), CABlocks.APPLE_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.CHERRY_LEAF_CARPET.get(), CABlocks.CHERRY_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.DENSEWOOD_LEAF_CARPET.get(), CABlocks.DENSEWOOD_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.DUPLICATION_LEAF_CARPET.get(), CABlocks.DUPLICATION_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.GINKGO_LEAF_CARPET.get(), CABlocks.GINKGO_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.MESOZOIC_LEAF_CARPET.get(), CABlocks.MESOZOIC_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.PEACH_LEAF_CARPET.get(), CABlocks.PEACH_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.SKYWOOD_LEAF_CARPET.get(), CABlocks.SKYWOOD_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.OAK_LEAF_CARPET.get(), Blocks.OAK_LEAVES);
		leafCarpet(recipeConsumer, CABlocks.SPRUCE_LEAF_CARPET.get(), Blocks.SPRUCE_LEAVES);
		leafCarpet(recipeConsumer, CABlocks.BIRCH_LEAF_CARPET.get(), Blocks.BIRCH_LEAVES);
		leafCarpet(recipeConsumer, CABlocks.JUNGLE_LEAF_CARPET.get(), Blocks.JUNGLE_LEAVES);
		leafCarpet(recipeConsumer, CABlocks.ACACIA_LEAF_CARPET.get(), Blocks.ACACIA_LEAVES);
		leafCarpet(recipeConsumer, CABlocks.DARK_OAK_LEAF_CARPET.get(), Blocks.DARK_OAK_LEAVES);
		leafCarpet(recipeConsumer, CABlocks.BLUE_CRYSTAL_LEAF_CARPET.get(), CABlocks.BLUE_CRYSTAL_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.GREEN_CRYSTAL_LEAF_CARPET.get(), CABlocks.GREEN_CRYSTAL_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.ORANGE_CRYSTAL_LEAF_CARPET.get(), CABlocks.ORANGE_CRYSTAL_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.PINK_CRYSTAL_LEAF_CARPET.get(), CABlocks.PINK_CRYSTAL_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.RED_CRYSTAL_LEAF_CARPET.get(), CABlocks.RED_CRYSTAL_LEAVES.get());
		leafCarpet(recipeConsumer, CABlocks.YELLOW_CRYSTAL_LEAF_CARPET.get(), CABlocks.YELLOW_CRYSTAL_LEAVES.get());

		ShapedRecipeBuilder.shaped(CABlocks.CRYSTAL_CRAFTING_TABLE.get())
				.define('#', CABlocks.CRYSTALWOOD_PLANKS.get())
				.pattern("##")
				.pattern("##")
				.unlockedBy("has_" + CABlocks.CRYSTALWOOD_PLANKS.get().asItem(), has(CABlocks.CRYSTALWOOD_PLANKS.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CABlocks.CRYSTAL_FURNACE.get())
				.define('#', CABlocks.KYANITE.get())
				.pattern("###")
				.pattern("# #")
				.pattern("###")
				.unlockedBy("has_" + CABlocks.KYANITE.get().asItem(), has(CABlocks.KYANITE.get())).save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.CRYSTALWOOD_SHARD.get(), 4)
				.define('#', CABlocks.CRYSTALWOOD_PLANKS.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.CRYSTALWOOD_PLANKS.get().asItem(), has(CABlocks.CRYSTALWOOD_PLANKS.get()))
				.save(recipeConsumer);

		ShapedRecipeBuilder.shaped(CAItems.CRYSTAL_TORCH.get(), 4)
				.define('#', CAItems.CRYSTAL_ENERGY.get())
				.define('$', CAItems.CRYSTALWOOD_SHARD.get())
				.pattern("#")
				.pattern("$")
				.unlockedBy("has_" + CAItems.CRYSTAL_ENERGY.get().asItem(), has(CAItems.CRYSTAL_ENERGY.get()))
				.unlockedBy("has_" + CAItems.CRYSTALWOOD_SHARD.get().asItem(), has(CAItems.CRYSTALWOOD_SHARD.get()))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.EXTREME_TORCH.get(), 2)
				.requires(Tags.Items.RODS_WOODEN)
				.requires(ItemTags.COALS)
				.requires(Tags.Items.DUSTS_REDSTONE)
				.requires(CAItems.SUNSTONE.get())
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN))
				.unlockedBy("has_" + ItemTags.COALS.getName(), has(ItemTags.COALS))
				.unlockedBy("has_" + Tags.Items.DUSTS_REDSTONE.getName(), has(Tags.Items.DUSTS_REDSTONE))
				.unlockedBy("has_" + CAItems.SUNSTONE.get().asItem(), has(CAItems.SUNSTONE.get())).save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.EXTREME_TORCH.get(), 2)
				.requires(Items.REDSTONE_TORCH)
				.requires(ItemTags.COALS)
				.requires(CAItems.SUNSTONE.get())
				.unlockedBy("has_" + Items.REDSTONE_TORCH.asItem(), has(Items.REDSTONE_TORCH))
				.unlockedBy("has_" + ItemTags.COALS.getName(), has(ItemTags.COALS))
				.unlockedBy("has_" + CAItems.SUNSTONE.get().asItem(), has(CAItems.SUNSTONE.get()))
				.save(recipeConsumer, "chaosawakens:extreme_torch_from_redstone_torch");
		ShapelessRecipeBuilder.shapeless(CAItems.EXTREME_TORCH.get(), 2)
				.requires(CAItems.SUNSTONE_TORCH.get())
				.requires(ItemTags.COALS)
				.requires(Tags.Items.DUSTS_REDSTONE)
				.unlockedBy("has_" + CAItems.SUNSTONE_TORCH.get().asItem(), has(CAItems.SUNSTONE_TORCH.get()))
				.unlockedBy("has_" + ItemTags.COALS.getName(), has(ItemTags.COALS))
				.unlockedBy("has_" + Tags.Items.DUSTS_REDSTONE.getName(), has(Tags.Items.DUSTS_REDSTONE))
				.save(recipeConsumer, "chaosawakens:extreme_torch_from_sunstone_torch");
		ShapelessRecipeBuilder.shapeless(CAItems.EXTREME_TORCH.get(), 2)
				.requires(Items.TORCH)
				.requires(Tags.Items.DUSTS_REDSTONE)
				.requires(CAItems.SUNSTONE.get())
				.unlockedBy("has_" + Items.TORCH.asItem(), has(Items.TORCH))
				.unlockedBy("has_" + Tags.Items.DUSTS_REDSTONE.getName(), has(Tags.Items.DUSTS_REDSTONE))
				.unlockedBy("has_" + CAItems.SUNSTONE.get().asItem(), has(CAItems.SUNSTONE.get()))
				.save(recipeConsumer, "chaosawakens:extreme_torch_from_torch");
		ShapedRecipeBuilder.shaped(CAItems.SUNSTONE_TORCH.get(), 2)
				.define('#', CAItems.SUNSTONE.get())
				.define('$', Tags.Items.RODS_WOODEN)
				.pattern("#")
				.pattern("$")
				.unlockedBy("has_" + CAItems.SUNSTONE.get().asItem(), has(CAItems.SUNSTONE.get()))
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN)).save(recipeConsumer);

		ShapedRecipeBuilder.shaped(CAItems.BEETROOT_ON_A_STICK.get())
				.define('S', Tags.Items.RODS_WOODEN)
				.define('B', Items.BEETROOT)
				.pattern("  S")
				.pattern(" SB")
				.pattern("S  ")
				.unlockedBy("has_" + Items.BEETROOT, has(Items.BEETROOT))
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN)).save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.CRYSTAL_BEETROOT_ON_A_STICK.get())
				.define('S', Tags.Items.RODS_WOODEN)
				.define('B', CAItems.CRYSTAL_BEETROOT.get())
				.pattern("  S")
				.pattern(" SB")
				.pattern("S  ")
				.unlockedBy("has_" + CAItems.CRYSTAL_BEETROOT.get().asItem(), has(CAItems.CRYSTAL_BEETROOT.get()))
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN)).save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.GOLDEN_BEETROOT_ON_A_STICK.get())
				.define('S', Tags.Items.RODS_WOODEN)
				.define('B', CAItems.GOLDEN_BEETROOT.get())
				.pattern("  S")
				.pattern(" SB")
				.pattern("S  ")
				.unlockedBy("has_" + CAItems.GOLDEN_BEETROOT.get().asItem(), has(CAItems.GOLDEN_BEETROOT.get()))
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN)).save(recipeConsumer);

		surroundItem(recipeConsumer, CAItems.POISON_SWORD.get(), CAItems.DEAD_STINK_BUG.get(), CAItems.EMERALD_SWORD.get());
	//	surroundItem(recipeConsumer, CAItems.SLAYER_CHAINSAW.get(), Blocks.REDSTONE_BLOCK, CAItems.ULTIMATE_AXE.get());
		surroundItem(recipeConsumer, CAItems.EXPERIENCE_SWORD.get(), Items.EXPERIENCE_BOTTLE, CAItems.EMERALD_SWORD.get());
		surroundItem(recipeConsumer, CAItems.EXPERIENCE_HELMET.get(), Items.EXPERIENCE_BOTTLE, CAItems.EMERALD_HELMET.get());
		surroundItem(recipeConsumer, CAItems.EXPERIENCE_CHESTPLATE.get(), Items.EXPERIENCE_BOTTLE, CAItems.EMERALD_CHESTPLATE.get());
		surroundItem(recipeConsumer, CAItems.EXPERIENCE_LEGGINGS.get(), Items.EXPERIENCE_BOTTLE, CAItems.EMERALD_LEGGINGS.get());
		surroundItem(recipeConsumer, CAItems.EXPERIENCE_BOOTS.get(), Items.EXPERIENCE_BOTTLE, CAItems.EMERALD_BOOTS.get());

		slab(recipeConsumer, CABlocks.MARBLE_SLAB.get(), CABlocks.MARBLE.get());
		stairs(recipeConsumer, CABlocks.MARBLE_STAIRS.get(), CABlocks.MARBLE.get());
		wall(recipeConsumer, CABlocks.MARBLE_WALL.get(), CABlocks.MARBLE.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_WALL.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.POLISHED_MARBLE.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.POLISHED_MARBLE_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.POLISHED_MARBLE_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.POLISHED_MARBLE_WALL.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_PILLAR.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE_PILLAR.get(), CABlocks.MARBLE_PILLAR_3.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE_PILLAR.get(), CABlocks.MARBLE_PILLAR_S.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE_PILLAR.get(), CABlocks.MARBLE_PILLAR_T.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE_PILLAR.get(), CABlocks.MARBLE_PILLAR_Z.get());

		bricks(recipeConsumer, CABlocks.MARBLE_BRICKS.get(),  CABlocks.POLISHED_MARBLE.get());
		slab(recipeConsumer, CABlocks.MARBLE_BRICK_SLAB.get(), CABlocks.MARBLE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.MARBLE_BRICK_STAIRS.get(), CABlocks.MARBLE_BRICKS.get());
		wall(recipeConsumer, CABlocks.MARBLE_BRICK_WALL.get(), CABlocks.MARBLE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.MARBLE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.MARBLE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_WALL.get());

		ShapelessRecipeBuilder.shapeless(CABlocks.MOSSY_MARBLE_BRICKS.get())
				.requires(CABlocks.MARBLE_BRICKS.get())
				.requires(Items.VINE)
				.unlockedBy("has_" + CABlocks.MARBLE_BRICKS.get().asItem(), has(CABlocks.MARBLE_BRICKS.get()))
				.unlockedBy("has_" + Items.VINE.asItem(), has(Items.VINE))
				.save(recipeConsumer);
		slab(recipeConsumer, CABlocks.MOSSY_MARBLE_BRICK_SLAB.get(), CABlocks.MOSSY_MARBLE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.MOSSY_MARBLE_BRICK_STAIRS.get(), CABlocks.MOSSY_MARBLE_BRICKS.get());
		wall(recipeConsumer, CABlocks.MOSSY_MARBLE_BRICK_WALL.get(), CABlocks.MOSSY_MARBLE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.MOSSY_MARBLE_BRICKS.get(), CABlocks.MOSSY_MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.MOSSY_MARBLE_BRICKS.get(), CABlocks.MOSSY_MARBLE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.MOSSY_MARBLE_BRICKS.get(), CABlocks.MOSSY_MARBLE_BRICK_WALL.get());

		ShapedRecipeBuilder.shaped(CABlocks.CHISELED_MARBLE_BRICKS.get())
				.define('#', CABlocks.MARBLE_BRICK_SLAB.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.MARBLE_BRICK_SLAB.get().asItem(), has(CABlocks.MARBLE_BRICK_SLAB.get()))
				.save(recipeConsumer);
		slab(recipeConsumer, CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		wall(recipeConsumer, CABlocks.CHISELED_MARBLE_BRICK_WALL.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.CHISELED_MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CHISELED_MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CHISELED_MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_WALL.get());

		bricks(recipeConsumer, CABlocks.POLISHED_MARBLE.get(),  CABlocks.MARBLE.get());
		slab(recipeConsumer, CABlocks.POLISHED_MARBLE_SLAB.get(), CABlocks.POLISHED_MARBLE.get());
		stairs(recipeConsumer, CABlocks.POLISHED_MARBLE_STAIRS.get(), CABlocks.POLISHED_MARBLE.get());
		wall(recipeConsumer, CABlocks.POLISHED_MARBLE_WALL.get(), CABlocks.POLISHED_MARBLE.get());
		ShapedRecipeBuilder.shaped(CABlocks.MARBLE_PILLAR.get())
				.define('#', CABlocks.POLISHED_MARBLE.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.POLISHED_MARBLE.get().asItem(), has(CABlocks.POLISHED_MARBLE.get()))
				.save(recipeConsumer);
		stonecutting(recipeConsumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.POLISHED_MARBLE_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.POLISHED_MARBLE_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.POLISHED_MARBLE_WALL.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.MARBLE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.MARBLE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.MARBLE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_WALL.get());

		smelting(recipeConsumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.CRACKED_MARBLE_BRICKS.get(), 0.1F, 200);
		slab(recipeConsumer, CABlocks.CRACKED_MARBLE_BRICK_SLAB.get(), CABlocks.CRACKED_MARBLE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_MARBLE_BRICK_STAIRS.get(), CABlocks.CRACKED_MARBLE_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_MARBLE_BRICK_WALL.get(), CABlocks.CRACKED_MARBLE_BRICKS.get());

		slab(recipeConsumer, CABlocks.LIMESTONE_SLAB.get(), CABlocks.LIMESTONE.get());
		stairs(recipeConsumer, CABlocks.LIMESTONE_STAIRS.get(), CABlocks.LIMESTONE.get());
		wall(recipeConsumer, CABlocks.LIMESTONE_WALL.get(), CABlocks.LIMESTONE.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_WALL.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_WALL.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_PILLAR.get());

		bricks(recipeConsumer, CABlocks.LIMESTONE_BRICKS.get(),  CABlocks.POLISHED_LIMESTONE.get());
		slab(recipeConsumer, CABlocks.LIMESTONE_BRICK_SLAB.get(), CABlocks.LIMESTONE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.LIMESTONE_BRICK_STAIRS.get(), CABlocks.LIMESTONE_BRICKS.get());
		wall(recipeConsumer, CABlocks.LIMESTONE_BRICK_WALL.get(), CABlocks.LIMESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.LIMESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.LIMESTONE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get());

		ShapelessRecipeBuilder.shapeless(CABlocks.MOSSY_LIMESTONE_BRICKS.get())
				.requires(CABlocks.LIMESTONE_BRICKS.get())
				.requires(Items.VINE)
				.unlockedBy("has_" + CABlocks.LIMESTONE_BRICKS.get().asItem(), has(CABlocks.LIMESTONE_BRICKS.get()))
				.unlockedBy("has_" + Items.VINE.asItem(), has(Items.VINE))
				.save(recipeConsumer);
		slab(recipeConsumer, CABlocks.MOSSY_LIMESTONE_BRICK_SLAB.get(), CABlocks.MOSSY_LIMESTONE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.MOSSY_LIMESTONE_BRICK_STAIRS.get(), CABlocks.MOSSY_LIMESTONE_BRICKS.get());
		wall(recipeConsumer, CABlocks.MOSSY_LIMESTONE_BRICK_WALL.get(), CABlocks.MOSSY_LIMESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.MOSSY_LIMESTONE_BRICKS.get(), CABlocks.MOSSY_LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.MOSSY_LIMESTONE_BRICKS.get(), CABlocks.MOSSY_LIMESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.MOSSY_LIMESTONE_BRICKS.get(), CABlocks.MOSSY_LIMESTONE_BRICK_WALL.get());

		ShapedRecipeBuilder.shaped(CABlocks.CHISELED_LIMESTONE_BRICKS.get())
				.define('#', CABlocks.LIMESTONE_BRICK_SLAB.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.LIMESTONE_BRICK_SLAB.get().asItem(), has(CABlocks.LIMESTONE_BRICK_SLAB.get()))
				.save(recipeConsumer);
		slab(recipeConsumer, CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		wall(recipeConsumer, CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.CHISELED_LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CHISELED_LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CHISELED_LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get());

		bricks(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(),  CABlocks.LIMESTONE.get());
		slab(recipeConsumer, CABlocks.POLISHED_LIMESTONE_SLAB.get(), CABlocks.POLISHED_LIMESTONE.get());
		stairs(recipeConsumer, CABlocks.POLISHED_LIMESTONE_STAIRS.get(), CABlocks.POLISHED_LIMESTONE.get());
		wall(recipeConsumer, CABlocks.POLISHED_LIMESTONE_WALL.get(), CABlocks.POLISHED_LIMESTONE.get());
		ShapedRecipeBuilder.shaped(CABlocks.LIMESTONE_PILLAR.get())
				.define('#', CABlocks.POLISHED_LIMESTONE.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.POLISHED_LIMESTONE.get().asItem(), has(CABlocks.POLISHED_LIMESTONE.get()))
				.save(recipeConsumer);
		stonecutting(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_WALL.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.LIMESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get());

		smelting(recipeConsumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.CRACKED_LIMESTONE_BRICKS.get(), 0.1F, 200);
		slab(recipeConsumer, CABlocks.CRACKED_LIMESTONE_BRICK_SLAB.get(), CABlocks.CRACKED_LIMESTONE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_LIMESTONE_BRICK_STAIRS.get(), CABlocks.CRACKED_LIMESTONE_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_LIMESTONE_BRICK_WALL.get(), CABlocks.CRACKED_LIMESTONE_BRICKS.get());
		
		slab(recipeConsumer, CABlocks.RHINESTONE_SLAB.get(), CABlocks.RHINESTONE.get());
		stairs(recipeConsumer, CABlocks.RHINESTONE_STAIRS.get(), CABlocks.RHINESTONE.get());
		wall(recipeConsumer, CABlocks.RHINESTONE_WALL.get(), CABlocks.RHINESTONE.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_WALL.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_WALL.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_PILLAR.get());

		bricks(recipeConsumer, CABlocks.RHINESTONE_BRICKS.get(),  CABlocks.POLISHED_RHINESTONE.get());
		slab(recipeConsumer, CABlocks.RHINESTONE_BRICK_SLAB.get(), CABlocks.RHINESTONE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.RHINESTONE_BRICK_STAIRS.get(), CABlocks.RHINESTONE_BRICKS.get());
		wall(recipeConsumer, CABlocks.RHINESTONE_BRICK_WALL.get(), CABlocks.RHINESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.RHINESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.RHINESTONE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get());

		ShapelessRecipeBuilder.shapeless(CABlocks.MOSSY_RHINESTONE_BRICKS.get())
				.requires(CABlocks.RHINESTONE_BRICKS.get())
				.requires(Items.VINE)
				.unlockedBy("has_" + CABlocks.RHINESTONE_BRICKS.get().asItem(), has(CABlocks.RHINESTONE_BRICKS.get()))
				.unlockedBy("has_" + Items.VINE.asItem(), has(Items.VINE))
				.save(recipeConsumer);
		slab(recipeConsumer, CABlocks.MOSSY_RHINESTONE_BRICK_SLAB.get(), CABlocks.MOSSY_RHINESTONE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.MOSSY_RHINESTONE_BRICK_STAIRS.get(), CABlocks.MOSSY_RHINESTONE_BRICKS.get());
		wall(recipeConsumer, CABlocks.MOSSY_RHINESTONE_BRICK_WALL.get(), CABlocks.MOSSY_RHINESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.MOSSY_RHINESTONE_BRICKS.get(), CABlocks.MOSSY_RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.MOSSY_RHINESTONE_BRICKS.get(), CABlocks.MOSSY_RHINESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.MOSSY_RHINESTONE_BRICKS.get(), CABlocks.MOSSY_RHINESTONE_BRICK_WALL.get());

		ShapedRecipeBuilder.shaped(CABlocks.CHISELED_RHINESTONE_BRICKS.get())
				.define('#', CABlocks.RHINESTONE_BRICK_SLAB.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.RHINESTONE_BRICK_SLAB.get().asItem(), has(CABlocks.RHINESTONE_BRICK_SLAB.get()))
				.save(recipeConsumer);
		slab(recipeConsumer, CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		wall(recipeConsumer, CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.CHISELED_RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CHISELED_RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CHISELED_RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get());

		bricks(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(),  CABlocks.RHINESTONE.get());
		slab(recipeConsumer, CABlocks.POLISHED_RHINESTONE_SLAB.get(), CABlocks.POLISHED_RHINESTONE.get());
		stairs(recipeConsumer, CABlocks.POLISHED_RHINESTONE_STAIRS.get(), CABlocks.POLISHED_RHINESTONE.get());
		wall(recipeConsumer, CABlocks.POLISHED_RHINESTONE_WALL.get(), CABlocks.POLISHED_RHINESTONE.get());
		ShapedRecipeBuilder.shaped(CABlocks.RHINESTONE_PILLAR.get())
				.define('#', CABlocks.POLISHED_RHINESTONE.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.POLISHED_RHINESTONE.get().asItem(), has(CABlocks.POLISHED_RHINESTONE.get()))
				.save(recipeConsumer);
		stonecutting(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_WALL.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.RHINESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_WALL.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get());

		smelting(recipeConsumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.CRACKED_RHINESTONE_BRICKS.get(), 0.1F, 200);
		slab(recipeConsumer, CABlocks.CRACKED_RHINESTONE_BRICK_SLAB.get(), CABlocks.CRACKED_RHINESTONE_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_RHINESTONE_BRICK_STAIRS.get(), CABlocks.CRACKED_RHINESTONE_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_RHINESTONE_BRICK_WALL.get(), CABlocks.CRACKED_RHINESTONE_BRICKS.get());

		stonecutting(recipeConsumer, Blocks.TERRACOTTA, CABlocks.TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.TERRACOTTA, CABlocks.TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.TERRACOTTA, CABlocks.TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.TERRACOTTA, CABlocks.TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.TERRACOTTA_BRICKS.get(),  Blocks.TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.TERRACOTTA_BRICKS.get(), CABlocks.TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.TERRACOTTA_BRICKS.get(), CABlocks.TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.TERRACOTTA_BRICKS.get(), CABlocks.TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.TERRACOTTA_BRICK_SLAB.get(), CABlocks.TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.TERRACOTTA_BRICK_STAIRS.get(), CABlocks.TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.TERRACOTTA_BRICK_WALL.get(), CABlocks.TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.WHITE_TERRACOTTA, CABlocks.WHITE_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.WHITE_TERRACOTTA, CABlocks.WHITE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.WHITE_TERRACOTTA, CABlocks.WHITE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.WHITE_TERRACOTTA, CABlocks.WHITE_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.WHITE_TERRACOTTA_BRICKS.get(),  Blocks.WHITE_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.WHITE_TERRACOTTA_BRICKS.get(), CABlocks.WHITE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.WHITE_TERRACOTTA_BRICKS.get(), CABlocks.WHITE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.WHITE_TERRACOTTA_BRICKS.get(), CABlocks.WHITE_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.WHITE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.WHITE_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.WHITE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.WHITE_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.WHITE_TERRACOTTA_BRICK_WALL.get(), CABlocks.WHITE_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.WHITE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.ORANGE_TERRACOTTA, CABlocks.ORANGE_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.ORANGE_TERRACOTTA, CABlocks.ORANGE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.ORANGE_TERRACOTTA, CABlocks.ORANGE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.ORANGE_TERRACOTTA, CABlocks.ORANGE_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.ORANGE_TERRACOTTA_BRICKS.get(),  Blocks.ORANGE_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.ORANGE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.ORANGE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.ORANGE_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.ORANGE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.ORANGE_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.ORANGE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.ORANGE_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.ORANGE_TERRACOTTA_BRICK_WALL.get(), CABlocks.ORANGE_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.MAGENTA_TERRACOTTA, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.MAGENTA_TERRACOTTA, CABlocks.MAGENTA_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.MAGENTA_TERRACOTTA, CABlocks.MAGENTA_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.MAGENTA_TERRACOTTA, CABlocks.MAGENTA_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get(),  Blocks.MAGENTA_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.MAGENTA_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.MAGENTA_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.MAGENTA_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.MAGENTA_TERRACOTTA_BRICK_SLAB.get(), CABlocks.MAGENTA_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.MAGENTA_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.MAGENTA_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.MAGENTA_TERRACOTTA_BRICK_WALL.get(), CABlocks.MAGENTA_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.LIGHT_BLUE_TERRACOTTA, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.LIGHT_BLUE_TERRACOTTA, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.LIGHT_BLUE_TERRACOTTA, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.LIGHT_BLUE_TERRACOTTA, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(),  Blocks.LIGHT_BLUE_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.YELLOW_TERRACOTTA, CABlocks.YELLOW_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.YELLOW_TERRACOTTA, CABlocks.YELLOW_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.YELLOW_TERRACOTTA, CABlocks.YELLOW_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.YELLOW_TERRACOTTA, CABlocks.YELLOW_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.YELLOW_TERRACOTTA_BRICKS.get(),  Blocks.YELLOW_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.YELLOW_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.YELLOW_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.YELLOW_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.YELLOW_TERRACOTTA_BRICK_SLAB.get(), CABlocks.YELLOW_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.YELLOW_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.YELLOW_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.YELLOW_TERRACOTTA_BRICK_WALL.get(), CABlocks.YELLOW_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.LIME_TERRACOTTA, CABlocks.LIME_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.LIME_TERRACOTTA, CABlocks.LIME_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.LIME_TERRACOTTA, CABlocks.LIME_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.LIME_TERRACOTTA, CABlocks.LIME_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.LIME_TERRACOTTA_BRICKS.get(),  Blocks.LIME_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.LIME_TERRACOTTA_BRICKS.get(), CABlocks.LIME_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.LIME_TERRACOTTA_BRICKS.get(), CABlocks.LIME_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.LIME_TERRACOTTA_BRICKS.get(), CABlocks.LIME_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.LIME_TERRACOTTA_BRICK_SLAB.get(), CABlocks.LIME_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.LIME_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.LIME_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.LIME_TERRACOTTA_BRICK_WALL.get(), CABlocks.LIME_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.LIME_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.PINK_TERRACOTTA, CABlocks.PINK_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.PINK_TERRACOTTA, CABlocks.PINK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.PINK_TERRACOTTA, CABlocks.PINK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.PINK_TERRACOTTA, CABlocks.PINK_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.PINK_TERRACOTTA_BRICKS.get(),  Blocks.PINK_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.PINK_TERRACOTTA_BRICKS.get(), CABlocks.PINK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.PINK_TERRACOTTA_BRICKS.get(), CABlocks.PINK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.PINK_TERRACOTTA_BRICKS.get(), CABlocks.PINK_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.PINK_TERRACOTTA_BRICK_SLAB.get(), CABlocks.PINK_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.PINK_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.PINK_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.PINK_TERRACOTTA_BRICK_WALL.get(), CABlocks.PINK_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.PINK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.GRAY_TERRACOTTA, CABlocks.GRAY_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.GRAY_TERRACOTTA, CABlocks.GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.GRAY_TERRACOTTA, CABlocks.GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.GRAY_TERRACOTTA, CABlocks.GRAY_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.GRAY_TERRACOTTA_BRICKS.get(),  Blocks.GRAY_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.GRAY_TERRACOTTA_BRICKS.get(), CABlocks.GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.GRAY_TERRACOTTA_BRICKS.get(), CABlocks.GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.GRAY_TERRACOTTA_BRICKS.get(), CABlocks.GRAY_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.GRAY_TERRACOTTA_BRICK_SLAB.get(), CABlocks.GRAY_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.GRAY_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.GRAY_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.GRAY_TERRACOTTA_BRICK_WALL.get(), CABlocks.GRAY_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.LIGHT_GRAY_TERRACOTTA, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.LIGHT_GRAY_TERRACOTTA, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.LIGHT_GRAY_TERRACOTTA, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.LIGHT_GRAY_TERRACOTTA, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(),  Blocks.LIGHT_GRAY_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.CYAN_TERRACOTTA, CABlocks.CYAN_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.CYAN_TERRACOTTA, CABlocks.CYAN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.CYAN_TERRACOTTA, CABlocks.CYAN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.CYAN_TERRACOTTA, CABlocks.CYAN_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.CYAN_TERRACOTTA_BRICKS.get(),  Blocks.CYAN_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CYAN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CYAN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CYAN_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CYAN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CYAN_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CYAN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CYAN_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CYAN_TERRACOTTA_BRICK_WALL.get(), CABlocks.CYAN_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.PURPLE_TERRACOTTA, CABlocks.PURPLE_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.PURPLE_TERRACOTTA, CABlocks.PURPLE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.PURPLE_TERRACOTTA, CABlocks.PURPLE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.PURPLE_TERRACOTTA, CABlocks.PURPLE_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.PURPLE_TERRACOTTA_BRICKS.get(),  Blocks.PURPLE_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.PURPLE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.PURPLE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.PURPLE_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.PURPLE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.PURPLE_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.PURPLE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.PURPLE_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.PURPLE_TERRACOTTA_BRICK_WALL.get(), CABlocks.PURPLE_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.BLUE_TERRACOTTA, CABlocks.BLUE_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.BLUE_TERRACOTTA, CABlocks.BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.BLUE_TERRACOTTA, CABlocks.BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.BLUE_TERRACOTTA, CABlocks.BLUE_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.BLUE_TERRACOTTA_BRICKS.get(),  Blocks.BLUE_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.BLUE_TERRACOTTA_BRICKS.get(), CABlocks.BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.BLUE_TERRACOTTA_BRICKS.get(), CABlocks.BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.BLUE_TERRACOTTA_BRICKS.get(), CABlocks.BLUE_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.BLUE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.BLUE_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.BLUE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.BLUE_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.BLUE_TERRACOTTA_BRICK_WALL.get(), CABlocks.BLUE_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.BROWN_TERRACOTTA, CABlocks.BROWN_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.BROWN_TERRACOTTA, CABlocks.BROWN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.BROWN_TERRACOTTA, CABlocks.BROWN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.BROWN_TERRACOTTA, CABlocks.BROWN_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.BROWN_TERRACOTTA_BRICKS.get(),  Blocks.BROWN_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.BROWN_TERRACOTTA_BRICKS.get(), CABlocks.BROWN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.BROWN_TERRACOTTA_BRICKS.get(), CABlocks.BROWN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.BROWN_TERRACOTTA_BRICKS.get(), CABlocks.BROWN_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.BROWN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.BROWN_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.BROWN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.BROWN_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.BROWN_TERRACOTTA_BRICK_WALL.get(), CABlocks.BROWN_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.BROWN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.GREEN_TERRACOTTA, CABlocks.GREEN_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.GREEN_TERRACOTTA, CABlocks.GREEN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.GREEN_TERRACOTTA, CABlocks.GREEN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.GREEN_TERRACOTTA, CABlocks.GREEN_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.GREEN_TERRACOTTA_BRICKS.get(),  Blocks.GREEN_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.GREEN_TERRACOTTA_BRICKS.get(), CABlocks.GREEN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.GREEN_TERRACOTTA_BRICKS.get(), CABlocks.GREEN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.GREEN_TERRACOTTA_BRICKS.get(), CABlocks.GREEN_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.GREEN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.GREEN_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.GREEN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.GREEN_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.GREEN_TERRACOTTA_BRICK_WALL.get(), CABlocks.GREEN_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.GREEN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.RED_TERRACOTTA, CABlocks.RED_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.RED_TERRACOTTA, CABlocks.RED_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.RED_TERRACOTTA, CABlocks.RED_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.RED_TERRACOTTA, CABlocks.RED_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.RED_TERRACOTTA_BRICKS.get(),  Blocks.RED_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.RED_TERRACOTTA_BRICKS.get(), CABlocks.RED_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.RED_TERRACOTTA_BRICKS.get(), CABlocks.RED_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.RED_TERRACOTTA_BRICKS.get(), CABlocks.RED_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.RED_TERRACOTTA_BRICK_SLAB.get(), CABlocks.RED_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.RED_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.RED_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.RED_TERRACOTTA_BRICK_WALL.get(), CABlocks.RED_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.RED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.BLACK_TERRACOTTA, CABlocks.BLACK_TERRACOTTA_BRICKS.get());
		stonecutting(recipeConsumer, Blocks.BLACK_TERRACOTTA, CABlocks.BLACK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, Blocks.BLACK_TERRACOTTA, CABlocks.BLACK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, Blocks.BLACK_TERRACOTTA, CABlocks.BLACK_TERRACOTTA_BRICK_WALL.get());
		bricks(recipeConsumer, CABlocks.BLACK_TERRACOTTA_BRICKS.get(),  Blocks.BLACK_TERRACOTTA);
		stonecutting(recipeConsumer, CABlocks.BLACK_TERRACOTTA_BRICKS.get(), CABlocks.BLACK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.BLACK_TERRACOTTA_BRICKS.get(), CABlocks.BLACK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.BLACK_TERRACOTTA_BRICKS.get(), CABlocks.BLACK_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.BLACK_TERRACOTTA_BRICK_SLAB.get(), CABlocks.BLACK_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.BLACK_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.BLACK_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.BLACK_TERRACOTTA_BRICK_WALL.get(), CABlocks.BLACK_TERRACOTTA_BRICKS.get());
		smelting(recipeConsumer, CABlocks.BLACK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(recipeConsumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(recipeConsumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(recipeConsumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_WALL.get());
		slab(recipeConsumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get());
		stairs(recipeConsumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get());
		wall(recipeConsumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get());

		ShapedRecipeBuilder.shaped(CAItems.CRITTER_CAGE.get(), 2)
				.define('#', Items.STICK)
				.define('$', Items.IRON_INGOT)
				.pattern("$#$")
				.pattern("# #")
				.pattern("$#$")
				.unlockedBy("has_" + Items.STICK.asItem(), has(Items.STICK))
				.unlockedBy("has_" + Items.IRON_INGOT.asItem(), has(Items.IRON_INGOT))
				.save(recipeConsumer, ChaosAwakens.prefix(CAItems.CRITTER_CAGE.get() + "_default"));
		ShapedRecipeBuilder.shaped(CAItems.CRITTER_CAGE.get(), 2)
				.define('#', CAItems.CRYSTALWOOD_SHARD.get())
				.define('$', CAItems.PINK_TOURMALINE_INGOT.get())
				.pattern("$#$")
				.pattern("# #")
				.pattern("$#$")
				.unlockedBy("has_" + CAItems.CRYSTALWOOD_SHARD.get().asItem(), has(CAItems.CRYSTALWOOD_SHARD.get()))
				.unlockedBy("has_" + CAItems.PINK_TOURMALINE_INGOT.get().asItem(), has(CAItems.PINK_TOURMALINE_INGOT.get()))
				.save(recipeConsumer, ChaosAwakens.prefix(CAItems.CRITTER_CAGE.get() + "_crystal_world"));

		armorHelmet(recipeConsumer, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL.get());
		armorChestplate(recipeConsumer, CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL.get());
		armorLeggings(recipeConsumer, CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL.get());
		armorBoots(recipeConsumer, CAItems.LAVA_EEL_BOOTS.get(), CAItems.LAVA_EEL.get());
		armorHelmet(recipeConsumer, CAItems.MOTH_SCALE_HELMET.get(), CAItems.MOTH_SCALE.get());
		armorChestplate(recipeConsumer, CAItems.MOTH_SCALE_CHESTPLATE.get(), CAItems.MOTH_SCALE.get());
		armorLeggings(recipeConsumer, CAItems.MOTH_SCALE_LEGGINGS.get(), CAItems.MOTH_SCALE.get());
		armorBoots(recipeConsumer, CAItems.MOTH_SCALE_BOOTS.get(), CAItems.MOTH_SCALE.get());
		armorHelmet(recipeConsumer, CAItems.EMERALD_HELMET.get(), Items.EMERALD);
		armorChestplate(recipeConsumer, CAItems.EMERALD_CHESTPLATE.get(), Items.EMERALD);
		armorLeggings(recipeConsumer, CAItems.EMERALD_LEGGINGS.get(), Items.EMERALD);
		armorBoots(recipeConsumer, CAItems.EMERALD_BOOTS.get(), Items.EMERALD);
		armorHelmet(recipeConsumer, CAItems.RUBY_HELMET.get(), CAItems.RUBY.get());
		armorChestplate(recipeConsumer, CAItems.RUBY_CHESTPLATE.get(), CAItems.RUBY.get());
		armorLeggings(recipeConsumer, CAItems.RUBY_LEGGINGS.get(), CAItems.RUBY.get());
		armorBoots(recipeConsumer, CAItems.RUBY_BOOTS.get(), CAItems.RUBY.get());
		armorHelmet(recipeConsumer, CAItems.AMETHYST_HELMET.get(), CAItems.AMETHYST.get());
		armorChestplate(recipeConsumer, CAItems.AMETHYST_CHESTPLATE.get(), CAItems.AMETHYST.get());
		armorLeggings(recipeConsumer, CAItems.AMETHYST_LEGGINGS.get(), CAItems.AMETHYST.get());
		armorBoots(recipeConsumer, CAItems.AMETHYST_BOOTS.get(), CAItems.AMETHYST.get());
		armorHelmet(recipeConsumer, CAItems.TIGERS_EYE_HELMET.get(), CAItems.TIGERS_EYE.get());
		armorChestplate(recipeConsumer, CAItems.TIGERS_EYE_CHESTPLATE.get(), CAItems.TIGERS_EYE.get());
		armorLeggings(recipeConsumer, CAItems.TIGERS_EYE_LEGGINGS.get(), CAItems.TIGERS_EYE.get());
		armorBoots(recipeConsumer, CAItems.TIGERS_EYE_BOOTS.get(), CAItems.TIGERS_EYE.get());
		armorHelmet(recipeConsumer, CAItems.LAPIS_HELMET.get(), Blocks.LAPIS_BLOCK);
		armorChestplate(recipeConsumer, CAItems.LAPIS_CHESTPLATE.get(), Blocks.LAPIS_BLOCK);
		armorLeggings(recipeConsumer, CAItems.LAPIS_LEGGINGS.get(), Blocks.LAPIS_BLOCK);
		armorBoots(recipeConsumer, CAItems.LAPIS_BOOTS.get(), Blocks.LAPIS_BLOCK);
		armorHelmet(recipeConsumer, CAItems.COPPER_HELMET.get(), CAItems.COPPER_LUMP.get());
		armorChestplate(recipeConsumer, CAItems.COPPER_CHESTPLATE.get(), CAItems.COPPER_LUMP.get());
		armorLeggings(recipeConsumer, CAItems.COPPER_LEGGINGS.get(), CAItems.COPPER_LUMP.get());
		armorBoots(recipeConsumer, CAItems.COPPER_BOOTS.get(), CAItems.COPPER_LUMP.get());
		armorHelmet(recipeConsumer, CAItems.TIN_HELMET.get(), CAItems.TIN_LUMP.get());
		armorChestplate(recipeConsumer, CAItems.TIN_CHESTPLATE.get(), CAItems.TIN_LUMP.get());
		armorLeggings(recipeConsumer, CAItems.TIN_LEGGINGS.get(), CAItems.TIN_LUMP.get());
		armorBoots(recipeConsumer, CAItems.TIN_BOOTS.get(), CAItems.TIN_LUMP.get());
		armorHelmet(recipeConsumer, CAItems.SILVER_HELMET.get(), CAItems.SILVER_LUMP.get());
		armorChestplate(recipeConsumer, CAItems.SILVER_CHESTPLATE.get(), CAItems.SILVER_LUMP.get());
		armorLeggings(recipeConsumer, CAItems.SILVER_LEGGINGS.get(), CAItems.SILVER_LUMP.get());
		armorBoots(recipeConsumer, CAItems.SILVER_BOOTS.get(), CAItems.SILVER_LUMP.get());
		armorHelmet(recipeConsumer, CAItems.PLATINUM_HELMET.get(), CAItems.PLATINUM_LUMP.get());
		armorChestplate(recipeConsumer, CAItems.PLATINUM_CHESTPLATE.get(), CAItems.PLATINUM_LUMP.get());
		armorLeggings(recipeConsumer, CAItems.PLATINUM_LEGGINGS.get(), CAItems.PLATINUM_LUMP.get());
		armorBoots(recipeConsumer, CAItems.PLATINUM_BOOTS.get(), CAItems.PLATINUM_LUMP.get());
		armorHelmet(recipeConsumer, CAItems.PEACOCK_FEATHER_HELMET.get(), CAItems.PEACOCK_FEATHER.get());
		armorChestplate(recipeConsumer, CAItems.PEACOCK_FEATHER_CHESTPLATE.get(), CAItems.PEACOCK_FEATHER.get());
		armorLeggings(recipeConsumer, CAItems.PEACOCK_FEATHER_LEGGINGS.get(), CAItems.PEACOCK_FEATHER.get());
		armorBoots(recipeConsumer, CAItems.PEACOCK_FEATHER_BOOTS.get(), CAItems.PEACOCK_FEATHER.get());
		armorHelmet(recipeConsumer, CAItems.PINK_TOURMALINE_HELMET.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		armorChestplate(recipeConsumer, CAItems.PINK_TOURMALINE_CHESTPLATE.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		armorLeggings(recipeConsumer, CAItems.PINK_TOURMALINE_LEGGINGS.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		armorBoots(recipeConsumer, CAItems.PINK_TOURMALINE_BOOTS.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		armorHelmet(recipeConsumer, CAItems.CATS_EYE_HELMET.get(), CAItems.CATS_EYE_INGOT.get());
		armorChestplate(recipeConsumer, CAItems.CATS_EYE_CHESTPLATE.get(), CAItems.CATS_EYE_INGOT.get());
		armorLeggings(recipeConsumer, CAItems.CATS_EYE_LEGGINGS.get(), CAItems.CATS_EYE_INGOT.get());
		armorBoots(recipeConsumer, CAItems.CATS_EYE_BOOTS.get(), CAItems.CATS_EYE_INGOT.get());
		armorHelmet(recipeConsumer, CAItems.MOBZILLA_SCALE_HELMET.get(), CAItems.MOBZILLA_SCALE.get());
		armorChestplate(recipeConsumer, CAItems.MOBZILLA_SCALE_CHESTPLATE.get(), CAItems.MOBZILLA_SCALE.get());
		armorLeggings(recipeConsumer, CAItems.MOBZILLA_SCALE_LEGGINGS.get(), CAItems.MOBZILLA_SCALE.get());
		armorBoots(recipeConsumer, CAItems.MOBZILLA_SCALE_BOOTS.get(), CAItems.MOBZILLA_SCALE.get());
		armorHelmet(recipeConsumer, CAItems.QUEEN_SCALE_HELMET.get(), CAItems.QUEEN_SCALE.get());
		armorChestplate(recipeConsumer, CAItems.QUEEN_SCALE_CHESTPLATE.get(), CAItems.QUEEN_SCALE.get());
		armorLeggings(recipeConsumer, CAItems.QUEEN_SCALE_LEGGINGS.get(), CAItems.QUEEN_SCALE.get());
		armorBoots(recipeConsumer, CAItems.QUEEN_SCALE_BOOTS.get(), CAItems.QUEEN_SCALE.get());
		armorHelmet(recipeConsumer, CAItems.ROYAL_GUARDIAN_HELMET.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		armorChestplate(recipeConsumer, CAItems.ROYAL_GUARDIAN_CHESTPLATE.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		armorLeggings(recipeConsumer, CAItems.ROYAL_GUARDIAN_LEGGINGS.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		armorBoots(recipeConsumer, CAItems.ROYAL_GUARDIAN_BOOTS.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		armorHelmet(recipeConsumer, CAItems.ENDER_DRAGON_SCALE_HELMET.get(), CAItems.ENDER_DRAGON_SCALE.get());
		ShapedRecipeBuilder.shaped(CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get())
				.define('S', CAItems.ENDER_DRAGON_SCALE.get())
				.define('E', Items.ELYTRA)
				.pattern("S S")
				.pattern("SES")
				.pattern("SSS")
				.unlockedBy("has_" + CAItems.ENDER_DRAGON_SCALE.get().asItem(), has(CAItems.ENDER_DRAGON_SCALE.get()))
				.unlockedBy("has_" + Items.ELYTRA.asItem(), has(Items.ELYTRA))
				.save(recipeConsumer);
		armorLeggings(recipeConsumer, CAItems.ENDER_DRAGON_SCALE_LEGGINGS.get(), CAItems.ENDER_DRAGON_SCALE.get());
		armorBoots(recipeConsumer, CAItems.ENDER_DRAGON_SCALE_BOOTS.get(), CAItems.ENDER_DRAGON_SCALE.get());
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_HELMET.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("TIT")
				.pattern("U U")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_CHESTPLATE.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("I I")
				.pattern("TTT")
				.pattern("UUU")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_LEGGINGS.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("III")
				.pattern("T T")
				.pattern("U U")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_BOOTS.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.pattern("T T")
				.pattern("U U")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);

		toolSword(recipeConsumer, CAItems.EMERALD_SWORD.get(), Items.EMERALD);
		toolShovel(recipeConsumer, CAItems.EMERALD_SHOVEL.get(), Items.EMERALD);
		toolPickaxe(recipeConsumer, CAItems.EMERALD_PICKAXE.get(), Items.EMERALD);
		toolAxe(recipeConsumer, CAItems.EMERALD_AXE.get(), Items.EMERALD);
		toolHoe(recipeConsumer, CAItems.EMERALD_HOE.get(), Items.EMERALD);
		toolSword(recipeConsumer, CAItems.RUBY_SWORD.get(), CAItems.RUBY.get());
		toolShovel(recipeConsumer, CAItems.RUBY_SHOVEL.get(), CAItems.RUBY.get());
		toolPickaxe(recipeConsumer, CAItems.RUBY_PICKAXE.get(), CAItems.RUBY.get());
		toolAxe(recipeConsumer, CAItems.RUBY_AXE.get(), CAItems.RUBY.get());
		toolHoe(recipeConsumer, CAItems.RUBY_HOE.get(), CAItems.RUBY.get());
		toolSword(recipeConsumer, CAItems.AMETHYST_SWORD.get(), CAItems.AMETHYST.get());
		toolShovel(recipeConsumer, CAItems.AMETHYST_SHOVEL.get(), CAItems.AMETHYST.get());
		toolPickaxe(recipeConsumer, CAItems.AMETHYST_PICKAXE.get(), CAItems.AMETHYST.get());
		toolAxe(recipeConsumer, CAItems.AMETHYST_AXE.get(), CAItems.AMETHYST.get());
		toolHoe(recipeConsumer, CAItems.AMETHYST_HOE.get(), CAItems.AMETHYST.get());
		toolSword(recipeConsumer, CAItems.TIGERS_EYE_SWORD.get(), CAItems.TIGERS_EYE.get());
		toolShovel(recipeConsumer, CAItems.TIGERS_EYE_SHOVEL.get(), CAItems.TIGERS_EYE.get());
		toolPickaxe(recipeConsumer, CAItems.TIGERS_EYE_PICKAXE.get(), CAItems.TIGERS_EYE.get());
		toolAxe(recipeConsumer, CAItems.TIGERS_EYE_AXE.get(), CAItems.TIGERS_EYE.get());
		toolHoe(recipeConsumer, CAItems.TIGERS_EYE_HOE.get(), CAItems.TIGERS_EYE.get());
		toolSword(recipeConsumer, CAItems.CRYSTALWOOD_SWORD.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		toolShovel(recipeConsumer, CAItems.CRYSTALWOOD_SHOVEL.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		toolPickaxe(recipeConsumer, CAItems.CRYSTALWOOD_PICKAXE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		toolAxe(recipeConsumer, CAItems.CRYSTALWOOD_AXE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		toolHoe(recipeConsumer, CAItems.CRYSTALWOOD_HOE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.CRYSTALWOOD_PLANKS.get());
		toolSword(recipeConsumer, CAItems.KYANITE_SWORD.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.KYANITE.get());
		toolShovel(recipeConsumer, CAItems.KYANITE_SHOVEL.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.KYANITE.get());
		toolPickaxe(recipeConsumer, CAItems.KYANITE_PICKAXE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.KYANITE.get());
		toolAxe(recipeConsumer, CAItems.KYANITE_AXE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.KYANITE.get());
		toolHoe(recipeConsumer, CAItems.KYANITE_HOE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CABlocks.KYANITE.get());
		toolSword(recipeConsumer, CAItems.CATS_EYE_SWORD.get(), CAItems.CRYSTALWOOD_SHARD.get(), CAItems.CATS_EYE_INGOT.get());
		toolShovel(recipeConsumer, CAItems.CATS_EYE_SHOVEL.get(), CAItems.CRYSTALWOOD_SHARD.get(), CAItems.CATS_EYE_INGOT.get());
		toolPickaxe(recipeConsumer, CAItems.CATS_EYE_PICKAXE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CAItems.CATS_EYE_INGOT.get());
		toolAxe(recipeConsumer, CAItems.CATS_EYE_AXE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CAItems.CATS_EYE_INGOT.get());
		toolHoe(recipeConsumer, CAItems.CATS_EYE_HOE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CAItems.CATS_EYE_INGOT.get());
		toolSword(recipeConsumer, CAItems.PINK_TOURMALINE_SWORD.get(), CAItems.CRYSTALWOOD_SHARD.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		toolShovel(recipeConsumer, CAItems.PINK_TOURMALINE_SHOVEL.get(), CAItems.CRYSTALWOOD_SHARD.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		toolPickaxe(recipeConsumer, CAItems.PINK_TOURMALINE_PICKAXE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		toolAxe(recipeConsumer, CAItems.PINK_TOURMALINE_AXE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		toolHoe(recipeConsumer, CAItems.PINK_TOURMALINE_HOE.get(), CAItems.CRYSTALWOOD_SHARD.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		toolSword(recipeConsumer, CAItems.COPPER_SWORD.get(), CAItems.COPPER_LUMP.get());
		toolShovel(recipeConsumer, CAItems.COPPER_SHOVEL.get(), CAItems.COPPER_LUMP.get());
		toolPickaxe(recipeConsumer, CAItems.COPPER_PICKAXE.get(), CAItems.COPPER_LUMP.get());
		toolAxe(recipeConsumer, CAItems.COPPER_AXE.get(), CAItems.COPPER_LUMP.get());
		toolHoe(recipeConsumer, CAItems.COPPER_HOE.get(), CAItems.COPPER_LUMP.get());
		toolSword(recipeConsumer, CAItems.TIN_SWORD.get(), CAItems.TIN_LUMP.get());
		toolShovel(recipeConsumer, CAItems.TIN_SHOVEL.get(), CAItems.TIN_LUMP.get());
		toolPickaxe(recipeConsumer, CAItems.TIN_PICKAXE.get(), CAItems.TIN_LUMP.get());
		toolAxe(recipeConsumer, CAItems.TIN_AXE.get(), CAItems.TIN_LUMP.get());
		toolHoe(recipeConsumer, CAItems.TIN_HOE.get(), CAItems.TIN_LUMP.get());
		toolSword(recipeConsumer, CAItems.SILVER_SWORD.get(), CAItems.SILVER_LUMP.get());
		toolShovel(recipeConsumer, CAItems.SILVER_SHOVEL.get(), CAItems.SILVER_LUMP.get());
		toolPickaxe(recipeConsumer, CAItems.SILVER_PICKAXE.get(), CAItems.SILVER_LUMP.get());
		toolAxe(recipeConsumer, CAItems.SILVER_AXE.get(), CAItems.SILVER_LUMP.get());
		toolHoe(recipeConsumer, CAItems.SILVER_HOE.get(), CAItems.SILVER_LUMP.get());
		toolSword(recipeConsumer, CAItems.PLATINUM_SWORD.get(), CAItems.PLATINUM_LUMP.get());
		toolShovel(recipeConsumer, CAItems.PLATINUM_SHOVEL.get(), CAItems.PLATINUM_LUMP.get());
		toolPickaxe(recipeConsumer, CAItems.PLATINUM_PICKAXE.get(), CAItems.PLATINUM_LUMP.get());
		toolAxe(recipeConsumer, CAItems.PLATINUM_AXE.get(), CAItems.PLATINUM_LUMP.get());
		toolHoe(recipeConsumer, CAItems.PLATINUM_HOE.get(), CAItems.PLATINUM_LUMP.get());
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_SWORD.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("T")
				.pattern("U")
				.pattern("I")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_SHOVEL.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("U")
				.pattern("T")
				.pattern("I")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_PICKAXE.get()).define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get()).define('I', CATags.Items.ULTIMATE_GEAR_HANDLES).pattern("TUT")
				.pattern(" U ").pattern(" I ")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_AXE.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("TT")
				.pattern("UI")
				.pattern(" I")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_HOE.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("TU")
				.pattern(" I")
				.pattern(" I")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_FISHING_ROD.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.define('S', Tags.Items.STRING)
				.pattern("  T")
				.pattern(" US")
				.pattern("I S")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_BOW.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.define('S', Tags.Items.STRING)
				.pattern(" TS")
				.pattern("I S")
				.pattern(" US")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_CROSSBOW.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('P', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.define('C', Items.CROSSBOW)
				.pattern("TPT")
				.pattern("UCU")
				.pattern(" U ")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_CROSSBOW_BOLT.get(), 8)
				.define('S', CAItems.SUNSTONE.get())
				.define('P', CAItems.PLATINUM_LUMP.get())
				.define('U', CAItems.URANIUM_NUGGET.get())
				.define('T', CAItems.TITANIUM_NUGGET.get())
				.pattern("SU ")
				.pattern("UPT")
				.pattern(" TP")
				.unlockedBy("has_" + CAItems.SUNSTONE.get().asItem(), has(CAItems.SUNSTONE.get()))
				.unlockedBy("has_" + CAItems.PLATINUM_LUMP.get().asItem(), has(CAItems.PLATINUM_LUMP.get()))
				.unlockedBy("has_" + CAItems.URANIUM_NUGGET.get().asItem(), has(CAItems.URANIUM_NUGGET.get()))
				.unlockedBy("has_" + CAItems.TITANIUM_NUGGET.get().asItem(), has(CAItems.TITANIUM_NUGGET.get()))
				.save(recipeConsumer);

		ShapedRecipeBuilder.shaped(CAItems.SKATE_STRING_BOW.get())
				.define('C', CAItems.CRYSTALWOOD_SHARD.get())
				.define('S', Tags.Items.STRING)
				.pattern(" CS")
				.pattern("C S")
				.pattern(" CS")
				.unlockedBy("has_" + CAItems.CRYSTALWOOD_SHARD.get().asItem(), has(CAItems.CRYSTALWOOD_SHARD.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.IRUKANDJI_ARROW.get(), 4)
				.define('I', CAItems.CRYSTALWOOD_SHARD.get())
				.define('#', CAItems.DEAD_IRUKANDJI.get())
				.define('P', CAItems.PEACOCK_FEATHER.get())
				.pattern("I")
				.pattern("#")
				.pattern("P")
				.unlockedBy("has_" + CAItems.CRYSTALWOOD_SHARD.get().asItem(), has(CAItems.CRYSTALWOOD_SHARD.get()))
				.unlockedBy("has_" + CAItems.DEAD_IRUKANDJI.get().asItem(), has(CAItems.DEAD_IRUKANDJI.get()))
				.unlockedBy("has_" + CAItems.PEACOCK_FEATHER.get().asItem(), has(CAItems.PEACOCK_FEATHER.get()))
				.save(recipeConsumer);

		ShapedRecipeBuilder.shaped(CAItems.ATTITUDE_ADJUSTER.get())
				.define('S', CAItems.ULTIMATE_SWORD.get())
				.define('A', CAItems.ULTIMATE_AXE.get())
				.define('H', CAItems.BIG_HAMMER.get())
				.define('G', CAItems.TRIFFID_GOO.get())
				.define('T', Items.STICK)
				.pattern(" SH")
				.pattern(" GA")
				.pattern("T  ")
				.unlockedBy("has_" + CAItems.ULTIMATE_SWORD.get().asItem(), has(CAItems.ULTIMATE_SWORD.get()))
				.unlockedBy("has_" + CAItems.ULTIMATE_AXE.get().asItem(), has(CAItems.ULTIMATE_AXE.get()))
				.unlockedBy("has_" + CAItems.BIG_HAMMER.get().asItem(), has(CAItems.BIG_HAMMER.get()))
				.unlockedBy("has_" + CAItems.TRIFFID_GOO.get().asItem(), has(CAItems.TRIFFID_GOO.get()))
				.unlockedBy("has_" + Items.STICK.asItem(), has(Items.STICK))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.BIG_BERTHA.get())
				.define('B', CAItems.BIG_BERTHA_BLADE.get())
				.define('G', CAItems.BIG_BERTHA_GUARD.get())
				.define('H', CAItems.BIG_BERTHA_HANDLE.get())
				.pattern("  B")
				.pattern(" G ")
				.pattern("H  ")
				.unlockedBy("has_" + CAItems.BIG_BERTHA_BLADE.get().asItem(), has(CAItems.BIG_BERTHA_BLADE.get()))
				.unlockedBy("has_" + CAItems.BIG_BERTHA_GUARD.get().asItem(), has(CAItems.BIG_BERTHA_GUARD.get()))
				.unlockedBy("has_" + CAItems.BIG_BERTHA_HANDLE.get().asItem(), has(CAItems.BIG_BERTHA_HANDLE.get()))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.BATTLE_AXE.get())
				.define('A', CAItems.ULTIMATE_AXE.get())
				.define('G', CAItems.TRIFFID_GOO.get())
				.define('R', CAItems.RUBY.get())
				.define('T', Items.STICK)
				.pattern(" AG")
				.pattern(" RA")
				.pattern("T  ")
				.unlockedBy("has_" + CAItems.ULTIMATE_AXE.get().asItem(), has(CAItems.ULTIMATE_AXE.get()))
				.unlockedBy("has_" + CAItems.TRIFFID_GOO.get().asItem(), has(CAItems.TRIFFID_GOO.get()))
				.unlockedBy("has_" + CAItems.RUBY.get().asItem(), has(CAItems.RUBY.get()))
				.unlockedBy("has_" + Items.STICK.asItem(), has(Items.STICK))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.SLAYER_CHAINSAW.get())
		        .define('A', CAItems.ULTIMATE_AXE.get())
		        .define('R', CAItems.RUBY.get())
		        .define('S', Blocks.REDSTONE_BLOCK)
		        .pattern("RRR")
		        .pattern("SAR")
		        .pattern("SSR")
		        .unlockedBy("has_" + CAItems.ULTIMATE_AXE.get().asItem(), has(CAItems.ULTIMATE_AXE.get()))
		        .unlockedBy("has_" + CAItems.RUBY.get().asItem(), has(CAItems.RUBY.get()))
		        .unlockedBy("has_" + Blocks.REDSTONE_BLOCK.asItem(), has(Blocks.REDSTONE_BLOCK))
		        .save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.QUEEN_SCALE_BATTLE_AXE.get())
				.define('B', CAItems.BATTLE_AXE.get())
				.define('P', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.define('S', CAItems.QUEEN_SCALE.get())
				.pattern("SBS")
				.pattern("SPS")
				.pattern(" P ")
				.unlockedBy("has_" + CAItems.BATTLE_AXE.get().asItem(), has(CAItems.BATTLE_AXE.get()))
				.unlockedBy("has_" + CAItems.QUEEN_SCALE.get().asItem(), has(CAItems.QUEEN_SCALE.get()))
				.save(recipeConsumer);
		toolSword(recipeConsumer, CAItems.ROYAL_GUARDIAN_SWORD.get(), CAItems.PLATINUM_LUMP.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		ShapedRecipeBuilder.shaped(CAItems.NIGHTMARE_SWORD.get())
				.define('N', CAItems.NIGHTMARE_SCALE.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('R', Tags.Items.DUSTS_REDSTONE)
				.define('D', Tags.Items.GEMS_DIAMOND)
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("NDN")
				.pattern("RTR")
				.pattern("NIN")
				.unlockedBy("has_" + CAItems.NIGHTMARE_SCALE.get().asItem(), has(CAItems.NIGHTMARE_SCALE.get()))
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + Tags.Items.DUSTS_REDSTONE.getName(), has(Tags.Items.DUSTS_REDSTONE))
				.unlockedBy("has_" + Tags.Items.GEMS_DIAMOND.getName(), has(Tags.Items.GEMS_DIAMOND))
				.unlockedBy("has_" + CATags.Items.ULTIMATE_GEAR_HANDLES.getName(), has(CATags.Items.ULTIMATE_GEAR_HANDLES))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.BASILISK_SWORD.get())
		        .define('B', CAItems.BASILISK_SCALE.get())
		        .define('U', CAItems.URANIUM_INGOT.get())
		        .define('G', Tags.Items.DUSTS_GLOWSTONE)
	        	.define('E', Tags.Items.GEMS_EMERALD)
		        .define('P', CATags.Items.ULTIMATE_GEAR_HANDLES)
		        .pattern("BEB")
		        .pattern("GUG")
		        .pattern("BPB")
		        .unlockedBy("has_" + CAItems.BASILISK_SCALE.get().asItem(), has(CAItems.BASILISK_SCALE.get()))
		        .unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
		        .unlockedBy("has_" + Tags.Items.DUSTS_GLOWSTONE.getName(), has(Tags.Items.DUSTS_GLOWSTONE))
		        .unlockedBy("has_" + Tags.Items.GEMS_EMERALD.getName(), has(Tags.Items.GEMS_EMERALD))
		        .unlockedBy("has_" + CATags.Items.ULTIMATE_GEAR_HANDLES.getName(), has(CATags.Items.ULTIMATE_GEAR_HANDLES))
		        .save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.THUNDER_STAFF.get())
				.define('D', Tags.Items.GEMS_DIAMOND)
				.define('R', CAItems.RUBY.get())
				.pattern(" RD")
				.pattern(" RR")
				.pattern("R  ")
				.unlockedBy("has_" + Tags.Items.GEMS_DIAMOND.getName(), has(Tags.Items.GEMS_DIAMOND))
				.unlockedBy("has_" + CAItems.RUBY.get().asItem(), has(CAItems.RUBY.get()))
				.save(recipeConsumer);

		ShapedRecipeBuilder.shaped(CAItems.MINERS_DREAM.get())
				.define('C', Blocks.CACTUS)
				.define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
				.define('G', Items.GUNPOWDER)
				.pattern("CCC")
				.pattern("RRR")
				.pattern("GGG")
				.unlockedBy("has_" + Blocks.CACTUS.asItem(), has(Blocks.CACTUS))
				.unlockedBy("has_" + Tags.Items.STORAGE_BLOCKS_REDSTONE.getName(), has(Tags.Items.STORAGE_BLOCKS_REDSTONE))
				.unlockedBy("has_" + Items.GUNPOWDER.asItem(), has(Items.GUNPOWDER))
				.save(recipeConsumer);
		
		boat(recipeConsumer, CAItems.APPLE_BOAT.get(), CABlocks.APPLE_PLANKS.get());
		boat(recipeConsumer, CAItems.DUPLICATOR_BOAT.get(), CABlocks.DUPLICATION_PLANKS.get());
		boat(recipeConsumer, CAItems.CHERRY_BOAT.get(), CABlocks.CHERRY_PLANKS.get());
		boat(recipeConsumer, CAItems.GINKGO_BOAT.get(), CABlocks.GINKGO_PLANKS.get());
		boat(recipeConsumer, CAItems.MESOZOIC_BOAT.get(), CABlocks.MESOZOIC_PLANKS.get());
		boat(recipeConsumer, CAItems.DENSEWOOD_BOAT.get(), CABlocks.DENSEWOOD_PLANKS.get());
		boat(recipeConsumer, CAItems.PEACH_BOAT.get(), CABlocks.PEACH_PLANKS.get());
		boat(recipeConsumer, CAItems.SKYWOOD_BOAT.get(), CABlocks.SKYWOOD_PLANKS.get());
		boat(recipeConsumer, CAItems.CRYSTALWOOD_BOAT.get(), CABlocks.CRYSTALWOOD_PLANKS.get());

		materialToBlock(recipeConsumer, CABlocks.ALUMINUM_BLOCK.get(), CAItems.ALUMINUM_INGOT.get());
		blockToMaterial(recipeConsumer, CABlocks.ALUMINUM_BLOCK.get(), CAItems.ALUMINUM_INGOT.get());
		materialToBlock(recipeConsumer, CABlocks.AMETHYST_BLOCK.get(), CAItems.AMETHYST.get());
		blockToMaterial(recipeConsumer, CABlocks.AMETHYST_BLOCK.get(), CAItems.AMETHYST.get());
		materialToBlock(recipeConsumer, CABlocks.BLOODSTONE_BLOCK.get(), CAItems.BLOODSTONE.get());
		blockToMaterial(recipeConsumer, CABlocks.BLOODSTONE_BLOCK.get(), CAItems.BLOODSTONE.get());
		materialToBlock(recipeConsumer, CABlocks.CATS_EYE_BLOCK.get(), CAItems.CATS_EYE_INGOT.get());
		blockToMaterial(recipeConsumer, CABlocks.CATS_EYE_BLOCK.get(), CAItems.CATS_EYE_INGOT.get());
		materialToBlock(recipeConsumer, CABlocks.COPPER_BLOCK.get(), CAItems.COPPER_LUMP.get());
		blockToMaterial(recipeConsumer, CABlocks.COPPER_BLOCK.get(), CAItems.COPPER_LUMP.get());
		materialToBlock(recipeConsumer, CABlocks.PINK_TOURMALINE_BLOCK.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		blockToMaterial(recipeConsumer, CABlocks.PINK_TOURMALINE_BLOCK.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		materialToBlock(recipeConsumer, CABlocks.PLATINUM_BLOCK.get(), CAItems.PLATINUM_LUMP.get());
		blockToMaterial(recipeConsumer, CABlocks.PLATINUM_BLOCK.get(), CAItems.PLATINUM_LUMP.get());
		materialToBlock(recipeConsumer, CABlocks.RUBY_BLOCK.get(), CAItems.RUBY.get());
		blockToMaterial(recipeConsumer, CABlocks.RUBY_BLOCK.get(), CAItems.RUBY.get());
		materialToBlock(recipeConsumer, CABlocks.SALT_BLOCK.get(), CAItems.SALT.get());
		blockToMaterial(recipeConsumer, CABlocks.SALT_BLOCK.get(), CAItems.SALT.get());
		materialToBlock(recipeConsumer, CABlocks.SILVER_BLOCK.get(), CAItems.SILVER_LUMP.get());
		blockToMaterial(recipeConsumer, CABlocks.SILVER_BLOCK.get(), CAItems.SILVER_LUMP.get());
		materialToBlock(recipeConsumer, CABlocks.SUNSTONE_BLOCK.get(), CAItems.SUNSTONE.get());
		blockToMaterial(recipeConsumer, CABlocks.SUNSTONE_BLOCK.get(), CAItems.SUNSTONE.get());
		materialToBlock(recipeConsumer, CABlocks.TIGERS_EYE_BLOCK.get(), CAItems.TIGERS_EYE.get());
		blockToMaterial(recipeConsumer, CABlocks.TIGERS_EYE_BLOCK.get(), CAItems.TIGERS_EYE.get());
		materialToBlock(recipeConsumer, CABlocks.TIN_BLOCK.get(), CAItems.TIN_LUMP.get());
		blockToMaterial(recipeConsumer, CABlocks.TIN_BLOCK.get(), CAItems.TIN_LUMP.get());
		materialToBlock(recipeConsumer, CABlocks.TITANIUM_BLOCK.get(), CAItems.TITANIUM_INGOT.get());
		blockToMaterial(recipeConsumer, CABlocks.TITANIUM_BLOCK.get(), CAItems.TITANIUM_INGOT.get());
		materialToBlock(recipeConsumer, CABlocks.URANIUM_BLOCK.get(), CAItems.URANIUM_INGOT.get());
		blockToMaterial(recipeConsumer, CABlocks.URANIUM_BLOCK.get(), CAItems.URANIUM_INGOT.get());

		materialToBlock(recipeConsumer, CABlocks.ENDER_EYE_BLOCK.get(), Items.ENDER_EYE);
		blockToMaterial(recipeConsumer, CABlocks.ENDER_EYE_BLOCK.get(), Items.ENDER_EYE, ChaosAwakens.prefix("ender_eye"));
		materialToBlock(recipeConsumer, CABlocks.ENDER_PEARL_BLOCK.get(), Items.ENDER_PEARL);
		blockToMaterial(recipeConsumer, CABlocks.ENDER_PEARL_BLOCK.get(), Items.ENDER_PEARL, ChaosAwakens.prefix("ender_pearl"));
		materialToBlock(recipeConsumer, CABlocks.MOTH_SCALE_BLOCK.get(), CAItems.MOTH_SCALE.get());
		blockToMaterial(recipeConsumer, CABlocks.MOTH_SCALE_BLOCK.get(), CAItems.MOTH_SCALE.get());
		materialToBlock(recipeConsumer, CABlocks.WATER_DRAGON_SCALE_BLOCK.get(), CAItems.WATER_DRAGON_SCALE.get());
		blockToMaterial(recipeConsumer, CABlocks.WATER_DRAGON_SCALE_BLOCK.get(), CAItems.WATER_DRAGON_SCALE.get());
		materialToBlock(recipeConsumer, CABlocks.ENDER_DRAGON_SCALE_BLOCK.get(), CAItems.ENDER_DRAGON_SCALE.get());
		blockToMaterial(recipeConsumer, CABlocks.ENDER_DRAGON_SCALE_BLOCK.get(), CAItems.ENDER_DRAGON_SCALE.get());
		materialToBlock(recipeConsumer, CABlocks.NIGHTMARE_SCALE_BLOCK.get(), CAItems.NIGHTMARE_SCALE.get());
		blockToMaterial(recipeConsumer, CABlocks.NIGHTMARE_SCALE_BLOCK.get(), CAItems.NIGHTMARE_SCALE.get());
		materialToBlock(recipeConsumer, CABlocks.MOBZILLA_SCALE_BLOCK.get(), CAItems.MOBZILLA_SCALE.get());
		blockToMaterial(recipeConsumer, CABlocks.MOBZILLA_SCALE_BLOCK.get(), CAItems.MOBZILLA_SCALE.get());
		materialToBlock(recipeConsumer, CABlocks.ROYAL_GUARDIAN_SCALE_BLOCK.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		blockToMaterial(recipeConsumer, CABlocks.ROYAL_GUARDIAN_SCALE_BLOCK.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		materialToBlock(recipeConsumer, CABlocks.QUEEN_SCALE_BLOCK.get(), CAItems.QUEEN_SCALE.get());
		blockToMaterial(recipeConsumer, CABlocks.QUEEN_SCALE_BLOCK.get(), CAItems.QUEEN_SCALE.get());
		materialToBlock(recipeConsumer, CABlocks.BASILISK_SCALE_BLOCK.get(), CAItems.BASILISK_SCALE.get());
		blockToMaterial(recipeConsumer, CABlocks.BASILISK_SCALE_BLOCK.get(), CAItems.BASILISK_SCALE.get());
		materialToBlock(recipeConsumer, CABlocks.EMPEROR_SCORPION_SCALE_BLOCK.get(), CAItems.EMPEROR_SCORPION_SCALE.get());
		blockToMaterial(recipeConsumer, CABlocks.EMPEROR_SCORPION_SCALE_BLOCK.get(), CAItems.EMPEROR_SCORPION_SCALE.get());

		nuggetToIngot(recipeConsumer, CAItems.ALUMINUM_INGOT.get(), CAItems.ALUMINUM_NUGGET.get());
		ingotToNugget(recipeConsumer, CAItems.ALUMINUM_INGOT.get(), CAItems.ALUMINUM_NUGGET.get());
		nuggetToIngot(recipeConsumer, CAItems.CATS_EYE_INGOT.get(), CAItems.CATS_EYE_NUGGET.get());
		ingotToNugget(recipeConsumer, CAItems.CATS_EYE_INGOT.get(), CAItems.CATS_EYE_NUGGET.get());
		nuggetToIngot(recipeConsumer, CAItems.PINK_TOURMALINE_INGOT.get(), CAItems.PINK_TOURMALINE_NUGGET.get());
		ingotToNugget(recipeConsumer, CAItems.PINK_TOURMALINE_INGOT.get(), CAItems.PINK_TOURMALINE_NUGGET.get());
		nuggetToIngot(recipeConsumer, CAItems.TITANIUM_INGOT.get(), CAItems.TITANIUM_NUGGET.get());
		ingotToNugget(recipeConsumer, CAItems.TITANIUM_INGOT.get(), CAItems.TITANIUM_NUGGET.get());
		nuggetToIngot(recipeConsumer, CAItems.URANIUM_INGOT.get(), CAItems.URANIUM_NUGGET.get());
		ingotToNugget(recipeConsumer, CAItems.URANIUM_INGOT.get(), CAItems.URANIUM_NUGGET.get());
		
		bucket(recipeConsumer, CAItems.PINK_TOURMALINE_INGOT.get(), CAItems.PINK_TOURMALINE_BUCKET.get());

		ShapelessRecipeBuilder.shapeless(Items.CYAN_DYE)
				.requires(CABlocks.CYAN_ROSE.get())
				.group("cyan_dye")
				.unlockedBy("has_" + CABlocks.CYAN_ROSE.get().asItem(), has(CABlocks.CYAN_ROSE.get()))
				.save(recipeConsumer, ChaosAwakens.prefix("cyan_dye_from_cyan_rose"));
		ShapelessRecipeBuilder.shapeless(Items.RED_DYE)
				.requires(CABlocks.RED_ROSE.get())
				.group("red_dye")
				.unlockedBy("has_" + CABlocks.RED_ROSE.get().asItem(), has(CABlocks.RED_ROSE.get()))
				.save(recipeConsumer, ChaosAwakens.prefix("red_dye_from_red_rose"));
		ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE)
				.requires(CABlocks.PAEONIA.get())
				.group("purple_dye")
				.unlockedBy("has_" + CABlocks.PAEONIA.get().asItem(), has(CABlocks.PAEONIA.get()))
				.save(recipeConsumer, ChaosAwakens.prefix("purple_dye_from_paeonia"));
		ShapelessRecipeBuilder.shapeless(Items.BLUE_DYE)
				.requires(CABlocks.BLUE_BULB.get())
				.group("purple_dye")
				.unlockedBy("has_" + CABlocks.BLUE_BULB.get().asItem(), has(CABlocks.BLUE_BULB.get()))
				.save(recipeConsumer, ChaosAwakens.prefix("blue_dye_from_blue_bulb"));
		ShapelessRecipeBuilder.shapeless(Items.PINK_DYE)
				.requires(CABlocks.PINK_BULB.get())
				.group("purple_dye")
				.unlockedBy("has_" + CABlocks.PINK_BULB.get().asItem(), has(CABlocks.PINK_BULB.get()))
				.save(recipeConsumer, ChaosAwakens.prefix("pink_dye_from_pink_bulb"));
		ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE)
				.requires(CABlocks.PURPLE_BULB.get())
				.group("purple_dye")
				.unlockedBy("has_" + CABlocks.PURPLE_BULB.get().asItem(), has(CABlocks.PURPLE_BULB.get()))
				.save(recipeConsumer, ChaosAwakens.prefix("purple_dye_from_purple_bulb"));

		surroundItem(recipeConsumer, CAItems.GOLDEN_MELON_SLICE.get(), Items.GOLD_INGOT, Items.MELON_SLICE);
		surroundItem(recipeConsumer, CAItems.GOLDEN_BEETROOT.get(), Items.GOLD_INGOT, Items.BEETROOT);
		surroundItem(recipeConsumer, CAItems.GOLDEN_POTATO.get(), Items.GOLD_INGOT, Items.POTATO);
		surroundItem(recipeConsumer, CAItems.GOLDEN_BAKED_POTATO.get(), Items.GOLD_INGOT, Items.BAKED_POTATO);
		surroundItem(recipeConsumer, Items.ENCHANTED_GOLDEN_APPLE, Items.GOLD_BLOCK, Items.APPLE, ChaosAwakens.prefix("enchanted_golden_apple"));
		surroundItem(recipeConsumer, CAItems.ENCHANTED_GOLDEN_CARROT.get(), Items.GOLD_BLOCK, Items.CARROT);

		plantSeeds(recipeConsumer, CAItems.STRAWBERRY_SEEDS.get(), CAItems.STRAWBERRY.get());
		plantSeeds(recipeConsumer, CAItems.RADISH_SEEDS.get(), CAItems.RADISH.get());
		plantSeeds(recipeConsumer, CAItems.QUINOA_SEEDS.get(), CAItems.QUINOA.get());
		plantSeeds(recipeConsumer, CAItems.LETTUCE_SEEDS.get(), CAItems.LETTUCE.get());
		plantSeeds(recipeConsumer, CAItems.CORN_SEEDS.get(), CAItems.CORN.get());
		plantSeeds(recipeConsumer, CAItems.TOMATO_SEEDS.get(), CAItems.TOMATO.get());

		ShapelessRecipeBuilder.shapeless(CAItems.BACON.get(), 2)
				.requires(Items.PORKCHOP)
				.requires(CAItems.SALT.get())
				.unlockedBy("has_" + Items.PORKCHOP.asItem(), has(Items.PORKCHOP))
				.unlockedBy("has_" + CAItems.SALT.get().asItem(), has(CAItems.SALT.get()))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.BLT_SANDWICH.get())
				.requires(Items.BREAD)
				.requires(CAItems.LETTUCE.get())
				.requires(CAItems.TOMATO.get())
				.requires(CAItems.COOKED_BACON.get())
				.requires(CAItems.BUTTER.get())
				.unlockedBy("has_" + Items.BREAD.asItem(), has(Items.BREAD))
				.unlockedBy("has_" + CAItems.LETTUCE.get().asItem(), has(CAItems.LETTUCE.get()))
				.unlockedBy("has_" + CAItems.TOMATO.get().asItem(), has(CAItems.TOMATO.get()))
				.unlockedBy("has_" + CAItems.COOKED_BACON.get().asItem(), has(CAItems.COOKED_BACON.get()))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.BUTTER.get(), 4)
				.requires(Items.MILK_BUCKET, 2)
				.unlockedBy("has_" + Items.MILK_BUCKET.asItem(), has(Items.MILK_BUCKET))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.CHEESE.get(), 2)
				.requires(Items.MILK_BUCKET, 4)
				.unlockedBy("has_" + Items.MILK_BUCKET.asItem(), has(Items.MILK_BUCKET))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.CORNDOG.get())
				.requires(CAItems.CORN.get())
				.requires(Tags.Items.RODS_WOODEN)
				.requires(Items.PORKCHOP)
				.requires(Items.CHICKEN)
				.unlockedBy("has_" + CAItems.CORN.get().asItem(), has(CAItems.CORN.get()))
				.unlockedBy("has_" + Items.PORKCHOP.asItem(), has(Items.PORKCHOP))
				.unlockedBy("has_" + Items.CHICKEN.asItem(), has(Items.CHICKEN))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.GARDEN_SALAD.get())
				.requires(Items.BOWL)
				.requires(CAItems.LETTUCE.get())
				.requires(CAItems.TOMATO.get())
				.requires(CAItems.RADISH.get())
				.requires(Tags.Items.CROPS_CARROT)
				.unlockedBy("has_" + CAItems.LETTUCE.get().asItem(), has(CAItems.LETTUCE.get()))
				.unlockedBy("has_" + CAItems.TOMATO.get().asItem(), has(CAItems.TOMATO.get()))
				.unlockedBy("has_" + CAItems.RADISH.get().asItem(), has(CAItems.RADISH.get()))
				.unlockedBy("has_" + Tags.Items.CROPS_CARROT.getName(), has(Tags.Items.CROPS_CARROT)).save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.RADISH_STEW.get())
				.requires(Items.BOWL)
				.requires(CAItems.RADISH.get())
				.requires(CAItems.RADISH.get())
				.requires(CAItems.RADISH.get())
				.requires(CAItems.RADISH.get())
				.requires(CAItems.RADISH.get())
				.requires(CAItems.RADISH.get())
				.unlockedBy("has_" + CAItems.RADISH.get().asItem(), has(CAItems.RADISH.get())).save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.QUINOA_SALAD.get())
				.requires(Items.BOWL)
				.requires(Items.BEETROOT)
				.requires(CAItems.QUINOA.get())
				.requires(CAItems.RADISH.get())
				.requires(CAItems.LETTUCE.get())
				.unlockedBy("has_" + CAItems.QUINOA.get().asItem(), has(CAItems.QUINOA.get()))
				.unlockedBy("has_" + CAItems.RADISH.get().asItem(), has(CAItems.RADISH.get()))
				.unlockedBy("has_" + CAItems.LETTUCE.get().asItem(), has(CAItems.LETTUCE.get())).save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.SEAFOOD_PATTY.get())
				.requires(Items.BREAD)
				.requires(CAItems.LETTUCE.get())
				.requires(CAItems.TOMATO.get())
				.requires(CAItems.COOKED_CRAB_MEAT.get())
				.unlockedBy("has_" + CAItems.LETTUCE.get().asItem(), has(CAItems.LETTUCE.get()))
				.unlockedBy("has_" + CAItems.TOMATO.get().asItem(), has(CAItems.TOMATO.get()))
				.unlockedBy("has_" + CAItems.COOKED_CRAB_MEAT.get().asItem(), has(CAItems.COOKED_CRAB_MEAT.get()))
				.save(recipeConsumer);
		
		ShapelessRecipeBuilder.shapeless(CAItems.EMPTY_POPCORN_BAG.get(), 3)
				.requires(Items.PAPER)
				.requires(Items.PAPER)
				.requires(Items.PAPER)
				.requires(Items.PAPER)
				.requires(Items.PAPER)
				.requires(Items.RED_DYE)
				.requires(Items.RED_DYE)
				.requires(Items.RED_DYE)
				.requires(Items.RED_DYE)
				.unlockedBy("has_" + Items.PAPER.asItem(), has(Items.PAPER))
				.unlockedBy("has_" + Items.RED_DYE.asItem(), has(Items.RED_DYE))
				.save(recipeConsumer);
		smelting(recipeConsumer, CAItems.CORN.get(), CAItems.POPCORN.get(), 0.35F, 200);
		ShapelessRecipeBuilder.shapeless(CAItems.POPCORN_BAG.get(), 1)
				.requires(CAItems.EMPTY_POPCORN_BAG.get())
				.requires(CAItems.POPCORN.get())
				.requires(CAItems.POPCORN.get())
				.requires(CAItems.POPCORN.get())
				.requires(CAItems.POPCORN.get())
				.unlockedBy("has_" + CAItems.EMPTY_POPCORN_BAG.get().asItem(), has(CAItems.EMPTY_POPCORN_BAG.get()))
				.unlockedBy("has_" + CAItems.POPCORN.get().asItem(), has(CAItems.POPCORN.get()))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.SALTED_POPCORN_BAG.get(), 1)
				.requires(CAItems.POPCORN_BAG.get())
				.requires(CAItems.SALT.get())
				.requires(CAItems.SALT.get())
				.requires(CAItems.SALT.get())
				.requires(CAItems.SALT.get())
				.unlockedBy("has_" + CAItems.POPCORN_BAG.get().asItem(), has(CAItems.POPCORN_BAG.get()))
				.unlockedBy("has_" + CAItems.SALT.get().asItem(), has(CAItems.SALT.get()))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.BUTTERED_POPCORN_BAG.get(), 1)
				.requires(CAItems.POPCORN_BAG.get())
				.requires(CAItems.BUTTER.get())
				.requires(CAItems.BUTTER.get())
				.requires(CAItems.BUTTER.get())
				.requires(CAItems.BUTTER.get())
				.unlockedBy("has_" + CAItems.POPCORN_BAG.get().asItem(), has(CAItems.POPCORN_BAG.get()))
				.unlockedBy("has_" + CAItems.BUTTER.get().asItem(), has(CAItems.BUTTER.get()))
				.save(recipeConsumer);
		
		ShapelessRecipeBuilder.shapeless(CAItems.BUTTER_CANDY.get(), 4)
				.requires(Items.SUGAR)
				.requires(CAItems.BUTTER.get())
				.unlockedBy("has_" + Items.SUGAR.asItem(), has(Items.SUGAR))
				.unlockedBy("has_" + CAItems.BUTTER.get().asItem(), has(CAItems.BUTTER.get()))
				.save(recipeConsumer);

		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_APPLE.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.define('A', Items.ENCHANTED_GOLDEN_APPLE)
				.pattern("TIU")
				.pattern("IAI")
				.pattern("UIT")
				.unlockedBy("has_" + Items.ENCHANTED_GOLDEN_APPLE.asItem(), has(Items.ENCHANTED_GOLDEN_APPLE))
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(recipeConsumer);

		smelting(recipeConsumer, Items.SUGAR, CAItems.CANDYCANE.get(), 0.35F, 200);

		smelting(recipeConsumer, CAItems.GOLDEN_POTATO.get(), CAItems.GOLDEN_BAKED_POTATO.get(), 0.35F, 200);
		cooking(recipeConsumer, CAItems.GOLDEN_POTATO.get(), CAItems.GOLDEN_BAKED_POTATO.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(recipeConsumer, CAItems.GOLDEN_POTATO.get(), CAItems.GOLDEN_BAKED_POTATO.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);

		smelting(recipeConsumer, CAItems.BACON.get(), CAItems.COOKED_BACON.get(), 0.35F, 200);
		cooking(recipeConsumer, CAItems.BACON.get(), CAItems.COOKED_BACON.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(recipeConsumer, CAItems.BACON.get(), CAItems.COOKED_BACON.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);
		smelting(recipeConsumer, CAItems.CORNDOG.get(), CAItems.COOKED_CORNDOG.get(), 0.35F, 200);
		cooking(recipeConsumer, CAItems.CORNDOG.get(), CAItems.COOKED_CORNDOG.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(recipeConsumer, CAItems.CORNDOG.get(), CAItems.COOKED_CORNDOG.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);
		smelting(recipeConsumer, CAItems.CRAB_MEAT.get(), CAItems.COOKED_CRAB_MEAT.get(), 0.35F, 200);
		cooking(recipeConsumer, CAItems.CRAB_MEAT.get(), CAItems.COOKED_CRAB_MEAT.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(recipeConsumer, CAItems.CRAB_MEAT.get(), CAItems.COOKED_CRAB_MEAT.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);
		smelting(recipeConsumer, CAItems.PEACOCK_LEG.get(), CAItems.COOKED_PEACOCK_LEG.get(), 0.35F, 200);
		cooking(recipeConsumer, CAItems.PEACOCK_LEG.get(), CAItems.COOKED_PEACOCK_LEG.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(recipeConsumer, CAItems.PEACOCK_LEG.get(), CAItems.COOKED_PEACOCK_LEG.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);
		smelting(recipeConsumer, CAItems.VENISON.get(), CAItems.COOKED_VENISON.get(), 0.35F, 200);
		cooking(recipeConsumer, CAItems.VENISON.get(), CAItems.COOKED_VENISON.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(recipeConsumer, CAItems.VENISON.get(), CAItems.COOKED_VENISON.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);

		smelting(recipeConsumer, CABlocks.ALUMINUM_ORE.get(), CAItems.ALUMINUM_INGOT.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.ALUMINUM_ORE.get(), CAItems.ALUMINUM_INGOT.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.AMETHYST_ORE.get(), CAItems.AMETHYST.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.AMETHYST_ORE.get(), CAItems.AMETHYST.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.BLOODSTONE_ORE.get(), CAItems.BLOODSTONE.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.BLOODSTONE_ORE.get(), CAItems.BLOODSTONE.get(), 0.1F, 100);
		smelting(recipeConsumer, CAItems.CATS_EYE_SHARD.get(), CAItems.CATS_EYE_NUGGET.get(), 0.1F, 200);
		blasting(recipeConsumer, CAItems.CATS_EYE_SHARD.get(), CAItems.CATS_EYE_NUGGET.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.COPPER_ORE.get(), CAItems.COPPER_LUMP.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.COPPER_ORE.get(), CAItems.COPPER_LUMP.get(), 0.1F, 100);
		smelting(recipeConsumer, CAItems.PINK_TOURMALINE_SHARD.get(), CAItems.PINK_TOURMALINE_NUGGET.get(), 0.1F, 200);
		blasting(recipeConsumer, CAItems.PINK_TOURMALINE_SHARD.get(), CAItems.PINK_TOURMALINE_NUGGET.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.PLATINUM_ORE.get(), CAItems.PLATINUM_LUMP.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.PLATINUM_ORE.get(), CAItems.PLATINUM_LUMP.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.RUBY_ORE.get(), CAItems.RUBY.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.RUBY_ORE.get(), CAItems.RUBY.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.SALT_ORE.get(), CAItems.SALT.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.SALT_ORE.get(), CAItems.SALT.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.SILVER_ORE.get(), CAItems.SILVER_LUMP.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.SILVER_ORE.get(), CAItems.SILVER_LUMP.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.SUNSTONE_ORE.get(), CAItems.SUNSTONE.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.SUNSTONE_ORE.get(), CAItems.SUNSTONE.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.TIGERS_EYE_ORE.get(), CAItems.TIGERS_EYE.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.TIGERS_EYE_ORE.get(), CAItems.TIGERS_EYE.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.TIN_ORE.get(), CAItems.TIN_LUMP.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.TIN_ORE.get(), CAItems.TIN_LUMP.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.TITANIUM_ORE.get(), CAItems.TITANIUM_NUGGET.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.TITANIUM_ORE.get(), CAItems.TITANIUM_NUGGET.get(), 0.1F, 100);
		smelting(recipeConsumer, CABlocks.URANIUM_ORE.get(), CAItems.URANIUM_NUGGET.get(), 0.1F, 200);
		blasting(recipeConsumer, CABlocks.URANIUM_ORE.get(), CAItems.URANIUM_NUGGET.get(), 0.1F, 100);

		ShapedRecipeBuilder.shaped(CABlocks.ROBO_GLASS_PANE.get(), 16)
				.define('G', CABlocks.ROBO_GLASS.get())
				.pattern("GGG")
				.pattern("GGG")
				.unlockedBy("has_" + CABlocks.ROBO_GLASS.get().asItem(), has(CABlocks.ROBO_GLASS.get()))
				.save(recipeConsumer);

		ShapelessRecipeBuilder.shapeless(CAItems.INSTANT_SURVIVAL_SHELTER.get())
				.requires(Tags.Items.STORAGE_BLOCKS_REDSTONE)
				.requires(Tags.Items.RODS_WOODEN)
				.requires(Tags.Items.COBBLESTONE)
				.unlockedBy("has_" + Tags.Items.STORAGE_BLOCKS_REDSTONE.getName(), has(Tags.Items.STORAGE_BLOCKS_REDSTONE))
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN))
				.unlockedBy("has_" + Tags.Items.COBBLESTONE.getName(), has(Tags.Items.COBBLESTONE)).save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.ZOO_CAGE_EXTRA_SMALL.get()).requires(Tags.Items.STORAGE_BLOCKS_IRON)
				.requires(Tags.Items.STORAGE_BLOCKS_QUARTZ)
				.requires(Tags.Items.GLASS)
				.group("zoo_cage")
				.unlockedBy("has_" + Tags.Items.STORAGE_BLOCKS_IRON.getName(), has(Tags.Items.STORAGE_BLOCKS_IRON))
				.unlockedBy("has_" + Tags.Items.STORAGE_BLOCKS_QUARTZ.getName(), has(Tags.Items.STORAGE_BLOCKS_QUARTZ))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.ZOO_CAGE_SMALL.get())
				.requires(Tags.Items.STORAGE_BLOCKS_IRON)
				.requires(Tags.Items.STORAGE_BLOCKS_QUARTZ)
				.requires(Tags.Items.GLASS)
				.requires(CAItems.ZOO_CAGE_EXTRA_SMALL.get())
				.group("zoo_cage")
				.unlockedBy("has_" + CAItems.ZOO_CAGE_EXTRA_SMALL.get().asItem(), has(CAItems.ZOO_CAGE_EXTRA_SMALL.get()))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.ZOO_CAGE_MEDIUM.get())
				.requires(Tags.Items.STORAGE_BLOCKS_IRON)
				.requires(Tags.Items.STORAGE_BLOCKS_QUARTZ)
				.requires(Tags.Items.GLASS)
				.requires(CAItems.ZOO_CAGE_SMALL.get())
				.group("zoo_cage")
				.unlockedBy("has_" + CAItems.ZOO_CAGE_SMALL.get().asItem(), has(CAItems.ZOO_CAGE_SMALL.get()))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.ZOO_CAGE_LARGE.get())
				.requires(Tags.Items.STORAGE_BLOCKS_IRON)
				.requires(Tags.Items.STORAGE_BLOCKS_QUARTZ)
				.requires(Tags.Items.GLASS)
				.requires(CAItems.ZOO_CAGE_MEDIUM.get())
				.group("zoo_cage")
				.unlockedBy("has_" + CAItems.ZOO_CAGE_MEDIUM.get().asItem(), has(CAItems.ZOO_CAGE_MEDIUM.get()))
				.save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(CAItems.ZOO_CAGE_EXTRA_LARGE.get())
				.requires(Tags.Items.STORAGE_BLOCKS_IRON)
				.requires(Tags.Items.STORAGE_BLOCKS_QUARTZ)
				.requires(Tags.Items.GLASS)
				.requires(CAItems.ZOO_CAGE_LARGE.get())
				.group("zoo_cage")
				.unlockedBy("has_" + CAItems.ZOO_CAGE_LARGE.get().asItem(), has(CAItems.ZOO_CAGE_LARGE.get()))
				.save(recipeConsumer);

		ShapedRecipeBuilder.shaped(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get())
				.define('I', ItemTags.bind("forge:ingots/copper"))
				.define('B', Items.BUCKET)
				.define('S', Blocks.SMOOTH_RED_SANDSTONE)
				.define('L', Blocks.SMOOTH_RED_SANDSTONE_SLAB)
				.pattern("III")
				.pattern("SBS")
				.pattern("LLL")
				.unlockedBy("has_" + ItemTags.bind("forge:ingots/copper").getName(), has(ItemTags.bind("forge:ingots/copper")))
				.unlockedBy("has_" + Items.BUCKET.asItem(), has(Items.BUCKET))
				.unlockedBy("has_" + Blocks.SMOOTH_RED_SANDSTONE.asItem(), has(Blocks.SMOOTH_RED_SANDSTONE))
				.unlockedBy("has_" + Blocks.SMOOTH_RED_SANDSTONE_SLAB.asItem(), has(Blocks.SMOOTH_RED_SANDSTONE_SLAB))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get())
				.define('I', Tags.Items.INGOTS_IRON)
				.define('B', Items.BUCKET)
				.define('S', Blocks.SMOOTH_STONE)
				.define('L', Blocks.SMOOTH_STONE_SLAB)
				.pattern("III")
				.pattern("SBS")
				.pattern("LLL")
				.unlockedBy("has_" + Tags.Items.INGOTS_IRON.getName(), has(Tags.Items.INGOTS_IRON))
				.unlockedBy("has_" + Items.BUCKET.asItem(), has(Items.BUCKET))
				.unlockedBy("has_" + Blocks.SMOOTH_STONE.asItem(), has(Blocks.SMOOTH_STONE))
				.unlockedBy("has_" + Blocks.SMOOTH_STONE_SLAB.asItem(), has(Blocks.SMOOTH_STONE_SLAB))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.CRYSTAL.getId())).get())
				.define('I', CAItems.PINK_TOURMALINE_INGOT.get())
				.define('B', CAItems.PINK_TOURMALINE_BUCKET.get())
				.define('S', CABlocks.KYANITE.get())
				//.define('L', CABlocks.KYANITE.get())
				.pattern("III")
				.pattern("SBS")
				.pattern("SSS")
				.unlockedBy("has_" + CAItems.PINK_TOURMALINE_INGOT.getId().getPath(), has(CAItems.PINK_TOURMALINE_INGOT.get()))
				.unlockedBy("has_" + CAItems.PINK_TOURMALINE_BUCKET.getId().getPath(), has(CAItems.PINK_TOURMALINE_BUCKET.get()))
				.unlockedBy("has_" + CABlocks.KYANITE.get().asItem(), has(CABlocks.KYANITE.get()))
				//.unlockedBy("has_" + Blocks.SMOOTH_STONE_SLAB.asItem(), has(Blocks.SMOOTH_STONE_SLAB))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.ALUMINUM_POWER_CHIP.get())
				.define('R', Tags.Items.DUSTS_REDSTONE)
				.define('A', CAItems.ALUMINUM_INGOT.get())
				.define('I', Tags.Items.INGOTS_IRON)
				.pattern("ARA")
				.pattern("RIR")
				.pattern("ARA")
				.unlockedBy("has_" + Tags.Items.DUSTS_REDSTONE.getName(), has(Tags.Items.DUSTS_REDSTONE))
				.unlockedBy("has_" + CAItems.ALUMINUM_INGOT.get().asItem(), has(CAItems.ALUMINUM_INGOT.get()))
				.unlockedBy("has_" + Tags.Items.INGOTS_IRON.getName(), has(Tags.Items.INGOTS_IRON))
				.save(recipeConsumer);
		ShapedRecipeBuilder.shaped(CAItems.CRYSTAL_POWER_CHIP.get())
				.define('R', CAItems.CRYSTAL_ENERGY.get())
				.define('A', CAItems.PINK_TOURMALINE_INGOT.get())
				.define('I', CAItems.CATS_EYE_INGOT.get())
				.pattern("ARA")
				.pattern("RIR")
				.pattern("ARA")
				.unlockedBy("has_" + CAItems.CRYSTAL_ENERGY.get().asItem(), has(CAItems.CRYSTAL_ENERGY.get()))
				.unlockedBy("has_" + CAItems.PINK_TOURMALINE_INGOT.get().asItem(), has(CAItems.ALUMINUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.CATS_EYE_INGOT.get().asItem(), has(CAItems.CATS_EYE_INGOT.get()))
				.save(recipeConsumer);
		buildDefossilizingRecipes(recipeConsumer);

		buildConversionRecipes(recipeConsumer);
	}

	private void buildDefossilizingRecipes(Consumer<IFinishedRecipe> recipeConsumer) {
		// Overworld (CA)
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ACACIA_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.ACACIA_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_ACACIA_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_BIRCH_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.BIRCH_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_BIRCH_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_DARK_OAK_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.DARK_OAK_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_DARK_OAK_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_JUNGLE_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.JUNGLE_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_JUNGLE_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_OAK_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.OAK_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_OAK_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SPRUCE_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.SPRUCE_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SPRUCE_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HERCULES_BEETLE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.HERCULES_BEETLE_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_HERCULES_BEETLE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
		        .builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
				        Ingredient.of(CABlocks.FOSSILISED_BEAVER.get()),
				        Ingredient.of(Items.WATER_BUCKET),
				        Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				        CAItems.BEAVER_SPAWN_EGG.get(), 1, 20, "copper")
		        .build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_BEAVER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_RUBY_BUG.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.RUBY_BUG_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_RUBY_BUG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_EMERALD_GATOR.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.EMERALD_GATOR_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_EMERALD_GATOR.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GREEN_FISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.GREEN_FISH_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_GREEN_FISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ROCK_FISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.ROCK_FISH_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_ROCK_FISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SPARK_FISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.SPARK_FISH_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SPARK_FISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WOOD_FISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.WOOD_FISH_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_WOOD_FISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
//		FossilRecipeBuilder
//				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
//						Ingredient.of(CABlocks.FOSSILISED_WTF.get()),
//						Ingredient.of(Items.WATER_BUCKET),
//						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
//						CAItems.WTF_SPAWN_EGG.get(), 1, 20, "copper")
//				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_WTF.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
//		FossilRecipeBuilder
//				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
//						Ingredient.of(CABlocks.FOSSILISED_SCORPION.get()),
//						Ingredient.of(Items.WATER_BUCKET),
//						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
//						CAItems.SCORPION_SPAWN_EGG.get(), 1, 20, "copper")
//				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SCORPION.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
	/*	FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WASP.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.WASP_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_WASP.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));*/
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WHALE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.WHALE_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_WHALE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WASP.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.WASP_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_WASP.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
//		FossilRecipeBuilder
//				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
//						Ingredient.of(CABlocks.FOSSILISED_PIRAPORU.get()),
//						Ingredient.of(Items.WATER_BUCKET),
//						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
//						CAItems.PIRAPORU_SPAWN_EGG.get(), 1, 20, "copper")
//				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_PIRAPORU.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_APPLE_COW.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.APPLE_COW_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_APPLE_COW.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GOLDEN_APPLE_COW.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.GOLDEN_APPLE_COW_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_GOLDEN_APPLE_COW.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_CARROT_PIG.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.CARROT_PIG_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_CARROT_PIG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.GOLDEN_CARROT_PIG_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
		        .builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
				        Ingredient.of(CABlocks.FOSSILISED_LETTUCE_CHICKEN.get()),
				        Ingredient.of(Items.WATER_BUCKET),
				        Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				        CAItems.LETTUCE_CHICKEN_SPAWN_EGG.get(), 1, 20, "copper")
		        .build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_LETTUCE_CHICKEN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_BIRD.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.BIRD_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_BIRD.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_TREE_FROG.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.TREE_FROG_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_TREE_FROG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		
		// Overworld (Vanilla)
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_BAT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.BAT_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_BAT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_BEE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.BEE_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_BEE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_CAVE_SPIDER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.CAVE_SPIDER_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_CAVE_SPIDER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_CHICKEN.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.CHICKEN_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_CHICKEN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_COD.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.COD_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_COD.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_COW.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.COW_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_COW.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_CREEPER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.CREEPER_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_CREEPER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_DOLPHIN.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.DOLPHIN_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_DOLPHIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_DONKEY.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.DONKEY_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_DONKEY.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_DROWNED.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.DROWNED_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_DROWNED.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ENDERMAN.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ENDERMAN_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_ENDERMAN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_EVOKER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.EVOKER_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_EVOKER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_FOX.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.FOX_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_FOX.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GIANT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.GIANT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_GIANT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GUARDIAN.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.GUARDIAN_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_GUARDIAN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HORSE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.HORSE_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_HORSE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HUSK.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.HUSK_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_HUSK.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HUSK_SANDSTONE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.HUSK_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_HUSK_SANDSTONE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HUSK_SANDSTONE.get()),
						Ingredient.of(CAItems.WATER_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.HUSK_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_HUSK_SANDSTONE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ILLUSIONER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.ILLUSIONER_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_ILLUSIONER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_IRON_GOLEM.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.IRON_GOLEM_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_IRON_GOLEM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_LLAMA.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.LLAMA_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_LLAMA.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_MOOSHROOM.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.MOOSHROOM_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_MOOSHROOM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_OCELOT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.OCELOT_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_OCELOT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PANDA.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PANDA_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_PANDA.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PIG.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PIG_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_PIG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PHANTOM.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PHANTOM_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_PHANTOM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PILLAGER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PILLAGER_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_PILLAGER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FROZEN_POLAR_BEAR.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.POLAR_BEAR_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FROZEN_POLAR_BEAR.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FROZEN_POLAR_BEAR.get()),
						Ingredient.of(CAItems.WATER_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.POLAR_BEAR_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FROZEN_POLAR_BEAR.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PUFFERFISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PUFFERFISH_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_PUFFERFISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_RABBIT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.RABBIT_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_RABBIT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_RAVAGER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.RAVAGER_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_RAVAGER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SALMON.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SALMON_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SALMON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SHEEP.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SHEEP_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SHEEP.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SKELETON.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SKELETON_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SKELETON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SKELETON_HORSE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SKELETON_HORSE_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SKELETON_HORSE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SLIME.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SLIME_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SLIME.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FROZEN_SNOW_GOLEM.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.SNOW_GOLEM_SPAWN_EGG.get(), 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FROZEN_SNOW_GOLEM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FROZEN_SNOW_GOLEM.get()),
						Ingredient.of(CAItems.WATER_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						CAItems.SNOW_GOLEM_SPAWN_EGG.get(), 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FROZEN_SNOW_GOLEM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SPIDER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SPIDER_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SPIDER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SQUID.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SQUID_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SQUID.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FROZEN_STRAY.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.STRAY_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FROZEN_STRAY.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FROZEN_STRAY.get()),
						Ingredient.of(CAItems.WATER_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.STRAY_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FROZEN_STRAY.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_TROPICAL_FISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.TROPICAL_FISH_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_TROPICAL_FISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_TURTLE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.TURTLE_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_TURTLE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_VILLAGER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.VILLAGER_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_VILLAGER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_VINDICATOR.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.VINDICATOR_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_VINDICATOR.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WANDERING_TRADER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.WANDERING_TRADER_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_WANDERING_TRADER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WITCH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.WITCH_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_WITCH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WOLF.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.WOLF_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_WOLF.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ZOMBIE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ZOMBIE_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_ZOMBIE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ZOMBIE_HORSE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ZOMBIE_HORSE_SPAWN_EGG, 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_ZOMBIE_HORSE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// Nether (CA)
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_CRIMSON_ENT.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.CRIMSON_ENT_SPAWN_EGG.get(), 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_CRIMSON_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WARPED_ENT.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.WARPED_ENT_SPAWN_EGG.get(), 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_WARPED_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
	/*	FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_LAVA_EEL.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.LAVA_EEL_SPAWN_EGG.get(), 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_LAVA_EEL.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));*/
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_CRIMSON_ENT.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						CAItems.CRIMSON_ENT_SPAWN_EGG.get(), 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_CRIMSON_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WARPED_ENT.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						CAItems.WARPED_ENT_SPAWN_EGG.get(), 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_WARPED_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
	/*	FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_LAVA_EEL.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						CAItems.LAVA_EEL_SPAWN_EGG.get(), 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_LAVA_EEL.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));*/
		
		// Nether (Vanilla)
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_BLAZE.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.BLAZE_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_BLAZE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GHAST.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.GHAST_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_GHAST.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HOGLIN.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.HOGLIN_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_HOGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ENDERMAN_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.MAGMA_CUBE_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.MAGMA_CUBE_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PIGLIN.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PIGLIN_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_PIGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SKELETON_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_STRIDER.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.STRIDER_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_STRIDER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WITHER_SKELETON.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.WITHER_SKELETON_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_WITHER_SKELETON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_BLAZE.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.BLAZE_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_BLAZE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GHAST.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.GHAST_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_GHAST.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HOGLIN.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.HOGLIN_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_HOGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.ENDERMAN_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.MAGMA_CUBE_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.MAGMA_CUBE_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PIGLIN.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.PIGLIN_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_PIGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.SKELETON_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_STRIDER.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.STRIDER_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_STRIDER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WITHER_SKELETON.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.WITHER_SKELETON_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_WITHER_SKELETON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.get()),
						Ingredient.of(CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		// End (CA)

		// End (Vanilla)
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ENDERMAN_END_STONE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ENDERMAN_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_ENDERMAN_END_STONE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ENDERMITE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ENDERMITE_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_ENDERMITE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SHULKER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SHULKER_SPAWN_EGG, 1, 20, "iron")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_SHULKER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ENDERMAN_END_STONE.get()),
						Ingredient.of(CAItems.WATER_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.ENDERMAN_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_ENDERMAN_END_STONE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ENDERMITE.get()),
						Ingredient.of(CAItems.WATER_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.ENDERMITE_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_ENDERMITE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SHULKER.get()),
						Ingredient.of(CAItems.WATER_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						Items.SHULKER_SPAWN_EGG, 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.FOSSILISED_SHULKER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// Mining Paradise (CA)
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_DIMETRODON.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.DIMETRODON_SPAWN_EGG.get(), 1, 20, "copper")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/" + CABlocks.FOSSILISED_DIMETRODON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// Crystalworld (CA)
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.get()),
						Ingredient.of(CAItems.WATER_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						CAItems.CRYSTAL_APPLE_COW_SPAWN_EGG.get(), 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.CRYSTALISED_CRYSTAL_CARROT_PIG.get()),
						Ingredient.of(CAItems.WATER_PINK_TOURMALINE_BUCKET.get()),
						Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
						CAItems.CRYSTAL_CARROT_PIG_SPAWN_EGG.get(), 1, 20, "crystal")
				.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.CRYSTALISED_CRYSTAL_CARROT_PIG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
		.builder(CARecipeTypes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.CRYSTALISED_CRYSTAL_GATOR.get()),
				Ingredient.of(CAItems.WATER_PINK_TOURMALINE_BUCKET.get()),
				Ingredient.of(CAItems.CRYSTAL_POWER_CHIP.get()),
				CAItems.CRYSTAL_GATOR_SPAWN_EGG.get(), 1, 20, "crystal")
		.build(recipeConsumer, ChaosAwakens.prefix("defossilizing/crystal_" + CABlocks.CRYSTALISED_CRYSTAL_GATOR.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
	}

	private static void buildConversionRecipes(Consumer<IFinishedRecipe> recipeConsumer) {
		philoConversionRecipe(recipeConsumer, Items.EMERALD, 2, CAItems.AMETHYST.get(), 1);
		philoConversionRecipe(recipeConsumer, Items.DIAMOND, 3, CAItems.TIGERS_EYE.get(), 1);
		philoConversionRecipe(recipeConsumer, CAItems.AMETHYST.get(), 4, CAItems.RUBY.get(), 1);
		philoConversionRecipe(recipeConsumer, CAItems.PINK_TOURMALINE_INGOT.get(), 4, CAItems.CATS_EYE_INGOT.get(), 1);
		philoConversionRecipe(recipeConsumer, CAItems.SUNSTONE.get(), 1, CAItems.BLOODSTONE.get(), 1);
		philoConversionRecipe(recipeConsumer, CAItems.TIN_LUMP.get(), 1, CAItems.COPPER_LUMP.get(), 2);
		philoConversionRecipe(recipeConsumer, CAItems.SILVER_LUMP.get(), 1, CAItems.TIN_LUMP.get(), 2);
		philoConversionRecipe(recipeConsumer, CAItems.PLATINUM_LUMP.get(), 2, CAItems.SILVER_LUMP.get(), 2);
	}

	private static void woodenPlanks(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pPlanks, ITag<Item> pLog) {
		ShapelessRecipeBuilder.shapeless(pPlanks, 4)
				.requires(pLog)
				.group("planks")
				.unlockedBy("has_log", has(pLog))
				.save(recipeConsumer);
	}

	private static void woodenPlanks(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pPlanks, IItemProvider pLog) {
		ShapelessRecipeBuilder.shapeless(pPlanks, 4)
				.requires(pLog)
				.group("planks")
				.unlockedBy("has_log", has(pLog))
				.save(recipeConsumer);
	}

	private static void woodenSlab(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pSlab, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pSlab, 6)
				.define('#', pMaterial)
				.pattern("###")
				.group("wooden_slab")
				.unlockedBy("has_planks", has(pMaterial))
				.save(recipeConsumer);
	}

	private static void woodenStairs(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pStairs, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pStairs, 4)
				.define('#', pMaterial)
				.pattern("#  ")
				.pattern("## ")
				.pattern("###")
				.group("wooden_stairs")
				.unlockedBy("has_planks", has(pMaterial))
				.save(recipeConsumer);
	}

	private static void woodenFence(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pFence, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pFence, 3)
				.define('#', Items.STICK)
				.define('W', pMaterial)
				.pattern("W#W")
				.pattern("W#W")
				.group("wooden_fence")
				.unlockedBy("has_planks", has(pMaterial))
				.save(recipeConsumer);
	}

	private static void woodenFenceGate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pFenceGate, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pFenceGate)
				.define('#', Items.STICK)
				.define('W', pMaterial)
				.pattern("#W#")
				.pattern("#W#")
				.group("wooden_fence_gate")
				.unlockedBy("has_planks", has(pMaterial))
				.save(recipeConsumer);
	}

	private static void woodenFence(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pFence, IItemProvider pStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pFence, 3)
				.define('#', pStick)
				.define('W', pMaterial)
				.pattern("W#W")
				.pattern("W#W")
				.group("wooden_fence")
				.unlockedBy("has_planks", has(pMaterial))
				.save(recipeConsumer);
	}

	private static void woodenFenceGate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pFenceGate, IItemProvider pStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pFenceGate)
				.define('#', pStick)
				.define('W', pMaterial)
				.pattern("#W#")
				.pattern("#W#")
				.group("wooden_fence_gate")
				.unlockedBy("has_planks", has(pMaterial))
				.save(recipeConsumer);
	}
	
	private static void woodenPressurePlate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pPressurePlate, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pPressurePlate)
				.define('#', pMaterial)
				.pattern("##")
				.group("wooden_pressure_plate")
				.unlockedBy("has_planks", has(pMaterial))
				.save(recipeConsumer);
	}

	private static void woodenButton(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pButton, IItemProvider pMaterial) {
		ShapelessRecipeBuilder.shapeless(pButton)
				.requires(pMaterial)
				.group("wooden_button")
				.unlockedBy("has_planks", has(pMaterial))
				.save(recipeConsumer);
	}

	private static void woodenTrapdoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pTrapdoor, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pTrapdoor, 2)
				.define('#', pMaterial)
				.pattern("###")
				.pattern("###")
				.group("wooden_trapdoor")
				.unlockedBy("has_planks", has(pMaterial))
				.save(recipeConsumer);
	}
	
	private static void woodenDoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pDoor, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pDoor, 3)
				.define('#', pMaterial)
				.pattern("##")
				.pattern("##")
				.pattern("##")
				.group("wooden_door")
				.unlockedBy("has_planks", has(pMaterial))
				.save(recipeConsumer);
	}

	private static void woodenWood(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pWood, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pWood, 3)
				.define('#', pMaterial)
				.pattern("##")
				.pattern("##")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void woodenSign(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pSign, IItemProvider pMaterial, IItemProvider pStick) {
		ShapedRecipeBuilder.shaped(pSign, 3)
				.define('P', pMaterial)
				.define('S', pStick)
				.pattern("PPP")
				.pattern("PPP")
				.pattern(" S ")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.unlockedBy("has_" + pStick.asItem(), has(pStick))
				.save(recipeConsumer);
	}

	private static void leafCarpet(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pLeadCarpet, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pLeadCarpet)
				.define('#', pMaterial)
				.pattern("##")
				.group("leaf_carpet")
				.unlockedBy("has_planks", has(pMaterial))
				.save(recipeConsumer);
	}

	private static void slab(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pSlab, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pSlab, 6)
				.define('#', pMaterial)
				.pattern("###")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void stairs(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pStairs, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pStairs, 4)
				.define('#', pMaterial)
				.pattern("#  ")
				.pattern("## ")
				.pattern("###")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void wall(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pWall, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pWall, 6)
				.define('#', pMaterial)
				.pattern("###")
				.pattern("###")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void bricks(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pBricks, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pBricks, 3)
				.define('#', pMaterial)
				.pattern("##")
				.pattern("##")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void surroundItem(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pOutput, IItemProvider pSurroundMaterial, IItemProvider pItemMaterial) {
		ShapedRecipeBuilder.shaped(pOutput)
				.define('#', pSurroundMaterial)
				.define('X', pItemMaterial)
				.pattern("###")
				.pattern("#X#")
				.pattern("###")
				.unlockedBy("has_" + pSurroundMaterial.asItem(), has(pSurroundMaterial))
				.save(recipeConsumer);
	}

	private static void surroundItem(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pOutput, IItemProvider pSurroundMaterial, IItemProvider pItemMaterial, ResourceLocation name) {
		ShapedRecipeBuilder.shaped(pOutput)
				.define('#', pSurroundMaterial)
				.define('X', pItemMaterial)
				.pattern("###")
				.pattern("#X#")
				.pattern("###")
				.unlockedBy("has_" + pSurroundMaterial.asItem(), has(pSurroundMaterial))
				.save(recipeConsumer, name);
	}

	private static void armorHelmet(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pArmorHelmet, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pArmorHelmet)
				.define('#', pMaterial)
				.pattern("###")
				.pattern("# #")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void armorChestplate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pArmorChestplate, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pArmorChestplate)
				.define('#', pMaterial)
				.pattern("# #")
				.pattern("###")
				.pattern("###")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void armorLeggings(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pArmorLeggings, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pArmorLeggings)
				.define('#', pMaterial)
				.pattern("###")
				.pattern("# #")
				.pattern("# #")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void armorBoots(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pArmorBoots, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pArmorBoots)
				.define('#', pMaterial)
				.pattern("# #")
				.pattern("# #")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void toolSword(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pToolSword, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolSword)
				.define('S', Items.STICK)
				.define('#', pMaterial)
				.pattern("#")
				.pattern("#")
				.pattern("S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void toolShovel(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pToolShovel, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolShovel)
				.define('S', Items.STICK)
				.define('#', pMaterial)
				.pattern("#")
				.pattern("S")
				.pattern("S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void toolPickaxe(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pToolPickaxe, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolPickaxe)
				.define('S', Items.STICK)
				.define('#', pMaterial)
				.pattern("###")
				.pattern(" S ")
				.pattern(" S ")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void toolAxe(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pToolAxe, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolAxe)
				.define('S', Items.STICK)
				.define('#', pMaterial)
				.pattern("##")
				.pattern("#S")
				.pattern(" S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void toolHoe(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pToolHoe, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolHoe)
				.define('S', Items.STICK)
				.define('#', pMaterial)
				.pattern("##")
				.pattern(" S")
				.pattern(" S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void toolSword(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pToolSword, IItemProvider pToolStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolSword)
				.define('S', pToolStick)
				.define('#', pMaterial)
				.pattern("#")
				.pattern("#")
				.pattern("S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void toolShovel(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pToolShovel, IItemProvider pToolStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolShovel)
				.define('S', pToolStick)
				.define('#', pMaterial)
				.pattern("#")
				.pattern("S")
				.pattern("S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void toolPickaxe(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pToolPickaxe, IItemProvider pToolStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolPickaxe)
				.define('S', pToolStick)
				.define('#', pMaterial)
				.pattern("###")
				.pattern(" S ")
				.pattern(" S ")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void toolAxe(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pToolAxe, IItemProvider pToolStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolAxe)
				.define('S', pToolStick)
				.define('#', pMaterial)
				.pattern("##")
				.pattern("#S")
				.pattern(" S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void toolHoe(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pToolHoe, IItemProvider pToolStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolHoe)
				.define('S', pToolStick)
				.define('#', pMaterial)
				.pattern("##")
				.pattern(" S")
				.pattern(" S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}
	
	private static void boat(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pBoat, IItemProvider pPlank) {
		ShapedRecipeBuilder.shaped(pBoat)
				.define('#', pPlank)
				.pattern("# #")
				.pattern("###")
				.unlockedBy("has_" + pPlank.asItem(), has(pPlank))
				.save(recipeConsumer);
	}

	private static void materialToBlock(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pBlock, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pBlock)
				.define('#', pMaterial)
				.pattern("###")
				.pattern("###")
				.pattern("###")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(recipeConsumer);
	}

	private static void blockToMaterial(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pBlock, IItemProvider pMaterial) {
		ShapelessRecipeBuilder.shapeless(pMaterial, 9)
				.requires(pBlock)
				.unlockedBy("has_" + pBlock.asItem(), has(pBlock))
				.save(recipeConsumer);
	}

	private static void blockToMaterial(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pBlock, IItemProvider pMaterial, ResourceLocation name) {
		ShapelessRecipeBuilder.shapeless(pMaterial, 9)
				.requires(pBlock)
				.unlockedBy("has_" + pBlock.asItem(), has(pBlock))
				.save(recipeConsumer, name);
	}

	private static void nuggetToIngot(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pIngot, IItemProvider pNugget) {
		ShapedRecipeBuilder.shaped(pIngot)
				.define('#', pNugget)
				.pattern("###")
				.pattern("###")
				.pattern("###")
				.unlockedBy("has_" + pNugget.asItem(), has(pNugget))
				.save(recipeConsumer, "chaosawakens:" + pIngot + "_from_nuggets");
	}

	private static void ingotToNugget(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pIngot, IItemProvider pNugget) {
		ShapelessRecipeBuilder.shapeless(pNugget, 9)
				.requires(pIngot)
				.unlockedBy("has_" + pIngot.asItem(), has(pIngot))
				.save(recipeConsumer);
	}

	private static void stonecutting(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pInput, IItemProvider pOutput) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(pInput), pOutput)
				.unlocks("has_" + pInput.asItem(), InventoryChangeTrigger.Instance.hasItems(pInput))
				.save(recipeConsumer, ChaosAwakens.prefix(pOutput.asItem() + "_from_" + pInput.asItem() + "_stonecutting"));
	}

	private static void stonecutting(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pInput, IItemProvider pOutput, int count) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(pInput), pOutput, count)
				.unlocks("has_" + pInput.asItem(), InventoryChangeTrigger.Instance.hasItems(pInput))
				.save(recipeConsumer, ChaosAwakens.prefix(pOutput.asItem() + "_from_" + pInput.asItem() + "_stonecutting"));
	}

	private static void smelting(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pInput, IItemProvider pOutput, Float experience, int cookingTime) {
		CookingRecipeBuilder.smelting(Ingredient.of(pInput), pOutput, experience, cookingTime)
				.unlockedBy("has_" + pInput, has(pInput))
				.save(recipeConsumer, "chaosawakens:" + pOutput.asItem() + "_from_smelting");
	}

	private static void blasting(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pInput, IItemProvider pOutput, Float experience, int cookingTime) {
		CookingRecipeBuilder.blasting(Ingredient.of(pInput), pOutput, experience, cookingTime)
				.unlockedBy("has_" + pInput, has(pInput))
				.save(recipeConsumer, "chaosawakens:" + pOutput + "_from_blasting");
	}

	private static void cooking(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pInput, IItemProvider pOutput, int cookingTime, String CookingRecipeSerializerID, CookingRecipeSerializer<?> cookingRecipeSerializer) {
		CookingRecipeBuilder.cooking(Ingredient.of(pInput), pOutput, 0.35F, cookingTime, cookingRecipeSerializer)
				.unlockedBy("has_" + pInput.asItem(), has(pInput))
				.save(recipeConsumer, "chaosawakens:" + pOutput + "_from_" + CookingRecipeSerializerID);
	}

	private static void plantSeeds(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pSeeds, IItemProvider pPlant) {
		ShapelessRecipeBuilder.shapeless(pSeeds, 2)
				.requires(pPlant)
				.unlockedBy("has_" + pPlant.asItem(), has(pPlant))
				.save(recipeConsumer);
	}
	
	private static void bucket(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider materialInput, IItemProvider output) {
		ShapedRecipeBuilder.shaped(output)
				.define('M', materialInput)
				.pattern("M M")
				.pattern(" M ")
				.unlockedBy("has_" + materialInput.asItem(), has(materialInput))
				.save(recipeConsumer);
	}

	private static void philoConversionRecipe(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider toConvert, int aAmount, IItemProvider resultItem, int bAmount) {
		whenModLoaded(ShapelessRecipeBuilder.shapeless(resultItem, bAmount)
				.requires(PEItems.PHILOSOPHERS_STONE)
				.requires(toConvert, aAmount)
				.unlockedBy("has_" + toConvert.asItem(), hasItems(PEItems.PHILOSOPHERS_STONE, toConvert)), "projecte")
				.generateAdvancement()
				.build(recipeConsumer, ChaosAwakens.prefix("conversions/" + toConvert + "_to_" + resultItem));
		whenModLoaded(ShapelessRecipeBuilder.shapeless(toConvert, aAmount)
				.requires(PEItems.PHILOSOPHERS_STONE)
				.requires(resultItem, bAmount)
				.unlockedBy("has_" + resultItem.asItem(), hasItems(PEItems.PHILOSOPHERS_STONE, resultItem)), "projecte")
				.generateAdvancement()
				.build(recipeConsumer, ChaosAwakens.prefix("conversions/" + resultItem + "_to_" + toConvert));
	}

	public static ConditionalRecipe.Builder whenModLoaded(ShapelessRecipeBuilder recipe, String modid) {
		return ConditionalRecipe.builder().addCondition(new ModLoadedCondition(modid)).addRecipe(recipe::save);
	}

	protected static InventoryChangeTrigger.Instance hasItems(IItemProvider... items) {
		return InventoryChangeTrigger.Instance.hasItems(items);
	}
}
