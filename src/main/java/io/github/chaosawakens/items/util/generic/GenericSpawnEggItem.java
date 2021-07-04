package io.github.chaosawakens.items.util.generic;

import io.github.chaosawakens.mixin.SpawnEggItemMixin;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class GenericSpawnEggItem extends SpawnEggItem {
    private final Supplier<? extends EntityType<?>> typeGetter;
    private final boolean isEnchanted;

    public GenericSpawnEggItem(Supplier<? extends EntityType<?>> type, Settings settings, boolean isEnchanted) {
        super(null, 0xFFFFFF, 0xFFFFFF, settings);
        this.typeGetter = type;
        this.isEnchanted = isEnchanted;
    }
    public GenericSpawnEggItem(Supplier<? extends EntityType<?>> type, Settings settings) {
        this(type, settings, false);
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        SpawnEggItemMixin.SPAWN_EGGS.put((EntityType<? extends MobEntity>) this.getEntityType(null), this);
        super.appendStacks(group, stacks);
    }

    @Override
    public EntityType<?> getEntityType(@Nullable NbtCompound nbt) {
        return typeGetter.get();
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return this.isEnchanted || super.hasGlint(stack);
    }
}
