package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class UltimateFishingBobberEntity extends FishingBobberEntity {

	@OnlyIn(Dist.CLIENT)
	public UltimateFishingBobberEntity(World worldIn, PlayerEntity p_i47290_2_, double x, double y, double z) {
		super(worldIn, p_i47290_2_, x, y, z);
	}

	public UltimateFishingBobberEntity(PlayerEntity p_i50220_1_, World p_i50220_2_, int p_i50220_3_, int p_i50220_4_) {
		super(p_i50220_1_, p_i50220_2_, p_i50220_3_, p_i50220_4_);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public EntityType<?> getType() {
		return CAEntityTypes.ULTIMATE_FISHING_BOBBER.get();
	}
}