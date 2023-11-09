/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.model.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelDGArmor extends ModelDG {

	public ModelRenderer chestplate;

	public ModelDGArmor(){
		this(0.0F);
	}

	public ModelDGArmor(float f){
		super(f);

		chestplate = new ModelRenderer(this, 0, 18);
		chestplate.setTextureSize(128, 64);
		chestplate.addBox(-6F, -7.5F, -3F, 12, 15, 6, f);
		chestplate.setRotationPoint(0F, -3F, 3F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);

		if (isChild)
		{
			float f6 = 2.0F;

			GlStateManager.pushMatrix();
			GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GlStateManager.translate(0.0F, 24.0F * par7, 0.0F);
			chestplate.render(par7);
			GlStateManager.popMatrix();
		} else chestplate.render(par7);
	}

	@Override
	public void setInvisible(boolean invisible)
	{
		super.setInvisible(invisible);
		chestplate.showModel = invisible;
	}
}
