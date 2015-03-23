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

public class ModelChagarothFist extends ModelBase {

	public ModelRenderer arm1;
	public ModelRenderer ground;
	public ModelRenderer arm2;
	public ModelRenderer arm3;
	public ModelRenderer palm1;
	public ModelRenderer palm2;
	public ModelRenderer finger1;
	public ModelRenderer finger2;
	public ModelRenderer finger3;
	public ModelRenderer finger4;
	public ModelRenderer finger5;
	public ModelRenderer finger12;
	public ModelRenderer finger22;
	public ModelRenderer finger32;
	public ModelRenderer finger42;
	public ModelRenderer finger52;
	public ModelRenderer finger13;
	public ModelRenderer finger23;
	public ModelRenderer finger33;
	public ModelRenderer finger43;
	public ModelRenderer finger53;
	public ModelRenderer eye;

	public ModelChagarothFist()
	{
		textureWidth = 64;
		textureHeight = 32;

		arm1 = new ModelRenderer(this, 0, 0);
		arm1.addBox(0F, 0F, 0F, 2, 14, 2);
		arm1.setRotationPoint(-1F, 15F, -1F);
		arm1.setTextureSize(64, 32);
		arm1.mirror = true;
		setRotation(arm1, 0F, 0F, 0F);
		ground = new ModelRenderer(this, 8, 0);
		ground.addBox(0F, 0F, 0F, 6, 1, 6);
		ground.setRotationPoint(-3F, 23F, -3F);
		ground.setTextureSize(64, 32);
		ground.mirror = true;
		setRotation(ground, 0F, 0F, 0F);
		arm2 = new ModelRenderer(this, 0, 0);
		arm2.addBox(-1F, -5F, -1F, 2, 5, 2);
		arm2.setRotationPoint(0F, 15.5F, 0F);
		arm2.setTextureSize(64, 32);
		arm2.mirror = true;
		setRotation(arm2, -0.3346075F, 0F, 0F);
		arm3 = new ModelRenderer(this, 0, 0);
		arm3.addBox(-1F, -4F, -1F, 2, 4, 2);
		arm3.setRotationPoint(0F, 11F, 1.5F);
		arm3.setTextureSize(64, 32);
		arm3.mirror = true;
		setRotation(arm3, 0F, 0F, 0F);
		palm1 = new ModelRenderer(this, 26, 0);
		palm1.addBox(0F, 0F, 0F, 6, 1, 4);
		palm1.setRotationPoint(-3F, 5F, 0.5F);
		palm1.setTextureSize(64, 32);
		palm1.mirror = true;
		setRotation(palm1, 0F, 0F, 0F);
		palm2 = new ModelRenderer(this, 42, 0);
		palm2.addBox(0F, 0F, 0F, 4, 1, 3);
		palm2.setRotationPoint(-2F, 6F, 0.5F);
		palm2.setTextureSize(64, 32);
		palm2.mirror = true;
		setRotation(palm2, 0F, 0F, 0F);
		finger1 = new ModelRenderer(this, 8, 0);
		finger1.addBox(0F, -3F, 0F, 1, 3, 1);
		finger1.setRotationPoint(-3F, 5.5F, 4F);
		finger1.setTextureSize(64, 32);
		finger1.mirror = true;
		setRotation(finger1, -0.4833219F, 0F, 0F);
		finger2 = new ModelRenderer(this, 8, 0);
		finger2.addBox(0F, -3F, 0F, 1, 3, 1);
		finger2.setRotationPoint(-1.5F, 5.5F, 4F);
		finger2.setTextureSize(64, 32);
		finger2.mirror = true;
		setRotation(finger2, -0.4833219F, 0F, 0F);
		finger3 = new ModelRenderer(this, 8, 0);
		finger3.addBox(0F, -3F, 0F, 1, 3, 1);
		finger3.setRotationPoint(0F, 5.5F, 4F);
		finger3.setTextureSize(64, 32);
		finger3.mirror = true;
		setRotation(finger3, -0.4833219F, 0F, 0F);
		finger4 = new ModelRenderer(this, 8, 0);
		finger4.addBox(0F, -3F, 0F, 1, 3, 1);
		finger4.setRotationPoint(1.5F, 5.5F, 4F);
		finger4.setTextureSize(64, 32);
		finger4.mirror = true;
		setRotation(finger4, -0.4833219F, 0F, 0F);
		finger5 = new ModelRenderer(this, 8, 0);
		finger5.addBox(0F, -3F, 0F, 1, 3, 1);
		finger5.setRotationPoint(2F, 5.5F, 3F);
		finger5.setTextureSize(64, 32);
		finger5.mirror = true;
		setRotation(finger5, -0.4833219F, 1.570796F, 0F);
		finger12 = new ModelRenderer(this, 8, 0);
		finger12.addBox(0F, -3F, 0F, 1, 3, 1);
		finger12.setRotationPoint(-3F, 3.5F, 5F);
		finger12.setTextureSize(64, 32);
		finger12.mirror = true;
		setRotation(finger12, 0F, 0F, 0F);
		finger22 = new ModelRenderer(this, 8, 0);
		finger22.addBox(0F, -3F, 0F, 1, 3, 1);
		finger22.setRotationPoint(-1.5F, 3.5F, 5F);
		finger22.setTextureSize(64, 32);
		finger22.mirror = true;
		setRotation(finger22, 0F, 0F, 0F);
		finger32 = new ModelRenderer(this, 8, 0);
		finger32.addBox(0F, -3F, 0F, 1, 3, 1);
		finger32.setRotationPoint(0F, 3.5F, 5F);
		finger32.setTextureSize(64, 32);
		finger32.mirror = true;
		setRotation(finger32, 0F, 0F, 0F);
		finger42 = new ModelRenderer(this, 8, 0);
		finger42.addBox(0F, -3F, 0F, 1, 3, 1);
		finger42.setRotationPoint(1.5F, 3.5F, 5F);
		finger42.setTextureSize(64, 32);
		finger42.mirror = true;
		setRotation(finger42, 0F, 0F, 0F);
		finger52 = new ModelRenderer(this, 8, 0);
		finger52.addBox(0F, -3F, 0F, 1, 3, 1);
		finger52.setRotationPoint(3F, 3.5F, 2F);
		finger52.setTextureSize(64, 32);
		finger52.mirror = true;
		setRotation(finger52, 0F, 0F, 0F);
		finger13 = new ModelRenderer(this, 8, 0);
		finger13.addBox(0F, -3F, 0F, 1, 3, 1);
		finger13.setRotationPoint(-3F, 1F, 5F);
		finger13.setTextureSize(64, 32);
		finger13.mirror = true;
		setRotation(finger13, 0.4833166F, 0F, 0F);
		finger23 = new ModelRenderer(this, 8, 0);
		finger23.addBox(0F, -3F, 0F, 1, 3, 1);
		finger23.setRotationPoint(-1.5F, 1F, 5F);
		finger23.setTextureSize(64, 32);
		finger23.mirror = true;
		setRotation(finger23, 0.4833166F, 0F, 0F);
		finger33 = new ModelRenderer(this, 8, 0);
		finger33.addBox(0F, -3F, 0F, 1, 3, 1);
		finger33.setRotationPoint(0F, 1F, 5F);
		finger33.setTextureSize(64, 32);
		finger33.mirror = true;
		setRotation(finger33, 0.4833166F, 0F, 0F);
		finger43 = new ModelRenderer(this, 8, 0);
		finger43.addBox(0F, -3F, 0F, 1, 3, 1);
		finger43.setRotationPoint(1.5F, 1F, 5F);
		finger43.setTextureSize(64, 32);
		finger43.mirror = true;
		setRotation(finger43, 0.4833166F, 0F, 0F);
		finger53 = new ModelRenderer(this, 8, 0);
		finger53.addBox(0F, -1F, 0F, 1, 1, 1);
		finger53.setRotationPoint(3F, 1F, 3F);
		finger53.setTextureSize(64, 32);
		finger53.mirror = true;
		setRotation(finger53, 0.4833166F, 1.570796F, 0F);
		eye = new ModelRenderer(this, 8, 7);
		eye.addBox(-1.5F, -3F, -1.5F, 3, 3, 3);
		eye.setRotationPoint(0F, 6F, 1F);
		eye.setTextureSize(64, 32);
		eye.mirror = true;
		setRotation(eye, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		arm1.render(f5);
		ground.render(f5);
		arm2.render(f5);
		arm3.render(f5);
		palm1.render(f5);
		palm2.render(f5);
		finger1.render(f5);
		finger2.render(f5);
		finger3.render(f5);
		finger4.render(f5);
		finger5.render(f5);
		finger12.render(f5);
		finger22.render(f5);
		finger32.render(f5);
		finger42.render(f5);
		finger52.render(f5);
		finger13.render(f5);
		finger23.render(f5);
		finger33.render(f5);
		finger43.render(f5);
		finger53.render(f5);
		eye.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		eye.rotateAngleY = f3 / (180F / (float)Math.PI);
		eye.rotateAngleX = f3 / (180F / (float)Math.PI);


	}
}