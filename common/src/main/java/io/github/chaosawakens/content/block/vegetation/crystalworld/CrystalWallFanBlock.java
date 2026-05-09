package io.github.chaosawakens.content.block.vegetation.crystalworld;

import com.mememan.nexus.template.object.block.vegetation.ConfigurablePlant;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;
import java.util.function.Supplier;

public class CrystalWallFanBlock extends BaseCoralWallFanBlock implements ConfigurablePlant {

    public CrystalWallFanBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction curFacingDir = state.getValue(FACING);
        BlockPos placementPos = pos.relative(curFacingDir.getOpposite());
        BlockState baseState = level.getBlockState(placementPos);

        return super.canSurvive(state, level, pos) && allowPlacementOn(baseState, level, placementPos);
    }

    @Override
    public Set<Supplier<TagKey<Block>>> getValidPlacementTags() {
        return ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL);
    }
}
