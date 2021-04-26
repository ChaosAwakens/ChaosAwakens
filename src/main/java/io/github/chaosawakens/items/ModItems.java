package io.github.chaosawakens.items;

import io.github.chaosawakens.items.tools.GenericAxeItem;
import io.github.chaosawakens.items.tools.GenericHoeItem;
import io.github.chaosawakens.items.tools.GenericPickaxeItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import io.github.chaosawakens.items.tools.AmethystToolMaterial;

public class ModItems implements ModInitializer {

    public static ToolItem AMETHYST_SWORD = new SwordItem(AmethystToolMaterial.INSTANCE, 3, -2.4F, new Item.Settings().group(ItemGroup.COMBAT));
    public static ToolItem AMETHYST_SHOVEL = new ShovelItem(AmethystToolMaterial.INSTANCE, 1.5F, -3.0F, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem AMETHYST_PICKAXE = new GenericPickaxeItem(AmethystToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem AMETHYST_AXE = new GenericAxeItem(AmethystToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem AMETHYST_HOE = new GenericHoeItem(AmethystToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_sword"), AMETHYST_SWORD);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_shovel"), AMETHYST_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_pickaxe"), AMETHYST_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_axe"), AMETHYST_AXE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_hoe"), AMETHYST_HOE);
    }
}
