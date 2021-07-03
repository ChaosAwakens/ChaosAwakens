package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.entity.projectile.RayGunProjectileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RayGunItem extends Item {
	private final IItemTier tier;

	public RayGunItem(IItemTier tierIn, Properties builderIn) {
		super(builderIn);
		this.tier = tierIn;
	}

	public IItemTier getTier() {
		return this.tier;
	}

	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return this.tier.getRepairMaterial().test(repair) || super.getIsRepairable(toRepair, repair);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack heldStack = playerIn.getHeldItem(handIn);

		if (!(worldIn instanceof ServerWorld))
			return new ActionResult(ActionResultType.PASS, heldStack);
		
		float xA = -MathHelper.sin(playerIn.rotationYawHead * ((float) Math.PI / 180F)) * MathHelper.cos(playerIn.rotationPitch * ((float) Math.PI / 180F));
		float yA = -MathHelper.sin(playerIn.rotationPitch * ((float) Math.PI / 180F));
		float zA = MathHelper.cos(playerIn.rotationYawHead * ((float) Math.PI / 180F)) * MathHelper.cos(playerIn.rotationPitch * ((float) Math.PI / 180F));

		RayGunProjectileEntity projectile = new RayGunProjectileEntity(worldIn, playerIn, xA, yA, zA);
		projectile.setPosition(playerIn.getPosX(), playerIn.getPosY()+1.5, playerIn.getPosZ());
		projectile.setDirectionAndMovement(projectile, playerIn.rotationPitch, playerIn.rotationYawHead, 0, 1F, 0);
		
		if (!playerIn.isCreative()) {
			heldStack.damageItem(1, playerIn, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
		}
		
		worldIn.addEntity(projectile);
		worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
		
		playerIn.addStat(Stats.ITEM_USED.get(this));
		return new ActionResult(ActionResultType.SUCCESS, heldStack);
	}
}