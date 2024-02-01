package io.github.chaosawakens.common.entity.base;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public abstract class AnimatableTameableAnimalEntity extends TameableEntity implements IAnimatableEntity {
	protected float lastDamageAmount;

	public AnimatableTameableAnimalEntity(EntityType<? extends TameableEntity> type, World world) {
		super(type, world);
	}

	@Override
	public abstract AnimationFactory getFactory();

	@Override
	public abstract <E extends IAnimatableEntity> WrappedAnimationController<? extends E> getMainWrappedController();

	@Override
	public abstract int animationInterval();

	@Override
	public abstract <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event);

	@Override
	public abstract <E extends IAnimatableEntity> ObjectArrayList<WrappedAnimationController<? extends E>> getWrappedControllers();

	@Override
	public abstract AgeableEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate);
	
	@Nullable
	@Override
	public abstract IAnimationBuilder getIdleAnim();
	
	@Nullable
	@Override
	public abstract IAnimationBuilder getWalkAnim();
	
	@Nullable
	@Override
	public IAnimationBuilder getSwimAnim() {
		return null;
	}

	@Nullable
	@Override
	public abstract IAnimationBuilder getDeathAnim();
	
	public abstract Ingredient getTamingStacks();
	public abstract Ingredient getTamedStacks();
	
	protected boolean isTamingStack(ItemStack stackToCheck) {
		return getTamingStacks().test(stackToCheck);
	}
	
	protected boolean isTamedStack(ItemStack stackToCheck) {
		return getTamedStacks().test(stackToCheck);
	}
	
	@Nullable
	public abstract SoundEvent getTamingSound();
	@Nullable
	public abstract SoundEvent getTamedSound();
	
	public IParticleData getTamingParticles() {
		return ParticleTypes.HEART;
	}
	
	public double getFollowRange() {
		return getAttributeValue(Attributes.FOLLOW_RANGE);
	}

	public double getAttackDamage() {
		return getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	public double getAttackSpeed() {
		return getAttributeValue(Attributes.ATTACK_SPEED);
	}

	public double getMovementSpeed() {
		return getAttributeValue(Attributes.MOVEMENT_SPEED);
	}

	public double getFlyingSpeed() {
		return getAttributeValue(Attributes.FLYING_SPEED);
	}

	public double getKnockbackResistance() {
		return getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
	}

	public double getArmor() {
		return getAttributeValue(Attributes.ARMOR);
	}

	public void setFollowRange(double newBaseValue) {
		getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(newBaseValue);;
	}

	public void setAttackDamage(double newBaseValue) {
		getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(newBaseValue);;
	}

	public void setAttackSpeed(double newBaseValue) {
		getAttribute(Attributes.ATTACK_SPEED).setBaseValue(newBaseValue);;
	}
	
	public void setAttackKnockback(double newBaseValue) {
		getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(newBaseValue);;
	}

	public void setMovementSpeed(double newBaseValue) {
		getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(newBaseValue);;
	}

	public void setFlyingSpeed(double newBaseValue) {
		getAttribute(Attributes.FLYING_SPEED).setBaseValue(newBaseValue);;
	}

	public void setKnockbackResistance(double newBaseValue) {
		getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(newBaseValue);;
	}

	public void setArmor(double newBaseValue) {
		getAttribute(Attributes.ARMOR).setBaseValue(newBaseValue);;
	}
	
	public void setArmorToughness(double newBaseValue) {
		getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(newBaseValue);;
	}
	
	@Override
	public ActionResultType mobInteract(PlayerEntity pPlayer, Hand pHand) {
		ItemStack heldStack = pPlayer.getItemInHand(pHand);
		
		if (!isTame()) {
			
		} else if (isTame()) {
			
		}
		
		return super.mobInteract(pPlayer, pHand);
	}
}
