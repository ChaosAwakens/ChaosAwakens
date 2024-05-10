package io.github.chaosawakens.common.util;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class EntityUtil {

	private EntityUtil() {
		throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
	}

	/**
	 * Gets entities inside a certain bounding box, the base being around a specified user entity, and allows for specifying 
	 * a certain radius to validate entities inside the bounding box as targets
	 * 
	 * @param user User entity, base from which the bounding box will grow
	 * @param entityClass the class of the entity the user should check for
	 * @param dX The distance on the x axis in which the bounding box will grow
	 * @param dY The distance on the y axis in which the bounding box will grow
	 * @param dZ The distance on the z axis in which the bounding box will grow
	 * @param radius the radius in which to validate entities that are already inside the bounding box
	 * @return a list of entities within the valid specified distance inside the grown bounding box
	 */
	public static <E extends Entity> List<E> getEntitiesAround(Entity user, Class<E> entityClass, double dX, double dY, double dZ, double radius) {
		Predicate<E> distPredicate = living -> living != user && (user.getTeam() != null && living.getTeam() != null ? !living.getTeam().equals(user.getTeam()) : living.isAlive()) && living.getClass() != user.getClass() && user.distanceTo(living) <= radius + living.getBbWidth() / 2F;
		return user.level.getEntitiesOfClass(entityClass, user.getBoundingBox().inflate(dX, dY, dZ), distPredicate);
	}
	
	/**
	 * Gets entities inside a certain bounding box, the base being around a specified user entity, and allows for specifying 
	 * a certain radius to validate entities inside the bounding box as targets. This applies to all entities, as opposed to the other method,
	 * which only applies to attackable entities
	 * 
	 * @param user user entity, base from which the bounding box will grow
	 * @param entityClass the class of the entity the user should check for
	 * @param dX the distance on the x axis in which the bounding box will grow
	 * @param dY the distance on the y axis in which the bounding box will grow
	 * @param dZ the distance on the z axis in which the bounding box will grow
	 * @param radius the radius in which to validate entities that are already inside the bounding box
	 * @return a list of entities within the valid specified distance inside the grown bounding box
	 */
	public static <E extends Entity> List<E> getEntitiesAroundNoPredicate(LivingEntity user, Class<E> entityClass, double dX, double dY, double dZ, double radius) {
		return user.level.getEntitiesOfClass(entityClass, user.getBoundingBox().inflate(dX, dY, dZ));
	}

	/**
	 * Gets entities inside a certain bounding box, the base being around a specified user entity, and allows for specifying 
	 * a custom predicate for entity detection/inclusion
	 * 
	 * @param user User entity, base from which the bounding box will grow
	 * @param entityClass the class of the entity the user should check for
	 * @param dX The distance on the x axis in which the bounding box will grow
	 * @param dY The distance on the y axis in which the bounding box will grow
	 * @param dZ The distance on the z axis in which the bounding box will grow
	 * @param detectionConditions The conditions for the entities within the defined area to be included
	 * @return a list of entities within the valid specified distance inside the grown bounding box
	 */
	public static <E extends Entity> List<E> getEntitiesAround(Entity user, Class<E> entityClass, double dX, double dY, double dZ, Predicate<E> detectionConditions) {
		return user.level.getEntitiesOfClass(entityClass, user.getBoundingBox().inflate(dX, dY, dZ), detectionConditions);
	}

	/**
	 * Gets entities inside a certain bounding box, the base being around a specified user entity, and allows for specifying 
	 * a certain radius to validate entities inside the bounding box as targets. This applies to all entities, as opposed to the other method,
	 * which only applies to attackable entities
	 * 
	 * @param user User entity, base from which the bounding box will grow
	 * @param entityClass the class of the entity the user should check for
	 * @param dX The distance on the x axis in which the bounding box will grow
	 * @param dY The distance on the y axis in which the bounding box will grow
	 * @param dZ The distance on the z axis in which the bounding box will grow
	 * @param radius the radius in which to validate entities that are already inside the bounding box
	 * @return a list of entities within the valid specified distance inside the grown bounding box
	 */
	public static <E extends Entity> List<E> getEntitiesAroundNoPredicate(Entity user, Class<E> entityClass, double dX, double dY, double dZ, double radius) {
		return user.level.getEntitiesOfClass(entityClass, user.getBoundingBox().inflate(dX, dY, dZ));
	}

	/**
	 * Gets all types of living entities around a specified user entity
	 * 
	 * @param user Base entity that will check for the other entities
	 * @param dX the distance on the x axis in which the bounding box will grow
	 * @param dY the distance on the y axis in which the bounding box will grow
	 * @param dZ the distance on the z axis in which the bounding box will grow
	 * @param radius the radius in which to validate entities that are already inside the bounding box
	 * @return a list of all living entities within the valid specified distance inside the grown bounding box
	 */
	@Nullable
	public static List<LivingEntity> getAllEntitiesAround(Entity user, double dX, double dY, double dZ, double radius) {
		return getEntitiesAround(user, LivingEntity.class, dX, dY, dZ, radius);
	}

	/**
	 * Gets all players around an entity
	 * 
	 * @param user Base entity, from which the check will begin
	 * @param dX the distance on the x axis in which the bounding box will grow
	 * @param dY the distance on the y axis in which the bounding box will grow
	 * @param dZ the distance on the z axis in which the bounding box will grow
	 * @param radius radius the radius in which to validate entities that are already inside the bounding box
	 * @return a list of all players within the valid specified distance inside the grown bounding box
	 */
	public static List<PlayerEntity> getAllPlayersAround(Entity user, double dX, double dY, double dZ, double radius) {
		List<Entity> nearbyEntities = user.level.getEntities(user, user.getBoundingBox().inflate(dX, dY, dZ));
		List<PlayerEntity> listEntityPlayers = nearbyEntities.stream().filter(neighbouringEntity -> neighbouringEntity instanceof PlayerEntity && user.distanceTo(neighbouringEntity) <= radius + neighbouringEntity.getBbWidth() / 2f).map(entityNeighbor -> (PlayerEntity) entityNeighbor).collect(Collectors.toList());
		return listEntityPlayers;
	}

	/**
	 * Freezes the rotation of a specified {@link AnimatableMonsterEntity}
	 * 
	 * @param targetEntity The target entity to have its rotation stopped
	 */
	public static void freezeEntityRotation(AnimatableMonsterEntity targetEntity) {
		targetEntity.yRot = targetEntity.yRotO;
	}

	/**
	 * Freezes the rotation of a specified {@link AnimatableAnimalEntity}
	 * 
	 * @param targetEntity The target entity to have its rotation stopped
	 */
	public static void freezeEntityRotation(AnimatableAnimalEntity targetEntity) {
		targetEntity.yBodyRot = targetEntity.yBodyRotO;
	}

	/**
	 * Checks if the player is wearing a full armor set, specified by the parameters below
	 * 
	 * @param player Player wearing the armor
	 * @param helmet Helmet item being worn by the player in the HEAD slot
	 * @param chestplate Chestplate item being worn by the player in the CHEST slot
	 * @param leggings Legging item being worn by the player in the LEGS slot
	 * @param boots Boot item being worn by the player in the FEET slot
	 * @return True if the player armor slots contain the specified armor pieces, else returns False
	 */
	public static boolean isFullArmorSet(PlayerEntity player, Item helmet, Item chestplate, Item leggings, Item boots) {
		if (!ObjectUtil.performNullityChecks(false, player, helmet, chestplate, leggings, boots)) return false;
		ItemStack head = player.getItemBySlot(EquipmentSlotType.HEAD);
		ItemStack chest = player.getItemBySlot(EquipmentSlotType.CHEST);
		ItemStack legs = player.getItemBySlot(EquipmentSlotType.LEGS);
		ItemStack feet = player.getItemBySlot(EquipmentSlotType.FEET);

		boolean checkHead = head.getItem().equals(helmet);
		boolean checkChest = chest.getItem().equals(chestplate);
		boolean checkLegs = legs.getItem().equals(leggings);
		boolean checkFeet = feet.getItem().equals(boots);

		return checkHead && checkChest && checkLegs && checkFeet;
	}

	/**
	 * Converts a specified entity into a different {@link EntityType}, copying all the necessary data over
	 * 
	 * @param <E> An instance of {@link LivingEntity} or its subclasses
	 * @param targetEntity The target entity to convert
	 * @param conversionEntity The resulting entity type
	 * @param targetWorld The {@link World} to convert the entity in
	 * @return True if a new instance of the <code>conversionEntity</code> with all the necessary data copied over from the previous entity can be created 
	 * (calling the check from {@link ForgeEventFactory#canLivingConvert(LivingEntity, EntityType, java.util.function.Consumer)}), else returns False
	 */
	public static <E extends LivingEntity> boolean convertEntity(E targetEntity, EntityType<? extends E> conversionEntity, World targetWorld) {
		if (ForgeEventFactory.canLivingConvert(targetEntity, conversionEntity, (timer) -> {})) {
			E targetConversionEntity = conversionEntity.create(targetWorld);

			assert targetConversionEntity != null;
			targetConversionEntity.moveTo(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), targetEntity.yRot, targetEntity.xRot);

			if (targetEntity.hasCustomName()) {
				targetConversionEntity.setCustomName(targetEntity.getCustomName());
				targetConversionEntity.setCustomNameVisible(targetEntity.isCustomNameVisible());
			}

			if (targetConversionEntity instanceof MobEntity) {
				if (targetEntity instanceof MobEntity) ((MobEntity) targetConversionEntity).setNoAi(((MobEntity) targetEntity).isNoAi());
				((MobEntity) targetConversionEntity).setBaby(targetEntity.isBaby());
				((MobEntity) targetConversionEntity).setPersistenceRequired();
			}

			ForgeEventFactory.onLivingConvert(targetEntity, targetConversionEntity);
			targetWorld.addFreshEntity(targetConversionEntity);
			targetEntity.remove();
			return true;
		} else return false;
	}

	/**
	 * Checks if an entity is holding an item in either of its hands
	 * 
	 * @param targetEntity The entity to perform the holding check on
	 * @param holdingItem The item to check for
	 * @return True if the specified entity is holding the specified item in either of its hands, else returns False
	 */
	public static boolean isHoldingItem(LivingEntity targetEntity, Item holdingItem) {
		return targetEntity.getMainHandItem().getItem() == holdingItem || targetEntity.getOffhandItem().getItem() == holdingItem;
	}
	
	/**
	 * Checks if an {@link Item} is on cooldown via {@link CooldownTracker#isOnCooldown(Item)}
	 *
	 * @param playerOwner The owner of the {@link Item} to check for cooldown
	 * @param targetItem The target {@link Item} to check for cooldown
	 *
	 * @return {@code true} if the specified item is on cooldown (and the player/item aren't {@code null}), else returns {@code false}
	 */
	public static boolean isItemOnCooldown(PlayerEntity playerOwner, Item targetItem) {
		return ObjectUtil.performNullityChecks(false, playerOwner, targetItem) && playerOwner.getCooldowns().isOnCooldown(targetItem);
	}

	/**
	 * Checks if the player's Curios slots are empty
	 *
	 * @param targetPlayer Player to check the Curios slots of
	 * @return {@code true} if Curios slots are empty or Curios is not installed, else returns {@code false}
	 */
	public static boolean areCuriosSlotsEmpty(PlayerEntity targetPlayer) {
		if (ModList.get().isLoaded("curios")) {
			LazyOptional<IItemHandlerModifiable> curiosHandler = CuriosApi.getCuriosHelper().getEquippedCurios(targetPlayer);

			if (curiosHandler.isPresent()) {
				IItemHandlerModifiable handler = curiosHandler.orElseThrow(IllegalStateException::new);

				for (int slot = 0; slot < handler.getSlots(); slot++) {
					if (!handler.getStackInSlot(slot).isEmpty()) return false;
				}
			}
		}
		return true;
	}

	public static void setEntityInFloatingMotion(Entity targetEntity, double yFloatingThreshold, float yFloatSpeed, boolean canMoveHorizontally) {
		if (targetEntity.isNoGravity() || targetEntity == null || !targetEntity.isAlive()) return;

		if (targetEntity.getDeltaMovement().y() <= -yFloatingThreshold) targetEntity.setDeltaMovement(canMoveHorizontally ? 1.0D : 0, MathHelper.lerp(1, Math.abs(targetEntity.getDeltaMovement().y * yFloatSpeed), yFloatingThreshold), canMoveHorizontally ? 1.0D : 0);
		if (targetEntity.getDeltaMovement().y() >= yFloatingThreshold) targetEntity.setDeltaMovement(canMoveHorizontally ? 1.0D : 0, MathHelper.lerp(1, -Math.abs(targetEntity.getDeltaMovement().y * yFloatSpeed), yFloatingThreshold), canMoveHorizontally ? 1.0D : 0);
	}

	public static void setEntityInFloatingMotion(Entity targetEntity, double yFloatingThreshold, float yFloatSpeed) {
		setEntityInFloatingMotion(targetEntity, yFloatingThreshold, yFloatSpeed, false);
	}

	/**
	 * Applies modification an entity's reach attribute if the reach attribute's value isn't default. This should usually be called in an item's 
	 * {@link Item#onEntitySwing(ItemStack, LivingEntity)} method.
	 * @param owner Owner entity
	 * @param heldStack The stack to apply the attribute mod to
	 * @param attackDamage The held item's damage
	 * @return True if the reach modifier can be applied and the target entity (or entities) can be hit, else returns False
	 */
	public static boolean applyReachModifierToEntity(LivingEntity owner, ItemStack heldStack, float attackDamage) {
		if (owner.getAttribute(ForgeMod.REACH_DISTANCE.get()) != null) {
			double reach = owner.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
			double reachSqr = reach * reach;
			World curWorld = owner.level;

			Vector3d viewVec = owner.getViewVector(1.0F);
			Vector3d eyeVec = owner.getEyePosition(1.0F);
			Vector3d targetVec = eyeVec.add(viewVec.x * reach, viewVec.y * reach, viewVec.z * reach);

			AxisAlignedBB reachBB = owner.getBoundingBox().expandTowards(viewVec.scale(reach)).inflate(4.0D, 4.0D, 4.0D);
			EntityRayTraceResult reachedEntityHitResult = ProjectileHelper.getEntityHitResult(curWorld, owner, eyeVec, targetVec, reachBB, EntityPredicates.NO_CREATIVE_OR_SPECTATOR);

			if (reachedEntityHitResult == null || !(reachedEntityHitResult.getEntity() instanceof LivingEntity) || reachedEntityHitResult.getType() != RayTraceResult.Type.ENTITY) return false;

			LivingEntity target = (LivingEntity) reachedEntityHitResult.getEntity();
			double distanceToTargetSqr = owner.distanceToSqr(target);
			boolean isValidTarget = (reachedEntityHitResult != null ? target : null) != null && reachedEntityHitResult.getType() == RayTraceResult.Type.ENTITY;

			if (isValidTarget) {
				if (reachSqr >= distanceToTargetSqr) {
					target.hurt(DamageSource.mobAttack(owner), attackDamage);
					heldStack.getItem().hurtEnemy(heldStack, target, owner);
				}
			}
		}
		return true;
	}
	
	/**
	 * Gets a rounded number from the target {@link Entity}'s horizontal {@linkplain Entity#getDeltaMovement() Delta Movement}.
	 * @param targetEntity The target entity to get the rounded horizontal {@linkplain Entity#getDeltaMovement() Delta Movement} of
	 * @return 0 if the target {@link Entity} is null, else returns the {@link Entity}'s the rounded horizontal {@linkplain Entity#getDeltaMovement() Delta Movement}
	 */
	public static double getCeiledHorizontalMovement(Entity targetEntity) {
		return targetEntity == null ? 0 : Math.sqrt((targetEntity.getDeltaMovement().x * targetEntity.getDeltaMovement().x) + (targetEntity.getDeltaMovement().z * targetEntity.getDeltaMovement().z)) * 10;
	}

	/**
	 * Disables a player's shields if the player is holding one.
	 * @param shieldHolder Target player holding/using the shield
	 * @param cooldown The cooldown (in ticks) before the shield becomes usable again
	 */
	public static void disableShield(PlayerEntity shieldHolder, int cooldown) {
		if (shieldHolder != null && isHoldingItem(shieldHolder, Items.SHIELD) && !isItemOnCooldown(shieldHolder, Items.SHIELD) && shieldHolder.getUseItem().getItem().equals(Items.SHIELD)) {
			shieldHolder.getCooldowns().addCooldown(shieldHolder.getUseItem().getItem(), cooldown);
			shieldHolder.stopUsingItem();
			shieldHolder.level.broadcastEntityEvent(shieldHolder, (byte) 30);
		}
	}

	/**
	 * Gets a standard value for the attack range of an attacking {@link LivingEntity} relative to its target.
	 * @param attacker The attacking entity
	 * @param target The affected entity
	 * @return The standard attack range value of the attacking entity
	 */
	public static double getMeleeAttackReachSqr(LivingEntity attacker, LivingEntity target) {
	    double atkRch = attacker.getBbWidth() * 1.75D + attacker.getBbWidth() / 3.6D * 0.25D;
	    
	    return atkRch * atkRch / 1.5D;
	}

	/**
	 * Checks if the {@link UUID} of an entity or player is equal to the specified {@link UUID}.
	 * @param entityToCheck entity to check UUID of 
	 * @param uuidToCheck   UUID to test entity's UUID against
	 * @return true if entity's UUID is equal to the specified UUID, else returns false
	 */
	public static boolean isUserOrEntityUUIDEqualTo(Entity entityToCheck, UUID uuidToCheck) {
		return entityToCheck.getUUID().equals(uuidToCheck);
	}

	/**
	 * Attracts {@link LivingEntity}s to a specified {@link LivingEntity}.
	 * @param targetEntity The central {@link LivingEntity} to attract other entities to
	 * @param radius The horizontal radius of the attraction effect
	 * @param height The vertical radius of the attraction effect
	 * @param attractionSpeed The speed at which entities should attract to the central {@link LivingEntity} (clamped between {@code 0.01 - 1.0D})
	 * @param exponentialPull Whether the attraction force should increase based on an inverse relationship between the attraction speed and distance between entities.
	 */
	public static void attractEntities(LivingEntity targetEntity, double radius, double height, double attractionSpeed, boolean exponentialPull) {
		if (targetEntity == null || targetEntity.noPhysics) return;

		List<LivingEntity> potentialAffectedTargets = getAllEntitiesAround(targetEntity, radius, height, radius, radius + height);

		for (LivingEntity potentialAffectedTarget : potentialAffectedTargets) {
			if (potentialAffectedTarget == null || !potentialAffectedTarget.isAlive() || potentialAffectedTarget.isNoGravity()) continue;

			double relAngleRadians = MathUtil.getRelativeAngleBetweenEntities(targetEntity, potentialAffectedTarget);
			double clampedAttractionSpeed = MathHelper.clamp(exponentialPull ? attractionSpeed / targetEntity.distanceTo(potentialAffectedTarget) : attractionSpeed, 0.01D, 1.0D);
			potentialAffectedTarget.setDeltaMovement(clampedAttractionSpeed * Math.cos(relAngleRadians), potentialAffectedTarget.getDeltaMovement().y, clampedAttractionSpeed * Math.sin(relAngleRadians));
		}
	}
	
	/**
	 * Repels {@linkplain Entity Entities} from a specified {@link Entity}.
	 * @param targetEntity The central {@link Entity} to repel other entities from
	 * @param radius The horizontal radius of the repulsion effect
	 * @param height The vertical radius of the repulsion effect
	 * @param repulsionPower The power/speed at which entities will be repelled from the specified {@link Entity} (clamped between {@code 0.01 - 10.0D}, with necessary negative conversions already done)
	 */
	public static <E extends Entity> void repelEntitiesOfClass(Entity targetEntity, Class<E> entityClassToRepel, double radius, double height, double repulsionPower) {
		if (targetEntity == null || targetEntity.noPhysics) return;

		List<E> potentialAffectedTargets = getEntitiesAround(targetEntity, entityClassToRepel, radius, height, radius, radius + height);

		for (E potentialAffectedTarget : potentialAffectedTargets) {
			if (potentialAffectedTarget == null || !potentialAffectedTarget.isAlive()) break;

			double relAngleRadians = (MathUtil.getRelativeAngleBetweenEntities(targetEntity, potentialAffectedTarget) + 90) * Math.PI / 180;
			double clampedRepulsionSpeed = MathHelper.clamp(repulsionPower, 0.01D, 10.0D);
			potentialAffectedTarget.setDeltaMovement(clampedRepulsionSpeed * Math.cos(relAngleRadians), potentialAffectedTarget.getDeltaMovement().y, clampedRepulsionSpeed * Math.sin(relAngleRadians));
		}
	}
	
	/**
	 * Repels {@link LivingEntity}s from a specified {@link LivingEntity}. Overloaded method from {@link #repelEntitiesOfClass(Entity, Class, double, double, double)}.
	 * @param targetEntity The central {@link LivingEntity} to repel other entities from
	 * @param radius The horizontal radius of the repulsion effect
	 * @param height The vertical radius of the repulsion effect
	 * @param repulsionPower The power/speed at which entities will be repelled from the specified {@link LivingEntity} (clamped between {@code 0.01 - 10.0D}, with necessary negative conversions already done)
	 */
	public static void repelEntities(LivingEntity targetEntity, double radius, double height, double repulsionPower) {
		repelEntitiesOfClass(targetEntity, Entity.class, radius, height, repulsionPower);
	}
	
	/**
	 * Makes the specified attacking {@link LivingEntity} "charge" towards a target {@link BlockPos} with a specified overshoot.
	 * @param attackingEntity The {@link LivingEntity} to have "charge" towards its target
	 * @param targetPosition The target {@link BlockPos} for the attacking {@link LivingEntity} to charge towards
	 * @param overshootAmount The extra distance traveled by the attacking {@link LivingEntity}'s charge
	 * @param chargeThreshold The maximum distance the attacking {@link LivingEntity}'s charge can travel. Set to -1 to remove the limit
	 * @param chargePercentageMod A percentage modifier to how far the charge should go relative to its length
	 */
	public static void chargeTowards(LivingEntity attackingEntity, BlockPos targetPosition, double overshootAmount, double chargeThreshold, double chargePercentageMod) {
		double relativeAngle = Math.atan2(targetPosition.getZ() - attackingEntity.getZ(), targetPosition.getX() - attackingEntity.getX());
		float hitDistanceSqr = (float) Math.sqrt((targetPosition.getZ() - attackingEntity.getZ()) * (targetPosition.getZ() - attackingEntity.getZ()) + (targetPosition.getX() - attackingEntity.getX()) * (targetPosition.getX() - attackingEntity.getX()));
		
		double targetX = Math.min(Math.cos(relativeAngle) * (hitDistanceSqr + overshootAmount), Math.cos(relativeAngle) * (hitDistanceSqr + chargeThreshold));
		double targetZ = Math.min(Math.sin(relativeAngle) * (hitDistanceSqr + overshootAmount), Math.sin(relativeAngle) * (hitDistanceSqr + chargeThreshold));
		
		final BlockPos targetPos = new BlockPos(attackingEntity.getX() + targetX, attackingEntity.getY(), attackingEntity.getZ() + targetZ).immutable();
		Vector3d chargeVec = new Vector3d(targetPos.getX() - attackingEntity.getX(), attackingEntity.getDeltaMovement().y, targetPos.getZ() - attackingEntity.getZ());
		
		attackingEntity.setDeltaMovement(chargeVec.multiply(Math.min(chargePercentageMod, 1), 1, Math.min(chargePercentageMod, 1)));
		attackingEntity.refreshDimensions();
	}
	
	/**
	 * Overloaded method for {@link #chargeTowards(LivingEntity, BlockPos, double, double, double)}.
	 * @param attackingEntity The {@link LivingEntity} to have "charge" towards its target
	 * @param targetEntity The target {@link LivingEntity} for the attacking {@link LivingEntity} to charge towards
	 * @param overshootAmount The extra distance traveled by the attacking {@link LivingEntity}'s charge
	 * @param chargeThreshold The maximum distance the attacking {@link LivingEntity}'s charge can travel. Set to -1 to remove the limit
	 * @param chargePercentageMod A percentage modifier to how far the charge should go relative to its length
	 */
	public static void chargeTowards(LivingEntity attackingEntity, LivingEntity targetEntity, double overshootAmount, double chargeThreshold, double chargePercentageMod) {
		chargeTowards(attackingEntity, targetEntity.blockPosition(), overshootAmount, chargeThreshold, chargePercentageMod);
	}
	
	/**
	 * Overloaded method for {@link #chargeTowards(LivingEntity, LivingEntity, double, double, double)}.
	 * @param attackingEntity The {@link LivingEntity} to have "charge" towards its target
	 * @param targetEntity The target {@link LivingEntity} for the attacking {@link LivingEntity} to charge towards
	 * @param overshootAmount The extra distance traveled by the attacking {@link LivingEntity}'s charge
	 * @param chargeThreshold The maximum distance the attacking {@link LivingEntity}'s charge can travel. Set to -1 to remove the limit
	 */
	public static void chargeTowards(LivingEntity attackingEntity, LivingEntity targetEntity, double overshootAmount, double chargeThreshold) {
		chargeTowards(attackingEntity, targetEntity, overshootAmount, chargeThreshold, 1);
	}

	/**
	 * Helper method which handles an animatable entity's death sequence by altering the order in which methods are called inside {@link LivingEntity#die(DamageSource)} 
	 * to match the specified {@link IAnimatableEntity}'s death animation (if it has one).
	 * @param targetAnimatable The animatable to invoke the {@code die} method onto
	 * @param deathCause The cause of death used to determine things like death loot, returning due to {@link ForgeHooks#onLivingDeath(LivingEntity, DamageSource)}, 
	 * etc.
	 * @param extraProtectedDeathFunctions Any extra protected methods in the target living animatable (which may cause compilation 
	 * errors for some reason if AT'd)
	 */
	@SuppressWarnings("deprecation")
	public static void handleAnimatableDeath(IAnimatableEntity targetAnimatable, DamageSource deathCause, Consumer<LivingEntity> extraProtectedDeathFunctions) {
		if (targetAnimatable.getDeathAnim() == null || !(targetAnimatable instanceof LivingEntity || ForgeHooks.onLivingDeath((LivingEntity) targetAnimatable, deathCause))) return;

		LivingEntity livingAnimatable = (LivingEntity) targetAnimatable;
		Entity causeEntity = deathCause.getEntity();
		LivingEntity killerEntity = livingAnimatable.getKillCredit();

		if (!livingAnimatable.removed && !livingAnimatable.dead) {
			if (targetAnimatable.getDeathAnim().getWrappedAnimProgress() == 0) {
				if (livingAnimatable.deathScore >= 0 && killerEntity != null)  killerEntity.awardKillScore(livingAnimatable, livingAnimatable.deathScore, deathCause);
				if (livingAnimatable.isSleeping()) livingAnimatable.stopSleeping();

				livingAnimatable.dead = true;
				livingAnimatable.getCombatTracker().recheckStatus();
			}

			if (targetAnimatable.getDeathAnim().hasAnimationFinished()) {			
				if (livingAnimatable.level instanceof ServerWorld) {
					ServerWorld curServerWorld = (ServerWorld) livingAnimatable.level;

					if (causeEntity != null) causeEntity.killed(curServerWorld, livingAnimatable);

					extraProtectedDeathFunctions.accept(livingAnimatable);
					livingAnimatable.createWitherRose(killerEntity);
				}
				
				livingAnimatable.level.broadcastEntityEvent(livingAnimatable, (byte) 3);
				livingAnimatable.setPose(Pose.DYING);
			}
		}
	}

	/**
	 * Attempts to spawn an {@link ItemEntity} with additional motion.
	 *
	 * @param ownerEntity The {@linkplain LivingEntity owner entity} that will spawn the specified {@link ItemEntity}.
	 * @param targetStack The {@link ItemStack} with which the {@link ItemEntity} will be spawned.
	 * @param additionalMotion The additional motion to apply to the spawned {@link ItemEntity}.
	 *
	 * @return The spawned {@link ItemEntity} or {@code null} if the specified {@link ItemStack} is empty/the level is client-side.
	 */
	public static ItemEntity spawnItemWithMotion(LivingEntity ownerEntity, ItemStack targetStack, Vector3d additionalMotion) {
		if (!targetStack.isEmpty() && !ownerEntity.level.isClientSide) {
			ItemEntity targetItemEntity = new ItemEntity(ownerEntity.level, ownerEntity.getX(), ownerEntity.getY(), ownerEntity.getZ(), targetStack);

			targetItemEntity.setDefaultPickUpDelay();

			if (ownerEntity.captureDrops() != null) ownerEntity.captureDrops().add(targetItemEntity);
			else {
				targetItemEntity.setDeltaMovement(targetItemEntity.getDeltaMovement().add(additionalMotion));
				ownerEntity.level.addFreshEntity(targetItemEntity);
			}
			return targetItemEntity;
		}

		return null;
	}
}
