/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.client.model.player.ModelStarSpawnPlayer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class LayerStarSpawnTentacles implements LayerRenderer<EntityPlayer> {

	private ModelStarSpawnPlayer model = new ModelStarSpawnPlayer();
	private final RenderPlayer render;
	private final ResourceLocation texture = new ResourceLocation("abyssalcraft:textures/model/tentacles.png");

	public LayerStarSpawnTentacles(RenderPlayer render){
		this.render = render;
	}

	@Override
	public void doRenderLayer(EntityPlayer player, float f, float f1, float partialTicks, float f2, float f3, float f4, float scale){
		if(EntityUtil.isPlayerCoralium(player) && !player.isInvisible()){

			render.bindTexture(texture);

			for (int j = 0; j < 1; ++j) {
				float f10 = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks - (player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks);
				float f11 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
				GlStateManager.pushMatrix();
				if (player.isSneaking())
					GlStateManager.translate(0.0F, 0.24F, 0.0F);
				GlStateManager.rotate(f10, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(f11, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0, -0.22F, 0);
				model.renderTentacles(0.0625F, player);
				GlStateManager.popMatrix();
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {

		return false;
	}
}
