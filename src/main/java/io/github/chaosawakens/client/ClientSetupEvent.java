package io.github.chaosawakens.client;

import io.github.chaosawakens.client.entity.render.*;
import io.github.chaosawakens.common.entity.EntEntity;
import io.github.chaosawakens.common.items.UltimateFishingRodItem;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAContainerTypes;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.FishRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientSetupEvent {

    public static void register() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetupEvent::onFMLClientSetupEvent);
    }

    public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.HERCULES_BEETLE.get(), HerculesBeetleEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BIRD.get(), (manager) -> new BirdEntityRender(manager));
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
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.CARROT_PIG.get(), CarrotPigEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.GOLDEN_CARROT_PIG.get(), GoldenCarrotPigEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.BEAVER.get(), BeaverEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.EMERALD_GATOR.get(), EmeraldGatorEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.RUBY_BUG.get(), RubyBugEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.STINK_BUG.get(), StinkBugEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_SNIPER.get(), RoboSniperEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_WARRIOR.get(), RoboWarriorEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROBO_LASER.get(), RoboLaserRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WASP.get(), WaspEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WHALE.get(), WhaleEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.ROCK_FISH.get(), RockFishEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.WOOD_FISH.get(), WoodFishRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.SPARK_FISH.get(), SparkFishEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CAEntityTypes.LAVA_EEL.get(), LavaEelEntityRender::new);

        RenderTypeLookup.setRenderLayer(CABlocks.PINK_TOURMALINE_BLOCK.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CATS_EYE_BLOCK.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.TUBE_WORM.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.TUBE_WORM_PLANT.get(), RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(CABlocks.RED_ANT_NEST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.BROWN_ANT_NEST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.RAINBOW_ANT_NEST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.UNSTABLE_ANT_NEST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.TERMITE_NEST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_TERMITE_NEST.get(), RenderType.cutout());

		RenderTypeLookup.setRenderLayer(CABlocks.CORN_TOP_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.CORN_BODY_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.STRAWBERRY_PLANT.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.TOMATO_TOP_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.TOMATO_BODY_BLOCK.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.GOLDEN_MELON_STEM.get(), RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(CABlocks.ATTACHED_GOLDEN_MELON_STEM.get(), RenderType.cutoutMipped());
		
        RenderTypeLookup.setRenderLayer(CABlocks.APPLE_SAPLING.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_SAPLING.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.PEACH_SAPLING.get(), RenderType.cutoutMipped());
		
        RenderTypeLookup.setRenderLayer(CABlocks.APPLE_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CHERRY_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.PEACH_LEAVES.get(), RenderType.cutoutMipped());
		
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_GRASS_BLOCK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CABlocks.KYANITE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_LOG.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_WOOD.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(CABlocks.CRYSTAL_WOOD_PLANKS.get(), RenderType.cutoutMipped());
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

        CAContainerTypes.registerScreens(event);
    }
}
