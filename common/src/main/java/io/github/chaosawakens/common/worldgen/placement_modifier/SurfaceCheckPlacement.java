package io.github.chaosawakens.common.worldgen.placement_modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.common.registry.CAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class SurfaceCheckPlacement extends PlacementModifier {
    public static final Codec<SurfaceCheckPlacement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("state").forGetter(check -> check.provider)).apply(instance, SurfaceCheckPlacement::new)
    );

    public final BlockStateProvider provider;

    public SurfaceCheckPlacement(BlockStateProvider provider) {
        this.provider = provider;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource source, BlockPos pos) {
        return provider.getState(source, pos).equals(context.getBlockState(pos.below())) ? Stream.empty(): Stream.of(pos);
    }

    @Override
    public PlacementModifierType<?> type() {
        return CAFeatures.PlacementModifiers.SURFACE_CHECK.get();
    }
}
