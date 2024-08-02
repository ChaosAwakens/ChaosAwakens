package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class CABlockModelProvider extends BlockModelProvider {

    public CABlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CAConstants.MODID, existingFileHelper);
    }

    @NotNull
    @Override
    public String getName() {
        return CAConstants.MOD_NAME.concat(": Block Models");
    }

    @Override
    protected void registerModels() {

    }

    protected ResourceLocation chaosRL(String texture) {
        return CAConstants.prefix(BLOCK_FOLDER + "/" + texture);
    }

    protected ResourceLocation mcRL(String texture) {
        return new ResourceLocation(BLOCK_FOLDER + "/" + texture);
    }

    public void trapDoor(String name) {
        singleTexture(name + "_bottom", mcRL("template_orientable_trapdoor_bottom"), chaosRL(name));
        singleTexture(name + "_open", mcRL("template_orientable_trapdoor_open"), chaosRL(name));
        singleTexture(name + "_top", mcRL("template_orientable_trapdoor_top"), chaosRL(name));
    }

    public void leafCarpet(String name, ResourceLocation texture) {
        singleTexture(name, chaosRL("leaf_carpet"), "texture", texture);
    }

    public void leafCarpetInventory(String name, ResourceLocation texture) {
        singleTexture(name, chaosRL("leaf_carpet_inventory"), "texture", texture);
    }

    public BlockModelBuilder wired2SidedLeftBlock(String name, ResourceLocation targetTexture, ResourceLocation sideTexture) {
        return orientableWithBottom(name, sideTexture, targetTexture, chaosRL(targetTexture.getPath() + "_lb"), chaosRL(targetTexture.getPath() + "_lt"));
    }

    public BlockModelBuilder wired2SidedRightBlock(String name, ResourceLocation targetTexture, ResourceLocation sideTexture) {
        return orientableWithBottom(name, sideTexture, targetTexture, chaosRL(targetTexture.getPath() + "_rb"), chaosRL(targetTexture.getPath() + "_rt"));
    }

    public BlockModelBuilder wired3SidedLeftBlock(String name, ResourceLocation targetTexture, ResourceLocation sideTexture) {
        return orientableWithBottom(name, sideTexture, targetTexture, chaosRL(targetTexture.getPath() + "_bottom"), chaosRL(targetTexture.getPath() + "_top"))
                .texture("east", chaosRL(targetTexture.getPath() + "_right"))
                .texture("west", chaosRL(targetTexture.getPath() + "_left"));
    }

    public void gateBlock(String name, ResourceLocation top) {
        withExistingParent(name, BLOCK_FOLDER).texture("side", chaosRL(name)).texture("top", top).texture("bottom", top);
    }

    public void plant(String name, String texture) {
        cross(name, chaosRL(texture));
    }

    public void doublePlant(String name, String texture) {
        cross(name + "_top", chaosRL(texture + "_top"));
        cross(name + "_bottom", chaosRL(texture + "_bottom"));
    }

    public BlockModelBuilder farmland(String name, ResourceLocation dirt, ResourceLocation top) {
        return withExistingParent(name, BLOCK_FOLDER + "/template_farmland")
                .texture("dirt", dirt)
                .texture("top", top);
    }

    public BlockModelBuilder grassBlock(String name, ResourceLocation particle, ResourceLocation bottom, ResourceLocation top, ResourceLocation side, ResourceLocation overlay) {
        return withExistingParent(name, BLOCK_FOLDER + "/grass_block")
                .texture("particle", particle)
                .texture("bottom", bottom)
                .texture("top", top)
                .texture("side", side)
                .texture("overlay", overlay);
    }

    public void standardPaneBlock(String normalPaneName, ResourceLocation paneTexture, ResourceLocation edgeTexture) {
        panePost(normalPaneName + "_post", paneTexture, edgeTexture);
        paneNoSide(normalPaneName + "_noside", paneTexture);
        paneSide(normalPaneName + "_side", paneTexture, edgeTexture);
        paneNoSideAlt(normalPaneName + "_noside_alt", paneTexture);
        paneSideAlt(normalPaneName + "_side_alt", paneTexture, edgeTexture);
    }

    public void standardBarBlock(String normalBarName, ResourceLocation barTexture) {
        barCap(normalBarName + "_cap", barTexture);
        barCapAlt(normalBarName + "_cap_alt", barTexture);
        barPost(normalBarName + "_post", barTexture);
        barPostEnds(normalBarName + "_post_ends", barTexture);
        barSide(normalBarName + "_side", barTexture);
        barSideAlt(normalBarName + "_side_alt", barTexture);
    }

    public BlockModelBuilder barCap(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_cap")
                .texture("particle", barTexture)
                .texture("bars", barTexture)
                .texture("edge", barTexture);
    }

    public BlockModelBuilder barCapAlt(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_cap_alt")
                .texture("particle", barTexture)
                .texture("bars", barTexture)
                .texture("edge", barTexture);
    }

    public BlockModelBuilder barPost(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_post")
                .texture("particle", barTexture)
                .texture("bars", barTexture);
    }

    public BlockModelBuilder barPostEnds(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_post_ends")
                .texture("particle", barTexture)
                .texture("bars", barTexture)
                .texture("edge", barTexture);
    }

    public BlockModelBuilder barSide(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_side")
                .texture("particle", barTexture)
                .texture("bars", barTexture)
                .texture("edge", barTexture);
    }

    public BlockModelBuilder barSideAlt(String name, ResourceLocation barTexture) {
        return withExistingParent(name, BLOCK_FOLDER + "/iron_bars_side_alt")
                .texture("particle", barTexture)
                .texture("bars", barTexture)
                .texture("edge", barTexture);
    }

    public BlockModelBuilder tintedCross(String name, ResourceLocation cross) {
        return withExistingParent(name, BLOCK_FOLDER + "/tinted_cross")
                .texture("cross", cross);
    }

    public void doubleTintedCross(String name, String texture) {
        tintedCross(name + "_top", chaosRL(texture + "_top"));
        tintedCross(name + "_bottom", chaosRL(texture + "_bottom"));
    }

    public BlockModelBuilder buttonInventory(String name, ResourceLocation texture) {
        return withExistingParent(name, BLOCK_FOLDER + "/button_inventory")
                .texture("texture", texture);
    }

    public BlockModelBuilder pottedCross(String name, ResourceLocation texture) {
        return withExistingParent(name, BLOCK_FOLDER + "/flower_pot_cross")
                .texture("plant", texture);
    }

    @Override
    public BlockModelBuilder cubeColumn(String name, ResourceLocation side, ResourceLocation end) {
        return withExistingParent(name, BLOCK_FOLDER).texture("side", side).texture("end", end);
    }

    @Override
    public BlockModelBuilder cubeColumnHorizontal(String name, ResourceLocation side, ResourceLocation end) {
        return withExistingParent(name, BLOCK_FOLDER).texture("side", side).texture("end", end);
    }
}
