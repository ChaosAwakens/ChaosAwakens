package io.github.chaosawakens.events.client;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.standard.BlockPropertyWrapper;
import io.github.chaosawakens.api.client.WrappedClampedItemPropertyFunction;
import io.github.chaosawakens.api.entity.EntityTypePropertyWrapper;
import io.github.chaosawakens.api.item.ItemPropertyWrapper;
import io.github.chaosawakens.common.registry.CAClientDataEntries;
import io.github.chaosawakens.util.ClientUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Optional;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = CAConstants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ChaosAwakensForgeClientSetupEvents {

    @SubscribeEvent
    public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
        // Item Model Properties
        ItemPropertyWrapper.getMappedIpws().entrySet().stream().filter(curEntry -> !curEntry.getValue().getCustomModelOverrideFunctions().isEmpty()).forEach(curEntry -> {
            Supplier<Item> itemSupEntry = curEntry.getKey();
            Object2ObjectOpenHashMap<ResourceLocation, WrappedClampedItemPropertyFunction> definedModelPredicateFunctions = curEntry.getValue().getCustomModelOverrideFunctions();

            definedModelPredicateFunctions.forEach((curName, curFunc) -> ItemProperties.register(itemSupEntry.get(), curName, ClientUtil.toClampedItemPropertyFunction(curFunc)));
        });
    }

    @SubscribeEvent
    public static void onRegisterEntityRenderersEvent(EntityRenderersEvent.RegisterRenderers event) {
        EntityTypePropertyWrapper.getMappedEtpws().forEach((parentEntityTypeSup, curEtpw) -> {
            Optional.of(curEtpw.getClientDataEntry().get()).ifPresentOrElse(
                    curEntry -> event.registerEntityRenderer(parentEntityTypeSup.get(), (ctx) -> curEntry.renderFactory().apply(() -> ctx).get()),
                    () -> CAClientDataEntries.getClientDataEntries().stream()
                            .filter(curEntry -> BuiltInRegistries.ENTITY_TYPE.getKey(parentEntityTypeSup.get()).equals(curEntry.get().entityTypeId()))
                            .findFirst()
                            .ifPresent(curEntry -> event.registerEntityRenderer(parentEntityTypeSup.get(), (ctx) -> curEntry.get().renderFactory().apply(() -> ctx).get())));
        });
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitionsEvent(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CAClientDataEntries.getClientDataEntries().forEach(((curEntry) -> event.registerLayerDefinition(curEntry.get().modelPair().left().get(), () -> curEntry.get().modelPair().right().get())));
    }

    @SubscribeEvent
    public static void onRegisterBlockColorHandlersEvent(RegisterColorHandlersEvent.Block event) {
        BlockPropertyWrapper.getMappedBpws().entrySet().stream().filter(curBwpEntry -> curBwpEntry.getValue().getBlockColorMappingFunc() != null).forEach(curBwpEntry -> {
            Supplier<Block> blockSupEntry = curBwpEntry.getKey();
            BlockColor curMappedBlockColor = ClientUtil.toBlockColor(curBwpEntry.getValue().getBlockColorMappingFunc().apply(blockSupEntry));

            event.register(curMappedBlockColor, blockSupEntry.get());
        });
    }

    @SubscribeEvent
    public static void onRegisterBlockColorHandlersEvent(RegisterColorHandlersEvent.Item event) {
        BlockPropertyWrapper.getMappedBpws().entrySet().stream().filter(curBwpEntry -> curBwpEntry.getValue().getBlockColorMappingFunc() != null).forEach(curBwpEntry -> {
            Supplier<Block> blockSupEntry = curBwpEntry.getKey();

            event.register((curStack, tintIdx) -> event.getBlockColors().getColor(blockSupEntry.get().defaultBlockState(), null, null, tintIdx), blockSupEntry.get());
        });
    }
}
