package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.platform.CAServices;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
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
    public static final Supplier<ResourceKey<ConfiguredWorldCarver<?>>> CONFIGURED_MINING_PARADISE_CANYON = configuredCanyon();
    public static final Supplier<ResourceKey<ConfiguredWorldCarver<?>>> CONFIGURED_MINING_PARADISE_EXTRA_CAVE = configuredExtraCave();

    public static Supplier<ResourceKey<ConfiguredWorldCarver<?>>> configuredCave() {
        Supplier<ResourceKey<ConfiguredWorldCarver<?>>> configuredMiningParadiseCave = CAServices.REGISTRAR.registerDatapackObject(
            CAConstants.prefix("mining_paradise_cave"),
                b -> () -> WorldCarver.CAVE
                    .configured(new CaveCarverConfiguration(
                            0.3F, // Increased probability to 30%
                            UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(320)), // Full height range, including below 0
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
    public static Supplier<ResourceKey<ConfiguredWorldCarver<?>>> configuredExtraCave() {
        Supplier<ResourceKey<ConfiguredWorldCarver<?>>> configuredMiningParadiseCave = CAServices.REGISTRAR.registerDatapackObject(
                CAConstants.prefix("mining_paradise_extra_cave"),
                b -> () -> WorldCarver.CAVE
                        .configured(new CaveCarverConfiguration(
                                0.07F,
                                UniformHeight.of(VerticalAnchor.aboveBottom(8),
                                        VerticalAnchor.absolute(47)),
                                UniformFloat.of(0.1F, 0.9F),
                                VerticalAnchor.aboveBottom(8),
                                CarverDebugSettings.of(false, Blocks.OAK_BUTTON.defaultBlockState()),
                                b.lookup(Registries.BLOCK).getOrThrow(CATags.CABlockTags.MINING_CARVER_REPLACEABLES.get()),
                                UniformFloat.of(0.7F, 1.4F),
                                UniformFloat.of(0.8F, 1.3F),
                                UniformFloat.of(-1.0F, -0.4F)))
                ,
                Registries.CONFIGURED_CARVER
        );
        CARVER_KEYS.add(configuredMiningParadiseCave);
        return configuredMiningParadiseCave;
    }


    public static Supplier<ResourceKey<ConfiguredWorldCarver<?>>> configuredCanyon() {
        Supplier<ResourceKey<ConfiguredWorldCarver<?>>> configuredMiningParadiseCanyon = CAServices.REGISTRAR.registerDatapackObject(
                CAConstants.prefix("mining_paradise_canyon"),
                b -> () -> WorldCarver.CANYON
                        .configured(new CanyonCarverConfiguration(
                                0.02F,
                                UniformHeight.of(VerticalAnchor.absolute(10), VerticalAnchor.absolute(134)),
                                ConstantFloat.of(3.0F),
                                VerticalAnchor.aboveBottom(16),
                                CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()),
                                b.lookup(Registries.BLOCK).getOrThrow(CATags.CABlockTags.MINING_CARVER_REPLACEABLES.get()),
                                UniformFloat.of(-0.25F, 0.25F),
                                new CanyonCarverConfiguration.CanyonShapeConfiguration(
                                        UniformFloat.of(0.25F, 1.5F),
                                        TrapezoidFloat.of(0.0F, 9.0F, 3.0F),
                                        3,
                                        UniformFloat.of(0.25F, 1.5F),
                                        1.5F,
                                        0.0F)
                        )),
                Registries.CONFIGURED_CARVER
        );
        CARVER_KEYS.add(configuredMiningParadiseCanyon);
        return configuredMiningParadiseCanyon;
    }
}
