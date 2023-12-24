package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class CrystalBranchFeature extends Feature<CrystalBranchConfig>{

	public CrystalBranchFeature(Codec<CrystalBranchConfig> p_i231953_1_) {
		super(p_i231953_1_);
	}

	@Override
	public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, CrystalBranchConfig p_241855_5_) {
		return true;
	}

}
