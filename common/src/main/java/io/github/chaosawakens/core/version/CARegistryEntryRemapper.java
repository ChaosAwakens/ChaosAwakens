package io.github.chaosawakens.core.version;

import com.mememan.nexus.asm.annotations.PostInit;
import com.mememan.nexus.template.event.blueprint.common.RegistryEventBlueprint;
import com.mememan.nexus.template.event.def.common.RegistryEvent;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.util.StringUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@PostInit
public final class CARegistryEntryRemapper {

    static {
        remap012Content();
        remap011Content();
    }

    private static void remap012Content() {
        RegistryEventBlueprint.MISSING_REGISTRY_ENTRIES.onEvent(event -> {
            List<RegistryEvent.MissingRegistryEntriesEvent.WrappedEntry<?>> missingEntries = event.getMissingEntries();

            missingEntries.forEach(entry -> {
                ResourceLocation oldId = entry.getOldKey();

                ChaosAwakens012Remaps.REMAPS.entrySet()
                        .stream()
                        .filter(curEntry -> curEntry.getKey().test(oldId))
                        .map(Map.Entry::getValue)
                        .map(idRemapper -> idRemapper.apply(oldId))
                        .findFirst()
                        .filter(newId -> !Objects.equals(oldId, newId))
                        .filter(newId -> BuiltInRegistries.REGISTRY.getOrThrow((ResourceKey) entry.getRegistryKey()).containsKey(newId))
                        .ifPresent(entry::attemptRemap);
            });
        });
    }

    private static void remap011Content() {

    }

    private static class ChaosAwakens012Remaps {
        private static final Map<Predicate<ResourceLocation>, Function<ResourceLocation, ResourceLocation>> REMAPS = new Object2ObjectOpenHashMap<>();

        private static final ResourceLocation FOSSIL_BASE = CAConstants.prefix("fossilised_");
        private static final String[] FOSSIL_SUFFIXES = new String[] {
                "Netherrack",
                "Blackstone",
                "Sandstone",
                "Sand",
                "Kyanite",
                "Gravel"
        };

        private static final ResourceLocation DUPLICATION_BASE = CAConstants.prefix("duplication_");
        private static final ResourceLocation DEAD_DUPLICATION_BASE = CAConstants.prefix("dead_duplication_");

        private static final ResourceLocation DEFOSILIZER_BASE = CAConstants.prefix("defossilizer");

        private static final ResourceLocation CRYSTAL_WOOD_BASE = CAConstants.prefix("crystal_wood");
        private static final ResourceLocation CRYSTAL_WOOD_SET_BASE = CAConstants.prefix("crystal");
        private static final ResourceLocation CHERRY_WOOD_BASE = CAConstants.prefix("cherry");

        private static final ResourceLocation[] CRYSTAL_WOOD_BASES = new ResourceLocation[] {
                CAConstants.prefix("crystal_button"),
                CAConstants.prefix("crystal_crafting_table"),
                CAConstants.prefix("crystal_fence"),
                CAConstants.prefix("crystal_fence_gate"),
                CAConstants.prefix("crystal_log"),
                CAConstants.prefix("crystal_planks"),
                CAConstants.prefix("crystal_pressure_plate"),
                CAConstants.prefix("crystal_sapling"),
                CAConstants.prefix("crystal_sign"),
                CAConstants.prefix("crystal_slab"),
                CAConstants.prefix("crystal_stairs"),
                CAConstants.prefix("crystal_trapdoor")
        };

        private static final ResourceLocation AMETHYST_BASE = CAConstants.prefix("amethyst");
        private static final ResourceLocation COPPER_BASE = CAConstants.prefix("copper");

        private static final ResourceLocation NEST_BLOCK_BASE = CAConstants.prefix("nest_block");

        private static final ResourceLocation SMALL_BUSH_BASE = CAConstants.prefix("small_bush");

        private static final ResourceLocation ROBO_BLOCK_V = CAConstants.prefix("robo_block_v");

        private static final ResourceLocation MARBLE_BASE = CAConstants.prefix("marble");
        private static final ResourceLocation RHINESTONE_BASE = CAConstants.prefix("rhinestone");
        private static final ResourceLocation LIMESTONE_BASE = CAConstants.prefix("limestone");

        static { // TODO For hardcoded substitutions, use the associated block IDs directly (where appropriate)
            // Fossilized stuff
            remap(
                    oldId -> oldId.getPath().startsWith(FOSSIL_BASE.getPath()),
                    oldId -> {
                        AtomicReference<ResourceLocation> result = new AtomicReference<>(oldId);
                        AtomicBoolean hasSuffix = new AtomicBoolean(false);

                        result.set(result.get().withPath(path -> path.replace(FOSSIL_BASE.getPath(), "fossilized_")));

                        Stream.concat(Arrays.stream(StringUtil.MULTICHAR_FOSSIL_SUFFIXES), Arrays.stream(FOSSIL_SUFFIXES))
                                .map(suffix -> suffix.toLowerCase(Locale.ROOT).replaceAll(" ", "_"))
                                .forEach(suffix -> {
                                    if (result.get().getPath().endsWith(suffix)) hasSuffix.set(true);
                                });

                        if (!hasSuffix.get()) result.set(result.get().withSuffix("_stone"));

                        return result.get();
                    }
            );

            // Duplicator stuff
            remap(
                    oldId -> oldId.getPath().startsWith(DUPLICATION_BASE.getPath()),
                    oldId -> oldId.withPath(path -> path.replace(DUPLICATION_BASE.getPath(), "duplicator_"))
            );

            // Dead duplicator stuff
            remap(
                    oldId -> oldId.getPath().startsWith(DEAD_DUPLICATION_BASE.getPath()),
                    oldId -> oldId.withPath(path -> path.replace(DEAD_DUPLICATION_BASE.getPath(), "dead_duplicator_"))
            );

            // Defossilizer
            remap(
                    oldId -> oldId.getPath().equals(DEFOSILIZER_BASE.getPath()),
                    oldId -> oldId.withPrefix("iron_")
            );

            // Crystalwood
            remap(
                    oldId -> oldId.getPath().equals(CRYSTAL_WOOD_BASE.getPath()),
                    oldId -> oldId.withPath("crystalwood")
            );

            // Other Crystalwood stuff
            remap(
                    oldId -> oldId.getPath().startsWith(CRYSTAL_WOOD_SET_BASE.getPath()) && !oldId.getPath().equals(CRYSTAL_WOOD_BASE.getPath()) && Arrays.asList(CRYSTAL_WOOD_BASES).contains(oldId),
                    oldId -> oldId.withPath(p -> p.replace("crystal_", "crystalwood_"))
            );

            // Cherry stuff
            remap(
                    oldId -> oldId.getNamespace().equals(CAConstants.MOD_ID) && (oldId.getPath().contains(CHERRY_WOOD_BASE.getPath())),
                    oldId -> new ResourceLocation(oldId.getPath())
            );

            // Amethyst stuff
            remap(
                    oldId -> oldId.getNamespace().equals(CAConstants.MOD_ID) && oldId.getPath().startsWith(AMETHYST_BASE.getPath()),
                    oldId -> oldId.withPath(path -> path.replace(AMETHYST_BASE.getPath(), "kunzite"))
            );

            // Copper stuff
            remap(
                    oldId -> oldId.getNamespace().equals(CAConstants.MOD_ID) && oldId.getPath().contains(COPPER_BASE.getPath()),
                    oldId -> new ResourceLocation(oldId.getPath())
            );

            // Wasp Nest Block
            remap(
                    oldId -> oldId.getPath().equals(NEST_BLOCK_BASE.getPath()),
                    oldId -> oldId.withPrefix("wasp_")
            );

            // Bush
            remap(
                    oldId -> oldId.getPath().equals(SMALL_BUSH_BASE.getPath()),
                    oldId -> oldId.withPath("bush")
            );

            // Robo Blocks
            remap(
                    oldId -> oldId.getPath().equals(ROBO_BLOCK_V.getPath()),
                    oldId -> oldId.withPath("robo_pillar")
            );
            remap(
                    oldId -> oldId.getPath().startsWith("robo_") && oldId.getPath().endsWith("_x"),
                    oldId -> oldId.withPath(p -> p.replace("_x", "_a"))
            );

            // Limestone/Rhinestone/Marble stuff
            remap(
                    oldId -> oldId.getPath().contains(MARBLE_BASE.getPath()) || oldId.getPath().contains(RHINESTONE_BASE.getPath()) || oldId.getPath().contains(LIMESTONE_BASE.getPath()),
                    oldId -> {
                        String oldPath = oldId.getPath();
                        Function<String, String> chiseledMapper = p -> oldPath.contains("chiseled") ? "chiseled_" + p : p;

                        if (oldPath.contains("pillar")) return new ResourceLocation("quartz_pillar");
                        if (oldPath.contains("slab")) return new ResourceLocation("stone_brick_slab");
                        if (oldPath.contains("stairs")) return new ResourceLocation("stone_brick_stairs");
                        if (oldPath.contains("wall")) return new ResourceLocation("stone_brick_wall");

                        return new ResourceLocation(chiseledMapper.apply("stone_bricks"));
                    }
            );
        }

        private static void remap(Predicate<ResourceLocation> condition, Function<ResourceLocation, ResourceLocation> remapFunction) {
            REMAPS.put(condition, remapFunction);
        }
    }
}
