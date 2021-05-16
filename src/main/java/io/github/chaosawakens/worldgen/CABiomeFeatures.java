package io.github.chaosawakens.worldgen;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.CABiomes;
import io.github.chaosawakens.registry.CAEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID)
public class CABiomeFeatures {
	
	//Mobs that appear on any biome, but only on the overworld
	private static final Consumer<MobSpawnInfoBuilder> OVERWORLD_MOBS = (builder) -> {
		builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.BROWN_ANT.get(), 20, 1, 5));
		builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RAINBOW_ANT.get(), 20, 1, 5));
		builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RED_ANT.get(), 20, 1, 5));
		builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.UNSTABLE_ANT.get(), 20, 1, 5));
		builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.TERMITE.get(), 20, 1, 5));
	};
	
	private static final Consumer<MobSpawnInfoBuilder> SWAMP_MOBS = (builder) -> {
		builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.RUBY_BUG.get(), 25, 3, 6));
		builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.EMERALD_GATOR.get(), 3, 1, 2));
	};
	
	private static final Consumer<MobSpawnInfoBuilder> FOREST_MOBS = (builder) -> {
		builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(CAEntityTypes.BEAVER.get(), 15, 1, 2));
	};
	
	public static void addMobSpawns(BiomeLoadingEvent event) {
		MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();
		
		switch (event.getCategory()) {
			case SWAMP:
				SWAMP_MOBS.accept(spawnInfoBuilder);
			case FOREST:
				FOREST_MOBS.accept(spawnInfoBuilder);				
			case THEEND:
			case NETHER:
				break;
			default:
				OVERWORLD_MOBS.accept(spawnInfoBuilder);
				break;
		}
	}
	
	public static void addStructureSpawns(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder builder = event.getGeneration();
		switch(event.getCategory()) {
			case FOREST:
				builder.withStructure(ConfiguredStructures.CONFIGURED_ENT_DUNGEON);
			default:
				break;
		}
	}
}