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
        this.cubeAll(CABlocks.APPLE_PLANKS.get());
        this.logBlock(CABlocks.STRIPPED_APPLE_LOG.get());
        this.logBlock(CABlocks.CHERRY_LOG.get());
        this.cubeAll(CABlocks.CHERRY_PLANKS.get());
        this.cubeAll(CABlocks.CHERRY_LEAVES.get());
        this.logBlock(CABlocks.STRIPPED_CHERRY_LOG.get());
        this.logBlock(CABlocks.DUPLICATION_LOG.get());
        this.logBlock(CABlocks.DEAD_DUPLICATION_LOG.get());
        this.cubeAll(CABlocks.DUPLICATION_PLANKS.get());
        this.cubeAll(CABlocks.DUPLICATION_LEAVES.get());
        this.logBlock(CABlocks.STRIPPED_DUPLICATION_LOG.get());
        this.logBlock(CABlocks.PEACH_LOG.get());
        this.cubeAll(CABlocks.PEACH_PLANKS.get());
        this.cubeAll(CABlocks.PEACH_LEAVES.get());
        this.logBlock(CABlocks.STRIPPED_PEACH_LOG.get());
        this.blockTexture(CABlocks.PEACH_SAPLING.get());
    }
}