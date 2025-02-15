package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.common.entity.ghoul.EntityGhoulBase;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;

public abstract class RenderGhoulBase<T extends EntityGhoulBase> extends RenderLiving<T> {

	public RenderGhoulBase(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

	@Override
	protected void renderModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		float alpha = 1;

		if(entitylivingbaseIn.ticksExisted == 0 && entitylivingbaseIn.doFadeIn)
			entitylivingbaseIn.fadeInTimer = 40;

		if(entitylivingbaseIn.fadeInTimer > 0) {
			entitylivingbaseIn.fadeInTimer--;
			alpha -= (float) Math.max(entitylivingbaseIn.fadeInTimer, 1.0f) / 10;
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		actuallyRender(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		GlStateManager.disableBlend();
	}

	//Special-casing for Shadow Ghouls
	protected void actuallyRender(T entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
	}
}
