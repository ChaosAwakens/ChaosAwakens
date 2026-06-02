package io.github.chaosawakens.content.item.equipment.big_wepons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class RoyalGuardian extends SwordItem {
    public static final int ATTACK_DAMAGE = 749;

    public RoyalGuardian(Tier tier, int attackDamage, float attackSpeedModifier, Item.Properties properties) {
        super(tier, attackDamage, attackSpeedModifier, properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
