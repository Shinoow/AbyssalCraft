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

public class ModelSkeletonGoliath extends ModelBase
{

	public ModelRenderer head;
	public ModelRenderer leftjaw;
	public ModelRenderer rightjaw;
	public ModelRenderer jaw1;
	public ModelRenderer jaw2;
	public ModelRenderer jaw3;
	public ModelRenderer tooth1;
	public ModelRenderer tooth2;
	public ModelRenderer tooth3;
	public ModelRenderer tooth4;
	public ModelRenderer tooth5;
	public ModelRenderer shoulders;
	public ModelRenderer spine;
	public ModelRenderer leftarm;
	public ModelRenderer rightarm;
	public ModelRenderer leftrib1;
	public ModelRenderer leftrib12;
	public ModelRenderer leftrib13;
	public ModelRenderer leftrib2;
	public ModelRenderer leftrib22;
	public ModelRenderer leftrib23;
	public ModelRenderer leftrib3;
	public ModelRenderer leftrib32;
	public ModelRenderer leftrib33;
	public ModelRenderer leftrib4;
	public ModelRenderer leftrib42;
	public ModelRenderer leftrib43;
	public ModelRenderer leftrib5;
	public ModelRenderer leftrib52;
	public ModelRenderer leftrib53;
	public ModelRenderer leftrib6;
	public ModelRenderer leftrib62;
	public ModelRenderer leftrib63;
	public ModelRenderer rightrib1;
	public ModelRenderer rightrib12;
	public ModelRenderer rightrib13;
	public ModelRenderer rightrib2;
	public ModelRenderer rightrib22;
	public ModelRenderer rightrib23;
	public ModelRenderer rightrib3;
	public ModelRenderer rightrib32;
	public ModelRenderer rightrib33;
	public ModelRenderer rightrib4;
	public ModelRenderer rightrib42;
	public ModelRenderer rightrib43;
	public ModelRenderer rightrib5;
	public ModelRenderer rightrib52;
	public ModelRenderer rightrib53;
	public ModelRenderer rightrib6;
	public ModelRenderer rightrib62;
	public ModelRenderer rightrib63;
	public ModelRenderer sternum;
	public ModelRenderer pelvis;
	public ModelRenderer leftleg;
	public ModelRenderer rightleg;

	public ModelSkeletonGoliath() {
		this(0.0F);
	}

	public ModelSkeletonGoliath(float f) {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.head = new ModelRenderer(this, 4, 0);
		this.head.setRotationPoint(0.0F, -15.0F, 0.0F);
		this.head.addBox(-4.0F, -7.0F, -4.0F, 9, 7, 9, f);
		this.leftrib1 = new ModelRenderer(this, 11, 20);
		this.leftrib1.setRotationPoint(1.0F, 8.0F, -1.0F);
		this.leftrib1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.tooth4 = new ModelRenderer(this, 0, 20);
		this.tooth4.setRotationPoint(-2.0F, 1.0F, -4.0F);
		this.tooth4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, f);
		this.leftrib13 = new ModelRenderer(this, 11, 20);
		this.leftrib13.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.leftrib13.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.jaw3 = new ModelRenderer(this, 4, 18);
		this.jaw3.setRotationPoint(1.0F, 2.0F, -3.0F);
		this.jaw3.addBox(-4.0F, 0.0F, -1.0F, 7, 1, 1, f);
		this.leftrib5 = new ModelRenderer(this, 11, 20);
		this.leftrib5.setRotationPoint(1.0F, 16.0F, -1.0F);
		this.leftrib5.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.pelvis = new ModelRenderer(this, 50, 21);
		this.pelvis.setRotationPoint(-4.0F, 5.0F, -2.0F);
		this.pelvis.addBox(0.0F, 0.0F, 0.0F, 9, 3, 5, f);
		this.rightrib43 = new ModelRenderer(this, 11, 20);
		this.rightrib43.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.rightrib43.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.leftrib4 = new ModelRenderer(this, 11, 20);
		this.leftrib4.setRotationPoint(1.0F, 14.0F, -1.0F);
		this.leftrib4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.rightrib1 = new ModelRenderer(this, 11, 20);
		this.rightrib1.setRotationPoint(-5.0F, 8.0F, -1.0F);
		this.rightrib1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.leftrib32 = new ModelRenderer(this, 11, 20);
		this.leftrib32.setRotationPoint(2.0F, 0.0F, 0.0F);
		this.leftrib32.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(leftrib32, 0.0F, 1.5707963267948966F, 0.0F);
		this.rightrib63 = new ModelRenderer(this, 11, 20);
		this.rightrib63.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.rightrib63.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.rightleg = new ModelRenderer(this, 0, 22);
		this.rightleg.setRotationPoint(-2.5F, 8.0F, 0.5F);
		this.rightleg.addBox(-1.5F, 0.0F, -2.0F, 4, 16, 4, f);
		this.tooth1 = new ModelRenderer(this, 0, 20);
		this.tooth1.setRotationPoint(4.0F, 1.0F, -4.0F);
		this.tooth1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, f);
		this.tooth3 = new ModelRenderer(this, 0, 20);
		this.tooth3.setRotationPoint(0.0F, 1.0F, -4.0F);
		this.tooth3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, f);
		this.rightrib6 = new ModelRenderer(this, 11, 20);
		this.rightrib6.setRotationPoint(-5.0F, 18.0F, -1.0F);
		this.rightrib6.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.rightrib3 = new ModelRenderer(this, 11, 20);
		this.rightrib3.setRotationPoint(-5.0F, 12.0F, -1.0F);
		this.rightrib3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.leftrib52 = new ModelRenderer(this, 11, 20);
		this.leftrib52.setRotationPoint(2.0F, 0.0F, 0.0F);
		this.leftrib52.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(leftrib52, 0.0F, 1.5707963267948966F, 0.0F);
		this.leftleg = new ModelRenderer(this, 0, 22);
		this.leftleg.setRotationPoint(3.5F, 8.0F, 0.5F);
		this.leftleg.addBox(-2.5F, 0.0F, -2.0F, 4, 16, 4, f);
		this.leftrib62 = new ModelRenderer(this, 11, 20);
		this.leftrib62.setRotationPoint(2.0F, 0.0F, 0.0F);
		this.leftrib62.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(leftrib62, 0.0F, 1.5707963267948966F, 0.0F);
		this.rightjaw = new ModelRenderer(this, 0, 16);
		this.rightjaw.setRotationPoint(-4.0F, 0.0F, 4.0F);
		this.rightjaw.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, f);
		this.rightrib4 = new ModelRenderer(this, 11, 20);
		this.rightrib4.setRotationPoint(-5.0F, 14.0F, -1.0F);
		this.rightrib4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.leftjaw = new ModelRenderer(this, 0, 16);
		this.leftjaw.setRotationPoint(4.0F, 0.0F, 4.0F);
		this.leftjaw.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, f);
		this.rightrib53 = new ModelRenderer(this, 11, 20);
		this.rightrib53.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.rightrib53.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.sternum = new ModelRenderer(this, 70, 11);
		this.sternum.setRotationPoint(-2.0F, 8.0F, -4.0F);
		this.sternum.addBox(0.0F, 0.0F, 0.0F, 3, 7, 1, f);
		this.leftarm = new ModelRenderer(this, 52, 0);
		this.leftarm.setRotationPoint(7.0F, -10.0F, 0.5F);
		this.leftarm.addBox(0.0F, -1.0F, -2.0F, 4, 16, 4, f);
		this.leftrib6 = new ModelRenderer(this, 11, 20);
		this.leftrib6.setRotationPoint(1.0F, 18.0F, -1.0F);
		this.leftrib6.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.leftrib3 = new ModelRenderer(this, 11, 20);
		this.leftrib3.setRotationPoint(1.0F, 12.0F, -1.0F);
		this.leftrib3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.rightrib2 = new ModelRenderer(this, 11, 20);
		this.rightrib2.setRotationPoint(-5.0F, 10.0F, -1.0F);
		this.rightrib2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.rightrib33 = new ModelRenderer(this, 11, 20);
		this.rightrib33.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.rightrib33.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.rightrib62 = new ModelRenderer(this, 11, 20);
		this.rightrib62.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightrib62.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(rightrib62, 0.0F, 1.5707963267948966F, 0.0F);
		this.tooth2 = new ModelRenderer(this, 0, 20);
		this.tooth2.setRotationPoint(2.0F, 1.0F, -4.0F);
		this.tooth2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, f);
		this.rightrib42 = new ModelRenderer(this, 11, 20);
		this.rightrib42.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightrib42.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(rightrib42, 0.0F, 1.5707963267948966F, 0.0F);
		this.rightrib32 = new ModelRenderer(this, 11, 20);
		this.rightrib32.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightrib32.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(rightrib32, 0.0F, 1.5707963267948966F, 0.0F);
		this.leftrib2 = new ModelRenderer(this, 11, 20);
		this.leftrib2.setRotationPoint(1.0F, 10.0F, -1.0F);
		this.leftrib2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.spine = new ModelRenderer(this, 40, 0);
		this.spine.setRotationPoint(1.0F, -15.0F, 1.0F);
		this.spine.addBox(-2.0F, 0.0F, -2.0F, 3, 20, 3, f);
		this.rightrib13 = new ModelRenderer(this, 11, 20);
		this.rightrib13.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.rightrib13.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.leftrib33 = new ModelRenderer(this, 11, 20);
		this.leftrib33.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.leftrib33.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.rightrib22 = new ModelRenderer(this, 11, 20);
		this.rightrib22.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightrib22.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(rightrib22, 0.0F, 1.5707963267948966F, 0.0F);
		this.leftrib12 = new ModelRenderer(this, 11, 20);
		this.leftrib12.setRotationPoint(2.0F, 0.0F, 0.0F);
		this.leftrib12.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(leftrib12, 0.0F, 1.5707963267948966F, 0.0F);
		this.shoulders = new ModelRenderer(this, 19, 27);
		this.shoulders.setRotationPoint(0.0F, 4.0F, -3.0F);
		this.shoulders.addBox(-7.0F, 0.0F, 0.0F, 13, 3, 5, f);
		this.leftrib63 = new ModelRenderer(this, 11, 20);
		this.leftrib63.setRotationPoint(1.0F, 0.0F, -3.0F);
		this.leftrib63.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.rightrib5 = new ModelRenderer(this, 11, 20);
		this.rightrib5.setRotationPoint(-5.0F, 16.0F, -1.0F);
		this.rightrib5.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.rightrib52 = new ModelRenderer(this, 11, 20);
		this.rightrib52.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightrib52.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(rightrib52, 0.0F, 1.5707963267948966F, 0.0F);
		this.rightrib23 = new ModelRenderer(this, 11, 20);
		this.rightrib23.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.rightrib23.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.leftrib43 = new ModelRenderer(this, 11, 20);
		this.leftrib43.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.leftrib43.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.rightrib12 = new ModelRenderer(this, 11, 20);
		this.rightrib12.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightrib12.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(rightrib12, 0.0F, 1.5707963267948966F, 0.0F);
		this.jaw2 = new ModelRenderer(this, 24, 18);
		this.jaw2.setRotationPoint(-4.0F, 2.0F, -4.0F);
		this.jaw2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, f);
		this.tooth5 = new ModelRenderer(this, 0, 20);
		this.tooth5.setRotationPoint(-4.0F, 1.0F, -4.0F);
		this.tooth5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, f);
		this.rightarm = new ModelRenderer(this, 52, 0);
		this.rightarm.setRotationPoint(-6.0F, -10.0F, 0.5F);
		this.rightarm.addBox(-4.0F, -1.0F, -2.0F, 4, 16, 4, f);
		this.leftrib23 = new ModelRenderer(this, 11, 20);
		this.leftrib23.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.leftrib23.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		this.leftrib42 = new ModelRenderer(this, 11, 20);
		this.leftrib42.setRotationPoint(2.0F, 0.0F, 0.0F);
		this.leftrib42.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(leftrib42, 0.0F, 1.5707963267948966F, 0.0F);
		this.leftrib22 = new ModelRenderer(this, 11, 20);
		this.leftrib22.setRotationPoint(2.0F, 0.0F, 0.0F);
		this.leftrib22.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.setRotateAngle(leftrib22, 0.0F, 1.5707963267948966F, 0.0F);
		this.leftrib53 = new ModelRenderer(this, 11, 20);
		this.leftrib53.setRotationPoint(1.0F, 0.0F, -3.0F);
		this.leftrib53.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		this.jaw1 = new ModelRenderer(this, 24, 18);
		this.jaw1.setRotationPoint(4.0F, 2.0F, -4.0F);
		this.jaw1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, f);
		this.spine.addChild(this.leftrib1);
		this.head.addChild(this.tooth4);
		this.leftrib1.addChild(this.leftrib13);
		this.head.addChild(this.jaw3);
		this.spine.addChild(this.leftrib5);
		this.rightrib4.addChild(this.rightrib43);
		this.spine.addChild(this.leftrib4);
		this.spine.addChild(this.rightrib1);
		this.leftrib3.addChild(this.leftrib32);
		this.rightrib6.addChild(this.rightrib63);
		this.head.addChild(this.tooth1);
		this.head.addChild(this.tooth3);
		this.spine.addChild(this.rightrib6);
		this.spine.addChild(this.rightrib3);
		this.leftrib5.addChild(this.leftrib52);
		this.leftrib6.addChild(this.leftrib62);
		this.head.addChild(this.rightjaw);
		this.spine.addChild(this.rightrib4);
		this.head.addChild(this.leftjaw);
		this.rightrib5.addChild(this.rightrib53);
		this.spine.addChild(this.sternum);
		this.spine.addChild(this.leftrib6);
		this.spine.addChild(this.leftrib3);
		this.spine.addChild(this.rightrib2);
		this.rightrib3.addChild(this.rightrib33);
		this.rightrib6.addChild(this.rightrib62);
		this.head.addChild(this.tooth2);
		this.rightrib4.addChild(this.rightrib42);
		this.rightrib3.addChild(this.rightrib32);
		this.spine.addChild(this.leftrib2);
		this.rightrib1.addChild(this.rightrib13);
		this.leftrib3.addChild(this.leftrib33);
		this.rightrib2.addChild(this.rightrib22);
		this.leftrib1.addChild(this.leftrib12);
		this.spine.addChild(this.shoulders);
		this.leftrib6.addChild(this.leftrib63);
		this.spine.addChild(this.rightrib5);
		this.rightrib5.addChild(this.rightrib52);
		this.rightrib2.addChild(this.rightrib23);
		this.leftrib4.addChild(this.leftrib43);
		this.rightrib1.addChild(this.rightrib12);
		this.head.addChild(this.jaw2);
		this.head.addChild(this.tooth5);
		this.leftrib2.addChild(this.leftrib23);
		this.leftrib4.addChild(this.leftrib42);
		this.leftrib2.addChild(this.leftrib22);
		this.leftrib5.addChild(this.leftrib53);
		this.head.addChild(this.jaw1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
		this.head.render(f5);
		this.pelvis.render(f5);
		this.rightleg.render(f5);
		this.leftleg.render(f5);
		this.leftarm.render(f5);
		this.spine.render(f5);
		this.rightarm.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		head.rotateAngleY = par4 / (180F / (float)Math.PI);
		head.rotateAngleX = par5 / (180F / (float)Math.PI);

		rightarm.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
		leftarm.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;

		rightarm.rotateAngleZ = 0.0F;
		leftarm.rotateAngleZ = 0.0F;

		rightleg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		rightleg.rotateAngleY = 0.0F;

		leftleg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		leftleg.rotateAngleY = 0.0F;

		if (isRiding)
		{
			rightarm.rotateAngleX += -((float)Math.PI / 5F);
			leftarm.rotateAngleX += -((float)Math.PI / 5F);

			rightleg.rotateAngleX = -((float)Math.PI * 2F / 5F);
			leftleg.rotateAngleX = -((float)Math.PI * 2F / 5F);

			rightleg.rotateAngleY = (float)Math.PI / 10F;
			leftleg.rotateAngleY = -((float)Math.PI / 10F);
		}

		rightarm.rotateAngleY = 0.0F;
		leftarm.rotateAngleY = 0.0F;
		float f6;
		float f7;

		if (swingProgress > -9990.0F)
		{
			f6 = swingProgress;
			shoulders.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			spine.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib1.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib12.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib13.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib2.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib22.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib23.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib3.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib32.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib33.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib4.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib42.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib43.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib5.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib52.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib53.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib6.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib62.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			leftrib63.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib1.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib12.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib13.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib2.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib22.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib23.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib3.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib32.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib33.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib4.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib42.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib43.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib5.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib52.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib53.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib6.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib62.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightrib63.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			sternum.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI * 2.0F) * 0.2F;
			rightarm.rotateAngleY += spine.rotateAngleY;
			leftarm.rotateAngleY += spine.rotateAngleY;
			leftarm.rotateAngleX += spine.rotateAngleY;
			f6 = 1.0F - swingProgress;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			f7 = MathHelper.sin(f6 * (float)Math.PI);
			float f8 = MathHelper.sin(swingProgress * (float)Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
			rightarm.rotateAngleX = (float)(rightarm.rotateAngleX - (f7 * 1.2D + f8));
			rightarm.rotateAngleY += spine.rotateAngleY * 2.0F;
			rightarm.rotateAngleZ = MathHelper.sin(swingProgress * (float)Math.PI) * -0.4F;
		}
	}

	public void setInvisible(boolean invisible)
	{
		this.head.showModel = invisible;
		this.pelvis.showModel = invisible;
		this.rightleg.showModel = invisible;
		this.leftleg.showModel = invisible;
		this.leftarm.showModel = invisible;
		this.spine.showModel = invisible;
		this.rightarm.showModel = invisible;
	}
}
