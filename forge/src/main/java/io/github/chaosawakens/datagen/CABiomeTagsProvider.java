package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.services.ForgeRegistrar;
import io.github.chaosawakens.api.tag.DataPackTagWrapper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DataPackRegistriesHooks;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

public class CABiomeTagsProvider extends BiomeTagsProvider {


    public CABiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registries.thenApply(r -> constructRegistries(r, ForgeRegistrar.getDatapackRegistrySetBuilder())), CAConstants.MODID, existingFileHelper);
    }


    @Override
    public String getName() {
        return CAConstants.MOD_NAME.concat(": Biome Tags");
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        if (!DataPackTagWrapper.getCachedTWEntries().isEmpty()) {
            DataPackTagWrapper.getCachedTWEntries().forEach(twEntry -> {
                TagKey<?> parentTagKey = twEntry.getParentTag().get();

                if (parentTagKey.isFor(Registries.BIOME)) {
                    twEntry.getPredefinedTagEntries().forEach(tagEntry -> {
                        ResourceKey<Biome> biomeTagEntry = (ResourceKey<Biome>) tagEntry.get();

                        if (biomeTagEntry != null) {
                            CAConstants.LOGGER.debug("[Tagging Biome]: {} -> {}", biomeTagEntry.location(), parentTagKey);

                            tag((TagKey<Biome>) parentTagKey).add(biomeTagEntry);
                        }
                    });

                    twEntry.getStoredTags().forEach(tagKeyEntry -> {
                        TagKey<?> storedTagKeyEntry = tagKeyEntry.get();

                        if (storedTagKeyEntry != null) {
                            CAConstants.LOGGER.debug("[Tagging Biome Tag]: {} -> {}", storedTagKeyEntry, parentTagKey);

                            tag((TagKey<Biome>) storedTagKeyEntry); // Force the existingFileHelper to track the tag to be added (otherwise throws exception). Goofy ahh patch.
                            tag((TagKey<Biome>) parentTagKey).addTag((TagKey<Biome>) storedTagKeyEntry);
                        }
                    });

                    twEntry.getParentTags().forEach(parentTagKeyEntry -> {
                        TagKey<?> storedParentTagKeyEntry = parentTagKeyEntry.get();

                        if (storedParentTagKeyEntry != null) {
                            CAConstants.LOGGER.debug("[Tagging Biome Tag]: {} -> {}", parentTagKey, storedParentTagKeyEntry);

                            tag((TagKey<Biome>) parentTagKey); // Force the existingFileHelper to track the tag to be added (otherwise throws exception). Goofy ahh patch.
                            tag((TagKey<Biome>) storedParentTagKeyEntry).addTag((TagKey<Biome>) parentTagKey);
                        }
                    });
                }
            });
        }
    }

    private static HolderLookup.Provider constructRegistries(HolderLookup.Provider original, RegistrySetBuilder datapackEntriesBuilder) {
        var builderKeys = new HashSet<>(datapackEntriesBuilder.getEntryKeys());
        DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().filter(data -> !builderKeys.contains(data.key())).forEach(data -> datapackEntriesBuilder.add(data.key(), context -> {}));
        return datapackEntriesBuilder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), original);
    }
}
