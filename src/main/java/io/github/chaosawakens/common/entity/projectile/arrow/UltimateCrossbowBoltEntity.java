package io.github.chaosawakens.common.entity.projectile.arrow;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class UltimateCrossbowBoltEntity extends AbstractArrowEntity implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private final AnimationController<UltimateCrossbowBoltEntity> mainController = new AnimationController<>(this, "ultimatecrossbowboltmaincontroller", 1, this::mainPredicate);
    private static final AnimationBuilder MOVE_ANIM = new AnimationBuilder().addAnimation("Moving", ILoopType.EDefaultLoopTypes.LOOP);
    private static final AnimationBuilder HIT_ANIM = new AnimationBuilder().addAnimation("Hit", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    private static final AnimationBuilder STUCK_ANIM = new AnimationBuilder().addAnimation("Stuck", ILoopType.EDefaultLoopTypes.LOOP);

    public UltimateCrossbowBoltEntity(EntityType<? extends AbstractArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public UltimateCrossbowBoltEntity(World worldIn, double x, double y, double z) {
        super(CAEntityTypes.ULTIMATE_CROSSBOW_BOLT.get(), x, y, z, worldIn);
    }

    public UltimateCrossbowBoltEntity(World worldIn, LivingEntity shooter) {
        super(CAEntityTypes.ULTIMATE_CROSSBOW_BOLT.get(), shooter, worldIn);
    }

    private <T extends IAnimatable> PlayState mainPredicate(AnimationEvent<T> event) {
        if (getDeltaMovement().length() > 0.01D) event.getController().setAnimation(MOVE_ANIM);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(mainController);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide && inGround && inGroundTime != 0 && inGroundTime >= 600) level.broadcastEntityEvent(this, (byte) 0);
        if (level.isClientSide && inGroundTime > 0) mainController.setAnimation(STUCK_ANIM);
    }

    @Override
    protected void onHit(RayTraceResult pResult) {
        super.onHit(pResult);

        if (level.isClientSide) mainController.setAnimation(HIT_ANIM);
    }

    @Override
    protected ItemStack getPickupItem() {
        return CAItems.ULTIMATE_CROSSBOW_BOLT.get().getDefaultInstance();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public int tickTimer() {
        return tickCount;
    }
}
