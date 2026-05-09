package io.github.chaosawakens.content.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.content.registry.CAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SimpleSupplierStateProvider extends BlockStateProvider {
    public static final Codec<SimpleSupplierStateProvider> CODEC = BlockState.CODEC.fieldOf("state")
            .xmap(state -> new SimpleSupplierStateProvider(() -> state), provider -> provider.state.get()).codec();
    private final Supplier<BlockState> state;

    public SimpleSupplierStateProvider(Supplier<BlockState> state) {
        this.state = state;
    }

    @Override
    protected @NotNull BlockStateProviderType<?> type() {
        return CAFeatures.StateProviders.SIMPLE_SUPPLIER_PROVIDER.get();
    }

    @Override
    public @NotNull BlockState getState(RandomSource randomSource, BlockPos blockPos) {
        return state.get();
    }
}