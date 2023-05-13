package io.github.chaosawakens.common.worldgen.feature;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class NBTTreeFeatureConfig implements IFeatureConfig {
	public static final Codec<NBTTreeFeatureConfig> CODEC = RecordCodecBuilder.create(builder -> builder
			.group(Heightmap.Type.CODEC.fieldOf("heightmap").forGetter(cfg -> cfg.heightmap),
					BlockStateProvider.CODEC.fieldOf("log_provider").forGetter(cfg -> cfg.leavesProvider),
					BlockStateProvider.CODEC.fieldOf("leaves_provider").forGetter(cfg -> cfg.leavesProvider),
					ResourceLocation.CODEC.listOf().fieldOf("trunk_pool").forGetter(cfg -> cfg.trunkPool),
					ResourceLocation.CODEC.listOf().fieldOf("foliage_pool").forGetter(cfg -> cfg.foliagePool),
					BlockState.CODEC.fieldOf("target_block").forGetter(cfg -> cfg.targetBlock),
					BlockState.CODEC.fieldOf("base_state").forGetter(cfg -> cfg.baseState)
					).apply(builder, NBTTreeFeatureConfig::new));
	
	public final Heightmap.Type heightmap;
	public final BlockStateProvider logProvider;
	public final BlockStateProvider leavesProvider;
	public final List<ResourceLocation> trunkPool;
	public final List<ResourceLocation> foliagePool;
	public final BlockState targetBlock;
	public final BlockState baseState;
	
	public NBTTreeFeatureConfig(Heightmap.Type heightmap, BlockStateProvider logProvider, BlockStateProvider leavesProvider,
			List<ResourceLocation> trunkPool, List<ResourceLocation> foliagePool, BlockState targetBlock, BlockState baseState) {
		this.heightmap = heightmap;
		this.logProvider = logProvider;
		this.leavesProvider = leavesProvider;
		this.trunkPool = trunkPool;
		this.foliagePool = foliagePool;
		this.targetBlock = targetBlock;
		this.baseState = baseState;
	}

}
