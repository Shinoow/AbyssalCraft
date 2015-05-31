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
package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.client.model.entity.ModelRemnant;
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRemnant extends RenderLiving {

	private static ResourceLocation defaultTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/Remnant.png");
	private static final ResourceLocation remnantTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/Remnant.png");
	private static final ResourceLocation librarianTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/Remnant_librarian.png");
	private static final ResourceLocation priestTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/Remnant_priest.png");
	private static final ResourceLocation blacksmithTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/Remnant_blacksmith.png");
	private static final ResourceLocation butcherTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/Remnant_butcher.png");
	private static final ResourceLocation bankerTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/Remnant_banker.png");
	private static final ResourceLocation masterBlacksmithTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/Remnant_master_blacksmith.png");

	public RenderRemnant()
	{
		super(new ModelRemnant(), 0.5F);
	}

	public void doRender(EntityRemnant entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		doRender((EntityRemnant)par1Entity, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getRemnantTexture(EntityRemnant entity){

		switch(entity.getProfession()){
		case 0:
			defaultTexture = remnantTexture;
			break;
		case 1:
			defaultTexture = librarianTexture;
			break;
		case 2:
			defaultTexture = priestTexture;
			break;
		case 3:
			defaultTexture = blacksmithTexture;
			break;
		case 4:
			defaultTexture = butcherTexture;
			break;
		case 5:
			defaultTexture = bankerTexture;
			break;
		case 6:
			defaultTexture = masterBlacksmithTexture;
			break;
		}

		return defaultTexture;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return getRemnantTexture((EntityRemnant)entity);
	}
}
