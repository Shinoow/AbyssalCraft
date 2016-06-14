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
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;

@SideOnly(Side.CLIENT)
public class RenderAbyssalZombie extends RenderBiped<EntityAbyssalZombie> {

	private static final ResourceLocation zombieTexture = new ResourceLocation("abyssalcraft:textures/model/abyssal_zombie.png");
	private static final ResourceLocation zombieTexture_end = new ResourceLocation("abyssalcraft:textures/model/abyssal_zombie_end.png");

	public RenderAbyssalZombie(RenderManager manager)
	{
		super(manager, new ModelBiped(), 0.5F);
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerBipedArmor(this)
		{
			@Override
			protected void initArmor()
			{
				field_177189_c = new ModelBiped(0.5F);
				field_177186_d = new ModelBiped(1.0F);
			}
		});
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAbyssalZombie par1EntityLiving)
	{
		return par1EntityLiving.getZombieType() == 2 ? zombieTexture_end : zombieTexture;
	}
}