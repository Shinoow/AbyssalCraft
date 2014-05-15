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

import com.shinoow.abyssalcraft.common.entity.EntityDepthsZombie;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderDepthsZombie extends RenderBiped
{
	protected ModelBiped model;

	public RenderDepthsZombie (ModelBiped ModelBiped, float f)
	{
		super(ModelBiped, f);
		model = ((ModelBiped)mainModel);
	}

	private static final ResourceLocation field_110862_k = new ResourceLocation("abyssalcraft:textures/model/depths_zombie.png");
	private static final ResourceLocation field_110861_l = new ResourceLocation("abyssalcraft:textures/model/depths_zombie_end.png");


	public void renderDepthsZombie(EntityDepthsZombie entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderDepthsZombie((EntityDepthsZombie)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderDepthsZombie((EntityDepthsZombie)par1Entity, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation func_110860_a(EntityDepthsZombie par1EntityLiving)
	{
		return par1EntityLiving.getZombieType() == 2 ? field_110861_l : field_110862_k;
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
	{
		return this.func_110860_a((EntityDepthsZombie)par1EntityLiving);
	}
}