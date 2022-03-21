package io.github.chaosawakens.common.worldgen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

//Vanilla code stuff but slightly modified :p
public abstract class CrystalFlowerFeature <T extends IFeatureConfig> extends Feature<T>{

	public CrystalFlowerFeature(Codec<T> c) {
		super(c);
	}
	
	public boolean place(ISeedReader reader, ChunkGenerator chunkGen, Random rand, BlockPos pos, T feature) {
		BlockState blockstate = this.getRandomFlower(rand, pos, feature);
		int i = 0;
		for(int j = 0; j < this.getCount(feature); ++j) {
	    	  BlockPos blockpos = this.getPos(rand, pos, feature);
	    	  if (reader.isEmptyBlock(blockpos) && blockpos.getY() < 255 && blockstate.canSurvive(reader, blockpos) && this.isValid(reader, blockpos, feature)) { 
	    		  reader.setBlock(blockpos, blockstate, 2);  
	    		  ++i;   
	    	  }  
		} 
		return i > 0;
	}

	public abstract boolean isValid(IWorld world, BlockPos pos, T feature);

	public abstract int getCount(T feature);
	 
	public abstract BlockPos getPos(Random rand, BlockPos pos, T feature);
	 
	public abstract BlockState getRandomFlower(Random rand, BlockPos pos, T feature);

}
