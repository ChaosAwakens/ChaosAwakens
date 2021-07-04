package io.github.chaosawakens.items;

import io.github.chaosawakens.items.util.generic.enchanted.GenericEnchantedArmorItem;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RoyaltyBoots extends GenericEnchantedArmorItem {
    public RoyaltyBoots(ArmorMaterial material, Settings settings, EnchantmentLevelEntry[] enchantments) {
        super(material, EquipmentSlot.FEET, settings, enchantments);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) { //why the fuck can i not find onArmorTick() anywhere?
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (livingEntity.getEquippedStack(EquipmentSlot.FEET) == stack) {
                if (!livingEntity.isOnGround()) livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0, false, false));
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
