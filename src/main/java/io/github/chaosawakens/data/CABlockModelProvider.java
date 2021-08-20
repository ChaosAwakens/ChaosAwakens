package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * @author invalid2
 *
 */
public class CABlockModelProvider extends BlockModelProvider {
	
	private final String cA = "chaosawakens";
	
	/**
	 * @param generator
	 * @param modid
	 * @param existingFileHelper
	 */
	public CABlockModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}
	
	@Override
	protected void registerModels() {
		// TODO Automate the data generators
		this.cubeColumn("apple_log", newRL(cA, "apple_log"), newRL(cA, "apple_log_top"));
		this.cubeColumnHorizontal("apple_log", newRL(cA, "apple_log"), newRL(cA, "apple_log_top"));
		this.cubeColumn("cherry_log", newRL(cA, "cherry_log"), newRL(cA, "cherry_log_top"));
		this.cubeColumnHorizontal("cherry_log", newRL(cA, "cherry_log"), newRL(cA, "cherry_log_top"));
		this.cubeColumn("peach_log", newRL(cA, "peach_log"), newRL(cA, "peach_log_top"));
		this.cubeColumnHorizontal("peach_log", newRL(cA, "peach_log"), newRL(cA, "peach_log_top"));
		this.cubeColumn("stripped_peach_log", newRL(cA, "stripped_peach_log"), newRL(cA, "stripped_peach_log_top"));
		this.cubeAll("peach_leaves", newRL(cA, "peach_leaves"));
		this.cubeAll("peach_planks", newRL(cA, "peach_planks"));
		this.cubeColumnHorizontal("stripped_peach_log", newRL(cA, "stripped_peach_log"), newRL(cA, "stripped_peach_log_top"));
		
	}
	
	private ResourceLocation newRL(String ns, String p) {
		return new ResourceLocation(ns, BLOCK_FOLDER+"/"+p);
	}
	
	public BlockModelBuilder cubeAll(String name, ResourceLocation texture) {
		return singleTexture(name, mcLoc(BLOCK_FOLDER), "all", texture);
	}
	
	public BlockModelBuilder cubeColumn(String name, ResourceLocation side, ResourceLocation end) {
		ChaosAwakens.debug("DATAGEN", side.getPath());
		return withExistingParent(name, BLOCK_FOLDER)
			.texture("side", side)
			.texture("end", end);
	}
	
	public BlockModelBuilder cubeColumnHorizontal(String name, ResourceLocation side, ResourceLocation end) {
		return withExistingParent(name, BLOCK_FOLDER)
				.texture("side", side)
				.texture("end", end);
	}
}