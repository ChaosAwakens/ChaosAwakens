package io.github.chaosawakens.common.items;

import java.util.function.Predicate;

import io.github.chaosawakens.api.IPreEnchanted;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.entity.projectile.UltimateArrowEntity;
import net.minecraft.enchantment.EnchantmentData;
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
import net.minecraftforge.event.ForgeEventFactory;

public class UltimateBowItem extends BowItem implements IVanishable, IPreEnchanted {
	
	private final EnchantmentData[] enchantments;
	
	public UltimateBowItem(Properties builderIn, EnchantmentData[] enchantments) {
		super(builderIn);
		this.enchantments = enchantments;
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			ItemStack stack = new ItemStack(this);
			if (CAConfig.COMMON.enableAutoEnchanting.get())
				for(EnchantmentData enchant : enchantments) {
					stack.addEnchantment( enchant.enchantment, enchant.enchantmentLevel);
				}
			items.add(stack);
		}
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			
			if (ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, this.getUseDuration(stack) - timeLeft, true) < 0)return;
			if (!worldIn.isRemote) {
				AbstractArrowEntity arrowEntity = new UltimateArrowEntity(worldIn, entityLiving);
				arrowEntity.setDirectionAndMovement(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, 3.0F, 0F);
				arrowEntity.setIsCritical(true);
				arrowEntity.setFire(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0 ? 250 : 75);

				int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
				if (!CAConfig.COMMON.enableAutoEnchanting.get()) {
					arrowEntity.setDamage(arrowEntity.getDamage() + (double) powerLevel * 0.5D + 2D);
				}
				else {
					arrowEntity.setDamage(arrowEntity.getDamage() + 3D);
				}

				int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
				arrowEntity.setKnockbackStrength(!CAConfig.COMMON.enableAutoEnchanting.get() ? k+1 : 1);
				
				if (!playerentity.isCreative()) {
					stack.damageItem(1, entityLiving, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
				}
				
				worldIn.addEntity(arrowEntity);
				
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

	@Override
	public EnchantmentData[] enchant() {
		return this.enchantments;
	}
}
