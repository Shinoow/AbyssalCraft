/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
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

public class ModelDreadSpawn extends ModelBase {

	public ModelRenderer body;
	public ModelRenderer head;
	public ModelRenderer jaw;
	public ModelRenderer thing;
	public ModelRenderer arm, arm1, arm2, arm3;
	public ModelRenderer t1, t11, t12, t13, t2, t21, t22, t23, t3, t31, t32, t33;

	public ModelDreadSpawn()
	{
		textureWidth = 32;
		textureHeight = 32;

		body = new ModelRenderer(this, 0, 17);
		body.addBox(-3F, -3F, -3F, 6, 5, 6);
		body.setRotationPoint(0F, 22F, 0F);
		body.setTextureSize(32, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		head = new ModelRenderer(this, 0, 4);
		head.addBox(-1.5F, -4F, -1.5F, 3, 3, 3);
		head.setRotationPoint(0F, 19F, 0F);
		head.setTextureSize(32, 32);
		head.mirror = true;
		setRotation(head, -0.4833219F, 0F, 0F);
		jaw = new ModelRenderer(this, 12, 4);
		jaw.addBox(-1.5F, -1F, -1.5F, 3, 1, 3);
		jaw.setRotationPoint(0F, 0F, 0F);
		jaw.setTextureSize(32, 32);
		head.addChild(jaw);
		jaw.mirror = true;
		setRotation(jaw, 0.4833219F, 0F, 0F);
		thing = new ModelRenderer(this, 0, 10);
		thing.addBox(0F, -1F, -3F, 6, 1, 6);
		thing.setRotationPoint(3F, 21F, 2F);
		thing.setTextureSize(32, 32);
		thing.mirror = true;
		setRotation(thing, 0F, 0F, 0F);

		t1 = new ModelRenderer(this, 10, 5);
		t1.addBox(0F, -1, -3, 1, 1, 1);
		t1.setRotationPoint(4F, -1F, 2F);
		t1.setTextureSize(32, 32);
		t1.mirror = true;
		setRotation(t1, 0F, 0.3F, 0F);
		thing.addChild(t1);
		t11 = new ModelRenderer(this, 10, 5);
		t11.addBox(0F, -1, -3, 1, 1, 1);
		t11.setRotationPoint(0F, -1F, 0F);
		t11.setTextureSize(32, 32);
		t11.mirror = true;
		setRotation(t11, 0F, 0F, 0F);
		t1.addChild(t11);
		t12 = new ModelRenderer(this, 10, 5);
		t12.addBox(0F, -1, -3, 1, 1, 1);
		t12.setRotationPoint(0F, -1F, 0F);
		t12.setTextureSize(32, 32);
		t12.mirror = true;
		setRotation(t12, 0F, 0F, 0F);
		t11.addChild(t12);
		t13 = new ModelRenderer(this, 10, 5);
		t13.addBox(0F, -1, -3, 1, 1, 1);
		t13.setRotationPoint(0F, -1F, 0F);
		t13.setTextureSize(32, 32);
		t13.mirror = true;
		setRotation(t13, 0F, 0F, 0F);
		t12.addChild(t13);

		t2 = new ModelRenderer(this, 10, 5);
		t2.addBox(0F, -1, -3, 1, 1, 1);
		t2.setRotationPoint(4F, -1F, 4F);
		t2.setTextureSize(32, 32);
		t2.mirror = true;
		setRotation(t2, 0F, 0.7F, 0F);
		thing.addChild(t2);
		t21 = new ModelRenderer(this, 10, 5);
		t21.addBox(0F, -1, -3, 1, 1, 1);
		t21.setRotationPoint(0F, -1F, 0F);
		t21.setTextureSize(32, 32);
		t21.mirror = true;
		setRotation(t21, 0F, 0F, 0F);
		t2.addChild(t21);
		t22 = new ModelRenderer(this, 10, 5);
		t22.addBox(0F, -1, -3, 1, 1, 1);
		t22.setRotationPoint(0F, -1F, 0F);
		t22.setTextureSize(32, 32);
		t22.mirror = true;
		setRotation(t22, 0F, 0F, 0F);
		t21.addChild(t22);
		t23 = new ModelRenderer(this, 10, 5);
		t23.addBox(0F, -1, -3, 1, 1, 1);
		t23.setRotationPoint(0F, -1F, 0F);
		t23.setTextureSize(32, 32);
		t23.mirror = true;
		setRotation(t23, 0F, 0F, 0F);
		t22.addChild(t23);

		t3 = new ModelRenderer(this, 10, 5);
		t3.addBox(0F, -1, -3, 1, 1, 1);
		t3.setRotationPoint(1F, -1F, 0F);
		t3.setTextureSize(32, 32);
		t3.mirror = true;
		setRotation(t3, 0F, -0.3F, 0F);
		thing.addChild(t3);
		t31 = new ModelRenderer(this, 10, 5);
		t31.addBox(0F, -1, -3, 1, 1, 1);
		t31.setRotationPoint(0F, -1F, 0F);
		t31.setTextureSize(32, 32);
		t31.mirror = true;
		setRotation(t31, 0F, 0F, 0F);
		t3.addChild(t31);
		t32 = new ModelRenderer(this, 10, 5);
		t32.addBox(0F, -1, -3, 1, 1, 1);
		t32.setRotationPoint(0F, -1F, 0F);
		t32.setTextureSize(32, 32);
		t32.mirror = true;
		setRotation(t32, 0F, 0F, 0F);
		t31.addChild(t32);
		t33 = new ModelRenderer(this, 10, 5);
		t33.addBox(0F, -1, -3, 1, 1, 1);
		t33.setRotationPoint(0F, -1F, 0F);
		t33.setTextureSize(32, 32);
		t33.mirror = true;
		setRotation(t33, 0F, 0F, 0F);
		t32.addChild(t33);

		arm = new ModelRenderer(this, 0, 0);
		arm.addBox(-1F, -1F, -3F, 2, 2, 2);
		arm.setRotationPoint(-2F, 22F, 0F);
		arm.setTextureSize(32, 32);
		arm.mirror = true;
		setRotation(arm, 0F, 0F, 0F);

		arm1 = new ModelRenderer(this, 0, 0);
		arm1.addBox(-1F, -1F, -3F, 2, 2, 2);
		arm1.setRotationPoint(0F, 0F, -2F);
		arm1.setTextureSize(32, 32);
		arm1.mirror = true;
		setRotation(arm1, 0F, 0F, 0F);
		arm.addChild(arm1);

		arm2 = new ModelRenderer(this, 8, 0);
		arm2.addBox(-1F, -1F, -2F, 2, 2, 2);
		arm2.setRotationPoint(0F, 0F, -3F);
		arm2.setTextureSize(32, 32);
		arm2.mirror = true;
		setRotation(arm2, 0F, 0F, 0F);
		arm1.addChild(arm2);

		arm3 = new ModelRenderer(this, 16, 0);
		arm3.addBox(-1F, -1F, -2F, 2, 2, 2);
		arm3.setRotationPoint(0F, 0F, -2F);
		arm3.setTextureSize(32, 32);
		arm3.mirror = true;
		setRotation(arm3, 0F, 0F, 0F);
		arm2.addChild(arm3);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		head.render(f5);
		thing.render(f5);
		arm.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		arm.rotateAngleY = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;

		float f15 = 0.02F * (par7Entity.getEntityId() % 10);
		arm1.rotateAngleY = MathHelper.sin(par7Entity.ticksExisted * f15) * 4.5F * (float)Math.PI / 180.0F;
		arm2.rotateAngleY = MathHelper.sin(par7Entity.ticksExisted * f15) * 4.5F * (float)Math.PI / 180.0F;
		arm3.rotateAngleY = MathHelper.sin(par7Entity.ticksExisted * f15) * 4.5F * (float)Math.PI / 180.0F;
		thing.rotateAngleZ = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		head.rotateAngleY = par4 / (180F / (float)Math.PI);

		float f16 = 0.03F;
		t1.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f16) * 4.25F * (float)Math.PI / 180.0F;
		t11.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f16) * 4.25F * (float)Math.PI / 180.0F;
		t12.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f16) * 4.25F * (float)Math.PI / 180.0F;
		t13.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f16) * 4.25F * (float)Math.PI / 180.0F;

		float f17 = 0.04F;
		t2.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f17) * 4.25F * (float)Math.PI / 180.0F;
		t21.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f17) * 4.25F * (float)Math.PI / 180.0F;
		t22.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f17) * 4.25F * (float)Math.PI / 180.0F;
		t23.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f17) * 4.25F * (float)Math.PI / 180.0F;

		float f18 = -0.04F;
		t3.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f18) * 4.25F * (float)Math.PI / 180.0F;
		t31.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f18) * 4.25F * (float)Math.PI / 180.0F;
		t32.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f18) * 4.25F * (float)Math.PI / 180.0F;
		t33.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * f18) * 4.25F * (float)Math.PI / 180.0F;
	}
}
