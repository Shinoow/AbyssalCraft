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

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.block.ISingletonInventory;
import com.shinoow.abyssalcraft.lib.client.render.TileEntitySingletonInventoryBlockRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemBlock;

/**
 * Middle layer for rendering the Item placed on a {@link ISingletonInventory}
 * <br>(Set through the non-empty constructor in {@link TileEntitySingletonInventoryBlockRenderer})
 * @author shinoow
 *
 */
public class ItemRenderingPose {

	/**
	 * Apply transformations, then display the ItemStack
	 * @param renderer The renderer this is being applied in
	 * @param pedestal Reference to the block itself (can be cased to a Tile Entity)
	 */
	public void applyPose(@Nonnull TileEntitySingletonInventoryBlockRenderer renderer, @Nonnull ISingletonInventory pedestal) {

		GlStateManager.pushMatrix();

		boolean flag = pedestal.getItem().getItem() instanceof ItemBlock;

		GlStateManager.rotate(180F, 1F, 0F, 0F);
		GlStateManager.translate(0.0F, flag ? -0.56F : -0.37F, 0F);
		GlStateManager.rotate(pedestal.shouldItemRotate() ? renderer.getWorldTime() : 0, 0F, 1F, 0F);

		Minecraft.getMinecraft().getRenderItem().renderItem(pedestal.getItem(), ItemCameraTransforms.TransformType.GROUND);

		GlStateManager.popMatrix();
	}
}
