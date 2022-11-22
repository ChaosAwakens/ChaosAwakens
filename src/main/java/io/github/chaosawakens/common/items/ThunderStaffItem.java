package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.entity.projectile.ThunderStaffProjectileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ThunderStaffItem extends Item {

	public ThunderStaffItem(Properties builderIn) {
		super(builderIn);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack heldStack = playerIn.getItemInHand(handIn);

		if (!(worldIn instanceof ServerWorld)) return new ActionResult<>(ActionResultType.PASS, heldStack);

		float xA = -MathHelper.sin(playerIn.yHeadRot * ((float) Math.PI / 180F)) * MathHelper.cos(playerIn.xRot * ((float) Math.PI / 180F));
		float yA = -MathHelper.sin(playerIn.xRot * ((float) Math.PI / 180F));
		float zA = MathHelper.cos(playerIn.yHeadRot * ((float) Math.PI / 180F)) * MathHelper.cos(playerIn.xRot * ((float) Math.PI / 180F));

		ThunderStaffProjectileEntity projectile = new ThunderStaffProjectileEntity(worldIn, playerIn, xA, yA, zA);
		projectile.setPos(playerIn.getX(), playerIn.getY() + 1.5, playerIn.getZ());
		projectile.shootFromRotation(projectile, playerIn.xRot, playerIn.yHeadRot, 0, 1F, 0);

		if (!playerIn.isCreative()) heldStack.hurtAndBreak(1, playerIn, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));

		worldIn.addFreshEntity(projectile);
		worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);

		playerIn.awardStat(Stats.ITEM_USED.get(this));
		return new ActionResult<>(ActionResultType.SUCCESS, heldStack);
	}
}
