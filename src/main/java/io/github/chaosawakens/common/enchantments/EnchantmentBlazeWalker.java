package io.github.chaosawakens.common.enchantments;

import java.util.function.Consumer;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CAEnchantments;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.GenericEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.IEventBusInvokeDispatcher;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EnchantmentBlazeWalker extends Enchantment {

	public EnchantmentBlazeWalker(Rarity rarity, EnchantmentType type, EquipmentSlotType[] slotType) {
		super(rarity, EnchantmentType.ARMOR_FEET, slotType);
	}

	@Override
	public int getMinCost(int cost) {
		return 20;
	}
	
	@Override
	public int getMaxCost(int cost) {
		return super.getMinCost(cost) + 10;
	}
	
     @Override
    public boolean isTradeable() {
    	return true;
    }
	
     @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
    	return true;
    }
	
     @Override
    public int getMinLevel() {
    	return 1;
    }
     
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return true;
	}
	
	@Override
	protected boolean checkCompatibility(Enchantment ench) {
		return super.checkCompatibility(ench) && ench != Enchantments.FROST_WALKER;
	}

	@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class BlazeOfGloryEquippedAndFunctions {
		
		@SubscribeEvent
		public static void enchantmentFunctions(PlayerTickEvent event) {
			PlayerEntity playerIn = event.player;
			World worldIn = playerIn.level;
			
			if (playerIn.isOnGround()) {
				if(worldIn.getBlockState(playerIn.blockPosition().below()).getBlock() == Blocks.LAVA) {
					worldIn.setBlockAndUpdate(playerIn.blockPosition(), Blocks.NETHERRACK.defaultBlockState());
					worldIn.setBlockAndUpdate(playerIn.blockPosition().below().below().below(), Blocks.NETHERRACK.defaultBlockState());
				}
			}
		}
		
	}
	
}
