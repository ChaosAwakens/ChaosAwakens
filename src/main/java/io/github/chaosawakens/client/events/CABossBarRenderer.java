package io.github.chaosawakens.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.HashMap;

public class CABossBarRenderer {//TODO Not literally be an AOA clone for this custom bossbar stuff. Will likely be deferrred to 1.20.1+.
	private static final HashMap<String, ResourceLocation> textureCache = new HashMap<String, ResourceLocation>();

	public static void onBossInfoRender(final RenderGameOverlayEvent.BossInfo ev) {
		if (!ev.isCanceled()) {
			ITextComponent nameComponent = ev.getBossInfo().getName();
			ITextComponent name;
			String id;
			MatrixStack matrix = ev.getMatrixStack();

			if (nameComponent.getSiblings().isEmpty() || !(nameComponent instanceof TranslationTextComponent))
				return;

			id = ((TranslationTextComponent)nameComponent).getKey();

			if (!id.startsWith("entity.chaosawakens."))
				return;

			name = nameComponent.getSiblings().get(0);

			Minecraft mc = Minecraft.getInstance();
			MainWindow mainWindow = mc.getWindow();
			ResourceLocation texture = getTexture(id.substring(20));
			int textureWidth = 196;
			int xPos = mainWindow.getGuiScaledWidth() / 2 - 100;
			int percentPixels = (int)Math.ceil(ev.getBossInfo().getPercent() * textureWidth);
			int stringWidth = mc.font.width(name);
			int x = mainWindow.getGuiScaledWidth() / 2 - stringWidth / 2;

			matrix.pushPose();
			RenderSystem.enableAlphaTest();
			RenderSystem.disableDepthTest();
			RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
			mc.getTextureManager().bind(texture);

			if (percentPixels < textureWidth)
				renderCustomSizedTexture(matrix, xPos, ev.getY(), 0, 10, 200, 10, 200, 36);

			if (percentPixels > 0)
				renderCustomSizedTexture(matrix, xPos + 2, ev.getY(), 2, -2, percentPixels, 12, 200, 36);

			renderCustomSizedTexture(matrix, xPos, ev.getY(), 0, 22, 200, 12, 200, 36);
			mc.font.drawShadow(ev.getMatrixStack(), name, x, ev.getY() - 9, 16777215);
			RenderSystem.enableDepthTest();
			RenderSystem.disableAlphaTest();
			matrix.popPose();

			ev.setIncrement(ev.getIncrement() + 5);
			ev.setCanceled(true);
		}
	}

	private static ResourceLocation getTexture(String id) {
		if (textureCache.containsKey(id))
			return textureCache.get(id);

		ResourceLocation texture = new ResourceLocation(ChaosAwakens.MODID, "textures/gui/bossbar/" + id + ".png");

		textureCache.put(id, texture);

		return texture;
	}
	
	public static void renderCustomSizedTexture(MatrixStack matrix, int x, int y, float u, float v, float uWidth, float vHeight, float textureWidth, float textureHeight) {
		renderScaledCustomSizedTexture(matrix, x, y, u, v, uWidth, vHeight, uWidth, vHeight, textureWidth, textureHeight);
	}

	public static void renderScaledCustomSizedTexture(MatrixStack matrixStack, float x, float y, float u, float v, float uWidth, float vHeight, float renderWidth, float renderHeight, float textureWidth, float textureHeight) {
		BufferBuilder buffer = Tessellator.getInstance().getBuilder();
		Matrix4f matrix = matrixStack.last().pose();
		float widthRatio = 1.0F / textureWidth;
		float heightRatio = 1.0F / textureHeight;

		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		buffer.vertex(matrix, x, y + renderHeight, 0f).uv(u * widthRatio, (v + vHeight) * heightRatio).endVertex();
		buffer.vertex(matrix, x + renderWidth, y + renderHeight, 0f).uv((u + uWidth) * widthRatio, (v + vHeight) * heightRatio).endVertex();
		buffer.vertex(matrix, x + renderWidth, y, 0f).uv((u + uWidth) * widthRatio, v * heightRatio).endVertex();
		buffer.vertex(matrix, x, y, 0f).uv(u * widthRatio, v * heightRatio).endVertex();
		buffer.end();
		WorldVertexBufferUploader.end(buffer);
	}

}
