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

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.entity.ModelAntiSkeleton;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSkeleton;

@SideOnly(Side.CLIENT)
public class RenderAntiSkeleton extends RenderBiped<EntityAntiSkeleton>
{
	private static final ResourceLocation skeletonTextures = new ResourceLocation("abyssalcraft:textures/model/anti/skeleton.png");

	public RenderAntiSkeleton(RenderManager manager)
	{
		super(manager, new ModelAntiSkeleton(), 0.5F);
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerBipedArmor(this)
		{
			@Override
			protected void initArmor()
			{
				field_177189_c = new ModelSkeleton(0.5F, true);
				field_177186_d = new ModelSkeleton(1.0F, true);
			}
		});
	}

	@Override
	public void transformHeldFull3DItemLayer()
	{
		GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAntiSkeleton par1EntityAntiSkeleton)
	{
		return skeletonTextures;
	}
}