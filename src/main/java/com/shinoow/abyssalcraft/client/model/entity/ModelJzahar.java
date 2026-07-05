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

import com.shinoow.abyssalcraft.common.entity.EntityJzahar;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelJzahar extends ModelBase {

	public ModelRenderer abyssalnomicon;
	public ModelRenderer body;
	public ModelRenderer staff1;
	public ModelRenderer robeRightInner;
	public ModelRenderer robeRightOuter;
	public ModelRenderer robeLeftInner;
	public ModelRenderer robeLeftOuter;
	public ModelRenderer eye1;
	public ModelRenderer head;
	public ModelRenderer tentacle1;
	public ModelRenderer tentacle2;
	public ModelRenderer tentacle3;
	public ModelRenderer tentacle4;
	public ModelRenderer tentacle5;
	public ModelRenderer tentacle6;
	public ModelRenderer tentacle7;
	public ModelRenderer tentacle8;
	public ModelRenderer eye2;
	public ModelRenderer maskRight;
	public ModelRenderer maskLeft;
	public ModelRenderer fT1;
	public ModelRenderer fT2;
	public ModelRenderer fT3;
	public ModelRenderer fT12;
	public ModelRenderer fT13;
	public ModelRenderer fT22;
	public ModelRenderer fT23;
	public ModelRenderer fT32;
	public ModelRenderer fT33;
	public ModelRenderer tentacle12;
	public ModelRenderer tentacle13;
	public ModelRenderer foot1;
	public ModelRenderer tentacle22;
	public ModelRenderer tentacle23;
	public ModelRenderer foot2;
	public ModelRenderer tentacle32;
	public ModelRenderer tentacle33;
	public ModelRenderer foot3;
	public ModelRenderer tentacle42;
	public ModelRenderer tentacle43;
	public ModelRenderer foot4;
	public ModelRenderer tentacle52;
	public ModelRenderer tentacle53;
	public ModelRenderer foot5;
	public ModelRenderer tentacle62;
	public ModelRenderer tentacle63;
	public ModelRenderer foot6;
	public ModelRenderer tentacle72;
	public ModelRenderer tentacle73;
	public ModelRenderer foot7;
	public ModelRenderer tentacle82;
	public ModelRenderer tentacle83;
	public ModelRenderer foot6_1;
	public ModelRenderer staff2;
	public ModelRenderer staff3;
	public ModelRenderer staff4;
	public ModelRenderer staff5;
	public ModelRenderer staff6;
	public ModelRenderer cube;

	public ModelJzahar() {
		textureWidth = 128;
		textureHeight = 64;
		fT2 = new ModelRenderer(this, 116, 0);
		fT2.setRotationPoint(0.0F, -2.0F, -6.0F);
		fT2.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		tentacle7 = new ModelRenderer(this, 0, 27);
		tentacle7.setRotationPoint(13.5F, 22.0F, 3.0F);
		tentacle7.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		setRotateAngle(tentacle7, 0.08726646259971647F, 0.3490658503988659F, 0.0F);
		robeRightOuter = new ModelRenderer(this, 46, 0);
		robeRightOuter.setRotationPoint(0.0F, 0.0F, -2.0F);
		robeRightOuter.addBox(0.0F, 0.0F, 0.0F, 4, 26, 1, 0.0F);
		fT33 = new ModelRenderer(this, 116, 4);
		fT33.setRotationPoint(0.0F, 2.0F, 0.0F);
		fT33.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		foot6_1 = new ModelRenderer(this, 0, 42);
		foot6_1.setRotationPoint(0.01F, 8.0F, -0.01F);
		foot6_1.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		fT12 = new ModelRenderer(this, 116, 2);
		fT12.setRotationPoint(0.0F, 2.0F, 0.0F);
		fT12.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		fT13 = new ModelRenderer(this, 116, 4);
		fT13.setRotationPoint(0.0F, 2.0F, 0.0F);
		fT13.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		staff6 = new ModelRenderer(this, 66, 35);
		staff6.setRotationPoint(0.0F, 0.0F, 0.0F);
		staff6.addBox(-3.9F, 1.0F, -4.1F, 1, 1, 1, 0.0F);
		setRotateAngle(staff6, -0.8922123136195012F, 0.5948082090796675F, 0.2230530784048753F);
		tentacle4 = new ModelRenderer(this, 0, 27);
		tentacle4.setRotationPoint(13.5F, 22.0F, 2.0F);
		tentacle4.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		setRotateAngle(tentacle4, -0.08726646259971647F, -0.3490658503988659F, 0.0F);
		maskRight = new ModelRenderer(this, 102, 0);
		maskRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		maskRight.addBox(-3.5F, -10.0F, -7.0F, 6, 8, 1, 0.0F);
		setRotateAngle(maskRight, 0.0F, 0.3490658503988659F, 0.0F);
		cube = new ModelRenderer(this, 62, 42);
		cube.setRotationPoint(0.0F, 0.0F, 0.0F);
		cube.addBox(-14.0F, -20.0F, -8.5F, 2, 2, 2, 0.0F);
		setRotateAngle(cube, 0.0F, 0.48328166987722987F, 0.520457182944709F);
		tentacle43 = new ModelRenderer(this, 0, 27);
		tentacle43.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle43.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		tentacle52 = new ModelRenderer(this, 0, 27);
		tentacle52.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle52.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		tentacle83 = new ModelRenderer(this, 0, 27);
		tentacle83.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle83.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		tentacle23 = new ModelRenderer(this, 0, 27);
		tentacle23.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle23.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		tentacle53 = new ModelRenderer(this, 0, 27);
		tentacle53.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle53.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		fT1 = new ModelRenderer(this, 116, 0);
		fT1.setRotationPoint(-3.0F, -2.0F, -5.5F);
		fT1.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		setRotateAngle(fT1, 0.0F, 0.3490658503988659F, 0.0F);
		tentacle2 = new ModelRenderer(this, 0, 27);
		tentacle2.setRotationPoint(7.5F, 22.0F, 2.0F);
		tentacle2.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		setRotateAngle(tentacle2, -0.08726646259971647F, 0.1308996938995747F, 0.0F);
		abyssalnomicon = new ModelRenderer(this, 28, 27);
		abyssalnomicon.setRotationPoint(15.0F, -5.43F, 3.0F);
		abyssalnomicon.addBox(-5.0F, -12.0F, -1.5F, 10, 12, 3, 0.0F);
		eye1 = new ModelRenderer(this, 70, 0);
		eye1.setRotationPoint(6.5F, 7.0F, -1.0F);
		eye1.addBox(0.0F, 0.0F, 0.0F, 5, 5, 1, 0.0F);
		foot3 = new ModelRenderer(this, 0, 42);
		foot3.setRotationPoint(0.01F, 8.0F, -0.01F);
		foot3.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		staff3 = new ModelRenderer(this, 66, 27);
		staff3.setRotationPoint(0.0F, 0.0F, 0.0F);
		staff3.addBox(-6.7F, -25.6F, -1.4F, 1, 2, 1, 0.0F);
		staff1 = new ModelRenderer(this, 54, 27);
		staff1.setRotationPoint(-11.0F, 6.0F, 4.0F);
		staff1.addBox(-4.8F, -20.7F, -1.4F, 1, 18, 1, 0.0F);
		fT3 = new ModelRenderer(this, 116, 0);
		fT3.setRotationPoint(3.0F, -2.0F, -5.5F);
		fT3.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		setRotateAngle(fT3, 0.0F, -0.3490658503988659F, 0.0F);
		tentacle22 = new ModelRenderer(this, 0, 27);
		tentacle22.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle22.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		foot6 = new ModelRenderer(this, 0, 42);
		foot6.setRotationPoint(0.01F, 8.0F, -0.01F);
		foot6.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		robeLeftInner = new ModelRenderer(this, 58, 0);
		robeLeftInner.setRotationPoint(13.0F, 0.0F, -1.0F);
		robeLeftInner.addBox(0.0F, 0.0F, 0.0F, 5, 26, 1, 0.0F);
		tentacle62 = new ModelRenderer(this, 0, 27);
		tentacle62.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle62.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		tentacle8 = new ModelRenderer(this, 0, 27);
		tentacle8.setRotationPoint(7.5F, 22.0F, 3.0F);
		tentacle8.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		setRotateAngle(tentacle8, 0.08726646259971647F, -0.1308996938995747F, 0.0F);
		tentacle3 = new ModelRenderer(this, 0, 27);
		tentacle3.setRotationPoint(10.5F, 22.0F, 2.0F);
		tentacle3.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		setRotateAngle(tentacle3, -0.08726646259971647F, -0.1308996938995747F, 0.0F);
		tentacle73 = new ModelRenderer(this, 0, 27);
		tentacle73.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle73.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		staff2 = new ModelRenderer(this, 62, 27);
		staff2.setRotationPoint(0.0F, 0.0F, 0.0F);
		staff2.addBox(6.0F, -23.8F, -1.4F, 1, 4, 1, 0.0F);
		setRotateAngle(staff2, 0.0F, 0.0F, -0.5235987755982988F);
		fT22 = new ModelRenderer(this, 116, 2);
		fT22.setRotationPoint(0.0F, 2.0F, 0.0F);
		fT22.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		robeRightInner = new ModelRenderer(this, 46, 0);
		robeRightInner.setRotationPoint(0.0F, 0.0F, -1.0F);
		robeRightInner.addBox(0.0F, 0.0F, 0.0F, 5, 26, 1, 0.0F);
		body = new ModelRenderer(this, 0, 0);
		body.setRotationPoint(-9.0F, -28.0F, 0.0F);
		body.addBox(0.0F, 0.0F, 0.0F, 18, 22, 5, 0.0F);
		tentacle33 = new ModelRenderer(this, 0, 27);
		tentacle33.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle33.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		staff4 = new ModelRenderer(this, 66, 30);
		staff4.setRotationPoint(0.0F, 0.0F, 0.0F);
		staff4.addBox(-21.6F, -19.3F, -1.4F, 1, 4, 1, 0.0F);
		setRotateAngle(staff4, 0.0F, 0.0F, 0.6981317007977318F);
		staff5 = new ModelRenderer(this, 62, 32);
		staff5.setRotationPoint(0.0F, 0.0F, 0.0F);
		staff5.addBox(18.3F, -21.5F, -1.4F, 1, 4, 1, 0.0F);
		setRotateAngle(staff5, 0.0F, 0.0F, -0.8726646259971648F);
		tentacle6 = new ModelRenderer(this, 0, 27);
		tentacle6.setRotationPoint(10.5F, 22.0F, 3.0F);
		tentacle6.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		setRotateAngle(tentacle6, 0.08726646259971647F, 0.1308996938995747F, 0.0F);
		robeLeftOuter = new ModelRenderer(this, 59, 0);
		robeLeftOuter.setRotationPoint(14.0F, 0.0F, -2.0F);
		robeLeftOuter.addBox(0.0F, 0.0F, 0.0F, 4, 26, 1, 0.0F);
		maskLeft = new ModelRenderer(this, 102, 0);
		maskLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		maskLeft.addBox(-2.5F, -10.0F, -7.0F, 6, 8, 1, 0.0F);
		setRotateAngle(maskLeft, 0.0F, -0.3490658503988659F, 0.0F);
		tentacle42 = new ModelRenderer(this, 0, 27);
		tentacle42.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle42.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		head = new ModelRenderer(this, 72, 0);
		head.setRotationPoint(9.0F, 0.0F, 1.0F);
		head.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, 0.0F);
		foot1 = new ModelRenderer(this, 0, 42);
		foot1.setRotationPoint(0.01F, 8.0F, -0.01F);
		foot1.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		fT23 = new ModelRenderer(this, 116, 4);
		fT23.setRotationPoint(0.0F, 2.0F, 0.0F);
		fT23.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		tentacle12 = new ModelRenderer(this, 0, 27);
		tentacle12.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle12.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		tentacle63 = new ModelRenderer(this, 0, 27);
		tentacle63.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle63.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		tentacle82 = new ModelRenderer(this, 0, 27);
		tentacle82.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle82.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		eye2 = new ModelRenderer(this, 70, 6);
		eye2.setRotationPoint(1.5F, 1.5F, -1.0F);
		eye2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		foot7 = new ModelRenderer(this, 0, 42);
		foot7.setRotationPoint(0.01F, 8.0F, -0.01F);
		foot7.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		tentacle13 = new ModelRenderer(this, 0, 27);
		tentacle13.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle13.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		tentacle5 = new ModelRenderer(this, 0, 27);
		tentacle5.setRotationPoint(4.5F, 22.0F, 3.0F);
		tentacle5.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		setRotateAngle(tentacle5, 0.08726646259971647F, -0.3490658503988659F, 0.0F);
		foot5 = new ModelRenderer(this, 0, 42);
		foot5.setRotationPoint(0.01F, 8.0F, -0.01F);
		foot5.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		fT32 = new ModelRenderer(this, 116, 2);
		fT32.setRotationPoint(0.0F, 2.0F, 0.0F);
		fT32.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		tentacle1 = new ModelRenderer(this, 0, 27);
		tentacle1.setRotationPoint(4.5F, 22.0F, 2.0F);
		tentacle1.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		setRotateAngle(tentacle1, -0.08726646259971647F, 0.3490658503988659F, 0.0F);
		foot2 = new ModelRenderer(this, 0, 42);
		foot2.setRotationPoint(0.01F, 8.0F, -0.01F);
		foot2.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		tentacle72 = new ModelRenderer(this, 0, 27);
		tentacle72.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle72.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		tentacle32 = new ModelRenderer(this, 0, 27);
		tentacle32.setRotationPoint(0.01F, 8.0F, -0.01F);
		tentacle32.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		foot4 = new ModelRenderer(this, 0, 42);
		foot4.setRotationPoint(0.01F, 8.0F, -0.01F);
		foot4.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		head.addChild(fT2);
		body.addChild(tentacle7);
		body.addChild(robeRightOuter);
		fT32.addChild(fT33);
		tentacle83.addChild(foot6_1);
		fT1.addChild(fT12);
		fT12.addChild(fT13);
		staff1.addChild(staff6);
		body.addChild(tentacle4);
		head.addChild(maskRight);
		staff1.addChild(cube);
		tentacle42.addChild(tentacle43);
		tentacle5.addChild(tentacle52);
		tentacle82.addChild(tentacle83);
		tentacle22.addChild(tentacle23);
		tentacle52.addChild(tentacle53);
		head.addChild(fT1);
		body.addChild(tentacle2);
		body.addChild(eye1);
		tentacle33.addChild(foot3);
		staff1.addChild(staff3);
		head.addChild(fT3);
		tentacle2.addChild(tentacle22);
		tentacle63.addChild(foot6);
		body.addChild(robeLeftInner);
		tentacle6.addChild(tentacle62);
		body.addChild(tentacle8);
		body.addChild(tentacle3);
		tentacle72.addChild(tentacle73);
		staff1.addChild(staff2);
		fT2.addChild(fT22);
		body.addChild(robeRightInner);
		tentacle32.addChild(tentacle33);
		staff1.addChild(staff4);
		staff1.addChild(staff5);
		body.addChild(tentacle6);
		body.addChild(robeLeftOuter);
		head.addChild(maskLeft);
		tentacle4.addChild(tentacle42);
		body.addChild(head);
		tentacle13.addChild(foot1);
		fT22.addChild(fT23);
		tentacle1.addChild(tentacle12);
		tentacle62.addChild(tentacle63);
		tentacle8.addChild(tentacle82);
		eye1.addChild(eye2);
		tentacle73.addChild(foot7);
		tentacle12.addChild(tentacle13);
		body.addChild(tentacle5);
		tentacle53.addChild(foot5);
		fT3.addChild(fT32);
		body.addChild(tentacle1);
		tentacle23.addChild(foot2);
		tentacle7.addChild(tentacle72);
		tentacle3.addChild(tentacle32);
		tentacle43.addChild(foot4);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		abyssalnomicon.render(f5);
		body.render(f5);
		staff1.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	private boolean passedMax = false;
	private float reverseNum = 0;

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		head.rotateAngleY = netHeadYaw / (180F / (float)Math.PI);
		head.rotateAngleX = headPitch / (180F / (float)Math.PI);

		// Face tentacle animation

		float swingSpeed = 0.0599F;
		float swing = MathHelper.sin(entityIn.ticksExisted * swingSpeed) * 4.5F * (float)Math.PI / 180.0F;

		fT1.rotateAngleX = swing;
		fT12.rotateAngleX = swing;
		fT13.rotateAngleX = swing;

		fT2.rotateAngleX = swing;
		fT22.rotateAngleX = swing;
		fT23.rotateAngleX = swing;

		fT3.rotateAngleX = swing;
		fT32.rotateAngleX = swing;
		fT33.rotateAngleX = swing;

		for(int i = 0; i < 4; i++)
			abyssalnomicon.rotationPointY = -5.5F + MathHelper.cos((i * 2 + ageInTicks) * 0.25F);

		for(int i = 0; i < 4; i++)
			staff1.rotationPointY = 6F + MathHelper.cos((i * 2 + ageInTicks) * 0.25F);

		float swing1 = MathHelper.sin(entityIn.ticksExisted * 0.12f) * 40.5F * (float)Math.PI / 180.0F;

		float swingX = Math.max(swing1, 0.08726646259971647F);
		float swingX0 = Math.max(swing1, -0.08726646259971647F);
		if(swing1 >= 0.69301057f && !passedMax) {
			passedMax = true;
			reverseNum = swing1;
		}
		if(swingX0 < 0.0F)
			passedMax = false;
		if(passedMax && reverseNum > -0.11F)
			reverseNum-= 0.02f;

		float swingX1 = swingX0 < 0.0F ? 0.0f : swing1;

		tentacle1.rotateAngleX = -swingX;
		tentacle12.rotateAngleX = passedMax ? reverseNum : swingX1;
		tentacle13.rotateAngleX = passedMax ? reverseNum : swingX1;
		foot1.rotateAngleX = passedMax ? reverseNum : swingX1;

		tentacle2.rotateAngleX = -swingX;
		tentacle22.rotateAngleX = passedMax ? reverseNum : swingX1;
		tentacle23.rotateAngleX = passedMax ? reverseNum : swingX1;
		foot2.rotateAngleX = passedMax ? reverseNum : swingX1;

		tentacle3.rotateAngleX = -swingX;
		tentacle32.rotateAngleX = passedMax ? reverseNum : swingX1;
		tentacle33.rotateAngleX = passedMax ? reverseNum : swingX1;
		foot3.rotateAngleX = passedMax ? reverseNum : swingX1;

		tentacle4.rotateAngleX = -swingX;
		tentacle42.rotateAngleX = passedMax ? reverseNum : swingX1;
		tentacle43.rotateAngleX = passedMax ? reverseNum : swingX1;
		foot4.rotateAngleX = passedMax ? reverseNum : swingX1;

		tentacle5.rotateAngleX = swingX;
		tentacle52.rotateAngleX = passedMax ? -reverseNum : -swingX1;
		tentacle53.rotateAngleX = passedMax ? -reverseNum : -swingX1;
		foot5.rotateAngleX = passedMax ? -reverseNum : -swingX1;

		tentacle6.rotateAngleX = swingX;
		tentacle62.rotateAngleX = passedMax ? -reverseNum : -swingX1;
		tentacle63.rotateAngleX = passedMax ? -reverseNum : -swingX1;
		foot6.rotateAngleX = passedMax ? -reverseNum : -swingX1;

		tentacle7.rotateAngleX = swingX;
		tentacle72.rotateAngleX = passedMax ? -reverseNum : -swingX1;
		tentacle73.rotateAngleX = passedMax ? -reverseNum : -swingX1;
		foot7.rotateAngleX = passedMax ? -reverseNum : -swingX1;

		tentacle8.rotateAngleX = swingX;
		tentacle82.rotateAngleX = passedMax ? -reverseNum : -swingX1;
		tentacle83.rotateAngleX = passedMax ? -reverseNum : -swingX1;
		foot6.rotateAngleX = passedMax ? -reverseNum : -swingX1;

		setRotateAngle(staff1, 0, 0, 0);
		float f6;
		float f7;

		if (swingProgress > -9990.0F)
		{
			f6 = swingProgress;
			staff1.rotateAngleY += MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			f6 = 1.0F - swingProgress;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			f7 = MathHelper.sin(f6 * (float)Math.PI);
			float f8 = MathHelper.sin(swingProgress * (float)Math.PI) * 0.75F;
			staff1.rotateAngleX = (float)(staff1.rotateAngleX + (f7 * 1.2D + f8));
			staff1.rotateAngleY += MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F * 2.0F;
		}

		int deathTicks = ((EntityJzahar)entityIn).deathTicks;

		if(deathTicks <= 800 && deathTicks > 0){
			head.rotateAngleX = 20F;
			eye1.isHidden = true;
			abyssalnomicon.isHidden = true;
			staff1.isHidden = true;
			fT1.isHidden = true;
			fT2.isHidden = true;
			fT3.isHidden = true;
			tentacle1.isHidden = true;
			tentacle2.isHidden = true;
			tentacle3.isHidden = true;
			tentacle4.isHidden = true;
			tentacle5.isHidden = true;
			tentacle6.isHidden = true;
			tentacle7.isHidden = true;
			foot1.isHidden = true;
			foot2.isHidden = true;
			foot3.isHidden = true;
			foot4.isHidden = true;
			foot5.isHidden = true;
			foot6.isHidden = true;

			for(int i = 0; i < 4; i++)
				body.rotationPointY = -9.5F + MathHelper.cos((i * 2 + ageInTicks) * 0.25F);
		}
		if(deathTicks == 0){
			head.rotateAngleX = headPitch / (180F / (float)Math.PI);
			eye1.isHidden = false;
			abyssalnomicon.isHidden = false;
			staff1.isHidden = false;
			fT1.isHidden = false;
			fT2.isHidden = false;
			fT3.isHidden = false;
			tentacle1.isHidden = false;
			tentacle2.isHidden = false;
			tentacle3.isHidden = false;
			tentacle4.isHidden = false;
			tentacle5.isHidden = false;
			tentacle6.isHidden = false;
			tentacle7.isHidden = false;
			foot1.isHidden = false;
			foot2.isHidden = false;
			foot3.isHidden = false;
			foot4.isHidden = false;
			foot5.isHidden = false;
			foot6.isHidden = false;

			for(int i = 0; i < 4; i++)
				body.rotationPointY = -28F + MathHelper.cos((i * 4 + ageInTicks) * 0.25F);
		}
	}
}

