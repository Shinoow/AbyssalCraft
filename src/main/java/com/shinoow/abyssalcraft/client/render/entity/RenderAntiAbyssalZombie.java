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

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiAbyssalZombie;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAntiAbyssalZombie extends RenderBiped<EntityAntiAbyssalZombie> {

	private static final ResourceLocation zombieTexture = new ResourceLocation("abyssalcraft:textures/model/anti/abyssal_zombie.png");
	private static final ResourceLocation zombieTextureAlt = new ResourceLocation("abyssalcraft:textures/model/anti/abyssal_zombie_old.png");

	public RenderAntiAbyssalZombie(RenderManager manager)
	{
		super(manager, new ModelZombie(0.0F, true), 0.5F);
		addLayer(new LayerBipedArmor(this)
		{
			@Override
			protected void initArmor()
			{
				modelLeggings = new ModelZombie(0.5F, true);
				modelArmor = new ModelZombie(1.0F, true);
			}
		});
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAntiAbyssalZombie par1EntityLiving)
	{
		return par1EntityLiving.getName().equalsIgnoreCase("woonihs") ? zombieTextureAlt : zombieTexture;
	}
}
