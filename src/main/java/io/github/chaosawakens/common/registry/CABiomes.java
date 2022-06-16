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

	public static RegistryObject<Biome> DENSE_MOUNTAIN = BIOMES.register("dense_mountain", BiomeMaker::theVoidBiome);
	public static RegistryObject<Biome> STALAGMITE_VALLEY = BIOMES.register("stalagmite_valley", BiomeMaker::theVoidBiome);
	public static RegistryObject<Biome> VILLAGE_PLAINS = BIOMES.register("village_plains", BiomeMaker::theVoidBiome);
	public static RegistryObject<Biome> VILLAGE_SAVANNA = BIOMES.register("village_savanna", BiomeMaker::theVoidBiome);
	public static RegistryObject<Biome> VILLAGE_TAIGA = BIOMES.register("village_taiga", BiomeMaker::theVoidBiome);
	public static RegistryObject<Biome> VILLAGE_SNOWY = BIOMES.register("village_snowy", BiomeMaker::theVoidBiome);
	public static RegistryObject<Biome> VILLAGE_DESERT = BIOMES.register("village_desert", BiomeMaker::theVoidBiome);
	public static RegistryObject<Biome> DANGER_ISLANDS = BIOMES.register("danger_islands", BiomeMaker::theVoidBiome);
	public static RegistryObject<Biome> CRYSTAL_PLAINS = BIOMES.register("crystal_plains", BiomeMaker::theVoidBiome);
	public static RegistryObject<Biome> CRYSTAL_HILLS = BIOMES.register("crystal_hills", BiomeMaker::theVoidBiome);

	public static final class Type {
		public static final BiomeDictionary.Type MINING_PARADISE = BiomeDictionary.Type.getType("MINING_PARADISE");
		public static final BiomeDictionary.Type DENSE_MOUNTAIN = BiomeDictionary.Type.getType("DENSE_MOUNTAIN");
		public static final BiomeDictionary.Type STALAGMITE_VALLEY = BiomeDictionary.Type.getType("STALAGMITE_VALLEY");

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
