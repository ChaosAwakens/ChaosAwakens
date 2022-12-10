package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.worldgen.foliageplacer.ElipticFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAFoliagePlacerTypes {
	
	public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, ChaosAwakens.MODID);
	
	public static final RegistryObject<FoliagePlacerType<ElipticFoliagePlacer>> GINKGO_FOLIAGE_TYPE = FOLIAGE_PLACER_TYPES.register("ginkgo_foliage_type", () -> new FoliagePlacerType<>(ElipticFoliagePlacer.CODEC));
	public CAFoliagePlacerTypes() {}

}
