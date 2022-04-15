package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.api.IGrabber;
import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.ForgeMod;

import java.util.EnumSet;

/**
 * @author invalid2
 */
public class AnimatableGrabGoal<G extends AnimatableMonsterEntity & IGrabber> extends AnimatableGoal {

    private final int avrgNumHits;
    private boolean isGrabbing;
    private boolean hasGrabbed;
    private int numOfHits;

    /**
     * Grabs the target entity and deals some damage
     */
    public AnimatableGrabGoal(G entity, int avrgNumHits) {
        this.entity = entity;
        this.avrgNumHits = avrgNumHits;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (RANDOM.nextInt(10) == 0) return false;

        return this.checkIfValid(this, this.entity, this.entity.getTarget());
    }

    @Override
    public boolean canContinueToUse() {
        if (RANDOM.nextInt(10) == 0) return true;

        return this.checkIfValid(this, this.entity, this.entity.getTarget());
    }

    @Override
    public void stop() {
        this.isGrabbing = false;
        ((IGrabber) this.entity).setGrabbing(this.entity, false);
        this.hasGrabbed = false;
    }

    @Override
    public void tick() {
        this.baseTick();
        LivingEntity target = this.entity.getTarget();
        if (target == null || this.hasGrabbed) return;

        this.grabTarget(target);

        target.rideTick();
    }

    protected void grabTarget(LivingEntity target) {
        if (this.isGrabbing) {
            this.attackWhileGrab(target);
            return;
        }
        target.startRiding(entity);
        this.entity.setAttacking(true);
        this.isGrabbing = true;
        ((IGrabber) this.entity).setGrabbing(this.entity, true);
    }

    protected void attackWhileGrab(LivingEntity target) {
        if (RANDOM.nextInt(this.avrgNumHits) < this.avrgNumHits - this.numOfHits) {
            this.entity.doHurtTarget(target);
            this.numOfHits++;
            return;
        }
        this.releaseTarget(target);
    }

    protected void releaseTarget(LivingEntity target) {
        this.hasGrabbed = true;
        this.isGrabbing = false;
        this.entity.setAttacking(false);
        ((IGrabber) this.entity).setGrabbing(this.entity, false);

    }
    
    protected static double getGrabAttackReachSq(AnimatableMonsterEntity attacker, LivingEntity target) {
        return attacker.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
    }

    private boolean checkIfValid(AnimatableGrabGoal<?> goal, AnimatableMonsterEntity attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                attacker.setAttacking(false);
                return false;
            }
            double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (distance <= getGrabAttackReachSq(attacker, target)) return true;
        }
        attacker.setAttacking(false);
        return false;
    }
}
