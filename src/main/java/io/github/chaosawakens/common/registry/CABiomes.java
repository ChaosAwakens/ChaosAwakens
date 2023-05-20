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

	public static RegistryObject<Biome> DENSE_PLAINS = registerVoidBiome("dense_plains");
	public static RegistryObject<Biome> DENSE_FOREST = registerVoidBiome("dense_forest");
	public static RegistryObject<Biome> DENSE_GINKGO_FOREST = registerVoidBiome("dense_ginkgo_forest");
	public static RegistryObject<Biome> DENSE_MOUNTAINS = registerVoidBiome("dense_mountains");
	public static RegistryObject<Biome> STALAGMITE_VALLEY = registerVoidBiome("stalagmite_valley");
	public static RegistryObject<Biome> MESOZOIC_JUNGLE = registerVoidBiome("mesozoic_jungle");
	public static RegistryObject<Biome> VILLAGE_PLAINS = registerVoidBiome("village_plains");
	public static RegistryObject<Biome> VILLAGE_SAVANNA = registerVoidBiome("village_savanna");
	public static RegistryObject<Biome> VILLAGE_TAIGA = registerVoidBiome("village_taiga");
	public static RegistryObject<Biome> VILLAGE_SNOWY = registerVoidBiome("village_snowy");
	public static RegistryObject<Biome> VILLAGE_DESERT = registerVoidBiome("village_desert");
	public static RegistryObject<Biome> DANGER_ISLANDS = registerVoidBiome("danger_islands");
	public static RegistryObject<Biome> CRYSTAL_PLAINS = registerVoidBiome("crystal_plains");
	public static RegistryObject<Biome> CRYSTAL_HILLS = registerVoidBiome("crystal_hills");
	
	public static RegistryObject<Biome> registerVoidBiome(String regName) {
		RegistryObject<Biome> regBiome = BIOMES.register(regName, BiomeMaker::theVoidBiome);
		return regBiome;
	}

	public static final class Type {
		public static final BiomeDictionary.Type MINING_PARADISE = BiomeDictionary.Type.getType("MINING_PARADISE");
		public static final BiomeDictionary.Type DENSE_PLAINS = BiomeDictionary.Type.getType("DENSE_PLAINS");
		public static final BiomeDictionary.Type DENSE_FOREST = BiomeDictionary.Type.getType("DENSE_FOREST");
		public static final BiomeDictionary.Type DENSE_GINKGO_FOREST = BiomeDictionary.Type.getType("DENSE_GINKGO_FOREST");
		public static final BiomeDictionary.Type DENSE_MOUNTAINS = BiomeDictionary.Type.getType("DENSE_MOUNTAINS");
		public static final BiomeDictionary.Type STALAGMITE_VALLEY = BiomeDictionary.Type.getType("STALAGMITE_VALLEY");
		public static final BiomeDictionary.Type MESOZOIC_JUNGLE = BiomeDictionary.Type.getType("MESOZOIC_JUNGLE");

		public static final BiomeDictionary.Type VILLAGE_MANIA = BiomeDictionary.Type.getType("VILLAGE_MANIA");
		public static final BiomeDictionary.Type VILLAGE_PLAINS = BiomeDictionary.Type.getType("VILLAGE_PLAINS");
		public static final BiomeDictionary.Type VILLAGE_SAVANNA = BiomeDictionary.Type.getType("VILLAGE_SAVANNA");
		public static final BiomeDictionary.Type VILLAGE_TAIGA = BiomeDictionary.Type.getType("VILLAGE_TAIGA");
		public static final BiomeDictionary.Type VILLAGE_SNOWY = BiomeDictionary.Type.getType("VILLAGE_SNOWY");
		public static final BiomeDictionary.Type VILLAGE_DESERT = BiomeDictionary.Type.getType("VILLAGE_DESERT");

		public static final BiomeDictionary.Type DANGER_ISLES = BiomeDictionary.Type.getType("DANGER_ISLES");
		public static final BiomeDictionary.Type CRYSTAL_WORLD = BiomeDictionary.Type.getType("CRYSTAL_WORLD");
	}
}
