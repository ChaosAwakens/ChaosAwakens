package io.github.chaosawakens.api;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.registry.CADimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec;

public interface ITeleporterMob {
	ITextComponent emptyInventoryMessage = new TranslationTextComponent("misc." + ChaosAwakens.MODID + ".empty_inventory");
	ITextComponent inaccessibleMessage = new TranslationTextComponent("misc." + ChaosAwakens.MODID + ".inaccessible_dimension");

	default ActionResultType mobInteract(PlayerEntity playerIn, Hand hand, World level, ForgeConfigSpec.ConfigValue<Boolean> tpConfig, RegistryKey<World> targetDimension) {
		ItemStack itemstack = playerIn.getItemInHand(hand);

		if (!tpConfig.get() || level.isClientSide || itemstack.getItem() != Items.AIR) {
			return ActionResultType.PASS;
		}

		boolean requireEmptyInventory = CACommonConfig.COMMON.crystalWorldRequiresEmptyInventory.get();
		boolean hasEmptyInventory = playerIn.inventory.isEmpty() && IUtilityHelper.areCuriosSlotsEmpty(playerIn);
		boolean isCreative = playerIn.isCreative();
		boolean canTeleport = targetDimension != null;

		if (requireEmptyInventory && level.dimension() != CADimensions.CRYSTAL_WORLD && targetDimension == CADimensions.CRYSTAL_WORLD) {
			if (!hasEmptyInventory && !isCreative) {
				playerIn.displayClientMessage(this.emptyInventoryMessage, true);
				return ActionResultType.PASS;
			}
		} else if (!canTeleport) {
			playerIn.displayClientMessage(this.inaccessibleMessage, true);
			return ActionResultType.PASS;
		}

		MinecraftServer minecraftServer = ((ServerWorld) level).getServer();
		ServerWorld targetWorld = minecraftServer.getLevel(level.dimension() == targetDimension ? World.OVERWORLD : targetDimension);
		ServerPlayerEntity serverPlayer = (ServerPlayerEntity) playerIn;

		while (targetWorld == null) {
			targetWorld = minecraftServer.getLevel(level.dimension() == targetDimension ? World.OVERWORLD : targetDimension);
		}

		serverPlayer.changeDimension(targetWorld, new HeightmapTeleporter());

		return ActionResultType.SUCCESS;
	}
}
