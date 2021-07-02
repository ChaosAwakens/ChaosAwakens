package io.github.chaosawakens.items;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.DataTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class MinersDream extends Item {
    private static final int HOLE_LENGTH = 36;
    private static final int HOLE_WIDTH = 4;
    private static final int HOLE_HEIGHT = 8;

    public MinersDream(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction direction = context.getPlayerFacing();
        World world = context.getWorld();

        if (world.isClient()) return ActionResult.FAIL;
        if (direction == Direction.UP || direction == Direction.DOWN) return ActionResult.FAIL;

        BlockPos blockPos = context.getBlockPos();
        int targetY = blockPos.getY();
        PlayerEntity playerIn = context.getPlayer();
        ChaosAwakens.debug("AA", targetY);
        Vec3i facing = direction.getVector();

        playerIn.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.5F);
        world.addParticle(ParticleTypes.EXPLOSION.getType(), blockPos.getX(), targetY, blockPos.getZ(), 0.25F, 0.25F, 0.25F);

        for (int i = 0; i < HOLE_LENGTH; i++) {
            for (int j = 0; j < HOLE_HEIGHT; j++) {
                for (int k = -HOLE_WIDTH ; k <= HOLE_WIDTH; k++) {
                    int lengthDelta = i * facing.getX() + k * facing.getZ();
                    int widthDelta = i * facing.getZ() + k * facing.getX();
                    BlockPos targetPos = blockPos.add(lengthDelta, -targetY + j, widthDelta);
                    BlockState targetState = world.getBlockState(targetPos);
                    if(targetState.isIn(DataTags.MINERS_DREAM_MINEABLE)) {
                        this.placeWoodPillars(world, targetPos, i, j, k);
                    }
                }
            }
        }
        context.getPlayer().incrementStat(Stats.USED.getOrCreateStat(this));
        context.getStack().decrement(1);

        return ActionResult.SUCCESS;
    }
    private void placeWoodPillars(World world, BlockPos blockPos, int i, int j, int k) {
        if(i != 0 && i % 8 == 0) {
            if(k == -HOLE_WIDTH || k == HOLE_WIDTH) {
                if(j == HOLE_HEIGHT-1) {
                    world.setBlockState(blockPos, Blocks.OAK_PLANKS.getDefaultState());
                    return;
                }
                world.setBlockState(blockPos, Blocks.OAK_FENCE.getDefaultState());
                return;
            }

            if (j == HOLE_HEIGHT-1) {
                if(k == 0) {
                    world.setBlockState(blockPos, Blocks.GLOWSTONE.getDefaultState());
                    return;
                }
                world.setBlockState(blockPos, Blocks.OAK_SLAB.getDefaultState().with(Properties.SLAB_TYPE, SlabType.TOP));
                return;
            }
        }
        world.removeBlock(blockPos, false);
    }

}
