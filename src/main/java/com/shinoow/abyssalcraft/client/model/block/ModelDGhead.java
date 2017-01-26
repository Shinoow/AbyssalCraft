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
package com.shinoow.abyssalcraft.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDGhead extends ModelBase
{

	ModelRenderer Head;
	ModelRenderer Jaw;
	ModelRenderer Tooth1;
	ModelRenderer Tooth2;
	ModelRenderer Tooth3;
	ModelRenderer Tooth4;
	ModelRenderer Tooth5;

	public ModelDGhead()
	{
		textureWidth = 128;
		textureHeight = 64;

		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-5F, -9F, -5F, 9, 9, 9);
		Head.setRotationPoint(1F, 22F, 1F);
		Head.setTextureSize(128, 64);
		Head.mirror = true;
		setRotation(Head, -0.2974289F, 0F, 0F);
		Jaw = new ModelRenderer(this, 36, 0);
		Jaw.addBox(-4F, 0F, -9F, 9, 1, 9);
		Jaw.setRotationPoint(0F, 23F, 5F);
		Jaw.setTextureSize(128, 64);
		Jaw.mirror = true;
		setRotation(Jaw, 0F, 0F, 0F);
		Tooth1 = new ModelRenderer(this, 48, 11);
		Tooth1.addBox(0F, -2F, 0F, 1, 2, 1);
		Tooth1.setRotationPoint(-4F, 23F, -4F);
		Tooth1.setTextureSize(128, 64);
		Tooth1.mirror = true;
		setRotation(Tooth1, 0F, 0F, 0F);
		Tooth2 = new ModelRenderer(this, 48, 11);
		Tooth2.addBox(0F, -2F, 0F, 1, 2, 1);
		Tooth2.setRotationPoint(-2F, 23F, -4F);
		Tooth2.setTextureSize(128, 64);
		Tooth2.mirror = true;
		setRotation(Tooth2, 0F, 0F, 0F);
		Tooth3 = new ModelRenderer(this, 48, 11);
		Tooth3.addBox(0F, -2F, 0F, 1, 2, 1);
		Tooth3.setRotationPoint(0F, 23F, -4F);
		Tooth3.setTextureSize(128, 64);
		Tooth3.mirror = true;
		setRotation(Tooth3, 0F, 0F, 0F);
		Tooth4 = new ModelRenderer(this, 48, 11);
		Tooth4.addBox(0F, -2F, 0F, 1, 2, 1);
		Tooth4.setRotationPoint(2F, 23F, -4F);
		Tooth4.setTextureSize(128, 64);
		Tooth4.mirror = true;
		setRotation(Tooth4, 0F, 0F, 0F);
		Tooth5 = new ModelRenderer(this, 48, 11);
		Tooth5.addBox(0F, -2F, 0F, 1, 2, 1);
		Tooth5.setRotationPoint(4F, 23F, -4F);
		Tooth5.setTextureSize(128, 64);
		Tooth5.mirror = true;
		setRotation(Tooth5, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Head.render(f5);
		Jaw.render(f5);
		Tooth1.render(f5);
		Tooth2.render(f5);
		Tooth3.render(f5);
		Tooth4.render(f5);
		Tooth5.render(f5);
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
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		Head.rotateAngleY = par4 / (180F / (float)Math.PI);

		Jaw.rotateAngleY = par4 / (180F / (float)Math.PI);
		Jaw.rotateAngleX = par5 / (180F / (float)Math.PI);

		Tooth1.rotateAngleY = par4 / (180F / (float)Math.PI);
		Tooth1.rotateAngleX = par5 / (180F / (float)Math.PI);

		Tooth2.rotateAngleY = par4 / (180F / (float)Math.PI);
		Tooth2.rotateAngleX = par5 / (180F / (float)Math.PI);

		Tooth3.rotateAngleY = par4 / (180F / (float)Math.PI);
		Tooth3.rotateAngleX = par5 / (180F / (float)Math.PI);

		Tooth4.rotateAngleY = par4 / (180F / (float)Math.PI);
		Tooth4.rotateAngleX = par5 / (180F / (float)Math.PI);

		Tooth5.rotateAngleY = par4 / (180F / (float)Math.PI);
		Tooth5.rotateAngleX = par5 / (180F / (float)Math.PI);
	}

}
