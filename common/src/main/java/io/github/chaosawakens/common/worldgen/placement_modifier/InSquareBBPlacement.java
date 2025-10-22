package io.github.chaosawakens.common.worldgen.placement_modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.common.registry.CAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class InSquareBBPlacement extends PlacementModifier {
    public static final Codec<InSquareBBPlacement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.intRange(0, 32).fieldOf("x").forGetter(place -> place.x),
            Codec.intRange(0, 32).fieldOf("z").forGetter(place -> place.z)
    ).apply(instance, InSquareBBPlacement::new));

    public final int x;
    public final int z;

    public InSquareBBPlacement(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource randomSource, BlockPos pos) {
        int posX = 32 - x > 0 ? randomSource.nextInt(32 - x) + pos.getX() : pos.getX();
        int posZ = 32 - z > 0 ? randomSource.nextInt(32 - z) + pos.getZ() : pos.getZ();
        return Stream.of(new BlockPos(posX, pos.getY(), posZ));
    }

    @Override
    public PlacementModifierType<?> type() {
        return CAFeatures.PlacementModifiers.IN_BB_SQUARE.get();
    }
}
