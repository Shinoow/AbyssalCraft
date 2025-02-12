/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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
import com.shinoow.abyssalcraft.common.entity.ghoul.EntityGhoulBase;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LayerDreadTentacles<E extends EntityLiving> implements LayerRenderer<E> {

	private ModelDreadTentacles model = new ModelDreadTentacles();
	private boolean foundPart;
	private final RenderLiving<E> render;
	private final ResourceLocation TEXTURE = new ResourceLocation("abyssalcraft:textures/model/dread_tentacle.png");

	private ModelRenderer part;
	
	public LayerDreadTentacles(RenderLiving<E> renderIn) {
		render = renderIn;
		alignTentacle();
	}

	@Override
	public void doRenderLayer(E entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if(!foundPart) return;
		if(EntityUtil.isDreadPlagueCarrier(entitylivingbaseIn)) {
			reAlignTentacle(entitylivingbaseIn);
			render.bindTexture(TEXTURE);
			model.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	@Override
	public boolean shouldCombineTextures() {

		return false;
	}

	private void alignTentacle() {
		if(render != null && render.getMainModel() != null)
			for(ModelRenderer model1 : render.getMainModel().boxList)
				if(!model1.cubeList.isEmpty()) {
					ModelBox cube = model1.cubeList.get(0);
					foundPart = true;
					part = model1;
					model.base.setRotationPoint(model1.rotationPointX, model1.rotationPointY + (cube.posY1 + cube.posY2) + 1.0F, model1.rotationPointZ);
					break;
				}
	}
	
	private void reAlignTentacle(E entity) {

		ModelBox cube = part.cubeList.get(0);
		float y = (cube.posY1 + cube.posY2) + 1.0F;
		if(entity instanceof EntityGhoulBase && entity.posX == entity.prevPosX
				 && entity.posZ == entity.prevPosZ && entity.posZ == entity.prevPosZ)
			y = 0;
		
		model.base.setRotationPoint(part.rotationPointX, part.rotationPointY + y, part.rotationPointZ);
	}
}
