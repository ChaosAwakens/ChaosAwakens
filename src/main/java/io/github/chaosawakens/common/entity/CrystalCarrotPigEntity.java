package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.registry.CABlocks;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class CrystalCarrotPigEntity extends CarrotPigEntity {
    private final AnimationFactory factory = new AnimationFactory(this);
    private final AnimationController<?> controller = new AnimationController<>(this, "crystalcarrotpigcontroller", animationInterval(), this::predicate);
    private static final Ingredient FOOD_ITEMS = Ingredient.of(CAItems.CRYSTAL_POTATO.get(), CAItems.CRYSTAL_BEETROOT.get());

    public CrystalCarrotPigEntity(EntityType<? extends CrystalCarrotPigEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 12);
    }

    public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.carrot_pig.walking_animation", true));
            return PlayState.CONTINUE;
        }
        if (!event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.carrot_pig.idle_animation", true));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(CAItems.CRYSTAL_BEETROOT_ON_A_STICK.get()), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, false, FOOD_ITEMS));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    public static boolean checkCrystalCarrotPigSpawnRules(EntityType<? extends AnimalEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return world.getBlockState(blockPos.below()).is(CABlocks.CRYSTAL_GRASS_BLOCK.get()) && world.getRawBrightness(blockPos, 0) > 8;
    }
    
    @Override
    public boolean canBeControlledByRider() {   
    	Entity entity = this.getControllingPassenger();       
    	if (!(entity instanceof PlayerEntity)) return false;
    	else {
    		PlayerEntity playerentity = (PlayerEntity)entity;
    		return playerentity.getMainHandItem().getItem() == CAItems.CRYSTAL_BEETROOT_ON_A_STICK.get() || playerentity.getOffhandItem().getItem() == CAItems.CRYSTAL_BEETROOT_ON_A_STICK.get();    
    	}   
    }
    
    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
       return new Vector3d(0.0D, (0.6F * this.getEyeHeight()), (this.getBbWidth() * 0.4F));
    }

    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PIG_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public CarrotPigEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
        return CAEntityTypes.CRYSTAL_CARROT_PIG.get().create(world);
    }

    public boolean readyForShearing() {
        return this.isAlive() && !this.isBaby();
    }
    
    @Override
    public boolean boost() {
    	return super.boost();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
    
    @Override
    public EntityType<?> getType() {
    	return CAEntityTypes.CRYSTAL_CARROT_PIG.get();
    }
    
    @Override
    public int tickTimer() {
    	return tickCount;
    }
    
    @Override
    public AnimationController<?> getController() {
    	return controller;
    }
}
