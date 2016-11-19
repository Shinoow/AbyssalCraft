/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
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
	public ModelRenderer arm;

	public ModelDreadSpawn()
	{
		textureWidth = 128;
		textureHeight = 64;

		body = new ModelRenderer(this, 52, 48);
		body.addBox(-3F, -3F, -3F, 6, 5, 6);
		body.setRotationPoint(0F, 22F, 0F);
		body.setTextureSize(128, 64);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		head = new ModelRenderer(this, 37, 18);
		head.addBox(-1.5F, -4F, -1.5F, 3, 3, 3);
		head.setRotationPoint(0F, 19F, 0F);
		head.setTextureSize(128, 64);
		head.mirror = true;
		setRotation(head, -0.4833219F, 0F, 0F);
		jaw = new ModelRenderer(this, 17, 23);
		jaw.addBox(-1.5F, -1F, -1.5F, 3, 1, 3);
		jaw.setRotationPoint(0F, 0F, 0F);
		jaw.setTextureSize(128, 64);
		head.addChild(jaw);
		jaw.mirror = true;
		setRotation(jaw, 0.4833219F, 0F, 0F);
		thing = new ModelRenderer(this, 42, 34);
		thing.addBox(0F, -1F, -3F, 6, 1, 6);
		thing.setRotationPoint(3F, 21F, 2F);
		thing.setTextureSize(128, 64);
		thing.mirror = true;
		setRotation(thing, 0F, 0F, 0F);
		arm = new ModelRenderer(this, 33, 7);
		arm.addBox(-1F, -1F, -6F, 2, 2, 6);
		arm.setRotationPoint(-2F, 22F, -2F);
		arm.setTextureSize(128, 64);
		arm.mirror = true;
		setRotation(arm, 0F, 0F, 0F);
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
		thing.rotateAngleZ = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		head.rotateAngleY = par4 / (180F / (float)Math.PI);
	}
}
