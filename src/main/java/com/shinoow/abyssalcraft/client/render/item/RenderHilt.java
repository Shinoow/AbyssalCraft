///*******************************************************************************
// * AbyssalCraft
// * Copyright (c) 2012 - 2016 Shinoow.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the GNU Lesser Public License v3
// * which accompanies this distribution, and is available at
// * http://www.gnu.org/licenses/lgpl-3.0.txt
// *
// * Contributors:
// *     Shinoow -  implementation
// ******************************************************************************/
//package com.shinoow.abyssalcraft.client.render.item;
//
//import net.minecraft.entity.Entity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.client.IItemRenderer;
//
//import org.lwjgl.opengl.GL11;
//
//import com.shinoow.abyssalcraft.client.model.item.ModelHilt;
//
//import cpw.mods.fml.client.FMLClientHandler;
//
//public class RenderHilt implements IItemRenderer {
//
//	protected ModelHilt model;
//
//	ResourceLocation resource = new ResourceLocation("abyssalcraft:textures/model/Katana.png");
//
//	public RenderHilt()
//	{
//		model = new ModelHilt();
//	}
//
//	@Override
//	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
//
//		return true;
//	}
//
//	@Override
//	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
//
//		switch (type) {
//		case INVENTORY:
//			return true;
//		default:
//			break;
//		}
//		return false;
//	}
//
//	@Override
//	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
//
//		if(type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED)
//		{
//			GL11.glPushMatrix();
//
//			FMLClientHandler.instance().getClient().renderEngine.bindTexture(resource);
//
//			GL11.glRotatef(90F, 1.0F, 1.0F, -0.5F);
//
//			if(type == ItemRenderType.EQUIPPED)
//				GL11.glTranslatef(0.75F, -0.1F, 0.1F);
//			else
//				GL11.glTranslatef(0.61F, 0.0F, 0.0F);
//
//			float scale = 1.1F;
//			GL11.glScalef(scale, scale, scale);
//
//			model.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
//
//			GL11.glPopMatrix();
//		}
//		if(type == ItemRenderType.ENTITY){
//
//			GL11.glPushMatrix();
//			float scale = 1.5F;
//			GL11.glScalef(scale, scale, scale);
//			FMLClientHandler.instance().getClient().renderEngine.bindTexture(resource);
//			GL11.glRotatef(0F, 1.0f, 0.0f, 0.0f);
//			GL11.glRotatef(90F, 0.0f, 1.0f, 0.0f);
//			GL11.glRotatef(90F, 0.0f, 0.0f, 1.0f);
//			GL11.glTranslatef(0.5F, -0.5F, 0.1F);
//			model.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
//			GL11.glPopMatrix();
//
//		}
//		if(type == ItemRenderType.INVENTORY){
//			GL11.glPushMatrix();
//			float scale = 1.0F;
//			GL11.glScalef(scale, scale, scale);
//			FMLClientHandler.instance().getClient().renderEngine.bindTexture(resource);
//
//			GL11.glRotatef(80F, 1.0f, 0.0f, 0.0f);
//			GL11.glRotatef(-80F, 0.0f, 1.0f, 0.0f);
//			GL11.glTranslatef(0.2F, 0.0F, -0.2F);
//			model.render(0.0625F);
//			GL11.glPopMatrix();
//		}
//	}
//}
