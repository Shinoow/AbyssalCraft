/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelDG extends ModelBase
{
	ModelRenderer Head;
	ModelRenderer jaw;
	ModelRenderer tooth1;
	ModelRenderer tooth2;
	ModelRenderer tooth3;
	ModelRenderer tooth4;
	ModelRenderer tooth5;
	ModelRenderer Spine1;
	ModelRenderer Spine2;
	ModelRenderer lrib1;
	ModelRenderer lrib2;
	ModelRenderer lrib3;
	ModelRenderer rrib1;
	ModelRenderer rrib2;
	ModelRenderer rrib3;
	ModelRenderer pelvis;
	ModelRenderer Spine3;
	ModelRenderer larm1;
	ModelRenderer larm2;
	ModelRenderer clawl1;
	ModelRenderer clawl2;
	ModelRenderer clawl3;
	ModelRenderer clawl4;
	ModelRenderer rarm1;
	ModelRenderer rarm2;
	ModelRenderer clawr1;
	ModelRenderer clawr2;
	ModelRenderer clawr3;
	ModelRenderer clawr4;
	ModelRenderer lleg;
	ModelRenderer rleg;
	ModelRenderer back;
	ModelRenderer lside;
	ModelRenderer rside;

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
		Head.addBox(-4.5F, -9.5F, -4.5F, 9, 9, 9);
		Head.setRotationPoint(0F, -17F, 1F);
		jaw = new ModelRenderer(this, 36, 0);
		jaw.setTextureSize(128, 64);
		jaw.addBox(-4.5F, -0.5F, -4.5F, 9, 1, 9);
		jaw.setRotationPoint(0F, 1F, 0F);
		Head.addChild(jaw);
		tooth1 = new ModelRenderer(this, 48, 11);
		tooth1.setTextureSize(128, 64);
		tooth1.addBox(-0.5F, -1F, -0.5F, 1, 2, 1);
		tooth1.setRotationPoint(-4F, -1.5F, -4F);
		jaw.addChild(tooth1);
		tooth2 = new ModelRenderer(this, 48, 11);
		tooth2.setTextureSize(128, 64);
		tooth2.addBox(-0.5F, -1F, -0.5F, 1, 2, 1);
		tooth2.setRotationPoint(-2F, -1.5F, -4F);
		jaw.addChild(tooth2);
		tooth3 = new ModelRenderer(this, 48, 11);
		tooth3.setTextureSize(128, 64);
		tooth3.addBox(-0.5F, -1F, -0.5F, 1, 2, 1);
		tooth3.setRotationPoint(0F, -1.5F, -4F);
		jaw.addChild(tooth3);
		tooth4 = new ModelRenderer(this, 48, 11);
		tooth4.setTextureSize(128, 64);
		tooth4.addBox(-0.5F, -1F, -0.5F, 1, 2, 1);
		tooth4.setRotationPoint(2F, -1.5F, -4F);
		jaw.addChild(tooth4);
		tooth5 = new ModelRenderer(this, 48, 11);
		tooth5.setTextureSize(128, 64);
		tooth5.addBox(-0.5F, -1F, -0.5F, 1, 2, 1);
		tooth5.setRotationPoint(4F, -1.5F, -4F);
		jaw.addChild(tooth5);
		Spine1 = new ModelRenderer(this, 0, 42);
		Spine1.setTextureSize(128, 64);
		Spine1.addBox(-2.5F, -4F, -3F, 5, 8, 6);
		Spine1.setRotationPoint(0F, -14F, 2F);
		Spine2 = new ModelRenderer(this, 0, 42);
		Spine2.setTextureSize(128, 64);
		Spine2.addBox(-2.5F, -8F, -3F, 5, 16, 6);
		Spine2.setRotationPoint(0F, -2.854666F, 2.884038F);
		lrib1 = new ModelRenderer(this, 101, 47);
		lrib1.setTextureSize(128, 64);
		lrib1.addBox(-1.5F, -1F, -1F, 3, 2, 2);
		lrib1.setRotationPoint(4F, -6.977365F, 2.8262F);
		lrib2 = new ModelRenderer(this, 101, 47);
		lrib2.setTextureSize(128, 64);
		lrib2.addBox(-1.5F, -1F, -1F, 3, 2, 2);
		lrib2.setRotationPoint(4F, -3.821312F, 2.627918F);
		lrib3 = new ModelRenderer(this, 101, 47);
		lrib3.setTextureSize(128, 64);
		lrib3.addBox(-1.5F, -1F, -1F, 3, 2, 2);
		lrib3.setRotationPoint(4F, -0.6646652F, 2.434038F);
		rrib1 = new ModelRenderer(this, 101, 47);
		rrib1.setTextureSize(128, 64);
		rrib1.addBox(-1.5F, -1F, -1F, 3, 2, 2);
		rrib1.setRotationPoint(-4F, -6.977365F, 2.826201F);
		rrib2 = new ModelRenderer(this, 101, 47);
		rrib2.setTextureSize(128, 64);
		rrib2.addBox(-1.5F, -1F, -1F, 3, 2, 2);
		rrib2.setRotationPoint(-4F, -3.82131F, 2.627918F);
		rrib3 = new ModelRenderer(this, 101, 47);
		rrib3.setTextureSize(128, 64);
		rrib3.addBox(-1.5F, -1F, -1F, 3, 2, 2);
		rrib3.setRotationPoint(-4F, -0.6652546F, 2.429635F);
		pelvis = new ModelRenderer(this, 80, 14);
		pelvis.setTextureSize(128, 64);
		pelvis.addBox(-6F, -1F, -3F, 12, 2, 6);
		pelvis.setRotationPoint(0F, 5.390734F, 2.999714F);
		Spine3 = new ModelRenderer(this, 76, 28);
		Spine3.setTextureSize(128, 64);
		Spine3.addBox(-9F, -2F, -2F, 18, 4, 4);
		Spine3.setRotationPoint(0F, -11.10007F, 2.768362F);
		larm1 = new ModelRenderer(this, 46, 48);
		larm1.setTextureSize(128, 64);
		larm1.addBox(0F, -2F, -2F, 4, 12, 4);
		larm1.setRotationPoint(9F, -11F, 2F);
		larm2 = new ModelRenderer(this, 64, 52);
		larm2.setTextureSize(128, 64);
		larm2.addBox(-2F, 0F, -2F, 4, 8, 4);
		larm2.setRotationPoint(11F, -3.689251F, -1.398964F);
		clawl1 = new ModelRenderer(this, 110, 57);
		clawl1.setTextureSize(128, 64);
		clawl1.addBox(-0.5F, -0.5F, -1F, 1, 5, 1);
		clawl1.setRotationPoint(10F, -4.828789F, -8.589336F);
		clawl2 = new ModelRenderer(this, 110, 57);
		clawl2.setTextureSize(128, 64);
		clawl2.addBox(-0.5F, -0.5F, -1F, 1, 5, 1);
		clawl2.setRotationPoint(12F, -4.828789F, -8.589336F);
		clawl3 = new ModelRenderer(this, 110, 57);
		clawl3.setTextureSize(128, 64);
		clawl3.addBox(-1F, -0.5F, -1F, 1, 5, 1);
		clawl3.setRotationPoint(9F, -2.843443F, -8.347676F);
		clawl4 = new ModelRenderer(this, 110, 57);
		clawl4.setTextureSize(128, 64);
		clawl4.addBox(0F, -0.5F, -1F, 1, 5, 1);
		clawl4.setRotationPoint(13F, -2.843443F, -8.347676F);
		rarm1 = new ModelRenderer(this, 46, 48);
		rarm1.setTextureSize(128, 64);
		rarm1.addBox(-4F, -2F, -2F, 4, 12, 4);
		rarm1.setRotationPoint(-9F, -11F, 2F);
		rarm2 = new ModelRenderer(this, 64, 52);
		rarm2.setTextureSize(128, 64);
		rarm2.addBox(-2F, 0F, -2F, 4, 8, 4);
		rarm2.setRotationPoint(-11F, -3.688976F, -1.398375F);
		clawr1 = new ModelRenderer(this, 110, 57);
		clawr1.setTextureSize(128, 64);
		clawr1.addBox(-0.5F, -0.5F, -1F, 1, 5, 1);
		clawr1.setRotationPoint(-10F, -4.827593F, -8.588894F);
		clawr2 = new ModelRenderer(this, 110, 57);
		clawr2.setTextureSize(128, 64);
		clawr2.addBox(-0.5F, -0.5F, -1F, 1, 5, 1);
		clawr2.setRotationPoint(-12F, -4.827593F, -8.588894F);
		clawr3 = new ModelRenderer(this, 110, 57);
		clawr3.setTextureSize(128, 64);
		clawr3.addBox(0F, -0.5F, -1F, 1, 5, 1);
		clawr3.setRotationPoint(-9F, -2.842278F, -8.346979F);
		clawr4 = new ModelRenderer(this, 110, 57);
		clawr4.setTextureSize(128, 64);
		clawr4.addBox(-1F, -0.5F, -1F, 1, 5, 1);
		clawr4.setRotationPoint(-13F, -2.842278F, -8.346979F);
		lleg = new ModelRenderer(this, 22, 40);
		lleg.setTextureSize(128, 64);
		lleg.addBox(-3F, -1F, -3F, 6, 18, 6);
		lleg.setRotationPoint(3F, 7F, 3F);
		rleg = new ModelRenderer(this, 22, 40);
		rleg.setTextureSize(128, 64);
		rleg.addBox(-3F, -1F, -3F, 6, 18, 6);
		rleg.setRotationPoint(-3F, 7F, 3F);
		back = new ModelRenderer(this, 0, 18);
		back.setTextureSize(128, 64);
		back.addBox(-6F, -7.5F, 0F, 12, 15, 0);
		back.setRotationPoint(0F, -3F, 6F);
		lside = new ModelRenderer(this, 30, 12);
		lside.setTextureSize(128, 64);
		lside.addBox(0F, -7.5F, -3F, 0, 15, 6);
		lside.setRotationPoint(6F, -3F, 3F);
		rside = new ModelRenderer(this, 42, 12);
		rside.setTextureSize(128, 64);
		rside.addBox(0F, -7.5F, -3F, 0, 15, 6);
		rside.setRotationPoint(-6F, -3F, 3F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

		Head.render(par7);

		jaw.rotateAngleX = 0.2365561F;
		jaw.rotateAngleY = 0F;
		jaw.rotateAngleZ = 0F;

		Spine1.rotateAngleX = 0.2590069F;
		Spine1.render(par7);
		Spine2.render(par7);
		lrib1.rotateAngleX = -0.07023507F;
		lrib1.render(par7);
		lrib2.rotateAngleX = -0.07023507F;
		lrib2.render(par7);
		lrib3.rotateAngleX = -0.07023507F;
		lrib3.render(par7);
		rrib1.rotateAngleX = -0.07023507F;
		rrib1.render(par7);
		rrib2.rotateAngleX = -0.07023507F;
		rrib2.render(par7);
		rrib3.rotateAngleX = -0.07023507F;
		rrib3.render(par7);
		pelvis.rotateAngleX = -1.637653E-07F;
		pelvis.render(par7);
		Spine3.rotateAngleX = 0.1290269F;
		Spine3.render(par7);

		larm1.rotateAngleX = -0.5595525F;
		larm1.render(par7);
		larm2.rotateAngleX = -1.44967F;
		larm2.render(par7);
		clawl1.rotateAngleX = -1.44967F;
		clawl1.render(par7);
		clawl2.rotateAngleX = -1.44967F;
		clawl2.render(par7);
		clawl3.rotateAngleX = -1.44967F;
		clawl3.render(par7);
		clawl4.rotateAngleX = -1.44967F;
		clawl4.render(par7);
		rarm1.rotateAngleX = -0.559472F;
		rarm1.render(par7);
		rarm2.rotateAngleX = -1.449542F;
		rarm2.render(par7);
		clawr1.rotateAngleX = -1.449542F;
		clawr1.render(par7);
		clawr2.rotateAngleX = -1.449542F;
		clawr2.render(par7);
		clawr3.rotateAngleX = -1.449542F;
		clawr3.render(par7);
		clawr4.rotateAngleX = -1.449542F;
		clawr4.render(par7);

		lleg.render(par7);
		rleg.render(par7);
		back.render(par7);
		lside.render(par7);
		rside.render(par7);

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

			rleg.rotateAngleX = -((float)Math.PI * 2F / 5F);
			lleg.rotateAngleX = -((float)Math.PI * 2F / 5F);

			rleg.rotateAngleY = (float)Math.PI / 10F;
			lleg.rotateAngleY = -((float)Math.PI / 10F);
		}
	}
}