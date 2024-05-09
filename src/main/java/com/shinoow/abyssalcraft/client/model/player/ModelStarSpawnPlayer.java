/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.model.player;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelStarSpawnPlayer extends ModelBiped {

	public ModelRenderer tentacle1, tentacle2, tentacle3, tentacle4;
	public ModelRenderer limb1, limb1_2, limb1_3, limb1_4;
	public ModelRenderer limb2, limb2_2, limb2_3, limb2_4;
	public ModelRenderer limb3, limb3_2, limb3_3, limb3_4;
	public ModelRenderer limb4, limb4_2, limb4_3, limb4_4;

	public ModelStarSpawnPlayer()
	{
		this(0.0F);
	}

	public ModelStarSpawnPlayer(float par1)
	{
		this(par1, 0.0F, 64, 32);
	}

	public ModelStarSpawnPlayer(float par1, float par2, int par3, int par4)
	{
		textureWidth = par3;
		textureHeight = par4;

		tentacle1 = new ModelRenderer(this, 36, 8);
		tentacle1.setRotationPoint(-3F, 0F + par2, -6F);
		tentacle1.addBox(-0.5F, 0F, 0F, 1, 1, 2, par1);
		tentacle2 = new ModelRenderer(this, 36, 8);
		tentacle2.setRotationPoint(-1F, 0F + par2, -6F);
		tentacle2.addBox(-0.5F, 0F, 0F, 1, 1, 2, par1);
		tentacle3 = new ModelRenderer(this, 36, 8);
		tentacle3.setRotationPoint(1F, 0F + par2, -6F);
		tentacle3.addBox(-0.5F, 0F, 0F, 1, 1, 2, par1);
		tentacle4 = new ModelRenderer(this, 36, 8);
		tentacle4.setRotationPoint(3F, 0F + par2, -6F);
		tentacle4.addBox(-0.5F, 0F, 0F, 1, 1, 2, par1);
		limb1 = new ModelRenderer(this, 36, 11);
		limb1.setRotationPoint(0,1,0);
		limb1.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		tentacle1.addChild(limb1);
		limb1_2 = new ModelRenderer(this, 36, 11);
		limb1_2.setRotationPoint(0,1,0);
		limb1_2.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb1.addChild(limb1_2);
		limb1_3 = new ModelRenderer(this, 36, 11);
		limb1_3.setRotationPoint(0,1,0);
		limb1_3.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb1_2.addChild(limb1_3);
		limb1_4 = new ModelRenderer(this, 36, 11);
		limb1_4.setRotationPoint(0,1,0);
		limb1_4.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb1_3.addChild(limb1_4);
		limb2 = new ModelRenderer(this, 36, 11);
		limb2.setRotationPoint(0,1,0);
		limb2.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		tentacle2.addChild(limb2);
		limb2_2 = new ModelRenderer(this, 36, 11);
		limb2_2.setRotationPoint(0,1,0);
		limb2_2.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb2.addChild(limb2_2);
		limb2_3 = new ModelRenderer(this, 36, 11);
		limb2_3.setRotationPoint(0,1,0);
		limb2_3.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb2_2.addChild(limb2_3);
		limb2_4 = new ModelRenderer(this, 36, 11);
		limb2_4.setRotationPoint(0,1,0);
		limb2_4.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb2_3.addChild(limb2_4);
		limb3 = new ModelRenderer(this, 36, 11);
		limb3.setRotationPoint(0,1,0);
		limb3.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		tentacle3.addChild(limb3);
		limb3_2 = new ModelRenderer(this, 36, 11);
		limb3_2.setRotationPoint(0,1,0);
		limb3_2.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb3.addChild(limb3_2);
		limb3_3 = new ModelRenderer(this, 36, 11);
		limb3_3.setRotationPoint(0,1,0);
		limb3_3.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb3_2.addChild(limb3_3);
		limb3_4 = new ModelRenderer(this, 36, 11);
		limb3_4.setRotationPoint(0,1,0);
		limb3_4.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb3_3.addChild(limb3_4);
		limb4 = new ModelRenderer(this, 36, 11);
		limb4.setRotationPoint(0,1,0);
		limb4.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		tentacle4.addChild(limb4);
		limb4_2 = new ModelRenderer(this, 36, 11);
		limb4_2.setRotationPoint(0,1,0);
		limb4_2.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb4.addChild(limb4_2);
		limb4_3 = new ModelRenderer(this, 36, 11);
		limb4_3.setRotationPoint(0,1,0);
		limb4_3.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb4_2.addChild(limb4_3);
		limb4_4 = new ModelRenderer(this, 36, 11);
		limb4_4.setRotationPoint(0,1,0);
		limb4_4.addBox(-0.5F, 0F, 0F, 1, 1, 1, par1);
		limb4_3.addChild(limb4_4);
	}

	public void setRotationAngles(Entity entity)
	{

		float f16 = 0.0299F;
		limb1.rotateAngleX = MathHelper.sin(entity.ticksExisted * f16) * 4.5F * (float)Math.PI / 180.0F;
		limb1_2.rotateAngleX = MathHelper.sin(entity.ticksExisted * f16) * 4.5F * (float)Math.PI / 180.0F;
		limb1_3.rotateAngleX = MathHelper.sin(entity.ticksExisted * f16) * 4.5F * (float)Math.PI / 180.0F;
		limb1_4.rotateAngleX = MathHelper.sin(entity.ticksExisted * f16) * 4.5F * (float)Math.PI / 180.0F;

		float f17 = 0.0301F;
		limb2.rotateAngleX = MathHelper.sin(entity.ticksExisted * f17) * 4.5F * (float)Math.PI / 180.0F;
		limb2_2.rotateAngleX = MathHelper.sin(entity.ticksExisted * f17) * 4.5F * (float)Math.PI / 180.0F;
		limb2_3.rotateAngleX = MathHelper.sin(entity.ticksExisted * f17) * 4.5F * (float)Math.PI / 180.0F;
		limb2_4.rotateAngleX = MathHelper.sin(entity.ticksExisted * f17) * 4.5F * (float)Math.PI / 180.0F;

		float f18 = 0.0301F;
		limb3.rotateAngleX = MathHelper.sin(entity.ticksExisted * f18) * 4.5F * (float)Math.PI / 180.0F;
		limb3_2.rotateAngleX = MathHelper.sin(entity.ticksExisted * f18) * 4.5F * (float)Math.PI / 180.0F;
		limb3_3.rotateAngleX = MathHelper.sin(entity.ticksExisted * f18) * 4.5F * (float)Math.PI / 180.0F;
		limb3_4.rotateAngleX = MathHelper.sin(entity.ticksExisted * f18) * 4.5F * (float)Math.PI / 180.0F;

		float f19 = 0.0299F;
		limb4.rotateAngleX = MathHelper.sin(entity.ticksExisted * f19) * 4.5F * (float)Math.PI / 180.0F;
		limb4_2.rotateAngleX = MathHelper.sin(entity.ticksExisted * f19) * 4.5F * (float)Math.PI / 180.0F;
		limb4_3.rotateAngleX = MathHelper.sin(entity.ticksExisted * f19) * 4.5F * (float)Math.PI / 180.0F;
		limb4_4.rotateAngleX = MathHelper.sin(entity.ticksExisted * f19) * 4.5F * (float)Math.PI / 180.0F;
	}

	@Override
	public void setVisible(boolean visible){
		super.setVisible(visible);
		tentacle1.showModel = visible;
		tentacle2.showModel = visible;
		tentacle3.showModel = visible;
		tentacle4.showModel = visible;
	}

	public void renderTentacles(float par1, Entity entity){
		setRotationAngles(entity);
		tentacle1.render(par1);
		tentacle2.render(par1);
		tentacle3.render(par1);
		tentacle4.render(par1);
	}
}
