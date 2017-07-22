/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.client.model.block.ModelJzaharSpawner;

public class TileEntityJzaharSpawnerRenderer extends TileEntitySpecialRenderer {

	ModelJzaharSpawner model = new ModelJzaharSpawner();
	private static final ResourceLocation texture = new ResourceLocation("abyssalcraft:textures/model/boss/j'zahar.png");

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.675F, (float) y + 0.9F, (float) z + 0.5F);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
		GlStateManager.scale(1.5F, 1.5F, 1.5F);

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		model.render((Entity)null, 0.0F, 0.0F, 8.0F, 0.0F, 0.0F, 0.0625F);

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}
