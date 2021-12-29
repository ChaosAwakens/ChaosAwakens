package io.github.chaosawakens.common.entity;

import java.util.EnumSet;

import java.util.Random;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreakBlockGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class WoodFishEntity extends AbstractGroupFishEntity implements IAnimatable{
	private final AnimationFactory factory = new AnimationFactory(this);
	private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.defineId(AbstractFishEntity.class, DataSerializers.BOOLEAN);

	public WoodFishEntity(EntityType<? extends AbstractGroupFishEntity> woodfish, World world) {
		super(woodfish, world);
		this.noCulling = true;
		this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
		this.moveControl = new WoodFishEntity.MoveHelperController(this);
	}
	
	@Override
	protected boolean canRandomSwim() {
		return true;
	}
	
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.MOVEMENT_SPEED, 0.7D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 20);
}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "woodfishcontroller", 0, this::predicate));
	}
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wood_fish.swim", true));
            return PlayState.CONTINUE;
        }
        if (!event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wood_fish.swim", true));
            return PlayState.CONTINUE;
        }
        if(this.dead) {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wood_fish.flop", true));
        }
        if(this.isSwimming()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wood_fish.swim", true));
        }
        if(this.isDeadOrDying()) {
        	 event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wood_fish.flop", true));
        	 return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }
	
	//I'm working on it since the breakblockgoal dont even work cuz laziness lmao
	@Override
	   protected void registerGoals() {
	      super.registerGoals();
	      this.goalSelector.addGoal(0, new PanicGoal(this, 1.05D));
	      this.goalSelector.addGoal(0, new LookAtGoal(this, PlayerEntity.class, 3.0F, 3.0F));
	      this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 4.0F, 0.8D, 0.7D, EntityPredicates.NO_SPECTATORS::test));
	      this.goalSelector.addGoal(4, new WoodFishEntity.SwimGoal(this));
	      this.goalSelector.addGoal(4, new WoodFishEntity.GoToWaterGoal(this, 30.0D));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.OAK_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.BIRCH_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.SPRUCE_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.JUNGLE_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.DARK_OAK_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.ACACIA_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_OAK_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_BIRCH_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_SPRUCE_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_JUNGLE_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_DARK_OAK_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_ACACIA_LOG, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.OAK_WOOD, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.BIRCH_WOOD, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.SPRUCE_WOOD, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.JUNGLE_WOOD, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.DARK_OAK_WOOD, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.ACACIA_WOOD, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_OAK_WOOD, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_BIRCH_WOOD, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_SPRUCE_WOOD, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_JUNGLE_WOOD, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_DARK_OAK_WOOD, this, 1.0D, 16));
	      this.goalSelector.addGoal(7, new WoodFishEntity.BreakingBlockGoal(Blocks.STRIPPED_ACACIA_WOOD, this, 1.0D, 16));
	      //this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
	   }
	
	  @Override
	   protected PathNavigator createNavigation(World world) {
		      return new SwimmerPathNavigator(this, world);
		   }

	     @Override
		   public void travel(Vector3d vector) {
		      if (this.isEffectiveAi() && this.isInWater()) {
		         this.moveRelative(0.01F, vector);
		         this.move(MoverType.SELF, this.getDeltaMovement());
		         this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
		         if (this.getTarget() == null) {
		            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
		         }
		      } else {
		         super.travel(vector);
		      }

		   }
		   
			public CreatureAttribute getMobType() {
				return CreatureAttribute.WATER;
			}
			
			public float getSoundVolume() {
				return 0.9F;
			}
			
			@Override
			protected SoundEvent getSwimSound() {
				return SoundEvents.FISH_SWIM;
			}
			
			@Override
			protected SoundEvent getHurtSound(DamageSource source) {
				return SoundEvents.TROPICAL_FISH_HURT;
			}
			
			@Override
			protected SoundEvent getDeathSound() {
				return SoundEvents.TROPICAL_FISH_DEATH;
			}
			
			@Override
			public int getMaxAirSupply() {
				return 1000;
			}
			
			@Override
			protected SoundEvent getAmbientSound() {
				return SoundEvents.TROPICAL_FISH_AMBIENT;
			}
			
	/*		static class BreakBlockGoal extends MoveToBlockGoal {
				private final WoodFishEntity woodfish;
			    private final Block blockToRemove;

				public BreakBlockGoal(Block blockToBreak, WoodFishEntity woodfish, double speed, int distance) {
					super(woodfish, speed, distance);
					this.woodfish = woodfish;
					this.blockToRemove = blockToBreak;
					
				}

				@Override
				protected boolean isValidTarget(IWorldReader p_179488_1_, BlockPos p_179488_2_) {
					return false;
				}
				
			}*/
	
	   static class MoveHelperController extends MovementController {
		      private final WoodFishEntity fish;

		      MoveHelperController(WoodFishEntity woodfish) {
		         super(woodfish);
		         this.fish = woodfish;
		      }

		      public void tick() {
		         if (this.fish.isEyeInFluid(FluidTags.WATER)) {
		            this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
		         }

		         if (this.operation == MovementController.Action.MOVE_TO && !this.fish.getNavigation().isDone()) {
		            float f = (float)(this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
		            this.fish.setSpeed(MathHelper.lerp(0.125F, this.fish.getSpeed(), f));
		            double d0 = this.wantedX - this.fish.getX();
		            double d1 = this.wantedY - this.fish.getY();
		            double d2 = this.wantedZ - this.fish.getZ();
		            if (d1 != 0.0D) {
		               double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
		               this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, (double)this.fish.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
		            }

		            if (d0 != 0.0D || d2 != 0.0D) {
		               float f1 = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
		               this.fish.yRot = this.rotlerp(this.fish.yRot, f1, 90.0F);
		               this.fish.yBodyRot = this.fish.yRot;
		            }

		         } else {
		            this.fish.setSpeed(0.0F);
		         }
		      }
		   }

		   static class SwimGoal extends RandomSwimmingGoal {
		      private static WoodFishEntity fish;

		      public SwimGoal(WoodFishEntity woodfish) {
		         super(woodfish, 1.0D, 40);
		         SwimGoal.fish = woodfish;
		      }

		      public boolean canUse() {
		         return SwimGoal.fish.canRandomSwim() && super.canUse();
		      }
		   }
		   
		  @Override
		  public void aiStep() {
			   if (!this.isInWater() && this.onGround && this.verticalCollision) {
			      this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), (double)0.4F, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
			      this.onGround = false;
			      this.hasImpulse = true;
			      this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
			   }

			   super.aiStep();
	       }
		  
		  @Override
		  protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		      ItemStack itemstack = player.getItemInHand(hand);
		      if (itemstack.getItem() == Items.WATER_BUCKET && this.isAlive()) {
		         this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
		         itemstack.shrink(1);
		         ItemStack itemstack1 = this.getBucketItemStack();
		         this.saveToBucketTag(itemstack1);
		         if (!this.level.isClientSide) {
		            CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)player, itemstack1);
		         }

		         if (itemstack.isEmpty()) {
		            player.setItemInHand(hand, itemstack1);
		         } else if (!player.inventory.add(itemstack1)) {
		            player.drop(itemstack1, false);
		         }

		         this.remove();
		         return ActionResultType.sidedSuccess(this.level.isClientSide);
		      } else {
		         return super.mobInteract(player, hand);
		      }
		   }

		  @Override
		   protected void saveToBucketTag(ItemStack stack) {
		      if (this.hasCustomName()) {
		         stack.setHoverName(this.getCustomName());
		      }

		   }
		  
		  static class BreakingBlockGoal extends BreakBlockGoal {

			public BreakingBlockGoal(Block p_i48795_1_, WoodFishEntity p_i48795_2_, double p_i48795_3_, int p_i48795_5_) {
			super(p_i48795_1_, p_i48795_2_, p_i48795_3_, p_i48795_5_);
			}		
			
			@Override
			public boolean canUse() {
				return WoodFishEntity.SwimGoal.fish.canRandomSwim() && super.canUse();
			}
			  
		  }
		  
		   static class GoToWaterGoal extends Goal {
			      private final CreatureEntity mob;
			      private double wantedX;
			      private double wantedY;
			      private double wantedZ;
			      private final double speedModifier;
			      private final World level;

			      public GoToWaterGoal(CreatureEntity p_i48910_1_, double p_i48910_2_) {
			         this.mob = p_i48910_1_;
			         this.speedModifier = p_i48910_2_;
			         this.level = p_i48910_1_.level;
			         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
			      }

			      public boolean canUse() {
			         if (!this.level.isDay()) {
			            return false;
			         } else if (this.mob.isInWater()) {
			            return false;
			         } else {
			            Vector3d vector3d = this.getWaterPos();
			            if (vector3d == null) {
			               return false;
			            } else {
			               this.wantedX = vector3d.x;
			               this.wantedY = vector3d.y;
			               this.wantedZ = vector3d.z;
			               return true;
			            }
			         }
			      }

			      public boolean canContinueToUse() {
			         return !this.mob.getNavigation().isDone();
			      }

			      public void start() {
			         this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
			      }

			      @Nullable
			      private Vector3d getWaterPos() {
			         Random random = this.mob.getRandom();
			         BlockPos blockpos = this.mob.blockPosition();

			         for(int i = 0; i < 10; ++i) {
			            BlockPos blockpos1 = blockpos.offset(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
			            if (this.level.getBlockState(blockpos1).is(Blocks.WATER)) {
			               return Vector3d.atBottomCenterOf(blockpos1);
			            }
			         }

			         return null;
			      }
			   }
		  
		  public static boolean checkFishSpawnRules(EntityType<? extends AbstractFishEntity> woodfish, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
		      return world.getBlockState(pos).is(Blocks.WATER) && world.getBlockState(pos.above()).is(Blocks.WATER);
		   }
		  
		  @Override
		  public int getMaxSchoolSize() {
			  return 6;
	       }

		  @Override
		   public boolean removeWhenFarAway(double distance) {
		      return !this.fromBucket() && !this.hasCustomName();
		   }

		  @Override
		   public int getMaxSpawnClusterSize() {
		      return 8;
		   }

		  @Override
		   protected void defineSynchedData() {
		      super.defineSynchedData();
		      this.entityData.define(FROM_BUCKET, false);
		   }

		   public boolean fromBucket() {
		      return this.entityData.get(FROM_BUCKET);
		   }

		   @Override
		   public void setFromBucket(boolean frombucket) {
		      this.entityData.set(FROM_BUCKET, frombucket);
		   }

		   @Override
		   public void addAdditionalSaveData(CompoundNBT bucketnbt) {
		      super.addAdditionalSaveData(bucketnbt);
		      bucketnbt.putBoolean("FromBucket", this.fromBucket());
		   }

		   @Override
		   public void readAdditionalSaveData(CompoundNBT bucketnbt) {
		      super.readAdditionalSaveData(bucketnbt);
		      this.setFromBucket(bucketnbt.getBoolean("FromBucket"));
		   }
		  
		  @Override
		public boolean requiresCustomPersistence() {
			  return super.requiresCustomPersistence() || this.fromBucket();
		  }

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected ItemStack getBucketItemStack() {
		return new ItemStack(CAItems.WOOD_FISH_BUCKET.get());
	}

	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntitySize size) {
		 return this.isBaby() ? size.height * 0.45F : 0.6F;
	}

}
