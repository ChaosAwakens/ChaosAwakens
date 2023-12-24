package io.github.chaosawakens.common.worldgen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.common.blocks.multiface.LeafCarpetBlock;
import io.github.chaosawakens.common.registry.CATreeDecoratorTypes;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class LeafCarpetTreeDecorator extends TreeDecorator {
	public static final Codec<LeafCarpetTreeDecorator> CODEC = RecordCodecBuilder.create((decorator) ->
					decorator.group(BlockState.CODEC.fieldOf("leaf_carpet").forGetter((instance) -> instance.carpet))
					.apply(decorator, LeafCarpetTreeDecorator::new));
	public final BlockState carpet;

	public LeafCarpetTreeDecorator(BlockState carpet) {
		this.carpet = carpet;
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return CATreeDecoratorTypes.LEAF_CARPET_TREE_DECORATOR.get();
	}

	@Override
	public void place(ISeedReader reader, Random rand, List<BlockPos> logs, List<BlockPos> leaves, Set<BlockPos> set, MutableBoundingBox bB) {
		BlockPos.Mutable potentialCarpetPos = new BlockPos.Mutable();

		for (int i = bB.x0; i < bB.x1; i++) {
			for (int j = bB.z0; j < bB.z1; j++) {
				int yMBNL = reader.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, i, j);
				int yWS = reader.getHeight(Heightmap.Type.WORLD_SURFACE, i, j);

				if (yMBNL != yWS && reader.isEmptyBlock(potentialCarpetPos.offset(i, yMBNL, j)) && !(reader.getBlockState(potentialCarpetPos.offset(i, yMBNL, j).below()).getBlock() instanceof LeafCarpetBlock)) {
					if (rand.nextInt(5) < 3) reader.setBlock(reader.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, potentialCarpetPos.offset(i, yMBNL, j)), carpet.setValue(LeafCarpetBlock.getFaceProperty(Direction.DOWN), true), 2);
				}
			}
		}
	}
}
