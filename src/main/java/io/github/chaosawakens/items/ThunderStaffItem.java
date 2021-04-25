package io.github.chaosawakens.items;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
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
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.getDamage() > 0 || !stack.isDamaged()) {
            if (!(worldIn instanceof ServerWorld))
                return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
            LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);

            RayTraceResult lookingAt = Minecraft.getInstance().objectMouseOver;
            if (lookingAt != null && lookingAt.getType() == RayTraceResult.Type.BLOCK) {

                ChaosAwakens.LOGGER.debug(lookingAt.getHitVec().getX());
                ChaosAwakens.LOGGER.debug(lookingAt.getHitVec().getY());
                ChaosAwakens.LOGGER.debug(lookingAt.getHitVec().getZ());

                lightning.setPosition(lookingAt.getHitVec().getX(), lookingAt.getHitVec().getY(), lookingAt.getHitVec().getZ());

                ItemStack held = playerIn.getHeldItem(handIn);

                if (!playerIn.isCreative()) {
                    held.setDamage(held.getDamage() + 1);
                }
                if (held.getDamage() == -1) {
                    held.shrink(1);
                }

                ((ServerWorld) worldIn).summonEntity(lightning);
                ((ServerWorld) worldIn).createExplosion(playerIn, lookingAt.getHitVec().getX(), lookingAt.getHitVec().getY(), lookingAt.getHitVec().getZ(), 6, true, Explosion.Mode.DESTROY);
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}