/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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
	public ModelRenderer leftarm;
	public ModelRenderer tentacle1;
	public ModelRenderer tentacle2;
	public ModelRenderer tentacle3;
	public ModelRenderer tentacle4;
	public ModelRenderer rightarm;
	public ModelRenderer rightLegJoint;
	public ModelRenderer leftLegJoint;
	public ModelRenderer rightLegT1;
	public ModelRenderer rightLegT2;
	public ModelRenderer rightLegT3;
	public ModelRenderer rightLegT4;
	public ModelRenderer rightLegB1;
	public ModelRenderer rightLegB2;
	public ModelRenderer rightLegB3;
	public ModelRenderer rightLegB4;
	public ModelRenderer leftLegT1;
	public ModelRenderer leftLegT2;
	public ModelRenderer leftLegT3;
	public ModelRenderer leftLegT4;
	public ModelRenderer leftLegB1;
	public ModelRenderer leftLegB2;
	public ModelRenderer leftLegB3;
	public ModelRenderer leftLegB4;

	public ModelRemnant()
	{
		textureWidth = 128;
		textureHeight = 64;

		mask1 = new ModelRenderer(this, 32, 0);
		mask1.mirror = true;
		mask1.addBox(-3.4F, -8F, -6F, 6, 8, 1);
		mask1.setRotationPoint(0F, 0F, 0F);
		mask1.setTextureSize(128, 64);
		setRotation(mask1, 0F, -0.5235988F, 0F);
		mask2 = new ModelRenderer(this, 32, 0);
		mask2.addBox(-2.6F, -8F, -6F, 6, 8, 1);
		mask2.setRotationPoint(0F, 0F, 0F);
		mask2.setTextureSize(128, 64);
		setRotation(mask2, 0F, 0.5235988F, 0F);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4F, -8F, -4F, 8, 8, 8);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(128, 64);
		setRotation(head, 0F, 0F, 0F);
		head.addChild(mask1);
		head.addChild(mask2);
		body = new ModelRenderer(this, 16, 16);
		body.addBox(-4F, 0F, -2F, 8, 16, 4);
		body.setRotationPoint(0F, 0F, 0F);
		body.setTextureSize(128, 64);
		setRotation(body, 0F, 0F, 0F);
		leftarm = new ModelRenderer(this, 0, 20);
		leftarm.mirror = true;
		leftarm.addBox(0F, -2F, -2F, 4, 10, 4);
		leftarm.setRotationPoint(4F, 2F, 0F);
		leftarm.setTextureSize(128, 64);
		setRotation(leftarm, 0F, 0F, 0F);
		tentacle1 = new ModelRenderer(this, 0, 46);
		tentacle1.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle1.setRotationPoint(1F, 8F, 1F);
		tentacle1.setTextureSize(128, 64);
		setRotation(tentacle1, 0F, 0F, 0F);
		tentacle2 = new ModelRenderer(this, 0, 46);
		tentacle2.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle2.setRotationPoint(3F, 8F, 1F);
		tentacle2.setTextureSize(128, 64);
		setRotation(tentacle2, 0F, 0F, 0F);
		tentacle3 = new ModelRenderer(this, 0, 46);
		tentacle3.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle3.setRotationPoint(1F, 8F, -1F);
		tentacle3.setTextureSize(128, 64);
		setRotation(tentacle3, 0F, 0F, 0F);
		tentacle4 = new ModelRenderer(this, 0, 46);
		tentacle4.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle4.setRotationPoint(3F, 8F, -1F);
		tentacle4.setTextureSize(128, 64);
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
		leftLegB1 = new ModelRenderer(this, 22, 38);
		leftLegB1.setRotationPoint(0.0F, 4.0F, 0.0F);
		leftLegB1.addBox(-2.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
		rightLegJoint = new ModelRenderer(this, 0, 0);
		rightLegJoint.setRotationPoint(-2.0F, 16.0F, 0.0F);
		rightLegJoint.addBox(-0.5F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
		rightLegT1 = new ModelRenderer(this, 18, 34);
		rightLegT1.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightLegT1.addBox(-2.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
		leftLegB3 = new ModelRenderer(this, 28, 38);
		leftLegB3.setRotationPoint(0.0F, 4.0F, 0.0F);
		leftLegB3.addBox(-2.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
		rightLegB2 = new ModelRenderer(this, 20, 38);
		rightLegB2.setRotationPoint(0.0F, 4.0F, 0.0F);
		rightLegB2.addBox(0.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
		rightLegB1 = new ModelRenderer(this, 18, 38);
		rightLegB1.setRotationPoint(0.0F, 4.0F, 0.0F);
		rightLegB1.addBox(-2.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
		leftLegT2 = new ModelRenderer(this, 24, 34);
		leftLegT2.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftLegT2.addBox(0.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
		leftLegT4 = new ModelRenderer(this, 26, 38);
		leftLegT4.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftLegT4.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
		leftLegT3 = new ModelRenderer(this, 28, 38);
		leftLegT3.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftLegT3.addBox(-2.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
		rightLegB3 = new ModelRenderer(this, 32, 38);
		rightLegB3.setRotationPoint(0.0F, 4.0F, 0.0F);
		rightLegB3.addBox(-2.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
		leftLegJoint = new ModelRenderer(this, 0, 0);
		leftLegJoint.setRotationPoint(2.0F, 16.0F, 0.0F);
		leftLegJoint.addBox(-0.5F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
		rightLegT3 = new ModelRenderer(this, 32, 34);
		rightLegT3.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightLegT3.addBox(-2.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
		leftLegT1 = new ModelRenderer(this, 22, 34);
		leftLegT1.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftLegT1.addBox(-2.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
		leftLegB2 = new ModelRenderer(this, 24, 38);
		leftLegB2.setRotationPoint(0.0F, 4.0F, 0.0F);
		leftLegB2.addBox(0.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
		rightLegB4 = new ModelRenderer(this, 30, 38);
		rightLegB4.setRotationPoint(0.0F, 4.0F, 0.0F);
		rightLegB4.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
		rightLegT4 = new ModelRenderer(this, 30, 34);
		rightLegT4.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightLegT4.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
		leftLegB4 = new ModelRenderer(this, 26, 38);
		leftLegB4.setRotationPoint(0.0F, 4.0F, 0.0F);
		leftLegB4.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
		rightLegT2 = new ModelRenderer(this, 20, 34);
		rightLegT2.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightLegT2.addBox(0.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
		leftLegT1.addChild(leftLegB1);
		rightLegJoint.addChild(rightLegT1);
		leftLegT3.addChild(leftLegB3);
		rightLegT2.addChild(rightLegB2);
		rightLegT1.addChild(rightLegB1);
		leftLegJoint.addChild(leftLegT2);
		leftLegJoint.addChild(leftLegT4);
		leftLegJoint.addChild(leftLegT3);
		rightLegT3.addChild(rightLegB3);
		rightLegJoint.addChild(rightLegT3);
		leftLegJoint.addChild(leftLegT1);
		leftLegT2.addChild(leftLegB2);
		rightLegT4.addChild(rightLegB4);
		rightLegJoint.addChild(rightLegT4);
		leftLegT4.addChild(leftLegB4);
		rightLegJoint.addChild(rightLegT2);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		rightLegJoint.render(f5);
		rightarm.render(f5);
		leftarm.render(f5);
		leftLegJoint.render(f5);
		head.render(f5);
		body.render(f5);
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

		rightLegJoint.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1 * 0.5F;
		leftLegJoint.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1 * 0.5F;
		rightLegJoint.rotateAngleY = 0.0F;
		leftLegJoint.rotateAngleY = 0.0F;

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

		float flap = MathHelper.sin(entity.ticksExisted * 0.2F) * 0.3F;
		float flap2 = MathHelper.cos(entity.ticksExisted * 0.2F) * 0.4F;

		leftLegT1.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F + 0.3f) * f1;
		leftLegT1.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		leftLegT3.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F - 0.3f) * f1;
		leftLegT3.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		leftLegT4.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F + 0.3F) * f1;
		leftLegT4.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		leftLegT2.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F - 0.3F) * f1;
		leftLegT2.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		rightLegT1.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F - 0.3F) * f1;
		rightLegT1.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		rightLegT3.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F + 0.3F) * f1;
		rightLegT3.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		rightLegT4.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F - 0.3F) * f1;
		rightLegT4.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		rightLegT2.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F + 0.3F) * f1;
		rightLegT2.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		rightLegB1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rightLegB1.rotateAngleZ = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rightLegB1.rotateAngleY = 0.0F;
		rightLegB2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rightLegB2.rotateAngleZ = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rightLegB2.rotateAngleY = 0.0F;
		rightLegB3.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rightLegB3.rotateAngleZ = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rightLegB3.rotateAngleY = 0.0F;
		rightLegB4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rightLegB4.rotateAngleZ = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rightLegB4.rotateAngleY = 0.0F;

		leftLegB1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leftLegB1.rotateAngleZ = -MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leftLegB1.rotateAngleY = 0.0F;
		leftLegB2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		leftLegB2.rotateAngleZ = -MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		leftLegB2.rotateAngleY = 0.0F;
		leftLegB3.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		leftLegB3.rotateAngleZ = -MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		leftLegB3.rotateAngleY = 0.0F;
		leftLegB4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leftLegB4.rotateAngleZ = -MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leftLegB4.rotateAngleY = 0.0F;

		if (isRiding)
		{
			rightarm.rotateAngleX += -((float)Math.PI / 5F);
			leftarm.rotateAngleX += -((float)Math.PI / 5F);

			rightLegJoint.rotateAngleX = -((float)Math.PI * 2F / 5F);
			leftLegJoint.rotateAngleX = -((float)Math.PI * 2F / 5F);

			rightLegJoint.rotateAngleY = (float)Math.PI / 10F;
			leftLegJoint.rotateAngleY = -((float)Math.PI / 10F);
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
