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