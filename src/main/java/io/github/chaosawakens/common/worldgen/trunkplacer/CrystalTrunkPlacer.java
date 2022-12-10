package io.github.chaosawakens.common.worldgen.trunkplacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.chaosawakens.common.registry.CATrunkPlacerTypes;
import io.github.chaosawakens.common.worldgen.feature.CrystalBranchConfig;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class CrystalTrunkPlacer extends AbstractTrunkPlacer {
	@SuppressWarnings("unused")
	private static final Direction[] DIRECTIONS = new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    public static final Codec<CrystalTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> trunkPlacerParts(instance).and(instance.group(
    		Codec.intRange(0, 12).fieldOf("branch_pos_offset").forGetter(i -> i.branchPosOffset),
    		CrystalBranchConfig.CODEC.fieldOf("branch_config").forGetter(i -> i.branchConfig)
    		)).apply(instance, CrystalTrunkPlacer::new)
    	);
	
    private final int branchPosOffset;
	private final CrystalBranchConfig branchConfig;	
	
	public CrystalTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, int branchPosOffset , CrystalBranchConfig branchConfig) {
		super(baseHeight, heightRandA, heightRandB);
		this.branchConfig = branchConfig;
		this.branchPosOffset = branchPosOffset;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return CATrunkPlacerTypes.CRYSTAL_TRUNK_PLACER;
	}
	
	@Override
	public int getTreeHeight(Random random) {
		return super.getTreeHeight(random);
	}
	
	public CrystalBranchConfig getBranchConfig() {
		return branchConfig;
	}
	
	public int getBranchPosOffset() {
		return branchPosOffset;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader reader, Random rand, int treeHeight, BlockPos pos, Set<BlockPos> bPos, MutableBoundingBox box, BaseTreeFeatureConfig config) {
		List<FoliagePlacer.Foliage> leaves = new ArrayList<>();
		
		for (int h = 0; h < treeHeight; h++) {
			if (!placeLog(reader, rand, pos.above(h), bPos, box, config)) {
				treeHeight = h;
				break;
			}
		}
		
		leaves.add(new Foliage(pos.above(treeHeight), 0, false));
		
		int branches = branchConfig.branchCount + rand.nextInt(branchConfig.randomBranchCount + 1);
		double offset = rand.nextDouble();
		if (branchConfig.randomBranchCount == 0) {
			branches = branchConfig.branchCount;
		}
		
		for (int amount = 0; amount < branches; amount++) {
			if (config.trunkProvider.getState(rand, pos.above()).isAir()) {
				placeBranches(reader, pos, bPos, leaves, (int) (treeHeight - offset + amount), branchConfig.branchLength, branchConfig.spacing * amount + offset, branchConfig.directionalPitch, rand, box, config);
			}
		}
		
		if (Feature.isAir(reader, pos)) {
			for (int thickness = 0; thickness < rand.nextInt(6) + 4; thickness++) {
				placeLog(reader, rand, pos, bPos, box, config);
				placeLog(reader, rand, pos, bPos, box, config);
			}
		}
		
		return leaves;
	}
	
	public BlockPos translate(BlockPos pos, double distance, double angle, double tilt) {
		double rangle = angle * 2.0D * Math.PI;
		double rtilt = tilt * Math.PI;

		return pos.offset(
				Math.round(Math.sin(rangle) * Math.sin(rtilt) * distance),
				Math.round(Math.cos(rtilt) * distance),
				Math.round(Math.cos(rangle) * Math.sin(rtilt) * distance)
		);
	}
	
	private void placeBranches(IWorldGenerationReader world, BlockPos pos, Set<BlockPos> trunkBlocks, List<FoliagePlacer.Foliage> leafBlocks, int height, double length, double angle, double tilt, Random treeRNG, MutableBoundingBox mbb, BaseTreeFeatureConfig config) {
		 BlockPos src = pos.above(height);    
		 BlockPos dest = translate(src, length, angle, tilt);	 
		 drawBranch(world, treeRNG, src, dest, trunkBlocks, mbb, config);
		 
		 placeLog(world, treeRNG, dest.east(), trunkBlocks, mbb, config);   
		 placeLog(world, treeRNG, dest.west(), trunkBlocks, mbb, config);    
		 placeLog(world, treeRNG, dest.south(), trunkBlocks, mbb, config);    
		 placeLog(world, treeRNG, dest.north(), trunkBlocks, mbb, config);
		 
		 leafBlocks.add(new FoliagePlacer.Foliage(dest, 0, false));
	}
	
	public BlockPos[] getBranchArrays(int x1, int y1, int z1, int x2, int y2, int z2) {
		int i, dx, dy, dz, absDx, absDy, absDz, x_inc, y_inc, z_inc, err_1, err_2, doubleAbsDx, doubleAbsDy, doubleAbsDz;

		BlockPos pixel = new BlockPos(x1, y1, z1);
		BlockPos lineArray[];

		dx = x2 - x1;
		dy = y2 - y1;
		dz = z2 - z1;
		x_inc = (dx < 0) ? -1 : 1;
		absDx = Math.abs(dx);
		y_inc = (dy < 0) ? -1 : 1;
		absDy = Math.abs(dy);
		z_inc = (dz < 0) ? -1 : 1;
		absDz = Math.abs(dz);
		doubleAbsDx = absDx << 1;
		doubleAbsDy = absDy << 1;
		doubleAbsDz = absDz << 1;

		if ((absDx >= absDy) && (absDx >= absDz)) {
			err_1 = doubleAbsDy - absDx;
			err_2 = doubleAbsDz - absDx;
			lineArray = new BlockPos[absDx + 1];
			for (i = 0; i < absDx; i++) {
				lineArray[i] = pixel;
				if (err_1 > 0) {
					pixel = pixel.above(y_inc);
					err_1 -= doubleAbsDx;
				}
				if (err_2 > 0) {
					pixel = pixel.south(z_inc);
					err_2 -= doubleAbsDx;
				}
				err_1 += doubleAbsDy;
				err_2 += doubleAbsDz;
				pixel = pixel.east(x_inc);
			}
		} else if ((absDy >= absDx) && (absDy >= absDz)) {
			err_1 = doubleAbsDx - absDy;
			err_2 = doubleAbsDz - absDy;
			lineArray = new BlockPos[absDy + 1];
			for (i = 0; i < absDy; i++) {
				lineArray[i] = pixel;
				if (err_1 > 0) {
					pixel = pixel.east(x_inc);
					err_1 -= doubleAbsDy;
				}
				if (err_2 > 0) {
					pixel = pixel.south(z_inc);
					err_2 -= doubleAbsDy;
				}
				err_1 += doubleAbsDx;
				err_2 += doubleAbsDz;
				pixel = pixel.above(y_inc);
			}
		} else {
			err_1 = doubleAbsDy - absDz;
			err_2 = doubleAbsDx - absDz;
			lineArray = new BlockPos[absDz + 1];
			for (i = 0; i < absDz; i++) {
				lineArray[i] = pixel;
				if (err_1 > 0) {
					pixel = pixel.above(y_inc);
					err_1 -= doubleAbsDz;
				}
				if (err_2 > 0) {
					pixel = pixel.east(x_inc);
					err_2 -= doubleAbsDz;
				}
				err_1 += doubleAbsDy;
				err_2 += doubleAbsDx;
				pixel = pixel.south(z_inc);
			}
		}
		lineArray[lineArray.length - 1] = pixel;

		return lineArray;
	}
	
	public BlockPos[] getBranchArrays(BlockPos src, BlockPos dest) {
		return getBranchArrays(src.getX(), src.getY(), src.getZ(), dest.getX(), dest.getY(), dest.getZ());
	}
	
    private void drawBranch(IWorldGenerationReader world, Random random, BlockPos from, BlockPos to, Set<BlockPos> state, MutableBoundingBox mbb, BaseTreeFeatureConfig config) {
        for (BlockPos pixel : getBranchArrays(from, to)) {
        	placeLog(world, random, pixel, state, mbb, config);
        }
    }
}
