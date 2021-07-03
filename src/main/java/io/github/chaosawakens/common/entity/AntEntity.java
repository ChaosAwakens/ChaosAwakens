package io.github.chaosawakens.common.entity;

import javax.annotation.Nullable;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.HeightmapTeleporter;
import io.github.chaosawakens.api.IAntEntity;
import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResultType;
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

public class AntEntity extends AnimalEntity implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ITextComponent inaccessibleMessage = new TranslationTextComponent("misc." + ChaosAwakens.MODID + ".inaccessible_dimension");
	
	private final ConfigValue<Boolean> tpConfig;
	private final RegistryKey<World> targetDimension;
	
	public AntEntity(EntityType<? extends AntEntity> type, World worldIn, ConfigValue<Boolean> tpConfig, RegistryKey<World> targetDimension) {
		super(type, worldIn);
		this.ignoreFrustumCheck = true;
		this.tpConfig = tpConfig;
		this.targetDimension = targetDimension;
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController()
					.setAnimation(new AnimationBuilder().addAnimation("animation.ant.walking_animation", true));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving()) {
			event.getController()
					.setAnimation(new AnimationBuilder().addAnimation("animation.ant.idle_animation", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.6));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.registerAttributes()
				.createMutableAttribute(Attributes.MAX_HEALTH, 1)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 8);
	}

	@Override
	public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
		ItemStack itemstack = playerIn.getHeldItem(hand);

		if (tpConfig.get() && !this.world.isRemote && itemstack.getItem() == Items.AIR) {
			if(targetDimension == null) {
				playerIn.sendStatusMessage(this.inaccessibleMessage ,true);
                return ActionResultType.PASS;
			} else {
				MinecraftServer minecraftServer = ((ServerWorld)this.world).getServer();
				ServerWorld targetWorld = minecraftServer.getWorld(this.world.getDimensionKey() == this.targetDimension ? World.OVERWORLD : this.targetDimension);
				ServerPlayerEntity serverPlayer = (ServerPlayerEntity) playerIn;
				if (targetWorld != null)
					serverPlayer.changeDimension(targetWorld, new HeightmapTeleporter());
			}
		}
		return super.getEntityInteractionResult(playerIn, hand);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<>(this, "antcontroller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Nullable
	@Override
	public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
		return null;
	}
}
