package io.github.chaosawakens.client.renderers.entity.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.chaosawakens.common.entity.misc.CAFallingBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;

import java.util.Random;

public class CAFallingBlockRenderer extends EntityRenderer<CAFallingBlockEntity>{

	public CAFallingBlockRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ResourceLocation getTextureLocation(CAFallingBlockEntity p_110775_1_) {
		return AtlasTexture.LOCATION_BLOCKS;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(CAFallingBlockEntity entity, float yRot, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		BlockState curState = entity.getBlock();
		if (curState.getRenderShape() == BlockRenderType.MODEL) {
			World curLevel = entity.getLevel();
			BlockState worldState = curLevel.getBlockState(entity.blockPosition());
			if (curState != worldState && curState.getRenderShape() != BlockRenderType.INVISIBLE) {
				stack.pushPose();
				BlockPos updatedPos = new BlockPos(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
				stack.translate(-0.5D, 0, -0.5D);
				for (RenderType type : RenderType.chunkBufferLayers()) {
					Random rand = new Random();
					ForgeHooksClient.setRenderLayer(type);
					Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateBlock(curLevel, Minecraft.getInstance().getBlockRenderer().getBlockModel(curState), curState, updatedPos, stack, buffer.getBuffer(type), false, rand, curState.getSeed(entity.getStartPos()), OverlayTexture.NO_OVERLAY);
				}
				ForgeHooksClient.setRenderLayer(null);
				stack.popPose();
				super.render(entity, yRot, partialTicks, stack, buffer, light);
			}
		}	
	}

}
