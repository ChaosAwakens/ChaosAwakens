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
import java.util.Map;
import java.util.Map.Entry;

@EMCMapper
public class CAEMCValues implements IEMCMapper<NormalizedSimpleStack, Long> {

    private static final Map<NormalizedSimpleStack, Long> customEmcValues = new HashMap<>();

    @Override
    public String getName() {
        return "ChaosAwakensMapper";
    }

    @Override
    public String getDescription() {
        return "Adds EMC Values to Chaos Awakens Items.";
    }

    public static void init(){
        registerCustomEMC(NSSItem.createItem(CABlocks.BROWN_ANT_NEST.getId()), 3);
        registerCustomEMC(NSSItem.createItem(CABlocks.RAINBOW_ANT_NEST.getId()), 3);
        registerCustomEMC(NSSItem.createItem(CABlocks.RED_ANT_NEST.getId()), 3);
        registerCustomEMC(NSSItem.createItem(CABlocks.UNSTABLE_ANT_NEST.getId()), 3);
        registerCustomEMC(NSSItem.createItem(CABlocks.TERMITE_NEST.getId()), 3);
        registerCustomEMC(NSSItem.createItem(CABlocks.CRYSTAL_TERMITE_NEST.getId()), 3);

        registerCustomEMC(NSSItem.createItem(CABlocks.CRYSTAL_GRASS_BLOCK.getId()), 2);
        registerCustomEMC(NSSItem.createItem(CABlocks.KYANITE.getId()), 2);
        registerCustomEMC(NSSItem.createItem(CABlocks.CRYSTAL_LOG.getId()), 64);
        registerCustomEMC(NSSItem.createItem(CABlocks.CRYSTAL_WOOD.getId()), 64);
        registerCustomEMC(NSSItem.createItem(CABlocks.GREEN_CRYSTAL_LEAVES.getId()), 2);
        registerCustomEMC(NSSItem.createItem(CABlocks.RED_CRYSTAL_LEAVES.getId()), 2);
        registerCustomEMC(NSSItem.createItem(CABlocks.YELLOW_CRYSTAL_LEAVES.getId()), 2);
        registerCustomEMC(NSSItem.createItem(CABlocks.GOLDEN_MELON.getId()), 16528);

        registerCustomEMC(NSSItem.createItem(CAItems.LAVA_EEL.getId()), 128);
        registerCustomEMC(NSSItem.createItem(CAItems.SALT.getId()), 16);
        registerCustomEMC(NSSItem.createItem(CAItems.CRYSTAL_APPLE.getId()), 33024);
        registerCustomEMC(NSSItem.createItem(CAItems.DEAD_STINK_BUG.getId()), 192);
        registerCustomEMC(NSSItem.createItem(CAItems.PEACOCK_FEATHER.getId()), 144);
    }

    public static void registerCustomEMC(@Nonnull NormalizedSimpleStack stack, long emcValue) {
        customEmcValues.put(stack, emcValue);
    }

    public static void unregisterNSS(@Nonnull NormalizedSimpleStack stack) {
        customEmcValues.remove(stack);
    }

    @Override
    public void addMappings(IMappingCollector<NormalizedSimpleStack, Long> mapper, CommentedFileConfig config, DataPackRegistries registry, IResourceManager resourceManager) {

        for (Entry<NormalizedSimpleStack, Long> entry : customEmcValues.entrySet()) {
            NormalizedSimpleStack normStack = entry.getKey();
            long value = entry.getValue();
            mapper.setValueBefore(normStack, value);
        }
    }
}