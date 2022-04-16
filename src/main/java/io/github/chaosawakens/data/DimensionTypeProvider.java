package io.github.chaosawakens.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class DimensionTypeProvider implements IDataProvider {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final Map<ResourceLocation, DimensionTypeBuilder> dimensionType = new LinkedHashMap();
	public final DataGenerator generator;

	public DimensionTypeProvider(DataGenerator generator) {
		this.generator = generator;
	}

	@Override
	public final void run(DirectoryCache cache) {
		this.dimensionType.clear();
		this.addDimensionTypes();
		Iterator var2 = this.dimensionType.entrySet().iterator();

		while (var2.hasNext()) {
			Map.Entry<ResourceLocation, DimensionTypeBuilder> entry = (Map.Entry) var2.next();
			ResourceLocation dimensionType = entry.getKey();
			Path path = generator.getOutputFolder().resolve("data/" + dimensionType.getNamespace() + "/dimension_type/" + dimensionType.getPath() + ".json");

			try {
				IDataProvider.save(GSON, cache, (entry.getValue()).serialize(), path);
			} catch (IOException var7) {
				throw new RuntimeException("Couldn't save dimension type file for dimension: " + dimensionType, var7);
			}
		}
	}

	protected abstract void addDimensionTypes();

	protected DimensionTypeBuilder createDimensionType(ResourceLocation id) {
		if (this.dimensionType.containsValue(id)) {
			throw new RuntimeException("Dimension type '" + id + "' has already been registered.");
		} else {
			DimensionTypeBuilder dimensionType = new DimensionTypeBuilder(id);
			this.dimensionType.put(id, dimensionType);
			return dimensionType;
		}
	}

	@Override
	public String getName() {
		return "Dimension Type";
	}
}
