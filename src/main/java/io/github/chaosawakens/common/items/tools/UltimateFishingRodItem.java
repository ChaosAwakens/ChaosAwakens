package io.github.chaosawakens.common.items.tools;

import io.github.chaosawakens.api.item.IAutoEnchantable;
import io.github.chaosawakens.common.entity.projectile.bobber.UltimateFishingBobberEntity;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class UltimateFishingRodItem extends FishingRodItem implements IAutoEnchantable {
	private final Supplier<EnchantmentData[]> enchantments;

	public UltimateFishingRodItem(Properties builder, Supplier<EnchantmentData[]> enchantments) {
		super(builder);
		this.enchantments = enchantments;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (allowdedIn(group)) {
			ItemStack fishingRodStack = new ItemStack(this);
			
			if (CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get()) {
				for (EnchantmentData enchant : enchantments.get()) {
					fishingRodStack.enchant(enchant.enchantment, enchant.level);
				}
			}
			items.add(fishingRodStack);
		}
	}

	@Override
	public void onCraftedBy(ItemStack targetStack, World world, PlayerEntity playerEntity) {
		if (CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get()) {
			for (EnchantmentData enchant : enchantments.get()) {
				targetStack.enchant(enchant.enchantment, enchant.level);
			}
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get() && super.isFoil(stack) || super.isFoil(stack);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack curHeldStack = playerIn.getItemInHand(handIn);
		if (playerIn.fishing != null) {
			if (!worldIn.isClientSide) {
				int retrievedItemCount = playerIn.fishing.retrieve(curHeldStack);
				curHeldStack.hurtAndBreak(retrievedItemCount, playerIn, (player) -> player.broadcastBreakEvent(handIn));
			}

			worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		} else {
			worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

			if (!worldIn.isClientSide) {
				int lureMod = EnchantmentHelper.getFishingSpeedBonus(curHeldStack) + 60;
				int luckMod = EnchantmentHelper.getFishingLuckBonus(curHeldStack) + 50;

				if (worldIn.dimension() == CADimensions.MINING_PARADISE) {
					lureMod = EnchantmentHelper.getFishingSpeedBonus(curHeldStack) + 20;
					luckMod = EnchantmentHelper.getFishingLuckBonus(curHeldStack) + 40;
				}

				worldIn.addFreshEntity(new UltimateFishingBobberEntity(playerIn, worldIn, luckMod, lureMod));
			}

			playerIn.awardStat(Stats.ITEM_USED.get(this));
		}

		return ActionResult.sidedSuccess(curHeldStack, worldIn.isClientSide());
	}

	@Override
	public EnchantmentData[] getEnchantments() {
		return this.enchantments.get();
	}
}
