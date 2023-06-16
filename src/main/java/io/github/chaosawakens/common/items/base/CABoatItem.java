package io.github.chaosawakens.common.items.base;

import java.util.List;
import java.util.function.Predicate;

import io.github.chaosawakens.common.entity.base.CABoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class CABoatItem extends BoatItem {
	private static final Predicate<Entity> PICKABLE_ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);
	private final String boatType;

	public CABoatItem(String woodType, Properties properties) {
		super(null, properties);
		this.boatType = woodType;
	}

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack mainHandItem = player.getItemInHand(hand);
		RayTraceResult playerHitResult = getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.ANY);

		if (playerHitResult.getType() == RayTraceResult.Type.MISS) return ActionResult.pass(mainHandItem);
		else {
			Vector3d viewVec = player.getViewVector(1.0F);
			List<Entity> viableEntities = world.getEntities(player, player.getBoundingBox().expandTowards(viewVec.scale(5.0D)).inflate(1.0D), PICKABLE_ENTITY_PREDICATE);

			if (!viableEntities.isEmpty()) {
				Vector3d eyeVec = player.getEyePosition(1.0F);

				for (Entity target : viableEntities) {
					AxisAlignedBB targetBB = target.getBoundingBox().inflate((double)target.getPickRadius());

					if (targetBB.contains(eyeVec)) return ActionResult.pass(mainHandItem);
				}
			}

			if (playerHitResult.getType() == RayTraceResult.Type.BLOCK) {
				CABoatEntity boatEntity = new CABoatEntity(world, playerHitResult.getLocation().x, playerHitResult.getLocation().y, playerHitResult.getLocation().z);	         
				boatEntity.setBoatWoodType(boatType);
				boatEntity.yRot = player.yRot;

				if (!world.noCollision(boatEntity, boatEntity.getBoundingBox().inflate(-0.1D))) return ActionResult.fail(mainHandItem);
				else {
					if (!world.isClientSide) {
						world.addFreshEntity(boatEntity);

						if (!player.abilities.instabuild) mainHandItem.shrink(1);
					}
					player.awardStat(Stats.ITEM_USED.get(this));
					return ActionResult.sidedSuccess(mainHandItem, world.isClientSide());
				}
			} else return ActionResult.pass(mainHandItem);
		}
	}
}
