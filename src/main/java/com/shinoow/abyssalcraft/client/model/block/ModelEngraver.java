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

public class ModelEngraver extends ModelBase {

	ModelRenderer anvilbottom;
	ModelRenderer anvilcenter;
	ModelRenderer anviltop;
	ModelRenderer engraver1;
	ModelRenderer crank;
	ModelRenderer handle1;
	ModelRenderer handle2;
	ModelRenderer engraver2;
	ModelRenderer engraver3;
	ModelRenderer engraver4;
	ModelRenderer engraver5;

	public ModelEngraver()
	{
		textureWidth = 64;
		textureHeight = 32;

		anvilbottom = new ModelRenderer(this, 31, 16);
		anvilbottom.addBox(0F, 0F, 0F, 7, 1, 9);
		anvilbottom.setRotationPoint(-1.5F, 23F, -4.5F);
		anvilbottom.setTextureSize(64, 32);
		anvilbottom.mirror = true;
		setRotation(anvilbottom, 0F, 0F, 0F);
		anvilcenter = new ModelRenderer(this, 36, 6);
		anvilcenter.addBox(0F, 0F, 0F, 5, 3, 7);
		anvilcenter.setRotationPoint(-0.5F, 20F, -3.5F);
		anvilcenter.setTextureSize(64, 32);
		anvilcenter.mirror = true;
		setRotation(anvilcenter, 0F, 0F, 0F);
		anviltop = new ModelRenderer(this, 0, 0);
		anviltop.addBox(0F, 0F, 0F, 8, 2, 10);
		anviltop.setRotationPoint(-2F, 18F, -5F);
		anviltop.setTextureSize(64, 32);
		anviltop.mirror = true;
		setRotation(anviltop, 0F, 0F, 0F);
		engraver1 = new ModelRenderer(this, 0, 12);
		engraver1.addBox(0F, 0F, 0F, 3, 13, 4);
		engraver1.setRotationPoint(-5F, 11F, -2F);
		engraver1.setTextureSize(64, 32);
		engraver1.mirror = true;
		setRotation(engraver1, 0F, 0F, 0F);
		crank = new ModelRenderer(this, 26, 0);
		crank.addBox(0F, 0F, 0F, 1, 1, 7);
		crank.setRotationPoint(-6F, 16F, -3F);
		crank.setTextureSize(64, 32);
		crank.mirror = true;
		setRotation(crank, -0.3717861F, 0F, 0F);
		handle1 = new ModelRenderer(this, 0, 0);
		handle1.addBox(-1F, 0F, 0F, 2, 1, 1);
		handle1.setRotationPoint(-7F, 16F, -3F);
		handle1.setTextureSize(64, 32);
		handle1.mirror = true;
		setRotation(handle1, -0.37179F, 0F, 0F);
		handle2 = new ModelRenderer(this, 0, 0);
		handle2.addBox(-1F, 0F, 0F, 2, 1, 1);
		handle2.setRotationPoint(-7F, 18.2F, 2.6F);
		handle2.setTextureSize(64, 32);
		handle2.mirror = true;
		setRotation(handle2, -0.37179F, 0F, 0F);
		engraver2 = new ModelRenderer(this, 36, 0);
		engraver2.addBox(0F, 0F, 0F, 5, 1, 5);
		engraver2.setRotationPoint(-0.5F, 17F, -2.5F);
		engraver2.setTextureSize(64, 32);
		engraver2.mirror = true;
		setRotation(engraver2, 0F, 0F, 0F);
		engraver3 = new ModelRenderer(this, 14, 14);
		engraver3.addBox(0F, 0F, 0F, 9, 3, 4);
		engraver3.setRotationPoint(-5F, 8F, -2F);
		engraver3.setTextureSize(64, 32);
		engraver3.mirror = true;
		setRotation(engraver3, 0F, 0F, 0F);
		engraver4 = new ModelRenderer(this, 14, 21);
		engraver4.addBox(0F, 0F, 0F, 2, 5, 2);
		engraver4.setRotationPoint(1F, 9F, -1F);
		engraver4.setTextureSize(64, 32);
		engraver4.mirror = true;
		setRotation(engraver4, 0F, 0F, 0F);
		engraver5 = new ModelRenderer(this, 36, 0);
		engraver5.addBox(0F, 0F, 0F, 5, 1, 5);
		engraver5.setRotationPoint(-0.5F, 14F, -2.5F);
		engraver5.setTextureSize(64, 32);
		engraver5.mirror = true;
		setRotation(engraver5, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		anvilbottom.render(f5);
		anvilcenter.render(f5);
		anviltop.render(f5);
		engraver1.render(f5);
		crank.render(f5);
		handle1.render(f5);
		handle2.render(f5);
		engraver2.render(f5);
		engraver3.render(f5);
		engraver4.render(f5);
		engraver5.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
