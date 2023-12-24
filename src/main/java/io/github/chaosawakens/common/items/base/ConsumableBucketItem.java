package io.github.chaosawakens.common.items.base;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class ConsumableBucketItem extends Item {
	private final Supplier<Item> emptyBucketItem;
	private final boolean curesHarmfulEffects;

	public ConsumableBucketItem(Supplier<Item> emptyBucketItem, Properties pProperties, boolean curesHarmfulEffects) {
		super(pProperties);
		this.emptyBucketItem = emptyBucketItem;
		this.curesHarmfulEffects = curesHarmfulEffects; //TODO Custom wrapper for initCapabilities
	}

	public Item getResultingEmptyBucketItem() {
		return emptyBucketItem.get();
	}
	
	public boolean curesHarmfulEffects() {//TODO Ceramic Buckets mod
		return curesHarmfulEffects;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack pStack, World pLevel, LivingEntity pEntityLiving) {
		if (!pLevel.isClientSide && curesHarmfulEffects) pEntityLiving.curePotionEffects(Items.MILK_BUCKET.getDefaultInstance());
		
		if (pEntityLiving instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) pEntityLiving;
			CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, pStack);
			serverPlayer.awardStat(Stats.ITEM_USED.get(this));
		}

		if (pEntityLiving instanceof PlayerEntity && !((PlayerEntity) pEntityLiving).abilities.instabuild) pStack.shrink(1);

		return pStack.isEmpty() ? new ItemStack(emptyBucketItem.get()) : pStack;
	}

	@Override
	public int getUseDuration(ItemStack pStack) {
		return 32;
	}

	@Override
	public UseAction getUseAnimation(ItemStack pStack) {
		return UseAction.DRINK;
	}

	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		return DrinkHelper.useDrink(pLevel, pPlayer, pHand);
	}
}