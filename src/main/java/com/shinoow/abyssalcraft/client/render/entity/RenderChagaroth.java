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
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.client.model.entity.ModelChagaroth;
import com.shinoow.abyssalcraft.common.entity.EntityChagaroth;

@SideOnly(Side.CLIENT)
public class RenderChagaroth extends RenderLiving<EntityChagaroth> {

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/boss/Chagaroth.png");

	public RenderChagaroth(RenderManager manager)
	{
		super(manager, new ModelChagaroth(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityChagaroth entity) {

		return mobTexture;
	}
}