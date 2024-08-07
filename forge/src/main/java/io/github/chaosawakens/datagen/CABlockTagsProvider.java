package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.BlockPropertyWrapper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CABlockTagsProvider extends BlockTagsProvider {

    public CABlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CAConstants.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return CAConstants.MOD_NAME.concat(": Block Tags");
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        BlockPropertyWrapper.getMappedBwps().forEach((blockSupEntry, curBwp) -> {
            List<TagKey<Block>> parentBlockTags = curBwp.getParentBlockTags();

            if (!parentBlockTags.isEmpty()){
                parentBlockTags.forEach(curBlockTag -> {
                    if (curBlockTag != null) {
                        CAConstants.LOGGER.debug("[Tagging Block]: " + blockSupEntry.get().getDescriptionId() + " -> " + curBlockTag);

                        tag(curBlockTag).add(blockSupEntry.get());
                    }
                });
            }
        });
    }
}
