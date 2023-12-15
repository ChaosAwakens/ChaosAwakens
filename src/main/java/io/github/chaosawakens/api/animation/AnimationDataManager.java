package io.github.chaosawakens.api.animation;

import com.google.common.collect.Lists;
import io.github.chaosawakens.ChaosAwakens;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

/**
 * Instead of selecting a "master" client, stores assets in a hardcoded set of resource locations for the server to reference and enforce on startup instead of on first player
 * login. Super ick approach, only for 1.16.5, since it's going EOL next update. Not a good practice, still pretty performant.
 */
public class AnimationDataManager {
    private static final AdvancedAnimationInformation INSTANCE = new AdvancedAnimationInformation();
    private static final ObjectArraySet<ResourceLocation> DEFAULT_ANIMATION_RESOURCES = Util.make(new ObjectArraySet<>(), (targetSet) -> targetSet.addAll(
            Lists.newArrayList(ChaosAwakens.prefix("animations/entity/boss/miniboss/hercules_beetle.animation.json"),
                    ChaosAwakens.prefix("animations/entity/boss/robo/robo_jeffery.animation.json"),
                    ChaosAwakens.prefix("animations/entity/creature/air/bird.animation.json"), ChaosAwakens.prefix("animations/entity/creature/air/butterfly.animation.json"),
                    ChaosAwakens.prefix("animations/entity/creature/land/ant.animation.json"), ChaosAwakens.prefix("animations/entity/creature/land/apple_cow.animation.json"), ChaosAwakens.prefix("animations/entity/creature/land/beaver.animation.json"), ChaosAwakens.prefix("animations/entity/creature/land/carrot_pig.animation.json"), ChaosAwakens.prefix("animations/entity/creature/land/gazelle.animation.json"), ChaosAwakens.prefix("animations/entity/creature/land/lettuce_chicken.animation.json"), ChaosAwakens.prefix("animations/entity/creature/land/ruby_bug.animation.json"), ChaosAwakens.prefix("animations/entity/creature/land/stink_bug.animation.json"), ChaosAwakens.prefix("animations/entity/creature/land/tree_frog.animation.json"),
                    ChaosAwakens.prefix("animations/entity/creature/water/whale.animation.json"),
                    ChaosAwakens.prefix("animations/entity/creature/water/fish/green_fish.animation.json"), ChaosAwakens.prefix("animations/entity/creature/water/fish/rock_fish.animation.json"), ChaosAwakens.prefix("animations/entity/creature/water/fish/spark_fish.animation.json"), ChaosAwakens.prefix("animations/entity/creature/water/fish/wood_fish.animation.json"),
                    ChaosAwakens.prefix("animations/entity/hostile/ent/ent.animation.json"),
                    ChaosAwakens.prefix("animations/entity/hostile/insect/cave_fisher.animation.json"), ChaosAwakens.prefix("animations/entity/hostile/insect/scorpion.animation.json"), ChaosAwakens.prefix("animations/entity/hostile/insect/wasp.animation.json"),
                    ChaosAwakens.prefix("animations/entity/hostile/robo/robo_pounder.animation.json"), ChaosAwakens.prefix("animations/entity/hostile/robo/robo_sniper.animation.json"), ChaosAwakens.prefix("animations/entity/hostile/robo/robo_warrior.animation.json"),
                    ChaosAwakens.prefix("animations/entity/hostile/water/attack_squid.animation.json"),
                    ChaosAwakens.prefix("animations/entity/misc/jeffery_shockwave.animation.json"),
                    ChaosAwakens.prefix("animations/entity/neutral/land/dimetrodon.animation.json"), ChaosAwakens.prefix("animations/entity/neutral/land/gator.animation.json"),
                    ChaosAwakens.prefix("animations/entity/neutral/lava/lava_eel.animation.json")
            ))); // Still better than several #add calls
    private static final ObjectArraySet<ResourceLocation> DEFAULT_ANIMATION_MODELS = Util.make(new ObjectArraySet<>(), (targetSet) -> targetSet.addAll(
            Lists.newArrayList(ChaosAwakens.prefix("geo/entity/boss/miniboss/hercules_beetle.geo.json"),
                    ChaosAwakens.prefix("geo/entity/boss/robo/robo_jeffery.geo.json"),
                    ChaosAwakens.prefix("geo/entity/creature/air/bird.geo.json"), ChaosAwakens.prefix("geo/entity/creature/air/butterfly.geo.json"),
                    ChaosAwakens.prefix("geo/entity/creature/land/ant.geo.json"), ChaosAwakens.prefix("geo/entity/creature/land/apple_cow.geo.json"), ChaosAwakens.prefix("geo/entity/creature/land/beaver.geo.json"), ChaosAwakens.prefix("geo/entity/creature/land/carrot_pig.geo.json"), ChaosAwakens.prefix("geo/entity/creature/land/gazelle.geo.json"), ChaosAwakens.prefix("geo/entity/creature/land/lettuce_chicken.geo.json"), ChaosAwakens.prefix("geo/entity/creature/land/ruby_bug.geo.json"), ChaosAwakens.prefix("geo/entity/creature/land/stink_bug.geo.json"), ChaosAwakens.prefix("geo/entity/creature/land/tree_frog.geo.json"),
                    ChaosAwakens.prefix("geo/entity/creature/water/whale.geo.json"),
                    ChaosAwakens.prefix("geo/entity/creature/water/fish/green_fish.geo.json"), ChaosAwakens.prefix("geo/entity/creature/water/fish/rock_fish.geo.json"), ChaosAwakens.prefix("geo/entity/creature/water/fish/spark_fish.geo.json"), ChaosAwakens.prefix("geo/entity/creature/water/fish/wood_fish.geo.json"),
                    ChaosAwakens.prefix("geo/entity/hostile/air/nightmare.geo.json"),
                    ChaosAwakens.prefix("geo/entity/hostile/ent/acacia_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/apple_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/birch_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/cherry_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/crimson_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/dark_oak_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/ginkgo_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/jungle_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/oak_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/peach_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/skywood_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/spruce_ent.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/ent/warped_ent.geo.json"),
                    ChaosAwakens.prefix("geo/entity/hostile/insect/cave_fisher.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/insect/scorpion.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/insect/wasp.geo.json"),
                    ChaosAwakens.prefix("geo/entity/hostile/robo/robo_pounder.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/robo/robo_sniper.geo.json"), ChaosAwakens.prefix("geo/entity/hostile/robo/robo_warrior.geo.json"),
                    ChaosAwakens.prefix("geo/entity/hostile/water/attack_squid.geo.json"),
                    ChaosAwakens.prefix("geo/entity/misc/jeffery_shockwave.geo.json"),
                    ChaosAwakens.prefix("geo/entity/neutral/land/dimetrodon.geo.json"), ChaosAwakens.prefix("geo/entity/neutral/land/gator.geo.json"),
                    ChaosAwakens.prefix("geo/entity/neutral/lava/lava_eel.geo.json")
            )));
    private static final ObjectArraySet<ResourceLocation> DEFAULT_ANIMATION_TEXTURES = Util.make(new ObjectArraySet<>(), (targetSet) -> targetSet.addAll(
            Lists.newArrayList(ChaosAwakens.prefix("textures/entity/boss/miniboss/modern_hercules_beetle.png"), ChaosAwakens.prefix("textures/entity/boss/miniboss/throwback_hercules_beetle.png"),
                    ChaosAwakens.prefix("textures/entity/boss/robo/robo_jeffery.png"),
                    ChaosAwakens.prefix("textures/entity/creature/air/bird/black.png"), ChaosAwakens.prefix("textures/entity/creature/air/bird/blue.png"), ChaosAwakens.prefix("textures/entity/creature/air/bird/brown.png"), ChaosAwakens.prefix("textures/entity/creature/air/bird/green.png"), ChaosAwakens.prefix("textures/entity/creature/air/bird/red.png"), ChaosAwakens.prefix("textures/entity/creature/air/bird/ruby.png"),
                    ChaosAwakens.prefix("textures/entity/creature/air/butterfly/blue.png"), ChaosAwakens.prefix("textures/entity/creature/air/butterfly/green.png"), ChaosAwakens.prefix("textures/entity/creature/air/butterfly/light_purple.png"), ChaosAwakens.prefix("textures/entity/creature/air/butterfly/orange.png"), ChaosAwakens.prefix("textures/entity/creature/air/butterfly/pink.png"), ChaosAwakens.prefix("textures/entity/creature/air/butterfly/purple.png"),
                    ChaosAwakens.prefix("textures/entity/creature/land/ant/brown_ant.png"), ChaosAwakens.prefix("textures/entity/creature/land/ant/rainbow_ant.png"), ChaosAwakens.prefix("textures/entity/creature/land/ant/red_ant.png"), ChaosAwakens.prefix("textures/entity/creature/land/ant/termite.png"), ChaosAwakens.prefix("textures/entity/creature/land/ant/unstable_ant.png"),
                    ChaosAwakens.prefix("textures/entity/creature/land/apple_cow/apple_cow.png"), ChaosAwakens.prefix("textures/entity/creature/land/apple_cow/crystal_cow.png"), ChaosAwakens.prefix("textures/entity/creature/land/apple_cow/golden_apple_cow.png"), ChaosAwakens.prefix("textures/entity/creature/land/apple_cow/halloween_apple_cow.png"), ChaosAwakens.prefix("textures/entity/creature/land/apple_cow/ultimate_apple_cow.png"),
                    ChaosAwakens.prefix("textures/entity/creature/land/carrot_pig/carrot_pig.png"), ChaosAwakens.prefix("textures/entity/creature/land/carrot_pig/crystal_carrot_pig.png"), ChaosAwakens.prefix("textures/entity/creature/land/carrot_pig/golden_carrot_pig.png"),
                    ChaosAwakens.prefix("textures/entity/creature/land/gazelle/beij_gazelle.png")
            )));

    public static void syncData() {
        
    }

    private static void writeData() {

    }

    private static boolean recieveData() {
        return true;
    }
}
