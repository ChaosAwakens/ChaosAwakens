package io.github.chaosawakens.common.items;

import java.util.function.Predicate;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.dto.EnchantmentAndLevel;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.entity.projectile.UltimateArrowEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class UltimateBowItem extends BowItem implements IVanishable {
	
	private final EnchantmentAndLevel[] enchantments;
	
	public UltimateBowItem(Properties builderIn, EnchantmentAndLevel[] enchantments) {
		super(builderIn);
		this.enchantments = enchantments;
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			ItemStack stack = new ItemStack(this);
			if (CAConfig.COMMON.enableAutoEnchanting.get())
				for(EnchantmentAndLevel enchant : enchantments) {
					stack.addEnchantment( enchant.getEnchantment(), enchant.getEnchantLevel());
				}
			items.add(stack);
		}
		ChaosAwakens.enchantedItems.put(this.getRegistryName(), enchantments);
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
				abstractarrowentity.setFire(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0 ? 250 : 75);

				int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
				if (!CAConfig.COMMON.enableAutoEnchanting.get()) {
					abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double) j * 0.5D + 2D);
				}
				else {
					abstractarrowentity.setDamage(abstractarrowentity.getDamage() + 3D);
				}

				int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
				abstractarrowentity.setKnockbackStrength(!CAConfig.COMMON.enableAutoEnchanting.get() ? k+1 : 1);
				
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
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return CAConfig.COMMON.enableAutoEnchanting.get();
	}
	
	private AbstractArrowEntity createArrow(World worldIn, LivingEntity shooter) {
		return new UltimateArrowEntity(worldIn, shooter);
	}
}
