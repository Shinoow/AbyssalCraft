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

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCreeper;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAntiCreeper extends RenderLiving {

	private static final ResourceLocation creeperTextures = new ResourceLocation("abyssalcraft:textures/model/anti/creeper.png");

	public RenderAntiCreeper(RenderManager manager)
	{
		super(manager, new ModelCreeper(), 0.5F);
	}

	protected void preRenderCallback(EntityAntiCreeper par1EntityAntiCreeper, float par2)
	{
		float f1 = par1EntityAntiCreeper.getCreeperFlashIntensity(par2);
		float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;

		if (f1 < 0.0F)
			f1 = 0.0F;

		if (f1 > 1.0F)
			f1 = 1.0F;

		f1 *= f1;
		f1 *= f1;
		float f3 = (1.0F + f1 * 0.4F) * f2;
		float f4 = (1.0F + f1 * 0.1F) / f2;
		GlStateManager.scale(f3, f4, f3);
	}

	protected int getColorMultiplier(EntityAntiCreeper par1EntityAntiCreeper, float par2, float par3)
	{
		float f2 = par1EntityAntiCreeper.getCreeperFlashIntensity(par3);

		if ((int)(f2 * 10.0F) % 2 == 0)
			return 0;
		else
		{
			int i = (int)(f2 * 0.2F * 255.0F);

			if (i < 0)
				i = 0;

			if (i > 255)
				i = 255;

			short short1 = 255;
			short short2 = 255;
			short short3 = 255;
			return i << 24 | short1 << 16 | short2 << 8 | short3;
		}
	}

	protected ResourceLocation getEntityTexture(EntityAntiCreeper par1EntityAntiCreeper)
	{
		return creeperTextures;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		this.preRenderCallback((EntityAntiCreeper)par1EntityLivingBase, par2);
	}

	@Override
	protected int getColorMultiplier(EntityLivingBase par1EntityLivingBase, float par2, float par3)
	{
		return this.getColorMultiplier((EntityAntiCreeper)par1EntityLivingBase, par2, par3);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTexture((EntityAntiCreeper)par1Entity);
	}
}
