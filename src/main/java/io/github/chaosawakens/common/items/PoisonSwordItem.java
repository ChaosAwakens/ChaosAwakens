package io.github.chaosawakens.common.items;

import io.github.chaosawakens.api.EnchantmentAndLevel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class PoisonSwordItem extends EnchantedSwordItem {

    public PoisonSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentAndLevel[] enchantments) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target != null) {
            target.addPotionEffect(new EffectInstance(Effects.POISON,(10 + target.world.rand.nextInt(10))*20,0));
            target.addPotionEffect(new EffectInstance(Effects.WITHER,(10 + target.world.rand.nextInt(10))*20,0));
            target.addPotionEffect(new EffectInstance(Effects.WEAKNESS,(10 + target.world.rand.nextInt(10))*20,0));
        }
        return true;
    }
}

