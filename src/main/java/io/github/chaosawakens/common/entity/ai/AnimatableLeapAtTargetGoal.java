package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.entity.AnimatableAnimalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.util.math.vector.Vector3d;

public class AnimatableLeapAtTargetGoal extends LeapAtTargetGoal{
    protected AnimatableAnimalEntity entity;
    private LivingEntity target;
    float yf;

	public AnimatableLeapAtTargetGoal(AnimatableAnimalEntity e, float f) {
		super(e, f);
		entity = e;
		yf = f;
	}
	
	@Override
	   public boolean canUse() {
		      if (this.entity.isVehicle()) {
		         return false;
		      } else {
		         this.target = this.entity.getTarget();
		         if (this.target == null) {
		            return false;
		         } else {
		            double d0 = this.entity.distanceToSqr(this.target);
		            if (!(d0 < 4.0D) && !(d0 > 16.0D)) {
		               if (!this.entity.isOnGround()) {
		                  return false;
		               } else {
		                  return this.entity.getRandom().nextInt(5) == 0;
		               }
		            } else {
		               return false;
		            }
		         }
		      }
		   }

	    @Override
		   public boolean canContinueToUse() {
		      return !this.entity.isOnGround();
		   }

	    @Override
		   public void start() {
		      Vector3d vector3d = this.entity.getDeltaMovement();
		      Vector3d vector3d1 = new Vector3d(this.target.getX() - this.entity.getX(), 0.0D, this.target.getZ() - this.entity.getZ());
		      if (vector3d1.lengthSqr() > 1.0E-7D) {
		         vector3d1 = vector3d1.normalize().scale(0.4D).add(vector3d.scale(0.2D));
		      }

		      this.entity.setDeltaMovement(vector3d1.x, (double)this.yf, vector3d1.z);
		   }

}
