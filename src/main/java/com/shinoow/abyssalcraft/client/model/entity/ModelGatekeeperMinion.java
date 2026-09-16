/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2026 Shinoow.
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

	public ModelRenderer body;
	public ModelRenderer rightarm1;
	public ModelRenderer head;
	public ModelRenderer leftarm1;
	public ModelRenderer leftLegJoint;
	public ModelRenderer rightLegJoint;
	public ModelRenderer rightshoulder;
	public ModelRenderer leftshoulder;
	public ModelRenderer lowerbody;
	public ModelRenderer rightarm2;
	public ModelRenderer leftmask;
	public ModelRenderer rightmask;
	public ModelRenderer peg1;
	public ModelRenderer peg2;
	public ModelRenderer peg1_1;
	public ModelRenderer peg2_1;
	public ModelRenderer leftarm2;
	public ModelRenderer tentacle1;
	public ModelRenderer tentacle2;
	public ModelRenderer tentacle3;
	public ModelRenderer tentacle4;
	public ModelRenderer lltentacle1;
	public ModelRenderer lltentacle2;
	public ModelRenderer lltentacle3;
	public ModelRenderer lltentacle4;
	public ModelRenderer lltentacle1_1;
	public ModelRenderer lltentacle2_1;
	public ModelRenderer lltentacle3_1;
	public ModelRenderer lltentacle4_1;
	public ModelRenderer rltentacle1;
	public ModelRenderer rltentacle2;
	public ModelRenderer rltentacle3;
	public ModelRenderer rltentacle4;
	public ModelRenderer rltentacle1_1;
	public ModelRenderer rltentacle2_1;
	public ModelRenderer rltentacle3_1;
	public ModelRenderer rltentacle4_1;

	public ModelGatekeeperMinion() {
		textureWidth = 128;
		textureHeight = 64;
		leftarm1 = new ModelRenderer(this, 0, 20);
		leftarm1.mirror = true;
		leftarm1.setRotationPoint(5.0F, -8.0F, 0.0F);
		leftarm1.addBox(0.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
		leftshoulder = new ModelRenderer(this, 44, 9);
		leftshoulder.mirror = true;
		leftshoulder.setRotationPoint(7.5F, 2.0F, 2.01F);
		leftshoulder.addBox(0.0F, -2.0F, -2.0F, 7, 4, 6, 0.0F);
		setRotateAngle(leftshoulder, 0.0F, 0.0F, 0.17453292519943295F);
		peg2 = new ModelRenderer(this, 26, 0);
		peg2.mirror = true;
		peg2.setRotationPoint(0.0F, 0.0F, 0.0F);
		peg2.addBox(1.6F, -1.0F, -7.0F, 1, 1, 1, 0.0F);
		lltentacle2_1 = new ModelRenderer(this, 0, 46);
		lltentacle2_1.setRotationPoint(0.0F, 6.0F, 0.0F);
		lltentacle2_1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		rltentacle2_1 = new ModelRenderer(this, 0, 46);
		rltentacle2_1.setRotationPoint(0.0F, 6.0F, 0.0F);
		rltentacle2_1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		tentacle2 = new ModelRenderer(this, 0, 46);
		tentacle2.setRotationPoint(3.0F, 9.0F, 2.0F);
		tentacle2.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		setRotateAngle(tentacle2, 0.0F, 0.0F, 0.11344640137963141F);
		tentacle4 = new ModelRenderer(this, 0, 46);
		tentacle4.setRotationPoint(3.0F, 9.0F, 0.0F);
		tentacle4.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		setRotateAngle(tentacle4, 0.1832595714594046F, 0.0F, 0.0F);
		peg2_1 = new ModelRenderer(this, 26, 0);
		peg2_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		peg2_1.addBox(-2.6F, -1.0F, -7.0F, 1, 1, 1, 0.0F);
		rightarm1 = new ModelRenderer(this, 0, 20);
		rightarm1.setRotationPoint(-5.0F, -8.0F, 0.0F);
		rightarm1.addBox(-4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
		peg1_1 = new ModelRenderer(this, 26, 0);
		peg1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		peg1_1.addBox(-2.6F, -8.0F, -7.0F, 1, 1, 1, 0.0F);
		lltentacle4_1 = new ModelRenderer(this, 0, 46);
		lltentacle4_1.setRotationPoint(0.0F, 6.0F, 0.0F);
		lltentacle4_1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		leftmask = new ModelRenderer(this, 32, 0);
		leftmask.mirror = true;
		leftmask.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftmask.addBox(-3.4F, -8.0F, -6.0F, 6, 8, 1, 0.0F);
		setRotateAngle(leftmask, 0.0F, -0.5235987755982988F, 0.0F);
		lltentacle1_1 = new ModelRenderer(this, 0, 46);
		lltentacle1_1.setRotationPoint(0.0F, 6.0F, 0.0F);
		lltentacle1_1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		rltentacle4 = new ModelRenderer(this, 12, 48);
		rltentacle4.setRotationPoint(1.0F, 0.0F, -1.0F);
		rltentacle4.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		lltentacle3_1 = new ModelRenderer(this, 0, 46);
		lltentacle3_1.setRotationPoint(0.0F, 6.0F, 0.0F);
		lltentacle3_1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		rightarm2 = new ModelRenderer(this, 0, 26);
		rightarm2.setRotationPoint(0.01F, 0.0F, 0.0F);
		rightarm2.addBox(-4.0F, 5.5F, -1.0F, 4, 10, 4, 0.0F);
		setRotateAngle(rightarm2, -0.17453292519943295F, 0.0F, 0.0F);
		head = new ModelRenderer(this, 0, 0);
		head.setRotationPoint(0.0F, -12.0F, 0.0F);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		rightshoulder = new ModelRenderer(this, 44, 9);
		rightshoulder.setRotationPoint(0.5F, 2.0F, 2.01F);
		rightshoulder.addBox(-7.0F, -2.0F, -2.0F, 7, 4, 6, 0.0F);
		setRotateAngle(rightshoulder, 0.0F, 0.0F, -0.17453292519943295F);
		rltentacle1_1 = new ModelRenderer(this, 0, 46);
		rltentacle1_1.setRotationPoint(0.0F, 6.0F, 0.0F);
		rltentacle1_1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		lltentacle4 = new ModelRenderer(this, 16, 48);
		lltentacle4.setRotationPoint(1.0F, 0.0F, -1.0F);
		lltentacle4.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		rltentacle2 = new ModelRenderer(this, 10, 48);
		rltentacle2.setRotationPoint(-1.0F, 0.0F, -1.0F);
		rltentacle2.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		rltentacle4_1 = new ModelRenderer(this, 0, 46);
		rltentacle4_1.setRotationPoint(0.0F, 6.0F, 0.0F);
		rltentacle4_1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		rltentacle1 = new ModelRenderer(this, 8, 48);
		rltentacle1.setRotationPoint(-1.0F, 0.0F, 1.0F);
		rltentacle1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		rightmask = new ModelRenderer(this, 32, 0);
		rightmask.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightmask.addBox(-2.6F, -8.0F, -6.0F, 6, 8, 1, 0.0F);
		setRotateAngle(rightmask, 0.0F, 0.5235987755982988F, 0.0F);
		rltentacle3 = new ModelRenderer(this, 10, 48);
		rltentacle3.setRotationPoint(1.0F, 0.0F, 1.0F);
		rltentacle3.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		rightLegJoint = new ModelRenderer(this, 0, 0);
		rightLegJoint.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightLegJoint.addBox(-0.5F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
		leftLegJoint = new ModelRenderer(this, 0, 0);
		leftLegJoint.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftLegJoint.addBox(-0.5F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
		rltentacle3_1 = new ModelRenderer(this, 0, 46);
		rltentacle3_1.setRotationPoint(0.0F, 6.0F, 0.0F);
		rltentacle3_1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		tentacle1 = new ModelRenderer(this, 0, 46);
		tentacle1.setRotationPoint(1.0F, 9.0F, 2.0F);
		tentacle1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		setRotateAngle(tentacle1, 0.1832595714594046F, 0.0F, 0.0F);
		body = new ModelRenderer(this, 16, 16);
		body.setRotationPoint(-4.0F, -12.0F, -3.0F);
		body.addBox(0.0F, 0.0F, 0.0F, 8, 22, 6, 0.0F);
		leftarm2 = new ModelRenderer(this, 0, 26);
		leftarm2.mirror = true;
		leftarm2.setRotationPoint(-0.01F, 0.0F, 0.0F);
		leftarm2.addBox(0.0F, 5.5F, -1.0F, 4, 4, 4, 0.0F);
		setRotateAngle(leftarm2, -0.17453292519943295F, 0.0F, 0.0F);
		lltentacle1 = new ModelRenderer(this, 14, 48);
		lltentacle1.setRotationPoint(-1.0F, 0.0F, 1.0F);
		lltentacle1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		lowerbody = new ModelRenderer(this, 8, 44);
		lowerbody.setRotationPoint(0.0F, 22.0F, 1.0F);
		lowerbody.addBox(0.0F, 0.0F, 0.0F, 8, 2, 4, 0.0F);
		tentacle3 = new ModelRenderer(this, 0, 46);
		tentacle3.setRotationPoint(1.0F, 9.0F, 0.0F);
		tentacle3.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		setRotateAngle(tentacle3, 0.0F, 0.0F, 0.11344640137963141F);
		lltentacle3 = new ModelRenderer(this, 18, 48);
		lltentacle3.setRotationPoint(1.0F, 0.0F, 1.0F);
		lltentacle3.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		peg1 = new ModelRenderer(this, 26, 0);
		peg1.mirror = true;
		peg1.setRotationPoint(0.0F, 0.0F, 0.0F);
		peg1.addBox(1.6F, -8.0F, -7.0F, 1, 1, 1, 0.0F);
		lltentacle2 = new ModelRenderer(this, 14, 48);
		lltentacle2.setRotationPoint(-1.0F, 0.0F, -1.0F);
		lltentacle2.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		body.addChild(leftshoulder);
		leftmask.addChild(peg2);
		lltentacle2.addChild(lltentacle2_1);
		rltentacle2.addChild(rltentacle2_1);
		leftarm2.addChild(tentacle2);
		leftarm2.addChild(tentacle4);
		rightmask.addChild(peg2_1);
		rightmask.addChild(peg1_1);
		lltentacle4.addChild(lltentacle4_1);
		head.addChild(leftmask);
		lltentacle1.addChild(lltentacle1_1);
		rightLegJoint.addChild(rltentacle4);
		lltentacle3.addChild(lltentacle3_1);
		rightarm1.addChild(rightarm2);
		body.addChild(rightshoulder);
		rltentacle1.addChild(rltentacle1_1);
		leftLegJoint.addChild(lltentacle4);
		rightLegJoint.addChild(rltentacle2);
		rltentacle4.addChild(rltentacle4_1);
		rightLegJoint.addChild(rltentacle1);
		head.addChild(rightmask);
		rightLegJoint.addChild(rltentacle3);
		rltentacle3.addChild(rltentacle3_1);
		leftarm2.addChild(tentacle1);
		leftarm1.addChild(leftarm2);
		leftLegJoint.addChild(lltentacle1);
		body.addChild(lowerbody);
		leftarm2.addChild(tentacle3);
		leftLegJoint.addChild(lltentacle3);
		leftmask.addChild(peg1);
		leftLegJoint.addChild(lltentacle2);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
		leftarm1.render(f5);
		rightarm1.render(f5);
		head.render(f5);
		rightLegJoint.render(f5);
		leftLegJoint.render(f5);
		body.render(f5);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
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

		rightLegJoint.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1 * 0.5F;
		leftLegJoint.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1 * 0.5F;
		rightLegJoint.rotateAngleY = 0.0F;
		leftLegJoint.rotateAngleY = 0.0F;

		float flap = MathHelper.sin(entity.ticksExisted * 0.2F) * 0.3F;
		float flap2 = MathHelper.cos(entity.ticksExisted * 0.2F) * 0.4F;

		lltentacle1.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F + 0.3f) * f1;
		lltentacle1.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		lltentacle3.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F - 0.3f) * f1;
		lltentacle3.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		lltentacle4.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F + 0.3F) * f1;
		lltentacle4.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		lltentacle2.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F - 0.3F) * f1;
		lltentacle2.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		rltentacle1.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F - 0.3F) * f1;
		rltentacle1.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		rltentacle3.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F + 0.3F) * f1;
		rltentacle3.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		rltentacle4.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F - 0.3F) * f1;
		rltentacle4.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		rltentacle2.rotateAngleY = (flap * 10.5F * (float)Math.PI / 180.0F + 0.3F) * f1;
		rltentacle2.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F * f1;

		rltentacle1_1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rltentacle1_1.rotateAngleZ = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rltentacle1_1.rotateAngleY = 0.0F;
		rltentacle2_1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rltentacle2_1.rotateAngleZ = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rltentacle2_1.rotateAngleY = 0.0F;
		rltentacle3_1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rltentacle3_1.rotateAngleZ = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		rltentacle3_1.rotateAngleY = 0.0F;
		rltentacle4_1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rltentacle4_1.rotateAngleZ = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		rltentacle4_1.rotateAngleY = 0.0F;

		lltentacle1_1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		lltentacle1_1.rotateAngleZ = -MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		lltentacle1_1.rotateAngleY = 0.0F;
		lltentacle2_1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		lltentacle2_1.rotateAngleZ = -MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		lltentacle2_1.rotateAngleY = 0.0F;
		lltentacle3_1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		lltentacle3_1.rotateAngleZ = -MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		lltentacle3_1.rotateAngleY = 0.0F;
		lltentacle4_1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		lltentacle4_1.rotateAngleZ = -MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		lltentacle4_1.rotateAngleY = 0.0F;

		if (isRiding)
		{
			rightarm1.rotateAngleX += -((float)Math.PI / 5F);
			leftarm1.rotateAngleX += -((float)Math.PI / 5F);

			rightLegJoint.rotateAngleX = -((float)Math.PI * 2F / 5F);
			leftLegJoint.rotateAngleX = -((float)Math.PI * 2F / 5F);

			rightLegJoint.rotateAngleY = (float)Math.PI / 10F;
			leftLegJoint.rotateAngleY = -((float)Math.PI / 10F);
		}

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
