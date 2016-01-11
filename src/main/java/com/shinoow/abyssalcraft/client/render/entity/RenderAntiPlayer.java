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

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPlayer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAntiPlayer extends RenderBiped
{
	private static final ResourceLocation playerTexture = new ResourceLocation("abyssalcraft:textures/model/anti/steve.png");

	public RenderAntiPlayer()
	{
		super(new ModelBiped(), 0.5F);
	}

	public void doRender(EntityAntiPlayer par1EntityEntityAntiPlayer, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(par1EntityEntityAntiPlayer, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getEntityTexture(EntityAntiPlayer par1EntityCow)
	{
		return playerTexture;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTexture((EntityAntiPlayer)par1Entity);
	}
}
