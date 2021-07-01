package io.github.chaosawakens.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.SpawnEggItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(SpawnEggItem.class)
public class SpawnEggItemMixin {
    @Final
    @Shadow
    public static Map<EntityType<? extends MobEntity>, SpawnEggItem> SPAWN_EGGS;
}
