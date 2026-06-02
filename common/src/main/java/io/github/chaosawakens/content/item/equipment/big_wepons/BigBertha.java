package io.github.chaosawakens.content.item.equipment.big_wepons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class BigBertha extends SwordItem {
    public static final int ATTACK_DAMAGE = 499;

    public BigBertha(Tier tier, int attackDamage, float attackSpeedModifier, Item.Properties properties){
        super(tier, attackDamage, attackSpeedModifier, properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
