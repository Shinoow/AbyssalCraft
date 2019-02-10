/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityGreaterDreadSpawn;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGreaterDreadSpawn extends RenderLiving<EntityGreaterDreadSpawn> {

	private float scale = 2.0F;

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/elite/dread_guard.png");

	public RenderGreaterDreadSpawn(RenderManager manager)
	{
		super(manager, new ModelDreadSpawn(), 0.5F);
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityGreaterDreadSpawn par1EntityGreaterDreadSpawn, float par2)
	{
		GlStateManager.scale(scale, scale, scale);
	}

	@Override
	protected void preRenderCallback(EntityGreaterDreadSpawn par1EntityLivingBase, float par2)
	{
		preRenderScale(par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGreaterDreadSpawn entity) {

		return mobTexture;
	}
}
