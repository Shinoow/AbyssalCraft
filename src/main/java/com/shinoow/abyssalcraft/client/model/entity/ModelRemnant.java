/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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

public class ModelRemnant extends ModelBase {

	public ModelRenderer mask1;
	public ModelRenderer mask2;
	public ModelRenderer head;
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg11;
	public ModelRenderer leg12;
	public ModelRenderer leg13;
	public ModelRenderer leg14;
	public ModelRenderer leg2;
	public ModelRenderer leg21;
	public ModelRenderer leg22;
	public ModelRenderer leg23;
	public ModelRenderer leg24;
	public ModelRenderer leg3;
	public ModelRenderer leg31;
	public ModelRenderer leg32;
	public ModelRenderer leg33;
	public ModelRenderer leg34;
	public ModelRenderer leg4;
	public ModelRenderer leg41;
	public ModelRenderer leg42;
	public ModelRenderer leg43;
	public ModelRenderer leg44;
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
		leg1.addBox(0F, -1F, -1F, 2, 2, 2);
		leg1.setRotationPoint(1F, 23F, 0F);
		leg1.setTextureSize(128, 64);
		leg1.mirror = true;
		setRotation(leg1, 0F, 0F, 0F);
		leg11 = new ModelRenderer(this, 0, 42);
		leg11.addBox(0F, -1F, -1F, 2, 2, 2);
		leg11.setRotationPoint(2F, 0F, 0F);
		leg11.setTextureSize(128, 64);
		leg11.mirror = true;
		setRotation(leg11, 0F, 0F, 0F);
		leg1.addChild(leg11);
		leg12 = new ModelRenderer(this, 0, 42);
		leg12.addBox(0F, -1F, -1F, 2, 2, 2);
		leg12.setRotationPoint(2F, 0F, 0F);
		leg12.setTextureSize(128, 64);
		leg12.mirror = true;
		setRotation(leg12, 0F, 0F, 0F);
		leg11.addChild(leg12);
		leg13 = new ModelRenderer(this, 0, 42);
		leg13.addBox(0F, -1F, -1F, 2, 2, 2);
		leg13.setRotationPoint(2F, 0F, 0F);
		leg13.setTextureSize(128, 64);
		leg13.mirror = true;
		setRotation(leg13, 0F, 0F, 0F);
		leg12.addChild(leg13);
		leg14 = new ModelRenderer(this, 0, 42);
		leg14.addBox(0F, -1F, -1F, 2, 2, 2);
		leg14.setRotationPoint(2F, 0F, 0F);
		leg14.setTextureSize(128, 64);
		leg14.mirror = true;
		setRotation(leg14, 0F, 0F, 0F);
		leg13.addChild(leg14);
		leg2 = new ModelRenderer(this, 0, 42);
		leg2.addBox(-2F, -1F, -1F, 2, 2, 2);
		leg2.setRotationPoint(-1F, 23F, 0F);
		leg2.setTextureSize(128, 64);
		leg2.mirror = true;
		setRotation(leg2, 0F, 0F, 0F);
		leg21 = new ModelRenderer(this, 0, 42);
		leg21.addBox(-2F, -1F, -1F, 2, 2, 2);
		leg21.setRotationPoint(-2F, 0F, 0F);
		leg21.setTextureSize(128, 64);
		leg21.mirror = true;
		setRotation(leg21, 0F, 0F, 0F);
		leg2.addChild(leg21);
		leg22 = new ModelRenderer(this, 0, 42);
		leg22.addBox(-2F, -1F, -1F, 2, 2, 2);
		leg22.setRotationPoint(-2F, 0F, 0F);
		leg22.setTextureSize(128, 64);
		leg22.mirror = true;
		setRotation(leg22, 0F, 0F, 0F);
		leg21.addChild(leg22);
		leg23 = new ModelRenderer(this, 0, 42);
		leg23.addBox(-2F, -1F, -1F, 2, 2, 2);
		leg23.setRotationPoint(-2F, 0F, 0F);
		leg23.setTextureSize(128, 64);
		leg23.mirror = true;
		setRotation(leg23, 0F, 0F, 0F);
		leg22.addChild(leg23);
		leg24 = new ModelRenderer(this, 0, 42);
		leg24.addBox(-2F, -1F, -1F, 2, 2, 2);
		leg24.setRotationPoint(-2F, 0F, 0F);
		leg24.setTextureSize(128, 64);
		leg24.mirror = true;
		setRotation(leg24, 0F, 0F, 0F);
		leg23.addChild(leg24);
		leg3 = new ModelRenderer(this, 2, 42);
		leg3.addBox(-1F, -1F, 0F, 2, 2, 2);
		leg3.setRotationPoint(0F, 23F, 1F);
		leg3.setTextureSize(128, 64);
		leg3.mirror = true;
		setRotation(leg3, 0F, 0F, 0F);
		leg31 = new ModelRenderer(this, 2, 42);
		leg31.addBox(-1F, -1F, 0F, 2, 2, 2);
		leg31.setRotationPoint(0F, 0F, 2F);
		leg31.setTextureSize(128, 64);
		leg31.mirror = true;
		setRotation(leg31, 0F, 0F, 0F);
		leg3.addChild(leg31);
		leg32 = new ModelRenderer(this, 2, 42);
		leg32.addBox(-1F, -1F, 0F, 2, 2, 2);
		leg32.setRotationPoint(0F, 0F, 2F);
		leg32.setTextureSize(128, 64);
		leg32.mirror = true;
		setRotation(leg32, 0F, 0F, 0F);
		leg31.addChild(leg32);
		leg33 = new ModelRenderer(this, 2, 42);
		leg33.addBox(-1F, -1F, 0F, 2, 2, 2);
		leg33.setRotationPoint(0F, 0F, 2F);
		leg33.setTextureSize(128, 64);
		leg33.mirror = true;
		setRotation(leg33, 0F, 0F, 0F);
		leg32.addChild(leg33);
		leg34 = new ModelRenderer(this, 2, 42);
		leg34.addBox(-1F, -1F, 0F, 2, 2, 2);
		leg34.setRotationPoint(0F, 0F, 2F);
		leg34.setTextureSize(128, 64);
		leg34.mirror = true;
		setRotation(leg34, 0F, 0F, 0F);
		leg33.addChild(leg34);
		leg4 = new ModelRenderer(this, 2, 42);
		leg4.addBox(-1F, -1F, -2F, 2, 2, 2);
		leg4.setRotationPoint(0F, 23F, -1F);
		leg4.setTextureSize(128, 64);
		leg4.mirror = true;
		setRotation(leg4, 0F, 0F, 0F);
		leg41 = new ModelRenderer(this, 2, 42);
		leg41.addBox(-1F, -1F, -2F, 2, 2, 2);
		leg41.setRotationPoint(0F, 0F, -2F);
		leg41.setTextureSize(128, 64);
		leg41.mirror = true;
		setRotation(leg41, 0F, 0F, 0F);
		leg4.addChild(leg41);
		leg42 = new ModelRenderer(this, 2, 42);
		leg42.addBox(-1F, -1F, -2F, 2, 2, 2);
		leg42.setRotationPoint(0F, 0F, -2F);
		leg42.setTextureSize(128, 64);
		leg42.mirror = true;
		setRotation(leg42, 0F, 0F, 0F);
		leg41.addChild(leg42);
		leg43 = new ModelRenderer(this, 2, 42);
		leg43.addBox(-1F, -1F, -2F, 2, 2, 2);
		leg43.setRotationPoint(0F, 0F, -2F);
		leg43.setTextureSize(128, 64);
		leg43.mirror = true;
		setRotation(leg43, 0F, 0F, 0F);
		leg42.addChild(leg43);
		leg44 = new ModelRenderer(this, 2, 42);
		leg44.addBox(-1F, -1F, -2F, 2, 2, 2);
		leg44.setRotationPoint(0F, 0F, -2F);
		leg44.setTextureSize(128, 64);
		leg44.mirror = true;
		setRotation(leg44, 0F, 0F, 0F);
		leg43.addChild(leg44);
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

		leg1.rotateAngleY = 0.6662F + MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
		leg11.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.9F * f1 * 0.5F;
		leg12.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.8F * f1 * 0.5F;
		leg13.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.7F * f1 * 0.5F;
		leg14.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.6F * f1 * 0.5F;
		leg2.rotateAngleY = 0.6662F + MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
		leg21.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.9F * f1 * 0.5F;
		leg22.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.8F * f1 * 0.5F;
		leg23.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.7F * f1 * 0.5F;
		leg24.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.6F * f1 * 0.5F;
		leg3.rotateAngleY = 0.6662F + MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
		leg31.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.9F * f1 * 0.5F;
		leg32.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.8F * f1 * 0.5F;
		leg33.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.7F * f1 * 0.5F;
		leg34.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.6F * f1 * 0.5F;
		leg4.rotateAngleY = 0.6662F + MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
		leg41.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.9F * f1 * 0.5F;
		leg42.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.8F * f1 * 0.5F;
		leg43.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.7F * f1 * 0.5F;
		leg44.rotateAngleY = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.6F * f1 * 0.5F;

		if (isRiding)
		{
			rightarm.rotateAngleX += -((float)Math.PI / 5F);
			leftarm.rotateAngleX += -((float)Math.PI / 5F);

			leg1.rotateAngleY = (float)Math.PI / 10F;
			leg2.rotateAngleY = -((float)Math.PI / 10F);
			leg3.rotateAngleY = (float)Math.PI / 10F;
			leg4.rotateAngleY = -((float)Math.PI / 10F);
		}

		rightarm.rotateAngleY = 0.0F;
		leftarm.rotateAngleY = 0.0F;
		float f6;
		float f7;

		if (swingProgress > -9990.0F)
		{
			f6 = swingProgress;
			body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightarm.rotateAngleY += body.rotateAngleY;
			leftarm.rotateAngleY += body.rotateAngleY;
			f6 = 1.0F - swingProgress;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			f7 = MathHelper.sin(f6 * (float)Math.PI);
			float f8 = MathHelper.sin(swingProgress * (float)Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
			rightarm.rotateAngleX = (float)(rightarm.rotateAngleX - (f7 * 1.2D + f8));
			rightarm.rotateAngleY += body.rotateAngleY * 2.0F;
			rightarm.rotateAngleZ = MathHelper.sin(swingProgress * (float)Math.PI) * -0.4F;
			leftarm.rotateAngleX = (float)(leftarm.rotateAngleX - (f7 * 1.2D + f8));
			leftarm.rotateAngleY += body.rotateAngleY * -2.0F;
			leftarm.rotateAngleZ = MathHelper.sin(swingProgress * (float)Math.PI) * 0.4F;
		}
	}
}
