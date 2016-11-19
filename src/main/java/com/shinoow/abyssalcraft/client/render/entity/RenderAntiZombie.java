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

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiZombie;

@SideOnly(Side.CLIENT)
public class RenderAntiZombie extends RenderBiped<EntityAntiZombie>
{
	private static final ResourceLocation zombieTextures = new ResourceLocation("abyssalcraft:textures/model/anti/zombie.png");
	private ModelBiped modelBiped;
	private final List<LayerRenderer<EntityAntiZombie>> field_177122_o;

	public RenderAntiZombie(RenderManager manager)
	{
		super(manager, new ModelZombie(), 0.5F, 1.0F);
		modelBiped = modelBipedMain;
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

	@Override
	public void doRender(EntityAntiZombie par1EntityAntiZombie, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderModel(par1EntityAntiZombie);
		super.doRender(par1EntityAntiZombie, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAntiZombie par1EntityAntiZombie)
	{
		return zombieTextures;
	}

	private void renderModel(EntityAntiZombie par1EntityAntiZombie)
	{

		mainModel = modelBiped;
		layerRenderers = field_177122_o;

		modelBipedMain = (ModelBiped)mainModel;
	}
}