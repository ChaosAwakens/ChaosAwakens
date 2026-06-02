package io.github.chaosawakens.content.item.equipment.big_wepons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class ChainSaw extends SwordItem {
    public static final int ATTACK_DAMAGE = 49;

    public ChainSaw(Tier tier, int attackDamage, float attackSpeedModifier, Item.Properties properties) {
        super(tier, attackDamage, attackSpeedModifier, properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
    /*
    public class Behavior {
        public static chainMining() {
            // TODO
            // give item properties of axe mineabels and mining speed
            // adjacent wood-type-family blocks breaks along with the directed block
        }

        // also another method for overiding the mining animation
        public static miningAnimationOverride() {
            // TODO
            // apply animation clip with animation controller
            // apply sound event while mining
        }
    }
     */
}
