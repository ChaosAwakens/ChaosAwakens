package io.github.chaosawakens.common.entity.base;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class AnimatableProjectileEntity extends DamagingProjectileEntity implements IAnimatableEntity {

    public AnimatableProjectileEntity(EntityType<? extends DamagingProjectileEntity> targetType, World targetWorld) {
        super(targetType, targetWorld);
    }

    public AnimatableProjectileEntity(EntityType<? extends DamagingProjectileEntity> pEntityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, World pLevel) {
        super(pEntityType, pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public AnimatableProjectileEntity(EntityType<? extends DamagingProjectileEntity> pEntityType, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, World pLevel) {
        super(pEntityType, pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    @Override
    public abstract <E extends IAnimatableEntity> WrappedAnimationController<? extends E> getMainWrappedController();

    @Override
    public int animationInterval() {
        return 1;
    }

    @Override
    public abstract <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event);

    @Override
    public void tick() {
        tickAnims();
        handleBaseAnimations();

        if (getDeathAnim() == null) super.tick();
        else {
            Entity projectileOwner = getOwner();

            if (level.isClientSide || (projectileOwner == null || !projectileOwner.removed) && level.hasChunkAt(blockPosition())) {
                if (!leftOwner) checkLeftOwner();
                if (shouldBurn()) setSecondsOnFire(1);

                RayTraceResult projectileHitResult = ProjectileHelper.getHitResult(this, this::canHitEntity);

                if (projectileHitResult.getType() != RayTraceResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, projectileHitResult)) onHit(projectileHitResult);

                checkInsideBlocks();

                Vector3d curDeltaMovement = getDeltaMovement();
                double xDeltaOffset = getX() + curDeltaMovement.x;
                double yDeltaOffset = getY() + curDeltaMovement.y;
                double zDeltaOffset = getZ() + curDeltaMovement.z;

                ProjectileHelper.rotateTowardsMovement(this, 0.9F);

                float motionInertia = getInertia();

                if (isInWater()) {
                    for (int i = 0; i < 4; ++i) level.addParticle(ParticleTypes.BUBBLE, xDeltaOffset - curDeltaMovement.x * 0.25D, yDeltaOffset - curDeltaMovement.y * 0.25D, zDeltaOffset - curDeltaMovement.z * 0.25D, curDeltaMovement.x, curDeltaMovement.y, curDeltaMovement.z);

                    motionInertia = 0.8F;
                }

                setDeltaMovement(curDeltaMovement.add(xPower, yPower, zPower).scale(motionInertia));

                if (getTrailParticle() != null) level.addParticle(getTrailParticle(), xDeltaOffset, yDeltaOffset + 0.5D, zDeltaOffset, 0.0D, 0.0D, 0.0D);

                setPos(xDeltaOffset, yDeltaOffset, zDeltaOffset);
            }

            if (satisfyDeathConditions()) {
                stopAnimation(getIdleAnim());
                playAnimation(getDeathAnim(), true);
            }

            if (getDeathAnim().hasAnimationFinished()) remove();
        }
    }

    public boolean satisfyDeathConditions() {
        RayTraceResult projectileHitResult = ProjectileHelper.getHitResult(this, this::canHitEntity);
        return projectileHitResult.getType() != RayTraceResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, projectileHitResult);
    }

    @Override
    protected abstract IParticleData getTrailParticle();

    @Override
    public abstract <E extends IAnimatableEntity> ObjectArrayList<WrappedAnimationController<? extends E>> getWrappedControllers();

    @Override
    public abstract <B extends IAnimationBuilder> ObjectArrayList<B> getCachedAnimations();

    @Override
    public abstract IAnimationBuilder getIdleAnim();

    @Override
    public final IAnimationBuilder getWalkAnim() {
        return null;
    }

    @Override
    public final IAnimationBuilder getSwimAnim() {
        return null;
    }

    @Override
    public abstract IAnimationBuilder getDeathAnim();

    @Override
    public abstract String getOwnerMDFileName();

    @Override
    public abstract AnimationFactory getFactory();

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void handleBaseAnimations() {
        if (!satisfyDeathConditions() && !removed && getIdleAnim() != null) playAnimation(getIdleAnim(), true);
    }
}
