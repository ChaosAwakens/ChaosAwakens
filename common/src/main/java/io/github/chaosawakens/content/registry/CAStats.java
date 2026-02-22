package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import com.mememan.nexus.property_wrapper.impl.specialised.language.SpecializedLanguagePropertyWrapper;
import io.github.chaosawakens.CAConstants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@RegistrarEntry
public final class CAStats {
    protected static final ObjectArrayList<Supplier<ResourceLocation>> CUSTOM_STATS = new ObjectArrayList<>();

    public static final Supplier<ResourceLocation> INTERACT_WITH_IRON_DEFOSSILIZER = new SpecializedLanguagePropertyWrapper<>(registerCustomStatType("interact_with_iron_defossilizer"), CAConstants.MOD_ID)
            .builder()
            .buildAndGet(); // TODO Custom PW stuff (for both stat types and custom stats)
    public static final Supplier<ResourceLocation> INTERACT_WITH_CRYSTAL_DEFOSSILIZER = new SpecializedLanguagePropertyWrapper<>(registerCustomStatType("interact_with_crystal_defossilizer"), CAConstants.MOD_ID)
            .builder()
            .buildAndGet(); // TODO Custom PW stuff (for both stat types and custom stats)

    private static Supplier<ResourceLocation> registerCustomStatType(ResourceLocation statId, Supplier<ResourceLocation> statKey, @Nullable StatFormatter statFormatter) {
        Supplier<ResourceLocation> registeredStatKey = NexusServices.REGISTRAR.registerObject(statId, statKey, BuiltInRegistries.CUSTOM_STAT);

        CUSTOM_STATS.add(registeredStatKey);

        return registeredStatKey;
    }

    private static Supplier<ResourceLocation> registerCustomStatType(ResourceLocation statId, Supplier<ResourceLocation> statKey) {
        return registerCustomStatType(statId, statKey, null);
    }

    private static Supplier<ResourceLocation> registerCustomStatType(ResourceLocation statId) {
        return registerCustomStatType(statId, () -> statId);
    }

    private static Supplier<ResourceLocation> registerCustomStatType(String statId) {
        return registerCustomStatType(CAConstants.prefix(statId));
    }

    public static ImmutableList<Supplier<ResourceLocation>> getCustomStats() {
        return ImmutableList.copyOf(CUSTOM_STATS);
    }
}
