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

import com.shinoow.abyssalcraft.lib.client.model.ModelArmoredBase;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelSkeletonGoliath extends ModelArmoredBase
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
		textureWidth = 128;
		textureHeight = 64;
		head = new ModelRenderer(this, 4, 0);
		head.setRotationPoint(0.0F, -15.0F, 0.0F);
		head.addBox(-4.0F, -7.0F, -4.0F, 9, 7, 9, f);
		leftrib1 = new ModelRenderer(this, 11, 20);
		leftrib1.setRotationPoint(1.0F, 8.0F, -1.0F);
		leftrib1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		tooth4 = new ModelRenderer(this, 0, 20);
		tooth4.setRotationPoint(-2.0F, 1.0F, -4.0F);
		tooth4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, f);
		leftrib13 = new ModelRenderer(this, 11, 20);
		leftrib13.setRotationPoint(0.0F, 0.0F, -3.0F);
		leftrib13.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		jaw3 = new ModelRenderer(this, 4, 18);
		jaw3.setRotationPoint(1.0F, 2.0F, -3.0F);
		jaw3.addBox(-4.0F, 0.0F, -1.0F, 7, 1, 1, f);
		leftrib5 = new ModelRenderer(this, 11, 20);
		leftrib5.setRotationPoint(1.0F, 16.0F, -1.0F);
		leftrib5.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		pelvis = new ModelRenderer(this, 50, 21);
		pelvis.setRotationPoint(-4.0F, 5.0F, -2.0F);
		pelvis.addBox(0.0F, 0.0F, 0.0F, 9, 3, 5, f);
		rightrib43 = new ModelRenderer(this, 11, 20);
		rightrib43.setRotationPoint(0.0F, 0.0F, -3.0F);
		rightrib43.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		leftrib4 = new ModelRenderer(this, 11, 20);
		leftrib4.setRotationPoint(1.0F, 14.0F, -1.0F);
		leftrib4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		rightrib1 = new ModelRenderer(this, 11, 20);
		rightrib1.setRotationPoint(-5.0F, 8.0F, -1.0F);
		rightrib1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		leftrib32 = new ModelRenderer(this, 11, 20);
		leftrib32.setRotationPoint(2.0F, 0.0F, 0.0F);
		leftrib32.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(leftrib32, 0.0F, 1.5707963267948966F, 0.0F);
		rightrib63 = new ModelRenderer(this, 11, 20);
		rightrib63.setRotationPoint(0.0F, 0.0F, -3.0F);
		rightrib63.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		rightleg = new ModelRenderer(this, 0, 22);
		rightleg.setRotationPoint(-2.5F, 8.0F, 0.5F);
		rightleg.addBox(-1.5F, 0.0F, -2.0F, 4, 16, 4, f);
		tooth1 = new ModelRenderer(this, 0, 20);
		tooth1.setRotationPoint(4.0F, 1.0F, -4.0F);
		tooth1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, f);
		tooth3 = new ModelRenderer(this, 0, 20);
		tooth3.setRotationPoint(0.0F, 1.0F, -4.0F);
		tooth3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, f);
		rightrib6 = new ModelRenderer(this, 11, 20);
		rightrib6.setRotationPoint(-5.0F, 18.0F, -1.0F);
		rightrib6.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		rightrib3 = new ModelRenderer(this, 11, 20);
		rightrib3.setRotationPoint(-5.0F, 12.0F, -1.0F);
		rightrib3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		leftrib52 = new ModelRenderer(this, 11, 20);
		leftrib52.setRotationPoint(2.0F, 0.0F, 0.0F);
		leftrib52.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(leftrib52, 0.0F, 1.5707963267948966F, 0.0F);
		leftleg = new ModelRenderer(this, 0, 22);
		leftleg.setRotationPoint(3.5F, 8.0F, 0.5F);
		leftleg.addBox(-2.5F, 0.0F, -2.0F, 4, 16, 4, f);
		leftrib62 = new ModelRenderer(this, 11, 20);
		leftrib62.setRotationPoint(2.0F, 0.0F, 0.0F);
		leftrib62.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(leftrib62, 0.0F, 1.5707963267948966F, 0.0F);
		rightjaw = new ModelRenderer(this, 0, 16);
		rightjaw.setRotationPoint(-4.0F, 0.0F, 4.0F);
		rightjaw.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, f);
		rightrib4 = new ModelRenderer(this, 11, 20);
		rightrib4.setRotationPoint(-5.0F, 14.0F, -1.0F);
		rightrib4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		leftjaw = new ModelRenderer(this, 0, 16);
		leftjaw.setRotationPoint(4.0F, 0.0F, 4.0F);
		leftjaw.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, f);
		rightrib53 = new ModelRenderer(this, 11, 20);
		rightrib53.setRotationPoint(0.0F, 0.0F, -3.0F);
		rightrib53.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		sternum = new ModelRenderer(this, 70, 11);
		sternum.setRotationPoint(-2.0F, 8.0F, -4.0F);
		sternum.addBox(0.0F, 0.0F, 0.0F, 3, 7, 1, f);
		leftarm = new ModelRenderer(this, 52, 0);
		leftarm.setRotationPoint(7.0F, -10.0F, 0.5F);
		leftarm.addBox(0.0F, -1.0F, -2.0F, 4, 16, 4, f);
		leftrib6 = new ModelRenderer(this, 11, 20);
		leftrib6.setRotationPoint(1.0F, 18.0F, -1.0F);
		leftrib6.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		leftrib3 = new ModelRenderer(this, 11, 20);
		leftrib3.setRotationPoint(1.0F, 12.0F, -1.0F);
		leftrib3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		rightrib2 = new ModelRenderer(this, 11, 20);
		rightrib2.setRotationPoint(-5.0F, 10.0F, -1.0F);
		rightrib2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		rightrib33 = new ModelRenderer(this, 11, 20);
		rightrib33.setRotationPoint(0.0F, 0.0F, -3.0F);
		rightrib33.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		rightrib62 = new ModelRenderer(this, 11, 20);
		rightrib62.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightrib62.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(rightrib62, 0.0F, 1.5707963267948966F, 0.0F);
		tooth2 = new ModelRenderer(this, 0, 20);
		tooth2.setRotationPoint(2.0F, 1.0F, -4.0F);
		tooth2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, f);
		rightrib42 = new ModelRenderer(this, 11, 20);
		rightrib42.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightrib42.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(rightrib42, 0.0F, 1.5707963267948966F, 0.0F);
		rightrib32 = new ModelRenderer(this, 11, 20);
		rightrib32.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightrib32.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(rightrib32, 0.0F, 1.5707963267948966F, 0.0F);
		leftrib2 = new ModelRenderer(this, 11, 20);
		leftrib2.setRotationPoint(1.0F, 10.0F, -1.0F);
		leftrib2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		spine = new ModelRenderer(this, 40, 0);
		spine.setRotationPoint(1.0F, -15.0F, 1.0F);
		spine.addBox(-2.0F, 0.0F, -2.0F, 3, 20, 3, f);
		rightrib13 = new ModelRenderer(this, 11, 20);
		rightrib13.setRotationPoint(0.0F, 0.0F, -3.0F);
		rightrib13.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		leftrib33 = new ModelRenderer(this, 11, 20);
		leftrib33.setRotationPoint(0.0F, 0.0F, -3.0F);
		leftrib33.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		rightrib22 = new ModelRenderer(this, 11, 20);
		rightrib22.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightrib22.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(rightrib22, 0.0F, 1.5707963267948966F, 0.0F);
		leftrib12 = new ModelRenderer(this, 11, 20);
		leftrib12.setRotationPoint(2.0F, 0.0F, 0.0F);
		leftrib12.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(leftrib12, 0.0F, 1.5707963267948966F, 0.0F);
		shoulders = new ModelRenderer(this, 19, 27);
		shoulders.setRotationPoint(0.0F, 4.0F, -3.0F);
		shoulders.addBox(-7.0F, 0.0F, 0.0F, 13, 3, 5, f);
		leftrib63 = new ModelRenderer(this, 11, 20);
		leftrib63.setRotationPoint(1.0F, 0.0F, -3.0F);
		leftrib63.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		rightrib5 = new ModelRenderer(this, 11, 20);
		rightrib5.setRotationPoint(-5.0F, 16.0F, -1.0F);
		rightrib5.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		rightrib52 = new ModelRenderer(this, 11, 20);
		rightrib52.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightrib52.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(rightrib52, 0.0F, 1.5707963267948966F, 0.0F);
		rightrib23 = new ModelRenderer(this, 11, 20);
		rightrib23.setRotationPoint(0.0F, 0.0F, -3.0F);
		rightrib23.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		leftrib43 = new ModelRenderer(this, 11, 20);
		leftrib43.setRotationPoint(0.0F, 0.0F, -3.0F);
		leftrib43.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		rightrib12 = new ModelRenderer(this, 11, 20);
		rightrib12.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightrib12.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(rightrib12, 0.0F, 1.5707963267948966F, 0.0F);
		jaw2 = new ModelRenderer(this, 24, 18);
		jaw2.setRotationPoint(-4.0F, 2.0F, -4.0F);
		jaw2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, f);
		tooth5 = new ModelRenderer(this, 0, 20);
		tooth5.setRotationPoint(-4.0F, 1.0F, -4.0F);
		tooth5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, f);
		rightarm = new ModelRenderer(this, 52, 0);
		rightarm.setRotationPoint(-6.0F, -10.0F, 0.5F);
		rightarm.addBox(-4.0F, -1.0F, -2.0F, 4, 16, 4, f);
		leftrib23 = new ModelRenderer(this, 11, 20);
		leftrib23.setRotationPoint(0.0F, 0.0F, -3.0F);
		leftrib23.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, f);
		leftrib42 = new ModelRenderer(this, 11, 20);
		leftrib42.setRotationPoint(2.0F, 0.0F, 0.0F);
		leftrib42.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(leftrib42, 0.0F, 1.5707963267948966F, 0.0F);
		leftrib22 = new ModelRenderer(this, 11, 20);
		leftrib22.setRotationPoint(2.0F, 0.0F, 0.0F);
		leftrib22.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		setRotateAngle(leftrib22, 0.0F, 1.5707963267948966F, 0.0F);
		leftrib53 = new ModelRenderer(this, 11, 20);
		leftrib53.setRotationPoint(1.0F, 0.0F, -3.0F);
		leftrib53.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, f);
		jaw1 = new ModelRenderer(this, 24, 18);
		jaw1.setRotationPoint(4.0F, 2.0F, -4.0F);
		jaw1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, f);
		spine.addChild(leftrib1);
		head.addChild(tooth4);
		leftrib1.addChild(leftrib13);
		head.addChild(jaw3);
		spine.addChild(leftrib5);
		rightrib4.addChild(rightrib43);
		spine.addChild(leftrib4);
		spine.addChild(rightrib1);
		leftrib3.addChild(leftrib32);
		rightrib6.addChild(rightrib63);
		head.addChild(tooth1);
		head.addChild(tooth3);
		spine.addChild(rightrib6);
		spine.addChild(rightrib3);
		leftrib5.addChild(leftrib52);
		leftrib6.addChild(leftrib62);
		head.addChild(rightjaw);
		spine.addChild(rightrib4);
		head.addChild(leftjaw);
		rightrib5.addChild(rightrib53);
		spine.addChild(sternum);
		spine.addChild(leftrib6);
		spine.addChild(leftrib3);
		spine.addChild(rightrib2);
		rightrib3.addChild(rightrib33);
		rightrib6.addChild(rightrib62);
		head.addChild(tooth2);
		rightrib4.addChild(rightrib42);
		rightrib3.addChild(rightrib32);
		spine.addChild(leftrib2);
		rightrib1.addChild(rightrib13);
		leftrib3.addChild(leftrib33);
		rightrib2.addChild(rightrib22);
		leftrib1.addChild(leftrib12);
		spine.addChild(shoulders);
		leftrib6.addChild(leftrib63);
		spine.addChild(rightrib5);
		rightrib5.addChild(rightrib52);
		rightrib2.addChild(rightrib23);
		leftrib4.addChild(leftrib43);
		rightrib1.addChild(rightrib12);
		head.addChild(jaw2);
		head.addChild(tooth5);
		leftrib2.addChild(leftrib23);
		leftrib4.addChild(leftrib42);
		leftrib2.addChild(leftrib22);
		leftrib5.addChild(leftrib53);
		head.addChild(jaw1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		head.render(f5);
		pelvis.render(f5);
		rightleg.render(f5);
		leftleg.render(f5);
		leftarm.render(f5);
		spine.render(f5);
		rightarm.render(f5);
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

	public void postRenderArm(float scale, EnumHandSide side){
		getArmForSide(side).postRender(scale);
	}

	protected ModelRenderer getArmForSide(EnumHandSide side)
	{
		return side == EnumHandSide.LEFT ? leftarm : rightarm;
	}

	@Override
	public void setVisible(boolean visible) {
		head.showModel = visible;
		pelvis.showModel = visible;
		rightleg.showModel = visible;
		leftleg.showModel = visible;
		leftarm.showModel = visible;
		spine.showModel = visible;
		rightarm.showModel = visible;
	}

	@Override
	public void setEquipmentSlotVisible(EntityEquipmentSlot slot) {

		switch (slot) {
		case FEET:
			rightleg.showModel = true;
			leftleg.showModel = true;
			break;
		case LEGS:
			pelvis.showModel = true;
			rightleg.showModel = true;
			leftleg.showModel = true;
			break;
		case CHEST:
			rightarm.showModel = true;
			leftarm.showModel = true;
			break;
		case HEAD:
			head.showModel = true;
			break;
		default:
			break;
		}
	}
}
