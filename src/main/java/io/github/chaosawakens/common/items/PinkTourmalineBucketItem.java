package io.github.chaosawakens.common.items;

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
import net.minecraftforge.fml.RegistryObject;

public class PinkTourmalineBucketItem extends BucketItem {
	private final java.util.function.Supplier<? extends Fluid> fluidSupplier;
	private final Map<ResourceLocation, RegistryObject<BucketItem>> fluidMap;
	
	public PinkTourmalineBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
		super(supplier, builder);
		this.fluidSupplier = supplier;
		this.fluidMap = ImmutableMap.of(Fluids.WATER.getRegistryName(), CAItems.WATER_PINK_TOURMALINE_BUCKET,
				Fluids.FLOWING_WATER.getRegistryName(), CAItems.WATER_PINK_TOURMALINE_BUCKET,
				Fluids.LAVA.getRegistryName(), CAItems.LAVA_PINK_TOURMALINE_BUCKET,
				Fluids.FLOWING_LAVA.getRegistryName(), CAItems.LAVA_PINK_TOURMALINE_BUCKET);
	}

	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		RayTraceResult raytraceresult = getPlayerPOVHitResult(pLevel, pPlayer,
				this.fluidSupplier.get() == Fluids.EMPTY ? RayTraceContext.FluidMode.SOURCE_ONLY : RayTraceContext.FluidMode.NONE);
		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(pPlayer, pLevel, itemstack,
				raytraceresult);
		if (ret != null)return ret;
		if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
			return ActionResult.pass(itemstack);
		} else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
			return ActionResult.pass(itemstack);
		} else {
			BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
			BlockPos blockpos = blockraytraceresult.getBlockPos();
			Direction direction = blockraytraceresult.getDirection();
			BlockPos blockpos1 = blockpos.relative(direction);
			if (pLevel.mayInteract(pPlayer, blockpos) && pPlayer.mayUseItemAt(blockpos1, direction, itemstack)) {
				if (this.fluidSupplier.get() == Fluids.EMPTY) {
					BlockState blockstate1 = pLevel.getBlockState(blockpos);
					if (blockstate1.getBlock() instanceof IBucketPickupHandler) {
						Fluid fluid = ((IBucketPickupHandler) blockstate1.getBlock()).takeLiquid(pLevel, blockpos,
								blockstate1);
						if (fluid != Fluids.EMPTY) {
							pPlayer.awardStat(Stats.ITEM_USED.get(this));

							SoundEvent soundevent = this.fluidSupplier.get().getAttributes().getFillSound();
							if (soundevent == null)
								soundevent = fluid.is(FluidTags.LAVA) ? SoundEvents.BUCKET_FILL_LAVA
										: SoundEvents.BUCKET_FILL;
							pPlayer.playSound(soundevent, 1.0F, 1.0F);
							ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, pPlayer,
									new ItemStack(fluidMap.get(fluid.getRegistryName()).get()));
							if (!pLevel.isClientSide) {
								CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) pPlayer,
										new ItemStack(fluidMap.get(fluid.getRegistryName()).get()));
							}

							return ActionResult.sidedSuccess(itemstack1, pLevel.isClientSide());
						}
					}

					return ActionResult.fail(itemstack);
				} else {
					BlockState blockstate = pLevel.getBlockState(blockpos);
					BlockPos blockpos2 = canBlockContainFluid(pLevel, blockpos, blockstate) ? blockpos : blockpos1;
					if (this.emptyBucket(pPlayer, pLevel, blockpos2, blockraytraceresult)) {
						this.checkExtraContent(pLevel, itemstack, blockpos2);
						if (pPlayer instanceof ServerPlayerEntity) {
							CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) pPlayer, blockpos2, itemstack);
						}

						pPlayer.awardStat(Stats.ITEM_USED.get(this));
						return ActionResult.sidedSuccess(this.getEmptySuccessItem(itemstack, pPlayer),
								pLevel.isClientSide());
					} else {
						return ActionResult.fail(itemstack);
					}
				}
			} else {
				return ActionResult.fail(itemstack);
			}
		}
	}
	
	@Override
	protected ItemStack getEmptySuccessItem(ItemStack pStack, PlayerEntity pPlayer) {
		return !pPlayer.abilities.instabuild ? new ItemStack(CAItems.PINK_TOURMALINE_BUCKET.get()) : pStack;
	}
	
	private boolean canBlockContainFluid(World worldIn, BlockPos posIn, BlockState blockstate) {
		return blockstate.getBlock() instanceof ILiquidContainer && ((ILiquidContainer)blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, this.fluidSupplier.get());
	}
}
