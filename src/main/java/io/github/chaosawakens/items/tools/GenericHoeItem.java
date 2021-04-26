package io.github.chaosawakens.items.tools;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

public class GenericHoeItem extends HoeItem {
    public GenericHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
