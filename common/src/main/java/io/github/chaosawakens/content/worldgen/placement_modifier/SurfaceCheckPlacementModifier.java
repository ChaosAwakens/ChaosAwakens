package io.github.chaosawakens.content.worldgen.placement_modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.content.registry.CAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class SurfaceCheckPlacementModifier extends PlacementModifier {
    public static final Codec<SurfaceCheckPlacementModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("state").forGetter(check -> check.provider)).apply(instance, SurfaceCheckPlacementModifier::new)
    );
    protected final BlockStateProvider provider;

    public SurfaceCheckPlacementModifier(BlockStateProvider provider) {
        this.provider = provider;
    }

    @Override
    public @NotNull Stream<BlockPos> getPositions(PlacementContext context, RandomSource source, BlockPos pos) {
        return provider.getState(source, pos).equals(context.getBlockState(pos.below())) ? Stream.empty() : Stream.of(pos);
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return CAFeatures.PlacementModifiers.SURFACE_CHECK.get();
    }

    public BlockStateProvider getProvider() {
        return provider;
    }
}