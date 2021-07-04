package io.github.chaosawakens.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ThunderStaff extends Item {
    public ThunderStaff(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack heldStack = user.getStackInHand(hand);
        if (!(world instanceof ServerWorld))
            return new TypedActionResult<>(ActionResult.PASS, heldStack);

        float xA = -MathHelper.sin(user.getHeadYaw() * ((float) Math.PI / 180F)) * MathHelper.cos(user.getPitch() * ((float) Math.PI / 180F));
        float yA = -MathHelper.sin(user.getPitch() * ((float) Math.PI / 180F));
        float zA = MathHelper.cos(user.getHeadYaw() * ((float) Math.PI / 180F)) * MathHelper.cos(user.getPitch() * ((float) Math.PI / 180F));

        //ThunderStaffProjectileEntity projectile = new ThunderStaffProjectileEntity(world, user, xA, yA, zA);
        //projectile.setPosition(user.getX(), user.getY()+1.5, user.getZ());
        //projectile.setDirectionAndMovement(projectile, user.getPitch(), user.getHeadYaw(), 0, 1F, 0);

        if (!user.isCreative()) {
            heldStack.damage(1, user, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }

        //world.spawnEntity(projectile);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (user.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return new TypedActionResult<>(ActionResult.SUCCESS, heldStack);
    }
}
