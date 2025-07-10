package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.platform.CAServices;
import io.github.chaosawakens.common.worldgen.config.mining_paradise.MiningParadiseDimensionConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;

import java.util.function.Function;
import java.util.function.Supplier;

@RegistrarEntry
public class CAMultiNoiseBiomeSourceParameterLists {
    private static final ObjectArrayList<Supplier<ResourceKey<MultiNoiseBiomeSourceParameterList>>> MULTI_NOISE_BIOME_SOURCE_PARAMETER_LISTS = new ObjectArrayList<>();

    public static final Supplier<ResourceKey<MultiNoiseBiomeSourceParameterList>> MINING_PARADISE_BIOME_LIST = registerMultiNoiseBiomeSourceParameterList("mining_paradise", MiningParadiseDimensionConfig::createMiningParadiseNoiseBiomes);

    private static Supplier<ResourceKey<MultiNoiseBiomeSourceParameterList>> registerMultiNoiseBiomeSourceParameterList(ResourceLocation id, Function<BootstapContext<MultiNoiseBiomeSourceParameterList>, Supplier<MultiNoiseBiomeSourceParameterList>> actualMultiNoiseBiomeSourceParameterListConfigFunc) {
        Supplier<ResourceKey<MultiNoiseBiomeSourceParameterList>> mnbsplKeySup = CAServices.REGISTRAR.registerDatapackObject(id, actualMultiNoiseBiomeSourceParameterListConfigFunc, Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);
        MULTI_NOISE_BIOME_SOURCE_PARAMETER_LISTS.add(mnbsplKeySup);
        return mnbsplKeySup;
    }

    private static Supplier<ResourceKey<MultiNoiseBiomeSourceParameterList>> registerMultiNoiseBiomeSourceParameterList(String id, Function<BootstapContext<MultiNoiseBiomeSourceParameterList>, Supplier<MultiNoiseBiomeSourceParameterList>> actualMultiNoiseBiomeSourceParameterListConfigFunc) {
        return registerMultiNoiseBiomeSourceParameterList(CAConstants.prefix(id), actualMultiNoiseBiomeSourceParameterListConfigFunc);
    }

    public static ImmutableList<Supplier<ResourceKey<MultiNoiseBiomeSourceParameterList>>> getMultiNoiseBiomeSourceParameterLists() {
        return ImmutableList.copyOf(MULTI_NOISE_BIOME_SOURCE_PARAMETER_LISTS);
    }

    @RegistrarEntry
    public static class Presets {
        private static final ObjectArrayList<Supplier<MultiNoiseBiomeSourceParameterList.Preset>> MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST_PRESETS = new ObjectArrayList<>();

        public static final Supplier<MultiNoiseBiomeSourceParameterList.Preset> MINING_PARADISE_BIOME_SOURCE_PRESET = registerBiomeSourceParameterListPreset(CAConstants.prefix("mining_paradise"), () -> new MultiNoiseBiomeSourceParameterList.Preset(CAConstants.prefix("mining_paradise"), MiningParadiseDimensionConfig::generateMiningParadiseBiomes));

        private static Supplier<MultiNoiseBiomeSourceParameterList.Preset> registerBiomeSourceParameterListPreset(ResourceLocation presetId, Supplier<MultiNoiseBiomeSourceParameterList.Preset> presetSup) {
            MultiNoiseBiomeSourceParameterList.Preset.BY_NAME = Util.make(MultiNoiseBiomeSourceParameterList.Preset.BY_NAME, (hardcodedPresetMap) -> hardcodedPresetMap.putIfAbsent(presetId, presetSup.get()));
            return presetSup;
        }

        public static ImmutableList<Supplier<MultiNoiseBiomeSourceParameterList.Preset>> getMultiNoiseBiomeSourceParameterListPresets() {
            return ImmutableList.copyOf(MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST_PRESETS);
        }
    }
}
