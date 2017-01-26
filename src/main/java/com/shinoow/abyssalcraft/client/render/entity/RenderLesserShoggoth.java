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
package com.shinoow.abyssalcraft.client.render.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.client.model.entity.ModelLesserShoggoth;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;

@SideOnly(Side.CLIENT)
public class RenderLesserShoggoth extends RenderLiving<EntityLesserShoggoth> {

	private static final ResourceLocation shoggothResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/LesserShoggoth.png");
	private static final ResourceLocation abyssalResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/AbyssalShoggoth.png");
	private static final ResourceLocation dreadedResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/DreadedShoggoth.png");
	private static final ResourceLocation omotholResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/OmotholShoggoth.png");
	private static final ResourceLocation darkResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/ShadowShoggoth.png");

	public RenderLesserShoggoth(RenderManager manager)
	{
		super(manager, new ModelLesserShoggoth(), 1.6F);
	}

	@Override
	protected void renderModel(EntityLesserShoggoth entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		if(entitylivingbaseIn.getShoggothType() < 4){
			super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
			return;
		}

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		GlStateManager.disableBlend();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLesserShoggoth par1EntityLiving)
	{
		switch (par1EntityLiving.getShoggothType())
		{
		case 0:
			return shoggothResource;
		case 1:
			return abyssalResource;
		case 2:
			return dreadedResource;
		case 3:
			return omotholResource;
		case 4:
			return darkResource;
		default:
			return shoggothResource;
		}
	}
}
