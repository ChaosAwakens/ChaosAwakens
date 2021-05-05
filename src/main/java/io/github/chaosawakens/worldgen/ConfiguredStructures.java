package io.github.chaosawakens.worldgen;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.ModStructures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.*;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.*;

public class ConfiguredStructures {

    public static StructureFeature<?, ?> CONFIGURED_ENT_DUNGEON = ModStructures.ENT_DUNGEON.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(ChaosAwakens.MODID, "configured_ent_dungeon"), CONFIGURED_ENT_DUNGEON);

        FlatGenerationSettings.STRUCTURES.put(ModStructures.ENT_DUNGEON.get(), CONFIGURED_ENT_DUNGEON);
    }
}