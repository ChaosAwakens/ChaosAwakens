package io.github.chaosawakens.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {
	
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, ChaosAwakens.MODID);
	
	static {
		BIOMES.register("mining_biome", BiomeMaker::makeVoidBiome);
		BIOMES.register("village_plains", BiomeMaker::makeVoidBiome);
		BIOMES.register("danger_islands", BiomeMaker::makeVoidBiome);
	}
}
