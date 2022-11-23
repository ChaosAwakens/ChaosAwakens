package io.github.chaosawakens.client;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.client.config.CAClientConfig;
import io.github.chaosawakens.client.entity.render.AggressiveAntEntityRenderer;
import io.github.chaosawakens.client.entity.render.AntEntityRenderer;
import io.github.chaosawakens.client.entity.render.AppleCowEntityRenderer;
import io.github.chaosawakens.client.entity.render.BeaverEntityRenderer;
import io.github.chaosawakens.client.entity.render.BirdEntityRenderer;
import io.github.chaosawakens.client.entity.render.CABoatRenderer;
import io.github.chaosawakens.client.entity.render.CAEmptyRenderer;
import io.github.chaosawakens.client.entity.render.CAFallingBlockRenderer;
import io.github.chaosawakens.client.entity.render.CarrotPigEntityRenderer;
import io.github.chaosawakens.client.entity.render.CrystalAppleCowEntityRenderer;
import io.github.chaosawakens.client.entity.render.CrystalCarrotPigEntityRenderer;
import io.github.chaosawakens.client.entity.render.CrystalGatorEntityRenderer;
import io.github.chaosawakens.client.entity.render.DimetrodonEntityRenderer;
import io.github.chaosawakens.client.entity.render.EmeraldGatorEntityRenderer;
import io.github.chaosawakens.client.entity.render.EnchantedGoldenAppleCowEntityRenderer;
import io.github.chaosawakens.client.entity.render.EnchantedGoldenCarrotPigEntityRenderer;
import io.github.chaosawakens.client.entity.render.EntEntityRenderer;
import io.github.chaosawakens.client.entity.render.GazelleEntityRenderer;
import io.github.chaosawakens.client.entity.render.GoldenAppleCowEntityRenderer;
import io.github.chaosawakens.client.entity.render.GoldenCarrotPigEntityRenderer;
import io.github.chaosawakens.client.entity.render.GreenFishEntityRenderer;
import io.github.chaosawakens.client.entity.render.HerculesBeetleEntityRenderer;
import io.github.chaosawakens.client.entity.render.IrukandjiArrowProjectileRenderer;
import io.github.chaosawakens.client.entity.render.LavaEelEntityRenderer;
import io.github.chaosawakens.client.entity.render.LeafyChickenEntityRenderer;
import io.github.chaosawakens.client.entity.render.RayGunProjectileRenderer;
import io.github.chaosawakens.client.entity.render.RoboLaserProjectileRenderer;
import io.github.chaosawakens.client.entity.render.RoboPounderEntityRenderer;
import io.github.chaosawakens.client.entity.render.RoboSniperEntityRenderer;
import io.github.chaosawakens.client.entity.render.RoboWarriorEntityRenderer;
import io.github.chaosawakens.client.entity.render.RockFishEntityRenderer;
import io.github.chaosawakens.client.entity.render.RubyBugEntityRenderer;
import io.github.chaosawakens.client.entity.render.SparkFishEntityRenderer;
import io.github.chaosawakens.client.entity.render.StinkBugEntityRenderer;
import io.github.chaosawakens.client.entity.render.ThunderStaffProjectileRenderer;
import io.github.chaosawakens.client.entity.render.TreeFrogEntityRenderer;
import io.github.chaosawakens.client.entity.render.UltimateAppleCowEntityRenderer;
import io.github.chaosawakens.client.entity.render.UltimateArrowProjectileRenderer;
import io.github.chaosawakens.client.entity.render.UltimateBobberProjectileRenderer;
import io.github.chaosawakens.client.entity.render.WaspEntityRenderer;
import io.github.chaosawakens.client.entity.render.WhaleEntityRenderer;
import io.github.chaosawakens.client.entity.render.WoodFishEntityRenderer;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.entity.EntEntity;
import io.github.chaosawakens.common.entity.HerculesBeetleEntity;
import io.github.chaosawakens.common.entity.nonliving.CAScreenShakeEntity;
import io.github.chaosawakens.common.entity.projectile.CALeafyChickenEggEntity;
import io.github.chaosawakens.common.items.EnderScaleArmorItem;
import io.github.chaosawakens.common.items.UltimateFishingRodItem;
import io.github.chaosawakens.common.particles.FartParticle.FartParticleProvider;
import io.github.chaosawakens.common.particles.RoboSparkParticle.RoboSparkParticleProvider;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAContainerTypes;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CAParticleTypes;
import io.github.chaosawakens.common.registry.CATileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

public class ClientSetupEvent {
	public static boolean def = false;

	public static class ClientEventsHelper {
		public static void renderFogColor(EntityViewRenderEvent.FogColors event) {
			Entity entity = event.getRenderer().getMainCamera().getEntity();

			if (entity == null)
				return;

			if (entity.level.dimension() == CADimensions.CRYSTAL_WORLD) {
				event.setRed(1);
				event.setGreen(1);
				event.setBlue(1);
			}
		}

		public static void renderFog(EntityViewRenderEvent.FogDensity event) {
			Entity entity = event.getRenderer().getMainCamera().getEntity();

			if (entity == null)
				return;

			if (CAClientConfig.CLIENT.enableCrystalDimensionFog.get()) {
				if (entity.level.dimension() == CADimensions.CRYSTAL_WORLD) {
					event.setDensity(CAClientConfig.CLIENT.crystalDimensionFogDensity.get());
					event.setCanceled(true);
				}
			}
			
			if (CACommonConfig.COMMON.enableLavaEelArmorSetBonus.get()) {
				//Avoiding exceptions when casting PlayerEntity onto entity
				if (entity instanceof PlayerEntity) {
					if (IUtilityHelper.isFullArmorSet((PlayerEntity) entity, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())) {
						if (entity.isEyeInFluid(FluidTags.LAVA)) {
							event.setDensity(CAClientConfig.CLIENT.lavaEelSetLavaFogDensity.get());
							event.setCanceled(true);
						}
					}
				}
 			}
		}

		@SuppressWarnings("resource")
		public static void renderParticles(TickEvent.WorldTickEvent event) {
			ClientPlayerEntity player = Minecraft.getInstance().player;

			if (player == null)
				return;

			if (CAClientConfig.CLIENT.enableCrystalDimensionParticles.get()) {
				if (player.level.dimension() == CADimensions.CRYSTAL_WORLD) {
					IUtilityHelper.addParticles(player.level, ParticleTypes.WHITE_ASH, player.getX(), player.getEyeY(), player.getZ(), 50, 0.03, 50, CAClientConfig.CLIENT.crystalDimensionParticleDensity.get());
				}
			}
		}
		
		public static void handleOverlay(RenderBlockOverlayEvent event) {
			if (CACommonConfig.COMMON.enableLavaEelArmorSetBonus.get()) {
				PlayerEntity player = event.getPlayer();
				if (IUtilityHelper.isFullArmorSet(player, CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())) {
					if (player.isEyeInFluid(FluidTags.LAVA) && player.hasEffect(Effects.FIRE_RESISTANCE) || player.isInLava() || player.isOnFire()) {
						event.getMatrixStack().translate(0, CAClientConfig.CLIENT.lavaEelSetFireStackTranslation.get(), 0);
					}
				}
			}
		}
		
		public static void onClientLoadComplete(FMLLoadCompleteEvent event) {
	        EntityRendererManager rendererManager = Minecraft.getInstance().getEntityRenderDispatcher();
	        rendererManager.getSkinMap().values().forEach(renderer -> {
	        	renderer.addLayer(new EnderScaleArmorItem.DragonElytraLayer<>(renderer));
	        });
		}
		
		@SuppressWarnings("resource")
		public static void onParticleRegistrationEvent(ParticleFactoryRegisterEvent event) {
			Minecraft.getInstance().particleEngine.register(CAParticleTypes.ROBO_SPARK.get(), RoboSparkParticleProvider::new);
			Minecraft.getInstance().particleEngine.register(CAParticleTypes.FART.get(), FartParticleProvider::new);
		}
		
		@SuppressWarnings("resource")
		public static void onPreRenderHUD(RenderGameOverlayEvent.Pre event) {
			ClientPlayerEntity player = Minecraft.getInstance().player;
			
			if (player != null) {
				if (player.isPassenger()) {
					if (player.getVehicle() instanceof HerculesBeetleEntity) {
		                if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT) event.setCanceled(true);
		                if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) Minecraft.getInstance().gui.setOverlayMessage(new TranslationTextComponent(""), false);
					}
				}
			}
		}
		
		public static void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
/*			MatrixStack stack = event.getMatrixStack();
			PlayerEntity player = event.getPlayer();
			
			if (player.getVehicle() != null && player.getVehicle().isAlive()) {
				if (player.getVehicle() instanceof HerculesBeetleEntity) {
					stack.pushPose();
					Vector3i facing = player.getVehicle().getDirection().getNormal();
					stack.translate(4 * facing.getX(), 0.7F, 4 * facing.getZ());
					stack.mulPose(Vector3f.XN.rotationDegrees(-90));
					stack.mulPose(Vector3f.ZN.rotationDegrees(-90));
					stack.popPose();
				}
			}*/
		}
		
		public static void onRenderPlayerHand(RenderHandEvent event) {
		}
		
		@SuppressWarnings("rawtypes")
		public static void onPreRenderLiving(RenderLivingEvent.Pre event) {
/*			MatrixStack stack = event.getMatrixStack();
			LivingEntity owner = event.getEntity();
			
			if (owner.getVehicle() != null && owner.getVehicle().isAlive()) {
				if (owner.getVehicle() instanceof HerculesBeetleEntity) {
					stack.pushPose();
					Vector3i facing = owner.getVehicle().getDirection().getNormal();
					stack.translate(4 * facing.getX(), 0.7F, 4 * facing.getZ());
					stack.mulPose(Vector3f.XN.rotationDegrees(-90));
					stack.mulPose(Vector3f.ZN.rotationDegrees(-90));
					stack.popPose();
				}
			}*/
		}
		
		@SuppressWarnings("resource")
		public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
			PlayerEntity clientPlayer = Minecraft.getInstance().player;
			// MM implementation
			if (CAClientConfig.CLIENT.enableCameraShake.get()) {
				float amp = 0F;
				
				for (CAScreenShakeEntity shaker : IUtilityHelper.getEntitiesAroundNoPredicate(clientPlayer, CAScreenShakeEntity.class, 20, 20, 20, 20)) {
					if (shaker.distanceTo(clientPlayer) < shaker.getRadius()) amp += shaker.getAmp(clientPlayer, Minecraft.getInstance().getFrameTime());
				}
				
				//Cap
				if (amp > 1.0F) amp = 1.0F;
				
				//Note to self: Don't use .random, and don't EVER leave this inside a loop. Seriously. -- Meme Man
                event.setPitch((float) (event.getPitch() + amp * Math.cos(Minecraft.getInstance().getFrameTime() * 3 + 2) * 25));
                event.setYaw((float) (event.getYaw() + amp * Math.cos(Minecraft.getInstance().getFrameTime() * 5 + 1) * 25));
                event.setRoll((float) (event.getRoll() + amp * Math.cos(Minecraft.getInstance().getFrameTime() * 4) * 25));
			}
		}
		
		@SuppressWarnings("rawtypes")
		public static void onPostRenderLiving(RenderLivingEvent.Pre event) {
		}
		
		public static void onPostRenderPlayer(RenderPlayerEvent.Post event) {
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

		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.HERCULES_BEETLE.get(), (manager) -> new HerculesBeetleEntityRenderer(manager, HerculesBeetleEntity.Type.MODERN));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.THROWBACK_HERCULES_BEETLE.get(), (manager) -> new HerculesBeetleEntityRenderer(manager, HerculesBeetleEntity.Type.THROWBACK));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BIRD.get(), BirdEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.OAK_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.OAK));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ACACIA_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.ACACIA));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.APPLE_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.APPLE));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BIRCH_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.BIRCH));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CHERRY_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.CHERRY));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.JUNGLE_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.JUNGLE));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.DARK_OAK_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.DARK_OAK));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.PEACH_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.PEACH));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.SKYWOOD_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.SKYWOOD));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.SPRUCE_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.SPRUCE));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CRIMSON_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.CRIMSON));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WARPED_ENT.get(), (manager) -> new EntEntityRenderer(manager, EntEntity.Types.WARPED));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RED_ANT.get(), (manager) -> new AggressiveAntEntityRenderer(manager, CAEntityTypes.RED_ANT.getId().getPath()));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BROWN_ANT.get(), (manager) -> new AntEntityRenderer(manager, CAEntityTypes.BROWN_ANT.getId().getPath()));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RAINBOW_ANT.get(), (manager) -> new AntEntityRenderer(manager, CAEntityTypes.RAINBOW_ANT.getId().getPath()));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.UNSTABLE_ANT.get(), (manager) -> new AntEntityRenderer(manager, CAEntityTypes.UNSTABLE_ANT.getId().getPath()));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.TERMITE.get(), (manager) -> new AggressiveAntEntityRenderer(manager, CAEntityTypes.TERMITE.getId().getPath()));
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.TREE_FROG.get(), TreeFrogEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ULTIMATE_ARROW.get(), UltimateArrowProjectileRenderer::new);
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
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.LEAFY_CHICKEN.get(), LeafyChickenEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.DIMETRODON.get(), DimetrodonEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.EMERALD_GATOR.get(), EmeraldGatorEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RUBY_BUG.get(), RubyBugEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.STINK_BUG.get(), StinkBugEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_SNIPER.get(), RoboSniperEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_WARRIOR.get(), RoboWarriorEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_POUNDER.get(), RoboPounderEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_LASER.get(), RoboLaserProjectileRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WASP.get(), WaspEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WHALE.get(), WhaleEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GREEN_FISH.get(), GreenFishEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROCK_FISH.get(), RockFishEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.SPARK_FISH.get(), SparkFishEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WOOD_FISH.get(), WoodFishEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.LAVA_EEL.get(), LavaEelEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.FALLING_BLOCK.get(), CAFallingBlockRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.SCREEN_SHAKE.get(), CAEmptyRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.LEAFY_CHICKEN_EGG.get(), (manager) -> new SpriteRenderer<CALeafyChickenEggEntity>(manager, Minecraft.getInstance().getItemRenderer()));

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
		
		RenderTypeLookup.setRenderLayer(CABlocks.APPLE_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.DUPLICATION_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.GINKGO_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.PEACH_DOOR.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.SKYWOOD_DOOR.get(), RenderType.cutout());
		
//		RenderTypeLookup.setRenderLayer(CABlocks.ROCK.get(), RenderType.cutout());

		RenderTypeLookup.setRenderLayer(CABlocks.APPLE_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.PEACH_SAPLING.get(), RenderType.cutout());
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
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_APPLE_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_CHERRY_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PEACH_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_RED_CRYSTAL_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_GREEN_CRYSTAL_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_YELLOW_CRYSTAL_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_PINK_CRYSTAL_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.POTTED_BLUE_CRYSTAL_SAPLING.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(CABlocks.ORANGE_CRYSTAL_SAPLING.get(), RenderType.cutout());
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
		RenderTypeLookup.setRenderLayer(CABlocks.PINK_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.BLUE_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.ORANGE_CRYSTAL_LEAVES.get(), RenderType.cutoutMipped());
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
		RenderTypeLookup.setRenderLayer(CABlocks.CRYSTALISED_CRYSTAL_CARROT_PIG.get(), RenderType.cutoutMipped());

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
/*		ItemModelsProperties.register(CAItems.ULTIMATE_CROSSBOW.get(), new ResourceLocation("pull"),
				(stack, world, living) -> {
					if (living == null) {
						return 0.0F;
					} else {
						return living.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - living.getUseItemRemainingTicks()) / 8.0F;
					}
				});*/
		ItemModelsProperties.register(CAItems.ULTIMATE_BOW.get(), new ResourceLocation("pulling"),
				(stack, world, living) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
/*		ItemModelsProperties.register(CAItems.ULTIMATE_CROSSBOW.get(), new ResourceLocation("pulling"),
				(stack, world, living) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);*/
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
		ItemModelsProperties.register(CAItems.CRITTER_CAGE.get(), new ResourceLocation("wasp"),
				(stack, world, living) -> {
					if (stack.getTag() == null) return 0.0F;
					assert stack.getTag() != null;
					ClientSetupEvent.def = true;
					return stack != null && stack.getTag().contains("entity") && stack.getItem().getName(stack).toString().contains("Wasp") ? 1.0F : 0.0F;
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
