package io.github.chaosawakens.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;

public class MantisClaw extends SwordItem {
    public MantisClaw(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int damageMultiplier = attacker.getHealth() >= attacker.getMaxHealth() ? 2 : 1;
        if (target != null && !target.world.isClient()) {
            attacker.heal(1F);
            target.damage(attacker instanceof PlayerEntity ? DamageSource.player((PlayerEntity) attacker) : DamageSource.mob(attacker), 1F);
        }
        stack.damage(damageMultiplier, attacker, (entity) -> {
            entity.sendToolBreakStatus(Hand.MAIN_HAND);
        });
        return true;
    }
}
