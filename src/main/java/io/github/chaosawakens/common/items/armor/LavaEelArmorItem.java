package io.github.chaosawakens.common.items.armor;

import io.github.chaosawakens.common.items.base.EnchantedArmorItem;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class LavaEelArmorItem extends EnchantedArmorItem {

	public LavaEelArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn, Supplier<EnchantmentData[]> enchantments) {
		super(materialIn, slot, builderIn, enchantments);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);

		if (player.level.dimension().location() == Dimension.NETHER.location() || player.isInLava() || player.isOnFire())
			player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0, true, false));
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get() || super.isFoil(stack);
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if (!CAConfigManager.MAIN_CLIENT.enableTooltips.get()) return;
		super.appendHoverText(stack, world, tooltip, flag);

		tooltip.add(new StringTextComponent("Full set bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("Eel-Like").withStyle(TextFormatting.DARK_RED)).append(new StringTextComponent(" (...)").withStyle(TextFormatting.GREEN)));

		if (Screen.hasShiftDown() || Screen.hasControlDown()) {
			tooltip.removeIf((s) -> s.toString().contains("(...)"));
			tooltip.add(new StringTextComponent("Full set bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("Eel-Like").withStyle(TextFormatting.DARK_RED))
					.append(new StringTextComponent("\nMakes lava act like water, allowing for fully-fledged swimming and swimming movement. Also grants better vision inside lava.").withStyle(TextFormatting.GREEN)));
		}

		if (!CAConfigManager.MAIN_COMMON.enableLavaEelArmorSetBonus.get()) {
			tooltip.add(new StringTextComponent("This full set bonus is disabled in the config!").withStyle(TextFormatting.RED).withStyle(TextFormatting.BOLD));
		}
	}
}
