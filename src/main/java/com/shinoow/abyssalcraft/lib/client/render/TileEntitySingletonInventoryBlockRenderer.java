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
package com.shinoow.abyssalcraft.lib.client.render;

import com.shinoow.abyssalcraft.api.block.ISingletonInventory;
import com.shinoow.abyssalcraft.lib.ItemPoses;
import com.shinoow.abyssalcraft.lib.client.render.data.ItemRenderingPose;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * TESR used for rendering Items placed on pedestal blocks<br>
 * (the block rendering itself is done through JSON models).
 * @author shinoow
 *
 */
@SideOnly(Side.CLIENT)
public class TileEntitySingletonInventoryBlockRenderer extends TileEntitySpecialRenderer {

	private ItemRenderingPose pose;

	public TileEntitySingletonInventoryBlockRenderer() {
		this(ItemPoses.DEFAULT);
	}

	public TileEntitySingletonInventoryBlockRenderer(ItemRenderingPose poseIn) {
		pose = poseIn;
	}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

		ISingletonInventory ped = (ISingletonInventory)te;

		if (ped != null && !ped.getItem().isEmpty() && pose != null)
			pose.applyPose(this, ped);

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}

	public long getWorldTime() {
		return getWorld().getWorldTime();
	}
}
