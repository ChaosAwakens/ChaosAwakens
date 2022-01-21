package io.github.chaosawakens.common.entity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import javax.annotation.Nullable;

import io.github.chaosawakens.client.entity.render.util.EntityTextureEnum;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
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

public class BirdEntity extends ParrotEntity implements IAnimatable, IFlyingAnimal{
	private final AnimationFactory factory = new AnimationFactory(this);
	private final DataParameter<Integer> COLOR_TEXTURE = EntityDataManager.defineId(BirdEntity.class, DataSerializers.INT);
    private float flap;
    private float flapSpeed;
    @SuppressWarnings("unused")
	private float oFlapSpeed;
    @SuppressWarnings("unused")
	private float oFlap;
    private float flapping = 1.0F;

	public BirdEntity(EntityType<? extends ParrotEntity> p_i50251_1_, World p_i50251_2_) {
		super(p_i50251_1_, p_i50251_2_);
		this.moveControl = new FlyingMovementController(this, 10, true);
		this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
		this.setPathfindingMalus(PathNodeType.LAVA, -1.0F);
		this.setPathfindingMalus(PathNodeType.DAMAGE_CACTUS, -1.0F);
		this.setPathfindingMalus(PathNodeType.DANGER_OTHER, -1.0F);
		this.setPathfindingMalus(PathNodeType.WALKABLE, 1.0F);
	}
	
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.LUCK, 1.0F)
                .add(Attributes.FOLLOW_RANGE, 12);
    }
	
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.dead) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bird.idle", true));
            return PlayState.CONTINUE;
        }
        if(this.onGround && !event.isMoving()) {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bird.idle", true));
        }
        if (event.isMoving() && this.onGround) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bird.walk", true));
            return PlayState.CONTINUE;
        }
        if (this.isFlying()) {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bird.fly", true));
        	return PlayState.CONTINUE;
        }
     //   event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.idle_animation", true));
        return PlayState.CONTINUE;
    }
    
	@Override
    protected PathNavigator createNavigation(World w) {
	    FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, w);
	    flyingpathnavigator.setCanOpenDoors(false);
	    flyingpathnavigator.setCanFloat(true);
	    flyingpathnavigator.setCanPassDoors(true);
	    return flyingpathnavigator;
    }
	
	@Override
     protected float getStandingEyeHeight(Pose p, EntitySize s) {
	    return s.height * 0.6F;
     }
	
	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource dmgsrc) {
		return SoundEvents.PARROT_HURT;
	}
	
	@Override
	public boolean canMate(AnimalEntity e) {
		return false;
	}
	
	@Override
	protected float getSoundVolume() {
		return 0.2F;
	}
	
    public int getColorTextureType() {
        return this.entityData.get(COLOR_TEXTURE);
    }
    
    private void setColorTextureType(int id) {
        this.entityData.set(COLOR_TEXTURE, id);
    }
	
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COLOR_TEXTURE, 0);
    }
    
    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TextureColorType", this.getColorTextureType());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setColorTextureType(compound.getInt("TextureColorType"));
    }

    public void setTextureData(EntityTextureEnum entityTextureEnum) {
        this.setColorTextureType(entityTextureEnum.getId());
    }

	@Override
	public void registerControllers(AnimationData data) {
	  data.addAnimationController(new AnimationController<>(this, "birdcontroller", 0, this::predicate));
	}
	
	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		super.playStepSound(p_180429_1_, p_180429_2_);
	}
	
    public static boolean checkBirdSpawnRules(EntityType<ParrotEntity> p, IWorld w, SpawnReason reason, BlockPos pos, Random random) {
        BlockState blockstate = w.getBlockState(pos.below());
	    return (blockstate.is(BlockTags.LEAVES) || blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(BlockTags.LOGS) || blockstate.is(Blocks.AIR)) && w.getRawBrightness(pos, 0) > 8;
    }
    
    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float)((double)this.flapSpeed + (double)(!this.onGround && !this.isPassenger() ? 4 : -1) * 0.3D);
        this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround && this.flapping < 1.0F) {
           this.flapping = 1.0F;
        }

        this.flapping = (float)((double)this.flapping * 0.9D);
        Vector3d vector3d = this.getDeltaMovement();
        if (!this.onGround && vector3d.y < 0.0D) {
           this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
        }

        this.flap += this.flapping * 2.0F;
     }
    
   /* private void calculateWalking() {
    	
    }*/
    
    @Override
    public void aiStep() {
        super.aiStep();
        this.calculateFlapping();
    }
    
    @Override
    public boolean checkSpawnRules(IWorld p_213380_1_, SpawnReason p_213380_2_) {
    	return true;
    }
    
    @Override
    public ActionResultType mobInteract(PlayerEntity p, Hand hand) {
    	return super.mobInteract(p, hand);
    }
    
    @Override
    @Nullable
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
       return null;
    }
    
    @Override
    protected boolean makeFlySound() {
        return true;
     }

    @Override
     protected float getVoicePitch() {
        return BirdEntity.getPitch(this.random);
     }
    
    public static float getPitch(Random r) {
        return (r.nextFloat() - r.nextFloat()) * 0.25F + 1.2F;
     }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
       return new Vector3d(0.0D, (double)(0.5F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
    }
    
    @Override
    public boolean isBaby() {
        return false;
     }

    @Override
     public SoundCategory getSoundSource() {
        return SoundCategory.NEUTRAL;
     }

     @Override
     public boolean isPushable() {
        return true;
     }
    
    @Override
    public boolean causeFallDamage(float f, float f2) {
    	return false;
    }
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(0, new SwimGoal(this));
	    this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(1, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new FollowMobGoal(this, 2.0D, 4.0F, 8.0F));
	}
	
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld w, DifficultyInstance dif, SpawnReason reason, ILivingEntityData sData, CompoundNBT nbt) {
		//this.setColorTextureType(random.nextInt(3));
		//	 sData = super.finalizeSpawn(w, dif, reason, sData, nbt);
		/* if (random.nextInt() == 1) {
			 this.setColorTextureType(BirdTypes.RED.getId());
		 }
		 if (random.nextInt() == 2) {
			 this.setColorTextureType(BirdTypes.BROWN.getId());
		 }
		 if (random.nextInt() == 3) {
			this.setColorTextureType(BirdTypes.BLACK.getId()); 
		 }
		 if (random.nextInt() == 4) {
			 this.setColorTextureType(BirdTypes.BLUE.getId());
		 } else {
			 this.setColorTextureType(BirdTypes.BLACK.getId());
		 }*/
		 return super.finalizeSpawn(w, dif, reason, sData, nbt);
	}
	
	 public enum BirdTypes {
	        RED(1), BROWN(2), BLACK(3), BLUE(4);
		 
		 private static final BirdTypes[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(BirdTypes::getId)).toArray(BirdTypes[]::new);

		    private final int id;

		    BirdTypes(int id) {
		        this.id = id;
		    }

		    public int getId() {
		        return this.id;
		    }

		    public static BirdTypes byId(int id) {
		        if (id < 0 || id >= VALUES.length) {
		            id = 0;
		        }
		        return VALUES[id];
		    }
	    }
	 
	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

}
