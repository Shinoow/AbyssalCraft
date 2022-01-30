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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelDG extends ModelBase
{
	public ModelRenderer Head;
	public ModelRenderer jaw;
	public ModelRenderer tooth1;
	public ModelRenderer tooth2;
	public ModelRenderer tooth3;
	public ModelRenderer tooth4;
	public ModelRenderer tooth5;
	public ModelRenderer Spine1;
	public ModelRenderer Spine2;
	public ModelRenderer lrib1;
	public ModelRenderer lrib2;
	public ModelRenderer lrib3;
	public ModelRenderer rrib1;
	public ModelRenderer rrib2;
	public ModelRenderer rrib3;
	public ModelRenderer pelvis;
	public ModelRenderer Spine3;
	public ModelRenderer larm1;
	public ModelRenderer larm2;
	public ModelRenderer clawl1;
	public ModelRenderer clawl2;
	public ModelRenderer clawl3;
	public ModelRenderer clawl4;
	public ModelRenderer rarm1;
	public ModelRenderer rarm2;
	public ModelRenderer clawr1;
	public ModelRenderer clawr2;
	public ModelRenderer clawr3;
	public ModelRenderer clawr4;
	public ModelRenderer lleg;
	public ModelRenderer rleg;
	public ModelRenderer back;
	public ModelRenderer lside;
	public ModelRenderer rside;

	public ModelDG()
	{
		this(0.0F);
	}

	public ModelDG(float f)
	{
		textureWidth = 128;
		textureHeight = 64;

		Head = new ModelRenderer(this, 0, 0);
		Head.setTextureSize(128, 64);
		Head.addBox(-4.5F, -9.5F, -4.5F, 9, 9, 9, f);
		Head.setRotationPoint(0F, -17F, 1F);
		jaw = new ModelRenderer(this, 36, 0);
		jaw.setTextureSize(128, 64);
		jaw.addBox(-4.5F, -0.5F, -4.5F, 9, 1, 9, f);
		jaw.setRotationPoint(0F, 1F, 0F);
		setRotation(jaw, 0.2365561F, 0, 0);
		Head.addChild(jaw);
		tooth1 = new ModelRenderer(this, 48, 11);
		tooth1.setTextureSize(128, 64);
		tooth1.addBox(-0.5F, -1F, -0.5F, 1, 2, 1, f);
		tooth1.setRotationPoint(-4F, -1.5F, -4F);
		jaw.addChild(tooth1);
		tooth2 = new ModelRenderer(this, 48, 11);
		tooth2.setTextureSize(128, 64);
		tooth2.addBox(-0.5F, -1F, -0.5F, 1, 2, 1, f);
		tooth2.setRotationPoint(-2F, -1.5F, -4F);
		jaw.addChild(tooth2);
		tooth3 = new ModelRenderer(this, 48, 11);
		tooth3.setTextureSize(128, 64);
		tooth3.addBox(-0.5F, -1F, -0.5F, 1, 2, 1, f);
		tooth3.setRotationPoint(0F, -1.5F, -4F);
		jaw.addChild(tooth3);
		tooth4 = new ModelRenderer(this, 48, 11);
		tooth4.setTextureSize(128, 64);
		tooth4.addBox(-0.5F, -1F, -0.5F, 1, 2, 1, f);
		tooth4.setRotationPoint(2F, -1.5F, -4F);
		jaw.addChild(tooth4);
		tooth5 = new ModelRenderer(this, 48, 11);
		tooth5.setTextureSize(128, 64);
		tooth5.addBox(-0.5F, -1F, -0.5F, 1, 2, 1, f);
		tooth5.setRotationPoint(4F, -1.5F, -4F);
		jaw.addChild(tooth5);
		Spine1 = new ModelRenderer(this, 0, 42);
		Spine1.setTextureSize(128, 64);
		Spine1.addBox(-2.5F, -4F, -3F, 5, 8, 6, f);
		Spine1.setRotationPoint(0F, -14F, 2F);
		setRotation(Spine1, 0.2590069F, 0, 0);
		Spine2 = new ModelRenderer(this, 0, 42);
		Spine2.setTextureSize(128, 64);
		Spine2.addBox(-2.5F, -8F, -3F, 5, 16, 6, f);
		Spine2.setRotationPoint(0F, -2.854666F, 2.884038F);
		lrib1 = new ModelRenderer(this, 101, 47);
		lrib1.setTextureSize(128, 64);
		lrib1.addBox(-1.5F, -1F, -1F, 3, 2, 2, f);
		lrib1.setRotationPoint(4F, -6.977365F, 2.8262F);
		setRotation(lrib1, -0.07023507F, 0, 0);
		lrib2 = new ModelRenderer(this, 101, 47);
		lrib2.setTextureSize(128, 64);
		lrib2.addBox(-1.5F, -1F, -1F, 3, 2, 2, f);
		lrib2.setRotationPoint(4F, -3.821312F, 2.627918F);
		setRotation(lrib2, -0.07023507F, 0, 0);
		lrib3 = new ModelRenderer(this, 101, 47);
		lrib3.setTextureSize(128, 64);
		lrib3.addBox(-1.5F, -1F, -1F, 3, 2, 2, f);
		lrib3.setRotationPoint(4F, -0.6646652F, 2.434038F);
		setRotation(lrib3, -0.07023507F, 0, 0);
		rrib1 = new ModelRenderer(this, 101, 47);
		rrib1.setTextureSize(128, 64);
		rrib1.addBox(-1.5F, -1F, -1F, 3, 2, 2, f);
		rrib1.setRotationPoint(-4F, -6.977365F, 2.826201F);
		setRotation(rrib1, -0.07023507F, 0, 0);
		rrib2 = new ModelRenderer(this, 101, 47);
		rrib2.setTextureSize(128, 64);
		rrib2.addBox(-1.5F, -1F, -1F, 3, 2, 2, f);
		rrib2.setRotationPoint(-4F, -3.82131F, 2.627918F);
		setRotation(rrib2, -0.07023507F, 0, 0);
		rrib3 = new ModelRenderer(this, 101, 47);
		rrib3.setTextureSize(128, 64);
		rrib3.addBox(-1.5F, -1F, -1F, 3, 2, 2, f);
		rrib3.setRotationPoint(-4F, -0.6652546F, 2.429635F);
		setRotation(rrib3, -0.07023507F, 0, 0);
		pelvis = new ModelRenderer(this, 80, 14);
		pelvis.setTextureSize(128, 64);
		pelvis.addBox(-6F, -1F, -3F, 12, 2, 6, f);
		pelvis.setRotationPoint(0F, 5.390734F, 2.999714F);
		setRotation(pelvis, -1.637653E-07F, 0, 0);
		Spine3 = new ModelRenderer(this, 76, 28);
		Spine3.setTextureSize(128, 64);
		Spine3.addBox(-9F, -2F, -2F, 18, 4, 4, f);
		Spine3.setRotationPoint(0F, -11.10007F, 2.768362F);
		setRotation(Spine3, 0.1290269F, 0, 0);
		larm1 = new ModelRenderer(this, 46, 48);
		larm1.setTextureSize(128, 64);
		larm1.addBox(0F, -2F, -2F, 4, 12, 4, f);
		larm1.setRotationPoint(9F, -11F, 2F);
		setRotation(larm1, -0.5595525F, 0, 0);
		larm2 = new ModelRenderer(this, 64, 52);
		larm2.setTextureSize(128, 64);
		larm2.addBox(-2F, 1F, 3F, 4, 8, 4, f);
		larm2.setRotationPoint(2F,4F,-2F);
		setRotation(larm2, -1.44967F - larm1.rotateAngleX, 0, 0);
		clawl1 = new ModelRenderer(this, 110, 57);
		clawl1.setTextureSize(128, 64);
		clawl1.addBox(-0.5F, -0.5F, -1F, 1, 5, 1, f);
		clawl1.setRotationPoint(-1F,8F,3F);
		clawl2 = new ModelRenderer(this, 110, 57);
		clawl2.setTextureSize(128, 64);
		clawl2.addBox(-0.5F, -0.5F, -1F, 1, 5, 1, f);
		clawl2.setRotationPoint(1F,8F,3F);
		clawl3 = new ModelRenderer(this, 110, 57);
		clawl3.setTextureSize(128, 64);
		clawl3.addBox(-1F, -0.5F, -1F, 1, 5, 1, f);
		clawl3.setRotationPoint(-2F,8F,5F);
		clawl4 = new ModelRenderer(this, 110, 57);
		clawl4.setTextureSize(128, 64);
		clawl4.addBox(0F, -0.5F, -1F, 1, 5, 1, f);
		clawl4.setRotationPoint(2F,8F,5F);
		rarm1 = new ModelRenderer(this, 46, 48);
		rarm1.setTextureSize(128, 64);
		rarm1.addBox(-4F, -2F, -2F, 4, 12, 4, f);
		rarm1.setRotationPoint(-9F, -11F, 2F);
		setRotation(rarm1, -0.559472F, 0, 0);
		rarm2 = new ModelRenderer(this, 64, 52);
		rarm2.setTextureSize(128, 64);
		rarm2.addBox(-2F, 1F, 3F, 4, 8, 4, f);
		rarm2.setRotationPoint(-2F,4F,-2F);
		setRotation(rarm2, -1.449542F - rarm1.rotateAngleX, 0, 0);
		clawr1 = new ModelRenderer(this, 110, 57);
		clawr1.setTextureSize(128, 64);
		clawr1.addBox(-0.5F, -0.5F, -1F, 1, 5, 1, f);
		clawr1.setRotationPoint(-1F,8F,3F);
		clawr2 = new ModelRenderer(this, 110, 57);
		clawr2.setTextureSize(128, 64);
		clawr2.addBox(-0.5F, -0.5F, -1F, 1, 5, 1, f);
		clawr2.setRotationPoint(1F,8F,3F);
		clawr3 = new ModelRenderer(this, 110, 57);
		clawr3.setTextureSize(128, 64);
		clawr3.addBox(0F, -0.5F, -1F, 1, 5, 1, f);
		clawr3.setRotationPoint(-3F,8F,5F);
		clawr4 = new ModelRenderer(this, 110, 57);
		clawr4.setTextureSize(128, 64);
		clawr4.addBox(-1F, -0.5F, -1F, 1, 5, 1, f);
		clawr4.setRotationPoint(3F,8F,5F);
		lleg = new ModelRenderer(this, 22, 40);
		lleg.setTextureSize(128, 64);
		lleg.addBox(-3F, -1F, -3F, 6, 18, 6, f);
		lleg.setRotationPoint(3F, 7F, 3F);
		rleg = new ModelRenderer(this, 22, 40);
		rleg.setTextureSize(128, 64);
		rleg.addBox(-3F, -1F, -3F, 6, 18, 6, f);
		rleg.setRotationPoint(-3F, 7F, 3F);
		back = new ModelRenderer(this, 0, 18);
		back.setTextureSize(128, 64);
		back.addBox(-6F, -7.5F, 0F, 12, 15, 0, f);
		back.setRotationPoint(0F, -3F, 6F);
		lside = new ModelRenderer(this, 30, 12);
		lside.setTextureSize(128, 64);
		lside.addBox(0F, -7.5F, -3F, 0, 15, 6, f);
		lside.setRotationPoint(6F, -3F, 3F);
		rside = new ModelRenderer(this, 42, 12);
		rside.setTextureSize(128, 64);
		rside.addBox(0F, -7.5F, -3F, 0, 15, 6, f);
		rside.setRotationPoint(-6F, -3F, 3F);

		larm1.addChild(larm2);
		rarm1.addChild(rarm2);

		larm2.addChild(clawl1);
		larm2.addChild(clawl2);
		larm2.addChild(clawl3);
		larm2.addChild(clawl4);

		rarm2.addChild(clawr1);
		rarm2.addChild(clawr2);
		rarm2.addChild(clawr3);
		rarm2.addChild(clawr4);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

		if (isChild)
		{
			float f6 = 2.0F;
			GlStateManager.pushMatrix();
			GlStateManager.scale(1.5F / f6, 1.5F / f6, 1.5F / f6);
			GlStateManager.translate(0.0F, 21.0F * par7, 0.0F);
			Head.render(par7);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GlStateManager.translate(0.0F, 24.0F * par7, 0.0F);
			Spine1.render(par7);
			Spine2.render(par7);
			lrib1.render(par7);
			lrib2.render(par7);
			lrib3.render(par7);
			rrib1.render(par7);
			rrib2.render(par7);
			rrib3.render(par7);
			pelvis.render(par7);
			Spine3.render(par7);
			larm1.render(par7);
			rarm1.render(par7);
			lleg.render(par7);
			rleg.render(par7);
			back.render(par7);
			lside.render(par7);
			rside.render(par7);
			GlStateManager.popMatrix();
		} else{
			Head.render(par7);
			Spine1.render(par7);
			Spine2.render(par7);
			lrib1.render(par7);
			lrib2.render(par7);
			lrib3.render(par7);
			rrib1.render(par7);
			rrib2.render(par7);
			rrib3.render(par7);
			pelvis.render(par7);
			Spine3.render(par7);
			larm1.render(par7);
			rarm1.render(par7);
			lleg.render(par7);
			rleg.render(par7);
			back.render(par7);
			lside.render(par7);
			rside.render(par7);
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {

		Head.rotateAngleY = par4 / (180F / (float)Math.PI);
		Head.rotateAngleX = par5 / (180F / (float)Math.PI);

		rleg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		rleg.rotateAngleY = 0.0F;

		lleg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		lleg.rotateAngleY = 0.0F;

		if (isRiding){

			rarm1.rotateAngleX += -((float)Math.PI / 5F);
			larm1.rotateAngleX += -((float)Math.PI / 5F);

			rleg.rotateAngleX = -((float)Math.PI * 2F / 5F);
			lleg.rotateAngleX = -((float)Math.PI * 2F / 5F);

			rleg.rotateAngleY = (float)Math.PI / 10F;
			lleg.rotateAngleY = -((float)Math.PI / 10F);
		}

		float f6 = MathHelper.sin(swingProgress * (float)Math.PI);
		float f7 = MathHelper.sin((1.0F - (1.0F - swingProgress) * (1.0F - swingProgress)) * (float)Math.PI);
		rarm1.rotateAngleZ = 0.0F;
		larm1.rotateAngleZ = 0.0F;
		rarm1.rotateAngleY = -(0.1F - f6 * 0.6F);
		larm1.rotateAngleY = 0.1F - f6 * 0.6F;
		rarm1.rotateAngleX = -((float)Math.PI / 3.7F);
		larm1.rotateAngleX = -((float)Math.PI / 3.7F);
		rarm1.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		larm1.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		rarm1.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
		larm1.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
		rarm1.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
		larm1.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
	}

	public void setInvisible(boolean invisible)
	{
		Head.showModel = invisible;
		Spine1.showModel = invisible;
		Spine2.showModel = invisible;
		lrib1.showModel = invisible;
		lrib2.showModel = invisible;
		lrib3.showModel = invisible;
		rrib1.showModel = invisible;
		rrib2.showModel = invisible;
		rrib3.showModel = invisible;
		pelvis.showModel = invisible;
		Spine3.showModel = invisible;
		larm1.showModel = invisible;
		rarm1.showModel = invisible;
		lleg.showModel = invisible;
		rleg.showModel = invisible;
		back.showModel = invisible;
		lside.showModel = invisible;
		rside.showModel = invisible;
	}

	public void postRenderArm(float scale, EnumHandSide side){
		getArmForSide(side).postRender(scale);
	}

	protected ModelRenderer getArmForSide(EnumHandSide side)
	{
		return side == EnumHandSide.LEFT ? larm1 : rarm1;
	}
}
