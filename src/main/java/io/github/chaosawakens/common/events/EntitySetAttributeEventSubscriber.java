package io.github.chaosawakens.common.events;

import io.github.chaosawakens.common.entity.*;
import io.github.chaosawakens.common.entity.robo.RoboPounderEntity;
import io.github.chaosawakens.common.entity.robo.RoboSniperEntity;
import io.github.chaosawakens.common.entity.robo.RoboWarriorEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public class EntitySetAttributeEventSubscriber {
	public static void onEntityAttributeCreationEvent(final EntityAttributeCreationEvent event) {
		event.put(CAEntityTypes.OAK_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.APPLE_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.ACACIA_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.BIRCH_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.CHERRY_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.DARK_OAK_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.JUNGLE_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.PEACH_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.SKYWOOD_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.SPRUCE_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.CRIMSON_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.WARPED_ENT.get(), EntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.RED_ANT.get(), AggressiveAntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.BROWN_ANT.get(), AntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.RAINBOW_ANT.get(), AntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.UNSTABLE_ANT.get(), AntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.TERMITE.get(), AggressiveAntEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.TREE_FROG.get(), TreeFrogEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.HERCULES_BEETLE.get(), HerculesBeetleEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.THROWBACK_HERCULES_BEETLE.get(), HerculesBeetleEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.BIRD.get(), BirdEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.APPLE_COW.get(), AppleCowEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.GOLDEN_APPLE_COW.get(), GoldenAppleCowEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.ULTIMATE_APPLE_COW.get(), UltimateAppleCowEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get(), EnchantedGoldenAppleCowEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.CRYSTAL_APPLE_COW.get(), CrystalAppleCowEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.CRYSTAL_GATOR.get(), CrystalGatorEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.CRYSTAL_CARROT_PIG.get(), CrystalCarrotPigEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.CARROT_PIG.get(), CarrotPigEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.GOLDEN_CARROT_PIG.get(), GoldenCarrotPigEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get(), EnchantedGoldenCarrotPigEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.BEAVER.get(), BeaverEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.DIMETRODON.get(), DimetrodonEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.GAZELLE.get(), GazelleEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.LEAFY_CHICKEN.get(), LeafyChickenEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.EMERALD_GATOR.get(), EmeraldGatorEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.RUBY_BUG.get(), RubyBugEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.STINK_BUG.get(), StinkBugEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.ROBO_POUNDER.get(), RoboPounderEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.ROBO_SNIPER.get(), RoboSniperEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.ROBO_WARRIOR.get(), RoboWarriorEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.WASP.get(), WaspEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.WHALE.get(), WhaleEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.GREEN_FISH.get(), GreenFishEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.ROCK_FISH.get(), RockFishEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.SPARK_FISH.get(), SparkFishEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.WOOD_FISH.get(), WoodFishEntity.setCustomAttributes().build());
		event.put(CAEntityTypes.LAVA_EEL.get(), LavaEelEntity.setCustomAttributes().build());
	}
}
