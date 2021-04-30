package io.github.chaosawakens.entity;

import io.github.chaosawakens.registry.ModDimensions;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RainbowAntEntity extends CreatureEntity implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    public RainbowAntEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreFrustumCheck = true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk.ant.model.animation", true));
            return PlayState.CONTINUE;
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("stand.ant.model.animation", true));
            return PlayState.CONTINUE;
        }
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
        data.addAnimationController(new AnimationController<RainbowAntEntity>(this, "antcontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        ItemStack itemstack = playerIn.getHeldItem(hand);
        if (itemstack.getItem() == null || itemstack.getItem() == Items.AIR) {
            if (!this.world.isRemote && this.world instanceof ServerWorld) {
                int i = this.getMaxInPortalTime();
                ServerWorld serverworld = (ServerWorld)this.world;
                MinecraftServer minecraftserver = serverworld.getServer();
                RegistryKey<World> worldReg = this.world.getDimensionKey() == ModDimensions.VILLAGE_MANIA? World.OVERWORLD : ModDimensions.VILLAGE_MANIA;
                ServerWorld serverWord1 = minecraftserver.getWorld(worldReg);
                if (serverWord1 != null && minecraftserver.getAllowNether()) {
                    this.changeDimension(serverWord1);
                    this.world.getProfiler().endSection();
                    return ActionResultType.PASS;
                }
                return ActionResultType.CONSUME;
            }
        }
        return super.getEntityInteractionResult(playerIn, hand);
    }

}
