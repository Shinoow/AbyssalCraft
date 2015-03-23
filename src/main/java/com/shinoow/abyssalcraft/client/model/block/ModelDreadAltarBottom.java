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

public class ModelDreadAltarBottom extends ModelBase {

	ModelRenderer center;
	ModelRenderer pillar1;
	ModelRenderer pillar2;
	ModelRenderer pillar3;
	ModelRenderer pillar4;
	ModelRenderer bottom;
	ModelRenderer top;
	ModelRenderer symbol1;
	ModelRenderer symbol2;
	ModelRenderer symbol3;
	ModelRenderer symbol4;
	ModelRenderer centerspike1;
	ModelRenderer centerspike2;
	ModelRenderer centerspike3;
	ModelRenderer centerspike4;
	ModelRenderer centerpad;
	ModelRenderer toothspike1;
	ModelRenderer toothspike2;
	ModelRenderer toothspike3;
	ModelRenderer toothspike4;
	ModelRenderer toothspike5;
	ModelRenderer toothspike6;
	ModelRenderer toothspike7;
	ModelRenderer toothspike8;
	ModelRenderer toothspike9;
	ModelRenderer toothspike10;
	ModelRenderer toothspike11;
	ModelRenderer toothspike12;
	ModelRenderer toothspike13;
	ModelRenderer toothspike14;
	ModelRenderer toothspike15;
	ModelRenderer toothspike16;

	public ModelDreadAltarBottom()
	{
		textureWidth = 128;
		textureHeight = 64;

		center = new ModelRenderer(this, 0, 0);
		center.addBox(0F, 0F, 0F, 12, 12, 12);
		center.setRotationPoint(-6F, 11F, -6F);
		center.setTextureSize(128, 64);
		center.mirror = true;
		setRotation(center, 0F, 0F, 0F);
		pillar1 = new ModelRenderer(this, 82, 18);
		pillar1.addBox(0F, 0F, 0F, 1, 12, 1);
		pillar1.setRotationPoint(-7F, 11F, -7F);
		pillar1.setTextureSize(128, 64);
		pillar1.mirror = true;
		setRotation(pillar1, 0F, 0F, 0F);
		pillar2 = new ModelRenderer(this, 82, 18);
		pillar2.addBox(0F, 0F, 0F, 1, 12, 1);
		pillar2.setRotationPoint(6F, 11F, -7F);
		pillar2.setTextureSize(128, 64);
		pillar2.mirror = true;
		setRotation(pillar2, 0F, 0F, 0F);
		pillar3 = new ModelRenderer(this, 82, 18);
		pillar3.addBox(0F, 0F, 0F, 1, 12, 1);
		pillar3.setRotationPoint(-7F, 11F, 6F);
		pillar3.setTextureSize(128, 64);
		pillar3.mirror = true;
		setRotation(pillar3, 0F, 0F, 0F);
		pillar4 = new ModelRenderer(this, 82, 18);
		pillar4.addBox(0F, 0F, 0F, 1, 12, 1);
		pillar4.setRotationPoint(6F, 11F, 6F);
		pillar4.setTextureSize(128, 64);
		pillar4.mirror = true;
		setRotation(pillar4, 0F, 0F, 0F);
		bottom = new ModelRenderer(this, 48, 0);
		bottom.addBox(0F, 0F, 0F, 16, 1, 16);
		bottom.setRotationPoint(-8F, 23F, -8F);
		bottom.setTextureSize(128, 64);
		bottom.mirror = true;
		setRotation(bottom, 0F, 0F, 0F);
		top = new ModelRenderer(this, 36, 17);
		top.addBox(0F, 0F, 0F, 14, 1, 14);
		top.setRotationPoint(-7F, 10F, -7F);
		top.setTextureSize(128, 64);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
		symbol1 = new ModelRenderer(this, 99, 0);
		symbol1.addBox(0F, 0F, 0F, 4, 8, 1);
		symbol1.setRotationPoint(-2F, 15F, -7F);
		symbol1.setTextureSize(128, 64);
		symbol1.mirror = true;
		setRotation(symbol1, 0F, 0F, 0F);
		symbol2 = new ModelRenderer(this, 99, 0);
		symbol2.addBox(0F, 0F, 0F, 4, 8, 1);
		symbol2.setRotationPoint(-2F, 15F, 6F);
		symbol2.setTextureSize(128, 64);
		symbol2.mirror = true;
		setRotation(symbol2, 0F, 0F, 0F);
		symbol3 = new ModelRenderer(this, 110, 0);
		symbol3.addBox(0F, 0F, 0F, 1, 8, 4);
		symbol3.setRotationPoint(6F, 15F, -2F);
		symbol3.setTextureSize(128, 64);
		symbol3.mirror = true;
		setRotation(symbol3, 0F, 0F, 0F);
		symbol4 = new ModelRenderer(this, 110, 0);
		symbol4.addBox(0F, 0F, 0F, 1, 8, 4);
		symbol4.setRotationPoint(-7F, 15F, -2F);
		symbol4.setTextureSize(128, 64);
		symbol4.mirror = true;
		setRotation(symbol4, 0F, 0F, 0F);
		centerspike1 = new ModelRenderer(this, 30, 25);
		centerspike1.addBox(0F, 0F, 0F, 1, 4, 1);
		centerspike1.setRotationPoint(3F, 6F, 3F);
		centerspike1.setTextureSize(128, 64);
		centerspike1.mirror = true;
		setRotation(centerspike1, 0F, 0F, 0F);
		centerspike2 = new ModelRenderer(this, 30, 25);
		centerspike2.addBox(0F, 0F, 0F, 1, 4, 1);
		centerspike2.setRotationPoint(3F, 6F, -4F);
		centerspike2.setTextureSize(128, 64);
		centerspike2.mirror = true;
		setRotation(centerspike2, 0F, 0F, 0F);
		centerspike3 = new ModelRenderer(this, 30, 25);
		centerspike3.addBox(0F, 0F, 0F, 1, 4, 1);
		centerspike3.setRotationPoint(-4F, 6F, -4F);
		centerspike3.setTextureSize(128, 64);
		centerspike3.mirror = true;
		setRotation(centerspike3, 0F, 0F, 0F);
		centerspike4 = new ModelRenderer(this, 30, 25);
		centerspike4.addBox(0F, 0F, 0F, 1, 4, 1);
		centerspike4.setRotationPoint(-4F, 6F, 3F);
		centerspike4.setTextureSize(128, 64);
		centerspike4.mirror = true;
		setRotation(centerspike4, 0F, 0F, 0F);
		centerpad = new ModelRenderer(this, 5, 24);
		centerpad.addBox(0F, 0F, 0F, 6, 1, 6);
		centerpad.setRotationPoint(-3F, 9F, -3F);
		centerpad.setTextureSize(128, 64);
		centerpad.mirror = true;
		setRotation(centerpad, 0F, 0F, 0F);
		toothspike1 = new ModelRenderer(this, 0, 25);
		toothspike1.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike1.setRotationPoint(-5F, 7F, 6F);
		toothspike1.setTextureSize(128, 64);
		toothspike1.mirror = true;
		setRotation(toothspike1, 0F, 0F, 0F);
		toothspike2 = new ModelRenderer(this, 0, 25);
		toothspike2.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike2.setRotationPoint(1F, 7F, 6F);
		toothspike2.setTextureSize(128, 64);
		toothspike2.mirror = true;
		setRotation(toothspike2, 0F, 0F, 0F);
		toothspike3 = new ModelRenderer(this, 0, 25);
		toothspike3.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike3.setRotationPoint(4F, 7F, 6F);
		toothspike3.setTextureSize(128, 64);
		toothspike3.mirror = true;
		setRotation(toothspike3, 0F, 0F, 0F);
		toothspike4 = new ModelRenderer(this, 0, 25);
		toothspike4.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike4.setRotationPoint(-2F, 7F, 6F);
		toothspike4.setTextureSize(128, 64);
		toothspike4.mirror = true;
		setRotation(toothspike4, 0F, 0F, 0F);
		toothspike5 = new ModelRenderer(this, 0, 25);
		toothspike5.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike5.setRotationPoint(-7F, 7F, 4F);
		toothspike5.setTextureSize(128, 64);
		toothspike5.mirror = true;
		setRotation(toothspike5, 0F, 0F, 0F);
		toothspike6 = new ModelRenderer(this, 0, 25);
		toothspike6.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike6.setRotationPoint(-7F, 7F, 1F);
		toothspike6.setTextureSize(128, 64);
		toothspike6.mirror = true;
		setRotation(toothspike6, 0F, 0F, 0F);
		toothspike7 = new ModelRenderer(this, 0, 25);
		toothspike7.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike7.setRotationPoint(-7F, 7F, -2F);
		toothspike7.setTextureSize(128, 64);
		toothspike7.mirror = true;
		setRotation(toothspike7, 0F, 0F, 0F);
		toothspike8 = new ModelRenderer(this, 0, 25);
		toothspike8.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike8.setRotationPoint(-7F, 7F, -5F);
		toothspike8.setTextureSize(128, 64);
		toothspike8.mirror = true;
		setRotation(toothspike8, 0F, 0F, 0F);
		toothspike9 = new ModelRenderer(this, 0, 25);
		toothspike9.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike9.setRotationPoint(-5F, 7F, -7F);
		toothspike9.setTextureSize(128, 64);
		toothspike9.mirror = true;
		setRotation(toothspike9, 0F, 0F, 0F);
		toothspike10 = new ModelRenderer(this, 0, 25);
		toothspike10.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike10.setRotationPoint(-2F, 7F, -7F);
		toothspike10.setTextureSize(128, 64);
		toothspike10.mirror = true;
		setRotation(toothspike10, 0F, 0F, 0F);
		toothspike11 = new ModelRenderer(this, 0, 25);
		toothspike11.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike11.setRotationPoint(1F, 7F, -7F);
		toothspike11.setTextureSize(128, 64);
		toothspike11.mirror = true;
		setRotation(toothspike11, 0F, 0F, 0F);
		toothspike12 = new ModelRenderer(this, 0, 25);
		toothspike12.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike12.setRotationPoint(4F, 7F, -7F);
		toothspike12.setTextureSize(128, 64);
		toothspike12.mirror = true;
		setRotation(toothspike12, 0F, 0F, 0F);
		toothspike13 = new ModelRenderer(this, 0, 25);
		toothspike13.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike13.setRotationPoint(6F, 7F, -5F);
		toothspike13.setTextureSize(128, 64);
		toothspike13.mirror = true;
		setRotation(toothspike13, 0F, 0F, 0F);
		toothspike14 = new ModelRenderer(this, 0, 25);
		toothspike14.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike14.setRotationPoint(6F, 7F, -2F);
		toothspike14.setTextureSize(128, 64);
		toothspike14.mirror = true;
		setRotation(toothspike14, 0F, 0F, 0F);
		toothspike15 = new ModelRenderer(this, 0, 25);
		toothspike15.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike15.setRotationPoint(6F, 7F, 1F);
		toothspike15.setTextureSize(128, 64);
		toothspike15.mirror = true;
		setRotation(toothspike15, 0F, 0F, 0F);
		toothspike16 = new ModelRenderer(this, 0, 25);
		toothspike16.addBox(0F, 0F, 0F, 1, 3, 1);
		toothspike16.setRotationPoint(6F, 7F, 4F);
		toothspike16.setTextureSize(128, 64);
		toothspike16.mirror = true;
		setRotation(toothspike16, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		center.render(f5);
		pillar1.render(f5);
		pillar2.render(f5);
		pillar3.render(f5);
		pillar4.render(f5);
		bottom.render(f5);
		top.render(f5);
		symbol1.render(f5);
		symbol2.render(f5);
		symbol3.render(f5);
		symbol4.render(f5);
		centerspike1.render(f5);
		centerspike2.render(f5);
		centerspike3.render(f5);
		centerspike4.render(f5);
		centerpad.render(f5);
		toothspike1.render(f5);
		toothspike2.render(f5);
		toothspike3.render(f5);
		toothspike4.render(f5);
		toothspike5.render(f5);
		toothspike6.render(f5);
		toothspike7.render(f5);
		toothspike8.render(f5);
		toothspike9.render(f5);
		toothspike10.render(f5);
		toothspike11.render(f5);
		toothspike12.render(f5);
		toothspike13.render(f5);
		toothspike14.render(f5);
		toothspike15.render(f5);
		toothspike16.render(f5);
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
