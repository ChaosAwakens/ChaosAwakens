package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UltimateFishingBobberEntity extends FishingBobberEntity {

    @OnlyIn(Dist.CLIENT)
    public UltimateFishingBobberEntity(World worldIn, PlayerEntity p_i47290_2_, double x, double y, double z) {
        super(worldIn, p_i47290_2_, x, y, z);
    }

    public UltimateFishingBobberEntity(PlayerEntity p_i50220_1_, World p_i50220_2_, int p_i50220_3_, int p_i50220_4_) {
        super(p_i50220_1_, p_i50220_2_, p_i50220_3_, p_i50220_4_);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        Entity entity = this.getOwner();
        return new SSpawnObjectPacket(this, entity == null ? this.getId() : entity.getId());
    }

    private boolean shouldStopFishing(PlayerEntity p_234600_1_) {
        ItemStack itemstack = p_234600_1_.getMainHandItem();
        ItemStack itemstack1 = p_234600_1_.getOffhandItem();
        boolean flag = itemstack.getItem() == CAItems.ULTIMATE_FISHING_ROD.get();
        boolean flag1 = itemstack1.getItem() == CAItems.ULTIMATE_FISHING_ROD.get();
        if (p_234600_1_.isAlive() && (flag || flag1) && !(this.distanceToSqr(p_234600_1_) > 1024.0D)) {
            return false;
        } else {
            this.remove();
            return true;
        }
    }

    private void checkCollision() {
        RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
        this.onHit(raytraceresult);
    }

    @Override
    public EntityType<?> getType() {
        return CAEntityTypes.ULTIMATE_FISHING_BOBBER.get();
    }
}