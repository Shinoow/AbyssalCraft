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

public class ModelCthulhuStatue extends ModelBase
{

	ModelRenderer base;
	ModelRenderer body;
	ModelRenderer rightwing;
	ModelRenderer leftwing;
	ModelRenderer head;
	ModelRenderer tentacle1;
	ModelRenderer tentacle2;
	ModelRenderer tentacle3;
	ModelRenderer tentacle4;
	ModelRenderer leftleg1;
	ModelRenderer leftleg2;
	ModelRenderer leftleg3;
	ModelRenderer leftfoot;
	ModelRenderer leftarm;
	ModelRenderer rightleg1;
	ModelRenderer rightleg2;
	ModelRenderer rightleg3;
	ModelRenderer rightfoot;
	ModelRenderer rightarm;

	public ModelCthulhuStatue()
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
		body.addBox(0F, 0F, 0F, 5, 7, 5);
		body.setRotationPoint(-2.5F, 11F, -3.5F);
		body.setTextureSize(64, 32);
		body.mirror = true;
		setRotation(body, 0.1745329F, 0F, 0F);
		rightwing = new ModelRenderer(this, 52, 10);
		rightwing.addBox(0F, 0F, 0F, 3, 9, 1);
		rightwing.setRotationPoint(-3.5F, 9F, 1F);
		rightwing.setTextureSize(64, 32);
		rightwing.mirror = true;
		setRotation(rightwing, 0.1745329F, 0F, 0F);
		leftwing = new ModelRenderer(this, 52, 0);
		leftwing.addBox(0F, 0F, 0F, 3, 9, 1);
		leftwing.setRotationPoint(0.5F, 9F, 1F);
		leftwing.setTextureSize(64, 32);
		leftwing.mirror = true;
		setRotation(leftwing, 0.1745329F, 0F, 0F);
		head = new ModelRenderer(this, 0, 14);
		head.addBox(0F, 0F, 0F, 4, 4, 4);
		head.setRotationPoint(-2F, 7F, -5.5F);
		head.setTextureSize(64, 32);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		tentacle1 = new ModelRenderer(this, 0, 0);
		tentacle1.addBox(0F, 0F, 0F, 1, 5, 1);
		tentacle1.setRotationPoint(1F, 10F, -5.5F);
		tentacle1.setTextureSize(64, 32);
		tentacle1.mirror = true;
		setRotation(tentacle1, -0.23753F, 0F, 0F);
		tentacle2 = new ModelRenderer(this, 0, 0);
		tentacle2.addBox(0F, 0F, 0F, 1, 5, 1);
		tentacle2.setRotationPoint(0F, 10F, -5.5F);
		tentacle2.setTextureSize(64, 32);
		tentacle2.mirror = true;
		setRotation(tentacle2, -0.4606017F, 0F, 0F);
		tentacle3 = new ModelRenderer(this, 0, 0);
		tentacle3.addBox(0F, 0F, 0F, 1, 5, 1);
		tentacle3.setRotationPoint(-1F, 10F, -5.5F);
		tentacle3.setTextureSize(64, 32);
		tentacle3.mirror = true;
		setRotation(tentacle3, -0.2974289F, 0F, 0F);
		tentacle4 = new ModelRenderer(this, 0, 0);
		tentacle4.addBox(0F, 0F, 0F, 1, 5, 1);
		tentacle4.setRotationPoint(-2F, 10F, -5.5F);
		tentacle4.setTextureSize(64, 32);
		tentacle4.mirror = true;
		setRotation(tentacle4, -0.4461433F, 0F, 0F);
		leftleg1 = new ModelRenderer(this, 0, 23);
		leftleg1.addBox(0F, 0F, 0F, 2, 3, 3);
		leftleg1.setRotationPoint(2F, 14.5F, -2F);
		leftleg1.setTextureSize(64, 32);
		leftleg1.mirror = true;
		setRotation(leftleg1, 0F, 0F, 0F);
		leftleg2 = new ModelRenderer(this, 41, 13);
		leftleg2.addBox(0F, 0F, 0F, 2, 2, 3);
		leftleg2.setRotationPoint(2F, 14F, -4F);
		leftleg2.setTextureSize(64, 32);
		leftleg2.mirror = true;
		setRotation(leftleg2, -0.3490659F, 0F, 0F);
		leftleg3 = new ModelRenderer(this, 32, 13);
		leftleg3.addBox(0F, 0F, 0F, 2, 4, 2);
		leftleg3.setRotationPoint(2F, 14F, -6F);
		leftleg3.setTextureSize(64, 32);
		leftleg3.mirror = true;
		setRotation(leftleg3, 0F, 0F, 0F);
		leftfoot = new ModelRenderer(this, 11, 23);
		leftfoot.addBox(0F, 0F, 0F, 2, 3, 1);
		leftfoot.setRotationPoint(2F, 18F, -5F);
		leftfoot.setTextureSize(64, 32);
		leftfoot.mirror = true;
		setRotation(leftfoot, 0F, 0F, 0F);
		leftarm = new ModelRenderer(this, 16, 14);
		leftarm.addBox(0F, 0F, 0F, 2, 2, 6);
		leftarm.setRotationPoint(2F, 12.5F, -6F);
		leftarm.setTextureSize(64, 32);
		leftarm.mirror = true;
		setRotation(leftarm, 0.2617994F, 0F, 0F);
		rightleg1 = new ModelRenderer(this, 0, 23);
		rightleg1.addBox(0F, 0F, 0F, 2, 3, 3);
		rightleg1.setRotationPoint(-4F, 14.5F, -2F);
		rightleg1.setTextureSize(64, 32);
		rightleg1.mirror = true;
		setRotation(rightleg1, 0F, 0F, 0F);
		rightleg2 = new ModelRenderer(this, 41, 13);
		rightleg2.addBox(0F, 0F, 0F, 2, 2, 3);
		rightleg2.setRotationPoint(-4F, 14F, -4F);
		rightleg2.setTextureSize(64, 32);
		rightleg2.mirror = true;
		setRotation(rightleg2, -0.3490659F, 0F, 0F);
		rightleg3 = new ModelRenderer(this, 32, 13);
		rightleg3.addBox(0F, 0F, 0F, 2, 4, 2);
		rightleg3.setRotationPoint(-4F, 14F, -6F);
		rightleg3.setTextureSize(64, 32);
		rightleg3.mirror = true;
		setRotation(rightleg3, 0F, 0F, 0F);
		rightfoot = new ModelRenderer(this, 11, 23);
		rightfoot.addBox(0F, 0F, 0F, 2, 3, 1);
		rightfoot.setRotationPoint(-4F, 18F, -5F);
		rightfoot.setTextureSize(64, 32);
		rightfoot.mirror = true;
		setRotation(rightfoot, 0F, 0F, 0F);
		rightarm = new ModelRenderer(this, 16, 14);
		rightarm.addBox(0F, 0F, 0F, 2, 2, 6);
		rightarm.setRotationPoint(-4F, 12.5F, -6F);
		rightarm.setTextureSize(64, 32);
		rightarm.mirror = true;
		setRotation(rightarm, 0.2617994F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		base.render(f5);
		body.render(f5);
		rightwing.render(f5);
		leftwing.render(f5);
		head.render(f5);
		tentacle1.render(f5);
		tentacle2.render(f5);
		tentacle3.render(f5);
		tentacle4.render(f5);
		leftleg1.render(f5);
		leftleg2.render(f5);
		leftleg3.render(f5);
		leftfoot.render(f5);
		leftarm.render(f5);
		rightleg1.render(f5);
		rightleg2.render(f5);
		rightleg3.render(f5);
		rightfoot.render(f5);
		rightarm.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
