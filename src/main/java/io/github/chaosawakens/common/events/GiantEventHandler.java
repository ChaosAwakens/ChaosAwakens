package io.github.chaosawakens.common.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.GiantEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.Objects;

@EventBusSubscriber
public class GiantEventHandler {

	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event) {
		World world = event.getWorld();
		Entity entity = event.getEntity();

		if (world.isClientSide || !(entity instanceof GiantEntity))return;

		GiantEntity giant = (GiantEntity) entity;

		giant.goalSelector.addGoal(8, new LookAtGoal(giant, PlayerEntity.class, 24.0F));
		giant.goalSelector.addGoal(8, new LookRandomlyGoal(giant));

		giant.goalSelector.addGoal(2, new MeleeAttackGoal(giant, 1.0F, false));
		giant.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(giant, 1.0F));
		giant.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(giant, PlayerEntity.class, true));
		giant.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(giant, AbstractVillagerEntity.class, false));
		giant.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(giant, IronGolemEntity.class, true));

		Objects.requireNonNull(giant.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(100); // FOLLOW_RANGE
		Objects.requireNonNull(giant.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.15F); // MOVEMENT_SPEED
		Objects.requireNonNull(giant.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(20.0D); // ATTACK_DAMAGE
		Objects.requireNonNull(giant.getAttribute(Attributes.ARMOR)).setBaseValue(10.0D); // ARMOR
	}
}