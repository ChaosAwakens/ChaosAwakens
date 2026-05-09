package io.github.chaosawakens.content.worldgen.placement_modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.content.registry.CAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class InSquareBBPlacementModifier extends PlacementModifier {
    public static final Codec<InSquareBBPlacementModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.intRange(0, 32).fieldOf("x").forGetter(place -> place.x),
            Codec.intRange(0, 32).fieldOf("z").forGetter(place -> place.z)
    ).apply(instance, InSquareBBPlacementModifier::new));
    protected final int x;
    protected final int z;

    public InSquareBBPlacementModifier(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public @NotNull Stream<BlockPos> getPositions(PlacementContext context, RandomSource randomSource, BlockPos pos) {
        int posX = 32 - x > 0 ? randomSource.nextInt(32 - x) + pos.getX() : pos.getX();
        int posZ = 32 - z > 0 ? randomSource.nextInt(32 - z) + pos.getZ() : pos.getZ();

        return Stream.of(new BlockPos(posX, pos.getY(), posZ));
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return CAFeatures.PlacementModifiers.IN_BB_SQUARE.get();
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}