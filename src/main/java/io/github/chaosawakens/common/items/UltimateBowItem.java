package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.entity.projectile.UltimateArrowEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class UltimateBowItem extends BowItem implements IVanishable {
	private final int[] enchantmentLevels;
	private final Enchantment[] enchantmentIds;
	
	public UltimateBowItem(Item.Properties builder, Enchantment[] enchants, int[] lvls) {
		super(builder);
		enchantmentIds = enchants;
		enchantmentLevels = lvls;
	}
	
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		if (!CAConfig.COMMON.enableAutoEnchanting.get())
			return;
		for (int i = 0; i < enchantmentIds.length; i++) {
			stack.addEnchantment(enchantmentIds[i], enchantmentLevels[i]);
		}
	}
	
	public void inventoryTick(ItemStack stack, World worldInD, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!CAConfig.COMMON.enableAutoEnchanting.get())
			return;
		if (EnchantmentHelper.getEnchantmentLevel(enchantmentIds[0], stack) <= 0) {
			for (int i = 0; i < enchantmentIds.length; i++) {
				stack.addEnchantment(enchantmentIds[i], enchantmentLevels[i]);
			}
		}
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			
			int i = this.getUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, true);
			if (i < 0)
				return;
			if (!worldIn.isRemote) {
				AbstractArrowEntity abstractarrowentity = createArrow(worldIn, playerentity);
				
				abstractarrowentity = customArrow(abstractarrowentity);
				abstractarrowentity.setDirectionAndMovement(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, 3.0F, 0F);
				abstractarrowentity.setIsCritical(true);
				abstractarrowentity.setDamage(abstractarrowentity.getDamage() + 3D);
				abstractarrowentity.setKnockbackStrength(2);
				abstractarrowentity.setFire(300);
				
				if (!playerentity.isCreative()) {
					stack.damageItem(1, entityLiving, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
				}
				
				worldIn.addEntity(abstractarrowentity);
				
				worldIn.playSound(null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
				playerentity.addStat(Stats.ITEM_USED.get(this));
			}
		}
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return 9000;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		
		playerIn.setActiveHand(handIn);
		return ActionResult.resultConsume(itemstack);
	}
	
	/**
	 * Get the predicate to match ammunition when searching the player's inventory,
	 * not their main/offhand
	 */
	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return ARROWS;
	}
	
	@Override
	public int func_230305_d_() {
		return 15;
	}
	
	public boolean hasEffect(ItemStack stack) {
		return CAConfig.COMMON.enableAutoEnchanting.get();
	}
	
	public AbstractArrowEntity createArrow(World worldIn, LivingEntity shooter) {
		return new UltimateArrowEntity(worldIn, shooter);
	}
}
