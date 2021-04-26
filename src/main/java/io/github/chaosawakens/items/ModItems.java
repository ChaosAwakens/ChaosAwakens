package io.github.chaosawakens.items;

import io.github.chaosawakens.items.armor.*;
import io.github.chaosawakens.items.tools.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems implements ModInitializer {

    public static final Item AMETHYST = new Item(new Item.Settings().group(ItemGroup.MISC));

    public static final ToolItem AMETHYST_SWORD = new SwordItem(AmethystToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem AMETHYST_SHOVEL = new ShovelItem(AmethystToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem AMETHYST_PICKAXE = new GenericPickaxeItem(AmethystToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem AMETHYST_AXE = new GenericAxeItem(AmethystToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem AMETHYST_HOE = new GenericHoeItem(AmethystToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item AMETHYST_HELMET = new ArmorItem(AmethystArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item AMETHYST_CHESTPLATE = new ArmorItem(AmethystArmorMaterial.INSTANCE, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item AMETHYST_LEGGINGS = new ArmorItem(AmethystArmorMaterial.INSTANCE, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item AMETHYST_BOOTS = new ArmorItem(AmethystArmorMaterial.INSTANCE, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    public static final ToolItem EMERALD_SWORD = new SwordItem(EmeraldToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem EMERALD_SHOVEL = new ShovelItem(EmeraldToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EMERALD_PICKAXE = new GenericPickaxeItem(EmeraldToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EMERALD_AXE = new GenericAxeItem(EmeraldToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EMERALD_HOE = new GenericHoeItem(EmeraldToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item EMERALD_HELMET = new ArmorItem(EmeraldArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EMERALD_CHESTPLATE = new ArmorItem(EmeraldArmorMaterial.INSTANCE, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EMERALD_LEGGINGS = new ArmorItem(EmeraldArmorMaterial.INSTANCE, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EMERALD_BOOTS = new ArmorItem(EmeraldArmorMaterial.INSTANCE, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    public static final Item RUBY = new Item(new Item.Settings().group(ItemGroup.MISC));

    public static final ToolItem RUBY_SWORD = new SwordItem(RubyToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem RUBY_SHOVEL = new ShovelItem(RubyToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem RUBY_PICKAXE = new GenericPickaxeItem(RubyToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem RUBY_AXE = new GenericAxeItem(RubyToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem RUBY_HOE = new GenericHoeItem(RubyToolMaterial.INSTANCE, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item RUBY_HELMET = new ArmorItem(RubyArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item RUBY_CHESTPLATE = new ArmorItem(RubyArmorMaterial.INSTANCE, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item RUBY_LEGGINGS = new ArmorItem(RubyArmorMaterial.INSTANCE, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item RUBY_BOOTS = new ArmorItem(RubyArmorMaterial.INSTANCE, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst"), AMETHYST);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_sword"), AMETHYST_SWORD);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_shovel"), AMETHYST_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_pickaxe"), AMETHYST_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_axe"), AMETHYST_AXE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_hoe"), AMETHYST_HOE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_helmet"), AMETHYST_HELMET);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_chestplate"), AMETHYST_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_leggings"), AMETHYST_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "amethyst_boots"), AMETHYST_BOOTS);

        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "emerald_sword"), EMERALD_SWORD);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "emerald_shovel"), EMERALD_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "emerald_pickaxe"), EMERALD_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "emerald_axe"), EMERALD_AXE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "emerald_hoe"), EMERALD_HOE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "emerald_helmet"), EMERALD_HELMET);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "emerald_chestplate"), EMERALD_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "emerald_leggings"), EMERALD_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "emerald_boots"), EMERALD_BOOTS);

        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "ruby"), RUBY);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "ruby_sword"), RUBY_SWORD);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "ruby_shovel"), RUBY_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "ruby_pickaxe"), RUBY_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "ruby_axe"), RUBY_AXE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "ruby_hoe"), RUBY_HOE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "ruby_helmet"), RUBY_HELMET);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "ruby_chestplate"), RUBY_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "ruby_leggings"), RUBY_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier("chaosawakens", "ruby_boots"), RUBY_BOOTS);
    }
}
