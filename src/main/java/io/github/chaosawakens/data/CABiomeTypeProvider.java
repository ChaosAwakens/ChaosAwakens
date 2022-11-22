package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;

import net.minecraft.data.DataGenerator;

public class CABiomeTypeProvider extends BiomeTypeProvider {

	public CABiomeTypeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + ": Biome Types";
	}

	@Override
	protected void addBiomeTypes() {
//		createBiomeType(CABiomes.CRYSTAL_PLAINS.getId())
//		.playerSpawnFriendly(true)
//		.precipitation("none")
//		.temperature(0.8)
//		.downfall(0.0)
//		.category("none")
//		.depth(0.125)
//		.scale(0.5)
//		.effects("effects")
//		.moodSound("mood_sound")
//		.sound(SoundEvents.AMBIENT_CAVE.getLocation())
//		.tickDelay(6000)
//		.blockSearchExtent(8)
//		.offset(2.0)
//		.skyColor(7907327)
//		.fogColor(12638463)
//		.waterColor(4159204)
//		.waterFogColor(329011)
//		.grassColor(353825)
//		.foliageColor(353825)
//		.surfaceBuilder("chaosawakens:crystal_world")
//		.carvers("carvers")
//		.carver("air")
//		.subCarver("minecraft:cave")
//		.subCarver("minecraft:canyon")
//		.features("features")
//		.starts("starts")
//		.spawners("spawners")
//		.creature("creature")
//		.subCreature(CAEntityTypes.CRYSTAL_APPLE_COW.get())
//		.spawnCosts("spawn_costs");
	}
}
