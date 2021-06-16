package io.github.chaosawakens.api;

import net.minecraft.world.gen.feature.ConfiguredFeature;

/**
 * Wrapper class containing a ConfiguredFeature and its id
 * @author invalid2
 */
public class FeatureWrapper {
	private String identifier;
	private ConfiguredFeature<?, ?> featureType;
	
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
