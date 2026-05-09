package io.github.chaosawakens.content.item.misc;

import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.loader.ModLoader;
import com.mememan.nexus.platform.NexusServices;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

public class DeferredItemNameBlockItem extends ItemNameBlockItem { // Required cuz classloading on Fabric + eager value resolution for blocks/items that depend on each other break things (we could technically group registration as other native solutions do in Nexus + other libraries/mods, but then CMT order would be annoying to deal with)
    private static final Map<Supplier<Supplier<? extends Block>>, Supplier<? extends Item>> DEFERRED_BLOCK_2_ITEM_MAP = new Object2ObjectOpenHashMap<>();
    private final Supplier<Supplier<? extends Block>> blockSup;

    public DeferredItemNameBlockItem(Supplier<Supplier<? extends Block>> blockSup, Properties properties) {
        super(NexusServices.PLATFORM_MANAGER.getPlatform().equals(ModLoader.FABRIC) ? Blocks.AIR : blockSup.get().get(), properties);

        this.blockSup = blockSup;

        if (NexusServices.PLATFORM_MANAGER.getPlatform().equals(ModLoader.FABRIC)) DEFERRED_BLOCK_2_ITEM_MAP.put(blockSup, () -> this);
    }

    @Override
    public void registerBlocks(Map<Block, Item> blockToItemMap, Item item) {
        if (NexusServices.PLATFORM_MANAGER.getPlatform().equals(ModLoader.FORGE)) super.registerBlocks(blockToItemMap, item);
    }

    @Override
    public @NotNull Block getBlock() {
        return NexusServices.PLATFORM_MANAGER.getPlatform().equals(ModLoader.FABRIC) ? blockSup.get().get() : super.getBlock();
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return NexusServices.PLATFORM_MANAGER.getPlatform().equals(ModLoader.FABRIC) ? !(getBlock() instanceof ShulkerBoxBlock) : super.canFitInsideContainerItems();
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        if (NexusServices.PLATFORM_MANAGER.getPlatform().equals(ModLoader.FORGE)) super.onDestroyed(itemEntity);
        else if (getBlock() instanceof ShulkerBoxBlock) {
            ItemStack itemStack = itemEntity.getItem();
            CompoundTag compoundTag = getBlockEntityData(itemStack);

            if (compoundTag != null && compoundTag.contains("Items", 9)) {
                ListTag listTag = compoundTag.getList("Items", 10);
                ItemUtils.onContainerDestroyed(itemEntity, listTag.stream().map(CompoundTag.class::cast).map(ItemStack::of));
            }
        }
    }

    @RegistrarEntry(priority = -2)
    private static class DeferredBlock2ItemMapHandler {

        static {
            if (NexusServices.PLATFORM_MANAGER.getPlatform().equals(ModLoader.FABRIC)) {
                DEFERRED_BLOCK_2_ITEM_MAP.forEach((blockSup, itemSup) -> {
                    Item.BY_BLOCK.put(blockSup.get().get(), itemSup.get());
                });
            }
        }
    }
}
