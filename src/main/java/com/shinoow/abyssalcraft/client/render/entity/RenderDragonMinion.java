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
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.entity.ModelDragonMinion;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerSpectralDragonEyes;
import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;

@SideOnly(Side.CLIENT)
public class RenderDragonMinion extends RenderLiving<EntityDragonMinion> {

	/** Scale of the model to use */
	private float scale = 0.5F;

	private static final ResourceLocation field_110844_k = new ResourceLocation("abyssalcraft:textures/model/elite/dragonminion.png");

	/** An instance of the dragon model in RenderDragon */
	protected ModelDragonMinion modelDragon;

	public RenderDragonMinion(RenderManager manager)
	{
		super(manager, new ModelDragonMinion(0.0F), 0.3F);
		modelDragon = (ModelDragonMinion)mainModel;
		addLayer(new LayerSpectralDragonEyes(this));
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityDragonMinion par1EntityDragonMinion, float par2)
	{
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	protected void rotateCorpse(EntityDragonMinion par1EntityDragonMinion, float par2, float par3, float par4)
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

	@Override
	protected void renderModel(EntityDragonMinion par1EntityDragonMinion, float par2, float par3, float par4, float par5, float par6, float par7)
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

	protected ResourceLocation func_110841_a(EntityDragonMinion par1EntityDragonMinion)
	{
		return field_110844_k;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityDragonMinion par1EntityLivingBase, float par2)
	{
		preRenderScale(par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDragonMinion par1Entity)
	{
		return func_110841_a(par1Entity);
	}
}