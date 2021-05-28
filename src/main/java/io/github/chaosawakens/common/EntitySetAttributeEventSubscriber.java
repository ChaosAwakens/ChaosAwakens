package io.github.chaosawakens.common;

import io.github.chaosawakens.common.entity.*;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

/**
 * Class with method(s) that subscribe to the EntityAttributeCreationEvent
 * @author invalid2
 */
public class EntitySetAttributeEventSubscriber {
	
	public static void onEntityAttributeCreationEvent(final EntityAttributeCreationEvent event) {
		
		event.put(CAEntityTypes.ENT.get(), EntEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.RED_ANT.get(), RedAntEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.BROWN_ANT.get(), BrownAntEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.RAINBOW_ANT.get(), RainbowAntEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.UNSTABLE_ANT.get(), UnstableAntEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.TERMITE.get(), TermiteEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.HERCULES_BEETLE.get(), HerculesBeetleEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.RUBY_BUG.get(), RubyBugEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.APPLE_COW.get(), AppleCowEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.GOLDEN_APPLE_COW.get(), GoldenAppleCowEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get(), EnchantedGoldenAppleCowEntity.setCustomAttributes().create());
		event.put(CAEntityTypes.CRYSTAL_APPLE_COW.get(), CrystalAppleCowEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.BEAVER.get(), BeaverEntity.setCustomAttributes().create());
        event.put(CAEntityTypes.EMERALD_GATOR.get(), EmeraldGatorEntity.setCustomAttributes().create());
		event.put(CAEntityTypes.ROBO_SNIPER.get(), RoboSniperEntity.setCustomAttributes().create());
		event.put(CAEntityTypes.ROBO_WARRIOR.get(), RoboWarriorEntity.setCustomAttributes().create());
	}
}
