package io.github.chaosawakens.content.block.vegetation.crystalworld;

import com.mememan.nexus.template.object.block.vegetation.ConfigurablePlant;
import io.github.chaosawakens.content.registry.CASoundTypes;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseCoralFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;
import java.util.function.Supplier;

public class CrystalFanBlock extends BaseCoralFanBlock implements ConfigurablePlant {

    public CrystalFanBlock(Properties properties) {
        super(properties.sound(CASoundTypes.CRYSTAL_GRASS));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos basePos = pos.below();
        BlockState baseState = level.getBlockState(basePos);

        return super.canSurvive(state, level, pos) && allowPlacementOn(baseState, level, basePos);
    }

    @Override
    public Set<Supplier<TagKey<Block>>> getValidPlacementTags() {
        return ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL);
    }
}
