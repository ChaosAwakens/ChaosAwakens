package io.github.chaosawakens.common.items;

import java.util.List;

import io.github.chaosawakens.api.IAutoEnchantable;
import io.github.chaosawakens.client.config.CAClientConfig;
import io.github.chaosawakens.common.config.CACommonConfig;
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

public class LavaEelArmorItem extends EnchantedArmorItem implements IAutoEnchantable {
	private final EnchantmentData[] enchantments;

	public LavaEelArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn, EnchantmentData[] enchantments) {
		super(materialIn, slot, builderIn, enchantments);
		this.enchantments = enchantments;
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		if (player.level.dimension().location() == Dimension.NETHER.location() || player.isInLava() || player.isOnFire()) {
			player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0, true, false));
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return CACommonConfig.COMMON.enableAutoEnchanting.get() || super.isFoil(stack);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if (!CAClientConfig.CLIENT.enableTooltips.get()) return;
		super.appendHoverText(stack, world, tooltip, flag);
		
		tooltip.add(new StringTextComponent("Full set bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("Eel-Like").withStyle(TextFormatting.DARK_RED)).append(new StringTextComponent(" (...)").withStyle(TextFormatting.GREEN)));
		
		if (Screen.hasShiftDown() || Screen.hasControlDown()) {
			tooltip.removeIf((s) -> s.toString().contains("(...)"));
			tooltip.add(new StringTextComponent("Full set bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("Eel-Like").withStyle(TextFormatting.DARK_RED))
					.append(new StringTextComponent("\nMakes lava act like water, allowing for fully-fledged swimming and swimming movement. Also grants better vision inside lava.").withStyle(TextFormatting.GREEN)));
		}
		
		if (!CACommonConfig.COMMON.enableLavaEelArmorSetBonus.get()) {
			tooltip.add(new StringTextComponent("This full set bonus is disabled in the config!").withStyle(TextFormatting.RED).withStyle(TextFormatting.BOLD));
		}
	}

	@Override
	public EnchantmentData[] enchantments() {
		return this.enchantments;
	}
}
