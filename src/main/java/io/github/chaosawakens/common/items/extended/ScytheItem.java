package io.github.chaosawakens.common.items.extended;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import io.github.chaosawakens.api.IUtilityHelper;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Lazy;

public class ScytheItem extends SwordItem implements IVanishable, IUtilityHelper {
	public static final UUID REACH_MODIFIER = UUID.fromString("467B83E8-53C8-4989-9684-A9CFF01E3626"); //467B83E8-53C8-4989-9684-A9CFF01E3626
	public static final UUID KB_MODIFIER = UUID.fromString("125404C8-B689-40CE-861E-BF86069C844B");
	public static int attackDamage;
	public static int attackSpeed;
	public static double attackReach;
	public static double attackKnockback;
	public static Lazy<? extends Multimap<Attribute, AttributeModifier>> LAZY = Lazy.of(() -> {
		Multimap<Attribute, AttributeModifier> map;
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
		if (ForgeMod.REACH_DISTANCE.isPresent()) {
			builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(REACH_MODIFIER, "Weapon modifier", attackReach, AttributeModifier.Operation.ADDITION));
		}
		builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(KB_MODIFIER, "Weapon modifier", attackKnockback, AttributeModifier.Operation.ADDITION));
		map = builder.build();
		return map;
	});

    public ScytheItem(IItemTier tierIn, int attackDamageIn, float attackSpeedIn, double attackReachIn, double attackKnockbackIn, Properties builderIn) {
        super(tierIn, attackDamageIn, attackSpeedIn, builderIn);
	    attackDamage = (int) ((float)attackDamageIn + tierIn.getAttackDamageBonus());
	    attackSpeed = (int) attackSpeedIn;
	    attackReach = attackReachIn;
	    attackKnockback = attackKnockbackIn;
    }

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		return slot == EquipmentSlotType.MAINHAND ? LAZY.get() : super.getAttributeModifiers(slot, stack);
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
//    	InputEvent.ClickInputEvent inputEvent = ForgeHooksClient.onClickInput(1, new KeyBinding("key.attack", InputMappings.Type.MOUSE, 0, "key.categories.gameplay"), Hand.MAIN_HAND);
//      if (entity instanceof PlayerEntity && inputEvent.isAttack()) {
          if (entity.getAttribute(ForgeMod.REACH_DISTANCE.get()) != null) {
          	double reach = entity.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
          	double reachSqr = reach * reach;
              World world = entity.level;

              Vector3d viewVec = entity.getViewVector(1.0F);
              Vector3d eyeVec = entity.getEyePosition(1.0F);
              Vector3d targetVec = eyeVec.add(viewVec.x * reach, viewVec.y * reach, viewVec.z * reach);

              AxisAlignedBB bb = entity.getBoundingBox().expandTowards(viewVec.scale(reach)).inflate(4.0D, 4.0D, 4.0D);
              EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(world, entity, eyeVec, targetVec, bb, EntityPredicates.NO_CREATIVE_OR_SPECTATOR);

              if (result == null || !(result.getEntity() instanceof LivingEntity) || result.getType() != RayTraceResult.Type.ENTITY) return false;

              LivingEntity target = (LivingEntity) result.getEntity();

              double distanceToTargetSqr = entity.distanceToSqr(target);

              boolean resultBool = (result != null ? target : null) != null && result.getType() == RayTraceResult.Type.ENTITY;

              if (resultBool) {
//                  if (entity instanceof PlayerEntity) {
                      if (reachSqr >= distanceToTargetSqr) {
                          target.hurt(DamageSource.playerAttack((PlayerEntity) entity), attackDamage);
                          this.hurtEnemy(stack, target, entity);
                      }
   //               }
              }
//          }
          }
      return super.onEntitySwing(stack, entity);
	}

	public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getDestroySpeed(worldIn, pos) != 0.0F) stack.hurtAndBreak(2, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		return true;
	}

	@Override
	public boolean isCorrectToolForDrops(BlockState blockIn) {
		return false;
	}
}
