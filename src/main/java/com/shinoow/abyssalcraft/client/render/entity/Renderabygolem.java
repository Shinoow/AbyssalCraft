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

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.common.entity.EntityAbygolem;

@SideOnly(Side.CLIENT)
public class Renderabygolem extends RenderLiving {

	protected ModelBiped model;

	private static final ResourceLocation field_110865_p = new ResourceLocation("abyssalcraft:textures/model/aby_warden.png");

	public Renderabygolem (RenderManager manager, ModelBiped ModelBiped, float f)
	{
		super(manager, ModelBiped, f);
		model = (ModelBiped)mainModel;
	}

	public void doRender(EntityAbygolem entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return field_110865_p;
	}
}