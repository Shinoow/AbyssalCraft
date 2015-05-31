/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.entity.ModelAntiSkeleton;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSkeleton;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAntiSkeleton extends RenderBiped
{
	private static final ResourceLocation skeletonTextures = new ResourceLocation("abyssalcraft:textures/model/anti/skeleton.png");


	public RenderAntiSkeleton()
	{
		super(new ModelAntiSkeleton(), 0.5F);
	}

	@Override
	protected void func_82422_c()
	{
		GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
	}

	protected ResourceLocation getEntityTexture(EntityAntiSkeleton par1EntityAntiSkeleton)
	{
		return skeletonTextures;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
	{
		return this.getEntityTexture((EntityAntiSkeleton)par1EntityLiving);
	}


	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTexture((EntityAntiSkeleton)par1Entity);
	}
}
