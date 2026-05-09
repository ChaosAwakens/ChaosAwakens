package io.github.chaosawakens.content.worldgen.level.tree_grower;

import io.github.chaosawakens.content.worldgen.feature.NBTTreeFeature;
import io.github.chaosawakens.content.worldgen.feature.configuration.NBTTreeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
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
    protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource source, boolean hasFlowers) {
        return trees.get(source.nextInt(trees.size())).get();
    }

    @Override
    public boolean growTree(ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState state, RandomSource random) {
        ResourceKey<ConfiguredFeature<?, ?>> key = getConfiguredFeature(random, false);

        if (key == null) return false;
        else {
            Holder<ConfiguredFeature<?, ?>> holder = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(key).orElse(null);

            if (holder == null) return false;
            else {
                ConfiguredFeature<NBTTreeConfiguration, NBTTreeFeature> feature = (ConfiguredFeature<NBTTreeConfiguration, NBTTreeFeature>) holder.value();
                BlockState fluidBlockState = level.getFluidState(pos).createLegacyBlock();
                level.setBlock(pos, fluidBlockState, Block.UPDATE_INVISIBLE);

                StructureTemplate template = level.getStructureManager().get(feature.config().template().left().get()).get();

                if (feature.place(level, generator, random, pos.offset(template.getSize().getX() / -2, 0, template.getSize().getZ() / -2))) {
                    return true;
                } else {
                    if (level.getBlockState(pos) == fluidBlockState) level.sendBlockUpdated(pos, state, fluidBlockState, Block.UPDATE_CLIENTS);

                    return false;
                }
            }
        }
    }
}