package io.github.chaosawakens.common.worldgen.level;

import io.github.chaosawakens.common.worldgen.feature.NBTTreeFeature;
import io.github.chaosawakens.common.worldgen.feature.configurations.NBTTreeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class MegaNBTTreeGrower extends AbstractMegaTreeGrower {

    public final List<Supplier<ResourceKey<ConfiguredFeature<?, ?>>>> megaTrees;
    public final List<Supplier<ResourceKey<ConfiguredFeature<?, ?>>>> trees;

    public MegaNBTTreeGrower(List<Supplier<ResourceKey<ConfiguredFeature<?, ?>>>> megaTrees, List<Supplier<ResourceKey<ConfiguredFeature<?, ?>>>> trees) {
        this.megaTrees = megaTrees;
        this.trees = trees;
    }

    @Override
    protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource source) {
        return megaTrees.get(source.nextInt(megaTrees.size())).get();
    }

    @Override
    protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource source, boolean b) {
        return trees.isEmpty() ? trees.get(source.nextInt(trees.size())).get() : null;
    }

    public boolean placeMega(ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState state, RandomSource random, int pBranchX, int pBranchY) {
        ResourceKey<ConfiguredFeature<?, ?>> key = this.getConfiguredMegaFeature(random);
        if (key == null) {
            return false;
        } else {
            Holder<ConfiguredFeature<?, ?>> holder = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(key).orElse(null);
            if (holder == null) {
                return false;
            } else {
                ConfiguredFeature<NBTTreeConfiguration, NBTTreeFeature> feature = (ConfiguredFeature<NBTTreeConfiguration, NBTTreeFeature>) holder.value();
                BlockState blockState = Blocks.AIR.defaultBlockState();
                level.setBlock(pos.offset(pBranchX, 0, pBranchY), blockState, 4);
                level.setBlock(pos.offset(pBranchX + 1, 0, pBranchY), blockState, 4);
                level.setBlock(pos.offset(pBranchX, 0, pBranchY + 1), blockState, 4);
                level.setBlock(pos.offset(pBranchX + 1, 0, pBranchY + 1), blockState, 4);

                StructureTemplate template = level.getStructureManager().get(feature.config().template.left().get()).get();

                if (feature.place(level, generator, random, pos.offset(template.getSize().getX() / -2, 0, template.getSize().getZ() / -2))) {
                    return true;
                } else {
                    level.setBlock(pos.offset(pBranchX, 0, pBranchY), state, 4);
                    level.setBlock(pos.offset(pBranchX + 1, 0, pBranchY), state, 4);
                    level.setBlock(pos.offset(pBranchX, 0, pBranchY + 1), state, 4);
                    level.setBlock(pos.offset(pBranchX + 1, 0, pBranchY + 1), state, 4);
                    return false;
                }
            }
        }
    }
}
