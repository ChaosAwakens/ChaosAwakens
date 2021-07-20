package io.github.chaosawakens.common.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreakBlockGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class BeaverEntity extends AnimalEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public BeaverEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.beaver.walking_animation", true));
            return PlayState.CONTINUE;
        }
        if (!event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.beaver.idle_animation", true));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.6));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.OAK_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.BIRCH_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.SPRUCE_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.JUNGLE_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.DARK_OAK_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.ACACIA_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_OAK_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_BIRCH_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_SPRUCE_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_JUNGLE_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_DARK_OAK_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_ACACIA_LOG, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.OAK_WOOD, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.BIRCH_WOOD, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.SPRUCE_WOOD, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.JUNGLE_WOOD, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.DARK_OAK_WOOD, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.ACACIA_WOOD, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_OAK_WOOD, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_BIRCH_WOOD, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_SPRUCE_WOOD, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_JUNGLE_WOOD, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_DARK_OAK_WOOD, this, 1.0D, 16));
        this.goalSelector.addGoal(7, new BreakBlockGoal(Blocks.STRIPPED_ACACIA_WOOD, this, 1.0D, 16));
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.FOLLOW_RANGE, 8);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "beavercontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
        return null;
    }
}
