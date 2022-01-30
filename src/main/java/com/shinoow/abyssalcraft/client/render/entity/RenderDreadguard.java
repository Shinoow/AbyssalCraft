/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDreadguard extends RenderBiped<EntityDreadguard>
{
	/** Scale of the model to use */
	private float scale = 1.5F;

	private static final ResourceLocation texture = new ResourceLocation("abyssalcraft:textures/model/elite/dread_guard.png");

	public RenderDreadguard(RenderManager manager)
	{
		super(manager, new ModelZombie(), 0.5F);
		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
		{
			@Override
			protected void initArmor()
			{
				modelLeggings = new ModelZombie(0.5F, true);
				modelArmor = new ModelZombie(1.0F, true);
			}
		};
		this.addLayer(layerbipedarmor);
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityDreadguard par1Entitydreadguard, float par2)
	{
		GlStateManager.scale(scale, scale, scale);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDreadguard par1Entitydreadguard)
	{
		return texture;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityDreadguard par1EntityLivingBase, float par2)
	{
		preRenderScale(par1EntityLivingBase, par2);
	}
}
