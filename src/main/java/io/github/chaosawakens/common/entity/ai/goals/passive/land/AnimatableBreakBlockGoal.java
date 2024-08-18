package io.github.chaosawakens.common.entity.ai.goals.passive.land;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Supplier;

public class AnimatableBreakBlockGoal extends Goal {
    protected final IAnimatableEntity animatableOwner;
    protected final ITag.INamedTag<Block> targetBlockTag;
    protected final int probability;
    protected final double searchRadius;
    protected final double distThreshold;
    protected final Supplier<IAnimationBuilder> breakAnim;
    protected final double animActionStartTick;
    protected final double animActionTick;
    @Nullable
    protected BlockPos targetPos;
    protected boolean hasReachedTarget = false;

    public AnimatableBreakBlockGoal(IAnimatableEntity animatableOwner, ITag.INamedTag<Block> targetBlockTag, int probability, double searchRadius, double distThreshold, Supplier<IAnimationBuilder> breakAnim, double animActionStartTick, double animActionTick) {
        this.animatableOwner = animatableOwner;
        this.targetBlockTag = targetBlockTag;
        this.probability = probability;
        this.searchRadius = searchRadius;
        this.distThreshold = distThreshold;
        this.breakAnim = breakAnim;
        this.animActionStartTick = animActionStartTick;
        this.animActionTick = animActionTick;
        setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (animatableOwner.tickTimer() % 60 == 0) findValidPos(targetBlockTag);

        return ObjectUtil.performNullityChecks(false, animatableOwner, breakAnim, breakAnim.get()) && ((MobEntity) animatableOwner).getRandom().nextInt(Math.min(1, probability)) == 0
                && !breakAnim.get().isPlaying() && targetPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return ObjectUtil.performNullityChecks(false, animatableOwner, targetPos, breakAnim, breakAnim.get())
                && !breakAnim.get().hasAnimationFinished() && hasReachedTarget;
    }

    @Override
    public void start() {
        MobEntity livingOwner = (MobEntity) animatableOwner;

        if (targetPos == null) return; // Shouldn't ever happen, but with the stupid reach NPE crashes you never know...

        this.hasReachedTarget = targetPos != null && livingOwner.level.getBlockState(targetPos).is(targetBlockTag) && livingOwner.distanceToSqr(targetPos.getX(), targetPos.getY(), targetPos.getZ()) <= distThreshold * distThreshold && !livingOwner.isDeadOrDying();

        if (hasReachedTarget) {
            animatableOwner.playAnimation(breakAnim.get(), true);
            livingOwner.getNavigation().stop();
            livingOwner.getLookControl().setLookAt(new Vector3d(targetPos.getX(), targetPos.getY(), targetPos.getZ()));
        } else if (targetPos != null) livingOwner.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.15D);
    }

    @Override
    public void stop() {
        this.hasReachedTarget = false;
        this.targetPos = null;
    }

    @Override
    public boolean isInterruptable() {
        return ((MobEntity) animatableOwner).isDeadOrDying() || targetPos == null;
    }

    @Override
    public void tick() {
        MobEntity livingOwner = (MobEntity) animatableOwner;

        if (targetPos == null) return;

        this.hasReachedTarget = targetPos != null && livingOwner.level.getBlockState(targetPos).is(targetBlockTag) && livingOwner.distanceToSqr(targetPos.getX(), targetPos.getY(), targetPos.getZ()) <= distThreshold * distThreshold && !livingOwner.isDeadOrDying();

        if (livingOwner.tickCount % 100 == 0 && (!hasReachedTarget || livingOwner.getNavigation().isStuck())) findValidPos(targetBlockTag);

        if (hasReachedTarget) {
            livingOwner.getNavigation().stop();

            animatableOwner.playAnimation(breakAnim.get(), false);
        } else if (targetPos != null && livingOwner.tickCount % 100 == 0) livingOwner.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.15D);

        if (hasReachedTarget && breakAnim.get().getWrappedAnimProgress() >= animActionStartTick) {
            CAScreenShakeEntity.shakeScreen(livingOwner.level, livingOwner.position(), 10.0F, (float) (breakAnim.get().getWrappedAnimProgress() / 100.0D) / 20.0F, 5, 20);
            livingOwner.getLookControl().setLookAt(new Vector3d(targetPos.getX(), targetPos.getY(), targetPos.getZ()));

            if (livingOwner.level instanceof ServerWorld){
                ((ServerWorld) livingOwner.level).sendParticles(new BlockParticleData(ParticleTypes.BLOCK, livingOwner.level.getBlockState(targetPos)), targetPos.getX() + 0.5D, targetPos.getY() + 0.5D, targetPos.getZ() + 0.5D, livingOwner.getRandom().nextInt(10), 0, 0, 0, 1);
                livingOwner.level.destroyBlockProgress(livingOwner.getId(), targetPos, (int) MathUtil.normalizeValues(breakAnim.get().getWrappedAnimProgress() * 25, animActionStartTick, animActionTick, 0, 10));
            }

            if (MathUtil.isBetween(breakAnim.get().getWrappedAnimProgress(), animActionTick - 3, animActionTick)) {
                ((ServerWorld) livingOwner.level).sendParticles(new BlockParticleData(ParticleTypes.BLOCK, livingOwner.level.getBlockState(targetPos)), targetPos.getX() + 0.5D, targetPos.getY() + 0.5D, targetPos.getZ() + 0.5D, livingOwner.getRandom().nextInt(80), 0, 0, 0, 1);
                livingOwner.level.destroyBlock(targetPos, false);

                CAScreenShakeEntity.shakeScreen(livingOwner.level, livingOwner.position(), 10.0F, 0.12F, 5, 20);
            }
        }
    }

    @Nullable
    public boolean findValidPos(ITag.INamedTag<Block> targetBlockTag) {
        MobEntity livingOwner = (MobEntity) animatableOwner;
        BlockPos.Mutable pointerPos = livingOwner.blockPosition().mutable();

        for (BlockPos curPos : BlockPos.betweenClosed(livingOwner.blockPosition(), livingOwner.blockPosition().mutable().offset(searchRadius, searchRadius, searchRadius).immutable())) {
            pointerPos.set(curPos);

            if (Math.abs(pointerPos.getY() - livingOwner.getY()) >= 3 || livingOwner.level.getBlockState(pointerPos).isAir(livingOwner.level, pointerPos)) continue;

            if (livingOwner.level.getBlockState(pointerPos).is(targetBlockTag)) {
                this.targetPos = new BlockPos(pointerPos);
                return true;
            }
        }

        return false;
    }
}
