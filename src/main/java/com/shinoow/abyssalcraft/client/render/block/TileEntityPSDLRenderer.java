/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPSDL;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityPSDLRenderer extends TileEntitySpecialRenderer {

	IModelCustom model = AdvancedModelLoader.loadModel(modelresource);
	private static final ResourceLocation modelresource = new ResourceLocation("abyssalcraft:models/PSDL.obj");
	private static final ResourceLocation Resourcelocation = new ResourceLocation("abyssalcraft:textures/model/blocks/PSDL.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.75D);

		Minecraft.getMinecraft().renderEngine.bindTexture(Resourcelocation);
		model.renderAll();
		
		GL11.glPopMatrix();
	}

	public void renderBlockPSDL(TileEntityPSDL tl, World world, int i, int j, int k, Block block) {
		Tessellator tessellator = Tessellator.instance;

		float f = block.getLightOpacity(world, i, j, k);
		int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
		int l1 = l % 65536;
		int l2 = l / 65536;
		tessellator.setColorOpaque_F(f, f, f);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1, l2);
	}
}