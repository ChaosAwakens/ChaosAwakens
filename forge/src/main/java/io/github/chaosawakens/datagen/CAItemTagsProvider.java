package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.standard.BlockPropertyWrapper;
import io.github.chaosawakens.api.item.ItemPropertyWrapper;
import io.github.chaosawakens.api.tag.TagWrapper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CAItemTagsProvider extends ItemTagsProvider {

    public CAItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockLookupProvider, CAConstants.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return CAConstants.MOD_NAME.concat(": Item Tags");
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        if (!BlockPropertyWrapper.getMappedBpws().isEmpty()) {
            BlockPropertyWrapper.getMappedBpws().forEach((blockSupEntry, curBwp) -> {
                List<TagKey<?>> parentTags = curBwp.getParentTags();

                if (!parentTags.isEmpty()){
                    parentTags.forEach(curBlockItemTag -> {
                        if (curBlockItemTag != null && curBlockItemTag.isFor(Registries.ITEM)) {
                            CAConstants.LOGGER.debug("[Tagging Block Item]: " + blockSupEntry.get().getDescriptionId() + " -> " + curBlockItemTag);

                            tag((TagKey<Item>) curBlockItemTag).add(blockSupEntry.get().asItem());
                        }
                    });
                }
            });
        }

        if (!ItemPropertyWrapper.getMappedIpws().isEmpty()) {
            ItemPropertyWrapper.getMappedIpws().forEach((itemSupEntry, curIwp) -> {
                List<TagKey<Item>> parentItemTags = curIwp.getParentTags();

                if (!parentItemTags.isEmpty()) {
                    parentItemTags.forEach(curItemTag -> {
                        CAConstants.LOGGER.debug("[Tagging Item]: " + itemSupEntry.get().getDescriptionId() + " -> " + curItemTag);

                        tag(curItemTag).add(itemSupEntry.get());
                    });
                }
            });
        }

        if (!TagWrapper.getCachedTWEntries().isEmpty()) {
            TagWrapper.getCachedTWEntries().forEach(twEntry -> {
                if (twEntry.getParentTag().isFor(Registries.ITEM)) {
                    TagKey<?> curItemTag = twEntry.getParentTag();

                    if (curItemTag != null) {
                        twEntry.getPredefinedTagEntries().forEach(tagEntry -> {
                            Item itemTagEntry = tagEntry.get() instanceof Block ? ((Block) tagEntry.get()).asItem() : (Item) tagEntry.get();

                            if (itemTagEntry != null) {
                                CAConstants.LOGGER.debug((tagEntry.get() instanceof Block ? "[Tagging Block Item]: " : "[Tagging Item]: ") + itemTagEntry.getDescriptionId() + " -> " + curItemTag);

                                tag((TagKey<Item>) curItemTag).add(itemTagEntry);
                            }
                        });

                        twEntry.getStoredTags().forEach(tagKeyEntry -> {
                            if (tagKeyEntry != null) {

                                CAConstants.LOGGER.debug("[Tagging Item Tag]: " + tagKeyEntry + " -> " + curItemTag);

                                tag((TagKey<Item>) tagKeyEntry); // Force the existingFileHelper to track the tag to be added (otherwise throws exception). Goofy ahh patch.
                                tag((TagKey<Item>) curItemTag).addTag((TagKey<Item>) tagKeyEntry);
                            }
                        });

                        twEntry.getParentTags().forEach(parentTagKeyEntry -> {
                            if (parentTagKeyEntry != null) {

                                CAConstants.LOGGER.debug("[Tagging Item Tag]: " + curItemTag + " -> " + parentTagKeyEntry);

                                tag((TagKey<Item>) curItemTag); // Force the existingFileHelper to track the tag to be added (otherwise throws exception). Goofy ahh patch.
                                tag((TagKey<Item>) parentTagKeyEntry).addTag((TagKey<Item>) curItemTag);
                            }
                        });
                    }
                }
            });
        }
    }
}
