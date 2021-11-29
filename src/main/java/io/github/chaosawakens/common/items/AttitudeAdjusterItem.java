package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.stats.Stats;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class AttitudeAdjusterItem extends SwordItem {

    private static final float EXPLOSION_POWER = CAConfig.COMMON.attitudeAdjusterExplosionSize.get();

    public AttitudeAdjusterItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.level.isClientSide) {
            target.level.explode(null, target.position().x, target.position().y, target.position().z, EXPLOSION_POWER, false, Explosion.Mode.BREAK);
        }
        stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        return true;
    }
}
