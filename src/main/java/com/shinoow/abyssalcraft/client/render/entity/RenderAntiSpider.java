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

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSpider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAntiSpider extends RenderLiving
{
	private static final ResourceLocation spiderEyesTextures = new ResourceLocation("abyssalcraft:textures/model/anti/spider_eyes.png");
	private static final ResourceLocation spiderTextures = new ResourceLocation("abyssalcraft:textures/model/anti/spider.png");

	public RenderAntiSpider()
	{
		super(new ModelSpider(), 1.0F);
		setRenderPassModel(new ModelSpider());
	}

	protected float getDeathMaxRotation(EntityAntiSpider par1EntityAntiSpider)
	{
		return 180.0F;
	}

	protected int shouldRenderPass(EntityAntiSpider par1EntityAntiSpider, int par2, float par3)
	{
		if (par2 != 0)
			return -1;
		else
		{
			bindTexture(spiderEyesTextures);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

			if (par1EntityAntiSpider.isInvisible())
				GL11.glDepthMask(false);
			else
				GL11.glDepthMask(true);

			char c0 = 61680;
			int j = c0 % 65536;
			int k = c0 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			return 1;
		}
	}

	protected ResourceLocation getEntityTexture(EntityAntiSpider par1EntityAntiSpider)
	{
		return spiderTextures;
	}

	@Override
	protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase)
	{
		return this.getDeathMaxRotation((EntityAntiSpider)par1EntityLivingBase);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return this.shouldRenderPass((EntityAntiSpider)par1EntityLivingBase, par2, par3);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTexture((EntityAntiSpider)par1Entity);
	}
}
