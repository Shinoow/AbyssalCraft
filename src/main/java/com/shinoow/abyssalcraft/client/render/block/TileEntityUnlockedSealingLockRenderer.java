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
package com.shinoow.abyssalcraft.client.render.block;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityUnlockedSealingLock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class TileEntityUnlockedSealingLockRenderer extends TileEntitySpecialRenderer<TileEntityUnlockedSealingLock> {

	private final ItemStack KEY = new ItemStack(ACItems.sealing_key);

	@Override
	public void render(TileEntityUnlockedSealingLock te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {


		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.4F, (float) z + 0.5F);

		GlStateManager.pushMatrix();

		GlStateManager.rotate(45F, 0F, 0F, 1F);
		GlStateManager.translate(-0.28F, -0.41F, 0F);

		Minecraft.getMinecraft().getRenderItem().renderItem(KEY, ItemCameraTransforms.TransformType.GROUND);

		GlStateManager.popMatrix();

		GlStateManager.popMatrix();
	}

}
