package io.github.chaosawakens.data.provider;

import com.google.common.collect.Lists;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.AnimationDataHolder;
import io.github.chaosawakens.data.builder.provider.AnimationMetadataProvider;
import net.minecraft.data.DataGenerator;
import software.bernie.geckolib3.core.builder.ILoopType;

/**
 * TODO DISCLAIMER: THIS DATAPACKING SYSTEM IS VERY INEFFICIENT AND WILL NOT BE USED IN VERSIONS 1.20.1+ (Since we'd depend on the up n coming EEL by then). 1.19 WILL
 * USE A DIFFERENT, MUCH MORE SAFE AND EFFICIENT SYSTEM. DO NOT REFERENCE THIS FOR PRACTICE!!!
 */
public class CAAnimationMetadataProvider extends AnimationMetadataProvider {

    public CAAnimationMetadataProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void initializeAnimMetadata() { //TODO Automation doesn't work cuz Minecraft's Resource Manager doesn't exist on the server :p
        // Boss/Miniboss
        createAnimationMetadata(ChaosAwakens.prefix("animations/boss/insect/hercules_beetle.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 3.52D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Death", 0.76D, ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME),
                new AnimationDataHolder("Walk", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Leap", 2.76D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Fly", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Ram Attack", 1.16D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Grab", 0.64D, ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME),
                new AnimationDataHolder("Munch Attack", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Body Slam Attack", 2.08D, ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME),
                new AnimationDataHolder("Ground Slam", 0.44D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Docile", 14.32D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Awaken", 7.28D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Flail Flap", 3.88D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));

        // Boss/Robo
        createAnimationMetadata(ChaosAwakens.prefix("animations/boss/robo/robo_jeffery.animation.json"), Lists.newArrayList( //TODO Only apply max double value (1.7976931348623157E308) to looping anims with no predefined length, as that's what they default to in Geckolib3
                new AnimationDataHolder("Idle", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Right Punch Attack", 1.7D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Left Punch Attack", 1.7D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Robo-Bomb Attack", 2D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Smash Attack", 3.0D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Leap Attack: Leap", 0.81D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Leap Attack: Midair", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Leap Attack: Land", 2.45D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Idle Extras", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Death", 9.5D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Low Health", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Healthy", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP)));

        // Creature/Air
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/air/bird.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Fly", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Idle", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.8D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/air/butterfly.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 2.0D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Death", 5.16D, ILoopType.EDefaultLoopTypes.LOOP)));

        // Creature/Land
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/land/ant.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 2.6D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.64D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/land/apple_cow.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Run", 1.8D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/land/beaver.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 2.0D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 0.6667D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Gnaw", 2.0D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/land/carrot_pig.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.8D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/land/gazelle.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Run", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Graze", 1.8D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/land/lettuce_chicken.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Run", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Sit", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Flap", 1.8D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/land/ruby_bug.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.8D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/land/stink_bug.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.8D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/land/tree_frog.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 1.76D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Jump", 8.04D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Blink", 1.64D, ILoopType.EDefaultLoopTypes.LOOP)));

        // Creature/Water
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/water/whale.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 3.52D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Swim", 3.52D, ILoopType.EDefaultLoopTypes.LOOP)));

        // Creature/Water/Fish
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/water/fish/green_fish.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 0.88D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Swim", 0.88D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Flop", 1.8D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/water/fish/rock_fish.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 1.76D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Swim", 1.76D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/water/fish/spark_fish.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Swim", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Flop", 1.8D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/creature/water/fish/wood_fish.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Swim", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Flop", 1.8D, ILoopType.EDefaultLoopTypes.LOOP)));

        // Hostile/Ent
        createAnimationMetadata(ChaosAwakens.prefix("animations/hostile/ent/ent.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 3.6D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 3.6D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Left Punch", 1.8D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Right Punch", 1.8D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Smash Attack", 1.8D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Death", 3.6D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Idle Extras", 1.8D, ILoopType.EDefaultLoopTypes.LOOP)));

        // Hostile/Insect
        createAnimationMetadata(ChaosAwakens.prefix("animations/hostile/insect/cave_fisher.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 2.08D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Pinch Attack", 2.08D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Walk", 1.24D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Climb", 1.24D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Inverse Walk", 1.24D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Inverse Idle", 2.08D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk Attack", 1.24D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/hostile/insect/scorpion.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 2.0D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 2.0D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Pinch Attack", 0.8D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Death", 3.6D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/hostile/insect/wasp.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Fly", 1.56D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Pinch Attack", 1.72D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));

        // Hostile/Robo
        createAnimationMetadata(ChaosAwakens.prefix("animations/hostile/robo/robo_pounder.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Dash Attack", 3.2D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Right Heavy Attack", 1.75D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Left Heavy Attack", 1.75D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Right Leg Attack", 1.75D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Left Leg Attack", 1.75D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Right Swing Attack", 2.0D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Left Swing Attack", 2.0D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Heavy AoE Attack", 2.3D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Rage Begin", 3.5D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Rage Run", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Cooldown", 10.25D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Cooldown Restart", 1.6D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Rage Crash", 10.0D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Rage Crash Restart", 1.2D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Death", 3.78D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Taunt", 2.4D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("The California Gurls Dance", 8.5D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Pose 1", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Pose 2", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/hostile/robo/robo_sniper.animation.json"), Lists.newArrayList(
        		new AnimationDataHolder("Idle", 3.6D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Idle Extras", 3.6D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Accelerate", 3.6D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Shoot Attack", 0.64D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Death", 2.24D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/hostile/robo/robo_warrior.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 3.56D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Death", 3.5D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Activate Shield", 1.52D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Idle Extras", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Destroy Shield", 1.52D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Deactivate Shield", 1.52D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Shield Up", 3.56D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 3.56D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Charged Laser Attack", 5.32D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Burst Laser Attack", 2.56D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Left Uppercut Attack", 1.85D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Right Uppercut Attack", 1.85D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));

        // Hostile/Water
        createAnimationMetadata(ChaosAwakens.prefix("animations/hostile/water/attack_squid.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Swim", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Death", 5.2D, ILoopType.EDefaultLoopTypes.PLAY_ONCE),
                new AnimationDataHolder("Shoot", 1.56D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));

        // Misc
        createAnimationMetadata(ChaosAwakens.prefix("animations/misc/jeffery_shockwave.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Expand", 18.5D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));
        
        // Projectile
        createAnimationMetadata(ChaosAwakens.prefix("animations/hostile/water/robo_sniper_laser.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("always play", 5.04D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("death", 0.24D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));
        
        // Neutral/Land
        createAnimationMetadata(ChaosAwakens.prefix("animations/neutral/land/dimetrodon.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 3.52D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Swim", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Bite Attack", 0.64D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/neutral/land/gator.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 3.56D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Walk", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Swim", 1.8D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Bite Attack", 0.56D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));

        // Neutral/Lava
        createAnimationMetadata(ChaosAwakens.prefix("animations/neutral/lava/lava_eel.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Swim", 1.76D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Flop", 1.76D, ILoopType.EDefaultLoopTypes.LOOP)));

        // Projectiles
        createAnimationMetadata(ChaosAwakens.prefix("animations/projectiles/robo_sniper_laser.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Idle", 5.04D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Death", 0.24D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));
        createAnimationMetadata(ChaosAwakens.prefix("animations/projectiles/ultimate_bolt.animation.json"), Lists.newArrayList(
                new AnimationDataHolder("Moving", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Stuck", 1.7976931348623157E308D, ILoopType.EDefaultLoopTypes.LOOP),
                new AnimationDataHolder("Hit", 1.0D, ILoopType.EDefaultLoopTypes.PLAY_ONCE)));

  /*      MolangParser glibMolangParser = GeckoLibCache.getInstance().parser;

        try {
            FileUtil.findClientAnimResources().forEach(animRl -> {
                AnimationFileLoader loader = new AnimationFileLoader();
                IReloadableResourceManager resourceManager = (IReloadableResourceManager) Minecraft.getInstance().getResourceManager();
                AnimationFile animFile = loader.loadAllAnimations(glibMolangParser, animRl, resourceManager);
                List<AnimationDataHolder> animMD = new ObjectArrayList<>();

                animFile.getAllAnimations().forEach(targetAnim -> {
                    AnimationDataHolder newHolder = new AnimationDataHolder(targetAnim.animationName, targetAnim.animationLength, targetAnim.loop);

                    animMD.add(newHolder);
                });

                createAnimationMetadata(animRl, animMD);
            });
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }
}
