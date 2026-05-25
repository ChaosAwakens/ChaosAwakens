package io.github.chaosawakens.content.item.equipment.big_wepons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class QueenBattleAxe extends SwordItem {
    public static final int ATTACK_DAMAGE = 665;

    public QueenBattleAxe(Tier tier, int attackDamage, float attackSpeedModifier, Item.Properties properties) {
        super(tier, attackDamage, attackSpeedModifier, properties);
    }
}
