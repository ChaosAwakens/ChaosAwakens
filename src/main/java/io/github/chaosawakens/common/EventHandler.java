package io.github.chaosawakens.common;

import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.block.Blocks;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
}