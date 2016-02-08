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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityRitualAltar;

@SideOnly(Side.CLIENT)
public class TileEntityRitualAltarRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

		TileEntityRitualAltar altar = (TileEntityRitualAltar) te;

		if (altar != null && altar.getItem() != null){
			GL11.glPushMatrix();
			EntityItem entityitem = new EntityItem(te.getWorld(), 0.0D, 0.0D, 0.0D, altar.getItem());
			entityitem.hoverStart = 0.0F;
			altar.getItem().stackSize = 1;
			GL11.glRotatef(180F, 1F, 0F, 0F);
			GL11.glTranslatef(0.0F, -0.56F, 0F);
			GL11.glRotatef(altar.getRotation(), 0F, 1F, 0F);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			RenderManager rm = Minecraft.getMinecraft().getRenderManager();
			if (rm.options.fancyGraphics)
				rm.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			else{
				GL11.glRotatef(180F, 0F, 1F, 0F);
				rm.options.fancyGraphics = true;
				int i = 15728880;
				int j = i % 65536;
				int k = i / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
				rm.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				rm.options.fancyGraphics = false;
			}
			GL11.glPopMatrix();
		}

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	//	public void renderBlockRitualAltar(TileEntityRitualAltar tl, World world, int i, int j, int k, Block block) {
	//		Tessellator tessellator = Tessellator.instance;
	//
	//		float f = block.getLightOpacity(world, i, j, k);
	//		int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
	//		int l1 = l % 65536;
	//		int l2 = l / 65536;
	//		tessellator.setColorOpaque_F(f, f, f);
	//		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1, l2);
	//	}
}
