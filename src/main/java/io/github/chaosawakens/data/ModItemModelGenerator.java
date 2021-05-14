package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.CABlocks;
import io.github.chaosawakens.registry.CAItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.function.Supplier;

public class ModItemModelGenerator extends ItemModelProvider {

	public ModItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, ChaosAwakens.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		generate(CAItems.ITEMS.getEntries());
		/*
		 * generated(ModItems.CORNDOG, ModItems.COOKED_CORNDOG, ModItems.BACON,
		 * ModItems.COOKED_BACON, ModItems.APPLE_SEEDS, ModItems.BUTTER,
		 * ModItems.RADISH, ModItems.RADISH_STEW, ModItems.SALT, ModItems.STRAWBERRY,
		 * ModItems.STRAWBERRY_SEEDS, ModItems.TOMATO_SEEDS, ModItems.CHERRIES,
		 * ModItems.CORN, ModItems.TOMATO, ModItems.LETTUCE, ModItems.CHEESE,
		 * ModItems.GARDEN_SALAD, ModItems.CRAB_MEAT, ModItems.COOKED_CRAB_MEAT,
		 * ModItems.PEACOCK_LEG, ModItems.COOKED_PEACOCK_LEG, ModItems.PEACH,
		 * ModItems.PEACH_SEED, ModItems.LETTUCE_SEEDS, ModItems.RADISH_SEEDS,
		 * ModItems.SEAFOOD_PATTY, ModItems.CORN_SEEDS, ModItems.LAVA_EEL,
		 * ModItems.CHERRY_SEEDS, ModItems.GREEN_FISH, ModItems.ROCK_FISH,
		 * ModItems.SPARK_FISH, ModItems.WOOD_FISH, ModItems.BLT, ModItems.GOLDEN_BREAD,
		 * ModItems.GOLDEN_CHICKEN, ModItems.GOLDEN_TROPICAL_FISH, ModItems.GOLDEN_COD,
		 * ModItems.GOLDEN_PORKCHOP,ModItems.GOLDEN_MELON_SLICE, ModItems.GOLDEN_MUSHROOM_STEW
		 * ModItems.GOLDEN_STEAK, ModItems.GOLDEN_COOKIE, ModItems.GOLDEN_POTATO,
		 * ModItems.GOLDEN_PUMPKIN_PIE, ModItems.GOLDEN_FLESH, ModItems.GOLDEN_PUFFERFISH, ModItems.GOLDEN_SALMON,
		 * ModItems.GOLDEN_CANDYCANE, ModItems.ULTIMATE_APPLE, ModItems.CANDYCANE,
		 * ModItems.GOLDEN_MELON_SEEDS, ModItems.AMETHYST, ModItems.RUBY,
		 * ModItems.TIGERS_EYE, ModItems.TITANIUM_INGOT, ModItems.TITANIUM_NUGGET,
		 * ModItems.URANIUM_INGOT, ModItems.URANIUM_NUGGET, ModItems.ALUMINIUM_INGOT,
		 * ModItems.AMETHYST_HELMET, ModItems.AMETHYST_CHESTPLATE, ModItems.AMETHYST_LEGGINGS, ModItems.AMETHYST_BOOTS,
		 * ModItems.EMERALD_HELMET, ModItems.EMERALD_CHESTPLATE, ModItems.EMERALD_LEGGINGS, ModItems.EMERALD_BOOTS,
		 * ModItems.RUBY_HELMET, ModItems.RUBY_CHESTPLATE, ModItems.RUBY_LEGGINGS, ModItems.RUBY_BOOTS,
		 * ModItems.TIGERS_EYE_HELMET, ModItems.TIGERS_EYE_CHESTPLATE, ModItems.TIGERS_EYE_LEGGINGS, ModItems.TIGERS_EYE_BOOTS,
		 * ModItems.ULTIMATE_HELMET,* ModItems.ULTIMATE_CHESTPLATE, ModItems.ULTIMATE_LEGGINGS, ModItems.ULTIMATE_BOOTS,
		 * ModItems.EXPERIENCE_HELMET, ModItems.EXPERIENCE_CHESTPLATE, ModItems.EXPERIENCE_LEGGINGS, ModItems.EXPERIENCE_BOOTS,
		 * ModItems.LAPIS_HELMET, ModItems.LAPIS_CHESTPLATE, ModItems.LAPIS_LEGGINGS, ModItems.LAPIS_BOOTS,
		 * ModItems.LAVA_EEL_HELMET, ModItems.LAVA_EEL_CHESTPLATE, ModItems.LAVA_EEL_LEGGINGS, ModItems.LAVA_EEL_BOOTS);
		 *
		 * handHeld(ModItems.AMETHYST_SWORD, ModItems.AMETHYST_PICKAXE, ModItems.AMETHYST_SHOVEL, ModItems.AMETHYST_AXE, ModItems.AMETHYST_HOE,
		 * ModItems.EMERALD_SWORD, ModItems.EMERALD_PICKAXE, ModItems.EMERALD_SHOVEL, ModItems.EMERALD_AXE, ModItems.EMERALD_HOE,
		 * ModItems.RUBY_SWORD, ModItems.RUBY_PICKAXE, ModItems.RUBY_SHOVEL, ModItems.RUBY_AXE, ModItems.RUBY_HOE,
		 * ModItems.TIGERS_EYE_SWORD, ModItems.TIGERS_EYE_PICKAXE, ModItems.TIGERS_EYE_SHOVEL, ModItems.TIGERS_EYE_AXE,ModItems.TIGERS_EYE_HOE,
		 * ModItems.ULTIMATE_SWORD, ModItems.ULTIMATE_PICKAXE, ModItems.ULTIMATE_SHOVEL, ModItems.ULTIMATE_AXE, ModItems.ULTIMATE_HOE,
		 * ModItems.EXPERIENCE_SWORD, ModItems.THUNDER_STAFF, ModItems.BIG_HAMMER, ModItems.FAIRY_SWORD, ModItems.NIGHTMARE_SWORD,
		 * ModItems.POISON_SWORD, ModItems.RAT_SWORD, ModItems.TIN_BOOTS);
		 */

		generateBlockItems(CABlocks.ITEMS.getEntries());
		/*
		 * this.simpleWithExistingParent(ModBlocks.ALUMINIUM_BLOCK.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.ALUMINIUM_ORE.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.AMETHYST_BLOCK.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.AMETHYST_ORE.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.RUBY_BLOCK.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.RUBY_ORE.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.SALT_ORE.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.TIGERS_EYE_BLOCK.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.TIGERS_EYE_ORE.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.TITANIUM_BLOCK.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.TITANIUM_ORE.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.URANIUM_BLOCK.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.URANIUM_ORE.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.FOSSILISED_ENT.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.FOSSILISED_HERCULES_BEETLE.getId().
		 * toString().replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.FOSSILISED_RUBY_BUG.getId().toString(
		 * ).replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.FOSSILISED_EMERALD_GATOR.getId().
		 * toString().replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.GATE_BLOCK.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.ENT_DUNGEON_WOOD.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.GOLDEN_MELON.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.COPPER_ORE.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.TIN_ORE.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.SILVER_ORE.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.PLATINUM_ORE.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.COPPER_BLOCK.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.SILVER_BLOCK.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.TIN_BLOCK.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 * this.simpleWithExistingParent(ModBlocks.PLATINUM_BLOCK.getId().toString().
		 * replaceFirst("chaosawakens:", ""));
		 */

	}

	@Nonnull
	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + " Item models";
	}

	private final void generate(final Collection<RegistryObject<Item>> items) {
		final ModelFile parentGenerated = getExistingFile(mcLoc("item/generated"));
		final ExistingModelFile parentHandheld = getExistingFile(mcLoc("item/handheld"));

		for (RegistryObject<Item> item : items) {
			String name = item.getId().getPath();

			if (name.contains("enchanted"))
				name = name.substring(name.indexOf("_") + 1);

			/*
			 *  Skip elements that have no texture at assets/chaosawakens/textures
			 *  or already have an existing model at assets/chaosawakens/models
			 */
			if (!existingFileHelper.exists(new ResourceLocation(ChaosAwakens.MODID, "item/" + name), TEXTURE) || existingFileHelper.exists(new ResourceLocation(ChaosAwakens.MODID, "item/" + name), MODEL))
				continue;

			ChaosAwakens.LOGGER.info(item.getId());
			
			getBuilder(item.getId().getPath()).parent(item.get().getMaxDamage(ItemStack.EMPTY) > 0 && !(item.get() instanceof ArmorItem) ? parentHandheld : parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/" + name);
		}
	}

	private void generateBlockItems(Collection<RegistryObject<Item>> itemBlocks) {
		for (RegistryObject<Item> item : itemBlocks) {
			String name = item.getId().getPath();

			/*
			 *  Skip elements that have no block model inside of assets/chaosawakens/models
			 *  or already have an existing item model at the same path
			 */
			if (!existingFileHelper.exists(new ResourceLocation(ChaosAwakens.MODID, "block/" + name), MODEL) || existingFileHelper.exists(new ResourceLocation(ChaosAwakens.MODID, "item/" + name), MODEL))
				continue;
			
			ChaosAwakens.LOGGER.info(item.getId());
			
			withExistingParent(name, new ResourceLocation(ChaosAwakens.MODID, "block/" + name));

		}
	}
}
