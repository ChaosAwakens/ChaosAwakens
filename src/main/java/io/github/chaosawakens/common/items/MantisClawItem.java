package io.github.chaosawakens.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.world.NoteBlockEvent;

public class MantisClawItem extends SwordItem {

    public MantisClawItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int damageMultiplier = attacker.getHealth() >= attacker.getMaxHealth() ? 2 : 1;
        if (target != null && !target.world.isRemote) {
            attacker.heal(1F);
            target.attackEntityFrom(attacker instanceof PlayerEntity ? DamageSource.causePlayerDamage((PlayerEntity) attacker) : DamageSource.causeMobDamage(attacker), 1F);
        }
        stack.damageItem(damageMultiplier, attacker, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }
}
