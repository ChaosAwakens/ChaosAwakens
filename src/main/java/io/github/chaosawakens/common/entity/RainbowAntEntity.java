package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CADimensions;
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
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class RainbowAntEntity extends AnimalEntity implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);

	public RainbowAntEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
		this.ignoreFrustumCheck = true;
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
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 1.0D)
				.createMutableAttribute(Attributes.ARMOR, 3).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 8);
	}

	public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
		ItemStack itemstack = playerIn.getHeldItem(hand);

		if (CAConfig.COMMON.enableRainbowAntTeleport.get()) {
			if (this.world instanceof ServerWorld && itemstack.getItem() == Items.AIR) {
				//int i = this.getMaxInPortalTime();
				ServerWorld currentWorld = (ServerWorld) this.world;
				MinecraftServer minecraftServer = currentWorld.getServer();
				RegistryKey<World> dimensionRegistryKey = this.world.getDimensionKey() == CADimensions.VILLAGE_MANIA ? World.OVERWORLD : CADimensions.VILLAGE_MANIA;
				ServerWorld targetWorld = minecraftServer.getWorld(dimensionRegistryKey);
				ServerPlayerEntity serverPlayer = (ServerPlayerEntity) playerIn;

				if (targetWorld != null) {
					serverPlayer.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.PERFORM_RESPAWN, 0));
					//ChaosAwakens.LOGGER.debug("before: "+targetWorld.getHeight(Heightmap.Type.WORLD_SURFACE, (int) playerIn.getPosX(), (int) playerIn.getPosZ()));

					/*
					 * Tp twice because the dimension hasn't yet loaded on the first one which results in HeightMap
					 * returning zero, thus the player spawns at bedrock
					 */
					serverPlayer.teleport(targetWorld, playerIn.getPosX(), targetWorld.getHeight(Heightmap.Type.WORLD_SURFACE, (int) playerIn.getPosX(), (int) playerIn.getPosZ()), playerIn.getPosZ(), serverPlayer.rotationYaw, serverPlayer.rotationPitch);
					serverPlayer.teleport(targetWorld, playerIn.getPosX(), targetWorld.getHeight(Heightmap.Type.WORLD_SURFACE, (int) playerIn.getPosX(), (int) playerIn.getPosZ()), playerIn.getPosZ(), serverPlayer.rotationYaw, serverPlayer.rotationPitch);

					//ChaosAwakens.LOGGER.debug("after: "+targetWorld.getHeight(Heightmap.Type.WORLD_SURFACE, (int) playerIn.getPosX(), (int) playerIn.getPosZ()));

					serverPlayer.connection.sendPacket(new SPlayerAbilitiesPacket(serverPlayer.abilities));

					for (EffectInstance effectinstance : (serverPlayer.getActivePotionEffects())) {
						serverPlayer.connection.sendPacket(new SPlayEntityEffectPacket(serverPlayer.getEntityId(), effectinstance));
					}
				}
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
