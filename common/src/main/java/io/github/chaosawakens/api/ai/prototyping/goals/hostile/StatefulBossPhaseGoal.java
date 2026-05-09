package io.github.chaosawakens.api.ai.prototyping.goals.hostile;

import io.github.chaosawakens.content.entity.base.stateful.StatefulBoss;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.bytes.ByteLinkedOpenHashSet;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.function.Predicate;

public class StatefulBossPhaseGoal extends Goal {
    protected final StatefulBoss boss;
    protected final byte defaultPhaseId;
    protected final Byte2ObjectMap<Predicate<StatefulBoss>> phasesByCriteria = new Byte2ObjectOpenHashMap<>();
    protected final ByteSet orderedPhases = new ByteLinkedOpenHashSet();
    protected byte currentPhaseId;

    public StatefulBossPhaseGoal(StatefulBoss boss, byte defaultPhaseId) {
        this.boss = boss;
        this.defaultPhaseId = defaultPhaseId;

        this.currentPhaseId = defaultPhaseId;
    }

    public StatefulBossPhaseGoal(StatefulBoss boss) {
        this(boss, StatefulBoss.DEFAULT_PHASE_ID);
    }

    public StatefulBossPhaseGoal addPhase(byte phaseId, Predicate<StatefulBoss> criteria) {
        orderedPhases.add(phaseId);
        phasesByCriteria.putIfAbsent(phaseId, criteria);

        return this;
    }

    @Override
    public boolean canUse() {
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void tick() {

    }
}
