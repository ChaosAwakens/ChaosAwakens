package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.api.IGrabber;
import io.github.chaosawakens.common.entity.ai.AnimatableGrabGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.registry.CASoundEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HerculesBeetleEntity extends AnimatableMonsterEntity implements IAnimatable, IGrabber {
    protected final Vector3d grabOffset = new Vector3d(0, 0.5, 2);
    private final AnimationFactory factory = new AnimationFactory(this);

    public HerculesBeetleEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 250)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 30)
                .add(Attributes.ATTACK_KNOCKBACK, 9.5D)
                .add(Attributes.FOLLOW_RANGE, 25);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
//        if (this.dead) {
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.death_animation", true));
//            return PlayState.CONTINUE;
//        }

        if (this.getAttacking()) {
//            if (this.getGrabbing(this)) {
   //             event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.walk_attack_animation", true));
   //             return PlayState.CONTINUE;
  //          }
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.attack_animation", false));
            return PlayState.CONTINUE;
        }

        if (this.getMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.walking_animation", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.idle_animation", true));
        return PlayState.CONTINUE;
    }
    
    @Override
    public boolean doHurtTarget(Entity p_70652_1_) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (p_70652_1_ instanceof LivingEntity) {
           f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)p_70652_1_).getMobType());
           f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
           p_70652_1_.setSecondsOnFire(i * 4);
        }

        boolean flag = p_70652_1_.hurt(DamageSource.mobAttack(this), f);
        if (flag) {
           if (f1 > 0.0F && p_70652_1_ instanceof LivingEntity) {
              ((LivingEntity)p_70652_1_).knockback(f1 * 2.5F, (double)MathHelper.sin(this.yRot * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.yRot * ((float)Math.PI / 180F))));
              this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
           }

           if (p_70652_1_ instanceof PlayerEntity) {
              PlayerEntity playerentity = (PlayerEntity)p_70652_1_;
              this.maybeDisableShield(playerentity, this.getMainHandItem(), playerentity.isUsingItem() ? playerentity.getUseItem() : ItemStack.EMPTY);
           }

           this.doEnchantDamageEffects(this, p_70652_1_);
           this.setLastHurtMob(p_70652_1_);
        }

        return flag;
    }
    
    private void maybeDisableShield(PlayerEntity p_233655_1_, ItemStack p_233655_2_, ItemStack p_233655_3_) {
        if (!p_233655_2_.isEmpty() && !p_233655_3_.isEmpty() && p_233655_2_.getItem() instanceof AxeItem && p_233655_3_.getItem() == Items.SHIELD) {
           float f = 0.25F + (float)EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
           if (this.random.nextFloat() < f) {
              p_233655_1_.getCooldowns().addCooldown(Items.SHIELD, 100);
              this.level.broadcastEntityEvent(p_233655_1_, (byte)30);
           }
        }

     }

    @Override
    protected void registerGoals() {
   //     this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
  //      this.goalSelector.addGoal(3, new LookAtGoal(this, IronGolemEntity.class, 24.0F));
   //     this.goalSelector.addGoal(3, new LookAtGoal(this, SnowGolemEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new AnimatableMoveToTargetGoal(this, 1.75, 8));
//		this.goalSelector.addGoal(4, new AnimatableGrabGoal<HerculesBeetleEntity>(this, 8));
        this.goalSelector.addGoal(3, new AnimatableMeleeGoal(this, 30.4, 0.20, 0.30));
//		this.goalSelector.addGoal(3, new ThrowRiderAttackGoal(this, 0.125F, false));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.6));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
    //    this.targetSelector.addGoal(4, new AnimatableMultiAttackGoal(this, new ZombieEntity(level), new AnimatableMeleeGoal(this, 30.4, 0.75, 0.85), new LookAtGoal(this, PlayerEntity.class, 24.0F), new RandomWalkingGoal(this, 1.6)));
   //     if (this.distanceTo(this.getTarget()) >= 20.0D) {
        //	this.targetSelector.addGoal(4, new AnimatableGrabGoal<HerculesBeetleEntity>(h, 8));
   //     }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "herculesbeetlecontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GRABBING, false);
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public void positionRider(Entity passenger) {
        this.positionRider(this, passenger, Entity::setPos);
    }

    public Vector3d getGrabOffset() {
        return this.grabOffset;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return CASoundEvents.HERCULES_BEETLE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CASoundEvents.HERCULES_BEETLE_DEATH.get();
    }
    
    @Override
    public void tick() {
    	super.tick();
    	
	/*	if (this.getTarget() != null && this.canSee(this.getTarget()) && this.distanceToSqr(this.getTarget()) >= 12.0F) {
			this.lookControl.setLookAt(this.getTarget(), 30.0F, 30.0F);
			this.getTarget().moveTo(this.getTarget().blockPosition(), 3.0F, 10.0F);
			if (this.navigation.isStuck()) {
				this.navigation.recomputePath(this.getTarget().blockPosition());
			}
		}
		
		if (this.getTarget() != null && this.canSee(this.getTarget()) && this.distanceToSqr(this.getTarget()) <= 12.0F) {
			this.lookControl.setLookAt(this.getTarget(), 30.0F, 30.0F);
			if (this.navigation.isStuck()) {
				this.navigation.recomputePath(this.getTarget().blockPosition());
			}			
		}*/
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }
}
