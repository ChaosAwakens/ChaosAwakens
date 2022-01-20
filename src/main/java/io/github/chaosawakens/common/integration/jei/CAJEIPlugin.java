package io.github.chaosawakens.common.integration.jei;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class CAJEIPlugin implements IModPlugin {
    public static final ResourceLocation MOD = new ResourceLocation(ChaosAwakens.MODID, "chaosawakens");
    public static final ResourceLocation DEFOSSILIZER_ID = new ResourceLocation(ChaosAwakens.MODID, "defossilizer");

    private void addDescription(IRecipeRegistration registry, ItemStack itemStack) {
        registry.addIngredientInfo(itemStack, VanillaTypes.ITEM, itemStack.getDescriptionId() + ".jei_desc");
    }

    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {

    }

    public void registerRecipes(IRecipeRegistration registry) {
        addDescription(registry, new ItemStack(CABlocks.DEFOSSILIZER.get()));
    }


    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(CABlocks.DEFOSSILIZER.get()), DEFOSSILIZER_ID);
        registry.addRecipeCatalyst(new ItemStack(CABlocks.CRYSTAL_CRAFTING_TABLE.get()), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(CABlocks.CRYSTAL_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
        registry.addRecipeCatalyst(new ItemStack(CABlocks.CRYSTAL_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return MOD;
    }
}