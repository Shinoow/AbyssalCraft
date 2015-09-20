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

public class ModelJzaharStatue extends ModelBase
{

	ModelRenderer base;
	ModelRenderer head;
	ModelRenderer mask1;
	ModelRenderer mask2;
	ModelRenderer facetentacle1;
	ModelRenderer facetentacle2;
	ModelRenderer facetentacle3;
	ModelRenderer body1;
	ModelRenderer body2;
	ModelRenderer body3;
	ModelRenderer body4;
	ModelRenderer leftarm;
	ModelRenderer rightarm;
	ModelRenderer armtentacle1;
	ModelRenderer armtentacle2;
	ModelRenderer armtentacle3;
	ModelRenderer armtentacle4;
	ModelRenderer abyssalnomicon;
	ModelRenderer tentacle1;
	ModelRenderer tentacle2;
	ModelRenderer tentacle3;
	ModelRenderer tentacle4;
	ModelRenderer tentacle5;
	ModelRenderer tentacle6;
	ModelRenderer tentacle7;
	ModelRenderer tentacle8;
	ModelRenderer tentacle9;

	public ModelJzaharStatue()
	{
		textureWidth = 64;
		textureHeight = 32;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 8, 6, 8);
		base.setRotationPoint(-4F, 18F, -4F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		head = new ModelRenderer(this, 32, 0);
		head.addBox(0F, 0F, 0F, 4, 4, 4);
		head.setRotationPoint(-2F, 7F, -2F);
		head.setTextureSize(64, 32);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		mask1 = new ModelRenderer(this, 48, 0);
		mask1.addBox(0F, 0F, 0F, 3, 4, 1);
		mask1.setRotationPoint(0F, 7F, -4F);
		mask1.setTextureSize(64, 32);
		mask1.mirror = true;
		setRotation(mask1, 0F, -0.6981317F, 0F);
		mask2 = new ModelRenderer(this, 48, 0);
		mask2.addBox(-3F, 0F, 0F, 3, 4, 1);
		mask2.setRotationPoint(0F, 7F, -4F);
		mask2.setTextureSize(64, 32);
		mask2.mirror = true;
		setRotation(mask2, 0F, 0.6981317F, 0F);
		facetentacle1 = new ModelRenderer(this, 0, 0);
		facetentacle1.addBox(0F, 0F, 0F, 1, 2, 1);
		facetentacle1.setRotationPoint(-1.5F, 10.5F, -2.5F);
		facetentacle1.setTextureSize(64, 32);
		facetentacle1.mirror = true;
		setRotation(facetentacle1, -0.1487144F, 0F, 0F);
		facetentacle2 = new ModelRenderer(this, 0, 0);
		facetentacle2.addBox(0F, 0F, 0F, 1, 2, 1);
		facetentacle2.setRotationPoint(-0.5F, 10.5F, -2.5F);
		facetentacle2.setTextureSize(64, 32);
		facetentacle2.mirror = true;
		setRotation(facetentacle2, -0.2602503F, 0F, 0F);
		facetentacle3 = new ModelRenderer(this, 0, 0);
		facetentacle3.addBox(0F, 0F, 0F, 1, 2, 1);
		facetentacle3.setRotationPoint(0.5F, 10.5F, -2.5F);
		facetentacle3.setTextureSize(64, 32);
		facetentacle3.mirror = true;
		setRotation(facetentacle3, -0.4089647F, 0F, 0F);
		body1 = new ModelRenderer(this, 0, 14);
		body1.addBox(0F, 0F, 0F, 4, 7, 2);
		body1.setRotationPoint(-2F, 11F, -0.5F);
		body1.setTextureSize(64, 32);
		body1.mirror = true;
		setRotation(body1, 0F, 0F, 0F);
		body2 = new ModelRenderer(this, 12, 14);
		body2.addBox(0F, 0F, 0F, 1, 7, 1);
		body2.setRotationPoint(-2F, 11F, -1.5F);
		body2.setTextureSize(64, 32);
		body2.mirror = true;
		setRotation(body2, 0F, 0F, 0F);
		body3 = new ModelRenderer(this, 12, 14);
		body3.addBox(0F, 0F, 0F, 1, 7, 1);
		body3.setRotationPoint(1F, 11F, -1.5F);
		body3.setTextureSize(64, 32);
		body3.mirror = true;
		setRotation(body3, 0F, 0F, 0F);
		body4 = new ModelRenderer(this, 16, 14);
		body4.addBox(0F, 0F, 0F, 2, 7, 1);
		body4.setRotationPoint(-1F, 11F, -1F);
		body4.setTextureSize(64, 32);
		body4.mirror = true;
		setRotation(body4, 0F, 0F, 0F);
		leftarm = new ModelRenderer(this, 32, 8);
		leftarm.addBox(0F, 0F, 0F, 2, 5, 2);
		leftarm.setRotationPoint(-3.5F, 11F, -1F);
		leftarm.setTextureSize(64, 32);
		leftarm.mirror = true;
		setRotation(leftarm, -0.6108652F, -0.2617994F, 0F);
		rightarm = new ModelRenderer(this, 32, 8);
		rightarm.addBox(0F, 0F, 0F, 2, 3, 2);
		rightarm.setRotationPoint(1.5F, 11F, -1F);
		rightarm.setTextureSize(64, 32);
		rightarm.mirror = true;
		setRotation(rightarm, -0.6108652F, 0.2617994F, 0F);
		armtentacle1 = new ModelRenderer(this, 28, 0);
		armtentacle1.addBox(0F, 0F, 0F, 1, 2, 1);
		armtentacle1.setRotationPoint(1.1F, 13.5F, -2.6F);
		armtentacle1.setTextureSize(64, 32);
		armtentacle1.mirror = true;
		setRotation(armtentacle1, -0.7224011F, 0.4476924F, 0F);
		armtentacle2 = new ModelRenderer(this, 28, 0);
		armtentacle2.addBox(0F, 0F, 0F, 1, 2, 1);
		armtentacle2.setRotationPoint(2F, 13.5F, -2.8F);
		armtentacle2.setTextureSize(64, 32);
		armtentacle2.mirror = true;
		setRotation(armtentacle2, -0.8711155F, 0.1130849F, 0F);
		armtentacle3 = new ModelRenderer(this, 28, 0);
		armtentacle3.addBox(0F, 0F, 0F, 1, 2, 1);
		armtentacle3.setRotationPoint(1.3F, 14F, -1.8F);
		armtentacle3.setTextureSize(64, 32);
		armtentacle3.mirror = true;
		setRotation(armtentacle3, -0.350615F, 0.5964069F, 0F);
		armtentacle4 = new ModelRenderer(this, 28, 0);
		armtentacle4.addBox(0F, 0F, 0F, 1, 2, 1);
		armtentacle4.setRotationPoint(2.2F, 14F, -2.1F);
		armtentacle4.setTextureSize(64, 32);
		armtentacle4.mirror = true;
		setRotation(armtentacle4, -0.350615F, -0.1843439F, 0F);
		abyssalnomicon = new ModelRenderer(this, 40, 8);
		abyssalnomicon.addBox(0F, 0F, 0F, 3, 4, 1);
		abyssalnomicon.setRotationPoint(-2F, 13F, -5F);
		abyssalnomicon.setTextureSize(64, 32);
		abyssalnomicon.mirror = true;
		setRotation(abyssalnomicon, 0.6108652F, 0F, 0F);
		tentacle1 = new ModelRenderer(this, 22, 15);
		tentacle1.addBox(0F, 0F, 0F, 2, 2, 4);
		tentacle1.setRotationPoint(-2F, 16F, 0.5F);
		tentacle1.setTextureSize(64, 32);
		tentacle1.mirror = true;
		setRotation(tentacle1, -0.0872665F, -0.5235988F, 0F);
		tentacle2 = new ModelRenderer(this, 22, 15);
		tentacle2.addBox(0F, 0F, 0F, 2, 4, 2);
		tentacle2.setRotationPoint(-3.5F, 16.4F, 3F);
		tentacle2.setTextureSize(64, 32);
		tentacle2.mirror = true;
		setRotation(tentacle2, 0F, -0.5235988F, 0F);
		tentacle3 = new ModelRenderer(this, 22, 15);
		tentacle3.addBox(0F, 0F, 0F, 1, 1, 3);
		tentacle3.setRotationPoint(0.5F, 17F, 1.5F);
		tentacle3.setTextureSize(64, 32);
		tentacle3.mirror = true;
		setRotation(tentacle3, 0F, 0.2230717F, 0F);
		tentacle4 = new ModelRenderer(this, 22, 15);
		tentacle4.addBox(0F, 0F, -2F, 2, 2, 4);
		tentacle4.setRotationPoint(1F, 16.5F, -2F);
		tentacle4.setTextureSize(64, 32);
		tentacle4.mirror = true;
		setRotation(tentacle4, 0F, -1.047198F, 0F);
		tentacle5 = new ModelRenderer(this, 22, 15);
		tentacle5.addBox(-1F, 0F, 0F, 4, 1, 1);
		tentacle5.setRotationPoint(-3F, 17F, -2F);
		tentacle5.setTextureSize(64, 32);
		tentacle5.mirror = true;
		setRotation(tentacle5, 0F, -0.6283185F, 0F);
		tentacle6 = new ModelRenderer(this, 22, 15);
		tentacle6.addBox(0F, 0F, 0F, 1, 4, 1);
		tentacle6.setRotationPoint(-4.6F, 17F, -3.2F);
		tentacle6.setTextureSize(64, 32);
		tentacle6.mirror = true;
		setRotation(tentacle6, 0F, -0.6283185F, 0F);
		tentacle7 = new ModelRenderer(this, 22, 15);
		tentacle7.addBox(0F, 0F, 0F, 1, 1, 3);
		tentacle7.setRotationPoint(-1.5F, 17F, -3.5F);
		tentacle7.setTextureSize(64, 32);
		tentacle7.mirror = true;
		setRotation(tentacle7, 0F, 0F, 0F);
		tentacle8 = new ModelRenderer(this, 22, 15);
		tentacle8.addBox(0F, 0F, 0F, 2, 1, 1);
		tentacle8.setRotationPoint(2F, 17F, 0F);
		tentacle8.setTextureSize(64, 32);
		tentacle8.mirror = true;
		setRotation(tentacle8, 0F, 0F, 0F);
		tentacle9 = new ModelRenderer(this, 22, 15);
		tentacle9.addBox(0F, 0F, 0F, 1, 3, 1);
		tentacle9.setRotationPoint(4F, 17F, 0F);
		tentacle9.setTextureSize(64, 32);
		tentacle9.mirror = true;
		setRotation(tentacle9, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		base.render(f5);
		head.render(f5);
		mask1.render(f5);
		mask2.render(f5);
		facetentacle1.render(f5);
		facetentacle2.render(f5);
		facetentacle3.render(f5);
		body1.render(f5);
		body2.render(f5);
		body3.render(f5);
		body4.render(f5);
		leftarm.render(f5);
		rightarm.render(f5);
		armtentacle1.render(f5);
		armtentacle2.render(f5);
		armtentacle3.render(f5);
		armtentacle4.render(f5);
		abyssalnomicon.render(f5);
		tentacle1.render(f5);
		tentacle2.render(f5);
		tentacle3.render(f5);
		tentacle4.render(f5);
		tentacle5.render(f5);
		tentacle6.render(f5);
		tentacle7.render(f5);
		tentacle8.render(f5);
		tentacle9.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}