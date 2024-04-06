package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.client.model.entity.ModelDreadTentacles;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LayerDreadTentacles<E extends EntityLivingBase> implements LayerRenderer<E> {

	private ModelDreadTentacles model = new ModelDreadTentacles();
	private final RenderLivingBase<E> render;
	private final ResourceLocation TEXTURE = new ResourceLocation("abyssalcraft:textures/model/dread_tentacle.png");
	
	public LayerDreadTentacles(RenderLivingBase<E> renderIn) {
		render = renderIn;
	}

	@Override
	public void doRenderLayer(E entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if(EntityUtil.isDreadPlagueCarrier(entitylivingbaseIn)) {
			render.bindTexture(TEXTURE);
			float f10 = entitylivingbaseIn.prevRotationYawHead + (entitylivingbaseIn.rotationYawHead - entitylivingbaseIn.prevRotationYawHead) * partialTicks - (entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks);
			float f11 = entitylivingbaseIn.prevRotationPitch + (entitylivingbaseIn.rotationPitch - entitylivingbaseIn.prevRotationPitch) * partialTicks;
			GlStateManager.pushMatrix();
//			
//			GlStateManager.rotate(45, 1, 0, 0);
//			GlStateManager.translate(0, 0.3f, -0.8f);
			model.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {

		return false;
	}

}
