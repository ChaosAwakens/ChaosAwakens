package io.github.chaosawakens.api;

import java.util.function.Supplier;

import net.minecraft.world.gen.feature.ConfiguredFeature;

/**
 * Wrapper for a feature's data ig
 * @author invalid2
 */
public class FeatureWrapper {
	private String identifier;
	private Supplier<ConfiguredFeature<?, ?>> featureType;
	/**
	 * WARNING: DOESN'T ACTUALLY CREATE A NEW THREAD, ONLY USED
	 * FOR BEING A FUNC INTERFACE THAT TAKES NO PARAMS AND RETURNS NOTHING
	 */
	private Runnable setFeatureValue;
	/**
	 * WARNING: this Runnable is not used to create a new thread
	 */
	public FeatureWrapper(String identifier, Supplier<ConfiguredFeature<?, ?>> featureType, Runnable setFeatureValue) {
		this.identifier = identifier;
		this.featureType = featureType;
		this.setFeatureValue = setFeatureValue;
	}
	
	public String getIdentifier() { return identifier; }
	public Supplier<ConfiguredFeature<?, ?>> getFeatureType() { return featureType; }
	public Runnable getSetFeatureValue() { return setFeatureValue; }
}
