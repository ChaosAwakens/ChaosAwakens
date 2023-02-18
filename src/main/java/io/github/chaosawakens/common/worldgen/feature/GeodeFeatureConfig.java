package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

/**
 * @author invalid2
 */
public class GeodeFeatureConfig implements IFeatureConfig {
	public static final Codec<GeodeFeatureConfig> CODEC = RecordCodecBuilder.create(
			(builder) -> builder.group(
					BlockState.CODEC.fieldOf("state").forGetter((config) -> config.state),
					FeatureSpread.CODEC.fieldOf("diameter").forGetter((config) -> config.diameter))
			.apply(builder, GeodeFeatureConfig::new));
	
	protected final BlockState state;
	protected final FeatureSpread diameter;

	public GeodeFeatureConfig( BlockState budState, FeatureSpread diameter) {
		super();
		this.state = budState;
		this.diameter = diameter;
	}
}
