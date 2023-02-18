package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.worldgen.carver.CrystalCanyonWorldCarver;
import io.github.chaosawakens.common.worldgen.carver.CrystalCaveWorldCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CACarvers {
	public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, ChaosAwakens.MODID);
	
	public static final RegistryObject<CrystalCaveWorldCarver> CRYSTAL_CAVE = CARVERS.register("crystal_cave", () -> new CrystalCaveWorldCarver(ProbabilityConfig.CODEC, 256));
	public static final RegistryObject<CrystalCanyonWorldCarver> CRYSTAL_CANYON = CARVERS.register("crystal_canyon", () -> new CrystalCanyonWorldCarver(ProbabilityConfig.CODEC));
}
