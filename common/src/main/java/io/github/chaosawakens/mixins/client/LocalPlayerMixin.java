package io.github.chaosawakens.mixins.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.authlib.GameProfile;
import io.github.chaosawakens.content.item.equipment.armor.LavaEelArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {

    @Shadow
    protected int sprintTriggerTime;

    @Final
    @Shadow
    protected Minecraft minecraft;

    @Shadow
    public Input input;

    @Shadow
    private boolean crouching;

    @Shadow
    private int autoJumpTime;

    @Final
    @Shadow
    public ClientPacketListener connection;

    @Shadow
    private boolean wasFallFlying;

    @Shadow
    private int waterVisionTime;

    @Shadow
    private int jumpRidingTicks;

    @Shadow
    private float jumpRidingScale;

    @Shadow
    private void handleNetherPortalClient() {}

    @Shadow
    private boolean hasEnoughImpulseToStartSprinting() {
        return false;
    }

    @Shadow
    public boolean isMovingSlowly() {
        return false;
    }

    @Shadow
    private void moveTowardsClosestSpace(double x, double z) {}

    @Shadow
    private boolean canStartSprinting() {
        return false;
    }

    @Shadow
    private boolean hasEnoughFoodToStartSprinting() {
        return false;
    }

    @Shadow
    protected boolean isControlledCamera() {
        return false;
    }

    @Shadow
    public PlayerRideableJumping jumpableVehicle() {
        return null;
    }

    @Shadow
    public float getJumpRidingScale() {
        return this.jumpRidingScale;
    }

    @Shadow
    protected void sendRidingJump() {}

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    // Mixin couldn't target the specific expressions, so it needs to be like this
    @WrapMethod(method = "aiStep")
    public void chaosawakens$aiStep(Operation<Void> original) {
        boolean isFullLavaEelSet = true;
        for (ItemStack stack : this.getArmorSlots()) {
            isFullLavaEelSet = isFullLavaEelSet && stack.getItem() instanceof LavaEelArmorItem;
        }

        if (isFullLavaEelSet) {
            if (this.sprintTriggerTime > 0) {
                --this.sprintTriggerTime;
            }

            if (!(this.minecraft.screen instanceof ReceivingLevelScreen)) {
                this.handleNetherPortalClient();
            }

            boolean flag = this.input.jumping;
            boolean flag1 = this.input.shiftKeyDown;
            boolean flag2 = this.hasEnoughImpulseToStartSprinting();
            this.crouching = !this.getAbilities().flying && !this.isSwimming() && this.canEnterPose(Pose.CROUCHING) && (this.isShiftKeyDown() || !this.isSleeping() && !this.canEnterPose(Pose.STANDING));
            float f = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(this), 0.0F, 1.0F);
            this.input.tick(this.isMovingSlowly(), f);
            this.minecraft.getTutorial().onInput(this.input);
            if (this.isUsingItem() && !this.isPassenger()) {
                Input var10000 = this.input;
                var10000.leftImpulse *= 0.2F;
                var10000 = this.input;
                var10000.forwardImpulse *= 0.2F;
                this.sprintTriggerTime = 0;
            }

            boolean flag3 = false;
            if (this.autoJumpTime > 0) {
                --this.autoJumpTime;
                flag3 = true;
                this.input.jumping = true;
            }

            if (!this.noPhysics) {
                this.moveTowardsClosestSpace(this.getX() - (double)this.getBbWidth() * 0.35, this.getZ() + (double)this.getBbWidth() * 0.35);
                this.moveTowardsClosestSpace(this.getX() - (double)this.getBbWidth() * 0.35, this.getZ() - (double)this.getBbWidth() * 0.35);
                this.moveTowardsClosestSpace(this.getX() + (double)this.getBbWidth() * 0.35, this.getZ() - (double)this.getBbWidth() * 0.35);
                this.moveTowardsClosestSpace(this.getX() + (double)this.getBbWidth() * 0.35, this.getZ() + (double)this.getBbWidth() * 0.35);
            }

            if (flag1) {
                this.sprintTriggerTime = 0;
            }

            boolean flag4 = this.canStartSprinting();
            boolean flag5 = this.isPassenger() ? this.getVehicle().onGround() : this.onGround();
            boolean flag6 = !flag1 && !flag2;
            if ((flag5 || this.isUnderWater()) && flag6 && flag4) {
                if (this.sprintTriggerTime <= 0 && !this.minecraft.options.keySprint.isDown()) {
                    this.sprintTriggerTime = 7;
                } else {
                    this.setSprinting(true);
                }
            }

            if ((!this.isInWater() || this.isUnderWater() || !this.isInLava() || this.isEyeInFluid(FluidTags.LAVA)) && flag4 && this.minecraft.options.keySprint.isDown()) {
                this.setSprinting(true);
            }

            if (this.isSprinting()) {
                boolean flag7 = !this.input.hasForwardImpulse() || !this.hasEnoughFoodToStartSprinting();
                boolean flag8 = flag7 || this.horizontalCollision && !this.minorHorizontalCollision || this.isInWater() && !this.isUnderWater() || this.isInLava() && !this.isEyeInFluid(FluidTags.LAVA);
                if (this.isSwimming()) {
                    if (!this.onGround() && !this.input.shiftKeyDown && flag7 || !this.isInWater() && !this.isInLava()) {
                        this.setSprinting(false);
                    }
                } else if (flag8) {
                    this.setSprinting(false);
                }
            }

            boolean flag9 = false;
            if (this.getAbilities().mayfly) {
                if (this.minecraft.gameMode.isAlwaysFlying()) {
                    if (!this.getAbilities().flying) {
                        this.getAbilities().flying = true;
                        flag9 = true;
                        this.onUpdateAbilities();
                    }
                } else if (!flag && this.input.jumping && !flag3) {
                    if (this.jumpTriggerTime == 0) {
                        this.jumpTriggerTime = 7;
                    } else if (!this.isSwimming()) {
                        this.getAbilities().flying = !this.getAbilities().flying;
                        flag9 = true;
                        this.onUpdateAbilities();
                        this.jumpTriggerTime = 0;
                    }
                }
            }

            if (this.input.jumping && !flag9 && !flag && !this.getAbilities().flying && !this.isPassenger() && !this.onClimbable()) {
                ItemStack itemstack = this.getItemBySlot(EquipmentSlot.CHEST);
                if (itemstack.is(Items.ELYTRA) && ElytraItem.isFlyEnabled(itemstack) && this.tryToStartFallFlying()) {
                    this.connection.send(new ServerboundPlayerCommandPacket(this, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
                }
            }

            this.wasFallFlying = this.isFallFlying();
            if (this.isInWater() && this.input.shiftKeyDown && this.isAffectedByFluids()) {
                this.goDownInWater();
            }

            if (this.isEyeInFluid(FluidTags.WATER)) {
                int i = this.isSpectator() ? 10 : 1;
                this.waterVisionTime = Mth.clamp(this.waterVisionTime + i, 0, 600);
            } else if (this.waterVisionTime > 0) {
                this.isEyeInFluid(FluidTags.WATER);
                this.waterVisionTime = Mth.clamp(this.waterVisionTime - 10, 0, 600);
            }

            if (this.getAbilities().flying && this.isControlledCamera()) {
                int j = 0;
                if (this.input.shiftKeyDown) {
                    --j;
                }

                if (this.input.jumping) {
                    ++j;
                }

                if (j != 0) {
                    this.setDeltaMovement(this.getDeltaMovement().add((double)0.0F, (double)((float)j * this.getAbilities().getFlyingSpeed() * 3.0F), (double)0.0F));
                }
            }

            PlayerRideableJumping playerrideablejumping = this.jumpableVehicle();
            if (playerrideablejumping != null && playerrideablejumping.getJumpCooldown() == 0) {
                if (this.jumpRidingTicks < 0) {
                    ++this.jumpRidingTicks;
                    if (this.jumpRidingTicks == 0) {
                        this.jumpRidingScale = 0.0F;
                    }
                }

                if (flag && !this.input.jumping) {
                    this.jumpRidingTicks = -10;
                    playerrideablejumping.onPlayerJump(Mth.floor(this.getJumpRidingScale() * 100.0F));
                    this.sendRidingJump();
                } else if (!flag && this.input.jumping) {
                    this.jumpRidingTicks = 0;
                    this.jumpRidingScale = 0.0F;
                } else if (flag) {
                    ++this.jumpRidingTicks;
                    if (this.jumpRidingTicks < 10) {
                        this.jumpRidingScale = (float)this.jumpRidingTicks * 0.1F;
                    } else {
                        this.jumpRidingScale = 0.8F + 2.0F / (float)(this.jumpRidingTicks - 9) * 0.1F;
                    }
                }
            } else {
                this.jumpRidingScale = 0.0F;
            }

            super.aiStep();
            if (this.onGround() && this.getAbilities().flying && !this.minecraft.gameMode.isAlwaysFlying()) {
                this.getAbilities().flying = false;
                this.onUpdateAbilities();
            }
        } else {
            original.call();
        }
    }
}
