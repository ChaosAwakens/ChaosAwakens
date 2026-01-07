package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.platform.CAServices;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

@RegistrarEntry
public final class CASoundEvents {
    private static final ObjectArrayList<Supplier<SoundEvent>> SOUND_EVENTS = new ObjectArrayList<>();

    // Mining Paradise
    public static final Supplier<SoundEvent> CRAGS = registerSoundEvent("crags", () -> SoundEvent.createVariableRangeEvent(CAConstants.prefix("soundtracks.mining_paradise.crags")));

    public static Music createBiomeMusic(Supplier<SoundEvent> soundEvent, int minDelay, int maxDelay) {
        return new Music(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(soundEvent.get()), minDelay, maxDelay, false);
    }

    public static Music createBiomeMusic(Supplier<SoundEvent> soundEvent) {
        return createBiomeMusic(soundEvent, 600, 4000);
    }

   private static Supplier<SoundEvent> registerSoundEvent(ResourceLocation id, Supplier<SoundEvent> soundEventSup) {
        Supplier<SoundEvent> registeredSoundEventSup = CAServices.REGISTRAR.registerObject(id, soundEventSup, BuiltInRegistries.SOUND_EVENT); // Otherwise reference to the sound event sup is null cuz it needs to be registered b4hand
        SOUND_EVENTS.add(registeredSoundEventSup);
        return registeredSoundEventSup;
    }

    private static Supplier<SoundEvent> registerSoundEvent(String id, Supplier<SoundEvent> itemSup) {
        return registerSoundEvent(CAConstants.prefix(id), itemSup);
    }

    public static ImmutableList<Supplier<SoundEvent>> getSoundEvents() {
        return ImmutableList.copyOf(SOUND_EVENTS);
    }
}
