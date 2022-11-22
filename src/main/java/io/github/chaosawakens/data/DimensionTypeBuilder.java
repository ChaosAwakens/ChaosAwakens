package io.github.chaosawakens.data;

import com.google.gson.JsonObject;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.ResourceLocation;

public class DimensionTypeBuilder {
	private final ResourceLocation id;
	private boolean ultrawarm;
	private boolean natural;
	private boolean piglinSafe;
	private boolean respawnAnchorWorks;
	private boolean bedWorks;
	private boolean hasRaids;
	private boolean hasSkylight;
	private boolean hasCeiling;
	private Float coordinateScale;
	private Float ambientLight;
	private Integer fixedTime;
	private Integer logicalHeight;
	private String effects;
	private String infiniburn;

	DimensionTypeBuilder(ResourceLocation id) {
		this.id = id;
	}

	public DimensionTypeBuilder ultrawarm(boolean ultrawarm) {
		if (this.ultrawarm) throw new RuntimeException("ultrawarm has already been set, remove unnecessary call.");
		else {
			this.ultrawarm = ultrawarm;
			return this;
		}
	}

	public DimensionTypeBuilder natural(boolean natural) {
		if (this.natural) throw new RuntimeException("natural has already been set, remove unnecessary call.");
		else {
			this.natural = natural;
			return this;
		}
	}

	public DimensionTypeBuilder piglinSafe(boolean piglinSafe) {
		if (this.piglinSafe) throw new RuntimeException("piglinSafe has already been set, remove unnecessary call.");
		else {
			this.piglinSafe = piglinSafe;
			return this;
		}
	}

	public DimensionTypeBuilder respawnAnchorWorks(boolean respawnAnchorWorks) {
		if (this.respawnAnchorWorks) throw new RuntimeException("respawnAnchorWorks has already been set, remove unnecessary call.");
		else {
			this.respawnAnchorWorks = respawnAnchorWorks;
			return this;
		}
	}

	public DimensionTypeBuilder bedWorks(boolean bedWorks) {
		if (this.bedWorks) throw new RuntimeException("bedWorks has already been set, remove unnecessary call.");
		else {
			this.bedWorks = bedWorks;
			return this;
		}
	}

	public DimensionTypeBuilder hasRaids(boolean hasRaids) {
		if (this.hasRaids) throw new RuntimeException("hasRaids has already been set, remove unnecessary call.");
		else {
			this.hasRaids = hasRaids;
			return this;
		}
	}

	public DimensionTypeBuilder hasSkylight(boolean hasSkylight) {
		if (this.hasSkylight) throw new RuntimeException("hasSkylight has already been set, remove unnecessary call.");
		else {
			this.hasSkylight = hasSkylight;
			return this;
		}
	}

	public DimensionTypeBuilder hasCeiling(boolean hasCeiling) {
		if (this.hasCeiling) throw new RuntimeException("hasCeiling has already been set, remove unnecessary call.");
		else {
			this.hasCeiling = hasCeiling;
			return this;
		}
	}

	public DimensionTypeBuilder coordinateScale(float coordinateScale) {
		this.coordinateScale = coordinateScale;
		return this;
	}

	public DimensionTypeBuilder ambientLight(float ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	public DimensionTypeBuilder fixedTime(int fixedTime) {
		this.fixedTime = fixedTime;
		return this;
	}

	public DimensionTypeBuilder logicalHeight(int logicalHeight) {
		this.logicalHeight = logicalHeight;
		return this;
	}

	public DimensionTypeBuilder effects(String effects) {
		this.effects = effects;
		return this;
	}

	public DimensionTypeBuilder infiniburn(String infiniburn) {
		this.infiniburn = infiniburn;
		return this;
	}

	JsonObject serialize() {
		JsonObject json = new JsonObject();
		json.addProperty("name", this.id.getPath());
		json.addProperty("ultrawarm", this.ultrawarm);
		json.addProperty("natural", this.natural);
		json.addProperty("piglin_safe", this.piglinSafe);
		json.addProperty("respawn_anchor_works", this.respawnAnchorWorks);
		json.addProperty("bed_works", this.bedWorks);
		json.addProperty("has_raids", this.hasRaids);
		json.addProperty("has_skylight", this.hasSkylight);
		json.addProperty("has_ceiling", this.hasCeiling);
		json.addProperty("coordinate_scale", this.coordinateScale);
		json.addProperty("ambient_light", this.ambientLight);
		if (this.fixedTime != null) json.addProperty("fixed_time", this.fixedTime);
		json.addProperty("logical_height", this.logicalHeight);
		json.addProperty("effects", this.effects);
		json.addProperty("infiniburn", this.infiniburn);

		ChaosAwakens.LOGGER.info("=--+--+--+--+--+--+--+--+--+--+--+--=");
		ChaosAwakens.LOGGER.debug("Dimension id: " + this.id.getPath());
		ChaosAwakens.LOGGER.debug("Ultrawarm: " + this.ultrawarm);
		ChaosAwakens.LOGGER.debug("Natural: " + this.natural);
		ChaosAwakens.LOGGER.debug("Piglin Safe: " + this.piglinSafe);
		ChaosAwakens.LOGGER.debug("Respawn Anchor Works: " + this.respawnAnchorWorks);
		ChaosAwakens.LOGGER.debug("Bed Works: " + this.bedWorks);
		ChaosAwakens.LOGGER.debug("Has Raids: " + this.hasRaids);
		ChaosAwakens.LOGGER.debug("Has Skylight: " + this.hasSkylight);
		ChaosAwakens.LOGGER.debug("Has Ceiling: " + this.hasCeiling);
		ChaosAwakens.LOGGER.debug("Coordinate Scale: " + this.coordinateScale);
		ChaosAwakens.LOGGER.debug("Ambient Light: " + this.ambientLight);
		if (this.fixedTime != null) ChaosAwakens.LOGGER.debug("Fixed Time: " + this.fixedTime);
		ChaosAwakens.LOGGER.debug("Logical Height: " + this.logicalHeight);
		ChaosAwakens.LOGGER.debug("Effects: " + this.effects);
		ChaosAwakens.LOGGER.debug("Infiniburn: " + this.infiniburn);
		ChaosAwakens.LOGGER.info("=--+--+--+--+--+--+--+--+--+--+--+--=");
		return json;
	}
}
