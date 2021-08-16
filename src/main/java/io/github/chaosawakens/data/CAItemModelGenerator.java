package io.github.chaosawakens.data;

import java.util.Collection;

import javax.annotation.Nonnull;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class CAItemModelGenerator extends ItemModelProvider {

	public CAItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, ChaosAwakens.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		generate(CAItems.ITEMS.getEntries());
		generateBlockItems(CABlocks.ITEM_BLOCKS.getEntries());
	}

	@Nonnull
	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + " Item models";
	}

	private void generate(final Collection<RegistryObject<Item>> items) {
		final ModelFile parentGenerated = getExistingFile(mcLoc("item/generated"));
		final ExistingModelFile parentHandheld = getExistingFile(mcLoc("item/handheld"));

		for (RegistryObject<Item> item : items) {
			String name = item.getId().getPath();

			if (name.startsWith("enchanted"))
				name = name.substring(name.indexOf("_") + 1);

			/*
			 *  Skip elements that have no texture at assets/chaosawakens/textures/item
			 *  or already have an existing model at assets/chaosawakens/models/item
			 */
			if (!existingFileHelper.exists(new ResourceLocation(ChaosAwakens.MODID, "item/" + name), TEXTURE) || existingFileHelper.exists(new ResourceLocation(ChaosAwakens.MODID, "item/" + name), MODEL))
				continue;

			ChaosAwakens.LOGGER.info(item.getId());

			getBuilder(item.getId().getPath()).parent(item.get().getMaxDamage(ItemStack.EMPTY) > 0 && !(item.get() instanceof ArmorItem) ? parentHandheld : parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/" + name);
		}
	}

	private void generateBlockItems(final Collection<RegistryObject<Item>> itemBlocks) {
		for (RegistryObject<Item> item : itemBlocks) {
			String name = item.getId().getPath();

			/*
			 *  Skip elements that have no block model inside of assets/chaosawakens/models/block
			 *  or already have an existing item model at assets/chaosawakens/models/item
			 */

			if (!existingFileHelper.exists(new ResourceLocation(ChaosAwakens.MODID, "block/" + name), MODEL) || existingFileHelper.exists(new ResourceLocation(ChaosAwakens.MODID, "item/" + name), MODEL))
				continue;

			ChaosAwakens.LOGGER.info(item.getId());

			withExistingParent(name, new ResourceLocation(ChaosAwakens.MODID, "block/" + name));

		}
	}
}
