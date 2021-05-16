package io.github.chaosawakens.items;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.config.CAConfig;
import io.github.chaosawakens.entity.projectile.ThunderStaffProjectileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SEntityPacket;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ThunderStaffItem extends Item {
	
	public ThunderStaffItem(Properties builderIn) {
		super(builderIn);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!(worldIn instanceof ServerWorld))
			return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
		
		ItemStack heldStack = playerIn.getHeldItem(handIn);
		
		float xA = -MathHelper.sin(playerIn.rotationYawHead * ((float) Math.PI / 180F)) * MathHelper.cos(playerIn.rotationPitch * ((float) Math.PI / 180F));
		float yA = -MathHelper.sin(playerIn.rotationPitch * ((float) Math.PI / 180F));
		float zA = MathHelper.cos(playerIn.rotationYawHead * ((float) Math.PI / 180F)) * MathHelper.cos(playerIn.rotationPitch * ((float) Math.PI / 180F));
		
		ThunderStaffProjectileEntity absFireball = new ThunderStaffProjectileEntity(worldIn, playerIn, xA, yA, zA);
		absFireball.setPosition(playerIn.getPosX(), playerIn.getPosY()+1.5, playerIn.getPosZ());
		absFireball.setDirectionAndMovement(absFireball, playerIn.rotationPitch, playerIn.rotationYawHead, 0, 1F, 0);
		
		ChaosAwakens.LOGGER.debug(xA + "  " + yA + " " + zA);
		if (!playerIn.isCreative()) {
			heldStack.damageItem(1, playerIn, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
		}
		
		worldIn.addEntity(absFireball);
		worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
		
		playerIn.addStat(Stats.ITEM_USED.get(this));
		return ActionResult.resultSuccess(heldStack);
	}
}