package io.github.chaosawakens.common.items;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class EnchantedCASpawnEggItem extends CASpawnEgg {
    private final Supplier<? extends EntityType<?>> typeGetter;

    public EnchantedCASpawnEggItem(Supplier<? extends EntityType<?>> typeIn, Properties properties) {
        super(null, properties);
        typeGetter = typeIn;
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_) {
        return typeGetter.get();
    }

    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}