package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.api.wrapper.CarverWrapper;
import io.github.chaosawakens.common.events.CACommonSetupEvents;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CAConfiguredCarvers {
	public static final ConfiguredCarver<?> CRYSTAL_CAVE = registerCarver("crystal_cave", CACarvers.CRYSTAL_CAVE.get().configured(new ProbabilityConfig(0.14285715F)));
	public static final ConfiguredCarver<?> CRYSTAL_CANYON = registerCarver("crystal_canyon", CACarvers.CRYSTAL_CANYON.get().configured(new ProbabilityConfig(0.02F)));
	
	private static <C extends ICarverConfig> ConfiguredCarver<C> registerCarver(String identifier, ConfiguredCarver<C> carver) {
		CACommonSetupEvents.CONFIG_CARVERS.add(new CarverWrapper(identifier, carver));
		return carver;
	}
}
