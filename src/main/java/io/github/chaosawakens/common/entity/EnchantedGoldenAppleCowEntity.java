package io.github.chaosawakens.common.entity;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IChargeableMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(
	value = Dist.CLIENT,
	_interface = IChargeableMob.class
)
public class EnchantedGoldenAppleCowEntity extends AnimalEntity implements IChargeableMob {
	private static final DataParameter<Boolean> ENCHANTED = EntityDataManager.createKey(EnchantedGoldenAppleCowEntity.class, DataSerializers.BOOLEAN);
	
	public EnchantedGoldenAppleCowEntity(EntityType<? extends EnchantedGoldenAppleCowEntity> type, World worldIn) {
		super(type, worldIn);
	}


	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
		if (CAConfig.COMMON.enableEnchantedGoldenAppleCowBreeding.get()) {
			this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
			this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.WHEAT), false));
			this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
		}
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2F)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 10);
	}

	@Nullable
	@Override
	public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
		return CAConfig.COMMON.enableEnchantedGoldenAppleCowBreeding.get() ? CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get().create(world) : null;
	}
	
	@Override
	public boolean canFallInLove() {
		return CAConfig.COMMON.enableEnchantedGoldenAppleCowBreeding.get();
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(ENCHANTED, true);
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		if (this.dataManager.get(ENCHANTED)) {
			compound.putBoolean("enchanted", true);
		}
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.dataManager.set(ENCHANTED, compound.getBoolean("enchanted"));
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_COW_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_COW_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_COW_DEATH;
	}
	
	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
	}
	
	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}
	
	@Override
	public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
		ItemStack itemstack = playerIn.getHeldItem(hand);
		if (itemstack.getItem() == Items.BUCKET) {
			playerIn.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
			ItemStack itemstack1 = DrinkHelper.fill(itemstack, playerIn, Items.MILK_BUCKET.getDefaultInstance());
			playerIn.setHeldItem(hand, itemstack1);
			return ActionResultType.func_233537_a_(this.world.isRemote);
		} else {
			return super.getEntityInteractionResult(playerIn, hand);
		}
	}
	
	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isChild() ? sizeIn.height * 0.95F : 1.3F;
	}
	
	@Override
	public boolean isCharged() {
		return this.dataManager.get(ENCHANTED);
	}
}
