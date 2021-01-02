/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.item;

import com.shinoow.abyssalcraft.common.entity.EntityCoraliumArrow;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCoraliumArrow extends RenderArrow<EntityCoraliumArrow>
{
	private static final ResourceLocation arrowTextures = new ResourceLocation("abyssalcraft:textures/model/corarrow.png");

	public RenderCoraliumArrow(RenderManager manager){
		super(manager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCoraliumArrow par1EntityArrow)
	{
		return arrowTextures;
	}
}
