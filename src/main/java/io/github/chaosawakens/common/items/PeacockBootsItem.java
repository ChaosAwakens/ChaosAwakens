package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class PeacockBootsItem extends ArmorItem {

    private final int[] enchantmentLevels = new int[0];
    private final Enchantment[] enchantmentIds = new Enchantment[0];

    public PeacockBootsItem(IArmorMaterial materialIn, Properties builderIn) {
        super(materialIn, EquipmentSlotType.FEET, builderIn);
    }

    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if (!CAConfig.COMMON.enableAutoEnchanting.get() || enchantmentIds.length < 1) return;
        if (stack.isEnchanted())return;
        for (int i = 0; i < enchantmentIds.length; i++) {
            stack.addEnchantment(enchantmentIds[i], enchantmentLevels[i]);
        }
    }

    public void inventoryTick(ItemStack stack, World worldInD, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!CAConfig.COMMON.enableAutoEnchanting.get() || enchantmentIds.length < 1) return;
        if (stack.isEnchanted())return;
        for (int i = 0; i < enchantmentIds.length; i++) {
            stack.addEnchantment(enchantmentIds[i], enchantmentLevels[i]);
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        if (!player.isOnGround()) player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 200, 0,false,false));
    }

    public boolean hasEffect(ItemStack stack) {
        return enchantmentIds.length > 0 && CAConfig.COMMON.enableAutoEnchanting.get();
    }

}