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
    public boolean shouldExecute() {
        return this.attacker.getPassengers().isEmpty() && super.shouldExecute();
    }

    @Override
    public void startExecuting() {
        this.throwTimer = 1 + attacker.getRNG().nextInt(5); // Wait 1.5 to 2.5 seconds before we throw the target
        timeout = 1 + attacker.getRNG().nextInt(5); // Lets only try to chase for around 5-6 seconds
        super.startExecuting();
    }

    @Override
    public void tick() {
        timeout--;
        if (!attacker.getPassengers().isEmpty())
            throwTimer--;
        else
            super.tick();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
        double d0 = this.getAttackReachSqr(p_190102_1_);

        if (p_190102_2_ <= d0 && this.getSwingCooldown() <= 0) {
            this.resetSwingCooldown();
            if (attacker.getPassengers().isEmpty() && p_190102_1_.getRidingEntity() == null) {
                p_190102_1_.startRiding(attacker);
            }
        }
    }

    @Override
    public void resetTask() {
        if (!attacker.getPassengers().isEmpty()) {
            Entity rider = attacker.getPassengers().get(0);
            attacker.removePassengers();

            rider.addVelocity(0.8, 1, 0.8);

            if (rider instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) rider;
                PacketThrowPlayer message = new PacketThrowPlayer(0.8f, 1, 0.8f);
                PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
            }
        }
        super.resetTask();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return (throwTimer > 0 && !attacker.getPassengers().isEmpty()) || (timeout > 0 && super.shouldContinueExecuting() && attacker.getPassengers().isEmpty());
    }

}