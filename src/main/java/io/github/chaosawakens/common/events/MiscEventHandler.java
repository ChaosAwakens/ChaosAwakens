package io.github.chaosawakens.common.events;

import io.github.chaosawakens.common.blocks.CAOreBlock;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.entity.RoboSniperEntity;
import io.github.chaosawakens.common.entity.RoboWarriorEntity;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.java.games.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GiantEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Objects;

public class MiscEventHandler {

    @SubscribeEvent
    public void LivingDeathEvent(LivingDeathEvent event) {
        if (CAConfig.COMMON.enableDragonEggRespawns.get()) {
            if (event.getEntityLiving() == null) return;
            MinecraftServer server = event.getEntity().getServer();
            if (server == null) return;
            if (event.getEntity().getCommandSenderWorld().equals(server.getLevel(World.END))) {
                if (event.getEntity() instanceof EnderDragonEntity) {
                    EnderDragonEntity dragon = (EnderDragonEntity) event.getEntity();
                    if (dragon.getDragonFight() != null && dragon.getDragonFight().hasPreviouslyKilledDragon()) {
                        event.getEntity().getCommandSenderWorld().setBlockAndUpdate(event.getEntity().getCommandSenderWorld().getHeightmapPos(Heightmap.Type.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.defaultBlockState());
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onMobDrops(LivingDropsEvent event) {
        ItemStack stack;
        ItemEntity drop;

        // ENDER DRAGON
        if (event.getEntityLiving() instanceof EnderDragonEntity)
        {
            EnderDragonEntity dragon = (EnderDragonEntity)event.getEntityLiving();

            //Drop #1: Ender Dragon Scales
            int amount = 8 + (int)(Math.random() * 6) + (int)(Math.random() * event.getLootingLevel() * 4);
            if (Objects.requireNonNull(dragon.getDragonFight()).hasPreviouslyKilledDragon()) amount /= 2; //Amount is halved with repeat kills.
            stack = new ItemStack(CAItems.ENDER_DRAGON_SCALE.get(), amount);
            drop = new ItemEntity(event.getEntityLiving().level, 0, 90, 0, stack);
            event.getDrops().add(drop);

            // Drop #2: Ender Dragon Head
            double chance = 0.1D + event.getLootingLevel() * 0.1D;
            if (Math.random() < chance && CAConfig.COMMON.mobHeadDrops.get()) {
                stack = new ItemStack(Items.DRAGON_HEAD, 1);
                drop = new ItemEntity(event.getEntityLiving().level, 0, 90, 0, stack);
                event.getDrops().add(drop);
            }
        }
        // ZOMBIE
        if (event.getEntityLiving() instanceof ZombieEntity)
        {
            // Drop #1: Zombie Head
            double chance = 0.1D + event.getLootingLevel() * 0.1D;
            if (Math.random() < chance && CAConfig.COMMON.mobHeadDrops.get()) {
                stack = new ItemStack(Items.ZOMBIE_HEAD, 1);
                drop = new ItemEntity(event.getEntityLiving().level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), stack);
                event.getDrops().add(drop);
            }
        }
        // SKELETON
        if (event.getEntityLiving() instanceof SkeletonEntity)
        {
            // Drop #1: Skeleton Skull
            double chance = 0.1D + event.getLootingLevel() * 0.1D;
            if (Math.random() < chance && CAConfig.COMMON.mobHeadDrops.get()) {
                stack = new ItemStack(Items.SKELETON_SKULL, 1);
                drop = new ItemEntity(event.getEntityLiving().level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), stack);
                event.getDrops().add(drop);
            }
        }
        // CREEPER
        if (event.getEntityLiving() instanceof CreeperEntity)
        {
            // Drop #1: Creeper Head
            double chance = 0.1D + event.getLootingLevel() * 0.1D;
            if (Math.random() < chance && CAConfig.COMMON.mobHeadDrops.get()) {
                stack = new ItemStack(Items.CREEPER_HEAD, 1);
                drop = new ItemEntity(event.getEntityLiving().level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), stack);
                event.getDrops().add(drop);
            }
        }
    }
    
    //Not functional for now, idk why :(
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @OnlyIn(Dist.CLIENT)
    public static void itemToolTips(final ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<ITextComponent> tooltip = event.getToolTip();
        if(stack.getItem() instanceof BlockItem) {
            Block block = ((BlockItem) stack.getItem()).getBlock();
            if (block instanceof CAOreBlock) {
                if (stack.getItem() == CABlocks.ALUMINUM_ORE.get().asItem()) {
                    tooltip.add(1, new TranslationTextComponent("tooltip.chaosawakens.aluminum_ore").withStyle(TextFormatting.AQUA));
                } else if (stack.getItem() == CABlocks.FOSSILISED_PIRAPORU.get().asItem().getItem()) {
                    tooltip.add(1, new TranslationTextComponent("tooltip.chaosawakens.fossilised_piraporu").withStyle(TextFormatting.AQUA));
                } else if (stack.getItem() == CABlocks.FOSSILISED_SCORPION.get().asItem().getItem()) {
                    tooltip.add(1, new TranslationTextComponent("tooltip.chaosawakens.fossilised_scorpion").withStyle(TextFormatting.AQUA));
                } else if (stack.getItem() == CABlocks.FOSSILISED_WTF.get().asItem().getItem()) {
//                    tooltip.add(1, new TranslationTextComponent("tooltip.chaosawakens.fossilised_wtf").withStyle(TextFormatting.AQUA));
                    tooltip.add(new StringTextComponent("tooltip.chaosawakens.fossilised_wtf").withStyle(TextFormatting.AQUA));
                }
//                else {
//                    tooltip.add(new TranslationTextComponent("tooltip.chaosawakens.default"));
//                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        //Make villagers afraid of our entities
        if (event.getEntity() instanceof VillagerEntity) {
            VillagerEntity villager = (VillagerEntity) event.getEntity();
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, RoboSniperEntity.class, 24.0F, 0.5D, 0.5D));
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, RoboWarriorEntity.class, 32.0F, 0.5D, 0.5D));
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, GiantEntity.class, 32.0F, 0.5D, 0.5D));
        }
        if (event.getEntity() instanceof WanderingTraderEntity) {
            WanderingTraderEntity wanderingTrader = (WanderingTraderEntity) event.getEntity();
            wanderingTrader.goalSelector.addGoal(1, new AvoidEntityGoal<>(wanderingTrader, RoboSniperEntity.class, 24.0F, 0.5D, 0.5D));
            wanderingTrader.goalSelector.addGoal(1, new AvoidEntityGoal<>(wanderingTrader, RoboWarriorEntity.class, 32.0F, 0.5D, 0.5D));
            wanderingTrader.goalSelector.addGoal(1, new AvoidEntityGoal<>(wanderingTrader, GiantEntity.class, 32.0F, 0.5D, 0.5D));
        }
    }
}