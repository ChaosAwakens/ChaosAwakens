package io.github.chaosawakens.mixins;

import java.util.Objects;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.common.registry.CATags;
import io.github.chaosawakens.common.util.EnumUtil.SurvivalSpawnerManipulationType;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;

@Mixin(SpawnEggItem.class)
public abstract class SpawnEggItemMixin {
	@Shadow
	public abstract EntityType<?> getType(CompoundNBT compoundNBT);

	@Inject(method = "useOn(Lnet/minecraft/item/ItemUseContext;)Lnet/minecraft/util/ActionResultType;", at = @At("HEAD"), cancellable = true)
	private void chaosawakens$useOn(ItemUseContext itemUseContext, CallbackInfoReturnable<ActionResultType> cir) {
		World world = itemUseContext.getLevel();

		if (!(world instanceof ServerWorld)) cir.setReturnValue(ActionResultType.SUCCESS);       
		else {
			ItemStack stackInHand = itemUseContext.getItemInHand();
			BlockPos clickedPos = itemUseContext.getClickedPos();
			Direction clickedDir = itemUseContext.getClickedFace();
			BlockState targetState = world.getBlockState(clickedPos);
			PlayerEntity player = itemUseContext.getPlayer();

			if (targetState.is(Blocks.SPAWNER)) {
				if ((CAConfigManager.MAIN_COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.NO_BLOCKING) ||
						(CAConfigManager.MAIN_COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.BLOCK_ALL && player.isCreative()) ||
						(CAConfigManager.MAIN_COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.BLOCK_CHAOS_AWAKENS && player.isCreative() && stackInHand.getItem().getRegistryName().getNamespace().equals("chaosawakens")) ||
						(CAConfigManager.MAIN_COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.BLOCK_CHAOS_AWAKENS && !stackInHand.getItem().getRegistryName().getNamespace().equals("chaosawakens")) ||
						(CAConfigManager.MAIN_COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.TAG_BLACKLISTED && !stackInHand.getItem().is(CATags.Items.SPAWNER_SPAWN_EGGS)) ||
						(CAConfigManager.MAIN_COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.TAG_WHITELISTED && stackInHand.getItem().is(CATags.Items.SPAWNER_SPAWN_EGGS))) {
					TileEntity targetTileEntity = world.getBlockEntity(clickedPos);

					if (targetTileEntity instanceof MobSpawnerTileEntity) {
						AbstractSpawner mobSpawner = ((MobSpawnerTileEntity) targetTileEntity).getSpawner();
						EntityType<?> spawnerEntityType = getType(stackInHand.getTag());
						mobSpawner.setEntityId(spawnerEntityType);
						targetTileEntity.setChanged();
						world.sendBlockUpdated(clickedPos, targetState, targetState, 3);
						stackInHand.shrink(1);
						cir.setReturnValue(ActionResultType.CONSUME);
					}
				}
			}

			BlockPos relTargetPos;
			if (targetState.getCollisionShape(world, clickedPos).isEmpty()) {
				relTargetPos = clickedPos;
			} else {
				relTargetPos = clickedPos.relative(clickedDir);
			}

			EntityType<?> tagEntityType = getType(stackInHand.getTag());
			if (!targetState.is(Blocks.SPAWNER)) {
				if (tagEntityType.spawn((ServerWorld) world, stackInHand, itemUseContext.getPlayer(), relTargetPos, SpawnReason.SPAWN_EGG, true, !Objects.equals(clickedPos, relTargetPos) && clickedDir == Direction.UP) != null) {
					stackInHand.shrink(1);
				}
			}

			cir.setReturnValue(ActionResultType.CONSUME);
		}
	}
}
