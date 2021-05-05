package io.github.chaosawakens.items;

import io.github.chaosawakens.entities.ModEntities;
import io.github.chaosawakens.items.util.RegisterItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;

public class SpawnEggs implements ModInitializer {
    public static final Item ENT_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item RED_ANT_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item BROWN_ANT_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item RAINBOW_ANT_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item UNSTABLE_ANT_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item TERMITE_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item HERCULES_BEETLE_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item RUBY_BUG_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item EMERALD_GATOR_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item ROBO_SNIPER_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item BEAVER_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item APPLE_COW_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item GOLDEN_APPLE_COW_EGG = new SpawnEggItem(ModEntities.ENT, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item IRON_GOLEM_EGG = new SpawnEggItem(EntityType.IRON_GOLEM, 0, 0, new Item.Settings().group(ItemGroup.MISC));
    public static final Item SNOW_GOLEM_EGG = new SpawnEggItem(EntityType.SNOW_GOLEM, 0, 0, new Item.Settings().group(ItemGroup.MISC));

    @Override
    public void onInitialize() {
        RegisterItem.register("ent_spawn_egg", ENT_EGG);
        RegisterItem.register("red_ant_spawn_egg", RED_ANT_EGG);
        RegisterItem.register("brown_ant_spawn_egg", BROWN_ANT_EGG);
        RegisterItem.register("rainbow_ant_spawn_egg", RAINBOW_ANT_EGG);
        RegisterItem.register("unstable_ant_spawn_egg", UNSTABLE_ANT_EGG);
        RegisterItem.register("termite_spawn_egg", TERMITE_EGG);
        RegisterItem.register("hercules_beetle_spawn_egg", HERCULES_BEETLE_EGG);
        RegisterItem.register("ruby_bug_spawn_egg", RUBY_BUG_EGG);
        RegisterItem.register("emerald_gator_spawn_egg", EMERALD_GATOR_EGG);
        RegisterItem.register("robo_sniper_spawn_egg", ROBO_SNIPER_EGG);
        RegisterItem.register("beaver_spawn_egg", BEAVER_EGG);
        RegisterItem.register("apple_cow_spawn_egg", APPLE_COW_EGG);
        RegisterItem.register("golden_apple_cow_spawn_egg", GOLDEN_APPLE_COW_EGG);
        RegisterItem.register("iron_golem_spawn_egg", IRON_GOLEM_EGG);
        RegisterItem.register("snow_golem_spawn_egg", SNOW_GOLEM_EGG);
    }
}