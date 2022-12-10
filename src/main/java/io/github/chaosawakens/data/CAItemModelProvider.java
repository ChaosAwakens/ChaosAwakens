package io.github.chaosawakens.data;

import java.util.Collection;

import javax.annotation.Nonnull;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.CrystalBushBlock;
import io.github.chaosawakens.common.blocks.DenseBushBlock;
import io.github.chaosawakens.common.blocks.LeafCarpetBlock;
import io.github.chaosawakens.common.items.MobestiaryItem;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class CAItemModelProvider extends ItemModelProvider {
	private static final ResourceLocation ITEM_GENERATED = new ResourceLocation("item/generated");

	public CAItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, ChaosAwakens.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		generate(CAItems.ITEMS.getEntries());
		generateBlockItems(CABlocks.ITEM_BLOCKS.getEntries());
	}

    @Nonnull
    @Override
    public String getName() {
        return ChaosAwakens.MODNAME + " Item models";
    }
    
    public ResourceLocation loc(String name) {
        return new ResourceLocation(name);
    }
    private void generate(final Collection<RegistryObject<Item>> items) {
        final ModelFile parentGenerated = getExistingFile(mcLoc("item/generated"));
        final ExistingModelFile parentHandheld = getExistingFile(mcLoc("item/handheld"));
	    final ModelFile defaultCritterCage = getExistingFile(loc("chaosawakens:item/default_critter_cage"));
        final ModelFile appleCow = getExistingFile(loc("chaosawakens:item/apple_cow_critter_cage"));
        final ModelFile bee = getExistingFile(loc("chaosawakens:item/bee_critter_cage"));
        final ModelFile bird = getExistingFile(loc("chaosawakens:item/bird_critter_cage"));
        final ModelFile carrotPig = getExistingFile(loc("chaosawakens:item/carrot_pig_critter_cage"));
        final ModelFile cat = getExistingFile(loc("chaosawakens:item/cat_critter_cage"));
        final ModelFile caveSpider = getExistingFile(loc("chaosawakens:item/cave_spider_critter_cage"));
        final ModelFile cow = getExistingFile(loc("chaosawakens:item/cow_critter_cage"));
        final ModelFile creeper = getExistingFile(loc("chaosawakens:item/creeper_critter_cage"));
        final ModelFile donkey = getExistingFile(loc("chaosawakens:item/donkey_critter_cage"));
        final ModelFile drowned = getExistingFile(loc("chaosawakens:item/drowned_critter_cage"));
        final ModelFile enderman = getExistingFile(loc("chaosawakens:item/enderman_critter_cage"));
        final ModelFile fox = getExistingFile(loc("chaosawakens:item/fox_critter_cage"));
        final ModelFile horse = getExistingFile(loc("chaosawakens:item/horse_critter_cage"));
        final ModelFile husk = getExistingFile(loc("chaosawakens:item/husk_critter_cage"));
        final ModelFile llama = getExistingFile(loc("chaosawakens:item/llama_critter_cage"));
        final ModelFile mooshroom = getExistingFile(loc("chaosawakens:item/mooshroom_critter_cage"));
        final ModelFile ostrich = getExistingFile(loc("chaosawakens:item/ostrich_critter_cage"));
        final ModelFile panda = getExistingFile(loc("chaosawakens:item/panda_critter_cage"));
        final ModelFile pig = getExistingFile(loc("chaosawakens:item/pig_critter_cage"));
        final ModelFile piraporu = getExistingFile(loc("chaosawakens:item/piraporu_critter_cage"));
        final ModelFile polarBear = getExistingFile(loc("chaosawakens:item/polar_bear_critter_cage"));
        final ModelFile rabbit = getExistingFile(loc("chaosawakens:item/rabbit_critter_cage"));
        final ModelFile sheep = getExistingFile(loc("chaosawakens:item/sheep_critter_cage"));
        final ModelFile skeleton = getExistingFile(loc("chaosawakens:item/skeleton_critter_cage"));
        final ModelFile slime = getExistingFile(loc("chaosawakens:item/slime_critter_cage"));
        final ModelFile spider = getExistingFile(loc("chaosawakens:item/spider_critter_cage"));
        final ModelFile stray = getExistingFile(loc("chaosawakens:item/stray_critter_cage"));
        final ModelFile wasp = getExistingFile(loc("chaosawakens:item/wasp_critter_cage"));
        final ModelFile wolf = getExistingFile(loc("chaosawakens:item/wolf_critter_cage"));
        final ModelFile zombieVillager = getExistingFile(loc("chaosawakens:item/zombie_villager_critter_cage"));
        final ModelFile zombie = getExistingFile(loc("chaosawakens:item/zombie_critter_cage"));

        final ResourceLocation defaultRL = new ResourceLocation("default");
        final ResourceLocation appleCowRL = new ResourceLocation("apple_cow");
        final ResourceLocation beeRL = new ResourceLocation("bee");
        final ResourceLocation birdRL = new ResourceLocation("bird");
        final ResourceLocation carrotPigRL = new ResourceLocation("carrot_pig");
        final ResourceLocation catRL = new ResourceLocation("cat");
        final ResourceLocation caveSpiderRL = new ResourceLocation("cave_spider");
        final ResourceLocation cowRL = new ResourceLocation("cow");
        final ResourceLocation creeperRL = new ResourceLocation("creeper");
        final ResourceLocation donkeyRL = new ResourceLocation("donkey");
        final ResourceLocation drownedRL = new ResourceLocation("drowned");
        final ResourceLocation endermanRL = new ResourceLocation("enderman");
        final ResourceLocation foxRL = new ResourceLocation("fox");
        final ResourceLocation horseRL = new ResourceLocation("horse");
        final ResourceLocation huskRL = new ResourceLocation("husk");
        final ResourceLocation llamaRL = new ResourceLocation("llama");
        final ResourceLocation mooshroomRL = new ResourceLocation("mooshroom");
        final ResourceLocation ostrichRL = new ResourceLocation("ostrich");
        final ResourceLocation pandaRL = new ResourceLocation("panda");
        final ResourceLocation pigRL = new ResourceLocation("pig");
        final ResourceLocation piraporuRL = new ResourceLocation("piraporu");
        final ResourceLocation polarBearRL = new ResourceLocation("polar_bear");
        final ResourceLocation rabbitRL = new ResourceLocation("rabbit");
        final ResourceLocation sheepRL = new ResourceLocation("sheep");
        final ResourceLocation skeletonRL = new ResourceLocation("skeleton");
        final ResourceLocation slimeRL = new ResourceLocation("slime");
        final ResourceLocation spiderRL = new ResourceLocation("spider");
        final ResourceLocation strayRL = new ResourceLocation("stray");
        final ResourceLocation waspRL = new ResourceLocation("wasp");
        final ResourceLocation wolfRL = new ResourceLocation("wolf");
        final ResourceLocation zombieVillagerRL = new ResourceLocation("zombie_villager");
        final ResourceLocation zombieRL = new ResourceLocation("zombie");

		for (RegistryObject<Item> item : items) {
			String name = item.getId().getPath();

			ChaosAwakens.LOGGER.debug(item.getId());

			if (name.startsWith("enchanted")) name = name.substring(name.indexOf("_") + 1);

			/*
			 * Skip elements that have no texture at assets/chaosawakens/textures/item or
			 * already have an existing model at assets/chaosawakens/models/item
			 */

            if (item.getId().getPath().contains("critter_cage")) {
	            getBuilder(item.getId().getPath()).parent(parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/critter_cages/" + name.replaceAll("_critter_cage", ""))
	            .override().predicate(defaultRL, 1.0F).model(defaultCritterCage).end()
	            .override().predicate(appleCowRL, 1.0F).model(appleCow).end()
	            .override().predicate(beeRL, 1.0F).model(bee).end()
	            .override().predicate(birdRL, 1.0F).model(bird).end()
	            .override().predicate(carrotPigRL, 1.0F).model(carrotPig).end()
	            .override().predicate(catRL, 1.0F).model(cat).end()
	            .override().predicate(caveSpiderRL, 1.0F).model(caveSpider).end()
	            .override().predicate(cowRL, 1.0F).model(cow).end()
	            .override().predicate(creeperRL, 1.0F).model(creeper).end()
	            .override().predicate(donkeyRL, 1.0F).model(donkey).end()
	            .override().predicate(drownedRL, 1.0F).model(drowned).end()
	            .override().predicate(endermanRL, 1.0F).model(enderman).end()
	            .override().predicate(foxRL, 1.0F).model(fox).end()
	            .override().predicate(horseRL, 1.0F).model(horse).end()
	            .override().predicate(huskRL, 1.0F).model(husk).end()
	            .override().predicate(llamaRL, 1.0F).model(llama).end()
	            .override().predicate(mooshroomRL, 1.0F).model(mooshroom).end()
	            .override().predicate(ostrichRL, 1.0F).model(ostrich).end()
	            .override().predicate(pandaRL, 1.0F).model(panda).end()
	            .override().predicate(pigRL, 1.0F).model(pig).end()
	            .override().predicate(piraporuRL, 1.0F).model(piraporu).end()
	            .override().predicate(polarBearRL, 1.0F).model(polarBear).end()
	            .override().predicate(rabbitRL, 1.0F).model(rabbit).end()
	            .override().predicate(sheepRL, 1.0F).model(sheep).end()
	            .override().predicate(skeletonRL, 1.0F).model(skeleton).end()
	            .override().predicate(slimeRL, 1.0F).model(slime).end()
	            .override().predicate(spiderRL, 1.0F).model(spider).end()
	            .override().predicate(strayRL, 1.0F).model(stray).end()
	            .override().predicate(waspRL, 1.0F).model(wasp).end()
	            .override().predicate(wolfRL, 1.0F).model(wolf).end()
	            .override().predicate(zombieVillagerRL, 1.0F).model(zombieVillager).end()
	            .override().predicate(zombieRL, 1.0F).model(zombie).end();
            } else if (item.getId().getPath().contains("_spawn_egg")) {
				getBuilder(item.getId().getPath()).parent(parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/spawn_eggs/" + name.replaceAll("_spawn_egg", ""));
            } else if (item.get() instanceof MobestiaryItem) {
				getBuilder(item.getId().getPath()).parent(parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/book_" + name);
			} else if (item.getId().getPath().contains("boat")) {
            	getBuilder(item.getId().getPath()).parent(parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/boats/" + name.replaceAll("_boat", ""));
            } else {
                if (!existingFileHelper.exists(getItemResourceLocation(name), TEXTURE) || existingFileHelper.exists(getItemResourceLocation(name), MODEL)) continue;
                getBuilder(item.getId().getPath()).parent(item.get().getMaxDamage(ItemStack.EMPTY) > 0 && !(item.get() instanceof ArmorItem) ? parentHandheld : parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/" + name);
            }
        }
    }

	private void generateBlockItems(final Collection<RegistryObject<Item>> itemBlocks) {
		for (RegistryObject<Item> item : itemBlocks) {
			String name = item.getId().getPath();
			BlockItem blockItem = (BlockItem) item.get();
			Block block = blockItem.getBlock();

			ChaosAwakens.LOGGER.debug(item.getId());

			/*
			 * Skip elements that have no block model inside of
			 * assets/chaosawakens/models/block or already have an existing item model at
			 * assets/chaosawakens/models/item
			 */

			if (item.getId().getPath().contains("_wall")) {
				withExistingParent(name, getBlockResourceLocation(name + "_inventory"));
			} else if (item.getId().getPath().contains("_trapdoor")) {
				withExistingParent(name, getBlockResourceLocation(name + "_bottom"));			
			} else if (item.getId().getPath().contains("_door")) {
				singleTextureLayer0(name, ITEM_GENERATED, getItemResourceLocation(name));
			} else if ((block instanceof CrystalBushBlock || block instanceof DenseBushBlock) && (item.getId().getPath().contains("_grass") || item.getId().getPath().contains("_sun"))) {
				if (item.getId().getPath().contains("tall_") || item.getId().getPath().contains("thorny_")) {
					singleTextureLayer0(name, ITEM_GENERATED, getBlockResourceLocation(name + "_top"));
				} else {
					singleTextureLayer0(name, ITEM_GENERATED, getBlockResourceLocation(name));
				}
			} else if (block instanceof LeafCarpetBlock) {
				withExistingParent(name, getBlockResourceLocation(name + "_inventory"));
			} else {
				if (!existingFileHelper.exists(getBlockResourceLocation(name), MODEL) || existingFileHelper.exists(getItemResourceLocation(name), MODEL)) {
					continue;
				}
				withExistingParent(name, getBlockResourceLocation(name));
			}
		}
	}

	private static ResourceLocation getResourceLocation(String path) {
		return new ResourceLocation(ChaosAwakens.MODID, path);
	}

	private static ResourceLocation getBlockResourceLocation(String name) {
		return getResourceLocation("block/" + name);
	}

	private static ResourceLocation getItemResourceLocation(String name) {
		return getResourceLocation("item/" + name);
	}

	public ItemModelBuilder singleTextureLayer0(String name, ResourceLocation parent, ResourceLocation texture) {
		return singleTexture(name, parent, "layer0", texture);
	}
}
