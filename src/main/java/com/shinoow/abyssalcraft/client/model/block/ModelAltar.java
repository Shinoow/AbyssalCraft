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
package com.shinoow.abyssalcraft.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAltar extends ModelBase
{

	ModelRenderer Basebot;
	ModelRenderer Basemid;
	ModelRenderer Basetop;
	ModelRenderer Candle1;
	ModelRenderer Candle2;
	ModelRenderer Candle3;
	ModelRenderer Candle4;
	ModelRenderer Center;
	ModelRenderer Bowl1;
	ModelRenderer Bowl2;
	ModelRenderer Bowl3;
	ModelRenderer Bowl4;
	ModelRenderer Liquid;

	public static boolean isActivated = false;

	public ModelAltar()
	{
		textureWidth = 64;
		textureHeight = 32;

		Basebot = new ModelRenderer(this, 0, 0);
		Basebot.addBox(-6F, 0F, -6F, 12, 1, 12);
		Basebot.setRotationPoint(0F, 23F, 0F);
		Basebot.setTextureSize(64, 32);
		Basebot.mirror = true;
		setRotation(Basebot, 0F, 0F, 0F);
		Basemid = new ModelRenderer(this, 0, 13);
		Basemid.addBox(-5F, -3F, -5F, 10, 5, 10);
		Basemid.setRotationPoint(0F, 21F, 0F);
		Basemid.setTextureSize(64, 32);
		Basemid.mirror = true;
		setRotation(Basemid, 0F, 0F, 0F);
		Basetop = new ModelRenderer(this, 0, 0);
		Basetop.addBox(-6F, 0F, -6F, 12, 1, 12);
		Basetop.setRotationPoint(0F, 17F, 0F);
		Basetop.setTextureSize(64, 32);
		Basetop.mirror = true;
		setRotation(Basetop, 0F, 0F, 0F);
		Candle1 = new ModelRenderer(this, 48, 0);
		Candle1.addBox(0F, -2F, 0F, 1, 3, 1);
		Candle1.setRotationPoint(4F, 16F, 4F);
		Candle1.setTextureSize(64, 32);
		Candle1.mirror = true;
		setRotation(Candle1, 0F, 0F, 0F);
		Candle2 = new ModelRenderer(this, 48, 0);
		Candle2.addBox(0F, -2F, 0F, 1, 3, 1);
		Candle2.setRotationPoint(-5F, 16F, -5F);
		Candle2.setTextureSize(64, 32);
		Candle2.mirror = true;
		setRotation(Candle2, 0F, 0F, 0F);
		Candle3 = new ModelRenderer(this, 48, 0);
		Candle3.addBox(0F, -2F, 0F, 1, 3, 1);
		Candle3.setRotationPoint(4F, 16F, -5F);
		Candle3.setTextureSize(64, 32);
		Candle3.mirror = true;
		setRotation(Candle3, 0F, 0F, 0F);
		Candle4 = new ModelRenderer(this, 48, 0);
		Candle4.addBox(0F, -2F, 0F, 1, 3, 1);
		Candle4.setRotationPoint(-5F, 16F, 4F);
		Candle4.setTextureSize(64, 32);
		Candle4.mirror = true;
		setRotation(Candle4, 0F, 0F, 0F);
		Center = new ModelRenderer(this, 40, 13);
		Center.addBox(-2F, 0F, -2F, 6, 1, 6);
		Center.setRotationPoint(-1F, 16F, -1F);
		Center.setTextureSize(64, 32);
		Center.mirror = true;
		setRotation(Center, 0F, 0F, 0F);
		Bowl1 = new ModelRenderer(this, 54, 0);
		Bowl1.addBox(-2F, -1F, 0F, 4, 3, 1);
		Bowl1.setRotationPoint(0F, 14F, 1F);
		Bowl1.setTextureSize(64, 32);
		Bowl1.mirror = true;
		setRotation(Bowl1, 0F, 0F, 0F);
		Bowl2 = new ModelRenderer(this, 54, 4);
		Bowl2.addBox(0F, 0F, 0F, 1, 3, 2);
		Bowl2.setRotationPoint(1F, 13F, -1F);
		Bowl2.setTextureSize(64, 32);
		Bowl2.mirror = true;
		setRotation(Bowl2, 0F, 0F, 0F);
		Bowl3 = new ModelRenderer(this, 54, 0);
		Bowl3.addBox(0F, 0F, 0F, 4, 3, 1);
		Bowl3.setRotationPoint(-2F, 13F, -2F);
		Bowl3.setTextureSize(64, 32);
		Bowl3.mirror = true;
		setRotation(Bowl3, 0F, 0F, 0F);
		Bowl4 = new ModelRenderer(this, 54, 4);
		Bowl4.addBox(0F, 0F, 0F, 1, 3, 2);
		Bowl4.setRotationPoint(-2F, 13F, -1F);
		Bowl4.setTextureSize(64, 32);
		Bowl4.mirror = true;
		setRotation(Bowl4, 0F, 0F, 0F);
		Liquid = new ModelRenderer(this, 48, 9);
		Liquid.addBox(0F, 0F, 0F, 2, 2, 2);
		Liquid.setRotationPoint(-1F, 14F, -1F);
		Liquid.setTextureSize(64, 32);
		Liquid.mirror = true;
		setRotation(Liquid, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		Basebot.render(f5);
		Basemid.render(f5);
		Basetop.render(f5);
		Candle1.render(f5);
		Candle2.render(f5);
		Candle3.render(f5);
		Candle4.render(f5);
		Center.render(f5);
		Bowl1.render(f5);
		Bowl2.render(f5);
		Bowl3.render(f5);
		Bowl4.render(f5);
		if(ModelAltar.isActivated = true)
			Liquid.render(f5);

	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{

	}

}
