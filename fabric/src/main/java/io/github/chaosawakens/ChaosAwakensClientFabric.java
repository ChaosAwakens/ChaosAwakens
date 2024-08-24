package io.github.chaosawakens;

import io.github.chaosawakens.api.datagen.block.BlockModelDefinition;
import io.github.chaosawakens.api.block.standard.BlockPropertyWrapper;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

/**
 * Needed for some loader-specific optimizations/implementations
 */
public class ChaosAwakensClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        handleBlockRenderLayers();
    }

    private static void handleBlockRenderLayers() {
        BlockPropertyWrapper.getMappedBpws().forEach((blockSupEntry, curBwp) -> {
            ObjectArrayList<BlockModelDefinition> allPresentModels = new ObjectArrayList<>(curBwp.getBlockModelDefinitions());

            if (!allPresentModels.isEmpty()) {
                allPresentModels.forEach((curBmd) -> {
                    if (curBmd.getBlockModelRenderType() != null) {
                        String renderTypeName = curBmd.getBlockModelRenderType().getPath();

                        // Gotta do hardcoded rendertype checks like I'm yanderedev because they decided not to have ANY FORM OF ACCESSORS FOR THE RENDERLAYERMAPS. FRICK U FABRIC!!!
                        if (renderTypeName.equals(RenderType.cutout().name)) BlockRenderLayerMap.INSTANCE.putBlock(blockSupEntry.get(), RenderType.cutout());
                        if (renderTypeName.equals(RenderType.cutoutMipped().name)) BlockRenderLayerMap.INSTANCE.putBlock(blockSupEntry.get(), RenderType.cutoutMipped());
                        if (renderTypeName.equals(RenderType.translucent().name)) BlockRenderLayerMap.INSTANCE.putBlock(blockSupEntry.get(), RenderType.translucent());
                        if (renderTypeName.equals(RenderType.translucentMovingBlock().name)) BlockRenderLayerMap.INSTANCE.putBlock(blockSupEntry.get(), RenderType.translucentMovingBlock());
                        if (renderTypeName.equals(RenderType.translucentNoCrumbling().name)) BlockRenderLayerMap.INSTANCE.putBlock(blockSupEntry.get(), RenderType.translucentNoCrumbling());
                        if (renderTypeName.equals(RenderType.glintTranslucent().name)) BlockRenderLayerMap.INSTANCE.putBlock(blockSupEntry.get(), RenderType.glintTranslucent());
                        if (renderTypeName.equals(RenderType.glint().name)) BlockRenderLayerMap.INSTANCE.putBlock(blockSupEntry.get(), RenderType.glint());
                        if (renderTypeName.equals(RenderType.glintDirect().name)) BlockRenderLayerMap.INSTANCE.putBlock(blockSupEntry.get(), RenderType.glintDirect());
                        if (renderTypeName.equals(RenderType.waterMask().name)) BlockRenderLayerMap.INSTANCE.putBlock(blockSupEntry.get(), RenderType.waterMask());
                        if (renderTypeName.equals(RenderType.tripwire().name)) BlockRenderLayerMap.INSTANCE.putBlock(blockSupEntry.get(), RenderType.tripwire());
                    }
                });
            }
        });
    }
}
