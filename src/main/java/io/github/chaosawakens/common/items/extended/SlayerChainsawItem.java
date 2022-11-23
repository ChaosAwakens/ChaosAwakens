package io.github.chaosawakens.common.items.extended;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.util.EnumUtils.CAItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.network.PacketDistributor;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SlayerChainsawItem extends AxeItem implements IVanishable, IAnimatable, ISyncable {
	private static final int CHAINSAW_LENGTH = 5;
	private static final int CHAINSAW_WIDTH = 5;
	private static final int CHAINSAW_HEIGHT = 48;
	private static final String CONTROLLER_NAME = "popupController";
	private static final int ANIM = 0;
	private static boolean ACTIVATED = false;
	public static final UUID REACH_MODIFIER = UUID.fromString("AF69D588-EC5D-11EC-8EA0-0242AC120002");
	public static final UUID KB_MODIFIER = UUID.fromString("BB29C4D2-EC5D-11EC-8EA0-0242AC120002");
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
	public AnimationFactory factory = new AnimationFactory(this);

    public SlayerChainsawItem(CAItemTier tierIn, int attackDamageIn, float attackSpeedIn, double attackReachIn, int attackKnockbackIn, Properties builderIn) {
	    super(tierIn, attackDamage, attackSpeedIn, builderIn);
	    attackDamage = (int) ((float)attackDamageIn + tierIn.getAttackDamageBonus());
	    attackSpeed = (int) attackSpeedIn;
	    attackReach = attackReachIn;
	    attackKnockback = attackKnockbackIn;
        GeckoLibNetwork.registerSyncable(this);
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

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (ACTIVATED) {
			if (target.getEntity().getType() == CAEntityTypes.ACACIA_ENT.get() || target.getEntity().getType() == CAEntityTypes.BIRCH_ENT.get()
					|| target.getEntity().getType() == CAEntityTypes.CRIMSON_ENT.get() || target.getEntity().getType() == CAEntityTypes.DARK_OAK_ENT.get()
					|| target.getEntity().getType() == CAEntityTypes.JUNGLE_ENT.get() || target.getEntity().getType() == CAEntityTypes.OAK_ENT.get()
					|| target.getEntity().getType() == CAEntityTypes.SPRUCE_ENT.get() || target.getEntity().getType() == CAEntityTypes.WARPED_ENT.get()
					&& !target.level.isClientSide) {
				target.hurt(DamageSource.GENERIC, (attackDamage * 2));
			}
			stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	@Override
	public boolean mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity) {
		if (ACTIVATED) {
			if (state.is(BlockTags.LOGS)) {
				for (int i = 0; i < CHAINSAW_LENGTH; i++) {
					for (int j = 0; j < CHAINSAW_HEIGHT; j++) {
						for (int k = -CHAINSAW_WIDTH; k <= CHAINSAW_WIDTH; k++) {
							BlockPos targetPos = pos.offset(i - CHAINSAW_LENGTH / 2, j - CHAINSAW_HEIGHT / 2, k - CHAINSAW_WIDTH / 2);
							BlockState targetState = world.getBlockState(targetPos);
							if (targetState.is(BlockTags.LOGS)) world.destroyBlock(targetPos, true);
						}
					}
				}
			}	   
			if (!world.isClientSide) {					
				stack.hurtAndBreak(8, entity, (owner) -> {	          				
					owner.broadcastBreakEvent(EquipmentSlotType.MAINHAND);         		
				});
			}
		}
		return super.mineBlock(stack, world, state, pos, entity);
	}

	private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {		
		if (ACTIVATED) {				
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.slayer_chainsaw.use_animation", true));				
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClientSide) {			
			if (!ACTIVATED) {
				ACTIVATED = true;
				if (ACTIVATED) {
					// Gets the item that the player is holding, should be a JackInTheBoxItem
					final ItemStack stack = player.getItemInHand(hand);
					final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) world);
					// Tell all nearby clients to trigger this JackInTheBoxItem
					final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player);
					GeckoLibNetwork.syncAnimation(target, this, id, ANIM);
				}
			} else {
				ACTIVATED = false;
			}
		}
		return super.use(world, player, hand);
	}
	
	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {		
		use(context.getLevel(), context.getPlayer(), context.getHand());
		return ActionResultType.SUCCESS;
	}

	@Override
	public void onAnimationSync(int id, int state) {
		if (state == ANIM) {
			// Always use GeckoLibUtil to get AnimationControllers when you don't have
			// access to an AnimationEvent
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, CONTROLLER_NAME);
			assert controller != null;
			if (controller != null) {
				if (controller.getAnimationState() == AnimationState.Stopped) {
					// If you don't do this, the popup animation will only play once because the
					// animation will be cached.
					controller.markNeedsReload();
					// Set the animation to open the JackInTheBoxItem which will start playing music
					// and
					// eventually do the actual animation. Also sets it to loop
					controller.setAnimation(new AnimationBuilder().addAnimation("animation.slayer_chainsaw.use_animation", true));
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController(this, "slayerchainsawcontroller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
