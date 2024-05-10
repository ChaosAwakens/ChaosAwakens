package io.github.chaosawakens.common.entity.projectile.bobber;

import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CALootTables;
import io.github.chaosawakens.common.util.EnumUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.network.FMLPlayMessages;

import java.util.Collections;
import java.util.List;

public class UltimateFishingBobberEntity extends FishingBobberEntity {
	protected boolean openLava = true;
	protected int outOfLavaTime = 0;

	public UltimateFishingBobberEntity(PlayerEntity pPlayer, World pLevel, int pLuck, int pLureSpeed) {
		super(pPlayer, pLevel, pLuck, pLureSpeed);
	}

	public UltimateFishingBobberEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
		super(world.getPlayerByUUID(spawnEntity.getAdditionalData().readUUID()), world, 0, 0);
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double pDistance) {
		return pDistance < 8192.0D;
	}

	@Override
	protected void bringInHookedEntity() {
		Entity ownerEntity = getOwner();

		if (ownerEntity != null) {
			Vector3d vector3d = new Vector3d(ownerEntity.getX() - getX(), ownerEntity.getY() - getY(), ownerEntity.getZ() - getZ()).scale(0.2D);
			this.hookedIn.setDeltaMovement(this.hookedIn.getDeltaMovement().add(vector3d));
		}
	}

	@Override
	protected boolean shouldStopFishing(PlayerEntity ownerPlayer) {
		ItemStack mainHandStack = ownerPlayer.getMainHandItem();
		ItemStack offhandStack = ownerPlayer.getOffhandItem();
		boolean isMainHandEquipped = mainHandStack.getItem().equals(CAItems.ULTIMATE_FISHING_ROD.get());
		boolean isOffhandEquipped = offhandStack.getItem().equals(CAItems.ULTIMATE_FISHING_ROD.get());

		if (!ownerPlayer.removed && ownerPlayer.isAlive() && (isMainHandEquipped || isOffhandEquipped) && distanceToSqr(ownerPlayer) <= getMaxDistFromBobberSqrd()) return false;
		else {
			remove();
			return true;
		}
	}

	@Override
	public void tick() {
		BlockPos curPos = blockPosition();
		FluidState targetFluidState = level.getFluidState(curPos);

		if (targetFluidState.is(FluidTags.WATER)) super.tick();
		else if (targetFluidState.is(FluidTags.LAVA)) {
			syncronizedRandom.setSeed(getUUID().getLeastSignificantBits() ^ level.getGameTime());

			handleRemoval();
			handleBobberStateInLava();
		} else if (targetFluidState.isEmpty() && !shouldStopFishing(getPlayerOwner())) {
			setDeltaMovement(getDeltaMovement().add(0.0D, -0.03D, 0.0D));
		}
	}

	@Override
	public int retrieve(ItemStack fishingRodStack) {
		PlayerEntity playerOwner = getPlayerOwner();

		if (!this.level.isClientSide && playerOwner != null) {
			ServerPlayerEntity serverPlayerOwner = (ServerPlayerEntity) playerOwner;
			int rodDamage = 0;
			ItemFishedEvent itemFishedEventForgeHook = null;

			if (this.hookedIn != null) {
				bringInHookedEntity();

				CriteriaTriggers.FISHING_ROD_HOOKED.trigger(serverPlayerOwner, fishingRodStack, this, Collections.emptyList());
				this.level.broadcastEntityEvent(this, (byte) 31);

				rodDamage = this.hookedIn instanceof ItemEntity ? 3 : 5;
			} else if (this.nibble > 0) {
				LootContext.Builder fishingLootContext = new LootContext.Builder((ServerWorld) level).withParameter(LootParameters.ORIGIN, position()).withParameter(LootParameters.TOOL, fishingRodStack).withParameter(LootParameters.THIS_ENTITY, this).withRandom(random).withLuck(luck + playerOwner.getLuck());

				fishingLootContext.withParameter(LootParameters.KILLER_ENTITY, serverPlayerOwner).withParameter(LootParameters.THIS_ENTITY, this);

				LootTable curLoottable = level.getFluidState(blockPosition()).is(FluidTags.LAVA) ? this.level.getServer().getLootTables().get(CALootTables.FISHING_LAVA) : this.level.getServer().getLootTables().get(LootTables.FISHING);
				List<ItemStack> chosenLoot = curLoottable.getRandomItems(fishingLootContext.create(LootParameterSets.FISHING));

				itemFishedEventForgeHook = new ItemFishedEvent(chosenLoot, this.onGround ? 2 : 1, this);
				MinecraftForge.EVENT_BUS.post(itemFishedEventForgeHook);

				if (itemFishedEventForgeHook.isCanceled()) {
					remove();
					return itemFishedEventForgeHook.getRodDamage();
				}

				CriteriaTriggers.FISHING_ROD_HOOKED.trigger(serverPlayerOwner, fishingRodStack, this, chosenLoot);

				for (ItemStack curLootStack : chosenLoot) {
					ItemEntity holderItemEntity = new ItemEntity(level, getX(), getY(), getZ(), curLootStack);
					double xDist = playerOwner.getX() - getX();
					double yDist = playerOwner.getY() - getY();
					double zDist = playerOwner.getZ() - getZ();
					double euclideanDistance = Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist);

					holderItemEntity.setDeltaMovement(xDist * 0.1D, yDist * 0.1D + Math.sqrt(euclideanDistance) * 0.08D, zDist * 0.1D);

					this.level.addFreshEntity(holderItemEntity);
					playerOwner.level.addFreshEntity(new ExperienceOrbEntity(playerOwner.level, playerOwner.getX(), playerOwner.getY() + 0.5D, playerOwner.getZ() + 0.5D, this.random.nextInt(12) + 2));

					if (curLootStack.getItem().is(ItemTags.FISHES)) playerOwner.awardStat(Stats.FISH_CAUGHT, 1);
				}

				rodDamage = 1;
			}

			if (onGround) rodDamage = 2;

			remove();
			return itemFishedEventForgeHook == null ? rodDamage : itemFishedEventForgeHook.getRodDamage();
		} else return 0;
	}

	protected void handleRemoval() {
		PlayerEntity ownerPlayer = getPlayerOwner();

		if (ownerPlayer == null) remove();
		else if ((level.isClientSide || !shouldStopFishing(ownerPlayer)) && onGround && life++ >= getGroundLifetime()) remove();
		else life = 0;
	}

	protected void handleBobberStateInLava() {
		float fluidHeight = 0.0F;
		BlockPos curPos = blockPosition();
		FluidState targetFluidState = this.level.getFluidState(curPos);

		if (targetFluidState.is(FluidTags.LAVA)) fluidHeight = targetFluidState.getHeight(this.level, curPos); // Keeping this extra check cause why not :p

		boolean isHeightValid = fluidHeight > 0.0F;
		if (this.currentState == FishingBobberEntity.State.FLYING) {
			if (this.hookedIn != null) {
				setDeltaMovement(Vector3d.ZERO);
				this.currentState = FishingBobberEntity.State.HOOKED_IN_ENTITY;
				return;
			}

			if (isHeightValid) {
				setDeltaMovement(getDeltaMovement().multiply(0.3D, 0.2D, 0.3D));
				this.currentState = FishingBobberEntity.State.BOBBING;
				return;
			}

			checkCollision();
		} else {
			if (this.currentState == FishingBobberEntity.State.HOOKED_IN_ENTITY) {
				if (this.hookedIn != null) {
					if (this.hookedIn.removed) {
						this.hookedIn = null;
						this.currentState = FishingBobberEntity.State.FLYING;
					} else setPos(this.hookedIn.getX(), this.hookedIn.getY(0.8D), this.hookedIn.getZ());
				}
				return;
			}

			if (this.currentState == FishingBobberEntity.State.BOBBING) {
				Vector3d curDeltaMovement = getDeltaMovement();
				double fluidHeightYOffset = getY() + curDeltaMovement.y - curPos.getY() - fluidHeight;

				if (Math.abs(fluidHeightYOffset) < 0.01D) fluidHeightYOffset += Math.signum(fluidHeightYOffset) * 0.1D;

				setDeltaMovement(curDeltaMovement.x * 0.9D, curDeltaMovement.y - fluidHeightYOffset * this.random.nextDouble() * 0.2D, curDeltaMovement.z * 0.9D);

				if (this.nibble <= 0 && this.timeUntilHooked <= 0) this.openLava = true;
				else this.openLava = openLava && outOfLavaTime < 10 && isValidLavaPos(curPos);

				if (isHeightValid) {
					this.outOfLavaTime = Math.max(0, outOfLavaTime - 1);

					if (biting) setDeltaMovement(getDeltaMovement().add(0.0D, -0.1D * this.syncronizedRandom.nextDouble() * this.syncronizedRandom.nextDouble(), 0.0D));
					if (!this.level.isClientSide) handleCatchingFishInLava(curPos);
				} else this.outOfLavaTime = Math.min(10, this.outOfLavaTime + 1);
			}
		}

		move(MoverType.SELF, getDeltaMovement());
		updateRotation();

		if (this.currentState == FishingBobberEntity.State.FLYING && (this.onGround || this.horizontalCollision)) setDeltaMovement(Vector3d.ZERO);

		setDeltaMovement(getDeltaMovement().scale(0.92D));
		reapplyPosition();
	}

	protected void handleCatchingFishInLava(BlockPos targetPos) {
		ServerWorld curServerWorld = (ServerWorld) level; // Assumes this check was done before calling this method
		BlockPos aboveTargetPos = targetPos.above();
		int baseHookSpeed = 1;

		if (this.random.nextFloat() < 0.3F && !this.level.canSeeSky(aboveTargetPos)) baseHookSpeed++;
		if (this.nibble > 0) {
			this.nibble--;

			if (this.nibble <= 0) {
				this.timeUntilLured = 0;
				this.timeUntilHooked = 0;

				getEntityData().set(DATA_BITING, false);
			}
		} else if (this.timeUntilHooked > 0) {
			this.timeUntilHooked -= baseHookSpeed;

			if (this.timeUntilHooked > 0) { //TODO 1.20.1 Refactor because Mojank is Mojanking:tm:
				this.fishAngle = (float) (this.fishAngle + this.random.nextGaussian() * 4.0F);

				float fishAngleRadians = this.fishAngle * ((float) Math.PI / 180F);
				float xFishAngle = MathHelper.sin(fishAngleRadians);
				float zFishAngle = MathHelper.cos(fishAngleRadians);
				double properXOffset = getX() + (xFishAngle * this.timeUntilHooked * 0.1D);
				double properYOffset = MathHelper.floor(getY()) + 1.0D;
				double properZOffset = getZ() + (zFishAngle * this.timeUntilHooked * 0.1D);

				BlockState targetOffsetState = curServerWorld.getBlockState(new BlockPos(properXOffset, properYOffset - 1.0D, properZOffset));
				if (targetOffsetState.getMaterial() == Material.LAVA) {
					if (this.random.nextFloat() < 0.15F) {
						curServerWorld.sendParticles(ParticleTypes.SOUL, properXOffset, properYOffset - 0.1D, properZOffset, 1, xFishAngle, 0.1D, zFishAngle, 0.0D);
					}

					float xFishAngleOffset = xFishAngle * 0.04F;
					float zFishAngleOffset = zFishAngle * 0.04F;

					curServerWorld.sendParticles(ParticleTypes.LAVA, properXOffset, properYOffset, properZOffset, 0, zFishAngleOffset, 0.01D, -xFishAngleOffset, 1.0D);
					curServerWorld.sendParticles(ParticleTypes.LAVA, properXOffset, properYOffset, properZOffset, 0, -zFishAngleOffset, 0.01D, xFishAngleOffset, 1.0D);
				}
			} else {
				double yOffset = getY() + 0.5D;

				playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);

				curServerWorld.sendParticles(ParticleTypes.SOUL, getX(), yOffset, getZ(), (int)(1.0F + getBbWidth() * 20.0F), getBbWidth(), 0.0D, getBbWidth(), 0.2D);
				curServerWorld.sendParticles(ParticleTypes.LAVA, getX(), yOffset, getZ(), (int)(1.0F + getBbWidth() * 20.0F), getBbWidth(), 0.0D, getBbWidth(), 0.2D);

				this.nibble = MathHelper.nextInt(random, 20, 40);

				getEntityData().set(DATA_BITING, true);
			}
		} else if (this.timeUntilLured > 0) {
			this.timeUntilLured -= baseHookSpeed;
			float splashChance = 0.15F;

			if (this.timeUntilLured < 20) splashChance = splashChance + (20 - timeUntilLured) * 0.05F;
			else if (this.timeUntilLured < 40) splashChance = splashChance + (40 - timeUntilLured) * 0.02F;
			else if (this.timeUntilLured < 60) splashChance = splashChance + (60 - timeUntilLured) * 0.01F;

			if (this.random.nextFloat() < splashChance) {
				float randomXRadianAngle = MathHelper.nextFloat(random, 0.0F, 360.0F) * ((float) Math.PI / 180F);
				float randomZRadianAngle = MathHelper.nextFloat(random, 25.0F, 60.0F);
				double properXOffset = getX() + (MathHelper.sin(randomXRadianAngle) * randomZRadianAngle * 0.1D);
				double properYOffset = MathHelper.floor(getY()) + 1.0D;
				double properZOffset = getZ() + (MathHelper.cos(randomXRadianAngle) * randomZRadianAngle * 0.1D);
				BlockState targetOffsetPos = curServerWorld.getBlockState(new BlockPos(properXOffset, properYOffset - 1.0D, properZOffset));

				if (targetOffsetPos.getMaterial() == Material.LAVA) curServerWorld.sendParticles(ParticleTypes.LAVA, properXOffset, properYOffset, properZOffset, 2 + this.random.nextInt(2), 0.1D, 0.0D, 0.1D, 0.0D);
			}

			if (this.timeUntilLured <= 0) {
				this.fishAngle = MathHelper.nextFloat(random, 0.0F, 360.0F);
				this.timeUntilHooked = MathHelper.nextInt(random, 20, 80);
			}
		} else {
			this.timeUntilLured = MathHelper.nextInt(random, 100, 600);
			this.timeUntilLured -= this.lureSpeed * 20 * 5;
		}
	}

	protected int getGroundLifetime() {
		return 2400;
	}

	protected double getMaxDistFromBobberSqrd() {
		return 2048.0D;
	}

	protected boolean isValidLavaPos(BlockPos targetPos) {
		for (int curYOffset = -1; curYOffset <= 2; curYOffset++) {
			EnumUtil.BobberLavaType targetBobberLavaType = getOpenLavaTypeForArea(targetPos.offset(-2, curYOffset, -2), targetPos.offset(2, curYOffset, 2));

			switch(targetBobberLavaType) {
				case INVALID:
					return false;
				case ABOVE_LAVA:
					return targetBobberLavaType != EnumUtil.BobberLavaType.INVALID;
				case INSIDE_LAVA:
					return targetBobberLavaType != EnumUtil.BobberLavaType.ABOVE_LAVA;
			}
		}
		return true;
	}

	protected EnumUtil.BobberLavaType getOpenLavaTypeForArea(BlockPos initialPos, BlockPos targetPos) {
		return BlockPos.betweenClosedStream(initialPos, targetPos).map(this::getOpenLavaTypeForBlock).reduce((curPosBobberLavaType, nextPosBobberLavaType) -> curPosBobberLavaType == nextPosBobberLavaType ? curPosBobberLavaType : EnumUtil.BobberLavaType.INVALID).orElse(EnumUtil.BobberLavaType.INVALID);
	}

	protected EnumUtil.BobberLavaType getOpenLavaTypeForBlock(BlockPos targetPos) {
		BlockState targetState = this.level.getBlockState(targetPos);
		FluidState targetFluidState = targetState.getFluidState();

		return !targetState.isAir() ? targetFluidState.is(FluidTags.LAVA) && targetFluidState.isSource() && targetState.getCollisionShape(level, targetPos).isEmpty() ? EnumUtil.BobberLavaType.INSIDE_LAVA : EnumUtil.BobberLavaType.INVALID : EnumUtil.BobberLavaType.ABOVE_LAVA;
	}
}
