package io.github.chaosawakens.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

import java.util.function.Supplier;

public class AntInfestedOre extends OreBlock {

    private final Supplier<? extends EntityType<?>> ant;

    public AntInfestedOre(Supplier<? extends EntityType<?>> ant, Properties properties) {
        super(properties);
        this.ant = ant;
    }

    private void spawnAnt(ServerWorld world, BlockPos pos) {
        MonsterEntity antEntity = (MonsterEntity) ant.get().create(world);
        assert antEntity != null;
        antEntity.moveTo((double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
        world.addFreshEntity(antEntity);
        antEntity.spawnAnim();
    }

    public void spawnAfterBreak(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
        super.spawnAfterBreak(state, worldIn, pos, stack);
        if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            for (int index0 = 0; index0 < 20; index0++) {
                this.spawnAnt(worldIn, pos);
            }
        }

    }
}
