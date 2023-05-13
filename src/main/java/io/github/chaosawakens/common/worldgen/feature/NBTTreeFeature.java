package io.github.chaosawakens.common.worldgen.feature;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.Template.BlockInfo;
import net.minecraft.world.gen.feature.template.Template.Palette;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class NBTTreeFeature extends Feature<NBTTreeFeatureConfig> {

	public NBTTreeFeature(Codec<NBTTreeFeatureConfig> pCodec) {
		super(pCodec);
	}

	@Override
	public boolean place(ISeedReader reader, ChunkGenerator gen, Random rand, BlockPos pos, NBTTreeFeatureConfig cfg) {
		BlockPos surfacePos = reader.getHeightmapPos(cfg.heightmap, pos).below();
		if(!reader.getBlockState(surfacePos).is(cfg.targetBlock.getBlock()))
			return false;
		TemplateManager manager = reader.getLevel().getServer().getStructureManager();
		Template trunk = manager.getOrCreate(this.getRandomItem(cfg.trunkPool, rand));
		Template foliage = manager.getOrCreate(this.getRandomItem(cfg.foliagePool, rand));
		if(surfacePos.getY() + trunk.getSize().getY() > reader.getMaxBuildHeight())
			return false;
		Rotation rot = Rotation.getRandom(rand);
		PlacementSettings settings = new PlacementSettings().setRotation(rot)
				.addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR);
		BlockPos origin = trunk.getZeroPositionWithTransform(surfacePos
				.west(trunk.getSize().getX() / 2).north(trunk.getSize().getZ() / 2), Mirror.NONE, rot);
		origin = placeTrunk(reader, trunk, settings, trunk.palettes, cfg.logProvider, cfg.baseState, origin);
		origin = foliage.getZeroPositionWithTransform(origin.below(foliage.getSize().getY() - 2)
				.west(foliage.getSize().getX() / 2).north(foliage.getSize().getZ() / 2), Mirror.NONE, rot);
		placeLeaves(reader, foliage, settings, foliage.palettes, cfg.leavesProvider, origin);
		return true;
	}

	private BlockPos placeTrunk(ISeedReader reader, Template trunk, PlacementSettings settings,
			List<Palette> palettes, BlockStateProvider provider, BlockState baseState, BlockPos origin) {
		BlockPos end = BlockPos.ZERO;
		List<Template.BlockInfo> infos = settings.getRandomPalette(palettes, origin).blocks();
		BlockState base = provider.getState(reader.getRandom(), origin);
		//TODO Understand what the second BlockPos parameter is for
		for(Template.BlockInfo info : processAxis(Template.processBlockInfos(reader, origin, BlockPos.ZERO, settings, infos, trunk), settings.getRotation())) {
			BlockState infoState = info.state;
			if(infoState.is(Blocks.BONE_BLOCK))
				end = info.pos;
			else if(infoState.is(Blocks.WHITE_CONCRETE)) {
				reader.setBlock(info.pos, baseState, 4);
				continue;
			}
			reader.setBlock(info.pos, base.setValue(RotatedPillarBlock.AXIS, infoState.getValue(RotatedPillarBlock.AXIS)), 4);
		}
		return end;
	}

	private void placeLeaves(ISeedReader reader, Template trunk, PlacementSettings settings,
			List<Palette> palettes, BlockStateProvider provider, BlockPos origin) {
		List<Template.BlockInfo> infos = settings.getRandomPalette(palettes, origin).blocks();
		BlockState base = provider.getState(reader.getRandom(), origin);
		//TODO Understand what the second BlockPos parameter is for
		for(Template.BlockInfo info : Template.processBlockInfos(reader, origin, BlockPos.ZERO, settings, infos, trunk)) {
			BlockState infoState = info.state;
			if(infoState.is(BlockTags.LEAVES) && canPlaceLeaves(reader, info.pos))
				reader.setBlock(info.pos, base.setValue(LeavesBlock.DISTANCE, infoState.getValue(LeavesBlock.DISTANCE)), 4);
		}
	}

	private List<BlockInfo> processAxis(List<Template.BlockInfo> infos, Rotation rotation) {
		switch (rotation) {
		case CLOCKWISE_90:
		case COUNTERCLOCKWISE_90:
			List<Template.BlockInfo> list = Lists.newArrayList();
			for(Template.BlockInfo info : infos) {
				BlockState state = info.state;
				if(state.hasProperty(RotatedPillarBlock.AXIS) && state.getValue(RotatedPillarBlock.AXIS).isHorizontal()) {
					state = state.setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS) == Axis.X ? Axis.Z : Axis.X);
				}
				list.add(new Template.BlockInfo(info.pos, state, info.nbt));
			}
			return list;
		default:
			return infos;
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private boolean canPlaceLeaves(ISeedReader reader, BlockPos pos) {
		return reader.getBlockState(pos).isAir(reader, pos) ||
				reader.isStateAtPosition(pos, state -> state.getMaterial() == Material.REPLACEABLE_PLANT);
	}

	private ResourceLocation getRandomItem(List<ResourceLocation> list, Random rand) {
		return list.get(rand.nextInt(list.size()));
	}
}
