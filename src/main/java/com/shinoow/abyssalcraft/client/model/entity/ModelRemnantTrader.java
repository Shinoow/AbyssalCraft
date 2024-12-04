package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelRemnantTrader extends ModelBase {
	public ModelRenderer villagerArms0;
	public ModelRenderer villagerArms1;
	public ModelRenderer rightLegJoint;
	public ModelRenderer leftLegJoint;
	public ModelRenderer head;
	public ModelRenderer villagerArms2;
	public ModelRenderer arm1;
	public ModelRenderer arm2;
	public ModelRenderer arm3;
	public ModelRenderer arm4;
	public ModelRenderer rightLegT2;
	public ModelRenderer rightLegT3;
	public ModelRenderer rightLegT4;
	public ModelRenderer rightLegT1;
	public ModelRenderer rightLegB2;
	public ModelRenderer rightLegB3;
	public ModelRenderer rightLegB4;
	public ModelRenderer rightLegB1;
	public ModelRenderer leftLegT2;
	public ModelRenderer leftLegT3;
	public ModelRenderer leftLegT4;
	public ModelRenderer leftLegT1;
	public ModelRenderer leftLegB2;
	public ModelRenderer leftLegB3;
	public ModelRenderer leftLegB4;
	public ModelRenderer leftLegB1;
	public ModelRenderer mask1;
	public ModelRenderer maskNose;
	public ModelRenderer villagerBody;

	public ModelRemnantTrader() {
		textureWidth = 64;
		textureHeight = 64;
		leftLegB3 = new ModelRenderer(this, 32, 0);
		leftLegB3.mirror = true;
		leftLegB3.setRotationPoint(0.0F, 6.0F, -1.0F);
		leftLegB3.addBox(0.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		leftLegT4 = new ModelRenderer(this, 0, 22);
		leftLegT4.mirror = true;
		leftLegT4.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftLegT4.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		rightLegB1 = new ModelRenderer(this, 32, 0);
		rightLegB1.setRotationPoint(-1.0F, 6.0F, -1.0F);
		rightLegB1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		villagerArms1 = new ModelRenderer(this, 44, 22);
		villagerArms1.setRotationPoint(0.0F, 3.0F, -1.0F);
		villagerArms1.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
		rightLegB4 = new ModelRenderer(this, 40, 0);
		rightLegB4.setRotationPoint(0.0F, 6.0F, 0.0F);
		rightLegB4.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		rightLegT4 = new ModelRenderer(this, 8, 30);
		rightLegT4.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightLegT4.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		arm1 = new ModelRenderer(this, 42, 46);
		arm1.setRotationPoint(4.0F, 4.0F, 0.0F);
		arm1.addBox(-4.0F, 0.0F, -2.0F, 4, 2, 2, 0.0F);
		arm3 = new ModelRenderer(this, 42, 50);
		arm3.setRotationPoint(4.0F, 4.0F, 0.0F);
		arm3.addBox(-4.0F, 0.0F, 0.0F, 4, 2, 2, 0.0F);
		head = new ModelRenderer(this, 0, 0);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		leftLegT2 = new ModelRenderer(this, 8, 30);
		leftLegT2.mirror = true;
		leftLegT2.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftLegT2.addBox(-2.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		rightLegT2 = new ModelRenderer(this, 0, 22);
		rightLegT2.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightLegT2.addBox(-2.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		leftLegT3 = new ModelRenderer(this, 8, 22);
		leftLegT3.mirror = true;
		leftLegT3.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftLegT3.addBox(0.0F, 0.0F, -2.0F, 2, 6, 2, 0.0F);
		arm2 = new ModelRenderer(this, 42, 50);
		arm2.setRotationPoint(4.0F, 4.0F, 0.0F);
		arm2.addBox(-4.0F, -2.0F, -2.0F, 4, 2, 2, 0.0F);
		leftLegT1 = new ModelRenderer(this, 0, 30);
		leftLegT1.mirror = true;
		leftLegT1.setRotationPoint(0.0F, 0.0F, 0.0F);
		leftLegT1.addBox(-2.0F, 0.0F, -2.0F, 2, 6, 2, 0.0F);
		leftLegB4 = new ModelRenderer(this, 40, 0);
		leftLegB4.mirror = true;
		leftLegB4.setRotationPoint(0.0F, 6.0F, 0.0F);
		leftLegB4.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		rightLegB2 = new ModelRenderer(this, 40, 0);
		rightLegB2.setRotationPoint(-1.0F, 6.0F, 0.0F);
		rightLegB2.addBox(-1.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		rightLegT1 = new ModelRenderer(this, 8, 22);
		rightLegT1.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightLegT1.addBox(-2.0F, 0.0F, -2.0F, 2, 6, 2, 0.0F);
		leftLegB1 = new ModelRenderer(this, 32, 0);
		leftLegB1.mirror = true;
		leftLegB1.setRotationPoint(-1.0F, 6.0F, -1.0F);
		leftLegB1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		arm4 = new ModelRenderer(this, 42, 46);
		arm4.setRotationPoint(4.0F, 4.0F, 0.0F);
		arm4.addBox(-4.0F, -2.0F, 0.0F, 4, 2, 2, 0.0F);
		mask1 = new ModelRenderer(this, 32, 8);
		mask1.setRotationPoint(0.0F, 0.0F, -1.0F);
		mask1.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 1, 0.0F);
		villagerArms2 = new ModelRenderer(this, 40, 38);
		villagerArms2.setRotationPoint(0.0F, 3.0F, 0.0F);
		villagerArms2.addBox(-4.0F, -1.0F, -2.0F, 4, 4, 4, 0.0F);
		rightLegB3 = new ModelRenderer(this, 32, 0);
		rightLegB3.setRotationPoint(0.0F, 6.0F, -1.0F);
		rightLegB3.addBox(0.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
		rightLegJoint = new ModelRenderer(this, 0, 0);
		rightLegJoint.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightLegJoint.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
		leftLegB2 = new ModelRenderer(this, 40, 0);
		leftLegB2.mirror = true;
		leftLegB2.setRotationPoint(-1.0F, 6.0F, 0.0F);
		leftLegB2.addBox(-1.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		villagerArms0 = new ModelRenderer(this, 44, 22);
		villagerArms0.setRotationPoint(0.0F, 3.0F, -1.0F);
		villagerArms0.addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
		maskNose = new ModelRenderer(this, 24, 0);
		maskNose.setRotationPoint(0.0F, -2.0F, 0.0F);
		maskNose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
		leftLegJoint = new ModelRenderer(this, 0, 0);
		leftLegJoint.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftLegJoint.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
		rightLegT3 = new ModelRenderer(this, 0, 30);
		rightLegT3.setRotationPoint(0.0F, 0.0F, 0.0F);
		rightLegT3.addBox(0.0F, 0.0F, -2.0F, 2, 6, 2, 0.0F);
		villagerBody = new ModelRenderer(this).setTextureSize(64, 64);
		villagerBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0);
		villagerBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.5F);
		leftLegT3.addChild(leftLegB3);
		leftLegJoint.addChild(leftLegT4);
		rightLegT1.addChild(rightLegB1);
		rightLegT4.addChild(rightLegB4);
		rightLegJoint.addChild(rightLegT4);
		villagerArms1.addChild(arm1);
		villagerArms1.addChild(arm3);
		leftLegJoint.addChild(leftLegT2);
		rightLegJoint.addChild(rightLegT2);
		leftLegJoint.addChild(leftLegT3);
		villagerArms1.addChild(arm2);
		leftLegJoint.addChild(leftLegT1);
		leftLegT4.addChild(leftLegB4);
		rightLegT2.addChild(rightLegB2);
		rightLegJoint.addChild(rightLegT1);
		leftLegT1.addChild(leftLegB1);
		villagerArms1.addChild(arm4);
		head.addChild(mask1);
		villagerArms0.addChild(villagerArms2);
		rightLegT3.addChild(rightLegB3);
		leftLegT2.addChild(leftLegB2);
		mask1.addChild(maskNose);
		rightLegJoint.addChild(rightLegT3);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		villagerArms1.render(scale);
		head.render(scale);
		villagerBody.render(scale);
		rightLegJoint.render(scale);
		villagerArms0.render(scale);
		leftLegJoint.render(scale);
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		head.rotateAngleY = netHeadYaw * 0.017453292F;
		head.rotateAngleX = headPitch * 0.017453292F;
		villagerArms0.rotationPointY = 3.0F;
		villagerArms0.rotationPointZ = -1.0F;
		villagerArms0.rotateAngleX = -0.75F;
		villagerArms1.rotationPointY = 3.0F;
		villagerArms1.rotationPointZ = -1.0F;
		villagerArms1.rotateAngleX = -0.75F;
		rightLegJoint.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		leftLegJoint.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		rightLegJoint.rotateAngleY = 0.0F;
		leftLegJoint.rotateAngleY = 0.0F;


		arm1.offsetX = arm1.offsetY = arm1.offsetZ = 0.0F;
		float f16 = 0.03F * (entityIn.getEntityId() % 10);
		arm1.rotateAngleY = MathHelper.cos(entityIn.ticksExisted * f16) * 10.5F * (float)Math.PI / 180.0F;
		arm1.rotateAngleX = 0.0F;
		arm1.rotateAngleZ = MathHelper.sin(entityIn.ticksExisted * f16) * 6.5F * (float)Math.PI / 180.0F;
		arm2.offsetX = arm2.offsetY = arm2.offsetZ = 0.0F;
		arm2.rotateAngleY = MathHelper.sin(entityIn.ticksExisted * f16) * 10.5F * (float)Math.PI / 180.0F;
		arm2.rotateAngleX = 0.0F;
		arm2.rotateAngleZ = MathHelper.cos(entityIn.ticksExisted * f16) * 6.5F * (float)Math.PI / 180.0F;
		arm3.offsetX = arm3.offsetY = arm3.offsetZ = 0.0F;
		arm3.rotateAngleY = MathHelper.sin(entityIn.ticksExisted * f16) * 10.5F * (float)Math.PI / 180.0F;
		arm3.rotateAngleX = 0.0F;
		arm3.rotateAngleZ = MathHelper.cos(entityIn.ticksExisted * f16) * 6.5F * (float)Math.PI / 180.0F;
		arm4.offsetX = arm4.offsetY = arm4.offsetZ = 0.0F;
		arm4.rotateAngleY = MathHelper.cos(entityIn.ticksExisted * f16) * 10.5F * (float)Math.PI / 180.0F;
		arm4.rotateAngleX = 0.0F;
		arm4.rotateAngleZ = MathHelper.sin(entityIn.ticksExisted * f16) * 6.5F * (float)Math.PI / 180.0F;

		MathHelper.sin((limbSwing * 0.4F + 2) * 1.5F);
		float flap = MathHelper.sin(entityIn.ticksExisted * 0.2F) * 0.3F;
		float flap2 = MathHelper.cos(entityIn.ticksExisted * 0.2F) * 0.4F;

		leftLegT1.rotateAngleY = flap * 10.5F * (float)Math.PI / 180.0F + 0.3f;
		leftLegT1.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F;

		leftLegT3.rotateAngleY = flap * 10.5F * (float)Math.PI / 180.0F - 0.3f;
		leftLegT3.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F;

		leftLegT4.rotateAngleY = flap * 10.5F * (float)Math.PI / 180.0F + 0.3F;
		leftLegT4.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F;

		leftLegT2.rotateAngleY = flap * 10.5F * (float)Math.PI / 180.0F - 0.3F;
		leftLegT2.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F;

		rightLegT1.rotateAngleY = flap * 10.5F * (float)Math.PI / 180.0F - 0.3F;
		rightLegT1.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F;

		rightLegT3.rotateAngleY = flap * 10.5F * (float)Math.PI / 180.0F + 0.3F;
		rightLegT3.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F;

		rightLegT4.rotateAngleY = flap * 10.5F * (float)Math.PI / 180.0F - 0.3F;
		rightLegT4.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F;

		rightLegT2.rotateAngleY = flap * 10.5F * (float)Math.PI / 180.0F + 0.3F;
		rightLegT2.rotateAngleX = flap2 * 6.5F * (float)Math.PI / 180.0F;

		rightLegB1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		rightLegB1.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		rightLegB1.rotateAngleY = 0.0F;
		rightLegB2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		rightLegB2.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		rightLegB2.rotateAngleY = 0.0F;
		rightLegB3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		rightLegB3.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		rightLegB3.rotateAngleY = 0.0F;
		rightLegB4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		rightLegB4.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		rightLegB4.rotateAngleY = 0.0F;

		leftLegB1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		leftLegB1.rotateAngleZ = -MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		leftLegB1.rotateAngleY = 0.0F;
		leftLegB2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		leftLegB2.rotateAngleZ = -MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		leftLegB2.rotateAngleY = 0.0F;
		leftLegB3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		leftLegB3.rotateAngleZ = -MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		leftLegB3.rotateAngleY = 0.0F;
		leftLegB4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		leftLegB4.rotateAngleZ = -MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		leftLegB4.rotateAngleY = 0.0F;
	}
}
