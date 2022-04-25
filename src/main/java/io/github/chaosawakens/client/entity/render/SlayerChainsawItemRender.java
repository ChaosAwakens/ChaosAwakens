package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.chaosawakens.client.entity.model.SlayerChainsawItemModel;
import io.github.chaosawakens.common.items.SlayerChainsawItem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SlayerChainsawItemRender extends GeoItemRenderer<SlayerChainsawItem> {
	public SlayerChainsawItemRender() {
		super(new SlayerChainsawItemModel());
	}
}
