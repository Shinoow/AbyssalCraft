/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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