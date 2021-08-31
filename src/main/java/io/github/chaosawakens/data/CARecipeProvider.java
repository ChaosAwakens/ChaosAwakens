package io.github.chaosawakens.data;

import java.io.IOException;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

import com.google.common.collect.Sets;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.util.ResourceLocation;

/**
 * This class is to make recipes that have tags, and mostly shapeless/smelting recipes. It does work for more diverse recipes. More diverse recipes however
 * should mostly be manually made, this class is only here to lighten the burden not remove it completely.
 * @author Meme Man
 *
 */

public class CARecipeProvider extends RecipeProvider{
	 protected final DataGenerator generator;

	public CARecipeProvider(DataGenerator data) {
		super(data);
		this.generator = data;
	}
	
	   protected Path getPath(ResourceLocation location) {
	       return this.generator.getOutputFolder().resolve("data/" + location.getNamespace() + "/recipes/" + location.getPath() + ".json");
	    }

	     public String getName() {
	        return "Chaos Awakens Recipes";
	     }
	     
	     @Override
	    public void run(DirectoryCache cache) throws IOException {
	    	 Path path = this.generator.getOutputFolder();
	         Set<ResourceLocation> set = Sets.newHashSet();
	         buildShapelessRecipes((p_200410_3_) -> {
	            if (!set.add(p_200410_3_.getId())) {
	               throw new IllegalStateException("Duplicate recipe " + p_200410_3_.getId());
	            } else {
	               saveRecipe(cache, p_200410_3_.serializeRecipe(), path.resolve("data/" + p_200410_3_.getId().getNamespace() + "/recipes/" + p_200410_3_.getId().getPath() + ".json"));
	            }
	         });
	    }
	     
	     
	     @Override
	    	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> recipe) {
	    		//Insert recipes here(all types lmao)
	    	}
	     
	     //Insert custom recipe methods here
	     
	     protected static void buildCookingRecipes(Consumer<IFinishedRecipe> consumer, String condition, CookingRecipeSerializer<?> recipe, int exp) {
	    	 //insert cooking recipes here
	     }
	
}
 
