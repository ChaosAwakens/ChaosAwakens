package io.github.chaosawakens.common.integration.jei;

import java.util.Arrays;
import java.util.Objects;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerBlock.DefossilizerType;
import io.github.chaosawakens.common.blocks.tileentities.containers.CopperDefossilizerContainer;
import io.github.chaosawakens.common.blocks.tileentities.containers.CrystalDefossilizerContainer;
import io.github.chaosawakens.common.blocks.tileentities.containers.IronDefossilizerContainer;
import io.github.chaosawakens.common.blocks.tileentities.screens.CopperDefossilizerScreen;
import io.github.chaosawakens.common.blocks.tileentities.screens.CrystalDefossilizerScreen;
import io.github.chaosawakens.common.blocks.tileentities.screens.IronDefossilizerScreen;
import io.github.chaosawakens.common.integration.jei.recipecategories.DefossilizerRecipeCategory;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CARecipeTypes;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

@JeiPlugin
public class CAJEIPlugin implements IModPlugin {
	public static final ResourceLocation MOD = ChaosAwakens.prefix("jei");
	public static final int INV_SIZE = 4 * 9;

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

	private void addDescription(IRecipeRegistration registry, ITextComponent langEntry, ItemStack... itemStack) {
		registry.addIngredientInfo(Arrays.asList(itemStack), VanillaTypes.ITEM, langEntry);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
		registry.addRecipeTransferHandler(IronDefossilizerContainer.class, DefossilizerRecipeCategory.ID, 0, 3, 4, INV_SIZE);
		registry.addRecipeTransferHandler(CopperDefossilizerContainer.class, DefossilizerRecipeCategory.ID, 0, 3, 4, INV_SIZE);
		registry.addRecipeTransferHandler(CrystalDefossilizerContainer.class, DefossilizerRecipeCategory.ID, 0, 3, 4, INV_SIZE);
	}

	@SuppressWarnings("resource")
	@Override
	public void registerRecipes(IRecipeRegistration registry) {
		ClientWorld world = Objects.requireNonNull(Minecraft.getInstance().level);
		
		registry.addRecipes(world.getRecipeManager().getAllRecipesFor(CARecipeTypes.DEFOSSILIZING_RECIPE_TYPE), DefossilizerRecipeCategory.ID);
		
		addDescription(registry, new TranslationTextComponent("jei_desc.chaosawakens.copper_defossilizer"), new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get()));
		addDescription(registry, new TranslationTextComponent("jei_desc.chaosawakens.iron_defossilizer"), new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get()));
		addDescription(registry, new TranslationTextComponent("jei_desc.chaosawakens.crystal_defossilizer"), new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.CRYSTAL.getId())).get()));
		addDescription(registry, new TranslationTextComponent("jei_desc.chaosawakens.defossilizer"), new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get()), new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get()));
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(IronDefossilizerScreen.class, 79, 35, 20, 20, DefossilizerRecipeCategory.ID);
		registration.addRecipeClickArea(CopperDefossilizerScreen.class, 79, 35, 20, 20, DefossilizerRecipeCategory.ID);
		registration.addRecipeClickArea(CrystalDefossilizerScreen.class, 79, 35, 20, 20, DefossilizerRecipeCategory.ID);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
		registry.addRecipeCatalyst(new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get()), DefossilizerRecipeCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get()), DefossilizerRecipeCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.CRYSTAL.getId())).get()), DefossilizerRecipeCategory.ID);
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
