package io.github.chaosawakens.common.entity.prototype.passive.animal.land;

import io.github.chaosawakens.api.animation.faal.base.ExtendedAnimationState;
import io.github.chaosawakens.common.entity.prototype.base.AnimatableAnimal;
import io.github.chaosawakens.common.entity.prototype.base.AnimatablePanickingAnimal;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.util.LootUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class LettuceChicken extends AnimatablePanickingAnimal {
    private static final EntityDataAccessor<Boolean> IS_PERCHING = SynchedEntityData.defineId(LettuceChicken.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> PERCH_TIME = SynchedEntityData.defineId(LettuceChicken.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PERCH_TIME_LIMIT = SynchedEntityData.defineId(LettuceChicken.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PERCH_COOLDOWN = SynchedEntityData.defineId(LettuceChicken.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> EGG_TIME = SynchedEntityData.defineId(LettuceChicken.class, EntityDataSerializers.INT);
    public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS); //, CAItems.LETTUCE_SEEDS.get(), CAItems.CORN_SEEDS.get(), CAItems.RADISH_SEEDS.get(), CAItems.STRAWBERRY_SEEDS.get(), CAItems.TOMATO_SEEDS.get());
    public static final String IDLE_ANIM = "Idle";
    public static final String PERCHING_ANIM = "Perching";
    public static final String FLAP_ANIM = "Flap";
    public final ExtendedAnimationState idleAnimState = wrapState(IDLE_ANIM);
    public final ExtendedAnimationState perchAnimState = wrapState(PERCHING_ANIM);
    public final ExtendedAnimationState flapAnimState = wrapState(FLAP_ANIM);

    public LettuceChicken(EntityType<? extends AnimatableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.FOLLOW_RANGE, 5)
                .add(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    public static LootTable.Builder createLootTable(Supplier<EntityType<LettuceChicken>> ownerType) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(CAItems.LETTUCE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.CHICKEN)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 4.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, LootUtil.ENTITY_ON_FIRE)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(IS_PERCHING, true);
        this.entityData.define(PERCH_TIME, 0);
        this.entityData.define(PERCH_TIME_LIMIT, random.nextInt(500, 1200));
        this.entityData.define(PERCH_COOLDOWN, 0);
        this.entityData.define(EGG_TIME, random.nextInt(2000, 4000));
    }

    public boolean isPerching() {
        return this.entityData.get(IS_PERCHING);
    }

    public void setPerching(boolean perching) {
        this.entityData.set(IS_PERCHING, perching);
    }

    public int getPerchTime() {
        return this.entityData.get(PERCH_TIME);
    }

    public void setPerchTime(int perchTime) {
        this.entityData.set(PERCH_TIME, perchTime);
    }

    public void updatePerchTime() {
        if (isPerching()) setPerchTime(getPerchTime() + 1);
        else setPerchTime(0);
    }

    public int getPerchTimeLimit() {
        return this.entityData.get(PERCH_TIME_LIMIT);
    }

    public void setPerchTimeLimit(int perchTimeLimit) {
        this.entityData.set(PERCH_TIME_LIMIT, perchTimeLimit);
    }

    public int getPerchCooldown() {
        return this.entityData.get(PERCH_COOLDOWN);
    }

    public void setPerchCooldown(int perchCooldown) {
        this.entityData.set(PERCH_COOLDOWN, perchCooldown);
    }

    public void updatePerchCooldown() {
        if (getPerchCooldown() > 0) setPerchCooldown(getPerchCooldown() - 1);
    }

    public int getEggTime() {
        return this.entityData.get(EGG_TIME);
    }

    public void setEggTime(int eggTime) {
        this.entityData.set(EGG_TIME, eggTime);
    }

    public void updateEggTime() {
        if (getEggTime() > 0) setEggTime(getEggTime() - 1);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag curTag) {
        super.readAdditionalSaveData(curTag);

        setPerching(curTag.getBoolean("Perching"));
        setPerchTime(curTag.getInt("PerchTime"));
        setPerchTimeLimit(curTag.getInt("PerchTimeLimit"));
        setPerchCooldown(curTag.getInt("PerchCooldown"));
        setEggTime(curTag.getInt("EggTime"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag curTag) {
        super.addAdditionalSaveData(curTag);

        curTag.putBoolean("Perching", isPerching());
        curTag.putInt("PerchTime", getPerchTime());
        curTag.putInt("PerchTimeLimit", getPerchTimeLimit());
        curTag.putInt("PerchCooldown", getPerchCooldown());
        curTag.putInt("EggTime", getEggTime());
    }

    @Override
    public Ingredient getTemptIngredient() {
        return FOOD_ITEMS;
    }

    @Override
    public void tickClientAnimations() {
        if (!isDeadOrDying() && !isPerching()) playAnimation(idleAnimState);
        else stopAnimation(idleAnimState);

        if (isPerching()) playAnimation(perchAnimState);
        else stopAnimation(perchAnimState);
    }

    @Override
    public void tickServerAnimations() {

    }

    @Override
    public void tickGeneralClient() {

    }

    @Override
    public void tick() {
        super.tick();

        updateEggTime();
        updatePerchTime();
        updatePerchCooldown();

        handlePerching();

        if (!onGround() && getDeltaMovement().y < 0.0D) {
            setDeltaMovement(getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }
    }

    @Override
    protected void tickDeath() {
        super.tickDeath();

        if (isPerching()) {
            setPerching(false);
            stopAnimation(perchAnimState);
        }
    }

    protected void handlePerching() {
        boolean allowPerching = !isPanicking() && !isDeadOrDying() && getPerchTime() <= getPerchTimeLimit() && getPerchCooldown() <= 0;

        if (!isPerching() && allowPerching) setPerching(random.nextDouble() <= 0.12D);
        else if (!allowPerching && isPerching()) {
            setPerching(false);
            setPerchTimeLimit(random.nextInt(500, 1200));
            setPerchCooldown(random.nextIntBetweenInclusive(350, 675));
        }

        if (isPerching()) {
            getNavigation().setSpeedModifier(0.0D);
            getNavigation().stop();

            setSpeed(0.0F);
        }
    }

    protected void layEgg(ItemLike eggItemLike) {
        if (!level().isClientSide() && isAlive() && !isBaby() && getEggTime() <= 0) {
            playSound(SoundEvents.CHICKEN_EGG, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            spawnAtLocation(eggItemLike);
            gameEvent(GameEvent.ENTITY_PLACE);

            setEggTime(random.nextIntBetweenInclusive(2000, 4000));
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose curPose, EntityDimensions entityDimensions) {
        return isBaby() ? entityDimensions.height * 0.85F : entityDimensions.height * 0.92F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.CHICKEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource lastDmgSrc) {
        return SoundEvents.CHICKEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CHICKEN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos stepPos, BlockState targetPos) {
        playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    public int getMaxHeadXRot() {
        return super.getMaxHeadXRot() * (isPerching() ? 0 : 1);
    }

    @Override
    public boolean useCustomDeathTime() {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return CAEntityTypes.LETTUCE_CHICKEN.get().create(serverLevel);
    }
}
