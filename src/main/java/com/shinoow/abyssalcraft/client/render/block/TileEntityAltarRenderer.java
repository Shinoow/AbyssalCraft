package com.shinoow.abyssalcraft.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.block.ModelAltar;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityAltar;

public class TileEntityAltarRenderer extends TileEntitySpecialRenderer{
    
	ModelAltar model = new ModelAltar();
	private static final ResourceLocation Resourcelocation = new ResourceLocation("abyssalcraft:textures/model/blocks/Altar.png");
	
	 @Override
	    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
	    
	            GL11.glPushMatrix();
	   
	            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
	            
	            GL11.glPushMatrix();
	            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
	            
	            Minecraft.getMinecraft().renderEngine.bindTexture(Resourcelocation);
	            this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

	            GL11.glPopMatrix();
	            GL11.glPopMatrix();
	    }
   
    public void renderBlockAltar(TileEntityAltar tl, World world, int i, int j, int k, Block block) {
        Tessellator tessellator = Tessellator.instance;
        
        float f = block.getLightOpacity(world, i, j, k);
        int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)l1, (float)l2); 
        
        /*This will rotate your model corresponding to player direction that was when you placed the block. If you want this to work, 
        add these lines to onBlockPlacedBy method in your block class.
        int dir = MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, dir, 0);*/

        int dir = world.getBlockMetadata(i, j, k);
        
        GL11.glPushMatrix();
         GL11.glTranslatef(0.5F, 0, 0.5F);
         
         GL11.glRotatef(dir * (-90F), 0F, 1F, 0F);
         GL11.glTranslatef(-0.5F, 0, -0.5F);
         
         
         this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }
}