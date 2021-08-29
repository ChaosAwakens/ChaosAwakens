package io.github.chaosawakens.data;

import java.nio.file.Path;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Meme Man
 *
 */

public class CATagProvider extends BlockTagsProvider{
	
    public CATagProvider(DataGenerator gen, ExistingFileHelper existingFileHelper)
    {
        super(gen, ChaosAwakens.MODID, existingFileHelper);
    }
    
   protected Path getPath(ResourceLocation p_200431_1_) {
       return this.generator.getOutputFolder().resolve("data/" + p_200431_1_.getNamespace() + "/tags/items/" + p_200431_1_.getPath() + ".json");
    }

     public String getName() {
        return "Chaos Awakens Item Block Tags";
     }
     
     @Override
    protected void addTags() {
       	this.tag(CATags.PEACH_WOOD).add(CABlocks.PEACH_LOG.get(), CABlocks.STRIPPED_PEACH_LOG.get());
    	this.tag(CATags.CHERRY_WOOD).add(CABlocks.CHERRY_LOG.get(), CABlocks.STRIPPED_CHERRY_LOG.get());
    	this.tag(CATags.DUPLICATOR_WOOD).add(CABlocks.DEAD_DUPLICATION_LOG.get(), CABlocks.STRIPPED_DUPLICATION_LOG.get());
    	this.tag(CATags.APPLE_WOOD).add(CABlocks.APPLE_LOG.get(), CABlocks.STRIPPED_APPLE_LOG.get());
    }
     
     //TODO make the static class and the main class function as one instead of having to make them separate
     public static class CATagProviderForBlocks extends BlockTagsProvider {

		public CATagProviderForBlocks(DataGenerator gen, ExistingFileHelper existingFileHelper)
	    {
	        super(gen, ChaosAwakens.MODID, existingFileHelper);
	    }
		
		 protected Path getPath(ResourceLocation p_200431_1_) {
		       return this.generator.getOutputFolder().resolve("data/" + p_200431_1_.getNamespace() + "/tags/blocks/" + p_200431_1_.getPath() + ".json");
		    }

		     public String getName() {
		        return "Chaos Awakens Block Tags";
		     }
		     
		     @Override
		    protected void addTags() {
		       	this.tag(CATags.PEACH_WOOD).add(CABlocks.PEACH_LOG.get(), CABlocks.STRIPPED_PEACH_LOG.get());
		    	this.tag(CATags.CHERRY_WOOD).add(CABlocks.CHERRY_LOG.get(), CABlocks.STRIPPED_CHERRY_LOG.get());
		    	this.tag(CATags.DUPLICATOR_WOOD).add(CABlocks.DEAD_DUPLICATION_LOG.get(), CABlocks.STRIPPED_DUPLICATION_LOG.get());
		    	this.tag(CATags.APPLE_WOOD).add(CABlocks.APPLE_LOG.get(), CABlocks.STRIPPED_APPLE_LOG.get());
		    }
    	 
     }

}
