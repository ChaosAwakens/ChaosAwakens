package io.github.chaosawakens.datagen;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import io.github.chaosawakens.CAConstants;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class CodecBasedDataProvider<T> implements DataProvider {
    protected final String modId;
    protected final PackOutput.PathProvider outputPath;
    protected final Codec<T> elementCodec;
    protected final Object2ObjectOpenHashMap<ResourceLocation, T> elementsToSerialize = new Object2ObjectOpenHashMap<>();

    public CodecBasedDataProvider(String modId, String baseOutputDir, PackOutput targetOutput, Codec<T> elementCodec) {
        this.modId = modId;
        this.outputPath = targetOutput.createPathProvider(PackOutput.Target.DATA_PACK, baseOutputDir);
        this.elementCodec = elementCodec;
    }

    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput pOutput) {
        elementsToSerialize.clear();

        addDataEntries();

        if (!elementsToSerialize.isEmpty()) {
            return CompletableFuture.allOf(elementsToSerialize.entrySet().stream().map(curEntry -> {
                ResourceLocation curElementId = curEntry.getKey();
                T curElement = curEntry.getValue();

                if (curElement != null) {
                    Optional<JsonElement> serializedElement = elementCodec.encodeStart(JsonOps.INSTANCE, curElement)
                            .resultOrPartial(errorMsg -> CAConstants.LOGGER.warn("Failed to serialize element {} with error: {}", curElement, errorMsg));

                    if (serializedElement.isEmpty()) {
                        CAConstants.LOGGER.warn("Skipping serialization of empty element {}.", curElement);
                        return CompletableFuture.completedFuture(null);
                    }

                    return DataProvider.saveStable(pOutput, serializedElement.get(), outputPath.json(curElementId));
                } else {
                    CAConstants.LOGGER.warn("Skipping serialization of null element.");
                    return CompletableFuture.completedFuture(null);
                }
            }).toArray(CompletableFuture[]::new));
        }

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public @NotNull String getName() {
        return String.format("Codec-Based Data Provider [%s]", outputPath.json(CAConstants.prefix("")));
    }

    protected abstract void addDataEntries();

    protected void putElement(ResourceLocation elementId, T element) {
        if (elementsToSerialize.put(elementId, element) != null) CAConstants.LOGGER.warn("Overriding duplicate element with ID {}: {}", elementId, element);
    }
}
