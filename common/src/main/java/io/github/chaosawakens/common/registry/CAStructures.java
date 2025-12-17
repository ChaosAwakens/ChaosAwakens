package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.platform.CAServices;
import io.github.chaosawakens.common.worldgen.structure.BeeHiveProcessor;
import io.github.chaosawakens.common.worldgen.structure.MesozoicVineProcessor;
import io.github.chaosawakens.common.worldgen.structure.structures.SurfaceStructure;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CAStructures {

    @RegistrarEntry
    public static class StructureTypes {
        private static final ObjectArrayList<Supplier<StructureType<?>>> STRUCTURE_TYPES = new ObjectArrayList<>();

        public static final Supplier<StructureType<SurfaceStructure>> SURFACE_STRUCTURE = registerStructureType("surface_structure", () -> () -> SurfaceStructure.CODEC);

        private static <S extends Structure> Supplier<StructureType<S>> registerStructureType(String id, Supplier<StructureType<S>> structureSup) {
            Supplier<StructureType<S>> typeSupplier = CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), structureSup, BuiltInRegistries.STRUCTURE_TYPE);
            STRUCTURE_TYPES.add((Supplier) typeSupplier);
            return typeSupplier;
        }

        public static ImmutableList<Supplier<StructureType<?>>> getStructureTypes() {
            return ImmutableList.copyOf(STRUCTURE_TYPES);
        }
    }

    @RegistrarEntry
    public static class Structures {
        private static final ObjectArrayList<Supplier<ResourceKey<Structure>>> STRUCTURES = new ObjectArrayList<>();

        public static final Supplier<ResourceKey<Structure>> ACACIA_ENT_TREE = registerStructure("acacia_ent_tree", (b) -> () -> new JigsawStructure(settings(b.lookup(Registries.BIOME).getOrThrow(CATags.BiomeTags.HAS_ACACIA_ENT_TREE.get()), TerrainAdjustment.NONE), b.lookup(Registries.TEMPLATE_POOL).getOrThrow(StructureTemplatePools.ACACIA_ENT_TREE_START.get()), 6, ConstantHeight.of(VerticalAnchor.absolute(0)), false, Heightmap.Types.WORLD_SURFACE_WG));
        public static final Supplier<ResourceKey<Structure>> BIRCH_ENT_TREE = registerStructure("birch_ent_tree", (b) -> () -> new JigsawStructure(settings(b.lookup(Registries.BIOME).getOrThrow(CATags.BiomeTags.HAS_BIRCH_ENT_TREE.get()), TerrainAdjustment.NONE), b.lookup(Registries.TEMPLATE_POOL).getOrThrow(StructureTemplatePools.BIRCH_ENT_TREE_START.get()), 6, ConstantHeight.of(VerticalAnchor.absolute(0)), false, Heightmap.Types.WORLD_SURFACE_WG));
        public static final Supplier<ResourceKey<Structure>> CRIMSON_ENT_TREE = registerStructure("crimson_ent_tree", (b) -> () -> new SurfaceStructure(settings(b.lookup(Registries.BIOME).getOrThrow(CATags.BiomeTags.HAS_CRIMSON_ENT_TREE.get()), TerrainAdjustment.NONE), b.lookup(Registries.TEMPLATE_POOL).getOrThrow(StructureTemplatePools.CRIMSON_ENT_TREE_START.get()), 6, ConstantHeight.of(VerticalAnchor.absolute(0)), false, Heightmap.Types.WORLD_SURFACE_WG));
        public static final Supplier<ResourceKey<Structure>> DARK_OAK_ENT_TREE = registerStructure("dark_oak_ent_tree", (b) -> () -> new JigsawStructure(settings(b.lookup(Registries.BIOME).getOrThrow(CATags.BiomeTags.HAS_DARK_OAK_ENT_TREE.get()), TerrainAdjustment.NONE), b.lookup(Registries.TEMPLATE_POOL).getOrThrow(StructureTemplatePools.DARK_OAK_ENT_TREE_START.get()), 6, ConstantHeight.of(VerticalAnchor.absolute(-6)), false, Heightmap.Types.WORLD_SURFACE_WG));
        public static final Supplier<ResourceKey<Structure>> GINKGO_ENT_TREE = registerStructure("ginkgo_ent_tree", (b) -> () -> new JigsawStructure(settings(b.lookup(Registries.BIOME).getOrThrow(CATags.BiomeTags.HAS_GINKGO_ENT_TREE.get()), TerrainAdjustment.NONE), b.lookup(Registries.TEMPLATE_POOL).getOrThrow(StructureTemplatePools.GINKGO_ENT_TREE_START.get()), 6, ConstantHeight.of(VerticalAnchor.absolute(0)), false, Heightmap.Types.WORLD_SURFACE_WG));
        public static final Supplier<ResourceKey<Structure>> JUNGLE_ENT_TREE = registerStructure("jungle_ent_tree", (b) -> () -> new JigsawStructure(settings(b.lookup(Registries.BIOME).getOrThrow(CATags.BiomeTags.HAS_JUNGLE_ENT_TREE.get()), TerrainAdjustment.NONE), b.lookup(Registries.TEMPLATE_POOL).getOrThrow(StructureTemplatePools.JUNGLE_ENT_TREE_START.get()), 6, ConstantHeight.of(VerticalAnchor.absolute(0)), false, Heightmap.Types.WORLD_SURFACE_WG));
        public static final Supplier<ResourceKey<Structure>> OAK_ENT_TREE = registerStructure("oak_ent_tree", (b) -> () -> new JigsawStructure(settings(b.lookup(Registries.BIOME).getOrThrow(CATags.BiomeTags.HAS_OAK_ENT_TREE.get()), TerrainAdjustment.NONE), b.lookup(Registries.TEMPLATE_POOL).getOrThrow(StructureTemplatePools.OAK_ENT_TREE_START.get()), 6, ConstantHeight.of(VerticalAnchor.absolute(0)), false, Heightmap.Types.WORLD_SURFACE_WG));
        public static final Supplier<ResourceKey<Structure>> SPRUCE_ENT_TREE = registerStructure("spruce_ent_tree", (b) -> () -> new JigsawStructure(settings(b.lookup(Registries.BIOME).getOrThrow(CATags.BiomeTags.HAS_SPRUCE_ENT_TREE.get()), TerrainAdjustment.NONE), b.lookup(Registries.TEMPLATE_POOL).getOrThrow(StructureTemplatePools.SPRUCE_ENT_TREE_START.get()), 6, ConstantHeight.of(VerticalAnchor.absolute(0)), false, Heightmap.Types.WORLD_SURFACE_WG));
        public static final Supplier<ResourceKey<Structure>> WARPED_ENT_TREE = registerStructure("warped_ent_tree", (b) -> () -> new SurfaceStructure(settings(b.lookup(Registries.BIOME).getOrThrow(CATags.BiomeTags.HAS_WARPED_ENT_TREE.get()), TerrainAdjustment.NONE), b.lookup(Registries.TEMPLATE_POOL).getOrThrow(StructureTemplatePools.WARPED_ENT_TREE_START.get()), 6, ConstantHeight.of(VerticalAnchor.absolute(0)), false, Heightmap.Types.WORLD_SURFACE_WG));

        private static <S extends Structure> Supplier<ResourceKey<S>> registerStructure(String id,  Function<BootstapContext<S>, Supplier<S>> structureFunction) {
            Supplier<ResourceKey<S>> typeSupplier = CAServices.REGISTRAR.registerDatapackObject(CAConstants.prefix(id), structureFunction, Registries.STRUCTURE);
            STRUCTURES.add((Supplier) typeSupplier);
            return typeSupplier;
        }

        public static ImmutableList<Supplier<ResourceKey<Structure>>> getStructures() {
            return ImmutableList.copyOf(STRUCTURES);
        }

        private static Structure.StructureSettings settings(HolderSet<Biome> $$0, Map<MobCategory, StructureSpawnOverride> $$1, GenerationStep.Decoration $$2, TerrainAdjustment $$3) {
            return new Structure.StructureSettings($$0, $$1, $$2, $$3);
        }

        private static Structure.StructureSettings settings(HolderSet<Biome> $$0, GenerationStep.Decoration $$1, TerrainAdjustment $$2) {
            return settings($$0, Map.of(), $$1, $$2);
        }

        private static Structure.StructureSettings settings(HolderSet<Biome> $$0, TerrainAdjustment $$1) {
            return settings($$0, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, $$1);
        }
    }

    @RegistrarEntry
    public static class StructureProcessors {
        private static final ObjectArrayList<Supplier<StructureProcessorType<?>>> STRUCTURE_PROCESSORS = new ObjectArrayList<>();

        public static final Supplier<StructureProcessorType<BeeHiveProcessor>> BEE_HIVE = registerProcessor("bee_hive", () -> () -> BeeHiveProcessor.CODEC);
        public static final Supplier<StructureProcessorType<MesozoicVineProcessor>> MESOZOIC_VINE = registerProcessor("mesozoic_vine", () -> () -> MesozoicVineProcessor.CODEC);

        private static <P extends StructureProcessor> Supplier<StructureProcessorType<P>> registerProcessor(String id, Supplier<StructureProcessorType<P>> codecSup) {
            Supplier<StructureProcessorType<P>> typeSupplier = CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), codecSup, BuiltInRegistries.STRUCTURE_PROCESSOR);
            STRUCTURE_PROCESSORS.add((Supplier) typeSupplier);
            return typeSupplier;
        }

        public static ImmutableList<Supplier<StructureProcessorType<?>>> getProcessors() {
            return ImmutableList.copyOf(STRUCTURE_PROCESSORS);
        }
    }

    @RegistrarEntry
    public static class StructureTemplatePools {
        private static final ObjectArrayList<Supplier<ResourceKey<StructureTemplatePool>>> TEMPLATE_POOLS = new ObjectArrayList<>();

        // ENTITIES
        public static final Supplier<ResourceKey<StructureTemplatePool>> ACACIA_ENT = registerPool("entity/ent/acacia", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:entity/ent/acacia"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> BIRCH_ENT = registerPool("entity/ent/birch", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:entity/ent/birch"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> CRIMSON_ENT = registerPool("entity/ent/crimson", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:entity/ent/crimson"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> DARK_OAK_ENT = registerPool("entity/ent/dark_oak", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:entity/ent/dark_oak"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> GINKGO_ENT = registerPool("entity/ent/ginkgo", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:entity/ent/ginkgo"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> JUNGLE_ENT = registerPool("entity/ent/jungle", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:entity/ent/jungle"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> OAK_ENT = registerPool("entity/ent/oak", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:entity/ent/oak"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> SPRUCE_ENT = registerPool("entity/ent/spruce", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:entity/ent/spruce"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> WARPED_ENT = registerPool("entity/ent/warped", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:entity/ent/warped"), 1)), StructureTemplatePool.Projection.RIGID));

        public static final Supplier<ResourceKey<StructureTemplatePool>> ACACIA_ENT_TREE_START = registerPool("ent_tree/acacia", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:ent_tree/acacia"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> BIRCH_ENT_TREE_START = registerPool("ent_tree/birch", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:ent_tree/birch"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> CRIMSON_ENT_TREE_START = registerPool("ent_tree/crimson", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:ent_tree/crimson"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> DARK_OAK_ENT_TREE_START = registerPool("ent_tree/dark_oak", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:ent_tree/dark_oak"), 4), Pair.of(StructurePoolElement.single("chaosawakens:ent_tree/dark_oak_rare"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> GINKGO_ENT_TREE_START = registerPool("ent_tree/ginkgo", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:ent_tree/ginkgo"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> JUNGLE_ENT_TREE_START = registerPool("ent_tree/jungle", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:ent_tree/jungle"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> OAK_ENT_TREE_START = registerPool("ent_tree/oak", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:ent_tree/oak"), 1), Pair.of(StructurePoolElement.single("chaosawakens:ent_tree/oak_2"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> SPRUCE_ENT_TREE_START = registerPool("ent_tree/spruce", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:ent_tree/spruce"), 1)), StructureTemplatePool.Projection.RIGID));
        public static final Supplier<ResourceKey<StructureTemplatePool>> WARPED_ENT_TREE_START = registerPool("ent_tree/warped", (b) -> () -> new StructureTemplatePool(b.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), ImmutableList.of(Pair.of(StructurePoolElement.single("chaosawakens:ent_tree/warped"), 1)), StructureTemplatePool.Projection.RIGID));

        private static Supplier<ResourceKey<StructureTemplatePool>> registerPool(String id,  Function<BootstapContext<StructureTemplatePool>, Supplier<StructureTemplatePool>> poolFunction) {
            Supplier<ResourceKey<StructureTemplatePool>> typeSupplier = CAServices.REGISTRAR.registerDatapackObject(CAConstants.prefix(id), poolFunction, Registries.TEMPLATE_POOL);
            TEMPLATE_POOLS.add((Supplier) typeSupplier);
            return typeSupplier;
        }

        public static ImmutableList<Supplier<ResourceKey<StructureTemplatePool>>> getTemplatePools() {
            return ImmutableList.copyOf(TEMPLATE_POOLS);
        }
    }

    @RegistrarEntry
    public static class StructureSets {
        private static final ObjectArrayList<Supplier<ResourceKey<StructureSet>>> STRUCTURE_SETS = new ObjectArrayList<>();

        public static final Supplier<ResourceKey<StructureSet>> ENT_TREES = registerSet("ent_trees", (b) -> createSupplier(b, new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 14357630), Structures.ACACIA_ENT_TREE, Structures.BIRCH_ENT_TREE, Structures.CRIMSON_ENT_TREE, Structures.DARK_OAK_ENT_TREE, Structures.GINKGO_ENT_TREE, Structures.JUNGLE_ENT_TREE, Structures.OAK_ENT_TREE, Structures.SPRUCE_ENT_TREE, Structures.WARPED_ENT_TREE));

        private static Supplier<ResourceKey<StructureSet>> registerSet(String id, Function<BootstapContext<StructureSet>, Supplier<StructureSet>> setFunction) {
            Supplier<ResourceKey<StructureSet>> typeSupplier = CAServices.REGISTRAR.registerDatapackObject(CAConstants.prefix(id), setFunction, Registries.STRUCTURE_SET);
            STRUCTURE_SETS.add((Supplier) typeSupplier);
            return typeSupplier;
        }

        public static ImmutableList<Supplier<ResourceKey<StructureSet>>> getTemplatePools() {
            return ImmutableList.copyOf(STRUCTURE_SETS);
        }

        private static Supplier<StructureSet> createSupplier(BootstapContext<StructureSet> boot , StructurePlacement placement, Supplier<ResourceKey<Structure>>... keys) {
            HolderGetter<Structure> structureHolderGetter = boot.lookup(Registries.STRUCTURE);
            if (keys.length == 1) {
                return () -> new StructureSet(structureHolderGetter.getOrThrow(keys[0].get()), placement);
            }
            return () -> new StructureSet(Arrays.asList(keys).stream().map((supplier) -> StructureSet.entry(structureHolderGetter.getOrThrow(supplier.get()))).collect(Collectors.toUnmodifiableList()), placement);
        }
    }
}
