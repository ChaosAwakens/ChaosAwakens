package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.entity.TermiteEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

public class TermiteInfestedOre extends OreBlock {
    public TermiteInfestedOre(Properties properties) {
        super(properties);
    }

    private void spawnTermite(ServerWorld world, BlockPos pos) {
        TermiteEntity termiteentity = CAEntityTypes.TERMITE.get().create(world);
        assert termiteentity != null;
        termiteentity.setLocationAndAngles((double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
        world.addEntity(termiteentity);
        termiteentity.spawnExplosionParticle();
    }

    public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
        super.spawnAdditionalDrops(state, worldIn, pos, stack);
        if (!worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            for (int index0 = 0; index0 < (25); index0++) {
                this.spawnTermite(worldIn, pos);
            }
        }
    }
}
