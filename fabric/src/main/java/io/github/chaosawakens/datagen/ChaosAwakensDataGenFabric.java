package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jetbrains.annotations.Nullable;

/**
 * We don't use Fabric datagen (although like Forge, it is configured to output generated resources inside :common). In order to see actual used datagen, refer to CA's Forge datagen classes.
 */
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
