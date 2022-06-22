package io.github.chaosawakens.common.items;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.util.EnumUtils;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

//Elytra set bonus W.I.P
public class EnderScaleArmorItem extends EnchantedArmorItem{
	public static final String ENDERELYTRA = ChaosAwakens.MODID + ":ElytraFullSetBonus";

	public EnderScaleArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn, EnchantmentData[] enchantments) {
		super(materialIn, slot, builderIn, enchantments);
	}
	
	public static ItemStack getElytraOnArmor(ItemStack stack, PlayerEntity player) {
		if (!isFullArmorSet(player)) return ItemStack.EMPTY;
		CompoundNBT elytraTag = stack.getTagElement(ENDERELYTRA);
		return elytraTag != null ? ItemStack.of(elytraTag) : ItemStack.EMPTY;
	}
	
	public static void toggleElytra(ItemStack chestPlate, ItemStack elytra) {
		chestPlate.getOrCreateTag().put(ENDERELYTRA, elytra.save(new CompoundNBT()));
	}
	
	public static boolean isElytraToggled(ItemStack stack) {
		return stack.getTagElement(ENDERELYTRA) != null;
	}
	
	public static void damage(ItemStack elytra, ItemStack chestPlate, LivingEntity entity, int damage) {
		EnumUtils.ElytraDamageType damageType = CACommonConfig.COMMON.enderDragonScaleArmorElytraDamageType.get();
		
		if (damageType == EnumUtils.ElytraDamageType.ELYTRA) {
			elytra.hurtAndBreak(damage, entity, e -> e.broadcastBreakEvent(EquipmentSlotType.CHEST));
		} else if (damageType == EnumUtils.ElytraDamageType.ARMOR) {
			if (chestPlate.isDamaged()) {
				elytra.setDamageValue(chestPlate.getDamageValue());
			}
		}
	}
	
	public static boolean canUse(ItemStack elytra, ItemStack chestPlate) {
		EnumUtils.ElytraDamageType damageType = CACommonConfig.COMMON.enderDragonScaleArmorElytraDamageType.get();
		
		if (elytra.isEmpty()) return false;
		if (damageType == EnumUtils.ElytraDamageType.ELYTRA) {
			return elytra.getItem() instanceof ElytraItem && ElytraItem.isFlyEnabled(elytra);
		} else if (damageType == EnumUtils.ElytraDamageType.ARMOR) {
			return chestPlate.getDamageValue() < chestPlate.getMaxDamage() - 1;
		}
		return true;
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (world.isClientSide) return;
		if (player.getArmorSlots() != null) {
			if (isFullArmorSet(player)) {
				ItemStack elytra = new ItemStack(Items.ELYTRA);
				toggleElytra(stack, elytra);
			}
		}
	}
	
	public static boolean isFullArmorSet(PlayerEntity player) {
		ItemStack helmet = new ItemStack(CAItems.ENDER_DRAGON_SCALE_HELMET.get());
		ItemStack chestplate = new ItemStack(CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get());
		ItemStack leggings = new ItemStack(CAItems.ENDER_DRAGON_SCALE_LEGGINGS.get());
		ItemStack boots = new ItemStack(CAItems.ENDER_DRAGON_SCALE_BOOTS.get());
		
		return player.getItemBySlot(EquipmentSlotType.HEAD) == helmet && player.getItemBySlot(EquipmentSlotType.CHEST) == chestplate && player.getItemBySlot(EquipmentSlotType.LEGS) == leggings && player.getItemBySlot(EquipmentSlotType.FEET) == boots;
	}

}
