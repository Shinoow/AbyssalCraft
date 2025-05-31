/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity.layers;

import java.util.function.Function;

import com.shinoow.abyssalcraft.client.render.entity.RenderDreadguard;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerDreadguardArmor implements LayerRenderer<EntityDreadguard>
{
	private final ResourceLocation ARMOR;
	private final RenderDreadguard renderer;

	public LayerDreadguardArmor(RenderDreadguard rendererIn, ResourceLocation armor)
	{
		renderer = rendererIn;
		ARMOR = armor;
	}

	@Override
	public void doRenderLayer(EntityDreadguard entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
		renderer.bindTexture(ARMOR);

		renderer.armorModel.render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}
}
