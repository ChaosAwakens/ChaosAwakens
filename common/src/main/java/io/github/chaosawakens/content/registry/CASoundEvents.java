package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.template.property_wrapper.SoundEventPropertyWrapperTemplates;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.core.template.CASEPWTemplates;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

@RegistrarEntry
public final class CASoundEvents {
    protected static final ObjectArrayList<Supplier<SoundEvent>> SOUND_EVENTS = new ObjectArrayList<>();

    // Soundtracks
    public static final Supplier<SoundEvent> CRAGS = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("crags"), () -> SoundEvent.createVariableRangeEvent(CAConstants.prefix("crags")), CASEPWTemplates.SOUNDTRACK_SOUND_EVENT, SOUND_EVENTS);

    // Crystal Blocks
    public static final Supplier<SoundEvent> CRYSTAL_BUDDING_BREAK = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("crystal_budding_break"), createForBlock("crystal_budding_break"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> CRYSTAL_CLUSTER_BREAK = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("crystal_cluster_break"), createForBlock("crystal_cluster_break"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);

    public static final Supplier<SoundEvent> KYANITE_BREAK = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("kyanite_break"), createForBlock("kyanite_break"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> KYANITE_STEP = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("kyanite_step"), createForBlock("kyanite_step"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);

    public static final Supplier<SoundEvent> CRYSTAL_GRASS_BREAK = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("crystal_grass_break"), createForBlock("crystal_grass_break"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> CRYSTAL_GRASS_STEP = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("crystal_grass_step"), createForBlock("crystal_grass_step"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);

    public static final Supplier<SoundEvent> CRYSTAL_LEAVES_BREAK = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("crystal_leaves_break"), createForBlock("crystal_leaves_break"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> CRYSTAL_LEAVES_STEP = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("crystal_leaves_step"), createForBlock("crystal_leaves_step"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);

    public static final Supplier<SoundEvent> CRYSTALWOOD_BREAK = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("crystalwood_break"), createForBlock("crystalwood_break"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> CRYSTALWOOD_STEP = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("crystalwood_step"), createForBlock("crystalwood_step"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);

    // Mining Paradise Blocks
    public static final Supplier<SoundEvent> TAR_STEP = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("tar_step"), createForBlock("tar_step"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> TAR_PLACE = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("tar_place"), createForBlock("tar_place"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> TAR_HIT = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("tar_hit"), createForBlock("tar_hit"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> TAR_FALL = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("tar_fall"), createForBlock("tar_fall"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);

    public static final Supplier<SoundEvent> DENSE_GRASS_BREAK = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("dense_grass_break"), createForBlock("dense_grass_break"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> DENSE_GRASS_STEP = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("dense_grass_step"), createForBlock("dense_grass_step"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> DENSE_GRASS_PLACE = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("dense_grass_place"), createForBlock("dense_grass_place"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> DENSE_GRASS_HIT = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("dense_grass_hit"), createForBlock("dense_grass_hit"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> DENSE_GRASS_FALL = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("dense_grass_fall"), createForBlock("dense_grass_fall"), CASEPWTemplates.BLOCK_SOUND_EVENT, SOUND_EVENTS);

    // Ent
/*
    public static final Supplier<SoundEvent> ENT_IDLE = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("ent_idle"), () -> SoundEvent.createVariableRangeEvent(CAConstants.prefix("ent_idle")), CASEPWTemplates.ENTITY_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> ENT_HURT = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("ent_hurt"), () -> SoundEvent.createVariableRangeEvent(CAConstants.prefix("ent_hurt")), CASEPWTemplates.ENTITY_SOUND_EVENT, SOUND_EVENTS);
    public static final Supplier<SoundEvent> ENT_DEATH = SoundEventPropertyWrapperTemplates.registerSoundEventFromTemplateAndReflect(CAConstants.prefix("ent_death"), () -> SoundEvent.createVariableRangeEvent(CAConstants.prefix("ent_death")), CASEPWTemplates.ENTITY_SOUND_EVENT, SOUND_EVENTS);
*/

    public static Music createBiomeMusic(Supplier<SoundEvent> soundEvent, int minDelay, int maxDelay) {
        return new Music(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(soundEvent.get()), minDelay, maxDelay, false);
    }

    public static Music createBiomeMusic(Supplier<SoundEvent> soundEvent) {
        return createBiomeMusic(soundEvent, 600, 4000);
    }

    private static Supplier<SoundEvent> createForBlock(ResourceLocation id) {
        return () -> SoundEvent.createFixedRangeEvent(id, 16.0F);
    }

    private static Supplier<SoundEvent> createForBlock(String id) {
        return createForBlock(CAConstants.prefix(id));
    }

    public static ImmutableList<Supplier<SoundEvent>> getSoundEvents() {
        return ImmutableList.copyOf(SOUND_EVENTS);
    }
}
