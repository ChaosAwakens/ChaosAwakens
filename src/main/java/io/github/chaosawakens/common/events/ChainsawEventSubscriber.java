package io.github.chaosawakens.common.events;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.ArrayList;
import java.util.List;

//Tried registering it in main class :/
@EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Bus.FORGE)
public class ChainsawEventSubscriber {
	@SubscribeEvent
	public static void onBlockBreak(final BreakEvent event) {
		World world = (World) event.getWorld();
		if (world.isClientSide()) {
			return;
		}
		PlayerEntity player = event.getPlayer();
		BlockPos pos = event.getPos();
		ItemStack heldItem = player.getMainHandItem();
		if (canHarvest(pos, player, world, heldItem)) {
			destroyTree(player, world, pos, heldItem);
		}
	}

	@SubscribeEvent
	public static void onBreakSpeed(final BreakSpeed event) {
		PlayerEntity p = event.getPlayer();
		if (p.isCreative()) {
			return;
		}

		PlayerEntity player = event.getPlayer();
		BlockPos pos = event.getPos();
		if (pos == null) {
			return;
		}
		if (canHarvest(pos, player, player.level, player.getMainHandItem())) {
			List<BlockPos> connectedLogs = getConnectedLogs(player.level, pos);
			event.setNewSpeed((float) (event.getOriginalSpeed() / Math.min(1D + 2 * connectedLogs.size(), 2)));
		}
	}

	public static boolean canHarvest(BlockPos pos, PlayerEntity player, World world, ItemStack heldItem) {
		if (player.abilities.instabuild) {
			return false;
		}

		if(player.isCreative()) {
			return false;
		}

		ItemStack chainsaw = new ItemStack(CAItems.SLAYER_CHAINSAW.get());
		if (!player.getItemBySlot(EquipmentSlotType.MAINHAND).equals(chainsaw)) {
			return false;
		}

		if (player.isShiftKeyDown()) {
			return false;
		}

		if (!isLog(world, pos)) {
			return false;
		}

		if (isGround(world, pos.below())) {
			return false;
		}

		BlockState state = world.getBlockState(pos);
		if (state.getProperties().stream().anyMatch(p -> p.equals(RotatedPillarBlock.AXIS))) {
			return state.getValue(RotatedPillarBlock.AXIS).equals(Direction.Axis.Y);
		}
		return true;
	}

	private static void destroyTree(PlayerEntity player, World world, BlockPos pos, ItemStack heldItem) {
		List<BlockPos> connectedLogs = getConnectedLogs(world, pos);

		for (BlockPos logPos : connectedLogs) {
			destroy(world, player, logPos, heldItem);
		}
	}

	private static List<BlockPos> getConnectedLogs(World world, BlockPos pos) {
		BlockPosList positions = new BlockPosList();
		collectLogs(world, pos, positions);
		return positions;
	}

	private static void collectLogs(World world, BlockPos pos, BlockPosList positions) {
		if (positions.size() >= 128) {
			return;
		}
		List<BlockPos> posList = new ArrayList<>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					BlockPos p = pos.offset(x, y, z);
					if (isLog(world, p)) {
						if (positions.size() <= 128) {
							if (positions.add(p)) {
								posList.add(p);
							}
						} else {
							return;
						}
					}
				}
			}
		}

		for (BlockPos p : posList) {
			collectLogs(world, p, positions);
		}
	}

	private static boolean isLog(World world, BlockPos pos) {
		world.getBlockState(pos);
		Block block = world.getBlockState(pos).getBlock();
		return block.is(BlockTags.LOGS);
	}

	private static boolean isGround(World world, BlockPos pos) {
		world.getBlockState(pos);
		Block block = world.getBlockState(pos).getBlock();
		return block.is(BlockTags.BASE_STONE_OVERWORLD);
	}

	private static void destroy(World world, PlayerEntity player, BlockPos pos, ItemStack heldItem) {
		if (heldItem != null) {
			ItemStack chainsaw = new ItemStack(CAItems.SLAYER_CHAINSAW.get());
			if(heldItem.equals(chainsaw)) {
				heldItem.getItem().mineBlock(heldItem, world, world.getBlockState(pos), pos, player);
				world.destroyBlock(pos, true);
				player.causeFoodExhaustion(0.025F);
			}
		}
	}

	private static class BlockPosList extends ArrayList<BlockPos> {
		@Override
		public boolean add(BlockPos pos) {
			if (!contains(pos)) {
				return super.add(pos);
			}
			return false;
		}

		@Override
		public boolean contains(Object o) {
			return stream().anyMatch(pos1 -> pos1.equals(o));
		}
	}
}
