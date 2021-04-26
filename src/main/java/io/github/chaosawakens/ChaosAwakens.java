package io.github.chaosawakens;

import io.github.chaosawakens.data.ModItemModelGenerator;
import io.github.chaosawakens.data.ModLootTableProvider;
import io.github.chaosawakens.data.ModRecipes;
import io.github.chaosawakens.entities.EntEntity;
import io.github.chaosawakens.registry.ModAttributes;
import io.github.chaosawakens.registry.ModBlocks;
import io.github.chaosawakens.registry.ModEntityTypes;
import io.github.chaosawakens.registry.ModItems;
import io.github.chaosawakens.worldgen.OreGeneration;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(ChaosAwakens.MODID)
public class ChaosAwakens {

    public static final String MODID = "chaosawakens";
    public static final String MODNAME = "Chaos Awakens";

    public static ChaosAwakens INSTANCE;

    public static final Logger LOGGER = LogManager.getLogger();

    public static ResourceLocation locate(String name) {
        return new ResourceLocation(MODID, name);
    }

    public static String find(String name) {
        return MODID + ":" + name;
    }

    public ChaosAwakens() {
        INSTANCE = this;
        GeckoLib.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::gatherData);
        ModItems.ITEMS.register(eventBus);
        ModBlocks.ITEMS.register(eventBus);
        ModBlocks.BLOCKS.register(eventBus);
        ModAttributes.ATTRIBUTES.register(eventBus);
        ModEntityTypes.ENTITY_TYPES.register(eventBus);
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(GameEvents.class);
    }

    private void setup(FMLCommonSetupEvent event) {
        OreGeneration.registerOres();

        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(ModEntityTypes.ENT.get(), EntEntity.setCustomAttributes().create());
        });
    }

    private void gatherData(final GatherDataEvent event) {

        DataGenerator dataGenerator = event.getGenerator();
        final ExistingFileHelper existing = event.getExistingFileHelper();

        if(event.includeServer()) {
            dataGenerator.addProvider(new ModLootTableProvider(dataGenerator));
            dataGenerator.addProvider(new ModRecipes(dataGenerator));
            dataGenerator.addProvider(new ModItemModelGenerator(dataGenerator, existing));
        }
    }
}
