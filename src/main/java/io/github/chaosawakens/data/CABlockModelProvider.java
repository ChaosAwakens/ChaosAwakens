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
		this.cubeColumn("duplication_log", newRL(cA, "duplication_log"), newRL(cA, "duplication_log_top"));
		this.cubeColumnHorizontal("duplication_log", newRL(cA, "duplication_log"), newRL(cA, "duplication_log_top"));
		this.cubeColumn("peach_log", newRL(cA, "peach_log"), newRL(cA, "peach_log_top"));
		this.cubeColumnHorizontal("peach_log", newRL(cA, "peach_log"), newRL(cA, "peach_log_top"));
		this.cubeColumn("stripped_peach_log", newRL(cA, "stripped_peach_log"), newRL(cA, "stripped_peach_log_top"));
		this.cubeColumnHorizontal("stripped_peach_log", newRL(cA, "stripped_peach_log"), newRL(cA, "stripped_peach_log_top"));	
		this.cubeColumn("stripped_duplication_log", newRL(cA, "stripped_duplication_log"), newRL(cA, "stripped_duplication_log_top"));
		this.cubeColumnHorizontal("stripped_duplication_log", newRL(cA, "stripped_duplication_log"), newRL(cA, "stripped_duplication_log_top"));
		this.cubeColumn("dead_duplication_log", newRL(cA, "dead_duplication_log"), newRL(cA, "dead_duplication_log_top"));
		this.cubeColumnHorizontal("dead_duplication_log", newRL(cA, "dead_duplication_log"), newRL(cA, "dead_duplication_log_top"));
		this.cubeColumn("stripped_apple_log", newRL(cA, "stripped_apple_log"), newRL(cA, "stripped_apple_log_top"));
		this.cubeColumnHorizontal("stripped_apple_log", newRL(cA, "stripped_apple_log"), newRL(cA, "stripped_apple_log_top"));
		this.cubeColumn("stripped_cherry_log", newRL(cA, "stripped_cherry_log"), newRL(cA, "stripped_cherry_log_top"));
		this.cubeColumnHorizontal("stripped_cherry_log", newRL(cA, "stripped_cherry_log"), newRL(cA, "stripped_cherry_log_top"));
		this.cubeAll("peach_planks", newRL(cA, "peach_planks"));
		this.cubeAll("duplication_leaves", newRL(cA, "duplication_leaves"));
		this.cubeAll("apple_planks", newRL(cA, "apple_planks"));
		this.cubeAll("cherry_planks", newRL(cA, "cherry_planks"));
		this.cubeAll("cherry_leaves", newRL(cA, "cherry_leaves"));
		this.cubeAll("duplication_planks", newRL(cA, "duplication_planks"));
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