package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.registry.CAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

import java.util.function.Supplier;

public class SimpleSupplierStateProvider extends BlockStateProvider {
    public static final Codec<SimpleSupplierStateProvider> CODEC = BlockState.CODEC.fieldOf("state")
            .xmap( state -> new SimpleSupplierStateProvider(() -> state), provider -> provider.state.get()).codec();
    private final Supplier<BlockState> state;

    public SimpleSupplierStateProvider(Supplier<BlockState> state) {
        this.state = state;
    }

    @Override
    protected BlockStateProviderType<?> type() {
        return CAFeatures.StateProviders.SIMPLE_SUPPLIER_PROVIDER.get();
    }

    @Override
    public BlockState getState(RandomSource var1, BlockPos var2) {
        return this.state.get();
    }
}
