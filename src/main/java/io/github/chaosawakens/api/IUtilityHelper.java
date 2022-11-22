package io.github.chaosawakens.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.FogRenderer.FogType;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;

/**
 * A mini API/Utility class full of general methods that can make your coding experience a little more bearable.
 * 
 * @author Meme Man
 *
 */
public interface IUtilityHelper {

	///////////////////////////////
	//         Variables         //
	///////////////////////////////

	List<Block> Queue = new ArrayList<>();
	
	//https://en.wikipedia.org/wiki/Machine_epsilon
	/**
	 * The smallest possible number greater than 1.0F (0.0000001)
	 */
	final float EPSILON = 1.0E-8F;
	
	/**
	 * The absolute maximum number the signed primitive type int can hold
	 */
	final int THIRTY_TWO_BIT_INTEGER_LIMIT = 2147483647;
	
	/**
	 * The absolute minimum number the signed primitive type int can hold
	 */
	final int NEGATIVE_THIRTY_TWO_BIT_INTEGER_LIMIT = -2147483647;

	///////////////////////////////
	//         Functions         //
	///////////////////////////////

	/**
	 * Set the reach distance of a player to a new value
	 * 
	 * @param player        player to apply modified reach to
	 * @param newReachValue the new reach value applied to the attribute the player
	 *                      has, it doesn't add to the original attribute value, but
	 *                      sets it to a new value
	 */
	static void setReach(PlayerEntity player, int newReachValue) {
		player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(newReachValue);
	}

	/**
	 * Sets the reach distance of a player to a new value, but only in the condition
	 * that the player is holding the stack defined
	 * 
	 * @param player        player to apply modified reach to
	 * @param newReachValue the new reach value applied to the attribute the player
	 *                      has, it doesn't add to the original attribute value, but
	 *                      sets it to a new value
	 * @param stack         the item/itemstack needed (to be held in the player's
	 *                      main hand) to activate the attribute modifier
	 */
	static void setReach(PlayerEntity player, int newReachValue, ItemStack stack) {
		if (player.getItemInHand(Hand.MAIN_HAND) == stack) {
			player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(newReachValue);
		}
	}

	/**
	 * Get the reach distance of the player
	 * 
	 * @param player player to check reach distance attribute value of
	 */
	static void getPlayerReach(PlayerEntity player) {
		player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getBaseValue();
	}

	/**
	 * Set the swim speed of a player to a new value
	 * 
	 * @param player            player to apply modified swim speed to
	 * @param newSwimSpeedValue the new swim speed value applied to the attribute
	 *                          the player has, it doesn't add to the original
	 *                          attribute value, but sets it to a new value
	 */
	static void setSwimSpeed(PlayerEntity player, int newSwimSpeedValue) {
		player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(newSwimSpeedValue);
	}

	/**
	 * Sets the swim speed of a player to a new value, but only in the condition
	 * that the player is holding the stack defined
	 * 
	 * @param player            player to apply modified reach to
	 * @param newSwimSpeedValue the new swim speed value applied to the attribute
	 *                          the player has, it doesn't add to the original
	 *                          attribute value, but sets it to a new value
	 * @param stack             the item/itemstack needed (to be held in the
	 *                          player's main hand) to activate the attribute
	 *                          modifier
	 */
	static void setSwimSpeed(PlayerEntity player, int newSwimSpeedValue, ItemStack stack) {
		if (player.getItemInHand(Hand.MAIN_HAND) == stack) {
			player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(newSwimSpeedValue);
		}
	}

	/**
	 * Get the swim speed of the player
	 * 
	 * @param player player to check swim speed attribute value of
	 */
	static void getPlayerSwimSpeed(PlayerEntity player) {
		player.getAttribute(ForgeMod.SWIM_SPEED.get()).getBaseValue();
	}

	/**
	 * Sets the gravity of a player to a new value
	 * 
	 * @param player                player to apply modified gravity to
	 * @param newPlayerGravityValue the new gravity value appled to the attribute
	 *                              the player has, it doesn't add to the original
	 *                              value, but sets it to a new value
	 */
	static void setPlayerGravity(PlayerEntity player, int newPlayerGravityValue) {
		player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(newPlayerGravityValue);
	}

	/**
	 * Sets the gravity of a player to a new value, but only in the condition that
	 * the player is holding the stack defined
	 * 
	 * @param player                player to apply modified gravity to
	 * @param newPlayerGravityValue the new gravity value appled to the attribute
	 *                              the player has, it doesn't add to the original
	 *                              value, but sets it to a new value
	 * @param stack                 the item/itemstack needed (to be held in the
	 *                              player's main hand) to activate the attribute
	 *                              modifier
	 */
	static void setPlayerGravity(PlayerEntity player, int newPlayerGravityValue, ItemStack stack) {
		if (player.getItemInHand(Hand.MAIN_HAND) == stack) {
			player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(newPlayerGravityValue);
		}
	}

	/**
	 * Get the gravity of the player
	 * 
	 * @param player player to check gravity attribute value of
	 */
	static void getPlayerGravity(PlayerEntity player) {
		player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).getBaseValue();
	}

	/**
	 * Summon particles into the world
	 * 
	 * @param world        world to summon particles in
	 * @param particleData particle data/particle type, just grab whatever particle
	 *                     type you want from the <code>ParticleTypes</code> class
	 * @param x            x position to add particles to
	 * @param y            y position to add particles to
	 * @param z            z position to add particles to
	 * @param randomXValue random value within the x position to add particles
	 * @param randomYValue random value within the y position to add particles
	 * @param randomZValue random value within the z position to add particles
	 * @param amount       amount of particles to add
	 */
	@SuppressWarnings("resource")
	static void addParticles(World world, IParticleData particleData, double x, double y, double z, double randomXValue, double randomYValue, double randomZValue, int amount) {
		if (world.isClientSide) {
			for (int particleCount = 0; particleCount <= amount; particleCount++) {
				Minecraft.getInstance().particleEngine.createParticle(particleData, x + getRandomHorizontalPos(randomXValue, world), y + getRandomVerticalPos(randomYValue, world), z + getRandomHorizontalPos(randomZValue, world), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	/**
	 * Add a certain number of living entities within a bounding box in a certain
	 * position
	 * 
	 * @param world         world to add entities in
	 * @param entitiesToAdd the list<LivingEntity> of entities to add
	 * @param pos           position to add entities in. CANNOT BE NULL!
	 * @param x             x position to add entities
	 * @param y             y position to add entities
	 * @param z             z position to add entities
	 * @param xSize         the x size of the bounding box
	 * @param ySize         the y size of the bounding box
	 * @param zSize         the z size of the bounding box
	 * @param amountToSpawn amount of entities inside the entitiesToAdd list, that
	 *                      amount will be summoned
	 */
	static void addLivingEntities(World world, List<LivingEntity> entitiesToAdd, @Nonnull BlockPos pos, double x, double y, double z, double xSize, double ySize, double zSize, int amountToSpawn) {
		if (!world.isClientSide) {
			amountToSpawn = entitiesToAdd.size();
			entitiesToAdd = world.getEntitiesOfClass(LivingEntity.class, (new AxisAlignedBB(pos).inflate(xSize, ySize, zSize)));
			for (int entityCount = 0; entityCount <= amountToSpawn; entityCount++) {
				if (pos != null) {
					world.addFreshEntity((Entity) entitiesToAdd);
				}
			}
		}
	}

	/**
	 * Summon a singular entity in a certain position (checks if the world isn't
	 * client side first by static)
	 * 
	 * @param world          world to summon entity in
	 * @param entityToSummon entity to be summoned
	 * @param pos            position to summon entity in
	 */
	static void summonLivingEntity(World world, LivingEntity entityToSummon, BlockPos pos) {
		if (!world.isClientSide) {
			world.addFreshEntity(entityToSummon);
		}
	}

	/**
	 * Adds fog after checking if the world is client side, to save you a little
	 * hassle
	 * 
	 * @param world   world to add fog in
	 * @param density fog density
	 * @param b       boolean
	 * @param pTicks  particle ticks
	 * @param type    fog type
	 * @param info    active render information
	 */
	static void addFog(World world, float density, boolean b, float pTicks, FogType type, ActiveRenderInfo info) {
		if (world.isClientSide) {
			FogRenderer.setupFog(info, type, density, false, pTicks);
		}
	}

	/**
	 * Adds the block specified to the <code>Blacklist</code> tag
	 * 
	 * @param blockToAdd block to add <code>Blacklist</code> tag to
	 */
	static void addBlockToDuplicationsBlackList(Block blockToAdd) {
		blockToAdd.getTags().add((ResourceLocation) CATags.Blocks.tag("blacklist"));
	}

	/**
	 * Adds the block specified to the <code>Whitelist</code> tag
	 * 
	 * @param blockToAdd block to add <code>Whitelist</code> tag to
	 */
	static void addBlockToDuplicationsWhiteList(Block blockToAdd) {
		blockToAdd.getTags().add((ResourceLocation) CATags.Blocks.tag("whitelist"));
	}

	/**
	 * Sets the item texture to something else in-game.
	 * 
	 * @param itemToSet        item to replace texture of
	 * @param pathWithTextures path containing textures
	 * @param textureName      name of texture file
	 * @param pathName         name of path, could simply be some characters in the
	 *                         name (e.g.
	 *                         <code>assets.chaosawakens.textures.item.critter_cage</code>
	 *                         can simply be <code>critter_cage</code>, or you could
	 *                         run the <code>getPath()</code> method off of a
	 *                         certain item in that folder/package you want to grab)
	 */
	static void setItemTexture(ItemStack itemToSet, ResourceLocation pathWithTextures, String textureName, String pathName) {
		boolean hasTextures = pathWithTextures.getPath().contains(pathName);
		if (hasTextures) {
			ItemModelsProperties.register(itemToSet.getItem(), pathWithTextures, (stack, world, entity) -> {
				stack = itemToSet;
				String modid = entity.getType().getRegistryName().getNamespace();
				String regName = entity.getType().getRegistryName().toString().replace(modid, "").replace(":", "");
				boolean nameMatches = entity.getType().getRegistryName().toString().contains(regName);
				return hasTextures && nameMatches ? 1.0F : 0.0F;
			});
		}
	}
	
	/**
	 * Mainly used for beavers and such, mainly used for block break goal classes
	 * @param entity entity breaking the blocks
	 */
	@SuppressWarnings("unused")
	static void breakBlockInFrontOf(LivingEntity entity) {
		Vector3d lookVec = entity.getLookAngle();
		Vector3d blockVec = lookVec.add(entity.position());
		RayTraceResult result = entity.pick(20.0D, 0, false);
		
		if (result.getType() != RayTraceResult.Type.BLOCK) return;
		
		BlockPos blockToBreakPos = ((BlockRayTraceResult) result).getBlockPos();
		BlockState blockStateToBreak = entity.level.getBlockState(blockToBreakPos);
		boolean canDestroy = blockStateToBreak.canEntityDestroy(entity.level, blockToBreakPos, entity);
		do {
			entity.lookAt(EntityAnchorArgument.Type.EYES, blockVec);
			canDestroy = true;
		} while (lookVec == blockVec && getDestroyProgress(blockStateToBreak, entity, entity.level, blockToBreakPos) != 30.0F);
	}
	
	/**
	 * Use for custom entity knockback/custom movement related stuff
	 * @param entity entity to send velocity packet from, has to be a ServerPlayerEntity
	 */
	static void sendServerPlayerVelocityPacket(Entity entity) {
		if (entity instanceof ServerPlayerEntity) {
			((ServerPlayerEntity)entity).connection.send(new SEntityVelocityPacket(entity));
		}
	}
	
	/**
	 * Allows for storing blocks into a list, for a certain entity to break said blocks.
	 * @param horizontalBlocksToBreak list of blocks the specified entity can break horizontally
	 * @param verticalBlocksToBreak list of blocks the specified entity can break vertically
	 * @param entityBreakingBlocks the entity breaking the blocks
	 */
	static void handleBlockBreaking(List<Block> horizontalBlocksToBreak, List<Block> verticalBlocksToBreak, LivingEntity entityBreakingBlocks) {
		if (!entityBreakingBlocks.isAlive()) return;
		
		boolean flag = false;
		AxisAlignedBB axisalignedbb = entityBreakingBlocks.getBoundingBox().inflate(0.2D);

		if (entityBreakingBlocks.horizontalCollision && ForgeEventFactory.getMobGriefingEvent(entityBreakingBlocks.level, entityBreakingBlocks)) {
			for(BlockPos blockpos : BlockPos.betweenClosed(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.maxY), MathHelper.floor(axisalignedbb.maxZ))) {
				BlockState blockstate = entityBreakingBlocks.level.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				if (horizontalBlocksToBreak.contains(block)) {
					flag = entityBreakingBlocks.level.destroyBlock(blockpos, true, entityBreakingBlocks) || flag;
				}
			}
		}

		if (entityBreakingBlocks.verticalCollision && ForgeEventFactory.getMobGriefingEvent(entityBreakingBlocks.level, entityBreakingBlocks)) {
			for(BlockPos blockpos : BlockPos.betweenClosed(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.maxY), MathHelper.floor(axisalignedbb.maxZ))) {
				BlockState blockstate = entityBreakingBlocks.level.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				if (verticalBlocksToBreak.contains(block)) {
					flag = entityBreakingBlocks.level.destroyBlock(blockpos, true, entityBreakingBlocks) || flag;
				}
			}
		}
	}
	
	/**
	 * Allows for storing blocks into a list, for a certain entity to not be able to break no matter what.
	 * @param horizontalBlocksToBreak list of blocks the specified entity can't break horizontally
	 * @param verticalBlocksToBreak list of blocks the specified entity can't break vertically
	 * @param entityBreakingBlocks the entity unable to break the blocks
	 */
	static void handleBlockBreakingBlacklist(List<Block> blacklistHorizontalBlocksToBreak, List<Block> blacklistVerticalBlocksToBreak, LivingEntity entityBreakingBlocks) {
		if (!entityBreakingBlocks.isAlive()) return;
		
		boolean flag = false;
		AxisAlignedBB axisalignedbb = entityBreakingBlocks.getBoundingBox().inflate(0.2D);

		if (entityBreakingBlocks.horizontalCollision && ForgeEventFactory.getMobGriefingEvent(entityBreakingBlocks.level, entityBreakingBlocks)) {
			for(BlockPos blockpos : BlockPos.betweenClosed(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.maxY), MathHelper.floor(axisalignedbb.maxZ))) {
				BlockState blockstate = entityBreakingBlocks.level.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				if (!blacklistHorizontalBlocksToBreak.contains(block)) {
					flag = entityBreakingBlocks.level.destroyBlock(blockpos, true, entityBreakingBlocks) || flag;
				}
			}
		}

		if (entityBreakingBlocks.verticalCollision && ForgeEventFactory.getMobGriefingEvent(entityBreakingBlocks.level, entityBreakingBlocks)) {
			for(BlockPos blockpos : BlockPos.betweenClosed(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.maxY), MathHelper.floor(axisalignedbb.maxZ))) {
				BlockState blockstate = entityBreakingBlocks.level.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				if (!blacklistVerticalBlocksToBreak.contains(block)) {
					flag = entityBreakingBlocks.level.destroyBlock(blockpos, true, entityBreakingBlocks) || flag;
				}
			}
		}
	}

	///////////////////////////////
	//          Booleans         //
	///////////////////////////////

	/**
	 * Checks if an entity is moving in any direction
	 * 
	 * @param entity entity to check
	 * @return true if entity is moving, else returns false
	 */
	static boolean isMoving(LivingEntity entity) {
		return entity.getDeltaMovement().x != 0 || entity.getDeltaMovement().y != 0 || entity.getDeltaMovement().z != 0;
	}

	/**
	 * Check the speed of an entity
	 * 
	 * @param speed         speed entity is moving at
	 * @param entityToCheck entity to check moving speed of
	 * @return true if the speed of the entity is equal to the speed parameter, else
	 *         returns false
	 */
	static boolean isMovingAtVelocity(float speed, LivingEntity entityToCheck) {
		return entityToCheck.getSpeed() == speed;
	}

	/**
	 * Checks the uuid of an entity or player
	 * 
	 * @param entityToCheck entity to check uuid of
	 * @param uuidToCheck   uuid of entity to check
	 * @return true if entity's uuid is equal to uuidToCheck, else returns false
	 */
	static boolean isUserOrEntityUUIDEqualTo(Entity entityToCheck, UUID uuidToCheck) {
		return entityToCheck.getUUID().equals(uuidToCheck);
	}

	/**
	 * Compares the name of an entity or player to the specified string
	 * 
	 * @param nameToCheck   name of entity to check
	 * @param entityToCheck entity to check name of
	 * @return true if entity name is equal to the name provided, else returns false
	 */
	static boolean isEntityNameEqualTo(Entity entityToCheck, String nameToCheck) {
		return entityToCheck.getName().getString().equals(nameToCheck);
	}

	/**
	 * Check if a block can be duplicated by the duplication tree
	 * 
	 * @param blockToCheck block to check for duplicatability (that's a word now)
	 * @return true if the block to check is duplicatable, else returns false
	 */
	static boolean isDuplicatable(Block blockToCheck) {
		return blockToCheck.is(CATags.Blocks.WHITELIST) || !blockToCheck.is(CATags.Blocks.BLACKLIST) || (!blockToCheck.is(CATags.Blocks.BLACKLIST) && !blockToCheck.is(CATags.Blocks.WHITELIST));
	}
	
	/**
	 * Check if a world is client side
	 * @param worldToCheck world to evaluate dist for
	 * @return true if the world is client side, else returns false
	 */
	static boolean isClientSide(World worldToCheck) {
		return worldToCheck.isClientSide();
	}
	
	/**
	 * Check if an entity is handling stuff client side
	 * @param entityToCheck entity to evaluate dist for
	 * @return true if the entity is on the client side, else returns false
	 */
	static boolean isClientEntity(Entity entityToCheck) {
		return isClientSide(entityToCheck.getCommandSenderWorld());
	}
	
	/**
	 * Check if a player is handling stuff client side
	 * @param playerToCheck player to evaluate dist for
	 * @return true if the player is on the client side, else returns false
	 */
	static boolean isClientPlayer(PlayerEntity playerToCheck) {
		return isClientSide(playerToCheck.getCommandSenderWorld()) || playerToCheck instanceof ClientPlayerEntity;
	}

	/**
	 * Checks if an entity is moving (voluntarily or not) on the y axis
	 * 
	 * @param entity entity to check
	 * @return true if entity is moving vertically, else returns false
	 */
	static boolean isMovingVertically(LivingEntity entity) {
		return entity.getDeltaMovement().y != 0;
	}

	static boolean isBoss(LivingEntity entityToCheck) {
		return false;
	}

	/**
	 * Gets all of an entity's active effects if it has any
	 * 
	 * @param entity entity to check for effects
	 * @return true if any effects are present, else returns false
	 */
	static boolean hasActiveEffects(LivingEntity entity) {
		return !entity.getActiveEffects().isEmpty();
	}

	/**
	 * Checks if an entity has a bounding box/"hitbox"
	 * 
	 * @param entity entity to check for bounding box
	 * @return true if entity has a bounding box, else returns false
	 */
	static boolean hasBoundingBox(LivingEntity entity) {
		return entity.getBoundingBox() != null && entity.getBoundingBox().getSize() > 0;
	}

	/**
	 * Checks if an item specified is in the player's inventory
	 * 
	 * @param stackToCheck stack to check in inventory
	 * @return true if the player has the specified item in their inventory, else
	 *         returns false
	 */
	static boolean hasItemInInventory(PlayerEntity player, ItemStack stackToCheck) {
		return player.inventory.items.contains(stackToCheck);
	}

	// WIP
	/*
	 * static boolean hasItemInInventoryByAmount(PlayerEntity player, ItemStack
	 * stackToCheck, int amount) { return NonNullList.withSize(amount, stackToCheck)
	 * != null; }
	 */

	/**
	 * Checks if an entity matches another entity stored inside the itemstack (which
	 * will most likely be a critter cage)
	 * 
	 * @param entity    entity to check name of
	 * @param cageStack itemstack to grab data from
	 * @return true if entity name matches the one stored in the itemstack, else
	 *         returns false
	 */
	static boolean matchesNameOfEntityStoredInItemStack(Entity entity, ItemStack cageStack) {
		return entity.getType().getRegistryName().toString().matches(cageStack.getTag().getString(entity.getType().getRegistryName().toString())) && entity != null && cageStack != null;
	}

	/**
	 * Checks if a block is part of the duplication queue list, can be used to
	 * arrange duplication patterns if there's multiple pattern lists
	 * 
	 * @param blockToCheck block to check for queue validity
	 * @return true if the block is inside the <code>Queue</code> list, else returns
	 *         false
	 */
	static boolean isInQueueForDuplication(Block blockToCheck) {
		return Queue.contains(blockToCheck);
	}
	
	/**
	 * Checks if a certain thing is part of chaos awakens
	 * 
	 * @param name resource location/registry to check
	 * @return true if it's part of chaos awakens, else returns false
	 */
	static boolean isChaosAwakens(ResourceLocation name) {
		return name.getNamespace().equalsIgnoreCase(ChaosAwakens.MODID);
	}

	/**
	 * Checks if an itemstack has an entity/entity data stored inside it
	 * 
	 * @param stackToCheck stack to check for entities
	 * @return true if the itemstack has an entity stored in it, else returns false
	 */
	static boolean stackHasMob(ItemStack stackToCheck) {
		return !(stackToCheck.isEmpty()) && stackToCheck.hasTag() && stackToCheck.getTag().contains("entity");
	}

	/**
	 * Checks if something is from a mod, specified by the modid
	 * 
	 * @param loc   resource location/registry to check
	 * @param modid the modid for the stuff you wanna get, from the loc
	 * @return true if stuff is from specified modid, else returns false
	 */
	static boolean isFromMod(ResourceLocation loc, String modid) {
		return loc.getNamespace().equalsIgnoreCase(modid);
	}

	/**
	 * Checks if an entity in air
	 * 
	 * @param entity entity to check
	 * @return true if entity is standing on air, else returns false
	 */
	static boolean isInAir(Entity entity) {
		return entity.blockPosition().below() == null;
	}

	/**
	 * Checks if an entity is completely surrounded by air from 4 cardinal
	 * directions
	 * 
	 * @param entity entity to check
	 * @return true if entity is completely surrounded by air from all
	 *         sides/cardinal directions, else returns false
	 */
	static boolean isSurroundedByAir(Entity entity) {
		return entity.blockPosition().below() == null && entity.blockPosition().above() == null && entity.blockPosition().east() == null && entity.blockPosition().west() == null && entity.blockPosition().north() == null && entity.blockPosition().south() == null;
	}
	
	/**
	 * Check if the <code>checkBlockConnectionsAtACertainDistance</code> list contains a certain position
	 * 
	 * @param world world to check
	 * @param pos position to verify
	 * @param blockToCheck block of the position to check
	 * @param blockToMatch block to match with the block to check
	 * @param distance distance of positions to verify
	 * @param directionToCheckIn direction in which the position list is verified
	 * @return true if the specified position is in the list, else returns false
	 */
	static boolean checkBlockConnectionList(ServerWorld world, BlockPos pos, Block blockToCheck, Block blockToMatch, int distance, Direction directionToCheckIn) {
		return checkBlockConnectionsAtACertainDistance(world, pos, blockToCheck, blockToMatch, distance, directionToCheckIn).contains(pos);
	}
	
	/**
	 * Checks if the player is wearing a full armor set, specified by the parameters below
	 * 
	 * @param player player wearing the armor
	 * @param helmet helmet item being worn by the player in the HEAD slot
	 * @param chestplate chestplate item being worn by the player in the CHEST slot
	 * @param leggings legging item being worn by the player in the LEGS slot
	 * @param boots boot item being worn by the player in the FEET slot
	 * @return true if the player armor slots contain the specified armor pieces, else returns false
	 */
    static boolean isFullArmorSet(PlayerEntity player, Item helmet, Item chestplate, Item leggings, Item boots) {
    	if (player == null) return false;
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

	///////////////////////////////
	//          Doubles          //
	///////////////////////////////

	/**
	 * Gets the distance between 2 positions on the x and z axis
	 * 
	 * @param a starting blockpos
	 * @param b finishing blockpos
	 * @return the distance between a and b
	 */
	static double getHorizontalDistanceBetween(BlockPos a, BlockPos b) {
		int x = Math.abs(a.getX() - b.getX());
		int z = Math.abs(a.getZ() - b.getZ());

		return Math.sqrt(x * x + z * z);
	}

	/**
	 * Gets the distance between 2 positions on the y axis
	 * 
	 * @param a starting blockpos
	 * @param b finishing blockpos
	 * @return the distance between a and b
	 */
	static double getVerticalDistanceBetween(BlockPos a, BlockPos b) {
		int y = Math.abs(a.getY() - b.getY());

		return Math.sqrt(y * y);
	}

	/**
	 * Gets the distance between 2 positions on all axis
	 * 
	 * @param a starting blockpos
	 * @param b finishing blockpos
	 * @return the distance between a and b
	 */
	static double getDistanceBetween(@Nullable BlockPos a, @Nullable BlockPos b) {
		int x = Math.abs(a.getX() - b.getX());
		int y = Math.abs(a.getY() - b.getY());
		int z = Math.abs(a.getZ() - b.getZ());

		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Gets the horizontal distance (distance on the x and z axis) between an entity
	 * and a block position
	 * 
	 * @param entity point a, where entity is
	 * @param pos    point b, where the destination pos is
	 * @return the distance (on the x and z axis) between the entity and the
	 *         destination pos
	 */
	static double getHorizontalDistanceBetweenEntityAndBlockPos(Entity entity, BlockPos pos) {
		int x = Math.abs((int) entity.getX() - pos.getX());
		int z = Math.abs((int) entity.getZ() - pos.getZ());

		return Math.sqrt(x * x + z * z);
	}

	/**
	 * Gets the vertical distance (distance on the y axis) between an entity and a
	 * block position
	 * 
	 * @param entity point a, where entity is
	 * @param pos    point b, where the destination pos is
	 * @return the distance (on the y axis) between the entity and the destination
	 *         pos
	 */
	static double getVerticalDistanceBetweenEntityAndBlockPos(Entity entity, BlockPos pos) {
		int y = Math.abs((int) entity.getY() - pos.getY());

		return Math.sqrt(y * y);
	}
	
	/**
	 * Returns an angle between 2 entities via the use of polar coordinates
	 * @param first the first entity to get its angle
	 * @param second the second entity to get its angle
	 * @return the angle between both entities
	 */
	static double getAngleBetweenEntities(Entity first, Entity second) {
        return Math.atan2(second.getZ() - first.getZ(), second.getX() - first.getX()) * (180 / Math.PI) + 90;
    }

	/**
	 * Gets distance between entity and a certain point
	 * 
	 * @param entity point a, where entity is
	 * @param pos    point b, where the destination pos is
	 * @return the distance between the entity and the destination pos
	 */
	static double getDistanceBetweenEntityAndBlockPos(Entity entity, BlockPos pos) {
		int x = Math.abs((int) entity.getX() - pos.getX());
		int y = Math.abs((int) entity.getY() - pos.getY());
		int z = Math.abs((int) entity.getZ() - pos.getZ());

		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Gets the horizontal distance (on the x and z axis) between 2 entities
	 * 
	 * @param entityA entity point a, starting pos to check
	 * @param entityB entity point b, finishing pos to check
	 * @return the distance (on the x and z axis) between the 2 entities
	 */
	static double getHorizontalDistanceBetweenEntities(Entity entityA, Entity entityB) {
		double x = Math.abs(entityA.getX() - entityB.getX());
		double z = Math.abs(entityA.getZ() - entityB.getZ());

		return Math.sqrt(x * x + z * z);
	}

	/**
	 * Gets the vertical distance (on the y axis) between 2 entities
	 * 
	 * @param entityA entity point a, starting pos to check
	 * @param entityB entity point b, finishing pos to check
	 * @return the distance (on the y axis) between the 2 entities
	 */
	static double getVerticalDistanceBetweenEntities(Entity entityA, Entity entityB) {
		double y = Math.abs(entityA.getY() - entityB.getY());

		return Math.sqrt(y * y);
	}

	/**
	 * Gets distance between 2 entities
	 * 
	 * @param entityA entity point a, starting pos to check
	 * @param entityB entity point b, finishing pos to check
	 * @return the distance between the 2 entities
	 */
	static double getDistanceBetweenEntities(@Nullable Entity entityA, @Nullable Entity entityB) {
		double x = Math.abs(entityA.getX() - entityB.getX());
		double y = Math.abs(entityA.getY() - entityB.getY());
		double z = Math.abs(entityA.getZ() - entityB.getZ());

		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Gets a random position horizontally. This is mainly used for particle
	 * summoning, but can be used for other purposes (Thanks to cyclic for this
	 * piece of code)
	 * 
	 * @param random random value
	 * @param world  world to get pos in
	 * @return random position to execute an event in
	 */
	static double getRandomHorizontalPos(double random, World world) {
		return (world.random.nextDouble() - 0.5D) * random;
	}

	/**
	 * Gets a random position vertically. This is mainly used for particle
	 * summoning, but can be used for other purposes (Thanks to cyclic for this
	 * piece of code)
	 * 
	 * @param random random value
	 * @param world  world to get pos in
	 * @return random position to execute an event in
	 */
	static double getRandomVerticalPos(double random, World world) {
		return world.random.nextDouble() - 0.1D * random;
	}

	/**
	 * Get the square root of a number/double without the hassle of typing
	 * <code>Math.sqrt()</code>
	 * 
	 * @param numberToGetSquareRootOf number to find square root of
	 * @return square root of the number entered
	 */
	static double squareRoot(double numberToGetSquareRootOf) {
		return Math.sqrt(numberToGetSquareRootOf);
	}

	/**
	 * Get the number squared
	 * 
	 * @param numberSquared number to get squared (number * number) --How could you
	 *                      not know this and code?
	 * @return the number entered squared (number * number)
	 */
	static double squared(double numberSquared) {
		return numberSquared * numberSquared;
	}

	/**
	 * I don't know how this could be used, I genuinely made this out of boredom -
	 * Meme Man
	 * 
	 * @param a          variable a
	 * @param b          variable b
	 * @param c          variable c
	 * @param isAddition whether or not the operation should be addition. If false,
	 *                   it'll be subtraction.
	 * @return <code>(-b + ((sqrt of bsq) - 4ac) / 2a)</code> if <code>isAddition</code>
	 *         is true, else <code>(-b - ((sqrt of bsq) - 4ac) / 2a)</code>
	 */
	static double quadraticFormula(double a, double b, double c, boolean isAddition) {
		if (isAddition) {
			return -b + ((squareRoot(squared(b))) - 4 * a * c) / 2 * a;
		}
		return -b - ((squareRoot(squared(b))) - 4 * a * c) / 2 * a;
	}
	
	///////////////////////////////
	//           Floats          //
	///////////////////////////////
	
	/**
	 * An implementation of the <code>Block#getDestroyProgress</code> method that allows a <code>LivingEntity</code> parameter
	 * 
	 * W.I.P
	 * 
	 * @param state state to get destruction progress of
	 * @param entity entity destroying the block
	 * @param world world in which the block is being destroyed
	 * @param pos position of the block
	 * @return 0.0F if the block is indestructible, else returns a default value of the <code>BlockState#getDestroySpeed</code> / 30 or 100, depending on whether or not the block can be destroyed
	 */
	static float getDestroyProgress(BlockState state, LivingEntity entity, IBlockReader world, BlockPos pos) { 
		float f = state.getDestroySpeed(world, pos); 
		if (f == -1.0F) {   
			return 0.0F;	   
		} else {
			int i = state.canEntityDestroy(world, pos, entity) ? 30 : 100;	    
			return f / (float)i;	    
		}	 
	}
	

	///////////////////////////////
	//          Strings          //
	///////////////////////////////
	
	

	///////////////////////////////
	//            Ints           //
	///////////////////////////////

	/**
	 * Sets the duplication tree duplicator cap
	 * 
	 * @return amount of blocks duplication tree can duplicate
	 */
	static int setDuplicationCap() {
		return 36;
	}

	/**
	 * Gets the cap specified in the method <code>setDuplicationCap()</code>
	 * 
	 * @return the duplication cap that was specified in
	 *         <code>setDuplicationCap()</code>
	 */
	static int getDuplicationCap() {
		return setDuplicationCap();
	}

	/**
	 * Sets the duplication tree update rate/speed in duplicating blocks
	 * 
	 * @return amount of ticks duplication tree undergoes before duplicating another
	 *         block
	 */
	static int setDuplicationSpeedInTicks() {
		return 2000;
	}

	/**
	 * Gets the duplication tree update rate/speed in duplicating blocks specified
	 * in <code>setDuplicationSpeedAKATickRate()</code>
	 * 
	 * @return amount of ticks duplication tree undergoes before duplicating another
	 *         block specified in <code>setDuplicationSpeedAKATickRate()</code>
	 */
	static int getDuplicationSpeedAKATickRate() {
		return setDuplicationSpeedInTicks();
	}
	
	/**
	 * Converts 1 experience orb to its equivalent durability value.
	 * @param xp xp value
	 * @return xp to durability (xp * 2)
	 */
	static int convertXPToDurability(int xp) {
		return xp * 2;
	}
	
	/**
	 * Converts durability to its equivalent xp value.
	 * @param durability durability
	 * @return durability to xp (durability / 2)
	 */
	static int convertDurabilityToXP(int durability) {
		return durability / 2;
	}
	
	/**
	 * Generates a random double in between the specified ints and rounds it down
	 * @param origin minimum randomly generated number
	 * @param bound maximum randomly generated number
	 * @return a random number between the origin and bound specified
	 */
	static int randomBetween(int origin, int bound) {
		Random rand = new Random();
		return (int) (origin + Math.floor(rand.nextDouble() * (1 * bound - origin)));
	}

	///////////////////////////////
	//           Lists           //
	///////////////////////////////

	/**
	 * Adds blocks to a queue list to be grabbed for duplication
	 * 
	 * @param blockToAdd block to add to duplication queue
	 * @return the list <code>Queue</code>
	 */
	static List<Block> addBlockToDuplicationQueue(Block blockToAdd) {
		if (!blockToAdd.is(CATags.Blocks.BLACKLIST) && blockToAdd != null && !(Queue.contains(blockToAdd))) {
			Queue.add(blockToAdd);
		}
		return Queue;
	}
	
	/**
	 * Creates a list of matching blocks' positions and stores those positions in that list
	 * @param world server world to verify conditions in
	 * @param pos position to start the check
	 * @param blockToCheck the block in that position
	 * @param blockToMatch the block to match it with (use a registry entry)
	 * @param distance distance to check
	 * @param directionToCheckIn direction in which the check will loop until it is broken
	 * @return a list containing 
	 */
	static List<BlockPos> checkBlockConnectionsAtACertainDistance(ServerWorld world, BlockPos pos, Block blockToCheck, Block blockToMatch, int distance, Direction directionToCheckIn) {
		List<BlockPos> posList = Lists.newArrayList();
		for (int d = 0; d < distance; d++) {
			for (BlockPos p : posList) {
				pos = p;
				blockToCheck = world.getBlockState(p).getBlock();
				blockToMatch = world.getBlockState(p.relative(directionToCheckIn)).getBlock();
				if (blockToCheck == blockToMatch) {
					p = p.relative(directionToCheckIn);
					posList.add(p);
				}
				if (d < distance && blockToCheck != blockToMatch) break;
			}
		}
		return posList;
	}
	
	/**
	 * Gets entities inside a certain bounding box, the base being around a specified user entity, and allows for specifying 
	 * a certain radius to validate entities inside the bounding box as targets
	 * @param user user entity, base from which the bounding box will grow
	 * @param entityClass the class of the entity the user should check for
	 * @param dX the distance on the x axis in which the bounding box will grow
	 * @param dY the distance on the y axis in which the bounding box will grow
	 * @param dZ the distance on the z axis in which the bounding box will grow
	 * @param radius the radius in which to validate entities that are already inside the bounding box
	 * @return a list of entities within the valid specified distance inside the grown bounding box
	 */
	static <T extends Entity> List<T> getEntitiesAround(LivingEntity user, Class<T> entityClass, double dX, double dY, double dZ, double radius) {
		Predicate<Entity> distPredicate = living -> living != user && (user.getTeam() != null && living.getTeam() != null ? !living.getTeam().equals(user.getTeam()) : living.isAlive()) && living.getClass() != user.getClass() && user.distanceTo(living) <= radius + living.getBbWidth() / 2F;
		return user.level.getEntitiesOfClass(entityClass, user.getBoundingBox().inflate(dX, dY, dZ), distPredicate);
	}
	
	/**
	 * Gets entities inside a certain bounding box, the base being around a specified user entity, and allows for specifying 
	 * a certain radius to validate entities inside the bounding box as targets. This applies to all entities, as opposed to the other method,
	 * which only applies to attackable entities
	 * @param user user entity, base from which the bounding box will grow
	 * @param entityClass the class of the entity the user should check for
	 * @param dX the distance on the x axis in which the bounding box will grow
	 * @param dY the distance on the y axis in which the bounding box will grow
	 * @param dZ the distance on the z axis in which the bounding box will grow
	 * @param radius the radius in which to validate entities that are already inside the bounding box
	 * @return a list of entities within the valid specified distance inside the grown bounding box
	 */
	static <T extends Entity> List<T> getEntitiesAroundNoPredicate(LivingEntity user, Class<T> entityClass, double dX, double dY, double dZ, double radius) {
		return user.level.getEntitiesOfClass(entityClass, user.getBoundingBox().inflate(dX, dY, dZ));
	}
	
	/**
	 * Gets all types of living entities around a specified user entity
	 * @param user base entity that will check for the other entities
	 * @param dX the distance on the x axis in which the bounding box will grow
	 * @param dY the distance on the y axis in which the bounding box will grow
	 * @param dZ the distance on the z axis in which the bounding box will grow
	 * @param radius the radius in which to validate entities that are already inside the bounding box
	 * @return a list of all living entities within the valid specified distance inside the grown bounding box
	 */
	@Nullable
	static List<LivingEntity> getAllEntitiesAround(LivingEntity user, double dX, double dY, double dZ, double radius) {
		return getEntitiesAround(user, LivingEntity.class, dX, dY, dZ, radius);
	}
	
	/**
	 * Gets all players around an entity
	 * @param user base entity, from which the check will begin
	 * @param dX the distance on the x axis in which the bounding box will grow
	 * @param dY the distance on the y axis in which the bounding box will grow
	 * @param dZ the distance on the z axis in which the bounding box will grow
	 * @param radius radius the radius in which to validate entities that are already inside the bounding box
	 * @return a list of all players within the valid specified distance inside the grown bounding box
	 */
    static List<PlayerEntity> getAllPlayersAround(LivingEntity user, double dX, double dY, double dZ, double radius) {
        List<Entity> nearbyEntities = user.level.getEntities(user, user.getBoundingBox().inflate(dX, dY, dZ));
        List<PlayerEntity> listEntityPlayers = nearbyEntities.stream().filter(neighbouringEntity -> neighbouringEntity instanceof PlayerEntity && user.distanceTo(neighbouringEntity) <= radius + neighbouringEntity.getBbWidth() / 2f).map(entityNeighbor -> (PlayerEntity) entityNeighbor).collect(Collectors.toList());
        return listEntityPlayers;
    }
	
	///////////////////////////////
	//     Other Method Types    //
	///////////////////////////////

	/**
	 * Reads NBT data. To put it simply, this just reads data input from the
	 * <code>writeToNBT</code> method
	 * 
	 * @param nbtToRead nbt to read data of
	 * @return NBT data read
	 */
	static CompoundNBT readFromNBT(CompoundNBT nbtToRead) {
		return nbtToRead;
	}

	/**
	 * Writes NBT data to a (usually) JSON file
	 * 
	 * @return Written NBT data
	 */
	static CompoundNBT writeToNBT() {
		return new CompoundNBT();
	}

	/**
	 * Gets/Writes NBT data from/to a living entity
	 * 
	 * @param entityToGetNBTDataFrom entity to get NBT data from
	 * @return the entity data/NBT
	 */
	static CompoundNBT getNBTDataFromLivingEntity(LivingEntity entityToGetNBTDataFrom) {
		return new CompoundNBT();
	}
	
	/**
	 * Gets a 6 block star-shaped area around a specified position
	 * @param pos position to check sqyare shaped area around
	 * @return positions of all 8 blocks surrounding the specified position
	 */
	static BlockPos[] allAround(BlockPos pos) {
		BlockPos[] posArray = new BlockPos[]{pos.above(), pos.below(), pos.east(), pos.west(), northWestOf(pos), northEastOf(pos), southWestOf(pos), southEastOf(pos)};
		return posArray;
	}
	
	/**
	 * Gets the block position northwest of the position specified. This northwest position is one block above diagonally at a horizontal axis
	 * @param pos position to get northwest position of
	 * @return Block position northwest to specified position in the parameters diagonally by one block.
	 */
	static BlockPos northWestOf(BlockPos pos) {
		return pos.north().west();
	}
	
	/**
	 * Gets the block position northeast of the position specified. This northeast position is one block above diagonally at a horizontal axis
	 * @param pos position to get northeast position of
	 * @return Block position northeast to specified position in the parameters diagonally by one block.
	 */
	static BlockPos northEastOf(BlockPos pos) {
		return pos.north().east();
	}
	
	/**
	 * Gets the block position southwest of the position specified. This southwest position is one block above diagonally at a horizontal axis
	 * @param pos position to get southwest position of
	 * @return Block position southwest to specified position in the parameters diagonally by one block.
	 */
	static BlockPos southWestOf(BlockPos pos) {
		return pos.south().west();
	}
	
	/**
	 * Gets the block position southeast of the position specified. This southeast position is one block above diagonally at a horizontal axis
	 * @param pos position to get southeast position of
	 * @return Block position southeast to specified position in the parameters diagonally by one block.
	 */
	static BlockPos southEastOf(BlockPos pos) {
		return pos.south().east();
	}
	
	/**
	 * Gets a position by a specified distance somewhere down a diagonal line, formed northwest of the specified position at a horizontal axis
	 * @param pos specified position
	 * @param distance distance down the diagonal line
	 * @return specific position northwest of the specified position in the parameters
	 */
	static BlockPos diagonalPositionCheckNorthWest(BlockPos pos, int distance) {
		if (distance <= 1) return northWestOf(pos);
		
		for (int d = 0; d < distance; d++) {
			pos = northWestOf(pos);
		}
		
		return pos;
	}
	
	/**
	 * Gets a position by a specified distance somewhere down a diagonal line, formed northeast of the specified position at a horizontal axis
	 * @param pos specified position
	 * @param distance distance down the diagonal line
	 * @return specific position northeast of the specified position in the parameters
	 */
	static BlockPos diagonalPositionCheckNorthEast(BlockPos pos, int distance) {
		if (distance <= 1) return northEastOf(pos);
		
		for (int d = 0; d < distance; d++) {
			pos = northEastOf(pos);
		}
		
		return pos;
	}
	
	/**
	 * Gets a position by a specified distance somewhere down a diagonal line, formed southwest of the specified position at a horizontal axis
	 * @param pos specified position
	 * @param distance distance down the diagonal line
	 * @return specific position southwest of the specified position in the parameters
	 */
	static BlockPos diagonalPositionCheckSouthWest(BlockPos pos, int distance) {
		if (distance <= 1) return southWestOf(pos);
		
		for (int d = 0; d < distance; d++) {
			pos = southWestOf(pos);
		}
		
		return pos;
	}
	
	/**
	 * Gets a position by a specified distance somewhere down a diagonal line, formed southeast of the specified position at a horizontal axis
	 * @param pos specified position
	 * @param distance distance down the diagonal line
	 * @return specific position southeast of the specified position in the parameters
	 */
	static BlockPos diagonalPositionCheckSouthEast(BlockPos pos, int distance) {
		if (distance <= 1) return southEastOf(pos);
		
		for (int d = 0; d < distance; d++) {
			pos = southEastOf(pos);
		}
		
		return pos;
	}
	
	/**
	 * Creates a 3 x 3 x 3 bounding box for testing conditions within its area
	 * @param center center block to create AxisAlignedBB at
	 * @return a new 3 x 3 x 3 AxisAlignedBB
	 */
	static AxisAlignedBB create3x3x3Box(BlockPos center) {
		AxisAlignedBB centerBB = new AxisAlignedBB(center);
		BlockPos northPos = center.north();
		BlockPos southPos = center.south();
		BlockPos eastPos = center.east();
		BlockPos westPos = center.west();
		BlockPos abovePos = center.above();
		BlockPos belowPos = center.below();
		BlockPos northEastPos = northEastOf(center);
		BlockPos northWestPos = northWestOf(center);
		BlockPos southEastPos = southEastOf(center);
		BlockPos southWestPos = southWestOf(center);
		
		centerBB.expandTowards(northPos.getX(), northPos.getY(), northPos.getZ());
		centerBB.expandTowards(southPos.getX(), southPos.getY(), southPos.getZ());
		centerBB.expandTowards(eastPos.getX(), eastPos.getY(), eastPos.getZ());
		centerBB.expandTowards(westPos.getX(), westPos.getY(), westPos.getZ());
		centerBB.expandTowards(northEastPos.getX(), northEastPos.getY(), northEastPos.getZ());
		centerBB.expandTowards(northWestPos.getX(), northWestPos.getY(), northWestPos.getZ());
		centerBB.expandTowards(southEastPos.getX(), southEastPos.getY(), southEastPos.getZ());
		centerBB.expandTowards(southWestPos.getX(), southWestPos.getY(), southWestPos.getZ());
		centerBB.expandTowards(abovePos.getX(), abovePos.getY(), abovePos.getZ());
		centerBB.expandTowards(belowPos.getX(), belowPos.getY(), belowPos.getZ());
		
		return centerBB;
	}
	
	/**
	 * RayTrace from a specified entity to a target entity
	 * @param entityRayTracing entity looking at target
	 * @param entityToTrace target entity
	 * @return a RayTraceResult if entityRayTracing is looking at a Nonnull entityToTrace, else returns null
	 */
	@Nullable
	static RayTraceResult rayTraceEntityToEntity(LivingEntity entityRayTracing, LivingEntity entityToTrace) {
		Vector3d start = entityRayTracing.getEyePosition(1.0F);
		Vector3d startView = entityRayTracing.getViewVector(1.0F);
		Vector3d target = start.add(entityRayTracing.getLookAngle().scale(entityRayTracing.distanceToSqr(entityToTrace)));
		
		AxisAlignedBB targetBB = entityRayTracing.getBoundingBox().expandTowards(startView.scale(entityRayTracing.distanceToSqr(entityToTrace))).inflate(4.0D, 4.0D, 4.0D);
		EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(entityRayTracing.level, entityRayTracing, start, target, targetBB, EntityPredicates.NO_CREATIVE_OR_SPECTATOR);
		
		if (result == null || !(result.getEntity() instanceof LivingEntity)) return null;
		if (result != null && result.getEntity() instanceof LivingEntity) entityToTrace = (LivingEntity) result.getEntity();
		
		boolean resultBool = (result != null ? target : null) != null;
		
		return resultBool ? entityRayTracing.level.clip(new RayTraceContext(start, target, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, entityToTrace)) : null;
	}

}
