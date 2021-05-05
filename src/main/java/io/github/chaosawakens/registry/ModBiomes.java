package io.github.chaosawakens.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomes {
	
	public static final RegistryKey<Biome> MINING_BIOME = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation( ChaosAwakens.MODID, "mining_biome"));
	
	public static final Type TYPE_MINING_BIOME = BiomeDictionary.Type.getType("MINING");
	
	@SubscribeEvent
	public static void setupBiomes(FMLCommonSetupEvent event) {
		ChaosAwakens.LOGGER.debug(MINING_BIOME);
		event.enqueueWork( () -> {
			BiomeDictionary.addTypes(MINING_BIOME, Type.HILLS);
			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(MINING_BIOME, 1500));
		});
	}
}
