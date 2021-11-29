package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.CAOreBlock;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItemGroups;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CARecipes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Consumer;

public class CARecipeProvider extends RecipeProvider {

	public CARecipeProvider(DataGenerator gen) {
		super(gen);
	}

	public String getName() {
		return "Chaos Awakens Recipes";
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		//insert recipes here (all types)
		buildDefossilizingRecipes(consumer);
	}

	//Insert custom recipe methods here

	protected static void buildCookingRecipes(Consumer<IFinishedRecipe> consumer, String condition, CookingRecipeSerializer<?> recipe, int exp) {
		//insert cooking recipes here
	}

	// FOSSILISED MOBS
	// Overworld (CA)
	private void buildDefossilizingRecipes(Consumer<IFinishedRecipe> consumer) {
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_ACACIA_ENT.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.ACACIA_ENT_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ACACIA_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_BIRCH_ENT.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.BIRCH_ENT_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_BIRCH_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_DARK_OAK_ENT.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.DARK_OAK_ENT_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_DARK_OAK_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_JUNGLE_ENT.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.JUNGLE_ENT_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_JUNGLE_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_OAK_ENT.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.OAK_ENT_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_OAK_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_SPRUCE_ENT.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.SPRUCE_ENT_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SPRUCE_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_HERCULES_BEETLE.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.HERCULES_BEETLE_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_HERCULES_BEETLE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_RUBY_BUG.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.RUBY_BUG_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_RUBY_BUG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_EMERALD_GATOR.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.EMERALD_GATOR_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_EMERALD_GATOR.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
//		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
//				Ingredient.of(CABlocks.FOSSILISED_WTF.get()),
//				Ingredient.of(Items.WATER_BUCKET),
//				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
//				CAItems.WTF_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WTF.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
//		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
//				Ingredient.of(CABlocks.FOSSILISED_SCORPION.get()),
//				Ingredient.of(Items.WATER_BUCKET),
//				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
//				CAItems.SCORPION_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SCORPION.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_WASP.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.WASP_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WASP.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
//		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
//				Ingredient.of(CABlocks.FOSSILISED_PIRAPORU.get()),
//				Ingredient.of(Items.WATER_BUCKET),
//				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
//				CAItems.PIRAPORU_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PIRAPORU.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_APPLE_COW.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.APPLE_COW_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_APPLE_COW.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_GOLDEN_APPLE_COW.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.GOLDEN_APPLE_COW_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_GOLDEN_APPLE_COW.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_CARROT_PIG.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.CARROT_PIG_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_CARROT_PIG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.GOLDEN_CARROT_PIG_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// Overworld (Vanilla)
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_BAT.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.BAT_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_BAT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_BEE.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.BEE_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_BEE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_CAVE_SPIDER.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.CAVE_SPIDER_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_CAVE_SPIDER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_CHICKEN.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.CHICKEN_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_CHICKEN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_COD.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.COD_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_COD.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_COW.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.COW_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_COW.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_CREEPER.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.CREEPER_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_CREEPER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_DOLPHIN.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.DOLPHIN_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_DOLPHIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_DONKEY.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.DONKEY_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_DONKEY.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_DROWNED.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.DROWNED_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_DROWNED.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_ENDERMAN.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.ENDERMAN_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ENDERMAN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_EVOKER.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.EVOKER_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_EVOKER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_FOX.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.FOX_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_FOX.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_GIANT.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.GIANT_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_GIANT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_GUARDIAN.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.GUARDIAN_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_GUARDIAN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_HUSK.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.HUSK_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_HUSK.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_ILLUSIONER.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.ILLUSIONER_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ILLUSIONER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_IRON_GOLEM.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.IRON_GOLEM_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_IRON_GOLEM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_LLAMA.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.LLAMA_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_LLAMA.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_MOOSHROOM.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.MOOSHROOM_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_MOOSHROOM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_OCELOT.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.OCELOT_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_OCELOT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_PANDA.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.PANDA_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PANDA.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_PIG.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.PIG_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PIG.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_PHANTOM.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.PHANTOM_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PHANTOM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_PILLAGER.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.PILLAGER_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PILLAGER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_POLAR_BEAR.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.POLAR_BEAR_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_POLAR_BEAR.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_PUFFERFISH.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.PUFFERFISH_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PUFFERFISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_RABBIT.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.RABBIT_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_RABBIT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_RAVAGER.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.RAVAGER_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_RAVAGER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_SALMON.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.SALMON_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SALMON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_SHEEP.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.SHEEP_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SHEEP.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_SKELETON.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.SKELETON_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SKELETON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_SKELETON_HORSE.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.SKELETON_HORSE_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SKELETON_HORSE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_SLIME.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.SLIME_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SLIME.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_SNOW_GOLEM.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.SNOW_GOLEM_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SNOW_GOLEM.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_SPIDER.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.SPIDER_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SPIDER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_SQUID.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.SQUID_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SQUID.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_STRAY.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.STRAY_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_STRAY.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_TROPICAL_FISH.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.TROPICAL_FISH_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_TROPICAL_FISH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_TURTLE.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.TURTLE_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_TURTLE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_VILLAGER.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.VILLAGER_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_VILLAGER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_VINDICATOR.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.VINDICATOR_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_VINDICATOR.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_WANDERING_TRADER.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.WANDERING_TRADER_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WANDERING_TRADER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_WITCH.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.WITCH_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WITCH.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_WOLF.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.WOLF_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WOLF.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_ZOMBIE.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.ZOMBIE_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ZOMBIE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_ZOMBIE_HORSE.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.ZOMBIE_HORSE_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ZOMBIE_HORSE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// Nether (CA)
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_CRIMSON_ENT.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.CRIMSON_ENT_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_CRIMSON_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_WARPED_ENT.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				CAItems.WARPED_ENT_SPAWN_EGG.get(), 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WARPED_ENT.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// Nether (Vanilla)
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_BLAZE.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.BLAZE_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_BLAZE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_GHAST.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.GHAST_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_GHAST.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_HOGLIN.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.HOGLIN_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_HOGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.ENDERMAN_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.MAGMA_CUBE_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.MAGMA_CUBE_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_PIGLIN.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.PIGLIN_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_PIGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.SKELETON_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_STRIDER.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.STRIDER_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_STRIDER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_WITHER_SKELETON.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.WITHER_SKELETON_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_WITHER_SKELETON.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.get()),
				Ingredient.of(Items.LAVA_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));

		// End (CA)

		// End (Vanilla)
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_ENDERMAN_END_STONE.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.ENDERMAN_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ENDERMAN_END_STONE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_ENDERMITE.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.ENDERMITE_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_ENDERMITE.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
		FossilRecipeBuilder.builder(CARecipes.DEFOSSILIZING_SERIALIZER.get(),
				Ingredient.of(CABlocks.FOSSILISED_SHULKER.get()),
				Ingredient.of(Items.WATER_BUCKET),
				Ingredient.of(CAItems.ALUMINUM_POWER_CHIP.get()),
				Items.SHULKER_SPAWN_EGG, 1, 20).build(consumer, new ResourceLocation(ChaosAwakens.MODID, "defossilizing/" + CABlocks.FOSSILISED_SHULKER.getId().toString().replaceAll("chaosawakens:", "") + "_to_spawn_egg"));
	}
}
