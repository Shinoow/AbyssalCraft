package com.shinoow.abyssalcraft.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.block.ModelRitualAltar;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTieredSacrificialAltar;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityTieredSacrificialAltarRenderer extends TileEntitySpecialRenderer {

	ModelRitualAltar model = new ModelRitualAltar();

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/model/blocks/TieredSacrificialAltar_"+te.getBlockMetadata()+".png"));
		model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		TileEntityTieredSacrificialAltar altar = (TileEntityTieredSacrificialAltar) te;

		if (altar != null && altar.getItem() != null){
			GL11.glPushMatrix();
			EntityItem entityitem = new EntityItem(te.getWorldObj(), 0.0D, 0.0D, 0.0D, altar.getItem());
			entityitem.hoverStart = 0.0F;
			altar.getItem().stackSize = 1;
			GL11.glRotatef(180F, 1F, 0F, 0F);
			GL11.glTranslatef(0.0F, -0.56F, 0F);
			GL11.glRotatef(altar.getRotation(), 0F, 1F, 0F);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			if (RenderManager.instance.options.fancyGraphics)
				RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			else{
				GL11.glRotatef(180F, 0F, 1F, 0F);
				RenderManager.instance.options.fancyGraphics = true;
				int i = 15728880;
				int j = i % 65536;
				int k = i / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
				RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				RenderManager.instance.options.fancyGraphics = false;
			}
			GL11.glPopMatrix();
		}

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public void renderBlockTieredSacrificialAltar(TileEntityTieredSacrificialAltar tl, World world, int i, int j, int k, Block block) {
		Tessellator tessellator = Tessellator.instance;

		float f = block.getLightOpacity(world, i, j, k);
		int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
		int l1 = l % 65536;
		int l2 = l / 65536;
		tessellator.setColorOpaque_F(f, f, f);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1, l2);
	}
}
