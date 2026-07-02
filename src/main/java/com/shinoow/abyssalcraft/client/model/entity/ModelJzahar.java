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
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.fT2 = new ModelRenderer(this, 116, 0);
		this.fT2.setRotationPoint(0.0F, -2.0F, -6.0F);
		this.fT2.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		this.tentacle7 = new ModelRenderer(this, 0, 27);
		this.tentacle7.setRotationPoint(13.5F, 22.0F, 3.0F);
		this.tentacle7.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.setRotateAngle(tentacle7, 0.08726646259971647F, 0.3490658503988659F, 0.0F);
		this.robeRightOuter = new ModelRenderer(this, 46, 0);
		this.robeRightOuter.setRotationPoint(0.0F, 0.0F, -2.0F);
		this.robeRightOuter.addBox(0.0F, 0.0F, 0.0F, 4, 26, 1, 0.0F);
		this.fT33 = new ModelRenderer(this, 116, 4);
		this.fT33.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.fT33.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		this.foot6_1 = new ModelRenderer(this, 0, 42);
		this.foot6_1.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.foot6_1.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		this.fT12 = new ModelRenderer(this, 116, 2);
		this.fT12.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.fT12.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		this.fT13 = new ModelRenderer(this, 116, 4);
		this.fT13.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.fT13.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		this.staff6 = new ModelRenderer(this, 66, 35);
		this.staff6.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.staff6.addBox(-3.9F, 1.0F, -4.1F, 1, 1, 1, 0.0F);
		this.setRotateAngle(staff6, -0.8922123136195012F, 0.5948082090796675F, 0.2230530784048753F);
		this.tentacle4 = new ModelRenderer(this, 0, 27);
		this.tentacle4.setRotationPoint(13.5F, 22.0F, 2.0F);
		this.tentacle4.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.setRotateAngle(tentacle4, -0.08726646259971647F, -0.3490658503988659F, 0.0F);
		this.maskRight = new ModelRenderer(this, 102, 0);
		this.maskRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.maskRight.addBox(-3.5F, -10.0F, -7.0F, 6, 8, 1, 0.0F);
		this.setRotateAngle(maskRight, 0.0F, 0.3490658503988659F, 0.0F);
		this.cube = new ModelRenderer(this, 62, 42);
		this.cube.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.cube.addBox(-14.0F, -20.0F, -8.5F, 2, 2, 2, 0.0F);
		this.setRotateAngle(cube, 0.0F, 0.48328166987722987F, 0.520457182944709F);
		this.tentacle43 = new ModelRenderer(this, 0, 27);
		this.tentacle43.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle43.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.tentacle52 = new ModelRenderer(this, 0, 27);
		this.tentacle52.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle52.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.tentacle83 = new ModelRenderer(this, 0, 27);
		this.tentacle83.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle83.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.tentacle23 = new ModelRenderer(this, 0, 27);
		this.tentacle23.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle23.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.tentacle53 = new ModelRenderer(this, 0, 27);
		this.tentacle53.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle53.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.fT1 = new ModelRenderer(this, 116, 0);
		this.fT1.setRotationPoint(-3.0F, -2.0F, -5.5F);
		this.fT1.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(fT1, 0.0F, 0.3490658503988659F, 0.0F);
		this.tentacle2 = new ModelRenderer(this, 0, 27);
		this.tentacle2.setRotationPoint(7.5F, 22.0F, 2.0F);
		this.tentacle2.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.setRotateAngle(tentacle2, -0.08726646259971647F, 0.1308996938995747F, 0.0F);
		this.abyssalnomicon = new ModelRenderer(this, 28, 27);
		this.abyssalnomicon.setRotationPoint(15.0F, -5.43F, 3.0F);
		this.abyssalnomicon.addBox(-5.0F, -12.0F, -1.5F, 10, 12, 3, 0.0F);
		this.eye1 = new ModelRenderer(this, 70, 0);
		this.eye1.setRotationPoint(6.5F, 7.0F, -1.0F);
		this.eye1.addBox(0.0F, 0.0F, 0.0F, 5, 5, 1, 0.0F);
		this.foot3 = new ModelRenderer(this, 0, 42);
		this.foot3.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.foot3.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		this.staff3 = new ModelRenderer(this, 66, 27);
		this.staff3.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.staff3.addBox(-6.7F, -25.6F, -1.4F, 1, 2, 1, 0.0F);
		this.staff1 = new ModelRenderer(this, 54, 27);
		this.staff1.setRotationPoint(-11.0F, 6.0F, 4.0F);
		this.staff1.addBox(-4.8F, -20.7F, -1.4F, 1, 18, 1, 0.0F);
		this.fT3 = new ModelRenderer(this, 116, 0);
		this.fT3.setRotationPoint(3.0F, -2.0F, -5.5F);
		this.fT3.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(fT3, 0.0F, -0.3490658503988659F, 0.0F);
		this.tentacle22 = new ModelRenderer(this, 0, 27);
		this.tentacle22.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle22.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.foot6 = new ModelRenderer(this, 0, 42);
		this.foot6.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.foot6.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		this.robeLeftInner = new ModelRenderer(this, 58, 0);
		this.robeLeftInner.setRotationPoint(13.0F, 0.0F, -1.0F);
		this.robeLeftInner.addBox(0.0F, 0.0F, 0.0F, 5, 26, 1, 0.0F);
		this.tentacle62 = new ModelRenderer(this, 0, 27);
		this.tentacle62.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle62.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.tentacle8 = new ModelRenderer(this, 0, 27);
		this.tentacle8.setRotationPoint(7.5F, 22.0F, 3.0F);
		this.tentacle8.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.setRotateAngle(tentacle8, 0.08726646259971647F, -0.1308996938995747F, 0.0F);
		this.tentacle3 = new ModelRenderer(this, 0, 27);
		this.tentacle3.setRotationPoint(10.5F, 22.0F, 2.0F);
		this.tentacle3.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.setRotateAngle(tentacle3, -0.08726646259971647F, -0.1308996938995747F, 0.0F);
		this.tentacle73 = new ModelRenderer(this, 0, 27);
		this.tentacle73.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle73.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.staff2 = new ModelRenderer(this, 62, 27);
		this.staff2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.staff2.addBox(6.0F, -23.8F, -1.4F, 1, 4, 1, 0.0F);
		this.setRotateAngle(staff2, 0.0F, 0.0F, -0.5235987755982988F);
		this.fT22 = new ModelRenderer(this, 116, 2);
		this.fT22.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.fT22.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		this.robeRightInner = new ModelRenderer(this, 46, 0);
		this.robeRightInner.setRotationPoint(0.0F, 0.0F, -1.0F);
		this.robeRightInner.addBox(0.0F, 0.0F, 0.0F, 5, 26, 1, 0.0F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(-9.0F, -28.0F, 0.0F);
		this.body.addBox(0.0F, 0.0F, 0.0F, 18, 22, 5, 0.0F);
		this.tentacle33 = new ModelRenderer(this, 0, 27);
		this.tentacle33.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle33.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.staff4 = new ModelRenderer(this, 66, 30);
		this.staff4.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.staff4.addBox(-21.6F, -19.3F, -1.4F, 1, 4, 1, 0.0F);
		this.setRotateAngle(staff4, 0.0F, 0.0F, 0.6981317007977318F);
		this.staff5 = new ModelRenderer(this, 62, 32);
		this.staff5.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.staff5.addBox(18.3F, -21.5F, -1.4F, 1, 4, 1, 0.0F);
		this.setRotateAngle(staff5, 0.0F, 0.0F, -0.8726646259971648F);
		this.tentacle6 = new ModelRenderer(this, 0, 27);
		this.tentacle6.setRotationPoint(10.5F, 22.0F, 3.0F);
		this.tentacle6.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.setRotateAngle(tentacle6, 0.08726646259971647F, 0.1308996938995747F, 0.0F);
		this.robeLeftOuter = new ModelRenderer(this, 59, 0);
		this.robeLeftOuter.setRotationPoint(14.0F, 0.0F, -2.0F);
		this.robeLeftOuter.addBox(0.0F, 0.0F, 0.0F, 4, 26, 1, 0.0F);
		this.maskLeft = new ModelRenderer(this, 102, 0);
		this.maskLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.maskLeft.addBox(-2.5F, -10.0F, -7.0F, 6, 8, 1, 0.0F);
		this.setRotateAngle(maskLeft, 0.0F, -0.3490658503988659F, 0.0F);
		this.tentacle42 = new ModelRenderer(this, 0, 27);
		this.tentacle42.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle42.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.head = new ModelRenderer(this, 72, 0);
		this.head.setRotationPoint(9.0F, 0.0F, 1.0F);
		this.head.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, 0.0F);
		this.foot1 = new ModelRenderer(this, 0, 42);
		this.foot1.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.foot1.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		this.fT23 = new ModelRenderer(this, 116, 4);
		this.fT23.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.fT23.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		this.tentacle12 = new ModelRenderer(this, 0, 27);
		this.tentacle12.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle12.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.tentacle63 = new ModelRenderer(this, 0, 27);
		this.tentacle63.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle63.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.tentacle82 = new ModelRenderer(this, 0, 27);
		this.tentacle82.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle82.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.eye2 = new ModelRenderer(this, 70, 6);
		this.eye2.setRotationPoint(1.5F, 1.5F, -1.0F);
		this.eye2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		this.foot7 = new ModelRenderer(this, 0, 42);
		this.foot7.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.foot7.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		this.tentacle13 = new ModelRenderer(this, 0, 27);
		this.tentacle13.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle13.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.tentacle5 = new ModelRenderer(this, 0, 27);
		this.tentacle5.setRotationPoint(4.5F, 22.0F, 3.0F);
		this.tentacle5.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.setRotateAngle(tentacle5, 0.08726646259971647F, -0.3490658503988659F, 0.0F);
		this.foot5 = new ModelRenderer(this, 0, 42);
		this.foot5.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.foot5.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		this.fT32 = new ModelRenderer(this, 116, 2);
		this.fT32.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.fT32.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
		this.tentacle1 = new ModelRenderer(this, 0, 27);
		this.tentacle1.setRotationPoint(4.5F, 22.0F, 2.0F);
		this.tentacle1.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.setRotateAngle(tentacle1, -0.08726646259971647F, 0.3490658503988659F, 0.0F);
		this.foot2 = new ModelRenderer(this, 0, 42);
		this.foot2.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.foot2.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		this.tentacle72 = new ModelRenderer(this, 0, 27);
		this.tentacle72.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle72.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.tentacle32 = new ModelRenderer(this, 0, 27);
		this.tentacle32.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.tentacle32.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.foot4 = new ModelRenderer(this, 0, 42);
		this.foot4.setRotationPoint(0.01F, 8.0F, -0.01F);
		this.foot4.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
		this.head.addChild(this.fT2);
		this.body.addChild(this.tentacle7);
		this.body.addChild(this.robeRightOuter);
		this.fT32.addChild(this.fT33);
		this.tentacle83.addChild(this.foot6_1);
		this.fT1.addChild(this.fT12);
		this.fT12.addChild(this.fT13);
		this.staff1.addChild(this.staff6);
		this.body.addChild(this.tentacle4);
		this.head.addChild(this.maskRight);
		this.staff1.addChild(this.cube);
		this.tentacle42.addChild(this.tentacle43);
		this.tentacle5.addChild(this.tentacle52);
		this.tentacle82.addChild(this.tentacle83);
		this.tentacle22.addChild(this.tentacle23);
		this.tentacle52.addChild(this.tentacle53);
		this.head.addChild(this.fT1);
		this.body.addChild(this.tentacle2);
		this.body.addChild(this.eye1);
		this.tentacle33.addChild(this.foot3);
		this.staff1.addChild(this.staff3);
		this.head.addChild(this.fT3);
		this.tentacle2.addChild(this.tentacle22);
		this.tentacle63.addChild(this.foot6);
		this.body.addChild(this.robeLeftInner);
		this.tentacle6.addChild(this.tentacle62);
		this.body.addChild(this.tentacle8);
		this.body.addChild(this.tentacle3);
		this.tentacle72.addChild(this.tentacle73);
		this.staff1.addChild(this.staff2);
		this.fT2.addChild(this.fT22);
		this.body.addChild(this.robeRightInner);
		this.tentacle32.addChild(this.tentacle33);
		this.staff1.addChild(this.staff4);
		this.staff1.addChild(this.staff5);
		this.body.addChild(this.tentacle6);
		this.body.addChild(this.robeLeftOuter);
		this.head.addChild(this.maskLeft);
		this.tentacle4.addChild(this.tentacle42);
		this.body.addChild(this.head);
		this.tentacle13.addChild(this.foot1);
		this.fT22.addChild(this.fT23);
		this.tentacle1.addChild(this.tentacle12);
		this.tentacle62.addChild(this.tentacle63);
		this.tentacle8.addChild(this.tentacle82);
		this.eye1.addChild(this.eye2);
		this.tentacle73.addChild(this.foot7);
		this.tentacle12.addChild(this.tentacle13);
		this.body.addChild(this.tentacle5);
		this.tentacle53.addChild(this.foot5);
		this.fT3.addChild(this.fT32);
		this.body.addChild(this.tentacle1);
		this.tentacle23.addChild(this.foot2);
		this.tentacle7.addChild(this.tentacle72);
		this.tentacle3.addChild(this.tentacle32);
		this.tentacle43.addChild(this.foot4);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
		this.abyssalnomicon.render(f5);
		this.body.render(f5);
		this.staff1.render(f5);
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
		if(swingX0 < 0.0F) {
			passedMax = false;
		}
		if(passedMax && reverseNum > -0.11F) {
			reverseNum-= 0.02f;
		}

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

			for(int i = 0; i < 4; i++){
				body.rotationPointY = -9.5F + MathHelper.cos((i * 2 + ageInTicks) * 0.25F);
			}
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

			for(int i = 0; i < 4; i++){
				body.rotationPointY = -28F + MathHelper.cos((i * 4 + ageInTicks) * 0.25F);
			}
		}
	}
}

