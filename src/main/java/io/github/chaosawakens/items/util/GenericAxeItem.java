package io.github.chaosawakens.items.util;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;

public class GenericAxeItem extends AxeItem {
    public GenericAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
