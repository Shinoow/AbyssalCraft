/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiZombie;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAntiZombie extends RenderBiped<EntityAntiZombie>
{
	private static final ResourceLocation zombieTextures = new ResourceLocation("abyssalcraft:textures/model/anti/zombie.png");

	public RenderAntiZombie(RenderManager manager)
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

	@Override
	protected ResourceLocation getEntityTexture(EntityAntiZombie par1EntityAntiZombie)
	{
		return zombieTextures;
	}
}
