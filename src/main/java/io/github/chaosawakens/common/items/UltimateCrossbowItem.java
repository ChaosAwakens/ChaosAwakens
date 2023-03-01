package io.github.chaosawakens.common.items;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import com.google.common.collect.Lists;

import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.entity.projectile.UltimateCrossbowArrowEntity;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@SuppressWarnings("all")
public class UltimateCrossbowItem extends CrossbowItem {
	private final EnchantmentData[] enchantments;
	/** Set to {@code true} when the crossbow is 20% charged. */
	private boolean startSoundPlayed = false;
	/** Set to {@code true} when the crossbow is 50% charged. */
	private boolean midLoadSoundPlayed = false;


	public UltimateCrossbowItem(Properties builderIn, EnchantmentData[] enchantments) {
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
	public Predicate<ItemStack> getSupportedHeldProjectiles() {
		return ARROW_OR_FIREWORK;
	}

	/**
	 * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
	 */
	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return ARROW_ONLY;
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		if (isCharged(itemstack)) {
			performShooting(pLevel, pPlayer, pHand, itemstack, getShootingPower(itemstack), 1.0F);
			setCharged(itemstack, false);
			return ActionResult.consume(itemstack);
		} else if (!pPlayer.getProjectile(itemstack).isEmpty()) {
			if (!isCharged(itemstack)) {
				this.startSoundPlayed = false;
				this.midLoadSoundPlayed = false;
				pPlayer.startUsingItem(pHand);
			}

			return ActionResult.consume(itemstack);
		} else {
			return ActionResult.fail(itemstack);
		}
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse button).
	 */
	@Override
	public void releaseUsing(ItemStack pStack, World pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
		int i = this.getUseDuration(pStack) - pTimeLeft;
		float f = getPowerForTime(i, pStack);
		if (f >= 1.0F && !isCharged(pStack) && tryLoadProjectiles(pEntityLiving, pStack)) {
			setCharged(pStack, true);
			SoundCategory soundcategory = pEntityLiving instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
			pLevel.playSound((PlayerEntity)null, pEntityLiving.getX(), pEntityLiving.getY(), pEntityLiving.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundcategory, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
		}

	}

	public static boolean tryLoadProjectiles(LivingEntity pShooter, ItemStack pCrossbowStack) {
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, pCrossbowStack);
		int j = i == 0 ? 1 : 3;
		boolean flag = pShooter instanceof PlayerEntity && ((PlayerEntity)pShooter).abilities.instabuild;
		ItemStack itemstack = pShooter.getProjectile(pCrossbowStack);
		ItemStack itemstack1 = itemstack.copy();

		for(int k = 0; k < j; ++k) {
			if (k > 0) {
				itemstack = itemstack1.copy();
			}

			if (itemstack.isEmpty() && flag) {
				itemstack = new ItemStack(Items.ARROW);
				itemstack1 = itemstack.copy();
			}

			if (!loadProjectile(pShooter, pCrossbowStack, itemstack, k > 0, flag)) {
				return false;
			}
		}

		return true;
	}

	private static boolean loadProjectile(LivingEntity pShooter, ItemStack pCrossbowStack, ItemStack pAmmoStack, boolean pHasAmmo, boolean pIsCreative) {
		if (pAmmoStack.isEmpty()) {
			return false;
		} else {
			boolean flag = pIsCreative && pAmmoStack.getItem() instanceof ArrowItem;
			ItemStack itemstack;
			if (!flag && !pIsCreative && !pHasAmmo) {
				itemstack = pAmmoStack.split(1);
				if (pAmmoStack.isEmpty() && pShooter instanceof PlayerEntity) {
					((PlayerEntity)pShooter).inventory.removeItem(pAmmoStack);
				}
			} else {
				itemstack = pAmmoStack.copy();
			}

			addChargedProjectile(pCrossbowStack, itemstack);
			return true;
		}
	}

	public static boolean isCharged(ItemStack pCrossbowStack) {
		CompoundNBT compoundnbt = pCrossbowStack.getTag();
		return compoundnbt != null && compoundnbt.getBoolean("Charged");
	}

	public static void setCharged(ItemStack pCrossbowStack, boolean pIsCharged) {
		CompoundNBT compoundnbt = pCrossbowStack.getOrCreateTag();
		compoundnbt.putBoolean("Charged", pIsCharged);
	}

	private static void addChargedProjectile(ItemStack pCrossbowStack, ItemStack pAmmoStack) {
		CompoundNBT compoundnbt = pCrossbowStack.getOrCreateTag();
		ListNBT listnbt;
		if (compoundnbt.contains("ChargedProjectiles", 9)) {
			listnbt = compoundnbt.getList("ChargedProjectiles", 10);
		} else {
			listnbt = new ListNBT();
		}

		CompoundNBT compoundnbt1 = new CompoundNBT();
		pAmmoStack.save(compoundnbt1);
		listnbt.add(compoundnbt1);
		compoundnbt.put("ChargedProjectiles", listnbt);
	}

	private static List<ItemStack> getChargedProjectiles(ItemStack pCrossbowStack) {
		List<ItemStack> list = Lists.newArrayList();
		CompoundNBT compoundnbt = pCrossbowStack.getTag();
		if (compoundnbt != null && compoundnbt.contains("ChargedProjectiles", 9)) {
			ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 10);
			if (listnbt != null) {
				for(int i = 0; i < listnbt.size(); ++i) {
					CompoundNBT compoundnbt1 = listnbt.getCompound(i);
					list.add(ItemStack.of(compoundnbt1));
				}
			}
		}

		return list;
	}

	private static void clearChargedProjectiles(ItemStack pCrossbowStack) {
		CompoundNBT compoundnbt = pCrossbowStack.getTag();
		if (compoundnbt != null) {
			ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 9);
			listnbt.clear();
			compoundnbt.put("ChargedProjectiles", listnbt);
		}

	}

	public static boolean containsChargedProjectile(ItemStack pCrossbowStack, Item pAmmoItem) {
		return getChargedProjectiles(pCrossbowStack).stream().anyMatch((p_220010_1_) -> {
			return p_220010_1_.getItem() == pAmmoItem;
		});
	}

	private static void shootProjectile(World pLevel, LivingEntity pShooter, Hand pHand, ItemStack pCrossbowStack, ItemStack pAmmoStack, float pSoundPitch, boolean pIsCreativeMode, float pVelocity, float pInaccuracy, float pProjectileAngle) {
		if (!pLevel.isClientSide) {
			boolean flag = pAmmoStack.getItem() == Items.FIREWORK_ROCKET;
			ProjectileEntity projectileentity;
			if (flag) {
				projectileentity = new FireworkRocketEntity(pLevel, pAmmoStack, pShooter, pShooter.getX(), pShooter.getEyeY() - (double)0.15F, pShooter.getZ(), true);
			} else {
				projectileentity = getArrow(pLevel, pShooter, pCrossbowStack, pAmmoStack);
				if (pIsCreativeMode || pProjectileAngle != 0.0F) {
					((UltimateCrossbowArrowEntity)projectileentity).pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
				}
			}

			if (pShooter instanceof ICrossbowUser) {
				ICrossbowUser icrossbowuser = (ICrossbowUser)pShooter;
				icrossbowuser.shootCrossbowProjectile(icrossbowuser.getTarget(), pCrossbowStack, projectileentity, pProjectileAngle);
			} else {
				Vector3d vector3d1 = pShooter.getUpVector(1.0F);
				Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), pProjectileAngle, true);
				Vector3d vector3d = pShooter.getViewVector(1.0F);
				Vector3f vector3f = new Vector3f(vector3d);
				vector3f.transform(quaternion);
				projectileentity.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), pVelocity, pInaccuracy);
			}

			pCrossbowStack.hurtAndBreak(flag ? 3 : 1, pShooter, (p_220017_1_) -> {
				p_220017_1_.broadcastBreakEvent(pHand);
			});
			pLevel.addFreshEntity(projectileentity);
			pLevel.playSound((PlayerEntity)null, pShooter.getX(), pShooter.getY(), pShooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, pSoundPitch);
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		if (enchantment == Enchantments.QUICK_CHARGE) {
			return false;
		}

		return super.canApplyAtEnchantingTable(stack, enchantment);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		if (book.isEnchanted() && EnchantmentHelper.getEnchantments(book).containsKey(Enchantments.QUICK_CHARGE)) {
			return false;
		}

		return super.isBookEnchantable(stack, book);
	}

	private static UltimateCrossbowArrowEntity getArrow(World world, LivingEntity owner, ItemStack crossbowStack, ItemStack arrowStack) {
		UltimateCrossbowArrowEntity abstractarrowentity = new UltimateCrossbowArrowEntity(world, owner);
		if (owner instanceof PlayerEntity) {
			abstractarrowentity.setCritArrow(true);
		}

		abstractarrowentity.setSoundEvent(SoundEvents.CROSSBOW_HIT);
		abstractarrowentity.setShotFromCrossbow(true);
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, crossbowStack);
		if (i > 0) {
			abstractarrowentity.setPierceLevel((byte)i);
		}

		return abstractarrowentity;
	}

	public static void performShooting(World pLevel, LivingEntity pShooter, Hand pUsedHand, ItemStack pCrossbowStack, float pVelocity, float pInaccuracy) {
		List<ItemStack> list = getChargedProjectiles(pCrossbowStack);
		float[] afloat = getShotPitches(pShooter.getRandom());

		for(int i = 0; i < list.size(); ++i) {
			ItemStack itemstack = list.get(i);
			boolean flag = pShooter instanceof PlayerEntity && ((PlayerEntity)pShooter).abilities.instabuild;
			if (!itemstack.isEmpty()) {
				if (i == 0) {
					shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack, afloat[i], flag, pVelocity, pInaccuracy, 0.0F);
				} else if (i == 1) {
					shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack, afloat[i], flag, pVelocity, pInaccuracy, -10.0F);
				} else if (i == 2) {
					shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack, afloat[i], flag, pVelocity, pInaccuracy, 10.0F);
				}
			}
		}

		onCrossbowShot(pLevel, pShooter, pCrossbowStack);
	}

	private static float[] getShotPitches(Random pRandom) {
		boolean flag = pRandom.nextBoolean();
		return new float[]{1.0F, getRandomShotPitch(flag), getRandomShotPitch(!flag)};
	}

	private static float getRandomShotPitch(boolean p_220032_0_) {
		float f = p_220032_0_ ? 0.63F : 0.43F;
		return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
	}

	/**
	 * Called after {@plainlink #fireProjectiles} to clear the charged projectile and to update the player advancements.
	 */
	private static void onCrossbowShot(World pLevel, LivingEntity pShooter, ItemStack pCrossbowStack) {
		if (pShooter instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)pShooter;
			if (!pLevel.isClientSide) {
				CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayerentity, pCrossbowStack);
			}

			serverplayerentity.awardStat(Stats.ITEM_USED.get(pCrossbowStack.getItem()));
		}

		clearChargedProjectiles(pCrossbowStack);
	}

	/**
	 * Called as the item is being used by an entity.
	 */
	@Override
	public void onUseTick(World pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pCount) {
		if (!pLevel.isClientSide) {
			int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, pStack);
			SoundEvent soundevent = this.getStartSound(i);
			SoundEvent soundevent1 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
			float f = (float)(pStack.getUseDuration() - pCount) / (float)getChargeDuration(pStack);
			if (f < 0.2F) {
				this.startSoundPlayed = false;
				this.midLoadSoundPlayed = false;
			}

			if (f >= 0.2F && !this.startSoundPlayed) {
				this.startSoundPlayed = true;
				pLevel.playSound((PlayerEntity)null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), soundevent, SoundCategory.PLAYERS, 0.5F, 1.0F);
			}

			if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
				this.midLoadSoundPlayed = true;
				pLevel.playSound((PlayerEntity)null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), soundevent1, SoundCategory.PLAYERS, 0.5F, 1.0F);
			}
		}

	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getUseDuration(ItemStack pStack) {
		return getChargeDuration(pStack) * 3;
	}

	/**
	 * The time the crossbow must be used to reload it
	 */
	public static int getChargeDuration(ItemStack pCrossbowStack) {
		return 50;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public UseAction getUseAnimation(ItemStack pStack) {
		return UseAction.CROSSBOW;
	}

	private SoundEvent getStartSound(int pEnchantmentLevel) {
		switch(pEnchantmentLevel) {
			case 1:
				return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
			case 2:
				return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
			case 3:
				return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
			default:
				return SoundEvents.CROSSBOW_LOADING_START;
		}
	}

	public static float getPowerForTime(int pUseTime, ItemStack pCrossbowStack) {
		float f = (float)pUseTime / (float)getChargeDuration(pCrossbowStack);
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		List<ItemStack> list = getChargedProjectiles(pStack);
		if (isCharged(pStack) && !list.isEmpty()) {
			ItemStack itemstack = list.get(0);
			pTooltip.add((new TranslationTextComponent("item.minecraft.crossbow.projectile")).append(" ").append(itemstack.getDisplayName()));
			if (pFlag.isAdvanced() && itemstack.getItem() == Items.FIREWORK_ROCKET) {
				List<ITextComponent> list1 = Lists.newArrayList();
				Items.FIREWORK_ROCKET.appendHoverText(itemstack, pLevel, list1, pFlag);
				if (!list1.isEmpty()) {
					for(int i = 0; i < list1.size(); ++i) {
						list1.set(i, (new StringTextComponent("  ")).append(list1.get(i)).withStyle(TextFormatting.GRAY));
					}

					pTooltip.addAll(list1);
				}
			}

		}
	}

	public static float getShootingPower(ItemStack pCrossbowStack) {
		return pCrossbowStack.getItem() == CAItems.ULTIMATE_CROSSBOW.get() && containsChargedProjectile(pCrossbowStack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
	}

	@Override
	public int getDefaultProjectileRange() {
		return 12;
	}
}
