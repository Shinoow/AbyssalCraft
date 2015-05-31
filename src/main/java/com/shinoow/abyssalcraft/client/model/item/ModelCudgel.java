/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.model.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCudgel extends ModelBase {

	ModelRenderer base1;
	ModelRenderer base2;
	ModelRenderer base3;
	ModelRenderer base4;
	ModelRenderer spike1;
	ModelRenderer spike2;
	ModelRenderer spike3;
	ModelRenderer spike4;
	ModelRenderer spike5;
	ModelRenderer spike6;
	ModelRenderer spike7;
	ModelRenderer spike8;
	ModelRenderer spike9;
	ModelRenderer spike10;
	ModelRenderer spike11;
	ModelRenderer thing1;
	ModelRenderer thing2;

	public ModelCudgel()
	{
		textureWidth = 128;
		textureHeight = 64;

		base1 = new ModelRenderer(this, 0, 0);
		base1.addBox(-1F, -1F, -7F, 2, 2, 10);
		base1.setRotationPoint(-6F, 10F, 0F);
		base1.setTextureSize(128, 64);
		base1.mirror = true;
		setRotation(base1, 0F, 0F, 0F);
		base2 = new ModelRenderer(this, 24, 2);
		base2.addBox(0F, 0F, 0F, 3, 3, 3);
		base2.setRotationPoint(-7.5F, 7.5F, -8F);
		base2.setTextureSize(128, 64);
		base2.mirror = true;
		setRotation(base2, -0.4089647F, 0F, 0F);
		base3 = new ModelRenderer(this, 24, 8);
		base3.addBox(0F, 0F, 0F, 4, 4, 6);
		base3.setRotationPoint(-6.5F, 5F, -13F);
		base3.setTextureSize(128, 64);
		base3.mirror = true;
		setRotation(base3, -0.3717861F, 0F, 0.2602503F);
		base4 = new ModelRenderer(this, 0, 12);
		base4.addBox(0F, 0F, 0F, 5, 5, 7);
		base4.setRotationPoint(-7.5F, 1F, -15F);
		base4.setTextureSize(128, 64);
		base4.mirror = true;
		setRotation(base4, -0.8551081F, 0.0743572F, 0F);
		spike1 = new ModelRenderer(this, 27, 0);
		spike1.addBox(0F, 0F, 0F, 3, 1, 1);
		spike1.setRotationPoint(-5F, 9F, -8F);
		spike1.setTextureSize(128, 64);
		spike1.mirror = true;
		setRotation(spike1, 0F, 0F, 0F);
		spike2 = new ModelRenderer(this, 27, 0);
		spike2.addBox(0F, 0F, 0F, 3, 1, 1);
		spike2.setRotationPoint(-10F, 9F, -8F);
		spike2.setTextureSize(128, 64);
		spike2.mirror = true;
		setRotation(spike2, 0F, 0F, 0F);
		spike3 = new ModelRenderer(this, 48, 0);
		spike3.addBox(0F, 0F, 0F, 1, 3, 1);
		spike3.setRotationPoint(-6.5F, 5F, -7F);
		spike3.setTextureSize(128, 64);
		spike3.mirror = true;
		setRotation(spike3, -0.3346075F, 0F, 0F);
		spike4 = new ModelRenderer(this, 48, 0);
		spike4.addBox(0F, 0F, 0F, 1, 3, 1);
		spike4.setRotationPoint(-6.5F, 10F, -9F);
		spike4.setTextureSize(128, 64);
		spike4.mirror = true;
		setRotation(spike4, 0F, 0F, 0F);
		spike5 = new ModelRenderer(this, 48, 0);
		spike5.addBox(0F, 0F, 0F, 1, 4, 1);
		spike5.setRotationPoint(-5F, 1F, -11F);
		spike5.setTextureSize(128, 64);
		spike5.mirror = true;
		setRotation(spike5, -0.8179294F, 0F, 0F);
		spike6 = new ModelRenderer(this, 26, 0);
		spike6.addBox(0F, 0F, 0F, 4, 1, 1);
		spike6.setRotationPoint(-10F, 5F, -13F);
		spike6.setTextureSize(128, 64);
		spike6.mirror = true;
		setRotation(spike6, -0.3717861F, 0.2602503F, 0F);
		spike7 = new ModelRenderer(this, 26, 0);
		spike7.addBox(0F, 0F, 0F, 4, 1, 1);
		spike7.setRotationPoint(-10F, 1F, -14F);
		spike7.setTextureSize(128, 64);
		spike7.mirror = true;
		setRotation(spike7, -0.4461433F, 0.4089647F, 0.5576792F);
		spike8 = new ModelRenderer(this, 26, 0);
		spike8.addBox(0F, 0F, 0F, 4, 1, 1);
		spike8.setRotationPoint(-3F, 7F, -15F);
		spike8.setTextureSize(128, 64);
		spike8.mirror = true;
		setRotation(spike8, -0.0371786F, -0.7807508F, -0.4461433F);
		spike9 = new ModelRenderer(this, 48, 0);
		spike9.addBox(0F, 0F, 0F, 1, 5, 1);
		spike9.setRotationPoint(-6F, 6F, -15F);
		spike9.setTextureSize(128, 64);
		spike9.mirror = true;
		setRotation(spike9, -0.9294653F, -0.6320364F, 0.8551081F);
		spike10 = new ModelRenderer(this, 36, 0);
		spike10.addBox(0F, 0F, 0F, 1, 1, 5);
		spike10.setRotationPoint(-4F, 1F, -20F);
		spike10.setTextureSize(128, 64);
		spike10.mirror = true;
		setRotation(spike10, -0.3717861F, 0F, 0.7435722F);
		spike11 = new ModelRenderer(this, 24, 0);
		spike11.addBox(0F, 0F, 0F, 5, 1, 1);
		spike11.setRotationPoint(-4F, 5F, -16F);
		spike11.setTextureSize(128, 64);
		spike11.mirror = true;
		setRotation(spike11, 0F, 0F, -0.5205006F);
		thing1 = new ModelRenderer(this, 24, 18);
		thing1.addBox(0F, 0F, 0F, 4, 4, 1);
		thing1.setRotationPoint(-8F, 8F, -6F);
		thing1.setTextureSize(128, 64);
		thing1.mirror = true;
		setRotation(thing1, 0F, 0F, 0F);
		thing2 = new ModelRenderer(this, 34, 18);
		thing2.addBox(0F, 0F, 0F, 6, 6, 1);
		thing2.setRotationPoint(-7.5F, 5F, -10F);
		thing2.setTextureSize(128, 64);
		thing2.mirror = true;
		setRotation(thing2, -0.2602503F, 0F, 0.260246F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		base1.render(f5);
		base2.render(f5);
		base3.render(f5);
		base4.render(f5);
		spike1.render(f5);
		spike2.render(f5);
		spike3.render(f5);
		spike4.render(f5);
		spike5.render(f5);
		spike6.render(f5);
		spike7.render(f5);
		spike8.render(f5);
		spike9.render(f5);
		spike10.render(f5);
		spike11.render(f5);
		thing1.render(f5);
		thing2.render(f5);
	}

	public void render(float f){

		base1.render(f);
		base2.render(f);
		base3.render(f);
		base4.render(f);
		spike1.render(f);
		spike2.render(f);
		spike3.render(f);
		spike4.render(f);
		spike5.render(f);
		spike6.render(f);
		spike7.render(f);
		spike8.render(f);
		spike9.render(f);
		spike10.render(f);
		spike11.render(f);
		thing1.render(f);
		thing2.render(f);
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
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}
}
