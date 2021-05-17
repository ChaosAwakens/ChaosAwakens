package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CADimensions {
	
	public static final RegistryKey<World> MINING_DIMENSION = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(ChaosAwakens.MODID + ":mining_dimension"));
	public static final RegistryKey<World> VILLAGE_MANIA = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(ChaosAwakens.MODID + ":village_mania"));
	public static final RegistryKey<World> DANGER_ISLANDS = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(ChaosAwakens.MODID + ":danger_islands"));
}