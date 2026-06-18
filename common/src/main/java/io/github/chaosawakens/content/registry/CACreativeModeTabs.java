package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.template.property_wrapper.CreativeModeTabPropertyWrapperTemplates;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.block.mining.fossil.FossilBlock;
import io.github.chaosawakens.content.block.mining.fossil.FossilBlockInstance;
import io.github.chaosawakens.util.PredicateUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;

import java.util.function.Supplier;
import java.util.stream.Collectors;

@RegistrarEntry
public final class CACreativeModeTabs {
    protected static final ObjectArrayList<Supplier<CreativeModeTab>> CREATIVE_MODE_TABS = new ObjectArrayList<>();

    public static final Supplier<CreativeModeTab> BLOCKS = CreativeModeTabPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("blocks"),
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.translatable("creative_mode_tab.chaosawakens.blocks"))
                    .icon(() -> CABlocks.BLOCKS.stream().findFirst().orElse(CABlocks.ROYAL_GUARDIAN_SCALE_BLOCK).get().asItem().getDefaultInstance())
                    .displayItems((param, output) -> output.acceptAll(CABlocks.BLOCK_ITEMS.stream()
                            .map(Supplier::get)
                            .filter(BlockItem.class::isInstance) // JIC
                            .filter(curBlockItem -> !PredicateUtil.hasSpecifiedCreativeModeTabs(((BlockItem) curBlockItem).getBlock()))
                            .map(Item::getDefaultInstance)
                            .collect(Collectors.toCollection(ObjectArrayList::new))))
                    .build(), CREATIVE_MODE_TABS)
            .withCustomName("Chaos Awakens: Blocks")
            .buildAndGet();

    public static final Supplier<CreativeModeTab> FOSSILS = CreativeModeTabPropertyWrapperTemplates.registerAndReflectAndChain(CAConstants.prefix("fossils"),
                    () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                            .title(Component.translatable("creative_mode_tab.chaosawakens.fossils"))
                            .icon(() -> CABlocks.BLOCKS.stream().filter(curBlockSup -> curBlockSup.get() instanceof FossilBlock).findFirst().map(Supplier::get).orElse(CABlocks.FOSSILIZED_HERCULES_BEETLE_STONE.get()).asItem().getDefaultInstance())
                            .displayItems((param, output) -> output.acceptAll(CABlocks.BLOCK_ITEMS.stream()
                                    .map(Supplier::get)
                                    .filter(BlockItem.class::isInstance) // JIC
                                    .filter(curBlockItem -> ((BlockItem) curBlockItem).getBlock() instanceof FossilBlockInstance)
                                    .map(Item::getDefaultInstance)
                                    .collect(Collectors.toCollection(ObjectArrayList::new))))
                            .build(), CREATIVE_MODE_TABS)
            .withCustomName("Chaos Awakens: Fossils")
            .buildAndGet();

    public static final Supplier<CreativeModeTab> ITEMS = CreativeModeTabPropertyWrapperTemplates.registerAndReflectAndChain(CAConstants.prefix("items"),
                    () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                            .title(Component.translatable("creative_mode_tab.chaosawakens.items"))
                            .icon(() -> CAItems.ITEMS.stream().filter(curItemSup -> !PredicateUtil.hasSpecifiedCreativeModeTabs(curItemSup)).findFirst().orElse(CAItems.TITANIUM_INGOT).get().getDefaultInstance())
                            .displayItems((param, output) -> output.acceptAll(CAItems.ITEMS.stream()
                                    .map(Supplier::get)
                                    .filter(curItem -> !PredicateUtil.hasSpecifiedCreativeModeTabs(curItem))
                                    .map(Item::getDefaultInstance)
                                    .collect(Collectors.toCollection(ObjectArrayList::new))))
                            .build(), CREATIVE_MODE_TABS)
            .withCustomName("Chaos Awakens: Items")
            .buildAndGet();

    public static final Supplier<CreativeModeTab> EQUIPMENT = CreativeModeTabPropertyWrapperTemplates.registerAndReflectAndChain(CAConstants.prefix("equipment"),
                    () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                            .title(Component.translatable("creative_mode_tab.chaosawakens.equipment"))
                            .icon(() -> CAItems.EQUIPMENT.stream().filter(curItem -> curItem.get() instanceof TieredItem).findFirst().orElse(CAItems.BIG_BERTHA::get).get().getDefaultInstance())
                            .displayItems((param, output) -> output.acceptAll(CAItems.EQUIPMENT.stream()
                                    .map(Supplier::get)
                                    .map(Item::getDefaultInstance)
                                    .collect(Collectors.toCollection(ObjectArrayList::new))))
                            .build(), CREATIVE_MODE_TABS)
            .withCustomName("Chaos Awakens: Equipment")
            .buildAndGet();

    public static final Supplier<CreativeModeTab> FOOD = CreativeModeTabPropertyWrapperTemplates.registerAndReflectAndChain(CAConstants.prefix("food"),
                     () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                             .title(Component.translatable("creative_mode_tab.chaosawakens.food"))
                             .icon(() -> CAItems.ITEMS.stream().filter(PredicateUtil::isFood).findFirst().orElse(CAItems.BLT_SANDWICH).get().getDefaultInstance())
                             .displayItems((param, output) -> output.acceptAll(CAItems.ITEMS.stream()
                                     .filter(PredicateUtil::isFood)
                                     .map(Supplier::get)
                                     .map(Item::getDefaultInstance)
                                     .collect(Collectors.toCollection(ObjectArrayList::new))))
                             .build(), CREATIVE_MODE_TABS)
             .withCustomName("Chaos Awakens: Food")
             .buildAndGet();
    /*
    // Only to be accepted once entities are in the game

    public static final Supplier<CreativeModeTab> SPAWN_EGGS = CreativeModeTabPropertyWrapperTemplates.registerAndReflectAndChain(CAConstants.prefix("spawn_eggs"),
                    () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                            .title(Component.translatable("creative_mode_tab.chaosawakens.spawn_eggs"))
                            .icon(() -> CAItems.ITEMS.stream().filter().findFirst().orElse(CAItems.RED_ANT_SPAWNEGG).get().getDefaultInstance())
                            .displayItems((param, output) -> output.acceptAll(CAItems.ITEMS.stream()
                                    .filter(PredicateUtil::isFood)
                                    .map(Supplier::get)
                                    .map(Item::getDefaultInstance)
                                    .collect(Collectors.toCollection(ObjectArrayList::new))))
                            .build(), CREATIVE_MODE_TABS)
            .withCustomName("Chaos Awakens: Spawns")
            .buildAndGet();
    */
    public static ImmutableList<Supplier<CreativeModeTab>> getCreativeModeTabs() {
        return ImmutableList.copyOf(CREATIVE_MODE_TABS);
    }
}
