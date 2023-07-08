package io.github.chaosawakens.common.items.weapons.extended;

import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.CAAxeItem;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SlayerChainsawItem extends CAAxeItem implements IAnimatable, ISyncable {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<SlayerChainsawItem> mainController = new AnimationController<SlayerChainsawItem>(this, "slayerchainsawmaincontroller", 1, this::mainPredicate);
	private static final AnimationBuilder ACTIVATED_ANIM = new AnimationBuilder().addAnimation("Activated", EDefaultLoopTypes.LOOP);
	private static final int CHAINSAW_LENGTH = 5;
	private static final int CHAINSAW_WIDTH = 5;
	private static final int CHAINSAW_HEIGHT = 48;
	private static final String CONTROLLER_NAME = "popupcontroller";
	private static final int ANIM = 0;

	public SlayerChainsawItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties) {
		super(pTier, configDmg, -3F, 2.5D, pProperties);
		GeckoLibNetwork.registerSyncable(this);
	}
	
	public boolean isActivated(ItemStack chainsawStack) {
		return !chainsawStack.isEmpty() && chainsawStack.hasTag() && chainsawStack.getTag().contains("activated") && chainsawStack.getTag().getBoolean("activated");
	}
	
	public void setActivated(ItemStack chainsawStack, boolean activated) {
		if (!chainsawStack.hasTag() || chainsawStack.isEmpty()) return;
		
		if (!chainsawStack.getTag().contains("activated")) {
			CompoundNBT activationNBT = chainsawStack.getOrCreateTag();
			activationNBT.putBoolean("activated", activated);
		} else chainsawStack.getTag().putBoolean("activated", activated);
	}

	@Override
	public boolean hurtEnemy(ItemStack targetStack, LivingEntity target, LivingEntity attacker) {
		if (isActivated(targetStack)) {
			if (target.getEntity().getType().getRegistryName().getPath().endsWith("_ent") && !target.level.isClientSide) target.hurt(DamageSource.GENERIC, (getActualAttackDamage().get().get() * 2));
			targetStack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		}
		return super.hurtEnemy(targetStack, target, attacker);
	}

	@Override
	public boolean mineBlock(ItemStack targetStack, World curWorld, BlockState targetState, BlockPos targetPos, LivingEntity miningEntity) {
		if (isActivated(targetStack)) {
			if (targetState.is(BlockTags.LOGS)) {
				for (int i = 0; i < CHAINSAW_LENGTH; i++) {
					for (int j = 0; j < CHAINSAW_HEIGHT; j++) {
						for (int k = -CHAINSAW_WIDTH; k <= CHAINSAW_WIDTH; k++) {
							BlockPos offsetPos = targetPos.offset(i - CHAINSAW_LENGTH / 2, j - CHAINSAW_HEIGHT / 2, k - CHAINSAW_WIDTH / 2);
							BlockState targetOffsetState = curWorld.getBlockState(offsetPos);
							if (targetOffsetState.is(BlockTags.LOGS)) curWorld.destroyBlock(offsetPos, true);
						}
					}
				}
			}
			if (!curWorld.isClientSide) targetStack.hurtAndBreak(8, miningEntity, (owner) -> owner.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		}
		return super.mineBlock(targetStack, curWorld, targetState, targetPos, miningEntity);
	}

	private <I extends Item & IAnimatable> PlayState mainPredicate(AnimationEvent<I> event) {
		final ItemStack chainsawStack  = getDefaultInstance();
		
		if (isActivated(chainsawStack)) {
			event.getController().setAnimation(ACTIVATED_ANIM);
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		handleChainsawBehaviour(world, player, hand);
		return super.use(world, player, hand);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext ctx) {
		handleChainsawBehaviour(ctx.getLevel(), ctx.getPlayer(), ctx.getHand());
		return super.useOn(ctx);
	}

	@Override
	public ActionResultType onItemUseFirst(ItemStack targetStack, ItemUseContext ctx) {		
		handleChainsawBehaviour(ctx.getLevel(), ctx.getPlayer(), ctx.getHand());
		return super.onItemUseFirst(targetStack, ctx);
	}
	
	private void handleChainsawBehaviour(World targetWorld, PlayerEntity ownerPlayer, Hand curHand) {
		final ItemStack targetStack = ownerPlayer.getItemInHand(curHand);
		
		if (!targetWorld.isClientSide) {
			if (!isActivated(targetStack)) setActivated(targetStack, true);
			else setActivated(targetStack, false);
			
			if (isActivated(targetStack)) {
				final int id = GeckoLibUtil.guaranteeIDForStack(targetStack, (ServerWorld) targetWorld);
				final PacketTarget trackingTarget = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> ownerPlayer);
				GeckoLibNetwork.syncAnimation(trackingTarget, this, id, ANIM);
			}
		}
	}

	@Override
	public void onAnimationSync(int id, int state) {
		if (state == ANIM) {
			final AnimationController<?> targetController = GeckoLibUtil.getControllerForID(factory, id, CONTROLLER_NAME);
			assert targetController != null;
			
			if (targetController != null) {
				if (targetController.getAnimationState() == AnimationState.Stopped) {
					targetController.markNeedsReload();
					targetController.setAnimation(ACTIVATED_ANIM);
				}
			}
		}
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(mainController);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
