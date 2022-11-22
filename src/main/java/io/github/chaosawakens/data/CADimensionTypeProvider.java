package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;

import io.github.chaosawakens.common.registry.CADimensions;
import net.minecraft.data.DataGenerator;

public class CADimensionTypeProvider extends DimensionTypeProvider {
	public CADimensionTypeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + ": Dimension Types";
	}

	@Override
	protected void addDimensionTypes() {
		createDimensionType(CADimensions.CRYSTAL_WORLD.location())
				.ultrawarm(false)
				.natural(true)
				.piglinSafe(false)
				.respawnAnchorWorks(false)
				.bedWorks(true)
				.hasRaids(false)
				.hasSkylight(true)
				.hasCeiling(false)
				.coordinateScale(1)
				.ambientLight(0)
				.logicalHeight(256)
				.effects("minecraft:overworld")
				.infiniburn("minecraft:infiniburn_overworld");
		createDimensionType(CADimensions.MINING_PARADISE.location())
				.ultrawarm(false)
				.natural(true)
				.piglinSafe(false)
				.respawnAnchorWorks(false)
				.bedWorks(true)
				.hasRaids(false)
				.hasSkylight(true)
				.hasCeiling(false)
				.coordinateScale(1)
				.ambientLight(0)
				.logicalHeight(256)
				.effects("minecraft:overworld")
				.infiniburn("minecraft:infiniburn_overworld");
		createDimensionType(CADimensions.DANGER_ISLES.location())
				.ultrawarm(false)
				.natural(true)
				.piglinSafe(true)
				.respawnAnchorWorks(false)
				.bedWorks(false)
				.hasRaids(false)
				.hasSkylight(false)
				.hasCeiling(false)
				.coordinateScale(1)
				.ambientLight(0)
				.logicalHeight(256)
				.effects("minecraft:overworld")
				.infiniburn("minecraft:infiniburn_overworld");
		createDimensionType(CADimensions.VILLAGE_MANIA.location())
				.ultrawarm(false)
				.natural(true)
				.piglinSafe(false)
				.respawnAnchorWorks(false)
				.bedWorks(true)
				.hasRaids(true)
				.hasSkylight(true)
				.hasCeiling(false)
				.coordinateScale(1)
				.ambientLight(0)
				.logicalHeight(256)
				.effects("minecraft:overworld")
				.infiniburn("minecraft:infiniburn_overworld");
	}
}
