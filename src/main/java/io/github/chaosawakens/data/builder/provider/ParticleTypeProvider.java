package io.github.chaosawakens.data.builder.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.chaosawakens.data.builder.ParticleTypeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ParticleTypeProvider implements IDataProvider {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final Map<String, ParticleTypeBuilder> particleType = new LinkedHashMap();
	public final DataGenerator generator;

	public ParticleTypeProvider(DataGenerator generator) {
		this.generator = generator;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public final void run(DirectoryCache cache) {
		this.particleType.clear();
		this.addParticleTypes();

		for (Map.Entry<String, ParticleTypeBuilder> resourceLocationDimensionTypeBuilderEntry : this.particleType.entrySet()) {
			Map.Entry<String, ParticleTypeBuilder> entry = (Map.Entry) resourceLocationDimensionTypeBuilderEntry;
			String particleType = entry.getKey();
			Path path = generator.getOutputFolder().resolve("assets/" + "chaosawakens" + "/particles/" + particleType + ".json");

			try {
				IDataProvider.save(GSON, cache, (entry.getValue()).serialize(), path);
			} catch (IOException var7) {
				throw new RuntimeException("Couldn't save particle type file for particle type: " + particleType, var7);
			}
		}
	}

	protected abstract void addParticleTypes();

	@SuppressWarnings("unlikely-arg-type")
	protected ParticleTypeBuilder createParticleType(String id) {
		if (this.particleType.containsValue(id)) throw new RuntimeException("Particle type '" + id + "' has already been registered.");
		else {
			ParticleTypeBuilder particleType = new ParticleTypeBuilder(id);
			this.particleType.put(id, particleType);
			return particleType;
		}
	}

	@Override
	public String getName() {
		return "Particle Type";
	}

}
