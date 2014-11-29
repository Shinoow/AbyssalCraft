/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.*;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiChicken;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderAntiChicken extends RenderLiving
{
	private static final ResourceLocation chickenTextures = new ResourceLocation("abyssalcraft:textures/model/anti/chicken.png");

	public RenderAntiChicken()
	{
		super(new ModelChicken(), 0.5F);
	}

	public void doRender(EntityAntiChicken par1EntityAntiChicken, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(par1EntityAntiChicken, par2, par4, par6, par8, par9);
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