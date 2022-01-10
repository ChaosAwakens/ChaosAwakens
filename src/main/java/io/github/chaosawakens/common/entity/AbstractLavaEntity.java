package io.github.chaosawakens.common.entity;

import java.util.Random;

import javax.annotation.Nonnull;

import io.github.chaosawakens.common.entity.ai.LavaSwimmingNavigator;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
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

public abstract class AbstractLavaEntity extends LavaMobEntity{

	private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.defineId(AbstractLavaEntity.class, DataSerializers.BOOLEAN);
	
	
	public AbstractLavaEntity(EntityType<? extends AbstractLavaEntity> p_i48565_1_, World p_i48565_2_) {
		super(p_i48565_1_, p_i48565_2_);
		this.moveControl = new MoveHelperController(this);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED,1.0D)
                .add(Attributes.ATTACK_SPEED, 0.30D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.35D)
                .add(Attributes.FOLLOW_RANGE, 10.0D);
    }
	
	@Override
	public void checkDespawn() {
		if (!this.hasCustomName() && !this.fromBucket()) {
			this.remove();
		}
		super.checkDespawn();
	}
	
/*	@Override
	public boolean checkSpawnRules(IWorld p_213380_1_, SpawnReason p_213380_2_) {
		return super.checkSpawnRules(p_213380_1_, p_213380_2_);
	}*/
	
	public static boolean checkLavaMobSpawnRules(IWorld world, SpawnReason reason, BlockPos pos, Random random) {
		return world.getBlockState(pos).getBlock() == Blocks.LAVA && world.getBlockState(pos.above()).getBlock() == Blocks.LAVA;
	}
	
	@Override
	public boolean fireImmune() {
		return true;
	}
	
	@Override
	public int getMaxSpawnClusterSize() {
		return 6;
	}
	
	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(FROM_BUCKET, false);
	}
	
	@Override
	public void onSyncedDataUpdated(net.minecraft.network.datasync.DataParameter<?> p_184206_1_) {
		
	}
	
/*	@Override
	public EntityDataManager getEntityData() {
		return this.getEntityData();
	}*/
	
	private boolean fromBucket() {
		return this.getEntityData().get(FROM_BUCKET);
	}
	
	public void setFromBucket(boolean fromBucket) {
		this.getEntityData().set(FROM_BUCKET, fromBucket);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		this.setFromBucket(nbt.getBoolean("FromBucket"));
	}
	
	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putBoolean("FromBucket", this.fromBucket());
	}
	
    protected void saveToBucketTag(ItemStack bucket) {
	    if (this.hasCustomName()) {
	       bucket.setHoverName(this.getCustomName());
		   }
	  }
    
    @Override
    protected PathNavigator createNavigation(World world) {
    	return new LavaSwimmingNavigator(this, world);
    }
	
	protected abstract SoundEvent getFlopSound();
	
	protected abstract ItemStack getBucketItemStack();
	
	@Override
	public boolean requiresCustomPersistence() {
	     return super.requiresCustomPersistence() || this.fromBucket();
	}
	
	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
	     return !this.fromBucket() && !this.hasCustomName();
	}
	
    @Override
    public boolean hurt(DamageSource dmgsrc, float power) {
    	if (this.isInWaterOrRain()) {
    		this.hurt(dmgsrc, power);
    	}
    	return super.hurt(dmgsrc, power);
    }
	
	@Override
	public void travel(@Nonnull Vector3d vector) {
        if (this.isEffectiveAi() && this.isInLava()) {
            this.moveRelative(this.getSpeed(), vector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(vector);
      }
  }
	
	@Override
	public void baseTick() {
	    if (!this.isInLava() && this.onGround && this.verticalCollision) {
	        this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F,
	            (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
	        this.onGround = false;
	        this.jumping = true;
	        this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
	      }
		super.baseTick();
	}
	
	@Override
	protected void registerGoals() {
	    super.registerGoals();
	    this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
	    this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PiglinEntity.class, 8.0F, 1.6D, 1.4D, EntityPredicates.NO_SPECTATORS::test));
	    this.goalSelector.addGoal(4, new AbstractLavaEntity.SwimGoal(this));
	}
	
	@Override
	protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
	      if (itemstack.getItem() == Items.LAVA_BUCKET && this.isAlive()) {
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
	
	  protected boolean canRandomSwim() {
		    return true;
	}
	
	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.FISH_SWIM;
	}
	
	  static class SwimGoal extends RandomSwimmingGoal {

		    private final AbstractLavaEntity fish;

		    public SwimGoal(AbstractLavaEntity fish) {
		      super(fish, 1.0D, 40);
		      this.fish = fish;
		    }

		    @Override
		    public boolean canUse() {
		      return this.fish.canRandomSwim() && super.canUse();
		    }
	  }
	  
	  static class MoveHelperController extends MovementController {

		    private final AbstractLavaEntity fish;

		    MoveHelperController(AbstractLavaEntity fish) {
		      super(fish);
		      this.fish = fish;
		    }
		    
		    public void tick() {
		       if (this.fish.isEyeInFluid(FluidTags.LAVA)) {
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
	  
}
