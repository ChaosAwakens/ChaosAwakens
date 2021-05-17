package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CABiomes {
	
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, ChaosAwakens.MODID);
	
	public static RegistryObject<Biome> MINING_BIOME = BIOMES.register("mining_biome", BiomeMaker::makeVoidBiome);
	public static RegistryObject<Biome> VILLAGE_PLAINS = BIOMES.register("village_plains", BiomeMaker::makeVoidBiome);
	public static RegistryObject<Biome> DANGER_ISLANDS = BIOMES.register("danger_islands", BiomeMaker::makeVoidBiome);
	
	
	public static final class Type {
		public static final BiomeDictionary.Type MINING_DIMENSION = BiomeDictionary.Type.getType("MINING");
		public static final BiomeDictionary.Type VILLAGER_DIMENSION = BiomeDictionary.Type.getType("VILLAGER");
		public static final BiomeDictionary.Type DANGER_DIMENSION = BiomeDictionary.Type.getType("DANGER");
	}	
}
