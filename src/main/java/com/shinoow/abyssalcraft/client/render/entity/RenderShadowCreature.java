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

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.client.model.entity.ModelShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShadowCreature extends RenderLiving {

	protected ModelShadowCreature model;

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/ShadowCreature.png");

	public RenderShadowCreature (ModelShadowCreature ModelShadowCreature, float f)
	{
		super(ModelShadowCreature, f);
		model = (ModelShadowCreature)mainModel;
	}

	public void doRender(EntityShadowCreature entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return mobTexture;
	}
}
