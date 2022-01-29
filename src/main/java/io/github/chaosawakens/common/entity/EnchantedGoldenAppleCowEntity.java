package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

@OnlyIn(value = Dist.CLIENT, _interface = IChargeableMob.class)
public class EnchantedGoldenAppleCowEntity extends AnimalEntity implements IChargeableMob {
    private static final DataParameter<Boolean> ENCHANTED = EntityDataManager.defineId(EnchantedGoldenAppleCowEntity.class, DataSerializers.BOOLEAN);

    public EnchantedGoldenAppleCowEntity(EntityType<? extends EnchantedGoldenAppleCowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 10);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        if (CAConfig.COMMON.enableEnchantedAnimalBreeding.get()) {
            this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
            this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WHEAT), false));
            this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        }
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
        return CAConfig.COMMON.enableEnchantedAnimalBreeding.get() ? CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get().create(world) : null;
    }

    @Override
    public boolean canFallInLove() {
        return CAConfig.COMMON.enableEnchantedAnimalBreeding.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ENCHANTED, true);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        if (this.entityData.get(ENCHANTED)) {
            compound.putBoolean("Enchanted", true);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(ENCHANTED, compound.getBoolean("Enchanted"));
    }

    @Override
    public void tick() {
        super.tick();
        this.entityData.set(ENCHANTED, true);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COW_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.COW_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COW_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    public void aiStep() {
        if (this.level.isClientSide) {
            this.level.addParticle(ParticleTypes.INSTANT_EFFECT, false, this.getRandomX(0.5D), this.getY(0.85D), this.getRandomZ(0.5D), -1000, -1000, -1000);
        }
        super.aiStep();
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity playerIn, Hand hand) {
        ItemStack itemstack = playerIn.getItemInHand(hand);
        if (itemstack.getItem() == Items.BUCKET) {
            playerIn.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, playerIn, Items.MILK_BUCKET.getDefaultInstance());
            playerIn.setItemInHand(hand, itemstack1);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(playerIn, hand);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.95F : 1.3F;
    }

    @Override
    public boolean isPowered() {
        return this.entityData.get(ENCHANTED);
    }

    public void thunderHit(ServerWorld serverWorld, LightningBoltEntity lightningBoltEntity) {
        if (net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get(), (timer) -> {})) {
            EnchantedGoldenCarrotPigEntity enchantedGoldenCarrotPigEntity = CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get().create(serverWorld);
            assert enchantedGoldenCarrotPigEntity != null;
            enchantedGoldenCarrotPigEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
            enchantedGoldenCarrotPigEntity.setNoAi(this.isNoAi());
            enchantedGoldenCarrotPigEntity.setBaby(this.isBaby());
            if (this.hasCustomName()) {
                enchantedGoldenCarrotPigEntity.setCustomName(this.getCustomName());
                enchantedGoldenCarrotPigEntity.setCustomNameVisible(this.isCustomNameVisible());
            }

            enchantedGoldenCarrotPigEntity.setPersistenceRequired();
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, enchantedGoldenCarrotPigEntity);
            serverWorld.addFreshEntity(enchantedGoldenCarrotPigEntity);
            this.remove();
        } else {
            super.thunderHit(serverWorld, lightningBoltEntity);
        }
    }
}
