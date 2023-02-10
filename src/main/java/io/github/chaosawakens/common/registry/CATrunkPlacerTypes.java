package io.github.chaosawakens.common.registry;

import com.mojang.serialization.Codec;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.worldgen.trunkplacer.CrystalTrunkPlacer;
import io.github.chaosawakens.common.worldgen.trunkplacer.DirtlessGiantTrunkPlacer;
import io.github.chaosawakens.common.worldgen.trunkplacer.GiantConiferTrunkPlacer;
import io.github.chaosawakens.common.worldgen.trunkplacer.ThinGiantTrunkPlacer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CATrunkPlacerTypes {
	
	public static final TrunkPlacerType<CrystalTrunkPlacer> CRYSTAL_TRUNK_PLACER = register("crystal_trunk_placer", CrystalTrunkPlacer.CODEC);
	public static final TrunkPlacerType<DirtlessGiantTrunkPlacer> DIRTLESS_GIANT_TRUNK_PLACER = register("giant_ginkgo_trunk_placer", DirtlessGiantTrunkPlacer.CODEC);
	public static final TrunkPlacerType<GiantConiferTrunkPlacer> GIANT_CONIFER_TRUNK_PLACER = register("giant_conifer_trunk_placer", GiantConiferTrunkPlacer.CODEC);
	public static final TrunkPlacerType<ThinGiantTrunkPlacer> THIN_GIANT_TRUNK_PLACER = register("thin_giant_trunk_placer", ThinGiantTrunkPlacer.CODEC);
	
	private static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> register(String regName, Codec<P> codec) {		   	
		return Registry.register(Registry.TRUNK_PLACER_TYPES, regName, new TrunkPlacerType<>(codec));	
	}

}
