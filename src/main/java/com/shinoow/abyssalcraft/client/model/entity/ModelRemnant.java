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
package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelRemnant extends ModelBase {

	public ModelRenderer mask1;
	public ModelRenderer mask2;
	public ModelRenderer head;
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer leftarm;
	public ModelRenderer tentacle1;
	public ModelRenderer tentacle2;
	public ModelRenderer tentacle3;
	public ModelRenderer tentacle4;
	public ModelRenderer rightarm;

	public ModelRemnant()
	{
		textureWidth = 128;
		textureHeight = 64;

		mask1 = new ModelRenderer(this, 32, 0);
		mask1.addBox(-3.4F, -8F, -6F, 6, 8, 1);
		mask1.setRotationPoint(0F, 0F, 0F);
		mask1.setTextureSize(128, 64);
		mask1.mirror = true;
		setRotation(mask1, 0F, -0.5235988F, 0F);
		mask2 = new ModelRenderer(this, 32, 0);
		mask2.addBox(-2.6F, -8F, -6F, 6, 8, 1);
		mask2.setRotationPoint(0F, 0F, 0F);
		mask2.setTextureSize(128, 64);
		mask2.mirror = true;
		setRotation(mask2, 0F, 0.5235988F, 0F);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4F, -8F, -4F, 8, 8, 8);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(128, 64);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		head.addChild(mask1);
		head.addChild(mask2);
		body = new ModelRenderer(this, 16, 16);
		body.addBox(-4F, 0F, -2F, 8, 22, 4);
		body.setRotationPoint(0F, 0F, 0F);
		body.setTextureSize(128, 64);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		leg1 = new ModelRenderer(this, 0, 42);
		leg1.addBox(0F, -1F, -1F, 10, 2, 2);
		leg1.setRotationPoint(1F, 23F, 0F);
		leg1.setTextureSize(128, 64);
		leg1.mirror = true;
		setRotation(leg1, 0F, 0F, 0F);
		leg2 = new ModelRenderer(this, 0, 42);
		leg2.addBox(-10F, -1F, -1F, 10, 2, 2);
		leg2.setRotationPoint(-1F, 23F, 0F);
		leg2.setTextureSize(128, 64);
		leg2.mirror = true;
		setRotation(leg2, 0F, 0F, 0F);
		leg3 = new ModelRenderer(this, 24, 42);
		leg3.addBox(-1F, -1F, 0F, 2, 2, 10);
		leg3.setRotationPoint(0F, 23F, 1F);
		leg3.setTextureSize(128, 64);
		leg3.mirror = true;
		setRotation(leg3, 0F, 0F, 0F);
		leg4 = new ModelRenderer(this, 24, 42);
		leg4.addBox(-1F, -1F, -10F, 2, 2, 10);
		leg4.setRotationPoint(0F, 23F, -1F);
		leg4.setTextureSize(128, 64);
		leg4.mirror = true;
		setRotation(leg4, 0F, 0F, 0F);
		leftarm = new ModelRenderer(this, 0, 20);
		leftarm.addBox(0F, -2F, -2F, 4, 10, 4);
		leftarm.setRotationPoint(4F, 2F, 0F);
		leftarm.setTextureSize(128, 64);
		leftarm.mirror = true;
		setRotation(leftarm, 0F, 0F, 0F);
		tentacle1 = new ModelRenderer(this, 0, 46);
		tentacle1.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle1.setRotationPoint(1F, 8F, 1F);
		tentacle1.setTextureSize(128, 64);
		tentacle1.mirror = true;
		setRotation(tentacle1, 0F, 0F, 0F);
		tentacle2 = new ModelRenderer(this, 0, 46);
		tentacle2.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle2.setRotationPoint(3F, 8F, 1F);
		tentacle2.setTextureSize(128, 64);
		tentacle2.mirror = true;
		setRotation(tentacle2, 0F, 0F, 0F);
		tentacle3 = new ModelRenderer(this, 0, 46);
		tentacle3.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle3.setRotationPoint(1F, 8F, -1F);
		tentacle3.setTextureSize(128, 64);
		tentacle3.mirror = true;
		setRotation(tentacle3, 0F, 0F, 0F);
		tentacle4 = new ModelRenderer(this, 0, 46);
		tentacle4.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle4.setRotationPoint(3F, 8F, -1F);
		tentacle4.setTextureSize(128, 64);
		tentacle4.mirror = true;
		setRotation(tentacle4, 0F, 0F, 0F);
		leftarm.addChild(tentacle1);
		leftarm.addChild(tentacle2);
		leftarm.addChild(tentacle3);
		leftarm.addChild(tentacle4);
		rightarm = new ModelRenderer(this, 0, 20);
		rightarm.addBox(-4F, -2F, -2F, 4, 16, 4);
		rightarm.setRotationPoint(-4F, 2F, 0F);
		rightarm.setTextureSize(128, 64);
		rightarm.mirror = true;
		setRotation(rightarm, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		body.render(f5);
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
		leftarm.render(f5);
		rightarm.render(f5);
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
		head.rotateAngleY = f3 / (180F / (float)Math.PI);
		head.rotateAngleX = f4 / (180F / (float)Math.PI);

		rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
		leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;

		rightarm.rotateAngleZ = 0.0F;
		leftarm.rotateAngleZ = 0.0F;

		tentacle1.offsetX = tentacle1.offsetY = tentacle1.offsetZ = 0.0F;
		float f16 = 0.03F * (entity.getEntityId() % 10);
		tentacle1.rotateAngleX = MathHelper.cos(entity.ticksExisted * f16) * 10.5F * (float)Math.PI / 180.0F;
		tentacle1.rotateAngleY = 0.0F;
		tentacle1.rotateAngleZ = MathHelper.sin(entity.ticksExisted * f16) * 6.5F * (float)Math.PI / 180.0F;
		float f17 = 0.03F * (entity.getEntityId() % 10);
		tentacle2.offsetX = tentacle2.offsetY = tentacle2.offsetZ = 0.0F;
		tentacle2.rotateAngleX = MathHelper.sin(entity.ticksExisted * f17) * 10.5F * (float)Math.PI / 180.0F;
		tentacle2.rotateAngleY = 0.0F;
		tentacle2.rotateAngleZ = MathHelper.cos(entity.ticksExisted * f17) * 6.5F * (float)Math.PI / 180.0F;
		float f18 = 0.03F * (entity.getEntityId() % 10);
		tentacle3.offsetX = tentacle3.offsetY = tentacle3.offsetZ = 0.0F;
		tentacle3.rotateAngleX = MathHelper.sin(entity.ticksExisted * f18) * 10.5F * (float)Math.PI / 180.0F;
		tentacle3.rotateAngleY = 0.0F;
		tentacle3.rotateAngleZ = MathHelper.cos(entity.ticksExisted * f18) * 6.5F * (float)Math.PI / 180.0F;
		float f19 = 0.03F * (entity.getEntityId() % 10);
		tentacle4.offsetX = tentacle4.offsetY = tentacle4.offsetZ = 0.0F;
		tentacle4.rotateAngleX = MathHelper.cos(entity.ticksExisted * f19) * 10.5F * (float)Math.PI / 180.0F;
		tentacle4.rotateAngleY = 0.0F;
		tentacle4.rotateAngleZ = MathHelper.sin(entity.ticksExisted * f19) * 6.5F * (float)Math.PI / 180.0F;

		leg1.rotateAngleY = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
		leg2.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		leg3.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leg4.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;

		if (isRiding)
		{
			rightarm.rotateAngleX += -((float)Math.PI / 5F);
			leftarm.rotateAngleX += -((float)Math.PI / 5F);

			leg1.rotateAngleX = -((float)Math.PI * 2F / 5F);
			leg2.rotateAngleX = -((float)Math.PI * 2F / 5F);
			leg3.rotateAngleX = -((float)Math.PI * 2F / 5F);
			leg4.rotateAngleX = -((float)Math.PI * 2F / 5F);

			leg1.rotateAngleY = (float)Math.PI / 10F;
			leg2.rotateAngleY = -((float)Math.PI / 10F);
			leg3.rotateAngleY = (float)Math.PI / 10F;
			leg4.rotateAngleY = -((float)Math.PI / 10F);
		}

		rightarm.rotateAngleY = 0.0F;
		leftarm.rotateAngleY = 0.0F;
		float f6;
		float f7;

		if (onGround > -9990.0F)
		{
			f6 = onGround;
			body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightarm.rotateAngleY += body.rotateAngleY;
			leftarm.rotateAngleY += body.rotateAngleY;
			f6 = 1.0F - onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			f7 = MathHelper.sin(f6 * (float)Math.PI);
			float f8 = MathHelper.sin(onGround * (float)Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
			rightarm.rotateAngleX = (float)(rightarm.rotateAngleX - (f7 * 1.2D + f8));
			rightarm.rotateAngleY += body.rotateAngleY * 2.0F;
			rightarm.rotateAngleZ = MathHelper.sin(onGround * (float)Math.PI) * -0.4F;
			leftarm.rotateAngleX = (float)(leftarm.rotateAngleX - (f7 * 1.2D + f8));
			leftarm.rotateAngleY += body.rotateAngleY * -2.0F;
			leftarm.rotateAngleZ = MathHelper.sin(onGround * (float)Math.PI) * 0.4F;
		}
	}
}
