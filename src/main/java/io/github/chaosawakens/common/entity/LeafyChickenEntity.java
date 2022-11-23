package io.github.chaosawakens.common.entity;

import java.util.EnumSet;

import io.github.chaosawakens.api.IAnimatableEntity;
import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnMobPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LeafyChickenEntity extends ChickenEntity implements IAnimatableEntity, IAnimationTickable {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<?> controller = new AnimationController<>(this, "leafychickencontroller", animationInterval(), this::predicate);
	private static final DataParameter<Boolean> PANIC = EntityDataManager.defineId(LeafyChickenEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> SITTING = EntityDataManager.defineId(LeafyChickenEntity.class, DataSerializers.BOOLEAN);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS, CAItems.LETTUCE_SEEDS.get(), CAItems.CORN_SEEDS.get(), CAItems.RADISH_SEEDS.get(), CAItems.STRAWBERRY_SEEDS.get(), CAItems.TOMATO_SEEDS.get());
	private int sitCooldown = 40;
	
	public LeafyChickenEntity(EntityType<? extends ChickenEntity> type, World world) {
		super(type, world);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 5)
				.add(Attributes.FOLLOW_RANGE, 5)
				.add(Attributes.MOVEMENT_SPEED, 0.25F);
	}
	
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leafy_chicken.idle", true));
			return PlayState.CONTINUE;
		}
		if (!isOnGround()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leafy_chicken.falling", true));
			return PlayState.CONTINUE;
		}
		if (event.isMoving() && !getPanicking()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leafy_chicken.walk", true));
			return PlayState.CONTINUE;
		}
		if (event.isMoving() && this.getPanicking()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leafy_chicken.run", true));
			return PlayState.CONTINUE;
		}
		if (this.getSitting() && !getPanicking() && !event.isMoving() || this.getSitting() && !getPanicking() && event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leafy_chicken.sit", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
	}
	
	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && !this.isChickenJockey() && this.eggTime <= 0) {	    
			this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);	    
			this.spawnAtLocation(CAItems.LEAFY_CHICKEN_EGG.get());
			this.eggTime = this.random.nextInt(6000) + 6000;   
		}
	}
	
	public boolean getPanicking() {
		return this.entityData.get(PANIC);
	}
	
	public void setPanicking(boolean panicking) {
		this.entityData.set(PANIC, panicking);
		if (this.entityData.get(SITTING)) {
			this.entityData.set(SITTING, false);
		}
	}
	
	public boolean getSitting() {
		return this.entityData.get(SITTING);
	}
	
	public void setSitting(boolean sitting) {
		this.entityData.set(SITTING, sitting);
		if (sitting) this.getNavigation().stop();
	}
	
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PANIC, false);
		this.entityData.define(SITTING, false);
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return new SSpawnMobPacket(this);
	}
	
	@Override
	public EntityDataManager getEntityData() {
		return this.entityData;
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new LeafyChickenSitGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new LeafyChickenPanicGoal(this));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, FOOD_ITEMS));	    
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));	    
		this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));	    
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));	    
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}
	
	@Override
	public void tick() {
		super.tick();
		
//		ChaosAwakens.debug("SITINTERVAL", "[Sitting Cooldown]: " + sitCooldown);
		
		if (sitCooldown > 0) sitCooldown--;
		if (sitCooldown <= 0) sitCooldown = IUtilityHelper.randomBetween(1200, 1400);
	}
	
	@Override
	public ChickenEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
		return CAEntityTypes.LEAFY_CHICKEN.get().create(world);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	class LeafyChickenPanicGoal extends PanicGoal {
		LeafyChickenEntity chicken;

		public LeafyChickenPanicGoal(CreatureEntity entity, double speedModifier) {
			super(entity, speedModifier);
			this.chicken = (LeafyChickenEntity) entity;
		}
		
		public LeafyChickenPanicGoal(CreatureEntity entity) {
			super(entity, 1.4D);
			this.chicken = (LeafyChickenEntity) entity;
		}
		
		@Override
		public void start() {
			super.start();
			this.chicken.setPanicking(true);
		}
		
		@Override
		public void stop() {
			super.stop();
			this.chicken.setPanicking(false);
		}
	}
	
	class LeafyChickenSitGoal extends Goal {
		private final LeafyChickenEntity leafyChicken;
		private int duration;
		
		public LeafyChickenSitGoal(LeafyChickenEntity leafyChicken) {
			this.leafyChicken = leafyChicken;
			duration = IUtilityHelper.randomBetween(400, 700);
			setFlags(EnumSet.of(Flag.LOOK));
		}
		
		@Override
		public void start() {
			leafyChicken.setSitting(true);
			leafyChicken.setSpeed(0);
			leafyChicken.getNavigation().stop();
		}
		
		@Override
		public void stop() {
			leafyChicken.setSitting(false);
			leafyChicken.setSpeed((float) leafyChicken.getAttributeValue(Attributes.MOVEMENT_SPEED));
		}

		@Override
		public boolean canUse() {
			return !leafyChicken.getPanicking() && sitCooldown <= 1 && isOnGround();
		}
		
		@Override
		public boolean canContinueToUse() {
			return !leafyChicken.getPanicking() && duration > 0 && isOnGround();
		}
		
		@Override
		public void tick() {
			if (duration > 0) {
				duration--;
	//			ChaosAwakens.debug("GOAL", "[Sit Goal In Progress]: " + duration);
				leafyChicken.getNavigation().stop();
			}
		}
		
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}

	@Override
	public AnimationController<?> getController() {
		return controller;
	}

	@Override
	public int animationInterval() {
		return 3;
	}

}
