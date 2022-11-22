package io.github.chaosawakens.common.entity;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.entity.robo.RoboEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GazelleEntity extends AnimatableAnimalEntity {
	private AnimationFactory factory = new AnimationFactory(this);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WHEAT);
	public static final DataParameter<Integer> DATA_TYPE_ID = EntityDataManager.defineId(GazelleEntity.class, DataSerializers.INT);
	public static final DataParameter<Boolean> IS_RUNNING = EntityDataManager.defineId(GazelleEntity.class, DataSerializers.BOOLEAN);
	private final AnimationController<?> controller = new AnimationController<IAnimatable>(this, "gazellecontroller", animationInterval(), this::predicate);
	private boolean sprinting;
	private EatGrassGoal eatGrassGoal;

	public GazelleEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 12)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.FOLLOW_RANGE, 14);
	}
	
	@Override
	public int animationInterval() {
		return 2;
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gazelle.walking", true));
			return PlayState.CONTINUE;
		}

		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gazelle.idle", true));
			return PlayState.CONTINUE;
		}

		if (event.isMoving() && this.isSprinting() || this.getSpeed() >= 0.21D) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gazelle.running", true));
			return PlayState.CONTINUE;
		}
		
		if (this.isEatingGrass()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.gazelle.grazing", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}
	
	@Override
	public AnimationController<?> getController() {
		return controller;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
	
	private boolean isEatingGrass() {
		return eatGrassGoal.getEatAnimationTick() > 0;
	}

	@Override
	protected void registerGoals() {
	//	this.goalSelector.addGoal(1, new HerdPanicGoal(this, 1.2D, 15));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
		this.goalSelector.addGoal(2, new EatGrassGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.0D));
		this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 1.0F));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0D, 1));
		this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, RoboEntity.class, 10, 0.0D, 2.0D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0));
		this.goalSelector.addGoal(3, new TemptGoal(this, 0.2D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(3, new TemptGoal(this, 0.2D, false, FOOD_ITEMS));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
	}
	
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("GazelleType", this.getGazelleType());
        nbt.putBoolean("GazelleRunning", this.getRunning());
    }

    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.setGazelleType(nbt.getInt("GazelleType"));
        this.setRunning(nbt.getBoolean("GazelleRunning"));
    }
	
    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }
    
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TYPE_ID, 0);
        this.entityData.define(IS_RUNNING, false);
    }
    
    protected boolean getRunning() {
    	return this.entityData.get(IS_RUNNING);
    }
    
    protected void setRunning(boolean condition) {
    	this.entityData.set(IS_RUNNING, condition);
    }

	public int getGazelleType() {
		return MathHelper.clamp(this.entityData.get(DATA_TYPE_ID), 0, 5);
	}

    public void setGazelleType(int type) {
        this.entityData.set(DATA_TYPE_ID, type);
    }
    
    @Override
    public void tick() {
    	super.tick();
    	
    	if (this.level.isClientSide) return;
    	
    	this.setRunning(this.getLastHurtByMob() != null && this.getLastHurtByMobTimestamp() <= 500);
    	if (getRunning() && !this.sprinting) {
    		this.setSprinting(true);
    		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3);
    	}
    	
    	if (!getRunning() && this.sprinting) {
    		this.setSprinting(false);
    		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25);
    	}
    }

	@Override
	public GazelleEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
		GazelleEntity entity = CAEntityTypes.GAZELLE.get().create(world);

		assert entity != null;
		entity.setGazelleType(((GazelleEntity) mate).getGazelleType());

		return entity;
	}

	static class GazelleData extends AgeableEntity.AgeableData {
		public final int gazelletype;

		private GazelleData(int gazelletype) {
			super(true);
			this.gazelletype = gazelletype;
		}
	}

	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
		int i = this.getRandomGazelleType(world);
		if (entityData instanceof GazelleData) i = ((GazelleData) entityData).gazelletype;
		else entityData = new GazelleData(i);
		this.setGazelleType(i);
		return super.finalizeSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
	}

	@SuppressWarnings("unused")
	private int getRandomGazelleType(IWorld world) {
		Biome biome = world.getBiome(this.blockPosition());
        int i = this.random.nextInt(5);
        return i;
    }
    
    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }
    
    @Override
    public float getStandingEyeHeight(Pose pose, EntitySize size) {
    	return this.isBaby() ? size.height * 0.75F : 1.1F;
    }

	@Override
	public int tickTimer() {
		return tickCount;
	}
}
