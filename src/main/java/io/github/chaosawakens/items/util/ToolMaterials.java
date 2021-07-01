package io.github.chaosawakens.items.util;

import io.github.chaosawakens.items.Items;
import io.github.chaosawakens.items.util.generic.GenericToolMaterial;
import net.minecraft.item.ToolMaterial;

public class ToolMaterials {
    public static final ToolMaterial ULTIMATE_TOOL_MATERIAL;
    public static final ToolMaterial EMERALD_TOOL_MATERIAL;
    public static final ToolMaterial RUBY_TOOL_MATERIAL;
    public static final ToolMaterial AMETHYST_TOOL_MATERIAL;
    public static final ToolMaterial TIGERS_EYE_TOOL_MATERIAL;
    public static final ToolMaterial EXPERIENCE_TOOL_MATERIAL;
    public static final ToolMaterial NIGHTMARE_TOOL_MATERIAL;

    static {
        ULTIMATE_TOOL_MATERIAL = new GenericToolMaterial(5, 3000, 15, 36, 64, null);
        EMERALD_TOOL_MATERIAL = new GenericToolMaterial(3, 2000, 10, 6, 24, net.minecraft.item.Items.EMERALD);
        RUBY_TOOL_MATERIAL = new GenericToolMaterial(4, 1800, 11, 16, 22, Items.RUBY);
        AMETHYST_TOOL_MATERIAL = new GenericToolMaterial(3, 2000, 11, 11, 18, Items.AMETHYST);
        TIGERS_EYE_TOOL_MATERIAL = new GenericToolMaterial(3, 600, 11, 8, 20, Items.TIGERS_EYE);
        EXPERIENCE_TOOL_MATERIAL = new GenericToolMaterial(500, 5, 3.0F, 2, 15, null);
        NIGHTMARE_TOOL_MATERIAL = new GenericToolMaterial(500, 5, 3.0F, 2, 15, null);
    }
}
