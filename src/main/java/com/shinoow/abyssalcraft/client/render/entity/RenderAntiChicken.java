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

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiChicken;

@SideOnly(Side.CLIENT)
public class RenderAntiChicken extends RenderLiving
{
	private static final ResourceLocation chickenTextures = new ResourceLocation("abyssalcraft:textures/model/anti/chicken.png");

	public RenderAntiChicken(RenderManager manager)
	{
		super(manager, new ModelChicken(), 0.5F);
	}

	protected ResourceLocation getEntityTexture(EntityAntiChicken par1EntityAntiChicken)
	{
		return chickenTextures;
	}

	protected float handleRotationFloat(EntityAntiChicken par1EntityAntiChicken, float par2)
	{
		float f1 = par1EntityAntiChicken.field_70888_h + (par1EntityAntiChicken.field_70886_e - par1EntityAntiChicken.field_70888_h) * par2;
		float f2 = par1EntityAntiChicken.field_70884_g + (par1EntityAntiChicken.destPos - par1EntityAntiChicken.field_70884_g) * par2;
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2)
	{
		return this.handleRotationFloat((EntityAntiChicken)par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTexture((EntityAntiChicken)par1Entity);
	}
}