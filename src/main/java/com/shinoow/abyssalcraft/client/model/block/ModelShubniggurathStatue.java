/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
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

public class ModelShubniggurathStatue extends ModelBase
{

	ModelRenderer base;
	ModelRenderer head1;
	ModelRenderer head2;
	ModelRenderer lefthorn;
	ModelRenderer righthorn;
	ModelRenderer leftear;
	ModelRenderer rightear;
	ModelRenderer body1;
	ModelRenderer body2;
	ModelRenderer body3;
	ModelRenderer body4;
	ModelRenderer body5;
	ModelRenderer leftarm1;
	ModelRenderer leftarm2;
	ModelRenderer leftfinger1;
	ModelRenderer leftfinger2;
	ModelRenderer rightarm1;
	ModelRenderer rightarm2;
	ModelRenderer rightfinger1;
	ModelRenderer rightfinger2;
	ModelRenderer leftleg1;
	ModelRenderer leftleg2;
	ModelRenderer lefthoof2;
	ModelRenderer lefthoof1;
	ModelRenderer rightleg1;
	ModelRenderer rightleg2;
	ModelRenderer righthoof1;
	ModelRenderer righthoof2;

	public ModelShubniggurathStatue()
	{
		textureWidth = 64;
		textureHeight = 32;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 8, 6, 8);
		base.setRotationPoint(-4F, 18F, -4F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		head1 = new ModelRenderer(this, 0, 21);
		head1.addBox(0F, 0F, 0F, 3, 4, 3);
		head1.setRotationPoint(-1.5F, 5.8F, -1.5F);
		head1.setTextureSize(64, 32);
		head1.mirror = true;
		setRotation(head1, -0.0872665F, 0F, 0F);
		head2 = new ModelRenderer(this, 12, 21);
		head2.addBox(0F, 0F, 0F, 3, 4, 1);
		head2.setRotationPoint(-1.5F, 6F, 1F);
		head2.setTextureSize(64, 32);
		head2.mirror = true;
		setRotation(head2, 0F, 0F, 0F);
		lefthorn = new ModelRenderer(this, 24, 22);
		lefthorn.addBox(0F, 0F, 0F, 1, 1, 3);
		lefthorn.setRotationPoint(1.5F, 6F, -1F);
		lefthorn.setTextureSize(64, 32);
		lefthorn.mirror = true;
		setRotation(lefthorn, 0.0872665F, 0F, 0F);
		righthorn = new ModelRenderer(this, 24, 22);
		righthorn.addBox(0F, 0F, 0F, 1, 1, 3);
		righthorn.setRotationPoint(-2.5F, 6F, -1F);
		righthorn.setTextureSize(64, 32);
		righthorn.mirror = true;
		setRotation(righthorn, 0.0872665F, 0F, 0F);
		leftear = new ModelRenderer(this, 20, 22);
		leftear.addBox(0F, 0F, 0F, 1, 3, 1);
		leftear.setRotationPoint(1F, 7F, -0.5F);
		leftear.setTextureSize(64, 32);
		leftear.mirror = true;
		setRotation(leftear, 0F, 0F, 0F);
		rightear = new ModelRenderer(this, 20, 22);
		rightear.addBox(0F, 0F, 0F, 1, 3, 1);
		rightear.setRotationPoint(-2F, 7F, -0.5F);
		rightear.setTextureSize(64, 32);
		rightear.mirror = true;
		setRotation(rightear, 0F, 0F, 0F);
		body1 = new ModelRenderer(this, 32, 0);
		body1.addBox(0F, 0F, 0F, 4, 5, 4);
		body1.setRotationPoint(-2F, 13F, -3F);
		body1.setTextureSize(64, 32);
		body1.mirror = true;
		setRotation(body1, 0F, 0F, 0F);
		body2 = new ModelRenderer(this, 34, 14);
		body2.addBox(0F, 0F, 0F, 5, 8, 2);
		body2.setRotationPoint(-2.5F, 10F, 0F);
		body2.setTextureSize(64, 32);
		body2.mirror = true;
		setRotation(body2, 0F, 0F, 0F);
		body3 = new ModelRenderer(this, 22, 14);
		body3.addBox(0F, 0F, 0F, 2, 2, 4);
		body3.setRotationPoint(0.2F, 11.5F, -3.5F);
		body3.setTextureSize(64, 32);
		body3.mirror = true;
		setRotation(body3, 0.3490659F, 0F, 0F);
		body4 = new ModelRenderer(this, 22, 14);
		body4.addBox(0F, 0F, 0F, 2, 2, 4);
		body4.setRotationPoint(-2.2F, 11.5F, -3.5F);
		body4.setTextureSize(64, 32);
		body4.mirror = true;
		setRotation(body4, 0.3490659F, 0F, 0F);
		body5 = new ModelRenderer(this, 32, 9);
		body5.addBox(0F, 0F, 0F, 2, 2, 3);
		body5.setRotationPoint(-1F, 12F, -2.5F);
		body5.setTextureSize(64, 32);
		body5.mirror = true;
		setRotation(body5, 0.3490659F, 0F, 0F);
		leftarm1 = new ModelRenderer(this, 0, 14);
		leftarm1.addBox(0F, 0F, 0F, 2, 4, 2);
		leftarm1.setRotationPoint(1.5F, 10F, 0F);
		leftarm1.setTextureSize(64, 32);
		leftarm1.mirror = true;
		setRotation(leftarm1, -0.1745329F, 0F, 0F);
		leftarm2 = new ModelRenderer(this, 8, 14);
		leftarm2.addBox(0F, 0F, 0F, 2, 2, 5);
		leftarm2.setRotationPoint(1.5F, 13.2F, -4F);
		leftarm2.setTextureSize(64, 32);
		leftarm2.mirror = true;
		setRotation(leftarm2, 0.1745329F, 0F, 0F);
		leftfinger1 = new ModelRenderer(this, 0, 0);
		leftfinger1.addBox(0F, 0F, 0F, 1, 1, 1);
		leftfinger1.setRotationPoint(0.5F, 13.1F, -4F);
		leftfinger1.setTextureSize(64, 32);
		leftfinger1.mirror = true;
		setRotation(leftfinger1, 0.1745329F, 0F, 0F);
		leftfinger2 = new ModelRenderer(this, 0, 0);
		leftfinger2.addBox(0F, 0F, 0F, 1, 1, 1);
		leftfinger2.setRotationPoint(0.5F, 14.2F, -3.9F);
		leftfinger2.setTextureSize(64, 32);
		leftfinger2.mirror = true;
		setRotation(leftfinger2, 0.1745329F, 0F, 0F);
		rightarm1 = new ModelRenderer(this, 0, 14);
		rightarm1.addBox(0F, 0F, 0F, 2, 4, 2);
		rightarm1.setRotationPoint(-3.5F, 10F, 0F);
		rightarm1.setTextureSize(64, 32);
		rightarm1.mirror = true;
		setRotation(rightarm1, -0.1745329F, 0F, 0F);
		rightarm2 = new ModelRenderer(this, 8, 14);
		rightarm2.addBox(0F, 0F, 0F, 2, 2, 5);
		rightarm2.setRotationPoint(-3.5F, 13.2F, -4F);
		rightarm2.setTextureSize(64, 32);
		rightarm2.mirror = true;
		setRotation(rightarm2, 0.1745329F, 0F, 0F);
		rightfinger1 = new ModelRenderer(this, 0, 0);
		rightfinger1.addBox(0F, 0F, 0F, 1, 1, 1);
		rightfinger1.setRotationPoint(-1.5F, 13.1F, -4F);
		rightfinger1.setTextureSize(64, 32);
		rightfinger1.mirror = true;
		setRotation(rightfinger1, 0.1745329F, 0F, 0F);
		rightfinger2 = new ModelRenderer(this, 0, 0);
		rightfinger2.addBox(0F, 0F, 0F, 1, 1, 1);
		rightfinger2.setRotationPoint(-1.5F, 14.2F, -3.8F);
		rightfinger2.setTextureSize(64, 32);
		rightfinger2.mirror = true;
		setRotation(rightfinger2, 0.1745329F, 0F, 0F);
		leftleg1 = new ModelRenderer(this, 48, 0);
		leftleg1.addBox(0F, 0F, 0F, 2, 2, 5);
		leftleg1.setRotationPoint(1F, 16F, -3F);
		leftleg1.setTextureSize(64, 32);
		leftleg1.mirror = true;
		setRotation(leftleg1, 0F, 0F, 0F);
		leftleg2 = new ModelRenderer(this, 48, 7);
		leftleg2.addBox(0F, 0F, 0F, 2, 4, 2);
		leftleg2.setRotationPoint(1F, 16F, -5F);
		leftleg2.setTextureSize(64, 32);
		leftleg2.mirror = true;
		setRotation(leftleg2, 0F, 0F, 0F);
		lefthoof2 = new ModelRenderer(this, 0, 3);
		lefthoof2.addBox(0F, 0F, 0F, 1, 2, 1);
		lefthoof2.setRotationPoint(2.1F, 20F, -5F);
		lefthoof2.setTextureSize(64, 32);
		lefthoof2.mirror = true;
		setRotation(lefthoof2, 0.2617994F, 0F, 0F);
		lefthoof1 = new ModelRenderer(this, 0, 3);
		lefthoof1.addBox(0F, 0F, 0F, 1, 2, 1);
		lefthoof1.setRotationPoint(0.9F, 20F, -5F);
		lefthoof1.setTextureSize(64, 32);
		lefthoof1.mirror = true;
		setRotation(lefthoof1, 0.2617994F, 0F, 0F);
		rightleg1 = new ModelRenderer(this, 48, 0);
		rightleg1.addBox(0F, 0F, 0F, 2, 2, 5);
		rightleg1.setRotationPoint(-3F, 16F, -3F);
		rightleg1.setTextureSize(64, 32);
		rightleg1.mirror = true;
		setRotation(rightleg1, 0F, 0F, 0F);
		rightleg2 = new ModelRenderer(this, 48, 7);
		rightleg2.addBox(0F, 0F, 0F, 2, 4, 2);
		rightleg2.setRotationPoint(-3F, 16F, -5F);
		rightleg2.setTextureSize(64, 32);
		rightleg2.mirror = true;
		setRotation(rightleg2, 0F, 0F, 0F);
		righthoof1 = new ModelRenderer(this, 0, 3);
		righthoof1.addBox(0F, 0F, 0F, 1, 2, 1);
		righthoof1.setRotationPoint(-1.9F, 20F, -5F);
		righthoof1.setTextureSize(64, 32);
		righthoof1.mirror = true;
		setRotation(righthoof1, 0.2617994F, 0F, 0F);
		righthoof2 = new ModelRenderer(this, 0, 3);
		righthoof2.addBox(0F, 0F, 0F, 1, 2, 1);
		righthoof2.setRotationPoint(-3.1F, 20F, -5F);
		righthoof2.setTextureSize(64, 32);
		righthoof2.mirror = true;
		setRotation(righthoof2, 0.2617994F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		base.render(f5);
		head1.render(f5);
		head2.render(f5);
		lefthorn.render(f5);
		righthorn.render(f5);
		leftear.render(f5);
		rightear.render(f5);
		body1.render(f5);
		body2.render(f5);
		body3.render(f5);
		body4.render(f5);
		body5.render(f5);
		leftarm1.render(f5);
		leftarm2.render(f5);
		leftfinger1.render(f5);
		leftfinger2.render(f5);
		rightarm1.render(f5);
		rightarm2.render(f5);
		rightfinger1.render(f5);
		rightfinger2.render(f5);
		leftleg1.render(f5);
		leftleg2.render(f5);
		lefthoof2.render(f5);
		lefthoof1.render(f5);
		rightleg1.render(f5);
		rightleg2.render(f5);
		righthoof1.render(f5);
		righthoof2.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
