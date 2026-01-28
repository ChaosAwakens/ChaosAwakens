package io.github.chaosawakens.template;

import com.mememan.nexus.client.model.item.ItemModelDefinition;
import com.mememan.nexus.property_wrapper.def.item.ItemPropertyWrapper;
import com.mememan.nexus.util.RegistryUtil;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.world.item.Item;

public class CAItemPWTemplates {
    public static final ItemPropertyWrapper<Item> HANDHELD_LONG = new ItemPropertyWrapper<>()
            .builder()
            .withModelDefinition(parentItem -> new ItemModelDefinition(CAModelTemplates.HANDHELD_LONG)
                    .withTextureMapping(TextureMapping.layer0(RegistryUtil.getTextureLocationOrDefault(parentItem, "item"))))
            .build();
}
