package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.entity.ai.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.registry.CAStructures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class EntEntity extends AnimatableMonsterEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public EntEntity(EntityType<? extends AnimatableMonsterEntity> type, World worldIn, Types entType) {
        super(type, worldIn);
        this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 150)
                .add(Attributes.ARMOR, 3)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 25)
                .add(Attributes.ATTACK_KNOCKBACK, 3.5D)
                .add(Attributes.FOLLOW_RANGE, 24);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.dead) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.death_animation", true));
            return PlayState.CONTINUE;
        }
        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.attacking_animation", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.walking_animation", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.idle_animation", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, IronGolemEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, SnowGolemEntity.class, 24.0F));
        this.goalSelector.addGoal(2, new AnimatableMoveToTargetGoal(this, 1.6, 8));
        this.goalSelector.addGoal(2, new AnimatableMeleeGoal(this, 48.3, 0.7, 0.8));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag)
    {
        // Only allow the ENT to wander when spawned outside the tree
        if (worldIn instanceof ServerWorld)
        {
            BlockPos pos = this.getOnPos();
            StructureManager strucManager = worldIn.getLevel().structureFeatureManager();

            boolean inDungeon = strucManager.getStructureAt(pos, false, CAStructures.ACACIA_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.BIRCH_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.CRIMSON_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.DARK_OAK_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.JUNGLE_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.OAK_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.SPRUCE_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.WARPED_ENT_TREE.get()).isValid();
            if (!inDungeon || reason != SpawnReason.STRUCTURE)
            {
                this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 1.6));
            }
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "entcontroller", 0, this::predicate));
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public enum Types {
        OAK("oak"), ACACIA("acacia"), DARK_OAK("dark_oak"), JUNGLE("jungle"), SPRUCE("spruce"), BIRCH("birch"),
        CRIMSON("crimson"), WARPED("warped");

        private final String name;

        Types(String name) {
            this.name = name;
        }

        public String getNameString() {
            return this.name;
        }
    }
}
