/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
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

public class ModelChagarothSpawn extends ModelBase {

	public ModelRenderer body;
	public ModelRenderer smallspike1;
	public ModelRenderer smallspike2;
	public ModelRenderer smallspike3;
	public ModelRenderer smallspike4;
	public ModelRenderer bigspike1;
	public ModelRenderer bigspike2;
	public ModelRenderer bigspike3;
	public ModelRenderer bigspike4;
	public ModelRenderer leftleg1;
	public ModelRenderer leftleg2;
	public ModelRenderer leftleg3;
	public ModelRenderer backleg1;
	public ModelRenderer backleg2;
	public ModelRenderer backleg3;
	public ModelRenderer frontleg1;
	public ModelRenderer frontleg2;
	public ModelRenderer frontleg3;
	public ModelRenderer rightleg1;
	public ModelRenderer rightleg2;
	public ModelRenderer rightleg3;

	public ModelChagarothSpawn()
	{
		textureWidth = 32;
		textureHeight = 32;

		body = new ModelRenderer(this, 0, 7);
		body.addBox(-3F, -3F, -3F, 6, 6, 6);
		body.setRotationPoint(0F, 21F, 0F);
		body.setTextureSize(32, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		smallspike1 = new ModelRenderer(this, 18, 1);
		smallspike1.addBox(0F, -4F, 0F, 1, 5, 1);
		smallspike1.setRotationPoint(2F, 18F, -3F);
		smallspike1.setTextureSize(32, 32);
		smallspike1.mirror = true;
		setRotation(smallspike1, 0.3346075F, -0.7435722F, 0F);
		smallspike2 = new ModelRenderer(this, 18, 1);
		smallspike2.addBox(0F, -4F, 0F, 1, 5, 1);
		smallspike2.setRotationPoint(-3F, 18F, -2F);
		smallspike2.setTextureSize(32, 32);
		smallspike2.mirror = true;
		setRotation(smallspike2, 0.3346145F, 0.8922867F, 0F);
		smallspike3 = new ModelRenderer(this, 18, 1);
		smallspike3.addBox(0F, -4F, 0F, 1, 5, 1);
		smallspike3.setRotationPoint(-2F, 18F, 3F);
		smallspike3.setTextureSize(32, 32);
		smallspike3.mirror = true;
		setRotation(smallspike3, 0.3346075F, 2.41661F, 0F);
		smallspike4 = new ModelRenderer(this, 18, 1);
		smallspike4.addBox(0F, -4F, 0F, 1, 5, 1);
		smallspike4.setRotationPoint(3F, 18F, 2F);
		smallspike4.setTextureSize(32, 32);
		smallspike4.mirror = true;
		setRotation(smallspike4, 0.3346075F, -2.379431F, 0F);
		bigspike1 = new ModelRenderer(this, 12, 0);
		bigspike1.addBox(-1F, -5F, -1F, 2, 5, 2);
		bigspike1.setRotationPoint(0F, 21F, 2F);
		bigspike1.setTextureSize(32, 32);
		bigspike1.mirror = true;
		setRotation(bigspike1, -0.8922867F, 0F, 0F);
		bigspike2 = new ModelRenderer(this, 12, 0);
		bigspike2.addBox(-1F, -5F, -1F, 2, 5, 2);
		bigspike2.setRotationPoint(0F, 21F, -2F);
		bigspike2.setTextureSize(32, 32);
		bigspike2.mirror = true;
		setRotation(bigspike2, 0.8922821F, 0F, 0F);
		bigspike3 = new ModelRenderer(this, 12, 0);
		bigspike3.addBox(-1F, -5F, -1F, 2, 5, 2);
		bigspike3.setRotationPoint(-2F, 21F, 0F);
		bigspike3.setTextureSize(32, 32);
		bigspike3.mirror = true;
		setRotation(bigspike3, 0F, 0F, -0.8922821F);
		bigspike4 = new ModelRenderer(this, 12, 0);
		bigspike4.addBox(-1F, -5F, -1F, 2, 5, 2);
		bigspike4.setRotationPoint(2F, 21F, 0F);
		bigspike4.setTextureSize(32, 32);
		bigspike4.mirror = true;
		setRotation(bigspike4, 0F, 0F, 0.8922821F);
		leftleg1 = new ModelRenderer(this, 0, 0);
		leftleg1.addBox(0F, 0F, 0F, 5, 1, 1);
		leftleg1.setRotationPoint(3F, 23F, -2.5F);
		leftleg1.setTextureSize(32, 32);
		leftleg1.mirror = true;
		setRotation(leftleg1, 0F, 0F, 0F);
		leftleg2 = new ModelRenderer(this, 0, 0);
		leftleg2.addBox(0F, 0F, 0F, 5, 1, 1);
		leftleg2.setRotationPoint(3F, 23F, -0.5F);
		leftleg2.setTextureSize(32, 32);
		leftleg2.mirror = true;
		setRotation(leftleg2, 0F, 0F, 0F);
		leftleg3 = new ModelRenderer(this, 0, 0);
		leftleg3.addBox(0F, 0F, 0F, 5, 1, 1);
		leftleg3.setRotationPoint(3F, 23F, 1.5F);
		leftleg3.setTextureSize(32, 32);
		leftleg3.mirror = true;
		setRotation(leftleg3, 0F, 0F, 0F);
		backleg1 = new ModelRenderer(this, 0, 0);
		backleg1.addBox(0F, 0F, 0F, 1, 1, 5);
		backleg1.setRotationPoint(1.5F, 23F, 3F);
		backleg1.setTextureSize(32, 32);
		backleg1.mirror = true;
		setRotation(backleg1, 0F, 0F, 0F);
		backleg2 = new ModelRenderer(this, 0, 0);
		backleg2.addBox(0F, 0F, 0F, 1, 1, 5);
		backleg2.setRotationPoint(-0.5F, 23F, 3F);
		backleg2.setTextureSize(32, 32);
		backleg2.mirror = true;
		setRotation(backleg2, 0F, 0F, 0F);
		backleg3 = new ModelRenderer(this, 0, 0);
		backleg3.addBox(0F, 0F, 0F, 1, 1, 5);
		backleg3.setRotationPoint(-2.5F, 23F, 3F);
		backleg3.setTextureSize(32, 32);
		backleg3.mirror = true;
		setRotation(backleg3, 0F, 0F, 0F);
		frontleg1 = new ModelRenderer(this, 0, 0);
		frontleg1.addBox(0F, 0F, -5F, 1, 1, 5);
		frontleg1.setRotationPoint(1.5F, 23F, -3F);
		frontleg1.setTextureSize(32, 32);
		frontleg1.mirror = true;
		setRotation(frontleg1, 0F, 0F, 0F);
		frontleg2 = new ModelRenderer(this, 0, 0);
		frontleg2.addBox(0F, 0F, -5F, 1, 1, 5);
		frontleg2.setRotationPoint(-0.5F, 23F, -3F);
		frontleg2.setTextureSize(32, 32);
		frontleg2.mirror = true;
		setRotation(frontleg2, 0F, 0F, 0F);
		frontleg3 = new ModelRenderer(this, 0, 0);
		frontleg3.addBox(0F, 0F, -5F, 1, 1, 5);
		frontleg3.setRotationPoint(-2.5F, 23F, -3F);
		frontleg3.setTextureSize(32, 32);
		frontleg3.mirror = true;
		setRotation(frontleg3, 0F, 0F, 0F);
		rightleg1 = new ModelRenderer(this, 0, 0);
		rightleg1.addBox(-5F, 0F, 0F, 5, 1, 1);
		rightleg1.setRotationPoint(-3F, 23F, 1.5F);
		rightleg1.setTextureSize(32, 32);
		rightleg1.mirror = true;
		setRotation(rightleg1, 0F, 0F, 0F);
		rightleg2 = new ModelRenderer(this, 0, 0);
		rightleg2.addBox(-5F, 0F, 0F, 5, 1, 1);
		rightleg2.setRotationPoint(-3F, 23F, -0.5F);
		rightleg2.setTextureSize(32, 32);
		rightleg2.mirror = true;
		setRotation(rightleg2, 0F, 0F, 0F);
		rightleg3 = new ModelRenderer(this, 0, 0);
		rightleg3.addBox(-5F, 0F, 0F, 5, 1, 1);
		rightleg3.setRotationPoint(-3F, 23F, -2.5F);
		rightleg3.setTextureSize(32, 32);
		rightleg3.mirror = true;
		setRotation(rightleg3, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		smallspike1.render(f5);
		smallspike2.render(f5);
		smallspike3.render(f5);
		smallspike4.render(f5);
		bigspike1.render(f5);
		bigspike2.render(f5);
		bigspike3.render(f5);
		bigspike4.render(f5);
		leftleg1.render(f5);
		leftleg2.render(f5);
		leftleg3.render(f5);
		backleg1.render(f5);
		backleg2.render(f5);
		backleg3.render(f5);
		frontleg1.render(f5);
		frontleg2.render(f5);
		frontleg3.render(f5);
		rightleg1.render(f5);
		rightleg2.render(f5);
		rightleg3.render(f5);
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
		frontleg1.rotateAngleY = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
		frontleg2.rotateAngleY = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
		frontleg3.rotateAngleY = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
		leftleg1.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
		leftleg2.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
		leftleg3.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
		backleg1.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		backleg2.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		backleg3.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		rightleg1.rotateAngleY = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		rightleg2.rotateAngleY = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		rightleg3.rotateAngleY = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
	}
}
