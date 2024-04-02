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
package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.client.render.entity.RenderShoggoth;
import com.shinoow.abyssalcraft.common.entity.EntityShoggothBase;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerShoggothEyes extends LayerEyes<EntityShoggothBase>
{
	private static final ResourceLocation SHOGGOTH_EYES = new ResourceLocation("abyssalcraft:textures/model/shoggoth/lessershoggoth_eyes.png");
	private static final ResourceLocation ABYSSAL_EYES = new ResourceLocation("abyssalcraft:textures/model/shoggoth/abyssalshoggoth_eyes.png");
	private static final ResourceLocation DREADED_EYES = new ResourceLocation("abyssalcraft:textures/model/shoggoth/dreadedshoggoth_eyes.png");
	private static final ResourceLocation OMOTHOL_EYES = new ResourceLocation("abyssalcraft:textures/model/shoggoth/omotholshoggoth_eyes.png");
	private static final ResourceLocation DARK_EYES = new ResourceLocation("abyssalcraft:textures/model/shoggoth/shadowshoggoth_eyes.png");

	public LayerShoggothEyes(RenderShoggoth shoggothRendererIn)
	{
		super(shoggothRendererIn, SHOGGOTH_EYES);
		addAlpha(e -> e.getShoggothType() == 4 ? e.getBrightness() : 1.0F);
	}

	@Override
	public void doRenderLayer(EntityShoggothBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
		if(!ACConfig.shoggothGlowingEyes) return;
		super.doRenderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityShoggothBase par1EntityLiving) {

		switch (par1EntityLiving.getShoggothType())
		{
		case 0:
			return SHOGGOTH_EYES;
		case 1:
			return ABYSSAL_EYES;
		case 2:
			return DREADED_EYES;
		case 3:
			return OMOTHOL_EYES;
		case 4:
			return DARK_EYES;
		default:
			return SHOGGOTH_EYES;
		}
	}
}
