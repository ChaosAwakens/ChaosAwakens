package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.api.CarverWrapper;
import io.github.chaosawakens.common.events.CommonSetupEvent;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CAConfiguredCarvers {
	
	public static final ConfiguredCarver<?> CRYSTAL_CAVE = register("crystal_cave", CACarvers.CRYSTAL_CAVE.get().configured(new ProbabilityConfig(0.14285715F)));
	public static final ConfiguredCarver<?> CRYSTAL_CANYON = register("crystal_canyon", CACarvers.CRYSTAL_CANYON.get().configured(new ProbabilityConfig(0.02F)));
	
	private static <C extends ICarverConfig> ConfiguredCarver<C> register(String identifier, ConfiguredCarver<C> carver) {
		CommonSetupEvent.configCarvers.add(new CarverWrapper(identifier, carver));
		return carver;
	}
}
