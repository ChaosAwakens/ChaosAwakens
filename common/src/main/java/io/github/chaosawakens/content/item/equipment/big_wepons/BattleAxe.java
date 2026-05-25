package io.github.chaosawakens.content.item.equipment.big_wepons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class BattleAxe extends SwordItem {
    public static final int ATTACK_DAMAGE = 249;

    public BattleAxe(Tier tier, int attackDamage, float attackSpeedModifier, Item.Properties properties) {
        super(tier, attackDamage, attackSpeedModifier, properties);
    }
}
