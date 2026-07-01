package io.github.chaosawakens.mixins.inventory;

import com.google.common.collect.Lists;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.item.equipment.armor.EmeraldArmorItem;
import io.github.chaosawakens.content.item.equipment.armor.LapisArmorItem;
import io.github.chaosawakens.core.template.CAArmorMaterialTemplates;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin {

    @Final
    @Shadow
    private Container enchantSlots;

    @Final
    @Shadow
    private ContainerLevelAccess access;

    @Final
    @Shadow
    private DataSlot enchantmentSeed;

    @Final
    @Shadow
    private RandomSource random;

    @WrapMethod(method = "clickMenuButton")
    private boolean yourHandlerMethod(Player player, int id, Operation<Boolean> original) {
        boolean isFullLapisSet = true;
        for (ItemStack stack : player.getArmorSlots()) {
            isFullLapisSet = isFullLapisSet && stack.getItem() instanceof LapisArmorItem;
        }
        EnchantmentMenu menu = (EnchantmentMenu) (Object) this;

        if (isFullLapisSet) {
            if (id >= 0 && id < menu.costs.length) {
                ItemStack itemStack = this.enchantSlots.getItem(0);
                ItemStack itemStack2 = this.enchantSlots.getItem(1);
                boolean fullLapis = isFullLapisSet;
                int i = id + 1;
                if ((itemStack2.isEmpty() || itemStack2.getCount() < i) && !player.getAbilities().instabuild) {
                    return false;
                } else if (menu.costs[id] <= 0 || itemStack.isEmpty() || (player.experienceLevel < i || player.experienceLevel < menu.costs[id]) && !player.getAbilities().instabuild) {
                    return false;
                } else {
                    this.access.execute((level, blockPos) -> {
                        ItemStack itemStack3 = itemStack;
                        List<EnchantmentInstance> list = this.getEnchantmentList(fullLapis, itemStack, id, (int)(Math.round(menu.costs[id] * 1.1)));
                        if (list != null && !list.isEmpty()) {
                            player.onEnchantmentPerformed(itemStack, i);
                            boolean bl = itemStack.is(Items.BOOK);
                            if (bl) {
                                itemStack3 = new ItemStack(Items.ENCHANTED_BOOK);
                                CompoundTag compoundTag = itemStack.getTag();
                                if (compoundTag != null) {
                                    itemStack3.setTag(compoundTag.copy());
                                }

                                this.enchantSlots.setItem(0, itemStack3);
                            }

                            for (EnchantmentInstance enchantmentInstance : list) {
                                if (bl) {
                                    EnchantedBookItem.addEnchantment(itemStack3, enchantmentInstance);
                                } else {
                                    itemStack3.enchant(enchantmentInstance.enchantment, enchantmentInstance.level);
                                }
                            }

                            if (!player.getAbilities().instabuild) {
                                itemStack2.shrink(i);
                                if (itemStack2.isEmpty()) {
                                    this.enchantSlots.setItem(1, ItemStack.EMPTY);
                                }
                            }

                            player.awardStat(Stats.ENCHANT_ITEM);
                            if (player instanceof ServerPlayer) {
                                CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)player, itemStack3, i);
                            }

                            this.enchantSlots.setChanged();
                            this.enchantmentSeed.set(player.getEnchantmentSeed());
                            menu.slotsChanged(this.enchantSlots);
                            level.playSound(null, blockPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);
                        }

                    });
                    return true;
                }
            } else {
                Component var10000 = player.getName();
                Util.logAndPauseIfInIde(var10000 + " pressed invalid button id: " + id);
                return false;
            }
        }
         else
             original.call(player, id);
        return true;
    }

    private List<EnchantmentInstance> getEnchantmentList(boolean isFullLapisSet, ItemStack stack, int enchantSlot, int level) {
        this.random.setSeed((long)(this.enchantmentSeed.get() + enchantSlot));
        List<EnchantmentInstance> list = selectEnchantment(isFullLapisSet, this.random, stack, level, false);
        if (stack.is(Items.BOOK) && list.size() > 1) {
            list.remove(this.random.nextInt(list.size()));
        }

        return list;
    }

    private static List<EnchantmentInstance> selectEnchantment(boolean isFullLapisSet, RandomSource random, ItemStack itemStack, int level, boolean allowTreasure) {
        List<EnchantmentInstance> list = Lists.newArrayList();
        Item item = itemStack.getItem();
        int i = item.getEnchantmentValue();
        if (i <= 0) {
            return list;
        } else {
            i = isFullLapisSet ? i + 5 : i;
            level += 1 + random.nextInt(i / 4 + 1) + random.nextInt(i / 4 + 1);
            float f = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
            level = Mth.clamp(Math.round((float)level + (float)level * f), 1, Integer.MAX_VALUE);
            List<EnchantmentInstance> list1 = EnchantmentHelper.getAvailableEnchantmentResults(level, itemStack, allowTreasure);
            if (!list1.isEmpty()) {
                Optional var10000 = WeightedRandom.getRandomItem(random, list1);
                Objects.requireNonNull(list);
                var10000.ifPresent((value) -> list.add((EnchantmentInstance)value));

                while(random.nextInt(50) <= level) {
                    if (!list.isEmpty()) {
                        EnchantmentHelper.filterCompatibleEnchantments(list1, (EnchantmentInstance)Util.lastOf(list));
                    }

                    if (list1.isEmpty()) {
                        break;
                    }

                    var10000 = WeightedRandom.getRandomItem(random, list1);
                    Objects.requireNonNull(list);
                    var10000.ifPresent((value) -> list.add((EnchantmentInstance)value));
                    level /= 2;
                }
            }

            return list;
        }
    }
}
