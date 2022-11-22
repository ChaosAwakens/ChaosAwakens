package io.github.chaosawakens.data;

import com.google.gson.JsonArray;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class BiomeTypeBuilder {
	@SuppressWarnings("unused")
	private final ResourceLocation id;
	private boolean playerSpawnFriendly;
	private String precipitation;
	private Double temperature;
	private Double downfall;
	private String category;
	private Double depth;
	private Double scale;
	private String effects;
	private String moodSound;
	private ResourceLocation sound;
	private Integer tickDelay;
	private Integer blockSearchExtent;
	private Double offset;
	private Integer skyColor;
	private Integer fogColor;
	private Integer waterColor;
	private Integer waterFogColor;
	private Integer grassColor;
	private Integer foliageColor;
	private String surfaceBuilder;
	private String carvers;
	private String carver;
	private String subCarver;
	private String features;
	private String feature;
	private String starts;
	private String start;
	private String spawners;
	private String creature;
	private String monster;
	private EntityType<?> subCreature;
	private EntityType<?> subMonster;
	private ResourceLocation type;
	private Integer weight;
	private Integer minCount;
	private Integer maxCount;
	private String spawnCosts;

	BiomeTypeBuilder(ResourceLocation id) {
		this.id = id;
	}

	public BiomeTypeBuilder playerSpawnFriendly(boolean playerSpawnFriendly) {
		if (this.playerSpawnFriendly) throw new RuntimeException("playerSpawnFriendly has already been set, remove unnecessary/duplicate call.");
		else {
			this.playerSpawnFriendly = playerSpawnFriendly;
			return this;
		}
	}

	public BiomeTypeBuilder precipitation(String precipitation) {
		this.precipitation = precipitation;
		return this;
	}

	public BiomeTypeBuilder temperature(double temperature) {
		this.temperature = temperature;
		return this;
	}

	public BiomeTypeBuilder downfall(double downfall) {
		this.downfall = downfall;
		return this;
	}

	public BiomeTypeBuilder category(String category) {
		this.category = category;
		return this;
	}

	public BiomeTypeBuilder depth(double depth) {
		this.depth = depth;
		return this;
	}

	public BiomeTypeBuilder scale(double scale) {
		this.scale = scale;
		return this;
	}

	public BiomeTypeBuilder effects(String effects) {
		if (this.effects == null) throw new NullPointerException("effects cannot be null");
		else {
			this.effects = effects;
			return this;
		}
	}

	public BiomeTypeBuilder moodSound(String moodSound) {
		this.moodSound = moodSound;
		return this;
	}

	public BiomeTypeBuilder sound(ResourceLocation sound) {
		if (this.moodSound == null && this.sound != null) throw new NullPointerException("sound cannot be registered, as moodSound is null");
		else {
			this.sound = sound;
			return this;
		}
	}

	public BiomeTypeBuilder tickDelay(int tickDelay) {
		if (this.moodSound == null && this.tickDelay != null) throw new NullPointerException("tickDelay cannot be registered, as moodSound is null");
		else {
			this.tickDelay = tickDelay;
			return this;
		}
	}

	public BiomeTypeBuilder blockSearchExtent(int blockSearchExtent) {
		if (this.moodSound == null && this.blockSearchExtent != null) throw new NullPointerException("blockSearchExtent cannot be registered, as moodSound is null");
		else {
			this.blockSearchExtent = blockSearchExtent;
			return this;
		}
	}

	public BiomeTypeBuilder offset(double offset) {
		if (this.moodSound == null && this.offset != null) throw new NullPointerException("offset cannot be registered, as moodSound is null");
		else {
			this.offset = offset;
			return this;
		}
	}

	public BiomeTypeBuilder skyColor(int skyColor) {
		this.skyColor = skyColor;
		return this;
	}

	public BiomeTypeBuilder fogColor(int fogColor) {
		this.fogColor = fogColor;
		return this;
	}

	public BiomeTypeBuilder waterColor(int waterColor) {
		this.waterColor = waterColor;
		return this;
	}

	public BiomeTypeBuilder waterFogColor(int waterFogColor) {
		this.waterFogColor = waterFogColor;
		return this;
	}

	public BiomeTypeBuilder grassColor(int grassColor) {
		this.grassColor = grassColor;
		return this;
	}

	public BiomeTypeBuilder foliageColor(int foliageColor) {
		this.foliageColor = foliageColor;
		return this;
	}

	public BiomeTypeBuilder surfaceBuilder(String surfaceBuilder) {
		this.surfaceBuilder = surfaceBuilder;
		return this;
	}

	public BiomeTypeBuilder carvers(String carvers) {
		this.carvers = carvers;
		return this;
	}

	public BiomeTypeBuilder carver(String carver) {
		if (this.carvers == null && this.carver != null) throw new NullPointerException("Cannot register carver as carvers is null");
		this.carver = carver;
		return this;
	}

	public BiomeTypeBuilder subCarver(String subCarver) {
		if (this.carvers == null && this.subCarver != null) throw new NullPointerException("Cannot register subCarver as carvers is null");
		this.subCarver = subCarver;
		return this;
	}

	public BiomeTypeBuilder features(String features) {
		this.features = features;
		return this;
	}

	public BiomeTypeBuilder feature(String feature) {
		if (this.features == null && this.feature != null) throw new NullPointerException("Cannot register feature as features is null");
		this.feature = feature;
		return this;
	}

	public BiomeTypeBuilder starts(String starts) {
		this.starts = starts;
		return this;
	}

	public BiomeTypeBuilder start(String start) {
		if (this.starts == null && this.start != null) throw new NullPointerException("Cannot register start as starts is null");
		this.start = start;
		return this;
	}

	public BiomeTypeBuilder spawners(String spawners) {
		this.spawners = spawners;
		return this;
	}

	public BiomeTypeBuilder creature(String creature) {
		if (this.spawners == null && this.creature != null) throw new NullPointerException("creature cannot be registered, as spawners is null");
		else {
			this.creature = creature;
			return this;
		}
	}

	public BiomeTypeBuilder monster(String monster) {
		if (this.spawners == null && this.monster != null) throw new NullPointerException("monster cannot be registered, as spawners is null");
		else {
			this.monster = monster;
			return this;
		}
	}

	public BiomeTypeBuilder subCreature(EntityType<?> subCreature) {
		if (this.spawners == null && this.subCreature != null) throw new NullPointerException("subCreature cannot be registered, as spawners is null");
		else {
			this.subCreature = subCreature;
			return this;
		}
	}

	public BiomeTypeBuilder subMonster(EntityType<?> subMonster) {
		if (this.spawners == null && this.subMonster != null) throw new NullPointerException("subMonster cannot be registered, as spawners is null");
		else {
			this.subMonster = subMonster;
			return this;
		}
	}

	public BiomeTypeBuilder type(ResourceLocation type) {
		if (this.spawners == null && this.type != null) throw new NullPointerException("type cannot be registered, as spawners is null");
		else {
			this.type = type;
			return this;
		}
	}

	public BiomeTypeBuilder weight(int weight) {
		if (this.spawners == null && this.weight != null) throw new NullPointerException("weight cannot be registered, as spawners is null");
		else {
			this.weight = weight;
			return this;
		}
	}

	public BiomeTypeBuilder minCount(int minCount) {
		if (this.spawners == null && this.minCount != null) throw new NullPointerException("minCount cannot be registered, as spawners is null");
		else {
			this.minCount = minCount;
			return this;
		}
	}

	public BiomeTypeBuilder maxCount(int maxCount) {
		if (this.spawners == null && this.maxCount != null) throw new NullPointerException("maxCount cannot be registered, as spawners is null");
		else {
			this.maxCount = maxCount;
			return this;
		}
	}

	public BiomeTypeBuilder spawnCosts(String spawnCosts) {
		this.spawnCosts = spawnCosts;
		return this;
	}

	JsonObject serialize() {
		JsonObject json = new JsonObject();
		JsonElement effectsArray = new JsonObject();

		json.addProperty("player_spawn_friendly", playerSpawnFriendly);
		json.addProperty("precipitation", precipitation);
		json.addProperty("temperature", temperature);
		json.addProperty("downfall", downfall);
		json.addProperty("category", category);
		json.addProperty("depth", depth);
		json.addProperty("scale", scale);

		if (this.moodSound != null && this.moodSound.equals("mood_sound")) {
			JsonElement moodSoundArray = new JsonArray();

			if (sound != null) moodSoundArray.getAsJsonObject().addProperty("sound", sound.getPath());
			if (tickDelay != null) moodSoundArray.getAsJsonObject().addProperty("tick_delay", tickDelay);
			if (blockSearchExtent != null) moodSoundArray.getAsJsonObject().addProperty("block_search_extent", blockSearchExtent);
			if (offset != null) moodSoundArray.getAsJsonObject().addProperty("offset", offset);

			if (this.moodSound != null && !this.moodSound.equals("mood_sound")) throw new RuntimeException("moodSound property must be named 'mood_sound'");

			effectsArray.getAsJsonObject().add("mood_sound", moodSoundArray);
		}

		if (skyColor != null) effectsArray.getAsJsonObject().addProperty("sky_color", skyColor);
		if (fogColor != null) effectsArray.getAsJsonObject().addProperty("fog_color", fogColor);
		if (waterColor != null) effectsArray.getAsJsonObject().addProperty("water_color", waterColor);
		if (waterFogColor != null) effectsArray.getAsJsonObject().addProperty("water_fog_color", waterFogColor);
		if (grassColor != null) effectsArray.getAsJsonObject().addProperty("grass_color", grassColor);
		if (foliageColor != null) effectsArray.getAsJsonObject().addProperty("foliage_color", foliageColor);

		if (this.effects != null && !this.effects.equals("effects")) throw new RuntimeException("effects property must be named 'effects'");

		json.add("effects", effectsArray);

		json.addProperty("surface_builder", surfaceBuilder);

		if (carvers != null && carvers.equals("carvers")) {
			JsonElement carversArray = new JsonObject();
			JsonElement carverArray = new JsonArray();

			// for (carvers = "carvers"; carver != null;
			// carversArray.getAsJsonArray().add(carver));

			if (carver != null) {
				carversArray.getAsJsonObject().add(carver, carverArray);
				if (subCarver != null) carverArray.getAsJsonArray().add(subCarver);
			}

			json.add("carvers", carversArray);
		}

		if (features != null && features.equals("features")) {
			JsonElement featuresArray = new JsonArray();

			// for (features = "features", feature != null;
			// carversArray.getAsJsonArray().add(carver))

			if (feature != null) featuresArray.getAsJsonArray().add(feature);

			json.add("features", featuresArray);
		}

		if (starts != null && starts.equals("starts")) {
			JsonElement startsArray = new JsonArray();

			if (start != null) startsArray.getAsJsonArray().add(start);

			json.add("starts", startsArray);
		}

		if (spawners != null && spawners.equals("spawners")) {
			JsonElement spawnersArray = new JsonObject();
			JsonElement monstersArray = new JsonArray();
			JsonElement creaturesArray = new JsonArray();

			if (monster != null) spawnersArray.getAsJsonObject().add("monster", monstersArray);

			if (creature != null) {
				spawnersArray.getAsJsonObject().add("creature", creaturesArray);
				if (subCreature != null) creaturesArray.getAsJsonArray().add(subCreature.getRegistryName().getPath());
			}

			json.add("spawners", spawnersArray);
		}

		if (spawnCosts != null && spawnCosts.equals("spawn_costs")) {
			JsonElement spawnCostsArray = new JsonObject();

			json.add("spawn_costs", spawnCostsArray);
		}

		return json;
	}
}
