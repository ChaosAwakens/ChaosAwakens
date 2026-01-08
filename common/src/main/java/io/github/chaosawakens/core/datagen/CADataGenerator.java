package io.github.chaosawakens.core.datagen;

import com.mememan.nexus.asm.annotations.DatagenRegistrarEntry;
import com.mememan.nexus.datagen.ModDatagenConfig;
import com.mememan.nexus.platform.NexusServices;
import io.github.chaosawakens.CAConstants;

@DatagenRegistrarEntry
public class CADataGenerator {
    public static final ModDatagenConfig DATAGEN_CONFIG = NexusServices.DATA_GENERATOR.registerConfigForMod(ModDatagenConfig.defaultConfig(CAConstants.MOD_ID));
}
