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

public class ModelGatekeeperMinion extends ModelBase {

	public ModelRenderer mask1;
	public ModelRenderer maskpart1;
	public ModelRenderer maskpart2;
	public ModelRenderer mask2;
	public ModelRenderer maskpart3;
	public ModelRenderer maskpart4;
	public ModelRenderer head;
	public ModelRenderer body;
	public ModelRenderer rightshoulder;
	public ModelRenderer leftshoulder;
	public ModelRenderer rightarm1;
	public ModelRenderer rightarm2;
	public ModelRenderer leftarm1;
	public ModelRenderer leftarm2;
	public ModelRenderer tentacle1;
	public ModelRenderer tentacle2;
	public ModelRenderer tentacle3;
	public ModelRenderer tentacle4;
	public ModelRenderer rltentacle1;
	public ModelRenderer rltentacle2;
	public ModelRenderer rltentacle3;
	public ModelRenderer rltentacle4;
	public ModelRenderer lltentacle1;
	public ModelRenderer lltentacle2;
	public ModelRenderer lltentacle3;
	public ModelRenderer lltentacle4;
	public ModelRenderer lowerbody;

	public ModelGatekeeperMinion()
	{
		textureWidth = 128;
		textureHeight = 64;

		mask1 = new ModelRenderer(this, 32, 0);
		mask1.addBox(-3.4F, -8F, -6F, 6, 8, 1);
		mask1.setRotationPoint(0F, 0F, 0F);
		mask1.setTextureSize(128, 64);
		mask1.mirror = true;
		setRotation(mask1, 0F, -0.5235988F, 0F);
		maskpart1 = new ModelRenderer(this, 26, 0);
		maskpart1.addBox(1.6F, -8F, -7F, 1, 1, 1);
		maskpart1.setRotationPoint(0F, 0F, 0F);
		maskpart1.setTextureSize(128, 64);
		maskpart1.mirror = true;
		setRotation(maskpart1, 0F, -0.5235988F, 0F);
		maskpart2 = new ModelRenderer(this, 26, 0);
		maskpart2.addBox(1.6F, -1F, -7F, 1, 1, 1);
		maskpart2.setRotationPoint(0F, 0F, 0F);
		maskpart2.setTextureSize(128, 64);
		maskpart2.mirror = true;
		setRotation(maskpart2, 0F, -0.5235988F, 0F);
		mask2 = new ModelRenderer(this, 32, 0);
		mask2.addBox(-2.6F, -8F, -6F, 6, 8, 1);
		mask2.setRotationPoint(0F, 0F, 0F);
		mask2.setTextureSize(128, 64);
		mask2.mirror = true;
		setRotation(mask2, 0F, 0.5235988F, 0F);
		maskpart3 = new ModelRenderer(this, 26, 0);
		maskpart3.addBox(-2.6F, -8F, -7F, 1, 1, 1);
		maskpart3.setRotationPoint(0F, 0F, 0F);
		maskpart3.setTextureSize(128, 64);
		maskpart3.mirror = true;
		setRotation(maskpart3, 0F, 0.5235988F, 0F);
		maskpart4 = new ModelRenderer(this, 26, 0);
		maskpart4.addBox(-2.6F, -1F, -7F, 1, 1, 1);
		maskpart4.setRotationPoint(0F, 0F, 0F);
		maskpart4.setTextureSize(128, 64);
		maskpart4.mirror = true;
		setRotation(maskpart4, 0F, 0.5235988F, 0F);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4F, -8F, -4F, 8, 8, 8);
		head.setRotationPoint(0F, -12F, 0F);
		head.setTextureSize(128, 64);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		head.addChild(mask1);
		head.addChild(mask2);
		head.addChild(maskpart1);
		head.addChild(maskpart2);
		head.addChild(maskpart3);
		head.addChild(maskpart4);
		body = new ModelRenderer(this, 16, 16);
		body.addBox(0F, 0F, 0F, 8, 22, 6);
		body.setRotationPoint(-4F, -12F, -3F);
		body.setTextureSize(128, 64);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		rightshoulder = new ModelRenderer(this, 44, 9);
		rightshoulder.addBox(-7F, -2F, -2F, 7, 4, 6);
		rightshoulder.setRotationPoint(0.5F, 2F, 2F);
		rightshoulder.setTextureSize(128, 64);
		rightshoulder.mirror = true;
		setRotation(rightshoulder, 0F, 0F, -0.1745329F);
		body.addChild(rightshoulder);
		leftshoulder = new ModelRenderer(this, 44, 9);
		leftshoulder.addBox(0F, -2F, -2F, 7, 4, 6);
		leftshoulder.setRotationPoint(7.5F, 2F, 2F);
		leftshoulder.setTextureSize(128, 64);
		leftshoulder.mirror = true;
		setRotation(leftshoulder, 0F, 0F, 0.1745329F);
		body.addChild(leftshoulder);
		rightarm1 = new ModelRenderer(this, 0, 20);
		rightarm1.addBox(-4F, -2F, -2F, 4, 8, 4);
		rightarm1.setRotationPoint(-5F, -8F, 0F);
		rightarm1.setTextureSize(128, 64);
		rightarm1.mirror = true;
		setRotation(rightarm1, -0.3839724F, 0F, 0F);
		rightarm2 = new ModelRenderer(this, 0, 26);
		rightarm2.addBox(-4F, 5.5F, -1F, 4, 10, 4);
		rightarm2.setRotationPoint(0,0,0);
		rightarm2.setTextureSize(128, 64);
		rightarm2.mirror = true;
		setRotation(rightarm2, -0.5585054F - rightarm1.rotateAngleX, 0F, 0F);
		rightarm1.addChild(rightarm2);
		leftarm1 = new ModelRenderer(this, 0, 20);
		leftarm1.addBox(0F, -2F, -2F, 4, 8, 4);
		leftarm1.setRotationPoint(5F, -8F, 0F);
		leftarm1.setTextureSize(128, 64);
		leftarm1.mirror = true;
		setRotation(leftarm1, -0.3839724F, 0F, 0F);
		leftarm2 = new ModelRenderer(this, 0, 26);
		leftarm2.addBox(0F, 5.5F, -1F, 4, 4, 4);
		leftarm2.setRotationPoint(0,0,0);
		leftarm2.setTextureSize(128, 64);
		leftarm2.mirror = true;
		setRotation(leftarm2, -0.5585054F - leftarm1.rotateAngleX, 0F, 0F);
		leftarm1.addChild(leftarm2);
		tentacle1 = new ModelRenderer(this, 0, 46);
		tentacle1.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle1.setRotationPoint(1F, 9F, 2F);
		tentacle1.setTextureSize(128, 64);
		tentacle1.mirror = true;
		setRotation(tentacle1, 0, 0F, 0F);
		tentacle2 = new ModelRenderer(this, 0, 46);
		tentacle2.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle2.setRotationPoint(3F, 9F, 2F);
		tentacle2.setTextureSize(128, 64);
		tentacle2.mirror = true;
		setRotation(tentacle2, 0, 0F, 0F);
		tentacle3 = new ModelRenderer(this, 0, 46);
		tentacle3.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle3.setRotationPoint(1F, 9F, 0F);
		tentacle3.setTextureSize(128, 64);
		tentacle3.mirror = true;
		setRotation(tentacle3, 0, 0F, 0F);
		tentacle4 = new ModelRenderer(this, 0, 46);
		tentacle4.addBox(-1F, 0F, -1F, 2, 6, 2);
		tentacle4.setRotationPoint(3F, 9F, 0F);
		tentacle4.setTextureSize(128, 64);
		tentacle4.mirror = true;
		setRotation(tentacle4, 0, 0F, 0F);
		rltentacle1 = new ModelRenderer(this, 0, 46);
		rltentacle1.addBox(-1F, 0F, -1F, 2, 6, 2);
		rltentacle1.setRotationPoint(-3F, 18F, 1F);
		rltentacle1.setTextureSize(128, 64);
		rltentacle1.mirror = true;
		leftarm2.addChild(tentacle1);
		leftarm2.addChild(tentacle2);
		leftarm2.addChild(tentacle3);
		leftarm2.addChild(tentacle4);
		setRotation(rltentacle1, 0F, 0F, 0F);
		rltentacle2 = new ModelRenderer(this, 0, 46);
		rltentacle2.addBox(-1F, 0F, -1F, 2, 6, 2);
		rltentacle2.setRotationPoint(-3F, 18F, -1F);
		rltentacle2.setTextureSize(128, 64);
		rltentacle2.mirror = true;
		setRotation(rltentacle2, 0F, 0F, 0F);
		rltentacle3 = new ModelRenderer(this, 0, 46);
		rltentacle3.addBox(-1F, 0F, -1F, 2, 6, 2);
		rltentacle3.setRotationPoint(-1F, 18F, 1F);
		rltentacle3.setTextureSize(128, 64);
		rltentacle3.mirror = true;
		setRotation(rltentacle3, 0F, 0F, 0F);
		rltentacle4 = new ModelRenderer(this, 0, 46);
		rltentacle4.addBox(-1F, 0F, -1F, 2, 6, 2);
		rltentacle4.setRotationPoint(-1F, 18F, -1F);
		rltentacle4.setTextureSize(128, 64);
		rltentacle4.mirror = true;
		setRotation(rltentacle4, 0F, 0F, 0F);
		lltentacle1 = new ModelRenderer(this, 0, 46);
		lltentacle1.addBox(-1F, 0F, -1F, 2, 6, 2);
		lltentacle1.setRotationPoint(1F, 18F, 1F);
		lltentacle1.setTextureSize(128, 64);
		lltentacle1.mirror = true;
		setRotation(lltentacle1, 0F, 0F, 0F);
		lltentacle2 = new ModelRenderer(this, 0, 46);
		lltentacle2.addBox(-1F, 0F, -1F, 2, 6, 2);
		lltentacle2.setRotationPoint(1F, 18F, -1F);
		lltentacle2.setTextureSize(128, 64);
		lltentacle2.mirror = true;
		setRotation(lltentacle2, 0F, 0F, 0F);
		lltentacle3 = new ModelRenderer(this, 0, 46);
		lltentacle3.addBox(-1F, 0F, -1F, 2, 6, 2);
		lltentacle3.setRotationPoint(3F, 18F, 1F);
		lltentacle3.setTextureSize(128, 64);
		lltentacle3.mirror = true;
		setRotation(lltentacle3, 0F, 0F, 0F);
		lltentacle4 = new ModelRenderer(this, 0, 46);
		lltentacle4.addBox(-1F, 0F, -1F, 2, 6, 2);
		lltentacle4.setRotationPoint(3F, 18F, -1F);
		lltentacle4.setTextureSize(128, 64);
		lltentacle4.mirror = true;
		setRotation(lltentacle4, 0F, 0F, 0F);
		lowerbody = new ModelRenderer(this, 8, 44);
		lowerbody.addBox(0F, 0F, 0F, 8, 8, 4);
		lowerbody.setRotationPoint(0F, 22F, 1F);
		lowerbody.setTextureSize(128, 64);
		lowerbody.mirror = true;
		setRotation(lowerbody, 0F, 0F, 0F);
		body.addChild(lowerbody);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		body.render(f5);
		rightarm1.render(f5);
		leftarm1.render(f5);
		//    tentacle1.render(f5);
		//    tentacle2.render(f5);
		//    tentacle3.render(f5);
		//    tentacle4.render(f5);
		rltentacle1.render(f5);
		rltentacle2.render(f5);
		rltentacle3.render(f5);
		rltentacle4.render(f5);
		lltentacle1.render(f5);
		lltentacle2.render(f5);
		lltentacle3.render(f5);
		lltentacle4.render(f5);
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

		rightarm1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
		leftarm1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;

		rightarm1.rotateAngleZ = 0.0F;
		leftarm1.rotateAngleZ = 0.0F;

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

		rltentacle1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rltentacle1.rotateAngleZ = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rltentacle1.rotateAngleY = 0.0F;
		rltentacle2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rltentacle2.rotateAngleZ = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rltentacle2.rotateAngleY = 0.0F;
		rltentacle3.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rltentacle3.rotateAngleZ = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rltentacle3.rotateAngleY = 0.0F;
		rltentacle4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rltentacle4.rotateAngleZ = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rltentacle4.rotateAngleY = 0.0F;

		lltentacle1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		lltentacle1.rotateAngleZ = -MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		lltentacle1.rotateAngleY = 0.0F;
		lltentacle2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		lltentacle2.rotateAngleZ = -MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		lltentacle2.rotateAngleY = 0.0F;
		lltentacle3.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		lltentacle3.rotateAngleZ = -MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		lltentacle3.rotateAngleY = 0.0F;
		lltentacle4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		lltentacle4.rotateAngleZ = -MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		lltentacle4.rotateAngleY = 0.0F;

		rightarm1.rotateAngleY = 0.0F;
		leftarm1.rotateAngleY = 0.0F;
		float f6;
		float f7;

		if (swingProgress > -9990.0F)
		{
			f6 = swingProgress;
			body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightarm1.rotateAngleY += body.rotateAngleY;
			leftarm1.rotateAngleY += body.rotateAngleY;
			f6 = 1.0F - swingProgress;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			f7 = MathHelper.sin(f6 * (float)Math.PI);
			float f8 = MathHelper.sin(swingProgress * (float)Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
			rightarm1.rotateAngleX = (float)(rightarm1.rotateAngleX - (f7 * 1.2D + f8));
			rightarm1.rotateAngleY += body.rotateAngleY * 2.0F;
			rightarm1.rotateAngleZ = MathHelper.sin(swingProgress * (float)Math.PI) * -0.4F;
			leftarm1.rotateAngleX = (float)(leftarm1.rotateAngleX - (f7 * 1.2D + f8));
			leftarm1.rotateAngleY += body.rotateAngleY * -2.0F;
			leftarm1.rotateAngleZ = MathHelper.sin(swingProgress * (float)Math.PI) * 0.4F;
		}
	}
}
