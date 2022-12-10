package io.github.chaosawakens.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;

public abstract class BiomeTypeProvider implements IDataProvider {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final Map<ResourceLocation, BiomeTypeBuilder> biomeType = new LinkedHashMap<ResourceLocation, BiomeTypeBuilder>();
	public final DataGenerator generator;

	public BiomeTypeProvider(DataGenerator generator) {
		this.generator = generator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void run(DirectoryCache cache) {
		this.biomeType.clear();
		this.addBiomeTypes();
		Iterator<?> var2 = this.biomeType.entrySet().iterator();

		while (var2.hasNext()) {
			Map.Entry<ResourceLocation, BiomeTypeBuilder> entry = (Entry<ResourceLocation, BiomeTypeBuilder>) var2.next();
			ResourceLocation biomeType = entry.getKey();
			Path path = generator.getOutputFolder().resolve("data/" + biomeType.getNamespace() + "/biome/" + biomeType.getPath() + ".json");

			try {
				IDataProvider.save(GSON, cache, (entry.getValue()).serialize(), path);
			} catch (IOException var7) {
				throw new RuntimeException("Couldn't save biome type file for dimension: " + biomeType, var7);
			}
		}
	}

	protected abstract void addBiomeTypes();

	@SuppressWarnings("unlikely-arg-type")
	protected BiomeTypeBuilder createBiomeType(ResourceLocation id) {
		if (this.biomeType.containsValue(id)) throw new RuntimeException("Biome type '" + id + "' has already been registered.");
		else {
			BiomeTypeBuilder biomeType = new BiomeTypeBuilder(id);
			this.biomeType.put(id, biomeType);
			return biomeType;
		}
	}

	@Override
	public String getName() {
		return "Biome Type";
	}
}
