package io.github.chaosawakens.common.entity.prototype.passive.animal.land;

import io.github.chaosawakens.api.animation.faal.base.ExtendedAnimationState;
import io.github.chaosawakens.common.entity.prototype.base.AnimatableAnimal;
import io.github.chaosawakens.common.entity.prototype.base.AnimatablePanickingAnimal;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class StinkBug extends AnimatablePanickingAnimal {
    private static final EntityDataAccessor<String> TYPE = SynchedEntityData.defineId(StinkBug.class, EntityDataSerializers.STRING);
    public static final AttributeModifier PANICKING_SPEED_MODIFIER = new AttributeModifier(PANIC_SPEED_MOD_UUID, "StinkBugPanicSpeedModifier", 1.11D, AttributeModifier.Operation.MULTIPLY_BASE);
    public static final String IDLE_ANIM = "Idle";
    public static final Ingredient TEMPT_ITEMS = Ingredient.of(Items.POISONOUS_POTATO);
    public final ExtendedAnimationState idleAnimState = wrapState(IDLE_ANIM);

    public StinkBug(EntityType<? extends AnimatableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.FOLLOW_RANGE, 8);
    }

    public static LootTable.Builder createLootTable(Supplier<EntityType<StinkBug>> ownerType) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(CAItems.DEAD_STINK_BUG.get())));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(TYPE, StinkBugType.GREEN.name());
    }

    public StinkBugType getStinkBugType() {
        return StinkBugType.fromString(this.entityData.get(TYPE));
    }

    public void setStinkBugType(StinkBugType type) {
        this.entityData.set(TYPE, type.name());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag curTag) {
        super.readAdditionalSaveData(curTag);

        setStinkBugType(Optional.ofNullable(StinkBugType.fromString(curTag.getString("Type"))).orElse(StinkBugType.GREEN));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag curTag) {
        super.addAdditionalSaveData(curTag);

        curTag.putString("Type", getStinkBugType().name());
    }

    @Override
    public @Nullable AttributeModifier getPanicSpeedModifier() {
        return PANICKING_SPEED_MODIFIER;
    }

    @Override
    public Ingredient getTemptIngredient() {
        return TEMPT_ITEMS;
    }

    @Override
    protected void actuallyHurt(DamageSource lastSrc, float damage) {
        super.actuallyHurt(lastSrc, damage);

        if (lastSrc.getEntity() instanceof LivingEntity livingSource) livingSource.addEffect(new MobEffectInstance(MobEffects.CONFUSION, random.nextInt(60, 150)));
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
    public boolean useCustomDeathTime() {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        StinkBug resultBug = CAEntityTypes.STINK_BUG.get().create(serverLevel);
        StinkBugType resultType = getStinkBugType();

        if (ageableMob instanceof StinkBug otherStinkBugParent) resultType = random.nextBoolean() ? otherStinkBugParent.getStinkBugType() : resultType;
        if (resultBug != null) resultBug.setStinkBugType(resultType);

        return resultBug;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(ServerLevelAccessor curLevel, DifficultyInstance curDifficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag spawnTag) {
        StinkBugType selectedType = Util.getRandom(StinkBugType.values(), random);

        groupData = groupData instanceof StinkBugData stinkBugData ? stinkBugData : new StinkBugData(selectedType);

        setStinkBugType(groupData instanceof StinkBugData stinkBugData ? stinkBugData.getStinkBugType() : selectedType);

        return super.finalizeSpawn(curLevel, curDifficulty, spawnType, groupData, spawnTag);
    }

    public static class StinkBugData extends AgeableMobGroupData {
        private final StinkBugType stinkBugType;

        private StinkBugData(StinkBugType stinkBugType) {
            super(true);
            this.stinkBugType = stinkBugType;
        }

        public StinkBugType getStinkBugType() {
            return stinkBugType;
        }
    }

    public enum StinkBugType {
        GREEN,
        BLACK,
        SCARLET,
        ASH,
        PURPLE;

        @Nullable
        public static StinkBugType fromString(String string) {
            for (StinkBugType type : StinkBugType.values()) {
                if (type.name().equals(string)) {
                    return type;
                }
            }

            return null;
        }
    }
}
