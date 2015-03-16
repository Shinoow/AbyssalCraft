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

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.client.model.entity.ModelDG;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLesserShoggoth extends RenderLiving {

	protected ModelDG model;

	private static final ResourceLocation shoggothResource = new ResourceLocation("abyssalcraft:textures/model/depths_ghoul.png");
	private static final ResourceLocation abyssalResource = new ResourceLocation("abyssalcraft:textures/model/depths_ghoul_pete.png");
	private static final ResourceLocation dreadedResource = new ResourceLocation("abyssalcraft:textures/model/depths_ghoul_wilson.png");
	private static final ResourceLocation omotholResource = new ResourceLocation("abyssalcraft:textures/model/depths_ghoul_orange.png");
	private static final ResourceLocation darkResource = new ResourceLocation("abyssalcraft:textures/model/omothol_ghoul.png");

	public RenderLesserShoggoth ()
	{
		super(new ModelDG(), 0.8F);
		model = (ModelDG)mainModel;
	}

	public void doRender(EntityLesserShoggoth entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getGhoulTexture(EntityLesserShoggoth par1EntityLiving)
	{
		switch (par1EntityLiving.getShoggothType())
		{
		case 0:
			return shoggothResource;
		case 1:
			return abyssalResource;
		case 2:
			return dreadedResource;
		case 3:
			return omotholResource;
		case 4:
			return darkResource;
		default:
			return shoggothResource;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return getGhoulTexture((EntityLesserShoggoth)entity);
	}
}