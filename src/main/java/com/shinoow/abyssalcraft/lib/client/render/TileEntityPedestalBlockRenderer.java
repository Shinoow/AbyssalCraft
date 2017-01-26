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
package com.shinoow.abyssalcraft.lib.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.lib.util.blocks.ISingletonInventory;

/**
 * TESR used for rendering Items placed on pedestal blocks<br>
 * (the block rendering itself is done through JSON models).
 * @author shinoow
 *
 */
@SideOnly(Side.CLIENT)
public class TileEntityPedestalBlockRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

		ISingletonInventory ped = (ISingletonInventory)te;

		if (ped != null && ped.getItem() != null){
			GlStateManager.pushMatrix();
			EntityItem entityitem = new EntityItem(te.getWorld(), 0.0D, 0.0D, 0.0D, ped.getItem());
			entityitem.hoverStart = 0.0F;
			ped.getItem().stackSize = 1;
			GlStateManager.rotate(180F, 1F, 0F, 0F);
			GlStateManager.translate(0.0F, -0.72F, 0F);
			GlStateManager.rotate(ped.getRotation(), 0F, 1F, 0F);
			GlStateManager.color(1F, 1F, 1F, 1F);
			RenderManager rm = Minecraft.getMinecraft().getRenderManager();
			if (rm.options.fancyGraphics)
				rm.doRenderEntity(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
			else{
				GlStateManager.rotate(180F, 0F, 1F, 0F);
				rm.options.fancyGraphics = true;
				int i = 15728880;
				int j = i % 65536;
				int k = i / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
				rm.doRenderEntity(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
				rm.options.fancyGraphics = false;
			}
			GlStateManager.popMatrix();
		}

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}
