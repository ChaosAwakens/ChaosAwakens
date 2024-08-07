package io.github.chaosawakens.datagen;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.BlockPropertyWrapper;
import io.github.chaosawakens.api.block.BlockStateDefinition;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CABlockStateProvider extends BlockStateProvider {
    protected final Object2ObjectLinkedOpenHashMap<Block, BlockStateGenerator> mappedVanillaGenerators = new Object2ObjectLinkedOpenHashMap<>();
    protected final PackOutput visibleOutput;

    public CABlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CAConstants.MODID, exFileHelper);
        this.visibleOutput = output;
    }

    @NotNull
    @Override
    public String getName() {
        return CAConstants.MOD_NAME.concat(": Block States");
    }

    @Override
    protected void registerStatesAndModels() {
        if (!BlockPropertyWrapper.getMappedBwps().isEmpty()) {
            BlockPropertyWrapper.getMappedBwps().forEach((blockSupEntry, curBwp) -> {
                BlockStateDefinition curStateDef = curBwp.getBlockStateDefinitionMappingFunction() == null ? null : curBwp.getBlockStateDefinitionMappingFunction().apply(blockSupEntry);

                if (curStateDef != null) {
                    CAConstants.LOGGER.debug("[Setting BlockState]: " + blockSupEntry.get().getDescriptionId());

                    BlockStateGenerator curStateGen = curStateDef.getBlockStateSupplier();
                    mappedVanillaGenerators.putIfAbsent(blockSupEntry.get(), curStateGen);
                }
            });
        }
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) { //TODO Buncha boilerplate 2 remove l8r
        models().generatedModels.clear();
        itemModels().generatedModels.clear();
        registeredBlocks.clear();
        registerStatesAndModels();
        CompletableFuture<?>[] futures = new CompletableFuture<?>[2 + this.mappedVanillaGenerators.size()];
        int i = 0;
        futures[i++] = generateAllFor(models(), cache);
        futures[i++] = generateAllFor(itemModels(), cache);
        for (Map.Entry<Block, BlockStateGenerator> entry : mappedVanillaGenerators.entrySet()) {
            futures[i++] = saveBlockState(cache, entry.getValue().get().getAsJsonObject(), entry.getKey());
        }
        return CompletableFuture.allOf(futures);
    }

    protected CompletableFuture<?> saveBlockState(CachedOutput cache, JsonObject stateJson, Block owner) {
        ResourceLocation blockName = Preconditions.checkNotNull(ForgeRegistries.BLOCKS.getKey(owner));
        Path outputPath = this.visibleOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(blockName.getNamespace()).resolve("blockstates").resolve(blockName.getPath() + ".json");
        return DataProvider.saveStable(cache, stateJson, outputPath);
    }

    protected <T extends ModelBuilder<T>> CompletableFuture<?> generateAllFor(ModelProvider<T> targetProvider, CachedOutput cache) {
        CompletableFuture<?>[] futures = new CompletableFuture<?>[targetProvider.generatedModels.size()];
        int i = 0;

        for (T model : targetProvider.generatedModels.values()) {
            Path target = getPathFor(targetProvider, model);
            futures[i++] = DataProvider.saveStable(cache, model.toJson(), target);
        }

        return CompletableFuture.allOf(futures);
    }

    protected <T extends ModelBuilder<T>> Path getPathFor(ModelProvider<T> targetProvider, T model) {
        ResourceLocation loc = model.getLocation();
        return visibleOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(loc.getNamespace()).resolve("models").resolve(loc.getPath() + ".json");
    }
}
