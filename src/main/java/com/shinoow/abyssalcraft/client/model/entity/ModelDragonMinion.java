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
package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDragonMinion extends ModelBase {

	private ModelRenderer head;
	private ModelRenderer neck;
	private ModelRenderer jaw;
	private ModelRenderer body;
	private ModelRenderer rearLeg;
	private ModelRenderer frontLeg;
	private ModelRenderer rearLegTip;
	private ModelRenderer frontLegTip;
	private ModelRenderer rearFoot;
	private ModelRenderer frontFoot;
	private ModelRenderer wing;
	private ModelRenderer wingTip;
	private float partialTicks;

	public ModelDragonMinion(float par1)
	{
		textureWidth = 256;
		textureHeight = 256;
		setTextureOffset("body.body", 0, 0);
		setTextureOffset("wing.skin", -56, 88);
		setTextureOffset("wingtip.skin", -56, 144);
		setTextureOffset("rearleg.main", 0, 0);
		setTextureOffset("rearfoot.main", 112, 0);
		setTextureOffset("rearlegtip.main", 196, 0);
		setTextureOffset("head.upperhead", 112, 30);
		setTextureOffset("wing.bone", 112, 88);
		setTextureOffset("head.upperlip", 176, 44);
		setTextureOffset("jaw.jaw", 176, 65);
		setTextureOffset("frontleg.main", 112, 104);
		setTextureOffset("wingtip.bone", 112, 136);
		setTextureOffset("frontfoot.main", 144, 104);
		setTextureOffset("neck.box", 192, 104);
		setTextureOffset("frontlegtip.main", 226, 138);
		setTextureOffset("body.scale", 220, 53);
		setTextureOffset("head.scale", 0, 0);
		setTextureOffset("neck.scale", 48, 0);
		setTextureOffset("head.nostril", 112, 0);
		float f1 = -16.0F;
		head = new ModelRenderer(this, "head");
		head.addBox("upperlip", -6.0F, -1.0F, -8.0F + f1, 12, 5, 16);
		head.addBox("upperhead", -8.0F, -8.0F, 6.0F + f1, 16, 16, 16);
		head.mirror = true;
		head.addBox("scale", -5.0F, -12.0F, 12.0F + f1, 2, 4, 6);
		head.addBox("nostril", -5.0F, -3.0F, -6.0F + f1, 2, 2, 4);
		head.mirror = false;
		head.addBox("scale", 3.0F, -12.0F, 12.0F + f1, 2, 4, 6);
		head.addBox("nostril", 3.0F, -3.0F, -6.0F + f1, 2, 2, 4);
		jaw = new ModelRenderer(this, "jaw");
		jaw.setRotationPoint(0.0F, 4.0F, 8.0F + f1);
		jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
		head.addChild(jaw);
		neck = new ModelRenderer(this, "neck");
		neck.addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
		neck.addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
		body = new ModelRenderer(this, "body");
		body.setRotationPoint(0.0F, 4.0F, 8.0F);
		body.addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
		body.addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
		body.addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
		body.addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
		wing = new ModelRenderer(this, "wing");
		wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
		wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
		wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
		wingTip = new ModelRenderer(this, "wingtip");
		wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
		wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
		wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
		wing.addChild(wingTip);
		frontLeg = new ModelRenderer(this, "frontleg");
		frontLeg.setRotationPoint(-12.0F, 20.0F, 2.0F);
		frontLeg.addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
		frontLegTip = new ModelRenderer(this, "frontlegtip");
		frontLegTip.setRotationPoint(0.0F, 20.0F, -1.0F);
		frontLegTip.addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
		frontLeg.addChild(frontLegTip);
		frontFoot = new ModelRenderer(this, "frontfoot");
		frontFoot.setRotationPoint(0.0F, 23.0F, 0.0F);
		frontFoot.addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
		frontLegTip.addChild(frontFoot);
		rearLeg = new ModelRenderer(this, "rearleg");
		rearLeg.setRotationPoint(-16.0F, 16.0F, 42.0F);
		rearLeg.addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
		rearLegTip = new ModelRenderer(this, "rearlegtip");
		rearLegTip.setRotationPoint(0.0F, 32.0F, -4.0F);
		rearLegTip.addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
		rearLeg.addChild(rearLegTip);
		rearFoot = new ModelRenderer(this, "rearfoot");
		rearFoot.setRotationPoint(0.0F, 31.0F, 4.0F);
		rearFoot.addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
		rearLegTip.addChild(rearFoot);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		partialTicks = par4;
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		GL11.glPushMatrix();
		EntityDragonMinion entitydragon = (EntityDragonMinion)par1Entity;
		float f6 = entitydragon.prevAnimTime + (entitydragon.animTime - entitydragon.prevAnimTime) * partialTicks;
		jaw.rotateAngleX = (float)(Math.sin(f6 * (float)Math.PI * 2.0F) + 1.0D) * 0.2F;
		float f7 = (float)(Math.sin(f6 * (float)Math.PI * 2.0F - 1.0F) + 1.0D);
		f7 = (f7 * f7 * 1.0F + f7 * 2.0F) * 0.05F;
		GL11.glTranslatef(0.0F, f7 - 2.0F, -3.0F);
		GL11.glRotatef(f7 * 2.0F, 1.0F, 0.0F, 0.0F);
		float f8 = -30.0F;
		float f9 = 0.0F;
		float f10 = 1.5F;
		double[] adouble = entitydragon.getMovementOffsets(6, partialTicks);
		float f11 = updateRotations(entitydragon.getMovementOffsets(5, partialTicks)[0] - entitydragon.getMovementOffsets(10, partialTicks)[0]);
		float f12 = updateRotations(entitydragon.getMovementOffsets(5, partialTicks)[0] + f11 / 2.0F);
		f8 += 2.0F;
		float f13 = f6 * (float)Math.PI * 2.0F;
		f8 = 20.0F;
		float f14 = -12.0F;
		float f15;

		for (int i = 0; i < 5; ++i)
		{
			double[] adouble1 = entitydragon.getMovementOffsets(5 - i, partialTicks);
			f15 = (float)Math.cos(i * 0.45F + f13) * 0.15F;
			neck.rotateAngleY = updateRotations(adouble1[0] - adouble[0]) * (float)Math.PI / 180.0F * f10;
			neck.rotateAngleX = f15 + (float)(adouble1[1] - adouble[1]) * (float)Math.PI / 180.0F * f10 * 5.0F;
			neck.rotateAngleZ = -updateRotations(adouble1[0] - f12) * (float)Math.PI / 180.0F * f10;
			neck.rotationPointY = f8;
			neck.rotationPointZ = f14;
			neck.rotationPointX = f9;
			f8 = (float)(f8 + Math.sin(neck.rotateAngleX) * 10.0D);
			f14 = (float)(f14 - Math.cos(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10.0D);
			f9 = (float)(f9 - Math.sin(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10.0D);
			neck.render(par7);
		}

		head.rotationPointY = f8;
		head.rotationPointZ = f14;
		head.rotationPointX = f9;
		double[] adouble2 = entitydragon.getMovementOffsets(0, partialTicks);
		head.rotateAngleY = updateRotations(adouble2[0] - adouble[0]) * (float)Math.PI / 180.0F * 1.0F;
		head.rotateAngleZ = -updateRotations(adouble2[0] - f12) * (float)Math.PI / 180.0F * 1.0F;
		head.render(par7);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-f11 * f10 * 1.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(0.0F, -1.0F, 0.0F);
		body.rotateAngleZ = 0.0F;
		body.render(par7);

		for (int j = 0; j < 2; ++j)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
			f15 = f6 * (float)Math.PI * 2.0F;
			wing.rotateAngleX = 0.125F - (float)Math.cos(f15) * 0.2F;
			wing.rotateAngleY = 0.25F;
			wing.rotateAngleZ = (float)(Math.sin(f15) + 0.125D) * 0.8F;
			wingTip.rotateAngleZ = -((float)(Math.sin(f15 + 2.0F) + 0.5D)) * 0.75F;
			rearLeg.rotateAngleX = 1.0F + f7 * 0.1F;
			rearLegTip.rotateAngleX = 0.5F + f7 * 0.1F;
			rearFoot.rotateAngleX = 0.75F + f7 * 0.1F;
			frontLeg.rotateAngleX = 1.3F + f7 * 0.1F;
			frontLegTip.rotateAngleX = -0.5F - f7 * 0.1F;
			frontFoot.rotateAngleX = 0.75F + f7 * 0.1F;
			wing.render(par7);
			frontLeg.render(par7);
			rearLeg.render(par7);
			GL11.glScalef(-1.0F, 1.0F, 1.0F);

			if (j == 0)
				GL11.glCullFace(GL11.GL_FRONT);
		}

		GL11.glPopMatrix();
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glDisable(GL11.GL_CULL_FACE);
		float f16 = -((float)Math.sin(f6 * (float)Math.PI * 2.0F)) * 0.0F;
		f13 = f6 * (float)Math.PI * 2.0F;
		f8 = 10.0F;
		f14 = 60.0F;
		f9 = 0.0F;
		adouble = entitydragon.getMovementOffsets(11, partialTicks);

		for (int k = 0; k < 12; ++k)
		{
			adouble2 = entitydragon.getMovementOffsets(12 + k, partialTicks);
			f16 = (float)(f16 + Math.sin(k * 0.45F + f13) * 0.05000000074505806D);
			neck.rotateAngleY = (updateRotations(adouble2[0] - adouble[0]) * f10 + 180.0F) * (float)Math.PI / 180.0F;
			neck.rotateAngleX = f16 + (float)(adouble2[1] - adouble[1]) * (float)Math.PI / 180.0F * f10 * 5.0F;
			neck.rotateAngleZ = updateRotations(adouble2[0] - f12) * (float)Math.PI / 180.0F * f10;
			neck.rotationPointY = f8;
			neck.rotationPointZ = f14;
			neck.rotationPointX = f9;
			f8 = (float)(f8 + Math.sin(neck.rotateAngleX) * 10.0D);
			f14 = (float)(f14 - Math.cos(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10.0D);
			f9 = (float)(f9 - Math.sin(neck.rotateAngleY) * Math.cos(neck.rotateAngleX) * 10.0D);
			neck.render(par7);
		}

		GL11.glPopMatrix();
	}

	/**
	 * Updates the rotations in the parameters for rotations greater than 180 degrees or less than -180 degrees. It adds
	 * or subtracts 360 degrees, so that the appearance is the same, although the numbers are then simplified to range
	 * -180 to 180
	 */
	private float updateRotations(double par1)
	{
		while (par1 >= 180.0D)
			par1 -= 360.0D;

		while (par1 < -180.0D)
			par1 += 360.0D;

		return (float)par1;
	}
}
