package io.github.chaosawakens.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class CAProjectileEntity extends Entity {
    private UUID owner;
    private int field_234610_c_;
    private boolean leftOwner;

    CAProjectileEntity(EntityType<RoboSniperLaserEntity> type, World world) {
        super(type, world);
    }

    public void setShooter(@Nullable Entity entityIn) {
        if (entityIn != null) {
            this.owner = entityIn.getUniqueID();
            this.field_234610_c_ = entityIn.getEntityId();
        }

    }

    @Nullable
    public Entity getShooter() {
        if (this.owner != null && this.world instanceof ServerWorld) {
            return ((ServerWorld)this.world).getEntityByUuid(this.owner);
        } else {
            return this.field_234610_c_ != 0 ? this.world.getEntityByID(this.field_234610_c_) : null;
        }
    }

    protected void writeAdditional(CompoundNBT compound) {
        if (this.owner != null) {
            compound.putUniqueId("Owner", this.owner);
        }

        if (this.leftOwner) {
            compound.putBoolean("LeftOwner", true);
        }

    }

    protected void readAdditional(CompoundNBT compound) {
        if (compound.hasUniqueId("Owner")) {
            this.owner = compound.getUniqueId("Owner");
        }

        this.leftOwner = compound.getBoolean("LeftOwner");
    }

    public void tick() {
        if (!this.leftOwner) {
            this.leftOwner = this.func_234615_h_();
        }

        super.tick();
    }

    private boolean func_234615_h_() {
        Entity entity = this.getShooter();
        if (entity != null) {
            for(Entity entity1 : this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (p_234613_0_) -> {
                return !p_234613_0_.isSpectator() && p_234613_0_.canBeCollidedWith();
            })) {
                if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy).scale((double)velocity);
        this.setMotion(vector3d);
        float f = MathHelper.sqrt(horizontalMag(vector3d));
        this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    public void setDirectionAndMovement(Entity projectile, float x, float y, float z, float velocity, float inaccuracy) {
        float f = -MathHelper.sin(y * ((float)Math.PI / 180F)) * MathHelper.cos(x * ((float)Math.PI / 180F));
        float f1 = -MathHelper.sin((x + z) * ((float)Math.PI / 180F));
        float f2 = MathHelper.cos(y * ((float)Math.PI / 180F)) * MathHelper.cos(x * ((float)Math.PI / 180F));
        this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        Vector3d vector3d = projectile.getMotion();
        this.setMotion(this.getMotion().add(vector3d.x, projectile.isOnGround() ? 0.0D : vector3d.y, vector3d.z));
    }

    protected void onImpact(RayTraceResult result) {
        RayTraceResult.Type raytraceresult$type = result.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            this.onEntityHit((EntityRayTraceResult)result);
        }
    }

    protected void onEntityHit(EntityRayTraceResult result) {
    }

    @OnlyIn(Dist.CLIENT)
    public void setVelocity(double x, double y, double z) {
        this.setMotion(x, y, z);
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (double)(180F / (float)Math.PI));
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (double)(180F / (float)Math.PI));
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
        }

    }

    protected boolean func_230298_a_(Entity entityIn) {
        if (!entityIn.isSpectator() && entityIn.isAlive() && entityIn.canBeCollidedWith()) {
            Entity entity = this.getShooter();
            return entity == null || this.leftOwner || !entity.isRidingSameEntity(entityIn);
        } else {
            return false;
        }
    }

    protected void updatePitchAndYaw() {
        Vector3d vector3d = this.getMotion();
        float f = MathHelper.sqrt(horizontalMag(vector3d));
        this.rotationPitch = func_234614_e_(this.prevRotationPitch, (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI)));
        this.rotationYaw = func_234614_e_(this.prevRotationYaw, (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI)));
    }

    protected static float func_234614_e_(float p_234614_0_, float p_234614_1_) {
        while(p_234614_1_ - p_234614_0_ < -180.0F) {
            p_234614_0_ -= 360.0F;
        }

        while(p_234614_1_ - p_234614_0_ >= 180.0F) {
            p_234614_0_ += 360.0F;
        }

        return MathHelper.lerp(0.2F, p_234614_0_, p_234614_1_);
    }
}