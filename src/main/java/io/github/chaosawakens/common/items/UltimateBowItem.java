package io.github.chaosawakens.common.items;

import io.github.chaosawakens.api.IAutoEnchantable;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.entity.projectile.UltimateArrowEntity;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class UltimateBowItem extends BowItem implements IVanishable, IAutoEnchantable {
	private final EnchantmentData[] enchantments;

	public UltimateBowItem(Properties builderIn, EnchantmentData[] enchantments) {
		super(builderIn);
		this.enchantments = enchantments;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (allowdedIn(group)) {
			ItemStack stack = new ItemStack(this);
			if (CACommonConfig.COMMON.enableAutoEnchanting.get()) {
				for (EnchantmentData enchant : enchantments) {
					stack.enchant(enchant.enchantment, enchant.level);
				}
			}
			items.add(stack);
		}
	}

	@Override
	public void onCraftedBy(ItemStack itemStack, World world, PlayerEntity playerEntity) {
		if (CACommonConfig.COMMON.enableAutoEnchanting.get()) {
			for (EnchantmentData enchant : enchantments) {
				itemStack.enchant(enchant.enchantment, enchant.level);
			}
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return CACommonConfig.COMMON.enableAutoEnchanting.get() && super.isFoil(stack) || super.isFoil(stack);
	}

	@Override
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;

			if (ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, this.getUseDuration(stack) - timeLeft, true) < 0) return;
			if (!worldIn.isClientSide) {
				AbstractArrowEntity arrowEntity = new UltimateArrowEntity(worldIn, entityLiving);
				arrowEntity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, 3.0F, 0F);
				arrowEntity.setCritArrow(true);
				arrowEntity.setSecondsOnFire(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0 ? 250 : 75);

				int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
				if (!CACommonConfig.COMMON.enableAutoEnchanting.get()) arrowEntity.setBaseDamage(CACommonConfig.COMMON.ultimateBowArrowBaseDamage.get() + (double) powerLevel * CACommonConfig.COMMON.ultimateBowArrowDamageMultiplier.get() + 2D);
				else arrowEntity.setBaseDamage(CACommonConfig.COMMON.ultimateBowArrowBaseDamage.get() + 3D);

				int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
				arrowEntity.setKnockback(!CACommonConfig.COMMON.enableAutoEnchanting.get() ? k + 1 : 1);

				if (!playerentity.isCreative()) stack.hurtAndBreak(1, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));

				worldIn.addFreshEntity(arrowEntity);
				worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
				playerentity.awardStat(Stats.ITEM_USED.get(this));
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 9000;
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);

		playerIn.startUsingItem(handIn);
		return ActionResult.consume(itemstack);
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return ARROW_ONLY;
	}

	@Override
	public int getDefaultProjectileRange() {
		return 15;
	}

	@Override
	public EnchantmentData[] enchantments() {
		return this.enchantments;
	}
}
