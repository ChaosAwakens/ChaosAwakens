package io.github.chaosawakens.common.items.weapons.ranged;

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
		Predicate<ItemStack> supportedProjectilesPredicate = ((ShootableItem) shootable.getItem()).getSupportedHeldProjectiles();
		ItemStack heldProjectileStack = ShootableItem.getHeldProjectile(playerEntity, supportedProjectilesPredicate);

		if (!heldProjectileStack.isEmpty()) return heldProjectileStack;
		else {
			supportedProjectilesPredicate = ((ShootableItem) shootable.getItem()).getAllSupportedProjectiles();

			for (int i = 0; i < playerEntity.inventory.getContainerSize(); ++i) {
				ItemStack curStack = playerEntity.inventory.getItem(i);

				if (supportedProjectilesPredicate.test(curStack)) return curStack;
			}
			return playerEntity.abilities.instabuild ? CAItems.IRUKANDJI_ARROW.get().getDefaultInstance() : ItemStack.EMPTY;
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity chargingEntity = (PlayerEntity) entityLiving;
			boolean applyInfinityLogic = chargingEntity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
			ItemStack existingAmmoStack = findAmmo(stack, chargingEntity);
			int remainingUseDuration = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, chargingEntity, getUseDuration(stack) - timeLeft, !existingAmmoStack.isEmpty() || applyInfinityLogic);

			if (remainingUseDuration < 0) return;

			if (!existingAmmoStack.isEmpty() || applyInfinityLogic) {
				if (existingAmmoStack.isEmpty()) existingAmmoStack = CAItems.IRUKANDJI_ARROW.get().getDefaultInstance();

				float chargedPower = getPowerForTime(remainingUseDuration);

				if (chargedPower >= 0.1F) {
					boolean hasInfiniteAmmo = chargingEntity.abilities.instabuild || (existingAmmoStack.getItem() instanceof ArrowItem && ((ArrowItem) existingAmmoStack.getItem()).isInfinite(existingAmmoStack, stack, chargingEntity));

					if (!worldIn.isClientSide()) {
						ArrowItem targetArrow = (ArrowItem) (existingAmmoStack.getItem() instanceof ArrowItem ? existingAmmoStack.getItem() : CAItems.IRUKANDJI_ARROW.get());
						AbstractArrowEntity spawnedArrow = customArrow(targetArrow.createArrow(worldIn, existingAmmoStack, chargingEntity));

						spawnedArrow.shootFromRotation(chargingEntity, chargingEntity.xRot, chargingEntity.yRot, 0.0F, chargedPower * 3.0F, 1.0F);

						if (chargedPower == 1.0F) spawnedArrow.setCritArrow(true);

						int powerEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
						int punchEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
						int flameEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack);

						if (powerEnchantmentLevel > 0) spawnedArrow.setBaseDamage(spawnedArrow.getBaseDamage() + (double) powerEnchantmentLevel * 0.5D + 0.5D);
						if (punchEnchantmentLevel > 0) spawnedArrow.setKnockback(punchEnchantmentLevel);
						if (flameEnchantmentLevel > 0) spawnedArrow.setSecondsOnFire(100);

						stack.hurtAndBreak(1, chargingEntity, (player) -> player.broadcastBreakEvent(chargingEntity.getUsedItemHand()));

						if (hasInfiniteAmmo || chargingEntity.abilities.instabuild && (existingAmmoStack.getItem() == Items.SPECTRAL_ARROW || existingAmmoStack.getItem() == Items.TIPPED_ARROW || existingAmmoStack.getItem() == Items.ARROW)) spawnedArrow.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;

						worldIn.addFreshEntity(spawnedArrow);
					}

					worldIn.playSound(null, chargingEntity.getX(), chargingEntity.getY(), chargingEntity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + chargedPower * 0.5F);

					if (!hasInfiniteAmmo && !chargingEntity.abilities.instabuild) {
						existingAmmoStack.shrink(1);

						if (existingAmmoStack.isEmpty()) chargingEntity.inventory.removeItem(existingAmmoStack);
					}

					chargingEntity.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}
}
