package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.crafting.recipe.DefossilizingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CARecipeTypes {
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ChaosAwakens.MODID);

	public static final IRecipeType<DefossilizingRecipe> DEFOSSILIZING_RECIPE_TYPE = IRecipeType.register(ChaosAwakens.MODID + "defossilizing");

	public static final RegistryObject<IRecipeSerializer<?>> DEFOSSILIZING_SERIALIZER = RECIPE_SERIALIZERS.register("defossilizing", DefossilizingRecipe.Serializer::new);
}
