package io.github.chaosawakens.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;

public class BigHammer extends SwordItem {
    public BigHammer(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target != null && !target.world.isClient()) {
            target.addVelocity(0.0D, Math.abs(target.world.random.nextFloat() * 1.5F), 0.0D);
        }
        stack.damage(1, attacker, (entity) -> {
            entity.sendToolBreakStatus(Hand.MAIN_HAND);
        });
        return true;
    }
}
