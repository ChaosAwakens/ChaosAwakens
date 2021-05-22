package io.github.chaosawakens.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class BigHammerItem extends SwordItem {

    public BigHammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target != null && !target.world.isRemote) {
            target.addVelocity(0.0D, Math.abs(target.world.rand.nextFloat() * 2.0F / 3.0F), 0.0D);
        }
        return super.hitEntity(stack, target, attacker);
    }
}
