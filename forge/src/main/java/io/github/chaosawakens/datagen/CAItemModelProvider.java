package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
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
        registerItemModels();
    }

    protected void registerItemModels() {

    }
}
