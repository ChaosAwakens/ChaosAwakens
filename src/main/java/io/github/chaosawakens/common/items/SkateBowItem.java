package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class SkateBowItem extends BowItem {
	public SkateBowItem(Properties builder) {
		super(builder);
	}

	private ItemStack findAmmo(ItemStack shootable, PlayerEntity playerEntity) {
		Predicate<ItemStack> predicate = ((ShootableItem) shootable.getItem()).getSupportedHeldProjectiles();
		ItemStack itemstack = ShootableItem.getHeldProjectile(playerEntity, predicate);
		if (!itemstack.isEmpty()) return itemstack;
		else {
			predicate = ((ShootableItem) shootable.getItem()).getAllSupportedProjectiles();
			for (int i = 0; i < playerEntity.inventory.getContainerSize(); ++i) {
				ItemStack itemstack1 = playerEntity.inventory.getItem(i);
				if (predicate.test(itemstack1)) return itemstack1;
			}
			return playerEntity.abilities.instabuild ? new ItemStack(CAItems.IRUKANDJI_ARROW.get()) : ItemStack.EMPTY;
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			boolean flag = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
			ItemStack itemstack = findAmmo(stack, playerentity);

			int i = this.getUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
			if (i < 0) return;

			if (!itemstack.isEmpty() || flag) {
				if (itemstack.isEmpty()) itemstack = new ItemStack(CAItems.IRUKANDJI_ARROW.get());

				float f = getPowerForTime(i);
				if (!((double) f < 0.1D)) {
					boolean flag1 = playerentity.abilities.instabuild
							|| (itemstack.getItem() instanceof ArrowItem
							&& ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
					if (!worldIn.isClientSide) {
						ArrowItem arrowitem = (ArrowItem) (itemstack.getItem() instanceof ArrowItem
								? itemstack.getItem()
								: CAItems.IRUKANDJI_ARROW.get());
						AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
						abstractarrowentity = customArrow(abstractarrowentity);
						abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F,
								f * 3.0F, 1.0F);

						if (f == 1.0F) abstractarrowentity.setCritArrow(true);

						int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
						if (j > 0) abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + (double) j * 0.5D + 0.5D);

						int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
						if (k > 0) abstractarrowentity.setKnockback(k);

						if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) abstractarrowentity.setSecondsOnFire(100);

						stack.hurtAndBreak(1, playerentity, (player) -> player.broadcastBreakEvent(playerentity.getUsedItemHand()));
						if (flag1 || playerentity.abilities.instabuild
								&& (itemstack.getItem() == Items.SPECTRAL_ARROW
								|| itemstack.getItem() == Items.TIPPED_ARROW || itemstack.getItem() == Items.ARROW)) {
							abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
						}

						worldIn.addFreshEntity(abstractarrowentity);
					}

					worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!flag1 && !playerentity.abilities.instabuild) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) playerentity.inventory.removeItem(itemstack);
					}

					playerentity.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}
}
