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

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.entity.ModelChagarothSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityLesserDreadbeast;

@SideOnly(Side.CLIENT)
public class RenderLesserDreadbeast extends RenderLiving<EntityLesserDreadbeast> {

	private float scale = 3.0F;

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/elite/Dread_guard.png");

	public RenderLesserDreadbeast(RenderManager manager)
	{
		super(manager, new ModelChagarothSpawn(), 0.5F);
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityLesserDreadbeast par1EntityEntityLesserDreadbeast, float par2)
	{
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	protected void preRenderCallback(EntityLesserDreadbeast par1EntityLivingBase, float par2)
	{
		preRenderScale(par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLesserDreadbeast entity) {

		return mobTexture;
	}
}