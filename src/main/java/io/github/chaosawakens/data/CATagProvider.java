package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CATags;
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
		this.tag(CATags.APPLE_LOGS).add(CABlocks.APPLE_LOG.get(), CABlocks.STRIPPED_APPLE_LOG.get());
		this.tag(CATags.CHERRY_LOGS).add(CABlocks.CHERRY_LOG.get(), CABlocks.STRIPPED_CHERRY_LOG.get());
		this.tag(CATags.PEACH_LOGS).add(CABlocks.PEACH_LOG.get(), CABlocks.STRIPPED_PEACH_LOG.get());
		this.tag(CATags.DUPLICATION_LOGS).add(CABlocks.DUPLICATION_LOG.get(), CABlocks.STRIPPED_DUPLICATION_LOG.get(), CABlocks.DEAD_DUPLICATION_LOG.get());
		this.tag(BlockTags.LOGS).addTags(CATags.DUPLICATION_LOGS);
		this.tag(BlockTags.LOGS_THAT_BURN).addTags(CATags.APPLE_LOGS, CATags.CHERRY_LOGS, CATags.PEACH_LOGS);
		this.tag(BlockTags.PLANKS).add(CABlocks.APPLE_PLANKS.get(), CABlocks.CHERRY_PLANKS.get(), CABlocks.PEACH_PLANKS.get(), CABlocks.DUPLICATION_PLANKS.get(), CABlocks.MOLDY_PLANKS.get());
	}
	
	// TODO make the static class and the main class function as one instead of having to make them separate
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
			this.tag(CATags.APPLE_LOGS).add(CABlocks.APPLE_LOG.get(), CABlocks.STRIPPED_APPLE_LOG.get());
			this.tag(CATags.CHERRY_LOGS).add(CABlocks.CHERRY_LOG.get(), CABlocks.STRIPPED_CHERRY_LOG.get());
			this.tag(CATags.PEACH_LOGS).add(CABlocks.PEACH_LOG.get(), CABlocks.STRIPPED_PEACH_LOG.get());
			this.tag(CATags.DUPLICATION_LOGS).add(CABlocks.DUPLICATION_LOG.get(), CABlocks.STRIPPED_DUPLICATION_LOG.get(), CABlocks.DEAD_DUPLICATION_LOG.get());
			this.tag(BlockTags.LOGS).addTags(CATags.DUPLICATION_LOGS);
			this.tag(BlockTags.LOGS_THAT_BURN).addTags(CATags.APPLE_LOGS, CATags.CHERRY_LOGS, CATags.PEACH_LOGS);
			this.tag(BlockTags.PLANKS).add(CABlocks.APPLE_PLANKS.get(), CABlocks.CHERRY_PLANKS.get(), CABlocks.PEACH_PLANKS.get(), CABlocks.DUPLICATION_PLANKS.get(), CABlocks.MOLDY_PLANKS.get());
		}
	}
}
