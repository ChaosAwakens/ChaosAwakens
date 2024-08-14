package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.standard.BlockPropertyWrapper;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CARecipeProvider extends RecipeProvider {

    public CARecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        if (!BlockPropertyWrapper.getMappedBwps().isEmpty()) {
            BlockPropertyWrapper.getMappedBwps().forEach((blockSupEntry, bpwEntry) -> {
                Function<Consumer<FinishedRecipe>, Consumer<Supplier<Block>>> recipeFunc = bpwEntry.getRecipeMappingFunction() == null ? null : bpwEntry.getRecipeMappingFunction();

                if (recipeFunc != null) {
                    CAConstants.LOGGER.debug("[Generating Block Recipe]: " + blockSupEntry.get().getDescriptionId());

                    recipeFunc.apply(pWriter).accept(blockSupEntry);
                }
            });
        }
    }
}
