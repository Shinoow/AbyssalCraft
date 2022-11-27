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
package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.client.model.entity.ModelLesserShoggoth;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLesserShoggoth extends RenderLiving {

	protected ModelLesserShoggoth model;

	private static final ResourceLocation shoggothResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/lessershoggoth.png");
	private static final ResourceLocation abyssalResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/abyssalshoggoth.png");
	private static final ResourceLocation dreadedResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/dreadedshoggoth.png");
	private static final ResourceLocation omotholResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/omotholshoggoth.png");
	private static final ResourceLocation darkResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/shadowshoggoth.png");

	public RenderLesserShoggoth ()
	{
		super(new ModelLesserShoggoth(), 1.6F);
		model = (ModelLesserShoggoth)mainModel;
	}

	public void doRender(EntityLesserShoggoth entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getShoggothTexture(EntityLesserShoggoth par1EntityLiving)
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

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return getShoggothTexture((EntityLesserShoggoth)entity);
	}
}
