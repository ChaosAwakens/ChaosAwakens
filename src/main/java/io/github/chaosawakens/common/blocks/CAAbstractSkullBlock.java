package io.github.chaosawakens.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CAAbstractSkullBlock extends ContainerBlock implements IArmorVanishable {
    private final CASkullBlock.ISkullType skullType;

    public CAAbstractSkullBlock(CASkullBlock.ISkullType iSkullType, AbstractBlock.Properties properties) {
        super(properties);
        this.skullType = iSkullType;
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new SkullTileEntity();
    }

    @OnlyIn(Dist.CLIENT)
    public CASkullBlock.ISkullType getSkullType() {
        return this.skullType;
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

}