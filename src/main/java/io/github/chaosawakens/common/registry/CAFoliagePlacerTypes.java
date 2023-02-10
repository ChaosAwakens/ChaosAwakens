package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.worldgen.foliageplacer.ConiferousFoliagePlacer;
import io.github.chaosawakens.common.worldgen.foliageplacer.CubicSkipFoliagePlacer;
import io.github.chaosawakens.common.worldgen.foliageplacer.SpheroidFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAFoliagePlacerTypes {
	
	public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, ChaosAwakens.MODID);
	
	public static final RegistryObject<FoliagePlacerType<SpheroidFoliagePlacer>> SPHEROID_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("spheroid_foliage_placer", () -> new FoliagePlacerType<>(SpheroidFoliagePlacer.CODEC));
	public static final RegistryObject<FoliagePlacerType<CubicSkipFoliagePlacer>> CUBIC_SKIP_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("cubic_skip_foliage_placer", () -> new FoliagePlacerType<>(CubicSkipFoliagePlacer.CODEC));
	public static final RegistryObject<FoliagePlacerType<ConiferousFoliagePlacer>> CONIFEROUS_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("coniferous_foliage_placer", () -> new FoliagePlacerType<>(ConiferousFoliagePlacer.CODEC));
}
