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
package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelDreadTentacles;
import com.shinoow.abyssalcraft.common.entity.EntityCompassTentacle;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCompassTentacle extends Render<EntityCompassTentacle> {

	private ModelDreadTentacles model = new ModelDreadTentacles();
	private static final ResourceLocation texture = new ResourceLocation("abyssalcraft", "textures/model/compass_tentacle.png");

	public RenderCompassTentacle(RenderManager renderManager) {
		super(renderManager);
		model.setRotateAngle(model.base, 1.5707963267948966F, 0.0F, 0.0F);
		model.pointing = true;
	}

	@Override
	public void doRender(EntityCompassTentacle entity, double x, double y, double z, float entityYaw, float partialTicks) {

		GlStateManager.pushMatrix();
		GlStateManager.translate(x+0.1f, y+1.3f, z-0.1f);
		GlStateManager.scale(0.3, 0.3, 0.3);

		GlStateManager.pushMatrix();
		GlStateManager.rotate(180.0f, 0.0F, 0.0F, 1.0F);

		GlStateManager.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);

		bindTexture(texture);
		model.render(entity, 0,0, partialTicks, 0, 0, 0.5F);

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCompassTentacle entity) {

		return texture;
	}

}
