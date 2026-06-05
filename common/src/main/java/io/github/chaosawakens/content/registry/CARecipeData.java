package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.data.recipe.defossilizing.AbstractDefossilizingRecipe;
import io.github.chaosawakens.content.data.recipe.defossilizing.CrystalDefossilizingRecipe;
import io.github.chaosawakens.content.data.recipe.defossilizing.IronDefossilizingRecipe;
import io.github.chaosawakens.content.data.recipe_serializer.DefossilizingRecipeSerializer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class CARecipeData {

    @RegistrarEntry
    public static class CARecipeTypes {
        private static final ObjectArrayList<Supplier<RecipeType<? extends Recipe<? extends Container>>>> RECIPE_TYPES = new ObjectArrayList<>();

        // Defossilization
        public static final Supplier<RecipeType<AbstractDefossilizingRecipe>> DEFOSSILIZING = registerRecipeType("defossilizing");

        private static <C extends Container, R extends Recipe<C>> Supplier<RecipeType<R>> registerRecipeType(ResourceLocation recipeTypeId, Supplier<RecipeType<R>> recipeTypeSup) {
            Supplier<RecipeType<R>> registeredRecipeType = NexusServices.REGISTRAR.registerObject(recipeTypeId, recipeTypeSup, BuiltInRegistries.RECIPE_TYPE);

            RECIPE_TYPES.add(registeredRecipeType::get);

            return registeredRecipeType;
        }

        private static <C extends Container, R extends Recipe<C>> Supplier<RecipeType<R>> registerRecipeType(ResourceLocation recipeTypeId) {
            return registerRecipeType(recipeTypeId, () -> new RecipeType<>() {


                @Override
                public String toString() {
                    return recipeTypeId.toString();
                }
            });
        }

        private static <C extends Container, R extends Recipe<C>> Supplier<RecipeType<R>> registerRecipeType(String recipeTypeId) {
            return registerRecipeType(CAConstants.prefix(recipeTypeId));
        }

        public static ImmutableList<Supplier<RecipeType<? extends Recipe<? extends Container>>>> getRecipeTypes() {
            return ImmutableList.copyOf(RECIPE_TYPES);
        }
    }

    @RegistrarEntry
    public static class CARecipeSerializers {
        protected static final ObjectArrayList<Supplier<RecipeSerializer<? extends Recipe<? extends Container>>>> RECIPE_SERIALIZERS = new ObjectArrayList<>();

        // Defossilization
        public static final Supplier<RecipeSerializer<AbstractDefossilizingRecipe>> IRON_DEFOSSILIZING = registerRecipeSerializer("iron_defossilizing", () -> new DefossilizingRecipeSerializer<>(IronDefossilizingRecipe::new, 200));
        public static final Supplier<RecipeSerializer<AbstractDefossilizingRecipe>> CRYSTAL_DEFOSSILIZING = registerRecipeSerializer("crystal_defossilizing", () -> new DefossilizingRecipeSerializer<>(CrystalDefossilizingRecipe::new, 200));

        private static <C extends Container, R extends Recipe<C>> Supplier<RecipeSerializer<R>> registerRecipeSerializer(ResourceLocation recipeSerializerId, Supplier<RecipeSerializer<R>> recipeSerializerSup) {
            Supplier<RecipeSerializer<R>> registeredRecipeSerializer = NexusServices.REGISTRAR.registerObject(recipeSerializerId, recipeSerializerSup, BuiltInRegistries.RECIPE_SERIALIZER);

            RECIPE_SERIALIZERS.add(registeredRecipeSerializer::get);

            return registeredRecipeSerializer;
        }

        private static <C extends Container, R extends Recipe<C>> Supplier<RecipeSerializer<R>> registerRecipeSerializer(String recipeSerializerId, Supplier<RecipeSerializer<R>> recipeSerializerSup) {
            return registerRecipeSerializer(CAConstants.prefix(recipeSerializerId), recipeSerializerSup);
        }

        public static ImmutableList<Supplier<RecipeSerializer<? extends Recipe<? extends Container>>>> getRecipeSerializers() {
            return ImmutableList.copyOf(RECIPE_SERIALIZERS);
        }
    }
}
