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

public class ModelNyarlathotepStatue extends ModelBase
{

	ModelRenderer base;
	ModelRenderer body;
	ModelRenderer head;
	ModelRenderer headtentacle1;
	ModelRenderer headtentacle2;
	ModelRenderer headtentacle3;
	ModelRenderer headtentacle4;
	ModelRenderer headtentacle5;
	ModelRenderer headtentacle6;
	ModelRenderer leftarm;
	ModelRenderer rightarm;
	ModelRenderer tentacle1;
	ModelRenderer tentacle2;
	ModelRenderer tentacle3;
	ModelRenderer tentacle4;
	ModelRenderer tentacle5;
	ModelRenderer tentacle6;
	ModelRenderer tentacle7;
	ModelRenderer tentacle8;
	ModelRenderer tentacle9;
	ModelRenderer tentacle10;
	ModelRenderer tentacle11;
	ModelRenderer tentacle12;
	ModelRenderer tentacle13;

	public ModelNyarlathotepStatue()
	{
		textureWidth = 64;
		textureHeight = 32;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 8, 6, 8);
		base.setRotationPoint(-4F, 18F, -4F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		body = new ModelRenderer(this, 32, 0);
		body.addBox(0F, 0F, 0F, 3, 5, 3);
		body.setRotationPoint(-1F, 10F, -3F);
		body.setTextureSize(64, 32);
		body.mirror = true;
		setRotation(body, 0F, -0.2974289F, 0F);
		head = new ModelRenderer(this, 44, 0);
		head.addBox(0F, 0F, 0F, 2, 3, 2);
		head.setRotationPoint(-1F, 7F, -2F);
		head.setTextureSize(64, 32);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		headtentacle1 = new ModelRenderer(this, 44, 0);
		headtentacle1.addBox(0F, 0F, 0F, 2, 2, 2);
		headtentacle1.setRotationPoint(-0.4F, 5.6F, -2F);
		headtentacle1.setTextureSize(64, 32);
		headtentacle1.mirror = true;
		setRotation(headtentacle1, 0F, 0F, 0.7853982F);
		headtentacle2 = new ModelRenderer(this, 44, 0);
		headtentacle2.addBox(-1F, 0F, 0F, 3, 2, 2);
		headtentacle2.setRotationPoint(-2.3F, 6.1F, -2F);
		headtentacle2.setTextureSize(64, 32);
		headtentacle2.mirror = true;
		setRotation(headtentacle2, 0F, 0F, -0.2617994F);
		headtentacle3 = new ModelRenderer(this, 44, 0);
		headtentacle3.addBox(0F, 0F, 0F, 2, 3, 2);
		headtentacle3.setRotationPoint(-3.5F, 6.4F, -2F);
		headtentacle3.setTextureSize(64, 32);
		headtentacle3.mirror = true;
		setRotation(headtentacle3, 0F, 0F, 0F);
		headtentacle4 = new ModelRenderer(this, 44, 0);
		headtentacle4.addBox(0F, 0F, 0F, 1, 1, 3);
		headtentacle4.setRotationPoint(-3F, 8.4F, 0F);
		headtentacle4.setTextureSize(64, 32);
		headtentacle4.mirror = true;
		setRotation(headtentacle4, 0F, 0.296706F, 0F);
		headtentacle5 = new ModelRenderer(this, 44, 0);
		headtentacle5.addBox(0F, 0F, 0F, 1, 1, 3);
		headtentacle5.setRotationPoint(-2.2F, 8.4F, 2.8F);
		headtentacle5.setTextureSize(64, 32);
		headtentacle5.mirror = true;
		setRotation(headtentacle5, 0F, 0.7679449F, 0F);
		headtentacle6 = new ModelRenderer(this, 44, 0);
		headtentacle6.addBox(0F, 0F, 0F, 1, 2, 1);
		headtentacle6.setRotationPoint(-0.8F, 8.7F, 4.3F);
		headtentacle6.setTextureSize(64, 32);
		headtentacle6.mirror = true;
		setRotation(headtentacle6, 0.2974289F, 0.7679449F, 0F);
		leftarm = new ModelRenderer(this, 32, 8);
		leftarm.addBox(0F, 0F, 0F, 1, 1, 3);
		leftarm.setRotationPoint(0.3F, 10F, 0.5F);
		leftarm.setTextureSize(64, 32);
		leftarm.mirror = true;
		setRotation(leftarm, -0.2602503F, 0.2974289F, 0F);
		rightarm = new ModelRenderer(this, 32, 8);
		rightarm.addBox(0F, 0F, -3F, 1, 1, 3);
		rightarm.setRotationPoint(-1F, 10F, -2.5F);
		rightarm.setTextureSize(64, 32);
		rightarm.mirror = true;
		setRotation(rightarm, 0.4089647F, 0.4461433F, 0F);
		tentacle1 = new ModelRenderer(this, 0, 14);
		tentacle1.addBox(0F, 0F, 0F, 2, 2, 4);
		tentacle1.setRotationPoint(-0.5F, 13F, 0F);
		tentacle1.setTextureSize(64, 32);
		tentacle1.mirror = true;
		setRotation(tentacle1, 0.4363323F, 0.4363323F, 0F);
		tentacle2 = new ModelRenderer(this, 0, 14);
		tentacle2.addBox(0F, 0F, 0F, 2, 4, 2);
		tentacle2.setRotationPoint(1F, 13F, -3F);
		tentacle2.setTextureSize(64, 32);
		tentacle2.mirror = true;
		setRotation(tentacle2, -0.4363323F, -0.8726646F, 0F);
		tentacle3 = new ModelRenderer(this, 0, 14);
		tentacle3.addBox(0F, 0F, 0F, 4, 2, 2);
		tentacle3.setRotationPoint(0.5F, 16F, -5.2F);
		tentacle3.setTextureSize(64, 32);
		tentacle3.mirror = true;
		setRotation(tentacle3, 0F, -0.6320364F, 0.1487144F);
		tentacle4 = new ModelRenderer(this, 0, 14);
		tentacle4.addBox(0F, 0F, 0F, 2, 6, 2);
		tentacle4.setRotationPoint(1F, 11.3F, 3.3F);
		tentacle4.setTextureSize(64, 32);
		tentacle4.mirror = true;
		setRotation(tentacle4, 0F, 0.4363323F, 0F);
		tentacle5 = new ModelRenderer(this, 0, 14);
		tentacle5.addBox(0F, 0F, 0F, 1, 1, 4);
		tentacle5.setRotationPoint(1.9F, 17F, 2.9F);
		tentacle5.setTextureSize(64, 32);
		tentacle5.mirror = true;
		setRotation(tentacle5, 0F, 0.4363323F, 0F);
		tentacle6 = new ModelRenderer(this, 0, 14);
		tentacle6.addBox(0F, -1F, 0F, 1, 2, 1);
		tentacle6.setRotationPoint(3.4F, 16F, 5.5F);
		tentacle6.setTextureSize(64, 32);
		tentacle6.mirror = true;
		setRotation(tentacle6, 0F, 0.4363323F, 0.2443461F);
		tentacle7 = new ModelRenderer(this, 0, 14);
		tentacle7.addBox(0F, 0F, 0F, 1, 1, 2);
		tentacle7.setRotationPoint(3.8F, 15.1F, 4F);
		tentacle7.setTextureSize(64, 32);
		tentacle7.mirror = true;
		setRotation(tentacle7, 0F, 0F, 0F);
		tentacle8 = new ModelRenderer(this, 0, 14);
		tentacle8.addBox(0F, 0F, 0F, 2, 4, 2);
		tentacle8.setRotationPoint(-2F, 13F, -2F);
		tentacle8.setTextureSize(64, 32);
		tentacle8.mirror = true;
		setRotation(tentacle8, 0F, 0F, 0.4363323F);
		tentacle9 = new ModelRenderer(this, 0, 14);
		tentacle9.addBox(0F, 0F, 0F, 2, 2, 4);
		tentacle9.setRotationPoint(-3.7F, 16.5F, -2F);
		tentacle9.setTextureSize(64, 32);
		tentacle9.mirror = true;
		setRotation(tentacle9, 0.2230717F, 0F, 0F);
		tentacle10 = new ModelRenderer(this, 0, 14);
		tentacle10.addBox(0F, -2F, 0F, 2, 3, 2);
		tentacle10.setRotationPoint(-1F, 17F, -6.3F);
		tentacle10.setTextureSize(64, 32);
		tentacle10.mirror = true;
		setRotation(tentacle10, 0F, -0.6320361F, 0F);
		tentacle11 = new ModelRenderer(this, 0, 14);
		tentacle11.addBox(0F, 0F, 0F, 1, 1, 2);
		tentacle11.setRotationPoint(-3F, 16F, 2F);
		tentacle11.setTextureSize(64, 32);
		tentacle11.mirror = true;
		setRotation(tentacle11, 0F, 0.2974289F, 0F);
		tentacle12 = new ModelRenderer(this, 0, 14);
		tentacle12.addBox(-2F, 0F, 0F, 3, 1, 1);
		tentacle12.setRotationPoint(-1.5F, 14F, -5.5F);
		tentacle12.setTextureSize(64, 32);
		tentacle12.mirror = true;
		setRotation(tentacle12, 0F, 0.2617994F, 0F);
		tentacle13 = new ModelRenderer(this, 0, 14);
		tentacle13.addBox(0F, 0F, 0F, 1, 2, 1);
		tentacle13.setRotationPoint(-4.4F, 14F, -4.7F);
		tentacle13.setTextureSize(64, 32);
		tentacle13.mirror = true;
		setRotation(tentacle13, 0F, 0.2617994F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		base.render(f5);
		body.render(f5);
		head.render(f5);
		headtentacle1.render(f5);
		headtentacle2.render(f5);
		headtentacle3.render(f5);
		headtentacle4.render(f5);
		headtentacle5.render(f5);
		headtentacle6.render(f5);
		leftarm.render(f5);
		rightarm.render(f5);
		tentacle1.render(f5);
		tentacle2.render(f5);
		tentacle3.render(f5);
		tentacle4.render(f5);
		tentacle5.render(f5);
		tentacle6.render(f5);
		tentacle7.render(f5);
		tentacle8.render(f5);
		tentacle9.render(f5);
		tentacle10.render(f5);
		tentacle11.render(f5);
		tentacle12.render(f5);
		tentacle13.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
