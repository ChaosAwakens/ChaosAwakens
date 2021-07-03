package io.github.chaosawakens.common.items;

import io.github.chaosawakens.api.IPreEnchanted;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.entity.projectile.UltimateFishingBobberEntity;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class UltimateFishingRodItem extends FishingRodItem implements IPreEnchanted {

    private final EnchantmentData[] enchantments;

    public UltimateFishingRodItem(Properties builder, EnchantmentData[] enchantments) {
        super(builder);
        this.enchantments = enchantments;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack stack = new ItemStack(this);
            if (CAConfig.COMMON.enableAutoEnchanting.get())
                for(EnchantmentData enchant : enchantments) {
                    stack.addEnchantment( enchant.enchantment, enchant.enchantmentLevel);
                }
            items.add(stack);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (playerIn.fishingBobber != null) {
            if (!worldIn.isRemote) {
                int i = playerIn.fishingBobber.handleHookRetraction(itemstack);
                itemstack.damageItem(i, playerIn, (player) -> {
                    player.sendBreakAnimation(handIn);
                });
            }

            worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        } else {
            worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            if (!worldIn.isRemote) {
                int k = EnchantmentHelper.getFishingSpeedBonus(itemstack);
                int j = EnchantmentHelper.getFishingLuckBonus(itemstack);
                worldIn.addEntity(new UltimateFishingBobberEntity(playerIn, worldIn, j, k));
            }

            playerIn.addStat(Stats.ITEM_USED.get(this));
        }

        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }

    @Override
    public EnchantmentData[] enchant() {
        return this.enchantments;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return CAConfig.COMMON.enableAutoEnchanting.get() || super.hasEffect(stack);
    }

}
