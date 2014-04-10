package com.shinoow.abyssalcraft.client.render.item;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.item.ModelStaff;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderStaff implements IItemRenderer 
{
	protected ModelStaff model;

	ResourceLocation resource = new ResourceLocation("/assets/abyssalcraft/textures/model/staff.png");
	
	public RenderStaff()
	{
		model = new ModelStaff();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		
		switch(type)
		{
		case EQUIPPED: return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		switch(type)
		{
		case EQUIPPED:
		{
			GL11.glPushMatrix();
			
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(resource);
			
			model.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			
			GL11.glPopMatrix();
		}
		default:
			break;
		}
		
	}
	
}