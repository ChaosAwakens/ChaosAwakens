package io.github.chaosawakens.api.sfx_library.prototyping;

import io.github.chaosawakens.content.entity.base.stateful.StatefulBoss;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectRBTreeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

import java.util.function.Predicate;

public class TickableBossMusicSoundInstance extends AbstractTickableSoundInstance {
    protected static final SoundManager SOUND_ENGINE = Minecraft.getInstance().getSoundManager();
    protected final StatefulBoss ownerBoss;
    protected Double2ObjectMap<Predicate<StatefulBoss>> segmentsMsByCriteria = new Double2ObjectRBTreeMap<>();
    protected double progressMs = 0.0D;

    public TickableBossMusicSoundInstance(StatefulBoss ownerBoss, SoundEvent coreBossMusicEvent) {
        super(coreBossMusicEvent, SoundSource.MUSIC, SoundInstance.createUnseededRandom());

        this.ownerBoss = ownerBoss;
    }

    public TickableBossMusicSoundInstance setSegments(Double2ObjectMap<Predicate<StatefulBoss>> segments) {
        this.segmentsMsByCriteria = segments;

        return this;
    }

    public TickableBossMusicSoundInstance setSegmentAt(double progressInMs, Predicate<StatefulBoss> criteria) {
        this.segmentsMsByCriteria.put(progressInMs, criteria);

        return this;
    }

    @Override
    public void tick() {

    }

    public static class Segment extends AbstractTickableSoundInstance {

        public Segment(SoundEvent coreBossSoundtrack) {
            super(coreBossSoundtrack, SoundSource.MUSIC, SoundInstance.createUnseededRandom());
        }

        @Override
        public void tick() {

        }

        @Override
        public boolean isLooping() {
            return super.isLooping();
        }
    }
}
