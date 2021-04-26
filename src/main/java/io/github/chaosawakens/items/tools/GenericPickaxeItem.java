package io.github.chaosawakens.items.tools;

import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class GenericPickaxeItem extends PickaxeItem {
    public GenericPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
