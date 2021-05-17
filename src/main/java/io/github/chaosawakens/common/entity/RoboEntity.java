package io.github.chaosawakens.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RoboEntity extends MonsterEntity {
    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(GhastEntity.class, DataSerializers.BOOLEAN);
    public boolean didLaser;

    public RoboEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isAttacking() {
        return this.dataManager.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.dataManager.set(ATTACKING, attacking);
    }

    private void setDidLaser(boolean didLaserIn) {
        this.didLaser = didLaserIn;
    }
}
