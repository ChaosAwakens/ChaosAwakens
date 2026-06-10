package io.github.chaosawakens.content.item.equipment.tools;

import io.github.chaosawakens.content.entity.projectiles.RayGunProjectileEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RayGun extends Item {
    private static final double PROJECTILE_SPAWN_Y_OFFSET = 1.5D;
    private static final float PROJECTILE_VELOCITY = 5.0F;
    private static final float PROJECTILE_INACCURACY = 0.25F;
    private static final float SHOOT_SOUND_VOLUME = 1.5F;
    private static final float SHOOT_SOUND_PITCH = 1.5F;

    public RayGun(Properties builderIn) {
        super(builderIn);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            RayGunProjectileEntity projectile = createProjectile(level, player);
            level.addFreshEntity(projectile);

            if (!player.getAbilities().instabuild) {
                heldStack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(hand));
            }
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, SHOOT_SOUND_VOLUME, SHOOT_SOUND_PITCH);
        return InteractionResultHolder.sidedSuccess(heldStack, level.isClientSide());
    }

    private static RayGunProjectileEntity createProjectile(Level level, Player player) {
        Vec3 lookAngle = player.getLookAngle();
        RayGunProjectileEntity projectile = new RayGunProjectileEntity(level, player, lookAngle.x, lookAngle.y, lookAngle.z);
        projectile.setPos(player.getX(), player.getY() + PROJECTILE_SPAWN_Y_OFFSET, player.getZ());
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, PROJECTILE_VELOCITY, PROJECTILE_INACCURACY);
        return projectile;
    }
}

