package io.github.chaosawakens.client.entity.model;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantedGoldenAppleCowModel<T extends Entity> extends CAQuadrupedModel<T> {
	public EnchantedGoldenAppleCowModel() {
		super(12, 0.0F, false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
		
		this.headModel = new ModelRenderer(this, 0, 0);
		this.headModel.addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, 0.0F);
		this.headModel.setPos(0.0F, 4.0F, -8.0F);
		this.headModel.texOffs(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F);
		this.headModel.texOffs(22, 0).addBox(4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F);
		
		this.modelGlint = new ModelRenderer(this, 32, 0);
		this.modelGlint.addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, 0.0F);
		this.modelGlint.setPos(0.0F, 4.0F, -8.0F);
		this.modelGlint.texOffs(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F);
		this.modelGlint.texOffs(22, 0).addBox(4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F);
		
		this.body = new ModelRenderer(this, 18, 4);
		this.body.addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, 0.0F);
		this.body.setPos(0.0F, 5.0F, 2.0F);
		this.body.texOffs(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F);
		
		--this.legBackRight.x;
		++this.legBackLeft.x;
		this.legBackRight.z += 0.0F;
		this.legBackLeft.z += 0.0F;
		--this.legFrontRight.x;
		++this.legFrontLeft.x;
		--this.legFrontRight.z;
		--this.legFrontLeft.z;
	}
	
	public ModelRenderer getHead() {
		return this.headModel;
	}
}
