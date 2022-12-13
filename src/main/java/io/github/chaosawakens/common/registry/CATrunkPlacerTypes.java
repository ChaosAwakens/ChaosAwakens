package io.github.chaosawakens.common.registry;

import com.mojang.serialization.Codec;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.worldgen.trunkplacer.CrystalTrunkPlacer;
import io.github.chaosawakens.common.worldgen.trunkplacer.GiantGinkgoTrunkPlacer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CATrunkPlacerTypes {
	
	public static final TrunkPlacerType<CrystalTrunkPlacer> CRYSTAL_TRUNK_PLACER = register("crystal_trunk_placer", CrystalTrunkPlacer.CODEC);
	public static final TrunkPlacerType<GiantGinkgoTrunkPlacer> GIANT_GINKGO_TRUNK_PLACER = register("giant_ginkgo_trunk_placer", GiantGinkgoTrunkPlacer.CODEC);
	
	
	private static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> register(String regName, Codec<P> codec) {		   	
		return Registry.register(Registry.TRUNK_PLACER_TYPES, regName, new TrunkPlacerType<>(codec));	
	}

}
