package io.github.chaosawakens.common.integration.jei;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerIronContainer;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerIronScreen;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CARecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.handlers.IScreenHandler;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Objects;

@JeiPlugin
public class CAJEIPlugin implements IModPlugin {
	public static final ResourceLocation MOD = new ResourceLocation(ChaosAwakens.MODID, "jei");
	public static final int INV_SIZE = 4 * 9;

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

	private void addDescription(IRecipeRegistration registry, String langEntry, ItemStack... itemStack) {
		registry.addIngredientInfo(Arrays.asList(itemStack), VanillaTypes.ITEM, langEntry);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
		registry.addRecipeTransferHandler(DefossilizerIronContainer.class, DefossilizerRecipeCategory.ID, 0, 3, 4, INV_SIZE);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registry) {
		ClientWorld world = Objects.requireNonNull(Minecraft.getInstance().level);
		registry.addRecipes(world.getRecipeManager().getAllRecipesFor(CARecipes.DEFOSSILIZING_RECIPE_TYPE), DefossilizerRecipeCategory.ID);
		addDescription(registry, "jei_desc.chaosawakens.copper_defossilizer",
				new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.COPPER.getId())).get()));
		addDescription(registry, "jei_desc.chaosawakens.iron_defossilizer",
				new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get()));
		addDescription(registry, "jei_desc.chaosawakens.defossilizer",
				new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.COPPER.getId())).get()),
				new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get()));
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(DefossilizerIronScreen.class, 79, 35, 20, 20, DefossilizerRecipeCategory.ID);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
		registry.addRecipeCatalyst(new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.COPPER.getId())).get()), DefossilizerRecipeCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get()), DefossilizerRecipeCategory.ID);
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
