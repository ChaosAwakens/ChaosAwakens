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
					BlockState.CODEC.fieldOf("budding").forGetter((config) -> config.budding),
					BlockState.CODEC.fieldOf("base").forGetter((config) -> config.base),
					FeatureSpread.CODEC.fieldOf("diameter").forGetter((config) -> config.diameter),
					FeatureSpread.CODEC.fieldOf("budding_chance").forGetter((config) -> config.buddingChance))
			.apply(builder, GeodeFeatureConfig::new));
	
	protected final BlockState budding;
	protected final BlockState base;
	protected final FeatureSpread diameter;
	protected final FeatureSpread buddingChance;

	public GeodeFeatureConfig(BlockState budding, BlockState base, FeatureSpread diameter, FeatureSpread buddingChance) {
		super();
		this.budding = budding;
		this.base = base;
		this.diameter = diameter;
		this.buddingChance = buddingChance;
	}
}
