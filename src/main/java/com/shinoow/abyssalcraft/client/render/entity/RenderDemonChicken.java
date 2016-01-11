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

import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonChicken;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderDemonChicken extends RenderLiving
{
	private static final ResourceLocation chickenTextures = new ResourceLocation("abyssalcraft:textures/model/demon_chicken.png");

	public RenderDemonChicken(ModelBase model, float par2)
	{
		super(model, par2);
	}

	public void doRender(EntityDemonChicken entity, double par2, double par3, double par4, float par5, float par6)
	{
		super.doRender(entity, par2, par3, par4, par5, par6);
	}

	protected float handleRotationFloat(EntityDemonChicken entity, float par2)
	{
		float f1 = entity.field_70888_h + (entity.field_70886_e - entity.field_70888_h) * par2;
		float f2 = entity.field_70884_g + (entity.destPos - entity.field_70884_g) * par2;
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase entity, float par2)
	{
		return this.handleRotationFloat((EntityDemonChicken)entity, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return chickenTextures;
	}
}
