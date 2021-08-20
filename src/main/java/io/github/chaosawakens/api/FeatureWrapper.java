package io.github.chaosawakens.api;

import net.minecraft.world.gen.feature.ConfiguredFeature;

/**
 * Wrapper POJO containing a ConfiguredFeature and its id
 * @author invalid2
 */
public class FeatureWrapper {
	private final String identifier;
	private final ConfiguredFeature<?, ?> featureType;

	public FeatureWrapper(String identifier, ConfiguredFeature<?, ?> featureType) {
		this.identifier = identifier;
		this.featureType = featureType;
	}

	@Override
	public String toString() {
		return identifier + ":" + featureType.toString();
	}
	public String getIdentifier() { return identifier; }
	public ConfiguredFeature<?, ?> getFeatureType() { return featureType; }
}
