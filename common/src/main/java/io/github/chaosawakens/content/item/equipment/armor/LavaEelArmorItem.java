package io.github.chaosawakens.content.item.equipment.armor;

import io.github.chaosawakens.content.item.misc.EnchantedArmorItem;
import io.github.chaosawakens.content.registry.CAExtraLocalizations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class LavaEelArmorItem extends EnchantedArmorItem {

    public LavaEelArmorItem(ArmorMaterial material, Type type, Properties properties, Map<Enchantment, Integer> enchantments) {
        super(material, type, properties, enchantments);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.translatable(CAExtraLocalizations.SET_BONUS_LABEL.key()).withStyle(ChatFormatting.BOLD));
        tooltipComponents.add(Component.translatable(CAExtraLocalizations.LAVA_EEL_BONUS_DESCRIPTION.key()));
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }
}
