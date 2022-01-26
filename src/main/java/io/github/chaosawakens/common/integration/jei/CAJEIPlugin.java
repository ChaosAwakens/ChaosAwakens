package io.github.chaosawakens.common.integration.jei;

import java.util.Objects;


import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerContainer;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerScreen;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CARecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class CAJEIPlugin implements IModPlugin {
    public static final ResourceLocation MOD = new ResourceLocation(ChaosAwakens.MODID, "jei");
    public static final ResourceLocation DEFOSSILIZER_ID = new ResourceLocation(ChaosAwakens.MODID, "defossilizer");
    public static final int INV_SIZE = 4 * 9;
    
    public void init() {
    	
    }
    
    public static ResourceLocation getDefossilizerId() {
		return DefossilizerRecipeCategory.ID;
	}
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
    }

    @SuppressWarnings("deprecation")
	private void addDescription(IRecipeRegistration registry, ItemStack itemStack) {
        registry.addIngredientInfo(itemStack, VanillaTypes.ITEM, itemStack.getDescriptionId() + ".jei_desc");
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
    	registry.addRecipeTransferHandler(DefossilizerContainer.class, DefossilizerRecipeCategory.ID, 0, 3, 4, INV_SIZE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
    	@SuppressWarnings("resource")
		ClientWorld world = Objects.requireNonNull(Minecraft.getInstance().level);
        registry.addRecipes(world.getRecipeManager().getAllRecipesFor(CARecipes.DEFOSSILIZING_RECIPE_TYPE), DefossilizerRecipeCategory.ID);
        addDescription(registry, new ItemStack(CABlocks.DEFOSSILIZER.get()));
    }
    
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
    	registration.addRecipeClickArea(DefossilizerScreen.class, 79, 35, 20, 20, DefossilizerRecipeCategory.ID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(CABlocks.DEFOSSILIZER.get()), DefossilizerRecipeCategory.ID);
        registry.addRecipeCatalyst(new ItemStack(CABlocks.CRYSTAL_CRAFTING_TABLE.get()), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(CABlocks.CRYSTAL_FURNACE.get()), VanillaRecipeCategoryUid.FUEL);
        registry.addRecipeCatalyst(new ItemStack(CABlocks.CRYSTAL_FURNACE.get()), VanillaRecipeCategoryUid.FURNACE);
    }
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
    	IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
    	registration.addRecipeCategories(new DefossilizerRecipeCategory(guiHelper));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return MOD;
    }
}