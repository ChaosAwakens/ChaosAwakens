package io.github.chaosawakens.common.blocks;

import java.util.function.Supplier;

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

public class AntInfestedOre extends OreBlock {
	
	private final Supplier<? extends EntityType<?>> ant;
	
    public AntInfestedOre(Supplier<? extends EntityType<?>> ant, Properties properties) {
        super(properties);
        this.ant = ant;
    }

    private void spawnAnt(ServerWorld world, BlockPos pos) {
    	MonsterEntity antEntity =  (MonsterEntity) ant.get().create(world);
    	antEntity.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
        world.addEntity(antEntity);
        antEntity.spawnExplosionParticle();
    }

    public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
        super.spawnAdditionalDrops(state, worldIn, pos, stack);
        if (!worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            for (int index0 = 0; index0 < (25); index0++) {
                this.spawnAnt(worldIn, pos);
            }
        }

    }
}
