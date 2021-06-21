package io.github.chaosawakens.common;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.entity.RoboSniperEntity;
import io.github.chaosawakens.common.entity.RoboWarriorEntity;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {

    @SubscribeEvent
    public void LivingDeathEvent(LivingDeathEvent event) {
        if (CAConfig.COMMON.enableDragonEggRespawns.get()) {
            if (event.getEntityLiving() == null) return;
            MinecraftServer server = event.getEntity().getServer();
            if (server == null) return;
            if (event.getEntity().getEntityWorld().equals(server.getWorld(World.THE_END))) {
                if (event.getEntity() instanceof EnderDragonEntity) {
                    EnderDragonEntity dragon = (EnderDragonEntity) event.getEntity();
                    if (dragon.getFightManager() != null && dragon.getFightManager().hasPreviouslyKilledDragon()) {
                        event.getEntity().getEntityWorld().setBlockState(event.getEntity().getEntityWorld().getHeight(Heightmap.Type.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.getDefaultState());
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
            if (dragon.getFightManager().hasPreviouslyKilledDragon()) amount /= 2; //Amount is halved with repeat kills.
            stack = new ItemStack(CAItems.ENDER_DRAGON_SCALE.get(), amount);
            drop = new ItemEntity(event.getEntityLiving().world, 0, 90, 0, stack);
            event.getDrops().add(drop);

            // Drop #2: Ender Dragon Head
            double chance = 0.1D + event.getLootingLevel() * 0.1D;
            if (!dragon.getFightManager().hasPreviouslyKilledDragon()) amount = 1; // 1st kill is guaranteed.
            if (Math.random() < chance && CAConfig.COMMON.mobHeadDrops.get()) {
                stack = new ItemStack(Items.DRAGON_HEAD, 1);
                drop = new ItemEntity(event.getEntityLiving().world, 0, 90, 0, stack);
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
                drop = new ItemEntity(event.getEntityLiving().world, event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), stack);
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
                drop = new ItemEntity(event.getEntityLiving().world, event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), stack);
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
                drop = new ItemEntity(event.getEntityLiving().world, event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), stack);
                event.getDrops().add(drop);
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
        }
        if (event.getEntity() instanceof WanderingTraderEntity) {
            WanderingTraderEntity wanderingTrader = (WanderingTraderEntity) event.getEntity();
            wanderingTrader.goalSelector.addGoal(1, new AvoidEntityGoal<>(wanderingTrader, RoboSniperEntity.class, 24.0F, 0.5D, 0.5D));
            wanderingTrader.goalSelector.addGoal(1, new AvoidEntityGoal<>(wanderingTrader, RoboWarriorEntity.class, 32.0F, 0.5D, 0.5D));
        }
    }
}