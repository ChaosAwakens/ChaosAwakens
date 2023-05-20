package io.github.chaosawakens.common.items.base;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;

public class StructureItem extends Item {
	protected String structureName;

	public StructureItem(Properties builderIn, String structureName) {
		super(builderIn);
		this.structureName = structureName;
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		World world = context.getLevel();
		BlockPos clickedPos = context.getClickedPos();
		PlayerEntity player = context.getPlayer();

		if (world instanceof ServerWorld) {
			Template structureTemplate = ((ServerWorld) world).getStructureManager().getOrCreate(ChaosAwakens.prefix(structureName));
			PlacementHelper targetPlacement = new PlacementHelper(clickedPos, structureTemplate.getSize(), context.getHorizontalDirection());
			if (structureTemplate != null) {
				structureTemplate.placeInWorldChunk((ServerWorld) world, targetPlacement.getTargetPos(), new PlacementSettings().setRotation(targetPlacement.getRotation()).setMirror(Mirror.NONE).setChunkPos(null).setIgnoreEntities(false), (world).random);
				player.awardStat(Stats.ITEM_USED.get(this));
				context.getItemInHand().shrink(1);
			}
		}
		return ActionResultType.SUCCESS;
	}

	/**
	 * Does the necessary value manipulation to figure out where to put the
	 * structure
	 *
	 * @author invalid2
	 */
	public static class PlacementHelper {
		private final BlockPos targetPos;
		private final Rotation targetRotation;

		public PlacementHelper(BlockPos pos, BlockPos templateSize, Direction direction) {
			super();
			switch (direction) {
			case NORTH:
				this.targetPos = new BlockPos(pos.getX() + templateSize.getX() / 2, pos.getY(), pos.getZ() + templateSize.getZ() / 2);
				this.targetRotation = Rotation.CLOCKWISE_180;
				break;
			case SOUTH:
				this.targetPos = new BlockPos(pos.getX() - templateSize.getX() / 2, pos.getY(), pos.getZ() - templateSize.getZ() / 2);
				this.targetRotation = Rotation.CLOCKWISE_180.getRotated(Rotation.CLOCKWISE_180);
				break;
			case WEST:
				this.targetPos = new BlockPos(pos.getX() + templateSize.getX() / 2, pos.getY(), pos.getZ() - templateSize.getZ() / 2);
				this.targetRotation = Rotation.CLOCKWISE_90;
				break;
			case EAST:
				this.targetPos = new BlockPos(pos.getX() - templateSize.getX() / 2, pos.getY(), pos.getZ() + templateSize.getZ() / 2);
				this.targetRotation = Rotation.COUNTERCLOCKWISE_90;
				break;
			default:
				this.targetPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
				this.targetRotation = Rotation.NONE;
			}
		}

		public BlockPos getTargetPos() {
			return targetPos;
		}

		public Rotation getRotation() {
			return targetRotation;
		}
	}
}
