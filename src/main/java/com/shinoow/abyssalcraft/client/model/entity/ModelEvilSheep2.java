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
package com.shinoow.abyssalcraft.client.model.entity;

import com.shinoow.abyssalcraft.common.entity.demon.EntityEvilSheep;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEvilSheep2 extends ModelQuadruped
{
	private float headRotationAngleX;

	public ModelEvilSheep2()
	{
		super(12, 0.0F);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
		head.setRotationPoint(0.0F, 6.0F, -8.0F);
		body = new ModelRenderer(this, 28, 8);
		body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
		body.setRotationPoint(0.0F, 5.0F, 2.0F);
	}

	/**
	 * Used for easily adding entity-dependent animations. The second and third float params here are the same second
	 * and third as in the setRotationAngles method.
	 */
	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime)
	{
		super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
		head.rotationPointY = 6.0F + ((EntityEvilSheep)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
		headRotationAngleX = ((EntityEvilSheep)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entityIn)
	{
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entityIn);
		head.rotateAngleX = headRotationAngleX;
	}
}
