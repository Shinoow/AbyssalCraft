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

import com.shinoow.abyssalcraft.client.model.entity.ModelDemonSheep1;
import com.shinoow.abyssalcraft.client.render.entity.RenderDemonSheep;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonSheep;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerDemonSheepWool implements LayerRenderer<EntityDemonSheep>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("abyssalcraft:textures/model/demon_sheep_fur.png");
	private final RenderDemonSheep sheepRenderer;
	private final ModelDemonSheep1 sheepModel = new ModelDemonSheep1();

	public LayerDemonSheepWool(RenderDemonSheep sheepRendererIn)
	{
		sheepRenderer = sheepRendererIn;

	}

	@Override
	public void doRenderLayer(EntityDemonSheep entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
		if (!entitylivingbaseIn.isInvisible())
		{

			sheepRenderer.bindTexture(TEXTURE);

			if (entitylivingbaseIn.hasCustomName() && "jeb_".equals(entitylivingbaseIn.getCustomNameTag()))
			{
				int i = entitylivingbaseIn.ticksExisted / 25 + entitylivingbaseIn.getEntityId();
				int j = EnumDyeColor.values().length;
				int k = i % j;
				int l = (i + 1) % j;
				float f = (entitylivingbaseIn.ticksExisted % 25 + partialTicks) / 25.0F;
				float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k));
				float[] afloat2 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(l));
				GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
			}

			sheepModel.setModelAttributes(sheepRenderer.getMainModel());
			sheepModel.setLivingAnimations(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks);
			sheepModel.render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return true;
	}
}
