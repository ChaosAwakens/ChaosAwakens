package io.github.chaosawakens.common.items;

import java.util.Set;
import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import io.github.chaosawakens.common.config.CACommonConfig;

import com.google.common.collect.ImmutableMultimap.Builder;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Lazy;

public abstract class CAToolItem extends ToolItem{
	   public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("7EEED399-42DF-49D0-A16B-56D01D30BFF3");
	   protected float speed;
	   private float attackDamageBaseline;
	   private double reach;
	   public Lazy<? extends Multimap<Attribute, AttributeModifier>> LAZY = Lazy.of(() -> {		
		   Multimap<Attribute, AttributeModifier> modMap;		
		   Builder<Attribute, AttributeModifier> modBuilder = ImmutableMultimap.builder();		
		   modBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamageBaseline, AttributeModifier.Operation.ADDITION));		
		   modBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)speed, AttributeModifier.Operation.ADDITION));			
		   if (ForgeMod.REACH_DISTANCE.isPresent()) {			
			   modBuilder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", (double)reach, AttributeModifier.Operation.ADDITION));			
		   }		
		   modMap = modBuilder.build();		
		   return modMap;		
	   });
	   
	   public CAToolItem(String nameNoModid, float attackDamage, float attackSpeed, double reachMultiplier, ConfigValue<Integer> v, IItemTier tier, Set<Block> blocks, Properties prop) {
	      super(attackDamage, attackSpeed, tier, blocks, prop);
	      this.speed = attackSpeed - 2.4F;
	      this.reach = reachMultiplier;
	  //    this.attackDamageBaseline = attackDamage + getConfig().get(v, nameNoModid) - 1;
	   }

	   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slotType) {
	      return slotType == EquipmentSlotType.MAINHAND ? LAZY.get() : super.getDefaultAttributeModifiers(slotType);
	   }
	   
	   public float getAttackDamage() {
	      return this.attackDamageBaseline;
	   }
	   
	   public abstract CACommonConfig getConfig();
	   
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
						target.hurt(DamageSource.playerAttack((PlayerEntity) entity), attackDamageBaseline);		
					}
				}
			}
			return super.onEntitySwing(stack, entity);	
		}
}
