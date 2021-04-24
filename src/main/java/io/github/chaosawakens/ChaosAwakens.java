package io.github.chaosawakens;

import io.github.chaosawakens.registry.ModBlocks;
import io.github.chaosawakens.registry.ModItems;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ChaosAwakens.MODID)
public class ChaosAwakens {

    public static final String MODID = "chaosawakens";

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

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(eventBus);
        ModBlocks.ITEMS.register(eventBus);
        ModBlocks.BLOCKS.register(eventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation location(String name) {
        return new ResourceLocation(MODID, name);
    }
}
