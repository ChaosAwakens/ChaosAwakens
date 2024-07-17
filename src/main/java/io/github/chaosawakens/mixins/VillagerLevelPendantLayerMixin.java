package io.github.chaosawakens.mixins;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.chaosawakens.common.registry.CAVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.VillagerLevelPendantLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerLevelPendantLayer.class)
public abstract class VillagerLevelPendantLayerMixin<T extends LivingEntity & IVillagerDataHolder, M extends EntityModel<T> & IHeadToggle> extends LayerRenderer<T, M> {
    @Shadow
    @Final
    private String path;
    @Shadow
    @Final
    private static Int2ObjectMap<ResourceLocation> LEVEL_LOCATIONS;

    private VillagerLevelPendantLayerMixin(IEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    @Inject(method = "Lnet/minecraft/client/renderer/entity/layers/VillagerLevelPendantLayer;render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/VillagerLevelPendantLayer;renderColoredCutoutModel(Lnet/minecraft/client/renderer/entity/model/EntityModel;Lnet/minecraft/util/ResourceLocation;Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/LivingEntity;FFF)V", ordinal = 1), cancellable = true)
    private void chaosawakens$render(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        if (!pLivingEntity.isInvisible()) {
            VillagerData curData = pLivingEntity.getVillagerData();
            VillagerType curType = curData.getType();
            VillagerProfession curProfession = curData.getProfession();
            ResourceLocation professionTypeLoc = Registry.VILLAGER_PROFESSION.getKey(curProfession);
            ResourceLocation archaeologistDefault = chaosawakens$getResourceLocation("profession", new ResourceLocation(professionTypeLoc.getNamespace(), professionTypeLoc.getPath().concat("_default")));
            ResourceLocation archaeologistByType = chaosawakens$getResourceLocation("profession", new ResourceLocation(professionTypeLoc.getNamespace(), professionTypeLoc.getPath().concat("_" + curType)));
            ResourceLocation professionLevelLoc = chaosawakens$getResourceLocation("profession_level", LEVEL_LOCATIONS.get(MathHelper.clamp(curData.getLevel(), 1, LEVEL_LOCATIONS.size())));

            if (curProfession.equals(CAVillagers.ARCHAEOLOGIST.get()) || curProfession.equals(CAVillagers.ARCHAEOLOGIST_COPPER.get())) {
                ci.cancel();
                renderColoredCutoutModel(getParentModel(), archaeologistByType != null ? archaeologistByType : archaeologistDefault, pMatrixStack, pBuffer, pPackedLight, pLivingEntity, 1.0F, 1.0F, 1.0F);
                renderColoredCutoutModel(getParentModel(), professionLevelLoc, pMatrixStack, pBuffer, pPackedLight, pLivingEntity, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    @Unique
    private ResourceLocation chaosawakens$getResourceLocation(String villagerSubPath, ResourceLocation villagerProfessionLoc) {
        return new ResourceLocation(villagerProfessionLoc.getNamespace(), "textures/entity/" + this.path + "/" + villagerSubPath + "/" + villagerProfessionLoc.getPath() + ".png");
    }
}
