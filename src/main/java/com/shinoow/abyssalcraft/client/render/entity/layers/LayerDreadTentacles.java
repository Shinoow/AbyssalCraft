/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.client.model.entity.ModelDreadTentacles;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LayerDreadTentacles<E extends EntityLivingBase> implements LayerRenderer<E> {

	private ModelDreadTentacles model = new ModelDreadTentacles();
	private boolean foundPart;
	private final RenderLivingBase<E> render;
	private final ResourceLocation TEXTURE = new ResourceLocation("abyssalcraft:textures/model/dread_tentacle.png");

	public LayerDreadTentacles(RenderLivingBase<E> renderIn) {
		render = renderIn;
		alignTentacle();
	}

	@Override
	public void doRenderLayer(E entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if(!foundPart) return;
		if(EntityUtil.isDreadPlagueCarrier(entitylivingbaseIn)) {
			render.bindTexture(TEXTURE);
			model.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	@Override
	public boolean shouldCombineTextures() {

		return false;
	}

	private void alignTentacle() {
		for(ModelRenderer model1 : render.getMainModel().boxList)
			if(!model1.cubeList.isEmpty()) {
				ModelBox cube = model1.cubeList.get(0);
				foundPart = true;
				model.base.setRotationPoint(model1.rotationPointX, model1.rotationPointY + (cube.posY1 + cube.posY2) + 1.0F, model1.rotationPointZ);
				break;
			}
	}
}
