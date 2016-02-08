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
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.block.ModelEngraver;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEngraver;

@SideOnly(Side.CLIENT)
public class TileEntityEngraverRenderer extends TileEntitySpecialRenderer {

	ModelEngraver model = new ModelEngraver();
	private static final ResourceLocation Resourcelocation = new ResourceLocation("abyssalcraft:textures/model/blocks/Engraver.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

		Minecraft.getMinecraft().renderEngine.bindTexture(Resourcelocation);
		if(te != null){
			TileEntityEngraver tile = (TileEntityEngraver)te;
			int direction = tile.getDirection();
			GL11.glRotatef(direction * 90, 0.0F, 1.0F, 0.0F);
		}else GL11.glRotatef(90, 0, 1, 0);

		model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	//	public void renderBlockEngraver(TileEntityEngraver tl, World world, int i, int j, int k, Block block) {
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
