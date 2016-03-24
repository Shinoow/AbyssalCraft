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

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;

@SideOnly(Side.CLIENT)
public class Renderdreadguard extends RenderBiped<EntityDreadguard>
{
	/** Scale of the model to use */
	private float scale = 1.5F;

	private static final ResourceLocation texture = new ResourceLocation("abyssalcraft:textures/model/elite/Dread_guard.png");

	private ModelBiped field_82434_o;
	private final List<LayerRenderer<EntityDreadguard>> field_177122_o;

	public Renderdreadguard(RenderManager manager)
	{
		super(manager, new ModelZombie(), 0.5F, 1.5F);
		field_82434_o = modelBipedMain;
		this.addLayer(new LayerHeldItem(this));
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
		field_177122_o = Lists.newArrayList(layerRenderers);
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityDreadguard par1Entitydreadguard, float par2)
	{
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	public void doRender(EntityDreadguard par1Entitydreadguard, double par2, double par4, double par6, float par8, float par9)
	{
		func_82427_a(par1Entitydreadguard);
		super.doRender(par1Entitydreadguard, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDreadguard par1Entitydreadguard)
	{
		return texture;
	}

	private void func_82427_a(EntityDreadguard par1Entitydreadguard)
	{
		mainModel = field_82434_o;
		layerRenderers = field_177122_o;

		modelBipedMain = (ModelBiped)mainModel;
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