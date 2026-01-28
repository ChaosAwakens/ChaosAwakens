package io.github.chaosawakens.template;

import io.github.chaosawakens.CAConstants;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;

import java.util.Optional;

public class CAModelTemplates {
    public static final ModelTemplate HANDHELD_LONG = new ModelTemplate(Optional.of(CAConstants.prefix("item/handheld_long")), Optional.empty(), TextureSlot.LAYER0);
}
