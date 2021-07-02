package io.github.chaosawakens.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class RayGun extends Item {
    private final ToolMaterial toolMaterial;

    public RayGun(ToolMaterial toolMaterial, Settings settings) {
        super(settings);
        this.toolMaterial = toolMaterial;
    }

    public ToolMaterial getToolMaterial() {
        return this.toolMaterial;
    }

    public boolean isRepairable(ItemStack stack, ItemStack ingredient) {
        return this.toolMaterial.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        super.use(world, user, hand);
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient()) return new TypedActionResult<>(ActionResult.PASS, stack);

        float xA = -MathHelper.sin(user.headYaw * ((float) Math.PI / 180F)) * MathHelper.cos(user.getPitch() * ((float) Math.PI / 180F));
        float yA = -MathHelper.sin(user.getPitch() * ((float) Math.PI / 180F));
        float zA = MathHelper.cos(user.headYaw * ((float) Math.PI / 180F)) * MathHelper.cos(user.getPitch() * ((float) Math.PI / 180F));

        /*
        RayGunProjectileEntity projectile = new RayGunProjectileEntity(world, user, xA, yA, zA);
        projectile.setPosition(user.getX(), user.getY()+1.5, user.getZ());
        projectile.setDirectionAndMovement(projectile, user.getPitch(), user.headYaw, 0, 1F, 0);
        */

        if (!user.isCreative()) {
            stack.damage(1, user, (entity) -> entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }

        //world.addEntity(projectile);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (user.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);

        user.incrementStat(Stats.USED.getOrCreateStat(this));

        return new TypedActionResult<>(ActionResult.SUCCESS, stack);
    }
}
