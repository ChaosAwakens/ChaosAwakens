package io.github.chaosawakens.data;

import java.io.BufferedWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is to make recipes that have tags, and mostly shapeless/smelting recipes. It does work for more diverse recipes. More diverse recipes however
 * should mostly be manually made, this class is only here to lighten the burden not remove it completely.
 * @author Meme Man
 *
 */

public class CARecipeProvider extends RecipeProvider{
	protected final DataGenerator generator;
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

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
		buildShapelessRecipes((builder) -> {
			if (!set.add(builder.getId())) {
				throw new IllegalStateException("Duplicate recipe " + builder.getId());
			} else {
				saveRecipe(cache, builder.serializeRecipe(), path.resolve("data/" + builder.getId().getNamespace() + "/recipes/" + builder.getId().getPath() + ".json"));
			}
		});
	}

	public static void saveRecipe(DirectoryCache p_208311_0_, JsonObject p_208311_1_, Path p_208311_2_) {
		try {
			String s = GSON.toJson((JsonElement)p_208311_1_);
			String s1 = SHA1.hashUnencodedChars(s).toString();
			if (!Objects.equals(p_208311_0_.getHash(p_208311_2_), s1) || !Files.exists(p_208311_2_)) {
				Files.createDirectories(p_208311_2_.getParent());
				try (BufferedWriter bufferedwriter = Files.newBufferedWriter(p_208311_2_)) {
					bufferedwriter.write(s);
				}
			}
			p_208311_0_.putNew(p_208311_2_, s1);
		} catch (IOException ioexception) {
			LOGGER.error("Couldn't save recipe {}", p_208311_2_, ioexception);
		}
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> recipe) {
		//insert recipes here (all types)
	}

	//Insert custom recipe methods here
	     
	protected static void buildCookingRecipes(Consumer<IFinishedRecipe> consumer, String condition, CookingRecipeSerializer<?> recipe, int exp) {
		//insert cooking recipes here
	}
}
 
