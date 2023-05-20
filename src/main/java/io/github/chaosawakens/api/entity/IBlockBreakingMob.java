package io.github.chaosawakens.api.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;

/**
 * For mobs such as the beaver, that mine blocks, excluding mobs that just plow through blocks (e.g. the robo pounder).
 * @author Meme Man
 *TODO
 */
public interface IBlockBreakingMob {
    List<Block> breakableBlocks = new ArrayList<>();
	
	/**
	 * Determines how fast an entity should mine a block. 1.0F means that it will instantly break it.
	 * @param entity the block breaking entity
	 * @return the mining speed of the entity (cannot be above 1.0F), defaults to 1.0F
	 */
	default float getMiningSpeed(LivingEntity entity) { return 1.0F; }
	
	default boolean canBreakBlock(Block block) { return breakableBlocks.contains(block); }
	
	default List<Block> getBreakableBlocks() { return breakableBlocks; }
	
	default void addBlocksAsBreakable(Block... blocks) { for (Block block : blocks) if (!breakableBlocks.contains(block)) breakableBlocks.add(block); }	
	
}
