package io.github.chaosawakens.common.item.misc;

import io.github.chaosawakens.api.vfx.basic.ScreenShakeEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CreeperLauncherItem extends Item {

    public CreeperLauncherItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack targetStack, Player ownerPlayer, LivingEntity targetEntity, InteractionHand ownerHand) {
        if (targetEntity instanceof Creeper targetCreeper) {
            targetCreeper.setDeltaMovement(targetCreeper.getDeltaMovement().add(0, 10, 0));

            ownerPlayer.level().playSound(ownerPlayer, targetCreeper, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 100.0F, ownerPlayer.getRandom().nextFloat() + 0.1F);
            ownerPlayer.level().playSound(ownerPlayer, targetCreeper, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 100.0F, ownerPlayer.getRandom().nextFloat() + 0.1F);
            ownerPlayer.level().playSound(ownerPlayer, targetCreeper, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 100.0F, ownerPlayer.getRandom().nextFloat() + 0.1F);
            ownerPlayer.level().playSound(ownerPlayer, targetCreeper, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 100.0F, ownerPlayer.getRandom().nextFloat() + 0.1F);
            ownerPlayer.level().playSound(ownerPlayer, targetCreeper, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 100.0F, ownerPlayer.getRandom().nextFloat() + 0.1F);

            if (ownerPlayer.getRandom().nextFloat() < 0.4F) {
                new ScreenShakeEffect(ownerPlayer.blockPosition(), 150.5D, 2000.2F, 450.5F, 1.2F).enqueue(ownerPlayer.level());
                ownerPlayer.level().explode(targetCreeper, targetCreeper.getX(), targetCreeper.getY(), targetCreeper.getZ(), 15.0F, true, Level.ExplosionInteraction.MOB);
            } else new ScreenShakeEffect(ownerPlayer.blockPosition(), 150.5D, 0.006F, 45.5F, 1.2F).enqueue(ownerPlayer.level());

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
