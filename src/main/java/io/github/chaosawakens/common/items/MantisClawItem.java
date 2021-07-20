package io.github.chaosawakens.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.world.NoteBlockEvent;

import net.minecraft.item.Item.Properties;

public class MantisClawItem extends SwordItem {

    public MantisClawItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int damageMultiplier = attacker.getHealth() >= attacker.getMaxHealth() ? 2 : 1;
        if (target != null && !target.level.isClientSide) {
            attacker.heal(1F);
            target.hurt(attacker instanceof PlayerEntity ? DamageSource.playerAttack((PlayerEntity) attacker) : DamageSource.mobAttack(attacker), 1F);
        }
        stack.hurtAndBreak(damageMultiplier, attacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        return true;
    }
}
