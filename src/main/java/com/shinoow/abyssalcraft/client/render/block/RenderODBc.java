/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.block;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityODBcPrimed;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderODBc extends Render {

	private RenderBlocks blockRenderer = new RenderBlocks();

	public RenderODBc() {
		shadowSize = 0.5F;
	}

	public void doRender(EntityODBcPrimed par1EntityODBcPrimed, double par2, double par4, double par6, float par8, float par9) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		float var10;

		if (par1EntityODBcPrimed.fuse - par9 + 1.0F < 10.0F) {
			var10 = 1.0F - (par1EntityODBcPrimed.fuse - par9 + 1.0F) / 10.0F;

			if (var10 < 0.0F)
				var10 = 0.0F;
			if (var10 > 1.0F)
				var10 = 1.0F;

			var10 *= var10;
			var10 *= var10;
			float var11 = 1.0F + var10 * 0.3F;
			GL11.glScalef(var11, var11, var11);
		}

		var10 = (1.0F - (par1EntityODBcPrimed.fuse - par9 + 1.0F) / 100.0F) * 0.8F;
		bindEntityTexture(par1EntityODBcPrimed);
		blockRenderer.renderBlockAsItem(AbyssalCraft.ODBcore, 0, par1EntityODBcPrimed.getBrightness(par9));

		if (par1EntityODBcPrimed.fuse / 5 % 2 == 0) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var10);
			blockRenderer.renderBlockAsItem(AbyssalCraft.ODBcore, 0, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		GL11.glPopMatrix();
	}

	protected ResourceLocation getEntityTexture(EntityODBcPrimed par1EntityODBPrimed)
	{
		return TextureMap.locationBlocksTexture;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((EntityODBcPrimed)entity);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		doRender((EntityODBcPrimed)par1Entity, par2, par4, par6, par8, par9);
	}
}
