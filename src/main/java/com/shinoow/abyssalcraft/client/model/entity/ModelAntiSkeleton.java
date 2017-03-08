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
package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSkeleton;

@SideOnly(Side.CLIENT)
public class ModelAntiSkeleton extends ModelZombie {

	public ModelAntiSkeleton()
	{
		this(0.0F);
	}

	public ModelAntiSkeleton(float par1)
	{
		super(par1, 0.0F, 64, 32);
		bipedRightArm = new ModelRenderer(this, 40, 16);
		bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, par1);
		bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		bipedLeftArm = new ModelRenderer(this, 40, 16);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, par1);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		bipedRightLeg = new ModelRenderer(this, 0, 16);
		bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, par1);
		bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		bipedLeftLeg = new ModelRenderer(this, 0, 16);
		bipedLeftLeg.mirror = true;
		bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, par1);
		bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		aimedBow = (EntityAntiSkeleton)par1EntityLivingBase != null;
		super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}
}
