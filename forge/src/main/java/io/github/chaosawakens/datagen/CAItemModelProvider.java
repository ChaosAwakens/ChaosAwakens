package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.BlockModelDefinition;
import io.github.chaosawakens.api.block.BlockPropertyWrapper;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class CAItemModelProvider extends ItemModelProvider {

    public CAItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CAConstants.MODID, existingFileHelper);
    }

    @NotNull
    @Override
    public String getName() {
        return CAConstants.MOD_NAME.concat(": Item Models");
    }

    @Override
    protected void registerModels() {
        registerBlockModels();
        registerItemModels();
    }

    protected void registerBlockModels() {
        BlockPropertyWrapper.getMappedBwps().forEach((blockSupEntry, mappedBpw) -> {
            String formattedBlockRegName = blockSupEntry.get().getDescriptionId().substring(blockSupEntry.get().getDescriptionId().lastIndexOf(".") + 1);
            BlockModelDefinition curModelDef = mappedBpw.getModelDefinition();


        });
    }

    protected void registerItemModels() {

    }
}
