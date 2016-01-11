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
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Renderdreadguard extends RenderBiped
{
	/** Scale of the model to use */
	private float scale = 1.5F;

	private static final ResourceLocation texture = new ResourceLocation("abyssalcraft:textures/model/elite/Dread_guard.png");

	private ModelBiped field_82434_o;
	protected ModelBiped field_82437_k;
	protected ModelBiped field_82435_l;
	protected ModelBiped field_82436_m;
	protected ModelBiped field_82433_n;

	public Renderdreadguard()
	{
		super(new ModelZombie(), 0.5F, 1.5F);
		field_82434_o = modelBipedMain;

	}

	@Override
	protected void func_82421_b()
	{
		field_82423_g = new ModelZombie(1.0F, true);
		field_82425_h = new ModelZombie(0.5F, true);
		field_82437_k = field_82423_g;
		field_82435_l = field_82425_h;
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityDreadguard par1Entitydreadguard, float par2)
	{
		GL11.glScalef(scale, scale, scale);
	}

	protected int shouldRenderPass(EntityDreadguard par1Entitydreadguard, int par2, float par3)
	{
		func_82427_a(par1Entitydreadguard);
		return super.shouldRenderPass(par1Entitydreadguard, par2, par3);
	}

	public void doRender(EntityDreadguard par1Entitydreadguard, double par2, double par4, double par6, float par8, float par9)
	{
		func_82427_a(par1Entitydreadguard);
		super.doRender(par1Entitydreadguard, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getTexture(EntityDreadguard par1Entitydreadguard)
	{
		return texture;
	}

	protected void renderEquippedItems(EntityDreadguard par1Entitydreadguard, float par2)
	{
		func_82427_a(par1Entitydreadguard);
		super.renderEquippedItems(par1Entitydreadguard, par2);
	}

	private void func_82427_a(EntityDreadguard par1Entitydreadguard)
	{
		{
			mainModel = field_82434_o;
			field_82423_g = field_82437_k;
			field_82425_h = field_82435_l;
		}

		modelBipedMain = (ModelBiped)mainModel;
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	{
		renderEquippedItems((EntityDreadguard)par1EntityLivingBase, par2);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		preRenderScale((EntityDreadguard)par1EntityLivingBase, par2);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return getTexture((EntityDreadguard)par1Entity);
	}

}
