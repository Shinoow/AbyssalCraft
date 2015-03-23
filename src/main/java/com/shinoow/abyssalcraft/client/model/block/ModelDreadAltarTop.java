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
package com.shinoow.abyssalcraft.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDreadAltarTop extends ModelBase {

	ModelRenderer head;
	ModelRenderer side1;
	ModelRenderer side2;
	ModelRenderer side3;
	ModelRenderer side4;
	ModelRenderer bighorn1;
	ModelRenderer bighorn2;
	ModelRenderer bighorn3;
	ModelRenderer bighorn4;
	ModelRenderer bowl1;
	ModelRenderer bowl2;
	ModelRenderer bowl3;
	ModelRenderer bowl4;
	ModelRenderer substance;

	public ModelDreadAltarTop()
	{
		textureWidth = 128;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 32);
		head.addBox(0F, 0F, 0F, 6, 8, 6);
		head.setRotationPoint(-3F, 17F, -3F);
		head.setTextureSize(128, 64);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		side1 = new ModelRenderer(this, 11, 14);
		side1.addBox(0F, 0F, 0F, 6, 1, 3);
		side1.setRotationPoint(-3F, 23F, 3F);
		side1.setTextureSize(128, 64);
		side1.mirror = true;
		setRotation(side1, 0F, 0F, 0F);
		side2 = new ModelRenderer(this, 11, 14);
		side2.addBox(0F, 0F, 0F, 6, 1, 3);
		side2.setRotationPoint(-3F, 23F, -6F);
		side2.setTextureSize(128, 64);
		side2.mirror = true;
		setRotation(side2, 0F, 0F, 0F);
		side3 = new ModelRenderer(this, 11, 14);
		side3.addBox(0F, 0F, 0F, 3, 1, 6);
		side3.setRotationPoint(3F, 23F, -3F);
		side3.setTextureSize(128, 64);
		side3.mirror = true;
		setRotation(side3, 0F, 0F, 0F);
		side4 = new ModelRenderer(this, 11, 14);
		side4.addBox(0F, 0F, 0F, 3, 1, 6);
		side4.setRotationPoint(-6F, 23F, -3F);
		side4.setTextureSize(128, 64);
		side4.mirror = true;
		setRotation(side4, 0F, 0F, 0F);
		bighorn1 = new ModelRenderer(this, 82, 18);
		bighorn1.addBox(0F, -6F, 0F, 1, 6, 1);
		bighorn1.setRotationPoint(2F, 18F, -3F);
		bighorn1.setTextureSize(128, 64);
		bighorn1.mirror = true;
		setRotation(bighorn1, 0.6108652F, -0.7853982F, 0F);
		bighorn2 = new ModelRenderer(this, 82, 18);
		bighorn2.addBox(-0.5F, -6F, 0F, 1, 6, 1);
		bighorn2.setRotationPoint(-2F, 18F, 2F);
		bighorn2.setTextureSize(128, 64);
		bighorn2.mirror = true;
		setRotation(bighorn2, -0.6108652F, -0.7853982F, 0F);
		bighorn3 = new ModelRenderer(this, 82, 18);
		bighorn3.addBox(0F, -6F, 0F, 1, 6, 1);
		bighorn3.setRotationPoint(-3F, 18F, -2F);
		bighorn3.setTextureSize(128, 64);
		bighorn3.mirror = true;
		setRotation(bighorn3, 0.6108652F, 0.7853982F, 0F);
		bighorn4 = new ModelRenderer(this, 82, 18);
		bighorn4.addBox(0F, -6F, -1F, 1, 6, 1);
		bighorn4.setRotationPoint(2F, 18F, 3F);
		bighorn4.setTextureSize(128, 64);
		bighorn4.mirror = true;
		setRotation(bighorn4, -0.6108652F, 0.7853982F, 0F);
		bowl1 = new ModelRenderer(this, 13, 0);
		bowl1.addBox(0F, 0F, 0F, 4, 2, 1);
		bowl1.setRotationPoint(-2F, 15F, 1F);
		bowl1.setTextureSize(128, 64);
		bowl1.mirror = true;
		setRotation(bowl1, 0F, 0F, 0F);
		bowl2 = new ModelRenderer(this, 13, 0);
		bowl2.addBox(0F, 0F, 0F, 4, 2, 1);
		bowl2.setRotationPoint(-2F, 15F, -2F);
		bowl2.setTextureSize(128, 64);
		bowl2.mirror = true;
		setRotation(bowl2, 0F, 0F, 0F);
		bowl3 = new ModelRenderer(this, 13, 0);
		bowl3.addBox(0F, 0F, 0F, 1, 2, 2);
		bowl3.setRotationPoint(-2F, 15F, -1F);
		bowl3.setTextureSize(128, 64);
		bowl3.mirror = true;
		setRotation(bowl3, 0F, 0F, 0F);
		bowl4 = new ModelRenderer(this, 13, 0);
		bowl4.addBox(0F, 0F, 0F, 1, 2, 2);
		bowl4.setRotationPoint(1F, 15F, -1F);
		bowl4.setTextureSize(128, 64);
		bowl4.mirror = true;
		setRotation(bowl4, 0F, 0F, 0F);
		substance = new ModelRenderer(this, 67, 23);
		substance.addBox(0F, 0F, 0F, 2, 1, 2);
		substance.setRotationPoint(-1F, 16F, -1F);
		substance.setTextureSize(128, 64);
		substance.mirror = true;
		setRotation(substance, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		side1.render(f5);
		side2.render(f5);
		side3.render(f5);
		side4.render(f5);
		bighorn1.render(f5);
		bighorn2.render(f5);
		bighorn3.render(f5);
		bighorn4.render(f5);
		bowl1.render(f5);
		bowl2.render(f5);
		bowl3.render(f5);
		bowl4.render(f5);
		substance.render(f5);
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
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
