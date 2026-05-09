package io.github.chaosawakens.content.worldgen.placement_modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.content.registry.CAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class PickLowestHeightPlacementModifier extends PlacementModifier {
    public static final Codec<PickLowestHeightPlacementModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BoundingBox.CODEC.fieldOf("box").forGetter(pick -> pick.box)).apply(instance, PickLowestHeightPlacementModifier::new)
    );
    protected final BoundingBox box;

    public PickLowestHeightPlacementModifier(BoundingBox box) {
        this.box = box;
    }

    @Override
    public @NotNull Stream<BlockPos> getPositions(PlacementContext context, RandomSource source, BlockPos pos) {
        WorldGenLevel level = context.getLevel();
        BlockPos firstCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.minX(), 0, box.minZ()));
        BlockPos secondCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.maxX(), 0, box.minZ()));
        BlockPos thirdCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.minX(), 0, box.maxZ()));
        BlockPos fourthCorner = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos.offset(box.maxX(), 0, box.maxZ()));

        BlockPos offsetPos;
        if (firstCorner.getY() <= secondCorner.getY() && firstCorner.getY() <= thirdCorner.getY() && firstCorner.getY() <= fourthCorner.getY()) {
            offsetPos = pos.atY(firstCorner.getY());
        } else if (secondCorner.getY() <= firstCorner.getY() && secondCorner.getY() <= thirdCorner.getY() && secondCorner.getY() <= fourthCorner.getY()) {
            offsetPos = pos.atY(secondCorner.getY());
        } else if (thirdCorner.getY() <= firstCorner.getY() && thirdCorner.getY() <= secondCorner.getY() && thirdCorner.getY() <= fourthCorner.getY()) {
            offsetPos = pos.atY(thirdCorner.getY());
        } else {
            offsetPos = pos.atY(fourthCorner.getY());
        }

        return Stream.of(offsetPos);
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return CAFeatures.PlacementModifiers.PICK_LOWEST_HEIGHT.get();
    }

    public BoundingBox getBox() {
        return box;
    }
}