package io.github.chaosawakens.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;

//Finally, more complex json file generation (Brought to you by Meme Man)
public class BiomeTypeBuilder {
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
	private String features;
	private String starts;
	private String spawners;
	private String creature;
	private ResourceLocation type;
	private Integer weight;
	private Integer minCount;
	private Integer maxCount;
	private String spawnCosts;
	
	BiomeTypeBuilder(ResourceLocation id) {
		this.id = id;
	}
	
	public BiomeTypeBuilder playerSpawnFriendly(boolean playerSpawnFriendly) {
		if (this.playerSpawnFriendly) {
			throw new RuntimeException("playerSpawnFriendly has already been set, remove unnecessary/duplicate call.");
		} else {
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
		if (this.effects == null) {
			throw new NullPointerException("effects cannot be null");
		} else {
			this.effects = effects;
			return this;
		}
	}
	
	public BiomeTypeBuilder moodSound(String moodSound) {
		this.moodSound = moodSound;
		return this;
	}
	
	public BiomeTypeBuilder sound(ResourceLocation sound) {
		if (this.moodSound == null && this.sound != null) {
			throw new NullPointerException("sound cannot be registered, as moodSound is null");
		} else {
			this.sound = sound;
			return this;
		}
	}
	
	public BiomeTypeBuilder tickDelay(int tickDelay) {
		if (this.moodSound == null && this.tickDelay != null) {
			throw new NullPointerException("tickDelay cannot be registered, as moodSound is null");
		} else {
			this.tickDelay = tickDelay;
			return this;
		}
	}
	
	public BiomeTypeBuilder blockSearchExtent(int blockSearchExtent) {
		if (this.moodSound == null && this.blockSearchExtent != null) {
			throw new NullPointerException("blockSearchExtent cannot be registered, as moodSound is null");
		} else {
			this.blockSearchExtent = blockSearchExtent;
			return this;
		}
	}
	
	public BiomeTypeBuilder offset(double offset) {
		if (this.moodSound == null && this.offset != null) {
			throw new NullPointerException("offset cannot be registered, as moodSound is null");
		} else {
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
	
	public BiomeTypeBuilder features(String features) {
		this.features = features;
		return this;
	}
	
	public BiomeTypeBuilder starts(String starts) {
		this.starts = starts;
		return this;
	}
	
	public BiomeTypeBuilder spawners(String spawners) {
		this.spawners = spawners;
		return this;
	}
	
	public BiomeTypeBuilder creature(String creature) {
		if (this.spawners == null && this.creature != null) {
			throw new NullPointerException("creature cannot be registered, as spawners is null");
		} else {
			this.creature = creature;
			return this;
		}
	}
	
	public BiomeTypeBuilder type(ResourceLocation type) {
		if (this.spawners == null && this.type != null) {
			throw new NullPointerException("type cannot be registered, as spawners is null");
		} else {
			this.type = type;
			return this;
		}
	}
	
	public BiomeTypeBuilder weight(int weight) {
		if (this.spawners == null && this.weight != null) {
			throw new NullPointerException("weight cannot be registered, as spawners is null");
		} else {
			this.weight = weight;
			return this;
		}
	}
	
	public BiomeTypeBuilder minCount(int minCount) {
		if (this.spawners == null && this.minCount != null) {
			throw new NullPointerException("minCount cannot be registered, as spawners is null");
		} else {
			this.minCount = minCount;
			return this;
		}
	}
	
	public BiomeTypeBuilder maxCount(int maxCount) {
		if (this.spawners == null && this.maxCount != null) {
			throw new NullPointerException("maxCount cannot be registered, as spawners is null");
		} else {
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
		JsonArray effectsArray = new JsonArray();
		
		json.addProperty("player_spawn_friendly", playerSpawnFriendly);
		json.addProperty("precipitation", precipitation);
		json.addProperty("temperature", temperature);
		json.addProperty("category", category);
		json.addProperty("depth", depth);
		json.addProperty("scale", scale);
		
		if (this.moodSound != null) {
			JsonArray moodSoundArray = new JsonArray();
			effectsArray.add(moodSoundArray);
			
			if (sound != null) moodSoundArray.add(sound.getPath());
			if (tickDelay != null) moodSoundArray.add(tickDelay);
			if (blockSearchExtent != null) moodSoundArray.add(blockSearchExtent);
			if (offset != null) moodSoundArray.add(offset);
		}
		
		json.add("effects", effectsArray);
		return json;
	}
	
}
