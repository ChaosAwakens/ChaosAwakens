package io.github.chaosawakens;

import io.github.chaosawakens.items.ThunderStaffItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class GameEvents {

    @SubscribeEvent
    public static void livingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof PlayerEntity) {

            PlayerEntity player = (PlayerEntity)event.getEntity();

            double baseReach = 5.0D;

            if (player.isCreative()) {
                baseReach = 8.0D;
            }

            Objects.requireNonNull(player.getAttribute(ForgeMod.REACH_DISTANCE.get())).setBaseValue(baseReach);
            player.getHeldItemMainhand();
            Item item = player.getHeldItemMainhand().getItem();
            if (item instanceof ThunderStaffItem) {
                Objects.requireNonNull(player.getAttribute(ForgeMod.REACH_DISTANCE.get())).setBaseValue(baseReach + 20.0);
            }
        }
    }
}