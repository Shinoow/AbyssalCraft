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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TileEntityPSDLRenderer extends TileEntitySpecialRenderer {

	//	IModelCustom model = AdvancedModelLoader.loadModel(modelresource);
	private static final ResourceLocation modelresource = new ResourceLocation("abyssalcraft:models/PSDL.obj");
	private static final ResourceLocation Resourcelocation = new ResourceLocation("abyssalcraft:textures/model/blocks/PSDL.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.75D);

		Minecraft.getMinecraft().renderEngine.bindTexture(Resourcelocation);
		//		model.renderAll();

		GL11.glPopMatrix();
	}

	//	public void renderBlockPSDL(TileEntityPSDL tl, World world, int i, int j, int k, Block block) {
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
