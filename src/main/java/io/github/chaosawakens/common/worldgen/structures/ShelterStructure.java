package io.github.chaosawakens.common.worldgen.structures;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
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

public class ShelterStructure extends Structure<NoFeatureConfig> {

	public ShelterStructure(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return ShelterStructure.Start::new;
	}

	@Override
	public GenerationStage.Decoration step() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
		BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);

		int landHeight = chunkGenerator.getBaseHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
		IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
		BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));

		return topBlock.getFluidState().isEmpty();
	}

	public static class Start extends StructureStart<NoFeatureConfig> {

		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		@Override
		public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
			int x = (chunkX << 4) + 7;
			int z = (chunkZ << 4) + 7;

			BlockPos blockpos = new BlockPos(x, 0, z);

			JigsawManager.addPieces(dynamicRegistryManager,
					new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(ChaosAwakens.MODID, "shelter/start_pool")), 10),
					AbstractVillagePiece::new, chunkGenerator, templateManagerIn, blockpos, pieces, random, false, true);

			this.calculateBoundingBox();
		}
	}
}
