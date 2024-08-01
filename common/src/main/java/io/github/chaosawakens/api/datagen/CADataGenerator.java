package io.github.chaosawakens.api.datagen;

import net.minecraft.WorldVersion;
import net.minecraft.data.DataGenerator;

import java.nio.file.Path;

public class CADataGenerator extends DataGenerator {

    public CADataGenerator(Path outputPath, WorldVersion worldVersion, boolean forceGeneration) {
        super(outputPath, worldVersion, forceGeneration);
    }

    @Override
    public PackGenerator getVanillaPack(boolean shouldRun) {
        throw new UnsupportedOperationException("Attempted to run vanilla pack generator for CA data generator.");
    }

    @Override
    public PackGenerator getBuiltinDatapack(boolean shouldRun, String packName) {
        throw new UnsupportedOperationException("Attempted to run vanilla data pack generator for CA data generator.");
    }
}
