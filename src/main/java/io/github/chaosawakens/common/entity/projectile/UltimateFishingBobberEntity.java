package io.github.chaosawakens.common.entity.projectile;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CALootTables;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UltimateFishingBobberEntity extends FishingBobberEntity implements IEntityAdditionalSpawnData {
	private final Random syncronizedRandom = new Random();
	private int outOfLiquidTime;
	private boolean biting;
	private FluidState fluid;
	private static final Field HAS_LEFT_OWNER = ObfuscationReflectionHelper.findField(ProjectileEntity.class, "field_234611_d_");
	private static final DataParameter<Integer> DATA_HOOKED_ENTITY = EntityDataManager.defineId(UltimateFishingBobberEntity.class, DataSerializers.INT);
	private static final DataParameter<Boolean> DATA_BITING = EntityDataManager.defineId(UltimateFishingBobberEntity.class, DataSerializers.BOOLEAN);
	private int life;
	private int nibble;
	private int timeUntilLured;
	private int timeUntilHooked;
	private float fishAngle;
	private boolean openWater = true;
	private boolean openLava = true;
	private Entity hookedIn;
	private State currentState = UltimateFishingBobberEntity.State.FLYING;
	private final int luck;
	private final int lureSpeed;

	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public UltimateFishingBobberEntity(World worldIn) {
		super(worldIn, Minecraft.getInstance().player, 0, 0, 0);
		this.luck = 0;
		this.lureSpeed = 0;
	}

	@OnlyIn(Dist.CLIENT)
	public UltimateFishingBobberEntity(World p_i47290_1_, PlayerEntity p_i47290_2_, double p_i47290_3_, double p_i47290_5_, double p_i47290_7_) {
		this(p_i47290_1_, p_i47290_2_, 0, 0);
		this.setPos(p_i47290_3_, p_i47290_5_, p_i47290_7_);
		this.xo = this.getX();
		this.yo = this.getY();
		this.zo = this.getZ();
	}

	public UltimateFishingBobberEntity(FMLPlayMessages.SpawnEntity spawnPacket, World world) {
		super(world.getPlayerByUUID(spawnPacket.getAdditionalData().readUUID()), world, 0, 0);
		PacketBuffer buf = spawnPacket.getAdditionalData();
		this.luck = buf.readInt();
		this.lureSpeed = buf.readInt();
		this.fishAngle = buf.readFloat();
		this.life = buf.readInt();
		this.nibble = buf.readInt();
		this.timeUntilLured = buf.readInt();
		this.timeUntilHooked = buf.readInt();
		this.openLava = buf.readBoolean();
		this.openWater = buf.readBoolean();
		this.outOfLiquidTime = buf.readInt();
		this.biting = buf.readBoolean();
	}

	public UltimateFishingBobberEntity(World w, PlayerEntity p, int luck, int speed) {
		super(p, w, luck, speed);
		this.noCulling = true;
		this.setOwner(p);
		p.fishing = this;
		this.luck = luck;
		this.lureSpeed = speed;
	}

	public UltimateFishingBobberEntity(PlayerEntity p_i50220_1_, World p_i50220_2_, int p_i50220_3_, int p_i50220_4_) {
		this(p_i50220_2_, p_i50220_1_, p_i50220_3_, p_i50220_4_);
		float f = p_i50220_1_.xRot;
		float f1 = p_i50220_1_.yRot;
		float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
		float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
		float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
		float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));
		double d0 = p_i50220_1_.getX() - (double) f3 * 0.3D;
		double d1 = p_i50220_1_.getEyeY();
		double d2 = p_i50220_1_.getZ() - (double) f2 * 0.3D;
		this.moveTo(d0, d1, d2, f1, f);
		Vector3d vector3d = new Vector3d(-f3, MathHelper.clamp(-(f5 / f4), -5.0F, 5.0F), -f2);
		double d3 = vector3d.length();
		vector3d = vector3d.multiply(
				0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D,
				0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D,
				0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D);
		this.setDeltaMovement(vector3d);
		this.yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI));
		this.xRot = (float) (MathHelper.atan2(vector3d.y, MathHelper.sqrt(getHorizontalDistanceSqr(vector3d))) * (double) (180F / (float) Math.PI));
		this.yRotO = this.yRot;
		this.xRotO = this.xRot;
	}

	@Override
	public boolean isInLava() {
		return super.isInLava();
	}

	@Override
	public boolean isInWater() {
		return super.isInWater();
	}

	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(DATA_HOOKED_ENTITY, 0);
		this.getEntityData().define(DATA_BITING, false);
	}

	private boolean leftOwner() {
		Entity entity = this.getOwner();
		if (entity != null) {
			for (Entity entity1 : this.level.getEntities(this,
					this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D),
					(p_234613_0_) -> !p_234613_0_.isSpectator() && p_234613_0_.isPickable())) {
				if (entity1.getRootVehicle() == entity.getRootVehicle()) return false;
			}
		}

		return true;
	}

	@Override
	protected void onHit(RayTraceResult p_70227_1_) {
		RayTraceResult.Type raytraceresult$type = p_70227_1_.getType();
		if (raytraceresult$type == RayTraceResult.Type.ENTITY) this.onHitEntity((EntityRayTraceResult) p_70227_1_);
		else if (raytraceresult$type == RayTraceResult.Type.BLOCK) this.onHitBlock((BlockRayTraceResult) p_70227_1_);
	}

	public void tick() {
		this.syncronizedRandom.setSeed(this.getUUID().getLeastSignificantBits() ^ this.level.getGameTime());

		try {
			// Using reflection to make leftOwner field public, for this. Should fix crash
			boolean hasLeftOwner = HAS_LEFT_OWNER.getBoolean(this);
			if (!hasLeftOwner) HAS_LEFT_OWNER.setBoolean(this, this.leftOwner());
		} catch (IllegalAccessException e) {
			ChaosAwakens.LOGGER.error(e.getStackTrace());
		}

		PlayerEntity playerentity = this.getPlayerOwner();
		if (playerentity == null) {
			this.remove();
		} else if (this.level.isClientSide || !this.shouldStopFishing(playerentity)) {
			if (this.onGround) {
				++this.life;
				if (this.life >= 2200) {
					this.remove();
					return;
				}
			} else this.life = 0;

			float f = 0.0F;
			BlockPos blockpos = this.blockPosition();
			FluidState fluidstate = this.level.getFluidState(blockpos);
			if (fluidstate.is(FluidTags.LAVA)) {
				f = fluidstate.getHeight(this.level, blockpos) * fluidstate.getHeight(this.level, blockpos);
				this.fluid = fluidstate;
			}

			boolean flag = f > 0.0F;
			if (this.currentState == UltimateFishingBobberEntity.State.FLYING) {
				if (this.hookedIn != null) {
					this.setDeltaMovement(Vector3d.ZERO);
					this.currentState = UltimateFishingBobberEntity.State.HOOKED_IN_ENTITY;
					return;
				}

				if (flag) {
					this.setDeltaMovement(this.getDeltaMovement().multiply(0.3D, 0.2D, 0.3D));
					this.currentState = UltimateFishingBobberEntity.State.BOBBING;
					return;
				}

				this.checkCollision();
			} else {
				if (this.currentState == UltimateFishingBobberEntity.State.HOOKED_IN_ENTITY) {
					if (this.hookedIn != null) {
						if (!this.hookedIn.isAlive()) {
							this.hookedIn = null;
							this.currentState = UltimateFishingBobberEntity.State.FLYING;
						} else this.setPos(this.hookedIn.getX(), this.hookedIn.getY(0.8D), this.hookedIn.getZ());
					}

					return;
				}

				if (this.currentState == UltimateFishingBobberEntity.State.BOBBING) {
					Vector3d vector3d = this.getDeltaMovement();
					double d0 = this.getY() + vector3d.y - (double) blockpos.getY() - (double) f;
					if (Math.abs(d0) < 0.01D) d0 += Math.signum(d0) * 0.1D;
					this.setDeltaMovement(vector3d.x * 0.9D, vector3d.y - d0 * (double) this.random.nextFloat() * 0.2D, vector3d.z * 0.9D);
					if (this.nibble <= 0 && this.timeUntilHooked <= 0) {
						this.openWater = true;
						if (this.fluid.is(FluidTags.LAVA)) this.openLava = true;
					} else {
						this.openWater = this.openWater && this.outOfLiquidTime < 10 && this.calculateOpenWater(blockpos);
						if (this.fluid.is(FluidTags.LAVA)) this.openLava = this.openLava && this.outOfLiquidTime < 10 && this.calculateOpenLava(blockpos);
					}

					if (flag) {
						this.outOfLiquidTime = Math.max(0, this.outOfLiquidTime - 1);
						if (this.biting) {
							this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.1D * (double) this.syncronizedRandom.nextFloat() * (double) this.syncronizedRandom.nextFloat(), 0.0D));
						}

						if (!this.level.isClientSide) this.catchingFish(blockpos);
					} else this.outOfLiquidTime = Math.min(10, this.outOfLiquidTime + 1);
				}
			}

			if (!fluidstate.is(FluidTags.WATER) && !fluidstate.is(FluidTags.LAVA)) this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));

			this.move(MoverType.SELF, this.getDeltaMovement());
			this.updateRotation();
			if (this.currentState == UltimateFishingBobberEntity.State.FLYING && (this.onGround || this.horizontalCollision)) this.setDeltaMovement(Vector3d.ZERO);

			this.setDeltaMovement(this.getDeltaMovement().scale(0.92D));
			this.reapplyPosition();
		}
	}

	@SuppressWarnings("deprecation")
	private boolean shouldStopFishing(PlayerEntity p_234600_1_) {
		ItemStack itemstack = p_234600_1_.getMainHandItem();
		ItemStack itemstack1 = p_234600_1_.getOffhandItem();
		boolean flag = itemstack.getItem() == CAItems.ULTIMATE_FISHING_ROD.get();
		boolean flag1 = itemstack1.getItem() == CAItems.ULTIMATE_FISHING_ROD.get();
		if (!p_234600_1_.removed && p_234600_1_.isAlive() && (flag || flag1) && !(this.distanceToSqr(p_234600_1_) > 2048.0D)) return false;
		else {
			this.remove();
			return true;
		}
	}

	@Override
	public boolean canChangeDimensions() {
		return true;
	}

	@Override
	protected boolean canHitEntity(Entity e) {
		if (!e.isSpectator() && e.isAlive() && e.isPickable()) {
			Entity entity = this.getOwner();
			return entity == null || this.leftOwner() || !entity.isPassengerOfSameVehicle(e) || e.isAlive() && e instanceof ItemEntity;
		} else return super.canHitEntity(e);
	}

	@Override
	protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
		// super.onHitEntity(p_213868_1_);
		if (!this.level.isClientSide) {
			this.hookedIn = p_213868_1_.getEntity();
			this.setHookedEntity();
		}
	}

	@Override
	protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
		super.onHitBlock(p_230299_1_);
		this.setDeltaMovement(this.getDeltaMovement().normalize().scale(p_230299_1_.distanceTo(this)));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte p_70103_1_) {
		if (p_70103_1_ == 31 && this.level.isClientSide && this.hookedIn instanceof PlayerEntity && ((PlayerEntity) this.hookedIn).isLocalPlayer()) this.bringInHookedEntity();
		// super.handleEntityEvent(p_70103_1_);
	}

	private void setHookedEntity() {
		this.getEntityData().set(DATA_HOOKED_ENTITY, this.hookedIn.getId() + 1);
	}

	private void catchingFish(BlockPos p_190621_1_) {
		ServerWorld serverworld = (ServerWorld) this.level;
		int i = 1;
		BlockPos blockpos = p_190621_1_.above();
		if (this.fluid.is(FluidTags.WATER)) {
			if (this.random.nextFloat() < 0.25F && this.level.isRainingAt(blockpos)) ++i;

			if (this.random.nextFloat() < 0.5F && !this.level.canSeeSky(blockpos)) {
				--i;
			} else if (this.fluid.is(FluidTags.LAVA)) {
				if (this.random.nextFloat() < 0.25F && this.fireImmune()) ++i;

				if (this.random.nextFloat() < 0.5F && !this.level.canSeeSky(blockpos)) ++i;

				if (this.random.nextFloat() < 0.4F && this.level.isRaining() || this.level.isDay()) --i;
			}
		} else {
			// Better chance to get goodies in Nether-temperature dimensions
			if (this.random.nextFloat() < 0.25F && this.level.dimensionType().ultraWarm()) ++i;
		}

		if (this.nibble > 0) {
			--this.nibble;
			if (this.nibble <= 0) {
				this.timeUntilLured = 0;
				this.timeUntilHooked = 0;
				this.getEntityData().set(DATA_BITING, false);
			}
		} else if (this.timeUntilHooked > 0) {
			this.timeUntilHooked -= i;
			if (this.timeUntilHooked > 0) {
				this.fishAngle = (float) ((double) this.fishAngle + this.random.nextGaussian() * 4.0D);
				float f = this.fishAngle * ((float) Math.PI / 180F);
				float f1 = MathHelper.sin(f);
				float f2 = MathHelper.cos(f);
				double d0 = this.getX() + (double) (f1 * (float) this.timeUntilHooked * 0.1F);
				double d1 = ((float) MathHelper.floor(this.getY()) + 1.0F);
				double d2 = this.getZ() + (double) (f2 * (float) this.timeUntilHooked * 0.1F);

				if (serverworld.getBlockState(new BlockPos((int) d0, (int) d1 - 1, (int) d2)).getMaterial() == net.minecraft.block.material.Material.WATER) {
					if (this.random.nextFloat() < 0.15F) serverworld.sendParticles(this.fluid.is(FluidTags.WATER) ? ParticleTypes.BUBBLE : ParticleTypes.SPLASH, d0, d1 - (double) 0.1F, d2, 1, f1, 0.1D, f2, 0.0D);
					float f3 = f1 * 0.04F;
					float f4 = f2 * 0.04F;
					serverworld.sendParticles(ParticleTypes.FISHING, d0, d1, d2, 0, f4, 0.01D, (-f3), 1.0D);
					serverworld.sendParticles(ParticleTypes.FISHING, d0, d1, d2, 0, (-f4), 0.01D, f3, 1.0D);
				} else if (serverworld.getBlockState(new BlockPos((int) d0, (int) d1 - 1, (int) d2)).getMaterial() == net.minecraft.block.material.Material.LAVA) {
					if (this.random.nextFloat() < 0.15F) serverworld.sendParticles(this.fluid.is(FluidTags.LAVA) ? ParticleTypes.SMOKE : ParticleTypes.DRIPPING_LAVA, d0, d1 - (double) 0.1F, d2, 1, f1, 0.1D, f2, 0.0D);
					float f3 = f1 * 0.04F;
					float f4 = f2 * 0.04F;
					serverworld.sendParticles(ParticleTypes.SMOKE, d0, d1, d2, 0, f4, 0.01D, -f3, 1.0D);
					serverworld.sendParticles(ParticleTypes.SMOKE, d0, d1, d2, 0, -f4, 0.01D, f3, 1.0D);
				}
			} else {
				ServerWorld server = (ServerWorld) this.level;
				double d1 = (float) MathHelper.floor(this.getY()) + 1.0F;
				BlockState state = server.getBlockState(new BlockPos(this.getX(), d1 - 1.0D, this.getZ()));
				Vector3d v = this.getDeltaMovement();
				double d3 = this.getY() + 0.5D;

				if (state.getMaterial() == Material.WATER) {
					this.setDeltaMovement(v.x, -0.4F * MathHelper.nextFloat(this.random, 0.6F, 1.0F), v.z);
					this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
					serverworld.sendParticles(this.fluid.is(FluidTags.WATER) ? ParticleTypes.BUBBLE : ParticleTypes.SPLASH, this.getX(), d3, this.getZ(), (int) (1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2F);
					serverworld.sendParticles(ParticleTypes.FISHING, this.getX(), d3, this.getZ(), (int) (1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2F);

				} else if (state.getMaterial() == Material.LAVA) {
					this.setDeltaMovement(v.x, -0.4F * MathHelper.nextFloat(this.random, 0.6F, 1.0F), v.z);
					this.playSound(SoundEvents.LAVA_POP, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
					serverworld.sendParticles(this.fluid.is(FluidTags.LAVA) ? ParticleTypes.SMOKE : ParticleTypes.DRIPPING_LAVA, this.getX(), d3, this.getZ(), (int) (1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2F);
					serverworld.sendParticles(ParticleTypes.FLAME, this.getX(), d3, this.getZ(), (int) (1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2F);
				}
				this.getEntityData().set(DATA_BITING, true);
				this.nibble = MathHelper.nextInt(this.random, 20, 40);
			}
		} else if (this.timeUntilLured > 0) {
			this.timeUntilLured -= i;
			float f5 = 0.15F;
			if (this.timeUntilLured < 20) f5 = (float) ((double) f5 + (double) (20 - this.timeUntilLured) * 0.05D);
			else if (this.timeUntilLured < 40) f5 = (float) ((double) f5 + (double) (40 - this.timeUntilLured) * 0.02D);
			else if (this.timeUntilLured < 60) f5 = (float) ((double) f5 + (double) (60 - this.timeUntilLured) * 0.01D);

			if (this.random.nextFloat() < f5) {
				float f6 = MathHelper.nextFloat(this.random, 0.0F, 360.0F) * ((float) Math.PI / 180F);
				float f7 = MathHelper.nextFloat(this.random, 25.0F, 60.0F);
				double d4 = this.getX() + (double) (MathHelper.sin(f6) * f7 * 0.1F);
				double d5 = (float) MathHelper.floor(this.getY()) + 1.0F;
				double d6 = this.getZ() + (double) (MathHelper.cos(f6) * f7 * 0.1F);
				if (serverworld.getBlockState(new BlockPos(d4, d5 - 1.0D, d6)).getMaterial() == net.minecraft.block.material.Material.WATER) serverworld.sendParticles(ParticleTypes.SPLASH, d4, d5, d6, 2 + this.random.nextInt(2), 0.1F, 0.0D, 0.1F, 0.0D);
				else if (serverworld.getBlockState(new BlockPos(d4, d5 - 1.0D, d6)).getMaterial() == net.minecraft.block.material.Material.LAVA) serverworld.sendParticles(ParticleTypes.LARGE_SMOKE, d4, d5, d6, 2 + this.random.nextInt(2), 0.1F, 0.0D, 0.1F, 0.0D);
			}

			if (this.timeUntilLured <= 0) {
				this.fishAngle = MathHelper.nextFloat(this.random, 0.0F, 360.0F);
				this.timeUntilHooked = MathHelper.nextInt(this.random, 20, 80);
			}
		} else {
			this.timeUntilLured = MathHelper.nextInt(this.random, 5, 15);
			this.timeUntilLured -= this.lureSpeed * 5 * 2;
		}

	}

	@Override
	public void remove(boolean keepData) {
		super.remove(keepData);
		PlayerEntity playerentity = this.getPlayerOwner();
		if (playerentity != null) playerentity.fishing = null;

	}

	@Override
	@Nullable
	public PlayerEntity getPlayerOwner() {
		Entity entity = this.getOwner();
		return entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
	}

	@Override
	@Nullable
	public Entity getHookedIn() {
		return this.hookedIn;
	}

	private boolean calculateOpenWater(BlockPos p_234603_1_) {
		UltimateFishingBobberEntity.WaterType waterType = UltimateFishingBobberEntity.WaterType.INVALID;

		for (int i = -1; i <= 2; ++i) {
			UltimateFishingBobberEntity.WaterType openWaterType = this.getOpenWaterTypeForArea(p_234603_1_.offset(-2, i, -2), p_234603_1_.offset(2, i, 2));
			switch (openWaterType) {
			case INVALID:
				return false;
			case ABOVE_WATER:
				if (waterType == UltimateFishingBobberEntity.WaterType.INVALID) return false;
				break;
			case INSIDE_WATER:
				if (waterType == UltimateFishingBobberEntity.WaterType.ABOVE_WATER) return false;
			}
			waterType = openWaterType;
		}
		return true;
	}

	private boolean calculateOpenLava(BlockPos p_234603_1_) {
		UltimateFishingBobberEntity.LavaType lavaType = UltimateFishingBobberEntity.LavaType.INVALID;

		for (int i = -1; i <= 2; ++i) {
			UltimateFishingBobberEntity.LavaType openLavaType = this.getOpenLavaTypeForArea(p_234603_1_.offset(-2, i, -2), p_234603_1_.offset(2, i, 2));
			switch (openLavaType) {
			case INVALID:
				return false;
			case ABOVE_LAVA:
				if (lavaType == UltimateFishingBobberEntity.LavaType.INVALID) return false;
				break;
			case INSIDE_LAVA:
				if (lavaType == UltimateFishingBobberEntity.LavaType.ABOVE_LAVA) return false;
			}
			lavaType = openLavaType;
		}
		return true;
	}

	private UltimateFishingBobberEntity.WaterType getOpenWaterTypeForArea(BlockPos p_234602_1_, BlockPos p_234602_2_) {
		return BlockPos.betweenClosedStream(p_234602_1_, p_234602_2_)
				.map(this::getOpenWaterTypeForBlock)
				.reduce((p_234601_0_, p_234601_1_) -> p_234601_0_ == p_234601_1_ ? p_234601_0_ : WaterType.INVALID)
				.orElse(UltimateFishingBobberEntity.WaterType.INVALID);
	}

	private UltimateFishingBobberEntity.LavaType getOpenLavaTypeForArea(BlockPos p_234602_1_, BlockPos p_234602_2_) {
		return BlockPos.betweenClosedStream(p_234602_1_, p_234602_2_)
				.map(this::getOpenLavaTypeForBlock)
				.reduce((p_234601_0_, p_234601_1_) -> p_234601_0_ == p_234601_1_ ? p_234601_0_ : LavaType.INVALID)
				.orElse(UltimateFishingBobberEntity.LavaType.INVALID);
	}

	@SuppressWarnings("deprecation")
	private UltimateFishingBobberEntity.WaterType getOpenWaterTypeForBlock(BlockPos p_234604_1_) {
		BlockState blockstate = this.level.getBlockState(p_234604_1_);
		if (!blockstate.isAir() && !blockstate.is(Blocks.LILY_PAD)) {
			FluidState fluidstate = blockstate.getFluidState();
			return (fluidstate.is(FluidTags.WATER)) && fluidstate.isSource() && blockstate.getCollisionShape(this.level, p_234604_1_).isEmpty()
					? UltimateFishingBobberEntity.WaterType.INSIDE_WATER
					: UltimateFishingBobberEntity.WaterType.INVALID;
		} else return UltimateFishingBobberEntity.WaterType.ABOVE_WATER;
	}

	@SuppressWarnings("deprecation")
	private UltimateFishingBobberEntity.LavaType getOpenLavaTypeForBlock(BlockPos p_234604_1_) {
		BlockState blockstate = this.level.getBlockState(p_234604_1_);
		if (!blockstate.isAir() && !blockstate.is(Blocks.LILY_PAD)) {
			FluidState fluidstate = blockstate.getFluidState();
			return (fluidstate.is(FluidTags.LAVA)) && fluidstate.isSource() && blockstate.getCollisionShape(this.level, p_234604_1_).isEmpty()
					? UltimateFishingBobberEntity.LavaType.INSIDE_LAVA
					: UltimateFishingBobberEntity.LavaType.INVALID;
		} else return UltimateFishingBobberEntity.LavaType.ABOVE_LAVA;
	}

	private void checkCollision() {
		RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
		if (raytraceresult.getType() == RayTraceResult.Type.MISS || !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) this.onHit(raytraceresult);
	}

	enum State {
		FLYING, HOOKED_IN_ENTITY, BOBBING;
	}

	enum WaterType {
		ABOVE_WATER, INSIDE_WATER, INVALID;
	}

	enum LavaType {
		ABOVE_LAVA, INSIDE_LAVA, INVALID;
	}

	@Override
	protected void bringInHookedEntity() {
		Entity entity = this.getOwner();
		if (entity != null) {
			Vector3d vector3d = (new Vector3d(entity.getX() - this.getX(), entity.getY() - this.getY(), entity.getZ() - this.getZ())).scale(0.1D);
			this.hookedIn.setDeltaMovement(this.hookedIn.getDeltaMovement().add(vector3d));
		}
	}

	@Override
	public boolean isOpenWaterFishing() {
		return this.openWater;
	}

	public boolean isOpenLavaFishing() {
		return this.openLava;
	}

	@Override
	public int retrieve(ItemStack p_146034_1_) {
		PlayerEntity playerentity = this.getPlayerOwner();
		if (!this.level.isClientSide && playerentity != null) {
			int i = 0;
			net.minecraftforge.event.entity.player.ItemFishedEvent e = null;
			if (this.hookedIn != null) {
				this.bringInHookedEntity();
				CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity) playerentity, p_146034_1_, this, Collections.emptyList());
				this.level.broadcastEntityEvent(this, (byte) 31);
				i = this.hookedIn instanceof ItemEntity ? 3 : 5;
			} else if (this.nibble > 0) {
				LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) this.level))
						.withParameter(LootParameters.ORIGIN, this.position())
						.withParameter(LootParameters.TOOL, p_146034_1_)
						.withParameter(LootParameters.THIS_ENTITY, this)
						.withRandom(this.random).withLuck((float) this.luck + playerentity.getLuck());
				lootcontext$builder.withParameter(LootParameters.KILLER_ENTITY, this.getOwner())
						.withParameter(LootParameters.THIS_ENTITY, this);
				double d = (float) MathHelper.floor(this.getBoundingBox().minY) + 1.0F;
				BlockState state = this.level.getBlockState(new BlockPos(this.getX(), d - 1.0D, this.getZ()));
				ResourceLocation table;

				if (state.getMaterial() == Material.LAVA) {
					if (BiomeDictionary.hasType(Biomes.NETHER_WASTES, BiomeDictionary.Type.NETHER)
							|| BiomeDictionary.hasType(Biomes.CRIMSON_FOREST, BiomeDictionary.Type.NETHER)
							|| BiomeDictionary.hasType(Biomes.WARPED_FOREST, BiomeDictionary.Type.NETHER)) {
						table = CALootTables.FISHING_NETHER_FISH;
					} else {
						table = CALootTables.FISHING_LAVA_FISH;
					}
				} else {
					table = LootTables.FISHING;
				}

				LootTable loottable = this.level.getServer().getLootTables().get(table);
				List<ItemStack> list = loottable.getRandomItems(lootcontext$builder.create(LootParameterSets.FISHING));
				e = new net.minecraftforge.event.entity.player.ItemFishedEvent(list, this.onGround ? 2 : 1, this);

				net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(e);
				if (e.isCanceled()) {
					this.remove();
					return e.getRodDamage();
				}

				CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayerEntity) playerentity, p_146034_1_, this, list);

				for (ItemStack itemstack : list) {
					ItemEntity itementity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemstack);
					double d0 = playerentity.getX() - this.getX();
					double d1 = playerentity.getY() - this.getY();
					double d2 = playerentity.getZ() - this.getZ();
					itementity.setDeltaMovement(d0 * 0.1D, d1 * 0.1D + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08D, d2 * 0.1D);
					this.level.addFreshEntity(itementity);
					playerentity.level.addFreshEntity(new ExperienceOrbEntity(playerentity.level, playerentity.getX(), playerentity.getY() + 0.5D, playerentity.getZ() + 0.5D, this.random.nextInt(6) + 1));
					if (itemstack.getItem().is(ItemTags.FISHES)) playerentity.awardStat(Stats.FISH_CAUGHT, 1);
					// Not completely foolproof
					// boolean immune = itementity.fireImmune();
					// if (itementity.isInLava() && itementity.isOnFire()) {
					// immune = true;
					// itementity.setRemainingFireTicks(0);
					// }
				}

				i = 1;
			}

			if (this.onGround) {
				i = 2;
			}

			this.remove();
			return e == null ? i : e.getRodDamage();
		} else {
			return 0;
		}
	}

	@Override
	public void onSyncedDataUpdated(DataParameter<?> p_184206_1_) {
		if (DATA_HOOKED_ENTITY.equals(p_184206_1_)) {
			int i = this.getEntityData().get(DATA_HOOKED_ENTITY);
			this.hookedIn = i > 0 ? this.level.getEntity(i - 1) : null;
		}

		if (DATA_BITING.equals(p_184206_1_)) {
			this.biting = this.getEntityData().get(DATA_BITING);
			if (this.biting) this.setDeltaMovement(this.getDeltaMovement().x, -0.4F * MathHelper.nextFloat(this.syncronizedRandom, 0.6F, 1.0F), this.getDeltaMovement().z);
		}

		super.onSyncedDataUpdated(p_184206_1_);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		Entity entity = this.getOwner();
		return new SSpawnObjectPacket(this, entity == null ? this.getId() : entity.getId());
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		PlayerEntity p = this.getPlayerOwner();
		if (p != null) buffer.writeUUID(p.getUUID());
		buffer.writeInt(luck);
		buffer.writeInt(lureSpeed);
		buffer.writeInt(life);
		buffer.writeInt(nibble);
		buffer.writeInt(outOfLiquidTime);
		buffer.writeInt(timeUntilHooked);
		buffer.writeInt(timeUntilLured);
		buffer.writeBoolean(biting);
		buffer.writeBoolean(openLava);
		buffer.writeBoolean(openWater);
		buffer.writeFloat(fishAngle);
	}

	@Nonnull
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
	}
}
