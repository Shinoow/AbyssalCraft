/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelJzahar extends ModelBase
{

	public ModelRenderer head;
	public ModelRenderer body;
	public ModelRenderer bodyr;
	public ModelRenderer bodyl;
	public ModelRenderer rightarm;
	public ModelRenderer leftarm;
	public ModelRenderer rightleg;
	public ModelRenderer leftleg;

	public ModelJzahar()
	{
		textureWidth = 128;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-7F, -14F, -7F, 14, 14, 14);
		head.setRotationPoint(4F, -28F, 2F);
		head.setTextureSize(128, 64);
		head.mirror = false;
		setRotation(head, 0F, 0F, 0F);
		body = new ModelRenderer(this, 56, 0);
		body.addBox(-8F, -16F, -2F, 16, 24, 8);
		body.setRotationPoint(4F, -12F, 0F);
		body.setTextureSize(128, 64);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		bodyr = new ModelRenderer(this, 104, 0);
		bodyr.addBox(-8F, -16F, -3F, 5, 31, 1);
		bodyr.setRotationPoint(4F, -12F, 0F);
		bodyr.setTextureSize(128, 64);
		bodyr.mirror = false;
		setRotation(bodyr, 0F, 0F, 0F);
		bodyl = new ModelRenderer(this, 116, 0);
		bodyl.addBox(3F, -16F, -3F, 5, 31, 1);
		bodyl.setRotationPoint(4F, -12F, 0F);
		bodyl.setTextureSize(128, 64);
		bodyl.mirror = true;
		setRotation(bodyl, 0F, 0F, 0F);
		rightarm = new ModelRenderer(this, 30, 30);
		rightarm.addBox(-6F, -4F, -4F, 7, 26, 8);
		rightarm.setRotationPoint(-5F, -24F, 2F);
		rightarm.setTextureSize(128, 64);
		rightarm.mirror = false;
		setRotation(rightarm, 0F, 0F, 0F);
		leftarm = new ModelRenderer(this, 30, 30);
		leftarm.addBox(-1F, -4F, -4F, 7, 26, 8);
		leftarm.setRotationPoint(13F, -24F, 2F);
		leftarm.setTextureSize(128, 64);
		leftarm.mirror = true;
		setRotation(leftarm, 0F, 0F, 0F);
		rightleg = new ModelRenderer(this, 0, 30);
		rightleg.addBox(-2F, 0F, -4F, 7, 26, 8);
		rightleg.setRotationPoint(-2F, -4F, 2F);
		rightleg.setTextureSize(128, 64);
		rightleg.mirror = false;
		setRotation(rightleg, 0F, 0F, 0F);
		leftleg = new ModelRenderer(this, 0, 30);
		leftleg.addBox(-3F, 0F, -4F, 7, 26, 8);
		leftleg.setRotationPoint(8F, -4F, 2F);
		leftleg.setTextureSize(128, 64);
		leftleg.mirror = true;
		setRotation(leftleg, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		body.render(f5);
		bodyr.render(f5);
		bodyl.render(f5);
		rightarm.render(f5);
		leftarm.render(f5);
		rightleg.render(f5);
		leftleg.render(f5);
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
	}

}
