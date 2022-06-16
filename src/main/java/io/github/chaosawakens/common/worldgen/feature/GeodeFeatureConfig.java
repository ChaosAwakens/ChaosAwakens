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
	public static final Codec<GeodeFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> builder
			.group(RuleTest.CODEC.fieldOf("target").forGetter((config) -> config.target),
					BlockState.CODEC.fieldOf("budState").forGetter((config) -> config.budState),
					BlockState.CODEC.fieldOf("clusterState").forGetter((config) -> config.clusterState),
					Codec.intRange(0, 64).fieldOf("lowerBound").forGetter((config) -> config.frequency),
					Codec.intRange(0, 64).fieldOf("upperBound").forGetter((config) -> config.frequency),
					Codec.intRange(0, 64).fieldOf("frequency").forGetter((config) -> config.frequency))
			.apply(builder, GeodeFeatureConfig::new));

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
