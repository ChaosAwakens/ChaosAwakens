package io.github.chaosawakens.common;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {

    @SubscribeEvent
    public void LivingDeathEvent(LivingDeathEvent event) {
        if (CAConfig.COMMON.enableDragonEggRespawns.get()) {
            MinecraftServer server = event.getEntity().getServer();
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


    // A method to handle LivingDropsEvents
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onMobDrops(LivingDropsEvent event) {
        ItemStack stack;
        ItemEntity drop;

        //ENDER DRAGONS
        if (event.getEntityLiving() instanceof EnderDragonEntity)
        {
            EnderDragonEntity boss = (EnderDragonEntity)event.getEntityLiving();

            //Drop #1: Ender Dragon Scales
            int amount = 6 + (int)(Math.random() * 3) + (int)(Math.random() * event.getLootingLevel() * 2);
            if (boss.getFightManager().hasPreviouslyKilledDragon()) amount /= 2; //Amount is halved with repeat kills.
            stack = new ItemStack(CAItems.PEACOCK_FEATHER.get(), amount);
            drop = new ItemEntity(event.getEntityLiving().world, 0, 90, 0, stack);
            event.getDrops().add(drop);

            //Drop #2: Ender Dragon Head
            double chance = 0.05D + event.getLootingLevel() * 0.05D;
            if (!boss.getFightManager().hasPreviouslyKilledDragon()) amount = 1; //1st kill is guaranteed.
            if (Math.random() < chance && CAConfig.COMMON.mobHeadDrops.get()) {
                stack = new ItemStack(Items.DRAGON_HEAD, 1);
                drop = new ItemEntity(event.getEntityLiving().world, 0, 90, 0, stack);
                event.getDrops().add(drop);
            }
        }
        //ZOMBIE
        if (event.getEntityLiving() instanceof ZombieEntity)
        {
            //Drop #1: Zombie Head
            double chance = 0.05D + event.getLootingLevel() * 0.05D;
            if (Math.random() < chance && CAConfig.COMMON.mobHeadDrops.get()) {
                stack = new ItemStack(Items.ZOMBIE_HEAD, 1);
                drop = new ItemEntity(event.getEntityLiving().world, event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), stack);
                event.getDrops().add(drop);
            }
        }
    }
}