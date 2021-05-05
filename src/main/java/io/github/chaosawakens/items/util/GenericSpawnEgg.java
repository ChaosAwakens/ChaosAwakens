package io.github.chaosawakens.items.util;

import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;

public class GenericSpawnEgg extends SpawnEggItem {
    public GenericSpawnEgg(EntityType<?> type, Settings settings) {
        super(type, 0, 0, settings);
    }
}
