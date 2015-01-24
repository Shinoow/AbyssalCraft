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