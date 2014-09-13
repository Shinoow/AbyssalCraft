/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCow;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderAntiCow extends RenderLiving
{
	private static final ResourceLocation cowTextures = new ResourceLocation("abyssalcraft:textures/model/anti/cow.png");

	public RenderAntiCow()
	{
		super(new ModelCow(), 0.5F);
	}

	public void doRender(EntityAntiCow par1EntityEntityAntiCow, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(par1EntityEntityAntiCow, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getEntityTexture(EntityAntiCow par1EntityCow)
	{
		return cowTextures;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTexture((EntityAntiCow)par1Entity);
	}
}