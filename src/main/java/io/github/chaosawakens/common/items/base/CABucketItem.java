package io.github.chaosawakens.common.items.base;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.RegistryObject;

public abstract class CABucketItem extends BucketItem {
	private final Supplier<? extends Fluid> targetFluid;

	public CABucketItem(Supplier<? extends Fluid> targetFluidSup, Properties builder) {
		super(targetFluidSup, builder);
		this.targetFluid = targetFluidSup;
	}
	
	public abstract RegistryObject<? extends CABucketItem> getWaterBucketItem();
	public abstract RegistryObject<? extends CABucketItem> getLavaBucketItem();

	protected Map<ResourceLocation, RegistryObject<? extends CABucketItem>> getFluidMap() {
		return ImmutableMap.of(Fluids.WATER.getRegistryName(), getWaterBucketItem(),
				Fluids.FLOWING_WATER.getRegistryName(), getWaterBucketItem(),
				Fluids.LAVA.getRegistryName(), getLavaBucketItem(),
				Fluids.FLOWING_LAVA.getRegistryName(), getLavaBucketItem());
	}

	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		ItemStack heldItem = pPlayer.getItemInHand(pHand);
		RayTraceResult playerHitResult = getPlayerPOVHitResult(pLevel, pPlayer, this.targetFluid.get() == Fluids.EMPTY ? RayTraceContext.FluidMode.SOURCE_ONLY : RayTraceContext.FluidMode.NONE);
		ActionResult<ItemStack> bucketUseActionResult = ForgeEventFactory.onBucketUse(pPlayer, pLevel, heldItem, playerHitResult);

		if (bucketUseActionResult != null) return bucketUseActionResult;
		if (playerHitResult.getType() == RayTraceResult.Type.MISS) return ActionResult.pass(heldItem);
		else if (playerHitResult.getType() != RayTraceResult.Type.BLOCK) return ActionResult.pass(heldItem);
		else {
			BlockRayTraceResult playerBlockHitResult = (BlockRayTraceResult) playerHitResult;
			BlockPos targetPos = playerBlockHitResult.getBlockPos();
			Direction targetDirection = playerBlockHitResult.getDirection();
			BlockPos relPos = targetPos.relative(targetDirection);

			if (pLevel.mayInteract(pPlayer, targetPos) && pPlayer.mayUseItemAt(relPos, targetDirection, heldItem)) {
				if (this.targetFluid.get() == Fluids.EMPTY) {
					BlockState targetState = pLevel.getBlockState(targetPos);

					if (targetState.getBlock() instanceof IBucketPickupHandler) {
						Fluid targetFluid = ((IBucketPickupHandler) targetState.getBlock()).takeLiquid(pLevel, targetPos, targetState);

						if (targetFluid != Fluids.EMPTY) {
							pPlayer.awardStat(Stats.ITEM_USED.get(this));

							SoundEvent bucketFillSound = this.targetFluid.get().getAttributes().getFillSound();
							if (bucketFillSound == null) bucketFillSound = targetFluid.is(FluidTags.LAVA) ? SoundEvents.BUCKET_FILL_LAVA : SoundEvents.BUCKET_FILL; //TODO Custom fluid thing maybe

							pPlayer.playSound(bucketFillSound, 1.0F, 1.0F);
							ItemStack filledBucketStack = DrinkHelper.createFilledResult(heldItem, pPlayer, new ItemStack(getFluidMap().get(targetFluid.getRegistryName()).get()));
							if (!pLevel.isClientSide) CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) pPlayer, new ItemStack(getFluidMap().get(targetFluid.getRegistryName()).get()));

							return ActionResult.sidedSuccess(filledBucketStack, pLevel.isClientSide());
						}
					}

					return ActionResult.fail(heldItem);
				} else {
					BlockState targetState = pLevel.getBlockState(targetPos);
					BlockPos finalTargetPos = canBlockContainFluid(pLevel, targetPos, targetState) ? targetPos : relPos;

					if (emptyBucket(pPlayer, pLevel, finalTargetPos, playerBlockHitResult)) {
						checkExtraContent(pLevel, heldItem, finalTargetPos);
						
						if (pPlayer instanceof ServerPlayerEntity) CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) pPlayer, finalTargetPos, heldItem);

						pPlayer.awardStat(Stats.ITEM_USED.get(this));
						return ActionResult.sidedSuccess(getEmptySuccessItem(heldItem, pPlayer), pLevel.isClientSide());
					} else return ActionResult.fail(heldItem);
				}
			} else {
				return ActionResult.fail(heldItem);
			}
		}
	}

	@Override
	protected ItemStack getEmptySuccessItem(ItemStack pStack, PlayerEntity pPlayer) {
		return !pPlayer.abilities.instabuild ? new ItemStack(CAItems.PINK_TOURMALINE_BUCKET.get()) : pStack;
	}

	private boolean canBlockContainFluid(World worldIn, BlockPos posIn, BlockState blockstate) {
		return blockstate.getBlock() instanceof ILiquidContainer && ((ILiquidContainer) blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, this.targetFluid.get());
	}
}