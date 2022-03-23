package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.CrystalBushBlock;
import io.github.chaosawakens.common.blocks.DenseBushBlock;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Collection;

public class CAItemModelProvider extends ItemModelProvider {
    private static final ResourceLocation ITEM_GENERATED = new ResourceLocation("item/generated");

    public CAItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
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

            ChaosAwakens.LOGGER.debug(item.getId());

            if (name.startsWith("enchanted"))
                name = name.substring(name.indexOf("_") + 1);

            /*
             *  Skip elements that have no texture at assets/chaosawakens/textures/item
             *  or already have an existing model at assets/chaosawakens/models/item
             */

            if (item.getId().getPath().contains("critter_cage")) {
            	getBuilder(item.getId().getPath()).parent(parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/critter_cages/" + name.replaceAll("_critter_cage", ""));
            }
            else if (item.getId().getPath().contains("_spawn_egg")) {
                getBuilder(item.getId().getPath()).parent(parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/spawn_eggs/" + name.replaceAll("_spawn_egg", ""));
            } else {
                if (!existingFileHelper.exists(getItemResourceLocation(name), TEXTURE) || existingFileHelper.exists(getItemResourceLocation(name), MODEL))
                    continue;
                getBuilder(item.getId().getPath()).parent(item.get().getMaxDamage(ItemStack.EMPTY) > 0 && !(item.get() instanceof ArmorItem) ? parentHandheld : parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/" + name);
            }
        }
    }

    private void generateBlockItems(final Collection<RegistryObject<Item>> itemBlocks) {
        for (RegistryObject<Item> item : itemBlocks) {
            String name = item.getId().getPath();
            BlockItem blockItem = (BlockItem) item.get();
            Block block = blockItem.getBlock();


            ChaosAwakens.LOGGER.debug(item.getId());

            /*
             *  Skip elements that have no block model inside of assets/chaosawakens/models/block
             *  or already have an existing item model at assets/chaosawakens/models/item
             */

            if (item.getId().getPath().contains("_trapdoor")) {
                withExistingParent(name, getBlockResourceLocation(name + "_bottom"));
            } else if ((block instanceof CrystalBushBlock || block instanceof DenseBushBlock) && (item.getId().getPath().contains("_grass") || item.getId().getPath().contains("_sun"))) {
                if (item.getId().getPath().contains("tall_") || item.getId().getPath().contains("thorny_")) {
                    singleTextureLayer0(name, ITEM_GENERATED, getBlockResourceLocation(name + "_top"));
                } else {
                    singleTextureLayer0(name, ITEM_GENERATED, getBlockResourceLocation(name));
                }
            } else {
                if (!existingFileHelper.exists(getBlockResourceLocation(name), MODEL) || existingFileHelper.exists(getItemResourceLocation(name), MODEL))
                    continue;
                withExistingParent(name, getBlockResourceLocation(name));
            }
        }
    }

    private static ResourceLocation getResourceLocation(String path) {
        return new ResourceLocation(ChaosAwakens.MODID, path);
    }

    private static ResourceLocation getBlockResourceLocation(String name) {
        return getResourceLocation("block/" + name);
    }

    private static ResourceLocation getItemResourceLocation(String name) {
        return getResourceLocation("item/" + name);
    }

    public ItemModelBuilder singleTextureLayer0(String name, ResourceLocation parent, ResourceLocation texture) {
        return singleTexture(name, parent, "layer0", texture);
    }
}
