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
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiAbyssalZombie;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAntiAbyssalZombie extends RenderBiped {

	protected ModelBiped model;

	public RenderAntiAbyssalZombie()
	{
		super(new ModelBiped(), 0.5F);
		model = (ModelBiped)mainModel;
	}

	private static final ResourceLocation zombieTexture = new ResourceLocation("abyssalcraft:textures/model/anti/abyssal_zombie.png");

	public void doRender(EntityAntiAbyssalZombie entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getZombieTexture(EntityAntiAbyssalZombie par1EntityLiving)
	{
		return zombieTexture;
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
	{
		return getZombieTexture((EntityAntiAbyssalZombie)par1EntityLiving);
	}
}
