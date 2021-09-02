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

    public static RegistryObject<Biome> MINING_BIOME = BIOMES.register("mining_biome", BiomeMaker::theVoidBiome);
    public static RegistryObject<Biome> STALAGMITE_VALLEY = BIOMES.register("stalagmite_valley", BiomeMaker::theVoidBiome);
    public static RegistryObject<Biome> VILLAGE_PLAINS = BIOMES.register("village_plains", BiomeMaker::theVoidBiome);
    public static RegistryObject<Biome> VILLAGE_SAVANNA = BIOMES.register("village_savanna", BiomeMaker::theVoidBiome);
    public static RegistryObject<Biome> VILLAGE_TAIGA = BIOMES.register("village_taiga", BiomeMaker::theVoidBiome);
    public static RegistryObject<Biome> VILLAGE_SNOWY = BIOMES.register("village_snowy", BiomeMaker::theVoidBiome);
    public static RegistryObject<Biome> VILLAGE_DESERT = BIOMES.register("village_desert", BiomeMaker::theVoidBiome);
    public static RegistryObject<Biome> DANGER_ISLANDS = BIOMES.register("danger_islands", BiomeMaker::theVoidBiome);
    public static RegistryObject<Biome> CRYSTAL_PLAINS = BIOMES.register("crystal_plains", BiomeMaker::theVoidBiome);

    public static final class Type {
        public static final BiomeDictionary.Type MINING_DIMENSION = BiomeDictionary.Type.getType("MINING");
        public static final BiomeDictionary.Type VILLAGE_DIMENSION = BiomeDictionary.Type.getType("VILLAGE");
        public static final BiomeDictionary.Type DANGER_DIMENSION = BiomeDictionary.Type.getType("DANGER");
        public static final BiomeDictionary.Type CRYSTAL_DIMENSION = BiomeDictionary.Type.getType("CRYSTAL");
    }
}
