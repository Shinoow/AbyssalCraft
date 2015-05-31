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
package com.shinoow.abyssalcraft.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelODB extends ModelBase{

	ModelRenderer catalyst;
	ModelRenderer core;
	ModelRenderer righttube2;
	ModelRenderer platform;
	ModelRenderer lefttube2;
	ModelRenderer machine;
	ModelRenderer righttube1;
	ModelRenderer stand;
	ModelRenderer lefttube1;
	ModelRenderer liquid1;
	ModelRenderer liquid2;

	public ModelODB()
	{
		textureWidth = 128;
		textureHeight = 64;

		catalyst = new ModelRenderer(this, 0, 23);
		catalyst.addBox(0F, 0F, 0F, 2, 6, 6);
		catalyst.setRotationPoint(-5F, 13F, -3F);
		catalyst.setTextureSize(128, 64);
		catalyst.mirror = true;
		setRotation(catalyst, 0F, 0F, 0F);
		core = new ModelRenderer(this, 32, 15);
		core.addBox(0F, -2F, -2F, 16, 8, 8);
		core.setRotationPoint(1F, 17F, -3F);
		core.setTextureSize(128, 64);
		core.mirror = true;
		setRotation(core, 0.7853982F, 0F, 0F);
		righttube2 = new ModelRenderer(this, 26, 19);
		righttube2.addBox(0F, 0F, 0F, 2, 1, 1);
		righttube2.setRotationPoint(-5F, 15F, -5F);
		righttube2.setTextureSize(128, 64);
		righttube2.mirror = true;
		setRotation(righttube2, 0F, 0F, 0F);
		platform = new ModelRenderer(this, 0, 0);
		platform.addBox(0F, 0F, 0F, 26, 1, 14);
		platform.setRotationPoint(-7F, 23F, -7F);
		platform.setTextureSize(128, 64);
		platform.mirror = true;
		setRotation(platform, 0F, 0F, 0F);
		lefttube2 = new ModelRenderer(this, 26, 19);
		lefttube2.addBox(0F, 0F, 0F, 2, 1, 1);
		lefttube2.setRotationPoint(-5F, 15F, 4F);
		lefttube2.setTextureSize(128, 64);
		lefttube2.mirror = true;
		setRotation(lefttube2, 0F, 0F, 0F);
		machine = new ModelRenderer(this, 80, 0);
		machine.addBox(0F, 0F, 0F, 4, 12, 12);
		machine.setRotationPoint(-3F, 11F, -6F);
		machine.setTextureSize(128, 64);
		machine.mirror = true;
		setRotation(machine, 0F, 0F, 0F);
		righttube1 = new ModelRenderer(this, 26, 16);
		righttube1.addBox(0F, 0F, 0F, 1, 2, 1);
		righttube1.setRotationPoint(-5F, 16F, -5F);
		righttube1.setTextureSize(128, 64);
		righttube1.mirror = true;
		setRotation(righttube1, 0F, 0F, 0F);
		stand = new ModelRenderer(this, 0, 0);
		stand.addBox(0F, 0F, 0F, 1, 8, 6);
		stand.setRotationPoint(17F, 15F, -3F);
		stand.setTextureSize(128, 64);
		stand.mirror = true;
		setRotation(stand, 0F, 0F, 0F);
		lefttube1 = new ModelRenderer(this, 26, 16);
		lefttube1.addBox(0F, 0F, 0F, 1, 2, 1);
		lefttube1.setRotationPoint(-5F, 16F, 4F);
		lefttube1.setTextureSize(128, 64);
		lefttube1.mirror = true;
		setRotation(lefttube1, 0F, 0F, 0F);
		liquid1 = new ModelRenderer(this, 0, 15);
		liquid1.addBox(0F, 0F, 0F, 3, 5, 3);
		liquid1.setRotationPoint(-6F, 18F, 3F);
		liquid1.setTextureSize(128, 64);
		liquid1.mirror = true;
		setRotation(liquid1, 0F, 0F, 0F);
		liquid2 = new ModelRenderer(this, 12, 15);
		liquid2.addBox(0F, 0F, 0F, 3, 5, 3);
		liquid2.setRotationPoint(-6F, 18F, -6F);
		liquid2.setTextureSize(128, 64);
		liquid2.mirror = true;
		setRotation(liquid2, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		catalyst.render(f5);
		core.render(f5);
		righttube2.render(f5);
		platform.render(f5);
		lefttube2.render(f5);
		machine.render(f5);
		righttube1.render(f5);
		stand.render(f5);
		lefttube1.render(f5);
		liquid1.render(f5);
		liquid2.render(f5);
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
