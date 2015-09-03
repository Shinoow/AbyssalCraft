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

public class ModelHasturStatue extends ModelBase
{

	ModelRenderer base;
	ModelRenderer body;
	ModelRenderer face;
	ModelRenderer head1;
	ModelRenderer head2;
	ModelRenderer head3;
	ModelRenderer head4;
	ModelRenderer tentacle1;
	ModelRenderer tentacle2;
	ModelRenderer tentacle3;
	ModelRenderer tentacle4;
	ModelRenderer tentacle5;
	ModelRenderer tentacle6;
	ModelRenderer tentacle7;
	ModelRenderer tentacle8;
	ModelRenderer tentacle9;
	ModelRenderer tentacle10;
	ModelRenderer tentacle11;
	ModelRenderer tentacle12;
	ModelRenderer tentacle13;
	ModelRenderer tentacle14;
	ModelRenderer tentacle15;
	ModelRenderer tentacle16;
	ModelRenderer tentacle17;
	ModelRenderer tentacle18;
	ModelRenderer tentacle19;
	ModelRenderer tentacle20;
	ModelRenderer tentacle21;
	ModelRenderer tentacle22;
	ModelRenderer tentacle23;
	ModelRenderer tentacle24;

	public ModelHasturStatue()
	{
		textureWidth = 64;
		textureHeight = 32;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 8, 6, 8);
		base.setRotationPoint(-4F, 18F, -4F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		body = new ModelRenderer(this, 0, 14);
		body.addBox(0F, 0F, 0F, 5, 8, 5);
		body.setRotationPoint(-2.5F, 10F, -2.5F);
		body.setTextureSize(64, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		face = new ModelRenderer(this, 42, 5);
		face.addBox(0F, 0F, 0F, 3, 3, 1);
		face.setRotationPoint(-1.5F, 7F, -2F);
		face.setTextureSize(64, 32);
		face.mirror = true;
		setRotation(face, 0F, 0F, 0F);
		head1 = new ModelRenderer(this, 32, 0);
		head1.addBox(0F, 0F, 0F, 1, 4, 4);
		head1.setRotationPoint(1F, 6F, -2.5F);
		head1.setTextureSize(64, 32);
		head1.mirror = true;
		setRotation(head1, 0F, 0F, 0F);
		head2 = new ModelRenderer(this, 32, 0);
		head2.addBox(0F, 0F, 0F, 1, 4, 4);
		head2.setRotationPoint(-2F, 6F, -2.5F);
		head2.setTextureSize(64, 32);
		head2.mirror = true;
		setRotation(head2, 0F, 0F, 0F);
		head3 = new ModelRenderer(this, 42, 0);
		head3.addBox(0F, 0F, 0F, 2, 1, 4);
		head3.setRotationPoint(-1F, 6F, -2.5F);
		head3.setTextureSize(64, 32);
		head3.mirror = true;
		setRotation(head3, 0F, 0F, 0F);
		head4 = new ModelRenderer(this, 54, 0);
		head4.addBox(0F, 0F, 0F, 2, 3, 1);
		head4.setRotationPoint(-1F, 7F, 0.5F);
		head4.setTextureSize(64, 32);
		head4.mirror = true;
		setRotation(head4, 0F, 0F, 0F);
		tentacle1 = new ModelRenderer(this, 0, 0);
		tentacle1.addBox(0F, 0F, 0F, 1, 1, 3);
		tentacle1.setRotationPoint(2F, 8F, -4F);
		tentacle1.setTextureSize(64, 32);
		tentacle1.mirror = true;
		setRotation(tentacle1, 0.2974289F, -0.9294653F, 0F);
		tentacle2 = new ModelRenderer(this, 0, 0);
		tentacle2.addBox(0F, 0F, 0F, 1, 3, 1);
		tentacle2.setRotationPoint(0.5F, 8.5F, -2F);
		tentacle2.setTextureSize(64, 32);
		tentacle2.mirror = true;
		setRotation(tentacle2, -0.669215F, 0F, 0F);
		tentacle3 = new ModelRenderer(this, 0, 0);
		tentacle3.addBox(0F, 0F, 0F, 2, 1, 1);
		tentacle3.setRotationPoint(-1.5F, 8.5F, -2.5F);
		tentacle3.setTextureSize(64, 32);
		tentacle3.mirror = true;
		setRotation(tentacle3, -0.2974216F, 0F, 0F);
		tentacle4 = new ModelRenderer(this, 0, 0);
		tentacle4.addBox(0F, 0F, 0F, 1, 2, 1);
		tentacle4.setRotationPoint(-2.5F, 8.5F, -2.5F);
		tentacle4.setTextureSize(64, 32);
		tentacle4.mirror = true;
		setRotation(tentacle4, -0.2974289F, 0F, 0F);
		tentacle5 = new ModelRenderer(this, 0, 0);
		tentacle5.addBox(0F, 0F, 0F, 1, 3, 1);
		tentacle5.setRotationPoint(-2.5F, 10F, -3F);
		tentacle5.setTextureSize(64, 32);
		tentacle5.mirror = true;
		setRotation(tentacle5, -0.2974216F, 0F, -0.3346075F);
		tentacle6 = new ModelRenderer(this, 0, 0);
		tentacle6.addBox(0F, 0F, 0F, 1, 3, 1);
		tentacle6.setRotationPoint(-1F, 9F, -2.5F);
		tentacle6.setTextureSize(64, 32);
		tentacle6.mirror = true;
		setRotation(tentacle6, -0.1858931F, 0F, 0.6320364F);
		tentacle7 = new ModelRenderer(this, 0, 0);
		tentacle7.addBox(0F, 0F, 0F, 1, 2, 1);
		tentacle7.setRotationPoint(-2.9F, 11.5F, -3F);
		tentacle7.setTextureSize(64, 32);
		tentacle7.mirror = true;
		setRotation(tentacle7, -0.185895F, 0F, -0.247341F);
		tentacle8 = new ModelRenderer(this, 0, 0);
		tentacle8.addBox(0F, 0F, 0F, 3, 1, 1);
		tentacle8.setRotationPoint(-2.4F, 12.4F, -3.2F);
		tentacle8.setTextureSize(64, 32);
		tentacle8.mirror = true;
		setRotation(tentacle8, -0.185895F, 0F, 0F);
		tentacle9 = new ModelRenderer(this, 0, 0);
		tentacle9.addBox(0F, 0F, 0F, 1, 2, 1);
		tentacle9.setRotationPoint(-0.3F, 12.8F, -3.4F);
		tentacle9.setTextureSize(64, 32);
		tentacle9.mirror = true;
		setRotation(tentacle9, 0F, 0F, -0.2602503F);
		tentacle10 = new ModelRenderer(this, 0, 0);
		tentacle10.addBox(0F, 0F, 0F, 3, 1, 1);
		tentacle10.setRotationPoint(0.2F, 13.7F, -3.4F);
		tentacle10.setTextureSize(64, 32);
		tentacle10.mirror = true;
		setRotation(tentacle10, 0F, 0F, 0F);
		tentacle11 = new ModelRenderer(this, 0, 0);
		tentacle11.addBox(0F, 0F, 0F, 1, 1, 3);
		tentacle11.setRotationPoint(2.2F, 13.7F, -2.5F);
		tentacle11.setTextureSize(64, 32);
		tentacle11.mirror = true;
		setRotation(tentacle11, 0F, -0.1115358F, 0F);
		tentacle12 = new ModelRenderer(this, 20, 20);
		tentacle12.addBox(0F, 0F, 0F, 2, 4, 2);
		tentacle12.setRotationPoint(-2.5F, 17F, -2.5F);
		tentacle12.setTextureSize(64, 32);
		tentacle12.mirror = true;
		setRotation(tentacle12, -0.9666439F, 0F, -0.2230717F);
		tentacle13 = new ModelRenderer(this, 20, 26);
		tentacle13.addBox(0F, 0F, 0F, 4, 2, 2);
		tentacle13.setRotationPoint(-4F, 19.6F, -5.7F);
		tentacle13.setTextureSize(64, 32);
		tentacle13.mirror = true;
		setRotation(tentacle13, -0.9666506F, 0F, -0.2230705F);
		tentacle14 = new ModelRenderer(this, 28, 22);
		tentacle14.addBox(0F, 0F, 0F, 2, 2, 2);
		tentacle14.setRotationPoint(-3.6F, 21.1F, -4.6F);
		tentacle14.setTextureSize(64, 32);
		tentacle14.mirror = true;
		setRotation(tentacle14, -0.9666439F, 0F, -0.2230717F);
		tentacle15 = new ModelRenderer(this, 0, 0);
		tentacle15.addBox(0F, 0F, 0F, 1, 1, 3);
		tentacle15.setRotationPoint(2F, 17F, -5F);
		tentacle15.setTextureSize(64, 32);
		tentacle15.mirror = true;
		setRotation(tentacle15, 0F, -0.4089647F, 0F);
		tentacle16 = new ModelRenderer(this, 20, 20);
		tentacle16.addBox(0F, 0F, 0F, 2, 4, 2);
		tentacle16.setRotationPoint(2.5F, 16F, -2F);
		tentacle16.setTextureSize(64, 32);
		tentacle16.mirror = true;
		setRotation(tentacle16, -0.9666439F, -1.635859F, -0.2230717F);
		tentacle17 = new ModelRenderer(this, 20, 20);
		tentacle17.addBox(0F, 0F, 0F, 2, 4, 2);
		tentacle17.setRotationPoint(0F, 16F, 2.5F);
		tentacle17.setTextureSize(64, 32);
		tentacle17.mirror = true;
		setRotation(tentacle17, -0.9666439F, 3.011467F, -0.2230717F);
		tentacle18 = new ModelRenderer(this, 0, 0);
		tentacle18.addBox(0F, 0F, 0F, 1, 1, 3);
		tentacle18.setRotationPoint(1F, 17F, 2.5F);
		tentacle18.setTextureSize(64, 32);
		tentacle18.mirror = true;
		setRotation(tentacle18, -0.3346075F, 0F, 0F);
		tentacle19 = new ModelRenderer(this, 0, 0);
		tentacle19.addBox(0F, 0F, 0F, 3, 1, 1);
		tentacle19.setRotationPoint(-5F, 17F, 1.5F);
		tentacle19.setTextureSize(64, 32);
		tentacle19.mirror = true;
		setRotation(tentacle19, 0F, 0.4461433F, 0F);
		tentacle20 = new ModelRenderer(this, 20, 14);
		tentacle20.addBox(-1F, 0F, 0F, 2, 2, 4);
		tentacle20.setRotationPoint(5.7F, 18F, -0.3F);
		tentacle20.setTextureSize(64, 32);
		tentacle20.mirror = true;
		setRotation(tentacle20, -0.9666506F, -1.635862F, -0.2230705F);
		tentacle21 = new ModelRenderer(this, 0, 0);
		tentacle21.addBox(0F, 0F, 0F, 1, 3, 1);
		tentacle21.setRotationPoint(2.3F, 17F, -5.8F);
		tentacle21.setTextureSize(64, 32);
		tentacle21.mirror = true;
		setRotation(tentacle21, 0F, -0.4089656F, 0F);
		tentacle22 = new ModelRenderer(this, 20, 14);
		tentacle22.addBox(0F, 0F, 0F, 2, 2, 4);
		tentacle22.setRotationPoint(-1F, 18.2F, 5.6F);
		tentacle22.setTextureSize(64, 32);
		tentacle22.mirror = true;
		setRotation(tentacle22, -0.9666506F, 3.011461F, -0.2230705F);
		tentacle23 = new ModelRenderer(this, 0, 0);
		tentacle23.addBox(0F, 0F, 0F, 1, 4, 1);
		tentacle23.setRotationPoint(1F, 18F, 4.3F);
		tentacle23.setTextureSize(64, 32);
		tentacle23.mirror = true;
		setRotation(tentacle23, 0F, 0F, 0.2230717F);
		tentacle24 = new ModelRenderer(this, 0, 0);
		tentacle24.addBox(0F, 0F, 0F, 1, 5, 1);
		tentacle24.setRotationPoint(-5.8F, 17F, 1.9F);
		tentacle24.setTextureSize(64, 32);
		tentacle24.mirror = true;
		setRotation(tentacle24, 0F, 0.4461411F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		base.render(f5);
		body.render(f5);
		face.render(f5);
		head1.render(f5);
		head2.render(f5);
		head3.render(f5);
		head4.render(f5);
		tentacle1.render(f5);
		tentacle2.render(f5);
		tentacle3.render(f5);
		tentacle4.render(f5);
		tentacle5.render(f5);
		tentacle6.render(f5);
		tentacle7.render(f5);
		tentacle8.render(f5);
		tentacle9.render(f5);
		tentacle10.render(f5);
		tentacle11.render(f5);
		tentacle12.render(f5);
		tentacle13.render(f5);
		tentacle14.render(f5);
		tentacle15.render(f5);
		tentacle16.render(f5);
		tentacle17.render(f5);
		tentacle18.render(f5);
		tentacle19.render(f5);
		tentacle20.render(f5);
		tentacle21.render(f5);
		tentacle22.render(f5);
		tentacle23.render(f5);
		tentacle24.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
