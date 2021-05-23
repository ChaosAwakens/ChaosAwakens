/**
 * 
 */
package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;

/**
 * @author invalid2
 */
public class GeodeFeatureConfig implements IFeatureConfig {
	public static final Codec<GeodeFeatureConfig> CODEC = RecordCodecBuilder.create( (builder) -> {
		return builder.group(RuleTest.CODEC.fieldOf("target").forGetter( (config) -> {
			return config.target;
		}), BlockState.CODEC.fieldOf("budState").forGetter( (config) -> {
			return config.budState;
		}), BlockState.CODEC.fieldOf("clusterState").forGetter( (config) -> {
			return config.clusterState;
		}), Codec.intRange(0, 64).fieldOf("lowerBound").forGetter( (config) -> {
			return config.frequency;
		}), Codec.intRange(0, 64).fieldOf("upperBound").forGetter( (config) -> {
			return config.frequency;
		}), Codec.intRange(0, 64).fieldOf("frequency").forGetter( (config) -> {
			return config.frequency;
		})).apply(builder, GeodeFeatureConfig::new);
		});
	
	public final RuleTest target;
	public final BlockState budState;
	public final BlockState clusterState;
	public final int lowerBound;
	public final int upperBound;
	public final int frequency;

	public GeodeFeatureConfig(RuleTest target, BlockState budState, BlockState clusterState, int lowerBound, int upperBound, int frequency) {
		super();
		this.target = target;
		this.budState = budState;
		this.clusterState = clusterState;
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
		this.frequency = frequency;
	}
}
