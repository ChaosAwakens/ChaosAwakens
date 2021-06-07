package io.github.chaosawakens.api;

import net.minecraft.world.gen.feature.ConfiguredFeature;

/**
 * Wrapper for a feature's data ig
 * @author invalid2
 */
public class FeatureWrapper {
	private String identifier;
	private ConfiguredFeature<?, ?> featureType;
	
	public FeatureWrapper(String identifier, ConfiguredFeature<?, ?> featureType) {
		this.identifier = identifier;
		this.featureType = featureType;
	}
	
	public String getIdentifier() { return identifier; }
	public ConfiguredFeature<?, ?> getFeatureType() { return featureType; }
}
