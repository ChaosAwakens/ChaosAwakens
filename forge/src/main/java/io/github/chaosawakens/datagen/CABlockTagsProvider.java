package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.standard.BlockPropertyWrapper;
import io.github.chaosawakens.api.tag.TagWrapper;
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
        if (!BlockPropertyWrapper.getMappedBwps().isEmpty()) {
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

        if (!TagWrapper.getCachedTWEntries().isEmpty()) {
            TagWrapper.getCachedTWEntries().forEach(twEntry -> {
                if (twEntry.isTagOfType(Block.class)) {
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
                            if (tagKeyEntry != null && tagKeyEntry.getClass().getGenericSuperclass().getClass().isAssignableFrom(Block.class)) {
                                TagKey<?> tkEntryGRep = tagKeyEntry;

                                CAConstants.LOGGER.debug("[Tagging Block Tag]: " + tkEntryGRep + " -> " + curBlockTag);

                                tag((TagKey<Block>) curBlockTag).addTag((TagKey<Block>) tkEntryGRep);
                            }
                        });

                        twEntry.getParentTags().forEach(parentTagKeyEntry -> {
                            if (parentTagKeyEntry != null && parentTagKeyEntry.getClass().getGenericSuperclass().getClass().isAssignableFrom(Block.class)) {
                                TagKey<?> tkParentEntryGRep = parentTagKeyEntry;

                                CAConstants.LOGGER.debug("[Tagging Block Tag]: " + curBlockTag + " -> " + tkParentEntryGRep);

                                tag((TagKey<Block>) tkParentEntryGRep).addTag((TagKey<Block>) curBlockTag);
                            }
                        });
                    }
                }
            });
        }
    }
}
