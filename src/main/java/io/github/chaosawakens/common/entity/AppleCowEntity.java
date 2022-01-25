package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.client.entity.render.util.EntityTextureEnum;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class AppleCowEntity extends AnimalEntity {
    private static final DataParameter<Integer> TEXTURE_TYPE = EntityDataManager.defineId(AppleCowEntity.class, DataSerializers.INT);

    public AppleCowEntity(EntityType<? extends AppleCowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.FOLLOW_RANGE, 10);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
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

    @Override
    public ActionResultType mobInteract(PlayerEntity playerIn, Hand hand) {
        ItemStack itemstack = playerIn.getItemInHand(hand);
        if (itemstack.getItem() == Items.BUCKET && !this.isBaby()) {
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
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
        AppleCowEntity entity = CAEntityTypes.APPLE_COW.get().create(world);

        if (this.isHalloweenObtained()) {
            assert entity != null;
            entity.setTextureType(EntityTextureEnum.HALLOWEEN.getId());
        } else {
            assert entity != null;
            entity.setTextureType(((AppleCowEntity) ageable).getTextureType());
        }

        return entity;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTURE_TYPE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TextureType", this.getTextureType());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setTextureType(compound.getInt("TextureType"));
    }

    public void setTextureData(EntityTextureEnum entityTextureEnum) {
        this.setTextureType(entityTextureEnum.getId());
    }

    public boolean isHalloweenObtained() {
        LocalDate localdate = LocalDate.now();
        int month = localdate.get(ChronoField.MONTH_OF_YEAR);

        return (month == 10 && this.random.nextFloat() < 0.05F && CAConfig.COMMON.holidayTextures.get());
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficulty, reason, spawnDataIn, dataTag);
        if (this.isHalloweenObtained()) {
            this.setTextureType(EntityTextureEnum.HALLOWEEN.getId());
        } else {
            this.setTextureType(EntityTextureEnum.DEFAULT.getId());
        }

        return super.finalizeSpawn(worldIn, difficulty, reason, spawnDataIn, dataTag);
    }

    private void setTextureType(int id) {
        this.entityData.set(TEXTURE_TYPE, id);
    }

    public int getTextureType() {
        return this.entityData.get(TEXTURE_TYPE);
    }

    public void thunderHit(ServerWorld serverWorld, LightningBoltEntity lightningBoltEntity) {
        if (net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, CAEntityTypes.CARROT_PIG.get(), (timer) -> {})) {
            CarrotPigEntity carrotPigEntity = CAEntityTypes.CARROT_PIG.get().create(serverWorld);
            assert carrotPigEntity != null;
            carrotPigEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
            carrotPigEntity.setNoAi(this.isNoAi());
            carrotPigEntity.setBaby(this.isBaby());
            if (this.hasCustomName()) {
                carrotPigEntity.setCustomName(this.getCustomName());
                carrotPigEntity.setCustomNameVisible(this.isCustomNameVisible());
            }

            carrotPigEntity.setPersistenceRequired();
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, carrotPigEntity);
            serverWorld.addFreshEntity(carrotPigEntity);
            this.remove();
        } else {
            super.thunderHit(serverWorld, lightningBoltEntity);
        }
    }
}
