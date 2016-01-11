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
package com.shinoow.abyssalcraft.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAzathothStatue extends ModelBase
{

	ModelRenderer base;
	ModelRenderer core;
	ModelRenderer limb1;
	ModelRenderer limb2;
	ModelRenderer limb3;
	ModelRenderer limb4;
	ModelRenderer limb5;
	ModelRenderer limb6;
	ModelRenderer limb7;
	ModelRenderer limb8;

	public ModelAzathothStatue()
	{
		textureWidth = 64;
		textureHeight = 32;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 8, 6, 8);
		base.setRotationPoint(-4F, 18F, -4F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		core = new ModelRenderer(this, 32, 0);
		core.addBox(0F, 0F, 0F, 4, 4, 4);
		core.setRotationPoint(-2F, 12F, -1F);
		core.setTextureSize(64, 32);
		core.mirror = true;
		setRotation(core, -0.2617994F, 0F, 0F);
		limb1 = new ModelRenderer(this, 0, 14);
		limb1.addBox(-1F, 0F, -6F, 2, 2, 6);
		limb1.setRotationPoint(0F, 16F, 2.5F);
		limb1.setTextureSize(64, 32);
		limb1.mirror = true;
		setRotation(limb1, 0F, 0F, 0F);
		limb2 = new ModelRenderer(this, 0, 14);
		limb2.addBox(-1F, -2F, -6F, 2, 2, 6);
		limb2.setRotationPoint(0F, 14F, 3F);
		limb2.setTextureSize(64, 32);
		limb2.mirror = true;
		setRotation(limb2, -0.5235988F, 0F, 0F);
		limb3 = new ModelRenderer(this, 0, 14);
		limb3.addBox(0F, 0F, -6F, 2, 2, 6);
		limb3.setRotationPoint(1F, 14F, 3F);
		limb3.setTextureSize(64, 32);
		limb3.mirror = true;
		setRotation(limb3, -0.2617994F, -0.2617994F, 0F);
		limb4 = new ModelRenderer(this, 0, 14);
		limb4.addBox(-2F, 0F, -6F, 2, 2, 6);
		limb4.setRotationPoint(-1F, 14F, 3F);
		limb4.setTextureSize(64, 32);
		limb4.mirror = true;
		setRotation(limb4, -0.2617994F, 0.2617994F, 0F);
		limb5 = new ModelRenderer(this, 0, 14);
		limb5.addBox(0F, -2F, -6F, 2, 2, 6);
		limb5.setRotationPoint(1F, 14F, 3F);
		limb5.setTextureSize(64, 32);
		limb5.mirror = true;
		setRotation(limb5, -0.5235988F, -0.2617994F, 0F);
		limb6 = new ModelRenderer(this, 0, 14);
		limb6.addBox(-2F, -2F, -6F, 2, 2, 6);
		limb6.setRotationPoint(-1F, 14F, 3F);
		limb6.setTextureSize(64, 32);
		limb6.mirror = true;
		setRotation(limb6, -0.5235988F, 0.2617994F, 0F);
		limb7 = new ModelRenderer(this, 0, 14);
		limb7.addBox(0F, 0F, -6F, 2, 2, 6);
		limb7.setRotationPoint(1F, 16F, 2.5F);
		limb7.setTextureSize(64, 32);
		limb7.mirror = true;
		setRotation(limb7, 0F, -0.2617994F, 0F);
		limb8 = new ModelRenderer(this, 0, 14);
		limb8.addBox(-2F, 0F, -6F, 2, 2, 6);
		limb8.setRotationPoint(-1F, 16F, 2.5F);
		limb8.setTextureSize(64, 32);
		limb8.mirror = true;
		setRotation(limb8, 0F, 0.2617994F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		base.render(f5);
		core.render(f5);
		limb1.render(f5);
		limb2.render(f5);
		limb3.render(f5);
		limb4.render(f5);
		limb5.render(f5);
		limb6.render(f5);
		limb7.render(f5);
		limb8.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
