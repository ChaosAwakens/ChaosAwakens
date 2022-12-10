package io.github.chaosawakens.common.entity.ai.pathfinding;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.MathHelper;

public class CAGroundMovementController extends MovementController {
	
	protected float maxRot = 90;

	public CAGroundMovementController(MobEntity entity, float maxRotation) {
		super(entity);
		this.maxRot = maxRotation;
	}
	
	@Override
	public void tick() {
		if (this.mob.getTarget() != null && this.mob.getNavigation().getTargetPos() == this.mob.getTarget().blockPosition()) {
			this.mob.lookAt(this.mob.getTarget(), 100, 100);
			this.mob.getLookControl().setLookAt(mob.getTarget(), 30F, 30F);
		}
        if (this.operation == CAGroundMovementController.Action.STRAFE) {
            float speed = (float)this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
            float speedModifier = (float)this.speedModifier * speed;
            float forwardMovement = this.strafeForwards;
            float rightMovement = this.strafeRight;
            float movementSqrd = MathHelper.sqrt(forwardMovement * forwardMovement + rightMovement * rightMovement);

            if (movementSqrd < 1.0F) {
                movementSqrd = 1.0F;
            }
            
            movementSqrd = speedModifier / movementSqrd;
            forwardMovement = forwardMovement * movementSqrd;
            rightMovement = rightMovement * movementSqrd;
            float yawSine = MathHelper.sin(this.mob.yRot * 0.017453292F);
            float yawCosine = MathHelper.cos(this.mob.yRot * 0.017453292F);
            float distMoveRight = forwardMovement * yawCosine - rightMovement * yawSine;
            float distMoveForward = rightMovement * yawCosine + forwardMovement * yawSine;
            PathNavigator navigator = this.mob.getNavigation();

            if (navigator != null) {
                NodeProcessor processor = navigator.getNodeEvaluator();

                if (processor != null && processor.getBlockPathType(this.mob.level, MathHelper.floor(this.mob.getX() + (double)distMoveRight), MathHelper.floor(this.mob.getY()),MathHelper.floor(this.mob.getZ() + (double)distMoveForward)) != PathNodeType.WALKABLE) {
                    this.strafeRight = 1.0F;
                    this.strafeForwards = 0.0F;
                    speedModifier = speed;
                }
            }

            this.mob.setSpeed(speedModifier);
            this.mob.setZza(this.strafeForwards);
            this.mob.setXxa(this.strafeRight);
            this.operation = CAGroundMovementController.Action.WAIT;
        } else if (this.operation == CAGroundMovementController.Action.MOVE_TO) {
            this.operation = CAGroundMovementController.Action.WAIT;
            double distX = this.wantedX - this.mob.getX();
            double distZ = this.wantedZ - this.mob.getZ();
            double distY = this.wantedY - this.mob.getY();
            double totalDist = distX * distX + distY * distY + distZ * distZ;

            //Vanilla value moment
            if (totalDist < 2.500000277905201E-7D) {
                this.mob.setZza(0.0F);
                return;
            }

            float distRotH = (float)(MathHelper.atan2(distZ, distX) * (180D / Math.PI)) - 90.0F;
            this.mob.yRot = this.rotlerp(this.mob.yRot, distRotH, maxRot);
            this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));

            if (distY > (double)this.mob.maxUpStep && distX * distX + distZ * distZ < (double)Math.max(1.0F, this.mob.getBbWidth())) {
                this.mob.getJumpControl().jump();
                this.operation = CAGroundMovementController.Action.JUMPING;
    
                //Pointless for now
/*                int time = 0;
                
                if (this.operation == CAGroundMovementController.Action.JUMPING) {
                	time++;
                	
                	if (time >= 1000) {
                        this.mob.setZza(this.strafeForwards);
                        this.mob.setXxa(this.strafeRight);
                	}
                } else {
                	if (time > 0) {
                		time --;
                	}
                }*/
            }
        } else if (this.operation == CAGroundMovementController.Action.JUMPING) {
            this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));

            if (this.mob.isOnGround()) {
                this.operation = CAGroundMovementController.Action.WAIT;
            }
        } else {
            this.mob.setZza(0.0F);            
        }	
	}
}
