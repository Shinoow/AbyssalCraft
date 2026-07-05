/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2026 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.client.render.data;

import com.shinoow.abyssalcraft.api.block.ISingletonInventory;
import com.shinoow.abyssalcraft.lib.client.render.TileEntitySingletonInventoryBlockRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemBlock;

public class AltarPose extends ItemRenderingPose {

	@Override
	public void applyPose(TileEntitySingletonInventoryBlockRenderer renderer, ISingletonInventory pedestal) {

		GlStateManager.pushMatrix();

		boolean flag = pedestal.getItem().getItem() instanceof ItemBlock;

		GlStateManager.rotate(180F, 1F, 0F, 0F);
		GlStateManager.translate(0.0F, flag ? -0.62F : -0.43F, 0F);
		GlStateManager.rotate(pedestal.shouldItemRotate() ? renderer.getWorldTime() : 0, 0F, 1F, 0F);

		Minecraft.getMinecraft().getRenderItem().renderItem(pedestal.getItem(), ItemCameraTransforms.TransformType.GROUND);

		GlStateManager.popMatrix();
	}
}
