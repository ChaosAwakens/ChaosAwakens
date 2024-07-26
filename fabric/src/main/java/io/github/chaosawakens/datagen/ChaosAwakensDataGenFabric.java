package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jetbrains.annotations.Nullable;

public class ChaosAwakensDataGenFabric implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

    }

    @Nullable
    @Override
    public String getEffectiveModId() {
        return CAConstants.MODID;
    }
}
