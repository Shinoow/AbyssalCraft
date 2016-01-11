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
package com.shinoow.abyssalcraft.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRitualAltar extends ModelBase {

	ModelRenderer bottom1;
	ModelRenderer bottom2;
	ModelRenderer base1;
	ModelRenderer base2;
	ModelRenderer side1;
	ModelRenderer side2;
	ModelRenderer side3;
	ModelRenderer side4;
	ModelRenderer top1;
	ModelRenderer top2;
	ModelRenderer Shape4;
	ModelRenderer candle1;
	ModelRenderer candle2;
	ModelRenderer candle3;
	ModelRenderer candle4;

	public ModelRitualAltar()
	{
		textureWidth = 64;
		textureHeight = 32;

		bottom1 = new ModelRenderer(this, 0, 0);
		bottom1.addBox(0F, 0F, 0F, 11, 2, 11);
		bottom1.setRotationPoint(-5.5F, 22F, -5.5F);
		bottom1.setTextureSize(64, 32);
		bottom1.mirror = true;
		setRotation(bottom1, 0F, 0F, 0F);
		bottom2 = new ModelRenderer(this, 24, 13);
		bottom2.addBox(0F, 0F, 0F, 10, 1, 10);
		bottom2.setRotationPoint(-5F, 21F, -5F);
		bottom2.setTextureSize(64, 32);
		bottom2.mirror = true;
		setRotation(bottom2, 0F, 0F, 0F);
		base1 = new ModelRenderer(this, 0, 13);
		base1.addBox(0F, 0F, 0F, 6, 8, 6);
		base1.setRotationPoint(-3F, 13F, -3F);
		base1.setTextureSize(64, 32);
		base1.mirror = true;
		setRotation(base1, 0F, 0F, 0F);
		base2 = new ModelRenderer(this, 37, 0);
		base2.addBox(0F, 0F, 0F, 7, 2, 7);
		base2.setRotationPoint(-3.5F, 16F, -3.5F);
		base2.setTextureSize(64, 32);
		base2.mirror = true;
		setRotation(base2, 0F, 0F, 0F);
		side1 = new ModelRenderer(this, 4, 0);
		side1.addBox(0F, 0F, 0F, 1, 8, 1);
		side1.setRotationPoint(-4F, 13F, -4F);
		side1.setTextureSize(64, 32);
		side1.mirror = true;
		setRotation(side1, 0F, 0F, 0F);
		side2 = new ModelRenderer(this, 4, 0);
		side2.addBox(0F, 0F, 0F, 1, 8, 1);
		side2.setRotationPoint(-4F, 13F, 3F);
		side2.setTextureSize(64, 32);
		side2.mirror = true;
		setRotation(side2, 0F, 0F, 0F);
		side3 = new ModelRenderer(this, 4, 0);
		side3.addBox(0F, 0F, 0F, 1, 8, 1);
		side3.setRotationPoint(3F, 13F, 3F);
		side3.setTextureSize(64, 32);
		side3.mirror = true;
		setRotation(side3, 0F, 0F, 0F);
		side4 = new ModelRenderer(this, 4, 0);
		side4.addBox(0F, 0F, 0F, 1, 8, 1);
		side4.setRotationPoint(3F, 13F, -4F);
		side4.setTextureSize(64, 32);
		side4.mirror = true;
		setRotation(side4, 0F, 0F, 0F);
		top1 = new ModelRenderer(this, 24, 13);
		top1.addBox(0F, 0F, 0F, 10, 1, 10);
		top1.setRotationPoint(-5F, 12F, -5F);
		top1.setTextureSize(64, 32);
		top1.mirror = true;
		setRotation(top1, 0F, 0F, 0F);
		top2 = new ModelRenderer(this, 0, 0);
		top2.addBox(0F, 0F, 0F, 11, 1, 11);
		top2.setRotationPoint(-5.5F, 11F, -5.5F);
		top2.setTextureSize(64, 32);
		top2.mirror = true;
		setRotation(top2, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 24, 24);
		Shape4.addBox(0F, 0F, 0F, 6, 1, 6);
		Shape4.setRotationPoint(-3F, 10F, -3F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		candle1 = new ModelRenderer(this, 0, 0);
		candle1.addBox(0F, 0F, 0F, 1, 3, 1);
		candle1.setRotationPoint(-4.5F, 8F, -4.5F);
		candle1.setTextureSize(64, 32);
		candle1.mirror = true;
		setRotation(candle1, 0F, 0F, 0F);
		candle2 = new ModelRenderer(this, 0, 0);
		candle2.addBox(0F, 0F, 0F, 1, 3, 1);
		candle2.setRotationPoint(-4.5F, 8F, 3.5F);
		candle2.setTextureSize(64, 32);
		candle2.mirror = true;
		setRotation(candle2, 0F, 0F, 0F);
		candle3 = new ModelRenderer(this, 0, 0);
		candle3.addBox(0F, 0F, 0F, 1, 3, 1);
		candle3.setRotationPoint(3.5F, 8F, 3.5F);
		candle3.setTextureSize(64, 32);
		candle3.mirror = true;
		setRotation(candle3, 0F, 0F, 0F);
		candle4 = new ModelRenderer(this, 0, 0);
		candle4.addBox(0F, 0F, 0F, 1, 3, 1);
		candle4.setRotationPoint(3.5F, 8F, -4.5F);
		candle4.setTextureSize(64, 32);
		candle4.mirror = true;
		setRotation(candle4, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		bottom1.render(f5);
		bottom2.render(f5);
		base1.render(f5);
		base2.render(f5);
		side1.render(f5);
		side2.render(f5);
		side3.render(f5);
		side4.render(f5);
		top1.render(f5);
		top2.render(f5);
		Shape4.render(f5);
		candle1.render(f5);
		candle2.render(f5);
		candle3.render(f5);
		candle4.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{

	}
}
