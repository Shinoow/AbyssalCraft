/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.client.render;

import com.shinoow.abyssalcraft.lib.tileentity.TEDirectional;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * TESR for directional TEs ({@link TEDirectional } implementations).<br>
 * Handles rotations applied to the model.
 * @author shinoow
 *
 */
@SideOnly(Side.CLIENT)
public class TileEntityDirectionalRenderer extends TileEntitySpecialRenderer {

	final ModelBase model;
	private final ResourceLocation texture;

	public TileEntityDirectionalRenderer(ModelBase model, String res){
		this.model = model;
		texture = new ResourceLocation(res);
	}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		TEDirectional tile = (TEDirectional) te;
		int direction = tile.getDirection();
		GlStateManager.rotate(direction * 90, 0.0F, 1.0F, 0.0F);
		model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}
