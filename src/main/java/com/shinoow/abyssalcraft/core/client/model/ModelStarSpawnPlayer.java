/**AbyssalCraft Core
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
package com.shinoow.abyssalcraft.core.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelStarSpawnPlayer extends ModelBiped {

	public ModelRenderer tentacle1;
	public ModelRenderer tentacle2;
	public ModelRenderer tentacle3;
	public ModelRenderer tentacle4;
	public ModelRenderer limb1;
	public ModelRenderer limb2;
	public ModelRenderer limb3;
	public ModelRenderer limb4;

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
		limb1 = new ModelRenderer(this, 36, 8);
		limb1.setRotationPoint(0,0,0);
		limb1.addBox(-0.5F, 0F, 0F, 1, 5, 1, par1);
		tentacle1.addChild(limb1);
		limb2 = new ModelRenderer(this, 36, 8);
		limb2.setRotationPoint(0,0,0);
		limb2.addBox(-0.5F, 0F, 0F, 1, 5, 1, par1);
		tentacle2.addChild(limb2);
		limb3 = new ModelRenderer(this, 36, 8);
		limb3.setRotationPoint(0,0,0);
		limb3.addBox(-0.5F, 0F, 0F, 1, 5, 1, par1);
		tentacle3.addChild(limb3);
		limb4 = new ModelRenderer(this, 36, 8);
		limb4.setRotationPoint(0,0,0);
		limb4.addBox(-0.5F, 0F, 0F, 1, 5, 1, par1);
		tentacle4.addChild(limb4);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par6Entity)
	{
		bipedHead.rotateAngleY = f3 / (180F / (float)Math.PI);
		bipedHead.rotateAngleX = f4 / (180F / (float)Math.PI);
		bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
		bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
		bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
		bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
		bipedRightArm.rotateAngleZ = 0.0F;
		bipedLeftArm.rotateAngleZ = 0.0F;
		bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
		bipedRightLeg.rotateAngleY = 0.0F;
		bipedLeftLeg.rotateAngleY = 0.0F;

		if (isRiding)
		{
			bipedRightArm.rotateAngleX += -((float)Math.PI / 5F);
			bipedLeftArm.rotateAngleX += -((float)Math.PI / 5F);
			bipedRightLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
			bipedLeftLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
			bipedRightLeg.rotateAngleY = (float)Math.PI / 10F;
			bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
		}

		if (heldItemLeft != 0)
			bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX * 0.5F - (float)Math.PI / 10F * heldItemLeft;

		if (heldItemRight != 0)
			bipedRightArm.rotateAngleX = bipedRightArm.rotateAngleX * 0.5F - (float)Math.PI / 10F * heldItemRight;

		bipedRightArm.rotateAngleY = 0.0F;
		bipedLeftArm.rotateAngleY = 0.0F;
		float f6;
		float f7;

		if (onGround > -9990.0F)
		{
			f6 = onGround;
			bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
			bipedRightArm.rotationPointZ = MathHelper.sin(bipedBody.rotateAngleY) * 5.0F;
			bipedRightArm.rotationPointX = -MathHelper.cos(bipedBody.rotateAngleY) * 5.0F;
			bipedLeftArm.rotationPointZ = -MathHelper.sin(bipedBody.rotateAngleY) * 5.0F;
			bipedLeftArm.rotationPointX = MathHelper.cos(bipedBody.rotateAngleY) * 5.0F;
			bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
			bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
			bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
			f6 = 1.0F - onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			f7 = MathHelper.sin(f6 * (float)Math.PI);
			float f8 = MathHelper.sin(onGround * (float)Math.PI) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
			bipedRightArm.rotateAngleX = (float)(bipedRightArm.rotateAngleX - (f7 * 1.2D + f8));
			bipedRightArm.rotateAngleY += bipedBody.rotateAngleY * 2.0F;
			bipedRightArm.rotateAngleZ = MathHelper.sin(onGround * (float)Math.PI) * -0.4F;
		}

		if (isSneak)
		{
			bipedBody.rotateAngleX = 0.5F;
			bipedRightArm.rotateAngleX += 0.4F;
			bipedLeftArm.rotateAngleX += 0.4F;
			bipedRightLeg.rotationPointZ = 4.0F;
			bipedLeftLeg.rotationPointZ = 4.0F;
			bipedRightLeg.rotationPointY = 9.0F;
			bipedLeftLeg.rotationPointY = 9.0F;
			bipedHead.rotationPointY = 1.0F;
			bipedHeadwear.rotationPointY = 1.0F;
			tentacle1.rotationPointY = 1.0F;
			tentacle2.rotationPointY = 1.0F;
			tentacle3.rotationPointY = 1.0F;
			tentacle4.rotationPointY = 1.0F;
		}
		else
		{
			bipedBody.rotateAngleX = 0.0F;
			bipedRightLeg.rotationPointZ = 0.1F;
			bipedLeftLeg.rotationPointZ = 0.1F;
			bipedRightLeg.rotationPointY = 12.0F;
			bipedLeftLeg.rotationPointY = 12.0F;
			bipedHead.rotationPointY = 0.0F;
			bipedHeadwear.rotationPointY = 0.0F;
			tentacle1.rotationPointY = 0.0F;
			tentacle2.rotationPointY = 0.0F;
			tentacle3.rotationPointY = 0.0F;
			tentacle4.rotationPointY = 0.0F;
		}

		bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
		bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
		bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
		bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;

		if (aimedBow)
		{
			f6 = 0.0F;
			f7 = 0.0F;
			bipedRightArm.rotateAngleZ = 0.0F;
			bipedLeftArm.rotateAngleZ = 0.0F;
			bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F) + bipedHead.rotateAngleY;
			bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F + bipedHead.rotateAngleY + 0.4F;
			bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + bipedHead.rotateAngleX;
			bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + bipedHead.rotateAngleX;
			bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
			bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
			bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
			bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
		}

		limb1.offsetX = limb1.offsetY = limb1.offsetZ = 0.0F;
		float f16 = 0.01F * (par6Entity.getEntityId() % 10);
		limb1.rotateAngleX = MathHelper.sin(par6Entity.ticksExisted * f16) * 4.5F * (float)Math.PI / 180.0F;
		limb1.rotateAngleY = 0.0F;
		limb1.rotateAngleZ = MathHelper.cos(par6Entity.ticksExisted * f16) * 2.5F * (float)Math.PI / 180.0F;
		float f17 = 0.02F * (par6Entity.getEntityId() % 10);
		limb2.offsetX = limb2.offsetY = limb2.offsetZ = 0.0F;
		limb2.rotateAngleX = MathHelper.sin(par6Entity.ticksExisted * f17) * 4.5F * (float)Math.PI / 180.0F;
		limb2.rotateAngleY = 0.0F;
		limb2.rotateAngleZ = MathHelper.cos(par6Entity.ticksExisted * f17) * 2.5F * (float)Math.PI / 180.0F;
		float f18 = 0.03F * (par6Entity.getEntityId() % 10);
		limb3.offsetX = limb3.offsetY = limb3.offsetZ = 0.0F;
		limb3.rotateAngleX = MathHelper.sin(par6Entity.ticksExisted * f18) * 4.5F * (float)Math.PI / 180.0F;
		limb3.rotateAngleY = 0.0F;
		limb3.rotateAngleZ = MathHelper.cos(par6Entity.ticksExisted * f18) * 2.5F * (float)Math.PI / 180.0F;
		float f19 = 0.04F * (par6Entity.getEntityId() % 10);
		limb4.offsetX = limb4.offsetY = limb4.offsetZ = 0.0F;
		limb4.rotateAngleX = MathHelper.sin(par6Entity.ticksExisted * f19) * 4.5F * (float)Math.PI / 180.0F;
		limb4.rotateAngleY = 0.0F;
		limb4.rotateAngleZ = MathHelper.cos(par6Entity.ticksExisted * f19) * 2.5F * (float)Math.PI / 180.0F;
	}

	public void renderTentacles(float par1){
		tentacle1.render(par1);
		tentacle2.render(par1);
		tentacle3.render(par1);
		tentacle4.render(par1);
	}
}
