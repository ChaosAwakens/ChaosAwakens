package io.github.chaosawakens.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class AntInfestedOre extends OreBlock {
	private final Supplier<? extends EntityType<?>> ant;

	public AntInfestedOre(Supplier<? extends EntityType<?>> ant, Properties properties) {
		super(properties);
		this.ant = ant;
	}

	private void spawnAnt(World world, BlockPos pos) {
		MonsterEntity antEntity = (MonsterEntity) ant.get().create(world);
		assert antEntity != null;
		antEntity.moveTo((double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
		world.addFreshEntity(antEntity);
		antEntity.spawnAnim();
	}

	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState state2, boolean p_196243_5_) {
		ItemStack stack = this.getBlock().asItem().getDefaultInstance();
		if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)
				&& EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
			for (int index0 = 0; index0 < 20; index0++) {
				this.spawnAnt(world, pos);
			}
		}
	}

	public void wasExploded(World world, BlockPos pos, Explosion explosion) {
		if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
			for (int index0 = 0; index0 < 20; index0++) {
				this.spawnAnt(world, pos);
			}
		}
	}
}
