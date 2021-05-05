package io.github.chaosawakens.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDimensions {
	
	public static final RegistryKey<World> MINING_DIMENSION = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(ChaosAwakens.MODID + ":mining_dimension"));
	public static final RegistryKey<World> VILLAGE_MANIA = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(ChaosAwakens.MODID + ":village_mania"));
	public static final RegistryKey<World> DANGER_ISLANDS = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(ChaosAwakens.MODID + ":danger_islands"));
}