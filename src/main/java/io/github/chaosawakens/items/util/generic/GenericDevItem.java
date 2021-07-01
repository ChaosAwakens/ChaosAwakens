package io.github.chaosawakens.items.util.generic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GenericDevItem extends Item {
    public GenericDevItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return super.use(world, user, hand);
    }
}
