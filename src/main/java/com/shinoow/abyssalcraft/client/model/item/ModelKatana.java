/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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
package com.shinoow.abyssalcraft.client.model.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKatana extends ModelBase {

	ModelRenderer handle;
	ModelRenderer tsuba;
	ModelRenderer blade1;
	ModelRenderer blade2;
	ModelRenderer blade3;

	public ModelKatana()
	{
		textureWidth = 64;
		textureHeight = 32;

		handle = new ModelRenderer(this, 0, 0);
		handle.addBox(0F, 0F, 0F, 2, 2, 7);
		handle.setRotationPoint(-7F, 9F, -4F);
		handle.setTextureSize(64, 32);
		handle.mirror = true;
		setRotation(handle, 0F, 0F, 0F);
		tsuba = new ModelRenderer(this, 18, 0);
		tsuba.addBox(0F, 0F, 0F, 4, 4, 1);
		tsuba.setRotationPoint(-8F, 8F, -5F);
		tsuba.setTextureSize(64, 32);
		tsuba.mirror = true;
		setRotation(tsuba, 0F, 0F, 0F);
		blade1 = new ModelRenderer(this, 22, 0);
		blade1.addBox(0F, -1F, -6F, 1, 2, 7);
		blade1.setRotationPoint(-6.5F, 10F, -6F);
		blade1.setTextureSize(64, 32);
		blade1.mirror = true;
		setRotation(blade1, 0F, 0F, 0F);
		blade2 = new ModelRenderer(this, 22, 0);
		blade2.addBox(0F, -1F, -6F, 1, 2, 7);
		blade2.setRotationPoint(-6.5F, 10F, -12F);
		blade2.setTextureSize(64, 32);
		blade2.mirror = true;
		setRotation(blade2, -0.0743572F, 0F, 0F);
		blade3 = new ModelRenderer(this, 22, 0);
		blade3.addBox(0F, -1F, -6F, 1, 2, 7);
		blade3.setRotationPoint(-6.5F, 9.5F, -18F);
		blade3.setTextureSize(64, 32);
		blade3.mirror = true;
		setRotation(blade3, -0.2230717F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		handle.render(f5);
		tsuba.render(f5);
		blade1.render(f5);
		blade2.render(f5);
		blade3.render(f5);
	}

	public void render(float f){

		handle.render(f);
		tsuba.render(f);
		blade1.render(f);
		blade2.render(f);
		blade3.render(f);
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
