package io.github.chaosawakens.common.util;

import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.ChaosAwakens;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import javax.annotation.Nullable;
import java.util.List;

public final class StructureUtil {

    private StructureUtil() {
        throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
    }

    /**
     * Overloaded variant of {@link #addProcessedBuildingToPool(MutableRegistry, ResourceLocation, ResourceLocation, int, StructureProcessorList)}, with the {@code structureProcessorList} set to {@code null}.
     *
     * @param templatePoolRegistry The target template pool registry to add the given structure to. You can usually grab this from events, like in {@link FMLServerAboutToStartEvent#getServer()}
     *                             (@{@link MinecraftServer#registryAccess()}).
     * @param poolRL The {@link ResourceLocation} of the target template pool to add the specified structure to. For instance, {@code "minecraft:village/desert/houses"}.
     * @param nbtPieceRL The {@link ResourceLocation} of the structure's NBT to add to the specified template pool. Already points to {@code data/structures} by default.
     * @param weight The weight of the structure to add to the specified template pool.
     */
    public static void addBuildingToPool(MutableRegistry<JigsawPattern> templatePoolRegistry, ResourceLocation poolRL, ResourceLocation nbtPieceRL, int weight) {
        addProcessedBuildingToPool(templatePoolRegistry, poolRL, nbtPieceRL, weight, null);
    }

    /**
     * Slightly modified implementation of <a href="https://gist.github.com/TelepathicGrunt/4fdbc445ebcbcbeb43ac748f4b18f342">TelepathicGrunt's Jigsaw Pool Implementation</a>. Attempts to (safely) add a given structure to
     * the specified template pool, with the specified weight. Supports ({@code nullable}) {@linkplain StructureProcessorList StructureProcessorLists}.
     *
     * @param templatePoolRegistry The target template pool registry to add the given structure to. You can usually grab this from events, like in {@link FMLServerAboutToStartEvent#getServer()}
     *                             (@{@link MinecraftServer#registryAccess()}).
     * @param poolRL The {@link ResourceLocation} of the target template pool to add the specified structure to. For instance, {@code "minecraft:village/desert/houses"}.
     * @param nbtPieceRL The {@link ResourceLocation} of the structure's NBT to add to the specified template pool. Already points to {@code data/structures} by default.
     * @param weight The weight of the structure to add to the specified template pool.
     * @param structureProcessor The {@link StructureProcessorList} to add to the specified template pool. If {@code null}, the structure will be added on its own without any attached processors.
     */
    public static void addProcessedBuildingToPool(MutableRegistry<JigsawPattern> templatePoolRegistry, ResourceLocation poolRL, ResourceLocation nbtPieceRL, int weight, @Nullable StructureProcessorList structureProcessor) {
        JigsawPattern pool = templatePoolRegistry.get(poolRL);

        ChaosAwakens.debug(structureProcessor == null ? "Adding structure to template pool" : "Adding processed structure to template pool", nbtPieceRL + " || " + poolRL);

        if (pool == null) {
            ChaosAwakens.LOGGER.warn(structureProcessor == null ? "Attempted to add a structure to an unknown template pool: " : "Attempted to add processed structure to an unknown template pool: " + poolRL.toString());
            return;
        }

        SingleJigsawPiece parsedCompiledPiece = structureProcessor != null ? SingleJigsawPiece.single(nbtPieceRL.toString(), structureProcessor).apply(JigsawPattern.PlacementBehaviour.RIGID) : SingleJigsawPiece.single(nbtPieceRL.toString()).apply(JigsawPattern.PlacementBehaviour.RIGID);

        for (int i = 0; i < weight; i++) pool.templates.add(parsedCompiledPiece);

        List<Pair<JigsawPiece, Integer>> rawPoolEntries = new ObjectArrayList<>(pool.rawTemplates);

        rawPoolEntries.add(new Pair<>(parsedCompiledPiece, weight));

        pool.rawTemplates = rawPoolEntries;
    }
}
