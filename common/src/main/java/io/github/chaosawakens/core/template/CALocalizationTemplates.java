package io.github.chaosawakens.core.template;

import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.property_wrapper.impl.specialised.language.SpecializedLanguagePropertyWrapper;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.registry.CAExtraLocalizations;
import net.minecraft.world.level.block.Blocks;

@RegistrarEntry(priority = -1)
public final class CALocalizationTemplates { // TODO Proper dummy property wrapper gjkSupport TBI in a later Nexus API version
    public static final Object CONTAINERS = new SpecializedLanguagePropertyWrapper<>(() -> Blocks.AIR, false, CAConstants.MOD_ID)
            .builder()
            .bypassDefaultTranslation()
            .withAdditionalLocalizationKey("container.chaosawakens.iron_defossilizer", "Iron Defossilizer")
            .withAdditionalLocalizationKey("container.chaosawakens.crystal_defossilizer", "Crystal Defossilizer")
            .withAdditionalLocalizationKey("container.chaosawakens.robo_crate", "Robo Crate")
            .buildAndGet();

    public static final Object SET_BONUSES = new SpecializedLanguagePropertyWrapper<>(() -> Blocks.AIR, false, CAConstants.MOD_ID)
            .builder()
            .bypassDefaultTranslation()
            .withAdditionalLocalizationKey(CAExtraLocalizations.SET_BONUS_LABEL.key(), CAExtraLocalizations.SET_BONUS_LABEL.value())
            .withAdditionalLocalizationKey(CAExtraLocalizations.EXPERIENCE_BONUS_DESCRIPTION.key(), CAExtraLocalizations.EXPERIENCE_BONUS_DESCRIPTION.value())
            .withAdditionalLocalizationKey(CAExtraLocalizations.LAVA_EEL_BONUS_DESCRIPTION.key(), CAExtraLocalizations.LAVA_EEL_BONUS_DESCRIPTION.value())
            .withAdditionalLocalizationKey(CAExtraLocalizations.EMERALD_BONUS_DESCRIPTION.key(), CAExtraLocalizations.EMERALD_BONUS_DESCRIPTION.value())
            .withAdditionalLocalizationKey(CAExtraLocalizations.LAPIS_BONUS_DESCRIPTION.key(), CAExtraLocalizations.LAPIS_BONUS_DESCRIPTION.value())
            .buildAndGet();
}
