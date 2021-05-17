package io.github.chaosawakens.common.integration;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import moze_intel.projecte.api.mapper.EMCMapper;
import moze_intel.projecte.api.mapper.IEMCMapper;
import moze_intel.projecte.api.mapper.collector.IMappingCollector;
import moze_intel.projecte.api.nss.NSSItem;
import moze_intel.projecte.api.nss.NormalizedSimpleStack;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.IResourceManager;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@EMCMapper
public class CAEMCValues implements IEMCMapper<NormalizedSimpleStack, Long> {

    private static final Map<NormalizedSimpleStack, Long> customEmcValues = new HashMap();

    @Override
    public String getName() {
        return "ChaosAwakensMapper";
    }

    @Override
    public String getDescription() {
        return "Adds EMC Values to Chaos Awakens Items.";
    }

    public static void init(){
        registerCustomEMC(NSSItem.createItem(CABlocks.CRYSTAL_GRASS_BLOCK.getId()), 2);
        registerCustomEMC(NSSItem.createItem(CABlocks.KYANITE.getId()), 2);
        registerCustomEMC(NSSItem.createItem(CABlocks.CRYSTAL_LOG.getId()), 64);
        registerCustomEMC(NSSItem.createItem(CABlocks.CRYSTAL_WOOD.getId()), 64);
        registerCustomEMC(NSSItem.createItem(CABlocks.CRYSTAL_WOOD_PLANKS.getId()), 16);
        registerCustomEMC(NSSItem.createItem(CABlocks.GREEN_CRYSTAL_LEAVES.getId()), 2);
        registerCustomEMC(NSSItem.createItem(CABlocks.RED_CRYSTAL_LEAVES.getId()), 2);
        registerCustomEMC(NSSItem.createItem(CABlocks.YELLOW_CRYSTAL_LEAVES.getId()), 2);
        registerCustomEMC(NSSItem.createItem(CABlocks.CRYSTAL_CRAFTING_TABLE.getId()), 64);
        registerCustomEMC(NSSItem.createItem(CABlocks.CRYSTAL_FURNACE.getId()), 16);
        registerCustomEMC(NSSItem.createItem(CABlocks.GOLDEN_MELON.getId()), 16528);
        registerCustomEMC(NSSItem.createItem(CAItems.CRYSTAL_WOOD_SHARD.getId()), 8);
        registerCustomEMC(NSSItem.createItem(CAItems.LAVA_EEL.getId()), 128);
        registerCustomEMC(NSSItem.createItem(CAItems.SALT.getId()), 16);
        registerCustomEMC(NSSItem.createItem(CAItems.CRYSTAL_APPLE.getId()), 33024);
    }

    public static void registerCustomEMC(@Nonnull NormalizedSimpleStack stack, long emcValue) {
        customEmcValues.put(stack, emcValue);
    }

    public static void unregisterNSS(@Nonnull NormalizedSimpleStack stack) {
        customEmcValues.remove(stack);
    }

    @Override
    public void addMappings(IMappingCollector<NormalizedSimpleStack, Long> mapper, CommentedFileConfig config, DataPackRegistries registry, IResourceManager resourceManager) {

        Iterator var4 = customEmcValues.entrySet().iterator();

        while (var4.hasNext()) {
            Entry<NormalizedSimpleStack, Long> entry = (Entry) var4.next();
            NormalizedSimpleStack normStack = entry.getKey();
            long value = entry.getValue();
            mapper.setValueBefore(normStack, value);
        }
    }
}