package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.network.PacketHandler;
import io.github.chaosawakens.common.network.PacketThrowPlayer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class ThrowRiderAttackGoal extends MeleeAttackGoal {

    private int throwTimer;
    private int timeout;

    public ThrowRiderAttackGoal(CreatureEntity creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
    }

    @Override
    public boolean canUse() {
        return this.mob.getPassengers().isEmpty() && super.canUse();
    }

    @Override
    public void start() {
        this.throwTimer = 1 + mob.getRandom().nextInt(5); // Wait 1.5 to 2.5 seconds before we throw the target
        timeout = 1 + mob.getRandom().nextInt(5); // Let's only try to chase for around 5-6 seconds
        super.start();
    }

    @Override
    public void tick() {
        timeout--;
        if (!mob.getPassengers().isEmpty())
            throwTimer--;
        else
            super.tick();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
        double d0 = this.getAttackReachSqr(p_190102_1_);

        if (p_190102_2_ <= d0 && this.getTicksUntilNextAttack() <= 0) {
            this.resetAttackCooldown();
            if (mob.getPassengers().isEmpty() && p_190102_1_.getVehicle() == null) {
                p_190102_1_.startRiding(mob);
            }
        }
    }

    @Override
    public void stop() {
        if (!mob.getPassengers().isEmpty()) {
            Entity rider = mob.getPassengers().get(0);
            mob.ejectPassengers();

            rider.push(0.8, 1.1, 0.8);

            if (rider instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) rider;
                PacketThrowPlayer message = new PacketThrowPlayer(0.8f, 1.1f, 0.8f);
                PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
            }
        }
        super.stop();
    }

    @Override
    public boolean canContinueToUse() {
        return (throwTimer > 0 && !mob.getPassengers().isEmpty()) || (timeout > 0 && super.canContinueToUse() && mob.getPassengers().isEmpty());
    }

}