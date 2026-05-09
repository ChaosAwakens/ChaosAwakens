package io.github.chaosawakens.content.entity.base.stateful;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class StatefulBoss extends StatefulMonster {
    protected static final EntityDataAccessor<Boolean> IS_IMMERSIVE = SynchedEntityData.defineId(StatefulBoss.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Byte> PHASE_ID = SynchedEntityData.defineId(StatefulBoss.class, EntityDataSerializers.BYTE);
    public static final byte DEFAULT_PHASE_ID = 0;
    protected final ServerBossEvent bossEvent;

    protected StatefulBoss(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);

        this.bossEvent = null; // TODO
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(PHASE_ID, DEFAULT_PHASE_ID);
        this.entityData.define(IS_IMMERSIVE, false);
    }

    public byte getPhaseId() {
        return this.entityData.get(PHASE_ID);
    }

    public void setPhaseId(byte phaseId) {
        this.entityData.set(PHASE_ID, phaseId);
    }

    public boolean isDefaultPhase() {
        return getPhaseId() == DEFAULT_PHASE_ID;
    }

    public boolean isImmersive() { // :drooling_face:
        return this.entityData.get(IS_IMMERSIVE);
    }

    public void setImmersive(boolean immersive) {
        this.entityData.set(IS_IMMERSIVE, immersive);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        
    }
}
