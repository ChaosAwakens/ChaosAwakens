package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CrystalAppleCowEntity extends AnimalEntity {

    public CrystalAppleCowEntity(EntityType<? extends CrystalAppleCowEntity> type, World worldIn) {
        super(type, worldIn);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 10);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COW_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.COW_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COW_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public ActionResultType mobInteract(PlayerEntity playerIn, Hand hand) {
        ItemStack itemstack = playerIn.getItemInHand(hand);
        if (itemstack.getItem() == Items.BUCKET && !this.isBaby()) {
            playerIn.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, playerIn, Items.MILK_BUCKET.getDefaultInstance());
            playerIn.setItemInHand(hand, itemstack1);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(playerIn, hand);
        }
    }

    public CrystalAppleCowEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
        return CAEntityTypes.CRYSTAL_APPLE_COW.get().create(world);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.95F : 1.3F;
    }
}
