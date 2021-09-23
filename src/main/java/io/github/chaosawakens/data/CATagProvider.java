package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.nio.file.Path;

/**
 * Yea I do understand that Jsons are easy to make, however it's about time we
 * focus on the real stuff, this provider makes it easier for us to focus on the
 * heavy code
 *
 * @author Meme Man
 */

public class CATagProvider extends BlockTagsProvider {
	
	public CATagProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
		super(gen, ChaosAwakens.MODID, existingFileHelper);
	}
	
	protected Path getPath(ResourceLocation resourceLocation) {
		return this.generator.getOutputFolder().resolve("data/" + resourceLocation.getNamespace() + "/tags/items/" + resourceLocation.getPath() + ".json");
	}
	
	public String getName() {
		return "Chaos Awakens Item-Block Tags";
	}
	
	@Override
	protected void addTags() {
		this.tag(BlockTags.PLANKS).add(CABlocks.APPLE_PLANKS.get(), CABlocks.CHERRY_PLANKS.get(), CABlocks.PEACH_PLANKS.get(), CABlocks.DUPLICATION_PLANKS.get(), CABlocks.SKYWOOD_PLANKS.get(), CABlocks.MOLDY_PLANKS.get());
		this.tag(BlockTags.FENCE_GATES).add(CABlocks.APPLE_FENCE_GATE.get(), CABlocks.CHERRY_FENCE_GATE.get(), CABlocks.PEACH_FENCE_GATE.get(), CABlocks.DUPLICATION_FENCE_GATE.get(), CABlocks.SKYWOOD_FENCE_GATE.get());
		this.tag(BlockTags.WOODEN_FENCES).add(CABlocks.APPLE_FENCE.get(), CABlocks.CHERRY_FENCE.get(), CABlocks.PEACH_FENCE.get(), CABlocks.DUPLICATION_FENCE.get(), CABlocks.SKYWOOD_FENCE.get(), CABlocks.MOLDY_FENCE.get());
	}
	
	// TODO make the static class and the main class function as one instead of
	// having to make them separate
	public static class CATagProviderForBlocks extends BlockTagsProvider {
		
		/**
		 * Static class for the main tag provider to make a separate package for the
		 * block tags to work properly in crafting recipes and such
		 *
		 * @param gen
		 * @param existingFileHelper
		 * @author Meme Man
		 */
		
		public CATagProviderForBlocks(DataGenerator gen, ExistingFileHelper existingFileHelper) {
			super(gen, ChaosAwakens.MODID, existingFileHelper);
		}
		
		protected Path getPath(ResourceLocation resourceLocation) {
			return this.generator.getOutputFolder().resolve("data/" + resourceLocation.getNamespace() + "/tags/blocks/" + resourceLocation.getPath() + ".json");
		}
		
		public String getName() {
			return "Chaos Awakens Block Tags";
		}
		
		@Override
		protected void addTags() {
			this.tag(BlockTags.PLANKS).add(CABlocks.APPLE_PLANKS.get(), CABlocks.CHERRY_PLANKS.get(), CABlocks.PEACH_PLANKS.get(), CABlocks.DUPLICATION_PLANKS.get(), CABlocks.SKYWOOD_PLANKS.get(), CABlocks.MOLDY_PLANKS.get());
			this.tag(BlockTags.FENCE_GATES).add(CABlocks.APPLE_FENCE_GATE.get(), CABlocks.CHERRY_FENCE_GATE.get(), CABlocks.PEACH_FENCE_GATE.get(), CABlocks.DUPLICATION_FENCE_GATE.get(), CABlocks.SKYWOOD_FENCE_GATE.get());
			this.tag(BlockTags.WOODEN_FENCES).add(CABlocks.APPLE_FENCE.get(), CABlocks.CHERRY_FENCE.get(), CABlocks.PEACH_FENCE.get(), CABlocks.DUPLICATION_FENCE.get(), CABlocks.SKYWOOD_FENCE.get(), CABlocks.MOLDY_FENCE.get());
		}
	}
}
