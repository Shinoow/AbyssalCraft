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

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.entity.ModelDragonMinion;
import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDragonMinion extends RenderLiving {

	/** Scale of the model to use */
	private float scale = 0.5F;

	private static final ResourceLocation field_110845_h = new ResourceLocation("abyssalcraft:textures/model/elite/dragonminion_eyes.png");
	private static final ResourceLocation field_110844_k = new ResourceLocation("abyssalcraft:textures/model/elite/dragonminion.png");

	/** An instance of the dragon model in RenderDragon */
	protected ModelDragonMinion modelDragon;

	public RenderDragonMinion()
	{
		super(new ModelDragonMinion(0.0F), 0.3F);
		modelDragon = (ModelDragonMinion)mainModel;
		setRenderPassModel(mainModel);
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityDragonMinion par1EntityDragonMinion, float par2)
	{
		GL11.glScalef(scale, scale, scale);
	}

	/**
	 * Used to rotate the dragon as a whole in RenderDragon. It's called in the rotateCorpse method.
	 */
	protected void rotateDragonBody(EntityDragonMinion par1EntityDragonMinion, float par2, float par3, float par4)
	{
		float f3 = (float)par1EntityDragonMinion.getMovementOffsets(7, par4)[0];
		float f4 = (float)(par1EntityDragonMinion.getMovementOffsets(5, par4)[1] - par1EntityDragonMinion.getMovementOffsets(10, par4)[1]);
		GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(f4 * 10.0F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, 0.0F, 1.0F);

		if (par1EntityDragonMinion.deathTime > 0)
		{
			float f5 = (par1EntityDragonMinion.deathTime + par4 - 1.0F) / 20.0F * 1.6F;
			f5 = MathHelper.sqrt_float(f5);

			if (f5 > 1.0F)
				f5 = 1.0F;

			GL11.glRotatef(f5 * getDeathMaxRotation(par1EntityDragonMinion), 0.0F, 0.0F, 1.0F);
		}
	}

	/**
	 * Renders the dragon model. Called by renderModel.
	 */
	protected void renderDragonModel(EntityDragonMinion par1EntityDragonMinion, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		bindEntityTexture(par1EntityDragonMinion);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		mainModel.render(par1EntityDragonMinion, par2, par3, par4, par5, par6, par7);
		GL11.glDisable(GL11.GL_BLEND);

		if (par1EntityDragonMinion.hurtTime > 0)
		{
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
			mainModel.render(par1EntityDragonMinion, par2, par3, par4, par5, par6, par7);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}
	}

	/**
	 * Renders the dragon, along with its dying animation
	 */
	public void renderDragon(EntityDragonMinion par1EntityDragonMinion, double par2, double par4, double par6, float par8, float par9)
	{

		super.doRender(par1EntityDragonMinion, par2, par4, par6, par8, par9);


	}

	protected ResourceLocation func_110841_a(EntityDragonMinion par1EntityDragonMinion)
	{
		return field_110844_k;
	}

	/**
	 * Renders the overlay for glowing eyes and the mouth. Called by shouldRenderPass.
	 */
	protected int renderGlow(EntityDragonMinion par1EntityDragonMinion, int par2, float par3)
	{
		if (par2 == 1)
			GL11.glDepthFunc(GL11.GL_LEQUAL);

		if (par2 != 0)
			return -1;
		else
		{
			bindTexture(field_110845_h);
			float f1 = 1.0F;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_EQUAL);
			char c0 = 61680;
			int j = c0 % 65536;
			int k = c0 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
			return 1;
		}
	}

	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderDragon((EntityDragonMinion)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return renderGlow((EntityDragonMinion)par1EntityLivingBase, par2, par3);
	}

	@Override
	protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		rotateDragonBody((EntityDragonMinion)par1EntityLivingBase, par2, par3, par4);
	}

	/**
	 * Renders the model in RenderLiving
	 */
	@Override
	protected void renderModel(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		renderDragonModel((EntityDragonMinion)par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
	}

	public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
	{
		renderDragon((EntityDragonMinion)par1EntityLivingBase, par2, par4, par6, par8, par9);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		preRenderScale((EntityDragonMinion)par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return func_110841_a((EntityDragonMinion)par1Entity);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderDragon((EntityDragonMinion)par1Entity, par2, par4, par6, par8, par9);
	}

}
