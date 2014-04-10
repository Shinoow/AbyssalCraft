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

public class TileEntityPSDLRenderer extends TileEntitySpecialRenderer{

	IModelCustom model = AdvancedModelLoader.loadModel(modelresource);
	private static final ResourceLocation modelresource = new ResourceLocation("abyssalcraft:models/PSDL.obj");
	private static final ResourceLocation Resourcelocation = new ResourceLocation("abyssalcraft:textures/model/blocks/PSDL.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {

		GL11.glPushMatrix();

		GL11.glTranslated(x + 0.5D, y, z + 0.75D);

		Minecraft.getMinecraft().renderEngine.bindTexture(Resourcelocation);
		this.model.renderAll();

		GL11.glPopMatrix();
	}

	public void renderBlockPSDL(TileEntityPSDL tl, World world, int i, int j, int k, Block block) {
		Tessellator tessellator = Tessellator.instance;

		float f = block.getLightOpacity(world, i, j, k);
		int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
		int l1 = l % 65536;
		int l2 = l / 65536;
		tessellator.setColorOpaque_F(f, f, f);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)l1, (float)l2); 

		int dir = world.getBlockMetadata(i, j, k);

		GL11.glPushMatrix();
		GL11.glTranslatef(0.5F, 0, 0.5F);

		GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
		GL11.glTranslatef(-0.5F, 0, -0.5F);

		this.model.renderAll();

		GL11.glPopMatrix();
	}
}