package io.github.chaosawakens.mixins;

import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.registry.CATags;
import io.github.chaosawakens.common.util.EnumUtils.SurvivalSpawnerManipulationType;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(SpawnEggItem.class)
public abstract class SpawnEggItemMixin {
    @Shadow
    public abstract EntityType<?> getType(CompoundNBT compoundNBT);

    @Inject(method = "useOn(Lnet/minecraft/item/ItemUseContext;)Lnet/minecraft/util/ActionResultType;", at = @At("HEAD"), cancellable = true)
    public void chaosawakens$useOn(ItemUseContext itemUseContext, CallbackInfoReturnable<ActionResultType> cir) {
        World world = itemUseContext.getLevel();
        if (!(world instanceof ServerWorld)) {
            cir.setReturnValue(ActionResultType.SUCCESS);
        } else {
            ItemStack itemstack = itemUseContext.getItemInHand();
            BlockPos blockpos = itemUseContext.getClickedPos();
            Direction direction = itemUseContext.getClickedFace();
            BlockState blockstate = world.getBlockState(blockpos);
            PlayerEntity player = itemUseContext.getPlayer();
            if (blockstate.is(Blocks.SPAWNER)) {
                if ((CACommonConfig.COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.NO_BLOCKING) ||
                        (CACommonConfig.COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.BLOCK_ALL && player.isCreative()) ||
                        (CACommonConfig.COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.BLOCK_CHAOS_AWAKENS && player.isCreative() && itemstack.getItem().getRegistryName().getNamespace().equals("chaosawakens")) ||
                        (CACommonConfig.COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.BLOCK_CHAOS_AWAKENS && !itemstack.getItem().getRegistryName().getNamespace().equals("chaosawakens")) ||
                        (CACommonConfig.COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.TAG_BLACKLISTED && !itemstack.getItem().is(CATags.Items.SPAWNER_SPAWN_EGGS)) ||
                        (CACommonConfig.COMMON.spawnEggsSpawnersSurvival.get() == SurvivalSpawnerManipulationType.TAG_WHITELISTED && itemstack.getItem().is(CATags.Items.SPAWNER_SPAWN_EGGS))) {
                    TileEntity tileentity = world.getBlockEntity(blockpos);
                    if (tileentity instanceof MobSpawnerTileEntity) {
                        AbstractSpawner abstractspawner = ((MobSpawnerTileEntity) tileentity).getSpawner();
                        EntityType<?> entitytype1 = getType(itemstack.getTag());
                        abstractspawner.setEntityId(entitytype1);
                        tileentity.setChanged();
                        world.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
                        itemstack.shrink(1);
                        cir.setReturnValue(ActionResultType.CONSUME);
                    }
                }
            }

			BlockPos blockpos1;
			if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
				blockpos1 = blockpos;
			} else {
				blockpos1 = blockpos.relative(direction);
			}

            EntityType<?> entitytype = getType(itemstack.getTag());
            if (!blockstate.is(Blocks.SPAWNER)) {
                if (entitytype.spawn((ServerWorld) world, itemstack, itemUseContext.getPlayer(), blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                    itemstack.shrink(1);
                }
            }

			cir.setReturnValue(ActionResultType.CONSUME);
		}
	}
}
