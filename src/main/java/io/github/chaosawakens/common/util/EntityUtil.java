package io.github.chaosawakens.common.util;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

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
	public static <E extends Entity> List<E> getEntitiesAround(LivingEntity user, Class<E> entityClass, double dX, double dY, double dZ, double radius) {
		Predicate<Entity> distPredicate = living -> living != user && (user.getTeam() != null && living.getTeam() != null ? !living.getTeam().equals(user.getTeam()) : living.isAlive()) && living.getClass() != user.getClass() && user.distanceTo(living) <= radius + living.getBbWidth() / 2F;
		return user.level.getEntitiesOfClass(entityClass, user.getBoundingBox().inflate(dX, dY, dZ), distPredicate);
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
	public static <E extends Entity> List<E> getEntitiesAround(LivingEntity user, Class<E> entityClass, double dX, double dY, double dZ, Predicate<E> detectionConditions) {
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
	public static <E extends Entity> List<E> getEntitiesAroundNoPredicate(LivingEntity user, Class<E> entityClass, double dX, double dY, double dZ, double radius) {
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
	public static List<LivingEntity> getAllEntitiesAround(LivingEntity user, double dX, double dY, double dZ, double radius) {
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
	public static List<PlayerEntity> getAllPlayersAround(LivingEntity user, double dX, double dY, double dZ, double radius) {
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
		targetEntity.yRot = targetEntity.yRotO;
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
	 * Checks if the player's Curios slots are empty
	 *
	 * @param targetPlayer Player to check the Curios slots of
	 * @return True if Curios slots are empty or Curios is not installed, else returns False
	 */
	public static boolean areCuriosSlotsEmpty(PlayerEntity targetPlayer) {
		if (ModList.get().isLoaded("curios")) {
			LazyOptional<IItemHandlerModifiable> curiosHandler = CuriosApi.getCuriosHelper().getEquippedCurios(targetPlayer);

			if (curiosHandler.isPresent()) {
				IItemHandlerModifiable handler = curiosHandler.orElseThrow(IllegalStateException::new);

				for (int slot = 0; slot < handler.getSlots(); slot++) {
					if (!handler.getStackInSlot(slot).isEmpty()) {
						return false;
					}
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

			AxisAlignedBB bb = owner.getBoundingBox().expandTowards(viewVec.scale(reach)).inflate(4.0D, 4.0D, 4.0D);
			EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(curWorld, owner, eyeVec, targetVec, bb, EntityPredicates.NO_CREATIVE_OR_SPECTATOR);

			if (result == null || !(result.getEntity() instanceof LivingEntity) || result.getType() != RayTraceResult.Type.ENTITY) return false;

			LivingEntity target = (LivingEntity) result.getEntity();
			double distanceToTargetSqr = owner.distanceToSqr(target);
			boolean resultBool = (result != null ? target : null) != null && result.getType() == RayTraceResult.Type.ENTITY;

			if (resultBool) {
				if (reachSqr >= distanceToTargetSqr) {
					target.hurt(DamageSource.mobAttack(owner), attackDamage);
					heldStack.getItem().hurtEnemy(heldStack, target, owner);
				}
			}		
		}
		return true;
	}
	
	/**
	 * Disables a player's shields if the player is holding one.
	 * @param shieldHolder Target player holding/using the shield
	 * @param cooldown The cooldown (in ticks) before the shield becomes usable again
	 */
	public static void disableShield(PlayerEntity shieldHolder, int cooldown) {
		if (shieldHolder != null && isHoldingItem(shieldHolder, Items.SHIELD) && shieldHolder.getUseItem().getItem().equals(Items.SHIELD)) {
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
		if (target == null) return 0;
		return (attacker.getBbWidth() * 2.0F * attacker.getBbWidth() * 2.0F + target.getBbWidth()) / 4;
	}
	
	/**
	 * Checks if the {@link UUID} of an entity or player is equal to the specified {@link UUID}.
	 * 
	 * @param entityToCheck entity to check UUID of
	 * @param uuidToCheck   UUID to test entity's UUID against
	 * @return true if entity's UUID is equal to the specified UUID, else returns false
	 */
	public static boolean isUserOrEntityUUIDEqualTo(Entity entityToCheck, UUID uuidToCheck) {
		return entityToCheck.getUUID().equals(uuidToCheck);
	}
}
