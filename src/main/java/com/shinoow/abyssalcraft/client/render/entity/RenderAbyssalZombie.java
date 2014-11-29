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

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderAbyssalZombie extends RenderBiped {

	protected ModelBiped model;

	public RenderAbyssalZombie (ModelBiped ModelBiped, float f)
	{
		super(ModelBiped, f);
		model = (ModelBiped)mainModel;
	}

	private static final ResourceLocation zombieTexture = new ResourceLocation("abyssalcraft:textures/model/abyssal_zombie.png");
	private static final ResourceLocation zombieTexture_end = new ResourceLocation("abyssalcraft:textures/model/abyssal_zombie_end.png");

	public void doRender(EntityAbyssalZombie entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getZombieTexture(EntityAbyssalZombie par1EntityLiving)
	{
		return par1EntityLiving.getZombieType() == 2 ? zombieTexture_end : zombieTexture;
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
	{
		return getZombieTexture((EntityAbyssalZombie)par1EntityLiving);
	}
}