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

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAntiCow extends RenderLiving
{
	private static final ResourceLocation cowTextures = new ResourceLocation("abyssalcraft:textures/model/anti/cow.png");

	public RenderAntiCow()
	{
		super(new ModelCow(), 0.5F);
	}

	public void doRender(EntityAntiCow par1EntityEntityAntiCow, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(par1EntityEntityAntiCow, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getEntityTexture(EntityAntiCow par1EntityCow)
	{
		return cowTextures;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTexture((EntityAntiCow)par1Entity);
	}
}
