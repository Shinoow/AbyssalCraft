/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.common.entity.demon.EntityEvilChicken;

@SideOnly(Side.CLIENT)
public class RenderEvilChicken extends RenderLiving
{
	private static final ResourceLocation chickenTextures = new ResourceLocation("textures/entity/chicken.png");

	public RenderEvilChicken(RenderManager manager)
	{
		super(manager, new ModelChicken(), 0.5F);
	}

	protected float handleRotationFloat(EntityEvilChicken entity, float par2)
	{
		float f1 = entity.field_70888_h + (entity.field_70886_e - entity.field_70888_h) * par2;
		float f2 = entity.field_70884_g + (entity.destPos - entity.field_70884_g) * par2;
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase entity, float par2)
	{
		return this.handleRotationFloat((EntityEvilChicken)entity, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return chickenTextures;
	}
}
