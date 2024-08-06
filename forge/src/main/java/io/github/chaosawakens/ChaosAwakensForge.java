package io.github.chaosawakens;

import io.github.chaosawakens.datagen.CABlockModelProvider;
import io.github.chaosawakens.datagen.CABlockTagsProvider;
import io.github.chaosawakens.datagen.CALanguageProvider;
import io.github.chaosawakens.datagen.CALootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.concurrent.CompletableFuture;

@Mod(CAConstants.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChaosAwakensForge {

    public ChaosAwakensForge() {
        ChaosAwakens.setup();
    }

    @SubscribeEvent
    public static void onFMLCommonSetupEvent(FMLCommonSetupEvent event) {
        ChaosAwakens.deferredSetup();
    }

    @SubscribeEvent
    public static void onGatherDataEvent(GatherDataEvent event) {
        DataGenerator datagen = event.getGenerator();
        PackOutput datagenPackOutput = datagen.getPackOutput();
        final ExistingFileHelper curFileHelper = event.getExistingFileHelper();
        final CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Resources
        datagen.addProvider(event.includeClient(), new CALanguageProvider(datagenPackOutput));

        datagen.addProvider(event.includeClient(), new CABlockModelProvider(datagenPackOutput, curFileHelper));

        // Data
        datagen.addProvider(event.includeServer(), new CALootTableProvider(datagenPackOutput));

        datagen.addProvider(event.includeServer(), new CABlockTagsProvider(datagenPackOutput, lookupProvider, curFileHelper));
    }
}