package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CARecipes;
import io.github.chaosawakens.common.registry.CATags;
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
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		woodenPlanks(consumer, CABlocks.APPLE_PLANKS.get(), CATags.Items.APPLE_LOGS);
		woodenSlab(consumer, CABlocks.APPLE_SLAB.get(), CABlocks.APPLE_PLANKS.get());
		woodenStairs(consumer, CABlocks.APPLE_STAIRS.get(), CABlocks.APPLE_PLANKS.get());
		woodenTrapdoor(consumer, CABlocks.APPLE_TRAPDOOR.get(), CABlocks.APPLE_PLANKS.get());
		woodenDoor(consumer, CABlocks.APPLE_DOOR.get(), CABlocks.APPLE_PLANKS.get());
		woodenFence(consumer, CABlocks.APPLE_FENCE.get(), CABlocks.APPLE_PLANKS.get());
		woodenFenceGate(consumer, CABlocks.APPLE_FENCE_GATE.get(), CABlocks.APPLE_PLANKS.get());
		woodenPressurePlate(consumer, CABlocks.APPLE_PRESSURE_PLATE.get(), CABlocks.APPLE_PLANKS.get());
		woodenButton(consumer, CABlocks.APPLE_BUTTON.get(), CABlocks.APPLE_PLANKS.get());
		woodenWood(consumer, CABlocks.APPLE_WOOD.get(), CABlocks.APPLE_LOG.get());
		woodenSign(consumer, CAItems.APPLE_SIGN.get(), CABlocks.APPLE_PLANKS.get(), Items.STICK);
		woodenPlanks(consumer, CABlocks.CHERRY_PLANKS.get(), CATags.Items.CHERRY_LOGS);
		woodenSlab(consumer, CABlocks.CHERRY_SLAB.get(), CABlocks.CHERRY_PLANKS.get());
		woodenStairs(consumer, CABlocks.CHERRY_STAIRS.get(), CABlocks.CHERRY_PLANKS.get());
		woodenTrapdoor(consumer, CABlocks.CHERRY_TRAPDOOR.get(), CABlocks.CHERRY_PLANKS.get());
		woodenDoor(consumer, CABlocks.CHERRY_DOOR.get(), CABlocks.CHERRY_PLANKS.get());
		woodenFence(consumer, CABlocks.CHERRY_FENCE.get(), CABlocks.CHERRY_PLANKS.get());
		woodenFenceGate(consumer, CABlocks.CHERRY_FENCE_GATE.get(), CABlocks.CHERRY_PLANKS.get());
		woodenPressurePlate(consumer, CABlocks.CHERRY_PRESSURE_PLATE.get(), CABlocks.CHERRY_PLANKS.get());
		woodenButton(consumer, CABlocks.CHERRY_BUTTON.get(), CABlocks.CHERRY_PLANKS.get());
		woodenWood(consumer, CABlocks.CHERRY_WOOD.get(), CABlocks.CHERRY_LOG.get());
		woodenSign(consumer, CAItems.CHERRY_SIGN.get(), CABlocks.CHERRY_PLANKS.get(), Items.STICK);
		woodenPlanks(consumer, CABlocks.DUPLICATION_PLANKS.get(), CATags.Items.DUPLICATION_LOGS);
		woodenSlab(consumer, CABlocks.DUPLICATION_SLAB.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenStairs(consumer, CABlocks.DUPLICATION_STAIRS.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenTrapdoor(consumer, CABlocks.DUPLICATION_TRAPDOOR.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenDoor(consumer, CABlocks.DUPLICATION_DOOR.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenFence(consumer, CABlocks.DUPLICATION_FENCE.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenFenceGate(consumer, CABlocks.DUPLICATION_FENCE_GATE.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenPressurePlate(consumer, CABlocks.DUPLICATION_PRESSURE_PLATE.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenButton(consumer, CABlocks.DUPLICATION_BUTTON.get(), CABlocks.DUPLICATION_PLANKS.get());
		woodenWood(consumer, CABlocks.DUPLICATION_WOOD.get(), CABlocks.DUPLICATION_LOG.get());
		woodenSign(consumer, CAItems.DUPLICATION_SIGN.get(), CABlocks.DUPLICATION_PLANKS.get(), Items.STICK);
		woodenPlanks(consumer, CABlocks.GINKGO_PLANKS.get(), CATags.Items.GINKGO_LOGS);
		woodenSlab(consumer, CABlocks.GINKGO_SLAB.get(), CABlocks.GINKGO_PLANKS.get());
		woodenStairs(consumer, CABlocks.GINKGO_STAIRS.get(), CABlocks.GINKGO_PLANKS.get());
		woodenTrapdoor(consumer, CABlocks.GINKGO_TRAPDOOR.get(), CABlocks.GINKGO_PLANKS.get());
		woodenDoor(consumer, CABlocks.GINKGO_DOOR.get(), CABlocks.GINKGO_PLANKS.get());
		woodenFence(consumer, CABlocks.GINKGO_FENCE.get(), CABlocks.GINKGO_PLANKS.get());
		woodenFenceGate(consumer, CABlocks.GINKGO_FENCE_GATE.get(), CABlocks.GINKGO_PLANKS.get());
		woodenPressurePlate(consumer, CABlocks.GINKGO_PRESSURE_PLATE.get(), CABlocks.GINKGO_PLANKS.get());
		woodenButton(consumer, CABlocks.GINKGO_BUTTON.get(), CABlocks.GINKGO_PLANKS.get());
		woodenWood(consumer, CABlocks.GINKGO_WOOD.get(), CABlocks.GINKGO_LOG.get());
		woodenSign(consumer, CAItems.GINKGO_SIGN.get(), CABlocks.GINKGO_PLANKS.get(), Items.STICK);
		woodenPlanks(consumer, CABlocks.PEACH_PLANKS.get(), CATags.Items.PEACH_LOGS);
		woodenSlab(consumer, CABlocks.PEACH_SLAB.get(), CABlocks.PEACH_PLANKS.get());
		woodenStairs(consumer, CABlocks.PEACH_STAIRS.get(), CABlocks.PEACH_PLANKS.get());
		woodenTrapdoor(consumer, CABlocks.PEACH_TRAPDOOR.get(), CABlocks.PEACH_PLANKS.get());
		woodenDoor(consumer, CABlocks.PEACH_DOOR.get(), CABlocks.PEACH_PLANKS.get());
		woodenFence(consumer, CABlocks.PEACH_FENCE.get(), CABlocks.PEACH_PLANKS.get());
		woodenFenceGate(consumer, CABlocks.PEACH_FENCE_GATE.get(), CABlocks.PEACH_PLANKS.get());
		woodenPressurePlate(consumer, CABlocks.PEACH_PRESSURE_PLATE.get(), CABlocks.PEACH_PLANKS.get());
		woodenButton(consumer, CABlocks.PEACH_BUTTON.get(), CABlocks.PEACH_PLANKS.get());
		woodenWood(consumer, CABlocks.PEACH_WOOD.get(), CABlocks.PEACH_LOG.get());
		woodenPlanks(consumer, CABlocks.SKYWOOD_PLANKS.get(), CATags.Items.SKYWOOD_LOGS);
		woodenSlab(consumer, CABlocks.SKYWOOD_SLAB.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenStairs(consumer, CABlocks.SKYWOOD_STAIRS.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenTrapdoor(consumer, CABlocks.SKYWOOD_TRAPDOOR.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenDoor(consumer, CABlocks.SKYWOOD_DOOR.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenFence(consumer, CABlocks.SKYWOOD_FENCE.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenFenceGate(consumer, CABlocks.SKYWOOD_FENCE_GATE.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenPressurePlate(consumer, CABlocks.SKYWOOD_PRESSURE_PLATE.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenButton(consumer, CABlocks.SKYWOOD_BUTTON.get(), CABlocks.SKYWOOD_PLANKS.get());
		woodenWood(consumer, CABlocks.SKYWOOD_WOOD.get(), CABlocks.SKYWOOD_LOG.get());
		woodenSign(consumer, CAItems.PEACH_SIGN.get(), CABlocks.PEACH_PLANKS.get(), Items.STICK);
		woodenPlanks(consumer, CABlocks.CRYSTAL_PLANKS.get(), CABlocks.CRYSTAL_LOG.get());
		woodenSlab(consumer, CABlocks.CRYSTAL_SLAB.get(), CABlocks.CRYSTAL_PLANKS.get());
		woodenStairs(consumer, CABlocks.CRYSTAL_STAIRS.get(), CABlocks.CRYSTAL_PLANKS.get());
		woodenFence(consumer, CABlocks.CRYSTAL_FENCE.get(), CABlocks.CRYSTAL_PLANKS.get());
		woodenFenceGate(consumer, CABlocks.CRYSTAL_FENCE_GATE.get(), CABlocks.CRYSTAL_PLANKS.get());
		woodenPressurePlate(consumer, CABlocks.CRYSTAL_PRESSURE_PLATE.get(), CABlocks.CRYSTAL_PLANKS.get());
		woodenButton(consumer, CABlocks.CRYSTAL_BUTTON.get(), CABlocks.CRYSTAL_PLANKS.get());
		woodenWood(consumer, CABlocks.CRYSTAL_WOOD.get(), CABlocks.CRYSTAL_LOG.get());
		woodenSign(consumer, CAItems.SKYWOOD_SIGN.get(), CABlocks.SKYWOOD_PLANKS.get(), Items.STICK);

		leafCarpet(consumer, CABlocks.APPLE_LEAF_CARPET.get(), CABlocks.APPLE_LEAVES.get());
		leafCarpet(consumer, CABlocks.CHERRY_LEAF_CARPET.get(), CABlocks.CHERRY_LEAVES.get());
		leafCarpet(consumer, CABlocks.DUPLICATION_LEAF_CARPET.get(), CABlocks.DUPLICATION_LEAVES.get());
		leafCarpet(consumer, CABlocks.GINKGO_LEAF_CARPET.get(), CABlocks.GINKGO_LEAVES.get());
		leafCarpet(consumer, CABlocks.PEACH_LEAF_CARPET.get(), CABlocks.PEACH_LEAVES.get());
		leafCarpet(consumer, CABlocks.SKYWOOD_LEAF_CARPET.get(), CABlocks.SKYWOOD_LEAVES.get());
		leafCarpet(consumer, CABlocks.OAK_LEAF_CARPET.get(), Blocks.OAK_LEAVES);
		leafCarpet(consumer, CABlocks.SPRUCE_LEAF_CARPET.get(), Blocks.SPRUCE_LEAVES);
		leafCarpet(consumer, CABlocks.BIRCH_LEAF_CARPET.get(), Blocks.BIRCH_LEAVES);
		leafCarpet(consumer, CABlocks.JUNGLE_LEAF_CARPET.get(), Blocks.JUNGLE_LEAVES);
		leafCarpet(consumer, CABlocks.ACACIA_LEAF_CARPET.get(), Blocks.ACACIA_LEAVES);
		leafCarpet(consumer, CABlocks.DARK_OAK_LEAF_CARPET.get(), Blocks.DARK_OAK_LEAVES);

		ShapedRecipeBuilder.shaped(CABlocks.CRYSTAL_CRAFTING_TABLE.get())
				.define('#', CABlocks.CRYSTAL_PLANKS.get())
				.pattern("##")
				.pattern("##")
				.unlockedBy("has_" + CABlocks.CRYSTAL_PLANKS.get().asItem(), has(CABlocks.CRYSTAL_PLANKS.get()))
				.save(consumer);
		ShapedRecipeBuilder.shaped(CABlocks.CRYSTAL_FURNACE.get())
				.define('#', CABlocks.KYANITE.get())
				.pattern("###")
				.pattern("# #")
				.pattern("###")
				.unlockedBy("has_" + CABlocks.KYANITE.get().asItem(), has(CABlocks.KYANITE.get())).save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.CRYSTAL_SHARD.get(), 4)
				.define('#', CABlocks.CRYSTAL_PLANKS.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.CRYSTAL_PLANKS.get().asItem(), has(CABlocks.CRYSTAL_PLANKS.get()))
				.save(consumer);

		ShapedRecipeBuilder.shaped(CAItems.CRYSTAL_TORCH.get(), 4)
				.define('#', CABlocks.CRYSTAL_ENERGY.get())
				.define('$', CAItems.CRYSTAL_SHARD.get())
				.pattern("#")
				.pattern("$")
				.unlockedBy("has_" + CABlocks.CRYSTAL_ENERGY.get().asItem(), has(CABlocks.CRYSTAL_ENERGY.get()))
				.unlockedBy("has_" + CAItems.CRYSTAL_SHARD.get().asItem(), has(CAItems.CRYSTAL_SHARD.get()))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.EXTREME_TORCH.get(), 2)
				.requires(Tags.Items.RODS_WOODEN)
				.requires(ItemTags.COALS)
				.requires(Tags.Items.DUSTS_REDSTONE)
				.requires(CAItems.SUNSTONE.get())
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN))
				.unlockedBy("has_" + ItemTags.COALS.getName(), has(ItemTags.COALS))
				.unlockedBy("has_" + Tags.Items.DUSTS_REDSTONE.getName(), has(Tags.Items.DUSTS_REDSTONE))
				.unlockedBy("has_" + CAItems.SUNSTONE.get().asItem(), has(CAItems.SUNSTONE.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.EXTREME_TORCH.get(), 2)
				.requires(Items.REDSTONE_TORCH)
				.requires(ItemTags.COALS)
				.requires(CAItems.SUNSTONE.get())
				.unlockedBy("has_" + Items.REDSTONE_TORCH.asItem(), has(Items.REDSTONE_TORCH))
				.unlockedBy("has_" + ItemTags.COALS.getName(), has(ItemTags.COALS))
				.unlockedBy("has_" + CAItems.SUNSTONE.get().asItem(), has(CAItems.SUNSTONE.get()))
				.save(consumer, "chaosawakens:extreme_torch_from_redstone_torch");
		ShapelessRecipeBuilder.shapeless(CAItems.EXTREME_TORCH.get(), 2)
				.requires(CAItems.SUNSTONE_TORCH.get())
				.requires(ItemTags.COALS)
				.requires(Tags.Items.DUSTS_REDSTONE)
				.unlockedBy("has_" + CAItems.SUNSTONE_TORCH.get().asItem(), has(CAItems.SUNSTONE_TORCH.get()))
				.unlockedBy("has_" + ItemTags.COALS.getName(), has(ItemTags.COALS))
				.unlockedBy("has_" + Tags.Items.DUSTS_REDSTONE.getName(), has(Tags.Items.DUSTS_REDSTONE))
				.save(consumer, "chaosawakens:extreme_torch_from_sunstone_torch");
		ShapelessRecipeBuilder.shapeless(CAItems.EXTREME_TORCH.get(), 2)
				.requires(Items.TORCH)
				.requires(Tags.Items.DUSTS_REDSTONE)
				.requires(CAItems.SUNSTONE.get())
				.unlockedBy("has_" + Items.TORCH.asItem(), has(Items.TORCH))
				.unlockedBy("has_" + Tags.Items.DUSTS_REDSTONE.getName(), has(Tags.Items.DUSTS_REDSTONE))
				.unlockedBy("has_" + CAItems.SUNSTONE.get().asItem(), has(CAItems.SUNSTONE.get()))
				.save(consumer, "chaosawakens:extreme_torch_from_torch");
		ShapedRecipeBuilder.shaped(CAItems.SUNSTONE_TORCH.get(), 2)
				.define('#', CAItems.SUNSTONE.get())
				.define('$', Tags.Items.RODS_WOODEN)
				.pattern("#")
				.pattern("$")
				.unlockedBy("has_" + CAItems.SUNSTONE.get().asItem(), has(CAItems.SUNSTONE.get()))
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN)).save(consumer);

		ShapedRecipeBuilder.shaped(CAItems.BEETROOT_ON_A_STICK.get())
				.define('S', Tags.Items.RODS_WOODEN)
				.define('B', Items.BEETROOT)
				.pattern("  S")
				.pattern(" SB")
				.pattern("S  ")
				.unlockedBy("has_" + Items.BEETROOT, has(Items.BEETROOT))
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN)).save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.CRYSTAL_BEETROOT_ON_A_STICK.get())
				.define('S', Tags.Items.RODS_WOODEN)
				.define('B', CAItems.CRYSTAL_BEETROOT.get())
				.pattern("  S")
				.pattern(" SB")
				.pattern("S  ")
				.unlockedBy("has_" + CAItems.CRYSTAL_BEETROOT.get().asItem(), has(CAItems.CRYSTAL_BEETROOT.get()))
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN)).save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.GOLDEN_BEETROOT_ON_A_STICK.get())
				.define('S', Tags.Items.RODS_WOODEN)
				.define('B', CAItems.GOLDEN_BEETROOT.get())
				.pattern("  S")
				.pattern(" SB")
				.pattern("S  ")
				.unlockedBy("has_" + CAItems.GOLDEN_BEETROOT.get().asItem(), has(CAItems.GOLDEN_BEETROOT.get()))
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN)).save(consumer);

		surroundItem(consumer, CAItems.POISON_SWORD.get(), CAItems.DEAD_STINK_BUG.get(), CAItems.EMERALD_SWORD.get());
	//	surroundItem(consumer, CAItems.SLAYER_CHAINSAW.get(), Blocks.REDSTONE_BLOCK, CAItems.ULTIMATE_AXE.get());
		surroundItem(consumer, CAItems.EXPERIENCE_SWORD.get(), Items.EXPERIENCE_BOTTLE, CAItems.EMERALD_SWORD.get());
		surroundItem(consumer, CAItems.EXPERIENCE_HELMET.get(), Items.EXPERIENCE_BOTTLE, CAItems.EMERALD_HELMET.get());
		surroundItem(consumer, CAItems.EXPERIENCE_CHESTPLATE.get(), Items.EXPERIENCE_BOTTLE, CAItems.EMERALD_CHESTPLATE.get());
		surroundItem(consumer, CAItems.EXPERIENCE_LEGGINGS.get(), Items.EXPERIENCE_BOTTLE, CAItems.EMERALD_LEGGINGS.get());
		surroundItem(consumer, CAItems.EXPERIENCE_BOOTS.get(), Items.EXPERIENCE_BOTTLE, CAItems.EMERALD_BOOTS.get());

		slab(consumer, CABlocks.MARBLE_SLAB.get(), CABlocks.MARBLE.get());
		stairs(consumer, CABlocks.MARBLE_STAIRS.get(), CABlocks.MARBLE.get());
		wall(consumer, CABlocks.MARBLE_WALL.get(), CABlocks.MARBLE.get());
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_STAIRS.get());
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_BRICKS.get());
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.POLISHED_MARBLE.get());
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.POLISHED_MARBLE_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.POLISHED_MARBLE_STAIRS.get());
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.POLISHED_MARBLE_WALL.get());
		stonecutting(consumer, CABlocks.MARBLE.get(), CABlocks.MARBLE_PILLAR.get());

		bricks(consumer, CABlocks.MARBLE_BRICKS.get(),  CABlocks.POLISHED_MARBLE.get());
		slab(consumer, CABlocks.MARBLE_BRICK_SLAB.get(), CABlocks.MARBLE_BRICKS.get());
		stairs(consumer, CABlocks.MARBLE_BRICK_STAIRS.get(), CABlocks.MARBLE_BRICKS.get());
		wall(consumer, CABlocks.MARBLE_BRICK_WALL.get(), CABlocks.MARBLE_BRICKS.get());
		stonecutting(consumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.MARBLE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.MARBLE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		stonecutting(consumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_WALL.get());

		ShapelessRecipeBuilder.shapeless(CABlocks.MOSSY_MARBLE_BRICKS.get())
				.requires(CABlocks.MARBLE_BRICKS.get())
				.requires(Items.VINE)
				.unlockedBy("has_" + CABlocks.MARBLE_BRICKS.get().asItem(), has(CABlocks.MARBLE_BRICKS.get()))
				.unlockedBy("has_" + Items.VINE.asItem(), has(Items.VINE))
				.save(consumer);
		slab(consumer, CABlocks.MOSSY_MARBLE_BRICK_SLAB.get(), CABlocks.MOSSY_MARBLE_BRICKS.get());
		stairs(consumer, CABlocks.MOSSY_MARBLE_BRICK_STAIRS.get(), CABlocks.MOSSY_MARBLE_BRICKS.get());
		wall(consumer, CABlocks.MOSSY_MARBLE_BRICK_WALL.get(), CABlocks.MOSSY_MARBLE_BRICKS.get());
		stonecutting(consumer, CABlocks.MOSSY_MARBLE_BRICKS.get(), CABlocks.MOSSY_MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.MOSSY_MARBLE_BRICKS.get(), CABlocks.MOSSY_MARBLE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.MOSSY_MARBLE_BRICKS.get(), CABlocks.MOSSY_MARBLE_BRICK_WALL.get());

		ShapedRecipeBuilder.shaped(CABlocks.CHISELED_MARBLE_BRICKS.get())
				.define('#', CABlocks.MARBLE_BRICK_SLAB.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.MARBLE_BRICK_SLAB.get().asItem(), has(CABlocks.MARBLE_BRICK_SLAB.get()))
				.save(consumer);
		slab(consumer, CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		stairs(consumer, CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		wall(consumer, CABlocks.CHISELED_MARBLE_BRICK_WALL.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		stonecutting(consumer, CABlocks.CHISELED_MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CHISELED_MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CHISELED_MARBLE_BRICKS.get(), CABlocks.CHISELED_MARBLE_BRICK_WALL.get());

		bricks(consumer, CABlocks.POLISHED_MARBLE.get(),  CABlocks.MARBLE.get());
		slab(consumer, CABlocks.POLISHED_MARBLE_SLAB.get(), CABlocks.POLISHED_MARBLE.get());
		stairs(consumer, CABlocks.POLISHED_MARBLE_STAIRS.get(), CABlocks.POLISHED_MARBLE.get());
		wall(consumer, CABlocks.POLISHED_MARBLE_WALL.get(), CABlocks.POLISHED_MARBLE.get());
		ShapedRecipeBuilder.shaped(CABlocks.MARBLE_PILLAR.get())
				.define('#', CABlocks.POLISHED_MARBLE.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.POLISHED_MARBLE.get().asItem(), has(CABlocks.POLISHED_MARBLE.get()))
				.save(consumer);
		stonecutting(consumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.POLISHED_MARBLE_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.POLISHED_MARBLE_STAIRS.get());
		stonecutting(consumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.POLISHED_MARBLE_WALL.get());
		stonecutting(consumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.MARBLE_BRICKS.get());
		stonecutting(consumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.MARBLE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.MARBLE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICKS.get());
		stonecutting(consumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.POLISHED_MARBLE.get(), CABlocks.CHISELED_MARBLE_BRICK_WALL.get());

		smelting(consumer, CABlocks.MARBLE_BRICKS.get(), CABlocks.CRACKED_MARBLE_BRICKS.get(), 0.1F, 200);
		slab(consumer, CABlocks.CRACKED_MARBLE_BRICK_SLAB.get(), CABlocks.CRACKED_MARBLE_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_MARBLE_BRICK_STAIRS.get(), CABlocks.CRACKED_MARBLE_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_MARBLE_BRICK_WALL.get(), CABlocks.CRACKED_MARBLE_BRICKS.get());

		slab(consumer, CABlocks.LIMESTONE_SLAB.get(), CABlocks.LIMESTONE.get());
		stairs(consumer, CABlocks.LIMESTONE_STAIRS.get(), CABlocks.LIMESTONE.get());
		wall(consumer, CABlocks.LIMESTONE_WALL.get(), CABlocks.LIMESTONE.get());
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_STAIRS.get());
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE.get());
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_STAIRS.get());
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_WALL.get());
		stonecutting(consumer, CABlocks.LIMESTONE.get(), CABlocks.LIMESTONE_PILLAR.get());

		bricks(consumer, CABlocks.LIMESTONE_BRICKS.get(),  CABlocks.POLISHED_LIMESTONE.get());
		slab(consumer, CABlocks.LIMESTONE_BRICK_SLAB.get(), CABlocks.LIMESTONE_BRICKS.get());
		stairs(consumer, CABlocks.LIMESTONE_BRICK_STAIRS.get(), CABlocks.LIMESTONE_BRICKS.get());
		wall(consumer, CABlocks.LIMESTONE_BRICK_WALL.get(), CABlocks.LIMESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.LIMESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.LIMESTONE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get());

		ShapelessRecipeBuilder.shapeless(CABlocks.MOSSY_LIMESTONE_BRICKS.get())
				.requires(CABlocks.LIMESTONE_BRICKS.get())
				.requires(Items.VINE)
				.unlockedBy("has_" + CABlocks.LIMESTONE_BRICKS.get().asItem(), has(CABlocks.LIMESTONE_BRICKS.get()))
				.unlockedBy("has_" + Items.VINE.asItem(), has(Items.VINE))
				.save(consumer);
		slab(consumer, CABlocks.MOSSY_LIMESTONE_BRICK_SLAB.get(), CABlocks.MOSSY_LIMESTONE_BRICKS.get());
		stairs(consumer, CABlocks.MOSSY_LIMESTONE_BRICK_STAIRS.get(), CABlocks.MOSSY_LIMESTONE_BRICKS.get());
		wall(consumer, CABlocks.MOSSY_LIMESTONE_BRICK_WALL.get(), CABlocks.MOSSY_LIMESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.MOSSY_LIMESTONE_BRICKS.get(), CABlocks.MOSSY_LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.MOSSY_LIMESTONE_BRICKS.get(), CABlocks.MOSSY_LIMESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.MOSSY_LIMESTONE_BRICKS.get(), CABlocks.MOSSY_LIMESTONE_BRICK_WALL.get());

		ShapedRecipeBuilder.shaped(CABlocks.CHISELED_LIMESTONE_BRICKS.get())
				.define('#', CABlocks.LIMESTONE_BRICK_SLAB.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.LIMESTONE_BRICK_SLAB.get().asItem(), has(CABlocks.LIMESTONE_BRICK_SLAB.get()))
				.save(consumer);
		slab(consumer, CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		stairs(consumer, CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		wall(consumer, CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.CHISELED_LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CHISELED_LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CHISELED_LIMESTONE_BRICKS.get(), CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get());

		bricks(consumer, CABlocks.POLISHED_LIMESTONE.get(),  CABlocks.LIMESTONE.get());
		slab(consumer, CABlocks.POLISHED_LIMESTONE_SLAB.get(), CABlocks.POLISHED_LIMESTONE.get());
		stairs(consumer, CABlocks.POLISHED_LIMESTONE_STAIRS.get(), CABlocks.POLISHED_LIMESTONE.get());
		wall(consumer, CABlocks.POLISHED_LIMESTONE_WALL.get(), CABlocks.POLISHED_LIMESTONE.get());
		ShapedRecipeBuilder.shaped(CABlocks.LIMESTONE_PILLAR.get())
				.define('#', CABlocks.POLISHED_LIMESTONE.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.POLISHED_LIMESTONE.get().asItem(), has(CABlocks.POLISHED_LIMESTONE.get()))
				.save(consumer);
		stonecutting(consumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_STAIRS.get());
		stonecutting(consumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.POLISHED_LIMESTONE_WALL.get());
		stonecutting(consumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.LIMESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.LIMESTONE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.POLISHED_LIMESTONE.get(), CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get());

		smelting(consumer, CABlocks.LIMESTONE_BRICKS.get(), CABlocks.CRACKED_LIMESTONE_BRICKS.get(), 0.1F, 200);
		slab(consumer, CABlocks.CRACKED_LIMESTONE_BRICK_SLAB.get(), CABlocks.CRACKED_LIMESTONE_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_LIMESTONE_BRICK_STAIRS.get(), CABlocks.CRACKED_LIMESTONE_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_LIMESTONE_BRICK_WALL.get(), CABlocks.CRACKED_LIMESTONE_BRICKS.get());
		
		slab(consumer, CABlocks.RHINESTONE_SLAB.get(), CABlocks.RHINESTONE.get());
		stairs(consumer, CABlocks.RHINESTONE_STAIRS.get(), CABlocks.RHINESTONE.get());
		wall(consumer, CABlocks.RHINESTONE_WALL.get(), CABlocks.RHINESTONE.get());
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_STAIRS.get());
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE.get());
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_STAIRS.get());
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_WALL.get());
		stonecutting(consumer, CABlocks.RHINESTONE.get(), CABlocks.RHINESTONE_PILLAR.get());

		bricks(consumer, CABlocks.RHINESTONE_BRICKS.get(),  CABlocks.POLISHED_RHINESTONE.get());
		slab(consumer, CABlocks.RHINESTONE_BRICK_SLAB.get(), CABlocks.RHINESTONE_BRICKS.get());
		stairs(consumer, CABlocks.RHINESTONE_BRICK_STAIRS.get(), CABlocks.RHINESTONE_BRICKS.get());
		wall(consumer, CABlocks.RHINESTONE_BRICK_WALL.get(), CABlocks.RHINESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.RHINESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.RHINESTONE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get());

		ShapelessRecipeBuilder.shapeless(CABlocks.MOSSY_RHINESTONE_BRICKS.get())
				.requires(CABlocks.RHINESTONE_BRICKS.get())
				.requires(Items.VINE)
				.unlockedBy("has_" + CABlocks.RHINESTONE_BRICKS.get().asItem(), has(CABlocks.RHINESTONE_BRICKS.get()))
				.unlockedBy("has_" + Items.VINE.asItem(), has(Items.VINE))
				.save(consumer);
		slab(consumer, CABlocks.MOSSY_RHINESTONE_BRICK_SLAB.get(), CABlocks.MOSSY_RHINESTONE_BRICKS.get());
		stairs(consumer, CABlocks.MOSSY_RHINESTONE_BRICK_STAIRS.get(), CABlocks.MOSSY_RHINESTONE_BRICKS.get());
		wall(consumer, CABlocks.MOSSY_RHINESTONE_BRICK_WALL.get(), CABlocks.MOSSY_RHINESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.MOSSY_RHINESTONE_BRICKS.get(), CABlocks.MOSSY_RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.MOSSY_RHINESTONE_BRICKS.get(), CABlocks.MOSSY_RHINESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.MOSSY_RHINESTONE_BRICKS.get(), CABlocks.MOSSY_RHINESTONE_BRICK_WALL.get());

		ShapedRecipeBuilder.shaped(CABlocks.CHISELED_RHINESTONE_BRICKS.get())
				.define('#', CABlocks.RHINESTONE_BRICK_SLAB.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.RHINESTONE_BRICK_SLAB.get().asItem(), has(CABlocks.RHINESTONE_BRICK_SLAB.get()))
				.save(consumer);
		slab(consumer, CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		stairs(consumer, CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		wall(consumer, CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.CHISELED_RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CHISELED_RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CHISELED_RHINESTONE_BRICKS.get(), CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get());

		bricks(consumer, CABlocks.POLISHED_RHINESTONE.get(),  CABlocks.RHINESTONE.get());
		slab(consumer, CABlocks.POLISHED_RHINESTONE_SLAB.get(), CABlocks.POLISHED_RHINESTONE.get());
		stairs(consumer, CABlocks.POLISHED_RHINESTONE_STAIRS.get(), CABlocks.POLISHED_RHINESTONE.get());
		wall(consumer, CABlocks.POLISHED_RHINESTONE_WALL.get(), CABlocks.POLISHED_RHINESTONE.get());
		ShapedRecipeBuilder.shaped(CABlocks.RHINESTONE_PILLAR.get())
				.define('#', CABlocks.POLISHED_RHINESTONE.get())
				.pattern("#")
				.pattern("#")
				.unlockedBy("has_" + CABlocks.POLISHED_RHINESTONE.get().asItem(), has(CABlocks.POLISHED_RHINESTONE.get()))
				.save(consumer);
		stonecutting(consumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_STAIRS.get());
		stonecutting(consumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.POLISHED_RHINESTONE_WALL.get());
		stonecutting(consumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.RHINESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.RHINESTONE_BRICK_WALL.get());
		stonecutting(consumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		stonecutting(consumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.POLISHED_RHINESTONE.get(), CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get());

		smelting(consumer, CABlocks.RHINESTONE_BRICKS.get(), CABlocks.CRACKED_RHINESTONE_BRICKS.get(), 0.1F, 200);
		slab(consumer, CABlocks.CRACKED_RHINESTONE_BRICK_SLAB.get(), CABlocks.CRACKED_RHINESTONE_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_RHINESTONE_BRICK_STAIRS.get(), CABlocks.CRACKED_RHINESTONE_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_RHINESTONE_BRICK_WALL.get(), CABlocks.CRACKED_RHINESTONE_BRICKS.get());

		stonecutting(consumer, Blocks.TERRACOTTA, CABlocks.TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.TERRACOTTA, CABlocks.TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.TERRACOTTA, CABlocks.TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.TERRACOTTA, CABlocks.TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.TERRACOTTA_BRICKS.get(),  Blocks.TERRACOTTA);
		stonecutting(consumer, CABlocks.TERRACOTTA_BRICKS.get(), CABlocks.TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.TERRACOTTA_BRICKS.get(), CABlocks.TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.TERRACOTTA_BRICKS.get(), CABlocks.TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.TERRACOTTA_BRICK_SLAB.get(), CABlocks.TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.TERRACOTTA_BRICK_STAIRS.get(), CABlocks.TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.TERRACOTTA_BRICK_WALL.get(), CABlocks.TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.WHITE_TERRACOTTA, CABlocks.WHITE_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.WHITE_TERRACOTTA, CABlocks.WHITE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.WHITE_TERRACOTTA, CABlocks.WHITE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.WHITE_TERRACOTTA, CABlocks.WHITE_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.WHITE_TERRACOTTA_BRICKS.get(),  Blocks.WHITE_TERRACOTTA);
		stonecutting(consumer, CABlocks.WHITE_TERRACOTTA_BRICKS.get(), CABlocks.WHITE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.WHITE_TERRACOTTA_BRICKS.get(), CABlocks.WHITE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.WHITE_TERRACOTTA_BRICKS.get(), CABlocks.WHITE_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.WHITE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.WHITE_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.WHITE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.WHITE_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.WHITE_TERRACOTTA_BRICK_WALL.get(), CABlocks.WHITE_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.WHITE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.ORANGE_TERRACOTTA, CABlocks.ORANGE_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.ORANGE_TERRACOTTA, CABlocks.ORANGE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.ORANGE_TERRACOTTA, CABlocks.ORANGE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.ORANGE_TERRACOTTA, CABlocks.ORANGE_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.ORANGE_TERRACOTTA_BRICKS.get(),  Blocks.ORANGE_TERRACOTTA);
		stonecutting(consumer, CABlocks.ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.ORANGE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.ORANGE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.ORANGE_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.ORANGE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.ORANGE_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.ORANGE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.ORANGE_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.ORANGE_TERRACOTTA_BRICK_WALL.get(), CABlocks.ORANGE_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.MAGENTA_TERRACOTTA, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.MAGENTA_TERRACOTTA, CABlocks.MAGENTA_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.MAGENTA_TERRACOTTA, CABlocks.MAGENTA_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.MAGENTA_TERRACOTTA, CABlocks.MAGENTA_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get(),  Blocks.MAGENTA_TERRACOTTA);
		stonecutting(consumer, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.MAGENTA_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.MAGENTA_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.MAGENTA_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.MAGENTA_TERRACOTTA_BRICK_SLAB.get(), CABlocks.MAGENTA_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.MAGENTA_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.MAGENTA_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.MAGENTA_TERRACOTTA_BRICK_WALL.get(), CABlocks.MAGENTA_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.LIGHT_BLUE_TERRACOTTA, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.LIGHT_BLUE_TERRACOTTA, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.LIGHT_BLUE_TERRACOTTA, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.LIGHT_BLUE_TERRACOTTA, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(),  Blocks.LIGHT_BLUE_TERRACOTTA);
		stonecutting(consumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get(), CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.YELLOW_TERRACOTTA, CABlocks.YELLOW_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.YELLOW_TERRACOTTA, CABlocks.YELLOW_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.YELLOW_TERRACOTTA, CABlocks.YELLOW_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.YELLOW_TERRACOTTA, CABlocks.YELLOW_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.YELLOW_TERRACOTTA_BRICKS.get(),  Blocks.YELLOW_TERRACOTTA);
		stonecutting(consumer, CABlocks.YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.YELLOW_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.YELLOW_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.YELLOW_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.YELLOW_TERRACOTTA_BRICK_SLAB.get(), CABlocks.YELLOW_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.YELLOW_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.YELLOW_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.YELLOW_TERRACOTTA_BRICK_WALL.get(), CABlocks.YELLOW_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.LIME_TERRACOTTA, CABlocks.LIME_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.LIME_TERRACOTTA, CABlocks.LIME_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.LIME_TERRACOTTA, CABlocks.LIME_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.LIME_TERRACOTTA, CABlocks.LIME_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.LIME_TERRACOTTA_BRICKS.get(),  Blocks.LIME_TERRACOTTA);
		stonecutting(consumer, CABlocks.LIME_TERRACOTTA_BRICKS.get(), CABlocks.LIME_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.LIME_TERRACOTTA_BRICKS.get(), CABlocks.LIME_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.LIME_TERRACOTTA_BRICKS.get(), CABlocks.LIME_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.LIME_TERRACOTTA_BRICK_SLAB.get(), CABlocks.LIME_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.LIME_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.LIME_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.LIME_TERRACOTTA_BRICK_WALL.get(), CABlocks.LIME_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.LIME_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.PINK_TERRACOTTA, CABlocks.PINK_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.PINK_TERRACOTTA, CABlocks.PINK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.PINK_TERRACOTTA, CABlocks.PINK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.PINK_TERRACOTTA, CABlocks.PINK_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.PINK_TERRACOTTA_BRICKS.get(),  Blocks.PINK_TERRACOTTA);
		stonecutting(consumer, CABlocks.PINK_TERRACOTTA_BRICKS.get(), CABlocks.PINK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.PINK_TERRACOTTA_BRICKS.get(), CABlocks.PINK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.PINK_TERRACOTTA_BRICKS.get(), CABlocks.PINK_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.PINK_TERRACOTTA_BRICK_SLAB.get(), CABlocks.PINK_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.PINK_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.PINK_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.PINK_TERRACOTTA_BRICK_WALL.get(), CABlocks.PINK_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.PINK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.GRAY_TERRACOTTA, CABlocks.GRAY_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.GRAY_TERRACOTTA, CABlocks.GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.GRAY_TERRACOTTA, CABlocks.GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.GRAY_TERRACOTTA, CABlocks.GRAY_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.GRAY_TERRACOTTA_BRICKS.get(),  Blocks.GRAY_TERRACOTTA);
		stonecutting(consumer, CABlocks.GRAY_TERRACOTTA_BRICKS.get(), CABlocks.GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.GRAY_TERRACOTTA_BRICKS.get(), CABlocks.GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.GRAY_TERRACOTTA_BRICKS.get(), CABlocks.GRAY_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.GRAY_TERRACOTTA_BRICK_SLAB.get(), CABlocks.GRAY_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.GRAY_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.GRAY_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.GRAY_TERRACOTTA_BRICK_WALL.get(), CABlocks.GRAY_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.LIGHT_GRAY_TERRACOTTA, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.LIGHT_GRAY_TERRACOTTA, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.LIGHT_GRAY_TERRACOTTA, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.LIGHT_GRAY_TERRACOTTA, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(),  Blocks.LIGHT_GRAY_TERRACOTTA);
		stonecutting(consumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get(), CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.CYAN_TERRACOTTA, CABlocks.CYAN_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.CYAN_TERRACOTTA, CABlocks.CYAN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.CYAN_TERRACOTTA, CABlocks.CYAN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.CYAN_TERRACOTTA, CABlocks.CYAN_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.CYAN_TERRACOTTA_BRICKS.get(),  Blocks.CYAN_TERRACOTTA);
		stonecutting(consumer, CABlocks.CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CYAN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CYAN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CYAN_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CYAN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CYAN_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CYAN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CYAN_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CYAN_TERRACOTTA_BRICK_WALL.get(), CABlocks.CYAN_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.PURPLE_TERRACOTTA, CABlocks.PURPLE_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.PURPLE_TERRACOTTA, CABlocks.PURPLE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.PURPLE_TERRACOTTA, CABlocks.PURPLE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.PURPLE_TERRACOTTA, CABlocks.PURPLE_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.PURPLE_TERRACOTTA_BRICKS.get(),  Blocks.PURPLE_TERRACOTTA);
		stonecutting(consumer, CABlocks.PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.PURPLE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.PURPLE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.PURPLE_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.PURPLE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.PURPLE_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.PURPLE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.PURPLE_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.PURPLE_TERRACOTTA_BRICK_WALL.get(), CABlocks.PURPLE_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.BLUE_TERRACOTTA, CABlocks.BLUE_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.BLUE_TERRACOTTA, CABlocks.BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.BLUE_TERRACOTTA, CABlocks.BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.BLUE_TERRACOTTA, CABlocks.BLUE_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.BLUE_TERRACOTTA_BRICKS.get(),  Blocks.BLUE_TERRACOTTA);
		stonecutting(consumer, CABlocks.BLUE_TERRACOTTA_BRICKS.get(), CABlocks.BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.BLUE_TERRACOTTA_BRICKS.get(), CABlocks.BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.BLUE_TERRACOTTA_BRICKS.get(), CABlocks.BLUE_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.BLUE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.BLUE_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.BLUE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.BLUE_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.BLUE_TERRACOTTA_BRICK_WALL.get(), CABlocks.BLUE_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.BROWN_TERRACOTTA, CABlocks.BROWN_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.BROWN_TERRACOTTA, CABlocks.BROWN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.BROWN_TERRACOTTA, CABlocks.BROWN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.BROWN_TERRACOTTA, CABlocks.BROWN_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.BROWN_TERRACOTTA_BRICKS.get(),  Blocks.BROWN_TERRACOTTA);
		stonecutting(consumer, CABlocks.BROWN_TERRACOTTA_BRICKS.get(), CABlocks.BROWN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.BROWN_TERRACOTTA_BRICKS.get(), CABlocks.BROWN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.BROWN_TERRACOTTA_BRICKS.get(), CABlocks.BROWN_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.BROWN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.BROWN_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.BROWN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.BROWN_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.BROWN_TERRACOTTA_BRICK_WALL.get(), CABlocks.BROWN_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.BROWN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.GREEN_TERRACOTTA, CABlocks.GREEN_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.GREEN_TERRACOTTA, CABlocks.GREEN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.GREEN_TERRACOTTA, CABlocks.GREEN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.GREEN_TERRACOTTA, CABlocks.GREEN_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.GREEN_TERRACOTTA_BRICKS.get(),  Blocks.GREEN_TERRACOTTA);
		stonecutting(consumer, CABlocks.GREEN_TERRACOTTA_BRICKS.get(), CABlocks.GREEN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.GREEN_TERRACOTTA_BRICKS.get(), CABlocks.GREEN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.GREEN_TERRACOTTA_BRICKS.get(), CABlocks.GREEN_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.GREEN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.GREEN_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.GREEN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.GREEN_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.GREEN_TERRACOTTA_BRICK_WALL.get(), CABlocks.GREEN_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.GREEN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.RED_TERRACOTTA, CABlocks.RED_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.RED_TERRACOTTA, CABlocks.RED_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.RED_TERRACOTTA, CABlocks.RED_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.RED_TERRACOTTA, CABlocks.RED_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.RED_TERRACOTTA_BRICKS.get(),  Blocks.RED_TERRACOTTA);
		stonecutting(consumer, CABlocks.RED_TERRACOTTA_BRICKS.get(), CABlocks.RED_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.RED_TERRACOTTA_BRICKS.get(), CABlocks.RED_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.RED_TERRACOTTA_BRICKS.get(), CABlocks.RED_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.RED_TERRACOTTA_BRICK_SLAB.get(), CABlocks.RED_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.RED_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.RED_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.RED_TERRACOTTA_BRICK_WALL.get(), CABlocks.RED_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.RED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_RED_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.BLACK_TERRACOTTA, CABlocks.BLACK_TERRACOTTA_BRICKS.get());
		stonecutting(consumer, Blocks.BLACK_TERRACOTTA, CABlocks.BLACK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, Blocks.BLACK_TERRACOTTA, CABlocks.BLACK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, Blocks.BLACK_TERRACOTTA, CABlocks.BLACK_TERRACOTTA_BRICK_WALL.get());
		bricks(consumer, CABlocks.BLACK_TERRACOTTA_BRICKS.get(),  Blocks.BLACK_TERRACOTTA);
		stonecutting(consumer, CABlocks.BLACK_TERRACOTTA_BRICKS.get(), CABlocks.BLACK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.BLACK_TERRACOTTA_BRICKS.get(), CABlocks.BLACK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.BLACK_TERRACOTTA_BRICKS.get(), CABlocks.BLACK_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.BLACK_TERRACOTTA_BRICK_SLAB.get(), CABlocks.BLACK_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.BLACK_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.BLACK_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.BLACK_TERRACOTTA_BRICK_WALL.get(), CABlocks.BLACK_TERRACOTTA_BRICKS.get());
		smelting(consumer, CABlocks.BLACK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get(), 0.1F, 200);
		stonecutting(consumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_SLAB.get(), 2);
		stonecutting(consumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_STAIRS.get());
		stonecutting(consumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_WALL.get());
		slab(consumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_SLAB.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get());
		stairs(consumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_STAIRS.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get());
		wall(consumer, CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_WALL.get(), CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get());

		ShapedRecipeBuilder.shaped(CAItems.CRITTER_CAGE.get(), 2)
				.define('#', Items.STICK)
				.define('$', Items.IRON_INGOT)
				.pattern("$#$")
				.pattern("# #")
				.pattern("$#$")
				.unlockedBy("has_" + Items.STICK.asItem(), has(Items.STICK))
				.unlockedBy("has_" + Items.IRON_INGOT.asItem(), has(Items.IRON_INGOT))
				.save(consumer, new ResourceLocation(ChaosAwakens.MODID, CAItems.CRITTER_CAGE.get() + "_default"));
		ShapedRecipeBuilder.shaped(CAItems.CRITTER_CAGE.get(), 2)
				.define('#', CAItems.CRYSTAL_SHARD.get())
				.define('$', CAItems.PINK_TOURMALINE_INGOT.get())
				.pattern("$#$")
				.pattern("# #")
				.pattern("$#$")
				.unlockedBy("has_" + CAItems.CRYSTAL_SHARD.get().asItem(), has(CAItems.CRYSTAL_SHARD.get()))
				.unlockedBy("has_" + CAItems.PINK_TOURMALINE_INGOT.get().asItem(), has(CAItems.PINK_TOURMALINE_INGOT.get()))
				.save(consumer, new ResourceLocation(ChaosAwakens.MODID, CAItems.CRITTER_CAGE.get() + "_crystal_world"));

		armorHelmet(consumer, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL.get());
		armorChestplate(consumer, CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL.get());
		armorLeggings(consumer, CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL.get());
		armorBoots(consumer, CAItems.LAVA_EEL_BOOTS.get(), CAItems.LAVA_EEL.get());
		armorHelmet(consumer, CAItems.MOTH_SCALE_HELMET.get(), CAItems.MOTH_SCALE.get());
		armorChestplate(consumer, CAItems.MOTH_SCALE_CHESTPLATE.get(), CAItems.MOTH_SCALE.get());
		armorLeggings(consumer, CAItems.MOTH_SCALE_LEGGINGS.get(), CAItems.MOTH_SCALE.get());
		armorBoots(consumer, CAItems.MOTH_SCALE_BOOTS.get(), CAItems.MOTH_SCALE.get());
		armorHelmet(consumer, CAItems.EMERALD_HELMET.get(), Items.EMERALD);
		armorChestplate(consumer, CAItems.EMERALD_CHESTPLATE.get(), Items.EMERALD);
		armorLeggings(consumer, CAItems.EMERALD_LEGGINGS.get(), Items.EMERALD);
		armorBoots(consumer, CAItems.EMERALD_BOOTS.get(), Items.EMERALD);
		armorHelmet(consumer, CAItems.RUBY_HELMET.get(), CAItems.RUBY.get());
		armorChestplate(consumer, CAItems.RUBY_CHESTPLATE.get(), CAItems.RUBY.get());
		armorLeggings(consumer, CAItems.RUBY_LEGGINGS.get(), CAItems.RUBY.get());
		armorBoots(consumer, CAItems.RUBY_BOOTS.get(), CAItems.RUBY.get());
		armorHelmet(consumer, CAItems.AMETHYST_HELMET.get(), CAItems.AMETHYST.get());
		armorChestplate(consumer, CAItems.AMETHYST_CHESTPLATE.get(), CAItems.AMETHYST.get());
		armorLeggings(consumer, CAItems.AMETHYST_LEGGINGS.get(), CAItems.AMETHYST.get());
		armorBoots(consumer, CAItems.AMETHYST_BOOTS.get(), CAItems.AMETHYST.get());
		armorHelmet(consumer, CAItems.TIGERS_EYE_HELMET.get(), CAItems.TIGERS_EYE.get());
		armorChestplate(consumer, CAItems.TIGERS_EYE_CHESTPLATE.get(), CAItems.TIGERS_EYE.get());
		armorLeggings(consumer, CAItems.TIGERS_EYE_LEGGINGS.get(), CAItems.TIGERS_EYE.get());
		armorBoots(consumer, CAItems.TIGERS_EYE_BOOTS.get(), CAItems.TIGERS_EYE.get());
		armorHelmet(consumer, CAItems.LAPIS_HELMET.get(), Blocks.LAPIS_BLOCK);
		armorChestplate(consumer, CAItems.LAPIS_CHESTPLATE.get(), Blocks.LAPIS_BLOCK);
		armorLeggings(consumer, CAItems.LAPIS_LEGGINGS.get(), Blocks.LAPIS_BLOCK);
		armorBoots(consumer, CAItems.LAPIS_BOOTS.get(), Blocks.LAPIS_BLOCK);
		armorHelmet(consumer, CAItems.COPPER_HELMET.get(), CAItems.COPPER_LUMP.get());
		armorChestplate(consumer, CAItems.COPPER_CHESTPLATE.get(), CAItems.COPPER_LUMP.get());
		armorLeggings(consumer, CAItems.COPPER_LEGGINGS.get(), CAItems.COPPER_LUMP.get());
		armorBoots(consumer, CAItems.COPPER_BOOTS.get(), CAItems.COPPER_LUMP.get());
		armorHelmet(consumer, CAItems.TIN_HELMET.get(), CAItems.TIN_LUMP.get());
		armorChestplate(consumer, CAItems.TIN_CHESTPLATE.get(), CAItems.TIN_LUMP.get());
		armorLeggings(consumer, CAItems.TIN_LEGGINGS.get(), CAItems.TIN_LUMP.get());
		armorBoots(consumer, CAItems.TIN_BOOTS.get(), CAItems.TIN_LUMP.get());
		armorHelmet(consumer, CAItems.SILVER_HELMET.get(), CAItems.SILVER_LUMP.get());
		armorChestplate(consumer, CAItems.SILVER_CHESTPLATE.get(), CAItems.SILVER_LUMP.get());
		armorLeggings(consumer, CAItems.SILVER_LEGGINGS.get(), CAItems.SILVER_LUMP.get());
		armorBoots(consumer, CAItems.SILVER_BOOTS.get(), CAItems.SILVER_LUMP.get());
		armorHelmet(consumer, CAItems.PLATINUM_HELMET.get(), CAItems.PLATINUM_LUMP.get());
		armorChestplate(consumer, CAItems.PLATINUM_CHESTPLATE.get(), CAItems.PLATINUM_LUMP.get());
		armorLeggings(consumer, CAItems.PLATINUM_LEGGINGS.get(), CAItems.PLATINUM_LUMP.get());
		armorBoots(consumer, CAItems.PLATINUM_BOOTS.get(), CAItems.PLATINUM_LUMP.get());
		armorHelmet(consumer, CAItems.PEACOCK_FEATHER_HELMET.get(), CAItems.PEACOCK_FEATHER.get());
		armorChestplate(consumer, CAItems.PEACOCK_FEATHER_CHESTPLATE.get(), CAItems.PEACOCK_FEATHER.get());
		armorLeggings(consumer, CAItems.PEACOCK_FEATHER_LEGGINGS.get(), CAItems.PEACOCK_FEATHER.get());
		armorBoots(consumer, CAItems.PEACOCK_FEATHER_BOOTS.get(), CAItems.PEACOCK_FEATHER.get());
		armorHelmet(consumer, CAItems.PINK_TOURMALINE_HELMET.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		armorChestplate(consumer, CAItems.PINK_TOURMALINE_CHESTPLATE.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		armorLeggings(consumer, CAItems.PINK_TOURMALINE_LEGGINGS.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		armorBoots(consumer, CAItems.PINK_TOURMALINE_BOOTS.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		armorHelmet(consumer, CAItems.CATS_EYE_HELMET.get(), CAItems.CATS_EYE_INGOT.get());
		armorChestplate(consumer, CAItems.CATS_EYE_CHESTPLATE.get(), CAItems.CATS_EYE_INGOT.get());
		armorLeggings(consumer, CAItems.CATS_EYE_LEGGINGS.get(), CAItems.CATS_EYE_INGOT.get());
		armorBoots(consumer, CAItems.CATS_EYE_BOOTS.get(), CAItems.CATS_EYE_INGOT.get());
		armorHelmet(consumer, CAItems.MOBZILLA_SCALE_HELMET.get(), CAItems.MOBZILLA_SCALE.get());
		armorChestplate(consumer, CAItems.MOBZILLA_SCALE_CHESTPLATE.get(), CAItems.MOBZILLA_SCALE.get());
		armorLeggings(consumer, CAItems.MOBZILLA_SCALE_LEGGINGS.get(), CAItems.MOBZILLA_SCALE.get());
		armorBoots(consumer, CAItems.MOBZILLA_SCALE_BOOTS.get(), CAItems.MOBZILLA_SCALE.get());
		armorHelmet(consumer, CAItems.QUEEN_SCALE_HELMET.get(), CAItems.QUEEN_SCALE.get());
		armorChestplate(consumer, CAItems.QUEEN_SCALE_CHESTPLATE.get(), CAItems.QUEEN_SCALE.get());
		armorLeggings(consumer, CAItems.QUEEN_SCALE_LEGGINGS.get(), CAItems.QUEEN_SCALE.get());
		armorBoots(consumer, CAItems.QUEEN_SCALE_BOOTS.get(), CAItems.QUEEN_SCALE.get());
		armorHelmet(consumer, CAItems.ROYAL_GUARDIAN_HELMET.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		armorChestplate(consumer, CAItems.ROYAL_GUARDIAN_CHESTPLATE.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		armorLeggings(consumer, CAItems.ROYAL_GUARDIAN_LEGGINGS.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		armorBoots(consumer, CAItems.ROYAL_GUARDIAN_BOOTS.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		armorHelmet(consumer, CAItems.ENDER_DRAGON_SCALE_HELMET.get(), CAItems.ENDER_DRAGON_SCALE.get());
		ShapedRecipeBuilder.shaped(CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get())
				.define('S', CAItems.ENDER_DRAGON_SCALE.get())
				.define('E', Items.ELYTRA)
				.pattern("S S")
				.pattern("SES")
				.pattern("SSS")
				.unlockedBy("has_" + CAItems.ENDER_DRAGON_SCALE.get().asItem(), has(CAItems.ENDER_DRAGON_SCALE.get()))
				.unlockedBy("has_" + Items.ELYTRA.asItem(), has(Items.ELYTRA))
				.save(consumer);
		armorLeggings(consumer, CAItems.ENDER_DRAGON_SCALE_LEGGINGS.get(), CAItems.ENDER_DRAGON_SCALE.get());
		armorBoots(consumer, CAItems.ENDER_DRAGON_SCALE_BOOTS.get(), CAItems.ENDER_DRAGON_SCALE.get());
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_HELMET.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("TIT")
				.pattern("U U")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_CHESTPLATE.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("I I")
				.pattern("TTT")
				.pattern("UUU")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_LEGGINGS.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("III")
				.pattern("T T")
				.pattern("U U")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_BOOTS.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.pattern("T T")
				.pattern("U U")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(consumer);

		toolSword(consumer, CAItems.EMERALD_SWORD.get(), Items.EMERALD);
		toolShovel(consumer, CAItems.EMERALD_SHOVEL.get(), Items.EMERALD);
		toolPickaxe(consumer, CAItems.EMERALD_PICKAXE.get(), Items.EMERALD);
		toolAxe(consumer, CAItems.EMERALD_AXE.get(), Items.EMERALD);
		toolHoe(consumer, CAItems.EMERALD_HOE.get(), Items.EMERALD);
		toolSword(consumer, CAItems.RUBY_SWORD.get(), CAItems.RUBY.get());
		toolShovel(consumer, CAItems.RUBY_SHOVEL.get(), CAItems.RUBY.get());
		toolPickaxe(consumer, CAItems.RUBY_PICKAXE.get(), CAItems.RUBY.get());
		toolAxe(consumer, CAItems.RUBY_AXE.get(), CAItems.RUBY.get());
		toolHoe(consumer, CAItems.RUBY_HOE.get(), CAItems.RUBY.get());
		toolSword(consumer, CAItems.AMETHYST_SWORD.get(), CAItems.AMETHYST.get());
		toolShovel(consumer, CAItems.AMETHYST_SHOVEL.get(), CAItems.AMETHYST.get());
		toolPickaxe(consumer, CAItems.AMETHYST_PICKAXE.get(), CAItems.AMETHYST.get());
		toolAxe(consumer, CAItems.AMETHYST_AXE.get(), CAItems.AMETHYST.get());
		toolHoe(consumer, CAItems.AMETHYST_HOE.get(), CAItems.AMETHYST.get());
		toolSword(consumer, CAItems.TIGERS_EYE_SWORD.get(), CAItems.TIGERS_EYE.get());
		toolShovel(consumer, CAItems.TIGERS_EYE_SHOVEL.get(), CAItems.TIGERS_EYE.get());
		toolPickaxe(consumer, CAItems.TIGERS_EYE_PICKAXE.get(), CAItems.TIGERS_EYE.get());
		toolAxe(consumer, CAItems.TIGERS_EYE_AXE.get(), CAItems.TIGERS_EYE.get());
		toolHoe(consumer, CAItems.TIGERS_EYE_HOE.get(), CAItems.TIGERS_EYE.get());
		toolSword(consumer, CAItems.CRYSTAL_WOOD_SWORD.get(), CAItems.CRYSTAL_SHARD.get(), CABlocks.CRYSTAL_PLANKS.get());
		toolShovel(consumer, CAItems.CRYSTAL_WOOD_SHOVEL.get(), CAItems.CRYSTAL_SHARD.get(), CABlocks.CRYSTAL_PLANKS.get());
		toolPickaxe(consumer, CAItems.CRYSTAL_WOOD_PICKAXE.get(), CAItems.CRYSTAL_SHARD.get(), CABlocks.CRYSTAL_PLANKS.get());
		toolAxe(consumer, CAItems.CRYSTAL_WOOD_AXE.get(), CAItems.CRYSTAL_SHARD.get(), CABlocks.CRYSTAL_PLANKS.get());
		toolHoe(consumer, CAItems.CRYSTAL_WOOD_HOE.get(), CAItems.CRYSTAL_SHARD.get(), CABlocks.CRYSTAL_PLANKS.get());
		toolSword(consumer, CAItems.KYANITE_SWORD.get(), CAItems.CRYSTAL_SHARD.get(), CABlocks.KYANITE.get());
		toolShovel(consumer, CAItems.KYANITE_SHOVEL.get(), CAItems.CRYSTAL_SHARD.get(), CABlocks.KYANITE.get());
		toolPickaxe(consumer, CAItems.KYANITE_PICKAXE.get(), CAItems.CRYSTAL_SHARD.get(), CABlocks.KYANITE.get());
		toolAxe(consumer, CAItems.KYANITE_AXE.get(), CAItems.CRYSTAL_SHARD.get(), CABlocks.KYANITE.get());
		toolHoe(consumer, CAItems.KYANITE_HOE.get(), CAItems.CRYSTAL_SHARD.get(), CABlocks.KYANITE.get());
		toolSword(consumer, CAItems.CATS_EYE_SWORD.get(), CAItems.CRYSTAL_SHARD.get(), CAItems.CATS_EYE_INGOT.get());
		toolShovel(consumer, CAItems.CATS_EYE_SHOVEL.get(), CAItems.CRYSTAL_SHARD.get(), CAItems.CATS_EYE_INGOT.get());
		toolPickaxe(consumer, CAItems.CATS_EYE_PICKAXE.get(), CAItems.CRYSTAL_SHARD.get(), CAItems.CATS_EYE_INGOT.get());
		toolAxe(consumer, CAItems.CATS_EYE_AXE.get(), CAItems.CRYSTAL_SHARD.get(), CAItems.CATS_EYE_INGOT.get());
		toolHoe(consumer, CAItems.CATS_EYE_HOE.get(), CAItems.CRYSTAL_SHARD.get(), CAItems.CATS_EYE_INGOT.get());
		toolSword(consumer, CAItems.PINK_TOURMALINE_SWORD.get(), CAItems.CRYSTAL_SHARD.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		toolShovel(consumer, CAItems.PINK_TOURMALINE_SHOVEL.get(), CAItems.CRYSTAL_SHARD.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		toolPickaxe(consumer, CAItems.PINK_TOURMALINE_PICKAXE.get(), CAItems.CRYSTAL_SHARD.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		toolAxe(consumer, CAItems.PINK_TOURMALINE_AXE.get(), CAItems.CRYSTAL_SHARD.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		toolHoe(consumer, CAItems.PINK_TOURMALINE_HOE.get(), CAItems.CRYSTAL_SHARD.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		toolSword(consumer, CAItems.COPPER_SWORD.get(), CAItems.COPPER_LUMP.get());
		toolShovel(consumer, CAItems.COPPER_SHOVEL.get(), CAItems.COPPER_LUMP.get());
		toolPickaxe(consumer, CAItems.COPPER_PICKAXE.get(), CAItems.COPPER_LUMP.get());
		toolAxe(consumer, CAItems.COPPER_AXE.get(), CAItems.COPPER_LUMP.get());
		toolHoe(consumer, CAItems.COPPER_HOE.get(), CAItems.COPPER_LUMP.get());
		toolSword(consumer, CAItems.TIN_SWORD.get(), CAItems.TIN_LUMP.get());
		toolShovel(consumer, CAItems.TIN_SHOVEL.get(), CAItems.TIN_LUMP.get());
		toolPickaxe(consumer, CAItems.TIN_PICKAXE.get(), CAItems.TIN_LUMP.get());
		toolAxe(consumer, CAItems.TIN_AXE.get(), CAItems.TIN_LUMP.get());
		toolHoe(consumer, CAItems.TIN_HOE.get(), CAItems.TIN_LUMP.get());
		toolSword(consumer, CAItems.SILVER_SWORD.get(), CAItems.SILVER_LUMP.get());
		toolShovel(consumer, CAItems.SILVER_SHOVEL.get(), CAItems.SILVER_LUMP.get());
		toolPickaxe(consumer, CAItems.SILVER_PICKAXE.get(), CAItems.SILVER_LUMP.get());
		toolAxe(consumer, CAItems.SILVER_AXE.get(), CAItems.SILVER_LUMP.get());
		toolHoe(consumer, CAItems.SILVER_HOE.get(), CAItems.SILVER_LUMP.get());
		toolSword(consumer, CAItems.PLATINUM_SWORD.get(), CAItems.PLATINUM_LUMP.get());
		toolShovel(consumer, CAItems.PLATINUM_SHOVEL.get(), CAItems.PLATINUM_LUMP.get());
		toolPickaxe(consumer, CAItems.PLATINUM_PICKAXE.get(), CAItems.PLATINUM_LUMP.get());
		toolAxe(consumer, CAItems.PLATINUM_AXE.get(), CAItems.PLATINUM_LUMP.get());
		toolHoe(consumer, CAItems.PLATINUM_HOE.get(), CAItems.PLATINUM_LUMP.get());
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_SWORD.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("T")
				.pattern("U")
				.pattern("I")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_SHOVEL.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("U")
				.pattern("T")
				.pattern("I")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_PICKAXE.get()).define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get()).define('I', CATags.Items.ULTIMATE_GEAR_HANDLES).pattern("TUT")
				.pattern(" U ").pattern(" I ")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_AXE.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("TT")
				.pattern("UI")
				.pattern(" I")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.ULTIMATE_HOE.get())
				.define('T', CAItems.TITANIUM_INGOT.get())
				.define('U', CAItems.URANIUM_INGOT.get())
				.define('I', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.pattern("TU")
				.pattern(" I")
				.pattern(" I")
				.unlockedBy("has_" + CAItems.TITANIUM_INGOT.get().asItem(), has(CAItems.TITANIUM_INGOT.get()))
				.unlockedBy("has_" + CAItems.URANIUM_INGOT.get().asItem(), has(CAItems.URANIUM_INGOT.get()))
				.save(consumer);
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
				.save(consumer);
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
				.save(consumer);

		ShapedRecipeBuilder.shaped(CAItems.SKATE_STRING_BOW.get())
				.define('C', CAItems.CRYSTAL_SHARD.get())
				.define('S', Tags.Items.STRING)
				.pattern(" CS")
				.pattern("C S")
				.pattern(" CS")
				.unlockedBy("has_" + CAItems.CRYSTAL_SHARD.get().asItem(), has(CAItems.CRYSTAL_SHARD.get()))
				.save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.IRUKANDJI_ARROW.get(), 4)
				.define('I', CAItems.CRYSTAL_SHARD.get())
				.define('#', CAItems.DEAD_IRUKANDJI.get())
				.define('P', CAItems.PEACOCK_FEATHER.get())
				.pattern("I")
				.pattern("#")
				.pattern("P")
				.unlockedBy("has_" + CAItems.CRYSTAL_SHARD.get().asItem(), has(CAItems.CRYSTAL_SHARD.get()))
				.unlockedBy("has_" + CAItems.DEAD_IRUKANDJI.get().asItem(), has(CAItems.DEAD_IRUKANDJI.get()))
				.unlockedBy("has_" + CAItems.PEACOCK_FEATHER.get().asItem(), has(CAItems.PEACOCK_FEATHER.get()))
				.save(consumer);

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
				.save(consumer);
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
				.save(consumer);
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
				.save(consumer);
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
		        .save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.QUEEN_SCALE_BATTLE_AXE.get())
				.define('B', CAItems.BATTLE_AXE.get())
				.define('P', CATags.Items.ULTIMATE_GEAR_HANDLES)
				.define('S', CAItems.QUEEN_SCALE.get())
				.pattern("SBS")
				.pattern("SPS")
				.pattern(" P ")
				.unlockedBy("has_" + CAItems.BATTLE_AXE.get().asItem(), has(CAItems.BATTLE_AXE.get()))
				.unlockedBy("has_" + CAItems.QUEEN_SCALE.get().asItem(), has(CAItems.QUEEN_SCALE.get()))
				.save(consumer);
		toolSword(consumer, CAItems.ROYAL_GUARDIAN_SWORD.get(), CAItems.PLATINUM_LUMP.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
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
				.save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.PRISMATIC_REAPER.get())
				.define('S', Tags.Items.RODS_WOODEN)
				.define('A', CAItems.AMETHYST.get())
				.define('D', Tags.Items.GEMS_DIAMOND)
				.define('E', Tags.Items.GEMS_EMERALD)
				.define('R', CAItems.RUBY.get())
				.pattern("ADE")
				.pattern("RS ")
				.pattern("S  ")
				.unlockedBy("has_" + CAItems.AMETHYST.get().asItem(), has(CAItems.AMETHYST.get()))
				.unlockedBy("has_" + Tags.Items.GEMS_DIAMOND.getName(), has(Tags.Items.GEMS_DIAMOND))
				.unlockedBy("has_" + Tags.Items.GEMS_EMERALD.getName(), has(Tags.Items.GEMS_EMERALD))
				.unlockedBy("has_" + CAItems.RUBY.get().asItem(), has(CAItems.RUBY.get()))
				.save(consumer);
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
		        .save(consumer);
		ShapedRecipeBuilder.shaped(CAItems.THUNDER_STAFF.get())
				.define('D', Tags.Items.GEMS_DIAMOND)
				.define('R', CAItems.RUBY.get())
				.pattern(" RD")
				.pattern(" RR")
				.pattern("R  ")
				.unlockedBy("has_" + Tags.Items.GEMS_DIAMOND.getName(), has(Tags.Items.GEMS_DIAMOND))
				.unlockedBy("has_" + CAItems.RUBY.get().asItem(), has(CAItems.RUBY.get()))
				.save(consumer);

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
				.save(consumer);
		
		boat(consumer, CAItems.APPLE_BOAT.get(), CABlocks.APPLE_PLANKS.get());
		boat(consumer, CAItems.DUPLICATOR_BOAT.get(), CABlocks.DUPLICATION_PLANKS.get());
		boat(consumer, CAItems.CHERRY_BOAT.get(), CABlocks.CHERRY_PLANKS.get());
		boat(consumer, CAItems.GINKGO_BOAT.get(), CABlocks.GINKGO_PLANKS.get());
		boat(consumer, CAItems.PEACH_BOAT.get(), CABlocks.PEACH_PLANKS.get());
		boat(consumer, CAItems.SKYWOOD_BOAT.get(), CABlocks.SKYWOOD_PLANKS.get());

		materialToBlock(consumer, CABlocks.ALUMINUM_BLOCK.get(), CAItems.ALUMINUM_INGOT.get());
		blockToMaterial(consumer, CABlocks.ALUMINUM_BLOCK.get(), CAItems.ALUMINUM_INGOT.get());
		materialToBlock(consumer, CABlocks.AMETHYST_BLOCK.get(), CAItems.AMETHYST.get());
		blockToMaterial(consumer, CABlocks.AMETHYST_BLOCK.get(), CAItems.AMETHYST.get());
		materialToBlock(consumer, CABlocks.BLOODSTONE_BLOCK.get(), CAItems.BLOODSTONE.get());
		blockToMaterial(consumer, CABlocks.BLOODSTONE_BLOCK.get(), CAItems.BLOODSTONE.get());
		materialToBlock(consumer, CABlocks.CATS_EYE_BLOCK.get(), CAItems.CATS_EYE_INGOT.get());
		blockToMaterial(consumer, CABlocks.CATS_EYE_BLOCK.get(), CAItems.CATS_EYE_INGOT.get());
		materialToBlock(consumer, CABlocks.COPPER_BLOCK.get(), CAItems.COPPER_LUMP.get());
		blockToMaterial(consumer, CABlocks.COPPER_BLOCK.get(), CAItems.COPPER_LUMP.get());
		materialToBlock(consumer, CABlocks.PINK_TOURMALINE_BLOCK.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		blockToMaterial(consumer, CABlocks.PINK_TOURMALINE_BLOCK.get(), CAItems.PINK_TOURMALINE_INGOT.get());
		materialToBlock(consumer, CABlocks.PLATINUM_BLOCK.get(), CAItems.PLATINUM_LUMP.get());
		blockToMaterial(consumer, CABlocks.PLATINUM_BLOCK.get(), CAItems.PLATINUM_LUMP.get());
		materialToBlock(consumer, CABlocks.RUBY_BLOCK.get(), CAItems.RUBY.get());
		blockToMaterial(consumer, CABlocks.RUBY_BLOCK.get(), CAItems.RUBY.get());
		materialToBlock(consumer, CABlocks.SALT_BLOCK.get(), CAItems.SALT.get());
		blockToMaterial(consumer, CABlocks.SALT_BLOCK.get(), CAItems.SALT.get());
		materialToBlock(consumer, CABlocks.SILVER_BLOCK.get(), CAItems.SILVER_LUMP.get());
		blockToMaterial(consumer, CABlocks.SILVER_BLOCK.get(), CAItems.SILVER_LUMP.get());
		materialToBlock(consumer, CABlocks.SUNSTONE_BLOCK.get(), CAItems.SUNSTONE.get());
		blockToMaterial(consumer, CABlocks.SUNSTONE_BLOCK.get(), CAItems.SUNSTONE.get());
		materialToBlock(consumer, CABlocks.TIGERS_EYE_BLOCK.get(), CAItems.TIGERS_EYE.get());
		blockToMaterial(consumer, CABlocks.TIGERS_EYE_BLOCK.get(), CAItems.TIGERS_EYE.get());
		materialToBlock(consumer, CABlocks.TIN_BLOCK.get(), CAItems.TIN_LUMP.get());
		blockToMaterial(consumer, CABlocks.TIN_BLOCK.get(), CAItems.TIN_LUMP.get());
		materialToBlock(consumer, CABlocks.TITANIUM_BLOCK.get(), CAItems.TITANIUM_INGOT.get());
		blockToMaterial(consumer, CABlocks.TITANIUM_BLOCK.get(), CAItems.TITANIUM_INGOT.get());
		materialToBlock(consumer, CABlocks.URANIUM_BLOCK.get(), CAItems.URANIUM_INGOT.get());
		blockToMaterial(consumer, CABlocks.URANIUM_BLOCK.get(), CAItems.URANIUM_INGOT.get());

		materialToBlock(consumer, CABlocks.ENDER_EYE_BLOCK.get(), Items.ENDER_EYE);
		blockToMaterial(consumer, CABlocks.ENDER_EYE_BLOCK.get(), Items.ENDER_EYE, new ResourceLocation(ChaosAwakens.MODID, "ender_eye"));
		materialToBlock(consumer, CABlocks.ENDER_PEARL_BLOCK.get(), Items.ENDER_PEARL);
		blockToMaterial(consumer, CABlocks.ENDER_PEARL_BLOCK.get(), Items.ENDER_PEARL, new ResourceLocation(ChaosAwakens.MODID, "ender_pearl"));
		materialToBlock(consumer, CABlocks.MOTH_SCALE_BLOCK.get(), CAItems.MOTH_SCALE.get());
		blockToMaterial(consumer, CABlocks.MOTH_SCALE_BLOCK.get(), CAItems.MOTH_SCALE.get());
		materialToBlock(consumer, CABlocks.WATER_DRAGON_SCALE_BLOCK.get(), CAItems.WATER_DRAGON_SCALE.get());
		blockToMaterial(consumer, CABlocks.WATER_DRAGON_SCALE_BLOCK.get(), CAItems.WATER_DRAGON_SCALE.get());
		materialToBlock(consumer, CABlocks.ENDER_DRAGON_SCALE_BLOCK.get(), CAItems.ENDER_DRAGON_SCALE.get());
		blockToMaterial(consumer, CABlocks.ENDER_DRAGON_SCALE_BLOCK.get(), CAItems.ENDER_DRAGON_SCALE.get());
		materialToBlock(consumer, CABlocks.NIGHTMARE_SCALE_BLOCK.get(), CAItems.NIGHTMARE_SCALE.get());
		blockToMaterial(consumer, CABlocks.NIGHTMARE_SCALE_BLOCK.get(), CAItems.NIGHTMARE_SCALE.get());
		materialToBlock(consumer, CABlocks.MOBZILLA_SCALE_BLOCK.get(), CAItems.MOBZILLA_SCALE.get());
		blockToMaterial(consumer, CABlocks.MOBZILLA_SCALE_BLOCK.get(), CAItems.MOBZILLA_SCALE.get());
		materialToBlock(consumer, CABlocks.ROYAL_GUARDIAN_SCALE_BLOCK.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		blockToMaterial(consumer, CABlocks.ROYAL_GUARDIAN_SCALE_BLOCK.get(), CAItems.ROYAL_GUARDIAN_SCALE.get());
		materialToBlock(consumer, CABlocks.QUEEN_SCALE_BLOCK.get(), CAItems.QUEEN_SCALE.get());
		blockToMaterial(consumer, CABlocks.QUEEN_SCALE_BLOCK.get(), CAItems.QUEEN_SCALE.get());
		materialToBlock(consumer, CABlocks.BASILISK_SCALE_BLOCK.get(), CAItems.BASILISK_SCALE.get());
		blockToMaterial(consumer, CABlocks.BASILISK_SCALE_BLOCK.get(), CAItems.BASILISK_SCALE.get());
		materialToBlock(consumer, CABlocks.EMPEROR_SCORPION_SCALE_BLOCK.get(), CAItems.EMPEROR_SCORPION_SCALE.get());
		blockToMaterial(consumer, CABlocks.EMPEROR_SCORPION_SCALE_BLOCK.get(), CAItems.EMPEROR_SCORPION_SCALE.get());

		nuggetToIngot(consumer, CAItems.ALUMINUM_INGOT.get(), CAItems.ALUMINUM_NUGGET.get());
		ingotToNugget(consumer, CAItems.ALUMINUM_INGOT.get(), CAItems.ALUMINUM_NUGGET.get());
		nuggetToIngot(consumer, CAItems.CATS_EYE_INGOT.get(), CAItems.CATS_EYE_NUGGET.get());
		ingotToNugget(consumer, CAItems.CATS_EYE_INGOT.get(), CAItems.CATS_EYE_NUGGET.get());
		nuggetToIngot(consumer, CAItems.PINK_TOURMALINE_INGOT.get(), CAItems.PINK_TOURMALINE_NUGGET.get());
		ingotToNugget(consumer, CAItems.PINK_TOURMALINE_INGOT.get(), CAItems.PINK_TOURMALINE_NUGGET.get());
		nuggetToIngot(consumer, CAItems.TITANIUM_INGOT.get(), CAItems.TITANIUM_NUGGET.get());
		ingotToNugget(consumer, CAItems.TITANIUM_INGOT.get(), CAItems.TITANIUM_NUGGET.get());
		nuggetToIngot(consumer, CAItems.URANIUM_INGOT.get(), CAItems.URANIUM_NUGGET.get());
		ingotToNugget(consumer, CAItems.URANIUM_INGOT.get(), CAItems.URANIUM_NUGGET.get());

		ShapelessRecipeBuilder.shapeless(Items.CYAN_DYE)
				.requires(CABlocks.CYAN_ROSE.get())
				.group("cyan_dye")
				.unlockedBy("has_" + CABlocks.CYAN_ROSE.get().asItem(), has(CABlocks.CYAN_ROSE.get()))
				.save(consumer, new ResourceLocation(ChaosAwakens.MODID, "cyan_dye_from_cyan_rose"));
		ShapelessRecipeBuilder.shapeless(Items.RED_DYE)
				.requires(CABlocks.RED_ROSE.get())
				.group("red_dye")
				.unlockedBy("has_" + CABlocks.RED_ROSE.get().asItem(), has(CABlocks.RED_ROSE.get()))
				.save(consumer, new ResourceLocation(ChaosAwakens.MODID, "red_dye_from_red_rose"));
		ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE)
				.requires(CABlocks.PAEONIA.get())
				.group("purple_dye")
				.unlockedBy("has_" + CABlocks.PAEONIA.get().asItem(), has(CABlocks.PAEONIA.get()))
				.save(consumer, new ResourceLocation(ChaosAwakens.MODID, "purple_dye_from_paeonia"));
		ShapelessRecipeBuilder.shapeless(Items.BLUE_DYE)
				.requires(CABlocks.BLUE_BULB.get())
				.group("purple_dye")
				.unlockedBy("has_" + CABlocks.BLUE_BULB.get().asItem(), has(CABlocks.BLUE_BULB.get()))
				.save(consumer, new ResourceLocation(ChaosAwakens.MODID, "blue_dye_from_blue_bulb"));
		ShapelessRecipeBuilder.shapeless(Items.PINK_DYE)
				.requires(CABlocks.PINK_BULB.get())
				.group("purple_dye")
				.unlockedBy("has_" + CABlocks.PINK_BULB.get().asItem(), has(CABlocks.PINK_BULB.get()))
				.save(consumer, new ResourceLocation(ChaosAwakens.MODID, "pink_dye_from_pink_bulb"));
		ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE)
				.requires(CABlocks.PURPLE_BULB.get())
				.group("purple_dye")
				.unlockedBy("has_" + CABlocks.PURPLE_BULB.get().asItem(), has(CABlocks.PURPLE_BULB.get()))
				.save(consumer, new ResourceLocation(ChaosAwakens.MODID, "purple_dye_from_purple_bulb"));

		surroundItem(consumer, CAItems.GOLDEN_MELON_SLICE.get(), Items.GOLD_INGOT, Items.MELON_SLICE);
		surroundItem(consumer, CAItems.GOLDEN_BEETROOT.get(), Items.GOLD_INGOT, Items.BEETROOT);
		surroundItem(consumer, CAItems.GOLDEN_POTATO.get(), Items.GOLD_INGOT, Items.POTATO);
		surroundItem(consumer, CAItems.GOLDEN_BAKED_POTATO.get(), Items.GOLD_INGOT, Items.BAKED_POTATO);
		surroundItem(consumer, Items.ENCHANTED_GOLDEN_APPLE, Items.GOLD_BLOCK, Items.APPLE, new ResourceLocation(ChaosAwakens.MODID, "enchanted_golden_apple"));
		surroundItem(consumer, CAItems.ENCHANTED_GOLDEN_CARROT.get(), Items.GOLD_BLOCK, Items.CARROT);

		plantSeeds(consumer, CAItems.STRAWBERRY_SEEDS.get(), CAItems.STRAWBERRY.get());
		plantSeeds(consumer, CAItems.RADISH_SEEDS.get(), CAItems.RADISH.get());
		plantSeeds(consumer, CAItems.LETTUCE_SEEDS.get(), CAItems.LETTUCE.get());
		plantSeeds(consumer, CAItems.CORN_SEEDS.get(), CAItems.CORN.get());
		plantSeeds(consumer, CAItems.TOMATO_SEEDS.get(), CAItems.TOMATO.get());

		ShapelessRecipeBuilder.shapeless(CAItems.BACON.get(), 2)
				.requires(Items.PORKCHOP)
				.requires(CAItems.SALT.get())
				.unlockedBy("has_" + Items.PORKCHOP.asItem(), has(Items.PORKCHOP))
				.unlockedBy("has_" + CAItems.SALT.get().asItem(), has(CAItems.SALT.get()))
				.save(consumer);
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
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.BUTTER.get(), 4)
				.requires(Items.MILK_BUCKET, 2)
				.unlockedBy("has_" + Items.MILK_BUCKET.asItem(), has(Items.MILK_BUCKET))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.CHEESE.get(), 2)
				.requires(Items.MILK_BUCKET, 4)
				.unlockedBy("has_" + Items.MILK_BUCKET.asItem(), has(Items.MILK_BUCKET))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.CORNDOG.get())
				.requires(CAItems.CORN.get())
				.requires(Tags.Items.RODS_WOODEN)
				.requires(Items.PORKCHOP)
				.requires(Items.CHICKEN)
				.unlockedBy("has_" + CAItems.CORN.get().asItem(), has(CAItems.CORN.get()))
				.unlockedBy("has_" + Items.PORKCHOP.asItem(), has(Items.PORKCHOP))
				.unlockedBy("has_" + Items.CHICKEN.asItem(), has(Items.CHICKEN))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.GARDEN_SALAD.get())
				.requires(Items.BOWL)
				.requires(CAItems.LETTUCE.get())
				.requires(CAItems.TOMATO.get())
				.requires(CAItems.RADISH.get())
				.requires(Tags.Items.CROPS_CARROT)
				.unlockedBy("has_" + CAItems.LETTUCE.get().asItem(), has(CAItems.LETTUCE.get()))
				.unlockedBy("has_" + CAItems.TOMATO.get().asItem(), has(CAItems.TOMATO.get()))
				.unlockedBy("has_" + CAItems.RADISH.get().asItem(), has(CAItems.RADISH.get()))
				.unlockedBy("has_" + Tags.Items.CROPS_CARROT.getName(), has(Tags.Items.CROPS_CARROT)).save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.RADISH_STEW.get())
				.requires(Items.BOWL)
				.requires(CAItems.RADISH.get())
				.requires(CAItems.RADISH.get())
				.requires(CAItems.RADISH.get())
				.requires(CAItems.RADISH.get())
				.requires(CAItems.RADISH.get())
				.requires(CAItems.RADISH.get())
				.unlockedBy("has_" + CAItems.RADISH.get().asItem(), has(CAItems.RADISH.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.SEAFOOD_PATTY.get())
				.requires(Items.BREAD)
				.requires(CAItems.LETTUCE.get())
				.requires(CAItems.TOMATO.get())
				.requires(CAItems.COOKED_CRAB_MEAT.get())
				.unlockedBy("has_" + CAItems.LETTUCE.get().asItem(), has(CAItems.LETTUCE.get()))
				.unlockedBy("has_" + CAItems.TOMATO.get().asItem(), has(CAItems.TOMATO.get()))
				.unlockedBy("has_" + CAItems.COOKED_CRAB_MEAT.get().asItem(), has(CAItems.COOKED_CRAB_MEAT.get()))
				.save(consumer);
		
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
		.save(consumer);
smelting(consumer, CAItems.CORN.get(), CAItems.POPCORN.get(), 0.35F, 200);
ShapelessRecipeBuilder.shapeless(CAItems.POPCORN_BAG.get(), 1)
		.requires(CAItems.EMPTY_POPCORN_BAG.get())
		.requires(CAItems.POPCORN.get())
		.requires(CAItems.POPCORN.get())
		.requires(CAItems.POPCORN.get())
		.requires(CAItems.POPCORN.get())
		.unlockedBy("has_" + CAItems.EMPTY_POPCORN_BAG.get().asItem(), has(CAItems.EMPTY_POPCORN_BAG.get()))
		.unlockedBy("has_" + CAItems.POPCORN.get().asItem(), has(CAItems.POPCORN.get()))
		.save(consumer);
ShapelessRecipeBuilder.shapeless(CAItems.SALTED_POPCORN_BAG.get(), 1)
		.requires(CAItems.POPCORN_BAG.get())
		.requires(CAItems.SALT.get())
		.requires(CAItems.SALT.get())
		.requires(CAItems.SALT.get())
		.requires(CAItems.SALT.get())
		.unlockedBy("has_" + CAItems.POPCORN_BAG.get().asItem(), has(CAItems.POPCORN_BAG.get()))
		.unlockedBy("has_" + CAItems.SALT.get().asItem(), has(CAItems.SALT.get()))
		.save(consumer);
ShapelessRecipeBuilder.shapeless(CAItems.BUTTERED_POPCORN_BAG.get(), 1)
		.requires(CAItems.POPCORN_BAG.get())
		.requires(CAItems.BUTTER.get())
		.requires(CAItems.BUTTER.get())
		.requires(CAItems.BUTTER.get())
		.requires(CAItems.BUTTER.get())
		.unlockedBy("has_" + CAItems.POPCORN_BAG.get().asItem(), has(CAItems.POPCORN_BAG.get()))
		.unlockedBy("has_" + CAItems.BUTTER.get().asItem(), has(CAItems.BUTTER.get()))
		.save(consumer);

ShapelessRecipeBuilder.shapeless(CAItems.BUTTER_CANDY.get(), 4)
		.requires(Items.SUGAR)
		.requires(CAItems.BUTTER.get())
		.unlockedBy("has_" + Items.SUGAR.asItem(), has(Items.SUGAR))
		.unlockedBy("has_" + CAItems.BUTTER.get().asItem(), has(CAItems.BUTTER.get()))
		.save(consumer);

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
				.save(consumer);

		smelting(consumer, Items.SUGAR, CAItems.CANDYCANE.get(), 0.35F, 200);

		smelting(consumer, CAItems.GOLDEN_POTATO.get(), CAItems.GOLDEN_BAKED_POTATO.get(), 0.35F, 200);
		cooking(consumer, CAItems.GOLDEN_POTATO.get(), CAItems.GOLDEN_BAKED_POTATO.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(consumer, CAItems.GOLDEN_POTATO.get(), CAItems.GOLDEN_BAKED_POTATO.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);

		smelting(consumer, CAItems.BACON.get(), CAItems.COOKED_BACON.get(), 0.35F, 200);
		cooking(consumer, CAItems.BACON.get(), CAItems.COOKED_BACON.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(consumer, CAItems.BACON.get(), CAItems.COOKED_BACON.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);
		smelting(consumer, CAItems.CORNDOG.get(), CAItems.COOKED_CORNDOG.get(), 0.35F, 200);
		cooking(consumer, CAItems.CORNDOG.get(), CAItems.COOKED_CORNDOG.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(consumer, CAItems.CORNDOG.get(), CAItems.COOKED_CORNDOG.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);
		smelting(consumer, CAItems.CRAB_MEAT.get(), CAItems.COOKED_CRAB_MEAT.get(), 0.35F, 200);
		cooking(consumer, CAItems.CRAB_MEAT.get(), CAItems.COOKED_CRAB_MEAT.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(consumer, CAItems.CRAB_MEAT.get(), CAItems.COOKED_CRAB_MEAT.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);
		smelting(consumer, CAItems.PEACOCK_LEG.get(), CAItems.COOKED_PEACOCK_LEG.get(), 0.35F, 200);
		cooking(consumer, CAItems.PEACOCK_LEG.get(), CAItems.COOKED_PEACOCK_LEG.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(consumer, CAItems.PEACOCK_LEG.get(), CAItems.COOKED_PEACOCK_LEG.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);
		smelting(consumer, CAItems.VENISON.get(), CAItems.COOKED_VENISON.get(), 0.35F, 200);
		cooking(consumer, CAItems.VENISON.get(), CAItems.COOKED_VENISON.get(), 100, "smoking", IRecipeSerializer.SMOKING_RECIPE);
		cooking(consumer, CAItems.VENISON.get(), CAItems.COOKED_VENISON.get(), 600, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);

		smelting(consumer, CABlocks.ALUMINUM_ORE.get(), CAItems.ALUMINUM_INGOT.get(), 0.1F, 200);
		blasting(consumer, CABlocks.ALUMINUM_ORE.get(), CAItems.ALUMINUM_INGOT.get(), 0.1F, 100);
		smelting(consumer, CABlocks.AMETHYST_ORE.get(), CAItems.AMETHYST.get(), 0.1F, 200);
		blasting(consumer, CABlocks.AMETHYST_ORE.get(), CAItems.AMETHYST.get(), 0.1F, 100);
		smelting(consumer, CABlocks.BLOODSTONE_ORE.get(), CAItems.BLOODSTONE.get(), 0.1F, 200);
		blasting(consumer, CABlocks.BLOODSTONE_ORE.get(), CAItems.BLOODSTONE.get(), 0.1F, 100);
		smelting(consumer, CABlocks.CATS_EYE_CLUSTER.get(), CAItems.CATS_EYE_INGOT.get(), 0.1F, 200);
		blasting(consumer, CABlocks.CATS_EYE_CLUSTER.get(), CAItems.CATS_EYE_INGOT.get(), 0.1F, 100);
		smelting(consumer, CABlocks.COPPER_ORE.get(), CAItems.COPPER_LUMP.get(), 0.1F, 200);
		blasting(consumer, CABlocks.COPPER_ORE.get(), CAItems.COPPER_LUMP.get(), 0.1F, 100);
		smelting(consumer, CABlocks.PINK_TOURMALINE_CLUSTER.get(), CAItems.PINK_TOURMALINE_INGOT.get(), 0.1F, 200);
		blasting(consumer, CABlocks.PINK_TOURMALINE_CLUSTER.get(), CAItems.PINK_TOURMALINE_INGOT.get(), 0.1F, 100);
		smelting(consumer, CABlocks.PLATINUM_ORE.get(), CAItems.PLATINUM_LUMP.get(), 0.1F, 200);
		blasting(consumer, CABlocks.PLATINUM_ORE.get(), CAItems.PLATINUM_LUMP.get(), 0.1F, 100);
		smelting(consumer, CABlocks.RUBY_ORE.get(), CAItems.RUBY.get(), 0.1F, 200);
		blasting(consumer, CABlocks.RUBY_ORE.get(), CAItems.RUBY.get(), 0.1F, 100);
		smelting(consumer, CABlocks.SALT_ORE.get(), CAItems.SALT.get(), 0.1F, 200);
		blasting(consumer, CABlocks.SALT_ORE.get(), CAItems.SALT.get(), 0.1F, 100);
		smelting(consumer, CABlocks.SILVER_ORE.get(), CAItems.SILVER_LUMP.get(), 0.1F, 200);
		blasting(consumer, CABlocks.SILVER_ORE.get(), CAItems.SILVER_LUMP.get(), 0.1F, 100);
		smelting(consumer, CABlocks.SUNSTONE_ORE.get(), CAItems.SUNSTONE.get(), 0.1F, 200);
		blasting(consumer, CABlocks.SUNSTONE_ORE.get(), CAItems.SUNSTONE.get(), 0.1F, 100);
		smelting(consumer, CABlocks.TIGERS_EYE_ORE.get(), CAItems.TIGERS_EYE.get(), 0.1F, 200);
		blasting(consumer, CABlocks.TIGERS_EYE_ORE.get(), CAItems.TIGERS_EYE.get(), 0.1F, 100);
		smelting(consumer, CABlocks.TIN_ORE.get(), CAItems.TIN_LUMP.get(), 0.1F, 200);
		blasting(consumer, CABlocks.TIN_ORE.get(), CAItems.TIN_LUMP.get(), 0.1F, 100);
		smelting(consumer, CABlocks.TITANIUM_ORE.get(), CAItems.TITANIUM_NUGGET.get(), 0.1F, 200);
		blasting(consumer, CABlocks.TITANIUM_ORE.get(), CAItems.TITANIUM_NUGGET.get(), 0.1F, 100);
		smelting(consumer, CABlocks.URANIUM_ORE.get(), CAItems.URANIUM_NUGGET.get(), 0.1F, 200);
		blasting(consumer, CABlocks.URANIUM_ORE.get(), CAItems.URANIUM_NUGGET.get(), 0.1F, 100);

		ShapelessRecipeBuilder.shapeless(CAItems.INSTANT_SURVIVAL_SHELTER.get())
				.requires(Tags.Items.STORAGE_BLOCKS_REDSTONE)
				.requires(Tags.Items.RODS_WOODEN)
				.requires(Tags.Items.COBBLESTONE)
				.unlockedBy("has_" + Tags.Items.STORAGE_BLOCKS_REDSTONE.getName(), has(Tags.Items.STORAGE_BLOCKS_REDSTONE))
				.unlockedBy("has_" + Tags.Items.RODS_WOODEN.getName(), has(Tags.Items.RODS_WOODEN))
				.unlockedBy("has_" + Tags.Items.COBBLESTONE.getName(), has(Tags.Items.COBBLESTONE)).save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.ZOO_CAGE_EXTRA_SMALL.get()).requires(Tags.Items.STORAGE_BLOCKS_IRON)
				.requires(Tags.Items.STORAGE_BLOCKS_QUARTZ)
				.requires(Tags.Items.GLASS)
				.group("zoo_cage")
				.unlockedBy("has_" + Tags.Items.STORAGE_BLOCKS_IRON.getName(), has(Tags.Items.STORAGE_BLOCKS_IRON))
				.unlockedBy("has_" + Tags.Items.STORAGE_BLOCKS_QUARTZ.getName(), has(Tags.Items.STORAGE_BLOCKS_QUARTZ))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.ZOO_CAGE_SMALL.get())
				.requires(Tags.Items.STORAGE_BLOCKS_IRON)
				.requires(Tags.Items.STORAGE_BLOCKS_QUARTZ)
				.requires(Tags.Items.GLASS)
				.requires(CAItems.ZOO_CAGE_EXTRA_SMALL.get())
				.group("zoo_cage")
				.unlockedBy("has_" + CAItems.ZOO_CAGE_EXTRA_SMALL.get().asItem(), has(CAItems.ZOO_CAGE_EXTRA_SMALL.get()))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.ZOO_CAGE_MEDIUM.get())
				.requires(Tags.Items.STORAGE_BLOCKS_IRON)
				.requires(Tags.Items.STORAGE_BLOCKS_QUARTZ)
				.requires(Tags.Items.GLASS)
				.requires(CAItems.ZOO_CAGE_SMALL.get())
				.group("zoo_cage")
				.unlockedBy("has_" + CAItems.ZOO_CAGE_SMALL.get().asItem(), has(CAItems.ZOO_CAGE_SMALL.get()))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.ZOO_CAGE_LARGE.get())
				.requires(Tags.Items.STORAGE_BLOCKS_IRON)
				.requires(Tags.Items.STORAGE_BLOCKS_QUARTZ)
				.requires(Tags.Items.GLASS)
				.requires(CAItems.ZOO_CAGE_MEDIUM.get())
				.group("zoo_cage")
				.unlockedBy("has_" + CAItems.ZOO_CAGE_MEDIUM.get().asItem(), has(CAItems.ZOO_CAGE_MEDIUM.get()))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(CAItems.ZOO_CAGE_EXTRA_LARGE.get())
				.requires(Tags.Items.STORAGE_BLOCKS_IRON)
				.requires(Tags.Items.STORAGE_BLOCKS_QUARTZ)
				.requires(Tags.Items.GLASS)
				.requires(CAItems.ZOO_CAGE_LARGE.get())
				.group("zoo_cage")
				.unlockedBy("has_" + CAItems.ZOO_CAGE_LARGE.get().asItem(), has(CAItems.ZOO_CAGE_LARGE.get()))
				.save(consumer);

		ShapedRecipeBuilder.shaped(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.COPPER.getId())).get())
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
				.save(consumer);
		ShapedRecipeBuilder.shaped(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get())
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
				.save(consumer);
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
				.save(consumer);
		buildDefossilizingRecipes(consumer);

		buildConversionRecipes(consumer);
	}

	private void buildDefossilizingRecipes(Consumer<IFinishedRecipe> consumer) {
		// Overworld (CA)
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ACACIA_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.ACACIA_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ACACIA_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_BIRCH_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.BIRCH_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_BIRCH_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_DARK_OAK_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.DARK_OAK_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_DARK_OAK_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_JUNGLE_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.JUNGLE_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_JUNGLE_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_OAK_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.OAK_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_OAK_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SPRUCE_ENT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.SPRUCE_ENT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SPRUCE_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HERCULES_BEETLE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.HERCULES_BEETLE_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_HERCULES_BEETLE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
		        .builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				        Ingredient.of(CABlocks.FOSSILISED_BEAVER.get()),
				        Ingredient.of(Items.WATER_BUCKET),
				        Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				        CAItems.BEAVER_SPAWN_EGG.get(), 1, 20, "copper")
		        .build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_BEAVER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_RUBY_BUG.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.RUBY_BUG_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_RUBY_BUG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_EMERALD_GATOR.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.EMERALD_GATOR_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_EMERALD_GATOR.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GREEN_FISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.GREEN_FISH_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_GREEN_FISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ROCK_FISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.ROCK_FISH_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ROCK_FISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SPARK_FISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.SPARK_FISH_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SPARK_FISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WOOD_FISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.WOOD_FISH_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WOOD_FISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
//		FossilRecipeBuilder
//				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
//						Ingredient.of(CABlocks.FOSSILISED_WTF.get()),
//						Ingredient.of(Items.WATER_BUCKET),
//						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
//						CAItems.WTF_SPAWN_EGG.get(), 1, 20, "copper")
//				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WTF.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
//		FossilRecipeBuilder
//				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
//						Ingredient.of(CABlocks.FOSSILISED_SCORPION.get()),
//						Ingredient.of(Items.WATER_BUCKET),
//						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
//						CAItems.SCORPION_SPAWN_EGG.get(), 1, 20, "copper")
//				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SCORPION.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WASP.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.WASP_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WASP.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WHALE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.WHALE_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WHALE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
//		FossilRecipeBuilder
//				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
//						Ingredient.of(CABlocks.FOSSILISED_PIRAPORU.get()),
//						Ingredient.of(Items.WATER_BUCKET),
//						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
//						CAItems.PIRAPORU_SPAWN_EGG.get(), 1, 20, "copper")
//				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PIRAPORU.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_APPLE_COW.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.APPLE_COW_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_APPLE_COW.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GOLDEN_APPLE_COW.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.GOLDEN_APPLE_COW_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_GOLDEN_APPLE_COW.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_CARROT_PIG.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.CARROT_PIG_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_CARROT_PIG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.GOLDEN_CARROT_PIG_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
		        .builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				        Ingredient.of(CABlocks.FOSSILISED_LETTUCE_CHICKEN.get()),
				        Ingredient.of(Items.WATER_BUCKET),
				        Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				        CAItems.LEAFY_CHICKEN_SPAWN_EGG.get(), 1, 20, "copper")
		        .build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_LETTUCE_CHICKEN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_BIRD.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.BIRD_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_BIRD.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_TREE_FROG.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.TREE_FROG_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_TREE_FROG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// Overworld (Vanilla)
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_BAT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.BAT_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_BAT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_BEE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.BEE_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_BEE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_CAVE_SPIDER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.CAVE_SPIDER_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_CAVE_SPIDER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_CHICKEN.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.CHICKEN_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_CHICKEN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_COD.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.COD_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_COD.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_COW.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.COW_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_COW.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_CREEPER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.CREEPER_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_CREEPER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_DOLPHIN.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.DOLPHIN_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_DOLPHIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_DONKEY.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.DONKEY_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_DONKEY.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_DROWNED.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.DROWNED_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_DROWNED.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ENDERMAN.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ENDERMAN_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ENDERMAN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_EVOKER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.EVOKER_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_EVOKER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_FOX.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.FOX_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_FOX.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GIANT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.GIANT_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_GIANT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GUARDIAN.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.GUARDIAN_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_GUARDIAN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HORSE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.HORSE_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_HORSE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HUSK.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.HUSK_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_HUSK.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HUSK_SANDSTONE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.HUSK_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_HUSK_SANDSTONE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ILLUSIONER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.ILLUSIONER_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ILLUSIONER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_IRON_GOLEM.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.IRON_GOLEM_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_IRON_GOLEM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_LLAMA.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.LLAMA_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_LLAMA.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_MOOSHROOM.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.MOOSHROOM_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_MOOSHROOM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_OCELOT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.OCELOT_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_OCELOT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PANDA.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PANDA_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PANDA.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PIG.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PIG_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PIG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PHANTOM.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PHANTOM_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PHANTOM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PILLAGER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PILLAGER_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PILLAGER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FROZEN_POLAR_BEAR.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.POLAR_BEAR_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FROZEN_POLAR_BEAR.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PUFFERFISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PUFFERFISH_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PUFFERFISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_RABBIT.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.RABBIT_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_RABBIT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_RAVAGER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.RAVAGER_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_RAVAGER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SALMON.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SALMON_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SALMON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SHEEP.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SHEEP_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SHEEP.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SKELETON.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SKELETON_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SKELETON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SKELETON_HORSE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SKELETON_HORSE_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SKELETON_HORSE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SLIME.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SLIME_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SLIME.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FROZEN_SNOW_GOLEM.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.SNOW_GOLEM_SPAWN_EGG.get(), 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FROZEN_SNOW_GOLEM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SPIDER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SPIDER_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SPIDER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SQUID.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SQUID_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SQUID.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FROZEN_STRAY.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.STRAY_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FROZEN_STRAY.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_TROPICAL_FISH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.TROPICAL_FISH_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_TROPICAL_FISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_TURTLE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.TURTLE_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_TURTLE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_VILLAGER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.VILLAGER_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_VILLAGER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_VINDICATOR.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.VINDICATOR_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_VINDICATOR.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WANDERING_TRADER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.WANDERING_TRADER_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WANDERING_TRADER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WITCH.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.WITCH_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WITCH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WOLF.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.WOLF_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WOLF.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ZOMBIE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ZOMBIE_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ZOMBIE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ZOMBIE_HORSE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ZOMBIE_HORSE_SPAWN_EGG, 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ZOMBIE_HORSE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// Nether (CA)
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_CRIMSON_ENT.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.CRIMSON_ENT_SPAWN_EGG.get(), 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_CRIMSON_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WARPED_ENT.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.WARPED_ENT_SPAWN_EGG.get(), 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WARPED_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_LAVA_EEL.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.LAVA_EEL_SPAWN_EGG.get(), 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_LAVA_EEL.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// Nether (Vanilla)
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_BLAZE.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.BLAZE_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_BLAZE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_GHAST.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.GHAST_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_GHAST.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_HOGLIN.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.HOGLIN_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_HOGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ENDERMAN_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.MAGMA_CUBE_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.MAGMA_CUBE_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_PIGLIN.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.PIGLIN_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PIGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SKELETON_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_STRIDER.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.STRIDER_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_STRIDER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_WITHER_SKELETON.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.WITHER_SKELETON_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WITHER_SKELETON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.get()),
						Ingredient.of(Items.LAVA_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// End (CA)

		// End (Vanilla)
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ENDERMAN_END_STONE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ENDERMAN_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ENDERMAN_END_STONE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_ENDERMITE.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.ENDERMITE_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ENDERMITE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_SHULKER.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						Items.SHULKER_SPAWN_EGG, 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SHULKER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// Mining Paradise (CA)
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.FOSSILISED_DIMETRODON.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.DIMETRODON_SPAWN_EGG.get(), 1, 20, "copper")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_DIMETRODON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// Crystalworld (CA)
		FossilRecipeBuilder
				.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
						Ingredient.of(CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.get()),
						Ingredient.of(Items.WATER_BUCKET),
						Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
						CAItems.CRYSTAL_APPLE_COW_SPAWN_EGG.get(), 1, 20, "iron")
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder
		.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.CRYSTALISED_CRYSTAL_CARROT_PIG.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.CRYSTAL_CARROT_PIG_SPAWN_EGG.get(), 1, 20, "iron")
		.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.CRYSTALISED_CRYSTAL_CARROT_PIG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
	}

	private static void buildConversionRecipes(Consumer<IFinishedRecipe> consumer) {
		philoConversionRecipe(consumer, Items.EMERALD, 2, CAItems.AMETHYST.get(), 1);
		philoConversionRecipe(consumer, Items.DIAMOND, 3, CAItems.TIGERS_EYE.get(), 1);
		philoConversionRecipe(consumer, CAItems.AMETHYST.get(), 4, CAItems.RUBY.get(), 1);
		philoConversionRecipe(consumer, CAItems.PINK_TOURMALINE_INGOT.get(), 4, CAItems.CATS_EYE_INGOT.get(), 1);
		philoConversionRecipe(consumer, CAItems.SUNSTONE.get(), 1, CAItems.BLOODSTONE.get(), 1);
		philoConversionRecipe(consumer, CAItems.TIN_LUMP.get(), 1, CAItems.COPPER_LUMP.get(), 2);
		philoConversionRecipe(consumer, CAItems.SILVER_LUMP.get(), 1, CAItems.TIN_LUMP.get(), 2);
		philoConversionRecipe(consumer, CAItems.PLATINUM_LUMP.get(), 2, CAItems.SILVER_LUMP.get(), 2);
	}

	private static void woodenPlanks(Consumer<IFinishedRecipe> consumer, IItemProvider pPlanks, ITag<Item> pLog) {
		ShapelessRecipeBuilder.shapeless(pPlanks, 4)
				.requires(pLog)
				.group("planks")
				.unlockedBy("has_log", has(pLog))
				.save(consumer);
	}

	private static void woodenPlanks(Consumer<IFinishedRecipe> consumer, IItemProvider pPlanks, IItemProvider pLog) {
		ShapelessRecipeBuilder.shapeless(pPlanks, 4)
				.requires(pLog)
				.group("planks")
				.unlockedBy("has_log", has(pLog))
				.save(consumer);
	}

	private static void woodenSlab(Consumer<IFinishedRecipe> consumer, IItemProvider pSlab, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pSlab, 6)
				.define('#', pMaterial)
				.pattern("###")
				.group("wooden_slab")
				.unlockedBy("has_planks", has(pMaterial))
				.save(consumer);
	}

	private static void woodenStairs(Consumer<IFinishedRecipe> consumer, IItemProvider pStairs, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pStairs, 4)
				.define('#', pMaterial)
				.pattern("#  ")
				.pattern("## ")
				.pattern("###")
				.group("wooden_stairs")
				.unlockedBy("has_planks", has(pMaterial))
				.save(consumer);
	}

	private static void woodenFence(Consumer<IFinishedRecipe> consumer, IItemProvider pFence, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pFence, 3)
				.define('#', Items.STICK)
				.define('W', pMaterial)
				.pattern("W#W")
				.pattern("W#W")
				.group("wooden_fence")
				.unlockedBy("has_planks", has(pMaterial))
				.save(consumer);
	}

	private static void woodenFenceGate(Consumer<IFinishedRecipe> consumer, IItemProvider pFenceGate, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pFenceGate)
				.define('#', Items.STICK)
				.define('W', pMaterial)
				.pattern("#W#")
				.pattern("#W#")
				.group("wooden_fence_gate")
				.unlockedBy("has_planks", has(pMaterial))
				.save(consumer);
	}

	private static void woodenPressurePlate(Consumer<IFinishedRecipe> consumer, IItemProvider pPressurePlate, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pPressurePlate)
				.define('#', pMaterial)
				.pattern("##")
				.group("wooden_pressure_plate")
				.unlockedBy("has_planks", has(pMaterial))
				.save(consumer);
	}

	private static void woodenButton(Consumer<IFinishedRecipe> consumer, IItemProvider pButton, IItemProvider pMaterial) {
		ShapelessRecipeBuilder.shapeless(pButton)
				.requires(pMaterial)
				.group("wooden_button")
				.unlockedBy("has_planks", has(pMaterial))
				.save(consumer);
	}

	private static void woodenTrapdoor(Consumer<IFinishedRecipe> consumer, IItemProvider pTrapdoor, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pTrapdoor)
				.define('#', pMaterial)
				.pattern("###")
				.pattern("###")
				.group("wooden_trapdoor")
				.unlockedBy("has_planks", has(pMaterial))
				.save(consumer);
	}
	
	private static void woodenDoor(Consumer<IFinishedRecipe> consumer, IItemProvider pDoor, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pDoor)
				.define('#', pMaterial)
				.pattern("##")
				.pattern("##")
				.pattern("##")
				.group("wooden_door")
				.unlockedBy("has_planks", has(pMaterial))
				.save(consumer);
	}

	private static void woodenWood(Consumer<IFinishedRecipe> consumer, IItemProvider pWood, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pWood, 3)
				.define('#', pMaterial)
				.pattern("##")
				.pattern("##")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void woodenSign(Consumer<IFinishedRecipe> consumer, IItemProvider pSign, IItemProvider pMaterial, IItemProvider pStick) {
		ShapedRecipeBuilder.shaped(pSign, 3)
				.define('P', pMaterial)
				.define('S', pStick)
				.pattern("PPP")
				.pattern("PPP")
				.pattern(" S ")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.unlockedBy("has_" + pStick.asItem(), has(pStick))
				.save(consumer);
	}

	private static void leafCarpet(Consumer<IFinishedRecipe> consumer, IItemProvider pLeadCarpet, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pLeadCarpet)
				.define('#', pMaterial)
				.pattern("##")
				.group("leaf_carpet")
				.unlockedBy("has_planks", has(pMaterial))
				.save(consumer);
	}

	private static void slab(Consumer<IFinishedRecipe> consumer, IItemProvider pSlab, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pSlab, 6)
				.define('#', pMaterial)
				.pattern("###")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void stairs(Consumer<IFinishedRecipe> consumer, IItemProvider pStairs, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pStairs, 4)
				.define('#', pMaterial)
				.pattern("#  ")
				.pattern("## ")
				.pattern("###")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void wall(Consumer<IFinishedRecipe> consumer, IItemProvider pWall, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pWall, 6)
				.define('#', pMaterial)
				.pattern("###")
				.pattern("###")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void bricks(Consumer<IFinishedRecipe> consumer, IItemProvider pBricks, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pBricks, 3)
				.define('#', pMaterial)
				.pattern("##")
				.pattern("##")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void surroundItem(Consumer<IFinishedRecipe> consumer, IItemProvider pOutput, IItemProvider pSurroundMaterial, IItemProvider pItemMaterial) {
		ShapedRecipeBuilder.shaped(pOutput)
				.define('#', pSurroundMaterial)
				.define('X', pItemMaterial)
				.pattern("###")
				.pattern("#X#")
				.pattern("###")
				.unlockedBy("has_" + pSurroundMaterial.asItem(), has(pSurroundMaterial))
				.save(consumer);
	}

	private static void surroundItem(Consumer<IFinishedRecipe> consumer, IItemProvider pOutput, IItemProvider pSurroundMaterial, IItemProvider pItemMaterial, ResourceLocation name) {
		ShapedRecipeBuilder.shaped(pOutput)
				.define('#', pSurroundMaterial)
				.define('X', pItemMaterial)
				.pattern("###")
				.pattern("#X#")
				.pattern("###")
				.unlockedBy("has_" + pSurroundMaterial.asItem(), has(pSurroundMaterial))
				.save(consumer, name);
	}

	private static void armorHelmet(Consumer<IFinishedRecipe> consumer, IItemProvider pArmorHelmet, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pArmorHelmet)
				.define('#', pMaterial)
				.pattern("###")
				.pattern("# #")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void armorChestplate(Consumer<IFinishedRecipe> consumer, IItemProvider pArmorChestplate, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pArmorChestplate)
				.define('#', pMaterial)
				.pattern("# #")
				.pattern("###")
				.pattern("###")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void armorLeggings(Consumer<IFinishedRecipe> consumer, IItemProvider pArmorLeggings, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pArmorLeggings)
				.define('#', pMaterial)
				.pattern("###")
				.pattern("# #")
				.pattern("# #")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void armorBoots(Consumer<IFinishedRecipe> consumer, IItemProvider pArmorBoots, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pArmorBoots)
				.define('#', pMaterial)
				.pattern("# #")
				.pattern("# #")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void toolSword(Consumer<IFinishedRecipe> consumer, IItemProvider pToolSword, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolSword)
				.define('S', Items.STICK)
				.define('#', pMaterial)
				.pattern("#")
				.pattern("#")
				.pattern("S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void toolShovel(Consumer<IFinishedRecipe> consumer, IItemProvider pToolShovel, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolShovel)
				.define('S', Items.STICK)
				.define('#', pMaterial)
				.pattern("#")
				.pattern("S")
				.pattern("S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void toolPickaxe(Consumer<IFinishedRecipe> consumer, IItemProvider pToolPickaxe, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolPickaxe)
				.define('S', Items.STICK)
				.define('#', pMaterial)
				.pattern("###")
				.pattern(" S ")
				.pattern(" S ")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void toolAxe(Consumer<IFinishedRecipe> consumer, IItemProvider pToolAxe, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolAxe)
				.define('S', Items.STICK)
				.define('#', pMaterial)
				.pattern("##")
				.pattern("#S")
				.pattern(" S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void toolHoe(Consumer<IFinishedRecipe> consumer, IItemProvider pToolHoe, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolHoe)
				.define('S', Items.STICK)
				.define('#', pMaterial)
				.pattern("##")
				.pattern(" S")
				.pattern(" S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void toolSword(Consumer<IFinishedRecipe> consumer, IItemProvider pToolSword, IItemProvider pToolStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolSword)
				.define('S', pToolStick)
				.define('#', pMaterial)
				.pattern("#")
				.pattern("#")
				.pattern("S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void toolShovel(Consumer<IFinishedRecipe> consumer, IItemProvider pToolShovel, IItemProvider pToolStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolShovel)
				.define('S', pToolStick)
				.define('#', pMaterial)
				.pattern("#")
				.pattern("S")
				.pattern("S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void toolPickaxe(Consumer<IFinishedRecipe> consumer, IItemProvider pToolPickaxe, IItemProvider pToolStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolPickaxe)
				.define('S', pToolStick)
				.define('#', pMaterial)
				.pattern("###")
				.pattern(" S ")
				.pattern(" S ")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void toolAxe(Consumer<IFinishedRecipe> consumer, IItemProvider pToolAxe, IItemProvider pToolStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolAxe)
				.define('S', pToolStick)
				.define('#', pMaterial)
				.pattern("##")
				.pattern("#S")
				.pattern(" S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void toolHoe(Consumer<IFinishedRecipe> consumer, IItemProvider pToolHoe, IItemProvider pToolStick, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pToolHoe)
				.define('S', pToolStick)
				.define('#', pMaterial)
				.pattern("##")
				.pattern(" S")
				.pattern(" S")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}
	
	private static void boat(Consumer<IFinishedRecipe> consumer, IItemProvider pBoat, IItemProvider pPlank) {
		ShapedRecipeBuilder.shaped(pBoat)
				.define('#', pPlank)
				.pattern("# #")
				.pattern("###")
				.unlockedBy("has_" + pPlank.asItem(), has(pPlank))
				.save(consumer);
	}

	private static void materialToBlock(Consumer<IFinishedRecipe> consumer, IItemProvider pBlock, IItemProvider pMaterial) {
		ShapedRecipeBuilder.shaped(pBlock)
				.define('#', pMaterial)
				.pattern("###")
				.pattern("###")
				.pattern("###")
				.unlockedBy("has_" + pMaterial.asItem(), has(pMaterial))
				.save(consumer);
	}

	private static void blockToMaterial(Consumer<IFinishedRecipe> consumer, IItemProvider pBlock, IItemProvider pMaterial) {
		ShapelessRecipeBuilder.shapeless(pMaterial, 9)
				.requires(pBlock)
				.unlockedBy("has_" + pBlock.asItem(), has(pBlock))
				.save(consumer);
	}

	private static void blockToMaterial(Consumer<IFinishedRecipe> consumer, IItemProvider pBlock, IItemProvider pMaterial, ResourceLocation name) {
		ShapelessRecipeBuilder.shapeless(pMaterial, 9)
				.requires(pBlock)
				.unlockedBy("has_" + pBlock.asItem(), has(pBlock))
				.save(consumer, name);
	}

	private static void nuggetToIngot(Consumer<IFinishedRecipe> consumer, IItemProvider pIngot, IItemProvider pNugget) {
		ShapedRecipeBuilder.shaped(pIngot)
				.define('#', pNugget)
				.pattern("###")
				.pattern("###")
				.pattern("###")
				.unlockedBy("has_" + pNugget.asItem(), has(pNugget))
				.save(consumer, "chaosawakens:" + pIngot + "_from_nuggets");
	}

	private static void ingotToNugget(Consumer<IFinishedRecipe> consumer, IItemProvider pIngot, IItemProvider pNugget) {
		ShapelessRecipeBuilder.shapeless(pNugget, 9)
				.requires(pIngot)
				.unlockedBy("has_" + pIngot.asItem(), has(pIngot))
				.save(consumer);
	}

	private static void stonecutting(Consumer<IFinishedRecipe> consumer, IItemProvider pInput, IItemProvider pOutput) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(pInput), pOutput)
				.unlocks("has_" + pInput.asItem(), InventoryChangeTrigger.Instance.hasItems(pInput))
				.save(consumer, new ResourceLocation(ChaosAwakens.MODID, pOutput.asItem() + "_from_" + pInput.asItem() + "_stonecutting"));
	}

	private static void stonecutting(Consumer<IFinishedRecipe> consumer, IItemProvider pInput, IItemProvider pOutput, int count) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(pInput), pOutput, count)
				.unlocks("has_" + pInput.asItem(), InventoryChangeTrigger.Instance.hasItems(pInput))
				.save(consumer, new ResourceLocation(ChaosAwakens.MODID, pOutput.asItem() + "_from_" + pInput.asItem() + "_stonecutting"));
	}

	private static void smelting(Consumer<IFinishedRecipe> consumer, IItemProvider pInput, IItemProvider pOutput, Float experience, int cookingTime) {
		CookingRecipeBuilder.smelting(Ingredient.of(pInput), pOutput, experience, cookingTime)
				.unlockedBy("has_" + pInput, has(pInput))
				.save(consumer, "chaosawakens:" + pOutput.asItem() + "_from_smelting");
	}

	private static void blasting(Consumer<IFinishedRecipe> consumer, IItemProvider pInput, IItemProvider pOutput, Float experience, int cookingTime) {
		CookingRecipeBuilder.blasting(Ingredient.of(pInput), pOutput, experience, cookingTime)
				.unlockedBy("has_" + pInput, has(pInput))
				.save(consumer, "chaosawakens:" + pOutput + "_from_blasting");
	}

	private static void cooking(Consumer<IFinishedRecipe> consumer, IItemProvider pInput, IItemProvider pOutput, int cookingTime, String CookingRecipeSerializerID, CookingRecipeSerializer<?> cookingRecipeSerializer) {
		CookingRecipeBuilder.cooking(Ingredient.of(pInput), pOutput, 0.35F, cookingTime, cookingRecipeSerializer)
				.unlockedBy("has_" + pInput.asItem(), has(pInput))
				.save(consumer, "chaosawakens:" + pOutput + "_from_" + CookingRecipeSerializerID);
	}

	private static void plantSeeds(Consumer<IFinishedRecipe> consumer, IItemProvider pSeeds, IItemProvider pPlant) {
		ShapelessRecipeBuilder.shapeless(pSeeds, 2)
				.requires(pPlant)
				.unlockedBy("has_" + pPlant.asItem(), has(pPlant))
				.save(consumer);
	}

	private static void philoConversionRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider a, int aAmount, IItemProvider b, int bAmount) {
		whenModLoaded(ShapelessRecipeBuilder.shapeless(b, bAmount)
				.requires(PEItems.PHILOSOPHERS_STONE)
				.requires(a, aAmount)
				.unlockedBy("has_" + a.asItem(), hasItems(PEItems.PHILOSOPHERS_STONE, a)), "projecte")
				.generateAdvancement()
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "conversions/" + a + "_to_" + b));
		whenModLoaded(ShapelessRecipeBuilder.shapeless(a, aAmount)
				.requires(PEItems.PHILOSOPHERS_STONE)
				.requires(b, bAmount)
				.unlockedBy("has_" + b.asItem(), hasItems(PEItems.PHILOSOPHERS_STONE, b)), "projecte")
				.generateAdvancement()
				.build(consumer, new ResourceLocation(ChaosAwakens.MODID, "conversions/" + b + "_to_" + a));
	}

	public static ConditionalRecipe.Builder whenModLoaded(ShapelessRecipeBuilder recipe, String modid) {
		return ConditionalRecipe.builder().addCondition(new ModLoadedCondition(modid)).addRecipe(recipe::save);
	}

	protected static InventoryChangeTrigger.Instance hasItems(IItemProvider... items) {
		return InventoryChangeTrigger.Instance.hasItems(items);
	}
}
