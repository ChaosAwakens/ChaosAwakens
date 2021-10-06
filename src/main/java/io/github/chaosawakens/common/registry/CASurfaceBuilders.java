package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.worldgen.surfacebuilder.StalagmiteValleySurfaceBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CASurfaceBuilders {
    public static final SurfaceBuilder<SurfaceBuilderConfig> STALAGMITE_VALLEY = new StalagmiteValleySurfaceBuilder(SurfaceBuilderConfig.CODEC);

    @SubscribeEvent
    public static void registerSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> event) {
        event.getRegistry().registerAll(
                STALAGMITE_VALLEY.setRegistryName(ChaosAwakens.MODID, "stalagmite_valley")
        );
    }

    public static final class Configs {
        public static final SurfaceBuilderConfig STALAGMITE_VALLEY = new SurfaceBuilderConfig(Blocks.STONE.defaultBlockState(), Blocks.STONE.defaultBlockState(), Blocks.STONE.defaultBlockState());
        public static final SurfaceBuilderConfig STONE = new SurfaceBuilderConfig(Blocks.STONE.defaultBlockState(), Blocks.STONE.defaultBlockState(), Blocks.STONE.defaultBlockState());
        public static final SurfaceBuilderConfig GRAVEL = new SurfaceBuilderConfig(Blocks.GRAVEL.defaultBlockState(), Blocks.GRAVEL.defaultBlockState(), Blocks.STONE.defaultBlockState());
        public static final SurfaceBuilderConfig ANDESITE = new SurfaceBuilderConfig(Blocks.ANDESITE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.STONE.defaultBlockState());
    }


    public static final class Configured {
        public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> STALAGMITE_VALLEY = CASurfaceBuilders.STALAGMITE_VALLEY.configured(Configs.STALAGMITE_VALLEY);

        private static <SC extends ISurfaceBuilderConfig> void register(String key, ConfiguredSurfaceBuilder<SC> builder) {
            WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(ChaosAwakens.MODID, key), builder);
        }

        public static void registerConfiguredSurfaceBuilders() {
            register("stalagmite_valley", STALAGMITE_VALLEY);
        }
    }
}
