package io.github.chaosawakens.common.blocks.ore;

import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class CAEntityTrapOreBlock extends CAOreBlock {
	private final Supplier<? extends EntityType<?>> entityToSummon;
	private int amountToSummon;

	public CAEntityTrapOreBlock(Supplier<EntityType<?>> entityToSummon, Properties properties) {
		super(properties);
		this.entityToSummon = entityToSummon;
		this.amountToSummon = MathHelper.nextInt(RANDOM, 10, 20);
	}

	protected void summonEntityAt(BlockPos targetPos, World world, boolean shouldSpawnByAmount) {
		Entity targetSummonableEntity;

		if (!world.isClientSide) {
			if (shouldSpawnByAmount) {
				for (int amount = 0; amount < amountToSummon; amount++) {
					targetSummonableEntity = entityToSummon.get().create(world);
					assert targetSummonableEntity != null;
					targetSummonableEntity.absMoveTo(targetPos.getX() + 0.5D, targetPos.getY(), targetPos.getZ() + 0.5D);
					world.addFreshEntity(targetSummonableEntity);
				}
			} else {
				targetSummonableEntity = entityToSummon.get().create(world);
				assert targetSummonableEntity != null;
				targetSummonableEntity.absMoveTo(targetPos.getX() + 0.5D, targetPos.getY(), targetPos.getZ() + 0.5D);
				world.addFreshEntity(targetSummonableEntity);
			}
		}
	}

	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean moving) {
		ItemStack blockStack = getBlock().asItem().getDefaultInstance();
		
		if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, blockStack) == 0) {
			summonEntityAt(pos, world, true);
		}
	}

	@Override
	public void wasExploded(World world, BlockPos explodedPos, Explosion explosion) {
		if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
			summonEntityAt(explodedPos, world, true);
		}
	}
	
	public CAEntityTrapOreBlock withMobLimit(int limit) {
		this.amountToSummon = limit;
		
		return this;
	}

}
