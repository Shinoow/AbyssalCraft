/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelLesserShoggoth;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerShoggothEyes;
import com.shinoow.abyssalcraft.common.entity.EntityShoggothBase;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShoggoth extends RenderLiving<EntityShoggothBase> {

	private static final ResourceLocation shoggothResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/lessershoggoth.png");
	private static final ResourceLocation abyssalResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/abyssalshoggoth.png");
	private static final ResourceLocation dreadedResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/dreadedshoggoth.png");
	private static final ResourceLocation omotholResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/omotholshoggoth.png");
	private static final ResourceLocation darkResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/shadowshoggoth.png");

	public RenderShoggoth(RenderManager manager, int size, float shadowSize)
	{
		super(manager, new ModelLesserShoggoth(size), shadowSize);
		addLayer(new LayerShoggothEyes(this));
	}

	@Override
	protected void renderModel(EntityShoggothBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		if(entitylivingbaseIn.getShoggothType() < 4){
			super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
			return;
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, entitylivingbaseIn.getBrightness());
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		GlStateManager.disableBlend();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityShoggothBase par1EntityLiving) {

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
