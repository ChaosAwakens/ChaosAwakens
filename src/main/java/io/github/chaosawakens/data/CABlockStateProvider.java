package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * @author invalid2
 */
public class CABlockStateProvider extends BlockStateProvider {

    /**
     * @param gen
     * @param modid
     * @param exFileHelper
     */
    public CABlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(CABlocks.FOSSILISED_ACACIA_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_BIRCH_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_DARK_OAK_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_JUNGLE_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_OAK_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_SPRUCE_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_HERCULES_BEETLE.get());
        this.simpleBlock(CABlocks.FOSSILISED_RUBY_BUG.get());
        this.simpleBlock(CABlocks.FOSSILISED_EMERALD_GATOR.get());
        this.simpleBlock(CABlocks.FOSSILISED_WTF.get());
        this.simpleBlock(CABlocks.FOSSILISED_SCORPION.get());
        this.simpleBlock(CABlocks.FOSSILISED_WASP.get());
        this.simpleBlock(CABlocks.FOSSILISED_PIRAPORU.get());
        this.simpleBlock(CABlocks.FOSSILISED_APPLE_COW.get());
        this.simpleBlock(CABlocks.FOSSILISED_GOLDEN_APPLE_COW.get());
        this.simpleBlock(CABlocks.FOSSILISED_CARROT_PIG.get());
        this.simpleBlock(CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.get());

        this.simpleBlock(CABlocks.FOSSILISED_BAT.get());
        this.simpleBlock(CABlocks.FOSSILISED_BEE.get());
        this.simpleBlock(CABlocks.FOSSILISED_CAVE_SPIDER.get());
        this.simpleBlock(CABlocks.FOSSILISED_CHICKEN.get());
        this.simpleBlock(CABlocks.FOSSILISED_COD.get());
        this.simpleBlock(CABlocks.FOSSILISED_COW.get());
        this.simpleBlock(CABlocks.FOSSILISED_CREEPER.get());
        this.simpleBlock(CABlocks.FOSSILISED_DOLPHIN.get());
        this.simpleBlock(CABlocks.FOSSILISED_DONKEY.get());
        this.simpleBlock(CABlocks.FOSSILISED_DROWNED.get());
        this.simpleBlock(CABlocks.FOSSILISED_ENDERMAN.get());
        this.simpleBlock(CABlocks.FOSSILISED_EVOKER.get());
        this.simpleBlock(CABlocks.FOSSILISED_FOX.get());
        this.simpleBlock(CABlocks.FOSSILISED_GIANT.get());
        this.simpleBlock(CABlocks.FOSSILISED_GUARDIAN.get());
        this.simpleBlock(CABlocks.FOSSILISED_HUSK.get());
        this.simpleBlock(CABlocks.FOSSILISED_ILLUSIONER.get());
        this.simpleBlock(CABlocks.FOSSILISED_IRON_GOLEM.get());
        this.simpleBlock(CABlocks.FOSSILISED_LLAMA.get());
        this.simpleBlock(CABlocks.FOSSILISED_MOOSHROOM.get());
        this.simpleBlock(CABlocks.FOSSILISED_OCELOT.get());
        this.simpleBlock(CABlocks.FOSSILISED_PANDA.get());
        this.simpleBlock(CABlocks.FOSSILISED_PIG.get());
        this.simpleBlock(CABlocks.FOSSILISED_PHANTOM.get());
        this.simpleBlock(CABlocks.FOSSILISED_PILLAGER.get());
        this.simpleBlock(CABlocks.FOSSILISED_POLAR_BEAR.get());
        this.simpleBlock(CABlocks.FOSSILISED_PUFFERFISH.get());
        this.simpleBlock(CABlocks.FOSSILISED_RABBIT.get());
        this.simpleBlock(CABlocks.FOSSILISED_RAVAGER.get());
        this.simpleBlock(CABlocks.FOSSILISED_SALMON.get());
        this.simpleBlock(CABlocks.FOSSILISED_SHEEP.get());
        this.simpleBlock(CABlocks.FOSSILISED_SKELETON.get());
        this.simpleBlock(CABlocks.FOSSILISED_SKELETON_HORSE.get());
        this.simpleBlock(CABlocks.FOSSILISED_SLIME.get());
        this.simpleBlock(CABlocks.FOSSILISED_SNOW_GOLEM.get());
        this.simpleBlock(CABlocks.FOSSILISED_SPIDER.get());
        this.simpleBlock(CABlocks.FOSSILISED_SQUID.get());
        this.simpleBlock(CABlocks.FOSSILISED_STRAY.get());
        this.simpleBlock(CABlocks.FOSSILISED_TROPICAL_FISH.get());
        this.simpleBlock(CABlocks.FOSSILISED_TURTLE.get());
        this.simpleBlock(CABlocks.FOSSILISED_VILLAGER.get());
        this.simpleBlock(CABlocks.FOSSILISED_VINDICATOR.get());
        this.simpleBlock(CABlocks.FOSSILISED_WANDERING_TRADER.get());
        this.simpleBlock(CABlocks.FOSSILISED_WOLF.get());
        this.simpleBlock(CABlocks.FOSSILISED_WITCH.get());
        this.simpleBlock(CABlocks.FOSSILISED_ZOMBIE.get());
        this.simpleBlock(CABlocks.FOSSILISED_ZOMBIE_HORSE.get());

        this.simpleBlock(CABlocks.FOSSILISED_CRIMSON_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_WARPED_ENT.get());

        this.simpleBlock(CABlocks.FOSSILISED_BLAZE.get());
        this.simpleBlock(CABlocks.FOSSILISED_GHAST.get());
        this.simpleBlock(CABlocks.FOSSILISED_HOGLIN.get());
        this.simpleBlock(CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get());
        this.simpleBlock(CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get());
        this.simpleBlock(CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get());
        this.simpleBlock(CABlocks.FOSSILISED_PIGLIN.get());
        this.simpleBlock(CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.get());
        this.simpleBlock(CABlocks.FOSSILISED_STRIDER.get());
        this.simpleBlock(CABlocks.FOSSILISED_WITHER_SKELETON.get());
        this.simpleBlock(CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.get());

        this.simpleBlock(CABlocks.FOSSILISED_ENDERMAN_END_STONE.get());
        this.simpleBlock(CABlocks.FOSSILISED_ENDERMITE.get());
        this.simpleBlock(CABlocks.FOSSILISED_SHULKER.get());

        this.simpleBlock(CABlocks.RUBY_ORE.get());
        this.simpleBlock(CABlocks.NETHERRACK_RUBY_ORE.get());
        this.simpleBlock(CABlocks.BLACKSTONE_RUBY_ORE.get());

        this.logBlock(CABlocks.APPLE_LOG.get());
        this.simpleBlock(CABlocks.APPLE_PLANKS.get());
        this.logBlock(CABlocks.STRIPPED_APPLE_LOG.get());
        this.logBlock(CABlocks.CHERRY_LOG.get());
        this.simpleBlock(CABlocks.CHERRY_PLANKS.get());
        this.logBlock(CABlocks.STRIPPED_CHERRY_LOG.get());
        this.logBlock(CABlocks.PEACH_LOG.get());
        this.simpleBlock(CABlocks.PEACH_PLANKS.get());
        this.logBlock(CABlocks.STRIPPED_PEACH_LOG.get());
        this.blockTexture(CABlocks.PEACH_SAPLING.get());
        this.logBlock(CABlocks.DUPLICATION_LOG.get());
        this.logBlock(CABlocks.DEAD_DUPLICATION_LOG.get());
        this.simpleBlock(CABlocks.DUPLICATION_PLANKS.get());
        this.simpleBlock(CABlocks.DUPLICATION_LEAVES.get());
        this.logBlock(CABlocks.STRIPPED_DUPLICATION_LOG.get());
        this.logBlock(CABlocks.SKYWOOD_LOG.get());
        this.simpleBlock(CABlocks.SKYWOOD_PLANKS.get());
        this.logBlock(CABlocks.STRIPPED_SKYWOOD_LOG.get());
        this.stairsBlock(CABlocks.APPLE_STAIRS.get(), chaosRL("apple_planks"));
        this.stairsBlock(CABlocks.CHERRY_STAIRS.get(), chaosRL("cherry_planks"));
        this.stairsBlock(CABlocks.PEACH_STAIRS.get(), chaosRL("peach_planks"));
        this.stairsBlock(CABlocks.DUPLICATION_STAIRS.get(), chaosRL("duplication_planks"));
        this.stairsBlock(CABlocks.SKYWOOD_STAIRS.get(), chaosRL("skywood_planks"));
        this.slabBlock(CABlocks.APPLE_SLAB.get(), chaosRL("apple_planks"), chaosRL("apple_planks"));
        this.slabBlock(CABlocks.CHERRY_SLAB.get(), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
        this.slabBlock(CABlocks.PEACH_SLAB.get(), chaosRL("peach_planks"), chaosRL("peach_planks"));
        this.slabBlock(CABlocks.DUPLICATION_SLAB.get(), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
        this.slabBlock(CABlocks.SKYWOOD_SLAB.get(), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
        this.fenceBlock(CABlocks.APPLE_FENCE.get(), chaosRL("apple_planks"));
        this.fenceBlock(CABlocks.CHERRY_FENCE.get(), chaosRL("cherry_planks"));
        this.fenceBlock(CABlocks.PEACH_FENCE.get(), chaosRL("peach_planks"));
        this.fenceBlock(CABlocks.DUPLICATION_FENCE.get(), chaosRL("duplication_planks"));
        this.fenceBlock(CABlocks.SKYWOOD_FENCE.get(), chaosRL("skywood_planks"));
        this.fenceGateBlock(CABlocks.APPLE_FENCE_GATE.get(), chaosRL("apple_planks"));
        this.fenceGateBlock(CABlocks.CHERRY_FENCE_GATE.get(), chaosRL("cherry_planks"));
        this.fenceGateBlock(CABlocks.PEACH_FENCE_GATE.get(), chaosRL("peach_planks"));
        this.fenceGateBlock(CABlocks.DUPLICATION_FENCE_GATE.get(), chaosRL("duplication_planks"));
        this.fenceGateBlock(CABlocks.SKYWOOD_FENCE_GATE.get(), chaosRL("skywood_planks"));
    }

    private ResourceLocation chaosRL(String texture) {
        return new ResourceLocation(ChaosAwakens.MODID, "block/" + texture);
    }
}