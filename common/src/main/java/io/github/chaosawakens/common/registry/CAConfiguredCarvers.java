package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.platform.CAServices;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

import java.util.function.Supplier;

@RegistrarEntry
public class CAConfiguredCarvers {
    public static final ObjectArrayList<Supplier<ResourceKey<ConfiguredWorldCarver<?>>>> CARVER_KEYS = new ObjectArrayList<>();

    public static final Supplier<ResourceKey<ConfiguredWorldCarver<?>>> CONFIGURED_MINING_PARADISE_CAVE = configuredCave();

    public static Supplier<ResourceKey<ConfiguredWorldCarver<?>>> configuredCave() {
        Supplier<ResourceKey<ConfiguredWorldCarver<?>>> configuredMiningParadiseCave = CAServices.REGISTRAR.registerDatapackObject(
            CAConstants.prefix("mining_paradise_cave"),
                b -> () -> WorldCarver.CAVE
                    .configured(new CaveCarverConfiguration(
                            0.35F, // Increased probability to 35%
                            UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(360)), // Full height range, including below 0
                            UniformFloat.of(0.5F, 3.25F), // Increased max vertical radius
                            VerticalAnchor.bottom(), // Start from world bottom
                            CarverDebugSettings.of(false, Blocks.DIAMOND_BLOCK.defaultBlockState()),
                            b.lookup(Registries.BLOCK).getOrThrow(CATags.CABlockTags.MINING_CARVER_REPLACEABLES.get()),
                            UniformFloat.of(1.0F, 3.5F), // Increased max horizontal radius
                            UniformFloat.of(1.0F, 3.5F), // Increased max horizontal radius
                            UniformFloat.of(-0.8F, 0.8F)) // Wider floor level range
                    ),
            Registries.CONFIGURED_CARVER
        );
        CARVER_KEYS.add(configuredMiningParadiseCave);
        return configuredMiningParadiseCave;
    }
}
