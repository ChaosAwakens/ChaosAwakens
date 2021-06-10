package io.github.chaosawakens.client.entity.render.tileentities;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.common.blocks.CAAbstractSkullBlock;
import io.github.chaosawakens.common.blocks.CASkullBlock;
import io.github.chaosawakens.common.blocks.CASkullWallBlock;
import io.github.chaosawakens.common.blocks.tileentities.CASkullTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.entity.model.HumanoidHeadModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import javax.annotation.Nullable;
import java.util.Map;

public class CASkullTileEntityRenderer extends TileEntityRenderer<CASkullTileEntity> {
    private static final Map<CASkullBlock.ISkullType, GenericHeadModel> MODELS = Util.make(Maps.newHashMap(), (p_209262_0_) -> {
        GenericHeadModel genericheadmodel1 = new HumanoidHeadModel();
        p_209262_0_.put(CASkullBlock.Types.HUSK, genericheadmodel1);
    });
    private static final Map<CASkullBlock.ISkullType, ResourceLocation> SKINS = Util.make(Maps.newHashMap(), (p_209263_0_) -> {
        p_209263_0_.put(CASkullBlock.Types.HUSK, new ResourceLocation("textures/entity/zombie/husk.png"));
    });

    public CASkullTileEntityRenderer(TileEntityRendererDispatcher p_i226015_1_) {
        super(p_i226015_1_);
    }

    public void render(CASkullTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        float f = tileEntityIn.getAnimationProgress(partialTicks);
        BlockState blockstate = tileEntityIn.getBlockState();
        boolean flag = blockstate.getBlock() instanceof CASkullWallBlock;
        Direction direction = flag ? blockstate.get(CASkullWallBlock.FACING) : null;
        float f1 = 22.5F * (float)(flag ? (2 + direction.getHorizontalIndex()) * 4 : blockstate.get(CASkullBlock.ROTATION));
        render(direction, f1, ((CAAbstractSkullBlock)blockstate.getBlock()).getSkullType(), tileEntityIn.getPlayerProfile(), f, matrixStackIn, bufferIn, combinedLightIn);
    }

    public static void render(@Nullable Direction directionIn, float p_228879_1_, CASkullBlock.ISkullType skullType, @Nullable GameProfile gameProfileIn, float animationProgress, MatrixStack matrixStackIn, IRenderTypeBuffer buffer, int combinedLight) {
        GenericHeadModel genericheadmodel = MODELS.get(skullType);
        matrixStackIn.push();
        if (directionIn == null) {
            matrixStackIn.translate(0.5D, 0.0D, 0.5D);
        } else {
            float f = 0.25F;
            matrixStackIn.translate((0.5F - (float)directionIn.getXOffset() * 0.25F), 0.25D, (0.5F - (float)directionIn.getZOffset() * 0.25F));
        }

        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        IVertexBuilder ivertexbuilder = buffer.getBuffer(getRenderType(skullType, gameProfileIn));
        genericheadmodel.func_225603_a_(animationProgress, p_228879_1_, 0.0F);
        genericheadmodel.render(matrixStackIn, ivertexbuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
    }

    private static RenderType getRenderType(CASkullBlock.ISkullType skullType, @Nullable GameProfile gameProfileIn) {
        ResourceLocation resourcelocation = SKINS.get(skullType);
        return RenderType.getEntityCutoutNoCullZOffset(resourcelocation);
    }
}