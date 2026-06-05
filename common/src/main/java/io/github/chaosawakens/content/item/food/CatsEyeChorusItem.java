package io.github.chaosawakens.content.item.food;

import io.github.chaosawakens.content.registry.CADimensions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.item.ChorusFruitItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Set;


public class CatsEyeChorusItem extends ChorusFruitItem{
    public CatsEyeChorusItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack targetStack, Level curLevel, LivingEntity livingOwner) {
        ItemStack resultStack = super.finishUsingItem(targetStack, curLevel, livingOwner);
        double x = livingOwner.getPosition(1F).x;
        double y = livingOwner.getPosition(1F).y;
        double z = livingOwner.getPosition(1F).z;

        if (!curLevel.isClientSide && curLevel instanceof ServerLevel level) {
            var levelKeys = CADimensions.getLevelKeys();

            if (!levelKeys.isEmpty()) {
                ResourceKey<Level> randomLevelKey = levelKeys
                        .get(livingOwner.getRandom().nextInt(levelKeys.size())).get();

                ServerLevel destination = level.getServer().getLevel(randomLevelKey);

                if (destination != null) {
                    livingOwner.teleportTo(destination, x, y ,z, Set.of(RelativeMovement.X, RelativeMovement.Y, RelativeMovement.Z), 0F,0F);
                }
            }
        }

        return resultStack;
    }
}
