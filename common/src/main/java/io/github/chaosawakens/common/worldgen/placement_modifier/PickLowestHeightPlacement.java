package io.github.chaosawakens.common.worldgen.placement_modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.common.registry.CAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.stream.Stream;

public class PickLowestHeightPlacement extends PlacementModifier {
    public static final Codec<PickLowestHeightPlacement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BoundingBox.CODEC.fieldOf("box").forGetter(pick -> pick.box),
            Codec.intRange(0, 100).fieldOf("max_y_difference").forGetter(pick -> pick.maxYDifference))
            .apply(instance, PickLowestHeightPlacement::new)
    );

    public final BoundingBox box;
    public final int maxYDifference;

    public PickLowestHeightPlacement(BoundingBox box, int maxYDifference) {
        this.box = box;
        this.maxYDifference = maxYDifference;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource source, BlockPos pos) {
        WorldGenLevel level = context.getLevel();

        BlockPos firstCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.minX(), 0, box.minZ()));
        BlockPos secondCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.maxX(), 0, box.minZ()));
        BlockPos thirdCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.minX(), 0, box.maxZ()));
        BlockPos fourthCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.maxX(), 0, box.maxZ()));

        BlockPos lowestPos;
        if (firstCorner.getY() <= secondCorner.getY() && firstCorner.getY() <= thirdCorner.getY() && firstCorner.getY() <= fourthCorner.getY()) {
            lowestPos = pos.atY(firstCorner.getY());
        } else if (secondCorner.getY() <= firstCorner.getY() && secondCorner.getY() <= thirdCorner.getY() && secondCorner.getY() <= fourthCorner.getY()) {
            lowestPos = pos.atY(secondCorner.getY());
        } else if (thirdCorner.getY() <= firstCorner.getY() && thirdCorner.getY() <= secondCorner.getY() && thirdCorner.getY() <= fourthCorner.getY()) {
            lowestPos = pos.atY(thirdCorner.getY());
        } else {
            lowestPos = pos.atY(fourthCorner.getY());
        }

        BlockPos highestPos;
        if (firstCorner.getY() >= secondCorner.getY() && firstCorner.getY() >= thirdCorner.getY() && firstCorner.getY() >= fourthCorner.getY()) {
            highestPos = pos.atY(firstCorner.getY());
        } else if (secondCorner.getY() >= firstCorner.getY() && secondCorner.getY() >= thirdCorner.getY() && secondCorner.getY() >= fourthCorner.getY()) {
            highestPos = pos.atY(secondCorner.getY());
        } else if (thirdCorner.getY() >= firstCorner.getY() && thirdCorner.getY() >= secondCorner.getY() && thirdCorner.getY() >= fourthCorner.getY()) {
            highestPos = pos.atY(thirdCorner.getY());
        } else {
            highestPos = pos.atY(fourthCorner.getY());
        }

        if (highestPos.getY() - lowestPos.getY() > this.maxYDifference) {
            return Stream.empty();
        }

        return Stream.of(lowestPos);
    }

    @Override
    public PlacementModifierType<?> type() {
        return CAFeatures.PlacementModifiers.PICK_LOWEST_HEIGHT.get();
    }
}
