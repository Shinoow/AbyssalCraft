/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelShadowCreature extends ModelBase
{

	public ModelRenderer Body;
	public ModelRenderer Head1;
	public ModelRenderer Tail1;
	public ModelRenderer Tail2;
	public ModelRenderer Tail3;
	public ModelRenderer Tail4;
	public ModelRenderer Tail5;
	public ModelRenderer Tail6;
	public ModelRenderer Tail7;
	public ModelRenderer LeftArm1;
	public ModelRenderer RightArm1;
	public ModelRenderer LeftArm2;
	public ModelRenderer RightArm2;

	public ModelShadowCreature()
	{
		textureWidth = 32;
		textureHeight = 32;

		Body = new ModelRenderer(this, 12, 22);
		Body.addBox(0F, 0F, 0F, 3, 3, 7);
		Body.setRotationPoint(-3F, 12F, -1F);
		Body.setTextureSize(32, 32);
		Body.mirror = true;
		setRotation(Body, 0F, 0F, 0F);
		Head1 = new ModelRenderer(this, 0, 0);
		Head1.addBox(-3F, -5F, -3F, 5, 5, 5);
		Head1.setRotationPoint(-1F, 12F, 0F);
		Head1.setTextureSize(32, 32);
		Head1.mirror = true;
		setRotation(Head1, 0F, 0F, 0F);
		Tail1 = new ModelRenderer(this, 18, 15);
		Tail1.addBox(0F, 0F, 0F, 2, 2, 5);
		Tail1.setRotationPoint(-2.466667F, 12.5F, 6F);
		Tail1.setTextureSize(32, 32);
		Tail1.mirror = true;
		setRotation(Tail1, -0.3717861F, 0F, 0F);
		Tail2 = new ModelRenderer(this, 26, 12);
		Tail2.addBox(0F, 0F, 0F, 1, 1, 2);
		Tail2.setRotationPoint(-2F, 14F, 9F);
		Tail2.setTextureSize(32, 32);
		Tail2.mirror = true;
		setRotation(Tail2, 1.115358F, 0F, 0F);
		Tail3 = new ModelRenderer(this, 23, 7);
		Tail3.addBox(0F, 0F, 0F, 3, 1, 1);
		Tail3.setRotationPoint(-1F, 14.5F, 9F);
		Tail3.setTextureSize(32, 32);
		Tail3.mirror = true;
		setRotation(Tail3, -0.4089647F, 0F, 0F);
		Tail4 = new ModelRenderer(this, 23, 7);
		Tail4.addBox(0F, 0F, 0F, 3, 1, 1);
		Tail4.setRotationPoint(-1F, 13.5F, 7F);
		Tail4.setTextureSize(32, 32);
		Tail4.mirror = true;
		setRotation(Tail4, -0.4089647F, 0F, 0F);
		Tail5 = new ModelRenderer(this, 23, 7);
		Tail5.addBox(0F, 0F, 0F, 3, 1, 1);
		Tail5.setRotationPoint(-5F, 14.5F, 9F);
		Tail5.setTextureSize(32, 32);
		Tail5.mirror = true;
		setRotation(Tail5, -0.4089647F, 0F, 0F);
		Tail6 = new ModelRenderer(this, 23, 7);
		Tail6.addBox(0F, 0F, 0F, 3, 1, 1);
		Tail6.setRotationPoint(-5F, 13.5F, 7F);
		Tail6.setTextureSize(32, 32);
		Tail6.mirror = true;
		setRotation(Tail6, -0.4089647F, 0F, 0F);
		Tail7 = new ModelRenderer(this, 26, 12);
		Tail7.addBox(0F, 0F, 0F, 1, 1, 2);
		Tail7.setRotationPoint(-2F, 13F, 7F);
		Tail7.setTextureSize(32, 32);
		Tail7.mirror = true;
		setRotation(Tail7, 1.115358F, 0F, 0F);
		LeftArm1 = new ModelRenderer(this, 11, 19);
		LeftArm1.addBox(0F, 0F, 0F, 2, 1, 1);
		LeftArm1.setRotationPoint(0F, 13F, 0F);
		LeftArm1.setTextureSize(32, 32);
		LeftArm1.mirror = true;
		setRotation(LeftArm1, 0F, 0F, 0F);
		RightArm1 = new ModelRenderer(this, 11, 19);
		RightArm1.addBox(0F, 0F, 0F, 2, 1, 1);
		RightArm1.setRotationPoint(-5F, 13F, 0F);
		RightArm1.setTextureSize(32, 32);
		RightArm1.mirror = true;
		setRotation(RightArm1, 0F, 0F, 0F);
		LeftArm2 = new ModelRenderer(this, 0, 22);
		LeftArm2.addBox(0F, 0F, -5F, 1, 1, 5);
		LeftArm2.setRotationPoint(1F, 13F, 0F);
		LeftArm2.setTextureSize(32, 32);
		LeftArm2.mirror = true;
		setRotation(LeftArm2, 0F, 0F, 0F);
		RightArm2 = new ModelRenderer(this, 0, 22);
		RightArm2.addBox(0F, 0F, -5F, 1, 1, 5);
		RightArm2.setRotationPoint(-5F, 13F, 0F);
		RightArm2.setTextureSize(32, 32);
		RightArm2.mirror = true;
		setRotation(RightArm2, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Body.render(f5);
		Head1.render(f5);
		Tail1.render(f5);
		Tail2.render(f5);
		Tail3.render(f5);
		Tail4.render(f5);
		Tail5.render(f5);
		Tail6.render(f5);
		Tail7.render(f5);
		LeftArm1.render(f5);
		RightArm1.render(f5);
		LeftArm2.render(f5);
		RightArm2.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		Head1.rotateAngleY = par4 / (180F / (float)Math.PI);
		Head1.rotateAngleX = par5 / (180F / (float)Math.PI);

		RightArm2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
		LeftArm2.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;

		RightArm2.rotateAngleZ = 0.0F;
		LeftArm2.rotateAngleZ = 0.0F;
	}

}
