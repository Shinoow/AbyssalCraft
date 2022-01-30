/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelShadowMonster extends ModelBase
{

	public ModelRenderer Head;
	public ModelRenderer Body1;
	public ModelRenderer Body2;
	public ModelRenderer Body3;
	public ModelRenderer Lshoulder1;
	public ModelRenderer Rshoulder1;
	public ModelRenderer Lshoulder2;
	public ModelRenderer Rshoulder2;
	public ModelRenderer Larm1;
	public ModelRenderer Larm2;
	public ModelRenderer Rarm1;
	public ModelRenderer Rarm2;
	public ModelRenderer Back1;
	public ModelRenderer Back2;
	public ModelRenderer Back3;
	public ModelRenderer Back4;
	public ModelRenderer Back5;
	public ModelRenderer Back6;
	public ModelRenderer Back7;
	public ModelRenderer Back8;
	public ModelRenderer Back9;

	public ModelShadowMonster()
	{
		textureWidth = 64;
		textureHeight = 32;

		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-3.5F, -7F, -3.5F, 7, 7, 7);
		Head.setRotationPoint(0F, 0F, 0F);
		Head.setTextureSize(64, 32);
		Head.mirror = true;
		setRotation(Head, 0F, 0F, 0F);
		Body1 = new ModelRenderer(this, 20, 14);
		Body1.addBox(-1.5F, -1F, -1.5F, 3, 10, 3);
		Body1.setRotationPoint(0F, 0F, 0F);
		Body1.setTextureSize(64, 32);
		Body1.mirror = true;
		setRotation(Body1, 0.4461433F, 0F, 0F);
		Body2 = new ModelRenderer(this, 28, 0);
		Body2.addBox(0F, 0F, 0F, 3, 6, 3);
		Body2.setRotationPoint(-1.5F, 7F, 2F);
		Body2.setTextureSize(64, 32);
		Body2.mirror = true;
		setRotation(Body2, 0F, 0F, 0F);
		Body3 = new ModelRenderer(this, 28, 0);
		Body3.addBox(0F, 0F, 0F, 3, 6, 3);
		Body3.setRotationPoint(-1.5F, 11F, 2.5F);
		Body3.setTextureSize(64, 32);
		Body3.mirror = true;
		setRotation(Body3, -0.5948578F, 0F, 0F);
		Lshoulder1 = new ModelRenderer(this, 40, 0);
		Lshoulder1.addBox(0F, 0F, 0F, 3, 2, 2);
		Lshoulder1.setRotationPoint(1.5F, 0F, 0F);
		Lshoulder1.setTextureSize(64, 32);
		Lshoulder1.mirror = true;
		setRotation(Lshoulder1, 0F, 0F, 0F);
		Rshoulder1 = new ModelRenderer(this, 40, 0);
		Rshoulder1.addBox(0F, 0F, 0F, 3, 2, 2);
		Rshoulder1.setRotationPoint(-4.5F, 0F, 0F);
		Rshoulder1.setTextureSize(64, 32);
		Rshoulder1.mirror = true;
		setRotation(Rshoulder1, 0F, 0F, 0F);
		Lshoulder2 = new ModelRenderer(this, 40, 0);
		Lshoulder2.addBox(0F, 0F, 0F, 3, 2, 2);
		Lshoulder2.setRotationPoint(1.5F, 5F, 2F);
		Lshoulder2.setTextureSize(64, 32);
		Lshoulder2.mirror = true;
		setRotation(Lshoulder2, 0F, 0F, 0F);
		Rshoulder2 = new ModelRenderer(this, 40, 0);
		Rshoulder2.addBox(0F, 0F, 0F, 3, 2, 2);
		Rshoulder2.setRotationPoint(-4.5F, 5F, 2F);
		Rshoulder2.setTextureSize(64, 32);
		Rshoulder2.mirror = true;
		setRotation(Rshoulder2, 0F, 0F, 0F);
		Larm1 = new ModelRenderer(this, 0, 14);
		Larm1.addBox(0F, -1F, -7F, 2, 2, 8);
		Larm1.setRotationPoint(4.5F, 1F, 1F);
		Larm1.setTextureSize(64, 32);
		Larm1.mirror = true;
		setRotation(Larm1, 0F, 0F, 0F);
		Larm2 = new ModelRenderer(this, 0, 14);
		Larm2.addBox(0F, -1F, -7F, 2, 2, 8);
		Larm2.setRotationPoint(4.5F, 6F, 3F);
		Larm2.setTextureSize(64, 32);
		Larm2.mirror = true;
		setRotation(Larm2, 0F, 0F, 0F);
		Rarm1 = new ModelRenderer(this, 0, 14);
		Rarm1.addBox(-2F, -1F, -7F, 2, 2, 8);
		Rarm1.setRotationPoint(-4.5F, 1F, 1F);
		Rarm1.setTextureSize(64, 32);
		Rarm1.mirror = true;
		setRotation(Rarm1, 0F, 0F, 0F);
		Rarm2 = new ModelRenderer(this, 0, 14);
		Rarm2.addBox(-2F, -1F, -7F, 2, 2, 8);
		Rarm2.setRotationPoint(-4.5F, 6F, 3F);
		Rarm2.setTextureSize(64, 32);
		Rarm2.mirror = true;
		setRotation(Rarm2, 0F, 0F, 0F);
		Back1 = new ModelRenderer(this, 50, 0);
		Back1.addBox(0F, 0F, 0F, 1, 2, 1);
		Back1.setRotationPoint(-0.5F, 2F, 4F);
		Back1.setTextureSize(64, 32);
		Back1.mirror = true;
		setRotation(Back1, -1.041002F, 0F, 0F);
		Back2 = new ModelRenderer(this, 50, 0);
		Back2.addBox(0F, 0F, 0F, 1, 2, 1);
		Back2.setRotationPoint(-0.5F, 4F, 5F);
		Back2.setTextureSize(64, 32);
		Back2.mirror = true;
		setRotation(Back2, -1.041002F, 0F, 0F);
		Back3 = new ModelRenderer(this, 50, 0);
		Back3.addBox(0F, 0F, 0F, 1, 2, 1);
		Back3.setRotationPoint(-0.5F, 0F, 3F);
		Back3.setTextureSize(64, 32);
		Back3.mirror = true;
		setRotation(Back3, -1.041002F, 0F, 0F);
		Back4 = new ModelRenderer(this, 50, 0);
		Back4.addBox(0F, 0F, 0F, 1, 1, 2);
		Back4.setRotationPoint(-0.5F, 8F, 4.5F);
		Back4.setTextureSize(64, 32);
		Back4.mirror = true;
		setRotation(Back4, 0F, 0F, 0F);
		Back5 = new ModelRenderer(this, 50, 0);
		Back5.addBox(0F, 0F, 0F, 1, 1, 2);
		Back5.setRotationPoint(-0.5F, 10F, 4.5F);
		Back5.setTextureSize(64, 32);
		Back5.mirror = true;
		setRotation(Back5, 0F, 0F, 0F);
		Back6 = new ModelRenderer(this, 50, 0);
		Back6.addBox(0F, 0F, 0F, 1, 1, 2);
		Back6.setRotationPoint(-0.5F, 12F, 4.5F);
		Back6.setTextureSize(64, 32);
		Back6.mirror = true;
		setRotation(Back6, 0F, 0F, 0F);
		Back7 = new ModelRenderer(this, 50, 0);
		Back7.addBox(0F, 0F, 0F, 1, 2, 1);
		Back7.setRotationPoint(-0.5F, 14F, 3.5F);
		Back7.setTextureSize(64, 32);
		Back7.mirror = true;
		setRotation(Back7, 1.00382F, 0F, 0F);
		Back8 = new ModelRenderer(this, 50, 0);
		Back8.addBox(0F, 0F, 0F, 1, 2, 1);
		Back8.setRotationPoint(-0.5F, 15.5F, 2.5F);
		Back8.setTextureSize(64, 32);
		Back8.mirror = true;
		setRotation(Back8, 1.00382F, 0F, 0F);
		Back9 = new ModelRenderer(this, 50, 0);
		Back9.addBox(0F, 0F, 0F, 1, 2, 1);
		Back9.setRotationPoint(-0.5F, 17F, 1.5F);
		Back9.setTextureSize(64, 32);
		Back9.mirror = true;
		setRotation(Back9, 1.00382F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Head.render(f5);
		Body1.render(f5);
		Body2.render(f5);
		Body3.render(f5);
		Lshoulder1.render(f5);
		Rshoulder1.render(f5);
		Lshoulder2.render(f5);
		Rshoulder2.render(f5);
		Larm1.render(f5);
		Larm2.render(f5);
		Rarm1.render(f5);
		Rarm2.render(f5);
		Back1.render(f5);
		Back2.render(f5);
		Back3.render(f5);
		Back4.render(f5);
		Back5.render(f5);
		Back6.render(f5);
		Back7.render(f5);
		Back8.render(f5);
		Back9.render(f5);
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
		Head.rotateAngleY = par4 / (180F / (float)Math.PI);
		Head.rotateAngleX = par5 / (180F / (float)Math.PI);

		Rarm1.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
		Larm1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
		Rarm2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F * 0.1F;
		Larm2.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F * 0.1F;

		Rarm1.rotateAngleZ = 0.0F;
		Larm1.rotateAngleZ = 0.0F;
		Rarm2.rotateAngleZ = 0.0F;
		Larm2.rotateAngleZ = 0.0F;
	}

}
