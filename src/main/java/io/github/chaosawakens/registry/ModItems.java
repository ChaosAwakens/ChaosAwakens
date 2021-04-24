package io.github.chaosawakens.registry;

import io.github.chaosawakens.enums.ArmorMaterials;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.enums.ToolMaterials;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);

    // GEMST0NES
    public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> TIGERS_EYE = ITEMS.register("tigers_eye", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> TITANIUM = ITEMS.register("titanium", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> TITANIUM_NUGGET = ITEMS.register("titanium_nugget", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> URANIUM = ITEMS.register("uranium", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> URANIUM_NUGGET = ITEMS.register("uranium_nugget", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> ALUMINIUM = ITEMS.register("aluminium", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));

    // EMERALD TOOLS
    public static final RegistryObject<SwordItem> EMERALD_SWORD = ITEMS.register("emerald_sword", () -> new SwordItem(ToolMaterials.TOOL_EMERALD, 3, -2.4F, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<PickaxeItem> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe", () -> new PickaxeItem(ToolMaterials.TOOL_EMERALD, 1, -2.8F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ShovelItem> EMERALD_SHOVEL = ITEMS.register("emerald_shovel", () -> new ShovelItem(ToolMaterials.TOOL_EMERALD, 1, 1.2F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<AxeItem> EMERALD_AXE = ITEMS.register("emerald_axe", () -> new AxeItem(ToolMaterials.TOOL_EMERALD, 1, 1.2F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<HoeItem> EMERALD_HOE = ITEMS.register("emerald_hoe", () -> new HoeItem(ToolMaterials.TOOL_EMERALD, 1, 0.0F, new Item.Properties().group(ItemGroup.TOOLS)));
    // EMERALD ARMOR

    public static final RegistryObject<ArmorItem> EMERALD_HELMET = ITEMS.register("emerald_helmet", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> EMERALD_CHESTPLATE = ITEMS.register("emerald_chestplate", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> EMERALD_LEGGINGS = ITEMS.register("emerald_leggings", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> EMERALD_BOOTS = ITEMS.register("emerald_boots", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT)));

    // AMETHYST TOOLS
    public static final RegistryObject<SwordItem> AMETHYST_SWORD = ITEMS.register("amethyst_sword", () -> new SwordItem(ToolMaterials.TOOL_AMETHYST, 3, -2.4F, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<PickaxeItem> AMETHYST_PICKAXE = ITEMS.register("amethyst_pickaxe", () -> new PickaxeItem(ToolMaterials.TOOL_AMETHYST, 1, -2.8F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ShovelItem> AMETHYST_SHOVEL = ITEMS.register("amethyst_shovel", () -> new ShovelItem(ToolMaterials.TOOL_AMETHYST, 1, 1.2F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<AxeItem> AMETHYST_AXE = ITEMS.register("amethyst_axe", () -> new AxeItem(ToolMaterials.TOOL_AMETHYST, 1, 1.2F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<HoeItem> AMETHYST_HOE = ITEMS.register("amethyst_hoe", () -> new HoeItem(ToolMaterials.TOOL_AMETHYST, 1, 0.0F, new Item.Properties().group(ItemGroup.TOOLS)));
    // AMETHYST ARMOR

    public static final RegistryObject<ArmorItem> AMETHYST_HELMET = ITEMS.register("amethyst_helmet", () -> new ArmorItem(ArmorMaterials.AMETHYST, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> AMETHYST_CHESTPLATE = ITEMS.register("amethyst_chestplate", () -> new ArmorItem(ArmorMaterials.AMETHYST, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> AMETHYST_LEGGINGS = ITEMS.register("amethyst_leggings", () -> new ArmorItem(ArmorMaterials.AMETHYST, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> AMETHYST_BOOTS = ITEMS.register("amethyst_boots", () -> new ArmorItem(ArmorMaterials.AMETHYST, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT)));

    // RUBY TOOLS
    public static final RegistryObject<SwordItem> RUBY_SWORD = ITEMS.register("ruby_sword", () -> new SwordItem(ToolMaterials.TOOL_RUBY, 3, -2.4F, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> new PickaxeItem(ToolMaterials.TOOL_RUBY, 1, -2.8F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ShovelItem> RUBY_SHOVEL = ITEMS.register("ruby_shovel", () -> new ShovelItem(ToolMaterials.TOOL_RUBY, 1, 1.2F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<AxeItem> RUBY_AXE = ITEMS.register("ruby_axe", () -> new AxeItem(ToolMaterials.TOOL_RUBY, 1, 1.2F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<HoeItem> RUBY_HOE = ITEMS.register("ruby_hoe", () -> new HoeItem(ToolMaterials.TOOL_RUBY, 1, 0.0F, new Item.Properties().group(ItemGroup.TOOLS)));
    // RUBY ARMOR

    public static final RegistryObject<ArmorItem> RUBY_HELMET = ITEMS.register("ruby_helmet", () -> new ArmorItem(ArmorMaterials.RUBY, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate", () -> new ArmorItem(ArmorMaterials.RUBY, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> RUBY_LEGGINGS = ITEMS.register("ruby_leggings", () -> new ArmorItem(ArmorMaterials.RUBY, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> RUBY_BOOTS = ITEMS.register("ruby_boots", () -> new ArmorItem(ArmorMaterials.RUBY, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT)));

    // TIGERS EYE TOOLS
    public static final RegistryObject<SwordItem> TIGERS_EYE_SWORD = ITEMS.register("tigers_eye_sword", () -> new SwordItem(ToolMaterials.TOOL_TIGERS_EYE, 3, -2.4F, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<PickaxeItem> TIGERS_EYE_PICKAXE = ITEMS.register("tigers_eye_pickaxe", () -> new PickaxeItem(ToolMaterials.TOOL_TIGERS_EYE, 1, -2.8F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ShovelItem> TIGERS_EYE_SHOVEL = ITEMS.register("tigers_eye_shovel", () -> new ShovelItem(ToolMaterials.TOOL_TIGERS_EYE, 1, 1.2F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<AxeItem> TIGERS_EYE_AXE = ITEMS.register("tigers_eye_axe", () -> new AxeItem(ToolMaterials.TOOL_TIGERS_EYE, 1, 1.2F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<HoeItem> TIGERS_EYE_HOE = ITEMS.register("tigers_eye_hoe", () -> new HoeItem(ToolMaterials.TOOL_TIGERS_EYE, 1, 0.0F, new Item.Properties().group(ItemGroup.TOOLS)));
    // TIGERS EYE ARMOR

    public static final RegistryObject<ArmorItem> TIGERS_EYE_HELMET = ITEMS.register("tigers_eye_helmet", () -> new ArmorItem(ArmorMaterials.TIGERS_EYE, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> TIGERS_EYE_CHESTPLATE = ITEMS.register("tigers_eye_chestplate", () -> new ArmorItem(ArmorMaterials.TIGERS_EYE, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> TIGERS_EYE_LEGGINGS = ITEMS.register("tigers_eye_leggings", () -> new ArmorItem(ArmorMaterials.TIGERS_EYE, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<ArmorItem> TIGERS_EYE_BOOTS = ITEMS.register("tigers_eye_boots", () -> new ArmorItem(ArmorMaterials.TIGERS_EYE, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT)));
}