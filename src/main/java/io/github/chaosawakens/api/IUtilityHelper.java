package io.github.chaosawakens.api;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.FilledCritterCageItem;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.FogRenderer.FogType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

public interface IUtilityHelper {
	
	///////////////////////////////
	//         Variables         //
	///////////////////////////////
	
	List<Block> Queue = new ArrayList<>();
	List<EntityType<? extends LivingEntity>> CHAOS_AWAKENS_ENTITIES = new ArrayList<>();
	List<EntityType<? extends LivingEntity>> VANILLA_ENTITIES = new ArrayList<>();
	
	///////////////////////////////
	//         Functions         //
	///////////////////////////////
	
	/**
	 * Set the reach distance of a player to a new value
	 * @param player player to apply modified reach to
	 * @param newReachValue the new reach value applied to the attribute the player has, it doesn't add to the original attribute value, but sets it to a new value
	 */
	default void setReach(PlayerEntity player, int newReachValue) {
		 player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(newReachValue);
	}
	
	/**
	 * Sets the reach distance of a player to a new value, but only in the condition that the player is holding the stack defined
	 * @param player player to apply modified reach to
	 * @param newReachValue the new reach value applied to the attribute the player has, it doesn't add to the original attribute value, but sets it to a new value
	 * @param stack the item/itemstack needed (to be held in the player's main hand) to activate the attribute modifier
	 */
	default void setReach(PlayerEntity player, int newReachValue, ItemStack stack) {
		if (player.getItemInHand(Hand.MAIN_HAND) == stack) {
			player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(newReachValue);
		}
	}
	
	/**
	 * Get the reach distance of the player
	 * @param player player to check reach distance attribute value of
	 */
	default void getPlayerReach(PlayerEntity player) {
	     player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getBaseValue();
	}
	
	/**
	 * Set the swim speed of a player to a new value
	 * @param player player to apply modified swim speed to
	 * @param newSwimSpeedValue the new swim speed value applied to the attribute the player has, it doesn't add to the original attribute value, but sets it to a new value
	 */
	default void setSwimSpeed(PlayerEntity player, int newSwimSpeedValue) {
		player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(newSwimSpeedValue);
	}
	
	/**
	 * Sets the swim speed of a player to a new value, but only in the condition that the player is holding the stack defined
	 * @param player player to apply modified reach to
	 * @param newSwimSpeedValue the new swim speed value applied to the attribute the player has, it doesn't add to the original attribute value, but sets it to a new value
	 * @param stack the item/itemstack needed (to be held in the player's main hand) to activate the attribute modifier
	 */
	default void setSwimSpeed(PlayerEntity player, int newSwimSpeedValue, ItemStack stack) {
		if (player.getItemInHand(Hand.MAIN_HAND) == stack) {
			player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(newSwimSpeedValue);
		}
	}
	
	/**
	 * Get the swim speed of the player
	 * @param player player to check swim speed attribute value of
	 */
	default void getPlayerSwimSpeed(PlayerEntity player) {
	     player.getAttribute(ForgeMod.SWIM_SPEED.get()).getBaseValue();
	}
	
	/**
	 * Sets the gravity of a player to a new value
	 * @param player player to apply modified gravity to
	 * @param newPlayerGravityValue the new gravity value appled to the attribute the player has, it doesn't add to the original value, but sets it to a new value
	 */
	default void setPlayerGravity(PlayerEntity player, int newPlayerGravityValue) {
		player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(newPlayerGravityValue);
	}
	
	/**
	 * Sets the gravity of a player to a new value, but only in the condition that the player is holding the stack defined
	 * @param player player to apply modified gravity to
	 * @param newPlayerGravityValue the new gravity value appled to the attribute the player has, it doesn't add to the original value, but sets it to a new value
	 * @param stack the item/itemstack needed (to be held in the player's main hand) to activate the attribute modifier
	 */
	default void setPlayerGravity(PlayerEntity player, int newPlayerGravityValue, ItemStack stack) {
		if (player.getItemInHand(Hand.MAIN_HAND) == stack) {
			player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(newPlayerGravityValue);
		}
	}
	
	/**
	 * Get the gravity of the player
	 * @param player player to check gravity attribute value of
	 */
	default void getPlayerGravity(PlayerEntity player) {
	     player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).getBaseValue();
	}
	
	/**
	 * Summon particles into the world
	 * @param world world to summon particles in
	 * @param particleData particle data/particle type, just grab whatever particle type you want from the <code>ParticleTypes</code> class
	 * @param x x position to add particles to
	 * @param y y position to add particles to
	 * @param z z position to add particles to
	 * @param randomXValue random value within the x position to add particles
	 * @param randomYValue random value within the y position to add particles
	 * @param randomZValue random value within the z position to add particles
	 * @param amount amount of particles to add
	 */
	@SuppressWarnings("resource")
	default void addParticles(World world, IParticleData particleData, double x, double y, double z, double randomXValue, double randomYValue, double randomZValue, int amount) {
		if (world.isClientSide) {
		  for (int particleCount = 0; particleCount <= amount; particleCount++) {
			  Minecraft.getInstance().particleEngine.createParticle(particleData, 
					  x + getRandomHorizontalPos(randomXValue, world),
					  y + getRandomVerticalPos(randomYValue, world),
					  z + getRandomHorizontalPos(randomZValue, world),
					  0.0D,
					  0.0D, 
					  0.0D);
		    }
	    }
	}
	
	/**
	 * Add a certain number of living entities within a bounding box in a certain position
	 * @param world world to add entities in
	 * @param entitiesToAdd the list<LivingEntity> of entities to add
	 * @param pos position to add entities in. CANNOT BE NULL!
	 * @param x x position to add entities
	 * @param y y position to add entities
	 * @param z z position to add entities
	 * @param xSize the x size of the bounding box
	 * @param ySize the y size of the bounding box
	 * @param zSize the z size of the bounding box
	 * @param amountToSpawn amount of entities inside the entitiesToAdd list, that amount will be summoned 
	 */
	default void addLivingEntities(World world, List<LivingEntity> entitiesToAdd, @Nonnull BlockPos pos, double x, double y, double z, double xSize, double ySize, double zSize, int amountToSpawn) {
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
	 * Summon a singular entity in a certain position (checks if the world isn't client side first by default)
	 * @param world world to summon entity in
	 * @param entityToSummon entity to be summoned
	 * @param pos position to summon entity in
	 */
	default void summonLivingEntity(World world, LivingEntity entityToSummon, BlockPos pos) {
		if (!world.isClientSide) {
			world.addFreshEntity(entityToSummon);
		}
	}
	
	/**
	 * Adds fog after checking if the world is client side, to save you a little hassle
	 * @param world world to add fog in
	 * @param density fog density
	 * @param b boolean
	 * @param pTicks particle ticks
	 * @param type fog type
	 * @param info active render information
	 */
	default void addFog(World world, float density, boolean b, float pTicks, FogType type, ActiveRenderInfo info) {
		if (world.isClientSide) {
			FogRenderer.setupFog(info, type, density, false, pTicks);
		}
	}
	
	/**
	 * Adds the block specified to the <code>Blacklist<code> tag
	 * @param blockToAdd block to add blacklist tag to
	 */
	default void addBlockToDuplicationsBlackList(Block blockToAdd) {
		blockToAdd.getTags().add((ResourceLocation) CATags.Blocks.tag("blacklist"));
	}
	
	/**
	 * Adds the block specified to the <code>Whitelist<code> tag
	 * @param blockToAdd block to add whitelist tag to
	 */
	default void addBlockToDuplicationsWhiteList(Block blockToAdd) {
		blockToAdd.getTags().add((ResourceLocation) CATags.Blocks.tag("whitelist"));
	}
	
	/**
	 * uh -WeirdNerd, circa 2022 A.D
	 */
	default void uh() {
		uh();
	}
	
	///////////////////////////////
	//          Booleans         //
	///////////////////////////////
	
	/**
	 * Gets an entity moving in any direction
	 * @param entity entity to check
	 * @return true if entity is moving, else returns false
	 */
	default boolean isMoving(LivingEntity entity) {
		if (entity.moveDist > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check the speed of an entity
	 * @param speed speed entity is moving at
	 * @param entityToCheck entity to check moving speed of
	 * @return true if the speed of the entity is equal to the speed parameter, else returns false
	 */
	default boolean isMovingAtVelocity(float speed, LivingEntity entityToCheck) {
		return entityToCheck.getSpeed() == speed;
	}

	/**
	 * Checks the uuid of an entity or player
	 * @param entityToCheck entity to check uuid of
	 * @param uuidToCheck uuid of entity to check
	 * @return true if entity's uuid is equal to uuidToCheck, else returns false
	 */
	static boolean isUserOrEntityUUIDEqualTo(Entity entityToCheck, UUID uuidToCheck) {
		return entityToCheck.getUUID().equals(uuidToCheck);
	}

	/**
	 * Gets the name of an entity or player
	 * @param nameToCheck name of entity to check
	 * @param entityToCheck entity to check name of
	 * @return true if entity name is equal to the name provided, else returns false
	 */
	static boolean isEntityNameEqualTo(Entity entityToCheck, String nameToCheck) {
		return entityToCheck.getName().getString().equals(nameToCheck);
	}
	
	/**
	 * Check if a block can be duplicated by the duplication tree
	 * @param blockToCheck block to check for duplicatability (that's a word now)
	 * @return true if the block to check is duplicatable, else returns false
	 */
	default boolean isDuplicatable(Block blockToCheck) {
		return blockToCheck.is(CATags.Blocks.WHITELIST) || !blockToCheck.is(CATags.Blocks.BLACKLIST) || !blockToCheck.is(CATags.Blocks.BLACKLIST) && !blockToCheck.is(CATags.Blocks.WHITELIST);
	}
	
	/**
	 * Checks if an entity is moving on the y axis
	 * @param entity entity to check
	 * @return true if entity is moving vertically, else returns false
	 */
	default boolean isMovingVertically(LivingEntity entity) {
		return false;
	}
	
	/**
	 * Gets all of an entity's active effects if it has any
	 * @param entity entity to check for effects
	 * @return true if any effects are present, else returns false
	 */
	default boolean hasActiveEffects(LivingEntity entity) {
		return entity.getActiveEffects() != null;
	}
	
	/**
	 * Gets an entity's bounding box, if it has any
	 * @param entity entity to check for bounding box
	 * @return true if entity has a bounding box, else returns false
	 */
	default boolean hasBoundingBox(LivingEntity entity) {
		return entity.getBoundingBox() != null;
	}
	
	/**
	 * Checks if an item specified is in the player's inventory
	 * @param stackToCheck stack to check in inventory
	 * @return true if the player has the specified item in their inventory, else returns false
	 */
	default boolean hasItemInInventory(PlayerEntity player, ItemStack stackToCheck) {	
		return player.inventory.items.contains(stackToCheck);
	}
	
	//WIP
/*	default boolean hasItemInInventoryByAmount(PlayerEntity player, ItemStack stackToCheck, int amount) {
		return NonNullList.withSize(amount, stackToCheck) != null;
	}*/
	
	/**
	 * Checks whether or not an entity is part of the <code>CHAOS_AWAKENS_ENTITIES</code> list
	 * @param mobToCheck mob to check for availability in the CA Entity list
	 * @return true if mob is present in the CA Entity list, else returns false
	 */
	default boolean isInCritterCageCAEntityList(EntityType<?> mobToCheck) {
		return CHAOS_AWAKENS_ENTITIES.contains(mobToCheck);
	}
	
	/**
	 * Checks if an entity matches another entity stored inside the itemstack (which will most likely be a critter cage)
	 * @param entity entity to check name of
	 * @param cageStack itemstack to grab data from
	 * @return true if entity name matches the one stored in the itemstack, else returns false
	 */
	default boolean matchesNameOfEntityStoredInItemStack(Entity entity, ItemStack cageStack) {
		boolean match = entity.getType().getRegistryName().toString().matches(cageStack.getTag().getString(entity.getType().getRegistryName().toString())) && entity != null && cageStack != null;
		return match;
	}
	
	/**
	 * Checks whether or not an entity is part of the <code>VANILLA_ENTITIES</code> list
	 * @param mobToCheck mob to check for availability in the Vanilla Entity list
	 * @return true if mob is present in the Vanilla Entity list, else returns false
	 */
	default boolean isInCritterCageVanillaEntityList(EntityType<?> mobToCheck) {
		return VANILLA_ENTITIES.contains(mobToCheck);
	}
	
	/**
	 * Checks if a block is part of the duplication queue list, can be used to arrange duplication patterns if there's multiple pattern lists
	 * @param blockToCheck block to check for queue validity 
	 * @return true if the block is inside the <code>Queue</code> list, else returns false
	 */
	default boolean isInQueueForDuplication(Block blockToCheck) {
		return Queue.contains(blockToCheck);
	}
	
	/**
	 * Checks if a certain thing is part of chaos awakens
	 * @param name resource location/registry to check
	 * @return true if it's part of chaos awakens, else returns false
	 */
	default boolean isChaosAwakens(ResourceLocation name) {
		return name.getNamespace().equalsIgnoreCase(ChaosAwakens.MODID);
	}
	
	/**
	 * Checks if an itemstack has an entity/entity data stored inside it
	 * @param stackToCheck stack to check for entities
	 * @return true if the itemstack has an entity stored in it, else returns false
	 */
	default boolean stackHasMob(ItemStack stackToCheck) {
		return !(stackToCheck.isEmpty()) && stackToCheck.hasTag() && stackToCheck.getTag().contains("entity");
	}
	
	/**
	 * Checks if something is from a mod, specified by the modid
	 * @param loc resource location/registry to check
	 * @param modid the modid for the stuff you wanna get, from the loc
	 * @return true if stuff is from specified modid, else returns false
	 */
	default boolean isFromMod(ResourceLocation loc, String modid) {
		return loc.getNamespace().equalsIgnoreCase(modid);
	}
	
	/**
	 * Checks if an entity in air
	 * @param entity entity to check
	 * @return true if entity is standing on air, else returns false
	 */
	default boolean isInAir(Entity entity) {
		return entity.blockPosition().below() == null;
	}
	
	/**
	 * Checks if an entity is completely surrounded by air from all cardinal directions
	 * @param entity entity to check
	 * @return true if entity is completely surrounded by air from all sides/cardinal directions, else returns false
	 */
	default boolean isSurroundedByAir(Entity entity) {
		return entity.blockPosition().below() == null && entity.blockPosition().above() == null && entity.blockPosition().east() == null && entity.blockPosition().west() == null && entity.blockPosition().north() == null && entity.blockPosition().south() == null;
	}
	
	///////////////////////////////
	//          Doubles          //
	///////////////////////////////
	
	/**
	 * Gets the distance between 2 positions on the x and z axis
	 * @param a starting blockpos
	 * @param b finishing blockpos
	 * @return the distance between a and b
	 */
	default double getHorizontalDistanceBetween(BlockPos a, BlockPos b) {
		int x = Math.abs(a.getX() - b.getX());
		int z= Math.abs(a.getZ() - b.getZ());
		
		return Math.sqrt(x * x + z * z);
	}
	
	/**
	 * Gets the distance between 2 positions on the y axis
	 * @param a starting blockpos
	 * @param b finishing blockpos
	 * @return the distance between a and b
	 */
	default double getVerticalDistanceBetween(BlockPos a, BlockPos b) {
		int y = Math.abs(a.getY() - b.getY());
		
		return Math.sqrt(y * y);
	}
	
	/**
	 * Gets the distance between 2 positions on all axis
	 * @param a starting blockpos
	 * @param b finishing blockpos
	 * @return the distance between a and b
	 */
	default double getDistanceBetween(BlockPos a, BlockPos b) {
		int x = Math.abs(a.getX() - b.getX());
		int y = Math.abs(a.getY() - b.getY());
		int z = Math.abs(a.getZ() - b.getZ());
		
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Gets the horizontal distance (distance on the x and z axis) between an entity and a block position
	 * @param entity point a, where entity is
	 * @param pos point b, where the destination pos is
	 * @return the distance (on the x and z axis) between the entity and the destination pos
	 */
	default double getHorizontalDistanceBetweenEntityAndBlockPos(Entity entity, BlockPos pos) {
		int x = Math.abs((int) entity.getX() - pos.getX());
		int z = Math.abs((int) entity.getZ() - pos.getZ());
		
		return Math.sqrt(x * x + z * z);
	}
	
	/**
	 * Gets the vertical distance (distance on the y axis) between an entity and a block position
	 * @param entity point a, where entity is
	 * @param pos point b, where the destination pos is
	 * @return the distance (on the y axis) between the entity and the destination pos
	 */
	default double getVerticalDistanceBetweenEntityAndBlockPos(Entity entity, BlockPos pos) {
		int y = Math.abs((int) entity.getY() - pos.getY());
		
		return Math.sqrt(y * y);
	}
	
	/**
	 * Gets distance between entity and a certain point
	 * @param entity point a, where entity is
	 * @param pos point b, where the destination pos is
	 * @return the distance between the entity and the destination pos
	 */
	default double getDistanceBetweenEntityAndBlockPos(Entity entity, BlockPos pos) {
		int x = Math.abs((int) entity.getX() - pos.getX());
		int y = Math.abs((int) entity.getY() - pos.getY());
		int z = Math.abs((int) entity.getZ() - pos.getZ());
		
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Gets the horizontal distance (on the x and z axis) between 2 entities
	 * @param entityA entity point a, starting pos to check
	 * @param entityB entity point b, finishing pos to check
	 * @return the distance (on the x and z axis) between the 2 entities
	 */
	default double getHorizontalDistanceBetweenEntities(Entity entityA, Entity entityB) {
		double x = Math.abs(entityA.getX() - entityB.getX());
		double z = Math.abs(entityA.getZ() - entityB.getZ());
		
		return Math.sqrt(x * x + z * z);
	}
	
	/**
	 * Gets the vertical distance (on the y axis) between 2 entities
	 * @param entityA entity point a, starting pos to check
	 * @param entityB entity point b, finishing pos to check
	 * @return the distance (on the y axis) between the 2 entities
	 */
	default double getVerticalDistanceBetweenEntities(Entity entityA, Entity entityB) {
		double y = Math.abs(entityA.getY() - entityB.getY());
		
		return Math.sqrt(y * y);
	}
	
	/**
	 * Gets distance between 2 entities
	 * @param entityA entity point a, starting pos to check
	 * @param entityB entity point b, finishing pos to check
	 * @return the distance between the 2 entities
	 */
	default double getDistanceBetweenEntities(Entity entityA, Entity entityB) {
		double x = Math.abs(entityA.getX() - entityB.getX());
		double y = Math.abs(entityA.getY() - entityB.getY());
		double z = Math.abs(entityA.getZ() - entityB.getZ());
		
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Gets a random position horizontally.
	 * This is mainly used for particle summoning, but can be used for other purposes
	 * (Thanks to cyclic for this piece of code)
	 * @param random random value 
	 * @param world world to get pos in
	 * @return random position to execute an event in
	 */
	default double getRandomHorizontalPos(double random, World world) {
		return (world.random.nextDouble() - 0.5D) * random;
	}
	
	/**
	 * Gets a random position vertically.
	 * This is mainly used for particle summoning, but can be used for other purposes
	 * (Thanks to cyclic for this piece of code)
	 * @param random random value 
	 * @param world world to get pos in
	 * @return random position to execute an event in
	 */
	default double getRandomVerticalPos(double random, World world) {
		return world.random.nextDouble() - 0.1D * random;
	}
	
	///////////////////////////////
	//            Ints           //
	///////////////////////////////
	
	/**
	 * Sets the duplication tree duplicator cap
	 * @return amount of blocks duplication tree can duplicate
	 */
	default int setDuplicationCap() {
		return 36;
	}
	
	/**
	 * Gets the cap specified in the method <code>setDuplicationCap()</code>
	 * @return the duplication cap that was specified in <code>setDuplicationCap()</code>
	 */
	default int getDuplicationCap() {
		return setDuplicationCap();
	}
	
	/**
	 * Sets the duplication tree update rate/speed in duplicating blocks
	 * @return amount of ticks duplication tree undergoes before duplicating another block
	 */
	default int setDuplicationSpeedAKATickRate() {
		return 2000;
	}
	
	/**
	 * Gets the duplication tree update rate/speed in duplicating blocks specified in <code>setDuplicationSpeedAKATickRate()</code>
	 * @return amount of ticks duplication tree undergoes before duplicating another block specified in <code>setDuplicationSpeedAKATickRate()</code>
	 */
	default int getDuplicationSpeedAKATickRate() {
		return setDuplicationSpeedAKATickRate();
	}
	
	///////////////////////////////
	//           Lists           //
	///////////////////////////////
	
	/**
	 * Adds blocks to a queue list to be grabbed for duplication
	 * @param blockToAdd block to add to duplication queue
	 * @return the list <code>Queue</code>
	 */
	default List<Block> addBlockToDuplicationQueue(Block blockToAdd) {
		if (!blockToAdd.is(CATags.Blocks.BLACKLIST) && blockToAdd != null && !(Queue.contains(blockToAdd))) {
			Queue.add(blockToAdd);
		}
		return Queue;
	}
	
	/**
	 * Adds CA mobs to the CA entities list, primarily used for differentiating between CA mobs and other mob types from vanilla MC or other mods
	 * @param CAMobToAdd Mob to add to the <code>CHAOS_AWAKENS_ENTITIES</code>
	 * @return the list <code>CHAOS_AWAKENS_ENTITIES</code>
	 */
	default List<EntityType<? extends LivingEntity>> addChaosEntityToCAEntityList(EntityType<? extends LivingEntity> CAMobToAdd) {
		if (!VANILLA_ENTITIES.contains(CAMobToAdd) && CAMobToAdd != null && !(CHAOS_AWAKENS_ENTITIES.contains(CAMobToAdd))) {
			CHAOS_AWAKENS_ENTITIES.add(CAMobToAdd);
		}
		return CHAOS_AWAKENS_ENTITIES;
	}
	
	/**
	 * Adds Vanilla mobs to the Vanilla entities list, primarily used for differentiating between Vanilla mobs and other mob types from CA or other mods
	 * @param VanillaMobToAdd Mob to add to the <code>VANILLA_ENTITIES</code>
	 * @return the list <code>VANILLA_ENTITIES</code>
	 */
	default List<EntityType<? extends LivingEntity>> addVanillaEntityToVanillaEntityList(EntityType<? extends LivingEntity> VanillaMobToAdd) {
		if (!CHAOS_AWAKENS_ENTITIES.contains(VanillaMobToAdd) && VanillaMobToAdd != null && !(VANILLA_ENTITIES.contains(VanillaMobToAdd))) {
			VANILLA_ENTITIES.add(VanillaMobToAdd);
		}
		return VANILLA_ENTITIES;
	}
	
	///////////////////////////////
	//     Other Method Types    //
	///////////////////////////////
	
	/**
	 * Reads NBT data. To put it simply, this just reads data input from the <code>writeToNBT</code> method
	 * @param nbtToRead nbt to read data of
	 * @return NBT data read
	 */
	default CompoundNBT readFromNBT(CompoundNBT nbtToRead) {
		return nbtToRead;
	}
	
	/**
	 * Writes NBT data to a (usually) JSON file 
	 * @return Written NBT data
	 */
	default CompoundNBT writeToNBT() {
		final CompoundNBT NBT = new CompoundNBT();
		return NBT;
	}
	
	/**
	 * Gets/Writes NBT data from/to a living entity
	 * @param entityToGetNBTDataFrom entity to get NBT data from
	 * @return the entity data/NBT
	 */
	default CompoundNBT getNBTDataFromLivingEntity(LivingEntity entityToGetNBTDataFrom) {
		CompoundNBT entityDataNBT = new CompoundNBT();
		return entityDataNBT;
	}
	
	/**
	 * Gets matching critter cage of mob
	 * @param entityToGet entity to get name of, will get the name excluding the modid (e.g. minecraft:pig will be read as pig)
	 * @param oldCageStack empty critter cage
	 * @param cageStack default critter cage (if mob doesn't have a texture for it by default)
	 * @param texturedCageStack textured critter cage (for when the mob has its own texture)
	 * @param loc texture location
	 * @return matching critter cage stack
	 */
	default ItemStack getMatching(Entity entityToGet, ItemStack oldCageStack, ItemStack cageStack, ItemStack texturedCageStack, ResourceLocation loc) {
		if (cageStack.getItem().getRegistryName().getPath().contains("critter_cage")) {
			String entityRegName = entityToGet.getType().getRegistryName().toString();
			String modid = loc.getNamespace();
			String f = entityRegName.replace(modid + ":", "");
			if (cageStack.getItem().getRegistryName().getPath().contains(f)) {
				texturedCageStack = new ItemStack(new FilledCritterCageItem(new Item.Properties(), entityToGet.getType()));
				return texturedCageStack;
			}
			return cageStack;
		}
		return oldCageStack;
	}
	
}
