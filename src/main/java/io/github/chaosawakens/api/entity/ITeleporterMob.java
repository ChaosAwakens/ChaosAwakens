package io.github.chaosawakens.api.entity;

import com.tiviacz.travelersbackpack.capability.CapabilityUtils;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.HeightmapTeleporter;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.fml.ModList;

public interface ITeleporterMob {
	TranslationTextComponent EMPTY_INVENTORY_MESSAGE = new TranslationTextComponent("misc." + ChaosAwakens.MODID + ".empty_inventory");
	TranslationTextComponent EMPTY_CURIOS_MESSAGE = new TranslationTextComponent("misc." + ChaosAwakens.MODID + ".empty_curios");
	TranslationTextComponent WEARING_BACKPACK_MESSAGE = new TranslationTextComponent("misc." + ChaosAwakens.MODID + ".wearing_backpack");
	TranslationTextComponent INACCESSIBLE_MESSAGE = new TranslationTextComponent("misc." + ChaosAwakens.MODID + ".inaccessible_dimension");

	default ActionResultType handleDimensionTeleporting(PlayerEntity playerIn, Hand hand, World level, BooleanValue tpConfig, RegistryKey<World> targetDimension) {
		ItemStack heldStack = playerIn.getItemInHand(hand);

		if (!tpConfig.get() || level.isClientSide || heldStack.getItem() != Items.AIR) return ActionResultType.PASS;

		boolean requireEmptyInventory = CAConfigManager.MAIN_COMMON.crystalWorldRequiresEmptyInventory.get();
		boolean isWearingTravelersBackpack = ModList.get().isLoaded("travelersbackpack") ? !CapabilityUtils.isWearingBackpack(playerIn) : true;
		boolean hasEmptyInventory = playerIn.inventory.isEmpty() && EntityUtil.areCuriosSlotsEmpty(playerIn) && isWearingTravelersBackpack;
		boolean isCreative = playerIn.isCreative();
		boolean canTeleport = targetDimension != null;

		if (requireEmptyInventory && level.dimension() != CADimensions.CRYSTAL_WORLD && targetDimension == CADimensions.CRYSTAL_WORLD) {
			if (!hasEmptyInventory && !isCreative) {
				if (!playerIn.inventory.isEmpty()) {
					playerIn.displayClientMessage(EMPTY_INVENTORY_MESSAGE, true);
				} else if (!EntityUtil.areCuriosSlotsEmpty(playerIn)) {
					playerIn.displayClientMessage(EMPTY_CURIOS_MESSAGE, true);
				} else if (!isWearingTravelersBackpack) {
					playerIn.displayClientMessage(WEARING_BACKPACK_MESSAGE, true);
				}
				return ActionResultType.PASS;
			}
		} else if (!canTeleport) {
			playerIn.displayClientMessage(INACCESSIBLE_MESSAGE, true);
			return ActionResultType.PASS;
		}

		MinecraftServer minecraftServer = ((ServerWorld) level).getServer();
		ServerWorld targetWorld = minecraftServer.getLevel(level.dimension() == targetDimension ? World.OVERWORLD : targetDimension);
		ServerPlayerEntity serverPlayer = (ServerPlayerEntity) playerIn;

		while (targetWorld == null) targetWorld = minecraftServer.getLevel(level.dimension() == targetDimension ? World.OVERWORLD : targetDimension);

		serverPlayer.changeDimension(targetWorld, new HeightmapTeleporter());

		return ActionResultType.SUCCESS;
	}
}
