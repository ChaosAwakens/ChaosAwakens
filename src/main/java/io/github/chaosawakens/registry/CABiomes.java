package io.github.chaosawakens.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CABiomes {
	
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, ChaosAwakens.MODID);
	
	public static RegistryObject<Biome> MINING_BIOME = BIOMES.register("mining_biome", BiomeMaker::makeVoidBiome);
	public static RegistryObject<Biome> VILLAGE_PLAINS = BIOMES.register("village_plains", BiomeMaker::makeVoidBiome);
	public static RegistryObject<Biome> DANGER_ISLANDS = BIOMES.register("danger_islands", BiomeMaker::makeVoidBiome);
}
