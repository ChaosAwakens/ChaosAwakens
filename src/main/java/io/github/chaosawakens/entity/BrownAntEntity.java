package io.github.chaosawakens.entity;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.ModDimensions;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.omg.PortableInterceptor.SUCCESSFUL;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BrownAntEntity extends CreatureEntity implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    public BrownAntEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreFrustumCheck = true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk.ant.model.animation", true));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("stand.ant.model.animation", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0F, false));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.6));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.registerAttributes()
                .createMutableAttribute(Attributes.MAX_HEALTH, 2D)
                .createMutableAttribute(Attributes.ARMOR, 0D)
                .createMutableAttribute(Attributes.ATTACK_SPEED, 4D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.1D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16D);
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<BrownAntEntity>(this, "antcontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

}
