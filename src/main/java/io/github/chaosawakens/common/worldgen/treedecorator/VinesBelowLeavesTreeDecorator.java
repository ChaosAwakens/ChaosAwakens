package io.github.chaosawakens.common.worldgen.treedecorator;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.chaosawakens.common.registry.CATreeDecoratorTypes;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class VinesBelowLeavesTreeDecorator extends TreeDecorator {
	public static final Codec<VinesBelowLeavesTreeDecorator> CODEC = RecordCodecBuilder.create((decorator) ->
		decorator.group(BlockState.CODEC.fieldOf("vine").forGetter((instance) -> instance.vine))
		.apply(decorator, VinesBelowLeavesTreeDecorator::new));
	private final BlockState vine;
	
	public VinesBelowLeavesTreeDecorator(BlockState vine) {
		this.vine = vine;
	}
	
	@Override
	protected TreeDecoratorType<?> type() {
		return CATreeDecoratorTypes.VINES_BELOW_LEAVES_TREE_DECORATOR.get();
	}

	@Override
	public void place(ISeedReader reader, Random rand, List<BlockPos> logs, List<BlockPos> leaves, Set<BlockPos> set, MutableBoundingBox bB) {
		leaves.stream().filter((pos) -> Feature.isAir(reader, pos.below())).forEach((pos) -> this.vineAttempt(reader, rand, pos));
	}
	
	public void vineAttempt(ISeedReader reader, Random rand, BlockPos pos) {
		int height = rand.nextInt(4) + 1;
		BlockPos.Mutable mutable = new BlockPos.Mutable(pos.getX(), pos.getY(), pos.getZ());
		
		if(rand.nextInt(10) == 0) {
			for(int i = 0; i < height; i++) {
				BlockPos placePos = mutable.offset(0, -1 - i, 0);
				if(Feature.isAir(reader, placePos))
					reader.setBlock(placePos, vine, 2);
				else
					return;
			}
		}
	}
}
