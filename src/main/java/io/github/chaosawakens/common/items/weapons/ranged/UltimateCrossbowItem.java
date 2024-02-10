package io.github.chaosawakens.common.items.weapons.ranged;

import com.google.common.collect.Lists;
import io.github.chaosawakens.api.item.IAutoEnchantable;
import io.github.chaosawakens.common.entity.projectile.ExplosiveFireworkEntity;
import io.github.chaosawakens.common.entity.projectile.arrow.UltimateCrossbowBoltEntity;
import io.github.chaosawakens.common.items.projectile.UltimateCrossbowBoltItem;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class UltimateCrossbowItem extends CrossbowItem implements IAutoEnchantable {
	public static final Predicate<ItemStack> BOLT_ONLY = (targetStack) -> targetStack.getItem() == CAItems.ULTIMATE_CROSSBOW_BOLT.get();
	public static final Predicate<ItemStack> BOLT_OR_FIREWORK = BOLT_ONLY.or((targetStack) -> targetStack.getItem() == Items.FIREWORK_ROCKET);
	private boolean startSoundPlayed = false;
	private boolean midLoadSoundPlayed = false;
	private final Supplier<EnchantmentData[]> enchantments;

	public UltimateCrossbowItem(Properties properties, Supplier<EnchantmentData[]> enchantments) {
		super(properties);
		this.enchantments = enchantments;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (allowdedIn(group)) {
			ItemStack targetStack = getDefaultInstance();

			if (CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get()) {
				for (EnchantmentData curEnchantment : enchantments.get()) targetStack.enchant(curEnchantment.enchantment, curEnchantment.level);
			}

			items.add(targetStack);
		}
	}

	@Override
	public void onCraftedBy(ItemStack targetStack, World world, PlayerEntity playerEntity) {
		if (CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get()) {
			for (EnchantmentData targetEnchantment : enchantments.get()) {
				if (targetEnchantment.level == 0) targetStack.enchant(targetEnchantment.enchantment, targetEnchantment.level);
			}
		}
	}

	@Override
	public EnchantmentData[] getEnchantments() {
		return enchantments.get();
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get() && super.isFoil(stack) || super.isFoil(stack);
	}

	@Override
	public int getUseDuration(ItemStack targetStack) {
		return getChargeDuration(targetStack) + 10;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerOwner, Hand hand) {
		ItemStack heldStack = playerOwner.getItemInHand(hand);

		if (isCharged(heldStack)) {
			performShooting(world, playerOwner, hand, heldStack, getShootingPowerFor(heldStack), 1.0F);
			setCharged(heldStack, false);

			return ActionResult.consume(heldStack);
		} else if (!playerOwner.getProjectile(heldStack).isEmpty()) {
			if (!isCharged(heldStack)) {
				this.startSoundPlayed = false;
				this.midLoadSoundPlayed = false;

				playerOwner.startUsingItem(hand);
			}
			return ActionResult.consume(heldStack);
		} else return ActionResult.fail(heldStack);
	}

	@Override
	public void releaseUsing(ItemStack stack, World world, LivingEntity owner, int dura) {
		int remainingUseDuration = getUseDuration(stack) - dura;
		float chargedPower = getPowerForTime(remainingUseDuration, stack);

		if (chargedPower >= 1.0F && !isCharged(stack) && tryLoadProjectiles(owner, stack)) {
			setCharged(stack, true);

			SoundCategory targetSoundCategory = owner instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;

			world.playSound((PlayerEntity) null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.CROSSBOW_LOADING_END, targetSoundCategory, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
		}
	}

	@Override
	public void onUseTick(World world, LivingEntity owner, ItemStack stack, int dura) {
		if (!world.isClientSide) {
			int quickChargeEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
			SoundEvent startSound = getStartSound(quickChargeEnchantmentLevel);
			SoundEvent loadSound = quickChargeEnchantmentLevel == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
			float deltaChargeDuration = (float) (stack.getUseDuration() - dura) / getChargeDuration(stack);

			if (deltaChargeDuration < 0.1F) {
				this.startSoundPlayed = false;
				this.midLoadSoundPlayed = false;
			}

			if (deltaChargeDuration >= 0.1F && !this.startSoundPlayed) {
				this.startSoundPlayed = true;
				world.playSound((PlayerEntity) null, owner.getX(), owner.getY(), owner.getZ(), startSound, SoundCategory.PLAYERS, 0.5F, 1.0F);
			}
			if (deltaChargeDuration >= 0.65F && loadSound != null && !this.midLoadSoundPlayed) {
				this.midLoadSoundPlayed = true;
				world.playSound((PlayerEntity) null, owner.getX(), owner.getY(), owner.getZ(), loadSound, SoundCategory.PLAYERS, 0.5F, 1.0F);
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack targetStack, @Nullable World curLevel, List<ITextComponent> tooltips, ITooltipFlag tooltipFlag) {
		//TODO Re-add tooltip support thingy
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return BOLT_ONLY;
	}

	@Override
	public Predicate<ItemStack> getSupportedHeldProjectiles() {
		return BOLT_OR_FIREWORK;
	}

	private SoundEvent getStartSound(int progress) {
		switch(progress) {
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

	@Override
	public int getDefaultProjectileRange() {
		return super.getDefaultProjectileRange() * 5;
	}

	public static boolean tryLoadProjectiles(LivingEntity ownerEntity, ItemStack arrowStack) {
		int multishotEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, arrowStack);
		boolean shouldApplyInfinityLogic = ownerEntity instanceof PlayerEntity && ((PlayerEntity) ownerEntity).abilities.instabuild;
		ItemStack targetProjectile = ownerEntity.getProjectile(arrowStack);
		ItemStack copiedProjectileStack = targetProjectile.copy();
		int storedProjectileAmount = targetProjectile.isEmpty() ? 1 : multishotEnchantmentLevel == 0 && (targetProjectile.getItem() instanceof UltimateCrossbowBoltItem) ? 1 : (multishotEnchantmentLevel * 2) + MathHelper.clamp(targetProjectile.getCount(), 1, 8); //TODO Un-hardcode amount

		for (int projectileAmount = 0; projectileAmount < storedProjectileAmount; projectileAmount++) {
			if (projectileAmount > 0) targetProjectile = copiedProjectileStack.copy();

			if (targetProjectile.isEmpty() && shouldApplyInfinityLogic) {
				targetProjectile = CAItems.ULTIMATE_CROSSBOW_BOLT.get().getDefaultInstance();
				copiedProjectileStack = targetProjectile.copy();
			}

			if (!loadProjectile(ownerEntity, arrowStack, targetProjectile, !targetProjectile.isEmpty(), shouldApplyInfinityLogic, storedProjectileAmount)) return false;
		}
		return true;
	}

	private static boolean loadProjectile(LivingEntity ownerEntity, ItemStack crossbowStack, ItemStack arrowStack, boolean hasArrows, boolean applyInfinityLogic, int deductionAmount) {
		if (arrowStack.isEmpty()) return false;
		else {
			boolean shouldApplyInfinityLogic = applyInfinityLogic && ownerEntity instanceof PlayerEntity && ((PlayerEntity) ownerEntity).abilities.instabuild;
			ItemStack targetProjectileStack;

			if (!shouldApplyInfinityLogic && !applyInfinityLogic && hasArrows) {
				targetProjectileStack = arrowStack.split(deductionAmount);

				if (arrowStack.isEmpty() && ownerEntity instanceof PlayerEntity) ((PlayerEntity) ownerEntity).inventory.removeItem(arrowStack);
			} else targetProjectileStack = arrowStack.copy();

			addChargedProjectile(crossbowStack, targetProjectileStack);
			return true;
		}
	}

	private static void addChargedProjectile(ItemStack crossbowStack, ItemStack arrowStack) {
		CompoundNBT crossbowNBT = crossbowStack.getOrCreateTag();
		ListNBT chargedProjectileListNBT = crossbowNBT.contains("ChargedProjectiles", 9) ? crossbowNBT.getList("ChargedProjectiles", 10) : new ListNBT();

		CompoundNBT projectileNBT = new CompoundNBT();

		arrowStack.save(projectileNBT);

		chargedProjectileListNBT.add(projectileNBT);
		crossbowNBT.put("ChargedProjectiles", chargedProjectileListNBT);
	}

	private static void shootProjectile(World world, LivingEntity owner, Hand hand, ItemStack crossbowStack, ItemStack arrowStack, float shotPitchRot, boolean applyInfinityLogic, float projectileVelocity, float projectileInaccuracy, float angleShotInterval) {
		if (!world.isClientSide) {
			boolean isFirework = arrowStack.getItem() == Items.FIREWORK_ROCKET;
			ProjectileEntity shotProjectile = isFirework ? new ExplosiveFireworkEntity(world, arrowStack, owner, owner.getX(), owner.getEyeY() - 0.15D, owner.getZ(), true) : getBolt(world, owner, crossbowStack, arrowStack, applyInfinityLogic);

			if (shotProjectile == null) {
				onCrossbowShot(world, owner, crossbowStack);
				return;
			}

			if (!isFirework && (applyInfinityLogic || angleShotInterval != 0.0F)) ((AbstractArrowEntity) shotProjectile).pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
			if (owner instanceof ICrossbowUser) ((ICrossbowUser) owner).shootCrossbowProjectile(((ICrossbowUser) owner).getTarget(), crossbowStack, shotProjectile, angleShotInterval);
			else {
				Vector3d lookupYVec = owner.getUpVector(1.0F);
				Quaternion angleQuaternion = new Quaternion(new Vector3f(lookupYVec), angleShotInterval, true);
				Vector3d viewVec = owner.getViewVector(1.0F);
				Vector3f viewVecF = new Vector3f(viewVec);

				viewVecF.transform(angleQuaternion);
				shotProjectile.shoot(viewVecF.x(), viewVecF.y(), viewVecF.z(), projectileVelocity, projectileInaccuracy);
			}

			crossbowStack.hurtAndBreak(isFirework ? arrowStack.getCount() / 4 : 1, owner, (targetStack) -> targetStack.broadcastBreakEvent(hand));

			world.addFreshEntity(shotProjectile);
			world.playSound((PlayerEntity) null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, shotPitchRot);
		}
	}

	private static UltimateCrossbowBoltEntity getBolt(World world, LivingEntity owner, ItemStack crossbowStack, ItemStack arrowStack, boolean applyInfinityLogic) {
		UltimateCrossbowBoltItem boltItem = (UltimateCrossbowBoltItem) (arrowStack.getItem() instanceof UltimateCrossbowBoltItem ? arrowStack.getItem() : null);

		if (boltItem == null && applyInfinityLogic) boltItem = CAItems.ULTIMATE_CROSSBOW_BOLT.get();
		else if (boltItem == null) return null;

		UltimateCrossbowBoltEntity crossbowBolt = boltItem.createArrow(world, arrowStack, owner);

		crossbowBolt.setBaseDamage(CAConfigManager.MAIN_SERVER.ultimateCrossbowBoltBaseDamage.get());
		crossbowBolt.setCritArrow(true);
		crossbowBolt.setSoundEvent(SoundEvents.CROSSBOW_HIT);
		crossbowBolt.setShotFromCrossbow(true);
		crossbowBolt.setPierceLevel((byte) 5);

		int piercingEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, crossbowStack);
		int powerEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, crossbowStack);

		if (piercingEnchantmentLevel > 0) crossbowBolt.setPierceLevel((byte) (piercingEnchantmentLevel + 4));
		if (powerEnchantmentLevel > 0) crossbowBolt.setBaseDamage(crossbowBolt.getBaseDamage() + powerEnchantmentLevel + 0.5F * CAConfigManager.MAIN_SERVER.ultimateBowArrowDamageMultiplier.get());

		return crossbowBolt;
	}

	private static float[] getShotPitches(Random random) {
		boolean randPitchAddModBool = random.nextBoolean();
		return new float[]{1.0F, getRandomShotPitch(randPitchAddModBool), getRandomShotPitch(!randPitchAddModBool)};
	}

	private static float getRandomShotPitch(boolean increasedPitchAddMod) {
		float pitchMod = increasedPitchAddMod ? 0.63F : 0.43F;
		return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + pitchMod - 0.22F;
	}

	public static void performShooting(World world, LivingEntity owner, Hand hand, ItemStack stack, float projectileVelocity, float projectileInaccuracy) {
		List<ItemStack> allChargedProjectiles = getChargedProjectiles(stack);
		float[] shotPitchAngles = getShotPitches(owner.getRandom());
		boolean shouldApplyInfinityLogic = owner instanceof PlayerEntity && ((PlayerEntity) owner).abilities.instabuild;
		AtomicBoolean hasShotBolt = new AtomicBoolean(false);

		allChargedProjectiles.forEach(targetStack -> {
			if (!targetStack.isEmpty()) {
				for (float angle = ((float) -allChargedProjectiles.size() / 2) * 10; angle < ((float) allChargedProjectiles.size() / 2) * 10; angle++) { //TODO Yes, sorta hardcoded
					if (allChargedProjectiles.size() <= 1) {
						shootProjectile(world, owner, hand, stack, targetStack, shotPitchAngles[0], shouldApplyInfinityLogic, projectileVelocity, projectileInaccuracy, 0);
						return;
					} else if (angle % 10 == 0) shootProjectile(world, owner, hand, stack, targetStack, shotPitchAngles[(int) MathHelper.clamp(angle / 10, 0, 2)], shouldApplyInfinityLogic, projectileVelocity, projectileInaccuracy, angle);
				}
			}
		});

		onCrossbowShot(world, owner, stack);
		hasShotBolt.set(false);
	}

	private static List<ItemStack> getChargedProjectiles(ItemStack stack) {
		List<ItemStack> storedChargedProjectiles = Lists.newArrayList();
		CompoundNBT stackNBTData = stack.getTag();

		if (stackNBTData != null && stackNBTData.contains("ChargedProjectiles", 9)) {
			ListNBT chargedProjectilesListNBTData = stackNBTData.getList("ChargedProjectiles", 10);

			if (chargedProjectilesListNBTData != null) {
				for (int i = 0; i < chargedProjectilesListNBTData.size(); ++i) {
					CompoundNBT curStackByData = chargedProjectilesListNBTData.getCompound(i);
					storedChargedProjectiles.add(ItemStack.of(curStackByData));
				}
			}
		}

		return storedChargedProjectiles;
	}

	private static void onCrossbowShot(World world, LivingEntity owner, ItemStack stack) {
		if (owner instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayerOwner = (ServerPlayerEntity) owner;

			if (!world.isClientSide) CriteriaTriggers.SHOT_CROSSBOW.trigger(serverPlayerOwner, stack);

			serverPlayerOwner.awardStat(Stats.ITEM_USED.get(stack.getItem()));
		}

		clearChargedProjectiles(stack);
	}

	private static void clearChargedProjectiles(ItemStack stack) {
		CompoundNBT curStackNBTData = stack.getTag();

		if (curStackNBTData != null) {
			ListNBT chargedProjectileNBTListData = curStackNBTData.getList("ChargedProjectiles", 9);

			chargedProjectileNBTListData.clear();
			curStackNBTData.put("ChargedProjectiles", chargedProjectileNBTListData);
		}
	}

	private static float getShootingPowerFor(ItemStack targetStack) {
		return targetStack.getItem() == CAItems.ULTIMATE_CROSSBOW.get() ? containsChargedProjectile(targetStack, CAItems.ULTIMATE_CROSSBOW_BOLT.get()) ? 5.15F : 3.15F : 3.15F;
	}

	public static boolean containsChargedProjectile(ItemStack targetCrossbowStack, Item ammoItem) {
		return getChargedProjectiles(targetCrossbowStack).stream().anyMatch((curAmmo) -> curAmmo.getItem() == ammoItem);
	}
}