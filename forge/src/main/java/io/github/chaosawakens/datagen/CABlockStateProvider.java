package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CABlockStateProvider extends BlockStateProvider {

    public CABlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CAConstants.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
