package io.github.chaosawakens.client;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.client.config.CAClientConfig;
import io.github.chaosawakens.client.entity.render.*;
import io.github.chaosawakens.common.entity.EntEntity;
import io.github.chaosawakens.common.items.UltimateFishingRodItem;
import io.github.chaosawakens.common.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientSetupEvent implements IUtilityHelper {
	public static void register() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetupEvent::onFMLClientSetupEvent);
	}

	public static boolean def = false;

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class ClientHelper {
		@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
		@OnlyIn(Dist.CLIENT)
		public static void renderFogColor(EntityViewRenderEvent.FogColors event) {
			Entity entity = event.getRenderer().getMainCamera().getEntity();

			if (entity == null) return;

			if (entity.level.dimension() == CADimensions.CRYSTAL_WORLD) {
				event.setRed(1);
				event.setGreen(1);
				event.setBlue(1);
			}
		}

		@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
		@OnlyIn(Dist.CLIENT)
		public static void renderFog(EntityViewRenderEvent.FogDensity event) {
			Entity entity = event.getRenderer().getMainCamera().getEntity();

			if (entity == null) return;

			if (CAClientConfig.CLIENT.enableCrystalDimensionFog.get()) {
				if (entity.level.dimension() == CADimensions.CRYSTAL_WORLD) {
					event.setDensity(CAClientConfig.CLIENT.crystalDimensionFogDensity.get());
					event.setCanceled(true);
				}
			}
		}

		@SuppressWarnings("resource")
		@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
		@OnlyIn(Dist.CLIENT)
		public static void renderParticles(TickEvent.WorldTickEvent event) {
			ClientPlayerEntity player = Minecraft.getInstance().player;

			if (player == null) return;

			if (CAClientConfig.CLIENT.enableCrystalDimensionParticles.get()) {
				if (player.level.dimension() == CADimensions.CRYSTAL_WORLD) {
					IUtilityHelper.addParticles(player.level, ParticleTypes.WHITE_ASH, player.getX(), player.getEyeY(), player.getZ(), 50, 0.03, 50, CAClientConfig.CLIENT.crystalDimensionParticleDensity.get());
				}
			}
		}
	}

	public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
		ClientRegistry.bindTileEntityRenderer(CATileEntities.CUSTOM_SIGN.get(), SignTileEntityRenderer::new);
		event.enqueueWork(() -> {
			Atlases.addWoodType(CABlocks.APPLE);
			Atlases.addWoodType(CABlocks.CHERRY);
			Atlases.addWoodType(CABlocks.DUPLICATION);
			Atlases.addWoodType(CABlocks.PEACH);
			Atlases.addWoodType(CABlocks.SKYWOOD);
			Atlases.addWoodType(CABlocks.GINKGO);
		});

        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.HERCULES_BEETLE.get(), HerculesBeetleEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BIRD.get(), BirdEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.OAK_ENT.get(), (manager) -> new EntEntityRender(manager, EntEntity.Types.OAK));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ACACIA_ENT.get(), (manager) -> new EntEntityRender(manager, EntEntity.Types.ACACIA));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BIRCH_ENT.get(), (manager) -> new EntEntityRender(manager, EntEntity.Types.BIRCH));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.JUNGLE_ENT.get(), (manager) -> new EntEntityRender(manager, EntEntity.Types.JUNGLE));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.DARK_OAK_ENT.get(), (manager) -> new EntEntityRender(manager, EntEntity.Types.DARK_OAK));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.SPRUCE_ENT.get(), (manager) -> new EntEntityRender(manager, EntEntity.Types.SPRUCE));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CRIMSON_ENT.get(), (manager) -> new EntEntityRender(manager, EntEntity.Types.CRIMSON));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WARPED_ENT.get(), (manager) -> new EntEntityRender(manager, EntEntity.Types.WARPED));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RED_ANT.get(), (manager) -> new AggressiveAntEntityRender(manager, CAEntityTypes.RED_ANT.getId().getPath()));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BROWN_ANT.get(), (manager) -> new AntEntityRender(manager, CAEntityTypes.BROWN_ANT.getId().getPath()));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RAINBOW_ANT.get(), (manager) -> new AntEntityRender(manager, CAEntityTypes.RAINBOW_ANT.getId().getPath()));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.UNSTABLE_ANT.get(), (manager) -> new AntEntityRender(manager, CAEntityTypes.UNSTABLE_ANT.getId().getPath()));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.TERMITE.get(), (manager) -> new AggressiveAntEntityRender(manager, CAEntityTypes.TERMITE.getId().getPath()));
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.FROG.get(), FrogEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ULTIMATE_ARROW.get(), UltimateArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.IRUKANDJI_ARROW.get(), IrukandjiArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.THUNDER_BALL.get(), ThunderStaffProjectileRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.EXPLOSIVE_BALL.get(), RayGunProjectileRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ULTIMATE_FISHING_BOBBER.get(), UltimateBobberRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.APPLE_COW.get(), AppleCowEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GOLDEN_APPLE_COW.get(), GoldenAppleCowEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get(), EnchantedGoldenAppleCowEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CRYSTAL_APPLE_COW.get(), CrystalAppleCowEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CRYSTAL_GATOR.get(), CrystalGatorRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CRYSTAL_CARROT_PIG.get(), CrystalCarrotPigEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CARROT_PIG.get(), CarrotPigEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GOLDEN_CARROT_PIG.get(), GoldenCarrotPigEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get(), EnchantedGoldenCarrotPigEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BEAVER.get(), BeaverEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GAZELLE.get(), GazelleEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.DIMETRODON.get(), DimetrodonEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.EMERALD_GATOR.get(), EmeraldGatorEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RUBY_BUG.get(), RubyBugEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.STINK_BUG.get(), StinkBugEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_SNIPER.get(), RoboSniperEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_WARRIOR.get(), RoboWarriorEntityRender::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_POUNDER.get(), RoboPounderRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_LASER.get(), RoboLaserRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WASP.get(), WaspEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WHALE.get(), WhaleEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GREEN_FISH.get(), GreenFishEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROCK_FISH.get(), RockFishEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.SPARK_FISH.get(), SparkFishEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WOOD_FISH.get(), WoodFishRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.LAVA_EEL.get(), LavaEelEntityRender::new);

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

        RenderTypeLookup.setRenderLayer(CABlocks.DENSE_GRASS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.TALL_DENSE_GRASS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.THORNY_SUN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.BLUE_BULB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.PINK_BULB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.PURPLE_BULB.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(CABlocks.CYAN_ROSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.RED_ROSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.PAEONIA.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(CABlocks.APPLE_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.DUPLICATION_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.GINKGO_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.PEACH_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.SKYWOOD_TRAPDOOR.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(CABlocks.APPLE_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.PEACH_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.RED_CRYSTAL_SAPLING.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.GREEN_CRYSTAL_SAPLING.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.YELLOW_CRYSTAL_SAPLING.get(), RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(CABlocks.BLUE_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.BLUE_CRYSTAL_FLOWER.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.GREEN_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.GREEN_CRYSTAL_FLOWER.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.RED_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.RED_CRYSTAL_FLOWER.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.YELLOW_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.YELLOW_CRYSTAL_FLOWER.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.ORANGE_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.PINK_CRYSTAL_GROWTH.get(), RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_CYAN_ROSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_RED_ROSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PAEONIA.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_BLUE_BULB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PINK_BULB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PURPLE_BULB.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_APPLE_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_CHERRY_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PEACH_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_RED_CRYSTAL_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_GREEN_CRYSTAL_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_YELLOW_CRYSTAL_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_RED_CRYSTAL_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_BLUE_CRYSTAL_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_GREEN_CRYSTAL_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_YELLOW_CRYSTAL_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_RED_CRYSTAL_GROWTH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_BLUE_CRYSTAL_GROWTH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_GREEN_CRYSTAL_GROWTH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_YELLOW_CRYSTAL_GROWTH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_ORANGE_CRYSTAL_GROWTH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PINK_CRYSTAL_GROWTH.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(CABlocks.APPLE_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.DUPLICATION_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.GINKGO_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.PEACH_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.SKYWOOD_LEAVES.get(), RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(CABlocks.APPLE_LEAF_CARPET.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_LEAF_CARPET.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.DUPLICATION_LEAF_CARPET.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.GINKGO_LEAF_CARPET.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.PEACH_LEAF_CARPET.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.SKYWOOD_LEAF_CARPET.get(), RenderType.cutoutMipped());

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

        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_GRASS_BLOCK.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.KYANITE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_LOG.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_WOOD.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_PLANKS.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_SLAB.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_PRESSURE_PLATE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_FENCE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_FENCE_GATE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_BUTTON.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_STAIRS.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.RED_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.GREEN_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.YELLOW_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.PINK_TOURMALINE_CLUSTER.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CATS_EYE_CLUSTER.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.BUDDING_PINK_TOURMALINE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.BUDDING_CATS_EYE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_ENERGY.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_CRAFTING_TABLE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_FURNACE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.PINK_TOURMALINE_BLOCK.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CATS_EYE_BLOCK.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.get(), RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_TORCH.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.WALL_CRYSTAL_TORCH.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.SUNSTONE_TORCH.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.WALL_SUNSTONE_TORCH.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.EXTREME_TORCH.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.WALL_EXTREME_TORCH.get(), RenderType.cutoutMipped());

        ItemModelsProperties.register(CAItems.ULTIMATE_BOW.get(), new ResourceLocation("pull"),
                (stack, world, living) -> {
                    if (living == null) {
                        return 0.0F;
                    } else {
                        return living.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - living.getUseItemRemainingTicks()) / 8.0F;
                    }
                });
        ItemModelsProperties.register(CAItems.ULTIMATE_BOW.get(), new ResourceLocation("pulling"),
                (stack, world, living) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
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
//        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("filled_default"),
  //              (stack, world, living) -> {
  //              	return stack != null && stack.getTag().contains("entity") ? 1.0F : 0.0F;
  //       });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("def"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	//Inefficiency at its finest
                	ClientSetupEvent.def = false;
                	return stack != null && stack.getTag().contains("entity") && !ClientSetupEvent.def ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("apple_cow"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Apple Cow") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("bee"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Bee") && !stack.getItem().getName(stack).toString().contains("Hercules") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("bird"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Bird") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("carrot_pig"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	if (living instanceof PlayerEntity) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Carrot Pig") && !stack.getItem().getName(stack).toString().contains("Golden") && !stack.getItem().getName(stack).toString().contains("Crystal")? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("cat"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Cat") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("cave_spider"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Cave Spider") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("cow"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Cow") && !stack.getItem().getName(stack).toString().contains("Apple") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("creeper"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Creeper") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("donkey"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Donkey") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("drowned"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Drowned") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("enderman"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Enderman") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("fox"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Fox") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("horse"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Horse") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("husk"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Husk") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("llama"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Llama") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("mooshroom"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Mooshroom") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("ostrich"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Ostrich") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("panda"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Panda") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("pig"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Pig") && !stack.getItem().getName(stack).toString().contains("Carrot") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("piraporu"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Piraporu") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("polar_bear"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Polar Bear") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("rabbit"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Rabbit") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("sheep"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
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
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Slime") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("spider"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Spider") && !stack.getItem().getName(stack).toString().contains("Cave")? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("stray"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Stray") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("wolf"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Wolf") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("zombie_villager"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Zombie Villager") ? 1.0F : 0.0F;
         });
        ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("zombie"),
                (stack, world, living) -> {
                	if (stack.getTag() == null) return 0.0F;
                	assert stack.getTag() != null;
                	ClientSetupEvent.def = true;
                	return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Zombie") && !stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Villager") ? 1.0F : 0.0F;
         });
        CAContainerTypes.registerScreens(event);
    }
}
