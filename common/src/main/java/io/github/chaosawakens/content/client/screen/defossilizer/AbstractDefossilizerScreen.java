package io.github.chaosawakens.content.client.screen.defossilizer;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.client.component.defossilizer.AbstractDefossilizerRecipeBookComponent;
import io.github.chaosawakens.content.client.menu.defossilizer.AbstractDefossilizerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDefossilizerScreen<ADM extends AbstractDefossilizerMenu> extends AbstractContainerScreen<ADM> implements RecipeUpdateListener {
    public static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
    public static final ResourceLocation DEFAULT_GUI_TEXTURE = CAConstants.prefix("textures/gui/container/defossilizer/defossilizer.png");
    protected final AbstractDefossilizerRecipeBookComponent recipeBookComponent;
    protected final ResourceLocation guiTexture;
    protected boolean widthTooNarrow;

    public AbstractDefossilizerScreen(ADM menu, AbstractDefossilizerRecipeBookComponent recipeBookComponent, Inventory playerInventory, Component title, ResourceLocation guiTexture) {
        super(menu, playerInventory, title);

        this.recipeBookComponent = recipeBookComponent;
        this.guiTexture = guiTexture;
    }

    @Override
    protected void init() {
        super.init();

        this.widthTooNarrow = width < 379;

        recipeBookComponent.init(width, this.height, minecraft, widthTooNarrow, menu);

        this.leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth);

        addRenderableWidget(new ImageButton(leftPos + 20, height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (button) -> {
            recipeBookComponent.toggleVisibility();

            this.leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth);

            button.setPosition(leftPos + 20, height / 2 - 49);
        }));

        this.titleLabelX = (imageWidth - font.width(title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int leftMostPos = leftPos;
        int topMostPos = topPos;

        guiGraphics.blit(guiTexture, leftMostPos, topMostPos, 0, 0, imageWidth, imageHeight);

        if (menu.isDefossilizing()) {
            int defossilizationProgress = menu.getDefossilizationProgress();
            int litProgress = Mth.clamp(200 - defossilizationProgress, 0, 200);

            guiGraphics.blit(guiTexture, leftMostPos + 79, topMostPos + 34, 176, 14, defossilizationProgress + 1, 16); // Arrow
            guiGraphics.blit(guiTexture, leftMostPos + 56, topMostPos + 36 + 12 - litProgress, 176, 12 - litProgress, 14, litProgress + 1); // Fire
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);

        if (recipeBookComponent.isVisible() && widthTooNarrow) {
            renderBg(guiGraphics, partialTick, mouseX, mouseY);
            recipeBookComponent.render(guiGraphics, mouseX, mouseY, partialTick);
        } else {
            recipeBookComponent.render(guiGraphics, mouseX, mouseY, partialTick);

            super.render(guiGraphics, mouseX, mouseY, partialTick);

            recipeBookComponent.renderGhostRecipe(guiGraphics, leftPos, topPos, true, partialTick);
        }

        renderTooltip(guiGraphics, mouseX, mouseY);
        recipeBookComponent.renderTooltip(guiGraphics, leftPos, topPos, mouseX, mouseY);
    }

    @Override
    protected void containerTick() {
        super.containerTick();

        recipeBookComponent.tick();
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        boolean outOfGuiBounds = mouseX < guiLeft || mouseY < guiTop || mouseX >= guiLeft + imageWidth || mouseY >= guiTop + this.imageHeight;
        return recipeBookComponent.hasClickedOutside(mouseX, mouseY, leftPos, topPos, imageWidth, imageHeight, mouseButton) && outOfGuiBounds;
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slot, slotId, mouseButton, type);

        recipeBookComponent.slotClicked(slot);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return recipeBookComponent.mouseClicked(mouseX, mouseY, button)
                || (widthTooNarrow && recipeBookComponent.isVisible())
                || super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return !recipeBookComponent.keyPressed(keyCode, scanCode, modifiers) && super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return recipeBookComponent.charTyped(codePoint, modifiers) || super.charTyped(codePoint, modifiers);
    }

    @Override
    public void recipesUpdated() {
        recipeBookComponent.recipesUpdated();
    }

    @Override
    public @NotNull AbstractDefossilizerRecipeBookComponent getRecipeBookComponent() {
        return recipeBookComponent;
    }
}
