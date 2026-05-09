package io.github.chaosawakens.core.template;

import com.mememan.nexus.client.sound.SoundPropertyHolder;
import com.mememan.nexus.platform.NexusServices;
import com.mememan.nexus.property_wrapper.def.sound_event.SoundEventPropertyWrapper;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.util.StringUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CASEPWTemplates {
    public static final SoundEventPropertyWrapper<SoundEvent> ENTITY_SOUND_EVENT = new SoundEventPropertyWrapper<>()
            .builder()
            .compose((parentObject, builder) -> builder
                    .withSoundDefinitions(findAndMapVariantSoundEvents(parentObject.get().getLocation()))
                    .withSubtitleKey("subtitles.entity.chaosawakens.%s".formatted(parentObject.get().getLocation().getPath()), StringUtil.formatEntitySoundEventSubtitle("subtitles.entity.chaosawakens.%s".formatted(parentObject.get().getLocation().getPath()))))
            .build();
    public static final SoundEventPropertyWrapper<SoundEvent> BLOCK_SOUND_EVENT = new SoundEventPropertyWrapper<>()
            .builder()
            .compose((parentObject, builder) -> builder
                    .withSoundDefinitions(findAndMapVariantSoundEvents(parentObject.get().getLocation()))
                    .withSubtitleKey("subtitles.block.chaosawakens.%s".formatted(parentObject.get().getLocation().getPath()), StringUtil.formatBlockSoundEventSubtitle("subtitles.block.chaosawakens.%s".formatted(parentObject.get().getLocation().getPath()))))
            .build();
    public static final SoundEventPropertyWrapper<SoundEvent> SOUNDTRACK_SOUND_EVENT = new SoundEventPropertyWrapper<>()
            .builder()
            .compose((parentObject, builder) -> builder // TODO
                    .withSoundDefinition(new SoundPropertyHolder(findAndMapLiteralSoundEvent(parentObject.get().getLocation())
                            .map(SoundPropertyHolder::soundFileLocation)
                            .orElse(parentObject.get().getLocation()), 1.0F, 1.0F, 1, true, 16.0F, true))
                    .withSubtitleKey("subtitles.soundtrack.chaosawakens.%s".formatted(parentObject.get().getLocation().getPath()), StringUtil.formatSoundtrackSubtitle("subtitles.soundtrack.chaosawakens.%s".formatted(parentObject.get().getLocation().getPath()))))
            .build();

    private CASEPWTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CASEPWTemplates)");
    }

    public static List<SoundPropertyHolder> findAndMapVariantSoundEvents(ResourceLocation baseId) {
        return NexusServices.PLATFORM_MANAGER.getModData().stream()
                .filter(curModData -> curModData.getModMetadata().modId().equals(CAConstants.MOD_ID))
                .findFirst().orElseThrow()
                .getAllResourcePaths(".ogg").stream()
                .filter(curPath -> curPath.substring(curPath.lastIndexOf("/") + 1).matches(baseId.getPath() + "_\\d+\\.ogg"))
                .map(curPath -> new SoundPropertyHolder(baseId.withPath(curPath.substring(curPath.lastIndexOf("sounds/") + "sounds/".length(), curPath.lastIndexOf(".ogg")))))
                .collect(Collectors.toCollection(ObjectArrayList::new));
    }

    public static Optional<SoundPropertyHolder> findAndMapLiteralSoundEvent(ResourceLocation baseId) {
        return NexusServices.PLATFORM_MANAGER.getModData().stream()
                .filter(curModData -> curModData.getModMetadata().modId().equals(CAConstants.MOD_ID))
                .findFirst().orElseThrow()
                .getAllResourcePaths(".ogg").stream()
                .filter(curPath -> curPath.substring(curPath.lastIndexOf("/") + 1).equals(baseId.getPath() + ".ogg"))
                .map(curPath -> new SoundPropertyHolder(baseId.withPath(curPath.substring(curPath.lastIndexOf("sounds/") + "sounds/".length(), curPath.lastIndexOf(".ogg")))))
                .findFirst();
    }
}
