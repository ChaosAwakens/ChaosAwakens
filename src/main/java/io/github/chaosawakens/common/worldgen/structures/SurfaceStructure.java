package io.github.chaosawakens.common.worldgen.structures;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class SurfaceStructure extends Structure<NoFeatureConfig> {
	protected final String path;
	private final BlockPos offset;
	
	public SurfaceStructure(Codec<NoFeatureConfig> codec, String path, BlockPos offset) {
		super(codec);
		this.path = path;
		this.offset = offset;
	}
	
	public SurfaceStructure(Codec<NoFeatureConfig> codec, String path) {
		this(codec, path, new BlockPos(0, 0, 0).immutable());
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return SurfaceStructure.Start::new;
	}

	@Override
	public GenerationStage.Decoration step() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGen, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
		BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);

		int landHeight = chunkGen.getBaseHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE);

		IBlockReader column = chunkGen.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
		BlockState topBlock = column.getBlockState(centerOfChunk.above(landHeight));

		return topBlock.getFluidState().isEmpty();
	}

	public class Start extends StructureStart<NoFeatureConfig> {

		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		@Override
		public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
			int x = (chunkX << 4) + 7;
			int z = (chunkZ << 4) + 7;
			boolean netherFlag = biomeIn.getBiomeCategory() == Category.NETHER;
			BlockPos blockpos = new BlockPos(x, 0, z);
			JigsawManager.addPieces(dynamicRegistryManager,
					new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(ChaosAwakens.prefix(path)), 10),
					AbstractVillagePiece::new, chunkGenerator, templateManagerIn, netherFlag ? SurfaceStructure.getGround(chunkGenerator, chunkGenerator.getBaseColumn((chunkX << 4) + 7, (chunkZ << 4) + 7), chunkX, chunkZ) : blockpos, pieces, random, false, !netherFlag);
			
			this.pieces.forEach(piece -> piece.move(offset.getX(), offset.getY(), offset.getZ()));
			this.calculateBoundingBox();
		}
	}

	protected static BlockPos getGround(ChunkGenerator chunkGen, IBlockReader column, int chunkX, int chunkZ) {
		BlockPos.Mutable mutable = new BlockPos.Mutable((chunkX << 4) + 7, 124, (chunkZ << 4) + 7);
		BlockState currState;
		while (mutable.getY() > chunkGen.getSeaLevel()) {
			currState = column.getBlockState(mutable);

			if (!currState.canOcclude()) {
				mutable.move(Direction.DOWN);
				continue;
			} else if (column.getBlockState(mutable.offset(0, 3, 0)).getMaterial() == Material.AIR && currState.canOcclude()) {
				break;
			}
			mutable.move(Direction.DOWN);
		}
		return mutable;
	}
}
