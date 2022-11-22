package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.feature.IFeatureConfig;

public class CrystalBranchConfig implements IFeatureConfig {
	public static final Codec<CrystalBranchConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(		
			Codec.intRange(0, 100).fieldOf("branch_count").forGetter(i -> i.branchCount),	
			Codec.intRange(0, 100).fieldOf("random_branch_count").orElse(0).forGetter(i -> i.randomBranchCount),
			Codec.doubleRange(1, 30).fieldOf("branch_length").forGetter(i -> i.branchLength),
			Codec.doubleRange(0, 24).fieldOf("random_branch_length").orElse(0.0).forGetter(i -> i.randomBranchLength),
			Codec.doubleRange(0, 1.0).fieldOf("spacing").orElse(0.35D).forGetter(i -> i.spacing),
			//How the branches extend North/South, where lesser = downwards and more = upwards
			Codec.floatRange(-1.0F, 1.0F).fieldOf("directional_pitch").orElse(0.0F).forGetter(i -> i.directionalPitch)
			).apply(instance, CrystalBranchConfig::new));
	
	public final int branchCount;
	public final int randomBranchCount;
	public final double branchLength;
	public final double randomBranchLength;
	public final double spacing;
	public final float directionalPitch;
	
	public CrystalBranchConfig(int branchCount, int randomBranchCount, double branchLength, double randomBranchLength, double spacing, float directionalPitch) {
		this.branchCount = branchCount;
		this.randomBranchCount = randomBranchCount;
		this.branchLength = branchLength;
		this.randomBranchLength = randomBranchLength;
		this.spacing = spacing;
		this.directionalPitch = directionalPitch;
	}
	
	public CrystalBranchConfig(int branchCount, int branchLength) {
		this.branchCount = branchCount;
		this.randomBranchCount = 0;
		this.branchLength = branchLength;
		this.randomBranchLength = 0;
		this.spacing = 0.35;
		this.directionalPitch = 0.8F;
	}
	
	public CrystalBranchConfig() {
		this.branchCount = 3;
		this.randomBranchCount = 0;
		this.branchLength = 8;
		this.randomBranchLength = 0;
		this.spacing = 0.35;
		this.directionalPitch = -0.8F;
	}
}
