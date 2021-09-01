package io.github.chaosawakens.data;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * @author invalid2
 */
public class CABlockStateProvider extends BlockStateProvider {
	
	/**
	 * @param gen
	 * @param modid
	 * @param exFileHelper
	 */
	public CABlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
		super(gen, modid, exFileHelper);
	}
	
	@Override
	protected void registerStatesAndModels() {
		this.logBlock(CABlocks.APPLE_LOG.get());
		this.logBlock(CABlocks.CHERRY_LOG.get());
		this.logBlock(CABlocks.PEACH_LOG.get());
		this.cubeAll(CABlocks.PEACH_LEAVES.get());
		this.cubeAll(CABlocks.PEACH_PLANKS.get());
		this.logBlock(CABlocks.STRIPPED_PEACH_LOG.get());
		
		this.cubeAll(CABlocks.CORN_BODY_BLOCK.get());
	}
}