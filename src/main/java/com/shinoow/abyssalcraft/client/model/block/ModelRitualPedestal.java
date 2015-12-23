/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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

public class ModelRitualPedestal extends ModelBase {

	ModelRenderer base;
	ModelRenderer bottom1;
	ModelRenderer bottom2;
	ModelRenderer top1;
	ModelRenderer top2;
	ModelRenderer point1;
	ModelRenderer point2;
	ModelRenderer point3;
	ModelRenderer point4;
	ModelRenderer point5;
	ModelRenderer point6;
	ModelRenderer point7;
	ModelRenderer point8;
	ModelRenderer side1;
	ModelRenderer side2;
	ModelRenderer side3;
	ModelRenderer side4;

	private final boolean renderPoint;

	public ModelRitualPedestal(){
		this(true);
	}
	
	public ModelRitualPedestal(boolean renderPoint)
	{
		this.renderPoint = renderPoint;
		textureWidth = 32;
		textureHeight = 32;

		base = new ModelRenderer(this, 0, 17);
		base.addBox(0F, 0F, 0F, 5, 10, 5);
		base.setRotationPoint(-2.5F, 11F, -2.5F);
		base.setTextureSize(32, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		bottom1 = new ModelRenderer(this, 0, 0);
		bottom1.addBox(0F, 0F, 0F, 8, 2, 8);
		bottom1.setRotationPoint(-4F, 22F, -4F);
		bottom1.setTextureSize(32, 32);
		bottom1.mirror = true;
		setRotation(bottom1, 0F, 0F, 0F);
		bottom2 = new ModelRenderer(this, 2, 9);
		bottom2.addBox(0F, 0F, 0F, 7, 1, 7);
		bottom2.setRotationPoint(-3.5F, 21F, -3.5F);
		bottom2.setTextureSize(32, 32);
		bottom2.mirror = true;
		setRotation(bottom2, 0F, 0F, 0F);
		top1 = new ModelRenderer(this, 2, 9);
		top1.addBox(0F, 0F, 0F, 7, 1, 7);
		top1.setRotationPoint(-3.5F, 10F, -3.5F);
		top1.setTextureSize(32, 32);
		top1.mirror = true;
		setRotation(top1, 0F, 0F, 0F);
		top2 = new ModelRenderer(this, 0, 0);
		top2.addBox(0F, 0F, 0F, 8, 1, 8);
		top2.setRotationPoint(-4F, 9F, -4F);
		top2.setTextureSize(32, 32);
		top2.mirror = true;
		setRotation(top2, 0F, 0F, 0F);
		point1 = new ModelRenderer(this, 0, 0);
		point1.addBox(0F, 0F, 0F, 1, 1, 1);
		point1.setRotationPoint(3F, 8F, 3F);
		point1.setTextureSize(32, 32);
		point1.mirror = true;
		setRotation(point1, 0F, 0F, 0F);
		point2 = new ModelRenderer(this, 0, 0);
		point2.addBox(0F, 0F, 0F, 1, 1, 1);
		point2.setRotationPoint(3F, 8F, -4F);
		point2.setTextureSize(32, 32);
		point2.mirror = true;
		setRotation(point2, 0F, 0F, 0F);
		point3 = new ModelRenderer(this, 0, 0);
		point3.addBox(0F, 0F, 0F, 1, 1, 1);
		point3.setRotationPoint(-4F, 8F, 3F);
		point3.setTextureSize(32, 32);
		point3.mirror = true;
		setRotation(point3, 0F, 0F, 0F);
		point4 = new ModelRenderer(this, 0, 0);
		point4.addBox(0F, 0F, 0F, 1, 1, 1);
		point4.setRotationPoint(-4F, 8F, -4F);
		point4.setTextureSize(32, 32);
		point4.mirror = true;
		setRotation(point4, 0F, 0F, 0F);
		point5 = new ModelRenderer(this, 0, 0);
		point5.addBox(0F, 0F, 0F, 1, 1, 1);
		point5.setRotationPoint(3F, 8F, -0.5F);
		point5.setTextureSize(32, 32);
		point5.mirror = true;
		setRotation(point5, 0F, 0F, 0F);
		point6 = new ModelRenderer(this, 0, 0);
		point6.addBox(0F, 0F, 0F, 1, 1, 1);
		point6.setRotationPoint(-4F, 8F, -0.5F);
		point6.setTextureSize(32, 32);
		point6.mirror = true;
		setRotation(point6, 0F, 0F, 0F);
		point7 = new ModelRenderer(this, 0, 0);
		point7.addBox(0F, 0F, 0F, 1, 1, 1);
		point7.setRotationPoint(-0.5F, 8F, 3F);
		point7.setTextureSize(32, 32);
		point7.mirror = true;
		setRotation(point7, 0F, 0F, 0F);
		point8 = new ModelRenderer(this, 0, 0);
		point8.addBox(0F, 0F, 0F, 1, 1, 1);
		point8.setRotationPoint(-0.5F, 8F, -4F);
		point8.setTextureSize(32, 32);
		point8.mirror = true;
		setRotation(point8, 0F, 0F, 0F);
		side1 = new ModelRenderer(this, 20, 21);
		side1.addBox(0F, 0F, 0F, 1, 10, 1);
		side1.setRotationPoint(-3F, 11F, -3F);
		side1.setTextureSize(32, 32);
		side1.mirror = true;
		setRotation(side1, 0F, 0F, 0F);
		side2 = new ModelRenderer(this, 20, 21);
		side2.addBox(0F, 0F, 0F, 1, 10, 1);
		side2.setRotationPoint(2F, 11F, -3F);
		side2.setTextureSize(32, 32);
		side2.mirror = true;
		setRotation(side2, 0F, 0F, 0F);
		side3 = new ModelRenderer(this, 20, 21);
		side3.addBox(0F, 0F, 0F, 1, 10, 1);
		side3.setRotationPoint(-3F, 11F, 2F);
		side3.setTextureSize(32, 32);
		side3.mirror = true;
		setRotation(side3, 0F, 0F, 0F);
		side4 = new ModelRenderer(this, 20, 21);
		side4.addBox(0F, 0F, 0F, 1, 10, 1);
		side4.setRotationPoint(2F, 11F, 2F);
		side4.setTextureSize(32, 32);
		side4.mirror = true;
		setRotation(side4, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		base.render(f5);
		bottom1.render(f5);
		bottom2.render(f5);
		top1.render(f5);
		top2.render(f5);
		if(renderPoint){
			point1.render(f5);
			point2.render(f5);
			point3.render(f5);
			point4.render(f5);
			point5.render(f5);
			point6.render(f5);
			point7.render(f5);
			point8.render(f5);
		}
		side1.render(f5);
		side2.render(f5);
		side3.render(f5);
		side4.render(f5);
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
