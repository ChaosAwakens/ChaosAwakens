package io.github.chaosawakens.common.worldgen.level;

import io.github.chaosawakens.common.worldgen.feature.NBTTreeFeature;
import io.github.chaosawakens.common.worldgen.feature.RandomKeySelectorFeature;
import io.github.chaosawakens.common.worldgen.feature.WeightedPlacedFeatureKey;
import io.github.chaosawakens.common.worldgen.feature.configurations.NBTTreeConfiguration;
import io.github.chaosawakens.common.worldgen.feature.configurations.RandomFeatureKeyConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class NBTTreeGrower extends AbstractTreeGrower {

    public final List<Supplier<ResourceKey<ConfiguredFeature<?, ?>>>> trees;

    public NBTTreeGrower(List<Supplier<ResourceKey<ConfiguredFeature<?, ?>>>> trees) {
        this.trees = trees;
    }

    @Override
    protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource source, boolean b) {
        return trees.get(source.nextInt(trees.size())).get();
    }

    @Override
    public boolean growTree(ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState state, RandomSource random) {
        ResourceKey<ConfiguredFeature<?, ?>> key = this.getConfiguredFeature(random, false);
        if (key == null) {
            return false;
        } else {
            Holder<ConfiguredFeature<?, ?>> holder = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(key).orElse(null);
            if (holder == null) {
                return false;
            } else {
                ConfiguredFeature<NBTTreeConfiguration, NBTTreeFeature> feature = (ConfiguredFeature<NBTTreeConfiguration, NBTTreeFeature>) holder.value();
                BlockState blockState = level.getFluidState(pos).createLegacyBlock();
                level.setBlock(pos, blockState, 4);

                StructureTemplate template = level.getStructureManager().get(feature.config().template().left().get()).get();

                if (!feature.place(level, generator, random, pos.offset(template.getSize().getX() / -2, 0, template.getSize().getZ() / -2))) {
                    if (level.getBlockState(pos) == blockState) {
                        level.sendBlockUpdated(pos, state, blockState, 2);
                    }
                    return true;
                } else {
                    level.setBlock(pos, state, 4);
                    return false;
                }
            }
        }
    }
}
