package io.github.chaosawakens.common.entity.prototype.passive.animal.land;

import io.github.chaosawakens.api.animation.faal.base.ExtendedAnimationState;
import io.github.chaosawakens.common.entity.prototype.base.AnimatableAnimal;
import io.github.chaosawakens.common.entity.prototype.base.AnimatablePanickingAnimal;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.util.LootUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

public class AppleCow extends AnimatablePanickingAnimal implements Shearable {
    private static final EntityDataAccessor<Boolean> IS_SHEARED = SynchedEntityData.defineId(AppleCow.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SHEARED_TIME = SynchedEntityData.defineId(AppleCow.class, EntityDataSerializers.INT);
    public static final String IDLE_ANIM = "Idle";
    public final ExtendedAnimationState idleAnimState = wrapState(IDLE_ANIM);
    public static final int TICKS_UNTIL_REGROWN = 12000;

    public AppleCow(EntityType<? extends AnimatableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.FOLLOW_RANGE, 10);
    }

    public static LootTable.Builder createStandardLootTable(Supplier<EntityType<AppleCow>> ownerType) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.BEEF)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(SmeltItemFunction.smelted()
                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, LootUtil.ENTITY_ON_FIRE)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 1.0F)))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.LEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 1.0F)))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.APPLE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())));
    }

    public static LootTable.Builder createGoldenLootTable(Supplier<EntityType<AppleCow>> ownerType) {
        return createStandardLootTable(ownerType).withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())));
    }

    public static LootTable.Builder createEnchantedLootTable(Supplier<EntityType<AppleCow>> ownerType) {
        return createGoldenLootTable(ownerType).withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())));
    }

    @Override
    public void tickClientAnimations() {
        if (!isDeadOrDying()) playAnimation(idleAnimState);
        else stopAnimation(idleAnimState);
    }

    @Override
    public void tickServerAnimations() {

    }

    @Override
    public void tickGeneralClient() {

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(IS_SHEARED, false);
        this.entityData.define(SHEARED_TIME, 0);
    }

    public boolean isSheared() {
        return this.entityData.get(IS_SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.entityData.set(IS_SHEARED, sheared);
    }

    public int getShearedTime() {
        return this.entityData.get(SHEARED_TIME);
    }

    public void setShearedTime(int shearedTime) {
        this.entityData.set(SHEARED_TIME, shearedTime);
    }

    public void updateShearedTime() {
        if (isSheared()) setShearedTime(getShearedTime() + 1);
        if (getShearedTime() >= TICKS_UNTIL_REGROWN) {
            setSheared(false);
            resetShearedTime();

            for (int i = 0; i < 25; i++) {
                level().addParticle(ParticleTypes.HAPPY_VILLAGER, getX(random.nextDouble() * 1.42D), getY(random.nextDouble()), getZ(random.nextDouble()), 0.0D, 0.108D, 0.0D);
            }
        }
    }

    public void resetShearedTime() {
        setShearedTime(0);
    }

    public boolean canShear() {
        return isAlive() && !isBaby() && !isSheared();
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
    protected void playStepSound(BlockPos stepPos, BlockState stepBlock) {
        playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return getType().getDescriptionId().contains("enchanted")
                ? CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get().create(serverLevel)
                : getType().getDescriptionId().contains("golden")
                ? CAEntityTypes.GOLDEN_APPLE_COW.get().create(serverLevel)
                : CAEntityTypes.APPLE_COW.get().create(serverLevel);
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return isBaby() ? pSize.height * 0.95F : 1.3F;
    }

    @Override
    public InteractionResult mobInteract(Player interactingPlayer, InteractionHand curHand) {
        ItemStack heldStack = interactingPlayer.getItemInHand(curHand);

        if (heldStack.is(Items.SHEARS) && canShear()) {
            shear(SoundSource.PLAYERS);

            gameEvent(GameEvent.SHEAR);
            heldStack.hurtAndBreak(1, interactingPlayer, (ownerPlayer) -> ownerPlayer.broadcastBreakEvent(curHand)); //TODO Maybe a common shearable entity class/interface or smth

            return InteractionResult.sidedSuccess(level().isClientSide);
        } else return super.mobInteract(interactingPlayer, curHand);
    }

    @Override
    public void tick() {
        super.tick();

        updateShearedTime();
    }

    @Override
    public boolean useCustomDeathTime() {
        return false;
    }

    @Override
    public Ingredient getTemptIngredient() {
        return Ingredient.of(Items.WHEAT);
    }

    @Override
    public void shear(SoundSource soundSource) {
        if (soundSource != SoundSource.BLOCKS) level().playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (level() instanceof ServerLevel curServerLevel) curServerLevel.sendParticles(ParticleTypes.EXPLOSION, getX(), getY(0.5D), getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);

        setSheared(true);

        if (getType().getDescriptionId().contains("enchanted")) level().addFreshEntity(new ItemEntity(level(), getX(), getY(1.0D), getZ(), Items.ENCHANTED_GOLDEN_APPLE.getDefaultInstance()));
        else if (getType().getDescriptionId().contains("golden")) {
            for (int i = 0; i < random.nextInt(1, 3); i++) level().addFreshEntity(new ItemEntity(level(), getX(), getY(1.0D), getZ(), Items.GOLDEN_APPLE.getDefaultInstance()));
        } else {
            for (int i = 0; i < random.nextInt(3, 7); i++) level().addFreshEntity(new ItemEntity(level(), getX(), getY(1.0D), getZ(), Items.APPLE.getDefaultInstance()));
        }
    }

    @Override
    public boolean readyForShearing() {
        return canShear();
    }
}
