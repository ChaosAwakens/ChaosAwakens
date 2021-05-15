package io.github.chaosawakens.blocks;

import io.github.chaosawakens.enums.BossVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class CASpawnerBlock extends Block {

    public static final EnumProperty<BossVariant> VARIANT = EnumProperty.create("boss", BossVariant.class);

    public CASpawnerBlock(Block.Properties props) {
        super(props);
        this.setDefaultState(stateContainer.getBaseState().with(VARIANT, BossVariant.ENT));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(VARIANT);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.get(VARIANT).hasSpawner();
    }

    @Override
    @Nullable
    public TileEntity createTileEntity(BlockState state, IBlockReader reader) {
        return state.get(VARIANT).getSpawner();
    }

    @Override
    public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity) {
        return state.getBlockHardness(world, pos) >= 0f;
    }
}