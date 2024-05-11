package io.github.chaosawakens.client.events;

import io.github.chaosawakens.client.renderers.entity.boss.insect.HerculesBeetleEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.boss.robo.RoboJefferyEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.air.BirdEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.AntEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.BeaverEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.GazelleEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.LettuceChickenEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.RubyBugEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.StinkBugEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.TreeFrogEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.applecow.AppleCowEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.applecow.CrystalAppleCowEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.applecow.EnchantedGoldenAppleCowEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.applecow.GoldenAppleCowEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.applecow.UltimateAppleCowEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.carrotpig.CarrotPigEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.carrotpig.CrystalCarrotPigEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.carrotpig.EnchantedGoldenCarrotPigEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.land.carrotpig.GoldenCarrotPigEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.water.WhaleEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.water.fish.GreenFishEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.water.fish.RockFishEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.water.fish.SparkFishEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.creature.water.fish.WoodFishEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.hostile.AggressiveAntEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.hostile.EntEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.hostile.insect.WaspEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.hostile.robo.RoboPounderEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.hostile.robo.RoboSniperEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.hostile.robo.RoboWarriorEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.misc.CABoatRenderer;
import io.github.chaosawakens.client.renderers.entity.misc.CAEmptyRenderer;
import io.github.chaosawakens.client.renderers.entity.misc.CAFallingBlockRenderer;
import io.github.chaosawakens.client.renderers.entity.misc.JefferyShockwaveEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.misc.UltimateBobberProjectileRenderer;
import io.github.chaosawakens.client.renderers.entity.neutral.land.dino.CrystalGatorEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.neutral.land.dino.DimetrodonEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.neutral.land.gator.EmeraldGatorEntityRenderer;
import io.github.chaosawakens.client.renderers.entity.projectile.*;
import io.github.chaosawakens.common.blocks.tileentities.screens.CopperDefossilizerScreen;
import io.github.chaosawakens.common.blocks.tileentities.screens.CrystalDefossilizerScreen;
import io.github.chaosawakens.common.blocks.tileentities.screens.IronDefossilizerScreen;
import io.github.chaosawakens.common.entity.projectile.CALettuceChickenEggEntity;
import io.github.chaosawakens.common.items.armor.EnderScaleArmorItem;
import io.github.chaosawakens.common.items.tools.UltimateFishingRodItem;
import io.github.chaosawakens.common.particles.FartParticle.FartParticleProvider;
import io.github.chaosawakens.common.particles.RoboSparkParticle.RoboSparkParticleProvider;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAContainerTypes;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CAParticleTypes;
import io.github.chaosawakens.common.registry.CATileEntities;
import io.github.chaosawakens.common.registry.CAWoodTypes;
import io.github.chaosawakens.common.util.EnumUtil.EntType;
import io.github.chaosawakens.common.util.EnumUtil.HerculesBeetleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

public class CAClientSetupEvents {
	private static boolean defaultInverse = false;
	
	@SubscribeEvent
	public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
		ClientRegistry.bindTileEntityRenderer(CATileEntities.CUSTOM_SIGN.get(), SignTileEntityRenderer::new);
		
		event.enqueueWork(CAWoodTypes::registerWoodTypesAtlas);

		// ENTITIES
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.HERCULES_BEETLE.get(), (manager) -> new HerculesBeetleEntityRenderer(manager, HerculesBeetleType.MODERN));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.THROWBACK_HERCULES_BEETLE.get(), (manager) -> new HerculesBeetleEntityRenderer(manager, HerculesBeetleType.THROWBACK));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BIRD.get(), BirdEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.OAK_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.OAK));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ACACIA_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.ACACIA));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.APPLE_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.APPLE));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BIRCH_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.BIRCH));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CHERRY_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.CHERRY));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.JUNGLE_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.JUNGLE));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.DARK_OAK_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.DARK_OAK));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GINKGO_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.GINKGO));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.PEACH_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.PEACH));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.SKYWOOD_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.SKYWOOD));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.SPRUCE_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.SPRUCE));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CRIMSON_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.CRIMSON));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WARPED_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntType.WARPED));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RED_ANT.get(), (manager) -> new AggressiveAntEntityRenderer(manager, CAEntityTypes.RED_ANT.getId().getPath()));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BROWN_ANT.get(), (manager) -> new AntEntityRenderer(manager, CAEntityTypes.BROWN_ANT.getId().getPath()));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RAINBOW_ANT.get(), (manager) -> new AntEntityRenderer(manager, CAEntityTypes.RAINBOW_ANT.getId().getPath()));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.UNSTABLE_ANT.get(), (manager) -> new AntEntityRenderer(manager, CAEntityTypes.UNSTABLE_ANT.getId().getPath()));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.TERMITE.get(), (manager) -> new AggressiveAntEntityRenderer(manager, CAEntityTypes.TERMITE.getId().getPath()));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.TREE_FROG.get(), TreeFrogEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ULTIMATE_ARROW.get(), UltimateArrowProjectileRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ULTIMATE_CROSSBOW_BOLT.get(), UltimateCrossbowBoltEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.IRUKANDJI_ARROW.get(), IrukandjiArrowProjectileRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.THUNDER_BALL.get(), ThunderStaffProjectileRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.EXPLOSIVE_BALL.get(), RayGunProjectileRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ULTIMATE_FISHING_BOBBER.get(), UltimateBobberProjectileRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CA_BOAT.get(), CABoatRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.APPLE_COW.get(), AppleCowEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GOLDEN_APPLE_COW.get(), GoldenAppleCowEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get(), EnchantedGoldenAppleCowEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CRYSTAL_APPLE_COW.get(), CrystalAppleCowEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ULTIMATE_APPLE_COW.get(), UltimateAppleCowEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CRYSTAL_GATOR.get(), CrystalGatorEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CRYSTAL_CARROT_PIG.get(), CrystalCarrotPigEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CARROT_PIG.get(), CarrotPigEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GOLDEN_CARROT_PIG.get(), GoldenCarrotPigEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get(), EnchantedGoldenCarrotPigEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BEAVER.get(), BeaverEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GAZELLE.get(), GazelleEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.LETTUCE_CHICKEN.get(), LettuceChickenEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.DIMETRODON.get(), DimetrodonEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.EMERALD_GATOR.get(), EmeraldGatorEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RUBY_BUG.get(), RubyBugEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.STINK_BUG.get(), StinkBugEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_SNIPER.get(), RoboSniperEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_WARRIOR.get(), RoboWarriorEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_JEFFERY.get(),  RoboJefferyEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_POUNDER.get(), RoboPounderEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_LASER.get(), RoboLaserProjectileRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WASP.get(), WaspEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WHALE.get(), WhaleEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GREEN_FISH.get(), GreenFishEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROCK_FISH.get(), RockFishEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.SPARK_FISH.get(), SparkFishEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WOOD_FISH.get(), WoodFishEntityRenderer::new);
	//	RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.LAVA_EEL.get(), LavaEelEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.FALLING_BLOCK.get(), CAFallingBlockRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.SCREEN_SHAKE.get(), CAEmptyRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BASE_AOE_HITBOX.get(), CAEmptyRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.JEFFERY_SHOCKWAVE.get(), JefferyShockwaveEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.LETTUCE_CHICKEN_EGG.get(), (manager) -> new SpriteRenderer<CALettuceChickenEggEntity>(manager, Minecraft.getInstance().getItemRenderer()));

		// BLOCKS
		RenderTypeLookup.setRenderLayer(CABlocks.TUBE_WORM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.TUBE_WORM_PLANT.get(), RenderType.cutoutMipped());

		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_GRASS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.TALL_CRYSTAL_GRASS.get(), RenderType.cutoutMipped());

		RenderTypeLookup.setRenderLayer(CABlocks.RED_ANT_NEST.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.BROWN_ANT_NEST.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.RAINBOW_ANT_NEST.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.UNSTABLE_ANT_NEST.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.TERMITE_NEST.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_TERMITE_NEST.get(), RenderType.cutout());

		RenderTypeLookup.setRenderLayer(CABlocks.DENSE_GRASS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.DENSE_GRASS_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.TALL_DENSE_GRASS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.DENSE_RED_ANT_NEST.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.THORNY_SUN.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.ALSTROEMERIAT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.DENSE_ORCHID.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.SMALL_BUSH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.TALL_BUSH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.SMALL_CARNIVOROUS_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.BIG_CARNIVOROUS_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.MESOZOIC_VINES.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.MESOZOIC_VINES_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.BLUE_BULB.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.PINK_BULB.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.PURPLE_BULB.get(), RenderType.cutout());

		RenderTypeLookup.setRenderLayer(CABlocks.CYAN_ROSE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.RED_ROSE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.PAEONIA.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.SWAMP_MILKWEED.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.DAISY.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.PRIMROSE.get(), RenderType.cutout());

		RenderTypeLookup.setRenderLayer(CABlocks.APPLE_TRAPDOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_TRAPDOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.DUPLICATION_TRAPDOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.GINKGO_TRAPDOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.MESOZOIC_TRAPDOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.DENSEWOOD_TRAPDOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.PEACH_TRAPDOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.SKYWOOD_TRAPDOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALWOOD_TRAPDOOR.get(), RenderType.cutout());
		
		RenderTypeLookup.setRenderLayer(CABlocks.APPLE_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.DUPLICATION_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.GINKGO_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.MESOZOIC_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.DENSEWOOD_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.PEACH_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.SKYWOOD_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALWOOD_DOOR.get(), RenderType.cutout());
		
		RenderTypeLookup.setRenderLayer(CABlocks.APPLE_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.PEACH_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.GINKGO_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.MESOZOIC_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.DENSEWOOD_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.RED_CRYSTAL_SAPLING.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.GREEN_CRYSTAL_SAPLING.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.YELLOW_CRYSTAL_SAPLING.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.PINK_CRYSTAL_SAPLING.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.BLUE_CRYSTAL_SAPLING.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.ORANGE_CRYSTAL_SAPLING.get(), RenderType.cutoutMipped());

		RenderTypeLookup.setRenderLayer(CABlocks.BLUE_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.BLUE_CRYSTAL_FLOWER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.GREEN_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.GREEN_CRYSTAL_FLOWER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.RED_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.RED_CRYSTAL_FLOWER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.YELLOW_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.YELLOW_CRYSTAL_FLOWER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.PINK_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.PINK_CRYSTAL_FLOWER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.ORANGE_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.ORANGE_CRYSTAL_FLOWER.get(), RenderType.cutoutMipped());
		
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_ROSE.get(), RenderType.cutoutMipped());

		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_CYAN_ROSE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_RED_ROSE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PAEONIA.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_BLUE_BULB.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PINK_BULB.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PURPLE_BULB.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_DENSE_ORCHID.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_SWAMP_MILKWEED.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_SMALL_BUSH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_SMALL_CARNIVOROUS_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_BIG_CARNIVOROUS_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PRIMROSE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_DAISY.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_APPLE_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_CHERRY_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PEACH_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_GINKGO_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_MESOZOIC_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_DENSEWOOD_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_RED_CRYSTAL_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_GREEN_CRYSTAL_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_YELLOW_CRYSTAL_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PINK_CRYSTAL_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_BLUE_CRYSTAL_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_ORANGE_CRYSTAL_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_RED_CRYSTAL_FLOWER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_BLUE_CRYSTAL_FLOWER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_GREEN_CRYSTAL_FLOWER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_YELLOW_CRYSTAL_FLOWER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PINK_CRYSTAL_FLOWER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_ORANGE_CRYSTAL_FLOWER.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_RED_CRYSTAL_GROWTH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_BLUE_CRYSTAL_GROWTH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_GREEN_CRYSTAL_GROWTH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_YELLOW_CRYSTAL_GROWTH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_ORANGE_CRYSTAL_GROWTH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PINK_CRYSTAL_GROWTH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_CRYSTAL_ROSE.get(), RenderType.cutout());

		RenderTypeLookup.setRenderLayer(CABlocks.APPLE_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.DUPLICATION_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.GINKGO_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.DENSEWOOD_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.MESOZOIC_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.PEACH_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.SKYWOOD_LEAVES.get(), RenderType.cutoutMipped());

		RenderTypeLookup.setRenderLayer(CABlocks.APPLE_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.DUPLICATION_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.GINKGO_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.MESOZOIC_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.DENSEWOOD_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.PEACH_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.SKYWOOD_LEAF_CARPET.get(), RenderType.cutoutMipped());
		
		RenderTypeLookup.setRenderLayer(CABlocks.RED_CRYSTAL_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.GREEN_CRYSTAL_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.YELLOW_CRYSTAL_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.PINK_CRYSTAL_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.BLUE_CRYSTAL_LEAF_CARPET.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.ORANGE_CRYSTAL_LEAF_CARPET.get(), RenderType.cutoutMipped());

		RenderTypeLookup.setRenderLayer(CABlocks.OAK_LEAF_CARPET.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.SPRUCE_LEAF_CARPET.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.BIRCH_LEAF_CARPET.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.JUNGLE_LEAF_CARPET.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.ACACIA_LEAF_CARPET.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.DARK_OAK_LEAF_CARPET.get(), RenderType.cutout());

		RenderTypeLookup.setRenderLayer(CABlocks.CORN_TOP_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CORN_BODY_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.STRAWBERRY_BUSH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.TOMATO_TOP_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.TOMATO_BODY_BLOCK.get(), RenderType.cutoutMipped());

		RenderTypeLookup.setRenderLayer(CABlocks.LETTUCE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.RADISH.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.QUINOA.get(), RenderType.cutout());

		RenderTypeLookup.setRenderLayer(CABlocks.ROBO_GLASS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.ROBO_GLASS_PANE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.ROBO_BARS.get(), RenderType.cutoutMipped());

		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_GRASS_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.KYANITE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALWOOD_LOG.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALWOOD.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALWOOD_PLANKS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALWOOD_SLAB.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_PRESSURE_PLATE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALWOOD_FENCE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALWOOD_FENCE_GATE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALWOOD_BUTTON.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALWOOD_STAIRS.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.RED_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.GREEN_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.YELLOW_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.PINK_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.BLUE_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.ORANGE_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.PINK_TOURMALINE_CLUSTER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CATS_EYE_CLUSTER.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.BUDDING_PINK_TOURMALINE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.BUDDING_CATS_EYE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.ENERGIZED_KYANITE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_CRAFTING_TABLE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_FURNACE.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.PINK_TOURMALINE_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CATS_EYE_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALISED_CRYSTAL_CARROT_PIG.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALISED_CRYSTAL_GATOR.get(), RenderType.cutoutMipped());

		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_TORCH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.WALL_CRYSTAL_TORCH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.SUNSTONE_TORCH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.WALL_SUNSTONE_TORCH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.EXTREME_TORCH.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.WALL_EXTREME_TORCH.get(), RenderType.cutoutMipped());

		// ITEMS
		ItemModelsProperties.register(CAItems.ULTIMATE_BOW.get(), new ResourceLocation("pull"),
				(stack, world, living) -> {
					if (living == null) {
						return 0.0F;
					} else {
						return living.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - living.getUseItemRemainingTicks()) / 8.0F;
					}
				});
		ItemModelsProperties.register(CAItems.ULTIMATE_CROSSBOW.get(), new ResourceLocation("pull"),
				(stack, world, living) -> {
					if (living == null) {
						return 0.0F;
					} else {
						return CrossbowItem.isCharged(stack) ? 0.0F : (float) (living.getTicksUsingItem()) / (float) CrossbowItem.getChargeDuration(stack);
					}
				});
		ItemModelsProperties.register(CAItems.ULTIMATE_BOW.get(), new ResourceLocation("pulling"),
				(stack, world, living) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
		ItemModelsProperties.register(CAItems.ULTIMATE_CROSSBOW.get(), new ResourceLocation("pulling"),
				(stack, world, living) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
		ItemModelsProperties.register(CAItems.ULTIMATE_CROSSBOW.get(), new ResourceLocation("charged"),
				(stack, world, living) -> living != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
		ItemModelsProperties.register(CAItems.ULTIMATE_CROSSBOW.get(), new ResourceLocation("firework"),
						(stack, world, living) -> living != null && CrossbowItem.isCharged(stack)
						&& CrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
		ItemModelsProperties.register(CAItems.SKATE_STRING_BOW.get(), new ResourceLocation("pull"),
				(stack, world, living) -> {
					if (living == null) {
						return 0.0F;
					} else {
						return living.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
					}
				});
		ItemModelsProperties.register(CAItems.SKATE_STRING_BOW.get(), new ResourceLocation("pulling"),
				(stack, world, living) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
		ItemModelsProperties.register(CAItems.ULTIMATE_FISHING_ROD.get(), new ResourceLocation("cast"), (p_239422_0_, p_239422_1_, p_239422_2_) -> {
			if (p_239422_2_ == null) {
				return 0.0F;
			} else {
				boolean flag = p_239422_2_.getMainHandItem() == p_239422_0_;
				boolean flag1 = p_239422_2_.getOffhandItem() == p_239422_0_;
				if (p_239422_2_.getMainHandItem().getItem() instanceof UltimateFishingRodItem) {
					flag1 = false;
				}

				return (flag || flag1) && p_239422_2_ instanceof PlayerEntity && ((PlayerEntity) p_239422_2_).fishing != null ? 1.0F : 0.0F;
			}
		});
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("default"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					//Inefficiency at its finest
					defaultInverse = false;
					return stack != null && stack.getTag().contains("entity") && !defaultInverse ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("apple_cow"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Apple Cow") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("bee"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Bee") && !stack.getItem().getName(stack).toString().contains("Hercules") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("bird"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Bird") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("carrot_pig"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					if (living instanceof PlayerEntity) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Carrot Pig") && !stack.getItem().getName(stack).toString().contains("Golden") && !stack.getItem().getName(stack).toString().contains("Crystal")? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("cat"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Cat") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("cave_spider"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Cave Spider") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("cow"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Cow") && !stack.getItem().getName(stack).toString().contains("Apple") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("creeper"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Creeper") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("donkey"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Donkey") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("drowned"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Drowned") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("enderman"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Enderman") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("fox"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Fox") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("horse"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Horse") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("husk"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Husk") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("llama"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Llama") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("mooshroom"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Mooshroom") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("ostrich"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Ostrich") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("panda"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Panda") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("pig"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Pig") && !stack.getItem().getName(stack).toString().contains("Carrot") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("piraporu"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Piraporu") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("polar_bear"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Polar Bear") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("rabbit"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Rabbit") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("sheep"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Sheep") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("skeleton"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Skeleton") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("slime"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Slime") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("spider"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Spider") && !stack.getItem().getName(stack).toString().contains("Cave")? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("stray"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Stray") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("wasp"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Wasp") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("wolf"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Wolf") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("zombie_villager"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Zombie Villager") ? 1.0F : 0.0F;
		 });
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("zombie"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					defaultInverse = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Zombie") && !stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Villager") ? 1.0F : 0.0F;
		 });
		
		ScreenManager.register(CAContainerTypes.COPPER_DEFOSSILIZER.get(), CopperDefossilizerScreen::new);
		ScreenManager.register(CAContainerTypes.IRON_DEFOSSILIZER.get(), IronDefossilizerScreen::new);
		ScreenManager.register(CAContainerTypes.CRYSTAL_DEFOSSILIZER.get(), CrystalDefossilizerScreen::new);
	}
	
	@SubscribeEvent
	public static void onFMLClientLoadCompleteEvent(FMLLoadCompleteEvent event) {
		EntityRendererManager rendererManager = Minecraft.getInstance().getEntityRenderDispatcher();
		rendererManager.getSkinMap().values().forEach(renderer -> {
			renderer.addLayer(new EnderScaleArmorItem.DragonElytraLayer<>(renderer));
		});
	}
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onParticleFactoryRegistrationEvent(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particleEngine.register(CAParticleTypes.ROBO_SPARK.get(), RoboSparkParticleProvider::new);
//		Minecraft.getInstance().particleEngine.register(CAParticleTypes.JEFFERY_SHOCKWAVE.get(), JefferyShockwaveParticleProvider::new);
		Minecraft.getInstance().particleEngine.register(CAParticleTypes.FART.get(), FartParticleProvider::new);
	}
	
	@SubscribeEvent
	public static void onHandleBlockColorsEvent(ColorHandlerEvent.Block event) {
		final IBlockColor grassBlockColor = (state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColors.get(0.5D, 1.0D);
		final IBlockColor foliageBlockColor = (state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColors.getDefaultColor();
		final IBlockColor spruceFoliageBlockColor = (state, reader, pos, color) -> FoliageColors.getEvergreenColor();
		final IBlockColor birchFoliageBlockColor = (state, reader, pos, color) -> FoliageColors.getBirchColor();
		
		event.getBlockColors().register(grassBlockColor, CABlocks.RED_ANT_NEST.get(), CABlocks.BROWN_ANT_NEST.get(), CABlocks.RAINBOW_ANT_NEST.get(), CABlocks.UNSTABLE_ANT_NEST.get(), CABlocks.TERMITE_NEST.get(), CABlocks.DENSE_GRASS.get(), CABlocks.DENSE_GRASS_BLOCK.get(), CABlocks.DENSE_RED_ANT_NEST.get(), CABlocks.TALL_DENSE_GRASS.get());
		event.getBlockColors().register(foliageBlockColor, CABlocks.OAK_LEAF_CARPET.get(), CABlocks.JUNGLE_LEAF_CARPET.get(), CABlocks.ACACIA_LEAF_CARPET.get(), CABlocks.DARK_OAK_LEAF_CARPET.get());
		event.getBlockColors().register(spruceFoliageBlockColor, CABlocks.SPRUCE_LEAF_CARPET.get());
		event.getBlockColors().register(birchFoliageBlockColor, CABlocks.BIRCH_LEAF_CARPET.get());
	}

	@SubscribeEvent
	public static void onHandleItemColorsEvent(ColorHandlerEvent.Item event) {
		event.getItemColors().register(
				(stack, color) -> event.getBlockColors().getColor(((BlockItem) stack.getItem()).getBlock().defaultBlockState(), null, null, color),
				CABlocks.RED_ANT_NEST.get(), CABlocks.BROWN_ANT_NEST.get(), CABlocks.RAINBOW_ANT_NEST.get(),
				CABlocks.UNSTABLE_ANT_NEST.get(), CABlocks.TERMITE_NEST.get(), CABlocks.OAK_LEAF_CARPET.get(),
				CABlocks.SPRUCE_LEAF_CARPET.get(), CABlocks.BIRCH_LEAF_CARPET.get(), CABlocks.JUNGLE_LEAF_CARPET.get(),
				CABlocks.ACACIA_LEAF_CARPET.get(), CABlocks.DARK_OAK_LEAF_CARPET.get(),
				CABlocks.DENSE_GRASS.get(), CABlocks.DENSE_GRASS_BLOCK.get(), CABlocks.DENSE_RED_ANT_NEST.get(), CABlocks.TALL_DENSE_GRASS.get());
	}
}
