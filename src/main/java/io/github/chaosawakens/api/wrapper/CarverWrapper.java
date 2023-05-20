package io.github.chaosawakens.api.wrapper;

import net.minecraft.world.gen.carver.ConfiguredCarver;

public class CarverWrapper {
	private final String identifier;
	private final ConfiguredCarver<?> carver;
	
	public CarverWrapper(String identifier, ConfiguredCarver<?> carver) {
		this.identifier = identifier;
		this.carver = carver;
	}

	public String getIdentifier() {
		return identifier;
	}

	public ConfiguredCarver<?> getCarver() {
		return carver;
	}
}
