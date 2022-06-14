package io.github.chaosawakens.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.util.EnumUtils;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Lazy;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class ExtendedHitAxeItem extends AxeItem implements IVanishable, IAnimatable, IUtilityHelper {
	public static final UUID REACH_MODIFIER = UUID.fromString("467B83E8-53C8-4989-9684-A9CFF01E3626");//467B83E8-53C8-4989-9684-A9CFF01E3626
	public static final UUID KB_MODIFIER = UUID.fromString("125404C8-B689-40CE-861E-BF86069C844B");
	public static int damage;
	public static int attackSpeed;
	public static double reach;
	public static double kb;
	public static Lazy<? extends Multimap<Attribute, AttributeModifier>> LAZY = Lazy.of(() -> {
		Multimap<Attribute, AttributeModifier> map;
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)ExtendedHitAxeItem.damage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)ExtendedHitAxeItem.attackSpeed, AttributeModifier.Operation.ADDITION));
		if (ForgeMod.REACH_DISTANCE.isPresent()) {
			builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(REACH_MODIFIER, "Weapon modifier", ExtendedHitAxeItem.reach, AttributeModifier.Operation.ADDITION));
		}
		builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(KB_MODIFIER, "Weapon modifier", ExtendedHitAxeItem.kb, AttributeModifier.Operation.ADDITION));
		map = builder.build();
		return map;
	});

	public ExtendedHitAxeItem(EnumUtils.CAItemTier tier, int damage, float attackSpeed, double reach, double knockBack, Properties prop) {
		super(tier, damage, attackSpeed, prop);
		ExtendedHitAxeItem.damage = (int) ((float)damage + tier.getAttackDamageBonus());
		ExtendedHitAxeItem.attackSpeed = (int) attackSpeed;
		ExtendedHitAxeItem.reach = reach;
		ExtendedHitAxeItem.kb = knockBack;
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		return slot == EquipmentSlotType.MAINHAND ? ExtendedHitAxeItem.LAZY.get() : super.getAttributeModifiers(slot, stack);
	}
	
	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		double reach = entity.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
		double reachSqr = reach * reach;
		
		Vector3d viewVec = entity.getViewVector(1.0F);
		Vector3d eyeVec = entity.getEyePosition(1.0F);
		Vector3d targetVec = eyeVec.add(viewVec.x * reach, viewVec.y * reach, viewVec.z * reach);
		
		AxisAlignedBB bb = entity.getBoundingBox().expandTowards(viewVec.scale(reach)).inflate(4.0D, 4.0D, 4.0D);
		EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(entity, eyeVec, targetVec, bb, EntityPredicates.NO_CREATIVE_OR_SPECTATOR, reachSqr);
		
		if (result == null) return false;
		
		Entity target = result.getEntity();
		
		double distanceToTargetSqr = entity.distanceToSqr(target);
		
		boolean resultBool = (result != null ? target : null) != null;
				
		if (resultBool) {
			if (entity instanceof PlayerEntity) {
				if (reachSqr >= distanceToTargetSqr) {		
					target.hurt(DamageSource.playerAttack((PlayerEntity) entity), ExtendedHitAxeItem.damage);		
				}
			}
		}
		return super.onEntitySwing(stack, entity);
	}

	@Override
	public void registerControllers(AnimationData data) {
		// insert controllers here
	}

	@Override
	public AnimationFactory getFactory() {
		return null;
	}
}
