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
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAbyssalniteGolem extends RenderBiped {

	private static final ResourceLocation field_110865_p = new ResourceLocation("abyssalcraft:textures/model/aby_warden.png");

	public RenderAbyssalniteGolem (RenderManager manager)
	{
		super(manager, new ModelBiped(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {

		return field_110865_p;
	}
}