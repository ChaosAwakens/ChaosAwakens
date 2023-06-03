package io.github.chaosawakens.common.items.weapons.ranged;

import java.util.function.Predicate;
import java.util.function.Supplier;

import io.github.chaosawakens.common.entity.projectile.arrow.UltimateArrowEntity;
import io.github.chaosawakens.common.items.base.EnchantedBowItem;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class UltimateBowItem extends EnchantedBowItem implements IVanishable {

	public UltimateBowItem(Properties builderIn, Supplier<EnchantmentData[]> enchantments) {
		super(builderIn, enchantments);
	}

	@Override
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity owner, int timeLeft) {
		if (owner instanceof PlayerEntity) {
			PlayerEntity playerOwner = (PlayerEntity) owner;

			if (ForgeEventFactory.onArrowLoose(stack, worldIn, playerOwner, this.getUseDuration(stack) - timeLeft, true) < 0) return;
			if (!worldIn.isClientSide) {
				UltimateArrowEntity ultArrow = new UltimateArrowEntity(worldIn, owner);
				ultArrow.shootFromRotation(playerOwner, playerOwner.xRot, playerOwner.yRot, 0.0F, 3.0F, 0F);
				ultArrow.setCritArrow(true);
				ultArrow.setSecondsOnFire(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0 ? 250 : 75);

				int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
				if (!CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get()) ultArrow.setBaseDamage(CAConfigManager.MAIN_SERVER.ultimateBowArrowBaseDamage.get() + (double) powerLevel * CAConfigManager.MAIN_SERVER.ultimateBowArrowDamageMultiplier.get() + 2D);
				else ultArrow.setBaseDamage(CAConfigManager.MAIN_SERVER.ultimateBowArrowBaseDamage.get() + 3D);

				int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
				ultArrow.setKnockback(!CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get() ? punchLevel + 1 : 1);

				if (!playerOwner.isCreative()) stack.hurtAndBreak(1, owner, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));

				worldIn.addFreshEntity(ultArrow);
				worldIn.playSound(null, playerOwner.getX(), playerOwner.getY(), playerOwner.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
				playerOwner.awardStat(Stats.ITEM_USED.get(this));
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 9000;
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack heldItem = playerIn.getItemInHand(handIn);

		playerIn.startUsingItem(handIn);
		return ActionResult.consume(heldItem);
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return ARROW_ONLY;
	}

	@Override
	public int getDefaultProjectileRange() {
		return 15;
	}
}
