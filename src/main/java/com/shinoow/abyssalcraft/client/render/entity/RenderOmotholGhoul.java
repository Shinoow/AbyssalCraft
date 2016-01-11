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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.entity.ModelDG;
import com.shinoow.abyssalcraft.common.entity.EntityOmotholGhoul;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderOmotholGhoul extends RenderLiving {

	private float scale = 1.2F;

	protected ModelDG model;

	private static final ResourceLocation ghoulResource = new ResourceLocation("abyssalcraft:textures/model/omothol_ghoul.png");

	public RenderOmotholGhoul ()
	{
		super(new ModelDG(), 0.8F);
		model = (ModelDG)mainModel;
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityOmotholGhoul par1EntityOmotholGhoul, float par2)
	{
		GL11.glScalef(scale, scale, scale);
	}

	public void doRender(EntityOmotholGhoul entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getGhoulTexture(EntityOmotholGhoul par1EntityLiving)
	{
		return ghoulResource;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		preRenderScale((EntityOmotholGhoul)par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return getGhoulTexture((EntityOmotholGhoul)entity);
	}
}
