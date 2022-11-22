package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.HeightmapTeleporter;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.registry.CADimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AggressiveAntEntity extends MonsterEntity implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ITextComponent emptyInventoryMessage = new TranslationTextComponent("misc." + ChaosAwakens.MODID + ".empty_inventory");
	private final ConfigValue<Boolean> tpConfig;
	private final RegistryKey<World> targetDimension;

	public AggressiveAntEntity(EntityType<? extends MonsterEntity> type, World worldIn, ConfigValue<Boolean> tpConfig, RegistryKey<World> targetDimension) {
		super(type, worldIn);
		this.noCulling = true;
		this.tpConfig = tpConfig;
		this.targetDimension = targetDimension;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 2)
				.add(Attributes.ATTACK_SPEED, 1)
				.add(Attributes.MOVEMENT_SPEED, 0.15D)
				.add(Attributes.ATTACK_DAMAGE, 1)
				.add(Attributes.ATTACK_KNOCKBACK, 0.5D)
				.add(Attributes.FOLLOW_RANGE, 8);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ant.walking_animation", true));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ant.idle_animation", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0F, false));
		this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.6));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AntEntity.class, false));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(AggressiveAntEntity.class));
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity playerIn, Hand hand) {
		ItemStack itemstack = playerIn.getItemInHand(hand);

		if (tpConfig.get() && !this.level.isClientSide && itemstack.getItem() == Items.AIR) {
			if (CACommonConfig.COMMON.crystalWorldRequiresEmptyInventory.get()
					&& playerIn.level.dimension() != CADimensions.CRYSTAL_WORLD
					&& targetDimension == CADimensions.CRYSTAL_WORLD) {
				if (playerIn.inventory.isEmpty() || playerIn.isCreative()) {
					MinecraftServer minecraftServer = ((ServerWorld) this.level).getServer();
					ServerWorld targetWorld = minecraftServer.getLevel(this.level.dimension() == this.targetDimension ? World.OVERWORLD : this.targetDimension);
					ServerPlayerEntity serverPlayer = (ServerPlayerEntity) playerIn;
					if (targetWorld != null) serverPlayer.changeDimension(targetWorld, new HeightmapTeleporter());
				} else {
					playerIn.displayClientMessage(this.emptyInventoryMessage, true);
					return ActionResultType.PASS;
				}
			} else {
				MinecraftServer minecraftServer = ((ServerWorld) this.level).getServer();
				ServerWorld targetWorld = minecraftServer.getLevel(this.level.dimension() == this.targetDimension ? World.OVERWORLD : this.targetDimension);
				ServerPlayerEntity serverPlayer = (ServerPlayerEntity) playerIn;
				if (targetWorld != null) serverPlayer.changeDimension(targetWorld, new HeightmapTeleporter());
			}
		}
		return super.mobInteract(playerIn, hand);
	}

	protected void handleAirSupply() {
		if (this.isAlive()) {
			if (this.isInWaterRainOrBubble() || this.isInLava()) {
				this.setAirSupply(0);
				this.hurt(DamageSource.DROWN, Integer.MAX_VALUE);
			}
		}
	}

	public void baseTick() {
		super.baseTick();
		this.handleAirSupply();
	}

	@Override
	protected boolean shouldDropExperience() {
		return false;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "antcontroller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
