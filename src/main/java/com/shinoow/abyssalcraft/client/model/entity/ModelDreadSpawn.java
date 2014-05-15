/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelDreadSpawn extends ModelBase
{

	ModelRenderer Body;
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg4;
	ModelRenderer leg5;
	ModelRenderer eye1;
	ModelRenderer eye2;
	ModelRenderer eye3;
	ModelRenderer eye4;

	public ModelDreadSpawn()
	{
		textureWidth = 64;
		textureHeight = 32;

		Body = new ModelRenderer(this, 0, 0);
		Body.addBox(0F, 0F, 0F, 3, 3, 3);
		Body.setRotationPoint(-1F, 21F, -1F);
		Body.setTextureSize(64, 32);
		Body.mirror = true;
		setRotation(Body, 0F, 0F, 0F);
		leg1 = new ModelRenderer(this, 14, 11);
		leg1.addBox(0F, -1F, -1F, 5, 2, 2);
		leg1.setRotationPoint(2F, 22.5F, 0.5F);
		leg1.setTextureSize(64, 32);
		leg1.mirror = true;
		setRotation(leg1, 0F, 0F, 0F);
		leg2 = new ModelRenderer(this, 22, 0);
		leg2.addBox(-1F, -1F, 0F, 2, 2, 5);
		leg2.setRotationPoint(0.5F, 22.5F, 2F);
		leg2.setTextureSize(64, 32);
		leg2.mirror = true;
		setRotation(leg2, 0F, 0F, 0F);
		leg3 = new ModelRenderer(this, 19, 18);
		leg3.addBox(-1F, -1F, -5F, 2, 2, 5);
		leg3.setRotationPoint(0.5F, 22.5F, -1F);
		leg3.setTextureSize(64, 32);
		leg3.mirror = true;
		setRotation(leg3, 0F, 0F, 0F);
		leg4 = new ModelRenderer(this, 43, 4);
		leg4.addBox(-5F, -1F, -1F, 5, 2, 2);
		leg4.setRotationPoint(-1F, 22.5F, 0.5F);
		leg4.setTextureSize(64, 32);
		leg4.mirror = true;
		setRotation(leg4, 0F, 0F, 0F);
		leg5 = new ModelRenderer(this, 0, 16);
		leg5.addBox(-1F, -5F, -1F, 2, 5, 2);
		leg5.setRotationPoint(0.5F, 21F, 0.5F);
		leg5.setTextureSize(64, 32);
		leg5.mirror = true;
		setRotation(leg5, 0F, 0F, 0F);
		eye1 = new ModelRenderer(this, 34, 12);
		eye1.addBox(0F, -1.5F, 0F, 3, 3, 3);
		eye1.setRotationPoint(1F, 22.5F, 1F);
		eye1.setTextureSize(64, 32);
		eye1.mirror = true;
		setRotation(eye1, 0F, 0F, 0F);
		eye2 = new ModelRenderer(this, 34, 12);
		eye2.addBox(-3F, -1.5F, -3F, 3, 3, 3);
		eye2.setRotationPoint(0F, 22.5F, 0F);
		eye2.setTextureSize(64, 32);
		eye2.mirror = true;
		setRotation(eye2, 0F, 0F, 0F);
		eye3 = new ModelRenderer(this, 34, 12);
		eye3.addBox(0F, -1.5F, -3F, 3, 3, 3);
		eye3.setRotationPoint(1F, 22.5F, 0F);
		eye3.setTextureSize(64, 32);
		eye3.mirror = true;
		setRotation(eye3, 0F, 0F, 0F);
		eye4 = new ModelRenderer(this, 34, 12);
		eye4.addBox(-3F, -1.5F, 0F, 3, 3, 3);
		eye4.setRotationPoint(0F, 22.5F, 1F);
		eye4.setTextureSize(64, 32);
		eye4.mirror = true;
		setRotation(eye4, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Body.render(f5);
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
		leg5.render(f5);
		eye1.render(f5);
		eye2.render(f5);
		eye3.render(f5);
		eye4.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		this.eye1.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.eye1.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.eye2.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.eye2.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.eye3.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.eye3.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.eye4.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.eye4.rotateAngleX = par5 / (180F / (float)Math.PI);

		this.leg1.rotateAngleY = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
		this.leg2.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
		this.leg3.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.leg4.rotateAngleY = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.leg5.rotateAngleY = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.0F * par2 * -0.5F;

		this.leg1.rotateAngleZ = 0.0F;
		this.leg2.rotateAngleZ = 0.0F;
		this.leg3.rotateAngleZ = 0.0F;
		this.leg4.rotateAngleZ = 0.0F;
		this.leg5.rotateAngleZ = 0.0F;

	}

}
