package io.github.chaosawakens.common.worldgen.placement_modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.common.registry.CAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.stream.Stream;

public class SurfaceAreaCheckPlacement extends PlacementModifier {
    public static final Codec<SurfaceAreaCheckPlacement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("state").forGetter(check -> check.provider),
            BoundingBox.CODEC.fieldOf("box").forGetter(check -> check.box)).apply(instance, SurfaceAreaCheckPlacement::new)
    );

    public final BlockStateProvider provider;
    public final BoundingBox box;

    public SurfaceAreaCheckPlacement(BlockStateProvider provider, BoundingBox box) {
        this.provider = provider;
        this.box = box;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource source, BlockPos pos) {
        WorldGenLevel level = context.getLevel();
        BlockPos firstCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.minX(), 0, box.minZ()));
        BlockPos secondCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.maxX(), 0, box.minZ()));
        BlockPos thirdCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.minX(), 0, box.maxZ()));
        BlockPos fourthCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.maxX(), 0, box.maxZ()));

        if (!provider.getState(source, pos).equals(level.getBlockState(firstCorner.below()))) return Stream.empty();
        if (!provider.getState(source, pos).equals(level.getBlockState(secondCorner.below()))) return Stream.empty();
        if (!provider.getState(source, pos).equals(level.getBlockState(thirdCorner.below()))) return Stream.empty();
        if (!provider.getState(source, pos).equals(level.getBlockState(fourthCorner.below()))) return Stream.empty();

        return Stream.of(pos);
    }

    @Override
    public PlacementModifierType<?> type() {
        return CAFeatures.PlacementModifiers.SURFACE_AREA_CHECK.get();
    }
}
