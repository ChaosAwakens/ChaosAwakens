package io.github.chaosawakens.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;

public class StructureItem extends Item {
	String structureName;

	public StructureItem(Properties builderIn, String structureName) {
		super(builderIn);
		this.structureName = structureName;
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		PlayerEntity player = context.getPlayer();

		if (world instanceof ServerWorld) {
			Template template = ((ServerWorld) world).getStructureManager().getOrCreate(new ResourceLocation("chaosawakens", structureName));
			PlacementHelper targetPlacement = new PlacementHelper(pos, template.getSize(), context.getHorizontalDirection());
			if (template != null) {
				template.placeInWorldChunk((ServerWorld) world, targetPlacement.getPos(), new PlacementSettings().setRotation(targetPlacement.getRotation()).setMirror(Mirror.NONE).setChunkPos(null).setIgnoreEntities(false), (world).random);
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
	static class PlacementHelper {
		private final BlockPos pos;
		private final Rotation rotation;

		public PlacementHelper(BlockPos pos, BlockPos templateSize, Direction direction) {
			super();
			switch (direction) {
			case NORTH:
				this.pos = new BlockPos(pos.getX() + templateSize.getX() / 2, pos.getY(), pos.getZ() + templateSize.getZ() / 2);
				this.rotation = Rotation.CLOCKWISE_180;
				break;
			case SOUTH:
				this.pos = new BlockPos(pos.getX() - templateSize.getX() / 2, pos.getY(), pos.getZ() - templateSize.getZ() / 2);
				this.rotation = Rotation.CLOCKWISE_180.getRotated(Rotation.CLOCKWISE_180);
				break;
			case WEST:
				this.pos = new BlockPos(pos.getX() + templateSize.getX() / 2, pos.getY(), pos.getZ() - templateSize.getZ() / 2);
				this.rotation = Rotation.CLOCKWISE_90;
				break;
			case EAST:
				this.pos = new BlockPos(pos.getX() - templateSize.getX() / 2, pos.getY(), pos.getZ() + templateSize.getZ() / 2);
				this.rotation = Rotation.COUNTERCLOCKWISE_90;
				break;
			default:
				this.pos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
				this.rotation = Rotation.NONE;
			}
		}

		public BlockPos getPos() {
			return pos;
		}

		public Rotation getRotation() {
			return rotation;
		}
	}
}
