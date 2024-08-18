package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.standard.BlockPropertyWrapper;
import io.github.chaosawakens.api.tag.TagWrapper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
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
        if (!BlockPropertyWrapper.getMappedBwps().isEmpty()) {
            BlockPropertyWrapper.getMappedBwps().forEach((blockSupEntry, curBwp) -> {
                List<TagKey<?>> parentTags = curBwp.getParentTags();

                if (!parentTags.isEmpty()){
                    parentTags.forEach(curBlockTag -> {
                        if (curBlockTag != null && curBlockTag.isFor(Registries.BLOCK)) {
                            CAConstants.LOGGER.debug("[Tagging Block]: " + blockSupEntry.get().getDescriptionId() + " -> " + curBlockTag);

                            tag((TagKey<Block>) curBlockTag).add(blockSupEntry.get());
                        }
                    });
                }
            });
        }

        if (!TagWrapper.getCachedTWEntries().isEmpty()) {
            TagWrapper.getCachedTWEntries().forEach(twEntry -> {
                if (twEntry.getParentTag().isFor(Registries.BLOCK)) {
                    TagKey<?> curBlockTag = twEntry.getParentTag();

                    if (curBlockTag != null) {
                        twEntry.getPredefinedTagEntries().forEach(tagEntry -> {
                            Block blockTagEntry = (Block) tagEntry;

                            if (blockTagEntry != null) {
                                CAConstants.LOGGER.debug("[Tagging Block]: " + blockTagEntry.getDescriptionId() + " -> " + curBlockTag);

                                tag((TagKey<Block>) curBlockTag).add(blockTagEntry);
                            }
                        });

                        twEntry.getStoredTags().forEach(tagKeyEntry -> {
                            if (tagKeyEntry != null) {

                                CAConstants.LOGGER.debug("[Tagging Block Tag]: " + tagKeyEntry + " -> " + curBlockTag);

                                tag((TagKey<Block>) tagKeyEntry); // Force the existingFileHelper to track the tag to be added (otherwise throws exception). Goofy ahh patch.
                                tag((TagKey<Block>) curBlockTag).addTag((TagKey<Block>) tagKeyEntry);
                            }
                        });

                        twEntry.getParentTags().forEach(parentTagKeyEntry -> {
                            if (parentTagKeyEntry != null) {

                                CAConstants.LOGGER.debug("[Tagging Block Tag]: " + curBlockTag + " -> " + parentTagKeyEntry);

                                tag((TagKey<Block>) curBlockTag); // Force the existingFileHelper to track the tag to be added (otherwise throws exception). Goofy ahh patch.
                                tag((TagKey<Block>) parentTagKeyEntry).addTag((TagKey<Block>) curBlockTag);
                            }
                        });
                    }
                }
            });
        }
    }
}
