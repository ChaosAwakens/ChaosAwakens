package io.github.chaosawakens.common.items.projectile;

import io.github.chaosawakens.common.entity.projectile.arrow.UltimateCrossbowBoltEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UltimateCrossbowBoltItem extends ArrowItem {

    public UltimateCrossbowBoltItem(Properties properties) {
        super(properties);
    }

    @Override
    public UltimateCrossbowBoltEntity createArrow(World pLevel, ItemStack pStack, LivingEntity pShooter) {
        UltimateCrossbowBoltEntity ultimateCrossbowBolt = new UltimateCrossbowBoltEntity(pLevel, pShooter);
        ultimateCrossbowBolt.setCritArrow(true);

        return ultimateCrossbowBolt;
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, PlayerEntity player) {
        return super.isInfinite(stack, bow, player);
    }
}
