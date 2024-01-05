package io.github.chaosawakens.common.entity.base;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public abstract class AnimatableArrowEntity extends AbstractArrowEntity implements IAnimatableEntity {

    public AnimatableArrowEntity(EntityType<? extends AbstractArrowEntity> targetType, World curWorld) {
        super(targetType, curWorld);
    }

    public AnimatableArrowEntity(EntityType<? extends AbstractArrowEntity> targetType, double spawnX, double spawnY, double spawnZ, World curWorld) {
        super(targetType, spawnX, spawnY, spawnZ, curWorld);
    }

    public AnimatableArrowEntity(EntityType<? extends AbstractArrowEntity> targetType, LivingEntity targetOwnerEntity, World curWorld) {
        super(targetType, targetOwnerEntity, curWorld);
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
    public abstract <E extends IAnimatableEntity> ObjectArrayList<WrappedAnimationController<? extends E>> getWrappedControllers();

    @Override
    public abstract <B extends IAnimationBuilder> ObjectArrayList<B> getCachedAnimations();

    @Override
    public abstract IAnimationBuilder getIdleAnim();

    @Nullable
    @Override
    public final IAnimationBuilder getWalkAnim() {
        return null;
    }

    @Nullable
    @Override
    public final IAnimationBuilder getSwimAnim() {
        return null;
    }

    @Override
    public abstract IAnimationBuilder getDeathAnim();

    @Override
    public abstract String getOwnerMDFileName();

    @Override
    protected abstract ItemStack getPickupItem();

    @Override
    public abstract AnimationFactory getFactory();
}
