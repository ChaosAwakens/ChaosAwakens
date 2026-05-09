package io.github.chaosawakens.core.template;

import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.property_wrapper.impl.specialised.language.SpecializedLanguagePropertyWrapper;
import io.github.chaosawakens.CAConstants;
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
}
